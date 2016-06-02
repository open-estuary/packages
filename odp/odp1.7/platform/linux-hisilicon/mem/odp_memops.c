/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#define _FILE_OFFSET_BITS 64
#include <errno.h>
#include <stdarg.h>
#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <inttypes.h>
#include <string.h>
#include <stdarg.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/queue.h>
#include <sys/file.h>
#include <unistd.h>
#include <limits.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <sys/time.h>

#include <odp/config.h>
#include <odp_memory.h>
#include <odp_mmdistrict.h>
#include <odp_base.h>
#include <odp_syslayout.h>
#include <odp_core.h>
#include <odp_common.h>
#include "odp_private.h"
#include "odp_local_cfg.h"
#include "odp_filesystem.h"
#include "odp_hugepage.h"
#include "odp_debug_internal.h"

static uint64_t baseaddr_offset;

#define RANDOMIZE_VA_SPACE_FILE "/proc/sys/kernel/randomize_va_space"

void odp_free(void *addr)
{
	if (addr == NULL)
		return;

	free(addr);
}

void *odp_zmalloc(const char *type, size_t size, unsigned align)
{
	(void)align;
	(void)type;

	void *p = malloc(size);

	if (!p)
		return NULL;

	memset(p, 0, size);

	return p;
}

int odp_mem_lock_page(const void *virt)
{
	unsigned long virtual = (unsigned long)virt;
	int page_size = getpagesize();
	unsigned long aligned = (virtual & ~(page_size - 1));

	return mlock((void *)aligned, page_size);
}

phys_addr_t odp_mem_virt2phy(const void *virtaddr)
{
	int fd;
	uint64_t page, physaddr;
	unsigned long virt_pfn;
	int page_size;
	off_t offset;

	page_size = getpagesize();

	fd = open("/proc/self/pagemap", O_RDONLY);
	if (fd < 0) {
		ODP_ERR("cannot open /proc/self/pagemap: %s\n",
			strerror(errno));
		return ODP_BAD_PHYS_ADDR;
	}

	virt_pfn = (unsigned long)virtaddr / page_size;
	offset = sizeof(uint64_t) * virt_pfn;
	if (lseek(fd, offset, SEEK_SET) == (off_t)-1) {
		ODP_ERR("seek error in /proc/self/pagemap: %s\n",
			strerror(errno));
		close(fd);
		return ODP_BAD_PHYS_ADDR;
	}

	if (read(fd, &page, sizeof(uint64_t)) < 0) {
		ODP_ERR("cannot read /proc/self/pagemap: %s\n",
			strerror(errno));
		close(fd);
		return ODP_BAD_PHYS_ADDR;
	}

	physaddr = ((page & 0x7fffffffffffffULL) * page_size) +
		   ((unsigned long)virtaddr % page_size);

	close(fd);
	return physaddr;
}

static int find_hpt_physaddrs(struct hugepage_file     *hugepg_tbl,
			      struct odp_hugepage_type *hpt)
{
	unsigned i;
	phys_addr_t addr;

	for (i = 0; i < hpt->num_pages[0]; i++) {
		addr = odp_mem_virt2phy(hugepg_tbl[i].orig_va);
		if (addr == ODP_BAD_PHYS_ADDR)
			return -1;

		hugepg_tbl[i].physaddr = addr;
	}

	return 0;
}

static int aslr_enabled(void)
{
	char c;
	int  retval, fd = open(RANDOMIZE_VA_SPACE_FILE, O_RDONLY);

	if (fd < 0)
		return -errno;

	retval = read(fd, &c, 1);
	close(fd);
	if (retval < 0)
		return -errno;

	if (retval == 0)
		return -EIO;

	switch (c) {
	case '0':
		return 0;
	case '1':
		return 1;
	case '2':
		return 2;
	default:
		return -EINVAL;
	}
}

