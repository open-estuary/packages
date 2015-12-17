/* Copyright (c) 2013, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */

/**
 * @file
 *
 * ODP atomic operations
 */

#ifndef ODP_PLAT_ATOMIC_H_
#define ODP_PLAT_ATOMIC_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <stdint.h>
#include <odp/align.h>
#include <odp/plat/atomic_types.h>
#include <odp/odp_atomic.h>
/** @ingroup odp_synchronizers
 *  @{
 */
#if 0
static inline  unsigned int odp_atomic_fetch_and_add_u32(unsigned int *ptr, unsigned int lIncr)
{
        unsigned long tmp;
        unsigned int result;
    
        asm volatile("// atomic_add_return\n"
    "1: ldaxr   %w0, %2\n"
    "   add %w0, %w0, %w3\n"
    "   stlxr   %w1, %w0, %2\n"
    "   cbnz    %w1, 1b"
        : "=&r" (result), "=&r" (tmp), "+Q" (*ptr)
        : "Ir" (lIncr)
        : "cc", "memory");
    
        return result - lIncr;
}

static inline  unsigned long long odp_atomic_fetch_and_add_u64(unsigned long long *ptr, unsigned long long lIncr)
    {
        unsigned long long result;
        unsigned long long tmp;
    
        asm volatile("// atomic64_add_return\n"
    "1: ldaxr   %0, %2\n"
    "   add %0, %0, %3\n"
    "   stlxr   %w1, %0, %2\n"
    "   cbnz    %w1, 1b"
        : "=&r" (result), "=&r" (tmp), "+Q" (*ptr)
        : "Ir" (lIncr)
        : "cc", "memory");
    
        return result - lIncr;
    }


static inline  unsigned int odp_atomic_fetch_and_sub_u32(unsigned int *ptr, unsigned int lIncr)
{
        unsigned int tmp;
        unsigned int result;
    
        asm volatile("// atomic_sub_return\n"
    "1: ldaxr   %w0, %2\n"
    "   sub %w0, %w0, %w3\n"
    "   stlxr   %w1, %w0, %2\n"
    "   cbnz    %w1, 1b"
        : "=&r" (result), "=&r" (tmp), "+Q" (*ptr)
        : "Ir" (lIncr)
        : "cc", "memory");
    
        return result - lIncr;
}

static inline  unsigned long long odp_atomic_fetch_and_sub_u64(unsigned long long *ptr, unsigned long long lIncr)
{
        unsigned long long result;
        unsigned long tmp;
    
        asm volatile("// atomic64_sub_return\n"
    "1: ldaxr   %0, %2\n"
    "   sub %0, %0, %3\n"
    "   stlxr   %w1, %0, %2\n"
    "   cbnz    %w1, 1b"
        : "=&r" (result), "=&r" (tmp), "+Q" (*ptr)
        : "Ir" (lIncr)
        : "cc", "memory");
    
        return result - lIncr;
}


static inline  void odp_atomic_add_u32( unsigned int *ptr, unsigned int ulIncr)
{
    unsigned long tmp;
    int result;
    
        asm volatile("// atomic_add\n"
    "1: ldxr    %w0, %2\n"
    "   add %w0, %w0, %w3\n"
    "   stxr    %w1, %w0, %2\n"
    "   cbnz    %w1, 1b"
        : "=&r" (result), "=&r" (tmp), "+Q" (*ptr)
        : "Ir" (ulIncr)
        : "cc");
}


static inline  void odp_atomic_add_u64( unsigned long long *ptr, unsigned long long ulIncr)
{
    long result;
    unsigned long tmp;

        asm volatile("// atomic64_add\n"
    "1: ldxr    %0, %2\n"
    "   add %0, %0, %3\n"
    "   stxr    %w1, %0, %2\n"
    "   cbnz    %w1, 1b"
        : "=&r" (result), "=&r" (tmp), "+Q" (*ptr)
        : "Ir" (ulIncr)
        : "cc");
}


static inline  void odp_atomic_sub_u32( unsigned int *ptr, unsigned int ulIncr)
{
        unsigned long tmp;
        int result;
    
        asm volatile("// atomic_sub\n"
    "1: ldxr    %w0, %2\n"
    "   sub %w0, %w0, %w3\n"
    "   stxr    %w1, %w0, %2\n"
    "   cbnz    %w1, 1b"
        : "=&r" (result), "=&r" (tmp), "+Q" (*ptr)
        : "Ir" (ulIncr)
        : "cc");
}

