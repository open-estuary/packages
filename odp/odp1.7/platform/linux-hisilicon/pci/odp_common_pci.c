/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#include <string.h>
#include <inttypes.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/queue.h>

#include <odp_pci.h>
#include <odp_core.h>
#include <odp_memory.h>
#include <odp_mmdistrict.h>
#include <odp_base.h>
#include <odp/config.h>
#include <odp_common.h>
#include <odp_devargs.h>
#include "odp_private.h"
#include "odp_config.h"
#include "odp_debug_internal.h"

struct pci_driver_list pci_driver_list;
struct pci_device_list pci_device_list;

/*probe all pci drivers*/
int probe_all_pci_drivers(struct odp_pci_device *dev)
{
	struct odp_pci_driver *dr = NULL;
	int rc = 0;

	if (!dev)
		return -1;

	TAILQ_FOREACH(dr, &pci_driver_list, next)
	{
		rc = odp_pci_probe_one_driver(dr, dev);
		if (rc < 0)
			return -1;

		if (rc > 0)
			continue;

		return 0;
	}
	return 1;
}

#ifdef ODP_LIBODP_HOTPLUG

/*close all pci drivers*/
int close_all_pci_drivers(struct odp_pci_device *dev)
{
	struct odp_pci_driver *dr = NULL;
	int rc = 0;

	if (!dev)
		return -1;

	TAILQ_FOREACH(dr, &pci_driver_list, next)
	{
		rc = odp_pci_close_one_driver(dr, dev);
		if (rc < 0)
			return -1;

		if (rc > 0)
			continue;

		return 0;
	}
	return 1;
}

/*probe one pci driver*/
int odp_probe_pci_one(struct odp_pci_addr *addr)
{
	struct odp_pci_device *dev = NULL;
	int ret = 0;

	if (!addr)
		return -1;

	TAILQ_FOREACH(dev, &pci_device_list, next)
	{
		if (odp_compare_pci_addr(&dev->addr, addr))
			continue;

		ret = probe_all_pci_drivers(dev);
		if (ret < 0)
			goto err_return;

		return 0;
	}
	return -1;

err_return:
	ODP_PRINT("Requested device " PCI_PRI_FMT
		  " cannot be used\n", dev->addr.domain, dev->addr.bus,
		  dev->addr.devid, dev->addr.function);
	return -1;
}

/*close one pci driver*/
int odp_close_pci_one(struct odp_pci_addr *addr)
{
	struct odp_pci_device *dev = NULL;
	int ret = 0;

	if (!addr)
		return -1;

	TAILQ_FOREACH(dev, &pci_device_list, next)
	{
		if (odp_compare_pci_addr(&dev->addr, addr))
			continue;

		ret = close_all_pci_drivers(dev);
		if (ret < 0)
			goto err_return;

		TAILQ_REMOVE(&pci_device_list, dev, next);
		return 0;
	}
	return -1;

err_return:
	ODP_PRINT("Requested device " PCI_PRI_FMT
		  " cannot be used\n", dev->addr.domain, dev->addr.bus,
		  dev->addr.devid, dev->addr.function);
	return -1;
}
#endif

/*look up pci dev args*/
struct odp_devargs *pci_devargs_lookup(struct odp_pci_device *dev)
{
	struct odp_devargs *args;

	TAILQ_FOREACH(args, &devargs_list, next)
	{
		if ((args->type != BLACK_LIST_PCI) &&
		    (args->type != WHITE_LIST_PCI))
			continue;

		if (!odp_compare_pci_addr(&dev->addr, &args->pci.addr))
			return args;
	}
	return NULL;
}

/*probe pci device*/
int odp_pci_probe(void)
{
	int ret = 0;
	int probe_all_flag = 0;
	struct odp_pci_device *dev = NULL;
	struct odp_devargs *args;

	if (odp_devargs_type_count(WHITE_LIST_PCI) == 0)
		probe_all_flag = 1;

	TAILQ_FOREACH(dev, &pci_device_list, next)
	{
		args = pci_devargs_lookup(dev);
		if (args)
			dev->devargs = args;

		if (probe_all_flag)
			ret = probe_all_pci_drivers(dev);
		else if ((args) && (args->type ==
				       WHITE_LIST_PCI))
			ret = probe_all_pci_drivers(dev);

		if (ret < 0) {
			ODP_PRINT("Requested device " PCI_PRI_FMT
				  " cannot be used\n",
				  dev->addr.domain, dev->addr.bus,
				  dev->addr.devid, dev->addr.function);
			abort();
		}
	}

	return 0;
}

/* register a driver */
void odp_pci_register(struct odp_pci_driver *driver)
{
	TAILQ_INSERT_TAIL(&pci_driver_list, driver, next);
}

/* unregister a driver */
void odp_pci_unregister(struct odp_pci_driver *driver)
{
	TAILQ_REMOVE(&pci_driver_list, driver, next);
}
