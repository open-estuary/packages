/* Copyright (c) 2013, Linaro Limited
 * All rights reserved.
 *
 * SPDX-License-Identifier:     BSD-3-Clause
 */


/**
 * @file
 *
 * ODP Packet IO
 */

#ifndef ODP_API_PACKET_IO_H_
#define ODP_API_PACKET_IO_H_

#ifdef __cplusplus
extern "C" {
#endif

/** @defgroup odp_packet_io ODP PACKET IO
 *  Operations on a packet.
 *  @{
 */

/**
 * @typedef odp_pktio_t
 * Packet IO handle
 */

/**
 * @def ODP_PKTIO_INVALID
 * Invalid packet IO handle
 */

/**
 * @def ODP_PKTIO_ANY
 * odp_pktio_t value to indicate any port
 */

/**
 * @def ODP_PKTIO_MACADDR_MAXSIZE
 * Minimum size of output buffer for odp_pktio_mac_addr()
 * Actual MAC address sizes may be different.
 */

/**
 * Open a packet IO interface
 *
 * Packet IO handles are a single instance per device. Attempts to open an
 * already open device will fail, returning ODP_PKTIO_INVALID with errno set.
 * odp_pktio_lookup() may be used to obtain a handle to an already open
 * device.
 *
 * @param dev    Packet IO device name
 * @param pool   Default pool from which to allocate buffers for storing packets
 *               received over this packet IO
 *
 * @return Packet IO handle
 * @retval ODP_PKTIO_INVALID on failure
 *
 * @note The device name "loop" is a reserved name for a loopback device used
 *	 for testing purposes.
 *
 * @note Packets arriving via this interface assigned to a CoS by the
 *	 classifier are received into the pool associated with that CoS. This
 *	 will occur either becuase this pktio is assigned a default CoS via
 *	 the odp_pktio_default_cos_set() routine, or because a matching PMR
 *	 assigned the packet to a specific CoS. The default pool specified
 *	 here is applicable only for those packets that are not assigned to a
 *	 more specific CoS.
 */
odp_pktio_t odp_pktio_open(const char *dev, odp_pool_t pool);

/**
 * Close a packet IO interface
 *
 * @param pktio  Packet IO handle
 *
 * @retval 0 on success
 * @retval <0 on failure
 */
int odp_pktio_close(odp_pktio_t pktio);

/**
 * Return a packet IO handle for an already open device
 *
 * @param dev Packet IO device name
 *
 * @return Packet IO handle
 * @retval ODP_PKTIO_INVALID on failure
 */
odp_pktio_t odp_pktio_lookup(const char *dev);

/**
 * Receive packets
 *
 * @param pktio       Packet IO handle
 * @param pkt_table[] Storage for received packets (filled by function)
 * @param len         Length of pkt_table[], i.e. max number of pkts to receive
 *
 * @return Number of packets received
 * @retval <0 on failure
 */
int odp_pktio_recv(odp_pktio_t pktio, odp_packet_t pkt_table[], unsigned int len);

/**
 * Send packets
 *
 * Sends out a number of packets. A successful call returns the actual number of
 * packets sent. If return value is less than 'len', the remaining packets at
 * the end of pkt_table[] are not consumed, and the caller has to take care of
 * them.
 *
 * @param pktio        Packet IO handle
 * @param pkt_table[]  Array of packets to send
 * @param len          length of pkt_table[]
 *
 * @return Number of packets sent
 * @retval <0 on failure
 */
int odp_pktio_send(odp_pktio_t pktio, odp_packet_t pkt_table[], int len);

/**
 * Set the default input queue to be associated with a pktio handle
 *
 * @param pktio		Packet IO handle
 * @param queue		default input queue set
 * @retval  0 on success
 * @retval <0 on failure
 */
int odp_pktio_inq_setdef(odp_pktio_t pktio, odp_queue_t queue);

/**
 * Get default input queue associated with a pktio handle
 *
 * @param pktio  Packet IO handle
 *
 * @return Default input queue set
 * @retval ODP_QUEUE_INVALID on failure
 */
odp_queue_t odp_pktio_inq_getdef(odp_pktio_t pktio);

/**
 * Remove default input queue (if set)
 *
 * @param pktio  Packet IO handle
 *
 * @retval 0 on success
 * @retval <0 on failure
 */
int odp_pktio_inq_remdef(odp_pktio_t pktio);

/**
 * Query default output queue
 *
 * @param pktio Packet IO handle
 *
 * @return Default out queue
 * @retval ODP_QUEUE_INVALID on failure
 */
odp_queue_t odp_pktio_outq_getdef(odp_pktio_t pktio);

/**
 * Return the currently configured MTU value of a packet IO interface.
 *
 * @param[in] pktio  Packet IO handle.
 *
 * @return MTU value on success
 * @retval <0 on failure
 */
int odp_pktio_mtu(odp_pktio_t pktio);

/**
 * Enable/Disable promiscuous mode on a packet IO interface.
 *
 * @param[in] pktio	Packet IO handle.
 * @param[in] enable	1 to enable, 0 to disable.
 *
 * @retval 0 on success
 * @retval <0 on failure
 */
int odp_pktio_promisc_mode_set(odp_pktio_t pktio, odp_bool_t enable);

/**
 * Determine if promiscuous mode is enabled for a packet IO interface.
 *
 * @param[in] pktio Packet IO handle.
 *
 * @retval  1 if promiscuous mode is enabled.
 * @retval  0 if promiscuous mode is disabled.
 * @retval <0 on failure
*/
int odp_pktio_promisc_mode(odp_pktio_t pktio);

/**
 * Get the default MAC address of a packet IO interface.
 *
 * @param	pktio     Packet IO handle
 * @param[out]	mac_addr  Output buffer (use ODP_PKTIO_MACADDR_MAXSIZE)
 * @param       size      Size of output buffer
 *
 * @return Number of bytes written (actual size of MAC address)
 * @retval <0 on failure
 */
int odp_pktio_mac_addr(odp_pktio_t pktio, void *mac_addr, int size);

/**
 * Setup per-port default class-of-service.
 *
 * @param[in]	pktio		Ingress port pktio handle.
 * @param[in]	default_cos	Class-of-service set to all packets arriving
 *				at this ingress port,
 *				unless overridden by subsequent
 *				header-based filters.
 *
 * @retval			0 on success
 * @retval			<0 on failure
 */
int odp_pktio_default_cos_set(odp_pktio_t pktio, odp_cos_t default_cos);

/**
 * Setup per-port error class-of-service
 *
 * @param[in]	pktio		Ingress port pktio handle.
 * @param[in]	error_cos	class-of-service set to all packets arriving
 *				at this ingress port that contain an error.
 *
 * @retval			0 on success
 * @retval			<0 on failure
 *
 * @note Optional.
 */
int odp_pktio_error_cos_set(odp_pktio_t pktio, odp_cos_t error_cos);

/**
 * Setup per-port header offset
 *
 * @param[in]	pktio		Ingress port pktio handle.
 * @param[in]	offset		Number of bytes the classifier must skip.
 *
 * @retval			0 on success
 * @retval			<0 on failure
 * @note  Optional.
 *
 */
int odp_pktio_skip_set(odp_pktio_t pktio, uint32_t offset);

/**
 * Specify per-port buffer headroom
 *
 * @param[in]	pktio		Ingress port pktio handle.
 * @param[in]	headroom	Number of bytes of space preceding
 *				packet data to reserve for use as headroom.
 *				Must not exceed the implementation
 *				defined ODP_PACKET_MAX_HEADROOM.
 *
 * @retval			0 on success
 * @retval			<0 on failure
 *
 * @note Optional.
 */
int odp_pktio_headroom_set(odp_pktio_t pktio, uint32_t headroom);

/**
 * Get printable value for an odp_pktio_t
 *
 * @param pktio   odp_pktio_t handle to be printed
 * @return     uint64_t value that can be used to print/display this
 *             handle
 *
 * @note This routine is intended to be used for diagnostic purposes
 * to enable applications to generate a printable value that represents
 * an odp_pktio_t handle.
 */
uint64_t odp_pktio_to_u64(odp_pktio_t pktio);

/*************************************************************************/
/* Add new API to linaro pktio API as below, in ODP version 1.2.0        */
/*  2015-08-18                                                           */
/*************************************************************************/
/**
 * A structure used to retrieve link-level information of an Ethernet port.
 */
struct odp_pktio_eth_link {
	uint16_t link_speed;      /**< ETH_LINK_SPEED_[10, 100, 1000, 10000] */
	uint16_t link_duplex;     /**< ETH_LINK_[HALF_DUPLEX, FULL_DUPLEX] */
	uint8_t  link_status : 1; /**< 1 -> link up, 0 -> link down */
}__attribute__((aligned(8)));     /**< aligned for atomic64 read/write */

#define ODP_ETH_LINK_SPEED_AUTONEG  0       /**< Auto-negotiate link speed. */
#define ODP_ETH_LINK_SPEED_10       10      /**< 10 megabits/second. */
#define ODP_ETH_LINK_SPEED_100      100     /**< 100 megabits/second. */
#define ODP_ETH_LINK_SPEED_1000     1000    /**< 1 gigabits/second. */
#define ODP_ETH_LINK_SPEED_10000    10000   /**< 10 gigabits/second. */
#define ODP_ETH_LINK_SPEED_10G      10000   /**< alias of 10 gigabits/second. */
#define ODP_ETH_LINK_SPEED_20G      20000   /**< 20 gigabits/second. */
#define ODP_ETH_LINK_SPEED_40G      40000   /**< 40 gigabits/second. */

#define ODP_ETH_LINK_AUTONEG_DUPLEX 0       /**< Auto-negotiate duplex. */
#define ODP_ETH_LINK_HALF_DUPLEX    1       /**< Half-duplex connection. */
#define ODP_ETH_LINK_FULL_DUPLEX    2       /**< Full-duplex connection. */

/**
 *  Simple flags are used for odp_pktio_eth_conf.rxmode.mq_mode.
 */
#define ODP_ETH_MQ_RX_RSS_FLAG  0x1
#define ODP_ETH_MQ_RX_DCB_FLAG  0x2
#define ODP_ETH_MQ_RX_VMDQ_FLAG 0x4

/**
 *  A set of values to identify what method is to be used to route
 *  packets to multiple queues.
 */
enum odp_pktio_eth_rx_mq_mode {
	/** None of DCB,RSS or VMDQ mode */
	ODP_ETH_MQ_RX_NONE = 0,

	/** For RX side, only RSS is on */
	ODP_ETH_MQ_RX_RSS = ODP_ETH_MQ_RX_RSS_FLAG,
	/** For RX side,only DCB is on. */
	ODP_ETH_MQ_RX_DCB = ODP_ETH_MQ_RX_DCB_FLAG,
	/** Both DCB and RSS enable */
	ODP_ETH_MQ_RX_DCB_RSS = ODP_ETH_MQ_RX_RSS_FLAG | ODP_ETH_MQ_RX_DCB_FLAG,

	/** Only VMDQ, no RSS nor DCB */
	ODP_ETH_MQ_RX_VMDQ_ONLY = ODP_ETH_MQ_RX_VMDQ_FLAG,
	/** RSS mode with VMDQ */
	ODP_ETH_MQ_RX_VMDQ_RSS = ODP_ETH_MQ_RX_RSS_FLAG | ODP_ETH_MQ_RX_VMDQ_FLAG,
	/** Use VMDQ+DCB to route traffic to queues */
	ODP_ETH_MQ_RX_VMDQ_DCB = ODP_ETH_MQ_RX_VMDQ_FLAG | ODP_ETH_MQ_RX_DCB_FLAG,
	/** Enable both VMDQ and DCB in VMDq */
	ODP_ETH_MQ_RX_VMDQ_DCB_RSS = ODP_ETH_MQ_RX_RSS_FLAG | ODP_ETH_MQ_RX_DCB_FLAG |
				 ODP_ETH_MQ_RX_VMDQ_FLAG,
};

/**
 * A structure used to configure the RX features of an Ethernet port.
 */
struct odp_pktio_eth_rxmode {
	/** The multi-queue packet distribution mode to be used, e.g. RSS. */
	enum odp_pktio_eth_rx_mq_mode mq_mode;
	uint32_t max_rx_pkt_len;  /**< Only used if jumbo_frame enabled. */
	uint16_t split_hdr_size;  /**< hdr buf size (header_split enabled).*/
	uint8_t header_split : 1, /**< Header Split enable. */
		hw_ip_checksum   : 1, /**< IP/UDP/TCP checksum offload enable. */
		hw_vlan_filter   : 1, /**< VLAN filter enable. */
		hw_vlan_strip    : 1, /**< VLAN strip enable. */
		hw_vlan_extend   : 1, /**< Extended VLAN enable. */
		jumbo_frame      : 1, /**< Jumbo Frame Receipt enable. */
		hw_strip_crc     : 1, /**< Enable CRC stripping by hardware. */
		enable_scatter   : 1; /**< Enable scatter packets rx handler */
};

/**
 * A set of values to identify what method is to be used to transmit
 * packets using multi-TCs.
 */
enum odp_pktio_eth_tx_mq_mode {
	ODP_ETH_MQ_TX_NONE    = 0,  /**< It is in neither DCB nor VT mode. */
	ODP_ETH_MQ_TX_DCB,          /**< For TX side,only DCB is on. */
	ODP_ETH_MQ_TX_VMDQ_DCB,	/**< For TX side,both DCB and VT is on. */
	ODP_ETH_MQ_TX_VMDQ_ONLY,    /**< Only VT on, no DCB */
};

/**
 * A structure used to configure the TX features of an Ethernet port.
 */
struct odp_pktio_eth_txmode {
	enum odp_pktio_eth_tx_mq_mode mq_mode; /**< TX multi-queues mode. */

	/* For i40e specifically */
	uint16_t pvid;
	uint8_t hw_vlan_reject_tagged : 1,
		/**< If set, reject sending out tagged pkts */
		hw_vlan_reject_untagged : 1,
		/**< If set, reject sending out untagged pkts */
		hw_vlan_insert_pvid : 1;
		/**< If set, enable port based VLAN insertion */
};

/**
 * A structure used to configure an Ethernet port.
 * Depending upon the RX multi-queue mode, extra advanced
 * configuration settings may be needed.
 */
struct odp_pktio_eth_conf {
	uint16_t link_speed;
	/**< ETH_LINK_SPEED_10[0|00|000], or 0 for autonegotation */
	uint16_t link_duplex;
	/**< ETH_LINK_[HALF_DUPLEX|FULL_DUPLEX], or 0 for autonegotation */
	struct odp_pktio_eth_rxmode rxmode; /**< Port RX configuration. */
	struct odp_pktio_eth_txmode txmode; /**< Port TX configuration. */
};

#define ODP_ETHDEV_QUEUE_STAT_CNTRS 16

/**
 * A structure used to retrieve statistics for an Ethernet port.
 */
struct odp_pktio_eth_stats {
	uint64_t ipackets;  /**< Total number of successfully received packets. */
	uint64_t opackets;  /**< Total number of successfully transmitted packets.*/
	uint64_t ibytes;    /**< Total number of successfully received bytes. */
	uint64_t obytes;    /**< Total number of successfully transmitted bytes. */
	uint64_t imissed;   /**< Total of RX missed packets (e.g full FIFO). */
	uint64_t ibadcrc;   /**< Total of RX packets with CRC error. */
	uint64_t ibadlen;   /**< Total of RX packets with bad length. */
	uint64_t ierrors;   /**< Total number of erroneous received packets. */
	uint64_t oerrors;   /**< Total number of failed transmitted packets. */
	uint64_t imcasts;   /**< Total number of multicast received packets. */
	uint64_t rx_nombuf; /**< Total number of RX mbuf allocation failures. */
	uint64_t fdirmatch; /**< Total number of RX packets matching a filter. */
	uint64_t fdirmiss;  /**< Total number of RX packets not matching any filter. */
	uint64_t tx_pause_xon;  /**< Total nb. of XON pause frame sent. */
	uint64_t rx_pause_xon;  /**< Total nb. of XON pause frame received. */
	uint64_t tx_pause_xoff; /**< Total nb. of XOFF pause frame sent. */
	uint64_t rx_pause_xoff; /**< Total nb. of XOFF pause frame received. */
	uint64_t q_ipackets[ODP_ETHDEV_QUEUE_STAT_CNTRS];
	/**< Total number of queue RX packets. */
	uint64_t q_opackets[ODP_ETHDEV_QUEUE_STAT_CNTRS];
	/**< Total number of queue TX packets. */
	uint64_t q_ibytes[ODP_ETHDEV_QUEUE_STAT_CNTRS];
	/**< Total number of successfully received queue bytes. */
	uint64_t q_obytes[ODP_ETHDEV_QUEUE_STAT_CNTRS];
	/**< Total number of successfully transmitted queue bytes. */
	uint64_t q_errors[ODP_ETHDEV_QUEUE_STAT_CNTRS];
	/**< Total number of queue packets received that are dropped. */
	uint64_t ilbpackets;
	/**< Total number of good packets received from loopback,VF Only */
	uint64_t olbpackets;
	/**< Total number of good packets transmitted to loopback,VF Only */
	uint64_t ilbbytes;
	/**< Total number of good bytes received from loopback,VF Only */
	uint64_t olbbytes;
	/**< Total number of good bytes transmitted to loopback,VF Only */
};

/**
 * Configure an packet IO实例.
 *
 *  @param[in] pktio  ODP packet IO实例handle.
 *  @param[in] dev_conf 为packet IO实例的配置参数
 *
 *  @return :: 0 成功
 *  @return :: <0, 执行错误
 *
 *  @attention 该API是在linaro odp pktio API基础上新增的接口   
 */
int odp_pktio_configure(odp_pktio_t pktio,const struct odp_pktio_eth_conf * dev_conf);

/**
 * Restart an ODP packet IO实例
 *
 *  该函数用于restart一个packet IO实例
 *  @param[in]  pktio     ODP packet IO实例handle
 *
 *  @return :: 0 成功
 *  @return :: <0, 执行错误    
 *
 *  @attention 该API是在linaro odp pktio API基础上新增的接口 
 */
int odp_pktio_restart(odp_pktio_t pktio);

/**
 * reset an ODP packet IO实例的统计信息.
 *
 *  @param[in]  pktio     ODP packet IO实例handle
 *
 *  @return :: 0 成功
 *  @return :: <0, 执行错误    
 *
 *  @attention 该API是在linaro odp pktio API基础上新增的接口 
 */
int odp_pktio_stats_reset(odp_pktio_t pktio);

/**
 * Get an ODP packet IO实例的统计信息.
 *
 *  @param[in]  pktio     ODP packet IO实例handle
 *
 *  @return :: 0 成功
 *  @return :: <0, 执行错误    
 *
 *  @attention 该API是在linaro odp pktio API基础上新增的接口
 */
int odp_pktio_stats_get(odp_pktio_t pktio, struct odp_pktio_eth_stats *stats);

/**
 * Get an ODP packet IO实例的link状态信息
 *
 *  @param[in]  pktio     ODP packet IO实例handle
 *  @param[out] eth_link  link status
 *
 *  @return :: 0 成功
 *  @return :: <0, 执行错误    
 *
 *  @attention 该API是在linaro odp pktio API基础上新增的接口
 */
int odp_pktio_link_get(odp_pktio_t pktio, struct odp_pktio_eth_link *eth_link);

/**
 * 为 ODP packet IO实例设置 a MTU value 
 *
 *  @param[in]  pktio     ODP packet IO实例handle
 *  @param[in]  mtu       mtu value
 *
 *  @return :: 0 成功
 *  @return :: <0, 执行错误    
 *
 *  @attention 该API是在linaro odp pktio API基础上新增的接口
 */
int odp_pktio_mtu_set(odp_pktio_t pktio, uint16_t mtu);

enum odp_pktio_dev_type
{
    ODP_PKITIO_DEV_TYPE_PCI = 0x1,
    ODP_PKITIO_DEV_TYPE_SOC,    
};

struct odp_pktio_pci_id {
	uint16_t vendor_id;           /**< Vendor ID or PCI_ANY_ID. */
	uint16_t device_id;           /**< Device ID or PCI_ANY_ID. */
	uint16_t subsystem_vendor_id; /**< Subsystem vendor ID or PCI_ANY_ID. */
	uint16_t subsystem_device_id; /**< Subsystem device ID or PCI_ANY_ID. */
};

/**
 * A structure describing the location of a PCI device.
 */
struct odp_pktio_pci_addr {
	uint16_t domain;                /**< Device domain */
	uint8_t bus;                    /**< Device bus */
	uint8_t devid;                  /**< Device ID */
	uint8_t function;               /**< Device function. */
};

struct odp_pktio_dev_pci_info {
	struct odp_pktio_pci_addr addr;       /**< PCI location. */
	struct odp_pktio_pci_id id;           /**< PCI ID. */
    int numa_node;                  /**< NUMA node connection */
};

struct odp_pktio_dev_soc_info {
    int  if_idx;                    /*soc pktio网口范围内的编号*/
    int  numa_node;                 /**< NUMA node connection */
};

union odp_pktio_dev_info {
	struct odp_pktio_dev_soc_info soc_info; /**< soc info */
	struct odp_pktio_dev_pci_info pci_info; /**< pci info */  
};

#define PACKET_IO_NAME_LENGTH_MAX 16
struct odp_pktio_info
{
    char name[PACKET_IO_NAME_LENGTH_MAX];    /**< Packet IO device name */
    enum odp_pktio_dev_type if_type;         /**< Packet IO device type */
    union odp_pktio_dev_info info;           /**< Packet IO device info */  
};

/** 获取ODP所有packet IO实例的信息
 *
 *  该函数用于获取当前系统中所有packet IO实例的信息
 *
 *  @param[out]  pktio_info     ODP packet IO实例的相关信息
 *  @param[out]  num            ODP packet IO实例的个数
 *  
 *  @return :: 0 成功
 *  @return :: <0, 执行错误    
 *
 *  @attention 该API是在linaro odp pktio API基础上新增的接口 
 */   
int odp_pktio_dev_get(struct odp_pktio_info *pktio_info, uint8_t * num);


/**
 * @}
 */

#ifdef __cplusplus
}
#endif

#endif
