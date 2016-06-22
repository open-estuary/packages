/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <stdarg.h>
#include <inttypes.h>
#include <string.h>
#include <errno.h>
#include <sys/queue.h>

#include <odp/atomic.h>
#include <odp_memory.h>
#include <odp_mmdistrict.h>
#include <odp_base.h>
#include <odp_syslayout.h>
#include <odp/config.h>

#include <odp_common.h>

#include "odp_private.h"
#include "odp_debug_internal.h"

static struct odp_mmfrag *free_mmfrag;

static inline struct odp_mm_district *mm_district_lookup(const char *name)
{
	struct odp_sys_layout *mcfg;
	unsigned i = 0;

	mcfg = odp_get_configuration()->sys_layout;

	for (i = 0; i < ODP_MAX_MM_DISTRICT && mcfg->mm_district[i].addr; i++)
		if (!strncmp(name, mcfg->mm_district[i].name,
			     ODP_MEMZONE_NAMESIZE))
			return &mcfg->mm_district[i];

	return NULL;
}

static inline struct odp_mm_district *free_mm_district_lookup(
	const char *orig_name)
{
	struct odp_sys_layout *mcfg;
	unsigned int i = 0;

	mcfg = odp_get_configuration()->sys_layout;

	for (i = 0; i < ODP_MAX_MM_DISTRICT && mcfg->free_mm_district[i].addr;
	     i++)
		if (!strncmp(orig_name, mcfg->free_mm_district[i].orig_name,
			     ODP_MEMZONE_NAMESIZE - 8))
			return &mcfg->free_mm_district[i];

	return NULL;
}

static inline void free_mm_district_fetch(struct odp_mm_district *md)
{
	struct odp_sys_layout *mcfg;
	unsigned i = 0;

	mcfg = odp_get_configuration()->sys_layout;
	mcfg->mm_district_idx++;
	memcpy(&mcfg->mm_district[mcfg->mm_district_idx], md,
	       sizeof(struct odp_mm_district));

	for (i = 0; i < mcfg->free_district_idx && md->addr; i++) {
		memcpy(md, md + 1, sizeof(struct odp_mm_district));
		md++;
	}

	memset(md, 0, sizeof(*md));
	md->socket_id = SOCKET_ID_ANY;
	mcfg->free_district_idx -= 1;
}

static inline const struct odp_mm_district *get_mm_district_by_vaddress(
	const void *addr)
{
	const struct odp_sys_layout *mcfg;
	unsigned i = 0;

	mcfg = odp_get_configuration()->sys_layout;

	for (i = 0; i < ODP_MAX_MM_DISTRICT && mcfg->mm_district[i].addr; i++)
		if ((((unsigned long)addr) >=
		     (unsigned long)(mcfg->mm_district[i].addr)) &&
		    (((unsigned long)addr) <
		     (((unsigned long)(mcfg->mm_district[i].addr)) +
		      mcfg->mm_district[i].len)))
			return &mcfg->mm_district[i];

	return NULL;
}

unsigned long long odp_v2p(const void *virtaddr)
{
	const struct odp_mm_district *mm_district;

	mm_district = get_mm_district_by_vaddress(virtaddr);
	if (mm_district)
		return (mm_district->phys_addr) + ((unsigned long)virtaddr -
						   (unsigned long)(mm_district->
								   addr));

	return -1;
}

static inline const struct odp_mm_district *get_mm_district_by_phyddress(
	const unsigned long long phyaddr)
{
	static unsigned int i;
	const struct odp_sys_layout *mcfg =
		odp_get_configuration()->sys_layout;

	if ((phyaddr >= mcfg->mm_district[i].phys_addr) &&
	    (phyaddr < mcfg->mm_district[i].phys_addr_end))
		return &mcfg->mm_district[i];

	for (i = 0; i < ODP_MAX_MM_DISTRICT && mcfg->mm_district[i].addr; i++)
		if ((phyaddr >= mcfg->mm_district[i].phys_addr) &&
		    (phyaddr < mcfg->mm_district[i].phys_addr_end))
			return &mcfg->mm_district[i];

	return NULL;
}

