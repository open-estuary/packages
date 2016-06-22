/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_ETHER_H_
#define _ODP_ETHER_H_


#ifdef __cplusplus
extern "C" {
#endif

#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <odp/random.h>
#include <odp/hints.h>
#include <odp/byteorder.h>

#define ODP_ETHER_TYPE_LEN 2
#define ODP_ETHER_CRC_LEN  4
#define ODP_ETHER_ADDR_LEN 6
#define ODP_ETHER_HDR_LEN   \
	(ODP_ETHER_ADDR_LEN * 2 + \
	 ODP_ETHER_TYPE_LEN)
#define ODP_ETHER_MIN_LEN 64
#define ODP_ETHER_MAX_LEN 1518
#define ODP_ETHER_MTU       \
	(ODP_ETHER_MAX_LEN - \
	 ODP_ETHER_HDR_LEN - ODP_ETHER_CRC_LEN)

#define ODP_ETHER_MAX_VLAN_FRAME_LEN \
	(ODP_ETHER_MAX_LEN + 4)

#define ODP_ETHER_MAX_JUMBO_FRAME_LEN \
	0x3F00

#define ODP_ETHER_MAX_VLAN_ID 4095
#define ODP_ETHER_MIN_MTU 68

struct odp_ether_addr {
	uint8_t addr_bytes[ODP_ETHER_ADDR_LEN];
} __attribute__((__packed__));

#define ETHER_LOCAL_ADMIN_ADDR 0x02
#define ETHER_GROUP_ADDR       0x01

struct odp_dev_eeprom_info {
	void	*data;
	uint32_t offset;
	uint32_t length;
	uint32_t magic;
};

struct odp_dev_reg_info {
	void	*data;
	uint32_t offset;
	uint32_t length;
	uint32_t version;
};

static inline int is_zero_ether_addr(const struct odp_ether_addr *ea)
{
	int i;

	for (i = 0; i < ODP_ETHER_ADDR_LEN; i++)
		if (ea->addr_bytes[i] != 0x00)
			return 0;

	return 1;
}

static inline int is_same_ether_addr(const struct odp_ether_addr *ea1,
				     const struct odp_ether_addr *ea2)
{
	int i;

	for (i = 0; i < ODP_ETHER_ADDR_LEN; i++)
		if (ea1->addr_bytes[i] != ea2->addr_bytes[i])
			return 0;

	return 1;
}

static inline int is_broadcast_ether_addr(const struct odp_ether_addr *ea)
{
	const uint16_t *ea_words = (const uint16_t *)ea;

	return ((ea_words[0] == 0xFFFF) && (ea_words[1] == 0xFFFF) &&
		(ea_words[2] == 0xFFFF));
}

static inline int is_universal_ether_addr(const struct odp_ether_addr *ea)
{
	return ((ea->addr_bytes[0] & ETHER_LOCAL_ADMIN_ADDR) == 0);
}

static inline int is_unicast_ether_addr(const struct odp_ether_addr *ea)
{
	return ((ea->addr_bytes[0] & ETHER_GROUP_ADDR) == 0);
}

static inline int is_multicast_ether_addr(const struct odp_ether_addr *ea)
{
	return (ea->addr_bytes[0] & ETHER_GROUP_ADDR);
}

static inline int is_local_admin_ether_addr(const struct odp_ether_addr *ea)
{
	return ((ea->addr_bytes[0] & ETHER_LOCAL_ADMIN_ADDR) != 0);
}

static inline int is_valid_assigned_ether_addr(const struct odp_ether_addr *ea)
{
	return (is_unicast_ether_addr(ea) && (!is_zero_ether_addr(ea)));
}

static inline uint64_t odp_rand(void)
{
	uint64_t val;

	val   = lrand48();
	val <<= 32;
	val  += lrand48();
	return val;
}

static inline void eth_random_addr(uint8_t *addr)
{
	uint64_t rand = odp_rand();
	uint8_t *p = (uint8_t *)&rand;

	memcpy(addr, p, ODP_ETHER_ADDR_LEN);
	addr[0] &= ~ETHER_GROUP_ADDR;
	addr[0] |= ETHER_LOCAL_ADMIN_ADDR;
}

static inline void ether_addr_copy(const struct odp_ether_addr *ea_from,
				   struct odp_ether_addr       *ea_to)
{
	memcpy(ea_to->addr_bytes, ea_from->addr_bytes, ODP_ETHER_ADDR_LEN);
}

#define ETHER_ADDR_FMT_SIZE 18

static inline void ether_format_addr(char *buf, uint16_t size,
				     const struct odp_ether_addr *eth_addr)
{
	snprintf(buf, size, "%02X:%02X:%02X:%02X:%02X:%02X",
		 eth_addr->addr_bytes[0],
		 eth_addr->addr_bytes[1],
		 eth_addr->addr_bytes[2],
		 eth_addr->addr_bytes[3],
		 eth_addr->addr_bytes[4],
		 eth_addr->addr_bytes[5]);
}

struct ether_hdr {
	struct odp_ether_addr d_addr;
	struct odp_ether_addr s_addr;
	uint16_t	      ether_type;
} __attribute__((__packed__));

struct vlan_hdr {
	uint16_t vlan_tci;
	uint16_t eth_proto;
} __attribute__((__packed__));

struct vxlan_hdr {
	uint32_t vx_flags;
	uint32_t vx_vni;
} __attribute__((__packed__));

#define ETHER_TYPE_VLAN 0x8100
#define ETHER_TYPE_1588 0x88F7
#define ETHER_TYPE_SLOW 0x8809
#define ETHER_TYPE_TEB	0x6558
#define ETHER_TYPE_IPV4 0x0800
#define ETHER_TYPE_IPV6 0x86DD
#define ETHER_TYPE_ARP	0x0806
#define ETHER_TYPE_RARP 0x8035

#ifdef __cplusplus
}
#endif
#endif /* _ODP_ETHER_H_ */
