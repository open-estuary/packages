/* Copyright (c) 2014, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */

/**
 * @file
 *
 * @example odp_l2fwd.c  ODP basic forwarding application
 */

/** enable strtok */
#define _POSIX_C_SOURCE 200112L

#include <stdlib.h>
#include <string.h>
#include <getopt.h>
#include <unistd.h>
#include <errno.h>

#include <example_debug.h>

#include <odp.h>
#include <odp/helper/linux.h>
#include <odp/helper/eth.h>
#include <odp/helper/ip.h>
#include <odp/hints.h>

#define ODP_BUFFER_POOL_SCHEME_0 0 /* xzb */
#define ODP_BUFFER_POOL_SCHEME_1 1 /* lzz */

#define ODP_BUFFER_POOL_SCHEME ODP_BUFFER_POOL_SCHEME_1

#if (ODP_BUFFER_POOL_SCHEME == ODP_BUFFER_POOL_SCHEME_1)
#include <odp_pool_internal.h>
#endif

/** @def MAX_WORKERS
 * @brief Maximum number of worker threads
 */
#define MAX_WORKERS 32

/** @def SHM_PKT_POOL_SIZE
 * @brief Size of the shared memory block
 */
#define SHM_PKT_POOL_SIZE (512 * 2048 * 4)

/** @def SHM_PKT_POOL_BUF_SIZE
 * @brief Buffer size of the packet pool buffer
 */
#define SHM_PKT_POOL_BUF_SIZE 1856 * 4

/** @def MAX_PKT_BURST
 * @brief Maximum number of packet bursts
 */
#define MAX_PKT_BURST 16

/** @def APPL_MODE_PKT_BURST
 * @brief The application will handle pakcets in bursts
 */
#define APPL_MODE_PKT_BURST 0

/** @def APPL_MODE_PKT_QUEUE
 * @brief The application will handle packets in queues
 */
#define APPL_MODE_PKT_QUEUE 1

/** @def PRINT_APPL_MODE(x)
 * @brief Macro to print the current status of how the application handles
 * packets.
 */
#define PRINT_APPL_MODE(x) printf("%s(%i)\n", # x, (x))

/** Get rid of path in filename - only for unix-type paths using '/' */
#define NO_PATH(file_name) (strrchr((file_name), '/') ? \
			    strrchr((file_name), '/') + 1 : (file_name))

/**
 * Parsed command line application arguments
 */
typedef struct {
	int    cpu_count;
	int    if_count; /**< Number of interfaces to be used */
	char **if_names; /**< Array of pointers to interface names */
	int    mode;     /**< Packet IO mode */
} appl_args_t;

/**
 * Thread specific arguments
 */
typedef struct {
	int src_idx;        /**< Source interface identifier */
} thread_args_t;

/**
 * Grouping of all global data
 */
typedef struct {
	/** Application (parsed) arguments */
	appl_args_t appl;

	/** Thread specific arguments */
	thread_args_t thread[MAX_WORKERS];

	/** Table of pktio handles */
	odp_pktio_t pktios[ODP_CONFIG_PKTIO_ENTRIES];
} args_t;

/** Global pointer to args */
static args_t *gbl_args;

/* helper funcs */
static inline odp_queue_t lookup_dest_q(odp_packet_t pkt);
static int drop_err_pkts(odp_packet_t pkt_tbl[], unsigned len);
static void parse_args(int argc, char *argv[], appl_args_t *appl_args);
static void print_info(char *progname, appl_args_t *appl_args);
static void usage(char *progname);

#if 0

/**
 * Packet IO worker thread using ODP queues
 *
 * @param arg  thread arguments of type 'thread_args_t *'
 */
