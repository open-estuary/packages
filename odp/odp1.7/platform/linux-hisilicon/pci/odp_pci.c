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
#include <dirent.h>
#include <sys/mman.h>

#include <odp/config.h>
#include <odp_pci.h>
#include <odp_syslayout.h>
#include <odp_devargs.h>
#include "odp_filesystem.h"
#include "odp_private.h"
#include "odp_pci_init.h"
#include "odp_debug_internal.h"

/*get pci kernal driver name by path*/
int get_pci_kdrv_name_by_path(const char *filename, char *dri_name)
{
	int count;
	char path[ODP_PATH_MAX];
	char *name;

	if (!filename || !dri_name)
		return -1;

	count = readlink(filename, path, PATH_MAX);
	if (count < 0)
		return 1;
	if (count >= PATH_MAX)
		return -1;

	path[count] = '\0';
	name = strrchr(path, '/');
	if (name) {
		strncpy(dri_name, name + 1, strlen(name + 1) + 1);
		return 0;
	}

	return -1;
}

/*find pci max end virtual addr*/
void *pci_find_max_end_va(void)
{
	const struct odp_mmfrag *seg  = odp_get_physmem_layout();
	const struct odp_mmfrag *last = seg;
	unsigned i = 0;

	for (i = 0; i < ODP_MAX_MMFRAG; i++, seg++) {
		if (!seg->addr)
			break;

		if (seg->addr > last->addr)
			last = seg;
	}

	return ODP_PTR_ADD(last->addr, last->len);
}

/*map pci resource*/
void *pci_map_resource(void *requested_addr, int fd, off_t offset, size_t size,
		       int additional_flags)
{
	void *mapaddr;

	mapaddr = mmap(requested_addr, size, PROT_READ | PROT_WRITE,
		       MAP_SHARED | additional_flags, fd, offset);
	if (mapaddr == MAP_FAILED)
		ODP_PRINT("%s(): cannot mmap(%d, %p, 0x%lx, 0x%lx): %s (%p)\n",
			  __func__, fd, requested_addr,
			  (unsigned long)size, (unsigned long)offset,
			  strerror(errno), mapaddr);
	else
		ODP_PRINT("  PCI memory mapped at %p\n", mapaddr);

	return mapaddr;
}

/*unmap pci resource*/
void pci_unmap_resource(void *requested_addr, size_t size)
{
	if (!requested_addr)
		return;

	if (munmap(requested_addr, size))
		ODP_PRINT("%s(): cannot munmap(%p, 0x%lx): %s\n",
			  __func__, requested_addr, (unsigned long)size,
			  strerror(errno));
	else
		ODP_PRINT("  PCI memory unmapped at %p\n", requested_addr);
}

/*parse pci sysfs resource*/
int pci_parse_sysfs_resource(const char		  *filename,
				    struct odp_pci_device *dev)
{
	FILE *f;
	int i;
	uint64_t phys_addr, end_addr, flags;
	char  buf[ODP_BUFF_SIZE];
	union pci_resource_info {
		struct {
			char *phys_addr;
			char *end_addr;
			char *flags;
		};
		char *ptrs[PCI_RESOURCE_FMT_NVAL];
	} res_info;

	f = fopen(filename, "r");
	if (!f) {
		ODP_PRINT("Cannot open sysfs resource\n");
		return -1;
	}

	for (i = 0; i < PCI_MAX_RESOURCE; i++) {
		if (!fgets(buf, sizeof(buf), f)) {
			ODP_PRINT("%s(): cannot read resource\n", __func__);
			goto error;
		}

		if (odp_strsplit(buf, sizeof(buf),
				 res_info.ptrs, 3, ' ') != 3) {
			ODP_PRINT("%s(): bad resource format\n", __func__);
			goto error;
		}

		errno = 0;
		phys_addr = strtoull(res_info.phys_addr, NULL, 16);
		end_addr  = strtoull(res_info.end_addr, NULL, 16);
		flags = strtoull(res_info.flags, NULL, 16);
		if (errno != 0) {
			ODP_PRINT("%s(): bad resource format\n", __func__);
			goto error;
		}

		if (flags & IORESOURCE_MEM) {
			dev->mem_resource[i].phys_addr = phys_addr;
			dev->mem_resource[i].len = end_addr - phys_addr + 1;

			dev->mem_resource[i].addr = NULL;
		}
	}

	fclose(f);
	return 0;

error:
	fclose(f);
	return -1;
}

