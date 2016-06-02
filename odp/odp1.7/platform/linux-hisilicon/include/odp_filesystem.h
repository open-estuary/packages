/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef ODP_FILESYSTEM_H
#define ODP_FILESYSTEM_H

#define RUNTIME_CONFIG_FMT "%s/.%s_config"

#include <stdint.h>
#include <limits.h>
#include <unistd.h>
#include <stdlib.h>

#include "odp_local_cfg.h"
#include "odp_debug_internal.h"

#define HUGEFILE_PREFIX_DEFAULT "odp"

extern struct odp_local_config local_config;

static const char *default_config_dir = "/var/run";

static inline const char *odp_runtime_config_path(void)
{
	static char buffer[ODP_PATH_MAX];
	const char *directory = default_config_dir;

	snprintf(buffer, sizeof(buffer) - 1, RUNTIME_CONFIG_FMT, directory,
		 HUGEFILE_PREFIX_DEFAULT);

	return buffer;
}

#define HUGEPAGE_INFO_FMT "%s/.%s_hugepage_info"

static inline const char *odp_hugepage_info_path(void)
{
	static char buffer[ODP_PATH_MAX];
	const char *directory = default_config_dir;

	snprintf(buffer, sizeof(buffer) - 1, HUGEPAGE_INFO_FMT, directory,
		 HUGEFILE_PREFIX_DEFAULT);
	return buffer;
}

#define HUGEFILE_FMT	  "%s/%smap_%d"
#define TEMP_HUGEFILE_FMT "%s/%smap_temp_%d"

static inline const char *odp_get_hugefile_path(char *buffer, size_t buflen,
						const char *hugedir, int f_id)
{
	snprintf(buffer, buflen, HUGEFILE_FMT, hugedir,
		 HUGEFILE_PREFIX_DEFAULT, f_id);
	buffer[buflen - 1] = '\0';
	return buffer;
}

unsigned long odp_parse_sysfs_value(const char *filename);
#endif /* ODP_FILESYSTEM_H */
