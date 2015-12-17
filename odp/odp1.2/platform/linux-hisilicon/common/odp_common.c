/*-
 *   BSD LICENSE
 *
 *   Copyright(c) 2010-2014 Huawei Corporation. All rights reserved.
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions
 *   are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *	 notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *	 notice, this list of conditions and the following disclaimer in
 *	 the documentation and/or other materials provided with the
 *	 distribution.
 *     * Neither the name of Huawei Corporation nor the names of its
 *	 contributors may be used to endorse or promote products derived
 *	 from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *   A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *   OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *   SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *   LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *   DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *   THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *   OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <stdarg.h>
#include <unistd.h>
#include <pthread.h>
#include <syslog.h>
#include <getopt.h>
#include <sys/file.h>
#include <fcntl.h>
#include <dlfcn.h>
#include <stddef.h>
#include <errno.h>
#include <limits.h>
#include <errno.h>
#include <sys/mman.h>
#include <sys/queue.h>

#include <odp/cpu.h>
#if defined(ODP_ARCH_X86_64) || defined(ODP_ARCH_I686)
#include <sys/io.h>
#endif


#include <odp_common.h>
#include <odp/config.h>
#include <odp_memory.h>
#include <odp_memzone.h>
#include <odp_hugepage.h>
#include <odp_base.h>
#include <odp_memconfig.h>
#include <odp_lcore.h>
#include <odp_cycles.h>
#include <odp_pci.h>
#include <odp_devargs.h>
#include <odp/atomic.h>
#include "odp_private.h"
#include "odp_internal_cfg.h"
#include "odp_filesystem.h"
#include "odp_options.h"

#define MEMSIZE_IF_NO_HUGE_PAGE (64ULL * 1024ULL * 1024ULL)

#define SOCKET_MEM_STRLEN (ODP_MAX_NUMA_NODES * 10)

/* Allow the application to print its usage message too if set */
static odp_usage_hook_t odp_application_usage_hook;

TAILQ_HEAD(shared_driver_list, shared_driver);

/* Definition for shared object drivers. */
struct shared_driver {
	TAILQ_ENTRY(shared_driver) next;

	char  name[ODP_PATH_MAX];
	void *lib_handle;
};

/* List of external loadable drivers */
static struct shared_driver_list solib_list =
	TAILQ_HEAD_INITIALIZER(solib_list);

/* early configuration structure, when memory config is not mmapped */
static struct odp_mem_config early_mem_config;

/* define fd variable here, because file needs to be kept open for the
 * duration of the program, as we hold a write lock on it in the primary proc */
static int mem_cfg_fd = -1;

static struct flock wr_lock = {
	.l_type	  = F_WRLCK,
	.l_whence = SEEK_SET,
	.l_start  = offsetof(struct odp_mem_config, memseg),
	.l_len	  = sizeof(early_mem_config.memseg),
};

/* Address of global and public configuration */
static struct odp_config godp_config = {
	.mem_config = &early_mem_config,
};

/* internal configuration (per-core) */
struct lcore_config glcore_config[ODP_MAX_LCORE];

/* internal configuration */
struct odp_internal_config internal_config;

/* used by odp_rdtsc() */
int odp_cycles_vmware_tsc_map;

ODP_DEFINE_PER_LCORE(int, _odp_errno); /**< Per core error number. */

/* Return a pointer to the configuration structure */
struct odp_config *odp_get_configuration(void)
{
	return &godp_config;
}

int get_mplock_value_debug(void)
{
	return godp_config.mem_config->mplock.cnt.v;
}

/* parse a sysfs (or other) file containing one integer value */
int odp_parse_sysfs_value(const char *filename, unsigned long *val)
{
	FILE *f;
	char  buf[ODP_BUFF_SIZE];
	char *end = NULL;

	f = fopen(filename, "r");
	if (!f) {
		PRINT("cannot open sysfs value %s\n", filename);
		return -1;
	}

	if (!fgets(buf, sizeof(buf), f)) {
		PRINT("cannot read sysfs value %s\n", filename);
		fclose(f);
		return -1;
	}

	*val = strtoul(buf, &end, 0);
	if ((buf[0] == '\0') || (!end) || (*end != '\n')) {
		PRINT("cannot parse sysfs value %s\n", filename);
		fclose(f);
		return -1;
	}

	fclose(f);
	return 0;
}

