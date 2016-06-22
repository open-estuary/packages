/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_ETHDEV_H_
#define _ODP_ETHDEV_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <stdint.h>
#include <odp/hints.h>
#include <odp/align.h>
#include "odp_config.h"
#include <odp_devargs.h>
#include "odp_ether.h"
#include "odp_eth_ctrl.h"

struct odp_eth_stats {
	uint64_t ipackets;
	uint64_t opackets;
	uint64_t ibytes;
	uint64_t obytes;
	uint64_t imissed;
	uint64_t ibadcrc;
	uint64_t ibadlen;
	uint64_t ierrors;
	uint64_t oerrors;
	uint64_t imcasts;
	uint64_t rx_nombuf;
	uint64_t fdirmatch;

	uint64_t fdirmiss;
	uint64_t tx_pause_xon;
	uint64_t rx_pause_xon;
	uint64_t tx_pause_xoff;
	uint64_t rx_pause_xoff;
	uint64_t q_ipackets[ODP_ETHDEV_QUEUE_STAT_CNTRS];
	uint64_t q_opackets[ODP_ETHDEV_QUEUE_STAT_CNTRS];
	uint64_t q_ibytes[ODP_ETHDEV_QUEUE_STAT_CNTRS];
	uint64_t q_obytes[ODP_ETHDEV_QUEUE_STAT_CNTRS];
	uint64_t q_errors[ODP_ETHDEV_QUEUE_STAT_CNTRS];
	uint64_t ilbpackets;
	uint64_t olbpackets;
	uint64_t ilbbytes;
	uint64_t olbbytes;
};

struct odp_eth_link {
	uint16_t link_speed;
	uint16_t link_duplex;
	uint8_t	 link_status : 1;
} ODP_ALIGNED(8);

#define ETH_LINK_SPEED_AUTONEG 0
#define ETH_LINK_SPEED_10      10
#define ETH_LINK_SPEED_100     100
#define ETH_LINK_SPEED_1000    1000
#define ETH_LINK_SPEED_10000   10000
#define ETH_LINK_SPEED_10G     10000
#define ETH_LINK_SPEED_20G     20000
#define ETH_LINK_SPEED_40G     40000

#define ETH_LINK_AUTONEG_DUPLEX 0
#define ETH_LINK_HALF_DUPLEX	1
#define ETH_LINK_FULL_DUPLEX	2

struct odp_eth_thresh {
	uint8_t pthresh;
	uint8_t hthresh;
	uint8_t wthresh;
};


#define ETH_MQ_RX_RSS_FLAG  0x1
#define ETH_MQ_RX_DCB_FLAG  0x2
#define ETH_MQ_RX_VMDQ_FLAG 0x4

enum odp_eth_rx_mq_mode {
	ETH_MQ_RX_NONE = 0,

	ETH_MQ_RX_RSS = ETH_MQ_RX_RSS_FLAG,

	ETH_MQ_RX_DCB = ETH_MQ_RX_DCB_FLAG,

	ETH_MQ_RX_DCB_RSS = ETH_MQ_RX_RSS_FLAG | ETH_MQ_RX_DCB_FLAG,

	ETH_MQ_RX_VMDQ_ONLY = ETH_MQ_RX_VMDQ_FLAG,

	ETH_MQ_RX_VMDQ_RSS = ETH_MQ_RX_RSS_FLAG | ETH_MQ_RX_VMDQ_FLAG,

	ETH_MQ_RX_VMDQ_DCB = ETH_MQ_RX_VMDQ_FLAG | ETH_MQ_RX_DCB_FLAG,

	ETH_MQ_RX_VMDQ_DCB_RSS = ETH_MQ_RX_RSS_FLAG | ETH_MQ_RX_DCB_FLAG |
				 ETH_MQ_RX_VMDQ_FLAG,
};

#define ETH_RSS	   ETH_MQ_RX_RSS
#define VMDQ_DCB   ETH_MQ_RX_VMDQ_DCB
#define ETH_DCB_RX ETH_MQ_RX_DCB

enum odp_eth_tx_mq_mode {
	ETH_MQ_TX_NONE = 0,
	ETH_MQ_TX_DCB,
	ETH_MQ_TX_VMDQ_DCB,
	ETH_MQ_TX_VMDQ_ONLY,
};

#define ETH_DCB_NONE	ETH_MQ_TX_NONE
#define ETH_VMDQ_DCB_TX ETH_MQ_TX_VMDQ_DCB
#define ETH_DCB_TX	ETH_MQ_TX_DCB

struct odp_eth_rxmode {
	enum odp_eth_rx_mq_mode mq_mode;
	uint32_t		max_rx_pkt_len;
	uint16_t		split_hdr_size;
	uint16_t		header_split   : 1,
				hw_ip_checksum : 1,
				hw_vlan_filter : 1,
				hw_vlan_strip  : 1,
				hw_vlan_extend : 1,
				jumbo_frame    : 1,
				hw_strip_crc   : 1,
				enable_scatter : 1,
				enable_lro     : 1,
				rev            : 7;
};

struct odp_eth_rss_conf {
	uint8_t *rss_key;
	uint8_t	 rss_key_len;
	uint64_t rss_hf;
};

