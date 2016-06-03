/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#include <sys/types.h>
#include <sys/queue.h>
#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>
#include <errno.h>
#include <stdint.h>
#include <inttypes.h>
#include <netinet/in.h>
#include <odp/config.h>

#include <odp_pci.h>
#include <odp_memory.h>
#include <odp_mmdistrict.h>
#include <odp_base.h>
#include <odp_core.h>
#include <odp/atomic.h>
#include <odp_common.h>
#include <odp/spinlock.h>

#include "odp_ether.h"
#include "odp_ethdev.h"
#include "odp_dev.h"
#include "odp_debug_internal.h"
#include "odp_hisi_atomic.h"
#include <sys/types.h>
#include <ifaddrs.h>
#include <sys/ioctl.h>
#include <sys/socket.h>
#include <net/if.h>

static const char *MZ_ODP_ETH_DEV_DATA = "odp_eth_dev_data";
struct odp_eth_dev odp_eth_devices[ODP_MAX_ETHPORTS];
static struct odp_eth_dev_data *odp_eth_dev_data;
static uint8_t nb_ports;

/* spinlock for eth device callbacks */
static odp_spinlock_t odp_eth_dev_cb_lock = {
	0
}; /* initiated unlock */

struct odp_eth_callback {
	TAILQ_ENTRY(odp_eth_callback) next;

	odp_eth_cb_fn	cb_fn;
	void		       *cb_arg;
	enum odp_eth_event_type event;
	uint32_t		active;
};

enum {
	STAT_QMAP_TX = 0,
	STAT_QMAP_RX
};

enum {
	DEV_DETACHED = 0,
	DEV_ATTACHED
};

#define ETHTOOL_GDRVINFO 0x00000003        /* Get driver info. */
#define SIOCETHTOOL	 0x8946

/* these strings are set to whatever the driver author decides... */
struct ethtool_drvinfo {
	uint32_t cmd;

	/* driver short name, "tulip", "eepro100" */
	char driver[32];

	/* driver version string */
	char version[32];

	/* firmware version string, if applicable */
	char fw_version[32];

	/* Bus info for this IF. */
	/* For PCI devices, use pci_dev->slot_name. */
	char bus_info[32];
	char erom_version[32];
	char reserved2[16];

	/* number of u64's from ETHTOOL_GSTATS */
	uint32_t n_stats;

	/* Size of data from ETHTOOL_GEEPROM (bytes) */
	uint32_t testinfo_len;
	uint32_t eedump_len;

	/* Size of data from ETHTOOL_GREGS (bytes) */
	uint32_t regdump_len;
};

/*allocate eth device by name*/
struct odp_eth_dev *odp_eth_allocated(const char *name)
{
	unsigned int i;

	for (i = 0; i < ODP_MAX_ETHPORTS; i++)
		if ((odp_eth_devices[i].attached == DEV_ATTACHED) &&
		    (strcmp(odp_eth_devices[i].data->name, name) == 0))
			return &odp_eth_devices[i];

	return NULL;
}

/*allocate eth device by port id*/
struct odp_eth_dev *odp_eth_allocated_id(int portid)
{
	if (portid < ODP_MAX_ETHPORTS &&
	    odp_eth_devices[portid].attached == DEV_ATTACHED)
		return &odp_eth_devices[portid];

	return NULL;
}

/*allocate eth data mem*/
inline void odp_eth_data_alloc(void)
{
	const unsigned flags = 0;
	const struct odp_mm_district *mz;

	if (odp_process_type() == ODP_PROC_PRIMARY)
		mz = odp_mm_district_reserve(MZ_ODP_ETH_DEV_DATA,
					     MZ_ODP_ETH_DEV_DATA,
					     ODP_MAX_ETHPORTS *
					     sizeof(*odp_eth_dev_data),
					     odp_socket_id(), flags);
	else
		mz = odp_mm_district_lookup(MZ_ODP_ETH_DEV_DATA);

	if (mz == NULL) {
		ODP_PRINT("Cannot allocate mm_district for ethernet port data\n");
		return;
	}

	odp_eth_dev_data = mz->addr;

	if (odp_process_type() == ODP_PROC_PRIMARY)
		memset(odp_eth_dev_data, 0, ODP_MAX_ETHPORTS *
		       sizeof(*odp_eth_dev_data));
}

/*free eth device port id*/
uint8_t odp_eth_find_free_port(enum odp_eth_nic_type type)
{
	int ret;
	char driver_type = ODP_ETH_NIC_UNKNOWN;
	unsigned int idx;
	static int   eth_dev_init_flag;
	struct ifaddrs *ifap;
	struct ifaddrs *iter_if;
	struct ethtool_drvinfo drvinfo;

	if (!eth_dev_init_flag) {
		memset(odp_eth_devices, 0, sizeof(struct odp_eth_dev) *
		       ODP_MAX_ETHPORTS);
		eth_dev_init_flag = 1;
	}

	if (getifaddrs(&ifap) != 0)
		perror("getifaddrs: ");

	iter_if = ifap;
	do {
		if ((iter_if->ifa_addr->sa_family == AF_PACKET) &&
		    (strncmp("odp", iter_if->ifa_name, strlen("odp")) == 0)) {
			struct ifreq ifr;

			strcpy(ifr.ifr_name, iter_if->ifa_name);

			/* Create socket */
			int sock = socket(AF_INET, SOCK_DGRAM, 0);

			if (sock == -1)
				ODP_PRINT("\nsocket\n");

			drvinfo.cmd  = ETHTOOL_GDRVINFO;
			ifr.ifr_data = (caddr_t)&drvinfo;
			ret = ioctl(sock, SIOCETHTOOL, &ifr);
			if (ret < 0) {
				ODP_PRINT("\nCannot get driver information\n");
				break;
			}

			driver_type = drvinfo.reserved2[0];
			if (driver_type != type)
				goto next_if;

			idx = atoi(iter_if->ifa_name + strlen("odp"));
			if (idx == -1) {
				ODP_PRINT("\n alloc port_id failed!\n");
				return ODP_MAX_ETHPORTS;
			}

			if (odp_eth_devices[idx].attached == DEV_DETACHED)
				return idx;
		}

next_if:
		iter_if = iter_if->ifa_next;
	} while (iter_if != NULL);

	freeifaddrs(ifap);

	return ODP_MAX_ETHPORTS;
}