static inline  void odp_atomic_sub_u64( unsigned long long *ptr, unsigned long long ulIncr)
{
        long result;
        unsigned long tmp;
    
        asm volatile("// atomic64_sub\n"
    "1: ldxr    %0, %2\n"
    "   sub %0, %0, %3\n"
    "   stxr    %w1, %0, %2\n"
    "   cbnz    %w1, 1b"
        : "=&r" (result), "=&r" (tmp), "+Q" (*ptr)
        : "Ir" (ulIncr)
        : "cc");
}

static inline void odp_atomic_init_u32(odp_atomic_u32_t *atom, uint32_t val)
{
	__atomic_store_n(&atom->v, val, __ATOMIC_RELAXED);
}

static inline uint32_t odp_atomic_load_u32(odp_atomic_u32_t *atom)
{
	return __atomic_load_n(&atom->v, __ATOMIC_RELAXED);
}

static inline void odp_atomic_store_u32(odp_atomic_u32_t *atom,
					uint32_t val)
{
	__atomic_store_n(&atom->v, val, __ATOMIC_RELAXED);
}

static inline uint32_t odp_atomic_fetch_add_u32(odp_atomic_u32_t *atom,
						uint32_t val)
{
	//return __atomic_fetch_add(&atom->v, val, __ATOMIC_RELAXED);
	return odp_atomic_fetch_and_add_u32(&atom->v, val);
}

static inline void odp_atomic_add_u32(odp_atomic_u32_t *atom,
				      uint32_t val)
{
	//(void)__atomic_fetch_add(&atom->v, val, __ATOMIC_RELAXED);
	(void)odp_atomic_add_u32(&atom->v, val);
}

static inline uint32_t odp_atomic_fetch_sub_u32(odp_atomic_u32_t *atom,
						uint32_t val)
{
	//return __atomic_fetch_sub(&atom->v, val, __ATOMIC_RELAXED);
	return odp_atomic_fetch_and_sub_u32(&atom->v, val);
}

static inline void odp_atomic_sub_u32(odp_atomic_u32_t *atom,
				      uint32_t val)
{
	//(void)__atomic_fetch_sub(&atom->v, val, __ATOMIC_RELAXED);
	(void)odp_atomic_sub_u32(&atom->v, val);
}

static inline uint32_t odp_atomic_fetch_inc_u32(odp_atomic_u32_t *atom)
{
	//return __atomic_fetch_add(&atom->v, 1, __ATOMIC_RELAXED);
	return odp_atomic_fetch_and_add_u32(&atom->v, 1);
}

static inline void odp_atomic_inc_u32(odp_atomic_u32_t *atom)
{
	//(void)__atomic_fetch_add(&atom->v, 1, __ATOMIC_RELAXED);
       (void)odp_atomic_add_u32(&atom->v, 1);
}

static inline uint32_t odp_atomic_fetch_dec_u32(odp_atomic_u32_t *atom)
{
	//return __atomic_fetch_sub(&atom->v, 1, __ATOMIC_RELAXED);
	return odp_atomic_fetch_and_sub_u32(&atom->v, 1);
}

static inline void odp_atomic_dec_u32(odp_atomic_u32_t *atom)
{
	//(void)__atomic_fetch_sub(&atom->v, 1, __ATOMIC_RELAXED);
	(void)odp_atomic_sub_u32(&atom->v, 1);
}

static inline void odp_atomic_init_u64(odp_atomic_u64_t *atom, uint64_t val)
{
	atom->v = val;
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	__atomic_clear(&atom->lock, __ATOMIC_RELAXED);
#endif
}

static inline uint64_t odp_atomic_load_u64(odp_atomic_u64_t *atom)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	return ATOMIC_OP(atom, (void)0);
#else
	return __atomic_load_n(&atom->v, __ATOMIC_RELAXED);
#endif
}

static inline void odp_atomic_store_u64(odp_atomic_u64_t *atom,
					uint64_t val)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	(void)ATOMIC_OP(atom, atom->v = val);
