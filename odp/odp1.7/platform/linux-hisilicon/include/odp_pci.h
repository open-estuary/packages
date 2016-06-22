/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_PCI_H_
#define _ODP_PCI_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <stdlib.h>
#include <stdio.h>
#include <limits.h>
#include <errno.h>
#include <sys/queue.h>
#include <stdint.h>
#include <inttypes.h>

#include "odp_config.h"

enum odp_intr_handle_type {
	ODP_INTR_HANDLE_UNKNOWN = 0,
	ODP_INTR_HANDLE_UIO,
	ODP_INTR_HANDLE_UIO_INTX,
	ODP_INTR_HANDLE_VFIO_LEGACY,
	ODP_INTR_HANDLE_VFIO_MSI,
	ODP_INTR_HANDLE_VFIO_MSIX,
	ODP_INTR_HANDLE_ALARM,
	ODP_INTR_HANDLE_MAX
};

struct odp_intr_handle {
	union {
		int vfio_dev_fd;
		int uio_cfg_fd;
	};

	int			  fd;
	enum odp_intr_handle_type type;
};

TAILQ_HEAD(pci_device_list, odp_pci_device);
TAILQ_HEAD(pci_driver_list, odp_pci_driver);

extern struct pci_driver_list pci_driver_list;

extern struct pci_device_list pci_device_list;

#define SYSFS_PCI_DEVICES "/sys/bus/pci/devices"

#define PCI_PRI_FMT "%.4" PRIx16 ":%.2" PRIx8 ":%.2" PRIx8 ".%" PRIx8

#define PCI_SHORT_PRI_FMT "%.2" PRIx8 ":%.2" PRIx8 ".%" PRIx8

#define PCI_FMT_NVAL 4

#define PCI_RESOURCE_FMT_NVAL 3

#define IORESOURCE_MEM 0x00000200

struct odp_pci_resource {
	uint64_t phys_addr;
	uint64_t len;
	void	*addr;
};

#define PCI_MAX_RESOURCE 6

struct odp_pci_id {
	uint16_t vendor_id;
	uint16_t device_id;
	uint16_t subsystem_vendor_id;
	uint16_t subsystem_device_id;
};

struct odp_pci_addr {
	uint16_t domain;
	uint8_t	 bus;
	uint8_t	 devid;
	uint8_t	 function;
};

struct odp_devargs;

enum odp_kernel_driver {
	ODP_KDRV_UNKNOWN = 0,
	ODP_KDRV_IGB_UIO,
};

struct odp_pci_device {
	TAILQ_ENTRY(odp_pci_device) next;
	struct odp_pci_addr	addr;
	struct odp_pci_id	id;
	struct odp_pci_resource mem_resource[PCI_MAX_RESOURCE];
	struct odp_intr_handle	intr_handle;
	struct odp_pci_driver  *driver;
	uint16_t		max_vfs;
	int			numa_node;
	struct odp_devargs     *devargs;
	enum odp_kernel_driver	kdrv;
};

#define PCI_ANY_ID (0xffff)

#ifdef __cplusplus
#define ODP_PCI_DEVICE(vend, dev) \
	((vend),                   \
	 (dev),                    \
	 PCI_ANY_ID,               \
	 PCI_ANY_ID)
#else
#define ODP_PCI_DEVICE(vend, dev)          \
	.vendor_id = (vend),               \
	.device_id = (dev),                \
	.subsystem_vendor_id = PCI_ANY_ID, \
	.subsystem_device_id = PCI_ANY_ID
#endif

struct odp_pci_driver;

typedef int (pci_devinit_t)(struct odp_pci_driver *, struct odp_pci_device *);

typedef int (pci_devuninit_t)(struct odp_pci_device *);

struct odp_pci_driver {
	TAILQ_ENTRY(odp_pci_driver) next;

	const char	  *name;
	pci_devinit_t	  *devinit;
	pci_devuninit_t	  *devuninit;
	struct odp_pci_id *id_table;
	uint32_t	   drv_flags;
};

#define ODP_PCI_DRV_NEED_MAPPING 0x0001

#pragma GCC poison ODP_PCI_DRV_MULTIPLE

#define ODP_PCI_DRV_FORCE_UNBIND 0x0004

#define ODP_PCI_DRV_INTR_LSC 0x0008

#define ODP_PCI_DRV_DETACHABLE 0x0010

#define GET_PCIADDR_FIELD(in, fd, lim, dlm)                   \
	do {                                                            \
		unsigned long val;                                      \
		char *end;                                              \
		errno = 0;                                              \
		val = strtoul((in), &end, 16);                          \
		if ((errno != 0) || (end[0] != (dlm)) || (val > (lim))) \
			return (-EINVAL);                               \
		(fd) = (typeof(fd))val;                                 \
		(in) = end + 1;                                         \
	} while (0)

static inline int odp_parse_pci_BDF(const char		*input,
				    struct odp_pci_addr *dev_addr)
{
	dev_addr->domain = 0;

	GET_PCIADDR_FIELD(input, dev_addr->bus, UINT8_MAX, ':');
	GET_PCIADDR_FIELD(input, dev_addr->devid, UINT8_MAX, '.');
	GET_PCIADDR_FIELD(input, dev_addr->function, UINT8_MAX, 0);
	return 0;
}

static inline int odp_parse_pci_dombdf(const char	   *input,
				       struct odp_pci_addr *dev_addr)
{
	GET_PCIADDR_FIELD(input, dev_addr->domain, UINT16_MAX, ':');
	GET_PCIADDR_FIELD(input, dev_addr->bus, UINT8_MAX, ':');
	GET_PCIADDR_FIELD(input, dev_addr->devid, UINT8_MAX, '.');
	GET_PCIADDR_FIELD(input, dev_addr->function, UINT8_MAX, 0);
	return 0;
}

#undef GET_PCIADDR_FIELD
static inline int odp_compare_pci_addr(struct odp_pci_addr *addr,
				       struct odp_pci_addr *addr2)
{
	uint64_t dev_addr, dev_addr2;

	if ((!addr) || (!addr2))
		return -1;

	dev_addr = (addr->domain << 24) | (addr->bus << 16) |
		   (addr->devid << 8) | addr->function;
	dev_addr2 = (addr2->domain << 24) | (addr2->bus << 16) |
		    (addr2->devid << 8) | addr2->function;

	if (dev_addr > dev_addr2)
		return 1;
	else if (dev_addr < dev_addr2)
		return -1;
	else
		return 0;
}

int odp_pci_scan(void);

int odp_pci_probe(void);

int odp_probe_pci_one(struct odp_pci_addr *addr);

int odp_close_pci_one(struct odp_pci_addr *addr);

void odp_pci_register(struct odp_pci_driver *driver);

void odp_pci_unregister(struct odp_pci_driver *driver);

#ifdef __cplusplus
}
#endif
#endif /* _ODP_PCI_H_ */
