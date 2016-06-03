/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#ifndef _ODP_TAILQ_H_
#define _ODP_TAILQ_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <sys/queue.h>
#include <stdio.h>

struct odp_tailq_entry {
	TAILQ_ENTRY(odp_tailq_entry) next;
	void *data;
};

TAILQ_HEAD(odp_tailq_entry_head, odp_tailq_entry);

#define ODP_TAILQ_NAMESIZE 32

struct odp_tailq_head {
	struct odp_tailq_entry_head tailq_head;
	char			    name[ODP_TAILQ_NAMESIZE];
};

struct odp_tailq_elem {

	struct odp_tailq_head *head;

	TAILQ_ENTRY(odp_tailq_elem) next;

	const char name[ODP_TAILQ_NAMESIZE];
};

#define ODP_TAILQ_CAST(tailq_entry, struct_name) \
	((struct struct_name *)&(tailq_entry)->tailq_head)

#define ODP_TAILQ_LOOKUP(name, struct_name) \
	ODP_TAILQ_CAST(odp_tailq_lookup(name), struct_name)

void odp_dump_tailq(FILE *f);

struct odp_tailq_head *odp_tailq_lookup(const char *name);

int odp_tailq_register(struct odp_tailq_elem *t);

#define ODP_REGISTER_TAILQ(t) \
	void tailqinitfn_ ## t(void); \
	void __attribute__((constructor, used)) tailqinitfn_ ## t(void) \
	{ \
		if (odp_tailq_register(&t) < 0) \
			printf("Cannot initialize tailq: %s\n", t.name); \
	}

#ifdef __cplusplus
}
#endif
#endif /* _ODP_TAILQ_H_ */