static void *pktio_queue_thread(void *arg)
{
	int thr;
	odp_queue_t outq_def;
	odp_packet_t pkt;
	odp_event_t ev;
	unsigned long pkt_cnt = 0;
	unsigned long err_cnt = 0;

	(void)arg;

	thr = odp_thread_id();

	printf("[%02i] QUEUE mode\n", thr);

	/* Loop packets */
	for (;; ) {
		/* Use schedule to get buf from any input queue */
		ev  = odp_schedule(NULL, ODP_SCHED_WAIT);
		pkt = odp_packet_from_event(ev);

		/* Drop packets with errors */
		if (odp_unlikely(drop_err_pkts(&pkt, 1) == 0)) {
			EXAMPLE_ERR("Drop frame - err_cnt:%lu\n", ++err_cnt);
			continue;
		}

		outq_def = lookup_dest_q(pkt);

		/* Enqueue the packet for output */
		odp_queue_enq(outq_def, ev);

		/* Print packet counts every once in a while */
		if (odp_unlikely(pkt_cnt++ % 100000 == 0)) {
			printf("  [%02i] pkt_cnt:%lu\n", thr, pkt_cnt);
			fflush(NULL);
		}
	}

	/* unreachable */
	return NULL;
}
#endif

/**
 * Lookup the destination pktio for a given packet
 */
static inline odp_queue_t lookup_dest_q(odp_packet_t pkt)
{
	int i, src_idx, dst_idx;
	odp_pktio_t pktio_src, pktio_dst;

	pktio_src = odp_packet_input(pkt);

	for (src_idx = -1, i = 0; gbl_args->pktios[i] != ODP_PKTIO_INVALID; ++i)
		if (gbl_args->pktios[i] == pktio_src)
			src_idx = i;

	if (src_idx == -1)
		EXAMPLE_ABORT("Failed to determine pktio input\n");

	dst_idx = (src_idx % 2 == 0) ? src_idx + 1 : src_idx - 1;
	pktio_dst = gbl_args->pktios[dst_idx];

	return odp_pktio_outq_getdef(pktio_dst);
}

/**
 * Packet IO worker thread using bursts from/to IO resources
 *
 * @param arg  thread arguments of type 'thread_args_t *'
 */
static void *pktio_ifburst_thread(void *arg)
{
	int thr;
	thread_args_t *thr_args;
	int pkts, pkts_ok;
	odp_packet_t pkt_tbl[MAX_PKT_BURST];
	unsigned long pkt_cnt = 0;
	unsigned long err_cnt = 0;
	unsigned long tmp = 0;
	int src_idx, dst_idx;
	odp_pktio_t   pktio_src, pktio_dst;

	thr = odp_thread_id();
	thr_args = arg;

	src_idx = thr_args->src_idx;

	/* dst_idx = (src_idx % 2 == 0) ? src_idx+1 : src_idx-1; */
	dst_idx = src_idx;
	pktio_src = gbl_args->pktios[src_idx];
	pktio_dst = gbl_args->pktios[dst_idx];

	printf("[%02i] srcif:%s dstif:%s spktio:%02d dpktio:%02d BURST mode\n",
	       thr,
	       gbl_args->appl.if_names[src_idx],
	       gbl_args->appl.if_names[dst_idx],
	       odp_pktio_to_u64(pktio_src), odp_pktio_to_u64(pktio_dst));

	/* Loop packets */
	for (;; ) {
		pkts = odp_pktio_recv(pktio_src, pkt_tbl, MAX_PKT_BURST);
		if (pkts <= 0) {
			usleep(10);
			continue;
		}

		/* printf("  [%02i] pkt_cnt:%lu\n", thr, pkts); */
		{
			int i, j;
			uint32_t len;
			char *addr;

			for (i = 0; i < pkts; i++) {
#if (ODP_BUFFER_POOL_SCHEME == ODP_BUFFER_POOL_SCHEME_0)
				addr = odp_packet_data(pkt_tbl[i]);
				len  = odp_packet_len(pkt_tbl[i]); /* 报文的长度 */
#else
				len  = odp_packet_len(pkt_tbl[i]);
				addr = (char *)pkt_tbl[i];
#endif

				/* Print packets */
#if 0
				printf("pkts: addr:%p, len=%d\r\n", addr, len);
				for (j = 0; j < len; j++) {
					printf("%02x ", addr[j]);
					if ((j + 1) % 16 == 0)
						printf("\r\n");
				}
				printf("\r\n");
#endif

				/* if ICMP req packet, reply ICMP reply packet.*/
				if (0x8 == addr[12] && 0x0 == addr[13] && 0x1 == addr[23] && len >= 35) {/* ICMP protocal */
					unsigned char tmp[16];

					/*swtich SMAC DMAC*/
					memcpy(tmp, addr, 6);
					memcpy(addr, addr + 6, 6);
					memcpy(addr + 6, tmp, 6);

					/*swtich SIP DIP*/
					memcpy(tmp, addr + 26, 4);
					memcpy(addr + 26, addr + 30, 4);
					memcpy(addr + 30, tmp, 4);

					addr[34] = 0; /* ICMP echo */
				}
			}

			pkts_ok = odp_pktio_send(pktio_dst, pkt_tbl, pkts);
			if (odp_unlikely(pkts_ok != pkts)) {
				err_cnt += pkts - pkts_ok;
				EXAMPLE_ERR("Dropped frames:%u - err_cnt:%lu\n",
					    pkts - pkts_ok, err_cnt);
			}
		}
	}

	/* unreachable */
	return NULL;
}