#ifndef BIT_ULL
#define BIT_ULL(nr) (1ULL << (nr))
#endif
#define ETH_RSS_IPV4		   BIT_ULL(ODP_ETH_FLOW_IPV4)
#define ETH_RSS_FRAG_IPV4	   BIT_ULL(ODP_ETH_FLOW_FRAG_IPV4)
#define ETH_RSS_NONFRAG_IPV4_TCP   BIT_ULL(ODP_ETH_FLOW_NONFRAG_IPV4_TCP)
#define ETH_RSS_NONFRAG_IPV4_UDP   BIT_ULL(ODP_ETH_FLOW_NONFRAG_IPV4_UDP)
#define ETH_RSS_NONFRAG_IPV4_SCTP  BIT_ULL(ODP_ETH_FLOW_NONFRAG_IPV4_SCTP)
#define ETH_RSS_NONFRAG_IPV4_OTHER BIT_ULL(ODP_ETH_FLOW_NONFRAG_IPV4_OTHER)
#define ETH_RSS_IPV6		   BIT_ULL(ODP_ETH_FLOW_IPV6)
#define ETH_RSS_FRAG_IPV6	   BIT_ULL(ODP_ETH_FLOW_FRAG_IPV6)
#define ETH_RSS_NONFRAG_IPV6_TCP   BIT_ULL(ODP_ETH_FLOW_NONFRAG_IPV6_TCP)
#define ETH_RSS_NONFRAG_IPV6_UDP   BIT_ULL(ODP_ETH_FLOW_NONFRAG_IPV6_UDP)
#define ETH_RSS_NONFRAG_IPV6_SCTP  BIT_ULL(ODP_ETH_FLOW_NONFRAG_IPV6_SCTP)
#define ETH_RSS_NONFRAG_IPV6_OTHER BIT_ULL(ODP_ETH_FLOW_NONFRAG_IPV6_OTHER)
#define ETH_RSS_L2_PAYLOAD	   BIT_ULL(ODP_ETH_FLOW_L2_PAYLOAD)
#define ETH_RSS_IPV6_EX		   BIT_ULL(ODP_ETH_FLOW_IPV6_EX)
#define ETH_RSS_IPV6_TCP_EX	   BIT_ULL(ODP_ETH_FLOW_IPV6_TCP_EX)
#define ETH_RSS_IPV6_UDP_EX	   BIT_ULL(ODP_ETH_FLOW_IPV6_UDP_EX)

#define ETH_RSS_IP ( \
		ETH_RSS_IPV4 | \
		ETH_RSS_FRAG_IPV4 | \
		ETH_RSS_NONFRAG_IPV4_OTHER | \
		ETH_RSS_IPV6 | \
		ETH_RSS_FRAG_IPV6 | \
		ETH_RSS_NONFRAG_IPV6_OTHER | \
		ETH_RSS_IPV6_EX)

#define ETH_RSS_UDP ( \
		ETH_RSS_NONFRAG_IPV4_UDP | \
		ETH_RSS_NONFRAG_IPV6_UDP | \
		ETH_RSS_IPV6_UDP_EX)

#define ETH_RSS_TCP ( \
		ETH_RSS_NONFRAG_IPV4_TCP | \
		ETH_RSS_NONFRAG_IPV6_TCP | \
		ETH_RSS_IPV6_TCP_EX)

#define ETH_RSS_SCTP ( \
		ETH_RSS_NONFRAG_IPV4_SCTP | \
		ETH_RSS_NONFRAG_IPV6_SCTP)

#define ETH_RSS_PROTO_MASK ( \
		ETH_RSS_IPV4 | \
		ETH_RSS_FRAG_IPV4 | \
		ETH_RSS_NONFRAG_IPV4_TCP | \
		ETH_RSS_NONFRAG_IPV4_UDP | \
		ETH_RSS_NONFRAG_IPV4_SCTP | \
		ETH_RSS_NONFRAG_IPV4_OTHER | \
		ETH_RSS_IPV6 | \
		ETH_RSS_FRAG_IPV6 | \
		ETH_RSS_NONFRAG_IPV6_TCP | \
		ETH_RSS_NONFRAG_IPV6_UDP | \
		ETH_RSS_NONFRAG_IPV6_SCTP | \
		ETH_RSS_NONFRAG_IPV6_OTHER | \
		ETH_RSS_L2_PAYLOAD | \
		ETH_RSS_IPV6_EX | \
		ETH_RSS_IPV6_TCP_EX | \
		ETH_RSS_IPV6_UDP_EX)

#define ETH_RSS_RETA_SIZE_64  64
#define ETH_RSS_RETA_SIZE_128 128
#define ETH_RSS_RETA_SIZE_512 512
#define ODP_RETA_GROUP_SIZE   64

#define ETH_VMDQ_MAX_VLAN_FILTERS   64
#define ETH_DCB_NUM_USER_PRIORITIES 8
#define ETH_VMDQ_DCB_NUM_QUEUES	    128
#define ETH_DCB_NUM_QUEUES	    128

#define ETH_DCB_PG_SUPPORT  0x00000001
#define ETH_DCB_PFC_SUPPORT 0x00000002

#define ETH_VLAN_STRIP_OFFLOAD	0x0001
#define ETH_VLAN_FILTER_OFFLOAD 0x0002
#define ETH_VLAN_EXTEND_OFFLOAD 0x0004

#define ETH_VLAN_STRIP_MASK  0x0001
#define ETH_VLAN_FILTER_MASK 0x0002
#define ETH_VLAN_EXTEND_MASK 0x0004
#define ETH_VLAN_ID_MAX	     0x0FFF

#define ETH_NUM_RECEIVE_MAC_ADDR 128

#define ETH_VMDQ_NUM_UC_HASH_ARRAY 128

#define ETH_VMDQ_ACCEPT_UNTAG	  0x0001
#define ETH_VMDQ_ACCEPT_HASH_MC	  0x0002
#define ETH_VMDQ_ACCEPT_HASH_UC	  0x0004
#define ETH_VMDQ_ACCEPT_BROADCAST 0x0008
#define ETH_VMDQ_ACCEPT_MULTICAST 0x0010

#define ETH_VMDQ_NUM_MIRROR_RULE 4

#define ETH_VMDQ_POOL_MIRROR	0x0001
#define ETH_VMDQ_UPLINK_MIRROR	0x0002
#define ETH_VMDQ_DOWNLIN_MIRROR 0x0004
#define ETH_VMDQ_VLAN_MIRROR	0x0008

