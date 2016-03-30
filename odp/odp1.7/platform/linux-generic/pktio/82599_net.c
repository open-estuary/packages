/* Copyright (c) 2013, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */

#define _GNU_SOURCE
#include <stdio.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ioctl.h>
#include <sys/mman.h>
#include <poll.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <stdlib.h>

#include <linux/ethtool.h>
#include <linux/sockios.h>

#include <odp_pkt_uio.h>
#include <net/if.h>
#include <odp_debug_internal.h>
#include <odp_packet_io_internal.h>
#include <odp_ether.h>
#include <odp_ethdev.h>

#define PKTIO_DEV_NAME "pktio_"

static uint16_t nb_rxd = ODP_TEST_RX_DESC_DEFAULT;
static uint16_t nb_txd = ODP_TEST_TX_DESC_DEFAULT;

static const struct odp_eth_conf port_conf = {
	.rxmode			 = {
		.mq_mode	 = ETH_MQ_RX_RSS,
		.max_rx_pkt_len = ODP_ETHER_MAX_LEN,
		.split_hdr_size = 0,
		.header_split	= 0,    /**< Header Split disabled */
		.hw_ip_checksum = 0,    /**< IP checksum offload disabled */
		.hw_vlan_filter = 0,    /**< VLAN filtering disabled */
		.jumbo_frame  = 0,      /**< Jumbo Frame Support disabled */
		.hw_strip_crc = 0,      /**< CRC stripped by hardware */
	},
	.rx_adv_conf		 = {
		.rss_conf	 = {
			.rss_key = NULL,
			.rss_hf	 = ETH_RSS_IPV4 | ETH_RSS_IPV6,
		},
	},
	.txmode			 = {
		.mq_mode	 = ETH_MQ_TX_NONE,
	},
};

static const struct odp_eth_rxconf rx_conf = {
	.rx_thresh	 = {
		.pthresh = RX_PTHRESH,
		.hthresh = RX_HTHRESH,
		.wthresh = RX_WTHRESH,
	},
};

static const struct odp_eth_txconf tx_conf = {
	.tx_thresh	 = {
		.pthresh = TX_PTHRESH,
		.hthresh = TX_HTHRESH,
		.wthresh = TX_WTHRESH,
	},
	.tx_free_thresh = 0,    /* Use UMD default values */
	.tx_rs_thresh	= 0,    /* Use UMD default values */

	/*
	 * As the example won't handle mult-segments and offload cases,
	 * set the flag by default.
	 */
	.txq_flags	 = ETH_TXQ_FLAGS_NOMULTSEGS | ETH_TXQ_FLAGS_NOOFFLOADS,
};

/* Test if s has only digits or not.*/
static int str_is_digital_number(const char *s)
{
	while (*s) {
		if (!isdigit(*s))
			return 0;

		s++;
	}

	return 1;
}

static int _odp_netdev_is_valid(const char *netdev)
{
	if ((strncmp(netdev, PKTIO_DEV_NAME, strlen(PKTIO_DEV_NAME)) != 0) ||
	    (strlen(netdev) <= strlen(PKTIO_DEV_NAME)) ||
	    !str_is_digital_number(netdev + strlen(PKTIO_DEV_NAME)))
		return 0;

	return 1;
}

int setup_net_82599(odp_pktio_t id ODP_UNUSED, pktio_entry_t *pktio_entry,
		    const char *netdev, odp_pool_t pool)
{
	uint8_t	portid = 0;
	uint16_t nbrxq = 0, nbtxq = 0;
	int ret, i;
	pkt_odp_t *const pkt_odp = &pktio_entry->s.pkt_odp;

	if (!_odp_netdev_is_valid(netdev)) {
		ODP_ERR("netdev %s, format err! should be"
			" pktio_x! x is digital number\n", netdev);
		return -1;
	}

	portid = atoi(netdev + strlen(PKTIO_DEV_NAME));
	pkt_odp->portid = portid;
	pkt_odp->pool = pool;
	pkt_odp->queueid = 0;

	/* On init set it up only to 1 rx and tx queue.*/
	nbtxq = 1;
	nbrxq = 1;

	ret = odp_eth_dev_configure(portid, nbrxq, nbtxq, &port_conf);
	if (ret < 0) {
		ODP_ERR("Cannot configure device: err=%d, port=%u\n",
			ret, (unsigned int)portid);
		return -1;
	}

	/* init one RX queue on each port */
	for (i = 0; i < nbrxq; i++) {
		ret = odp_eth_rx_queue_setup(portid, i, nb_rxd,
					     0, NULL, (void *)pool);
		if (ret < 0) {
			ODP_ERR("rxq:err=%d, port=%u\n",
				ret, (unsigned int)portid);
			return -1;
		}
	}

	/* init one TX queue on each port */
	for (i = 0; i < nbtxq; i++) {
		ret = odp_eth_tx_queue_setup(portid, i, nb_txd,
					     0, NULL, (void *)pool);
		if (ret < 0) {
			ODP_ERR("%s txq:err=%d, port=%u\n",
				ret, (unsigned int)portid);
			return -1;
		}
	}

	/* Start device */
	ret = odp_eth_dev_start(portid);
	if (ret < 0) {
		ODP_ERR("odp_eth_dev_start:err=%d, port=%u\n",
			ret, (unsigned int)portid);
		return -1;
	}

	return 0;
}

