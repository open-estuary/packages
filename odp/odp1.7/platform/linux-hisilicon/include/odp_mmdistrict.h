/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_MEMZONE_H_
#define _ODP_MEMZONE_H_

#include <stdio.h>
#include "odp_memory.h"

#ifdef __cplusplus
extern "C" {
#endif

#define ODP_MEMZONE_2MB		   0x00000001
#define ODP_MEMZONE_1GB		   0x00000002
#define ODP_MEMZONE_16MB	   0x00000100
#define ODP_MEMZONE_16GB	   0x00000200
#define ODP_MEMZONE_SIZE_HINT_ONLY 0x00000004
#define ODP_MEMZONE_NAMESIZE	   64

struct odp_mm_district {
	char	    name[ODP_MEMZONE_NAMESIZE];
	char	    orig_name[ODP_MEMZONE_NAMESIZE - 8];
	phys_addr_t phys_addr;
	phys_addr_t phys_addr_end;
	void	   *excursion_addr;

	union {
		void	*addr;
		uint64_t addr_64;
	};
	size_t len;
	uint64_t hugepage_sz;
	int32_t socket_id;
	uint32_t flags;
	uint32_t mmfrag_id;
} __attribute__((__packed__));

const struct odp_mm_district
*odp_mm_district_reserve(const char *name,
			 const char *orig_name,
			 size_t len, int socket_id,
			 unsigned int flags);
const uint32_t odp_mm_district_unreserve(const char *name);

const struct odp_mm_district
*odp_mm_district_reserve_aligned(const char *name,
				 const char *orig_name,
				 size_t len, int socket_id,
				 unsigned int flags,
				 unsigned int align);

const struct odp_mm_district
*odp_mm_district_reserve_bounded(const char *name,
				 const char *orig_name,
				 size_t len, int socket_id,
				 unsigned flags, unsigned int align,
				 unsigned int bound);

const struct odp_mm_district *odp_mm_district_lookup(const char *name);

void odp_mm_district_dump(FILE *);

void
odp_mm_district_walk(void (*func)(const struct odp_mm_district *, void *arg),
		     void *arg);

unsigned long long odp_v2p(const void *virtaddr);
inline void *odp_p2v(const unsigned long long phyaddr);

#ifdef __cplusplus
}
#endif
#endif /* _ODP_MEMZONE_H_ */
