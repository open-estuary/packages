/*-
 *   BSD LICENSE
 *
 *   Copyright(c) 2010-2014 Intel Corporation. All rights reserved.
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
 *     * Neither the name of Intel Corporation nor the names of its
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
#define ODP_EXEC_ENV_LINUXAPP /*added for compiling 2015-09-02*/
#ifndef ODP_EXEC_ENV_LINUXAPP
#error "KNI is not supported"
#endif

#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/ioctl.h>

#include <odp/spinlock.h>
#include <odp_debug_internal.h>
#include <odp/pool.h>
#include <odp/buffer.h>
#include <odp/packet.h>
#include <odp_memory.h>
#include <odp_memcpy.h>
#include <odp_mmdistrict.h>
#include "odp_ethdev.h"

#include "odp_kni_common.h"
#include "odp_kni.h"
#include "odp_kni_fifo.h"

#define BD_BUFFER_MAX_SIZE 4096          /* PV660 RCB buffer×î´óÖµ */

#define MAX_MBUF_BURST_NUM 32

/* Maximum number of ring entries */
#define KNI_FIFO_COUNT_MAX 1024
#define KNI_FIFO_SIZE	   (KNI_FIFO_COUNT_MAX * sizeof(void *) + \
			    sizeof(struct odp_kni_fifo))

#define KNI_REQUEST_MBUF_NUM_MAX 32

enum kni_ops_status {
	KNI_REQ_NO_REGISTER = 0,
	KNI_REQ_REGISTERED,
};

/**
 * KNI mm_district pool slot
 */
struct odp_kni_mm_district_slot {
	uint32_t id;
	uint8_t	 in_use : 1;                   /**< slot in use */

	/* Memzones */
	const struct odp_mm_district *m_ctx;      /**< KNI ctx */
	const struct odp_mm_district *m_tx_q;     /**< TX queue */
	const struct odp_mm_district *m_rx_q;     /**< RX queue */
	const struct odp_mm_district *m_alloc_q;  /**< Allocated mbufs queue */
	const struct odp_mm_district *m_free_q;   /**< To be freed mbufs queue */
	const struct odp_mm_district *m_req_q;    /**< Request queue */
	const struct odp_mm_district *m_resp_q;   /**< Response queue */
	const struct odp_mm_district *m_sync_addr;

	/* Free linked list */
	struct odp_kni_mm_district_slot *next;     /**< Next slot link.list */
};

/**
 * KNI mm_district pool
 */
struct odp_kni_mm_district_pool {
	uint8_t initialized : 1;/**< Global KNI pool init flag */

	uint32_t		     max_ifaces; /**< Max. num of KNI ifaces */
	struct odp_kni_mm_district_slot *slots;      /**< Pool slots */
	odp_spinlock_t		     mutex;      /**< alloc/relase mutex */

	/* Free mm_district slots linked-list */
	struct odp_kni_mm_district_slot *free;       /**< First empty slot */
	struct odp_kni_mm_district_slot *free_tail;  /**< Last empty slot */
};

static void kni_free_mbufs(struct odp_kni *kni);
static void kni_allocate_mbufs(struct odp_kni *kni);

static int kni_fd = -1;
static struct odp_kni_mm_district_pool kni_mm_district_pool = {
	.initialized = 0,
};

static const struct odp_mm_district  *kni_mm_district_reserve(const char *name,
						size_t len, int socket_id,
						       unsigned flags)
{
	const struct odp_mm_district *mz = odp_mm_district_lookup(name);

	if (mz == NULL)
		mz = odp_mm_district_reserve(name, name, len, socket_id, flags);

	return mz;
}

/* Pool mgmt */
static struct odp_kni_mm_district_slot *kni_mm_district_pool_alloc(void)
{
	struct odp_kni_mm_district_slot *slot;

	odp_spinlock_lock(&kni_mm_district_pool.mutex);

	if (!kni_mm_district_pool.free) {
		odp_spinlock_unlock(&kni_mm_district_pool.mutex);
		return NULL;
	}

	slot = kni_mm_district_pool.free;
	kni_mm_district_pool.free = slot->next;
	slot->in_use = 1;

	if (!kni_mm_district_pool.free)
		kni_mm_district_pool.free_tail = NULL;

	odp_spinlock_unlock(&kni_mm_district_pool.mutex);

	return slot;
}

