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
#define _GNU_SOURCE
#include <sched.h>
#include <unistd.h>
#include <limits.h>
#include <string.h>
#include <dirent.h>
#include <pthread.h>

#include <sys/queue.h>
#include <sys/syscall.h>

/* #include <odp_log.h> */
#include <odp_base.h>
#include <odp/atomic.h>
#include <odp/config.h>

#include <odp_common.h>
#include <odp_lcore.h>
#include <odp_memory.h>

/* #include <odp_string_fns.h> */
#include <odp/sync.h>

#include "odp_private.h"
#include "odp_filesystem.h"

/* #include "odp_thread.h" */
ODP_DEFINE_PER_LCORE(unsigned, _lcore_id)  = LCORE_ID_ANY;
ODP_DEFINE_PER_LCORE(unsigned, _socket_id) = (unsigned)SOCKET_ID_ANY;
ODP_DEFINE_PER_LCORE(cpu_set_t, _cpuset);

/* ODP_DECLARE_PER_LCORE(unsigned,_socket_id); */
#define SYS_CPU_DIR   "/sys/devices/system/cpu/cpu%u"
#define CORE_ID_FILE  "topology/core_id"
#define PHYS_PKG_FILE "topology/physical_package_id"

/* Check if a cpu is present by the presence of the cpu information for it */
static int odp_cpu_detected(unsigned lcore_id)
{
	char path[ODP_PATH_MAX];
	int  len = snprintf(path, sizeof(path), SYS_CPU_DIR
			    "/" CORE_ID_FILE, lcore_id);

	if ((len <= 0) || ((unsigned)len >= sizeof(path)))
		return 0;

	if (access(path, F_OK) != 0)
		return 0;

	return 1;
}

/* Get CPU socket id (NUMA node) by reading directory
 * /sys/devices/system/cpu/cpuX looking for symlink "nodeY"
 * which gives the NUMA topology information.
 * Note: physical package id != NUMA node, but we use it as a
 * fallback for kernels which don't create a nodeY link
 */
unsigned odp_cpu_socket_id(unsigned lcore_id)
{
	const char node_prefix[] = "node";
	const size_t prefix_len	 = sizeof(node_prefix) - 1;
	char path[ODP_PATH_MAX];
	DIR *d = NULL;
	unsigned long id = 0;
	struct dirent *e;
	char *endptr = NULL;

	int   len = snprintf(path, sizeof(path),
			     SYS_CPU_DIR, lcore_id);

	if ((len <= 0) || ((unsigned)len >= sizeof(path)))
		goto err;

	d = opendir(path);
	if (!d)
		goto err;

	while ((e = readdir(d)) != NULL)
		if (strncmp(e->d_name, node_prefix, prefix_len) == 0) {
			id = strtoul(e->d_name + prefix_len, &endptr, 0);
			break;
		}

	if ((!endptr) || (*endptr != '\0') || (endptr == e->d_name + prefix_len)) {
		/* PRINT("Cannot read numa node link for lcore %u - using physical package id instead\n", lcore_id); */
		len = snprintf(path, sizeof(path), SYS_CPU_DIR "/%s", lcore_id, PHYS_PKG_FILE);
		if ((len <= 0) || ((unsigned)len >= sizeof(path)))
			goto err;

		if (odp_parse_sysfs_value(path, &id) != 0)
			goto err;
	}

	closedir(d);
	return (unsigned)id;

err:
	if (d)
		closedir(d);

	return 0;
}

/* Get the cpu core id value from the /sys/.../cpuX core_id value */
static unsigned odp_cpu_core_id(unsigned lcore_id)
{
	char path[ODP_PATH_MAX];
	unsigned long id;

	int len = snprintf(path, sizeof(path), SYS_CPU_DIR "/%s", lcore_id, CORE_ID_FILE);

	if ((len <= 0) || ((unsigned)len >= sizeof(path)))
		goto err;

	if (odp_parse_sysfs_value(path, &id) != 0)
		goto err;

	return (unsigned)id;

err:
	PRINT("Error reading core id value from %s\n"
	      "for lcore %u - assuming core 0", SYS_CPU_DIR, lcore_id);
	return 0;
}