/*allocate eth devices*/
struct odp_eth_dev *odp_eth_dev_allocate(const char
					 *name,
					 enum
					 odp_eth_nic_type
					 type)
{
	uint8_t port_id;
	struct odp_eth_dev *eth_dev;

	port_id = odp_eth_find_free_port(type);
	if (port_id == ODP_MAX_ETHPORTS) {
		ODP_ERR("Reached maximum number "
			"of Ethernet ports\n");
		return NULL;
	}

	if (odp_eth_allocated(name) != NULL) {
		ODP_ERR("Ethernet Device with name "
			"%s already allocated!\n", name);
		return NULL;
	}

	if (odp_eth_dev_data == NULL)
		odp_eth_data_alloc();

	eth_dev = &odp_eth_devices[port_id];
	eth_dev->data = &odp_eth_dev_data[port_id];
	snprintf(eth_dev->data->name,
		 sizeof(eth_dev->data->name), "%s", name);
	eth_dev->attached = DEV_ATTACHED;
	switch (type) {
	case	ODP_ETH_NIC_SOC:
		eth_dev->dev_type = ODP_ETH_DEV_SOC;
		break;
	case	ODP_ETH_DEV_UNKNOWN:
		eth_dev->dev_type = ODP_ETH_DEV_UNKNOWN;
		break;
	default:
		eth_dev->dev_type = ODP_ETH_DEV_PCI;
		break;
	}

	eth_dev->data->port_id = port_id;
	nb_ports++;

	return eth_dev;
}

/*release eth device port*/
int odp_eth_release_port(struct odp_eth_dev *eth_dev)
{
	if (eth_dev == NULL)
		return -EINVAL;

	eth_dev->attached = DEV_DETACHED;
	nb_ports--;
	return 0;
}

/*create unique eth device name*/
inline int odp_eth_create_unique_device_name(
	char		      *name,
	size_t		       size,
	struct odp_pci_device *pci_dev)
{
	int ret;

	if ((name == NULL) || (pci_dev == NULL))
		return -EINVAL;

	ret = snprintf(name, size, "%d:%d.%d",
		       pci_dev->addr.bus, pci_dev->addr.devid,
		       pci_dev->addr.function);
	if (ret < 0)
		return ret;

	return 0;
}

/*init eth devices*/
static int odp_eth_init(struct odp_pci_driver *pci_drv,
			    struct odp_pci_device *pci_dev)
{
	int diag;
	struct eth_driver *eth_drv;
	struct odp_eth_dev *eth_dev;
	char ethdev_name[ODP_ETH_NAME_MAX_LEN];

	eth_drv = (struct eth_driver *)pci_drv;

	odp_eth_create_unique_device_name(ethdev_name,
					      sizeof(ethdev_name),
					      pci_dev);

	eth_dev =
		odp_eth_dev_allocate(ethdev_name,
				     ODP_ETH_NIC_IXGBE);
	if (eth_dev == NULL)
		return -ENOMEM;

	if (odp_process_type() == ODP_PROC_PRIMARY) {
		eth_dev->data->dev_private =
			odp_zmalloc("dev_priv",
				    eth_drv->dev_private_size,
				    0);
		if (eth_dev->data->dev_private == NULL)
			ODP_PRINT("Cannot allocate "
				  "mm_district for private port data\n");
	}

	eth_dev->pci_dev = pci_dev;
	eth_dev->driver	 = eth_drv;
	eth_dev->data->rx_mbuf_alloc_failed = 0;

	TAILQ_INIT(&eth_dev->link_intr_cbs);

	eth_dev->data->mtu = ODP_ETHER_MTU;

	diag = (*eth_drv->eth_dev_init)(eth_dev);
	if (diag == 0)
		return 0;

	ODP_ERR("driver %s: eth_dev_init("
		"vendor_id=0x%u device_id=0x%x)"
		" failed\n", pci_drv->name,
		(unsigned)pci_dev->id.vendor_id,
		(unsigned)pci_dev->id.device_id);
	if (odp_process_type() == ODP_PROC_PRIMARY)
		free(eth_dev->data->dev_private);

	eth_dev->attached = DEV_DETACHED;
	nb_ports--;
	return diag;
}

/*uninit eth devices*/
static int odp_eth_uninit(struct odp_pci_device *pci_dev)
{
	int ret;
	const struct eth_driver *eth_drv;
	struct odp_eth_dev *eth_dev;
	char ethdev_name[ODP_ETH_NAME_MAX_LEN];

	if (pci_dev == NULL)
		return -EINVAL;

	odp_eth_create_unique_device_name(ethdev_name,
					      sizeof(ethdev_name),
					      pci_dev);

	eth_dev = odp_eth_allocated(ethdev_name);
	if (eth_dev == NULL)
		return -ENODEV;

	eth_drv = (const struct eth_driver *)pci_dev->driver;

	/* Invoke UMD device uninit function */
	if (*eth_drv->eth_dev_uninit) {
		ret = (*eth_drv->eth_dev_uninit)(eth_dev);
		if (ret)
			return ret;
	}

	/* free ether device */
	odp_eth_release_port(eth_dev);

	if (odp_process_type() == ODP_PROC_PRIMARY)
		free(eth_dev->data->dev_private);

	eth_dev->pci_dev = NULL;
	eth_dev->driver	 = NULL;
	eth_dev->data = NULL;

	return 0;
}


/*register eth driver*/
void odp_eth_driver_register(struct eth_driver *eth_drv)
{
	eth_drv->pci_drv.devinit = odp_eth_init;
	eth_drv->pci_drv.devuninit = odp_eth_uninit;
	odp_pci_register(&eth_drv->pci_drv);
}

/*check eth device port is valid*/
int odp_eth_is_valid_port(uint8_t port_id)
{
	if ((port_id >= ODP_MAX_ETHPORTS) ||
	    (odp_eth_devices[port_id].attached != DEV_ATTACHED))
		return 0;
	else
		return 1;
}

