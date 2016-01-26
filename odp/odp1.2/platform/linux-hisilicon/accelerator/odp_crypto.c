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
 *       distribution
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

#include <odp/config.h>
#include <odp/crypto.h>
#include <odp_internal.h>
#include <odp/atomic.h>
#include <odp/spinlock.h>
#include <odp/sync.h>
#include <odp/debug.h>
#include <odp/align.h>
#include <odp/shared_memory.h>
#include <odp_crypto_internal.h>
#include <odp_debug_internal.h>
#include <odp/hints.h>
#include <odp/random.h>
#include <odp_packet_internal.h>
#include <odp/accdev.h>
#include <odp_acc_dev_internal.h>
#include <odp_acc_comm.h>

#include <openssl/des.h>
#include <openssl/rand.h>
#include <openssl/hmac.h>
#include <openssl/evp.h>

#include <string.h>
#include <unistd.h>

static struct odp_packet_clarrify_mng clarrify_result[ODP_SSN_MAX_NUM];
odp_pool_t odp_acc_pool;

int32_t odp_random_data(uint8_t *buf, int32_t len, odp_bool_t use_entropy ODP_UNUSED)
{
	return 0;
}

int odp_crypto_check_alg_is_support(odp_crypto_session_params_t *params, int *is_support)
{
	struct odp_acc_caps *caps;

	caps = &odp_handle_dev_ptr[params->dev_handle]->data.caps;

	if (params->cipher_alg >= ODP_CIPHER_ALG_NUM) {
		printf("Invalid cipher_alg = %u!\n", params->cipher_alg);
		return (-1);
	}

	if (params->auth_alg >= ODP_AUTH_ALG_NUM) {
		printf("Invalid auth_alg = %u!\n", params->auth_alg);
		return (-1);
	}

	if ((params->auth_alg == ODP_AUTH_ALG_NULL) && (params->cipher_alg == ODP_CIPHER_ALG_NULL)) {
		printf("Invalid cipher_alg =%u. auth_alg =%u!\n", params->cipher_alg, params->auth_alg);
		return (-1);
	}

	if (params->cipher_alg > ODP_CIPHER_ALG_NULL)
		if ((caps->cy.cipher_feature & (((uint64_t)0x1) << params->cipher_alg)) == 0) {
			*is_support = ODP_FALSE;
			return 0;
		}

	if (params->auth_alg > ODP_AUTH_ALG_NULL)
		if ((caps->cy.auth_feature & (((uint64_t)0x1) << params->auth_alg)) == 0) {
			*is_support = ODP_FALSE;
			return 0;
		}

	*is_support = ODP_TRUE;

	return 0;
}

/*create crypto session*/
int odp_crypto_session_create(odp_crypto_session_params_t    *params,
			      odp_crypto_session_t	     *session_out,
			      enum odp_crypto_ses_create_err *status)
{
	int iret;
	odp_acc_session_t acc_session;
	odp_crypto_session_t session;
	int is_support;
	struct odp_acc_session_params_t session_param;

	/* Default to successful result */
	*status = ODP_CRYPTO_SES_CREATE_ERR_NONE;
	if (odp_unlikely(!params)) {
		printf("params is null!\n");
		*status = ODP_CRYPTO_SES_CREATE_ERR_ENOMEM;
		return (-1);
	}

	if (odp_unlikely((params->dev_handle == 0) || (params->dev_handle >= ODP_ACC_HANDLE_NUM))) {
		printf("Invalid dev_handle = %lu!\n", params->dev_handle);
		*status = ODP_CRYPTO_SES_CREATE_ERR_ENOMEM;
		return (-1);
	}

	if (odp_unlikely(!odp_handle_dev_ptr[params->dev_handle])) {
		printf("dev is not find!\n");
		*status = ODP_CRYPTO_SES_CREATE_ERR_ENOMEM;
		return (-1);
	}