/* create memory configuration in shared/mmap memory. Take out
 * a write lock on the memsegs, so we can auto-detect primary/secondary.
 * This means we never close the file while running (auto-close on exit).
 * We also don't lock the whole file, so that in future we can use read-locks
 * on other parts, e.g. memzones, to detect if there are running secondary
 * processes. */
static void odp_config_create(void)
{
	void *odp_mem_cfg_addr;
	int   retval;

	const char *pathname = odp_runtime_config_path();

	if (internal_config.no_shconf)
		return;
	
	/* map the config before hugepage address so that we don't waste a page */
	if (internal_config.base_virtaddr != 0)
		odp_mem_cfg_addr = (void *)ODP_ALIGN_FLOOR(internal_config.base_virtaddr -
							     sizeof(struct odp_mem_config), sysconf(_SC_PAGE_SIZE));
	else
		odp_mem_cfg_addr = NULL;

	if (mem_cfg_fd < 0) {
		mem_cfg_fd = open(pathname, O_RDWR | O_CREAT, 0660);
		if (mem_cfg_fd < 0)
			PRINT("Cannot open '%s' for odp_mem_config\n", pathname);
	}

	retval = ftruncate(mem_cfg_fd, sizeof(*godp_config.mem_config));
	if (retval < 0) {
		close(mem_cfg_fd);
		PRINT("Cannot resize '%s' for odp_mem_config\n", pathname);
	}

	retval = fcntl(mem_cfg_fd, F_SETLK, &wr_lock);
	if (retval < 0) {
		close(mem_cfg_fd);
		PRINT("Cannot create lock on '%s'. Is another primary process running?\n", pathname);
	}

	odp_mem_cfg_addr = mmap(odp_mem_cfg_addr, sizeof(*godp_config.mem_config),
				 PROT_READ | PROT_WRITE, MAP_SHARED, mem_cfg_fd, 0);

	if (odp_mem_cfg_addr == MAP_FAILED)
		PRINT("Cannot mmap memory for godp_config\n");

	memcpy(odp_mem_cfg_addr, &early_mem_config, sizeof(early_mem_config));
	godp_config.mem_config = (struct odp_mem_config *)odp_mem_cfg_addr;

	/* store address of the config in the config itself so that secondary
	 * processes could later map the config into this exact location */
	godp_config.mem_config->mem_cfg_addr = (uintptr_t)odp_mem_cfg_addr;
}

/* attach to an existing shared memory config */
static void odp_config_attach(void)
{
	struct odp_mem_config *mem_config;

	const char *pathname = odp_runtime_config_path();

	if (internal_config.no_shconf)
		return;

	if (mem_cfg_fd < 0) {
		mem_cfg_fd = open(pathname, O_RDWR);
		if (mem_cfg_fd < 0)
			PRINT("Cannot open '%s' for odp_mem_config\n", pathname);
	}

	/* map it as read-only first */
	mem_config = (struct odp_mem_config *)mmap(NULL, sizeof(*mem_config),
						    PROT_READ, MAP_SHARED, mem_cfg_fd, 0);
	if (mem_config == MAP_FAILED)
		PRINT("Cannot mmap memory for godp_config\n");

	godp_config.mem_config = mem_config;
}

