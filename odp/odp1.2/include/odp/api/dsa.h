/* Copyright (c) 2014, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */


/**
 * @file
 *
 * ODP dsa
 */

#ifndef ODP_API_DSA_H_
#define ODP_API_DSA_H_

#ifdef __cplusplus
extern "C" {
#endif


enum odp_dsa_op {
	ODP_DSA_OP_GEN_G,
    ODP_DSA_OP_GEN_P,
    ODP_DSA_OP_GEN_Y,
    ODP_DSA_OP_SIGN_R,
    ODP_DSA_OP_SIGN_S,
    ODP_DSA_OP_SIGN_RS,
    ODP_DSA_OP_VERIFY_RS
};


typedef struct odp_dsa_op_result {
	odp_crypto_session_t session;    /**< Session handle from creation */
	odp_bool_t  ok;                  /**< Request completed successfully */
	void *ctx;                       /**< User context from request */
	odp_packet_t R;                /* R签名 */
	odp_packet_t S;                /* S签名 */

    odp_packet_t P;                /* 素数P */
    odp_packet_t G;                /* 参数G，满足1<G<P*/
    odp_packet_t Y;                /* 参数Y */

    enum odp_boolean protocol_status;
    enum odp_boolean verify_status;
	odp_crypto_compl_status_t status;  /**< status */
} odp_dsa_op_result_t;




typedef struct odp_dsa_session_params {
    odp_acc_dev_handle dev_handle;     /**< device handle */
	enum odp_dsa_op op;
	enum odp_crypto_op_mode pref_mode;   /**< Preferred sync vs async */
	odp_queue_t compl_queue;           /**< Async mode completion event queue */
	odp_pool_t output_pool;            /**< Output buffer pool */

    void (*odp_asym_compl_pfn)(odp_crypto_session_t session, odp_dsa_op_result_t *result); /* 注册加解密回调处理函数 */
} odp_dsa_session_params_t;



typedef struct odp_dsa_op_params {
	odp_crypto_session_t session;     /**< Session handle from creation */
	void *ctx;                      /**< User context */
	odp_packet_t R;                /* R签名 */
	odp_packet_t S;                /* S签名 */

    odp_packet_t Z;                /* 待签名明文的hash值 */
    odp_packet_t K;                /* 参数K，满足0<K<Q */
    odp_packet_t H;                /* 参数H */
    odp_packet_t Q;                /* 参数Q */
    odp_packet_t X;                /* 参数X，满足0<X<Q  */
    odp_packet_t P;                /* 素数P */
    odp_packet_t G;                /* 参数G，满足1<G<P*/
    odp_packet_t Y;                /* 参数Y */
} odp_dsa_op_params_t;


/*****************************************************************************
 Function     : odp_dsa_session_create
 Description  : 基于设备创建会话
 Input        : odp_dsa_session_params_t *params:会话参数
 Output       :
                odp_crypto_session_t *session:会话句柄
                enum odp_crypto_ses_create_err *status:会话失败原因
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_dsa_session_create(odp_dsa_session_params_t *params,
			  odp_crypto_session_t *session,
			  enum odp_crypto_ses_create_err *status);


/*****************************************************************************
 Function     : odp_dsa_session_destroy
 Description  : 删除会话
 Input        : odp_crypto_session_t session:会话句柄
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_dsa_session_destroy(odp_crypto_session_t session);




/*****************************************************************************
 Function     : odp_dsa_operation
 Description  : DSA操作
 Input        : odp_dsa_op_params_t *params:操作参数
 Output       :
                odp_bool_t *posted:同步/异步标志
                odp_dsa_op_result_t *result:操作结果
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_dsa_operation(odp_dsa_op_params_t *params,
		     odp_bool_t *posted,
		     odp_dsa_op_result_t *result);




/*****************************************************************************
 Function     : odp_dsa_operation_burst
 Description  : DSA多包操作(同一个会话)
 Input        : odp_dsa_op_params_t *params_tbl[]:操作参数
                uint16_t nb_pkts:操作个数
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_dsa_operation_burst(odp_dsa_op_params_t *params_tbl[], uint16_t nb_pkts);





#ifdef __cplusplus
}
#endif

#endif