static void *get_virtual_area(size_t *size, size_t hugepage_sz)
{
	void *addr;
	int   fd;
	long  aligned_addr;

	if (local_config.base_virtaddr != 0)
		addr = (void *)(uintptr_t)(local_config.base_virtaddr
					   + baseaddr_offset);
	else
		addr = NULL;

	fd = open("/dev/zero", O_RDONLY);
	if (fd < 0) {
		ODP_ERR("Cannot open /dev/zero\n");
		return NULL;
	}

	do {
		addr = mmap(addr, (*size) + hugepage_sz,
			    PROT_READ, MAP_PRIVATE, fd, 0);
		if (addr == MAP_FAILED)
			*size -= hugepage_sz;
	} while (addr == MAP_FAILED && *size > 0);

	if (addr == MAP_FAILED) {
		close(fd);
		ODP_ERR("Cannot get a virtual area\n");
		return NULL;
	}

	munmap(addr, (*size) + hugepage_sz);
	close(fd);

	aligned_addr  = (long)addr;
	aligned_addr += (hugepage_sz - 1);
	aligned_addr &= (~(hugepage_sz - 1));
	addr = (void *)(aligned_addr);

	baseaddr_offset += *size;

	return addr;
}

static int map_hpt_hugepages(struct hugepage_file *hugepg_tbl,
			     struct odp_hugepage_type *hpt, int orig)
{
	int fd;
	unsigned int i = 0;
	void *virtaddr;
	void *vma_addr = NULL;
	size_t vma_len = 0;

	for (i = 0; i < hpt->num_pages[0]; i++) {
		uint64_t hugepage_sz = hpt->hugepage_sz;

		if (orig) {
			hugepg_tbl[i].file_id = i;
			hugepg_tbl[i].size = hugepage_sz;
			odp_get_hugefile_path(hugepg_tbl[i].filepath,
					      sizeof(hugepg_tbl[i].filepath),
					      hpt->hugedir,
					      hugepg_tbl[i].file_id);
			hugepg_tbl[i].filepath[sizeof(hugepg_tbl[i].filepath) -
					       1] = '\0';
		} else if (sizeof(long) == 4 &&
			   ((hugepage_sz == ODP_PGSIZE_1G) ||
			    (hugepage_sz == ODP_PGSIZE_16G))) {

			ODP_ERR("map_hpt_hugepages\n");
			hugepg_tbl[i].final_va = hugepg_tbl[i].orig_va;
			hugepg_tbl[i].orig_va  = NULL;
			continue;
		} else if (vma_len == 0) {
			unsigned int j;
			unsigned int num_pages;

			for (j = i + 1; j < hpt->num_pages[0]; j++)
				if (hugepg_tbl[j].physaddr !=
				    hugepg_tbl[j - 1].physaddr + hugepage_sz)
					break;

			num_pages = j - i;
			vma_len = num_pages * hugepage_sz;

			vma_addr = get_virtual_area(&vma_len, hpt->hugepage_sz);
			if (!vma_addr)
				vma_len = hugepage_sz;

			HNS_PRINT(
				"vma_addr = 0x%lx, num_pages = %04d, vma_len = %lu(m)\n",
				(unsigned long)vma_addr, num_pages,
				(unsigned long)vma_len / 1024 / 1024);
		}

		fd = open(hugepg_tbl[i].filepath, O_CREAT | O_RDWR, 0755);
		if (fd < 0) {
			ODP_ERR("open failed: %s\n", strerror(errno));
			return -1;
		}

		virtaddr = mmap(vma_addr, hugepage_sz,
				PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
		if (virtaddr == MAP_FAILED) {
			ODP_ERR("mmap failed: %s\n", strerror(errno));
			close(fd);
			return -1;
		}

		if (orig) {
			hugepg_tbl[i].orig_va = virtaddr;
			memset(virtaddr, 0, hugepage_sz);
		} else {
			hugepg_tbl[i].final_va = virtaddr;
		}

		if (flock(fd, LOCK_SH | LOCK_NB) == -1) {
			ODP_ERR("Locking file failed:%s\n", strerror(errno));
			close(fd);
			return -1;
		}

		close(fd);

		if (!vma_addr)
			vma_addr = (char *)virtaddr + hugepage_sz;
		else
			vma_addr = (char *)vma_addr + hugepage_sz;

		vma_len -= hugepage_sz;
	}

	return 0;
}

static int unmap_hpt_hugepages_orig(struct hugepage_file     *hugepg_tbl,
				    struct odp_hugepage_type *hpt)
{
	unsigned int i;

	for (i = 0; i < hpt->num_pages[0]; i++)
		if (hugepg_tbl[i].orig_va) {
			munmap(hugepg_tbl[i].orig_va, hpt->hugepage_sz);
			hugepg_tbl[i].orig_va = NULL;
		}

