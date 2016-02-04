/* Copyright (c) 2014, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */

/**
 * @file
 *
 * ODP DEV DRV
 */

#ifndef ODP_API_DEV_DRV_H_
#define ODP_API_DEV_DRV_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <odp/accdev_mng.h>
#include <sys/queue.h>
#include <odp/spinlock.h>
#include <odp_his_crypto_internal.h>

#define ODP_ACC_NAME_MAX_LEN (32)

#define ODP_ACC_HANDLE_NUM (512)

#define ODP_ACC_PF_NUM (32)

#define ODP_CRYPTO_DEV_NAME "odp_cy%u"
#define ODP_DC_DEV_NAME	    "odp_dc%u"

#define ODP_DEV_PATH "/dev/odp/"

enum odp_used {
	ODP_NOTUSED = 0,
	ODP_USED,
	ODP_USED_ILLEGAL
};

/* type of acc device */
enum odp_acc_dev_sub_type {
	ODP_ACC_DEV_PF_SUB_TYPE,
	ODP_ACC_DEV_VF_SUB_TYPE,
	ODP_ACC_DEV_SUB_TYPE_INVALID
};

#define OP_RESULT_MAGIC 0x91919191

/**
 * Per packet operation result
 */
typedef struct odp_acc_generic_op_result {
	uint32_t		magic;
	union odp_acc_op_result result;
} odp_acc_generic_op_result_t;

/**
 * @internal
 * The data part, with no function pointers, associated with each ethernet device.
 */
struct odp_acc_dev_data {
	char name[ODP_ACC_NAME_MAX_LEN];                    /**< Unique identifier name */

	void *dev_private;                                  /**< UMD-specific private data */

	enum odp_acc_dev_sub_type sub_type;
	struct odp_acc_pf_attr	  pf_attr;

	struct odp_acc_caps caps;

	uint8_t is_pci_dev;
	uint8_t bus_no;
	uint8_t dev_no;
	uint8_t fun_no;
};

/**
 * @internal A structure containing the functions exported by an Acc driver.
 */
#ifdef ACC_DEV_H

unsigned char odp_handle_used_flag[ODP_ACC_HANDLE_NUM] = {
	0
};

struct odp_acc_dev *odp_handle_dev_ptr[ODP_ACC_HANDLE_NUM] = {
	NULL
};

struct odp_acc_dev *odp_acc_pf_ptr[ODP_ACC_PF_NUM] = {
	NULL
};

odp_acc_session_t   odp_ssn_invert_mng[ODP_ACC_HANDLE_NUM][ODP_DEV_SSN_MAX_NUM];

#else

extern unsigned char odp_handle_used_flag[ODP_ACC_HANDLE_NUM];
extern struct odp_acc_dev *odp_handle_dev_ptr[ODP_ACC_HANDLE_NUM];
extern struct odp_acc_dev *odp_acc_pf_ptr[ODP_ACC_PF_NUM];
extern odp_acc_session_t   odp_ssn_invert_mng[ODP_ACC_HANDLE_NUM][ODP_DEV_SSN_MAX_NUM];
#endif

struct odp_acc_dev;
struct odp_acc_dev_ops {
	int (*acc_dev_open_t)(struct odp_acc_dev *dev);
	int (*acc_dev_close_t)(struct odp_acc_dev *dev);
	int (*acc_dev_reset_t)(struct odp_acc_dev *dev);
	int (*acc_get_dev_status_t)(struct odp_acc_dev *dev, uint32_t *status);
	int (*acc_get_dev_stat_t)(struct odp_acc_dev *dev, struct odp_acc_dev_stat *stat);
	int (*acc_clear_dev_stat_t)(struct odp_acc_dev *dev);

	int (*acc_session_create_t)(struct odp_acc_dev *dev, struct odp_acc_session_params_t *session_params,
				    odp_acc_session_t *session);
	int (*acc_session_delete_t)(struct odp_acc_dev *dev, odp_acc_session_t session);

	int (*acc_get_session_stat_t)(struct odp_acc_dev *dev, odp_acc_session_t session,
				      struct odp_crypto_session_stat *stat);
	int (*acc_clear_session_stat_t)(struct odp_acc_dev *dev, odp_acc_session_t session);
};

typedef uint32_t (*odp_acc_rx_burst_t)(struct odp_acc_dev *dev, union odp_acc_op_result *result, uint32_t nb_pkts);
typedef uint32_t (*odp_acc_tx_burst_t)(struct odp_acc_dev *dev, struct odp_acc_op_params *op_params, uint32_t nb_pkts);

typedef int (*odp_acc_rx_t)(struct odp_acc_dev *dev, union odp_acc_op_result *result);
typedef int (*odp_acc_tx_t)(struct odp_acc_dev *dev, struct odp_acc_op_params *op_params);

struct accio_entry {
	odp_spinlock_t lock;         /**< entry spinlock */
	odp_queue_t    inq_default;  /**< default input queue, if set */
	odp_queue_t    outq_default; /**< default out queue */
};

/**
 * @internal
 * The generic data structure associated with each acc device.
 */
struct odp_acc_dev {
	odp_acc_rx_t		rx_pkt;       /**< Pointer to UMD receive function.  */
	odp_acc_tx_t		tx_pkt;       /**< Pointer to UMD transmit function. */
	odp_acc_rx_burst_t	rx_pkt_burst; /**< Pointer to UMD receive function.  */
	odp_acc_tx_burst_t	tx_pkt_burst; /**< Pointer to UMD transmit function. */
	struct odp_acc_dev_data data;         /**< Pointer to device data */
	struct odp_acc_dev_ops *dev_ops;      /**< Functions exported by UMD */
	struct odp_uio_device  *uio_dev;      /**< UIO info. supplied by probing */
	uint8_t			attached;     /**< Flag indicating the deice is attached */
	int			fd;
	odp_acc_dev_handle	dev_handle;
	enum odp_acc_dev_type	dev_type;     /**< Flag indicating the device type */

	TAILQ_ENTRY(odp_acc_dev) next;        /**< Next ACC device. */

	odp_atomic_u64_t   dev_pkt_num;
	odp_atomic_u64_t   dev_sessin_num;
	odp_spinlock_t	   dev_lock;
	struct accio_entry s;
};

/*****************************************************************************
   Function     : odp_acc_dev_register
   Description  : acc device register
   Input        : struct odp_acc_dev *dev:the acc device
   Output       : NA
   Return       :
   Create By    :
   Modification :
   Restriction  :
*****************************************************************************/
int odp_acc_dev_register(struct odp_acc_dev *dev);

/*****************************************************************************
   Function     : odp_acc_dev_unregister
   Description  : acc device unregister
   Input        : struct odp_acc_dev *dev:the acc device
   Output       : NA
   Return       :
   Create By    :
   Modification :
   Restriction  :
*****************************************************************************/
int odp_acc_dev_unregister(struct odp_acc_dev *dev);

/*****************************************************************************
   Function     : odp_acc_dev_init
   Description  : ODP acc device manegement init
   Input        : NA
   Output       : NA
   Return       :
   Create By    :
   Modification :
   Restriction  :
*****************************************************************************/
int odp_acc_dev_init(void);

/*****************************************************************************
   Function     : get_op_result_from_event
   Description  : get result from event
   Input        : odp_event_t ev
   Output       : NA
   Return       : odp_acc_generic_op_result_t *
   Create By    :
   Modification :
   Restriction  :
*****************************************************************************/
odp_acc_generic_op_result_t *get_op_result_from_event(odp_event_t ev);

#ifdef __cplusplus
}
#endif
#endif
