#ifndef ODP_PLAT_HISI_ATOMIC_H_
#define ODP_PLAT_HISI_ATOMIC_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <stdint.h>

#include <odp/plat/atomic_types.h>

static inline int odp_atomic_cmpset_u32(odp_atomic_u32_t *dst, uint32_t exp, uint32_t src)
{
	uint32_t tmp;
	int oldval;

#if (!defined(__arm32__))
	asm volatile ("// atomic_cmpxchg\n"
		      "1: ldaxr   %w1, %2\n"
		      "   cmp %w1, %w3\n"
		      "   b.ne    2f\n"
		      "   stlxr   %w0, %w4, %2\n"
		      "   cbnz    %w0, 1b\n"
		      "2:"
		      : "=&r" (tmp), "=&r" (oldval), "+Q" (dst->v)
		      : "Ir" (exp), "r" (src)
		      : "cc", "memory");
#else

	/*TO DO: 当前odp版本中，实际上没有用到该函数, 相关实现暂且为空. 2015.10.12*/
	tmp  = 0;
	tmp += 1;
	oldval = *(int *)dst;
#endif

	return (int)(oldval == exp);
}

static inline int odp_atomic_cmpset_u64(odp_atomic_u64_t *dst, uint64_t exp, uint64_t src)
{
	int oldval;
	uint64_t res;

#if (!defined(__arm32__))
	asm volatile ("// atomic64_cmpxchg\n"
		      "1: ldaxr   %1, %2\n"
		      "   cmp %1, %3\n"
		      "   b.ne    2f\n"
		      "   stlxr   %w0, %4, %2\n"
		      "   cbnz    %w0, 1b\n"
		      "2:"
		      : "=&r" (res), "=&r" (oldval), "+Q" (dst->v)
		      : "Ir" (exp), "r" (src)
		      : "cc", "memory");
#else

	/*TO DO: 当前odp版本中，实际上没有用到该函数, 相关实现暂且为空. 2015.10.12*/
	res  = 0;
	res += 1;
	oldval = *(int *)dst;
#endif

	return (int)(oldval == exp);
}

#ifdef __cplusplus
}
#endif
#endif
