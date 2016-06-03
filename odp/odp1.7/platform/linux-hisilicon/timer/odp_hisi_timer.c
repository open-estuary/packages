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
#include <stdio.h>
#include <stdint.h>
#include <inttypes.h>
#include <assert.h>
#include <sys/queue.h>

#include <odp/atomic.h>
#include <odp/random.h>
#include <odp/hints.h>

#include <odp_common.h>
#include <odp_cycles.h>

#include <odp_memory.h>
#include <odp_mmdistrict.h>

#include <odp_base.h>

#include <odp_core.h>

#include <odp/spinlock.h>
#include <odp/sync.h>

#include "odp_hisi_timer.h"
#include "odp_hisi_atomic.h"
#include "odp_debug_internal.h"
LIST_HEAD(odp_timer_list, odp_hisi_timer);

struct priv_timer {
	struct odp_hisi_timer pending_head;
	odp_spinlock_t	      list_lock;

	int updated;

	unsigned curr_skiplist_depth;

	unsigned prev_core;
} __odp_cache_aligned;

static struct priv_timer priv_timer[ODP_MAX_CORE];

#define __TIMER_STAT_ADD(name, n) do {} while (0)

void odp_hisi_timer_subsystem_init(void)
{
	unsigned core_id;

	for (core_id = 0; core_id < ODP_MAX_CORE; core_id++) {
		odp_spinlock_init(&priv_timer[core_id].list_lock);
		priv_timer[core_id].prev_core = core_id;
	}
}

void odp_hisi_timer_init(struct odp_hisi_timer *tim)
{
	union odp_hisi_timer_status status;

	status.state = ODP_HISI_TIMER_STOP;
	status.owner = ODP_HISI_TIMER_NO_OWNER;
	tim->status.u32 = status.u32;
}

static int timer_set_config_state(struct odp_hisi_timer	      *tim,
				  union odp_hisi_timer_status *ret_prev_status)
{
	union odp_hisi_timer_status prev_status, status;
	int success = 0;
	unsigned core_id;

	core_id = odp_core_id();

	while (success == 0) {
		prev_status.u32 = tim->status.u32;

		if ((prev_status.state == ODP_HISI_TIMER_RUNNING) &&
		    (prev_status.owner != (uint16_t)core_id))
			return -1;

		if (prev_status.state == ODP_HISI_TIMER_CONFIG)
			return -1;

		status.state = ODP_HISI_TIMER_CONFIG;
		status.owner = (int16_t)core_id;
		success =
			odp_atomic_cmpset_u32_a64(
				(odp_atomic_u32_t *)&tim->status.u32,
				prev_status.u32,
				status.u32);
	}

	ret_prev_status->u32 = prev_status.u32;
	return 0;
}

static int timer_set_running_state(struct odp_hisi_timer *tim)
{
	union odp_hisi_timer_status prev_status, status;
	unsigned core_id = odp_core_id();
	int success = 0;

	while (success == 0) {
		prev_status.u32 = tim->status.u32;

		if (prev_status.state != ODP_HISI_TIMER_PENDING)
			return -1;

		status.state = ODP_HISI_TIMER_RUNNING;
		status.owner = (int16_t)core_id;
		success =
			odp_atomic_cmpset_u32_a64(
				(odp_atomic_u32_t *)&tim->status.u32,
				prev_status.u32,
				status.u32);
	}

	return 0;
}

static uint32_t timer_get_skiplist_level(unsigned curr_depth)
{
	uint32_t rand;

	odp_random_data((uint8_t *)&rand, sizeof(int32_t), 0);
	rand = rand & (UINT32_MAX - 1);

	uint32_t level = rand == 0 ? MAX_SKIPLIST_DEPTH :
			 (odp_bsf32(rand) - 1) / 2;

	if (level > curr_depth)
		level = curr_depth;

	if (level >= MAX_SKIPLIST_DEPTH)
		level = MAX_SKIPLIST_DEPTH - 1;

	return level;
}

static void timer_get_prev_entries(uint64_t time_val, unsigned tim_core,
				   struct odp_hisi_timer **prev)
{
	unsigned lvl = priv_timer[tim_core].curr_skiplist_depth;

	prev[lvl] = &priv_timer[tim_core].pending_head;
	while (lvl != 0) {
		lvl--;
		prev[lvl] = prev[lvl + 1];
		while (prev[lvl]->sl_next[lvl] &&
		       prev[lvl]->sl_next[lvl]->expire <= time_val)
			prev[lvl] = prev[lvl]->sl_next[lvl];
	}
}

static void timer_get_prev_entries_for_node(struct odp_hisi_timer  *tim,
					    unsigned		    tim_core,
					    struct odp_hisi_timer **prev)
{
	int i;

