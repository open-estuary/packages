/*
 * Copyright(c) 2010-2015 Intel Corporation.
 * Copyright(c) 2014-2015 Hisilicon Limited.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the BSD-3-Clause License as published by
 * the Free Software Foundation.
 *
 */

#include <sys/queue.h>
#include <stdint.h>
#include <errno.h>
#include <stdio.h>
#include <stdarg.h>
#include <string.h>
#include <inttypes.h>

#include <odp_memory.h>
#include <odp_mmdistrict.h>
#include <odp_base.h>
#include <odp_syslayout.h>
#include <odp_core.h>
#include <odp/config.h>
#include <odp/atomic.h>


#include "odp_private.h"
#include "odp_debug_internal.h"
TAILQ_HEAD(odp_tailq_elem_head, odp_tailq_elem);

/* local tailq list */
static struct odp_tailq_elem_head odp_tailq_elem_head =
	TAILQ_HEAD_INITIALIZER(odp_tailq_elem_head);

static int odp_tailqs_count = -1;

void odp_dump_tailq(FILE *f)
{
	struct odp_sys_layout *mcfg;
	unsigned i = 0;

	mcfg = odp_get_configuration()->sys_layout;

	odp_rwlock_read_lock(&mcfg->qlock);
	for (i = 0; i < ODP_MAX_TAILQ; i++) {
		const struct odp_tailq_head *tailq = &mcfg->tailq_head[i];
		const struct odp_tailq_entry_head *head = &tailq->tailq_head;

		fprintf(f, "Tailq %u: qname:<%s>, tqh_first:%p, tqh_last:%p\n",
			i, tailq->name, head->tqh_first, head->tqh_last);
	}

	odp_rwlock_read_lock(&mcfg->qlock);
}


struct odp_tailq_head *odp_tailq_lookup(const char *name)
{
	unsigned i;
	struct odp_sys_layout *mcfg = odp_get_configuration()->sys_layout;

	if (!name)
		return NULL;

	for (i = 0; i < ODP_MAX_TAILQ; i++)
		if (!strncmp(name, mcfg->tailq_head[i].name,
			     ODP_TAILQ_NAMESIZE - 1))
			return &mcfg->tailq_head[i];

	return NULL;
}

static int odp_tailq_local_register(struct odp_tailq_elem *t)
{
	struct odp_tailq_elem *temp;

	TAILQ_FOREACH(temp, &odp_tailq_elem_head, next)
	{
		if (!strncmp(t->name, temp->name, sizeof(temp->name)))
			return -1;
	}

	TAILQ_INSERT_TAIL(&odp_tailq_elem_head, t, next);
	return 0;
}


static struct odp_tailq_head *odp_tailq_create(const char *name)
{
	struct odp_tailq_head *head = NULL;

	if (!odp_tailq_lookup(name) &&
	    (odp_tailqs_count + 1 < ODP_MAX_TAILQ)) {
		struct odp_sys_layout *mcfg;

		mcfg = odp_get_configuration()->sys_layout;
		head = &mcfg->tailq_head[odp_tailqs_count];
		snprintf(head->name, sizeof(head->name) - 1, "%s", name);
		TAILQ_INIT(&head->tailq_head);
		odp_tailqs_count++;
	}

	return head;
}

static void odp_tailq_update(struct odp_tailq_elem *t)
{
	if (odp_process_type() == ODP_PROC_PRIMARY)
		t->head = odp_tailq_create(t->name);
	else
		t->head = odp_tailq_lookup(t->name);
}

int odp_tailq_register(struct odp_tailq_elem *t)
{
	if (odp_tailq_local_register(t) < 0) {
		ODP_PRINT("%s tailq is already registered\n", t->name);
		goto error;
	}
	if (odp_tailqs_count >= 0) {
		odp_tailq_update(t);
		if (!t->head) {
			ODP_ERR("Cannot initialize tailq: %s\n", t->name);
			TAILQ_REMOVE(&odp_tailq_elem_head, t, next);
			goto error;
		}
	}

	return 0;

error:
	t->head = NULL;
	return -1;
}

int odp_tailqs_init(void)
{
	struct odp_tailq_elem *t;

	odp_tailqs_count = 0;

	TAILQ_FOREACH(t, &odp_tailq_elem_head, next)
	{
		odp_tailq_update(t);
		if (!t->head) {
			ODP_ERR("Cannot initialize tailq: %s\n", t->name);
			goto fail;
		}
	}

	return 0;

fail:
	odp_dump_tailq(stderr);
	return -1;
}
