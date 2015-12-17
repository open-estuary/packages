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

#ifndef ODP_API_ACC_IO_H_
#define ODP_API_ACC_IO_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <odp_queue_internal.h>
#include <odp_buffer_internal.h>

/**
 * Set the default input queue to be associated with a acc handle
 *
 * @param acc		ODP acc IO handle
 * @param queue		default input queue set
 * @retval  0 on success
 * @retval <0 on failure
 */
int odp_acc_inq_setdef(odp_acc_dev_handle id, odp_queue_t queue);

/**
 * Get default input queue associated with a acc handle
 *
 * @param acc  ODP acc IO handle
 *
 * @return Default input queue set
 * @retval ODP_QUEUE_INVALID on failure
 */
odp_queue_t odp_acc_inq_getdef(odp_acc_dev_handle id);

/**
 * Remove default input queue (if set)
 *
 * @param acc  ODP acc IO handle
 *
 * @retval 0 on success
 * @retval <0 on failure
 */
int odp_acc_inq_remdef(odp_acc_dev_handle id);

int odp_acc_outq_setdef(odp_acc_dev_handle id, odp_queue_t queue);
odp_queue_t odp_acc_outq_getdef(odp_acc_dev_handle id);
int odp_acc_outq_remdef(odp_acc_dev_handle id);

int accin_enqueue(queue_entry_t *qentry, odp_buffer_hdr_t *buf_hdr);

odp_buffer_hdr_t *accin_dequeue(queue_entry_t *qentry);

int accin_enq_multi(queue_entry_t *qentry, odp_buffer_hdr_t *buf_hdr[], int num);
int accin_deq_multi(queue_entry_t *qentry, odp_buffer_hdr_t *buf_hdr[], int num);

#ifdef __cplusplus
}
#endif
#endif