struct odp_eth_vlan_mirror {
	uint64_t vlan_mask;
	uint16_t vlan_id[ETH_VMDQ_MAX_VLAN_FILTERS];
};

struct odp_eth_vmdq_mirror_conf {
	uint8_t	 rule_type_mask;
	uint8_t	 dst_pool;
	uint64_t pool_mask;
	struct odp_eth_vlan_mirror vlan;
};

struct odp_eth_rss_reta_entry64 {
	uint64_t mask;
	uint8_t reta[ODP_RETA_GROUP_SIZE];
};

enum odp_eth_nb_tcs {
	ETH_4_TCS = 4,
	ETH_8_TCS = 8
};

enum odp_eth_nb_pools {
	ETH_8_POOLS  = 8,
	ETH_16_POOLS = 16,
	ETH_32_POOLS = 32,
	ETH_64_POOLS = 64
};

struct odp_eth_dcb_rx_conf {
	enum odp_eth_nb_tcs nb_tcs;
	uint8_t		    dcb_queue[ETH_DCB_NUM_USER_PRIORITIES];

};

struct odp_eth_vmdq_dcb_tx_conf {
	enum odp_eth_nb_pools nb_queue_pools;
	uint8_t		      dcb_queue[ETH_DCB_NUM_USER_PRIORITIES];

};

struct odp_eth_dcb_tx_conf {
	enum odp_eth_nb_tcs nb_tcs;
	uint8_t		    dcb_queue[ETH_DCB_NUM_USER_PRIORITIES];
};

struct odp_eth_vmdq_tx_conf {
	enum odp_eth_nb_pools nb_queue_pools;
};

struct odp_eth_vmdq_dcb_conf {
	enum odp_eth_nb_pools nb_queue_pools;
	uint8_t		      enable_default_pool;
	uint8_t		      default_pool;
	uint8_t		      nb_pool_maps;

	struct {
		uint16_t vlan_id;
		uint64_t pools;
	} pool_map[ETH_VMDQ_MAX_VLAN_FILTERS];
	uint8_t dcb_queue[ETH_DCB_NUM_USER_PRIORITIES];
};

struct odp_eth_vmdq_rx_conf {
	enum odp_eth_nb_pools nb_queue_pools;
	uint8_t		      enable_default_pool;
	uint8_t		      default_pool;
	uint8_t		      enable_loop_back;
	uint8_t		      nb_pool_maps;
	uint32_t	      rx_mode;

	struct {
		uint16_t vlan_id;
		uint64_t pools;
	} pool_map[ETH_VMDQ_MAX_VLAN_FILTERS];
};

struct odp_eth_txmode {
	enum odp_eth_tx_mq_mode mq_mode;

	uint16_t pvid;
	uint8_t	 hw_vlan_reject_tagged : 1,
		 hw_vlan_reject_untagged : 1,
		 hw_vlan_insert_pvid : 1;
};

struct odp_eth_rxconf {
	struct odp_eth_thresh rx_thresh;
	uint16_t	      rx_free_thresh;
	uint8_t		      rx_drop_en;
	uint8_t rx_deferred_start;
};

#define ETH_TXQ_FLAGS_NOMULTSEGS 0x0001
#define ETH_TXQ_FLAGS_NOREFCOUNT 0x0002
#define ETH_TXQ_FLAGS_NOMULTMEMP 0x0004
#define ETH_TXQ_FLAGS_NOVLANOFFL 0x0100
#define ETH_TXQ_FLAGS_NOXSUMSCTP 0x0200
#define ETH_TXQ_FLAGS_NOXSUMUDP	 0x0400
#define ETH_TXQ_FLAGS_NOXSUMTCP	 0x0800
#define ETH_TXQ_FLAGS_NOOFFLOADS \
	(ETH_TXQ_FLAGS_NOVLANOFFL | ETH_TXQ_FLAGS_NOXSUMSCTP | \
	 ETH_TXQ_FLAGS_NOXSUMUDP | ETH_TXQ_FLAGS_NOXSUMTCP)
#define ETH_TXQ_FLAGS_NOXSUMS \
	(ETH_TXQ_FLAGS_NOXSUMSCTP | ETH_TXQ_FLAGS_NOXSUMUDP | \
	 ETH_TXQ_FLAGS_NOXSUMTCP)

struct odp_eth_txconf {
	struct odp_eth_thresh tx_thresh;
	uint16_t	      tx_rs_thresh;
	uint16_t	      tx_free_thresh;
	uint32_t	      txq_flags;
	uint8_t tx_deferred_start;
};

enum odp_eth_fc_mode {
	ODP_FC_NONE = 0,
	ODP_FC_RX_PAUSE,
	ODP_FC_TX_PAUSE,
	ODP_FC_FULL
};

struct odp_eth_fc_conf {
	uint32_t	     high_water;
	uint32_t	     low_water;
	uint16_t	     pause_time;
	uint16_t	     send_xon;
	enum odp_eth_fc_mode mode;
	uint8_t		     mac_ctrl_frame_fwd;
	uint8_t		     autoneg;
};

struct odp_eth_pfc_conf {
	struct odp_eth_fc_conf fc;
	uint8_t		       priority;
};

enum odp_fdir_pballoc_type {
	ODP_FDIR_PBALLOC_64K = 0,
	ODP_FDIR_PBALLOC_128K,
	ODP_FDIR_PBALLOC_256K,
};

enum odp_fdir_status_mode {
	ODP_FDIR_NO_REPORT_STATUS = 0,

	ODP_FDIR_REPORT_STATUS,
	ODP_FDIR_REPORT_STATUS_ALWAYS,
};