static void kni_mm_district_pool_release(struct odp_kni_mm_district_slot *slot)
{
	odp_spinlock_lock(&kni_mm_district_pool.mutex);

	if (kni_mm_district_pool.free)
		kni_mm_district_pool.free_tail->next = slot;
	else
		kni_mm_district_pool.free = slot;

	kni_mm_district_pool.free_tail = slot;
	slot->next = NULL;
	slot->in_use = 0;

	odp_spinlock_unlock(&kni_mm_district_pool.mutex);
}

/* Shall be called before any allocation happens */
int odp_kni_init(unsigned int max_kni_ifaces)
{
	return 0;

	uint32_t i;
	struct odp_kni_mm_district_slot *it;
	const struct odp_mm_district   *mz;

#define OBJNAMSIZ 32

	char obj_name[OBJNAMSIZ];
	char mz_name[ODP_MEMZONE_NAMESIZE];

	/* Immediately return if KNI is already initialized */
	if (kni_mm_district_pool.initialized) {
		ODP_ERR("Double call to odp_kni_init()");
		return -1;
	}

	if (max_kni_ifaces == 0) {
		ODP_ERR("Invalid number of "
			"max_kni_ifaces %d\n", max_kni_ifaces);
		ODP_ERR("Unable to initialize KNI\n");
		return -1;
	}

	/* Check FD and open */
	if (kni_fd < 0) {
		kni_fd = open("/dev/" KNI_DEVICE, O_RDWR);
		if (kni_fd < 0) {
			ODP_ERR("Can not open /dev/%s\n", KNI_DEVICE);
			return -1;
		}
	}

	/* Allocate slot objects */
	kni_mm_district_pool.slots = (struct odp_kni_mm_district_slot *)
	 malloc(sizeof(struct odp_kni_mm_district_slot) * max_kni_ifaces);
	if (!kni_mm_district_pool.slots) {
		ODP_ERR("malloc failed!\n");
		goto malloc_failed;
	}
	memset((void *)kni_mm_district_pool.slots, 0,
		sizeof(struct odp_kni_mm_district_slot) * max_kni_ifaces);

	/* Initialize general pool variables */
	kni_mm_district_pool.initialized = 1;
	kni_mm_district_pool.max_ifaces  = max_kni_ifaces;
	kni_mm_district_pool.free = &kni_mm_district_pool.slots[0];
	odp_spinlock_init(&kni_mm_district_pool.mutex);

	/* Pre-allocate all mm_districts of all the slots; panic on error */
	for (i = 0; i < max_kni_ifaces; i++) {
		/* Recover current slot */
		it = &kni_mm_district_pool.slots[i];
		it->id = i;

		/* Allocate KNI context */
		snprintf(mz_name, ODP_MEMZONE_NAMESIZE, "KNI_INFO_%d", i);
		mz = kni_mm_district_reserve(mz_name, sizeof(struct odp_kni),
					 SOCKET_ID_ANY, 0);
		if (!mz) {
			ODP_ERR("kni_mm_district_reserve "
				"for KNI_INFO_%d failed!\n", i);
			goto kni_mm_district_reserve_failed;
		}

		it->m_ctx = mz;

		/* TX RING */
		snprintf(obj_name, OBJNAMSIZ, "kni_tx_%d", i);
		mz = kni_mm_district_reserve(obj_name, KNI_FIFO_SIZE,
					 SOCKET_ID_ANY, 0);
		if (!mz) {
			ODP_ERR("kni_mm_district_reserve for "
				"kni_tx_%d failed!\n", i);
			goto kni_mm_district_reserve_failed;
		}

		it->m_tx_q = mz;

		/* RX RING */
		snprintf(obj_name, OBJNAMSIZ, "kni_rx_%d", i);
		mz = kni_mm_district_reserve(obj_name, KNI_FIFO_SIZE,
					 SOCKET_ID_ANY, 0);
		if (!mz) {
			ODP_ERR("kni_mm_district_reserve for "
				"kni_rx_%d failed!\n", i);
			goto kni_mm_district_reserve_failed;
		}

		it->m_rx_q = mz;

		/* ALLOC RING */
		snprintf(obj_name, OBJNAMSIZ, "kni_alloc_%d", i);
		mz = kni_mm_district_reserve(obj_name, KNI_FIFO_SIZE,
					 SOCKET_ID_ANY, 0);
		if (!mz) {
			ODP_ERR("kni_mm_district_reserve for "
				"kni_alloc_%d failed!\n", i);
			goto kni_mm_district_reserve_failed;
		}

		it->m_alloc_q = mz;

		/* FREE RING */
		snprintf(obj_name, OBJNAMSIZ, "kni_free_%d", i);
		mz = kni_mm_district_reserve(obj_name, KNI_FIFO_SIZE,
					 SOCKET_ID_ANY, 0);
		if (!mz) {
			ODP_ERR("kni_mm_district_reserve for "
				"kni_free_%d failed!\n", i);
			goto kni_mm_district_reserve_failed;
		}

		it->m_free_q = mz;

		/* Request RING */
		snprintf(obj_name, OBJNAMSIZ, "kni_req_%d", i);
		mz = kni_mm_district_reserve(obj_name, KNI_FIFO_SIZE,
					 SOCKET_ID_ANY, 0);
		if (!mz) {
			ODP_ERR("kni_mm_district_reserve for "
				"kni_req_%d failed!\n", i);
			goto kni_mm_district_reserve_failed;
		}

		it->m_req_q = mz;

		/* Response RING */
		snprintf(obj_name, OBJNAMSIZ, "kni_resp_%d", i);
		mz = kni_mm_district_reserve(obj_name, KNI_FIFO_SIZE,
					 SOCKET_ID_ANY, 0);
		if (!mz) {
			ODP_ERR("kni_mm_district_reserve for "
				"kni_resp_%d failed!\n", i);
			goto kni_mm_district_reserve_failed;
		}

		it->m_resp_q = mz;

		/* Req/Resp sync mem area */
		snprintf(obj_name, OBJNAMSIZ, "kni_sync_%d", i);
		mz = kni_mm_district_reserve(obj_name, KNI_FIFO_SIZE,
					 SOCKET_ID_ANY, 0);
		if (!mz) {
			ODP_ERR("kni_mm_district_reserve for "
				"kni_sync_%d failed!\n", i);
			goto kni_mm_district_reserve_failed;
		}

		it->m_sync_addr = mz;

		if ((i + 1) == max_kni_ifaces) {
			it->next = NULL;
			kni_mm_district_pool.free_tail = it;
		} else {
			it->next = &kni_mm_district_pool.slots[i + 1];
		}
	}

	return 0;

kni_mm_district_reserve_failed:
	free(kni_mm_district_pool.slots);

	/* Reset general pool variables */
	kni_mm_district_pool.initialized = 0;
	kni_mm_district_pool.max_ifaces  = 0;
	kni_mm_district_pool.free = NULL;
	kni_mm_district_pool.free_tail = NULL;
	kni_mm_district_pool.slots = NULL;

malloc_failed:
	close(kni_fd);
	kni_fd = -1;

	ODP_ERR("Unable to allocate memory for "
		"max_kni_ifaces:%d. Increase the amount of hugepages memory\n",
		max_kni_ifaces);

	return -1;
}

