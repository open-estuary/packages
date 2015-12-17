/* Copyright (c) 2014, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */

/**
 * @file
 *
 * ODP acc
 */



#ifndef ODP_API_ACC_DEV_H_
#define ODP_API_ACC_DEV_H_

#ifdef __cplusplus
extern "C" {
#endif


enum odp_acc_session_type
{
    ODP_ACC_CRYPTO_SESSION_TYPE,
    ODP_ACC_RSA_SESSION_TYPE,
    ODP_ACC_DSA_SESSION_TYPE,
    ODP_ACC_DH_SESSION_TYPE,
    ODP_ACC_RBG_SESSION_TYPE,
    ODP_ACC_KEY_SESSION_TYPE,
    ODP_ACC_PRIME_SESSION_TYPE,
};




struct odp_acc_op_params {
    enum odp_acc_session_type type;
    union
    {
        odp_crypto_op_params_t *params;
        odp_rsa_op_params_t *rsa_params;
        odp_dsa_op_params_t *dsa_params;
        odp_dh_op_params_t *dh_params;
        odp_rbg_op_params_t *rbg_params;
        odp_key_op_params_t *key_params;
        odp_prime_op_params_t *prime_params;
    };
};

union odp_acc_op_result {
	odp_acc_session_t session;
    odp_crypto_op_result_t result;
    odp_rsa_op_result_t rsa_result;
    odp_dsa_op_result_t dsa_result;
    odp_dh_op_result_t dh_result;
    odp_rbg_op_result_t rbg_result;
    odp_key_op_result_t key_result;
    odp_prime_op_result_t prime_result;
};

struct odp_acc_session_params_t {
    enum odp_acc_session_type type;
    union
    {
        odp_crypto_session_params_t *params;
        odp_rsa_session_params_t *rsa_params;
        odp_dsa_session_params_t *dsa_params;
        odp_dh_session_params_t *dh_params;
        odp_rbg_session_params_t *rbg_params;
        odp_key_session_params_t *key_params;
        odp_prime_session_params_t *prime_params;
    };
};


/* 加速设备类型 */
enum odp_acc_dev_type
{
    ODP_ACC_DEV_CRYPTO_TYPE,       /* 加解密设备                 */
    ODP_ACC_DEV_DC_TYPE,           /* 数据压缩设备               */
    ODP_ACC_DEV_TYPE_INVALID
};


enum odp_acc_dev_event
{
    ODP_ACC_DEV_EVENT_RESTARTING,     /* 复位之前通知事件 */
    ODP_ACC_DEV_EVENT_RESTARTED       /* 复位之后通知事件 */
};



/* 加速设备能力描述 */
struct odp_acc_caps
{
    uint32_t sessions_num;            /* 会话个数 */

    union
    {
        struct
        {
            uint32_t cipher_flow_caps[ODP_CIPHER_ALG_NUM];      /* 每种算法支持的能力，单位mpps */
            uint32_t auth_flow_caps[ODP_AUTH_ALG_NUM];          /* 每种算法支持的能力，单位mpps */
            uint32_t asym_flow_caps[1];                         /* 每种算法支持的能力，单位ops  */
            uint64_t cipher_feature;                      /* 对称加解密支持的算法odp_cipher_alg(1个bit位对应一种算法)   */
            uint64_t auth_feature;                        /* 认证支持的算法odp_auth_alg(1个bit位对应一种算法)           */
            uint64_t asym_feature;                        /* 支持的非对称加解密算法(1个bit位对应一种算法)               */
        }cy;
        struct
        {
            uint32_t dc_flow_caps[1];                      /* 每种算法支持的能力，单位mpps */
            uint64_t dc_feature;                           /* 数据压缩支持的算法  */
        }dc;
    };
};


/* 获取加速设备统计 */
struct odp_acc_dev_stat
{
    uint64_t  num_sessions_competed;                  /* 会话建立成功次数 */
    uint64_t  num_sessions_competed_errors;           /* 会话建立失败次数 */
    uint64_t  num_sessions_removed;                   /* 会话删除成功次数 */
    uint64_t  num_sessions_removed_errors;            /* 会话删除失败次数 */
    uint64_t  num_op_request_completed;               /* 操作请求成功次数 */
    uint64_t  num_op_request_completed_errors;        /* 操作请求失败次数 */
    uint64_t  num_op_respond_completed;               /* 操作应答成功次数 */
    uint64_t  num_op_respond_completed_errors;        /* 操作应答失败次数 */
};


void odp_acc_dev_lock(odp_acc_dev_handle dev_handle);
void odp_acc_dev_unlock(odp_acc_dev_handle dev_handle);
/*****************************************************************************
 Function     : odp_acc_get_dev_names
 Description  : 获取指定类型的设备列表
 Input        : enum odp_acc_dev_type dev_type:加速设备类型
                uint32_t *dev_num: 需要查询的设备个数
 Output       : char *dev_name[]:设备名列表
                uint32_t *dev_num:实际设备个数
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_get_dev_names(enum odp_acc_dev_type dev_type, char *dev_name[], uint32_t *dev_num);


/*****************************************************************************
 Function     : odp_acc_get_dev_caps
 Description  : 获取加速设备能力
 Input        : char *dev_name
 Output       : struct odp_acc_caps *caps:设备能力
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_get_dev_caps(char *dev_name, struct odp_acc_caps *caps);



/*****************************************************************************
 Function     : odp_acc_get_dev_handle
 Description  : 获取加速设备handle
 Input        : char *dev_name:设备名
 Output       : NA
 Return       : odp_acc_dev_handle dev_handle:设备句柄(0或大于512都是非法值)
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
odp_acc_dev_handle odp_acc_get_dev_handle(char *dev_name);



/*****************************************************************************
 Function     : odp_acc_get_dev_numa_node
 Description  : 获取加速设备所在numa_node
 Input        : char *dev_name:设备名
 Output       : int *numa_node:numa节点
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_get_dev_numa_node(char *dev_name, int *numa_node);


/*****************************************************************************
 Function     : odp_acc_get_dev_type
 Description  : 获取加速设备类型
 Input        : odp_acc_dev_handle dev_handle:设备句柄
 Output       : enum odp_acc_dev_type *dev_type:设备类型
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_get_dev_type(odp_acc_dev_handle dev_handle, enum odp_acc_dev_type *dev_type);




/*****************************************************************************
 Function     : odp_acc_open_dev
 Description  : 打开加速设备
 Input        : odp_acc_dev_handle dev_handle:设备句柄
 Output       : NA
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_open_dev(odp_acc_dev_handle dev_handle);


/*****************************************************************************
 Function     : odp_acc_close_dev
 Description  : 关闭加速设备
 Input        : odp_acc_dev_handle dev_handle:设备句柄
 Output       : NA
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_close_dev(odp_acc_dev_handle dev_handle);


/*****************************************************************************
 Function     : odp_acc_reset_dev
 Description  : 重启加速设备
 Input        : odp_acc_dev_handle dev_handle:设备句柄
 Output       : NA
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_reset_dev(odp_acc_dev_handle dev_handle);


/*****************************************************************************
 Function     : odp_acc_get_dev_status
 Description  : 获取加速设备故障状态
 Input        : odp_acc_dev_handle dev_handle:设备句柄
 Output       : uint32_t *status(0:正常，其他:异常)
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_get_dev_status(odp_acc_dev_handle dev_handle, uint32_t *status);



/*****************************************************************************
 Function     : odp_acc_get_open_status
 Description  : 获取加速设备打开状态
 Input        : odp_acc_dev_handle dev_handle:设备句柄
 Output       : uint32_t *status(0:关闭，1:打开)
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_get_open_status(odp_acc_dev_handle dev_handle, uint32_t *status);



/*****************************************************************************
 Function     : odp_acc_get_dev_stat
 Description  : 获取加速设备统计
 Input        : odp_acc_dev_handle dev_handle:设备句柄
 Output       : struct odp_acc_dev_stat *stat:统计信息
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_get_dev_stat(odp_acc_dev_handle dev_handle, struct odp_acc_dev_stat *stat);





/*****************************************************************************
 Function     : odp_acc_clear_dev_stat
 Description  : 清除加速设备统计
 Input        : odp_acc_dev_handle dev_handle:设备句柄
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
int odp_acc_clear_dev_stat(odp_acc_dev_handle dev_handle);








/*****************************************************************************
 Function     : odp_acc_dev_fault_notification_reg
 Description  : 加速设备故障通知注册函数
 Input        : odp_acc_dev_handle dev_handle:设备句柄
                const odp_acc_dev_fault_cb_func cb_func:回调函数
                void *callback_tag:回调参数
 Output       :
 Return       :
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
typedef void (*odp_acc_dev_fault_cb_func)(odp_acc_dev_handle dev_handle, void *callback_tag, enum odp_acc_dev_event event);
void odp_acc_dev_fault_notification_reg(odp_acc_dev_handle dev_handle, const odp_acc_dev_fault_cb_func cb_func, void *callback_tag);




/*****************************************************************************
 Function     : odp_acc_dev_recv_burst
 Description  : 在回调函数未注册情况下的接收处理函数
 Input        : odp_acc_dev_handle dev_handle:设备句柄
                union odp_acc_op_result result[]:结果
                uint32_t pkts_num:结果个数
 Output       : NA
 Return       : 实际接收的结果个数
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
uint32_t odp_acc_dev_recv_burst(odp_acc_dev_handle dev_handle, union odp_acc_op_result result[], uint32_t pkts_num);



/*****************************************************************************
 Function     : odp_acc_result_to_compl
 Description  : 将结果写入事件相应位置
 Input        : odp_event_t completion_event:事件
                union odp_acc_op_result *result:结果
 Output       : NA
 Return       : NA
 Create By    :
 Modification :
 Restriction  :
*****************************************************************************/
void odp_acc_result_to_compl(odp_event_t completion_event,
			union odp_acc_op_result *result);


#ifdef __cplusplus
}
#endif

#endif