struct odp_fdir_conf {
	enum odp_fdir_mode	   mode;
	enum odp_fdir_pballoc_type pballoc;
	enum odp_fdir_status_mode  status;
	uint8_t			      drop_queue;
	struct odp_eth_fdir_masks     mask;
	struct odp_eth_fdir_flex_conf flex_conf;
};

struct odp_eth_udp_tunnel {
	uint16_t udp_port;
	uint8_t	 prot_type;
};

enum odp_l4type {
	ODP_FDIR_L4TYPE_NONE = 0,
	ODP_FDIR_L4TYPE_UDP,
	ODP_FDIR_L4TYPE_TCP,
	ODP_FDIR_L4TYPE_SCTP,
};

enum odp_iptype {
	ODP_FDIR_IPTYPE_IPV4 = 0,
	ODP_FDIR_IPTYPE_IPV6,
};

struct odp_fdir_filter {
	uint16_t flex_bytes;
	uint16_t vlan_id;
	uint16_t port_src;
	uint16_t port_dst;

	union {
		uint32_t ipv4_addr;
		uint32_t ipv6_addr[4];
	} ip_src;

	union {
		uint32_t ipv4_addr;
		uint32_t ipv6_addr[4];
	} ip_dst;
	enum odp_l4type l4type;
	enum odp_iptype iptype;
};

struct odp_fdir_masks {
	uint8_t only_ip_flow;
	uint8_t vlan_id;
	uint8_t vlan_prio;
	uint8_t flexbytes;
	uint8_t set_ipv6_mask;
	uint8_t comp_ipv6_dst;
	uint32_t dst_ipv4_mask;
	uint32_t src_ipv4_mask;
	uint16_t dst_ipv6_mask;
	uint16_t src_ipv6_mask;
	uint16_t src_port_mask;
	uint16_t dst_port_mask;
};

struct odp_eth_fdir {
	uint16_t collision;
	uint16_t free;
	uint16_t maxhash;
	uint8_t maxlen;
	uint64_t add;
	uint64_t remove;
	uint64_t f_add;
	uint64_t f_remove;
};

struct odp_intr_conf {
	uint16_t lsc;
};

struct odp_eth_conf {
	uint16_t link_speed;
	uint16_t link_duplex;
	struct odp_eth_rxmode rxmode;
	struct odp_eth_txmode txmode;
	uint32_t lpbk_mode;

	struct {
		struct odp_eth_rss_conf	     rss_conf;
		struct odp_eth_vmdq_dcb_conf vmdq_dcb_conf;
		struct odp_eth_dcb_rx_conf dcb_rx_conf;
		struct odp_eth_vmdq_rx_conf vmdq_rx_conf;
	} rx_adv_conf;
	union {
		struct odp_eth_vmdq_dcb_tx_conf vmdq_dcb_tx_conf;
		struct odp_eth_dcb_tx_conf dcb_tx_conf;
		struct odp_eth_vmdq_tx_conf vmdq_tx_conf;
	} tx_adv_conf;

	uint32_t	     dcb_capability_en;
	struct odp_fdir_conf fdir_conf;
	struct odp_intr_conf intr_conf;
};

#define DEV_RX_OFFLOAD_VLAN_STRIP 0x00000001
#define DEV_RX_OFFLOAD_IPV4_CKSUM 0x00000002
#define DEV_RX_OFFLOAD_UDP_CKSUM  0x00000004
#define DEV_RX_OFFLOAD_TCP_CKSUM  0x00000008
#define DEV_RX_OFFLOAD_TCP_LRO	  0x00000010

#define DEV_TX_OFFLOAD_VLAN_INSERT 0x00000001
#define DEV_TX_OFFLOAD_IPV4_CKSUM  0x00000002
#define DEV_TX_OFFLOAD_UDP_CKSUM   0x00000004
#define DEV_TX_OFFLOAD_TCP_CKSUM   0x00000008
#define DEV_TX_OFFLOAD_SCTP_CKSUM  0x00000010
#define DEV_TX_OFFLOAD_TCP_TSO	   0x00000020
#define DEV_TX_OFFLOAD_UDP_TSO	   0x00000040

#define DEV_TX_OFFLOAD_OUTER_IPV4_CKSUM 0x00000080

struct odp_eth_dev_info {
	struct odp_pci_device *pci_dev;
	const char	      *driver_name;

	unsigned int if_index;

	uint32_t min_rx_bufsize;
	uint32_t max_rx_pktlen;
	uint16_t max_rx_queues;
	uint16_t max_tx_queues;
	uint32_t max_mac_addrs;
	uint32_t max_hash_mac_addrs;

	uint16_t max_vfs;
	uint16_t max_vmdq_pools;
	uint32_t rx_offload_capa;
	uint32_t tx_offload_capa;
	uint16_t reta_size;
	uint8_t hash_key_size;

	uint64_t	      flow_type_rss_offloads;
	struct odp_eth_rxconf default_rxconf;
	struct odp_eth_txconf default_txconf;
	uint16_t	      vmdq_queue_base;
	uint16_t	      vmdq_queue_num;
	uint16_t	      vmdq_pool_base;
};

#define ODP_ETH_XSTATS_NAME_SIZE 64

struct odp_eth_xstats {
	char	 name[ODP_ETH_XSTATS_NAME_SIZE];
	uint64_t value;
};

struct odp_eth_dev;

struct odp_eth_callback;

TAILQ_HEAD(odp_eth_dev_cb_list, odp_eth_callback);

typedef int (*eth_dev_configure_t)(struct odp_eth_dev *dev);

typedef int (*eth_dev_start_t)(struct odp_eth_dev *dev);

typedef void (*eth_dev_stop_t)(struct odp_eth_dev *dev);

typedef int (*eth_dev_set_link_up_t)(struct odp_eth_dev *dev);

typedef int (*eth_dev_set_link_down_t)(struct odp_eth_dev *dev);

