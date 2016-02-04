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

#define _POSIX_C_SOURCE 200112L

#include <stdlib.h>
#include <string.h>
#include <getopt.h>
#include <unistd.h>
#include <errno.h>
#include <sys/time.h>

#include <example_debug.h>

#include <odp.h>
#include <odp/helper/linux.h>
#include <odp/helper/eth.h>
#include <odp/helper/ip.h>
#include <odp_packet_internal.h>

#define CIPHER	    1
#define AUTH	    2
#define CIPHER_AUTH 3
#define TEST_ISSUE  CIPHER

#define PACKET	 0
#define OPDATA	 1
#define SGL_MODE OPDATA

#define USE_MY_PACKET 0

#define SEGMENT_SIZE	64
#define SGL_CIPHER_SIZE (1024 * 1024)

/* #if USE_CRYPTO */
#define TDES_CBC_KEY_LEN      24
#define TDES_CBC_IV_LEN	      8
#define TDES_CBC_MAX_DATA_LEN 16

static uint8_t tdes_cbc_reference_key[][TDES_CBC_KEY_LEN] = {
	{0x62, 0x7f, 0x46, 0x0e, 0x08, 0x10, 0x4a, 0x10, 0x43, 0xcd, 0x26, 0x5d,
	 0x58, 0x40, 0xea, 0xf1, 0x31, 0x3e, 0xdf, 0x97, 0xdf, 0x2a, 0x8a, 0x8c, },

	{0x37, 0xae, 0x5e, 0xbf, 0x46, 0xdf, 0xf2, 0xdc, 0x07, 0x54, 0xb9, 0x4f,
	 0x31, 0xcb, 0xb3, 0x85, 0x5e, 0x7f, 0xd3, 0x6d, 0xc8, 0x70, 0xbf, 0xae}
};

static uint8_t tdes_cbc_reference_iv[][TDES_CBC_IV_LEN] = {
	{0x8e, 0x29, 0xf7, 0x5e, 0xa7, 0x7e, 0x54, 0x75},

	{0x3d, 0x1d, 0xe3, 0xcc, 0x13, 0x2e, 0x3b, 0x65}
};

#define HMAC_MD5_KEY_LENGTH 16

static uint8_t hmac_md5_key[HMAC_MD5_KEY_LENGTH] = {
	0x12, 0x2f, 0x36, 0x0e, 0x08, 0x10, 0x4a, 0x10, 0x43, 0xcd, 0x26, 0x5d,
	0x48, 0x10, 0x1a, 0xf1
};

#if 1

static uint8_t soft_cipher_result[TDES_CBC_MAX_DATA_LEN] = {
	0x3b, 0xa4, 0x5a, 0xd2, 0x81, 0x10, 0xd2, 0x6d, 0x50, 0x7a, 0x62, 0x4e, 0xf0, 0x2b, 0x5e, 0x18
};

static uint8_t sec_cipher_result[TDES_CBC_MAX_DATA_LEN] = {
	0x3b, 0xa4, 0x5a, 0xd2, 0x81, 0x10, 0xd2, 0x6d, 0x50, 0x7a, 0x62, 0x4e, 0xf0, 0x2b, 0x5e, 0x18
};

static uint8_t soft_hash_result[TDES_CBC_MAX_DATA_LEN] = {
	0x83, 0x13, 0xe6, 0xca, 0x11, 0x67, 0x17, 0x10, 0xcb, 0x96, 0x3e, 0xf3
};

static uint8_t sec_hash_result[TDES_CBC_MAX_DATA_LEN] = {
	0x7a, 0x86, 0xa1, 0xbe, 0xa5, 0x4e, 0x5d, 0x76, 0x91, 0xe3, 0xea, 0xcd
};
#endif

/** @def MAX_WORKERS
 * @brief Maximum number of worker threads
 */
#define MAX_WORKERS 32

/** @def SHM_PKT_POOL_SIZE
 * @brief Size of the shared memory block
 */
#define SHM_PKT_POOL_SIZE (1024 * 4) /* /(512 * 2048 * 4) */

/** @def SHM_PKT_POOL_BUF_SIZE
 * @brief Buffer size of the packet pool buffer
 */
#define SHM_PKT_POOL_BUF_SIZE 2048   /* 1856 * 4 */

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
	int    if_count;  /**< Number of interfaces to be used */
	char **if_names;  /**< Array of pointers to interface names */
	int    mode;      /**< Packet IO mode */
	int    sec_idx;   /**< index of sec uio device  */
	int    init_flag; /**< index of sec uio device  */
} appl_args_t;

/**
 * Thread specific arguments
 */