	return 0;
}

static int find_hpt_numasocket(struct hugepage_file	*hugepg_tbl,
			       struct odp_hugepage_type *hpt)
{
	int socket_id;
	char *end, *nodestr;
	unsigned i, hp_count = 0;
	uint64_t virt_addr;
	char buf[ODP_BUFF_SIZE];
	char hugedir_str[ODP_PATH_MAX];
	FILE *f;

	f = fopen("/proc/self/numa_maps", "r");
	if (!f)
		return 0;

	snprintf(hugedir_str, sizeof(hugedir_str), "%s/%s",
		 hpt->hugedir, HUGEFILE_PREFIX_DEFAULT);

	while (fgets(buf, sizeof(buf), f)) {
		if (!strstr(buf, " huge ") && !strstr(buf, hugedir_str))
			continue;

		virt_addr = strtoull(buf, &end, 16);
		if ((virt_addr == 0) || (end == buf)) {
			ODP_ERR("error in numa_maps parsing\n");
			goto error;
		}

		nodestr = strstr(buf, " N");
		if (!nodestr) {
			ODP_ERR("error in numa_maps parsing\n");
			goto error;
		}

		nodestr += 2;
		end = strstr(nodestr, "=");
		if (!end) {
			ODP_ERR("error in numa_maps parsing\n");
			goto error;
		}

		end[0] = '\0';
		end = NULL;

		socket_id = strtoul(nodestr, &end, 0);
		if ((nodestr[0] == '\0') || (end == NULL) || (*end != '\0')) {
			ODP_ERR("error in numa_maps parsing\n");
			goto error;
		}

		for (i = 0; i < hpt->num_pages[0]; i++) {
			void *va = (void *)(unsigned long)virt_addr;

			if (hugepg_tbl[i].orig_va == va) {
				hugepg_tbl[i].socket_id = socket_id;
				hp_count++;
			}
		}
	}

	if (hp_count < hpt->num_pages[0])
		goto error;

	fclose(f);
	return 0;

error:
	fclose(f);
	return -1;
}

static int sort_hpt_by_physaddr(struct hugepage_file	 *hugepg_tbl,
				struct odp_hugepage_type *hpt)
{
	unsigned i, j;
	int compare_idx;
	uint64_t compare_addr;
	struct hugepage_file tmp;

	for (i = 0; i < hpt->num_pages[0]; i++) {
		compare_addr = 0;
		compare_idx  = -1;

		for (j = i; j < hpt->num_pages[0]; j++)
			if ((compare_addr == 0) ||
			    (hugepg_tbl[j].physaddr < compare_addr)) {
				compare_addr = hugepg_tbl[j].physaddr;
				compare_idx  = j;
			}

		if (compare_idx == -1) {
			ODP_ERR("error in physaddr sorting\n");
			return -1;
		}

		memcpy(&tmp, &hugepg_tbl[compare_idx],
		       sizeof(struct hugepage_file));
		memcpy(&hugepg_tbl[compare_idx],
		       &hugepg_tbl[i], sizeof(struct hugepage_file));
		memcpy(&hugepg_tbl[i], &tmp, sizeof(struct hugepage_file));
	}

