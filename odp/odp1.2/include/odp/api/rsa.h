/* Copyright (c) 2014, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */


/**
 * @file
 *
 * ODP rsa
 */

#ifndef ODP_API_RSA_H_
#define ODP_API_RSA_H_

#ifdef __cplusplus
extern "C" {
#endif



enum odp_rsa_private_key_rep_type
{
	ODP_RSA_PRIVATE_KEY_REP_TYPE_1 = 1,   /* RSA第一方案适用的私钥 */
	ODP_RSA_PRIVATE_KEY_REP_TYPE_2        /* RSA第二方案适用的私钥 */
};


typedef struct odp_rsa_private_key_rep1
{
	odp_packet_t modulus_n;           /* RSA加密算法中密钥N */
	odp_packet_t private_exponent_d;  /* RSA加密算法中密钥D */
}odp_rsa_private_key_rep1_t;


typedef struct odp_rsa_private_key_rep2
{
	odp_packet_t prime_1p;          /* 大素数1P */
	odp_packet_t prime_2q;          /* 大素数2Q */
    odp_packet_t exponent_1dp;      /* 密钥1Dp  */
	odp_packet_t exponent_2dq;      /* 密钥2Dp  */
    odp_packet_t coefficient_qinv;  /* 密钥QInv */
}odp_rsa_private_key_rep2_t;


typedef struct odp_rsa_private_key
{
    enum odp_rsa_private_key_rep_type rep_type;      /* 方案序号，不同的序号对应着不同的私钥类型 */
    odp_rsa_private_key_rep1_t private_key_rep1;     /* 在PKCS＃1 V2.1规范中按第一个方案定义的RSA私钥 */
    odp_rsa_private_key_rep2_t private_key_rep2;     /* 在PKCS＃1 V2.1规范中按第二方案定义的RSA私钥   */
}odp_rsa_private_key_t;


typedef struct odp_rsa_public_key
{
    odp_packet_t modulus_n;          /* RSA加密算法中公钥N */
	odp_packet_t public_exponent_e;  /* RSA加密算法中公钥E */
}odp_rsa_public_key_t;



typedef struct odp_rsa_op_result {
	odp_crypto_session_t session;      /**< Session handle from creation */
	odp_bool_t  ok;                  /**< Request completed successfully */
	void *ctx;                       /**< User context from request */
	odp_packet_t pkt;                /**< Output packet */
    odp_rsa_private_key_t private_key;
    odp_rsa_public_key_t public_key;
	odp_crypto_compl_status_t status;  /**< status */
} odp_rsa_op_result_t;




typedef struct odp_rsa_session_params {
    odp_acc_dev_handle dev_handle;     /**< device handle */
	enum odp_rsa_op op;                /**< Encode versus decode */
	enum odp_crypto_op_mode pref_mode;   /**< Preferred sync vs async */
	odp_queue_t compl_queue;           /**< Async mode completion event queue */
	odp_pool_t output_pool;            /**< Output buffer pool */

    void (*odp_asym_compl_pfn)(odp_crypto_session_t session, odp_rsa_op_result_t *result); /* 注册加解密回调处理函数 */
} odp_rsa_session_params_t;



typedef struct odp_rsa_op_params {
	odp_crypto_session_t session;     /**< Session handle from creation */
	void *ctx;                      /**< User context */
	odp_packet_t pkt;               /**< Input packet buffer */
	odp_packet_t out_pkt;           /**< Output packet buffer */

    odp_rsa_private_key_t private_key;
    odp_rsa_public_key_t public_key;
} odp_rsa_op_params_t;


/*****************************************************************************
 Function     : odp_rsa_session_create
 Description  : 基于设备创建会话
 Input        : odp_rsa_session_params_t *params:会话参数
 Output       :
                odp_crypto_session_t *session:会话句柄
                enum odp_crypto_ses_create_err *status:会话失败原因
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_rsa_session_create(odp_rsa_session_params_t *params,
			  odp_crypto_session_t *session,
			  enum odp_crypto_ses_create_err *status);


/*****************************************************************************
 Function     : odp_rsa_session_destroy
 Description  : 删除会话
 Input        : odp_crypto_session_t session:会话句柄
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_rsa_session_destroy(odp_crypto_session_t session);




/*****************************************************************************
 Function     : odp_rsa_operation
 Description  : RSA操作
 Input        : odp_rsa_op_params_t *params:操作参数
 Output       :
                odp_bool_t *posted:同步/异步标志
                odp_rsa_op_result_t *result:操作结果
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_rsa_operation(odp_rsa_op_params_t *params,
		     odp_bool_t *posted,
		     odp_rsa_op_result_t *result);




/*****************************************************************************
 Function     : odp_rsa_operation_burst
 Description  : RSA多包操作(同一个会话)
 Input        : odp_rsa_op_params_t *params_tbl[]:操作参数
                uint16_t nb_pkts:操作个数
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_rsa_operation_burst(odp_rsa_op_params_t *params_tbl[], uint16_t nb_pkts);




#ifdef __cplusplus
}
#endif

#endif