/* reattach the shared config at exact memory location primary process has it */
static void odp_config_reattach(void)
{
	struct odp_mem_config *mem_config;
	void *odp_mem_cfg_addr;

	if (internal_config.no_shconf)
		return;

	/* save the address primary process has mapped shared config to */
	odp_mem_cfg_addr = (void *)(uintptr_t)godp_config.mem_config->mem_cfg_addr;

	/* unmap original config */
	munmap(godp_config.mem_config, sizeof(struct odp_mem_config));

	/* remap the config at proper address */
	mem_config = (struct odp_mem_config *)mmap(odp_mem_cfg_addr,
						    sizeof(*mem_config), PROT_READ | PROT_WRITE, MAP_SHARED,
						    mem_cfg_fd, 0);
	close(mem_cfg_fd);
	if ((mem_config == MAP_FAILED) || (mem_config != odp_mem_cfg_addr))
		PRINT("Cannot mmap memory for godp_config\n");

	godp_config.mem_config = mem_config;
}

/* Detect if we are a primary or a secondary process */
enum odp_proc_type_t odp_proc_type_detect(void)
{
	enum odp_proc_type_t ptype = ODP_PROC_PRIMARY;
	const char *pathname = odp_runtime_config_path();

	/* if we can open the file but not get a write-lock we are a secondary
	 * process. NOTE: if we get a file handle back, we keep that open
	 * and don't close it to prevent a race condition between multiple opens */
	mem_cfg_fd = open(pathname, O_RDWR);
	if ((mem_cfg_fd >= 0) &&
	    (fcntl(mem_cfg_fd, F_SETLK, &wr_lock) < 0))
		ptype = ODP_PROC_SECONDARY;

	PRINT("Auto-detected process type: %s\n",
	      ptype == ODP_PROC_PRIMARY ? "PRIMARY" : "SECONDARY");

	return ptype;
}

/* Sets up godp_config structure with the pointer to shared memory config.*/
static void odp_config_init(void)
{
	godp_config.process_type = internal_config.process_type;
        
	switch (godp_config.process_type) {
	case ODP_PROC_PRIMARY:
		odp_config_create();
		break;
	case ODP_PROC_SECONDARY:
		odp_config_attach();
		odp_mcfg_wait_complete(godp_config.mem_config);
		odp_config_reattach();
		break;
	case ODP_PROC_AUTO:
	case ODP_PROC_INVALID:
		PRINT("Invalid process type\n");
	}
}

/* Unlocks hugepage directories that were locked by odp_hugepage_info_init */
static void odp_hugedirs_unlock(void)
{
	int i;

	for (i = 0; i < MAX_HUGEPAGE_SIZES; i++) {
		/* skip uninitialized */
		if (internal_config.hugepage_info[i].lock_descriptor < 0)
			continue;

		/* unlock hugepage file */

		flock(internal_config.hugepage_info[i].lock_descriptor, LOCK_UN);
		close(internal_config.hugepage_info[i].lock_descriptor);

		/* reset the field */
		internal_config.hugepage_info[i].lock_descriptor = -1;
	}
}

/* display usage */
static void odp_usage(const char *prgname)
{
	PRINT("Usage: %s ", prgname);
	odp_common_usage();
	PRINT("HODP Linux options:\n"
	      " -d LIB.so Add driver (can be used multiple times)\n"
	      " --" OPT_SOCKET_MEM " Memory to allocate on sockets (comma separated values)\n"
	      " --" OPT_HUGE_DIR " Directory where hugetlbfs is mounted\n"
	      " --" OPT_FILE_PREFIX " Prefix for hugepage filenames\n"
	      " --" OPT_BASE_VIRTADDR " Base virtual address\n"
	      "  --" OPT_CREATE_UIO_DEV " Create /dev/uioX (usually done by hotplug)\n"
	      "  --" OPT_VFIO_INTR "         Interrupt mode for VFIO (legacy|msi|msix)\n"
	      "  --" OPT_XEN_DOM "            Support running on Xen dom0 without hugetlbfs\n"
	      "\n");

	/* Allow the application to print its usage message too if hook is set */
	if (odp_application_usage_hook) {
		PRINT("===== Application Usage =====\n\n");
		odp_application_usage_hook(prgname);
	}
}

