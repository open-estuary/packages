/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_KNI_COMMON_H_
#define _ODP_KNI_COMMON_H_

#ifdef __KERNEL__
#include <linux/if.h>
#endif

#include <odp/align.h>

#define ODP_KNI_NAMESIZE 32

#ifndef ODP_CACHE_LINE_SIZE
#define ODP_CACHE_LINE_SIZE 64
#endif

enum odp_kni_req_id {
	ODP_KNI_REQ_UNKNOWN = 0,
	ODP_KNI_REQ_CHANGE_MTU,
	ODP_KNI_REQ_CFG_NETWORK_IF,
	ODP_KNI_REQ_MAX,
};

struct odp_kni_request {
	uint32_t req_id;

	union {
		uint32_t new_mtu;
		uint8_t	 if_up;
	};
	int32_t result;
} __attribute__((__packed__));

struct odp_kni_fifo {
	unsigned write;
	unsigned read;
	unsigned len;
	unsigned elem_size;
	void	*buffer[0];
};

struct odp_kni_mbuf {
	void	*buf_addr ODP_ALIGNED(ODP_CACHE_LINE_SIZE);
	char		  pad0[10];
	uint16_t	  data_off;
	char		  pad1[4];
	uint64_t	  ol_flags;

#ifdef RTE_NEXT_ABI
	char	 pad2[4];
	uint32_t pkt_len;
	uint16_t data_len;

#else
	char	 pad2[2];
	uint16_t data_len;
	uint32_t pkt_len;
#endif
	char  pad3[8] ODP_ALIGNED(ODP_CACHE_LINE_SIZE);
	void *pool;
	void *next;
};

struct odp_kni_device_info {
	char name[ODP_KNI_NAMESIZE];

	phys_addr_t tx_phys;
	phys_addr_t rx_phys;
	phys_addr_t alloc_phys;
	phys_addr_t free_phys;
	phys_addr_t req_phys;
	phys_addr_t resp_phys;
	phys_addr_t sync_phys;
	void	   *sync_va;

	void	   *mbuf_va;
	phys_addr_t mbuf_phys;

	uint16_t vendor_id;
	uint16_t device_id;
	uint8_t	 bus;
	uint8_t	 devid;
	uint8_t	 function;

	uint16_t group_id;
	uint32_t core_id;

	uint8_t force_bind : 1;
	unsigned mbuf_size;
};

#define KNI_DEVICE "kni"

#define ODP_KNI_IOCTL_TEST    _IOWR(0, 1, int)
#define ODP_KNI_IOCTL_CREATE  _IOWR(0, 2, struct odp_kni_device_info)
#define ODP_KNI_IOCTL_RELEASE _IOWR(0, 3, struct odp_kni_device_info)
#endif /* _ODP_KNI_COMMON_H_ */
