/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_ETH_CTRL_H_
#define _ODP_ETH_CTRL_H_

#ifdef __cplusplus
extern "C" {
#endif
#include "odp_base.h"

#define ODP_ETH_FLOW_UNKNOWN		0
#define ODP_ETH_FLOW_RAW		1
#define ODP_ETH_FLOW_IPV4		2
#define ODP_ETH_FLOW_FRAG_IPV4		3
#define ODP_ETH_FLOW_NONFRAG_IPV4_TCP	4
#define ODP_ETH_FLOW_NONFRAG_IPV4_UDP	5
#define ODP_ETH_FLOW_NONFRAG_IPV4_SCTP	6
#define ODP_ETH_FLOW_NONFRAG_IPV4_OTHER 7
#define ODP_ETH_FLOW_IPV6		8
#define ODP_ETH_FLOW_FRAG_IPV6		9
#define ODP_ETH_FLOW_NONFRAG_IPV6_TCP	10
#define ODP_ETH_FLOW_NONFRAG_IPV6_UDP	11
#define ODP_ETH_FLOW_NONFRAG_IPV6_SCTP	12
#define ODP_ETH_FLOW_NONFRAG_IPV6_OTHER 13
#define ODP_ETH_FLOW_L2_PAYLOAD		14
#define ODP_ETH_FLOW_IPV6_EX		15
#define ODP_ETH_FLOW_IPV6_TCP_EX	16
#define ODP_ETH_FLOW_IPV6_UDP_EX	17
#define ODP_ETH_FLOW_MAX		18

enum odp_filter_op {
	ODP_ETH_FILTER_NOP = 0,
	ODP_ETH_FILTER_ADD,
	ODP_ETH_FILTER_UPDATE,
	ODP_ETH_FILTER_DELETE,
	ODP_ETH_FILTER_FLUSH,
	ODP_ETH_FILTER_GET,
	ODP_ETH_FILTER_SET,
	ODP_ETH_FILTER_INFO,
	ODP_ETH_FILTER_STATS,
	ODP_ETH_FILTER_OP_MAX
};

enum odp_filter_type {
	ODP_ETH_FILTER_NONE = 0,
	ODP_ETH_FILTER_MACVLAN,
	ODP_ETH_FILTER_ETHERTYPE,
	ODP_ETH_FILTER_FLEXIBLE,
	ODP_ETH_FILTER_SYN,
	ODP_ETH_FILTER_NTUPLE,
	ODP_ETH_FILTER_TUNNEL,
	ODP_ETH_FILTER_FDIR,
	ODP_ETH_FILTER_HASH,
	ODP_ETH_FILTER_MAX
};

enum odp_mac_filter_type {
	ODP_MAC_PERFECT_MATCH = 1,
	ODP_MACVLAN_PERFECT_MATCH,
	ODP_MAC_HASH_MATCH,
	ODP_MACVLAN_HASH_MATCH,
};

struct odp_eth_mac_filter {
	uint8_t			 is_vf;
	uint16_t		 dst_id;
	enum odp_mac_filter_type filter_type;
	struct odp_ether_addr	 mac_addr;
};

#define ODP_ETHTYPE_FLAGS_MAC  0x0001
#define ODP_ETHTYPE_FLAGS_DROP 0x0002

struct odp_eth_ethertype_filter {
	struct odp_ether_addr mac_addr;
	uint16_t	      ether_type;
	uint16_t	      flags;
	uint16_t	      queue;
};

#define ODP_FLEX_FILTER_MAXLEN 128
#define ODP_FLEX_FILTER_MASK_SIZE  \
	(ODP_ALIGN(ODP_FLEX_FILTER_MAXLEN, CHAR_BIT) / CHAR_BIT)

struct odp_eth_syn_filter {
	uint8_t	 hig_pri;
	uint16_t queue;
};

struct odp_eth_flex_filter {
	uint16_t len;
	uint8_t	 bytes[ODP_FLEX_FILTER_MAXLEN];
	uint8_t	 mask[ODP_FLEX_FILTER_MASK_SIZE];
	uint8_t	 priority;
	uint16_t queue;
};

#define ODP_NTUPLE_FLAGS_DST_IP	0x0001
#define ODP_NTUPLE_FLAGS_SRC_IP	0x0002

#define ODP_NTUPLE_FLAGS_DST_PORT 0x0004

#define ODP_NTUPLE_FLAGS_SRC_PORT 0x0008

#define ODP_NTUPLE_FLAGS_PROTO 0x0010

#define ODP_NTUPLE_FLAGS_TCP_FLAG 0x0020

#define ODP_5TUPLE_FLAGS ( \
		ODP_NTUPLE_FLAGS_DST_IP | \
		ODP_NTUPLE_FLAGS_SRC_IP | \
		ODP_NTUPLE_FLAGS_DST_PORT | \
		ODP_NTUPLE_FLAGS_SRC_PORT | \
		ODP_NTUPLE_FLAGS_PROTO)

