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
   this program; if not, write to the Free Software Foundation, Inc.
   51 Franklin St - Fifth Floor, Boston, MA 02110-1301 USA.

   The full GNU General Public License is included in this distribution in
   the file called "COPYING".

   Contact Information:TBD

*******************************************************************************/

#include <linux/cdev.h>
#include <linux/module.h>
#include <linux/kernel.h>
#include <linux/init.h>
#include <linux/netdevice.h>
#include <linux/etherdevice.h>
#include <asm/cacheflush.h>
#include <linux/phy.h>
#include <linux/platform_device.h>
#include <linux/of.h>
#include <linux/of_platform.h>
#include <linux/of_net.h>
#include <linux/fs.h>
#include <linux/ethtool.h>
#include <linux/workqueue.h>
#include <linux/rtnetlink.h>
#include <linux/acpi.h>
#include <linux/uio_driver.h>
#include <linux/kthread.h>
#include <linux/slab.h>

#include <linux/of_net.h>
#include <linux/of_mdio.h>
#include "hns_uio_enet.h"
#include "hns_dsaf_reg.h"

#define MODE_IDX_IN_NAME 8
#define HNS_UIO_DEV_MAX 129

/* module_param(num, int, S_IRUGO); */

#define UIO_OK 0
#define UIO_ERROR -1

#ifndef PRINT
#define PRINT(fmt, ...) printk(KERN_ERR "[Func: %s. Line: %d] " fmt, __func__, __LINE__, ## __VA_ARGS__)
#endif

enum {
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
	HNS_UIO_IOCTL_NUM
};

struct hns_uio_ioctrl_para {
	unsigned long long index;
	unsigned long long cmd;
	unsigned long long value;
	unsigned char data[40];
};

/* DSAF_MODE_DISABLE_FIX,	    / **< non-dasf, fixed to queue* /
   DSAF_MODE_DISABLE_2PORT_8VM,	/ **< non-dasf, 2port 8VM * /
   DSAF_MODE_DISABLE_2PORT_16VM,	/ **< non-dasf, 2port 16VM * /
   DSAF_MODE_DISABLE_2PORT_64VM,	/ **< non-dasf, 2port 64VM * /
   DSAF_MODE_DISABLE_6PORT_0VM,	/ **< non-dasf, 6port 0VM * /
   DSAF_MODE_DISABLE_6PORT_2VM,	/ **< non-dasf, 6port 2VM * /
   DSAF_MODE_DISABLE_6PORT_4VM,	/ **< non-dasf, 6port 4VM * /
   DSAF_MODE_DISABLE_6PORT_16VM,	/ **< non-dasf, 6port 16VM * / */

static int port_vf[] = {
	0, 8, 16, 64, 0, 2, 4, 16
};

static int vf_sum;
struct nic_uio_device *uio_dev_info[HNS_UIO_DEV_MAX] = {
	0
};

static int char_dev_flag;
struct char_device char_dev;

struct task_struct *ring_task;
unsigned int kthread_stop_flag;

static ssize_t hns_cdev_read(
	struct file *file,
	char __user *buffer,
	size_t	     length,
	loff_t	    *offset)
{
	return UIO_OK;
}

static ssize_t hns_cdev_write(struct file *file,
			      const char __user *buffer, size_t length, loff_t *offset)
{
	return UIO_OK;
}

static int hns_uio_change_mtu(struct nic_uio_device *priv, int new_mtu)
{
	struct hnae_handle *h = priv->ae_handle;
	int ret;
	int state = 1;

	if (new_mtu < 68)
		return UIO_ERROR;

	if (!h->dev->ops->set_mtu)
		return UIO_ERROR;

	state = h->dev->ops->get_status(h);

	if (state) {
		if (h->dev->ops->stop)
			h->dev->ops->stop(h);

		ret = h->dev->ops->set_mtu(h, new_mtu);
		ret = h->dev->ops->start ? h->dev->ops->start(h) : 0;
	} else {
		ret = h->dev->ops->set_mtu(h, new_mtu);
	}

	if (!ret)
		priv->netdev->mtu = new_mtu;
	else
		netdev_err(priv->netdev, "set mtu net fail. ret = %d.\n", ret);

	return ret;
}