int close_net_82599(pktio_entry_t *pktio_entry)
{
	ODP_DBG("close pkt_odp, %u\n", pktio_entry->s.pkt_odp.portid);

	return 0;
}

int odp_82599_rx_burst(pktio_entry_t *pktio_entry, odp_packet_t *rx_pkts,
		       unsigned int nb_pkts)
{
	struct odp_eth_dev *dev;
	uint8_t port_id = pktio_entry->s.pkt_odp.portid;
	uint16_t queue_id = pktio_entry->s.pkt_odp.queueid;

	dev = &odp_eth_devices[port_id];

	int nb_rx = (*dev->rx_pkt_burst)(dev->data->rx_queues[queue_id],
					 (void **)rx_pkts, nb_pkts);

#ifdef ODP_ETHDEV_RXTX_CALLBACKS

	struct odp_eth_rxtx_callback *cb = dev->post_rx_burst_cbs[queue_id];

	if (odp_unlikely(cb)) {
		do {
			nb_rx = cb->fn.rx(port_id, queue_id,
					  (void **)rx_pkts, nb_rx, nb_pkts,
					  cb->param);
			cb = cb->next;
		} while (cb);
	}
#endif

	return nb_rx;
}

int odp_82599_tx_burst(pktio_entry_t *pktio_entry,
		       odp_packet_t *tx_pkts, unsigned nb_pkts)
{
	struct odp_eth_dev *dev;
	uint8_t	port_id	= pktio_entry->s.pkt_odp.portid;
	uint16_t queue_id = pktio_entry->s.pkt_odp.queueid;

	dev = &odp_eth_devices[port_id];

#ifdef ODP_ETHDEV_RXTX_CALLBACKS

	struct odp_eth_rxtx_callback *cb = dev->pre_tx_burst_cbs[queue_id];

	if (odp_unlikely(cb)) {
		do {
			nb_pkts = cb->fn.tx(port_id, queue_id,
					    (void **)tx_pkts, nb_pkts,
					    cb->param);
			cb = cb->next;
		} while (cb);
	}
#endif
	return (*dev->tx_pkt_burst)(dev->data->tx_queues[queue_id],
				    (void **)tx_pkts, nb_pkts);
}

int odp_82599_mtu_get(pktio_entry_t *pktio_entry)
{
	uint8_t	port_id = pktio_entry->s.pkt_odp.portid;
	uint16_t mtu;
	int ret;

	if (!odp_eth_dev_is_valid_port(port_id))
		return -1;

	ret = odp_eth_dev_get_mtu(port_id, &mtu);
	if (ret) {
		ODP_DBG("port_id %d get mtu failed!\n", port_id);
		return -1;
	}

	return (int)mtu;
}

int odp_82599_mac_get(pktio_entry_t *pktio_entry, void *mac_addr)
{
	uint8_t port_id = pktio_entry->s.pkt_odp.portid;

	if (!odp_eth_dev_is_valid_port(port_id))
		return -1;

	(void)odp_eth_get_macaddr(port_id, mac_addr);

	return ODPH_ETHADDR_LEN;
}

int odp_82599_promisc_mode_set(pktio_entry_t *pktio_entry, int enable)
{
	uint8_t port_id = pktio_entry->s.pkt_odp.portid;

	if (!odp_eth_dev_is_valid_port(port_id))
		return -1;

	if (enable)
		(void)odp_eth_promiscuous_enable(port_id);
	else
		(void)odp_eth_promiscuous_disable(port_id);

	return 0;
}