/* Set a per-application usage message */
odp_usage_hook_t odp_set_application_usage_hook(odp_usage_hook_t usage_func)
{
	odp_usage_hook_t old_func;

	/* Will be NULL on the first call to denote the last usage routine. */
	old_func = odp_application_usage_hook;
	odp_application_usage_hook = usage_func;

	return old_func;
}

static int odp_parse_socket_mem(char *socket_mem)
{
	char *arg[ODP_MAX_NUMA_NODES];
	char *end;
	int   arg_num, i, len;
	uint64_t total_mem = 0;

	len = strnlen(socket_mem, SOCKET_MEM_STRLEN);
	if (len == SOCKET_MEM_STRLEN) {
		PRINT("--socket-mem is too long\n");
		return -1;
	}

	/* all other error cases will be caught later */
	if (!isdigit(socket_mem[len - 1]))
		return -1;

	/* split the optarg into separate socket values */
	arg_num = odp_strsplit(socket_mem, len,
				arg, ODP_MAX_NUMA_NODES, ',');

	/* if split failed, or 0 arguments */
	if (arg_num <= 0)
		return -1;

	internal_config.force_sockets = 1;

	/* parse each defined socket option */
	errno = 0;
	for (i = 0; i < arg_num; i++) {
		end = NULL;
		internal_config.socket_mem[i] = strtoull(arg[i], &end, 10);

		/* check for invalid input */
		if ((errno != 0) || (arg[i][0] == '\0') ||
		    (!end) || (*end != '\0'))
			return -1;

		internal_config.socket_mem[i] *= 1024ULL;
		internal_config.socket_mem[i] *= 1024ULL;
		total_mem += internal_config.socket_mem[i];
	}

	/* check if we have a positive amount of total memory */
	if (total_mem == 0)
		return -1;

	return 0;
}

static int odp_parse_base_virtaddr(const char *arg)
{
	char *end;
	uint64_t addr;

	errno = 0;
	addr  = strtoull(arg, &end, 16);

	/* check for errors */
	if ((errno != 0) || (arg[0] == '\0') || (!end) || (*end != '\0'))
		return -1;

	/* make sure we don't exceed 32-bit boundary on 32-bit target */
#ifndef ODP_ARCH_64
	if (addr >= UINTPTR_MAX)
		return -1;
#endif

	/* align the addr on 16M boundary, 16MB is the minimum huge page
	 * size on IBM Power architecture. If the addr is aligned to 16MB,
	 * it can align to 2MB for x86. So this alignment can also be used
	 * on x86 */
	internal_config.base_virtaddr =
		ODP_PTR_ALIGN_CEIL((uintptr_t)addr, (size_t)ODP_PGSIZE_16M);

	/* PRINT("addr = 0x%lx, base_virtaddr = 0x%lx\n", addr, internal_config.base_virtaddr); */

	return 0;
}

static int odp_parse_vfio_intr(const char *mode)
{
	unsigned i;

	static struct {
		const char	  *name;
		enum odp_intr_mode value;
	} map[] = {
		{ "legacy", ODP_INTR_MODE_LEGACY },
		{ "msi",    ODP_INTR_MODE_MSI	 },
		{ "msix",   ODP_INTR_MODE_MSIX	 },
	};

	for (i = 0; i < ODP_DIM(map); i++)
		if (!strcmp(mode, map[i].name)) {
			internal_config.vfio_intr_mode = map[i].value;
			return 0;
		}

	return -1;
}

static inline size_t odp_get_hugepage_mem_size(void)
{
	uint64_t size = 0;
	unsigned i, j;

	for (i = 0; i < internal_config.num_hugepage_sizes; i++) {
		struct hugepage_info *hpi = &internal_config.hugepage_info[i];

		if (hpi->hugedir)
			for (j = 0; j < ODP_MAX_NUMA_NODES; j++)
				size += hpi->hugepage_sz * hpi->num_pages[j];
	}

	return (size < MEM_SIZE_MAX) ? (size_t)(size) : MEM_SIZE_MAX;
}

