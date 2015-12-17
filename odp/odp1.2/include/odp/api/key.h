/* Copyright (c) 2014, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */


/**
 * @file
 *
 * ODP key
 */

#ifndef ODP_API_KEY_H_
#define ODP_API_KEY_H_

#ifdef __cplusplus
extern "C" {
#endif


enum odp_key_op {
	ODP_KEY_OP_GEN_SSL,
    ODP_KEY_OP_GEN_TLS,
    ODP_KEY_OP_GEN_TLS2
};



enum odp_key_op_derive
{
    ODP_KEY_OP_MASTER_SECRET_DERIVE = 1,
    ODP_KEY_OP_KEY_MATERIAL_DERIVE,
    ODP_KEY_OP_CLIENT_FINISHED_DERIVE,
    ODP_KEY_OP_SERVER_FINISHED_DERIVE,
    ODP_KEY_OP_USER_DEFINED
};


enum odp_hash_alg {
    ODP_HASH_ALG_HMAC_MD5 = 1,
    ODP_HASH_ALG_HMAC_SHA_1,
    ODP_HASH_ALG_HMAC_SHA_160,
    ODP_HASH_ALG_HMAC_SHA_224,
    ODP_HASH_ALG_HMAC_SHA_256,
    ODP_HASH_ALG_HMAC_SHA_384,
    ODP_HASH_ALG_HMAC_SHA_512,
    ODP_HASH_ALG_AES_XCBC,
    ODP_HASH_ALG_AES_CCM,
    ODP_HASH_ALG_AES_GCM,
    ODP_HASH_ALG_AES_CMAC,
    ODP_HASH_ALG_KASUMI_F9,
    ODP_HASH_ALG_SNOW3G_UEA2
};


typedef struct odp_key_op_result {
	odp_crypto_session_t session;    /**< Session handle from creation   */
	odp_bool_t  ok;                  /**< Request completed successfully */
	void *ctx;                       /**< User context from request */

	odp_packet_t out_key;
	odp_crypto_compl_status_t status;  /**< status */
} odp_key_op_result_t;




typedef struct odp_key_session_params {
    odp_acc_dev_handle dev_handle;     /**< device handle */
	enum odp_key_op op;
	enum odp_crypto_op_mode pref_mode;   /**< Preferred sync vs async */
	odp_queue_t compl_queue;           /**< Async mode completion event queue */
	odp_pool_t output_pool;            /**< Output buffer pool */

    void (*odp_asym_compl_pfn)(odp_crypto_session_t session, odp_key_op_result_t *result); /* 注册加解密回调处理函数 */
} odp_key_session_params_t;



typedef struct odp_key_op_params {
	odp_crypto_session_t session;     /**< Session handle from creation */
	void *ctx;                      /**< User context */

    enum odp_key_op_derive op_derive;  /* 操作类型 */
    odp_packet_t sec_ret;              /* 安全密钥 */
    odp_packet_t seed;                 /* seed值   */
    uint32_t len_in_bytes;             /* 产生的密钥长度 */
    odp_packet_t user_label;           /* 用户自定义 */
    enum odp_hash_alg hash_alg;

	odp_packet_t out_key;
} odp_key_op_params_t;


/*****************************************************************************
 Function     : odp_key_session_create
 Description  : 基于设备创建会话
 Input        : odp_key_session_params_t *params:会话参数
 Output       :
                odp_crypto_session_t *session:会话句柄
                enum odp_crypto_ses_create_err *status:会话失败原因
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_key_session_create(odp_key_session_params_t *params,
			  odp_crypto_session_t *session,
			  enum odp_crypto_ses_create_err *status);


/*****************************************************************************
 Function     : odp_key_session_destroy
 Description  : 删除会话
 Input        : odp_crypto_session_t session:会话句柄
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_key_session_destroy(odp_crypto_session_t session);




/*****************************************************************************
 Function     : odp_key_operation
 Description  : KEY操作
 Input        : odp_key_op_params_t *params:操作参数
 Output       :
                odp_bool_t *posted:同步/异步标志
                odp_key_op_result_t *result:操作结果
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_key_operation(odp_key_op_params_t *params,
		     odp_bool_t *posted,
		     odp_key_op_result_t *result);




/*****************************************************************************
 Function     : odp_key_operation_burst
 Description  : KEY多包操作(同一个会话)
 Input        : odp_key_op_params_t *params_tbl[]:操作参数
                uint16_t nb_pkts:操作个数
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_key_operation_burst(odp_key_op_params_t *params_tbl[], uint16_t nb_pkts);





#ifdef __cplusplus
}
#endif

#endif




