/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_DEV_H_
#define _ODP_DEV_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <sys/queue.h>

TAILQ_HEAD(odp_driver_list, odp_driver);

typedef int (odp_dev_init_t)(const char *name, const char *args);

typedef int (odp_dev_uninit_t)(const char *name);

enum umd_type {
	UMD_VDEV = 0,
	UMD_PDEV = 1,
};

struct odp_driver {
	TAILQ_ENTRY(odp_driver) next;
	enum umd_type	  type;
	const char	 *name;
	odp_dev_init_t	 *init;
	odp_dev_uninit_t *uninit;
};

void odp_driver_register(struct odp_driver *driver);

void odp_driver_unregister(struct odp_driver *driver);

int odp_vdev_init(const char *name, const char *args);

int odp_vdev_uninit(const char *name);

#define UMD_REGISTER_DRIVER(d) \
	void devinitfn_ ## d(void); \
	void __attribute__((constructor, used)) devinitfn_ ## d(void) \
	{ \
		odp_driver_register(&d); \
	}

#ifdef __cplusplus
}
#endif
#endif /* _ODP_VDEV_H_ */