#else
	__atomic_store_n(&atom->v, val, __ATOMIC_RELAXED);
#endif
}

static inline uint64_t odp_atomic_fetch_add_u64(odp_atomic_u64_t *atom,
						uint64_t val)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	return ATOMIC_OP(atom, atom->v += val);
#else
	//return __atomic_fetch_add(&atom->v, val, __ATOMIC_RELAXED);
       return odp_atomic_fetch_and_add_u64((unsigned long long *)&atom->v, val);
#endif
}

static inline void odp_atomic_add_u64(odp_atomic_u64_t *atom, uint64_t val)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	(void)ATOMIC_OP(atom, atom->v += val);
#else
	(void)odp_atomic_add_u64((unsigned long long *)&atom->v, val);
	//(void)__atomic_fetch_add(&atom->v, val, __ATOMIC_RELAXED);
#endif
}

static inline uint64_t odp_atomic_fetch_sub_u64(odp_atomic_u64_t *atom,
						uint64_t val)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	return ATOMIC_OP(atom, atom->v -= val);
#else
	//return __atomic_fetch_sub(&atom->v, val, __ATOMIC_RELAXED);
       return odp_atomic_fetch_and_sub_u64((unsigned long long *)&atom->v, val);
#endif
}

static inline void odp_atomic_sub_u64(odp_atomic_u64_t *atom, uint64_t val)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	(void)ATOMIC_OP(atom, atom->v -= val);
#else
	(void)odp_atomic_sub_u64((unsigned long long *)&atom->v, val);
	//(void)__atomic_fetch_sub(&atom->v, val, __ATOMIC_RELAXED);
#endif
}

static inline uint64_t odp_atomic_fetch_inc_u64(odp_atomic_u64_t *atom)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	return ATOMIC_OP(atom, atom->v++);
#else
	return odp_atomic_fetch_and_add_u64((unsigned long long *)&atom->v, 1);
	//return __atomic_fetch_add(&atom->v, 1, __ATOMIC_RELAXED);
#endif
}

static inline void odp_atomic_inc_u64(odp_atomic_u64_t *atom)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	(void)ATOMIC_OP(atom, atom->v++);
#else
	(void)odp_atomic_add_u64((unsigned long long *)&atom->v, 1);
	//(void)__atomic_fetch_add(&atom->v, 1, __ATOMIC_RELAXED);
#endif
}

static inline uint64_t odp_atomic_fetch_dec_u64(odp_atomic_u64_t *atom)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	return ATOMIC_OP(atom, atom->v--);
#else
	return odp_atomic_fetch_and_sub_u64((unsigned long long *)&atom->v, 1);
	//return __atomic_fetch_sub(&atom->v, 1, __ATOMIC_RELAXED);
#endif
}

static inline void odp_atomic_dec_u64(odp_atomic_u64_t *atom)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	(void)ATOMIC_OP(atom, atom->v--);
#else
	(void)odp_atomic_sub_u64((unsigned long long *)&atom->v, 1);
	//(void)__atomic_fetch_sub(&atom->v, 1, __ATOMIC_RELAXED);
#endif
}
#else
static inline void odp_atomic_init_u32(odp_atomic_u32_t *atom, uint32_t val)
{
	__atomic_store_n(&atom->v, val, __ATOMIC_RELAXED);
}

static inline uint32_t odp_atomic_load_u32(odp_atomic_u32_t *atom)
{
	return __atomic_load_n(&atom->v, __ATOMIC_RELAXED);
}

static inline void odp_atomic_store_u32(odp_atomic_u32_t *atom,
					uint32_t val)
{
	__atomic_store_n(&atom->v, val, __ATOMIC_RELAXED);
}

static inline uint32_t odp_atomic_fetch_add_u32(odp_atomic_u32_t *atom,
						uint32_t val)
{
	return __atomic_fetch_add(&atom->v, val, __ATOMIC_RELAXED);
}

static inline void odp_atomic_add_u32(odp_atomic_u32_t *atom,
				      uint32_t val)
{
	(void)__atomic_fetch_add(&atom->v, val, __ATOMIC_RELAXED);
}

static inline uint32_t odp_atomic_fetch_sub_u32(odp_atomic_u32_t *atom,
						uint32_t val)
{
	return __atomic_fetch_sub(&atom->v, val, __ATOMIC_RELAXED);
}