typedef struct {
	int src_idx;        /**< Source interface identifier */
	int sec_uio_idx;
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

#define   MAX_DEV_NUM 16

char dev_name[MAX_DEV_NUM][32];
odp_acc_dev_handle gdev_handle;

long int sgl_recv_cmpl_num = 0;
long int sgl_recv_net_num  = 0;
long int sgl_send_num = 0;
long int sgl_send_net_num = 0;

/* helper funcs */
static inline odp_queue_t lookup_dest_q(odp_packet_t pkt);
static int drop_err_pkts(odp_packet_t pkt_tbl[], unsigned len);
static void parse_args(int argc, char *argv[], appl_args_t *appl_args);
static void print_info(char *progname, appl_args_t *appl_args);
static void usage(char *progname);

static odp_acc_dev_handle get_crypto_handle(int uio_index);
static void *async_sec_thread(void *arg);

/* static void *sync_sec_thread(void *arg);
   static void *sec_sgl_thread(void *arg); */

/* ////////////////////////////////////////////////////////////////////////////////////// */

static void sec_show_memory(void *p_show_addr, int nunits, int width)
{
	static int d_nitems = 0x80;         /* default number of item to display */
	static int dwidth = 2;              /* default width */
	static unsigned long *last_adrs;    /* last location displayed */
	unsigned long *p_addr = (unsigned long *)p_show_addr;

	int item;
	int ix;
	unsigned char *p_byte;
	unsigned char *tmp_byte;
	unsigned short *tmp_short;
	unsigned int  *tmp_int;
	char ascii[16 + 1];

	ascii[16] = '\0';

	if (nunits == 0)
		nunits = d_nitems;
	else
		d_nitems = nunits;

	if (width == 0) {
		width = dwidth;
	} else {
		/* check for valid width */
		if ((width != 1) && (width != 2) && (width != 4) && (width != 8))
			width = 1;

		dwidth = width;
	}

	if (p_addr == 0)
		p_addr = last_adrs;
	else
		last_adrs = p_addr;

	last_adrs = (void *)((unsigned long)last_adrs & ~((unsigned long)(unsigned)width - 1));
	memset((void *)ascii, '.', (size_t)16);

	if (sizeof(long) == 4)
		printf("0x%08lx:  ", (unsigned long)last_adrs & ~0xf);
	else
		printf("0x%016lx:  ", (unsigned long)last_adrs & ~0xf);

	for (item = 0; item < (int)(((unsigned long)last_adrs & 0xf)) / width; item++) {
		printf("%*s ", 2 * width, " ");
		memset((void *)&ascii[(long)item * width], ' ', (size_t)2 * width);
	}

	while (nunits-- > 0) {
		if (item == 16 / width) {
			if (sizeof(long) == 4)
				printf("  *%16s*\n0x%08lx:  ", ascii, (unsigned long)last_adrs);
			else
				printf("  *%16s*\n0x%016lx:  ", ascii, (unsigned long)last_adrs);

			memset((void *)ascii, '.', (size_t)16);
			item = 0;
		}

		switch (width) {
		case 1:
			tmp_byte = (unsigned char *)last_adrs;
			printf("%02x", *tmp_byte);
			break;
		case 2:
			tmp_short = (unsigned short *)(void *)last_adrs;
			printf("%04x", *tmp_short);
			break;
		case 4:
			tmp_int = (unsigned int *)(void *)last_adrs;
			printf("%08x", *tmp_int);
			break;
		case 8:
			tmp_int = (unsigned int *)(void *)last_adrs;
			printf("%08x%08x", *(tmp_int + 1), *tmp_int);
			break;
		default:
			tmp_byte = (unsigned char *)last_adrs;
			printf("%02x", *tmp_byte);
			break;
		}

		printf(" ");

		p_byte = (unsigned char *)last_adrs;
		for (ix = 0; ix < width; ix++) {
			if ((*p_byte == ' ') || ((*p_byte >= 0x30) && (*p_byte < 0x80)))
				ascii[(long)item * width + ix] = (char)*p_byte;

			p_byte++;
		}

		last_adrs = (unsigned long *)((unsigned long)last_adrs + (unsigned)width);
		item++;
	}

	for (; item < 16 / width; item++)
		printf("%*s ", 2 * width, " ");

	printf("  *%16s*\n", ascii);
}

void dump_buf(unsigned char *buf, int len)
{
	int i;

	printf("buf = %p, len = %d\n", buf, len);
	for (i = 1; i <= len; i++) {
		printf("%02x ", *(buf++));
		if ((i % 10 == 0))
			printf("\n");
	}

	printf("\n");
}

unsigned int recv_pkt_count  = 0;
unsigned int g_crypto_ok_num = 0;
unsigned int g_op_error_num  = 0;
unsigned int g_ctx_error_num = 0;

uint64_t total_cycles_for_latency = 0;
unsigned int g_tx_buffer_len = 0;
unsigned int g_my_pkt_count  = 0;
unsigned int g_net_pkt_count = 0;
odp_packet_t g_recv_pkt_tbl[MAX_PKT_BURST];
unsigned int g_recv_idx = 0;

/* callback function of async or sync thread*/
unsigned int  odp_buffer_crypto_compl(odp_crypto_session_t session, struct odp_packet_clarrify_mng *result)
{
	int i;
	odp_packet_t pkt_table[MAX_PKT_BURST];
	odp_pktio_t  pktio_dst;

	if (odp_unlikely(!result)) {
		PRINT("result is null!\n");
		return -1;
	}

	pktio_dst = gbl_args->pktios[*((char *)(result->result[0]->result.pkt) + 31)];
	for (i = 0; i < result->result_num; i++)
		pkt_table[i] = result->result[i]->result.pkt;

	odp_pktio_send(pktio_dst, pkt_table, result->result_num);
	return 0;
}

/* callback function of sgl thread*/
unsigned int  odp_sgl_crypto_compl(odp_crypto_session_t session, odp_crypto_op_result_t *result)
{
	int i;
	unsigned int segnum;
	odp_packet_seg_t seg_temp;
	odp_packet_hdr_t *buffer_header;
	odp_pktio_t pktio_dst;
	void *seg_data;

	if (!result) {
		PRINT("result is null!\n");
		return -1;
	}

	buffer_header = odp_packet_hdr(result->pkt);
	if (buffer_header->opdata) {
		for (i = 0; i < buffer_header->opdata->num_datas; i++)
			odp_packet_free((odp_packet_t)((unsigned long)(buffer_header->opdata->datas[i].data)));

		free(buffer_header->opdata);
	} else {
		seg_temp = odp_packet_first_seg(result->pkt);
		seg_data = odp_packet_seg_data(result->pkt, seg_temp);
		segnum = odp_packet_num_segs(result->pkt);
		for (i = 0; i < segnum; i++) {
			/*      sec_show_memory(seg_data,256,1); */
			seg_temp = odp_packet_next_seg(result->pkt, seg_temp);
			seg_data = odp_packet_seg_data(result->pkt, seg_temp);
		}
	}

	pktio_dst = gbl_args->pktios[*((int *)(result->pkt) + 21)];
	if (*((char *)(result->pkt) + 144) == 0xaa)

		odp_packet_free(result->pkt);
	else
		odp_pktio_send(pktio_dst, &result->pkt, 1);

	return 0;
}

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

	/* unsigned long   pkt_cnt    = 0; */
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
	}

	/* unreachable */
	return NULL;
}

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

	/* dst_idx = (src_idx % 2 == 0) ? src_idx+1 : src_idx-1; */
	dst_idx = src_idx;
	pktio_dst = gbl_args->pktios[dst_idx];

	return odp_pktio_outq_getdef(pktio_dst);
}