/* Parse the argument given in the command line of the application */
static int odp_parse_args(int argc, char **argv)
{
	int opt, ret;
	char **argvopt;
	int option_index;
	char *prgname = argv[0];
	struct shared_driver *solib;

	argvopt = argv;

	odp_reset_internal_config(&internal_config);

	while ((opt = getopt_long(argc, argvopt, odp_short_options,
				  odp_long_options, &option_index)) != EOF) {
		int ret;

		/* getopt is not happy, stop right now */
		if (opt == '?') {
			odp_usage(prgname);
			return -1;
		}

		ret = odp_parse_common_option(opt, optarg, &internal_config);

		/* common parser is not happy */
		if (ret < 0) {
			odp_usage(prgname);
			return -1;
		}

		/* common parser handled this option */
		if (ret == 0)
			continue;

		switch (opt) {
		case 'h':
			odp_usage(prgname);
			exit(EXIT_SUCCESS);

		/* force loading of external driver */
		case 'd':
			solib = malloc(sizeof(*solib));
			if (!solib) {
				PRINT("malloc(solib) failed\n");
				return -1;
			}

			memset(solib, 0, sizeof(*solib));
			strncpy(solib->name, optarg, ODP_PATH_MAX - 1);
			solib->name[ODP_PATH_MAX - 1] = 0;
			TAILQ_INSERT_TAIL(&solib_list, solib, next);
			break;

		/* long options */
		case OPT_XEN_DOM0_NUM:
#ifdef ODP_LIBODP_XEN_DOM0
			internal_config.xen_dom0_support = 1;
#else
			PRINT("Can't support DPDK app\n"
			      "running on Dom0, please configure\n"
			      "ODP_LIBODP_XEN_DOM0=y\n");
			return -1;
#endif
			break;

		case OPT_HUGE_DIR_NUM:
			internal_config.hugepage_dir = optarg;
			break;

		case OPT_FILE_PREFIX_NUM:
			internal_config.hugefile_prefix = optarg;
			break;

		case OPT_SOCKET_MEM_NUM:
			if (odp_parse_socket_mem(optarg) < 0) {
				PRINT("invalid parameters for --"
				      OPT_SOCKET_MEM "\n");
				odp_usage(prgname);
				return -1;
			}

			break;

		case OPT_BASE_VIRTADDR_NUM:
			if (odp_parse_base_virtaddr(optarg) < 0) {
				PRINT("invalid parameter for --"
				      OPT_BASE_VIRTADDR "\n");
				odp_usage(prgname);
				return -1;
			}

			break;

		case OPT_VFIO_INTR_NUM:
			if (odp_parse_vfio_intr(optarg) < 0) {
				PRINT("invalid parameters for --"
				      OPT_VFIO_INTR "\n");
				odp_usage(prgname);
				return -1;
			}

			break;

		case OPT_CREATE_UIO_DEV_NUM:
			internal_config.create_uio_dev = 1;
			break;
		default:
			if ((opt < OPT_LONG_MIN_NUM) && isprint(opt))
				PRINT("Option %c is not supported on Linux\n", opt);
			else if ((opt >= OPT_LONG_MIN_NUM) &&
				 (opt < OPT_LONG_MAX_NUM))
				PRINT("Option %s is not supported on Linux\n",
				      odp_long_options[option_index].name);
			else
				PRINT("Option %d is not supported on Linux\n", opt);

			odp_usage(prgname);
			return -1;
		}
	}

	if (odp_adjust_config(&internal_config) != 0)
		return -1;

	/* sanity checks */
	if (odp_check_common_options(&internal_config) != 0) {
		odp_usage(prgname);
		return -1;
	}

	/* --xen-dom0 doesn't make sense with --socket-mem */
	if (internal_config.xen_dom0_support && (internal_config.force_sockets == 1)) {
		PRINT("Options --" OPT_SOCKET_MEM " cannot be specified\n"
		      "together with --" OPT_XEN_DOM ".");
		odp_usage(prgname);
		return -1;
	}

	ret = optind - 1;
	optind = 0; /* reset getopt lib */
	return ret;
}