	iret = odp_crypto_check_alg_is_support(params, &is_support);
	if (odp_unlikely(iret)) {
		*status = ODP_CRYPTO_SES_CREATE_ERR_ENOMEM;
		printf("call odp_crypto_check_alg_is_support failed!\n");
		return iret;
	}

	if (!is_support) {
		*status = ODP_CRYPTO_SES_CREATE_ERR_ENOMEM;
		printf("alg is not support!\n");
		return (-1);
	}

	session = (odp_crypto_session_t)odp_alloc_ssn();
	if ((session == 0) || (session >= ODP_SSN_MAX_NUM)) {
		*status = ODP_CRYPTO_SES_CREATE_ERR_ENOMEM;
		printf("Invalid c_session = %lu!\n", session);
		return (-1);
	}

	session_param.type   = ODP_ACC_CRYPTO_SESSION_TYPE;
	session_param.params = params;
	iret		     =
		odp_handle_dev_ptr[params->dev_handle]->dev_ops->acc_session_create_t(odp_handle_dev_ptr[params->
													 dev_handle],
										      &session_param,
										      &acc_session);
	if (odp_unlikely(iret)) {
		printf("call acc_session_create_t failed!\n");
		*status = ODP_CRYPTO_SES_CREATE_ERR_ENOMEM;
		odp_free_ssn((odp_acc_session_t)session);
		return iret;
	}

	if (odp_unlikely(acc_session >= ODP_DEV_SSN_MAX_NUM)) {
		printf("Invalid session =%lu!\n", acc_session);
		*status = ODP_CRYPTO_SES_CREATE_ERR_ENOMEM;
		odp_free_ssn((odp_acc_session_t)session);
		return (-1);
	}

	/* odp_atomic_inc_u64(&odp_handle_dev_ptr[params->dev_handle]->dev_sessin_num); */

	odp_ssn_mng[session].dev_handle		  = params->dev_handle;
	odp_ssn_mng[session].acc_session	  = acc_session;
	odp_ssn_mng[session].pref_mode		  = params->pref_mode;
	odp_ssn_mng[session].compl_queue	  = params->compl_queue;
	odp_ssn_mng[session].odp_crypto_compl_pfn = params->odp_crypto_compl_pfn;

	/* odp_atomic_store_u64(&odp_ssn_mng[session].ssn_pkt_num, 0);
	    odp_atomic_init_u64(&odp_ssn_mng[session].ssn_pkt_num, 0); */

	odp_ssn_invert_mng[params->dev_handle][acc_session] = session;

	*session_out = session;
	return 0;
}

/*destory crypto session*/
int odp_crypto_session_destroy(odp_crypto_session_t session)
{
	int iret;
	int i = 0;

	if (odp_unlikely((session == 0) || (session >= ODP_SSN_MAX_NUM))) {
		printf("Invalid session = %lu!\n", session);
		return (-1);
	}

	while (odp_atomic_load_u64(&odp_ssn_mng[session].ssn_pkt_num) != 0) {
		odp_crypto_dev_poll(odp_ssn_mng[session].dev_handle, 8);

		if (odp_ssn_mng[session].compl_queue)
			odp_crypto_comp_queue_poll(odp_ssn_mng[session].dev_handle,
						   odp_ssn_mng[session].compl_queue, 8);

		usleep(100);

		i++;
		if (i >= 100) {
			printf("resource recycle failed!\n");
			return (-1);
		}
	}

	iret =
		odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle]->dev_ops->acc_session_delete_t(
			odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle],
			odp_ssn_mng[session].acc_session);
	if (odp_unlikely(iret)) {
		printf("call acc_session_delete_t failed!\n");
		return iret;
	}

	odp_atomic_dec_u64(&odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle]->dev_sessin_num);

	odp_free_ssn((odp_acc_session_t)session);

	odp_ssn_invert_mng[odp_ssn_mng[session].dev_handle][odp_ssn_mng[session].acc_session] = 0;
	memset(&odp_ssn_mng[session], 0, sizeof(struct odp_session_mng));

	return 0;
}

