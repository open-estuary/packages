/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef ODP_PACKET_ODP_H
#define ODP_PACKET_ODP_H

#include <stdint.h>

#include <odp/helper/eth.h>

#include <odp/align.h>
#include <odp/debug.h>
#include <odp/packet.h>
#include <odp_packet_internal.h>
#include <odp/pool.h>
#include <odp_pool_internal.h>
#include <odp_buffer_internal.h>
#include <odp/std_types.h>

#include <odp_config.h>
#include <odp_memory.h>
#include <odp_mmdistrict.h>
#include <odp_tailq.h>
#include <odp_base.h>
#include <odp_core.h>
#include <odp_cycles.h>
#include <odp_pci.h>
#include <odp_ether.h>
#include <odp_ethdev.h>
#define ODP_ODP_MODE_HW 0
#define ODP_ODP_MODE_SW 1
#define ODP_BLOCKING_IO

#define RX_PTHRESH 8
#define RX_HTHRESH 8
#define RX_WTHRESH 4

#define TX_PTHRESH 36
#define TX_HTHRESH 0
#define TX_WTHRESH 0
#define BURST_TX_DRAIN_US	 100
#define ODP_TEST_RX_DESC_DEFAULT 128
#define ODP_TEST_TX_DESC_DEFAULT 512

typedef struct {
	odp_pool_t pool;

	char	 ifname[32];
	uint8_t	 portid;
	uint16_t queueid;
} pkt_odp_t;

#endif
