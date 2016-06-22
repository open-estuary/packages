/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef ODP_PCI_INIT_H_
#define ODP_PCI_INIT_H_

struct pci_map {
	void	*addr;
	char	*path;
	uint64_t offset;
	uint64_t size;
	uint64_t phaddr;
};

struct mapped_pci_resource {
	TAILQ_ENTRY(mapped_pci_resource) next;

	struct odp_pci_addr pci_addr;
	char		    path[ODP_PATH_MAX];
	int		    nb_maps;
	struct pci_map	    maps[PCI_MAX_RESOURCE];
};

TAILQ_HEAD(mapped_pci_res_list, mapped_pci_resource);

extern void *pci_map_addr;
void *pci_find_max_end_va(void);

void *pci_map_resource(void *requested_addr, int fd, off_t offset,
		       size_t size, int additional_flags);

int pci_uio_map_resource(struct odp_pci_device *dev);

void pci_unmap_resource(void *requested_addr, size_t size);

#endif /* ODP_PCI_INIT_H_ */