	return 0;
}

static void *create_shared_memory(const char *filename, const size_t mem_size)
{
	void *retval;
	int   fd = open(filename, O_CREAT | O_RDWR, 0666);

	if (fd < 0)
		return NULL;

	if (ftruncate(fd, mem_size) < 0) {
		close(fd);
		return NULL;
	}

	retval = mmap(NULL, mem_size,
		      PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
	close(fd);
	return retval;
}

static int copy_hugepages_to_shared_mem(struct hugepage_file	   *dst,
					int			    dest_size,
					const struct hugepage_file *src,
					int			    src_size)
{
	int src_pos, dst_pos = 0;

	for (src_pos = 0; src_pos < src_size; src_pos++)
		if (src[src_pos].final_va) {
			if (dst_pos == dest_size)
				return -1;

			memcpy(&dst[dst_pos],
			       &src[src_pos], sizeof(struct hugepage_file));
			dst_pos++;
		}

	return 0;
}

static int unmap_unneeded_hugepages(struct hugepage_file     *hugepg_tbl,
				    struct odp_hugepage_type *hpt,
				    unsigned		      num_hp_info)
{
	unsigned socket, size;
	int page, nrpages = 0;

	for (size = 0; size < num_hp_info; size++)
		for (socket = 0; socket < ODP_MAX_NUMA_NODES; socket++)
			nrpages +=
				local_config.odp_hugepage_type[size].
				num_pages[socket];

	for (size = 0; size < num_hp_info; size++)
		for (socket = 0; socket < ODP_MAX_NUMA_NODES; socket++) {
			unsigned pages_found = 0;

			for (page = 0; page < nrpages; page++) {
				struct hugepage_file *hp = &hugepg_tbl[page];

				if ((hp->size == hpt[size].hugepage_sz) &&
				    (hp->socket_id == (int)socket)) {

					if (pages_found ==
					    hpt[size].num_pages[socket]) {
						uint64_t unmap_len;

						unmap_len = hp->size;
						munmap(hp->final_va,
						       (size_t)unmap_len);

						hp->final_va = NULL;
					} else {
						pages_found++;
					}
				}
			}
		}

	return 0;
}

static inline uint64_t get_socket_mem_size(int socket)
{
	uint64_t size = 0;
	unsigned i;

	for (i = 0; i < local_config.num_hugepage_types; i++) {
		struct odp_hugepage_type *hpt =
			&local_config.odp_hugepage_type[i];

		if (hpt->hugedir)
			size += hpt->hugepage_sz * hpt->num_pages[socket];
	}

	return size;
}

static int calc_num_pages_per_socket(uint64_t		      *memory,
				     struct odp_hugepage_type *hp_info,
				     struct odp_hugepage_type *hp_used,
				     unsigned		       num_hp_info)
{
	unsigned socket, j, i = 0;
	unsigned requested, available;
	int total_num_pages = 0;
	uint64_t remaining_mem, cur_mem;
	uint64_t total_mem = local_config.memory;

	if (num_hp_info == 0)
		return -1;

	if (local_config.force_sockets == 0) {
		int cpu_per_socket[ODP_MAX_NUMA_NODES];
		size_t default_size, total_size;
		unsigned core_id;

		memset(cpu_per_socket, 0, sizeof(cpu_per_socket));
		ODP_LCORE_FOREACH(core_id)
		{
			cpu_per_socket[odp_core_to_socket_id(core_id)]++;
		}

		total_size = local_config.memory;
		for (socket = 0; socket < ODP_MAX_NUMA_NODES &&
		     total_size != 0; socket++) {
			default_size =
				(local_config.memory * cpu_per_socket[socket])
				/ odp_core_num();

			default_size = ODP_MIN(default_size,
					       get_socket_mem_size(socket));

			memory[socket] = default_size;
			total_size -= default_size;
		}

		for (socket = 0; socket < ODP_MAX_NUMA_NODES &&
		     total_size != 0; socket++) {
			default_size =
				ODP_MIN(get_socket_mem_size(socket)
					- memory[socket], total_size);

			memory[socket] += default_size;
			total_size -= default_size;
		}
	}

	for (socket = 0; socket < ODP_MAX_NUMA_NODES && total_mem != 0;
	     socket++) {
		for (i = 0; i < num_hp_info && memory[socket] != 0; i++) {
			hp_used[i].hugedir = hp_info[i].hugedir;
			hp_used[i].num_pages[socket] = ODP_MIN(
				memory[socket] / hp_info[i].hugepage_sz,
				hp_info[i].num_pages[socket]);

			cur_mem = hp_used[i].num_pages[socket] *
				  hp_used[i].hugepage_sz;

			memory[socket] -= cur_mem;
			total_mem -= cur_mem;

			total_num_pages += hp_used[i].num_pages[socket];

			if (memory[socket] == 0)
				break;

			if (hp_used[i].num_pages[socket] ==
			    hp_info[i].num_pages[socket])
				continue;

			remaining_mem = 0;
			for (j = i + 1; j < num_hp_info; j++)
				remaining_mem += hp_info[j].hugepage_sz *
						 hp_info[j].num_pages[socket];

			if (remaining_mem < memory[socket]) {
				cur_mem = ODP_MIN(memory[socket],
						  hp_info[i].hugepage_sz);
				memory[socket] -= cur_mem;
				total_mem -= cur_mem;
				hp_used[i].num_pages[socket]++;
				total_num_pages++;
				break;
			}
		}

		if (memory[socket] > 0) {
			requested = (unsigned)(local_config.socket_mem[socket] /
					       0x100000);
			available = requested -
				    ((unsigned)(memory[socket] / 0x100000));
			ODP_ERR("Not enough memory available on socket %u! "
				"Requested: %uMB, available: %uMB\n", socket,
				requested, available);
			return -1;
		}
	}

	if (total_mem > 0) {
		requested = (unsigned)(local_config.memory / 0x100000);
		available = requested - (unsigned)(total_mem / 0x100000);
		ODP_ERR("Not enough memory available! Requested: %uMB, "
			"available: %uMB\n", requested, available);
		return -1;
	}

	return total_num_pages;
}

static inline int odp_no_hugetlbfs(void)
{
	void *addr;
	struct odp_sys_layout *mlayout;

	mlayout = odp_get_configuration()->sys_layout;
	addr = mmap(NULL, local_config.memory,
		    PROT_READ | PROT_WRITE, MAP_PRIVATE | MAP_ANONYMOUS, 0, 0);
	if (addr == MAP_FAILED) {
		ODP_ERR("mmap() failed: %s\n", strerror(errno));
		return -1;
	}

	mlayout->mmfrag[0].phys_addr = (phys_addr_t)(uintptr_t)addr;
	mlayout->mmfrag[0].addr = addr;
	mlayout->mmfrag[0].len	= local_config.memory;
	mlayout->mmfrag[0].socket_id = SOCKET_ID_ANY;
	return 0;
}

static int odp_hugepage_init(void)
{
	struct odp_sys_layout *mcfg;
	struct hugepage_file  *hugepage, *tmp_hp = NULL;
	struct odp_hugepage_type used_hp[MAX_HUGEPAGE_SIZES];

	uint64_t memory[ODP_MAX_NUMA_NODES];

	unsigned int hp_offset;
	int i, j, new_mmfrag;
	int nr_hugefiles = 0;
	int nr_hugepages = 0;

	memset(used_hp, 0, sizeof(used_hp));

	mcfg = odp_get_configuration()->sys_layout;

	if (local_config.no_hugetlbfs)
		if (odp_no_hugetlbfs())
			goto fail;

	for (i = 0; i < (int)local_config.num_hugepage_types; i++) {
		used_hp[i].hugepage_sz =
			local_config.odp_hugepage_type[i].hugepage_sz;
		nr_hugepages += local_config.odp_hugepage_type[i].num_pages[0];
	}

	tmp_hp = malloc(nr_hugepages * sizeof(struct hugepage_file));
	if (!tmp_hp)
		goto fail;

	memset(tmp_hp, 0, nr_hugepages * sizeof(struct hugepage_file));

	hp_offset = 0;

	for (i = 0; i < (int)local_config.num_hugepage_types; i++) {
		struct odp_hugepage_type *hpt;

		hpt = &local_config.odp_hugepage_type[i];

		if (hpt->num_pages[0] == 0)
			continue;

		if (map_hpt_hugepages(&tmp_hp[hp_offset], hpt, 1) < 0) {
			ODP_ERR("Failed to mmap %u MB hugepages\n",
				(unsigned)(hpt->hugepage_sz / 0x100000));
			goto fail;
		}

		if (find_hpt_physaddrs(&tmp_hp[hp_offset], hpt) < 0) {
			ODP_ERR("Failed to find phys addr for %u MB pages\n",
				(unsigned)(hpt->hugepage_sz / 0x100000));
			goto fail;
		}

		if (find_hpt_numasocket(&tmp_hp[hp_offset], hpt) < 0) {
			ODP_ERR("Failed to find NUMA socket for "
				"%u MB pages\n",
				(unsigned)(hpt->hugepage_sz / 0x100000));
			goto fail;
		}

		if (sort_hpt_by_physaddr(&tmp_hp[hp_offset], hpt) < 0)
			goto fail;

		if (map_hpt_hugepages(&tmp_hp[hp_offset], hpt, 0) < 0) {
			ODP_ERR("Failed to remap %u MB pages\n",
				(unsigned)(hpt->hugepage_sz / 0x100000));
			goto fail;
		}

		if (unmap_hpt_hugepages_orig(&tmp_hp[hp_offset], hpt) < 0)
			goto fail;

		hp_offset += hpt->num_pages[0];
	}

	nr_hugefiles = nr_hugepages;

	for (i = 0; i < (int)local_config.num_hugepage_types; i++)
		for (j = 0; j < ODP_MAX_NUMA_NODES; j++)
			local_config.odp_hugepage_type[i].num_pages[j] = 0;

	for (i = 0; i < nr_hugefiles; i++) {
		int socket = tmp_hp[i].socket_id;

		for (j = 0; j < (int)local_config.num_hugepage_types; j++)
			if (tmp_hp[i].size ==
			    local_config.odp_hugepage_type[j].hugepage_sz)
				local_config.odp_hugepage_type[j].
				num_pages[socket]++;
	}

	for (i = 0; i < ODP_MAX_NUMA_NODES; i++)
		memory[i] = local_config.socket_mem[i];

	nr_hugepages =
		calc_num_pages_per_socket(memory,
					  local_config.odp_hugepage_type,
					  used_hp,
					  local_config.num_hugepage_types);
	if (nr_hugepages < 0)
		goto fail;

	hugepage = create_shared_memory(odp_hugepage_info_path(),
					nr_hugefiles *
					sizeof(struct hugepage_file));

	if (!hugepage) {
		ODP_ERR("Failed to create shared memory!\n");
		goto fail;
	}

	memset(hugepage, 0, nr_hugefiles * sizeof(struct hugepage_file));

	if (unmap_unneeded_hugepages(tmp_hp, used_hp,
				     local_config.num_hugepage_types) < 0) {
		ODP_ERR("Unmapping and locking hugepages failed!\n");
		goto fail;
	}

	if (copy_hugepages_to_shared_mem(hugepage, nr_hugefiles,
					 tmp_hp, nr_hugefiles) < 0) {
		ODP_ERR("Copying tables to shared memory failed!\n");
		goto fail;
	}

	free(tmp_hp);
	tmp_hp = NULL;

	for (j = 0; j < ODP_MAX_MMFRAG; j++)
		if (!mcfg->mmfrag[j].addr) {
			j--;
			break;
		}

	for (i = 0; i < nr_hugefiles; i++) {
		new_mmfrag = 0;
		if (i == 0)
			new_mmfrag = 1;
		else if (hugepage[i].socket_id != hugepage[i - 1].socket_id)
			new_mmfrag = 1;
		else if (hugepage[i].size != hugepage[i - 1].size)
			new_mmfrag = 1;
		else if ((hugepage[i].physaddr - hugepage[i - 1].physaddr) !=
			 hugepage[i].size)
			new_mmfrag = 1;
		else if (((unsigned long)hugepage[i].final_va -
			  (unsigned long)hugepage[i - 1].final_va) !=
			 hugepage[i].size)
			new_mmfrag = 1;

		if (new_mmfrag) {
			j += 1;
			if (j == ODP_MAX_MMFRAG)
				break;

			mcfg->mmfrag[j].phys_addr = hugepage[i].physaddr;
			mcfg->mmfrag[j].addr = hugepage[i].final_va;
			mcfg->mmfrag[j].len  = hugepage[i].size;
			mcfg->mmfrag[j].socket_id = hugepage[i].socket_id;
			mcfg->mmfrag[j].hugepage_sz = hugepage[i].size;
		} else {
			mcfg->mmfrag[j].len += mcfg->mmfrag[j].hugepage_sz;
		}

		hugepage[i].mmfrag_id = j;
	}

	mcfg->frag_idx = j;

	if (i < nr_hugefiles) {
		ODP_ERR("Can only reserve %d pages from "
			"%d requested\n Current %s=%d is not enough\n "
			"Please either increase it or request "
			"less amount of memory.\n",
			i, nr_hugefiles, ODP_STR(
				CONFIG_ODP_MAX_MMFRAG),
			ODP_MAX_MMFRAG);
		return -ENOMEM;
	}

	return 0;

fail:
	if (tmp_hp)
		free(tmp_hp);

	return -1;
}

static off_t getfilesize(int fd)
{
	struct stat st;

	if (fstat(fd, &st) < 0)
		return 0;

	return st.st_size;
}

static int odp_hugepage_attach(void)
{
	const struct odp_sys_layout *mcfg = odp_get_configuration()->sys_layout;
	const struct hugepage_file  *hp = NULL;
	unsigned num_hp = 0;
	unsigned i, s = 0;
	off_t size;
	int   fd, fd_zero = -1, fd_hugepage = -1;

	if (aslr_enabled() > 0) {
		ODP_PRINT("WARNING: Address Space Layout Randomization "
			  "(ASLR) is enabled in the kernel.\n");
		ODP_PRINT("   This may cause issues with mapping memory "
			  "into secondary processes\n");
	}

	fd_zero = open("/dev/zero", O_RDONLY);
	if (fd_zero < 0) {
		ODP_ERR("Could not open /dev/zero\n");
		goto error;
	}

	fd_hugepage = open(odp_hugepage_info_path(), O_RDONLY);
	if (fd_hugepage < 0) {
		ODP_ERR("Could not open %s\n", odp_hugepage_info_path());
		goto error;
	}

	for (s = 0; s < ODP_MAX_MMFRAG; ++s) {
		void *base_addr;

		if (mcfg->mmfrag[s].len == 0)
			break;

		base_addr = mmap(mcfg->mmfrag[s].addr, mcfg->mmfrag[s].len,
				 PROT_READ, MAP_PRIVATE, fd_zero, 0);
		if ((base_addr == MAP_FAILED) ||
		    (base_addr != mcfg->mmfrag[s].addr)) {
			ODP_ERR("Could not mmap %llu bytes "
				"in /dev/zero to requested address [%p]: '%s'\n",
				(unsigned long long)mcfg->mmfrag[s].len,
				mcfg->mmfrag[s].addr, strerror(errno));
			if (aslr_enabled() > 0)
				ODP_PRINT("It is recommended to "
					  "disable ASLR in the kernel "
					  "and retry running both primary "
					  "and secondary processes\n");

			goto error;
		}
	}

	size = getfilesize(fd_hugepage);
	hp = mmap(NULL, size, PROT_READ, MAP_PRIVATE, fd_hugepage, 0);
	if (!hp) {
		ODP_ERR("Could not mmap %s\n", odp_hugepage_info_path());
		goto error;
	}

	num_hp = size / sizeof(struct hugepage_file);
	ODP_PRINT("Analysing %u files, size = 0x%lx\n", num_hp,
		  (unsigned long)size);

	s = 0;
	while (s < ODP_MAX_MMFRAG && mcfg->mmfrag[s].len > 0) {
		void *addr, *base_addr;
		uintptr_t offset = 0;
		size_t mapping_size;

		base_addr = mcfg->mmfrag[s].addr;
		munmap(base_addr, mcfg->mmfrag[s].len);

		for (i = 0; i < num_hp && offset < mcfg->mmfrag[s].len; i++)
			if (hp[i].mmfrag_id == (int)s) {
				fd = open(hp[i].filepath, O_RDWR);
				if (fd < 0) {
					ODP_ERR("Could not open %s\n",
						hp[i].filepath);
					goto error;
				}

				mapping_size = hp[i].size;
				addr = mmap(ODP_PTR_ADD(base_addr,
							offset),
					    mapping_size,
					    PROT_READ | PROT_WRITE,
					    MAP_SHARED, fd, 0);

				close(fd);
				if ((addr == MAP_FAILED) ||
				    (addr !=
				     ODP_PTR_ADD(base_addr, offset))) {
					ODP_ERR("Could not mmap %s\n",
						hp[i].filepath);
					goto error;
				}

				offset += mapping_size;
			}

		ODP_PRINT("Mapped segment %u of size 0x%lx\n", s,
			  mcfg->mmfrag[s].len);
		s++;
	}

	munmap((void *)(uintptr_t)hp, size);
	close(fd_zero);
	close(fd_hugepage);
	return 0;

error:
	if (fd_zero >= 0)
		close(fd_zero);

	if (fd_hugepage >= 0)
		close(fd_hugepage);

	return -1;
}

static int odp_memdevice_init(void)
{
	struct odp_config *config;

	if (odp_process_type() == ODP_PROC_SECONDARY)
		return 0;

	config = odp_get_configuration();
	config->sys_layout->channel_num = local_config.force_channel_num;
	config->sys_layout->rank_num = local_config.force_rank_num;

	return 0;
}

int odp_memory_init(void)
{
	const int retval = odp_process_type();

	if (retval == ODP_PROC_PRIMARY)
		odp_hugepage_init();
	else
		odp_hugepage_attach();

	if (retval < 0)
		return -1;

	if ((local_config.no_shconf == 0) && (odp_memdevice_init() < 0))
		return -1;

	return 0;
}
