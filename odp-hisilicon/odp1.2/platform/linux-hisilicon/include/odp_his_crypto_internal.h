/* Copyright (c) 2014, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */

#ifndef ODP_HIS_CRYPTO_INTERNAL_H_
#define ODP_HIS_CRYPTO_INTERNAL_H_

#ifdef __cplusplus
extern "C" {
#endif

#define ODP_SSN_MAX_NUM	    (16 * 1024)
#define ODP_DEV_SSN_MAX_NUM (1024)

struct odp_session_mng {
	odp_acc_dev_handle	dev_handle;  /**< device handle */
	odp_acc_session_t	acc_session;
	enum odp_crypto_op_mode pref_mode;   /**< Preferred sync vs async */
	odp_queue_t		compl_queue; /**< Async mode completion event queue */
	odp_pool_t		output_pool; /**< Output buffer pool */

	union {
		void (*odp_acc_compl_pfn)(odp_crypto_session_t session, void *result);
		void (*odp_crypto_compl_pfn)(odp_crypto_session_t session, odp_crypto_op_result_t *result);
		void (*odp_rsa_compl_pfn)(odp_crypto_session_t session, odp_rsa_op_result_t *result);
		void (*odp_dsa_compl_pfn)(odp_crypto_session_t session, odp_dsa_op_result_t *result);
		void (*odp_dh_compl_pfn)(odp_crypto_session_t session, odp_dh_op_result_t *result);
		void (*odp_rbg_compl_pfn)(odp_crypto_session_t session, odp_rbg_op_result_t *result);
		void (*odp_key_compl_pfn)(odp_crypto_session_t session, odp_key_op_result_t *result);
		void (*odp_prime_compl_pfn)(odp_crypto_session_t session, odp_prime_op_result_t *result);
	};

	odp_atomic_u64_t ssn_pkt_num;
};

int odp_crypto_init(void);

#ifdef __cplusplus
}
#endif
#endif