odp_crypto_compl_t odp_crypto_compl_from_event(odp_event_t ev)
{
	/* This check not mandated by the API specification */
	if (odp_event_type(ev) != ODP_EVENT_CRYPTO_COMPL)
		ODP_ABORT("Event not a crypto completion");

	return (odp_crypto_compl_t)ev;
}

odp_event_t odp_crypto_compl_to_event(odp_crypto_compl_t completion_event)
{
	return (odp_event_t)completion_event;
}

void odp_crypto_compl_result(odp_crypto_compl_t	     completion_event,
			     odp_crypto_op_result_t *result)
{
	odp_event_t ev = odp_crypto_compl_to_event(completion_event);
	odp_acc_generic_op_result_t *op_result;

	op_result = get_op_result_from_event(ev);

	if (OP_RESULT_MAGIC != op_result->magic)
		ODP_ABORT();

	memcpy(result, &op_result->result, sizeof(*result));
}

void odp_crypto_compl_free(odp_crypto_compl_t completion_event ODP_UNUSED)
{
	/* We use the packet as the completion event so nothing to do here */
}

/*send crypto task on single mode*/
int odp_crypto_operation(odp_crypto_op_params_t *params,
			 odp_bool_t		*posted,
			 odp_crypto_op_result_t *result)
{
	int iret;
	odp_crypto_session_t session;
	struct odp_acc_op_params session_param;

	if (odp_unlikely(!params)) {
		printf("params is null!\n");
		return (-1);
	}

	if (odp_unlikely(!posted)) {
		printf("posted is null!\n");
		return (-1);
	}

	if (odp_unlikely((params->session == 0) || (params->session >= ODP_SSN_MAX_NUM))) {
		printf("Invalid session = %lu!\n", params->session);
		return (-1);
	}

	session		     = params->session;
	params->session	     = odp_ssn_mng[session].acc_session;
	session_param.type   = ODP_ACC_CRYPTO_SESSION_TYPE;
	session_param.params = params;
	odp_acc_dev_lock(odp_ssn_mng[session].dev_handle);
	iret =
		odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle]->tx_pkt(odp_handle_dev_ptr[odp_ssn_mng[session].
											       dev_handle],
									    &session_param);
	if (odp_unlikely(iret)) {
		odp_acc_dev_unlock(odp_ssn_mng[session].dev_handle);
		printf("call tx_pkt failed!\n");
		return iret;
	}

	odp_atomic_inc_u64(&odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle]->dev_pkt_num);
	odp_atomic_inc_u64(&odp_ssn_mng[session].ssn_pkt_num);

	if (odp_ssn_mng[session].pref_mode == ODP_CRYPTO_ASYNC) {
		/* Indicate to caller operation was async */
		*posted = 1;
	} else {
		while (1) {
			if (odp_ssn_mng[session].compl_queue) {
				odp_event_t event;
				odp_crypto_compl_t compl_event;

				event = odp_queue_deq(odp_ssn_mng[session].compl_queue);
				if (event != ODP_EVENT_INVALID) {
					compl_event = odp_crypto_compl_from_event(event);
					odp_crypto_compl_result(compl_event, result);
					odp_buffer_free(odp_buffer_from_event(event));
					odp_crypto_compl_free(compl_event);
					break;
				}
			}

			iret =
				odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle]->rx_pkt(
					odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle],
					(union odp_acc_op_result *)result);
			if (!iret) {
				result->session = odp_ssn_invert_mng[odp_ssn_mng[session].dev_handle][result->session];
				break;
			}
		}

		if (result->session != session) {
			if (!odp_ssn_mng[result->session].compl_queue) {
				odp_ssn_mng[result->session].odp_acc_compl_pfn(result->session, result);
				odp_atomic_dec_u64(&odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle]->dev_pkt_num);
				odp_atomic_dec_u64(&odp_ssn_mng[result->session].ssn_pkt_num);
			} else {
				odp_event_t completion_event;
				odp_buffer_t buffer;

				/* Linux generic will always use packet for completion event */
				do
					buffer = odp_buffer_alloc(odp_acc_pool);
				while (!buffer);

				completion_event = odp_buffer_to_event(buffer);
				_odp_buffer_type_set(buffer, ODP_EVENT_CRYPTO_COMPL);

				/* Asynchronous, build result (no HW so no errors) and send it*/
				odp_acc_result_to_compl(completion_event, (union odp_acc_op_result *)result);
				odp_queue_enq(odp_ssn_mng[result->session].compl_queue, completion_event);
			}
		} else {
			odp_atomic_dec_u64(&odp_ssn_mng[result->session].ssn_pkt_num);
		}

		*posted = 0;
	}

	odp_acc_dev_unlock(odp_ssn_mng[session].dev_handle);

	return 0;
}