	timer_get_prev_entries(tim->expire - 1, tim_core, prev);
	for (i = priv_timer[tim_core].curr_skiplist_depth - 1; i >= 0; i--)
		while (prev[i]->sl_next[i] && (prev[i]->sl_next[i] != tim) &&
		       (prev[i]->sl_next[i]->expire <= tim->expire))
			prev[i] = prev[i]->sl_next[i];
}

static void timer_add(struct odp_hisi_timer *tim,
		      unsigned tim_core, int local_is_locked)
{
	unsigned core_id = odp_core_id();
	unsigned lvl;
	struct odp_hisi_timer *prev[MAX_SKIPLIST_DEPTH + 1];

	if ((tim_core != core_id) || !local_is_locked)
		odp_spinlock_lock(&priv_timer[tim_core].list_lock);

	timer_get_prev_entries(tim->expire, tim_core, prev);

	const unsigned tim_level = timer_get_skiplist_level(
		priv_timer[tim_core].curr_skiplist_depth);

	if (tim_level == priv_timer[tim_core].curr_skiplist_depth)
		priv_timer[tim_core].curr_skiplist_depth++;

	lvl = tim_level;
	while (lvl > 0) {
		tim->sl_next[lvl] = prev[lvl]->sl_next[lvl];
		prev[lvl]->sl_next[lvl] = tim;
		lvl--;
	}

	tim->sl_next[0] = prev[0]->sl_next[0];
	prev[0]->sl_next[0] = tim;

	priv_timer[tim_core].pending_head.expire =
		priv_timer[tim_core].pending_head.sl_next[0]->expire;

	if ((tim_core != core_id) || !local_is_locked)
		odp_spinlock_unlock(&priv_timer[tim_core].list_lock);
}

static void timer_del(struct odp_hisi_timer	 *tim,
		      union odp_hisi_timer_status prev_status,
		      int			  local_is_locked)
{
	unsigned core_id = odp_core_id();
	unsigned prev_owner = prev_status.owner;
	int i;
	struct odp_hisi_timer *prev[MAX_SKIPLIST_DEPTH + 1];

	if ((prev_owner != core_id) || !local_is_locked)
		odp_spinlock_lock(&priv_timer[prev_owner].list_lock);

	if (tim == priv_timer[prev_owner].pending_head.sl_next[0])
		priv_timer[prev_owner].pending_head.expire =
			((!tim->sl_next[0]) ? 0 : tim->sl_next[0]->expire);

	timer_get_prev_entries_for_node(tim, prev_owner, prev);
	for (i = priv_timer[prev_owner].curr_skiplist_depth - 1; i >= 0; i--)
		if (prev[i]->sl_next[i] == tim)
			prev[i]->sl_next[i] = tim->sl_next[i];

	for (i = priv_timer[prev_owner].curr_skiplist_depth - 1; i >= 0; i--)
		if (!priv_timer[prev_owner].pending_head.sl_next[i])
			priv_timer[prev_owner].curr_skiplist_depth--;
		else
			break;

	if ((prev_owner != core_id) || !local_is_locked)
		odp_spinlock_unlock(&priv_timer[prev_owner].list_lock);
}

static int __odp_hisi_timer_reset(struct odp_hisi_timer *tim, uint64_t expire,
				  uint64_t period, unsigned tim_core,
				  odp_timer_cb_t fct, void *arg,
				  int local_is_locked)
{
	union odp_hisi_timer_status prev_status, status;
	int ret;
	unsigned core_id = odp_core_id();

	if (tim_core == (unsigned)CORE_ID_ANY) {
		if (core_id < ODP_MAX_CORE) {
			tim_core = odp_get_next_core(
				priv_timer[core_id].prev_core,
				0, 1);
			priv_timer[core_id].prev_core = tim_core;
		} else {

			tim_core = odp_get_next_core(CORE_ID_ANY, 0, 1);
		}
	}

	ret = timer_set_config_state(tim, &prev_status);
	if (ret < 0)
		return -1;

	__TIMER_STAT_ADD(reset, 1);
	if ((prev_status.state == ODP_HISI_TIMER_RUNNING) &&
	    (core_id < ODP_MAX_CORE))
		priv_timer[core_id].updated = 1;

	if (prev_status.state == ODP_HISI_TIMER_PENDING) {
		timer_del(tim, prev_status, local_is_locked);
		__TIMER_STAT_ADD(pending, -1);
	}

	tim->period = period;
	tim->expire = expire;
	tim->f = fct;
	tim->arg = arg;

	__TIMER_STAT_ADD(pending, 1);
	timer_add(tim, tim_core, local_is_locked);

	odp_mb_full();
	status.state = ODP_HISI_TIMER_PENDING;
	status.owner = (int16_t)tim_core;
	tim->status.u32 = status.u32;

	return 0;
}

