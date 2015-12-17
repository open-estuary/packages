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
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided with the
 *       distribution.
 *     * Neither the name of Huawei Corporation nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
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
#define ACC_COMMON_H

#include <odp/crypto.h>
#include <odp_internal.h>
#include <odp/atomic.h>
#include <odp/spinlock.h>
#include <odp/sync.h>
#include <odp/debug.h>
#include <odp/align.h>
#include <odp/shared_memory.h>
#include <odp_his_crypto_internal.h>
#include <odp_debug_internal.h>
#include <odp/hints.h>
#include <odp/random.h>
#include <odp_packet_internal.h>
#include <odp/accdev.h>
#include <odp_acc_comm.h>
#include <odp_acc_dev_internal.h>

#include <string.h>

odp_acc_session_t odp_alloc_ssn(void)
{
	unsigned long long i;

	odp_spinlock_lock(&odp_acc_ssn_lock);

	for (i = 1; i < ODP_SSN_MAX_NUM; i++)
		if (ODP_NOTUSED == odp_ssn_used_flag[i]) {
			odp_ssn_used_flag[i] = ODP_USED;
			odp_spinlock_unlock(&odp_acc_ssn_lock);
			return i;
		}

	odp_spinlock_unlock(&odp_acc_ssn_lock);
	return 0;
}

void odp_free_ssn(odp_acc_session_t session)
{
	odp_spinlock_lock(&odp_acc_ssn_lock);
	odp_ssn_used_flag[session] = ODP_NOTUSED;
	odp_spinlock_unlock(&odp_acc_ssn_lock);
}
