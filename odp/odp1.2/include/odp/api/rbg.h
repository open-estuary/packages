/* Copyright (c) 2014, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */


/**
 * @file
 *
 * ODP rbg
 */

#ifndef ODP_API_RBG_H_
#define ODP_API_RBG_H_

#ifdef __cplusplus
extern "C" {
#endif


enum odp_rbg_op {
	ODP_RBG_OP_DRBG_GEN,
    ODP_RBG_OP_DRBG_RESEED,
    ODP_RBG_OP_NRBG_ENTROPY
};


enum odp_rbg_sec_strength {
    ODP_RBG_SEC_STRENGTH_112 = 1,
    ODP_RBG_SEC_STRENGTH_128,
    ODP_RBG_SEC_STRENGTH_192,
    ODP_RBG_SEC_STRENGTH_256
};



typedef struct odp_rbg_op_result {
	odp_crypto_session_t session;    /**< Session handle from creation   */
	odp_bool_t  ok;                  /**< Request completed successfully */
	void *ctx;                       /**< User context from request */
    odp_packet_t out_random_data;

	odp_crypto_compl_status_t status;  /**< status */
} odp_rbg_op_result_t;




typedef struct odp_rbg_session_params {
    odp_acc_dev_handle dev_handle;     /**< device handle */
	enum odp_rbg_op op;
	enum odp_crypto_op_mode pref_mode;   /**< Preferred sync vs async */
	odp_queue_t compl_queue;           /**< Async mode completion event queue */
	odp_pool_t output_pool;            /**< Output buffer pool */

    odp_packet_t personalization_string;
    enum odp_rbg_sec_strength sec_strength;  /* 安全强度 */
    enum odp_boolean prediction_resistance_required;

    void (*odp_asym_compl_pfn)(odp_crypto_session_t session, odp_rbg_op_result_t *result); /* 注册加解密回调处理函数 */
} odp_rbg_session_params_t;



typedef struct odp_rbg_op_params {
	odp_crypto_session_t session;     /**< Session handle from creation */
	void *ctx;                      /**< User context */

    uint32_t length_in_bytes;                /* 产生请求数的长度 */
    enum odp_rbg_sec_strength sec_strength;  /* 安全强度 */
    enum odp_boolean prediction_resistance_required;
    odp_packet_t additional_input;           /* 额外输入 */

    odp_packet_t out_random_data;
} odp_rbg_op_params_t;


/*****************************************************************************
 Function     : odp_rbg_session_create
 Description  : 基于设备创建会话
 Input        : odp_rbg_session_params_t *params:会话参数
 Output       :
                odp_crypto_session_t *session:会话句柄
                enum odp_crypto_ses_create_err *status:会话失败原因
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_rbg_session_create(odp_rbg_session_params_t *params,
			  odp_crypto_session_t *session,
			  enum odp_crypto_ses_create_err *status);


/*****************************************************************************
 Function     : odp_rbg_session_destroy
 Description  : 删除会话
 Input        : odp_crypto_session_t session:会话句柄
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_rbg_session_destroy(odp_crypto_session_t session);




/*****************************************************************************
 Function     : odp_rbg_operation
 Description  : RBG操作
 Input        : odp_rbg_op_params_t *params:操作参数
 Output       :
                odp_bool_t *posted:同步/异步标志
                odp_rbg_op_result_t *result:操作结果
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_rbg_operation(odp_rbg_op_params_t *params,
		     odp_bool_t *posted,
		     odp_rbg_op_result_t *result);




/*****************************************************************************
 Function     : odp_rbg_operation_burst
 Description  : RBG多包操作(同一个会话)
 Input        : odp_rbg_op_params_t *params_tbl[]:操作参数
                uint16_t nb_pkts:操作个数
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_rbg_operation_burst(odp_rbg_op_params_t *params_tbl[], uint16_t nb_pkts);





#ifdef __cplusplus
}
#endif

#endif