typedef void (*eth_dev_close_t)(struct odp_eth_dev *dev);

typedef void (*eth_promiscuous_enable_t)(struct odp_eth_dev *dev);

typedef void (*eth_promiscuous_disable_t)(struct odp_eth_dev *dev);

typedef void (*eth_allmulticast_enable_t)(struct odp_eth_dev *dev);

typedef void (*eth_allmulticast_disable_t)(struct odp_eth_dev *dev);

typedef int (*eth_link_update_t)(struct odp_eth_dev *dev,
				 int		     wait_to_complete);

typedef void (*eth_stats_get_t)(struct odp_eth_dev   *dev,
				struct odp_eth_stats *igb_stats);

typedef void (*eth_stats_reset_t)(struct odp_eth_dev *dev);

typedef int (*eth_xstats_get_t)(struct odp_eth_dev *dev,
				struct odp_eth_xstats *stats, unsigned n);

typedef void (*eth_xstats_reset_t)(struct odp_eth_dev *dev);

typedef int (*eth_queue_stats_mapping_set_t)(struct odp_eth_dev *dev,
					     uint16_t		 queue_id,
					     uint8_t		 stat_idx,
					     uint8_t		 is_rx);

typedef void (*eth_dev_infos_get_t)(struct odp_eth_dev	    *dev,
				    struct odp_eth_dev_info *dev_info);

typedef int (*eth_queue_start_t)(struct odp_eth_dev *dev,
				 uint16_t	     queue_id);

typedef int (*eth_queue_stop_t)(struct odp_eth_dev *dev,
				uint16_t	    queue_id);

typedef int (*eth_rx_queue_setup_t)(struct odp_eth_dev		*dev,
				    uint16_t			 rx_queue_id,
				    uint16_t			 nb_rx_desc,
				    unsigned int		 socket_id,
				    const struct odp_eth_rxconf *rx_conf,
				    void			*mb_pool);

typedef int (*eth_tx_queue_setup_t)(struct odp_eth_dev		*dev,
				    uint16_t			 tx_queue_id,
				    uint16_t			 nb_tx_desc,
				    unsigned int		 socket_id,
				    const struct odp_eth_txconf *tx_conf,
				    void			*mb_pool);

typedef void (*eth_queue_release_t)(void *queue);

typedef uint32_t (*eth_rx_queue_count_t)(struct odp_eth_dev *dev,
					 uint16_t	     rx_queue_id);

typedef int (*eth_rx_descriptor_done_t)(void *rxq, uint16_t offset);

typedef int (*mtu_set_t)(struct odp_eth_dev *dev, uint16_t mtu);

typedef int (*vlan_filter_set_t)(struct odp_eth_dev *dev,
				 uint16_t	     vlan_id,
				 int		     on);

typedef void (*vlan_tpid_set_t)(struct odp_eth_dev *dev,
				uint16_t	    tpid);

typedef void (*vlan_offload_set_t)(struct odp_eth_dev *dev, int mask);

typedef int (*vlan_pvid_set_t)(struct odp_eth_dev *dev,
			       uint16_t		   vlan_id,
			       int		   on);

typedef void (*vlan_strip_queue_set_t)(struct odp_eth_dev *dev,
				       uint16_t		   rx_queue_id,
				       int		   on);

typedef uint16_t (*eth_rx_burst_t)(void	   *rxq,
				   void	  **rx_pkts,
				   uint16_t nb_pkts);

typedef uint16_t (*eth_tx_burst_t)(void	   *txq,
				   void	  **tx_pkts,
				   uint16_t nb_pkts);

typedef int (*fdir_add_signature_filter_t)(struct odp_eth_dev	  *dev,
					   struct odp_fdir_filter *fdir_ftr,
					   uint8_t		   rx_queue);

typedef int (*fdir_update_signature_filter_t)(struct odp_eth_dev     *dev,
					      struct odp_fdir_filter *fdir_ftr,
					      uint8_t		      rx_queue);

typedef int (*fdir_remove_signature_filter_t)(struct odp_eth_dev     *dev,
					      struct odp_fdir_filter *fdir_ftr);

typedef void (*fdir_infos_get_t)(struct odp_eth_dev  *dev,
				 struct odp_eth_fdir *fdir);

typedef int (*fdir_add_perfect_filter_t)(struct odp_eth_dev *dev,
					 struct odp_fdir_filter *fdir_ftr,
					 uint16_t soft_id, uint8_t rx_queue,
					 uint8_t drop);

typedef int (*fdir_update_perfect_filter_t)(struct odp_eth_dev *dev,
					    struct odp_fdir_filter *fdir_ftr,
					    uint16_t soft_id, uint8_t rx_queue,
					    uint8_t drop);

typedef int (*fdir_remove_perfect_filter_t)(struct odp_eth_dev	   *dev,
					    struct odp_fdir_filter *fdir_ftr,
					    uint16_t		    soft_id);

typedef int (*fdir_set_masks_t)(struct odp_eth_dev    *dev,
				struct odp_fdir_masks *fdir_masks);

typedef int (*flow_ctrl_get_t)(struct odp_eth_dev     *dev,
			       struct odp_eth_fc_conf *fc_conf);

typedef int (*flow_ctrl_set_t)(struct odp_eth_dev     *dev,
			       struct odp_eth_fc_conf *fc_conf);

typedef int (*priority_flow_ctrl_set_t)(struct odp_eth_dev	*dev,
					struct odp_eth_pfc_conf *pfc_conf);

typedef int (*reta_update_t)(struct odp_eth_dev		     *dev,
			     struct odp_eth_rss_reta_entry64 *reta_conf,
			     uint16_t			      reta_size);

typedef int (*reta_query_t)(struct odp_eth_dev		    *dev,
			    struct odp_eth_rss_reta_entry64 *reta_conf,
			    uint16_t			     reta_size);