void hns_uio_get_stats(struct nic_uio_device *priv, unsigned long long *data)
{
	unsigned long long *p = data;
	struct hnae_handle *h = priv->ae_handle;
	const struct rtnl_link_stats64 *net_stats;
	struct rtnl_link_stats64 temp;

	if (!h->dev->ops->get_stats || !h->dev->ops->update_stats) {
		netdev_err(priv->netdev, "get_stats or update_stats is null!\n");
		return;
	}

	h->dev->ops->update_stats(h, &priv->netdev->stats);

	net_stats = dev_get_stats(priv->netdev, &temp);

	/* get netdev statistics */
	p[0]  = net_stats->rx_packets;
	p[1]  = net_stats->tx_packets;
	p[2]  = net_stats->rx_bytes;
	p[3]  = net_stats->tx_bytes;
	p[4]  = net_stats->rx_errors;
	p[5]  = net_stats->tx_errors;
	p[6]  = net_stats->rx_dropped;
	p[7]  = net_stats->tx_dropped;
	p[8]  = net_stats->multicast;
	p[9]  = net_stats->collisions;
	p[10] = net_stats->rx_over_errors;
	p[11] = net_stats->rx_crc_errors;
	p[12] = net_stats->rx_frame_errors;
	p[13] = net_stats->rx_fifo_errors;
	p[14] = net_stats->rx_missed_errors;
	p[15] = net_stats->tx_aborted_errors;
	p[16] = net_stats->tx_carrier_errors;
	p[17] = net_stats->tx_fifo_errors;
	p[18] = net_stats->tx_heartbeat_errors;
	p[19] = net_stats->rx_length_errors;
	p[20] = net_stats->tx_window_errors;
	p[21] = net_stats->rx_compressed;
	p[22] = net_stats->tx_compressed;

	p[23] = priv->netdev->rx_dropped.counter;
	p[24] = priv->netdev->tx_dropped.counter;

	/* get driver statistics */
	h->dev->ops->get_stats(h, &p[25]);
}

void hns_uio_pausefrm_cfg(void *mac_drv, u32 rx_en, u32 tx_en)
{
	struct hns_mac_cb *mac_cb = (struct hns_mac_cb *)mac_drv;
	u8 __iomem *base = (u8 *)mac_cb->vaddr + XGMAC_MAC_PAUSE_CTRL_REG;
	u32 origin = readl(base);

	dsaf_set_bit(origin, XGMAC_PAUSE_CTL_TX_B, !!tx_en);
	dsaf_set_bit(origin, XGMAC_PAUSE_CTL_RX_B, !!rx_en);
	writel(origin, base);
}

