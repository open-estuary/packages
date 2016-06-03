/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_MEMCONFIG_H_
#define _ODP_MEMCONFIG_H_
#include <odp_base.h>
#include <odp_config.h>
#include <odp_tailq.h>
#include <odp_memory.h>
#include <odp_mmdistrict.h>
#include <odp/rwlock.h>
#include <odp_common.h>

#ifdef __cplusplus
extern "C" {
#endif
#define MEM_SIZE_MAX 0x100000000000ull

struct odp_sys_layout {
	uint64_t mem_cfg_addr;
	uint32_t magic;
	uint32_t channel_num;
	uint32_t rank_num;
	uint32_t mm_district_idx;
	uint32_t frag_idx;
	uint32_t free_frag_idx;
	uint32_t free_district_idx;

	odp_rwlock_t mlock;
	odp_rwlock_t qlock;
	odp_rwlock_t mplock;
	odp_rwlock_t rw_lock;
	uint64_t     odp_lcore_status[ODP_MAX_CORE >> 6];

	struct odp_mmfrag mmfrag[ODP_MAX_MMFRAG];
	struct odp_mm_district mm_district[ODP_MAX_MM_DISTRICT];
	struct odp_mm_district free_mm_district[ODP_MAX_MM_DISTRICT];
	struct odp_mmfrag free_mmfrag[ODP_MAX_MMFRAG];
	struct odp_tailq_head tailq_head[ODP_MAX_TAILQ];

} __attribute__((__packed__));

static inline void odp_mcfg_wait_complete(struct odp_sys_layout *mcfg)
{
	while (mcfg->magic != ODP_MAGIC)
		odp_pause();
}

#ifdef __cplusplus
}
#endif
#endif