#if 1

/**
 * Packet IO worker thread using bursts from/to IO resources
 *
 * @param arg  thread arguments of type 'thread_args_t *'
 */
char test_ctx;

#define PTK_ZONE_NUM 1

/* thread is used to test async crypto function*/
static void *async_sec_thread(void *arg)
{
	int thr;
	thread_args_t *thr_args;
	int pkts, pkts_ok;
	odp_packet_t pkt_tbl[MAX_PKT_BURST];
	int src_idx, dst_idx;
	odp_pktio_t pktio_src, pktio_dst;
	odp_crypto_session_params_t ses_params;
	odp_crypto_session_t session;
	odp_crypto_op_params_t *op_params[PTK_ZONE_NUM * MAX_PKT_BURST];
	enum odp_crypto_ses_create_err status;
	enum odp_cipher_alg cipher_alg;
	enum odp_auth_alg   auth_alg;

#if (TEST_ISSUE == CIPHER_AUTH)
	cipher_alg = ODP_CIPHER_ALG_3DES_CBC;
	auth_alg = ODP_AUTH_ALG_MD5_96;

#elif (TEST_ISSUE == CIPHER)
	cipher_alg = ODP_CIPHER_ALG_3DES_CBC;
	auth_alg = ODP_AUTH_ALG_NULL;

#elif (TEST_ISSUE == AUTH)
	cipher_alg = ODP_CIPHER_ALG_NULL;
	auth_alg = ODP_AUTH_ALG_HMAC_MD5_96;
#endif

	odp_crypto_key_t cipher_key = {
		.data = NULL, .length = 0
	};
	odp_crypto_key_t auth_key = {
		.data = NULL, .length = 0
	};
	odp_crypto_iv_t	 iv;
	int ret = 0;
	int i = 0, j;
	int recv_num = 0;
	odp_acc_dev_handle dev_handle;
	unsigned char start_flag = 0;

	thr = odp_thread_id();
	thr_args = arg;
	src_idx	 = thr_args->src_idx;

	/* dst_idx = (src_idx % 2 == 0) ? src_idx+1 : src_idx-1; */
	dst_idx = src_idx;
	pktio_src  = gbl_args->pktios[src_idx];
	pktio_dst  = gbl_args->pktios[dst_idx];
	dev_handle = get_crypto_handle(thr_args->sec_uio_idx);
	cipher_key.data = tdes_cbc_reference_key[i];
	cipher_key.length = sizeof(tdes_cbc_reference_key[i]);
	iv.data = tdes_cbc_reference_iv[i];
	iv.length = sizeof(tdes_cbc_reference_iv[i]);
	auth_key.data = hmac_md5_key;
	auth_key.length = sizeof(hmac_md5_key);

	memset(&ses_params, 0, sizeof(ses_params));

	/* ses_params.op = ODP_CRYPTO_OP_DECODE; */
	ses_params.op = ODP_CRYPTO_OP_ENCODE;
	ses_params.auth_cipher_text = true;
	ses_params.pref_mode  = ODP_CRYPTO_ASYNC;
	ses_params.cipher_alg = cipher_alg;
	ses_params.auth_alg = auth_alg;
	ses_params.compl_queue = ODP_QUEUE_INVALID;
	ses_params.output_pool = odp_pool_lookup("packet pool");
	ses_params.cipher_key  = cipher_key;
	ses_params.iv = iv;
	ses_params.auth_key = auth_key;
	ses_params.dev_handle = dev_handle;
	ses_params.odp_crypto_compl_pfn = (void *)odp_buffer_crypto_compl;
	ret = odp_crypto_session_create(&ses_params, &session, &status);
	if (ret)
		EXAMPLE_ABORT("Failed odp_crypto_session_create\n");

	for (i = 0; i < PTK_ZONE_NUM * MAX_PKT_BURST; i++) {
		op_params[i] = malloc(sizeof(odp_crypto_op_params_t));
		if (!op_params[i]) {
			PRINT("malloc memory failed!\n");
			return NULL;
		}

		memset(op_params[i], 0, sizeof(odp_crypto_op_params_t));
		op_params[i]->pkt = ODP_PACKET_INVALID;
		op_params[i]->out_pkt = ODP_PACKET_INVALID;
	}

	printf("[%02i] srcif:%s dstif:%s spktio:%02d dpktio:%02d sec uio index:%d  BURST mode\n",
	       thr,
	       gbl_args->appl.if_names[src_idx],
	       gbl_args->appl.if_names[dst_idx],
	       odp_pktio_to_u64(pktio_src),
	       odp_pktio_to_u64(pktio_dst),
	       thr_args->sec_uio_idx);
	PRINT("async !\n");

	/* Loop packets */
	for (;; ) {
		pkts = odp_pktio_recv(pktio_src, pkt_tbl, MAX_PKT_BURST);
		pkts_ok = pkts;

		if (pkts_ok == 0) {
			usleep(10);
			continue;
		}

		/* Drop packets with errors */
		if (pkts_ok > 0) {
			if (start_flag == 0) {
				start_flag = 1;
				g_tx_buffer_len = packet_get_len(pkt_tbl[0]);
				PRINT("g_tx_buffer_len = %d\n", (g_tx_buffer_len - 64));

				for (j = 0; j < PTK_ZONE_NUM * MAX_PKT_BURST; j++) {
					if (cipher_alg != ODP_CIPHER_ALG_NULL) {
						op_params[j]->cipher_range.offset = 32;
						op_params[j]->cipher_range.length = (g_tx_buffer_len - 64);
					}

					if (auth_alg != ODP_AUTH_ALG_NULL) {
						op_params[j]->auth_range.offset	 = 32;
						op_params[j]->auth_range.length	 = (g_tx_buffer_len - 64);
						op_params[j]->hash_result_offset = 32;
					}

					op_params[j]->ctx = NULL;
					op_params[j]->out_digest = NULL;
					op_params[j]->override_aad_ptr = NULL;
					op_params[j]->override_iv_ptr  = NULL;
				}
			}

			for (i = 0; i < pkts_ok; i++) {
				op_params[i]->session = session;
				op_params[i]->pkt = pkt_tbl[i];
				op_params[i]->out_pkt = pkt_tbl[i];
				*((char *)(op_params[i]->pkt) + 31) = dst_idx;
			}

			ret = odp_crypto_operation_burst(op_params, PTK_ZONE_NUM * pkts_ok);

			do
				recv_num = odp_crypto_dev_poll(dev_handle, 16);
			while (recv_num > 0);

		}

		if (pkts_ok == 0)
			continue;
	}

	for (i = 0; i < PTK_ZONE_NUM * MAX_PKT_BURST; i++)
		free(op_params[i]);

	/* unreachable */
	return NULL;
}
#endif