#define ODP_2TUPLE_FLAGS ( \
		ODP_NTUPLE_FLAGS_DST_PORT | \
		ODP_NTUPLE_FLAGS_PROTO)

#define TCP_URG_FLAG 0x20
#define TCP_ACK_FLAG 0x10
#define TCP_PSH_FLAG 0x08
#define TCP_RST_FLAG 0x04
#define TCP_SYN_FLAG 0x02
#define TCP_FIN_FLAG 0x01
#define TCP_FLAG_ALL 0x3F

struct odp_eth_ntuple_filter {
	uint16_t flags;
	uint32_t dst_ip;
	uint32_t dst_ip_mask;
	uint32_t src_ip;
	uint32_t src_ip_mask;
	uint16_t dst_port;
	uint16_t dst_port_mask;
	uint16_t src_port;
	uint16_t src_port_mask;
	uint8_t	 proto;
	uint8_t	 proto_mask;

	uint8_t tcp_flags;

	uint16_t priority;
	uint16_t queue;
};

enum odp_eth_tunnel_type {
	ODP_TUNNEL_TYPE_NONE = 0,
	ODP_TUNNEL_TYPE_VXLAN,
	ODP_TUNNEL_TYPE_GENEVE,
	ODP_TUNNEL_TYPE_TEREDO,
	ODP_TUNNEL_TYPE_NVGRE,
	ODP_TUNNEL_TYPE_MAX,
};

#define ETH_TUNNEL_FILTER_OMAC	0x01
#define ETH_TUNNEL_FILTER_OIP	0x02
#define ETH_TUNNEL_FILTER_TENID 0x04
#define ETH_TUNNEL_FILTER_IMAC	0x08
#define ETH_TUNNEL_FILTER_IVLAN 0x10
#define ETH_TUNNEL_FILTER_IIP	0x20

#define ODP_TUNNEL_FILTER_IMAC_IVLAN	   (ETH_TUNNEL_FILTER_IMAC | \
					    ETH_TUNNEL_FILTER_IVLAN)
#define ODP_TUNNEL_FILTER_IMAC_IVLAN_TENID (ETH_TUNNEL_FILTER_IMAC | \
					    ETH_TUNNEL_FILTER_IVLAN | \
					    ETH_TUNNEL_FILTER_TENID)
#define ODP_TUNNEL_FILTER_IMAC_TENID	   (ETH_TUNNEL_FILTER_IMAC | \
					    ETH_TUNNEL_FILTER_TENID)
#define ODP_TUNNEL_FILTER_OMAC_TENID_IMAC  (ETH_TUNNEL_FILTER_OMAC | \
					    ETH_TUNNEL_FILTER_TENID | \
					    ETH_TUNNEL_FILTER_IMAC)

enum odp_tunnel_iptype {
	ODP_TUNNEL_IPTYPE_IPV4 = 0,
	ODP_TUNNEL_IPTYPE_IPV6,
};

