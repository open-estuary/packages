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

#include <odp_queue_internal.h>
#include <odp/packet_io.h>
#include <odp_packet_io_internal.h>
#include <odp_packet_io_queue.h>
#include <odp/packet.h>
#include <odp_packet_internal.h>
#include <odp_classification_internal.h>
#include <odp_internal.h>
#include <odp/spinlock.h>
#include <odp/shared_memory.h>
#include <odp_packet_socket.h>
#include <odp/config.h>

#include <odp_schedule_internal.h>
#include <odp_debug_internal.h>
#include <odp_crypto_internal.h>
#include <odp_acc_dev_internal.h>
#include <odp_acc_io_queue.h>

#include <string.h>
#include <sys/ioctl.h>

/* #include <linux/if_arp.h> */
#include <ifaddrs.h>
#include <errno.h>

int odp_acc_inq_setdef(odp_acc_dev_handle id, odp_queue_t queue)
{
	struct accio_entry *io_entry;
	queue_entry_t *qentry;
	odp_pktio_t   *pkt_id = NULL;

	if ((!odp_handle_dev_ptr[id]) || (queue == ODP_QUEUE_INVALID))
		return -1;

	qentry = queue_to_qentry(queue);

	if (qentry->s.type != ODP_QUEUE_TYPE_ACCIN)
		return -1;

	io_entry = &odp_handle_dev_ptr[id]->s;
	odp_spinlock_lock(&io_entry->lock);
	io_entry->inq_default = queue;
	odp_spinlock_unlock(&io_entry->lock);

	switch (qentry->s.type) {
	/* Change to ODP_QUEUE_TYPE_POLL when ODP_QUEUE_TYPE_PKTIN is removed */
	case ODP_QUEUE_TYPE_ACCIN:

		/* User polls the input queue */
		queue_lock(qentry);
		qentry->s.accin = id;
		queue_unlock(qentry);

		/* Uncomment when ODP_QUEUE_TYPE_PKTIN is removed
		    break;
		   case ODP_QUEUE_TYPE_SCHED:
		 */

		/* Packet input through the scheduler */
		*pkt_id = 0;
		if (schedule_pktio_start(*pkt_id, ODP_SCHED_PRIO_LOWEST)) {
			ODP_ERR("Schedule pktio start failed.\n");
			return -1;
		}

		break;
	default:
		ODP_ABORT("Bad queue type\n");
	}

	return 0;
}

int odp_acc_inq_remdef(odp_acc_dev_handle id)
{
	return odp_acc_inq_setdef(id, ODP_QUEUE_INVALID);
}

odp_queue_t odp_acc_inq_getdef(odp_acc_dev_handle id)
{
	if (!odp_handle_dev_ptr[id])
		return ODP_QUEUE_INVALID;

	return odp_handle_dev_ptr[id]->s.inq_default;
}

odp_queue_t odp_acc_outq_getdef(odp_acc_dev_handle id)
{
	if (!odp_handle_dev_ptr[id])
		return ODP_QUEUE_INVALID;

	return odp_handle_dev_ptr[id]->s.outq_default;
}

int accin_enqueue(queue_entry_t *qentry, odp_buffer_hdr_t *buf_hdr)
{
	/* Use default action */
	return queue_enq(qentry, buf_hdr);
}

odp_buffer_hdr_t *accin_dequeue(queue_entry_t *qentry)
{
	odp_buffer_hdr_t *buf_hdr;

	buf_hdr = queue_deq(qentry);

	if (!buf_hdr) {
		odp_buffer_t buf;
		odp_buffer_hdr_t *tmp_hdr_tbl[QUEUE_MULTI_MAX];
		uint32_t pkts, i;
		odp_event_t completion_event;
		union odp_acc_op_result result[QUEUE_MULTI_MAX];

		pkts = odp_acc_dev_recv_burst(qentry->s.accin, &result[0],
					      QUEUE_MULTI_MAX);

		if (pkts > 0) {
			for (i = 0; i < pkts; i++) {
				completion_event = odp_packet_to_event(result[i].result.pkt);
				buf = odp_buffer_from_event(completion_event);
				_odp_buffer_type_set(buf, ODP_EVENT_CRYPTO_COMPL);
				tmp_hdr_tbl[i] = odp_buf_to_hdr(buf);

				odp_acc_result_to_compl(completion_event, &result[i]);
			}

			if (i > 1)
				queue_enq_multi(qentry, &tmp_hdr_tbl[1], i - 1);

			buf_hdr = tmp_hdr_tbl[0];
		}
	}

	return buf_hdr;
}

int accin_enq_multi(queue_entry_t *qentry, odp_buffer_hdr_t *buf_hdr[], int num)
{
	/* Use default action */
	return queue_enq_multi(qentry, buf_hdr, num);
}

int accin_deq_multi(queue_entry_t *qentry, odp_buffer_hdr_t *buf_hdr[], int num)
{
	int nbr;

	nbr = queue_deq_multi(qentry, buf_hdr, num);

	if (nbr < num) {
		odp_buffer_t buf;
		odp_buffer_hdr_t *tmp_hdr_tbl[QUEUE_MULTI_MAX];
		uint32_t pkts, i;
		odp_event_t completion_event;
		union odp_acc_op_result result[QUEUE_MULTI_MAX];

		pkts = odp_acc_dev_recv_burst(qentry->s.accin, &result[0],
					      QUEUE_MULTI_MAX);

		if (pkts > 0) {
			for (i = 0; i < pkts; i++) {
				completion_event = odp_packet_to_event(result[i].result.pkt);
				buf = odp_buffer_from_event(completion_event);
				_odp_buffer_type_set(buf, ODP_EVENT_CRYPTO_COMPL);
				tmp_hdr_tbl[i] = odp_buf_to_hdr(buf);

				odp_acc_result_to_compl(completion_event, &result[i]);
			}

			queue_enq_multi(qentry, tmp_hdr_tbl, pkts);
		}
	}

	return nbr;
}