/*get eth device socket id*/
int odp_eth_socket_id(uint8_t port_id)
{
	if (!odp_eth_is_valid_port(port_id))
		return RET_ERROR;

	return odp_eth_devices[port_id].pci_dev->numa_node;
}

/*count eth device number*/
uint8_t odp_eth_count(void)
{
	return nb_ports;
}

/*get changed eth device port*/
int odp_eth_get_changed_port(struct odp_eth_dev *
					devs,
					uint8_t            *
					port_id)
{
	if ((!devs) || (!port_id))
		return -EINVAL;

	for (*port_id = 0; *port_id < ODP_MAX_ETHPORTS;
	     (*port_id)++, devs++)
		if (odp_eth_devices[*port_id].attached ^
		    devs->attached)
			return 0;

	return -ENODEV;
}

/*get eth device type*/
enum odp_eth_dev_type odp_eth_get_device_type(
	uint8_t port_id)
{
	if (!odp_eth_is_valid_port(port_id))
		return ODP_ETH_DEV_UNKNOWN;

	return odp_eth_devices[port_id].dev_type;
}

/*save eth device*/
int odp_eth_save(struct odp_eth_dev *devs,
			    size_t		size)
{
	if (!devs ||
	    (size !=
	     sizeof(struct odp_eth_dev) * ODP_MAX_ETHPORTS))
		return -EINVAL;

	memcpy(devs, odp_eth_devices, size);
	return 0;
}

/*get eth device addr by port*/
int odp_eth_get_addr_by_port(uint8_t port_id,
					struct odp_pci_addr
					       *addr)
{
	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return -EINVAL;
	}

	if (addr == NULL) {
		ODP_ERR("Null pointer is specified\n");
		return -EINVAL;
	}

	*addr = odp_eth_devices[port_id].pci_dev->addr;
	return 0;
}

/*get eth device name by port*/
int odp_eth_get_name_by_port(uint8_t port_id,
					char   *name)
{
	char *tmp;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return -EINVAL;
	}

	if (name == NULL) {
		ODP_ERR("Null pointer is specified\n");
		return -EINVAL;
	}

	tmp = odp_eth_dev_data[port_id].name;
	strcpy(name, tmp);
	return 0;
}

/*check eth device is detachable*/
int odp_eth_is_detachable(uint8_t port_id)
{
	uint32_t drv_flags;

	if (port_id >= ODP_MAX_ETHPORTS) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return -EINVAL;
	}

	if (odp_eth_devices[port_id].dev_type ==
	    ODP_ETH_DEV_PCI)
		switch (odp_eth_devices[port_id].pci_dev->kdrv)	{
		case ODP_KDRV_IGB_UIO:
			break;
		default:
			return -ENOTSUP;
		}

	drv_flags =
		odp_eth_devices[port_id].driver->pci_drv.
		drv_flags;
	return !(drv_flags & ODP_PCI_DRV_DETACHABLE);
}

/*attach physical eth device*/
int odp_eth_attach_pdev(struct odp_pci_addr *addr,
				   uint8_t             *
				   port_id)
{
	uint8_t new_port_id;
	struct odp_eth_dev devs[ODP_MAX_ETHPORTS];

	if ((addr == NULL) || (port_id == NULL))
		goto err;

	if (odp_eth_save(devs, sizeof(devs)))
		goto err;

	if (odp_pci_scan())
		goto err;

	if (odp_probe_pci_one(addr))
		goto err;

	if (odp_eth_get_changed_port(devs, &new_port_id))
		goto err;

	*port_id = new_port_id;
	return RET_OK;
err:
	ODP_PRINT("Driver, cannot attach the device\n");
	return RET_ERROR;
}

/*detach physical eth device*/
int odp_eth_detach_pdev(uint8_t		port_id,
				   struct odp_pci_addr *addr)
{
	struct odp_pci_addr freed_addr;
	struct odp_pci_addr vp;

	if (addr == NULL)
		goto err;

	if (odp_eth_is_detachable(port_id))
		goto err;

	if (odp_eth_get_addr_by_port(port_id, &freed_addr))
		goto err;

	vp.domain = 0;
	vp.bus = 0;
	vp.devid = 0;
	vp.function = 0;
	if (odp_compare_pci_addr(&vp, &freed_addr) == 0)
		goto err;

	if (odp_close_pci_one(&freed_addr))
		goto err;

	*addr = freed_addr;
	return RET_OK;
err:
	ODP_PRINT("Driver, cannot detach the device\n");
	return RET_ERROR;
}

/*attach virtual eth device*/
int odp_eth_attach_vdev(const char *vdevargs,
				   uint8_t    *port_id)
{
	int ret = -1;
	uint8_t new_port_id;
	char   *name = NULL, *args = NULL;
	struct odp_eth_dev devs[ODP_MAX_ETHPORTS];

	if ((vdevargs == NULL) || (port_id == NULL))
		goto end;

	if (odp_parse_devargs_str(vdevargs, &name, &args))
		goto end;

	if (odp_eth_save(devs, sizeof(devs)))
		goto end;

	if (odp_vdev_init(name, args))
		goto end;

	if (odp_eth_get_changed_port(devs, &new_port_id))
		goto end;

	ret = 0;
	*port_id = new_port_id;
end:
	if (args)
		free(args);

	if (name)
		free(name);

	if (ret < 0)
		ODP_PRINT("Driver, cannot attach the device\n");

	return ret;
}

/*detach virtual eth device*/
int odp_eth_detach_vdev(uint8_t port_id,
				   char	  *vdevname)
{
	char name[ODP_ETH_NAME_MAX_LEN];

	if (vdevname == NULL)
		goto err;

	if (odp_eth_is_detachable(port_id))
		goto err;

	if (odp_eth_get_name_by_port(port_id, name))
		goto err;

	if (odp_vdev_uninit(name))
		goto err;

	strncpy(vdevname, name, sizeof(name));
	return RET_OK;
err:
	ODP_PRINT("Driver, cannot detach the device\n");
	return RET_ERROR;
}