static inline void odp_atomic_sub_u32(odp_atomic_u32_t *atom,
				      uint32_t val)
{
	(void)__atomic_fetch_sub(&atom->v, val, __ATOMIC_RELAXED);
}

static inline uint32_t odp_atomic_fetch_inc_u32(odp_atomic_u32_t *atom)
{
	return __atomic_fetch_add(&atom->v, 1, __ATOMIC_RELAXED);
}

static inline void odp_atomic_inc_u32(odp_atomic_u32_t *atom)
{
	(void)__atomic_fetch_add(&atom->v, 1, __ATOMIC_RELAXED);
}

static inline uint32_t odp_atomic_fetch_dec_u32(odp_atomic_u32_t *atom)
{
	return __atomic_fetch_sub(&atom->v, 1, __ATOMIC_RELAXED);
}

static inline void odp_atomic_dec_u32(odp_atomic_u32_t *atom)
{
	(void)__atomic_fetch_sub(&atom->v, 1, __ATOMIC_RELAXED);
}

static inline void odp_atomic_init_u64(odp_atomic_u64_t *atom, uint64_t val)
{
	atom->v = val;
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	__atomic_clear(&atom->lock, __ATOMIC_RELAXED);
#endif
}

static inline uint64_t odp_atomic_load_u64(odp_atomic_u64_t *atom)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	return ATOMIC_OP(atom, (void)0);
#else
	return __atomic_load_n(&atom->v, __ATOMIC_RELAXED);
#endif
}

static inline void odp_atomic_store_u64(odp_atomic_u64_t *atom,
					uint64_t val)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	(void)ATOMIC_OP(atom, atom->v = val);
#else
	__atomic_store_n(&atom->v, val, __ATOMIC_RELAXED);
#endif
}

static inline uint64_t odp_atomic_fetch_add_u64(odp_atomic_u64_t *atom,
						uint64_t val)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	return ATOMIC_OP(atom, atom->v += val);
#else
	return __atomic_fetch_add(&atom->v, val, __ATOMIC_RELAXED);
#endif
}

static inline void odp_atomic_add_u64(odp_atomic_u64_t *atom, uint64_t val)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	(void)ATOMIC_OP(atom, atom->v += val);
#else
	(void)__atomic_fetch_add(&atom->v, val, __ATOMIC_RELAXED);
#endif
}

static inline uint64_t odp_atomic_fetch_sub_u64(odp_atomic_u64_t *atom,
						uint64_t val)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	return ATOMIC_OP(atom, atom->v -= val);
#else
	return __atomic_fetch_sub(&atom->v, val, __ATOMIC_RELAXED);
#endif
}

static inline void odp_atomic_sub_u64(odp_atomic_u64_t *atom, uint64_t val)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	(void)ATOMIC_OP(atom, atom->v -= val);
#else
	(void)__atomic_fetch_sub(&atom->v, val, __ATOMIC_RELAXED);
#endif
}

static inline uint64_t odp_atomic_fetch_inc_u64(odp_atomic_u64_t *atom)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	return ATOMIC_OP(atom, atom->v++);
#else
	return __atomic_fetch_add(&atom->v, 1, __ATOMIC_RELAXED);
#endif
}

static inline void odp_atomic_inc_u64(odp_atomic_u64_t *atom)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	(void)ATOMIC_OP(atom, atom->v++);
#else
	(void)__atomic_fetch_add(&atom->v, 1, __ATOMIC_RELAXED);
#endif
}

static inline uint64_t odp_atomic_fetch_dec_u64(odp_atomic_u64_t *atom)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	return ATOMIC_OP(atom, atom->v--);
#else
	return __atomic_fetch_sub(&atom->v, 1, __ATOMIC_RELAXED);
#endif
}

static inline void odp_atomic_dec_u64(odp_atomic_u64_t *atom)
{
#if __GCC_ATOMIC_LLONG_LOCK_FREE < 2
	(void)ATOMIC_OP(atom, atom->v--);
#else
	(void)__atomic_fetch_sub(&atom->v, 1, __ATOMIC_RELAXED);
#endif
}

#endif
/**
 * @}
 */

#include <odp/api/atomic.h>

#ifdef __cplusplus
}
#endif

#endif