/*****************************************************************************
   Function     : odp_cpu_init
   Description  : cpu information init
   Input        : None
   Output       : None
   Return       : 0:successed;other,failed
   Create By    : x00180405
   Modification :
   1.created: 2015/7/2
   Restriction  :
*****************************************************************************/
int odp_cpu_init(void)
{
	/* pointer to global configuration */
	struct odp_config *config = odp_get_configuration();
	unsigned lcore_id;
	unsigned count = 0;

	/*
	 * Parse the maximum set of logical cores, detect the subset of running
	 * ones and enable them by default.
	 */
	for (lcore_id = 0; lcore_id < ODP_MAX_LCORE; lcore_id++) {
		/* init cpuset for per lcore config */
		CPU_ZERO(&glcore_config[lcore_id].cpuset);

		/* in 1:1 mapping, record related cpu detected state */
		glcore_config[lcore_id].detected = odp_cpu_detected(lcore_id);
		if (glcore_config[lcore_id].detected == 0) {
			config->lcore_role[lcore_id] = ROLE_OFF;
			continue;
		}

		/* By default, lcore 1:1 map to cpu id */
		CPU_SET(lcore_id, &glcore_config[lcore_id].cpuset);

		/* By default, each detected core is enabled */
		config->lcore_role[lcore_id] = ROLE_ODP;
		glcore_config[lcore_id].core_id = odp_cpu_core_id(lcore_id);
		glcore_config[lcore_id].socket_id = odp_cpu_socket_id(lcore_id);

#ifdef ODP_ALLOW_INV_SOCKET_ID
		if (glcore_config[lcore_id].socket_id >= ODP_MAX_NUMA_NODES)
			glcore_config[lcore_id].socket_id = 0;
#endif
		count++;
	}

	/* Set the count of enabled logical cores of the HODP configuration */
	config->lcore_count = count;

	return 0;
}

unsigned odp_socket_id(void)
{
	return ODP_PER_LCORE(_socket_id);
}

int odp_cpuset_socket_id(cpu_set_t *cpusetp)
{
	unsigned cpu  = 0;
	int socket_id = SOCKET_ID_ANY;
	int sid;

	if (!cpusetp)
		return SOCKET_ID_ANY;

	do {
		if (!CPU_ISSET(cpu, cpusetp))
			continue;

		if (socket_id == SOCKET_ID_ANY)
			socket_id = odp_cpu_socket_id(cpu);

		sid = odp_cpu_socket_id(cpu);
		if (socket_id != sid) {
			socket_id = SOCKET_ID_ANY;
			break;
		}
	} while (++cpu < ODP_MAX_LCORE);

	return socket_id;
}

int odp_thread_set_affinity(cpu_set_t *cpusetp)
{
	int s;
	unsigned lcore_id;
	pthread_t tid;

	tid = pthread_self();

	s = pthread_setaffinity_np(tid, sizeof(cpu_set_t), cpusetp);
	if (s != 0) {
		PRINT("pthread_setaffinity_np failed\n");
		return -1;
	}

	/* store socket_id in TLS for quick access */
	ODP_PER_LCORE(_socket_id) =
		odp_cpuset_socket_id(cpusetp);

	/* store cpuset in TLS for quick access */
	memmove(&ODP_PER_LCORE(_cpuset), cpusetp,
		sizeof(cpu_set_t));

	lcore_id = odp_lcore_id();
	if (lcore_id != (unsigned)LCORE_ID_ANY) {
		/* HODP thread will update lcore_config */
		glcore_config[lcore_id].socket_id = ODP_PER_LCORE(_socket_id);
		memmove(&glcore_config[lcore_id].cpuset, cpusetp,
			sizeof(cpu_set_t));
	}

	return 0;
}

void odp_thread_get_affinity(cpu_set_t *cpusetp)
{
	/* assert(cpusetp); */
	memmove(cpusetp, &ODP_PER_LCORE(_cpuset),
		sizeof(cpu_set_t));
}

int odp_thread_dump_affinity(char *str, unsigned size)
{
	cpu_set_t cpuset;
	unsigned  cpu;
	int ret;
	unsigned int out = 0;

	odp_thread_get_affinity(&cpuset);

	for (cpu = 0; cpu < ODP_MAX_LCORE; cpu++) {
		if (!CPU_ISSET(cpu, &cpuset))
			continue;

		ret = snprintf(str + out,
			       size - out, "%u,", cpu);
		if ((ret < 0) || ((unsigned)ret >= size - out)) {
			/* string will be truncated */
			ret = -1;
			goto exit;
		}

		out += ret;
	}

	ret = 0;
exit:

	/* remove the last separator */
	if (out > 0)
		str[out - 1] = '\0';

	return ret;
}

