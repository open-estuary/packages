/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_PRIVATE_H_
#define _ODP_PRIVATE_H_

#include <stdio.h>

int odp_mm_district_init(void);

int odp_common_log_init(FILE *default_log);

int odp_cpu_init(void);

int odp_memory_init(void);

int odp_hisi_timer_init(void);

int odp_log_early_init(void);

int odp_log_init(const char *id, int facility);

int odp_pci_info_init(void);

struct odp_pci_driver;
struct odp_pci_device;

int odp_pci_probe_one_driver(struct odp_pci_driver *dr,
			     struct odp_pci_device *dev);

int odp_pci_close_one_driver(struct odp_pci_driver *dr,
			     struct odp_pci_device *dev);

int odp_tailqs_init(void);

int odp_intr_init(void);

int odp_alarm_init(void);

int odp_dev_init(void);

int odp_check_module(const char *module_name);
#endif /* _ODP_PRIVATE_H_ */