int odp_82599_promisc_mode_get(pktio_entry_t *pktio_entry)
{
	uint8_t port_id = pktio_entry->s.pkt_odp.portid;

	if (!odp_eth_dev_is_valid_port(port_id))
		return -1;

	return odp_eth_promiscuous_get(port_id);
}

/* Check the link status of all ports in up to 9s, and print them finally */
static void check_all_ports_link_status(uint8_t port_num, uint64_t port_mask)
{
#define CHECK_INTERVAL 100 /* 100ms */
#define MAX_CHECK_TIME 90  /* 9s (90 * 100ms) in total */

	uint8_t portid, count, all_ports_up, print_flag = 0;
	struct odp_eth_link link;

	printf("\nChecking link status");
	fflush(stdout);
	for (count = 0; count <= MAX_CHECK_TIME; count++) {
		all_ports_up = 1;
		for (portid = 0; portid < port_num; portid++) {
			if ((port_mask & (1ULL << portid)) == 0)
				continue;

			memset(&link, 0, sizeof(link));
			odp_eth_link_get_nowait(portid, &link);

			/* print link status if flag set */
			if (print_flag == 1) {
				if (link.link_status)
					printf("Port %d Link Up - speed %u "
					       "Mbps - %s\n", (uint8_t)portid,
					       (unsigned)link.link_speed,
					       (link.link_duplex ==
						ETH_LINK_FULL_DUPLEX) ?
					       ("full-duplex") : (
						       "half-duplex\n"));
				else
					printf("Port %d Link Down\n",
					       (uint8_t)portid);

				continue;
			}

			/* clear all_ports_up flag if any link down */
			if (link.link_status == 0) {
				all_ports_up = 0;
				break;
			}
		}

		/* after finally printing all link status, get out */
		if (print_flag == 1)
			break;

		if (all_ports_up == 0) {
			printf(".");
			fflush(stdout);
			usleep(CHECK_INTERVAL * 1000);
		}

		/* set the print_flag if all ports up or timeout */
		if (all_ports_up == 1 || count == (MAX_CHECK_TIME - 1)) {
			print_flag = 1;
			printf("done\n");
		}
	}
}

int ixgbe_link_status(pktio_entry_t *pktio_entry)
{
	check_all_ports_link_status(34, 0x03);
	return 0;
}

const pktio_if_ops_t ixgbe_eth_pktio_ops = {
	.init  = NULL,
	.term  = NULL,
	.open  = setup_net_82599,
	.close = close_net_82599,
	.recv  = odp_82599_rx_burst,
	.send  = odp_82599_tx_burst,
	.mtu_get	  = odp_82599_mtu_get,
	.promisc_mode_set = odp_82599_promisc_mode_set,
	.promisc_mode_get = odp_82599_promisc_mode_get,
	.mac_get     = odp_82599_mac_get,
	.link_status = ixgbe_link_status,
};

static int odp_pktio_is_not_82599(pktio_entry_t *entry)
{
	return (entry->s.ops != &ixgbe_eth_pktio_ops);
}

int odp_pktio_ixgbe_restart(odp_pktio_t id)
{
	pktio_entry_t *entry;
	uint8_t port_id;
	int ret;

	entry = get_pktio_entry(id);
	if (entry == NULL) {
		ODP_ERR("pktio entry %d does not exist\n", id);
		return -1;
	}

	if (odp_unlikely(is_free(entry))) {
		ODP_ERR("already freed pktio\n");
		return -1;
	}

	if (odp_pktio_is_not_82599(entry)) {
		ODP_ERR("pktio entry %d is not ODP UMD pktio\n", id);
		return -1;
	}

	port_id = entry->s.pkt_odp.portid;

	if (!odp_eth_dev_is_valid_port(port_id)) {
		ODP_ERR("pktio entry %d ODP UMD"
			" Invalid port_id=%d\n", id, port_id);
		return -1;
	}

	/* Stop device */
	odp_eth_dev_stop(port_id);

	/* Start device */
	ret = odp_eth_dev_start(port_id);
	if (ret < 0) {
		ODP_ERR("odp_eth_dev_start:err=%d, port=%u\n",
			ret, (unsigned)port_id);
		return -1;
	}

	ODP_DBG("odp pmd restart done\n\n");

	return 0;
}
