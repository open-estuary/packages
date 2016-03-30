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

#include <odp_config.h>
#include <odp_pkt_uio.h>
#include <net/if.h>
#include <odp_debug_internal.h>
#include <odp_packet_io_internal.h>
#include <odp/pool.h>
#include <odp_kni.h>

#define KNI_PKTIO_DEV_NAME "kni_"

#define KNI_MAX_NUM_PER_ETHPORTS 1

#define KNI_MAX_NUM (KNI_MAX_NUM_PER_ETHPORTS * 4)

int kni_pktio_init(void)
{
	return odp_kni_init(KNI_MAX_NUM);
}

int kni_pktio_term(void)
{
	odp_kni_close();
	return 0;
}

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

static int _kni_netdev_is_valid(const char *netdev)
{
	if ((strncmp(netdev, KNI_PKTIO_DEV_NAME,
		     strlen(KNI_PKTIO_DEV_NAME)) != 0) ||
	    (strlen(netdev) <= strlen(KNI_PKTIO_DEV_NAME)) ||
	    !str_is_digital_number(netdev + strlen(KNI_PKTIO_DEV_NAME)))
		return 0;

	return 1;
}

int setup_pkt_kni(struct odp_kni *pkt_kni, const char *netdev,
		  odp_pool_t pool)
{
	int ret;
	uint8_t portid = 0;
	struct odp_kni_ops ops;
	struct odp_kni *kni = NULL;
	odp_pool_info_t info;
	unsigned mbuf_size;

	if (!_kni_netdev_is_valid(netdev)) {
		ODP_ERR("netdev %s, format err! should"
			" be pktio_x! x is digital number\n", netdev);
		return -1;
	}

	portid = atoi(netdev + strlen(KNI_PKTIO_DEV_NAME));

	memset(&info, 0, sizeof(info));
	ret = odp_pool_info(pool, &info);
	if (ret < 0) {
		ODP_ERR("netdev %s, odp_pool_info failed!\n");
		return -1;
	}

	switch (info.params.type) {
	case ODP_POOL_PACKET:
		mbuf_size = info.params.pkt.len;
		ODP_DBG("info.params.pkt.len = %d, info.params.pkt.num = %d\n",
			info.params.pkt.len, info.params.pkt.num);
		break;
	case ODP_POOL_BUFFER:
	case ODP_POOL_TIMEOUT:
	default:
		ODP_ERR("netdev %s, pool type err!\n");
		return -1;
	}

	memset(&ops, 0, sizeof(struct odp_kni_ops));
	kni = odp_kni_create(portid, mbuf_size, pool, &ops);
	if (!kni) {
		ODP_ERR("netdev %s, odp_kni_create failed!\n", netdev);
		return -1;
	}

	*pkt_kni = *kni;

	return 0;
}

static int kni_pktio_open(odp_pktio_t id ODP_UNUSED,
			  pktio_entry_t *pktio_entry,
			  const char *devname, odp_pool_t pool)
{
	return setup_pkt_kni(&pktio_entry->s.pkt_kni, devname, pool);
}

static int kni_pktio_close(pktio_entry_t *pktio_entry)
{
	return 0;
}

static int kni_pktio_recv(pktio_entry_t *pktio_entry,
			  odp_packet_t pkt_table[], unsigned int len)
{
	return odp_kni_rx_burst(&pktio_entry->s.pkt_kni, pkt_table, len);
}

static int kni_pktio_send(pktio_entry_t *pktio_entry,
			  odp_packet_t pkt_table[], unsigned len)
{
	return odp_kni_tx_burst(&pktio_entry->s.pkt_kni, pkt_table, len);
}

int kni_pktio_mtu_get(pktio_entry_t *pktio_entry)
{
	return -1;
}

int kni_pktio_mac_get(pktio_entry_t *pktio_entry, void *mac_addr)
{
	return -1;
}

int kni_pktio_promisc_mode_set(pktio_entry_t *pktio_entry, int enable)
{
	return 0;
}

int kni_pktio_promisc_mode_get(pktio_entry_t *pktio_entry)
{
	return 0;
}

const pktio_if_ops_t kni_pktio_ops = {
	.init  = kni_pktio_init,
	.term  = kni_pktio_term,
	.open  = kni_pktio_open,
	.close = kni_pktio_close,
	.recv  = kni_pktio_recv,
	.send  = kni_pktio_send,
	.mtu_get	  = kni_pktio_mtu_get,
	.promisc_mode_set = kni_pktio_promisc_mode_set,
	.promisc_mode_get = kni_pktio_promisc_mode_get,
	.mac_get	  = kni_pktio_mac_get,
};