typedef int (*rss_hash_update_t)(struct odp_eth_dev	 *dev,
				 struct odp_eth_rss_conf *rss_conf);

typedef int (*rss_hash_conf_get_t)(struct odp_eth_dev	   *dev,
				   struct odp_eth_rss_conf *rss_conf);

typedef int (*eth_dev_led_on_t)(struct odp_eth_dev *dev);

typedef int (*eth_dev_led_off_t)(struct odp_eth_dev *dev);

typedef void (*eth_mac_addr_remove_t)(struct odp_eth_dev *dev, uint32_t index);

typedef void (*eth_mac_addr_add_t)(struct odp_eth_dev	 *dev,
				   struct odp_ether_addr *mac_addr,
				   uint32_t		  index,
				   uint32_t		  vmdq);

typedef void (*eth_mac_addr_set_t)(struct odp_eth_dev	 *dev,
				   struct odp_ether_addr *mac_addr);

typedef int (*eth_uc_hash_table_set_t)(struct odp_eth_dev    *dev,
				       struct odp_ether_addr *mac_addr,
				       uint8_t		      on);

typedef int (*eth_uc_all_hash_table_set_t)(struct odp_eth_dev *dev,
					   uint8_t	       on);

typedef int (*eth_set_vf_rx_mode_t)(struct odp_eth_dev *dev,
				    uint16_t		vf,
				    uint16_t		rx_mode,
				    uint8_t		on);

typedef int (*eth_set_vf_rx_t)(struct odp_eth_dev *dev,
			       uint16_t		   vf,
			       uint8_t		   on);

typedef int (*eth_set_vf_tx_t)(struct odp_eth_dev *dev,
			       uint16_t		   vf,
			       uint8_t		   on);

typedef int (*eth_set_vf_vlan_filter_t)(struct odp_eth_dev *dev,
					uint16_t	    vlan,
					uint64_t	    vf_mask,
					uint8_t		    vlan_on);

typedef int (*eth_set_queue_rate_limit_t)(struct odp_eth_dev *dev,
					  uint16_t	      queue_idx,
					  uint16_t	      tx_rate);

typedef int (*eth_set_vf_rate_limit_t)(struct odp_eth_dev *dev,
				       uint16_t		   vf,
				       uint16_t		   tx_rate,
				       uint64_t		   q_msk);

typedef int (*eth_mirror_rule_set_t)(struct odp_eth_dev *dev,
				     struct odp_eth_vmdq_mirror_conf *
				     mirror_conf,
				     uint8_t		 rule_id,
				     uint8_t		 on);

typedef int (*eth_mirror_rule_reset_t)(struct odp_eth_dev *dev,
				       uint8_t		   rule_id);

typedef int (*eth_udp_tunnel_add_t)(struct odp_eth_dev	      *dev,
				    struct odp_eth_udp_tunnel *tunnel_udp);

typedef int (*eth_udp_tunnel_del_t)(struct odp_eth_dev	      *dev,
				    struct odp_eth_udp_tunnel *tunnel_udp);

typedef int (*eth_set_mc_addr_list_t)(struct odp_eth_dev    *dev,
				      struct odp_ether_addr *mc_addr_set,
				      uint32_t		     nb_mc_addr);

typedef int (*eth_timesync_enable_t)(struct odp_eth_dev *dev);

typedef int (*eth_timesync_disable_t)(struct odp_eth_dev *dev);

typedef int (*eth_timesync_read_rx_timestamp_t)(struct odp_eth_dev *dev,
						struct timespec	   *timestamp,
						uint32_t	    flags);

typedef int (*eth_timesync_read_tx_timestamp_t)(struct odp_eth_dev *dev,
						struct timespec	   *timestamp);

typedef int (*eth_get_reg_length_t)(struct odp_eth_dev *dev);

typedef int (*eth_get_reg_t)(struct odp_eth_dev	     *dev,
			     struct odp_dev_reg_info *info);

typedef int (*eth_get_eeprom_length_t)(struct odp_eth_dev *dev);

typedef int (*eth_get_eeprom_t)(struct odp_eth_dev	   *dev,
				struct odp_dev_eeprom_info *info);

typedef int (*eth_set_eeprom_t)(struct odp_eth_dev	   *dev,
				struct odp_dev_eeprom_info *info);

typedef int (*eth_filter_ctrl_t)(struct odp_eth_dev  *dev,
				 enum odp_filter_type filter_type,
				 enum odp_filter_op   filter_op,
				 void		     *arg);

struct eth_dev_ops {
	eth_dev_configure_t	  dev_configure;
	eth_dev_start_t		  dev_start;
	eth_dev_stop_t		  dev_stop;
	eth_dev_set_link_up_t	  dev_set_link_up;
	eth_dev_set_link_down_t	  dev_set_link_down;
	eth_dev_close_t		  dev_close;
	eth_promiscuous_enable_t  promiscuous_enable;
	eth_promiscuous_disable_t promiscuous_disable;
	eth_allmulticast_enable_t allmulticast_enable;

	eth_allmulticast_disable_t allmulticast_disable;
	eth_link_update_t	   link_update;
	eth_stats_get_t		   stats_get;
	eth_stats_reset_t	   stats_reset;
	eth_xstats_get_t	   xstats_get;

	eth_xstats_reset_t	      xstats_reset;
	eth_queue_stats_mapping_set_t queue_stats_mapping_set;

	eth_dev_infos_get_t dev_infos_get;
	mtu_set_t	    mtu_set;
	vlan_filter_set_t   vlan_filter_set;
	vlan_tpid_set_t	    vlan_tpid_set;