/* It is deprecated and just for backward compatibility */
struct odp_kni *odp_kni_create(uint8_t		   port_id,
			       unsigned		   mbuf_size,
			       odp_pool_t	   pktmbuf_pool,
			       struct odp_kni_ops *ops)
{
	struct odp_kni_conf conf;

	memset(&conf, 0, sizeof(conf));

	if (mbuf_size < BD_BUFFER_MAX_SIZE) {
		ODP_ERR("mbuf_size(%d) err, should "
			"<= BD_BUFFER_MAX_SIZE(%d).\n",
			mbuf_size, BD_BUFFER_MAX_SIZE);
		return NULL;
	}

	snprintf(conf.name, sizeof(conf.name), "vEth%u", port_id);
	conf.group_id  = (uint16_t)port_id;
	conf.mbuf_size = mbuf_size;

	/* Save the port id for request handling */
	ops->port_id = port_id;

	return odp_kni_alloc(pktmbuf_pool, &conf, ops);
}

struct odp_kni *odp_kni_alloc(odp_pool_t		 pktmbuf_pool,
			      const struct odp_kni_conf *conf,
			      struct odp_kni_ops	*ops)
{
	int ret;
	struct odp_kni_device_info dev_info;
	struct odp_kni *ctx;
	char intf_name[ODP_KNI_NAMESIZE];
	const struct odp_mm_district *mz;
	struct odp_kni_mm_district_slot *slot = NULL;
	odp_pool_info_t info;

	if (!pktmbuf_pool || !conf || !conf->name[0])
		return NULL;

	/* Check if KNI subsystem has been initialized */
	if (kni_mm_district_pool.initialized != 1) {
		ODP_ERR("KNI subsystem has not been initialized. "
			"Invoke odp_kni_init() first\n");
		return NULL;
	}

