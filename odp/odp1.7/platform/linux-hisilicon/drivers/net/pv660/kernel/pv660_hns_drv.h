/*******************************************************************************

   Hisilicon network interface controller driver
   Copyright(c) 2014 - 2019 Huawei Corporation.

   This program is free software; you can redistribute it and/or modify it
   under the terms and conditions of the GNU General Public License,
   version 2, as published by the Free Software Foundation.

   This program is distributed in the hope it will be useful, but WITHOUT
   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
   FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
   more details.

   You should have received a copy of the GNU General Public License along with
   this program; if not, write to the Free Software Foundation, Inc.,
   51 Franklin St - Fifth Floor, Boston, MA 02110-1301 USA.

   The full GNU General Public License is included in this distribution in
   the file called "COPYING".

   Contact Information:TBD

*******************************************************************************/

#ifndef _HNS_UIO_ENET_H
#define _HNS_UIO_ENET_H

#include "hnae.h"
#include "hns_dsaf_mac.h"
#include "hns_dsaf_main.h"

#define NIC_MOD_VERSION "iWareV2R2C00B961"
#define DRIVER_UIO_NAME "hns-nic-uio"
#define NIC_UIO_SIZE	0x10000
#define NUM_MAX		64

enum  {
	HNS_UIO_IOCTL_MAC = 0,
	HNS_UIO_IOCTL_UP,
	HNS_UIO_IOCTL_DOWN,
	HNS_UIO_IOCTL_PORT,
	HNS_UIO_IOCTL_VF_MAX,
	HNS_UIO_IOCTL_VF_ID,
	HNS_UIO_IOCTL_QNUM,
	HNS_UIO_IOCTL_MTU,
	HNS_UIO_IOCTL_GET_STAT,
	HNS_UIO_IOCTL_GET_LINK,
	HNS_UIO_IOCTL_REG_READ,
	HNS_UIO_IOCTL_REG_WRITE,
	HNS_UIO_IOCTL_SET_PAUSE,
	HNS_UIO_IOCTL_GET_TYPE,
	HNS_UIO_IOCTL_QNUM_MAX,
	HNS_UIO_IOCTL_QNUM_START,
	HNS_UIO_IOCTL_NUM
};

struct char_device {
	unsigned int major;          /*设备的主设备号*/
	char	     class_name[64]; /*设备类型的名称*/
	char	     name[64];       /*设备文件名称(包括路径)*/
	struct	     class *dev_class;
};

struct nic_uio_device {
	struct device	   *dev;
	struct hnae_handle *ae_handle;
	struct net_device  *netdev;
	struct device_node *ae_node;
	struct hnae_vf_cb  *vf_cb;
	char		    mac_addr[8];
	unsigned int	    port;
	unsigned int	    vf_max;
	unsigned int	    q_num_start;
	unsigned int	    q_num_end;
	unsigned int	    q_num_max;
	unsigned int	    uio_index[NUM_MAX];
	unsigned int	    vf_type[NUM_MAX];
	unsigned int	    vf_id[NUM_MAX];
	unsigned int	    q_num[NUM_MAX];
	struct uio_info	    uinfo[NUM_MAX];
	unsigned long long  cfg_status[HNS_UIO_IOCTL_NUM];
};
#endif /*#ifndef _HNS_UIO_ENET_H*/