inline void *odp_p2v(const unsigned long long phyaddr)
{
	static unsigned int i;
	const struct odp_sys_layout *mcfg =
		odp_get_configuration()->sys_layout;

	if ((phyaddr >= mcfg->mm_district[i].phys_addr) &&
	    (phyaddr < mcfg->mm_district[i].phys_addr_end))
#ifdef __arm32__
		return mcfg->mm_district[i].addr +
		       (phyaddr - mcfg->mm_district[i].phys_addr);

#else
		return mcfg->mm_district[i].excursion_addr + phyaddr;
#endif

	for (i = 0; i < ODP_MAX_MM_DISTRICT && mcfg->mm_district[i].addr; i++)
		if ((phyaddr >= mcfg->mm_district[i].phys_addr) &&
		    (phyaddr < mcfg->mm_district[i].phys_addr_end))
#ifdef __arm32__
			return mcfg->mm_district[i].addr +
			       (phyaddr - mcfg->mm_district[i].phys_addr);

#else
			return mcfg->mm_district[i].excursion_addr + phyaddr;
#endif

	return NULL;
}

const struct odp_mm_district *odp_mm_district_reserve(const char *name,
						      const char *orig_name,
						      size_t len, int socket_id,
						      unsigned flags)
{
	return odp_mm_district_reserve_aligned(name, orig_name, len,
					       socket_id, flags,
					       ODP_CACHE_LINE_SIZE);
}

static inline phys_addr_t align_phys_boundary(const struct odp_mmfrag *ms,
					      size_t len, size_t align,
					      size_t bound)
{
	phys_addr_t addr_offset, bmask, end, start;
	size_t step;

	step  = ODP_MAX(align, bound);
	bmask = ~((phys_addr_t)bound - 1);

	start = ODP_ALIGN_CEIL(ms->phys_addr, align);
	addr_offset = start - ms->phys_addr;

	while (addr_offset + len < ms->len) {
		end = start + len - (len != 0);
		if ((start & bmask) == (end & bmask))
			break;

		start = ODP_ALIGN_CEIL(start + 1, step);
		addr_offset = start - ms->phys_addr;
	}

	return addr_offset;
}

