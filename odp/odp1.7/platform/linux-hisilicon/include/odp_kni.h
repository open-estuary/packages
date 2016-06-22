/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_KNI_H_
#define _ODP_KNI_H_

#include <odp_pci.h>
#include <odp_memory.h>

#include "odp_kni_common.h"

#ifdef __cplusplus
extern "C" {
#endif

struct odp_kni_ops {
	uint8_t port_id;
	int (*change_mtu)(uint8_t port_id, unsigned new_mtu);
	int (*config_network_if)(uint8_t port_id, uint8_t if_up);
};

struct odp_kni_conf {

	char		    name[ODP_KNI_NAMESIZE];
	uint32_t	    core_id;
	uint16_t	    group_id;
	unsigned	    mbuf_size;
	struct odp_pci_addr addr;
	struct odp_pci_id   id;

	uint8_t force_bind : 1;
};

struct odp_kni {
	char	   name[ODP_KNI_NAMESIZE];
	uint16_t   group_id;
	uint32_t   slot_id;
	odp_pool_t pktmbuf_pool;
	unsigned   mbuf_size;

	struct odp_kni_fifo *tx_q;
	struct odp_kni_fifo *rx_q;
	struct odp_kni_fifo *alloc_q;
	struct odp_kni_fifo *free_q;

	struct odp_kni_fifo *req_q;
	struct odp_kni_fifo *resp_q;
	void		    *sync_addr;

	struct odp_kni_ops ops;
	uint8_t		   in_use : 1;
};

typedef struct odp_kni pkt_kni_t;

int odp_kni_init(unsigned int max_kni_ifaces);

struct odp_kni *odp_kni_alloc(odp_pool_t		 pktmbuf_pool,
			      const struct odp_kni_conf *conf,
			      struct odp_kni_ops	*ops);

struct odp_kni *odp_kni_create(uint8_t		   port_id,
			       unsigned		   mbuf_size,
			       odp_pool_t	   pktmbuf_pool,
			       struct odp_kni_ops *ops);

struct odp_kni *odp_kni_info_get(uint8_t port_id) __attribute__ ((deprecated));

int odp_kni_register_handlers(struct odp_kni *kni, struct odp_kni_ops *ops);


void odp_kni_close(void);

int odp_kni_release(struct odp_kni *kni);

uint8_t odp_kni_get_port_id(struct odp_kni *kni) __attribute__ ((deprecated));

struct odp_kni *odp_kni_get(const char *name);

const char *odp_kni_get_name(const struct odp_kni *kni);

int odp_kni_handle_request(struct odp_kni *kni);

unsigned odp_kni_rx_burst(struct odp_kni *kni, odp_packet_t mbufs[],
			  unsigned num);

unsigned odp_kni_tx_burst(struct odp_kni *kni, odp_packet_t mbufs[],
			  unsigned num);


#ifdef __cplusplus
}
#endif
#endif /* _ODP_KNI_H_ */
