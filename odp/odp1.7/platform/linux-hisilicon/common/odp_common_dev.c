/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */
#include <stdio.h>
#include <string.h>
#include <inttypes.h>
#include <sys/queue.h>

#include <odp/config.h>
#include <odp_dev.h>
#include <odp_devargs.h>
#include "odp_private.h"
#include "odp_config.h"
#include "odp_debug_internal.h"

static struct odp_driver_list dev_driver_list =
	TAILQ_HEAD_INITIALIZER(dev_driver_list);

/*init virtual device*/
int odp_vdev_init(const char *name, const char *args)
{
	struct odp_driver *driver;

	if (!name)
		return -EINVAL;

	TAILQ_FOREACH(driver, &dev_driver_list, next)
	{
		if (driver->type != UMD_VDEV)
			continue;

		if (!strncmp(driver->name, name, strlen(driver->name)))
			return driver->init(name, args);
	}

	ODP_PRINT("no driver found for %s\n", name);
	return -EINVAL;
}

/*init physical device*/
int odp_dev_init(void)
{
	struct odp_devargs *args;
	struct odp_driver  *driver;

	TAILQ_FOREACH(args, &devargs_list, next)
	{
		if (args->type != ODP_DEVTYPE_VIRTUAL)
			continue;

		if (odp_vdev_init(args->virtual.drv_name, args->args)) {
			ODP_PRINT("failed to initialize %s device\n",
				  args->virtual.drv_name);
			return -1;
		}
	}

	TAILQ_FOREACH(driver, &dev_driver_list, next)
	{
		if (driver->type != UMD_PDEV)
			continue;

		if (driver->init)
			driver->init(NULL, NULL);
	}
	return 0;
}

#ifdef ODP_LIBODP_HOTPLUG
int odp_vdev_uninit(const char *name)
{
	struct odp_driver *driver;

	if (!name)
		return -EINVAL;

	TAILQ_FOREACH(driver, &dev_driver_list, next)
	{
		if (driver->type != UMD_VDEV)
			continue;

		if (!strncmp(driver->name, name, strlen(driver->name)))
			return driver->uninit(name);
	}

	ODP_PRINT("no driver found for %s\n", name);
	return -EINVAL;
}
#endif

/* register a driver */
void odp_driver_register(struct odp_driver *driver)
{
	TAILQ_INSERT_TAIL(&dev_driver_list, driver, next);
}

/* unregister a driver */
void odp_driver_unregister(struct odp_driver *driver)
{
	TAILQ_REMOVE(&dev_driver_list, driver, next);
}