/**
 * Create a pktio handle, optionally associating a default input queue.
 *
 * @param dev Name of device to open
 * @param pool Pool to associate with device for packet RX/TX
 * @param mode Packet processing mode for this device (BURST or QUEUE)
 *
 * @return The handle of the created pktio object.
 * @retval ODP_PKTIO_INVALID if the create fails.
 */
static odp_pktio_t create_pktio(const char *dev, odp_pool_t pool,
				int mode)
{
	char inq_name[ODP_QUEUE_NAME_LEN];

	/* odp_queue_param_t qparam;
	   odp_queue_t inq_def; */
	odp_pktio_t pktio;

	/* int ret; */

	pktio = odp_pktio_open(dev, pool);
	if (pktio == ODP_PKTIO_INVALID) {
		EXAMPLE_ERR("Error: failed to open %s\n", dev);
		return ODP_PKTIO_INVALID;
	}

	printf("created pktio 0x%llx (%s)\n",
	       odp_pktio_to_u64(pktio), dev);

	/* no further setup needed for burst mode */
	if (mode == APPL_MODE_PKT_BURST)
		return pktio;

#if 0
	qparam.sched.prio  = ODP_SCHED_PRIO_DEFAULT;
	qparam.sched.sync  = ODP_SCHED_SYNC_ATOMIC;
	qparam.sched.group = ODP_SCHED_GROUP_DEFAULT;
#endif
	snprintf(inq_name, sizeof(inq_name), "0x%llx-pktio_inq_def",
		 odp_pktio_to_u64(pktio));
	inq_name[ODP_QUEUE_NAME_LEN - 1] = '\0';

#if 0
	inq_def = odp_queue_create(inq_name, ODP_QUEUE_TYPE_PKTIN, &qparam);
	if (inq_def == ODP_QUEUE_INVALID) {
		EXAMPLE_ERR("Error: pktio queue creation failed\n");
		return ODP_PKTIO_INVALID;
	}

	ret = odp_pktio_inq_setdef(pktio, inq_def);
	if (ret != 0) {
		EXAMPLE_ERR("Error: default input-Q setup\n");
		return ODP_PKTIO_INVALID;
	}
#endif
	return pktio;
}

/**
 * ODP L2 forwarding main function
 */
