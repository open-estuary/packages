/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_DEVARGS_H_
#define _ODP_DEVARGS_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <stdio.h>
#include <sys/queue.h>
#include <odp_pci.h>

enum odp_devtype {
	WHITE_LIST_PCI,
	BLACK_LIST_PCI,
	ODP_DEVTYPE_VIRTUAL,
};

struct odp_devargs {
	TAILQ_ENTRY(odp_devargs) next;

	enum odp_devtype type;

	union {
		struct {
			struct odp_pci_addr addr;
		} pci;

		struct {
			char drv_name[32];
		} virtual;
	};

	char *args;
};

TAILQ_HEAD(odp_devargs_list, odp_devargs);

extern struct odp_devargs_list devargs_list;

int odp_parse_devargs_str(const char *devargs_str,
			  char **drvname, char **drvargs);

int odp_devargs_add(enum odp_devtype devtype, const char *devargs_str);

unsigned int
odp_devargs_type_count(enum odp_devtype devtype);


#ifdef __cplusplus
}
#endif
#endif /* _ODP_DEVARGS_H_ */
