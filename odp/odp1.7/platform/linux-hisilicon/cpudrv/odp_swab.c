/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#include <inttypes.h>

uint16_t  __arch_swab16(uint16_t x)
{
	__asm__("rev16 %0, %1" : "=r" (x) : "r" (x));
	return x;
}

#if (!defined(__arm32__))
uint32_t __arch_swab32(uint32_t x)
{
	__asm__("rev32 %0, %1" : "=r" (x) : "r" (x));
	return x;
}
#endif
