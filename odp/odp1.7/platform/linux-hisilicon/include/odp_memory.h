/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_MEMORY_H_
#define _ODP_MEMORY_H_

#include <stdint.h>
#include <stddef.h>
#include <stdio.h>

#ifdef __cplusplus
extern "C" {
#endif

enum odp_page_sizes {
	ODP_PGSIZE_4K  = 1ULL << 12,
	ODP_PGSIZE_2M  = 1ULL << 21,
	ODP_PGSIZE_1G  = 1ULL << 30,
	ODP_PGSIZE_64K = 1ULL << 16,
	ODP_PGSIZE_16M = 1ULL << 24,
	ODP_PGSIZE_16G = 1ULL << 34
};

#define ODP_PAGE_MEMORY_MAX (1024 * 1024 * 1024)
#define SOCKET_ID_ANY	    -1
#ifndef ODP_CACHE_LINE_SIZE
#define ODP_CACHE_LINE_SIZE 64
#endif
#define ODP_CACHE_LINE_MASK (ODP_CACHE_LINE_SIZE - 1)

#define ODP_CACHE_LINE_ROUNDUP(size) \
	(ODP_CACHE_LINE_SIZE * ((size + ODP_CACHE_LINE_SIZE - \
				 1) / ODP_CACHE_LINE_SIZE))

#define __odp_cache_aligned __attribute__((__aligned__(ODP_CACHE_LINE_SIZE)))

typedef uint64_t phys_addr_t;

#define ODP_BAD_PHYS_ADDR ((phys_addr_t)-1)

struct odp_mmfrag {
	phys_addr_t phys_addr;

	union {
		void	*addr;
		uint64_t addr_64;
	};

	size_t	 len;
	uint64_t hugepage_sz;
	int32_t	 socket_id;
	uint32_t channel_num;
	uint32_t rank_num;

#ifdef ODP_LIBODP_XEN_DOM0
	uint64_t mfn[2048];
#endif
} __attribute__((__packed__));

void odp_dump_physmem_layout(FILE *f);

uint64_t odp_get_physmem_size(void);

unsigned int odp_memory_get_channel_num(void);

unsigned int odp_memory_get_rank_num(void);

void odp_free(void *addr);

void *odp_zmalloc(const char *type, size_t size, unsigned align);

int odp_mem_lock_page(const void *virt);

phys_addr_t odp_mem_virt2phy(const void *virt);

const struct odp_mmfrag *odp_get_physmem_layout(void);

#ifdef ODP_LIBODP_XEN_DOM0

phys_addr_t odp_mem_phy2mch(uint32_t mmfrag_id, const phys_addr_t phy_addr);

int odp_xen_dom0_memory_init(void);

int odp_xen_dom0_memory_attach(void);
#endif
int odp_memory_init(void);
#ifdef __cplusplus
}
#endif
#endif