long hns_cdev_ioctl(struct file *file, unsigned int cmd, unsigned long arg)
{
	int ret = 0;
	void __user *parg;
	struct hns_uio_ioctrl_para uio_para;
	struct nic_uio_device *priv = NULL;
	struct hnae_handle *handle;

	/* unsigned long long data[128] = {0}; */

	parg = (void __user *)arg;

	if (copy_from_user(&uio_para, parg, sizeof(struct hns_uio_ioctrl_para))) {
		PRINT("copy_from_user error.\n");
		return UIO_ERROR;
	}

	if (uio_para.index >= vf_sum) {
		PRINT("Device index is out of range (%d).\n", vf_sum);
		return UIO_ERROR;
	}

	priv = uio_dev_info[uio_para.index];
	if (!priv) {
		PRINT("nic_uio_dev is null!\n");
		return UIO_ERROR;
	}

	handle = priv->ae_handle;

	switch (cmd) {
	case HNS_UIO_IOCTL_MAC:
		memcpy((void *)priv->netdev->dev_addr, (void *)&uio_para.data[0], 6);
		ret = handle->dev->ops->set_mac_addr(handle, priv->netdev->dev_addr);
		if (ret) {
			PRINT("set_mac_addr faill, ret = %d\n", ret);
			return UIO_ERROR;
		}

		break;
	case HNS_UIO_IOCTL_UP:
		ret = handle->dev->ops->start ? handle->dev->ops->start(handle) : 0;
		if (ret != 0) {
			PRINT("set_mac_addr faill, ret = %d.\n", ret);
			return UIO_ERROR;
		}

		break;
	case HNS_UIO_IOCTL_DOWN:
		if (handle->dev->ops->stop)
			handle->dev->ops->stop(priv->ae_handle);

		break;
	case HNS_UIO_IOCTL_PORT:
		uio_para.value = priv->port;
		if (copy_to_user((void __user *)arg, &uio_para, sizeof(struct hns_uio_ioctrl_para)) != 0)
			return UIO_ERROR;

		break;
	case HNS_UIO_IOCTL_VF_MAX:
		uio_para.value = priv->vf_sum;
		if (copy_to_user((void __user *)arg, &uio_para, sizeof(struct hns_uio_ioctrl_para)) != 0)
			return UIO_ERROR;

		break;
	case HNS_UIO_IOCTL_VF_ID:
		uio_para.value = priv->vf_id;
		if (copy_to_user((void __user *)arg, &uio_para, sizeof(struct hns_uio_ioctrl_para)) != 0)
			return UIO_ERROR;

		break;
	case HNS_UIO_IOCTL_QNUM:
		uio_para.value = priv->q_num;
		if (copy_to_user((void __user *)arg, &uio_para, sizeof(struct hns_uio_ioctrl_para)) != 0)
			return UIO_ERROR;

		break;

	case HNS_UIO_IOCTL_MTU:
		ret = hns_uio_change_mtu(priv, (int)uio_para.value);
		break;

	case HNS_UIO_IOCTL_GET_STAT:
	{
		unsigned long long *data = kzalloc(sizeof(unsigned long long) * 256, GFP_KERNEL);

		hns_uio_get_stats(priv, data);
		if (copy_to_user((void __user *)arg, data, sizeof(data)) != 0)
			return UIO_ERROR;

		break;
	}
	case HNS_UIO_IOCTL_GET_LINK:
		uio_para.value = handle->dev->ops->get_status ? handle->dev->ops->get_status(handle) : 0;
		if (copy_to_user((void __user *)arg, &uio_para, sizeof(struct hns_uio_ioctrl_para)) != 0)
			return UIO_ERROR;

		break;
	case HNS_UIO_IOCTL_REG_READ:
	{
		struct hnae_queue *queue;

		queue = handle->qs[0];
		
		uio_para.value = dsaf_read_reg(queue->io_base, uio_para.cmd);
		if (copy_to_user((void __user *)arg, &uio_para, sizeof(struct hns_uio_ioctrl_para)) != 0)
			return UIO_ERROR;
	
		break;
	}
	case HNS_UIO_IOCTL_REG_WRITE:
	{
		struct hnae_queue *queue;

		queue = handle->qs[0];
		dsaf_write_reg(queue->io_base, uio_para.cmd, uio_para.value);
		uio_para.value = dsaf_read_reg(queue->io_base, uio_para.cmd);
		if (copy_to_user((void __user *)arg, &uio_para, sizeof(struct hns_uio_ioctrl_para)) != 0)
			return UIO_ERROR;
	
		break;

	}
	case HNS_UIO_IOCTL_SET_PAUSE:
	{
		hns_uio_pausefrm_cfg(priv->vf_cb->mac_cb, 0, uio_para.value);
		break;
	}
	default:
		PRINT("uio ioctl cmd(%d) illegal! range:0-%d.\n", cmd, HNS_UIO_IOCTL_NUM - 1);
		return UIO_ERROR;
	}

	return ret;
}

