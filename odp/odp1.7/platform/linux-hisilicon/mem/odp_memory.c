/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdarg.h>
#include <inttypes.h>
#include <sys/queue.h>

#include <odp_memory.h>
#include <odp_mmdistrict.h>
#include <odp_base.h>
#include <odp_syslayout.h>
#include "odp_private.h"

const struct odp_mmfrag *odp_get_physmem_layout(void)
{
	return odp_get_configuration()->sys_layout->mmfrag;
}

uint64_t odp_get_physmem_size(void)
{
	const struct odp_sys_layout *mcfg;
	unsigned i = 0;
	uint64_t total_len = 0;

	mcfg = odp_get_configuration()->sys_layout;

	for (i = 0; i < ODP_MAX_MMFRAG; i++) {
		if (!mcfg->mmfrag[i].addr)
			break;

		total_len += mcfg->mmfrag[i].len;
	}

	return total_len;
}

void odp_dump_physmem_layout(FILE *f)
{
	const struct odp_sys_layout *mcfg;
	unsigned i = 0;

	mcfg = odp_get_configuration()->sys_layout;

	for (i = 0; i < ODP_MAX_MMFRAG; i++) {
		if (!mcfg->mmfrag[i].addr)
			break;

		fprintf(f, "Segment %u: phys:0x%lx, len:%zu, virt:%p, "
			"socket_id:%d, hugepage_sz:%lu, "
			"channel_num:%x, rank_num:%x\n", i,
			mcfg->mmfrag[i].phys_addr,
			mcfg->mmfrag[i].len,
			mcfg->mmfrag[i].addr,
			mcfg->mmfrag[i].socket_id,
			mcfg->mmfrag[i].hugepage_sz,
			mcfg->mmfrag[i].channel_num,
			mcfg->mmfrag[i].rank_num);
	}
}

unsigned int odp_memory_get_channel_num(void)
{
	return odp_get_configuration()->sys_layout->channel_num;
}

unsigned int odp_memory_get_rank_num(void)
{
	return odp_get_configuration()->sys_layout->rank_num;
}