/* thread is used to test sync crypto function*/
static void *sync_sec_thread(void *arg)
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
	odp_crypto_session_params_t ses_params;
	odp_crypto_session_t session;
	odp_crypto_op_params_t *op_params;
	enum odp_crypto_ses_create_err status;
	enum odp_cipher_alg cipher_alg;
	enum odp_auth_alg   auth_alg;

#if (TEST_ISSUE == CIPHER_AUTH)
	cipher_alg = ODP_CIPHER_ALG_3DES_CBC;
	auth_alg = ODP_AUTH_ALG_MD5_96;

#elif (TEST_ISSUE == CIPHER)
	cipher_alg = ODP_CIPHER_ALG_3DES_CBC;
	auth_alg = ODP_AUTH_ALG_NULL;

#elif (TEST_ISSUE == AUTH)
	cipher_alg = ODP_CIPHER_ALG_NULL;
	auth_alg = ODP_AUTH_ALG_HMAC_MD5_96;
#endif

	odp_crypto_key_t cipher_key = {
		.data = NULL, .length = 0
	};
	odp_crypto_key_t auth_key = {
		.data = NULL, .length = 0
	};
	odp_crypto_iv_t	 iv;
	int ret = 0;
	int i = 0, j;
	odp_bool_t posted;
	odp_crypto_op_result_t result;

	/* unsigned int tx_buffer_len = 0; */
	struct timeval start;
	int recv_num = 0;
	odp_acc_dev_handle dev_handle;
	unsigned char start_flag = 0;

	odp_packet_hdr_t *buffer_header;
	unsigned int gran_num;
	odp_cpumask_t get, get_bak;
	long long int pkt_from_net = 0;
	long long int pkt_from_dev = 0;

	thr = odp_thread_id();
	thr_args = arg;
	src_idx	 = thr_args->src_idx;

	/* dst_idx = (src_idx % 2 == 0) ? src_idx+1 : src_idx-1; */
	dst_idx = src_idx;
	pktio_src = gbl_args->pktios[src_idx];
	pktio_dst = gbl_args->pktios[dst_idx];

	dev_handle = get_crypto_handle(thr_args->sec_uio_idx);
	cipher_key.data = tdes_cbc_reference_key[i];
	cipher_key.length = sizeof(tdes_cbc_reference_key[i]);
	iv.data = tdes_cbc_reference_iv[i];
	iv.length = sizeof(tdes_cbc_reference_iv[i]);
	auth_key.data = hmac_md5_key;
	auth_key.length = sizeof(hmac_md5_key);

	memset(&ses_params, 0, sizeof(ses_params));

	/* ses_params.op = ODP_CRYPTO_OP_DECODE; */
	ses_params.op = ODP_CRYPTO_OP_ENCODE;
	ses_params.auth_cipher_text = true;
	ses_params.pref_mode  = ODP_CRYPTO_SYNC;
	ses_params.cipher_alg = cipher_alg;
	ses_params.auth_alg = auth_alg;
	ses_params.compl_queue = ODP_QUEUE_INVALID;
	ses_params.output_pool = odp_pool_lookup("packet pool");
	ses_params.cipher_key  = cipher_key;
	ses_params.iv = iv;
	ses_params.auth_key = auth_key;
	ses_params.dev_handle = dev_handle;
	ses_params.odp_crypto_compl_pfn = NULL;
	ret = odp_crypto_session_create(&ses_params, &session, &status);
	if (ret)
		EXAMPLE_ABORT("Failed odp_crypto_session_create\n");

	op_params = malloc(sizeof(odp_crypto_op_params_t));
	if (!op_params) {
		PRINT("malloc memory failed!\n");
		return NULL;
	}

	memset(op_params, 0, sizeof(odp_crypto_op_params_t));
	op_params->pkt = ODP_PACKET_INVALID;
	op_params->out_pkt = ODP_PACKET_INVALID;

	printf("[%02i] srcif:%s dstif:%s spktio:%02d dpktio:%02d sec uio index:%d  BURST mode\n",
	       thr,
	       gbl_args->appl.if_names[src_idx],
	       gbl_args->appl.if_names[dst_idx],
	       odp_pktio_to_u64(pktio_src),
	       odp_pktio_to_u64(pktio_dst),
	       thr_args->sec_uio_idx);

	PRINT("sync !\n");

	/* Loop packets */
	for (;; ) {
#if USE_MY_PACKET
		for (i = 0; i < MAX_PKT_BURST; i++) {
			pkt_tbl[i] = odp_packet_alloc(ses_params.output_pool, 1024);
			pkts_ok = MAX_PKT_BURST;
		}

#else
		pkts = odp_pktio_recv(pktio_src, pkt_tbl, MAX_PKT_BURST);
		if (pkts == 0) {
			usleep(10);
			continue;
		}
		pkts_ok = pkts;
#endif

		/* Drop packets with errors */
		if (pkts_ok > 0) {
			for (i = 0; i < pkts_ok; i++) {
				if (start_flag == 0) {
					start_flag = 1;
					g_tx_buffer_len = odp_packet_len(pkt_tbl[0]);
					PRINT("g_tx_buffer_len = %d\n", (g_tx_buffer_len - 120));

					if (cipher_alg != ODP_CIPHER_ALG_NULL) {
						op_params->cipher_range.offset = 64;
						op_params->cipher_range.length = (g_tx_buffer_len - 120);
					}

					if (auth_alg != ODP_AUTH_ALG_NULL) {
						op_params->auth_range.offset  = 64;
						op_params->auth_range.length  = (g_tx_buffer_len - 120);
						op_params->hash_result_offset = 64;
					}

					op_params->ctx = NULL;
					op_params->out_digest = NULL;
					op_params->override_aad_ptr = NULL;
					op_params->override_iv_ptr  = NULL;
				}

				op_params->session = session;
				op_params->pkt = pkt_tbl[i];
				op_params->out_pkt = pkt_tbl[i];
				test_ctx = 0xbb;
				op_params->ctx = &test_ctx;
				ret = odp_crypto_operation(op_params, &posted, &result);

#if 1
				ses_params.op = ODP_CRYPTO_OP_DECODE;
				ret = odp_crypto_session_create(&ses_params, &session, &status);
				if (ret)
					EXAMPLE_ABORT("Failed odp_crypto_session_create\n");

				op_params->session = session;
				op_params->pkt = result.pkt;
				op_params->out_pkt = result.pkt;
				ret = odp_crypto_operation(op_params, &posted, &result);
#endif
			}

#if USE_MY_PACKET
			for (i = 0; i < MAX_PKT_BURST; i++)
				odp_packet_free(pkt_tbl[i]);

#else

			/* sec_show_memory(result.pkt,1024,1); */
			odp_pktio_send(pktio_dst, &result.pkt, pkts_ok);
#endif
		}

		if (pkts_ok == 0)
			continue;
	}

	free(op_params);

	/* unreachable */
	return NULL;
}

