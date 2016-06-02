/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_PREFETCH_ARM64_H_
#define _ODP_PREFETCH_ARM64_H_

#ifdef __cplusplus
extern "C" {
#endif

static inline void odp_prefetch0(void *p)
{
	asm volatile ("PRFM PLDL1KEEP, [%0]" : : "r" (p));
}

static inline void odp_prefetch1(void *p)
{
	asm volatile ("PRFM PLDL2KEEP, [%0]" : : "r" (p));
}

static inline void odp_prefetch2(void *p)
{
	asm volatile ("PRFM PLDL3KEEP, [%0]" : : "r" (p));
}

#ifdef __cplusplus
}
#endif
#endif