/*attach eth device*/
int odp_eth_dev_attach(const char *devargs,
		       uint8_t	  *port_id)
{
	struct odp_pci_addr addr;

	if ((devargs == NULL) || (port_id == NULL))
		return -EINVAL;

	if (odp_parse_pci_dombdf(devargs, &addr) == 0)
		return odp_eth_attach_pdev(&addr, port_id);
	else
		return odp_eth_attach_vdev(devargs,
					       port_id);
}

/*detach eth device*/
int odp_eth_dev_detach(uint8_t port_id, char *name)
{
	struct odp_pci_addr addr;
	int ret;

	if (name == NULL)
		return -EINVAL;

	if (odp_eth_get_device_type(port_id) ==
	    ODP_ETH_DEV_PCI) {
		ret =
			odp_eth_get_addr_by_port(port_id,
						     &addr);
		if (ret < 0)
			return ret;

		ret = odp_eth_detach_pdev(port_id, &addr);
		if (ret == 0)
			snprintf(name, ODP_ETH_NAME_MAX_LEN,
				 "%04x:%02x:%02x.%d",
				 addr.domain, addr.bus,
				 addr.devid, addr.function);

		return ret;
	} else {
		return odp_eth_detach_vdev(port_id, name);
	}
}

/*config eth devices rx queues*/
int odp_eth_rx_queue_config(struct odp_eth_dev *
				       dev,
				       uint16_t nb_queues)
{
	void **rxq;
	unsigned i;
	uint16_t old_nb_queues = dev->data->nb_rx_queues;

	if (dev->data->rx_queues == NULL) {
		dev->data->rx_queues =
			odp_zmalloc("rx_que",
				    sizeof(dev->data->rx_queues[
						   0]) *
				    nb_queues, 0);
		if (dev->data->rx_queues == NULL) {
			dev->data->nb_rx_queues = 0;
			return -(ENOMEM);
		}
	} else {
		if (*dev->dev_ops->rx_queue_release == NULL) {
			ODP_ERR("function not support!\n");
			return -ENOTSUP;
		}

		rxq = dev->data->rx_queues;

		for (i = nb_queues; i < old_nb_queues; i++)
			(*dev->dev_ops->rx_queue_release)(rxq[i]);

		rxq =
			odp_zmalloc("rxq",
				    sizeof(rxq[0]) * nb_queues,
				    0);
		if (rxq == NULL)
			return -(ENOMEM);

		if (nb_queues > old_nb_queues) {
			uint16_t new_qs = nb_queues -
					  old_nb_queues;

			memset(rxq + old_nb_queues, 0,
			       sizeof(rxq[0]) * new_qs);
		}

		dev->data->rx_queues = rxq;
	}

	dev->data->nb_rx_queues = nb_queues;

	return 0;
}

/*config eth devices tx queues*/
int odp_eth_tx_queue_config(struct odp_eth_dev *
				       dev,
				       uint16_t nb_queues)
{
	void **txq;
	unsigned i;
	uint16_t old_nb_queues = dev->data->nb_tx_queues;

	if (dev->data->tx_queues == NULL) {
		dev->data->tx_queues =
			odp_zmalloc("tx_que",
				    sizeof(dev->data->tx_queues[
						   0]) *
				    nb_queues, 0);
		if (dev->data->tx_queues == NULL) {
			dev->data->nb_tx_queues = 0;
			return -(ENOMEM);
		}
	} else {
		if (*dev->dev_ops->tx_queue_release == NULL) {
			ODP_ERR("function not support!\n");
			return -ENOTSUP;
		}

		txq = dev->data->tx_queues;

		for (i = nb_queues; i < old_nb_queues; i++)
			(*dev->dev_ops->tx_queue_release)(txq[i]);

		txq =
			odp_zmalloc("txq",
				    sizeof(txq[0]) * nb_queues,
				    0);
		if (txq == NULL)
			return -ENOMEM;

		if (nb_queues > old_nb_queues) {
			uint16_t new_qs = nb_queues -
					  old_nb_queues;

			memset(txq + old_nb_queues, 0,
			       sizeof(txq[0]) * new_qs);
		}

		dev->data->tx_queues = txq;
	}

	dev->data->nb_tx_queues = nb_queues;
	return 0;
}

/*check eth device's vf rss throught rxq num*/
int odp_eth_check_vf_rss_rxq_num(uint8_t  port_id,
					    uint16_t nb_rx_q)
{
	struct odp_eth_dev *dev = &odp_eth_devices[port_id];

	switch (nb_rx_q) {
	case 1:
	case 2:
		ODP_ETH_DEV_SRIOV(dev).active =
			ETH_64_POOLS;
		break;
	case 4:
		ODP_ETH_DEV_SRIOV(dev).active =
			ETH_32_POOLS;
		break;
	default:
		return -EINVAL;
	}

	ODP_ETH_DEV_SRIOV(dev).nb_q_per_pool  = nb_rx_q;
	ODP_ETH_DEV_SRIOV(dev).def_pool_q_idx =
		dev->pci_dev->max_vfs * nb_rx_q;

	return 0;
}