int main(int argc, char *argv[])
{
	odph_linux_pthread_t thread_tbl[MAX_WORKERS];
	odp_pool_t pool[ODP_CONFIG_PKTIO_ENTRIES];
	int i;
	int cpu;
	int num_workers;
	odp_shm_t shm;
	odp_cpumask_t cpumask;
	char cpumaskstr[ODP_CPUMASK_STR_SIZE];
	odp_pool_param_t params;
	char *poolname[2] = {
		"packet pool 1", "packet pool 2"
	};

	struct odp_pktio_info pktio_info[128];
	uint8_t num;

	/* Init ODP before calling anything else */
	if (odp_init_global(NULL, NULL)) {
		EXAMPLE_ERR("Error: ODP global init failed.\n");
		exit(EXIT_FAILURE);
	}

	/* Init this thread */
	if (odp_init_local(ODP_THREAD_CONTROL)) {
		EXAMPLE_ERR("Error: ODP local init failed.\n");
		exit(EXIT_FAILURE);
	}

#if 0
	odp_pktio_dev_get(pktio_info, &num);
	for (i = 0; i < num; i++) {
		printf("pktio_info[%d].info.soc_info.if_idx :%d \n", i, pktio_info[i].info.soc_info.if_idx);
		printf("pktio_info[%d].info.name :%s \n", i, pktio_info[i].name);
	}
#endif

	/* Reserve memory for args from shared mem */
	shm = odp_shm_reserve("shm_args", sizeof(args_t),
			      ODP_CACHE_LINE_SIZE, 0);
	gbl_args = odp_shm_addr(shm);

	if (gbl_args == NULL) {
		EXAMPLE_ERR("Error: shared mem alloc failed.\n");
		exit(EXIT_FAILURE);
	}

	memset(gbl_args, 0, sizeof(*gbl_args));

	/* Parse and store the application arguments */
	parse_args(argc, argv, &gbl_args->appl);

	/* Print both system and application information */
	print_info(NO_PATH(argv[0]), &gbl_args->appl);

	/* Default to system CPU count unless user specified */
	num_workers = MAX_WORKERS;
	if (gbl_args->appl.cpu_count)
		num_workers = gbl_args->appl.cpu_count;

	/*
	 * By default CPU #0 runs Linux kernel background tasks.
	 * Start mapping thread from CPU #1
	 */

	num_workers = odp_cpumask_def_worker(&cpumask, num_workers);
	(void)odp_cpumask_to_str(&cpumask, cpumaskstr, sizeof(cpumaskstr));

	printf("num worker threads: %i\n", num_workers);
	printf("first CPU:          %i\n", odp_cpumask_first(&cpumask));
	printf("cpu mask:           %s\n", cpumaskstr);

#if 0
	if (num_workers < gbl_args->appl.if_count) {
		EXAMPLE_ERR("Error: CPU count %d less than interface count\n",
			    num_workers);
		exit(EXIT_FAILURE);
	}
#endif

#if 0
	if (gbl_args->appl.if_count % 2 != 0) {
		EXAMPLE_ERR("Error: interface count %d is odd in fwd appl.\n",
			    gbl_args->appl.if_count);
		exit(EXIT_FAILURE);
	}
#endif

	/* Create packet pool */
	memset(&params, 0, sizeof(params));
	params.pkt.seg_len = SHM_PKT_POOL_BUF_SIZE;
	params.pkt.len = SHM_PKT_POOL_BUF_SIZE;
	params.pkt.num = SHM_PKT_POOL_SIZE / SHM_PKT_POOL_BUF_SIZE;
	params.type = ODP_POOL_PACKET;

	for (i = 0; i < gbl_args->appl.if_count; ++i) {
		pool[i] = odp_pool_create(poolname[i], &params);
		if (pool[i] == ODP_POOL_INVALID) {
			EXAMPLE_ERR("Error: packet pool create failed.\n");
			exit(EXIT_FAILURE);
		}

		/*  odp_pool_print(pool[i]); */
		gbl_args->pktios[i] = create_pktio(gbl_args->appl.if_names[i],
						   pool[i], gbl_args->appl.mode);
		if (gbl_args->pktios[i] == ODP_PKTIO_INVALID)
			exit(EXIT_FAILURE);
	}

	gbl_args->pktios[i] = ODP_PKTIO_INVALID;

#if 0
	{
		int ret;
		struct odp_eth_link eth_link = {0};

		ret = odp_pktio_mtu_set(gbl_args->pktios[0], 1500);
		if (ret)
			printf("odp_pktio_mtu_set failed! ret = %d\n", ret);

		ret = odp_pktio_link_get(gbl_args->pktios[0], &eth_link);
		if (ret)
			printf("odp_pktio_link_get failed! ret = %d\n", ret);
		else
			printf("odp_pktio_link_get success! link_speed = %d,link_duplex=%d,link_status=%d\n",
			       eth_link.link_speed, eth_link.link_duplex, eth_link.link_status);
	}
#endif

	memset(thread_tbl, 0, sizeof(thread_tbl));

	/* Create worker threads */
	cpu = odp_cpumask_first(&cpumask);
	for (i = 0; i < num_workers; ++i) {
		odp_cpumask_t thd_mask;
		void *(*thr_run_func)(void *);

		/*use APPL_MODE_PKT_BURST mode*/
		thr_run_func = pktio_ifburst_thread;

		gbl_args->thread[i].src_idx = i % gbl_args->appl.if_count;

		odp_cpumask_zero(&thd_mask);
		odp_cpumask_set(&thd_mask, cpu);
		odph_linux_pthread_create(&thread_tbl[i], &thd_mask,
					  thr_run_func,
					  &gbl_args->thread[i]);
		cpu = odp_cpumask_next(&cpumask, cpu);
	}

	/* Master thread waits for other threads to exit */
	odph_linux_pthread_join(thread_tbl, num_workers);

	printf("Exit\n\n");

	return 0;
}