/*send crypto task on burst mode*/
int odp_crypto_operation_burst(odp_crypto_op_params_t *params_tbl[], uint16_t nb_pkts)
{
	int iret;
	int i;
	odp_crypto_session_t session;
	struct odp_acc_op_params session_param[16];
	struct odp_acc_op_params *temp_session_param;

	if (odp_unlikely((nb_pkts == 0) || (nb_pkts > 16))) {
		printf("Invalid nb_pkts = %u!\n", nb_pkts);
		return (-1);
	}

	temp_session_param = &session_param[0];
	for (i = 0; i < nb_pkts; i++) {
		if (odp_unlikely(!params_tbl[i])) {
			printf("params_tbl[%d]is null!\n", i);
			return (-1);
		}

		temp_session_param->type   = ODP_ACC_CRYPTO_SESSION_TYPE;
		temp_session_param->params = params_tbl[i];
		temp_session_param++;
	}

	if (odp_unlikely((params_tbl[0]->session == 0) || (params_tbl[0]->session >= ODP_SSN_MAX_NUM))) {
		printf("Invalid session = %lu!\n", params_tbl[0]->session);
		return (-1);
	}

	session		       = params_tbl[0]->session;
	params_tbl[0]->session = odp_ssn_mng[session].acc_session;
	odp_acc_dev_lock(odp_ssn_mng[session].dev_handle);
	iret =
		odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle]->tx_pkt_burst(
			odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle], &session_param[0], nb_pkts);
	if (odp_unlikely(iret != nb_pkts)) {
		odp_acc_dev_unlock(odp_ssn_mng[session].dev_handle);
		printf("call tx_pkt_burst failed!\n");
		return iret;
	}

	/* odp_atomic_add_u64(&odp_handle_dev_ptr[odp_ssn_mng[session].dev_handle]->dev_pkt_num, nb_pkts);
	   odp_atomic_add_u64(&odp_ssn_mng[session].ssn_pkt_num, nb_pkts); */
	odp_acc_dev_unlock(odp_ssn_mng[session].dev_handle);

	return nb_pkts;
}