/*check eth devices multiple queues mode*/
int odp_eth_check_mq_mode(uint8_t			port_id,
				     uint16_t			nb_rx_q,
				     uint16_t			nb_tx_q,
				     const struct odp_eth_conf *dev_conf)
{
	struct odp_eth_dev *dev = &odp_eth_devices[port_id];

	if (ODP_ETH_DEV_SRIOV(dev).active != 0) {
		if ((dev_conf->rxmode.mq_mode ==
		     ETH_MQ_RX_DCB) ||
		    (dev_conf->rxmode.mq_mode ==
		     ETH_MQ_RX_DCB_RSS) ||
		    (dev_conf->txmode.mq_mode ==
		     ETH_MQ_TX_DCB)) {
			ODP_ERR("ethdev port_id=%u"
				" SRIOV active, "
				"wrong VMDQ mq_mode rx %u tx %u\n",
				port_id,
				dev_conf->rxmode.mq_mode,
				dev_conf->txmode.mq_mode);
			return (-EINVAL);
		}

		switch (dev_conf->rxmode.mq_mode) {
		case ETH_MQ_RX_VMDQ_DCB:
		case ETH_MQ_RX_VMDQ_DCB_RSS:
			ODP_ERR("ethdev port_id=%u"
				" SRIOV active, "
				"unsupported VMDQ mq_mode rx %u\n",
				port_id,
				dev_conf->rxmode.mq_mode);
			return (-EINVAL);
		case ETH_MQ_RX_VMDQ_RSS:
			dev->data->dev_conf.rxmode.mq_mode =
				ETH_MQ_RX_VMDQ_RSS;
			if (nb_rx_q <=
			    ODP_ETH_DEV_SRIOV(dev).nb_q_per_pool)
				if (
					odp_eth_check_vf_rss_rxq_num(
						port_id,
						nb_rx_q)
					!=
					0) {
					ODP_ERR(
						"ethdev port_id=%d"
						" SRIOV active, invalid queue"
						" number for VMDQ RSS, allowed"
						" value are 1, 2 or 4\n",
						port_id);
					return -EINVAL;
				}

			break;
		case ETH_MQ_RX_RSS:
			ODP_ERR("ethdev port_id=%u"
				" SRIOV active, "
				"Rx mq mode is changed from:"
				"mq_mode %u into VMDQ mq_mode %u\n",
				port_id,
				dev_conf->rxmode.mq_mode,
				dev->data->dev_conf.rxmode.
				mq_mode);

		default:
			dev->data->dev_conf.rxmode.mq_mode =
				ETH_MQ_RX_VMDQ_ONLY;
			if (ODP_ETH_DEV_SRIOV(dev).nb_q_per_pool
			    > 1)
				ODP_ETH_DEV_SRIOV(dev).
				nb_q_per_pool = 1;

			break;
		}

		switch (dev_conf->txmode.mq_mode) {
		case ETH_MQ_TX_VMDQ_DCB:

			ODP_ERR("ethdev port_id=%u"
				" SRIOV active, "
				"unsupported VMDQ mq_mode tx %u\n",
				port_id,
				dev_conf->txmode.mq_mode);
			return (-EINVAL);
		default:
			dev->data->dev_conf.txmode.mq_mode =
				ETH_MQ_TX_VMDQ_ONLY;
			break;
		}

		if ((nb_rx_q >
		     ODP_ETH_DEV_SRIOV(dev).nb_q_per_pool) ||
		    (nb_tx_q >
		     ODP_ETH_DEV_SRIOV(dev).nb_q_per_pool)) {
			ODP_ERR(
				"ethdev port_id=%d SRIOV active, "
				"queue number must less equal to %d\n",
				port_id,
				ODP_ETH_DEV_SRIOV(dev).
				nb_q_per_pool);
			return (-EINVAL);
		}
	} else {

		if (dev_conf->rxmode.mq_mode ==
		    ETH_MQ_RX_VMDQ_DCB) {
			const struct odp_eth_vmdq_dcb_conf *conf;

			if (nb_rx_q !=
			    ETH_VMDQ_DCB_NUM_QUEUES) {
				ODP_ERR("ethdev port_id=%d "
					"VMDQ+DCB, nb_rx_q "
					"!= %d\n",
					port_id,
					ETH_VMDQ_DCB_NUM_QUEUES);
				return (-EINVAL);
			}

			conf =
				&dev_conf->rx_adv_conf.
				vmdq_dcb_conf;
			if (!((conf->nb_queue_pools ==
			       ETH_16_POOLS) ||
			      (conf->nb_queue_pools ==
			       ETH_32_POOLS))) {
				ODP_ERR("ethdev port_id=%d "
					"VMDQ+DCB selected, "
					"nb_queue_pools must be %d or %d\n",
					port_id,
					ETH_16_POOLS,
					ETH_32_POOLS);
				return (-EINVAL);
			}
		}

		if (dev_conf->txmode.mq_mode ==
		    ETH_MQ_TX_VMDQ_DCB) {
			const struct odp_eth_vmdq_dcb_tx_conf *
				conf;

			if (nb_tx_q !=
			    ETH_VMDQ_DCB_NUM_QUEUES) {
				ODP_ERR("ethdev port_id=%d "
					"VMDQ+DCB, nb_tx_q "
					"!= %d\n",
					port_id,
					ETH_VMDQ_DCB_NUM_QUEUES);
				return (-EINVAL);
			}

			conf =
				&dev_conf->tx_adv_conf.
				vmdq_dcb_tx_conf;
			if (!((conf->nb_queue_pools ==
			       ETH_16_POOLS) ||
			      (conf->nb_queue_pools ==
			       ETH_32_POOLS))) {
				ODP_ERR("ethdev port_id=%d "
					"VMDQ+DCB selected, "
					"nb_queue_pools != %d "
					"or nb_queue_pools "
					"!= %d\n",
					port_id, ETH_16_POOLS,
					ETH_32_POOLS);
				return (-EINVAL);
			}
		}

		if (dev_conf->rxmode.mq_mode == ETH_MQ_RX_DCB) {
			const struct odp_eth_dcb_rx_conf *conf;

			if (nb_rx_q != ETH_DCB_NUM_QUEUES) {
				ODP_ERR("ethdev port_id=%d "
					"DCB, nb_rx_q "
					"!= %d\n",
					port_id,
					ETH_DCB_NUM_QUEUES);
				return (-EINVAL);
			}

			conf =
				&dev_conf->rx_adv_conf.
				dcb_rx_conf;
			if (!((conf->nb_tcs == ETH_4_TCS) ||
			      (conf->nb_tcs == ETH_8_TCS))) {
				ODP_ERR("ethdev port_id=%d "
					"DCB selected, "
					"nb_tcs != %d or nb_tcs "
					"!= %d\n",
					port_id,
					ETH_4_TCS, ETH_8_TCS);
				return (-EINVAL);
			}
		}

		if (dev_conf->txmode.mq_mode == ETH_MQ_TX_DCB) {
			const struct odp_eth_dcb_tx_conf *conf;

			if (nb_tx_q != ETH_DCB_NUM_QUEUES) {
				ODP_ERR("ethdev port_id=%d "
					"DCB, nb_tx_q "
					"!= %d\n",
					port_id,
					ETH_DCB_NUM_QUEUES);
				return (-EINVAL);
			}

			conf =
				&dev_conf->tx_adv_conf.dcb_tx_conf;
			if (!((conf->nb_tcs == ETH_4_TCS) ||
			      (conf->nb_tcs == ETH_8_TCS))) {
				ODP_ERR("ethdev port_id=%d "
					"DCB selected, "
					"nb_tcs != %d or nb_tcs "
					"!= %d\n",
					port_id, ETH_4_TCS,
					ETH_8_TCS);
				return (-EINVAL);
			}
		}
	}

	return 0;
}