/* thread is used to test sgl function */
static void *sec_sgl_thread(void *arg)
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
	odp_crypto_session_params_t ses_params;
	odp_crypto_session_t session;
	odp_crypto_op_params_t *op_params = NULL;
	enum odp_crypto_ses_create_err status;
	enum odp_cipher_alg cipher_alg;
	enum odp_auth_alg   auth_alg;

#if (TEST_ISSUE == CIPHER_AUTH)
	cipher_alg = ODP_CIPHER_ALG_3DES_CBC;
	auth_alg = ODP_AUTH_ALG_MD5_96;

#elif (TEST_ISSUE == CIPHER)
	cipher_alg = ODP_CIPHER_ALG_3DES_CBC;
	auth_alg = ODP_AUTH_ALG_NULL;

#elif (TEST_ISSUE == AUTH)
	cipher_alg = ODP_CIPHER_ALG_NULL;
	auth_alg = ODP_AUTH_ALG_HMAC_MD5_96;
#endif

	odp_crypto_key_t cipher_key = {
		.data = NULL, .length = 0
	};
	odp_crypto_key_t auth_key = {
		.data = NULL, .length = 0
	};
	odp_crypto_iv_t	 iv;
	int ret = 0;
	int i = 0, j;
	odp_bool_t posted;
	odp_crypto_op_result_t result;

	/* unsigned int tx_buffer_len = 0; */
	struct timeval start;
	int recv_num = 0;
	odp_acc_dev_handle dev_handle;
	unsigned char start_flag = 0;

	odp_packet_t  packet_sgl;
	unsigned int  segnum = 0, seglen;
	odp_packet_seg_t seg_temp;
	uint32_t seg_data_len, seg_buf_len;
	void *seg_buf_addr, *seg_data;
	odp_packet_hdr_t *buffer_header;
	void *seg_pkt_addr[MAX_PKT_BURST] = {NULL};
	void *seg_pkt_data[MAX_PKT_BURST] = {NULL};
	unsigned int gran_num;
	odp_cpumask_t get, get_bak;
	long long int pkt_from_net = 0;
	long long int pkt_from_dev = 0;
	long int v2paddr;
	void *p2vaddr = NULL;

	thr = odp_thread_id();
	thr_args = arg;
	src_idx	 = thr_args->src_idx;

	/* dst_idx = (src_idx % 2 == 0) ? src_idx+1 : src_idx-1; */
	dst_idx = src_idx;
	pktio_src = gbl_args->pktios[src_idx];
	pktio_dst = gbl_args->pktios[dst_idx];

	dev_handle = get_crypto_handle(thr_args->sec_uio_idx);
	cipher_key.data = tdes_cbc_reference_key[i];
	cipher_key.length = sizeof(tdes_cbc_reference_key[i]);
	iv.data = tdes_cbc_reference_iv[i];
	iv.length = sizeof(tdes_cbc_reference_iv[i]);
	auth_key.data = hmac_md5_key;
	auth_key.length = sizeof(hmac_md5_key);

	memset(&ses_params, 0, sizeof(ses_params));

	/* ses_params.op = ODP_CRYPTO_OP_DECODE; */
	ses_params.op = ODP_CRYPTO_OP_ENCODE;
	ses_params.auth_cipher_text = true;
	ses_params.pref_mode  = ODP_CRYPTO_ASYNC;
	ses_params.cipher_alg = cipher_alg;
	ses_params.auth_alg = auth_alg;
	ses_params.compl_queue = ODP_QUEUE_INVALID;
	ses_params.output_pool = odp_pool_lookup("packet pool");
	ses_params.cipher_key  = cipher_key;
	ses_params.iv = iv;
	ses_params.auth_key = auth_key;
	ses_params.dev_handle = dev_handle;
	ses_params.odp_crypto_compl_pfn = (void *)odp_sgl_crypto_compl;
	ret = odp_crypto_session_create(&ses_params, &session, &status);
	if (ret)
		EXAMPLE_ABORT("Failed odp_crypto_session_create\n");

	op_params = malloc(sizeof(odp_crypto_op_params_t));
	if (!op_params) {
		PRINT("malloc memory failed!\n");
		return NULL;
	}

	memset(op_params, 0, sizeof(odp_crypto_op_params_t));
	op_params->pkt = ODP_PACKET_INVALID;
	op_params->out_pkt = ODP_PACKET_INVALID;

	printf("[%02i] srcif:%s dstif:%s spktio:%02d dpktio:%02d sec uio index:%d  BURST mode\n",
	       thr,
	       gbl_args->appl.if_names[src_idx],
	       gbl_args->appl.if_names[dst_idx],
	       odp_pktio_to_u64(pktio_src),
	       odp_pktio_to_u64(pktio_dst),
	       thr_args->sec_uio_idx);

