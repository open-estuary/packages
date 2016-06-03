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
#include <unistd.h>
#include <fcntl.h>
#include <dirent.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <linux/pci_regs.h>

#include <odp_pci.h>
#include <odp_syslayout.h>
#include <odp_common.h>
#include <odp/config.h>
#include "odp_filesystem.h"
#include "odp_pci_init.h"
#include "odp_debug_internal.h"
#include "odp_uio_internal.h"

static struct odp_tailq_elem odp_uio_tailq = {
	.name = "UIO_RESOURCE_LIST",
};

ODP_REGISTER_TAILQ(odp_uio_tailq)

#define OFF_MAX ((uint64_t)(off_t)-1)

void *pci_map_addr = NULL;

/*map pci uio respurce in second process*/
int map_pci_uio_secondary(struct odp_pci_device *dev)
{
	int fd, i;
	struct mapped_pci_resource *res;
	struct mapped_pci_res_list *res_list =
		ODP_TAILQ_CAST(odp_uio_tailq.head, mapped_pci_res_list);

	TAILQ_FOREACH(res, res_list, next)
	{
		if (odp_compare_pci_addr(&res->pci_addr, &dev->addr))
			continue;

		for (i = 0; i != res->nb_maps; i++) {

			fd = open(res->maps[i].path, O_RDWR);
			if (fd < 0) {
				ODP_PRINT("Cannot open %s: %s\n",
					  res->maps[i].path,
					  strerror(errno));
				return -1;
			}

			void *mapaddr =
				pci_map_resource(res->maps[i].addr,
						 fd,
						 (off_t)res->maps[i].offset,
						 (size_t)res->maps[i].size,
						 0);

			if (mapaddr != res->maps[i].addr) {
				if (mapaddr == MAP_FAILED)
					ODP_PRINT(
						"Cannot mmap device resource file %s: %s\n",
						res->maps[i].path,
						strerror(errno));
				else
					ODP_PRINT(
						"Cannot mmap device resource file %s to address: %p\n",
						res->maps[i].path,
						res->maps[i].addr);

				close(fd);
				return -1;
			}

			close(fd);
		}

		return 0;
	}

	ODP_PRINT("Cannot find resource for device\n");
	return 1;
}

/*set pci uio bus*/
int set_pci_uio_bus_master(int dev_fd)
{
	uint16_t reg;
	int ret;

	ret = pread(dev_fd, &reg, sizeof(reg), PCI_COMMAND);
	if (ret != sizeof(reg)) {
		ODP_PRINT("Cannot read command from PCI config space!\n");
		return -1;
	}

	if (reg & PCI_COMMAND_MASTER)
		return 0;

	reg |= PCI_COMMAND_MASTER;

	ret = pwrite(dev_fd, &reg, sizeof(reg), PCI_COMMAND);
	if (ret != sizeof(reg)) {
		ODP_PRINT("Cannot write command to PCI config space!\n");
		return -1;
	}

	return 0;
}

/*get pci uio device information*/
int get_pci_uio_dev(struct odp_pci_device *dev, char *dstbuf,
			   unsigned int buflen)
{
	DIR *dir;
	struct dirent *e;
	unsigned int uio_num;
	char dirname[ODP_PATH_MAX];
	struct odp_pci_addr *loc = &dev->addr;

	snprintf(dirname, sizeof(dirname),
		 SYSFS_PCI_DEVICES "/" PCI_PRI_FMT "/uio",
		 loc->domain, loc->bus, loc->devid, loc->function);

	dir = opendir(dirname);
	if (!dir) {
		snprintf(dirname, sizeof(dirname),
			 SYSFS_PCI_DEVICES "/" PCI_PRI_FMT,
			 loc->domain, loc->bus, loc->devid, loc->function);
		dir = opendir(dirname);

		if (!dir) {
			ODP_PRINT("Cannot opendir %s\n", dirname);
			return -1;
		}
	}

	while ((e = readdir(dir)) != NULL) {
		int shortprefix_len = sizeof("uio") - 1;

		int longprefix_len = sizeof("uio:uio") - 1;
		char *endptr;

		if (strncmp(e->d_name, "uio", 3) != 0)
			continue;

		errno = 0;
		uio_num = strtoull(e->d_name + longprefix_len, &endptr, 10);
		if ((errno == 0) && (endptr != (e->d_name + longprefix_len))) {
			snprintf(dstbuf, buflen, "%s/uio:uio%u",
				 dirname, uio_num);
			break;
		}

		errno = 0;
		uio_num = strtoull(e->d_name + shortprefix_len, &endptr, 10);
		if ((errno == 0) && (endptr != (e->d_name + shortprefix_len))) {
			snprintf(dstbuf, buflen, "%s/uio%u", dirname, uio_num);
			break;
		}

	}

	closedir(dir);

	if (!e)
		return -1;

	return uio_num;
}