/*configure eth devices*/
int odp_eth_configure(uint8_t port_id, uint16_t nb_rx_q,
			  uint16_t nb_tx_q,
			  const struct odp_eth_conf *
			  dev_conf)
{
	int diag;
	struct odp_eth_dev *dev;
	struct odp_eth_dev_info dev_info;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return (-EINVAL);
	}

	if (nb_tx_q > ODP_MAX_QUEUES_PER_PORT) {
		ODP_ERR(
			"Number of TX queues requested (%u) is "
			"greater than max supported(%d)\n",
			nb_tx_q, ODP_MAX_QUEUES_PER_PORT);
		return (-EINVAL);
	}

	if (nb_rx_q > ODP_MAX_QUEUES_PER_PORT) {
		ODP_ERR(
			"Number of RX queues requested (%u) is "
			"greater than max supported(%d)\n",
			nb_rx_q, ODP_MAX_QUEUES_PER_PORT);
		return (-EINVAL);
	}

	dev = &odp_eth_devices[port_id];

	if ((*dev->dev_ops->dev_infos_get == NULL) ||
	    (*dev->dev_ops->dev_configure == NULL)) {
		ODP_ERR("function not support!\n");
		return -ENOTSUP;
	}

	if (dev->data->dev_started) {
		ODP_ERR(
			"port %d must be stopped to allow configuration\n",
			port_id);
		return (-EBUSY);
	}

	(*dev->dev_ops->dev_infos_get)(dev, &dev_info);
	if (nb_rx_q == 0) {
		ODP_ERR("ethdev port_id=%d nb_rx_q == 0\n",
			port_id);
		return (-EINVAL);
	}

	if (nb_rx_q > dev_info.max_rx_queues) {
		ODP_ERR(
			"ethdev port_id=%d nb_rx_queues=%d > %d\n",
			port_id, nb_rx_q,
			dev_info.max_rx_queues);
		return (-EINVAL);
	}

	if (nb_tx_q == 0) {
		ODP_ERR("ethdev port_id=%d nb_tx_q == 0\n",
			port_id);
		return (-EINVAL);
	}

	if (nb_tx_q > dev_info.max_tx_queues) {
		ODP_ERR(
			"ethdev port_id=%d nb_tx_queues=%d > %d\n",
			port_id, nb_tx_q,
			dev_info.max_tx_queues);
		return (-EINVAL);
	}

	memcpy(&dev->data->dev_conf, dev_conf,
	       sizeof(dev->data->dev_conf));

	if (dev_conf->intr_conf.lsc == 1) {
		const struct odp_pci_driver *pci_drv =
			&dev->driver->pci_drv;

		if (!(pci_drv->drv_flags &
		      ODP_PCI_DRV_INTR_LSC)) {
			ODP_ERR(
				"driver %s does not support lsc\n",
				pci_drv->name);
			return (-EINVAL);
		}
	}

	if (dev_conf->rxmode.jumbo_frame == 1) {
		if (dev_conf->rxmode.max_rx_pkt_len >
		    dev_info.max_rx_pktlen) {
			ODP_ERR(
				"ethdev port_id=%d max_rx_pkt_len %u"
				" > max valid value %u\n",
				port_id,
				(unsigned)dev_conf->rxmode.
				max_rx_pkt_len,
				(unsigned)dev_info.max_rx_pktlen);
			return (-EINVAL);
		} else if (dev_conf->rxmode.max_rx_pkt_len <
			   ODP_ETHER_MIN_LEN) {
			ODP_ERR(
				"ethdev port_id=%d max_rx_pkt_len %u"
				" < min valid value %u\n",
				port_id,
				(unsigned)dev_conf->rxmode.
				max_rx_pkt_len,
				(unsigned)ODP_ETHER_MIN_LEN);
			return (-EINVAL);
		}
	} else if ((dev_conf->rxmode.max_rx_pkt_len <
		    ODP_ETHER_MIN_LEN) ||
		   (dev_conf->rxmode.max_rx_pkt_len >
		    ODP_ETHER_MAX_LEN)) {
		dev->data->dev_conf.rxmode.max_rx_pkt_len =
			ODP_ETHER_MAX_LEN;
	}

	diag =
		odp_eth_check_mq_mode(port_id, nb_rx_q,
					  nb_tx_q,
					  dev_conf);
	if (diag != 0) {
		ODP_ERR(
			"port%d odp_eth_check_mq_mode = %d\n",
			port_id, diag);
		return diag;
	}

	diag = odp_eth_tx_queue_config(dev, nb_tx_q);
	if (diag != 0) {
		ODP_ERR(
			"port%d odp_eth_tx_queue_config = %d\n",
			port_id, diag);
		odp_eth_rx_queue_config(dev, 0);
		return diag;
	}

	diag = odp_eth_rx_queue_config(dev, nb_rx_q);
	if (diag != 0) {
		ODP_ERR(
			"port%d odp_eth_rx_queue_config = %d\n",
			port_id, diag);
		return diag;
	}

	diag = (*dev->dev_ops->dev_configure)(dev);
	if (diag != 0) {
		ODP_ERR("port%d dev_configure = %d\n",
			port_id, diag);
		odp_eth_rx_queue_config(dev, 0);
		odp_eth_tx_queue_config(dev, 0);
		return diag;
	}

	return 0;
}