#if (SGL_MODE == PACKET)
	PRINT("packet sgl !\n");
	while (1) {
		packet_sgl = odp_packet_alloc(ses_params.output_pool, 40960);
		memset(packet_sgl, 0, 40960);

		segnum = odp_packet_num_segs(packet_sgl);
		seglen = odp_packet_seg_len(packet_sgl);

		if (cipher_alg != ODP_CIPHER_ALG_NULL) {
			op_params->cipher_range.offset = 32;
			op_params->cipher_range.length = 512;
		}

		if (auth_alg != ODP_AUTH_ALG_NULL) {
			op_params->auth_range.offset  = 32;
			op_params->auth_range.length  = 512;
			op_params->hash_result_offset = 64;
		}

		buffer_header = odp_packet_hdr(packet_sgl);
		buffer_header->opdata = NULL;
		op_params->ctx = NULL;
		op_params->out_digest = NULL;
		op_params->override_aad_ptr = NULL;
		op_params->override_iv_ptr  = NULL;
		op_params->session = session;
		op_params->pkt = packet_sgl;
		op_params->out_pkt = packet_sgl;
		*((char *)(op_params->pkt) + 144) = 0xaa;
		*((int *)(op_params->pkt) + 20) = src_idx;
		*((int *)(op_params->pkt) + 21) = dst_idx;

		ret = odp_crypto_operation(op_params, &posted, &result);
		if (ret)
			EXAMPLE_ERR("odp_crypto_operation failed!\n");

		sgl_send_num++;

		do
			recv_num = odp_crypto_dev_poll(dev_handle, 8);
		while (recv_num == 0);

		recv_num = 0;

		/*  usleep(10); */
	}

	/* test sgl mode *opdata */