/*poll crypto task*/
uint32_t odp_crypto_dev_poll(odp_acc_dev_handle dev_handle, uint32_t pkts_num)
{
	int iret;
	int i = 0, j = 0;
	odp_acc_session_t session;
	union odp_acc_op_result result[16];
	union odp_acc_op_result *temp_result;

	if (odp_unlikely((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM))) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (odp_unlikely(!odp_handle_dev_ptr[dev_handle])) {
		printf("dev is not find!\n");
		return (-1);
	}

	if (odp_unlikely((pkts_num == 0) || (pkts_num > 16))) {
		printf("Invalid pkts_num = %u!\n", pkts_num);
		return (-1);
	}

	iret = odp_handle_dev_ptr[dev_handle]->rx_pkt_burst(odp_handle_dev_ptr[dev_handle], &result[0], pkts_num);

	temp_result = &result[0];
	for (i = 0; i < iret; i++) {
		temp_result->session =
			odp_ssn_invert_mng[dev_handle][temp_result->session];
		clarrify_result[temp_result->session].result[j++] = temp_result;
		clarrify_result[temp_result->session].result_num++;
		clarrify_result[temp_result->session].call_pfn_flag = 0;
		temp_result++;
	}

	session = result[0].session;
	for (i = 0; i < iret; i++) {
		if (!odp_ssn_mng[session].compl_queue) {
			if ((clarrify_result[session].result_num != 0) &&
			    (clarrify_result[session].call_pfn_flag == 0)) {
				odp_ssn_mng[session].odp_acc_compl_pfn(result[i].session,
								       (void *)&clarrify_result[session]);

				/*odp_atomic_dec_u64(&odp_handle_dev_ptr[dev_handle]->dev_pkt_num);
				   odp_atomic_sub_u64(&odp_handle_dev_ptr[dev_handle]->dev_pkt_num,
					clarrify_result[session].result_num);*/
				clarrify_result[session].result_num    = 0;
				clarrify_result[session].call_pfn_flag = 1;
			}

			/* odp_atomic_dec_u64(&odp_ssn_mng[result[i].session].ssn_pkt_num); */
		} else {
			odp_event_t completion_event;
			odp_buffer_t buffer;

			/* Linux generic will always use packet for completion event */
			do
				buffer = odp_buffer_alloc(odp_acc_pool);
			while (!buffer);

			completion_event = odp_buffer_to_event(buffer);
			_odp_buffer_type_set(buffer, ODP_EVENT_CRYPTO_COMPL);

			/* Asynchronous, build result (no HW so no errors) and send it*/
			odp_acc_result_to_compl(completion_event, &result[i]);
			odp_queue_enq(odp_ssn_mng[session].compl_queue, completion_event);
		}

		temp_result++;
	}

	return iret;
}

uint32_t odp_crypto_comp_queue_poll(odp_acc_dev_handle dev_handle, odp_queue_t compl_queue, uint32_t pkts_num)
{
	odp_event_t event;
	odp_crypto_compl_t compl_event;
	union odp_acc_op_result result;
	uint32_t num = 0;

	if (odp_unlikely(!compl_queue)) {
		printf("compl_queue is null!\n");
		return (-1);
	}

	if (odp_unlikely((pkts_num == 0) || (pkts_num > 8))) {
		printf("Invalid pkts_num = %u!\n", pkts_num);
		return (-1);
	}

	odp_crypto_dev_poll(dev_handle, pkts_num);

	/* Poll completion queue for results */
	event = odp_queue_deq(compl_queue);
	while (event != ODP_EVENT_INVALID) {
		compl_event = odp_crypto_compl_from_event(event);
		odp_crypto_compl_result(compl_event, &result.result);
		odp_buffer_free(odp_buffer_from_event(event));
		odp_crypto_compl_free(compl_event);

		odp_ssn_mng[result.session].odp_acc_compl_pfn(result.session, (void *)&result);
		odp_atomic_dec_u64(&odp_handle_dev_ptr[odp_ssn_mng[result.session].dev_handle]->dev_pkt_num);
		odp_atomic_dec_u64(&odp_ssn_mng[result.session].ssn_pkt_num);

		num++;
		if (num == pkts_num)
			break;

		event = odp_queue_deq(compl_queue);
	}

	return num;
}

int odp_crypto_init(void)
{
	odp_pool_param_t params;

	memset(odp_ssn_invert_mng, 0, sizeof(odp_ssn_invert_mng));
	memset(odp_ssn_mng, 0, sizeof(odp_ssn_mng));

	odp_spinlock_init(&odp_acc_ssn_lock);

	params.buf.size	 = sizeof(odp_acc_generic_op_result_t);
	params.buf.align = 0;
	params.buf.num	 = params.buf.size * 1024;
	params.type	 = ODP_POOL_BUFFER;

	odp_acc_pool = odp_pool_create("odp_acc", &params);

	if (odp_acc_pool == ODP_POOL_INVALID) {
		printf("call odp_pool_create failed!\n");
		return (-1);
	}

	return 0;
}

int odp_crypto_init_global(void)
{
	odp_crypto_init();
	return 0;
}

int odp_crypto_term_global(void)
{
	return 0;
}