/*map pci uio device resource*/
int pci_uio_map_resource(struct odp_pci_device *dev)
{
	int i, map_idx;
	void *mapaddr;
	int  uio_num;
	uint64_t phaddr;
	char dirname[ODP_PATH_MAX];
	char cfgname[ODP_PATH_MAX];
	char devname[ODP_PATH_MAX];
	struct odp_pci_addr *loc = &dev->addr;
	struct mapped_pci_resource *res;
	struct mapped_pci_res_list *res_list =
		ODP_TAILQ_CAST(odp_uio_tailq.head, mapped_pci_res_list);
	struct pci_map *maps;

	dev->intr_handle.fd = -1;
	dev->intr_handle.uio_cfg_fd = -1;
	dev->intr_handle.type = ODP_INTR_HANDLE_UNKNOWN;

	if (odp_process_type() != ODP_PROC_PRIMARY)
		return map_pci_uio_secondary(dev);

	uio_num = get_pci_uio_dev(dev, dirname, sizeof(dirname));
	if (uio_num < 0) {
		ODP_PRINT("  " PCI_PRI_FMT " not managed by UIO"
			  " driver, skipping\n",
			  loc->domain, loc->bus, loc->devid, loc->function);
		return 1;
	}

	snprintf(devname, sizeof(devname), "/dev/uio%u", uio_num);

	dev->intr_handle.fd = open(devname, O_RDWR);
	if (dev->intr_handle.fd < 0) {
		ODP_PRINT("Cannot open %s: %s\n",
			  devname, strerror(errno));
		return -1;
	}

	dev->intr_handle.type = ODP_INTR_HANDLE_UIO;

	snprintf(cfgname, sizeof(cfgname),
		 "/sys/class/uio/uio%u/device/config", uio_num);
	dev->intr_handle.uio_cfg_fd = open(cfgname, O_RDWR);
	if (dev->intr_handle.uio_cfg_fd < 0) {
		ODP_PRINT("Cannot open %s: %s\n",
			  cfgname, strerror(errno));
		return -1;
	}

	if (set_pci_uio_bus_master(dev->intr_handle.uio_cfg_fd)) {
		ODP_PRINT("Cannot set up bus mastering!\n");
		return -1;
	}

	res = malloc(sizeof(*res));
	if (!res) {
		ODP_PRINT("%s(): cannot store uio mmap details\n", __func__);
		return -1;
	}

	memset((void *)res, 0, sizeof(*res));
	snprintf(res->path, sizeof(res->path), "%s", devname);
	memcpy(&res->pci_addr, &dev->addr, sizeof(res->pci_addr));

	maps = res->maps;
	for (i = 0, map_idx = 0; i != PCI_MAX_RESOURCE; i++) {
		int fail = 0;

		phaddr = dev->mem_resource[i].phys_addr;
		if (phaddr == 0)
			continue;

		snprintf(devname, sizeof(devname),
			 SYSFS_PCI_DEVICES "/" PCI_PRI_FMT "/resource%d",
			 loc->domain, loc->bus, loc->devid, loc->function,
			 i);

		mapaddr = pci_map_resource(NULL, dev->intr_handle.fd,
					   (map_idx) * PAGE_SIZE,
					   (size_t)dev->mem_resource[i].len, 0);
		if (mapaddr == MAP_FAILED)
			fail = 1;

		maps[map_idx].path = malloc(strlen(devname) + 1);
		if (!maps[map_idx].path)
			fail = 1;

		if (fail) {
			free(res);
			return -1;
		}

		maps[map_idx].phaddr = dev->mem_resource[i].phys_addr;
		maps[map_idx].size = dev->mem_resource[i].len;
		maps[map_idx].addr = mapaddr;
		maps[map_idx].offset = 0;
		strcpy(maps[map_idx].path, devname);
		map_idx++;
		dev->mem_resource[i].addr = mapaddr;
	}

	res->nb_maps = map_idx;

	TAILQ_INSERT_TAIL(res_list, res, next);

	return 0;
}