#elif (SGL_MODE == OPDATA)
	PRINT("opdata test\n");
	while (1) {
		pkts = odp_pktio_recv(pktio_src, pkt_tbl, MAX_PKT_BURST);
		if (pkts <= 0) {
			usleep(10);
			continue;
		}

		sgl_send_net_num += pkts;
		pkts_ok = drop_err_pkts(pkt_tbl, pkts);

		if (pkts_ok > 0)

			for (i = 0; i < pkts_ok; i++) {
				buffer_header = odp_packet_hdr(pkt_tbl[i]);
				buffer_header->opdata = malloc(sizeof(odp_packet_opdata_t));
				if (!buffer_header->opdata) {
					PRINT("malloc memory failed!\n");
					return NULL;
				}

				buffer_header->opdata->num_datas = MAX_PKT_BURST;
				for (j = 0; j < MAX_PKT_BURST; j++) {
					seg_pkt_addr[j] = odp_packet_alloc(ses_params.output_pool, 1024);
					if (!seg_pkt_addr[j]) {
						PRINT("alloc segment memory fail!\n");
						return NULL;
					}

					seg_pkt_data[j] = odp_packet_data(seg_pkt_addr[j]);
					memset(seg_pkt_data[j], 0, SEGMENT_SIZE);
					buffer_header->opdata->datas[j].data = seg_pkt_data[j];
					buffer_header->opdata->datas[j].data_len = SEGMENT_SIZE;
				}

				if (cipher_alg != ODP_CIPHER_ALG_NULL) {
					op_params->cipher_range.offset = 32;
					op_params->cipher_range.length = 512;
				}

				if (auth_alg != ODP_AUTH_ALG_NULL) {
					op_params->auth_range.offset  = 32;
					op_params->auth_range.length  = 512;
					op_params->hash_result_offset = 64;
				}

				op_params->ctx = NULL;
				op_params->out_digest = NULL;
				op_params->override_aad_ptr = NULL;
				op_params->override_iv_ptr  = NULL;
				op_params->session = session;
				op_params->pkt = pkt_tbl[i];
				op_params->out_pkt = pkt_tbl[i];
				*((int *)(op_params->pkt) + 20) = src_idx;
				*((int *)(op_params->pkt) + 21) = dst_idx;

				ret = odp_crypto_operation(op_params, &posted, &result);
				if (ret)
					EXAMPLE_ERR("odp_crypto_operation failed!\n");
			}

		do
			recv_num = odp_crypto_dev_poll(dev_handle, 8);
		while (recv_num > 0);
	}
#endif

	free(op_params);

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
	odp_queue_param_t qparam;
	odp_queue_t inq_def;
	odp_pktio_t pktio;
	int ret;

	pktio = odp_pktio_open(dev, pool);
	if (pktio == ODP_PKTIO_INVALID) {
		EXAMPLE_ERR("Error: failed to open %s\n", dev);
		return ODP_PKTIO_INVALID;
	}

	/* no further setup needed for burst mode */
	if (mode == APPL_MODE_PKT_BURST)
		return pktio;

	qparam.sched.prio  = ODP_SCHED_PRIO_DEFAULT;
	qparam.sched.sync  = ODP_SCHED_SYNC_ATOMIC;
	qparam.sched.group = ODP_SCHED_GROUP_DEFAULT;
	snprintf(inq_name, sizeof(inq_name), "%d-pktio_inq_def",
		 odp_pktio_to_u64(pktio));
	inq_name[ODP_QUEUE_NAME_LEN - 1] = '\0';

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

	return pktio;
}

/**
 * ODP L2 forwarding main function
 */
odp_acc_dev_handle get_crypto_handle(int uio_index)
{
	char *pdev_name[16];
	uint32_t dev_num = 16;
	int   i = 0;
	int   ret = 0;
	odp_acc_dev_handle dev_handle = 0;

	if ((uio_index < 0) && (uio_index >= 16)) {
		printf("\n input parametre uio index is invalid!\n");
		uio_index = 0;
	}

	for (i = 0; i < MAX_DEV_NUM; i++)
		pdev_name[i] = &dev_name[i][0];

	ret = odp_acc_get_dev_names(ODP_ACC_DEV_CRYPTO_TYPE, &pdev_name[0], &dev_num);
	if (ret) {
		EXAMPLE_ERR("Error: odp_acc_get_dev_names failed.\n");

		/* exit(EXIT_FAILURE); */

		return ODP_ACC_HANDLE_INVALID;
	}

	PRINT("devname = %s, dev_num = %d\n", (char *)pdev_name[uio_index], dev_num);
	dev_handle = odp_acc_get_dev_handle((char *)pdev_name[uio_index]);
	if (dev_handle == ODP_ACC_HANDLE_INVALID) {
		EXAMPLE_ERR("Error: odp_acc_get_dev_handle failed.\n");

		/* exit(EXIT_FAILURE); */

		return ODP_ACC_HANDLE_INVALID;
	}

	ret = odp_acc_open_dev(dev_handle);
	if (ret != 0) {
		EXAMPLE_ERR("Error: odp_acc_open_dev failed.\n");

		/* exit(EXIT_FAILURE); */
		return ODP_ACC_HANDLE_INVALID;
	}

	return dev_handle;
}