	/* Get an available slot from the pool */
	slot = kni_mm_district_pool_alloc();
	if (!slot) {
		ODP_ERR("Cannot allocate more KNI interfaces; "
			"increase the number of max_kni_ifaces(current %d) "
			"or release unusued ones.\n",
			kni_mm_district_pool.max_ifaces);
		return NULL;
	}

	/* Recover ctx */
	ctx = slot->m_ctx->addr;
	snprintf(intf_name, ODP_KNI_NAMESIZE, "%s", conf->name);

	if (ctx->in_use) {
		ODP_ERR("KNI %s is in use\n", ctx->name);
		goto kni_in_use;
	}

	memset(ctx, 0, sizeof(struct odp_kni));
	if (ops)
		memcpy(&ctx->ops, ops, sizeof(struct odp_kni_ops));

	memset(&dev_info, 0, sizeof(dev_info));
	dev_info.bus = conf->addr.bus;
	dev_info.devid = conf->addr.devid;
	dev_info.function  = conf->addr.function;
	dev_info.vendor_id = conf->id.vendor_id;
	dev_info.device_id = conf->id.device_id;
	dev_info.core_id = conf->core_id;
	dev_info.force_bind = conf->force_bind;
	dev_info.group_id  = conf->group_id;
	dev_info.mbuf_size = conf->mbuf_size;

	snprintf(ctx->name, ODP_KNI_NAMESIZE, "%s", intf_name);
	snprintf(dev_info.name, ODP_KNI_NAMESIZE, "%s", intf_name);

	ODP_DBG("pci: %02x:%02x:%02x \t %02x:%02x\n",
		dev_info.bus, dev_info.devid, dev_info.function,
		dev_info.vendor_id, dev_info.device_id);

	/* TX RING */
	mz = slot->m_tx_q;
	ctx->tx_q = mz->addr;
	kni_fifo_init(ctx->tx_q, KNI_FIFO_COUNT_MAX);
	dev_info.tx_phys = mz->phys_addr;

	/* RX RING */
	mz = slot->m_rx_q;
	ctx->rx_q = mz->addr;
	kni_fifo_init(ctx->rx_q, KNI_FIFO_COUNT_MAX);
	dev_info.rx_phys = mz->phys_addr;

	/* ALLOC RING */
	mz = slot->m_alloc_q;
	ctx->alloc_q = mz->addr;
	kni_fifo_init(ctx->alloc_q, KNI_FIFO_COUNT_MAX);
	dev_info.alloc_phys = mz->phys_addr;

	/* FREE RING */
	mz = slot->m_free_q;
	ctx->free_q = mz->addr;
	kni_fifo_init(ctx->free_q, KNI_FIFO_COUNT_MAX);
	dev_info.free_phys = mz->phys_addr;

	/* Request RING */
	mz = slot->m_req_q;
	ctx->req_q = mz->addr;
	kni_fifo_init(ctx->req_q, KNI_FIFO_COUNT_MAX);
	dev_info.req_phys = mz->phys_addr;

	/* Response RING */
	mz = slot->m_resp_q;
	ctx->resp_q = mz->addr;
	kni_fifo_init(ctx->resp_q, KNI_FIFO_COUNT_MAX);
	dev_info.resp_phys = mz->phys_addr;

	/* Req/Resp sync mem area */
	mz = slot->m_sync_addr;
	ctx->sync_addr = mz->addr;
	dev_info.sync_va = mz->addr;
	dev_info.sync_phys = mz->phys_addr;

	/* mempool */
	ret = odp_pool_info(pktmbuf_pool, &info);
	if (ret < 0) {
		ODP_ERR("odp_pool_info failed!\n");
		goto odp_pool_info_failed;
	}

	mz = odp_mm_district_lookup(info.name);
	if (!mz) {
		ODP_ERR("odp_mm_district_lookup %s failed\n", info.name);
		goto odp_mm_district_lookup_failed;
	}

	dev_info.mbuf_va = mz->addr;
	dev_info.mbuf_phys = mz->phys_addr;
	ctx->pktmbuf_pool  = pktmbuf_pool;
	ctx->group_id  = conf->group_id;
	ctx->slot_id   = slot->id;
	ctx->mbuf_size = conf->mbuf_size;
	ret = ioctl(kni_fd, ODP_KNI_IOCTL_CREATE, &dev_info);
	if (ret < 0) {
		ODP_ERR("ioctl ODP_KNI_IOCTL_CREATE failed\n");
		goto ioctl_failed;
	}

