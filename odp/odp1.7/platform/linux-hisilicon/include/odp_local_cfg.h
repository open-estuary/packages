/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef ODP_INTERNAL_CFG_H
#define ODP_INTERNAL_CFG_H

#include <odp_common.h>
#include <odp_pci_dev_feature_defs.h>

#define MAX_HUGEPAGE_SIZES 3

struct odp_hugepage_type {
	uint64_t    hugepage_sz;
	const char *hugedir;
	uint32_t    num_pages[ODP_MAX_NUMA_NODES];
	int lock_descriptor;
};

struct odp_local_config {
	size_t		     memory;
	unsigned	     force_channel_num;
	unsigned	     force_rank_num;
	unsigned	     no_hugetlbfs;
	unsigned	     no_pci;
	unsigned	     no_shconf;
	enum odp_proc_type_t process_type;

	unsigned force_sockets;

	uint64_t socket_mem[ODP_MAX_NUMA_NODES];

	uintptr_t base_virtaddr;
	int	  syslog_facility;

	enum odp_intr_mode vfio_intr_mode;

	unsigned		 num_hugepage_types;
	struct odp_hugepage_type odp_hugepage_type[MAX_HUGEPAGE_SIZES];
};

extern struct odp_local_config local_config;
void odp_init_local_config(struct odp_local_config *local_cfg);
#endif /* ODP_INTERNAL_CFG_H */