static inline void odp_mcfg_complete(void)
{
	/* ALL shared mem_config related INIT DONE */
	if (godp_config.process_type == ODP_PROC_PRIMARY)
		godp_config.mem_config->magic = ODP_MAGIC;
}

/*
 * Request iopl privilege for all RPL, returns 0 on success
 * iopl() call is mostly for the i386 architecture. For other architectures,
 * return -1 to indicate IO privilege can't be changed in this way.
 */
int odp_iopl_init(void)
{
#if defined(ODP_ARCH_X86_64) || defined(ODP_ARCH_I686)
	if (iopl(3) != 0)
		return -1;

	return 0;
#else
	return -1;
#endif
}

/*****************************************************************************
   Function     : odp_pre_init
   Description  : odp mem and cpu info init
   Input        : in put
   Output       : None
   Return       : 0,succesed;other,failed
   Create By    : x00180405
   Modification :
   1.created: 2015/7/2
   Restriction  :
*****************************************************************************/
int odp_init(int argc, char **argv)
{
	int fctret, ret;
	static int run_once;
	struct shared_driver *solib = NULL;

	if (run_once) {
		PRINT("odp_init has been called!!");
		return -1;
	}

	ret = odp_cpu_init();
	if (ret < 0) {
		PRINT("odp_cpu_init fail ret = %d!!", ret);
		return -1;
	}

	fctret = odp_parse_args(argc, argv);
	if (fctret < 0)
		exit(1);

	/* set log level as early as possible */
	/* odp_set_log_level(internal_config.log_level); */
	if ((internal_config.no_hugetlbfs == 0) &&
	    (internal_config.process_type != ODP_PROC_SECONDARY) &&
	    (internal_config.xen_dom0_support == 0) &&
	    (odp_hugepage_info_init() < 0)) {
		PRINT("Cannot get hugepage information\n");
		return -1;
	}

	if ((internal_config.memory == 0) && (internal_config.force_sockets == 0)) {
		if (internal_config.no_hugetlbfs)
			internal_config.memory = MEMSIZE_IF_NO_HUGE_PAGE;
		else
			internal_config.memory = odp_get_hugepage_mem_size();
	}

	if (internal_config.vmware_tsc_map == 1) {
#ifdef ODP_LIBODP_VMWARE_TSC_MAP_SUPPORT
		odp_cycles_vmware_tsc_map = 1;
		PRINT("Using VMWARE TSC MAP, you must have monitor_control.pseudo_perfctr = TRUE\n");
#else
		PRINT("Ignoring --vmware-tsc-map because ODP_LIBODP_VMWARE_TSC_MAP_SUPPORT is not set\n");
#endif
	}

	PRINT("memory=%lu (k), page_sz=%lu (k), dir=%s, num=%d\n",
	      internal_config.memory / 1024, internal_config.hugepage_info[0].hugepage_sz / 1024,
	      internal_config.hugepage_info[0].hugedir, internal_config.hugepage_info[0].num_pages[0]);

	odp_config_init();

	if (odp_pci_init() < 0) {
		PRINT("Cannot init PCI\n");
		return -1;
	}

	if (odp_memory_init() < 0) {
		PRINT("Cannot init memory\n");
		return -1;
	}

	/* the directories are locked during odp_hugepage_info_init */
	odp_hugedirs_unlock();

	if (odp_memzone_init() < 0) {
		PRINT("Cannot init memzone\n");
		return -1;
	}

	if (odp_tailqs_init() < 0) {
		PRINT("Cannot init tail queues for objects\n");
		return -1;
	}

	/* odp_check_mem_on_local_socket(); */
	odp_mcfg_complete();

	TAILQ_FOREACH(solib, &solib_list, next)
	{
		PRINT("open shared lib %s\n", solib->name);
		solib->lib_handle = dlopen(solib->name, RTLD_NOW);
		if (!solib->lib_handle)
			PRINT("%s\n", dlerror());
	}

	if (odp_dev_init() < 0) {
		PRINT("Cannot init pmd devices\n");
		return -1;
	}

	/* Probe & Initialize PCI devices */
	if (odp_pci_probe()) {
		PRINT("Cannot probe PCI\n");
		return -1;
	}

	return fctret;
}

