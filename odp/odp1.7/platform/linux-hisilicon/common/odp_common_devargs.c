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

/* #include <rte_log.h> */
#include <odp/config.h>
#include <odp_pci.h>
#include <odp_devargs.h>
#include "odp_private.h"
#include "odp_debug_internal.h"

/** Global list of user devices */
struct odp_devargs_list devargs_list =
	TAILQ_HEAD_INITIALIZER(devargs_list);

/*parse device args string*/
int odp_parse_devargs_str(const char *devargs_str,
			  char **drvname, char **drvargs)
{
	char *sep;

	if (!devargs_str || !drvname || !drvargs)
		return -1;

	*drvname = strdup(devargs_str);
	if (!drvname) {
		ODP_PRINT("cannot allocate temp memory for driver name\n");
		return -1;
	}

	sep = strchr(*drvname, ',');
	if (sep) {
		sep[0] = '\0';
		*drvargs = strdup(sep + 1);
	} else {
		*drvargs = strdup("");
	}

	if (!(*drvargs)) {
		ODP_PRINT("cannot allocate temp memory for driver arguments\n");
		free(*drvname);
		return -1;
	}

	return 0;
}

/*add device args*/
int odp_devargs_add(enum odp_devtype devtype, const char *devargs_str)
{
	struct odp_devargs *devargs = NULL;
	char *buf = NULL;
	int   ret;

	/* use malloc instead of odp_malloc as it's called early at init */
	devargs = malloc(sizeof(*devargs));
	if (!devargs) {
		ODP_PRINT("cannot allocate devargs\n");
		goto fail;
	}

	memset(devargs, 0, sizeof(*devargs));
	devargs->type = devtype;

	if (odp_parse_devargs_str(devargs_str, &buf, &devargs->args))
		goto fail;

	switch (devargs->type) {
	case WHITE_LIST_PCI:
	case BLACK_LIST_PCI:

		if ((odp_parse_pci_BDF(buf, &devargs->pci.addr) != 0) &&
		    (odp_parse_pci_dombdf(buf, &devargs->pci.addr) != 0)) {
			ODP_PRINT("invalid PCI identifier <%s>\n", buf);
			goto fail;
		}

		break;
	case ODP_DEVTYPE_VIRTUAL:

		ret = snprintf(devargs->virtual.drv_name,
			       sizeof(devargs->virtual.drv_name), "%s", buf);
		if ((ret < 0) ||
		    (ret >= (int)sizeof(devargs->virtual.drv_name))) {
			ODP_PRINT("driver name too large: <%s>\n", buf);
			goto fail;
		}

		break;
	}

	free(buf);
	TAILQ_INSERT_TAIL(&devargs_list, devargs, next);
	return 0;

fail:
	if (buf)
		free(buf);

	if (devargs) {
		free(devargs->args);
		free(devargs);
	}

	return -1;
}

/*count device args type*/
unsigned int odp_devargs_type_count(enum odp_devtype devtype)
{
	struct odp_devargs *devargs;
	unsigned int count = 0;

	TAILQ_FOREACH(devargs, &devargs_list, next)
	{
		if (devargs->type != devtype)
			continue;

		count++;
	}
	return count;
}

