/* Copyright (c) 2013, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */

#ifndef ODP_PACKET_DPDK_H
#define ODP_PACKET_DPDK_H

#include <stdint.h>

/* #include <net/if.h> */

#include <odp/helper/eth.h>

/* #include <odp/helper/packet.h> */
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
#include <odp_memzone.h>

/* #include <odp_launch.h> */
#include <odp_tailq.h>
#include <odp_base.h>

/* #include <odp_per_lcore.h> */
#include <odp_lcore.h>

/* #include <odp_branch_prediction.h>
 #include <odp_prefetch.h> */
#include <odp_cycles.h>

/* #include <odp_err.h>
   #include <odp_debug.h>
 #include <odp_log.h> */
//#include <odp_byteorder.h>
#include <odp_pci.h>

/* #include <odp_random.h> */
#include <odp_ether.h>
#include <odp_ethdev.h>

/* #include <odp_hash.h>
   #include <odp_jhash.h>
 #include <odp_hash_crc.h> */

#define ODP_DPDK_MODE_HW 0
#define ODP_DPDK_MODE_SW 1

#define DPDK_BLOCKING_IO

/*
 * RX and TX Prefetch, Host, and Write-back threshold values should be
 * carefully set for optimal performance. Consult the network
 * controller's datasheet and supporting DPDK documentation for guidance
 * on how these parameters should be set.
 */
#define RX_PTHRESH 8                        /**< Default values of RX prefetch threshold reg. */
#define RX_HTHRESH 8                        /**< Default values of RX host threshold reg. */
#define RX_WTHRESH 4                        /**< Default values of RX write-back threshold reg. */

/*
 * These default values are optimized for use with the Huawei(R) 82599 10 GbE
 * Controller and the DPDK ixgbe PMD. Consider using other values for other
 * network controllers and/or network drivers.
 */
#define TX_PTHRESH 36                       /**< Default values of TX prefetch threshold reg. */
#define TX_HTHRESH 0                        /**< Default values of TX host threshold reg. */
#define TX_WTHRESH 0                        /**< Default values of TX write-back threshold reg. */

#define MAX_PKT_BURST		  16
#define BURST_TX_DRAIN_US	  100       /* TX drain every ~100us */
#define ODP_TEST_RX_DESC_DEFAULT 128
#define ODP_TEST_TX_DESC_DEFAULT 512

/** Packet socket using dpdk mmaped rings for both Rx and Tx */
typedef struct {
	odp_pool_t pool;

	/********************************/
	char	 ifname[32];
	uint8_t	 portid;
	uint16_t queueid;
} pkt_dpdk_t;

/**
 * Configure an interface to work in dpdk mode
 */
int setup_pkt_dpdk(pkt_dpdk_t *const pkt_dpdk, const char *netdev,
		   odp_pool_t pool);

/**
 * Switch interface from dpdk mode to normal mode
 */
int close_pkt_dpdk(pkt_dpdk_t *const pkt_dpdk);

/**
 * Receive packets using dpdk
 */
int recv_pkt_dpdk(pkt_dpdk_t *const pkt_dpdk, odp_packet_t pkt_table[],
		  unsigned len);

/**
 * Send packets using dpdk
 */
int send_pkt_dpdk(pkt_dpdk_t *const pkt_dpdk, odp_packet_t pkt_table[],
		  unsigned len);

/* int odp_init(void); */
#endif