const struct file_operations hns_uio_fops = {
	.owner		= THIS_MODULE,
	.read		= hns_cdev_read,
	.write		= hns_cdev_write,
	.unlocked_ioctl = hns_cdev_ioctl,
	.compat_ioctl	= hns_cdev_ioctl,
};

int hns_uio_register_cdev(void)
{
	struct device *aeclassdev;
	struct char_device *priv = &char_dev;

	if (char_dev_flag != 0)
		return UIO_OK;

	(void)strncpy(priv->name, "nic_uio", strlen("nic_uio"));
	priv->major = register_chrdev(0, priv->name, &hns_uio_fops);
	(void)strncpy(priv->class_name, "nic_uio", strlen("nic_uio"));
	priv->dev_class = class_create(THIS_MODULE, priv->class_name);
	if (IS_ERR(priv->dev_class)) {
		PRINT("Class_create device %s failed!\n", priv->class_name);
		(void)unregister_chrdev(priv->major, priv->name);
		return PTR_ERR(priv->dev_class);
	}

	aeclassdev = device_create(priv->dev_class, NULL, MKDEV(priv->major, 0), NULL, priv->name);
	if (IS_ERR(aeclassdev)) {
		PRINT("Class_device_create device %s failed!\n", priv->class_name);
		(void)unregister_chrdev(priv->major, priv->name);
		class_destroy((void *)priv->dev_class);
		return PTR_ERR(aeclassdev);
	}

	char_dev_flag = 1;

	return UIO_OK;
}

void hns_uio_unregister_cdev(void)
{
	struct char_device *priv = &char_dev;

	unregister_chrdev(priv->major, priv->name);
	device_destroy(priv->dev_class, MKDEV(priv->major, 0));
	class_destroy(priv->dev_class);

	char_dev_flag = 0;
}

static int hns_uio_nic_open(struct uio_info *dev_info, struct inode *node)
{
	return UIO_OK;
}

static int hns_uio_nic_release(struct uio_info *dev_info, struct inode *inode)
{
	return UIO_OK;
}

static int hns_uio_nic_irqcontrol(struct uio_info *dev_info, s32 irq_state)
{
	return UIO_OK;
}

static irqreturn_t hns_uio_nic_irqhandler(int irq, struct uio_info *dev_info)
{
	return IRQ_HANDLED;
}

static int hns_uio_alloc(struct hnae_ring *ring, struct hnae_desc_cb *cb)
{
	return UIO_OK;
}

static void hns_uio_free(struct hnae_ring *ring, struct hnae_desc_cb *cb)
{
}

static int hns_uio_map(struct hnae_ring *ring, struct hnae_desc_cb *cb)
{
	return UIO_OK;
}

static void hns_uio_unmap(struct hnae_ring *ring, struct hnae_desc_cb *cb)
{
}

static struct hnae_buf_ops hns_uio_nic_bops = {
	.alloc_buffer = hns_uio_alloc,
	.free_buffer  = hns_uio_free,
	.map_buffer   = hns_uio_map,
	.unmap_buffer = hns_uio_unmap,
};

static inline int hns_uio_get_port(const char *ops)
{
	return ops[0] - '0';
}