static const struct odp_mm_district *mm_district_reserve_aligned(
	const char *name, const char *orig_name,
	size_t len,
	int socket_id, unsigned int flags,
	unsigned int align,
	unsigned int bound)
{
	struct odp_sys_layout *mcfg;
	unsigned i = 0;
	int mmfrag_idx = -1;
	uint64_t addr_offset, seg_offset = 0;
	size_t	 requested_len;
	size_t	 mmfrag_len = 0;
	phys_addr_t mmfrag_physaddr;
	void *mmfrag_addr;
	struct odp_mm_district *mm_district = NULL;

	mcfg = odp_get_configuration()->sys_layout;

	if (mcfg->mm_district_idx >= ODP_MAX_MM_DISTRICT) {
		ODP_ERR("%s: No more room in config\n", name);
		odp_err = ENOSPC;
		return NULL;
	}

	mm_district = mm_district_lookup(name);
	if (mm_district)
		return mm_district;

	if (align && !odp_is_power_of_2(align)) {
		ODP_ERR("Invalid alignment: %u\n", align);
		odp_err = EINVAL;
		return NULL;
	}

	if (align < ODP_CACHE_LINE_SIZE)
		align = ODP_CACHE_LINE_SIZE;

	if (len > MEM_SIZE_MAX - ODP_CACHE_LINE_MASK) {
		odp_err = EINVAL;
		return NULL;
	}

	len += ODP_CACHE_LINE_MASK;
	len &= ~((size_t)ODP_CACHE_LINE_MASK);

	requested_len = ODP_MAX((size_t)ODP_CACHE_LINE_SIZE, len);

	if ((bound != 0) && ((requested_len > bound) ||
			     !odp_is_power_of_2(bound))) {
		odp_err = EINVAL;
		return NULL;
	}

	for (i = 0; i < ODP_MAX_MMFRAG; i++) {
		if (!free_mmfrag[i].addr)
			break;

		if (free_mmfrag[i].len == 0)
			continue;

		if ((socket_id != SOCKET_ID_ANY) &&
		    (free_mmfrag[i].socket_id != SOCKET_ID_ANY) &&
		    (socket_id != free_mmfrag[i].socket_id))
			continue;

		addr_offset = align_phys_boundary(free_mmfrag + i,
						  requested_len, align, bound);

		if ((requested_len + addr_offset) > free_mmfrag[i].len)
			continue;

		if ((flags & ODP_MEMZONE_2MB) &&
		    (free_mmfrag[i].hugepage_sz == ODP_PGSIZE_1G))
			continue;

		if ((flags & ODP_MEMZONE_1GB) &&
		    (free_mmfrag[i].hugepage_sz == ODP_PGSIZE_2M))
			continue;

		if ((flags & ODP_MEMZONE_16MB) &&
		    (free_mmfrag[i].hugepage_sz == ODP_PGSIZE_16G))
			continue;

		if ((flags & ODP_MEMZONE_16GB) &&
		    (free_mmfrag[i].hugepage_sz == ODP_PGSIZE_16M))
			continue;

		if (mmfrag_idx == -1) {
			mmfrag_idx = i;
			mmfrag_len = free_mmfrag[i].len;
			seg_offset = addr_offset;
		}

		else if (len == 0) {
			if (free_mmfrag[i].len > mmfrag_len) {
				mmfrag_idx = i;
				mmfrag_len = free_mmfrag[i].len;
				seg_offset = addr_offset;
			}
		}

		else if ((free_mmfrag[i].len + align < mmfrag_len) ||
			 ((free_mmfrag[i].len <= mmfrag_len + align) &&
			  (addr_offset < seg_offset))) {
			mmfrag_idx = i;
			mmfrag_len = free_mmfrag[i].len;
			seg_offset = addr_offset;
		}
	}

	if (mmfrag_idx == -1) {

		if ((flags & ODP_MEMZONE_SIZE_HINT_ONLY) &&
		    ((flags & ODP_MEMZONE_1GB) ||
		     (flags & ODP_MEMZONE_2MB) ||
		     (flags & ODP_MEMZONE_16MB) ||
		     (flags & ODP_MEMZONE_16GB)))
			return mm_district_reserve_aligned(name, orig_name,
							   len, socket_id, 0,
							   align, bound);

		odp_err = ENOMEM;
		return NULL;
	}

	mmfrag_physaddr = free_mmfrag[mmfrag_idx].phys_addr + seg_offset;
	mmfrag_addr = ODP_PTR_ADD(free_mmfrag[mmfrag_idx].addr,
				  (uintptr_t)seg_offset);

	if (len == 0) {
		if (bound == 0)
			requested_len = mmfrag_len - seg_offset;
		else
			requested_len =
				ODP_ALIGN_CEIL(mmfrag_physaddr + 1, bound)
				- mmfrag_physaddr;
	}

	len = (size_t)seg_offset + requested_len;

	free_mmfrag[mmfrag_idx].len -= len;
	free_mmfrag[mmfrag_idx].phys_addr += len;
	free_mmfrag[mmfrag_idx].addr =
		(char *)free_mmfrag[mmfrag_idx].addr + len;

	struct odp_mm_district *mz =
		&mcfg->mm_district[mcfg->mm_district_idx++];

	snprintf(mz->orig_name, sizeof(mz->orig_name), "%s", orig_name);
	snprintf(mz->name, sizeof(mz->name), "%s", name);
	mz->phys_addr = mmfrag_physaddr;
	mz->phys_addr_end  = mmfrag_physaddr + requested_len;
	mz->excursion_addr = mmfrag_addr - mmfrag_physaddr;
	mz->addr = mmfrag_addr;
	mz->len	 = requested_len;
	mz->hugepage_sz = free_mmfrag[mmfrag_idx].hugepage_sz;
	mz->socket_id = free_mmfrag[mmfrag_idx].socket_id;
	mz->flags = 0;
	mz->mmfrag_id = mmfrag_idx;

	return mz;
}