/* get core role */
enum odp_lcore_role_t odp_lcore_role(unsigned lcore_id)
{
	return godp_config.lcore_role[lcore_id];
}

enum odp_proc_type_t odp_process_type(void)
{
	return godp_config.process_type;
}

int odp_has_hugepages(void)
{
	return !internal_config.no_hugetlbfs;
}

int odp_check_module(const char *module_name)
{
	char mod_name[30]; /* Any module names can be longer than 30 bytes? */
	int  ret = 0;
	int  n;

	if (NULL == module_name)
		return -1;

	FILE *fd = fopen("/proc/modules", "r");

	if (NULL == fd) {
		PRINT("Open /proc/modules failed! error %i (%s)\n",
		      errno, strerror(errno));
		return -1;
	}

	while (!feof(fd)) {
		n = fscanf(fd, "%29s %*[^\n]", mod_name);
		if ((n == 1) && !strcmp(mod_name, module_name)) {
			ret = 1;
			break;
		}
	}

	fclose(fd);

	return ret;
}

#define LINE_LEN 128
void odp_hexdump(FILE *f, const char *title, const void *buf, unsigned int len)
{
	unsigned int i, out, ofs;
	const unsigned char *data = buf;
	char line[LINE_LEN]; /* space needed 8+16*3+3+16 == 75 */

	fprintf(f, "%s at [%p], len=%u\n", (title) ? title  : "  Dump data", data, len);
	ofs = 0;
	while (ofs < len) {
		/* format the line in the buffer, then use PRINT to output to screen */
		out = snprintf(line, LINE_LEN, "%08X:", ofs);
		for (i = 0; ((ofs + i) < len) && (i < 16); i++)
			out += snprintf(line + out, LINE_LEN - out, " %02X", (data[ofs + i] & 0xff));

		for (; i <= 16; i++)
			out += snprintf(line + out, LINE_LEN - out, " | ");

		for (i = 0; (ofs < len) && (i < 16); i++, ofs++) {
			unsigned char c = data[ofs];

			if ((c < ' ') || (c > '~'))
				c = '.';

			out += snprintf(line + out, LINE_LEN - out, "%c", c);
		}

		fprintf(f, "%s\n", line);
	}

	fflush(f);
}

int odp_init_hisilicon(void)
{
	int test_argc = 5;
	char *test_argv[6];
	int core_count, i, num_cores = 0;
	char core_mask[8];
	int save_optind;

	core_count = odp_cpu_count();
	for (i = 0; i < core_count; i++)
		num_cores += (0x1 << i);

	sprintf(core_mask, "%x", num_cores);

	test_argv[0] = malloc(sizeof("odp_dpdk"));
	strcpy(test_argv[0], "odp_dpdk");
	test_argv[1] = malloc(sizeof("-c"));
	strcpy(test_argv[1], "-c");
	test_argv[2] = malloc(sizeof(core_mask));
	strcpy(test_argv[2], core_mask);
	test_argv[3] = malloc(sizeof("-n"));
	strcpy(test_argv[3], "-n");
	test_argv[4] = malloc(sizeof("3"));
	strcpy(test_argv[4], "3");

	save_optind = optind;
	optind = 1;
	if (odp_init(test_argc, (char **)test_argv) < 0) {
		optind = save_optind;
		for (i = 0; i < 5; i++)
			free(test_argv[i]);

		PRINT("Cannot init the odp_init!");
		return -1;
	}

	optind = save_optind;
	for (i = 0; i < 5; i++)
		free(test_argv[i]);

	return 0;
}