/**
 * Drop packets which input parsing marked as containing errors.
 *
 * Frees packets with error and modifies pkt_tbl[] to only contain packets with
 * no detected errors.
 *
 * @param pkt_tbl  Array of packet
 * @param len      Length of pkt_tbl[]
 *
 * @return Number of packets with no detected error
 */
static int drop_err_pkts(odp_packet_t pkt_tbl[], unsigned len)
{
	odp_packet_t pkt;
	unsigned pkt_cnt = len;
	unsigned i, j;

	for (i = 0, j = 0; i < len; ++i) {
		pkt = pkt_tbl[i];

		if (odp_unlikely(odp_packet_has_error(pkt))) {
			printf("drop_err_pkts [i:%d j:%d]\n", i, j);
			printf("drop_err_pkts [len:%d ]\n", len);

			odp_packet_free(pkt); /* Drop */
			pkt_cnt--;
		} else if (odp_unlikely(i != j++)) {
			pkt_tbl[j - 1] = pkt;
		}
	}

	return pkt_cnt;
}

/**
 * Parse and store the command line arguments
 *
 * @param argc       argument count
 * @param argv[]     argument vector
 * @param appl_args  Store application arguments here
 */
static void parse_args(int argc, char *argv[], appl_args_t *appl_args)
{
	int opt;
	int long_index;
	char *names, *str, *token, *save;
	size_t len;
	int   i;
	static struct option longopts[] = {
		{"count",     required_argument, NULL, 'c'},
		{"interface", required_argument, NULL, 'i'}, /* return 'i' */
		{"mode",      required_argument, NULL, 'm'}, /* return 'm' */
		{"help",      no_argument,	 NULL, 'h'}, /* return 'h' */
		{NULL,	      0,		 NULL, 0  }
	};

	appl_args->mode = -1; /* Invalid, must be changed by parsing */

	while (1) {
		opt = getopt_long(argc, argv, "+c:i:m:h",
				  longopts, &long_index);

		if (opt == -1)
			break; /* No more options */

		switch (opt) {
		case 'c':
			appl_args->cpu_count = atoi(optarg);
			break;

		/* parse packet-io interface names */
		case 'i':
			len = strlen(optarg);
			if (len == 0) {
				usage(argv[0]);
				exit(EXIT_FAILURE);
			}

			len += 1; /* add room for '\0' */

			names = malloc(len);
			if (names == NULL) {
				usage(argv[0]);
				exit(EXIT_FAILURE);
			}

			/* count the number of tokens separated by ',' */
			strcpy(names, optarg);
			for (str = names, i = 0;; str = NULL, i++) {
				token = strtok_r(str, ",", &save);
				if (token == NULL)
					break;
			}

			appl_args->if_count = i;

			if (appl_args->if_count == 0) {
				usage(argv[0]);
				exit(EXIT_FAILURE);
			}

			/* allocate storage for the if names */
			appl_args->if_names =
				calloc(appl_args->if_count, sizeof(char *));

			/* store the if names (reset names string) */
			strcpy(names, optarg);
			for (str = names, i = 0;; str = NULL, i++) {
				token = strtok_r(str, ",", &save);
				if (token == NULL)
					break;

				appl_args->if_names[i] = token;
			}

			break;

		case 'm':
			i = atoi(optarg);
			if (i == 0)
				appl_args->mode = APPL_MODE_PKT_BURST;
			else
				appl_args->mode = APPL_MODE_PKT_QUEUE;

			/*use APPL_MODE_PKT_BURST mode*/
			appl_args->mode = APPL_MODE_PKT_BURST;
			break;

		case 'h':
			usage(argv[0]);
			exit(EXIT_SUCCESS);
			break;

		default:
			break;
		}
	}

	if ((appl_args->if_count == 0) || (appl_args->mode == -1)) {
		usage(argv[0]);
		exit(EXIT_FAILURE);
	}

	optind = 1; /* reset 'extern optind' from the getopt lib */
}

