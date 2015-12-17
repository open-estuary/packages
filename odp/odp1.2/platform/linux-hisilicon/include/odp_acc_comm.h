/* Copyright (c) 2014, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */

#ifndef ODP_ACC_COMM_H_
#define ODP_ACC_COMM_H_

#ifdef __cplusplus
extern "C" {
#endif

#ifdef ACC_COMMON_H

odp_spinlock_t odp_acc_ssn_lock;
unsigned char  odp_ssn_used_flag[ODP_SSN_MAX_NUM] = {
	0
};

struct odp_session_mng odp_ssn_mng[ODP_SSN_MAX_NUM];

#else

extern odp_spinlock_t odp_acc_ssn_lock;
extern unsigned char  odp_ssn_used_flag[ODP_SSN_MAX_NUM];
extern struct odp_session_mng odp_ssn_mng[ODP_SSN_MAX_NUM];
#endif

odp_acc_session_t odp_alloc_ssn(void);
void odp_free_ssn(odp_acc_session_t session);

#ifdef __cplusplus
}
#endif
#endif
