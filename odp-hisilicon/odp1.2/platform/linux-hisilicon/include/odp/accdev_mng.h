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

#ifndef ODP_API_ACC_DEV_MNG_H_
#define ODP_API_ACC_DEV_MNG_H_

#ifdef __cplusplus
extern "C" {
#endif

#define ODP_VERSION_LEN 16

struct odp_acc_pf_attr {
	uint32_t pf_id;                            /* 物理设备编号 */
	char	 firmwareversion[ODP_VERSION_LEN]; /* 固件版本 */
	char	 hardwareversion[ODP_VERSION_LEN]; /* 硬件版本 */
};

/*****************************************************************************
   Function     : odp_acc_get_pf_dev_names
   Description  : 获取指定类型的物理设备列表
   Input        : enum odp_acc_dev_type dev_type:加速设备类型
                uint32_t *dev_num: 需要查询的物理设备个数
   Output       : char *dev_name[]:物理设备名列表
                uint32_t *dev_num:实际物理设备个数
   Return       :
   Create By    :
   Modification :
   Restriction  :
*****************************************************************************/
int odp_acc_get_pf_dev_names(enum odp_acc_dev_type dev_type, char *dev_name[], uint32_t *dev_num);

/*****************************************************************************
   Function     : odp_acc_get_vf_dev_names
   Description  : 获取指定物理设备的虚拟设备列表
   Input        : char *dev_name:物理设备名
                uint32_t *vf_num: 需要查询的虚拟设备个数
   Output       : char *vf_name[]:虚拟设备名列表
                uint32_t *vf_num:实际虚拟设备个数
   Return       :
   Create By    :
   Modification :
   Restriction  :
*****************************************************************************/
int odp_acc_get_vf_dev_names(char *dev_name, char *vf_name[], uint32_t *vf_num);

/*****************************************************************************
   Function     : odp_acc_get_pf_dev_attr
   Description  : 获取物理设备信息
   Input        : char *dev_name
   Output       : struct odp_acc_pf_attr *attr:设备信息
   Return       :
   Create By    :
   Modification :
   Restriction  :
*****************************************************************************/
int odp_acc_get_pf_dev_attr(char *dev_name, struct odp_acc_pf_attr *attr);

#ifdef __cplusplus
}
#endif
#endif