	vlan_strip_queue_set_t	 vlan_strip_queue_set;
	vlan_offload_set_t	 vlan_offload_set;
	vlan_pvid_set_t		 vlan_pvid_set;
	eth_queue_start_t	 rx_queue_start;
	eth_queue_stop_t	 rx_queue_stop;
	eth_queue_start_t	 tx_queue_start;
	eth_queue_stop_t	 tx_queue_stop;
	eth_rx_queue_setup_t	 rx_queue_setup;
	eth_queue_release_t	 rx_queue_release;
	eth_rx_queue_count_t	 rx_queue_count;
	eth_rx_descriptor_done_t rx_descriptor_done;
	eth_tx_queue_setup_t	 tx_queue_setup;
	eth_queue_release_t	 tx_queue_release;
	eth_dev_led_on_t	 dev_led_on;
	eth_dev_led_off_t	 dev_led_off;
	flow_ctrl_get_t		 flow_ctrl_get;
	flow_ctrl_set_t		 flow_ctrl_set;

	priority_flow_ctrl_set_t priority_flow_ctrl_set;
	eth_mac_addr_remove_t	 mac_addr_remove;
	eth_mac_addr_add_t	 mac_addr_add;
	eth_mac_addr_set_t	 mac_addr_set;

	eth_uc_hash_table_set_t uc_hash_table_set;

	eth_uc_all_hash_table_set_t uc_all_hash_table_set;
	eth_mirror_rule_set_t	    mirror_rule_set;

	eth_mirror_rule_reset_t	 mirror_rule_reset;
	eth_set_vf_rx_mode_t	 set_vf_rx_mode;
	eth_set_vf_rx_t		 set_vf_rx;
	eth_set_vf_tx_t		 set_vf_tx;
	eth_set_vf_vlan_filter_t set_vf_vlan_filter;
	eth_udp_tunnel_add_t	 udp_tunnel_add;
	eth_udp_tunnel_del_t	 udp_tunnel_del;

	eth_set_queue_rate_limit_t set_queue_rate_limit;
	eth_set_vf_rate_limit_t	   set_vf_rate_limit;
	fdir_add_signature_filter_t fdir_add_signature_filter;

	fdir_update_signature_filter_t fdir_update_signature_filter;

	fdir_remove_signature_filter_t fdir_remove_signature_filter;

	fdir_infos_get_t fdir_infos_get;

	fdir_add_perfect_filter_t fdir_add_perfect_filter;

	fdir_update_perfect_filter_t fdir_update_perfect_filter;

	fdir_remove_perfect_filter_t fdir_remove_perfect_filter;

	fdir_set_masks_t fdir_set_masks;

	reta_update_t reta_update;

	reta_query_t reta_query;

	eth_get_reg_length_t get_reg_length;

	eth_get_reg_t get_reg;

	eth_get_eeprom_length_t get_eeprom_length;

	eth_get_eeprom_t get_eeprom;

	eth_set_eeprom_t set_eeprom;

	rss_hash_update_t rss_hash_update;

	rss_hash_conf_get_t    rss_hash_conf_get;
	eth_filter_ctrl_t      filter_ctrl;
	eth_set_mc_addr_list_t set_mc_addr_list;

	eth_timesync_enable_t timesync_enable;

	eth_timesync_disable_t timesync_disable;

	eth_timesync_read_rx_timestamp_t timesync_read_rx_timestamp;

	eth_timesync_read_tx_timestamp_t timesync_read_tx_timestamp;
};

typedef uint16_t (*odp_rx_callback_fn)(uint8_t port, uint16_t queue,
				       void *pkts[], uint16_t nb_pkts,
				       uint16_t max_pkts,
				       void *user_param);

typedef uint16_t (*odp_tx_callback_fn)(uint8_t port, uint16_t queue,
				       void *pkts[], uint16_t nb_pkts,
				       void *user_param);

struct odp_eth_rxtx_callback {
	struct odp_eth_rxtx_callback *next;

	union {
		odp_rx_callback_fn rx;
		odp_tx_callback_fn tx;
	} fn;

	void *param;
};

enum odp_eth_dev_type {
	ODP_ETH_DEV_UNKNOWN,
	ODP_ETH_DEV_PCI,

	ODP_ETH_DEV_VIRTUAL,
	ODP_ETH_DEV_SOC,
	ODP_ETH_DEV_MAX
};

enum odp_eth_nic_type {
	ODP_ETH_NIC_UNKNOWN,
	ODP_ETH_NIC_SOC,
	ODP_ETH_NIC_IXGBE,
	ODP_ETH_NIC_MLX,
	ODP_ETH_NIC_VIRTIO_MMIO,
	ODP_ETH_NIC_VIRTIO_PCI,
	ODP_ETH_NIC_MAX      /**< max value of this enum */
};

struct odp_eth_dev {
	eth_rx_burst_t		 rx_pkt_burst;
	eth_tx_burst_t		 tx_pkt_burst;
	struct odp_eth_dev_data *data;
	const struct eth_driver *driver;
	struct eth_dev_ops	*dev_ops;
	struct odp_pci_device	*pci_dev;
	struct odp_eth_dev_cb_list link_intr_cbs;

	struct odp_eth_rxtx_callback
	*post_rx_burst_cbs[ODP_MAX_QUEUES_PER_PORT];

	struct odp_eth_rxtx_callback *pre_tx_burst_cbs[ODP_MAX_QUEUES_PER_PORT];
	uint8_t			      attached;
	enum odp_eth_dev_type	      dev_type;
	uint16_t                      q_num;
};

struct odp_eth_dev_sriov {
	uint8_t	 active;
	uint8_t	 nb_q_per_pool;
	uint16_t def_vmdq_idx;
	uint16_t def_pool_q_idx;
};

#define ODP_ETH_DEV_SRIOV(dev) ((dev)->data->sriov)

#define ODP_ETH_NAME_MAX_LEN (32)

struct odp_eth_dev_data {
	char name[ODP_ETH_NAME_MAX_LEN];