	ctx->in_use = 1;

	/* Allocate mbufs and then put them into alloc_q */
	kni_allocate_mbufs(ctx);

	return ctx;

ioctl_failed:
odp_mm_district_lookup_failed:
odp_pool_info_failed:
kni_in_use:

	kni_mm_district_pool_release(&kni_mm_district_pool.slots[slot->id]);

	return NULL;
}

static void kni_free_fifo(struct odp_kni_fifo *fifo)
{
	int ret;
	odp_buffer_t pkt;

	do {
		ret = kni_fifo_get(fifo, (void **)&pkt, 1);
		if (ret)
			odp_buffer_free(pkt);
	} while (ret);
}

int odp_kni_release(struct odp_kni *kni)
{
	struct odp_kni_device_info dev_info;
	uint32_t slot_id;

	if (!kni || !kni->in_use)
		return -1;

	snprintf(dev_info.name, sizeof(dev_info.name), "%s", kni->name);
	if (ioctl(kni_fd, ODP_KNI_IOCTL_RELEASE, &dev_info) < 0) {
		ODP_ERR("Fail to release kni device\n");
		return -1;
	}

	/* mbufs in all fifo should be released, except request/response */
	kni_free_fifo(kni->tx_q);
	kni_free_fifo(kni->rx_q);
	kni_free_fifo(kni->alloc_q);
	kni_free_fifo(kni->free_q);

	slot_id = kni->slot_id;

	/* Memset the KNI struct */
	memset(kni, 0, sizeof(struct odp_kni));

	/* Release mm_district */
	if (slot_id > kni_mm_district_pool.max_ifaces) {
		ODP_ERR("KNI pool: corrupted slot ID: %d, max: %d\n",
			slot_id, kni_mm_district_pool.max_ifaces);
		return -1;
	}

	kni_mm_district_pool_release(&kni_mm_district_pool.slots[slot_id]);

	return 0;
}

int odp_kni_handle_request(struct odp_kni *kni)
{
	unsigned ret;
	struct odp_kni_request *req;

	if (kni == NULL)
		return -1;

	/* Get request mbuf */
	ret = kni_fifo_get(kni->req_q, (void **)&req, 1);
	if (ret != 1)
		return 0; /* It is OK of can not getting the request mbuf */

	if (req != kni->sync_addr) {
		ODP_ERR("Wrong req pointer %p\n", req);
		return -1;
	}

	/* Analyze the request and call the relevant actions for it */
	switch (req->req_id) {
	case ODP_KNI_REQ_CHANGE_MTU: /* Change MTU */
		if (kni->ops.change_mtu)
			req->result = kni->ops.change_mtu(kni->ops.port_id,
							  req->new_mtu);

		break;
	case ODP_KNI_REQ_CFG_NETWORK_IF: /* Set network interface up/down */
		if (kni->ops.config_network_if)
			req->result =
			kni->ops.config_network_if(kni->ops.port_id,
					req->if_up);

		break;
	default:
		ODP_ERR("Unknown request id %u\n", req->req_id);
		req->result = -EINVAL;
		break;
	}

	/* Construct response mbuf and put it back to resp_q */
	ret = kni_fifo_put(kni->resp_q, (void **)&req, 1);
	if (ret != 1) {
		ODP_ERR("Fail to put the muf back to resp_q\n");
		return -1; /* It is an error of can't putting the mbuf back */
	}

	return 0;
}

unsigned odp_kni_tx_burst(struct odp_kni *kni,
				odp_packet_t mbufs[], unsigned num)
{
	unsigned ret = kni_fifo_put(kni->rx_q, (void **)mbufs, num);

	/* Get mbufs from free_q and then free them */
	kni_free_mbufs(kni);

	return ret;
}

unsigned odp_kni_rx_burst(struct odp_kni *kni,
			odp_packet_t mbufs[], unsigned num)
{
	unsigned ret = kni_fifo_get(kni->tx_q, (void **)mbufs, num);

	/* If buffers removed, allocate mbufs and then put them into alloc_q */
	if (ret)
		kni_allocate_mbufs(kni);

	return ret;
}

static void kni_free_mbufs(struct odp_kni *kni)
{
	int i, ret;
	odp_packet_t pkts[MAX_MBUF_BURST_NUM];

	ret = kni_fifo_get(kni->free_q, (void **)pkts, MAX_MBUF_BURST_NUM);
	if (odp_likely(ret > 0))
		for (i = 0; i < ret; i++)
			odp_packet_free(pkts[i]);

}