static const uint32_t mm_district_unreserve(const char *name)
{
	struct odp_sys_layout *mcfg = odp_get_configuration()->sys_layout;
	uint16_t index;
	const struct odp_mm_district *md = NULL;
	int i = 0;
	uint32_t md_id = 0;

	md = mm_district_lookup(name);
	if (!md) {
		ODP_ERR("%s mm district is not exist!!\r\n", name);
		return -1;
	}

	md_id = md->mmfrag_id;

	if ((free_mmfrag[md_id].addr + free_mmfrag[md_id].len) ==
	    md->addr) {
		free_mmfrag[md_id].len += md->len;
	} else if ((md->addr + md->len) == free_mmfrag[md_id].addr) {
		free_mmfrag[md_id].addr = md->addr;
		free_mmfrag[md_id].len += md->len;
	} else {
		if (mcfg->free_district_idx < ODP_MAX_MM_DISTRICT) {
			memcpy(&mcfg->free_mm_district[mcfg->free_district_idx],
			       md, sizeof(struct odp_mm_district));
			mcfg->free_district_idx++;
		} else {
			ODP_ERR("mm_district_unreserve fail!!\r\n");
			return -1;
		}
	}

	for (index = 0; index < ODP_MAX_MM_DISTRICT &&
	     mcfg->mm_district[index].addr; index++)
		if (!strncmp(name, mcfg->mm_district[index].name,
			     ODP_MEMZONE_NAMESIZE)) {
			struct odp_mm_district *md_t =
				&mcfg->mm_district[index];

			for (i = index; i < mcfg->mm_district_idx; i++) {
				memcpy(md_t, md_t + 1,
				       sizeof(struct odp_mm_district));
				md_t++;
			}

			memset(md_t, 0, sizeof(*md_t));
			md_t->socket_id = SOCKET_ID_ANY;
			mcfg->mm_district_idx -= 1;
			return 0;
		}

	return -1;
}

const struct odp_mm_district
*odp_mm_district_reserve_aligned(const char *name,
				 const char *orig_name, size_t len,
				 int socket_id, unsigned int flags,
				 unsigned int align)
{
	struct odp_sys_layout *mcfg;
	const struct odp_mm_district *mz = NULL;

	if (((flags & ODP_MEMZONE_1GB) && (flags & ODP_MEMZONE_2MB)) ||
	    ((flags & ODP_MEMZONE_16MB) && (flags & ODP_MEMZONE_16GB))) {
		odp_err = EINVAL;
		return NULL;
	}

	mcfg = odp_get_configuration()->sys_layout;

	odp_rwlock_write_lock(&mcfg->mlock);

	mz = mm_district_reserve_aligned(name, orig_name,
					 len, socket_id, flags, align, 0);

	odp_rwlock_write_unlock(&mcfg->mlock);

	return mz;
}

const struct odp_mm_district
*odp_mm_district_reserve_bounded(const char *name,
				 const char *orig_name,
				 size_t len, int socket_id, unsigned flags,
				 unsigned align,
				 unsigned bound)
{
	struct odp_sys_layout *mcfg;
	const struct odp_mm_district *mz = NULL;

	if (((flags & ODP_MEMZONE_1GB) && (flags & ODP_MEMZONE_2MB)) ||
	    ((flags & ODP_MEMZONE_16MB) && (flags & ODP_MEMZONE_16GB))) {
		odp_err = EINVAL;
		return NULL;
	}

	mcfg = odp_get_configuration()->sys_layout;

	odp_rwlock_write_lock(&mcfg->mlock);

	mz = mm_district_reserve_aligned(
		name, orig_name, len, socket_id, flags, align, bound);

	odp_rwlock_write_unlock(&mcfg->mlock);

	return mz;
}

const uint32_t odp_mm_district_unreserve(const char *name)
{
	struct odp_sys_layout *mcfg;
	uint32_t ret;

	mcfg = odp_get_configuration()->sys_layout;

	odp_rwlock_write_lock(&mcfg->mlock);

	ret = mm_district_unreserve(name);

	odp_rwlock_write_unlock(&mcfg->mlock);

	return ret;
}