	void   **rx_queues;
	void   **tx_queues;
	uint16_t nb_rx_queues;
	uint16_t nb_tx_queues;
	struct odp_eth_dev_sriov sriov;
	void *dev_private;
	struct odp_eth_link dev_link;
	struct odp_eth_conf dev_conf;
	uint16_t	    mtu;
	uint32_t min_rx_buf_size;

	uint64_t	       rx_mbuf_alloc_failed;
	struct odp_ether_addr *mac_addrs;
	uint64_t	       mac_pool_sel[ETH_NUM_RECEIVE_MAC_ADDR];
	struct odp_ether_addr *hash_mac_addrs;

	uint8_t port_id;
	uint8_t promiscuous   : 1,
		scattered_rx  : 1,
		all_multicast : 1,
		dev_started   : 1,
		lro	      : 1;
};

extern struct odp_eth_dev odp_eth_devices[];

uint8_t odp_eth_count(void);

struct odp_eth_dev *odp_eth_allocated(const char *name);
struct odp_eth_dev *odp_eth_allocated_id(int portid);

struct odp_eth_dev *odp_eth_dev_allocate(const char	      *name,
					 enum odp_eth_nic_type type);

int odp_eth_release_port(struct odp_eth_dev *eth_dev);

int odp_eth_dev_attach(const char *devargs, uint8_t *port_id);

int odp_eth_dev_detach(uint8_t port_id, char *devname);

struct eth_driver;

typedef int (*eth_dev_init_t)(struct odp_eth_dev *eth_dev);

typedef int (*eth_dev_uninit_t)(struct odp_eth_dev *eth_dev);

struct eth_driver {
	struct odp_pci_driver pci_drv;
	eth_dev_init_t	      eth_dev_init;
	eth_dev_uninit_t      eth_dev_uninit;
	unsigned int	      dev_private_size;
};

void odp_eth_driver_register(struct eth_driver *eth_drv);

int odp_eth_configure(uint8_t		     port_id,
			  uint16_t		     nb_rx_queue,
			  uint16_t		     nb_tx_queue,
			  const struct odp_eth_conf *eth_conf);

int odp_eth_rx_queue_setup(uint8_t port_id, uint16_t rx_queue_id,
			   uint16_t nb_rx_desc, unsigned int socket_id,
			   const struct odp_eth_rxconf *rx_conf,
			   void *mb_pool);

int odp_eth_tx_queue_setup(uint8_t port_id, uint16_t tx_queue_id,
			   uint16_t nb_tx_desc, unsigned int socket_id,
			   const struct odp_eth_txconf *tx_conf,
			   void *mb_pool);

int odp_eth_socket_id(uint8_t port_id);

int odp_eth_is_valid_port(uint8_t port_id);

int odp_eth_start(uint8_t port_id);

void odp_eth_stop(uint8_t port_id);

void odp_eth_promiscuous_enable(uint8_t port_id);

void odp_eth_promiscuous_disable(uint8_t port_id);

int odp_eth_promiscuous_get(uint8_t port_id);

void odp_eth_allmulticast_enable(uint8_t port_id);

void odp_eth_allmulticast_disable(uint8_t port_id);

int odp_eth_allmulticast_get(uint8_t port_id);

void odp_eth_link_get(uint8_t port_id, struct odp_eth_link *link);

void odp_eth_link_get_nowait(uint8_t		  port_id,
			     struct odp_eth_link *link);

void odp_eth_get_macaddr(uint8_t port_id, struct odp_ether_addr *mac_addr);

void odp_eth_info_get(uint8_t port_id, struct odp_eth_dev_info *dev_info);

int odp_eth_get_mtu(uint8_t port_id, uint16_t *mtu);

static inline uint32_t odp_eth_rx_queue_count(uint8_t  port_id,
					      uint16_t queue_id)
{
	struct odp_eth_dev *dev;

	dev = &odp_eth_devices[port_id];
	return (*dev->dev_ops->rx_queue_count)(dev, queue_id);
}

static inline int odp_eth_rx_descriptor_done(uint8_t port_id,
					     uint16_t queue_id, uint16_t offset)
{
	struct odp_eth_dev *dev;

	dev = &odp_eth_devices[port_id];
	return (*dev->dev_ops->rx_descriptor_done)(
		dev->data->rx_queues[queue_id], offset);
}

enum odp_eth_event_type {
	ODP_ETH_EVENT_UNKNOWN,
	ODP_ETH_EVENT_INTR_LSC,
	ODP_ETH_EVENT_MAX
};

typedef void (*odp_eth_cb_fn)(uint8_t port_id,
				  enum odp_eth_event_type event, void *cb_arg);

void odp_eth_callback_process(struct odp_eth_dev	  *dev,
				   enum odp_eth_event_type event);

int odp_eth_dev_bypass_state_show(uint8_t port, uint32_t *state);

int odp_eth_dev_bypass_state_set(uint8_t port, uint32_t *new_state);

int odp_eth_dev_bypass_event_show(uint8_t port,
				  uint32_t event, uint32_t *state);

int odp_eth_dev_bypass_event_store(uint8_t port,
				   uint32_t event, uint32_t state);

int odp_eth_dev_wd_timeout_store(uint8_t port, uint32_t timeout);

int odp_eth_dev_bypass_ver_show(uint8_t port, uint32_t *ver);

int odp_eth_dev_bypass_wd_timeout_show(uint8_t port, uint32_t *wd_timeout);

int odp_eth_dev_bypass_wd_reset(uint8_t port);

int odp_eth_dev_filter_ctrl(uint8_t port_id, enum odp_filter_type filter_type,
			    enum odp_filter_op filter_op, void *arg);

extern struct odp_eth_dev odp_eth_devices[ODP_MAX_ETHPORTS];

#ifdef __cplusplus
}
#endif
#endif /* _ODP_ETHDEV_H_ */