int odp_hisi_timer_reset(struct odp_hisi_timer *tim, uint64_t ticks,
			 enum odp_hisi_timer_type type, unsigned tim_core,
			 odp_timer_cb_t fct, void *arg)
{
	uint64_t cur_time = odp_get_tsc_cycles();
	uint64_t period;

	if (odp_unlikely((tim_core != (unsigned)CORE_ID_ANY) &&
			 (!odp_core_is_enabled(tim_core))))
		return -1;

	if (type == PERIODICAL)
		period = ticks;
	else
		period = 0;

	return __odp_hisi_timer_reset(tim, cur_time + ticks, period, tim_core,
				      fct, arg, 0);
}

void odp_hisi_timer_reset_sync(struct odp_hisi_timer *tim, uint64_t ticks,
			       enum odp_hisi_timer_type type, unsigned tim_core,
			       odp_timer_cb_t fct, void *arg)
{
	while (odp_hisi_timer_reset(tim, ticks, type, tim_core,
				    fct, arg) != 0)
		odp_pause();
}

int odp_hisi_timer_stop(struct odp_hisi_timer *tim)
{
	union odp_hisi_timer_status prev_status, status;
	unsigned core_id = odp_core_id();
	int ret;

	ret = timer_set_config_state(tim, &prev_status);
	if (ret < 0)
		return -1;

	__TIMER_STAT_ADD(stop, 1);
	if ((prev_status.state == ODP_HISI_TIMER_RUNNING) &&
	    (core_id < ODP_MAX_CORE))
		priv_timer[core_id].updated = 1;

	if (prev_status.state == ODP_HISI_TIMER_PENDING) {
		timer_del(tim, prev_status, 0);
		__TIMER_STAT_ADD(pending, -1);
	}

	odp_mb_full();
	status.state = ODP_HISI_TIMER_STOP;
	status.owner = ODP_HISI_TIMER_NO_OWNER;
	tim->status.u32 = status.u32;

	return 0;
}

void odp_hisi_timer_stop_sync(struct odp_hisi_timer *tim)
{
	while (odp_hisi_timer_stop(tim) != 0)
		odp_pause();
}

int odp_hisi_timer_pending(struct odp_hisi_timer *tim)
{
	return tim->status.state == ODP_HISI_TIMER_PENDING;
}

void odp_hisi_timer_manage(void)
{
	union odp_hisi_timer_status status;
	struct odp_hisi_timer *tim, *next_tim;
	unsigned core_id = odp_core_id();
	struct odp_hisi_timer *prev[MAX_SKIPLIST_DEPTH + 1];
	uint64_t cur_time;
	int i, ret;

	assert(core_id < ODP_MAX_CORE);

	__TIMER_STAT_ADD(manage, 1);

	if (!priv_timer[core_id].pending_head.sl_next[0])
		return;

	cur_time = odp_get_tsc_cycles();

	odp_spinlock_lock(&priv_timer[core_id].list_lock);

	if ((!priv_timer[core_id].pending_head.sl_next[0]) ||
	    (priv_timer[core_id].pending_head.sl_next[0]->expire > cur_time))
		goto done;

	tim = priv_timer[core_id].pending_head.sl_next[0];

	timer_get_prev_entries(cur_time, core_id, prev);
	for (i = priv_timer[core_id].curr_skiplist_depth - 1; i >= 0; i--) {
		priv_timer[core_id].pending_head.sl_next[i] =
			prev[i]->sl_next[i];
		if (!prev[i]->sl_next[i])
			priv_timer[core_id].curr_skiplist_depth--;

		prev[i]->sl_next[i] = NULL;
	}

	for (; tim; tim = next_tim) {
		next_tim = tim->sl_next[0];

		ret = timer_set_running_state(tim);

		if (ret < 0)
			continue;

		odp_spinlock_unlock(&priv_timer[core_id].list_lock);

		priv_timer[core_id].updated = 0;

		tim->f(tim, tim->arg);

		odp_spinlock_lock(&priv_timer[core_id].list_lock);
		__TIMER_STAT_ADD(pending, -1);

		if (priv_timer[core_id].updated == 1)
			continue;

		if (tim->period == 0) {
			status.state = ODP_HISI_TIMER_STOP;
			status.owner = ODP_HISI_TIMER_NO_OWNER;
			odp_mb_full();
			tim->status.u32 = status.u32;
		} else {
			status.state = ODP_HISI_TIMER_PENDING;
			__TIMER_STAT_ADD(pending, 1);
			status.owner = (int16_t)core_id;
			odp_mb_full();
			tim->status.u32 = status.u32;
			__odp_hisi_timer_reset(tim, cur_time + tim->period,
					       tim->period, core_id, tim->f,
					       tim->arg, 1);
		}
	}

	priv_timer[core_id].pending_head.expire =
		(!priv_timer[core_id].pending_head.sl_next[0]) ? 0 :
		priv_timer[core_id].pending_head.sl_next[0]->expire;
done:

	odp_spinlock_unlock(&priv_timer[core_id].list_lock);
}

void odp_hisi_timer_dump_stats(FILE *f)
{

	fprintf(f, "No timer statistics, "
		"ODP_TIMER_DEBUG is disabled\n");
}