static void kni_allocate_mbufs(struct odp_kni *kni)
{
	int i, ret;
	odp_packet_t pkts[MAX_MBUF_BURST_NUM];

	/* Check if pktmbuf pool has been configured */
	if (kni->pktmbuf_pool == NULL) {
		ODP_ERR("No valid mempool for allocating mbufs\n");
		return;
	}

	for (i = 0; i < MAX_MBUF_BURST_NUM; i++) {
		pkts[i] = odp_packet_alloc(kni->pktmbuf_pool,
			BD_BUFFER_MAX_SIZE);
		if (odp_unlikely(pkts[i] == NULL)) {
			/* Out of memory */
			ODP_ERR("Out of memory\n");
			break;
		}
	}

	/* No pkt mbuf alocated */
	if (i <= 0)
		return;

	ret = kni_fifo_put(kni->alloc_q, (void **)pkts, i);

	/* Check if any mbufs not put into alloc_q, and then free them */
	if (ret >= 0 && ret < i && ret < MAX_MBUF_BURST_NUM) {
		int j;

		for (j = ret; j < i; j++)
			odp_packet_free(pkts[j]);
	}
}

/* It is deprecated and just for backward compatibility */
uint8_t odp_kni_get_port_id(struct odp_kni *kni)
{
	if (!kni)
		return ~0x0;

	return kni->ops.port_id;
}

struct odp_kni *odp_kni_get(const char *name)
{
	uint32_t i;
	struct odp_kni_mm_district_slot *it;
	struct odp_kni *kni;

	/* Note: could be improved perf-wise if necessary */
	for (i = 0; i < kni_mm_district_pool.max_ifaces; i++) {
		it = &kni_mm_district_pool.slots[i];
		if (it->in_use == 0)
			continue;

		kni = it->m_ctx->addr;
		if (strncmp(kni->name, name, ODP_KNI_NAMESIZE) == 0)
			return kni;
	}

	return NULL;
}

const char *odp_kni_get_name(const struct odp_kni *kni)
{
	return kni->name;
}

/*
 * It is deprecated and just for backward compatibility.
 */
struct odp_kni *odp_kni_info_get(uint8_t port_id)
{
	char name[ODP_MEMZONE_NAMESIZE];

	if (port_id >= ODP_MAX_ETHPORTS)
		return NULL;

	snprintf(name, ODP_MEMZONE_NAMESIZE, "vEth%u", port_id);

	return odp_kni_get(name);
}

static enum kni_ops_status kni_check_request_register(struct odp_kni_ops *ops)
{
	/* check if KNI request ops has been registered*/
	if (NULL == ops)
		return KNI_REQ_NO_REGISTER;

	if ((NULL == ops->change_mtu) && (NULL == ops->config_network_if))
		return KNI_REQ_NO_REGISTER;

	return KNI_REQ_REGISTERED;
}

int odp_kni_register_handlers(struct odp_kni *kni, struct odp_kni_ops *ops)
{
	enum kni_ops_status req_status;

	if (NULL == ops) {
		ODP_ERR("Invalid KNI request operation.\n");
		return -1;
	}

	if (NULL == kni) {
		ODP_ERR("Invalid kni info.\n");
		return -1;
	}

	req_status = kni_check_request_register(&kni->ops);
	if (KNI_REQ_REGISTERED == req_status) {
		ODP_ERR("The KNI request operation has already registered.\n");
		return -1;
	}

	memcpy(&kni->ops, ops, sizeof(struct odp_kni_ops));
	return 0;
}

int odp_kni_unregister_handlers(struct odp_kni *kni)
{
	if (NULL == kni) {
		ODP_ERR("Invalid kni info.\n");
		return -1;
	}

	kni->ops.change_mtu = NULL;
	kni->ops.config_network_if = NULL;
	return 0;
}

void odp_kni_close(void)
{
	/* Reset general pool variables */
	kni_mm_district_pool.initialized = 0;
	kni_mm_district_pool.max_ifaces  = 0;
	kni_mm_district_pool.free = NULL;
	kni_mm_district_pool.free_tail = NULL;

	/* Free slot objects */
	free(kni_mm_district_pool.slots);

	if (kni_fd < 0)
		return;

	close(kni_fd);
	kni_fd = -1;
}
