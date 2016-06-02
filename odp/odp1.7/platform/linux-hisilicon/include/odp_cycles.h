/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_CYCLES_X86_64_H_
#define _ODP_CYCLES_X86_64_H_

#ifdef __cplusplus
extern "C" {
#endif
#include <odp/cpu.h>
#include <odp/sync.h>
#include <odp/atomic.h>
#include <odp/time.h>

static inline uint64_t odp_rdtsc_precise(void)
{
	odp_mb_full();
	return odp_cpu_cycles();
}

static inline uint64_t odp_get_tsc_cycles(void)
{
	return odp_cpu_cycles();
}

#ifdef __cplusplus
}
#endif
#endif /* _ODP_CYCLES_X86_64_H_ */