int odp_remote_launch(int (*f)(void *), void *arg, unsigned slave_id)
{
	int n;
	char c	= 0;
	int m2s = glcore_config[slave_id].pipe_master2slave[1];
	int s2m = glcore_config[slave_id].pipe_slave2master[0];

	if (glcore_config[slave_id].state != WAIT)
		return -EBUSY;

	glcore_config[slave_id].f = f;
	glcore_config[slave_id].arg = arg;

	/* send message */
	n = 0;
	while (n == 0 || (n < 0 && errno == EINTR))
		n = write(m2s, &c, 1);

	if (n < 0)
		PRINT("cannot write on configuration pipe\n");

	/* wait ack */
	do
		n = read(s2m, &c, 1);
	while (n < 0 && errno == EINTR);

	if (n <= 0)
		PRINT("cannot read on configuration pipe\n");

	return 0;
}

/* set affinity for current HODP thread */
static int odp_hisi_thread_set_affinity(void)
{
	unsigned lcore_id = odp_lcore_id();

	/* acquire system unique id  */
	odp_gettid();

	/* update HODP thread core affinity */
	return odp_thread_set_affinity(&glcore_config[lcore_id].cpuset);
}

void odp_thread_init_master(unsigned lcore_id)
{
	/* set the lcore ID in per-lcore memory area */
	ODP_PER_LCORE(_lcore_id) = lcore_id;

	/* set CPU affinity */
	if (odp_hisi_thread_set_affinity() < 0)
		PRINT("cannot set affinity\n");
}

/* main loop of threads */
__attribute__((noreturn)) void *odp_thread_loop(__attribute__((unused)) void *arg)
{
	char c;
	int  n, ret;
	unsigned lcore_id;
	pthread_t thread_id;
	int m2s, s2m;
	char cpuset[ODP_CPU_AFFINITY_STR_LEN];

	thread_id = pthread_self();

	/* retrieve our lcore_id from the configuration structure */
	ODP_LCORE_FOREACH_SLAVE(lcore_id)
	{
		if (thread_id == glcore_config[lcore_id].thread_id)
			break;
	}
	if (lcore_id == ODP_MAX_LCORE)
		PRINT("cannot retrieve lcore id\n");

	m2s = glcore_config[lcore_id].pipe_master2slave[0];
	s2m = glcore_config[lcore_id].pipe_slave2master[1];

	/* set the lcore ID in per-lcore memory area */
	ODP_PER_LCORE(_lcore_id) = lcore_id;

	/* set CPU affinity */
	if (odp_hisi_thread_set_affinity() < 0)
		PRINT("cannot set affinity\n");

	ret = odp_thread_dump_affinity(cpuset, ODP_CPU_AFFINITY_STR_LEN);

	PRINT("lcore %u is ready (tid=%x;cpuset=[%s%s])\n",
	      lcore_id, (int)thread_id, cpuset, ret == 0 ? "" : "...");

	/* read on our pipe to get commands */
	while (1) {
		void *fct_arg;

		/* wait command */
		do
			n = read(m2s, &c, 1);
		while (n < 0 && errno == EINTR);

		if (n <= 0)
			PRINT("cannot read on configuration pipe\n");

		glcore_config[lcore_id].state = RUNNING;

		/* send ack */
		n = 0;
		while (n == 0 || (n < 0 && errno == EINTR))
			n = write(s2m, &c, 1);

		if (n < 0)
			PRINT("cannot write on configuration pipe\n");

		if (!glcore_config[lcore_id].f)
			PRINT("NULL function pointer\n");

		/* call the function and store the return value */
		fct_arg = glcore_config[lcore_id].arg;
		ret = glcore_config[lcore_id].f(fct_arg);
		glcore_config[lcore_id].ret = ret;
		odp_sync_stores();
		glcore_config[lcore_id].state = FINISHED;
	}

	/* never reached */
	/* pthread_exit(NULL); */
	/* return NULL; */
}

/* require calling thread tid by gettid() */
int odp_sys_gettid(void)
{
	return (int)syscall(SYS_gettid);
}