/**
 * Print system and application info
 */
static void print_info(char *progname, appl_args_t *appl_args)
{
	int i;

	printf("\n"
	       "ODP system info\n"
	       "---------------\n"
	       "ODP API version: %s\n"
	       "CPU model:       %s\n"
	       "CPU freq (hz):   %d\n"
	       "Cache line size: %i\n"
	       "CPU count:       %i\n"
	       "\n",
	       odp_version_api_str(), odp_sys_cpu_model_str(), odp_sys_cpu_hz(),
	       odp_sys_cache_line_size(), odp_cpu_count());

	printf("Running ODP appl: \"%s\"\n"
	       "-----------------\n"
	       "IF-count:        %i\n"
	       "Using IFs:      ",
	       progname, appl_args->if_count);
	for (i = 0; i < appl_args->if_count; ++i)
		printf(" %s", appl_args->if_names[i]);

	printf("\n"
	       "Mode:            ");
	if (appl_args->mode == APPL_MODE_PKT_BURST)
		PRINT_APPL_MODE(APPL_MODE_PKT_BURST);
	else
		PRINT_APPL_MODE(APPL_MODE_PKT_QUEUE);

	printf("\n\n");
	fflush(NULL);
}

/**
 * Prinf usage information
 */
static void usage(char *progname)
{
	printf("\n"
	       "OpenDataPlane L2 forwarding application.\n"
	       "\n"
	       "Usage: %s OPTIONS\n"
	       "  E.g. %s -i kni_0,kni_1,kni_2,kni_3 -m 0 -t 1\n"
	       " In the above example,\n"
	       " kni_0 will send pkts to kni_0 and vice versa\n"
	       " kni_1 will send pkts to kni_1 and vice versa\n"
	       "\n"
	       "Mandatory OPTIONS:\n"
	       "  -i, --interface Eth interfaces (comma-separated, no spaces)\n"
	       "  -m, --mode      0: Burst send&receive packets (no queues)\n"
	       "                  1: Send&receive packets through ODP queues.\n"
	       "\n"
	       "Optional OPTIONS\n"
	       "  -c, --count <number> CPU count.\n"
	       "  -h, --help           Display help and exit.\n\n"
	       " environment variables: ODP_PKTIO_DISABLE_SOCKET_MMAP\n"
	       "                        ODP_PKTIO_DISABLE_SOCKET_MMSG\n"
	       "                        ODP_PKTIO_DISABLE_SOCKET_BASIC\n"
	       " can be used to advanced pkt I/O selection for linux-generic\n"
	       "\n", NO_PATH(progname), NO_PATH(progname)
	      );
}
