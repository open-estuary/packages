/* Copyright (c) 2014, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */


/**
 * @file
 *
 * ODP prime
 */

#ifndef ODP_API_PRIME_H_
#define ODP_API_PRIME_H_

#ifdef __cplusplus
extern "C" {
#endif


enum odp_prime_op {
	ODP_PRIME_OP_TEST,
};


typedef struct odp_prime_op_result {
	odp_crypto_session_t session;    /**< Session handle from creation   */
	odp_bool_t  ok;                  /**< Request completed successfully */
	void *ctx;                       /**< User context from request */

    enum odp_boolean test_passed;
	odp_crypto_compl_status_t status;  /**< status */
} odp_prime_op_result_t;




typedef struct odp_prime_session_params {
    odp_acc_dev_handle dev_handle;     /**< device handle */
	enum odp_prime_op op;
	enum odp_crypto_op_mode pref_mode;   /**< Preferred sync vs async */
	odp_queue_t compl_queue;           /**< Async mode completion event queue */
	odp_pool_t output_pool;            /**< Output buffer pool */

    void (*odp_asym_compl_pfn)(odp_crypto_session_t session, odp_prime_op_result_t *result); /* 注册加解密回调处理函数 */
} odp_prime_session_params_t;



typedef struct odp_prime_op_params {
	odp_crypto_session_t session;     /**< Session handle from creation */
	void *ctx;                        /**< User context */

	odp_packet_t base_G;                /* 参数G，满足0<G<P */

    odp_packet_t prime_candidate;           /* 待测试素数 */
    enum odp_boolean perform_gcd_test;      /* 表示是否执行GCD素数测试 */
    enum odp_boolean perform_fermat_test;   /* 表示是否执行fermat素数测试 */
    uint32_t num_miller_rabin_rounds;       /* MR环数，决定素性测试的准确性 */
    odp_packet_t miller_rabin_random_input; /* MR环，其长度大小为待测素数长度(如果小于64bit则为64bit)乘以MR环数 */
    enum odp_boolean perform_lucas_test;    /* 表示Lucas素数判断是否执行 */
} odp_prime_op_params_t;


/*****************************************************************************
 Function     : odp_prime_session_create
 Description  : 基于设备创建会话
 Input        : odp_prime_session_params_t *params:会话参数
 Output       :
                odp_crypto_session_t *session:会话句柄
                enum odp_crypto_ses_create_err *status:会话失败原因
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_prime_session_create(odp_prime_session_params_t *params,
			  odp_crypto_session_t *session,
			  enum odp_crypto_ses_create_err *status);


/*****************************************************************************
 Function     : odp_prime_session_destroy
 Description  : 删除会话
 Input        : odp_crypto_session_t session:会话句柄
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_prime_session_destroy(odp_crypto_session_t session);




/*****************************************************************************
 Function     : odp_prime_operation
 Description  : PRIME操作
 Input        : odp_prime_op_params_t *params:操作参数
 Output       :
                odp_bool_t *posted:同步/异步标志
                odp_prime_op_result_t *result:操作结果
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_prime_operation(odp_prime_op_params_t *params,
		     odp_bool_t *posted,
		     odp_prime_op_result_t *result);




/*****************************************************************************
 Function     : odp_prime_operation_burst
 Description  : PRIME多包操作(同一个会话)
 Input        : odp_prime_op_params_t *params_tbl[]:操作参数
                uint16_t nb_pkts:操作个数
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_prime_operation_burst(odp_prime_op_params_t *params_tbl[], uint16_t nb_pkts);





#ifdef __cplusplus
}
#endif

#endif