void odp_eth_config_restore(uint8_t port_id)
{
	uint16_t i;
	uint32_t pool = 0;
	struct odp_eth_dev *dev;
	struct odp_eth_dev_info dev_info;
	struct odp_ether_addr	addr;

	dev = &odp_eth_devices[port_id];

	odp_eth_info_get(port_id, &dev_info);

	if (ODP_ETH_DEV_SRIOV(dev).active)
		pool = ODP_ETH_DEV_SRIOV(dev).def_vmdq_idx;

	for (i = 0; i < dev_info.max_mac_addrs; i++) {
		addr = dev->data->mac_addrs[i];

		if (is_zero_ether_addr(&addr))
			continue;

		if (*dev->dev_ops->mac_addr_add &&
		    (dev->data->mac_pool_sel[i] &
		     (1ULL << pool))) {
			(*dev->dev_ops->mac_addr_add)(dev,
						      &addr, i,
						      pool);
		} else {
			ODP_ERR("port %d: MAC address "
				"array not supported\n",
				port_id);

			break;
		}
	}

	if (odp_eth_allmulticast_get(port_id) == 1)
		odp_eth_allmulticast_enable(port_id);
	else if (odp_eth_allmulticast_get(port_id) == 0)
		odp_eth_allmulticast_disable(port_id);

	if (odp_eth_promiscuous_get(port_id) == 1)
		odp_eth_promiscuous_enable(port_id);
	else if (odp_eth_promiscuous_get(port_id) == 0)
		odp_eth_promiscuous_disable(port_id);
}

/*start eth devices function*/
int odp_eth_start(uint8_t port_id)
{
	int diag;
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return (-EINVAL);
	}

	dev = &odp_eth_devices[port_id];
	if (*dev->dev_ops->dev_start == NULL) {
		ODP_ERR("function not support!\n");
		return -ENOTSUP;
	}

	if (dev->data->dev_started != 0) {
		ODP_ERR(
			"Device with port_id=%u already started\n",
			port_id);
		return 0;
	}

	diag = (*dev->dev_ops->dev_start)(dev);
	if (diag == 0)
		dev->data->dev_started = 1;
	else
		return diag;

	odp_eth_config_restore(port_id);

	if (dev->data->dev_conf.intr_conf.lsc != 0) {
		if (*dev->dev_ops->link_update == NULL) {
			ODP_ERR("function not support!\n");
			return -ENOTSUP;
		}

		(*dev->dev_ops->link_update)(dev, 0);
	}

	return 0;
}

/*close eth devices*/
void odp_eth_stop(uint8_t port_id)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return;
	}

	dev = &odp_eth_devices[port_id];

	if (*dev->dev_ops->dev_stop == NULL) {
		ODP_ERR("function not support!\n");
		return;
	}

	if (dev->data->dev_started == 0) {
		ODP_ERR("Device with port_id=%u"
			" already stopped\n",
			port_id);
		return;
	}

	dev->data->dev_started = 0;
	(*dev->dev_ops->dev_stop)(dev);
}

/*setup eth devices rx queue*/
int odp_eth_rx_queue_setup(uint8_t	port_id,
			   uint16_t	rx_queue_id,
			   uint16_t	nb_rx_desc,
			   unsigned int socket_id,
			   const struct odp_eth_rxconf *
			   rx_conf,
			   void	       *mp)
{
	int ret;

	struct odp_eth_dev *dev;
	struct odp_eth_dev_info dev_info;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return (-EINVAL);
	}

	dev = &odp_eth_devices[port_id];
	if (rx_queue_id >= dev->data->nb_rx_queues) {
		ODP_ERR("Invalid RX queue_id=%d\n",
			rx_queue_id);
		return (-EINVAL);
	}

	if (dev->data->dev_started) {
		ODP_ERR(
			"port %d must be stopped to allow configuration\n",
			port_id);
		return -EBUSY;
	}

	if (*dev->dev_ops->rx_queue_setup == NULL) {
		ODP_ERR("function not support!\n");
		return -ENOTSUP;
	}

	odp_eth_info_get(port_id, &dev_info);

	if (rx_conf == NULL)
		rx_conf = &dev_info.default_rxconf;

	ret =
		(*dev->dev_ops->rx_queue_setup)(dev,
						rx_queue_id,
						nb_rx_desc,
						socket_id,
						rx_conf, mp);

	return ret;
}

/*setup eth devices tx queue*/
int odp_eth_tx_queue_setup(uint8_t port_id,
			   uint16_t tx_queue_id,
			   uint16_t nb_tx_desc,
			   unsigned int socket_id,
			   const struct odp_eth_txconf *
			   tx_conf, void *mp)
{
	struct odp_eth_dev *dev;
	struct odp_eth_dev_info dev_info;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return (-EINVAL);
	}

	dev = &odp_eth_devices[port_id];
	if (tx_queue_id >= dev->data->nb_tx_queues) {
		ODP_ERR("Invalid TX queue_id=%d\n",
			tx_queue_id);
		return (-EINVAL);
	}

	if (dev->data->dev_started) {
		ODP_ERR(
			"port %d must be stopped to allow configuration\n",
			port_id);
		return -EBUSY;
	}

	if (*dev->dev_ops->tx_queue_setup == NULL) {
		ODP_ERR("function not support!\n");
		return -ENOTSUP;
	}

	odp_eth_info_get(port_id, &dev_info);

	if (tx_conf == NULL)
		tx_conf = &dev_info.default_txconf;

	return (*dev->dev_ops->tx_queue_setup)(dev, tx_queue_id,
					       nb_tx_desc,
					       socket_id,
					       tx_conf, mp);
}

/*enable eth devices promiscuous status*/
void odp_eth_promiscuous_enable(uint8_t port_id)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return;
	}

	dev = &odp_eth_devices[port_id];

	if (*dev->dev_ops->promiscuous_enable == NULL) {
		ODP_ERR("function not support!\n");
		return;
	}

	(*dev->dev_ops->promiscuous_enable)(dev);
	dev->data->promiscuous = 1;
}

