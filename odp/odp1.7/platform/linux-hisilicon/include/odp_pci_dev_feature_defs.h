/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_PCI_DEV_DEFS_H_
#define _ODP_PCI_DEV_DEFS_H_

/* interrupt mode */
enum odp_intr_mode {
	ODP_INTR_MODE_NONE = 0,
	ODP_INTR_MODE_LEGACY,
	ODP_INTR_MODE_MSI,
	ODP_INTR_MODE_MSIX
};
#endif /* _ODP_PCI_DEV_DEFS_H_ */