int main(int argc, char *argv[])
{
	odph_linux_pthread_t thread_tbl[MAX_WORKERS];
	odp_pool_t pool;
	int i;
	int cpu;
	int num_workers;
	odp_shm_t shm;
	odp_cpumask_t cpumask;
	char cpumaskstr[ODP_CPUMASK_STR_SIZE];
	odp_pool_param_t params;

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

	/* Reserve memory for args from shared mem */
	shm = odp_shm_reserve("shm_args", sizeof(args_t),
			      ODP_CACHE_LINE_SIZE, 0);
	gbl_args = odp_shm_addr(shm);

	if (!gbl_args) {
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

	if (num_workers < gbl_args->appl.if_count) {
		EXAMPLE_ERR("Error: CPU count %d less than interface count\n", num_workers);
		exit(EXIT_FAILURE);
	}

	for (i = 0; i < gbl_args->appl.if_count; ++i) {
		params.pkt.seg_len = PACKET_SEG_LEN;
		params.pkt.len = SHM_PKT_POOL_BUF_SIZE;
		params.pkt.num = SHM_PKT_POOL_SIZE;
		params.pkt.lock = 0;
		params.type = ODP_POOL_PACKET;
		pool = odp_pool_create(gbl_args->appl.if_names[i], &params);
		if (pool == ODP_POOL_INVALID) {
			EXAMPLE_ERR("Error: packet pool create failed.\n");
			exit(EXIT_FAILURE);
		}

		gbl_args->pktios[i] = create_pktio(gbl_args->appl.if_names[i], pool, gbl_args->appl.mode);
		if (gbl_args->pktios[i] == ODP_PKTIO_INVALID) {
			PRINT("create_pktio is fail.\n");
			exit(EXIT_FAILURE);
		}
	}

	gbl_args->pktios[i] = ODP_PKTIO_INVALID;
	memset(thread_tbl, 0, sizeof(thread_tbl));

	/* gbl_args->appl.sec_idx = 1; */

	/* Create worker threads */
	cpu = odp_cpumask_first(&cpumask);
	for (i = 0; i < num_workers; ++i) {
		odp_cpumask_t thd_mask;
		void *(*thr_run_func)(void *);

		PRINT("test !\n");

		if (gbl_args->appl.mode == APPL_MODE_PKT_BURST)
			thr_run_func = async_sec_thread;

		/* thr_run_func = sync_sec_thread;
		   thr_run_func = sec_sgl_thread; */
		else    /* APPL_MODE_PKT_QUEUE */
			thr_run_func = pktio_queue_thread;

		gbl_args->thread[i].src_idx = i % gbl_args->appl.if_count;
		gbl_args->thread[i].sec_uio_idx = gbl_args->appl.sec_idx;

		odp_cpumask_zero(&thd_mask);
		odp_cpumask_set(&thd_mask, cpu);
		odph_linux_pthread_create(&thread_tbl[i], &thd_mask,
					  thr_run_func,
					  &gbl_args->thread[i]);
		cpu = odp_cpumask_next(&cpumask, cpu);
		gbl_args->appl.sec_idx++;
		if (gbl_args->appl.sec_idx > 15)
			gbl_args->appl.sec_idx = 0;
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
			/* PRINT("pkt:%p\n",pkt); */
			odp_packet_free(pkt); /* Drop */

			/* PRINT("pkt:%p\n",pkt); */
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
	int   i, j;
	static struct option longopts[] = {
		{"count",     required_argument, NULL, 'c'},
		{"interface", required_argument, NULL, 'i'},                /* return 'i' */
		{"mode",      required_argument, NULL, 'm'},                /* return 'm' */
		{"sec",	      required_argument, NULL, 's'},                /* return 's' */
		{"init",      required_argument, NULL, 'y'},                /* return 'y' */
		{"help",      no_argument,	 NULL, 'h'},                /* return 'h' */
		{NULL,	      0,		 NULL, 0  }
	};

	appl_args->mode = -1; /* Invalid, must be changed by parsing */

	while (1) {
		opt = getopt_long(argc, argv, "+c:i:m:s:y:h",
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
			if (!names) {
				usage(argv[0]);
				exit(EXIT_FAILURE);
			}

			/* count the number of tokens separated by ',' */
			strcpy(names, optarg);
			for (str = names, i = 0;; str = NULL, i++) {
				token = strtok_r(str, ",", &save);
				if (!token)
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
				if (!token)
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

			break;

		case 's':
			j = atoi(optarg);
			appl_args->sec_idx = j;
			PRINT("appl_args->sec_idx =%d\n", appl_args->sec_idx);
			break;

		case 'y':
			j = atoi(optarg);
			appl_args->init_flag = j;
			PRINT("appl_args->init_flag =%d\n", appl_args->init_flag);
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

	printf("ODP system info\n"
	       "---------------\n"
	       "ODP API version: %s\n"
	       "CPU model:       %s\n"
	       "CPU freq (hz):   %d\n"
	       "Cache line size: %i\n"
	       "CPU count:       %i\n",
	       odp_version_api_str(), odp_sys_cpu_model_str(), odp_sys_cpu_hz(),
	       odp_sys_cache_line_size(), odp_cpu_count());

	printf("Running ODP appl: \"%s\"\n"
	       "-----------------\n"
	       "IF-count:        %i\n"
	       "Using IFs:      ", progname, appl_args->if_count);
	for (i = 0; i < appl_args->if_count; ++i)
		printf(" %s\n", appl_args->if_names[i]);

	printf("Mode:            ");
	if (appl_args->mode == APPL_MODE_PKT_BURST)
		PRINT_APPL_MODE(APPL_MODE_PKT_BURST);
	else
		PRINT_APPL_MODE(APPL_MODE_PKT_QUEUE);

	printf("sec uio index:          %d\n", appl_args->sec_idx);
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
	       "  E.g. %s -i eth0,eth1,eth2,eth3 -m 0 -t 1\n"
	       " In the above example,\n"
	       " eth0 will send pkts to eth1 and vice versa\n"
	       " eth2 will send pkts to eth3 and vice versa\n"
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