const struct odp_mm_district *odp_mm_district_lookup(const char *name)
{
	struct odp_sys_layout *mcfg;
	const struct odp_mm_district *mm_district = NULL;

	mcfg = odp_get_configuration()->sys_layout;

	odp_rwlock_read_lock(&mcfg->mlock);

	mm_district = mm_district_lookup(name);

	odp_rwlock_read_unlock(&mcfg->mlock);

	return mm_district;
}

void odp_mm_district_dump(FILE *f)
{
	struct odp_sys_layout *mcfg;
	unsigned i = 0;

	mcfg = odp_get_configuration()->sys_layout;

	odp_rwlock_read_lock(&mcfg->mlock);

	for (i = 0; i < ODP_MAX_MM_DISTRICT; i++) {
		if (!mcfg->mm_district[i].addr)
			break;

		fprintf(f, "Zone %u: name:<%s>, phys:0x%lx, len:0x%zx, "
			"virt:%p, socket_id:%d, flags:%x\n", i,
			mcfg->mm_district[i].name,
			mcfg->mm_district[i].phys_addr,
			mcfg->mm_district[i].len,
			mcfg->mm_district[i].addr,
			mcfg->mm_district[i].socket_id,
			mcfg->mm_district[i].flags);
	}

	odp_rwlock_read_unlock(&mcfg->mlock);
}

static int mmfrag_sanitize(struct odp_mmfrag *mmfrag)
{
	unsigned phys_align;
	unsigned virt_align;
	unsigned off;

	phys_align = mmfrag->phys_addr & ODP_CACHE_LINE_MASK;
	virt_align = (unsigned long)mmfrag->addr & ODP_CACHE_LINE_MASK;

	if (phys_align != virt_align)
		return -1;

	if (mmfrag->len < (2 * ODP_CACHE_LINE_SIZE)) {
		mmfrag->len = 0;
		return 0;
	}

	off = (ODP_CACHE_LINE_SIZE - phys_align) & ODP_CACHE_LINE_MASK;
	mmfrag->phys_addr += off;
	mmfrag->addr = (char *)mmfrag->addr + off;
	mmfrag->len -= off;

	mmfrag->len &= ~((uint64_t)ODP_CACHE_LINE_MASK);

	return 0;
}

int odp_mm_district_init(void)
{
	struct odp_sys_layout *mcfg;
	const struct odp_mmfrag *mmfrag;
	unsigned i = 0;

	mcfg = odp_get_configuration()->sys_layout;

	free_mmfrag = mcfg->free_mmfrag;

	if (odp_process_type() == ODP_PROC_SECONDARY)
		return 0;

	mmfrag = odp_get_physmem_layout();
	if (!mmfrag) {
		ODP_ERR("Cannot get physical layout.\n");
		return -1;
	}

	odp_rwlock_write_lock(&mcfg->mlock);

	for (i = 0; i < ODP_MAX_MMFRAG; i++) {
		if (!mmfrag[i].addr)
			break;

		if (free_mmfrag[i].addr)
			continue;

		memcpy(&free_mmfrag[i], &mmfrag[i], sizeof(struct odp_mmfrag));
	}

	mcfg->free_frag_idx = i - 1;

	for (i = 0; i < ODP_MAX_MMFRAG; i++) {
		if (!free_mmfrag[i].addr)
			break;

		if (mmfrag_sanitize(&free_mmfrag[i]) < 0) {
			ODP_ERR("Sanity check failed\n");
			odp_rwlock_write_unlock(&mcfg->mlock);
			return -1;
		}
	}

	mcfg->mm_district_idx = 0;
	memset(mcfg->mm_district, 0, sizeof(mcfg->mm_district));

	odp_rwlock_write_unlock(&mcfg->mlock);

	return 0;
}

void odp_mm_district_walk(void (*func)(const struct odp_mm_district *, void *),
			  void *arg)
{
	struct odp_sys_layout *mcfg;
	unsigned i;

	mcfg = odp_get_configuration()->sys_layout;

	odp_rwlock_read_lock(&mcfg->mlock);
	for (i = 0; i < ODP_MAX_MM_DISTRICT; i++)
		if (mcfg->mm_district[i].addr)
			(*func)(&mcfg->mm_district[i], arg);

	odp_rwlock_read_unlock(&mcfg->mlock);
}