struct odp_eth_tunnel_filter_conf {
	struct odp_ether_addr *outer_mac;
	struct odp_ether_addr *inner_mac;
	uint16_t	       inner_vlan;
	enum odp_tunnel_iptype ip_type;

	union {
		uint32_t ipv4_addr;
		uint32_t ipv6_addr[4];
	} ip_addr;

	uint16_t		 filter_type;
	enum odp_eth_tunnel_type tunnel_type;
	uint32_t		 tenant_id;
	uint16_t		 queue_id;
};

#define ODP_ETH_FDIR_MAX_FLEXLEN 16

struct odp_eth_ipv4_flow {
	uint32_t src_ip;
	uint32_t dst_ip;
};

struct odp_eth_udpv4_flow {
	struct odp_eth_ipv4_flow ip;
	uint16_t		 src_port;
	uint16_t		 dst_port;
};

struct odp_eth_tcpv4_flow {
	struct odp_eth_ipv4_flow ip;
	uint16_t		 src_port;
	uint16_t		 dst_port;
};

struct odp_eth_sctpv4_flow {
	struct odp_eth_ipv4_flow ip;
	uint32_t		 verify_tag;
};

struct odp_eth_ipv6_flow {
	uint32_t src_ip[4];
	uint32_t dst_ip[4];
};

struct odp_eth_udpv6_flow {
	struct odp_eth_ipv6_flow ip;
	uint16_t		 src_port;
	uint16_t		 dst_port;
};

struct odp_eth_tcpv6_flow {
	struct odp_eth_ipv6_flow ip;
	uint16_t		 src_port;
	uint16_t		 dst_port;
};

struct odp_eth_sctpv6_flow {
	struct odp_eth_ipv6_flow ip;
	uint32_t		 verify_tag;
};

union odp_eth_fdir_flow {
	struct odp_eth_udpv4_flow  udp4_flow;
	struct odp_eth_tcpv4_flow  tcp4_flow;
	struct odp_eth_sctpv4_flow sctp4_flow;
	struct odp_eth_ipv4_flow   ip4_flow;
	struct odp_eth_udpv6_flow  udp6_flow;
	struct odp_eth_tcpv6_flow  tcp6_flow;
	struct odp_eth_sctpv6_flow sctp6_flow;
	struct odp_eth_ipv6_flow   ipv6_flow;
};

struct odp_eth_fdir_flow_ext {
	uint16_t vlan_tci;
	uint8_t	 flexbytes[ODP_ETH_FDIR_MAX_FLEXLEN];

};

struct odp_eth_fdir_input {
	uint16_t		flow_type;
	union odp_eth_fdir_flow flow;
	struct odp_eth_fdir_flow_ext flow_ext;
};

enum odp_eth_fdir_status {
	ODP_ETH_FDIR_NO_REPORT_STATUS = 0,
	ODP_ETH_FDIR_REPORT_ID,
	ODP_ETH_FDIR_REPORT_ID_FLEX_4,
	ODP_ETH_FDIR_REPORT_FLEX_8,
};

enum odp_eth_fdir_behavior {
	ODP_ETH_FDIR_ACCEPT = 0,
	ODP_ETH_FDIR_REJECT,
};

struct odp_eth_fdir_action {
	uint16_t		   rx_queue;
	enum odp_eth_fdir_behavior behavior;
	enum odp_eth_fdir_status   report_status;
	uint8_t			   flex_off;
};

struct odp_eth_fdir_filter {
	uint32_t soft_id;
	struct odp_eth_fdir_input  input;
	struct odp_eth_fdir_action action;
};

struct odp_eth_fdir_masks {
	uint16_t		 vlan_tci_mask;
	struct odp_eth_ipv4_flow ipv4_mask;
	struct odp_eth_ipv6_flow ipv6_mask;
	uint16_t		 src_port_mask;
	uint16_t		 dst_port_mask;
};