int hns_uio_probe(struct platform_device *pdev)
{
	struct nic_uio_device *priv = NULL;
	struct hnae_handle *handle;
	struct device *dev	 = &pdev->dev;
	struct device_node *node = dev->of_node;
	struct net_device *netdev;
	struct hnae_queue *queue;
	struct hnae_vf_cb *vf_cb;
	const char *ae_name;

	int ret;
	int port       = 0;
	int mode       = 0;
	int port_start = 0;
	int port_end   = 0;
	int i	       = 0;

	if (!dma_set_mask_and_coherent(dev, DMA_BIT_MASK(64)))
		PRINT("set mask to 64bit.\n");
	else
		PRINT("set mask to 32bit.\n");

	ret = of_property_read_string(node, "ae-name", &ae_name);
	if (ret)
		return UIO_ERROR;

	ret = of_property_read_u32(node, "port-id", &port);
	if (ret)
		return UIO_ERROR;


	PRINT("vf_sum = %d, name = %s, port = %d\n",vf_sum,  ae_name, port);


	mode = 7;
	port_start = vf_sum;
	port_end   = vf_sum + port_vf[mode];

	for (i = 0; i < port_vf[mode]; i++) {
		handle = hnae_get_handle(dev, ae_name, port, &hns_uio_nic_bops);
		if (IS_ERR_OR_NULL(handle)) {
			ret = PTR_ERR(handle);
			PRINT("hnae_get_handle fail. (port = %d, port_vf = %d).\n", port, i);
			goto err_uio_dev_free;
		}

		vf_cb = (struct hnae_vf_cb *)container_of(handle, struct hnae_vf_cb, ae_handle);
		netdev = alloc_etherdev_mq(sizeof(struct nic_uio_device), handle->q_num);
		if (!netdev) {
			PRINT("alloc_etherdev_mq fail. (name = %s, port = %d).\n", ae_name, port);
			goto err_get_handle;
		}

		priv		    = netdev_priv(netdev);
		priv->dev	    = dev;
		priv->netdev	= netdev;
		priv->ae_handle	= handle;
		priv->vf_cb     = vf_cb;
		priv->port	    = port;
		priv->vf_sum	= port_vf[mode];
		priv->vf_id	    = handle->vf_id;
		priv->q_num	    = handle->q_num;
		priv->port_vf_start = port_start;
		priv->port_vf_end   = port_end;

		queue			   = handle->qs[0];
		priv->uinfo.name	   = DRIVER_UIO_NAME;
		priv->uinfo.version	   = "1";
		priv->uinfo.priv	   = (void *)priv;
		priv->uinfo.mem[0].name	   = "rcb ring";
		priv->uinfo.mem[0].addr	   = (unsigned long)queue->phy_base;
		priv->uinfo.mem[0].size	   = NIC_UIO_SIZE * handle->q_num;
		priv->uinfo.mem[0].memtype = UIO_MEM_PHYS;

		priv->uinfo.mem[1].name	   = "tx_bd";
		priv->uinfo.mem[1].addr	   = (unsigned long)queue->tx_ring.desc;
		priv->uinfo.mem[1].size	   = queue->tx_ring.desc_num * sizeof(queue->tx_ring.desc[0]) * handle->q_num;
		priv->uinfo.mem[1].memtype = UIO_MEM_LOGICAL;

		priv->uinfo.mem[2].name	   = "rx_bd";
		priv->uinfo.mem[2].addr	   = (unsigned long)queue->rx_ring.desc;
		priv->uinfo.mem[2].size	   = queue->rx_ring.desc_num * sizeof(queue->rx_ring.desc[0]) * handle->q_num;
		priv->uinfo.mem[2].memtype = UIO_MEM_LOGICAL;

		priv->uinfo.mem[3].name	   = "nic_uio_device";
		priv->uinfo.mem[3].addr	   = (unsigned long)(vf_sum);
		priv->uinfo.mem[3].size	   = sizeof(unsigned long);
		priv->uinfo.mem[3].memtype = UIO_MEM_LOGICAL;

		/* priv->uinfo.irq = queue->rx_ring->irq; */
		priv->uinfo.irq_flags  = UIO_IRQ_CUSTOM;
		priv->uinfo.handler    = hns_uio_nic_irqhandler;
		priv->uinfo.irqcontrol = hns_uio_nic_irqcontrol;
		priv->uinfo.open       = hns_uio_nic_open;
		priv->uinfo.release    = hns_uio_nic_release;

		ret = uio_register_device(dev, &priv->uinfo);
		if (ret) {
			dev_err(dev, "uio_register_device failed!\n");
			goto err_unregister_uio;
		}

		/* PRINT("handle->q_num = %d, vf_id = %d, vf_sum = %d\n", handle->q_num, handle->vf_id, vf_sum); */
		platform_set_drvdata(pdev, netdev);
		uio_dev_info[vf_sum] = priv;

		vf_sum++;
	}

	ret = hns_uio_register_cdev();
	if (ret) {
		PRINT("registering the character device failed! ret = %d\n", ret);
		goto err_uio_dev_free;
	}

	return UIO_OK;

err_get_handle:
	hnae_put_handle(priv->ae_handle);
err_unregister_uio:
	kfree(uio_dev_info);
err_uio_dev_free:
	for (i = 0; i < vf_sum; i++) {
		priv = uio_dev_info[i];
		uio_unregister_device(&priv->uinfo);
		free_netdev(priv->netdev);
		hnae_put_handle(priv->ae_handle);
		uio_dev_info[i] = NULL;
	}

	return ret;
}

