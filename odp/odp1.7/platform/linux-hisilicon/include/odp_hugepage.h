/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef ODP_HUGEPAGE_H
#define ODP_HUGEPAGE_H

#include <stddef.h>
#include <stdint.h>
#include <limits.h>

#define MAX_HUGEPAGE_PATH ODP_PATH_MAX

struct hugepage_file {
	void	*orig_va;
	void	*final_va;
	uint64_t physaddr;
	size_t	 size;
	int	 socket_id;
	int	 file_id;
	int	 mmfrag_id;
	char filepath[MAX_HUGEPAGE_PATH];
};

int odp_hugepage_info_init(void);
#endif /* ODP_HUGEPAGES_H */
