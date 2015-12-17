/* Copyright (c) 2015, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */


/**
 * @file
 *
 * ODP crypto
 */

#ifndef ODP_CRYPTO_TYPES_H_
#define ODP_CRYPTO_TYPES_H_

#ifdef __cplusplus
extern "C" {
#endif

/** @addtogroup odp_crypto
 *  @{
 */

#define ODP_CRYPTO_SESSION_INVALID (0xffffffffffffffffULL)
#define ODP_ACC_HANDLE_INVALID         (0xffffffffffffffffULL)

typedef uint64_t odp_crypto_session_t;
#if 1
typedef uint64_t odp_acc_dev_handle;
#else
typedef ODP_HANDLE_T(odp_acc_dev_handle);
#endif
typedef ODP_HANDLE_T(odp_crypto_compl_t);
typedef uint64_t odp_acc_session_t;
typedef odp_handle_t odp_acc_pkt_t;



/* ¨ºy?Y¨¢¡ä¡À¨ª?¨²¦Ì? */
typedef struct odp_pkt_data_node
{
    struct odp_pkt_data_node *next;     /* ??¨°???¨ºy?Y??¦Ì?¦Ì??¡¤ */
    uint32_t data_len;                  /* ¨ºy?Y3¡è?¨¨           */
    uint8_t  *data;                     /* ¨ºy?Y¦Ì??¡¤           */
}adp_pkt_data_node_t;


/* ¡À¡§??¦Ì?¨ºy?Y¨¢¡ä¡À¨ª */
typedef struct odp_pkt_list
{
    uint32_t           node_count;          /* ¨ºy?Y¨¢¡ä¡À¨ª?¨²¦Ì?¦Ì???¨ºy */
    adp_pkt_data_node_t *head_node;         /* ¨¢¡ä¡À¨ª¨ª¡¤?¨²¦Ì?¦Ì?¦Ì??¡¤   */
}odp_pkt_list_t;




enum odp_crypto_op_mode {
	ODP_CRYPTO_SYNC,
	ODP_CRYPTO_ASYNC,
};

enum odp_crypto_op {
	ODP_CRYPTO_OP_ENCODE,
	ODP_CRYPTO_OP_DECODE,
};


enum odp_rsa_op {
	ODP_RSA_OP_ENCODE,
	ODP_RSA_OP_DECODE,
    ODP_RSA_OP_GEN_KEY
};


enum  odp_cipher_alg {
	ODP_CIPHER_ALG_NULL,
    ODP_CIPHER_ALG_ARC4,
	ODP_CIPHER_ALG_DES,
    ODP_CIPHER_ALG_DES_ECB,
    ODP_CIPHER_ALG_DES_CBC,
    ODP_CIPHER_ALG_3DES_ECB,
	ODP_CIPHER_ALG_3DES_CBC,
    ODP_CIPHER_ALG_AES_ECB,
    ODP_CIPHER_ALG_AES_CBC,
    ODP_CIPHER_ALG_AES_CTR,
    ODP_CIPHER_ALG_AES_CCM,
    ODP_CIPHER_ALG_AES_GCM,
    ODP_CIPHER_ALG_KASUMI_F8,
    ODP_CIPHER_ALG_SNOW3G_UEA2,
    ODP_CIPHER_ALG_AES_F8,
    ODP_CIPHER_ALG_ZUC_EEA3,
    ODP_CIPHER_ALG_NUM
};

enum odp_auth_alg {
	ODP_AUTH_ALG_NULL,
    ODP_AUTH_ALG_MD5,
	ODP_AUTH_ALG_MD5_96,
    ODP_AUTH_ALG_SHA1,
    ODP_AUTH_ALG_SHA_160,
    ODP_AUTH_ALG_SHA_224,
    ODP_AUTH_ALG_SHA_256,
    ODP_AUTH_ALG_SHA_384,
    ODP_AUTH_ALG_SHA_512,
    ODP_AUTH_ALG_HMAC_MD5,
    ODP_AUTH_ALG_HMAC_MD5_96,
    ODP_AUTH_ALG_HMAC_SHA_1,
    ODP_AUTH_ALG_HMAC_SHA_160,
    ODP_AUTH_ALG_HMAC_SHA_224,
    ODP_AUTH_ALG_HMAC_SHA_256,
    ODP_AUTH_ALG_HMAC_SHA_384,
    ODP_AUTH_ALG_HMAC_SHA_512,
    ODP_AUTH_ALG_AES_XCBC,
    ODP_AUTH_ALG_AES_XCBC_MAC_96,
    ODP_AUTH_ALG_AES_XCBC_PRF_128,
    ODP_AUTH_ALG_AES_CCM,
    ODP_AUTH_ALG_AES_GCM,
    ODP_AUTH_ALG_AES_CMAC,
    ODP_AUTH_ALG_KASUMI_F9,
    ODP_AUTH_ALG_SNOW3G_UIA2,
    ODP_AUTH_ALG_NUM
};

enum odp_crypto_ses_create_err {
	ODP_CRYPTO_SES_CREATE_ERR_NONE,
	ODP_CRYPTO_SES_CREATE_ERR_ENOMEM,
	ODP_CRYPTO_SES_CREATE_ERR_INV_CIPHER,
	ODP_CRYPTO_SES_CREATE_ERR_INV_AUTH,
};

enum crypto_alg_err {
	ODP_CRYPTO_ALG_ERR_NONE,
	ODP_CRYPTO_ALG_ERR_DATA_SIZE,
	ODP_CRYPTO_ALG_ERR_KEY_SIZE,
	ODP_CRYPTO_ALG_ERR_ICV_CHECK,
	ODP_CRYPTO_ALG_ERR_IV_INVALID,
};

enum crypto_hw_err {
	ODP_CRYPTO_HW_ERR_NONE,
	ODP_CRYPTO_HW_ERR_DMA,
	ODP_CRYPTO_HW_ERR_BP_DEPLETED,
};

/** Get printable format of odp_crypto_session_t */
static inline uint64_t odp_crypto_session_to_u64(odp_crypto_session_t hdl)
{
	return (uint64_t)hdl;
}

/** Get printable format of odp_crypto_compl_t_t */
static inline uint64_t odp_crypto_compl_to_u64(odp_crypto_compl_t hdl)
{
	return _odp_pri(hdl);
}

/**
 * @}
 */

#ifdef __cplusplus
}
#endif

#endif