/*scan one pci device*/
int odp_scan_one_pci_dev(const char *dirname, uint16_t domain, uint8_t bus,
			    uint8_t devid, uint8_t function)
{
	char filename[ODP_PATH_MAX];
	unsigned long tmp;
	struct odp_pci_device *dev;
	char driver[ODP_PATH_MAX];
	int  ret;

	dev = malloc(sizeof(*dev));
	if (!dev)
		return -1;

	memset(dev, 0, sizeof(*dev));
	dev->addr.domain = domain;
	dev->addr.bus = bus;
	dev->addr.devid = devid;
	dev->addr.function = function;

	/* get device id */
	snprintf(filename, sizeof(filename), "%s/device", dirname);
	tmp = odp_parse_sysfs_value(filename);
	if (tmp < 0) {
		free(dev);
		return -1;
	}

	dev->id.device_id = (uint16_t)tmp;

	/* get vendor id */
	snprintf(filename, sizeof(filename), "%s/vendor", dirname);
	tmp = odp_parse_sysfs_value(filename);
	if (tmp < 0) {
		free(dev);
		return -1;
	}

	dev->id.vendor_id = (uint16_t)tmp;

	/* get subsystem_device id */
	snprintf(filename, sizeof(filename), "%s/subsystem_device", dirname);
	tmp = odp_parse_sysfs_value(filename);
	if (tmp < 0) {
		free(dev);
		return -1;
	}

	dev->id.subsystem_device_id = (uint16_t)tmp;

	/* get subsystem_vendor id */
	snprintf(filename, sizeof(filename), "%s/subsystem_vendor", dirname);
	tmp = odp_parse_sysfs_value(filename);
	if (tmp < 0) {
		free(dev);
		return -1;
	}

	dev->id.subsystem_vendor_id = (uint16_t)tmp;

	/* get max_vfs */
	dev->max_vfs = 0;
	snprintf(filename, sizeof(filename), "%s/max_vfs", dirname);
	tmp = odp_parse_sysfs_value(filename);
	if (!access(filename, F_OK) &&
	    (tmp != -1)) {
		dev->max_vfs = (uint16_t)tmp;
	} else {

		snprintf(filename, sizeof(filename), "%s/sriov_numvfs",
			 dirname);
		tmp = odp_parse_sysfs_value(filename);
		if (!access(filename, F_OK) &&
		    (tmp != -1))
			dev->max_vfs = (uint16_t)tmp;
	}

	snprintf(filename, sizeof(filename), "%s/resource", dirname);
	ret = pci_parse_sysfs_resource(filename, dev);
	if (ret < 0) {
		ODP_ERR("%s(): cannot parse resource\n", __func__);
		free(dev);
		return -1;
	}

	snprintf(filename, sizeof(filename), "%s/numa_node", dirname);
	if (access(filename, R_OK) != 0) {
		dev->numa_node = -1;
	} else {
		tmp = odp_parse_sysfs_value(filename);
		if (tmp < 0) {
			free(dev);
			return -1;
		}

		dev->numa_node = tmp;
	}

	snprintf(filename, sizeof(filename), "%s/driver", dirname);
	ret = get_pci_kdrv_name_by_path(filename, driver);
	if (!ret) {
		if (!strcmp(driver, "igb_uio"))
			dev->kdrv = ODP_KDRV_IGB_UIO;
		else
			dev->kdrv = ODP_KDRV_UNKNOWN;
	} else if (ret > 0) {
		dev->kdrv = ODP_KDRV_UNKNOWN;
	} else {
		ODP_ERR("Fail to get kernel driver\n");
		free(dev);
		return -1;
	}

	if (TAILQ_EMPTY(&pci_device_list)) {
		TAILQ_INSERT_TAIL(&pci_device_list, dev, next);
	} else {
		struct odp_pci_device *dev2 = NULL;
		int ret;

		TAILQ_FOREACH(dev2, &pci_device_list, next)
		{
			ret = odp_compare_pci_addr(&dev->addr, &dev2->addr);
			if (ret > 0) {
				continue;
			} else if (ret < 0) {
				TAILQ_INSERT_BEFORE(dev2, dev, next);
			} else {
				dev2->kdrv = dev->kdrv;
				dev2->max_vfs = dev->max_vfs;
				memmove(dev2->mem_resource,
					dev->mem_resource,
					sizeof(dev->mem_resource));
				free(dev);
			}

			return 0;
		}
		TAILQ_INSERT_TAIL(&pci_device_list, dev, next);
	}

	return 0;
}