/**
 * hns_uio_nic_remove - remove nic_uio_device
 * @pdev: platform device
 *
 * Return 0 on success, negative on failure
 */
int hns_uio_remove(struct platform_device *pdev)
{
	int i				   = 0;
	struct net_device *ndev		   = platform_get_drvdata(pdev);
	struct nic_uio_device *nic_uio_dev = netdev_priv(ndev);
	struct nic_uio_device *priv;

	hns_uio_unregister_cdev();
	i = nic_uio_dev->port_vf_start;

	if (uio_dev_info[i]->port_vf_start != nic_uio_dev->port_vf_start) {
		PRINT("uio_dev_info[i]->port_vf_start = %d, nic_uio_dev->port_vf_start = %d.\n",
		      uio_dev_info[i]->port_vf_start,
		      nic_uio_dev->port_vf_start);
		return UIO_ERROR;
	}

	for (i = nic_uio_dev->port_vf_start; i < nic_uio_dev->port_vf_end; i++) {
		priv = uio_dev_info[i];
		uio_unregister_device(&priv->uinfo);

		if (priv->ae_handle->dev->ops->stop)
			priv->ae_handle->dev->ops->stop(priv->ae_handle);

		free_netdev(priv->netdev);
		hnae_put_handle(priv->ae_handle);
		uio_dev_info[i] = NULL;
	}

	PRINT("Uninstall UIO driver successfully.\n\n");
	return UIO_OK;
}

/**
 * hns_uio_nic_suspend - netdev suspend
 * @pdev: platform device
 * @state: power manage message
 *
 * Return 0 on success, negative on failure
 */
int hns_uio_suspend(struct platform_device *pdev, pm_message_t state)
{
	return UIO_OK;
}

/**
 * hns_uio_nic_resume - netdev resume
 * @pdev: platform device
 *
 * Return 0 on success, negative on failure
 */
int hns_uio_resume(struct platform_device *pdev)
{
	return UIO_OK;
}

/*for dts*/
static const struct of_device_id hns_uio_enet_match[] = {
	{.compatible = "hisilicon,hns-nic-v0"},
	{}
};

MODULE_DEVICE_TABLE(of, hns_uio_enet_match);

static struct platform_driver hns_uio_driver = {
	.probe	 = hns_uio_probe,
	.remove	 = hns_uio_remove,
	.suspend = hns_uio_suspend,
	.resume	 = hns_uio_resume,
	.driver	 = {
		.name		= DRIVER_UIO_NAME,
		.owner		= THIS_MODULE,
		.of_match_table = hns_uio_enet_match,
	},
};

int __init hns_uio_module_init(void)
{
	int ret;

	ret = platform_driver_register(&hns_uio_driver);
	if (ret) {
		PRINT("platform_driver_register fail, ret = %d\n", ret);
		return ret;
	}

	return UIO_OK;
}

void __exit hns_uio_module_exit(void)
{
	platform_driver_unregister(&hns_uio_driver);
}

module_init(hns_uio_module_init);

module_exit(hns_uio_module_exit);
MODULE_DESCRIPTION("Hisilicon HNS uio Ethernet driver");
MODULE_LICENSE("GPL");
MODULE_AUTHOR("Huawei Tech. Co., Ltd.");
MODULE_VERSION(NIC_MOD_VERSION);
