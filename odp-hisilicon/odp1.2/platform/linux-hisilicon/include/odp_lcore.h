/*-
 *   BSD LICENSE
 *
 *   Copyright(c) 2010-2014 Huawei Corporation. All rights reserved.
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions
 *   are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided with the
 *       distribution.
 *     * Neither the name of Huawei Corporation nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *   A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *   OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *   SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *   LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *   DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *   THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *   OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#ifndef _ODP_LCORE_H_
#define _ODP_LCORE_H_

/**
 * @file
 *
 * API for lcore and socket manipulation
 *
 */

/* #include <odp_per_lcore.h>
 #include <odp.h> */
#include <odp_common.h>
#include <pthread.h>

#ifdef __cplusplus
extern "C" {
#endif

#define ODP_CPU_AFFINITY_STR_LEN 256

#define LCORE_ID_ANY UINT32_MAX                    /**< Any lcore. */

typedef int (lcore_function_t)(void *);

#if defined(__linux__)
typedef	cpu_set_t odp_cpuset_t;

#elif defined(__FreeBSD__)
#include <pthread_np.h>
typedef cpuset_t odp_cpuset_t;
#endif

/**
 * State of an lcore.
 */
enum odp_lcore_state_t {
	WAIT,     /**< waiting a new command */
	RUNNING,  /**< executing command */
	FINISHED, /**< command executed */
};

/**
 * Structure storing internal configuration (per-lcore)
 */
struct lcore_config {
	unsigned	       detected;                    /**< true if lcore was detected */
	pthread_t	       thread_id;                   /**< pthread identifier */
	int		       pipe_master2slave[2];        /**< communication pipe with master */
	int		       pipe_slave2master[2];        /**< communication pipe with master */
	lcore_function_t      *f;                           /**< function to call */
	void		      *arg;                         /**< argument of function */
	int		       ret;                         /**< return value of function */
	enum odp_lcore_state_t state;                       /**< lcore state */
	unsigned	       socket_id;                   /**< physical socket id for this lcore */
	unsigned	       core_id;                     /**< core number on socket for this lcore */
	int		       core_index;                  /**< relative index, starting from 0 */
	odp_cpuset_t	       cpuset;                      /**< cpu set which the lcore affinity to */
};

/**
 * Internal configuration (per-lcore)
 */
extern struct lcore_config glcore_config[ODP_MAX_LCORE];

extern ODP_DECLARE_PER_LCORE(unsigned, _lcore_id);    /**< Per thread "lcore id". */
extern ODP_DECLARE_PER_LCORE(cpu_set_t, _cpuset);     /**< Per thread "cpuset". */

/**
 * Return the ID of the execution unit we are running on.
 * @return
 *  Logical core ID (in ODP thread) or LCORE_ID_ANY (in non-ODP thread)
 */
static inline unsigned odp_lcore_id(void)
{
	return ODP_PER_LCORE(_lcore_id);
}

/**
 * Get the id of the master lcore
 *
 * @return
 *   the id of the master lcore
 */
static inline unsigned odp_get_master_lcore(void)
{
	return odp_get_configuration()->master_lcore;
}

/**
 * Return the number of execution units (lcores) on the system.
 *
 * @return
 *   the number of execution units (lcores) on the system.
 */
static inline unsigned odp_lcore_count(void)
{
	const struct odp_config *cfg = odp_get_configuration();

	return cfg->lcore_count;
}

/**
 * Return the index of the lcore starting from zero.
 * The order is physical or given by command line (-l option).
 *
 * @param lcore_id
 *   The targeted lcore, or -1 for the current one.
 * @return
 *   The relative index, or -1 if not enabled.
 */
static inline int odp_lcore_index(int lcore_id)
{
	if (lcore_id >= ODP_MAX_LCORE)
		return -1;

	if (lcore_id < 0)
		lcore_id = odp_lcore_id();

	return glcore_config[lcore_id].core_index;
}

/**
 * Return the ID of the physical socket of the logical core we are
 * running on.
 * @return
 *   the ID of current lcoreid's physical socket
 */
unsigned odp_socket_id(void);

int odp_cpu_init(void);

/**
 * Get the ID of the physical socket of the specified lcore
 *
 * @param lcore_id
 *   the targeted lcore, which MUST be between 0 and ODP_MAX_LCORE-1.
 * @return
 *   the ID of lcoreid's physical socket
 */
static inline unsigned odp_lcore_to_socket_id(unsigned lcore_id)
{
	return glcore_config[lcore_id].socket_id;
}

/**
 * Test if an lcore is enabled.
 *
 * @param lcore_id
 *   The identifier of the lcore, which MUST be between 0 and
 *   ODP_MAX_LCORE-1.
 * @return
 *   True if the given lcore is enabled; false otherwise.
 */
static inline int odp_lcore_is_enabled(unsigned lcore_id)
{
	struct odp_config *cfg = odp_get_configuration();

	if (lcore_id >= ODP_MAX_LCORE)
		return 0;

	return (cfg->lcore_role[lcore_id] != ROLE_OFF);
}

/**
 * Get the next enabled lcore ID.
 *
 * @param i
 *   The current lcore (reference).
 * @param skip_master
 *   If true, do not return the ID of the master lcore.
 * @param wrap
 *   If true, go back to 0 when ODP_MAX_LCORE is reached; otherwise,
 *   return ODP_MAX_LCORE.
 * @return
 *   The next lcore_id or ODP_MAX_LCORE if not found.
 */
static inline unsigned odp_get_next_lcore(unsigned i, int skip_master, int wrap)
{
	i++;
	if (wrap)
		i %= ODP_MAX_LCORE;

	while (i < ODP_MAX_LCORE) {
		if (!odp_lcore_is_enabled(i) ||
		    (skip_master && (i == odp_get_master_lcore()))) {
			i++;
			if (wrap)
				i %= ODP_MAX_LCORE;

			continue;
		}

		break;
	}

	return i;
}

/**
 * Macro to browse all running lcores.
 */
#define ODP_LCORE_FOREACH(i)                       \
	for (i = odp_get_next_lcore(-1, 0, 0);             \
	     i < ODP_MAX_LCORE;                       \
	     i = odp_get_next_lcore(i, 0, 0))

/**
 * Macro to browse all running lcores except the master lcore.
 */
#define ODP_LCORE_FOREACH_SLAVE(i)                 \
	for (i = odp_get_next_lcore(-1, 1, 0);             \
	     i < ODP_MAX_LCORE;                       \
	     i = odp_get_next_lcore(i, 1, 0))

/**
 * Set core affinity of the current thread.
 * Support both ODP and non-ODP thread and update TLS.
 *
 * @param cpusetp
 *   Point to cpu_set_t for setting current thread affinity.
 * @return
 *   On success, return 0; otherwise return -1;
 */
int odp_thread_set_affinity(cpu_set_t *cpusetp);

/**
 * Get core affinity of the current thread.
 *
 * @param cpusetp
 *   Point to cpu_set_t for getting current thread cpu affinity.
 *   It presumes input is not NULL, otherwise it causes panic.
 *
 */
void odp_thread_get_affinity(cpu_set_t *cpusetp);

#ifdef __cplusplus
}
#endif
#endif /* _ODP_LCORE_H_ */