/*parse pci addr format*/
static int parse_pci_addr_format(const char *buf, int bufsize, uint16_t *domain,
				 uint8_t *bus, uint8_t *devid,
				 uint8_t *function)
{
	union splitaddr {
		struct {
			char *domain;
			char *bus;
			char *devid;
			char *function;
		};
		char *str[PCI_FMT_NVAL];
	} splitaddr;

	char *buf_copy = strndup(buf, bufsize);

	if (!buf_copy)
		return -1;

	if (odp_strsplit(buf_copy, bufsize, splitaddr.str, PCI_FMT_NVAL, ':')
	    != PCI_FMT_NVAL - 1)
		goto error;

	splitaddr.function = strchr(splitaddr.devid, '.');
	if (!splitaddr.function)
		goto error;

	*splitaddr.function++ = '\0';

	errno = 0;
	*domain = (uint16_t)strtoul(splitaddr.domain, NULL, 16);
	*bus = (uint8_t)strtoul(splitaddr.bus, NULL, 16);
	*devid = (uint8_t)strtoul(splitaddr.devid, NULL, 16);
	*function = (uint8_t)strtoul(splitaddr.function, NULL, 10);
	if (errno != 0)
		goto error;

	free(buf_copy);
	return 0;
error:
	free(buf_copy);
	return -1;
}

/*scan pci device*/
int odp_pci_scan(void)
{
	DIR *dir;
	uint16_t domain;
	struct dirent *e;
	char dirname[PATH_MAX];
	uint8_t	 bus, devid, function;

	dir = opendir(SYSFS_PCI_DEVICES);
	if (!dir) {
		ODP_ERR("%s(): opendir failed: %s\n", __func__,
			strerror(errno));
		return -1;
	}

	while ((e = readdir(dir)) != NULL) {
		if (e->d_name[0] == '.')
			continue;

		if (parse_pci_addr_format(e->d_name, sizeof(e->d_name),
					  &domain, &bus, &devid,
					  &function) != 0)
			continue;

		snprintf(dirname, sizeof(dirname), "%s/%s",
			 SYSFS_PCI_DEVICES, e->d_name);
		if (odp_scan_one_pci_dev(dirname,
				     domain, bus, devid, function) < 0) {
			closedir(dir);
			ODP_ERR("odp_scan_one_pci_dev fail!!!");

			return -1;
		}
	}

	closedir(dir);
	return 0;
}

/*map pci device resource*/
static int pci_map_device(struct odp_pci_device *dev)
{
	int ret = -1;

	switch (dev->kdrv) {
	case ODP_KDRV_IGB_UIO:
		ret = pci_uio_map_resource(dev);
		break;
	default:
		ODP_PRINT("Not managed by a supported kernel driver,skipp\n");
		ret = 1;
		break;
	}

	return ret;
}

/*probe one pci driver*/
int odp_pci_probe_one_driver(struct odp_pci_driver *dr,
			     struct odp_pci_device *dev)
{
	int ret;
	struct odp_pci_id *id_table;

	for (id_table = dr->id_table; id_table->vendor_id != 0; id_table++) {

		if ((id_table->vendor_id != dev->id.vendor_id) &&
		    (id_table->vendor_id != PCI_ANY_ID))
			continue;

		if ((id_table->device_id != dev->id.device_id) &&
		    (id_table->device_id != PCI_ANY_ID))
			continue;

		if ((id_table->subsystem_vendor_id !=
		     dev->id.subsystem_vendor_id) &&
		    (id_table->subsystem_vendor_id != PCI_ANY_ID))
			continue;

		if ((id_table->subsystem_device_id !=
		     dev->id.subsystem_device_id) &&
		    (id_table->subsystem_device_id != PCI_ANY_ID))
			continue;

		struct odp_pci_addr *loc = &dev->addr;

		ODP_PRINT("  probe driver: %x:%x %s\n", dev->id.vendor_id,
			  dev->id.device_id, dr->name);

		ODP_PRINT("PCI device " PCI_PRI_FMT " on NUMA socket %i\n",
			  loc->domain, loc->bus, loc->devid, loc->function,
			  dev->numa_node);

		if (dr->drv_flags & ODP_PCI_DRV_NEED_MAPPING) {

			ret = pci_map_device(dev);
			if (ret != 0)
				return ret;
		}

		if ((dev->devargs) &&
		    (dev->devargs->type == BLACK_LIST_PCI)) {
			ODP_PRINT("  Device is blacklisted, not initializing\n");
			return 1;
		}

		dev->driver = dr;

		return dr->devinit(dr, dev);
	}

	return 1;
}

/*close pci driver*/
int odp_pci_close_one_driver(struct odp_pci_driver *dr	__odp_unused,
			     struct odp_pci_device *dev __odp_unused)
{
	ODP_ERR("Hotplug support isn't enabled\n");
	return -1;
}

/*init pci information*/
int odp_pci_info_init(void)
{
	TAILQ_INIT(&pci_driver_list);
	TAILQ_INIT(&pci_device_list);

	if (local_config.no_pci)
		return 0;

	if (odp_pci_scan() < 0) {
		ODP_ERR("%s(): Cannot scan PCI bus\n", __func__);
		return -1;
	}

	return 0;
}