enum odp_eth_payload_type {
	ODP_ETH_PAYLOAD_UNKNOWN = 0,
	ODP_ETH_RAW_PAYLOAD,
	ODP_ETH_L2_PAYLOAD,
	ODP_ETH_L3_PAYLOAD,
	ODP_ETH_L4_PAYLOAD,
	ODP_ETH_PAYLOAD_MAX = 8,
};

struct odp_eth_flex_payload_cfg {
	enum odp_eth_payload_type type;
	uint16_t		  src_offset[ODP_ETH_FDIR_MAX_FLEXLEN];
};

struct odp_eth_fdir_flex_mask {
	uint16_t flow_type;
	uint8_t	 mask[ODP_ETH_FDIR_MAX_FLEXLEN];
};

struct odp_eth_fdir_flex_conf {
	uint16_t nb_payloads;
	uint16_t nb_flexmasks;

	struct odp_eth_flex_payload_cfg flex_set[ODP_ETH_PAYLOAD_MAX];
	struct odp_eth_fdir_flex_mask flex_mask[ODP_ETH_FLOW_MAX];
};

enum odp_fdir_mode {
	ODP_FDIR_MODE_NONE = 0,
	ODP_FDIR_MODE_SIGNATURE,
	ODP_FDIR_MODE_PERFECT,
};

#define UINT32_BIT (CHAR_BIT * sizeof(uint32_t))
#define ODP_FLOW_MASK_ARRAY_SIZE \
	(ODP_ALIGN(ODP_ETH_FLOW_MAX, UINT32_BIT) / UINT32_BIT)


struct odp_eth_fdir_stats {
	uint32_t collision;
	uint32_t free;
	uint32_t maxhash;
	uint32_t maxlen;
	uint64_t add;
	uint64_t remove;
	uint64_t f_add;
	uint64_t f_remove;
	uint32_t guarant_cnt;
	uint32_t best_cnt;
};

struct odp_eth_fdir_info {
	enum odp_fdir_mode	  mode;
	struct odp_eth_fdir_masks mask;

	struct odp_eth_fdir_flex_conf flex_conf;

	uint32_t guarant_spc;
	uint32_t best_spc;

	uint32_t flow_types_mask[ODP_FLOW_MASK_ARRAY_SIZE];
	uint32_t max_flexpayload;
	uint32_t flex_payload_unit;
	uint32_t max_flex_payload_segment_num;
	uint16_t flex_payload_limit;
	uint32_t flex_bitmask_unit;
	uint32_t max_flex_bitmask_num;
};

enum odp_eth_hash_function {
	ODP_ETH_HASH_FUNCTION_DEFAULT = 0,
	ODP_ETH_HASH_FUNCTION_TOEPLITZ,
	ODP_ETH_HASH_FUNCTION_SIMPLE_XOR,
	ODP_ETH_HASH_FUNCTION_MAX,
};

enum odp_eth_hash_filter_info_type {
	ODP_ETH_HASH_FILTER_INFO_TYPE_UNKNOWN = 0,

	ODP_ETH_HASH_FILTER_SYM_HASH_ENA_PER_PORT,

	ODP_ETH_HASH_FILTER_GLOBAL_CONFIG,
	ODP_ETH_HASH_FILTER_INFO_TYPE_MAX,
};

#define ODP_SYM_HASH_MASK_ARRAY_SIZE \
	(ODP_ALIGN(ODP_ETH_FLOW_MAX, UINT32_BIT) / UINT32_BIT)

struct odp_eth_hash_global_conf {
	enum odp_eth_hash_function hash_func;
	uint32_t sym_hash_enable_mask[ODP_SYM_HASH_MASK_ARRAY_SIZE];
	uint32_t valid_bit_mask[ODP_SYM_HASH_MASK_ARRAY_SIZE];
};

struct odp_eth_hash_filter_info {
	enum odp_eth_hash_filter_info_type info_type;
	union {
		uint8_t enable;

		struct odp_eth_hash_global_conf global_conf;
	} info;
};

#ifdef __cplusplus
}
#endif
#endif /* _ODP_ETH_CTRL_H_ */