/*disable eth devices promiscuous status*/
void odp_eth_promiscuous_disable(uint8_t port_id)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return;
	}

	dev = &odp_eth_devices[port_id];

	if (*dev->dev_ops->promiscuous_disable == NULL) {
		ODP_ERR("function not support!\n");
		return;
	}

	dev->data->promiscuous = 0;
	(*dev->dev_ops->promiscuous_disable)(dev);
}

/*get eth devices promiscuous status*/
int odp_eth_promiscuous_get(uint8_t port_id)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return RET_ERROR;
	}

	dev = &odp_eth_devices[port_id];
	return dev->data->promiscuous;
}

/*enable eth devices multicast function*/
void odp_eth_allmulticast_enable(uint8_t port_id)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return;
	}

	dev = &odp_eth_devices[port_id];

	if (*dev->dev_ops->allmulticast_enable == NULL) {
		ODP_ERR("function not support!\n");
		return;
	}

	(*dev->dev_ops->allmulticast_enable)(dev);
	dev->data->all_multicast = 1;
}

/*disable eth devices multicast function*/
void odp_eth_allmulticast_disable(uint8_t port_id)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return;
	}

	dev = &odp_eth_devices[port_id];

	if (*dev->dev_ops->allmulticast_disable == NULL)
		return;

	dev->data->all_multicast = 0;
	(*dev->dev_ops->allmulticast_disable)(dev);
}

/*get eth devices multicast status*/
int odp_eth_allmulticast_get(uint8_t port_id)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return RET_ERROR;
	}

	dev = &odp_eth_devices[port_id];
	return dev->data->all_multicast;
}

/*read eth devices link status*/
int odp_eth_atomic_read_link_status(struct odp_eth_dev    *dev,
					struct   odp_eth_link *link)
{
	struct odp_eth_link *dst = link;
	struct odp_eth_link *src = &dev->data->dev_link;

	if (!odp_atomic_cmpset_u64_a64((odp_atomic_u64_t *)dst,
				       *(uint64_t *)dst,
				       *(uint64_t *)src))
		return RET_ERROR;

	return RET_OK;
}

/*link up eth devices*/
void odp_eth_link_get(uint8_t		   port_id,
		      struct odp_eth_link *eth_link)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return;
	}

	dev = &odp_eth_devices[port_id];

	if (dev->data->dev_conf.intr_conf.lsc != 0) {
		odp_eth_atomic_read_link_status(dev,
						    eth_link);
	} else {
		if (*dev->dev_ops->link_update == NULL) {
			ODP_ERR("function not support!\n");
			return;
		}

		(*dev->dev_ops->link_update)(dev, 1);
		*eth_link = dev->data->dev_link;
	}
}

/*link up eth devices immediately*/
void odp_eth_link_get_nowait(uint8_t		  port_id,
			     struct odp_eth_link *eth_link)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return;
	}

	dev = &odp_eth_devices[port_id];

	if (dev->data->dev_conf.intr_conf.lsc != 0) {
		odp_eth_atomic_read_link_status(dev,
						    eth_link);
	} else {
		if (*dev->dev_ops->link_update == NULL) {
			ODP_ERR("function not support!\n");
			return;
		}

		(*dev->dev_ops->link_update)(dev, 0);
		*eth_link = dev->data->dev_link;
	}
}

/*get eth devices mtu*/
int odp_eth_get_mtu(uint8_t port_id, uint16_t *mtu)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return (-ENODEV);
	}

	dev  = &odp_eth_devices[port_id];
	*mtu = dev->data->mtu;
	return 0;
}

/*get device information by port id*/
void odp_eth_info_get(uint8_t		   port_id,
			  struct odp_eth_dev_info *dev_info)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return;
	}

	dev = &odp_eth_devices[port_id];

	memset(dev_info, 0, sizeof(struct odp_eth_dev_info));

	if (*dev->dev_ops->dev_infos_get == NULL) {
		ODP_ERR("function not support!\n");
		return;
	}

	(*dev->dev_ops->dev_infos_get)(dev, dev_info);
	dev_info->pci_dev = dev->pci_dev;
	if (dev->driver)
		dev_info->driver_name =
			dev->driver->pci_drv.name;
}

/*get eth devices MAC address*/
void odp_eth_get_macaddr(uint8_t		port_id,
			 struct odp_ether_addr *mac_addr)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return;
	}

	dev = &odp_eth_devices[port_id];
	ether_addr_copy(&dev->data->mac_addrs[0], mac_addr);
}

/*set up eth devices filter*/
int odp_eth_dev_filter_ctrl(uint8_t		 port_id,
			    enum odp_filter_type filter_type,
			    enum odp_filter_op	 filter_op,
			    void		*arg)
{
	struct odp_eth_dev *dev;

	if (!odp_eth_is_valid_port(port_id)) {
		ODP_ERR("Invalid port_id=%d\n", port_id);
		return -ENODEV;
	}

	dev = &odp_eth_devices[port_id];

	if (*dev->dev_ops->filter_ctrl == NULL) {
		ODP_ERR("function not support!\n");
		return -ENOTSUP;
	}

	return (*dev->dev_ops->filter_ctrl)(dev, filter_type,
					    filter_op, arg);
}

/*call all eth device's callback function*/
void odp_eth_callback_process(struct odp_eth_dev     *
				   dev,
				   enum odp_eth_event_type
				   event)
{
	struct odp_eth_callback *cb_lst;
	struct odp_eth_callback  dev_cb;

	odp_spinlock_lock(&odp_eth_dev_cb_lock);
	TAILQ_FOREACH(cb_lst, &dev->link_intr_cbs, next)
	{
		if ((!cb_lst->cb_fn) ||
		    (cb_lst->event != event))
			continue;

		dev_cb = *cb_lst;
		cb_lst->active = 1;
		odp_spinlock_unlock(&odp_eth_dev_cb_lock);
		dev_cb.cb_fn(dev->data->port_id, dev_cb.event,
			     dev_cb.cb_arg);
		odp_spinlock_lock(&odp_eth_dev_cb_lock);
		cb_lst->active = 0;
	}
	odp_spinlock_unlock(&odp_eth_dev_cb_lock);
}

