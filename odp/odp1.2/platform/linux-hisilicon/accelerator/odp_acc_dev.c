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

#define  ACC_DEV_H

#include <sys/queue.h>
#include <string.h>

#include <odp/crypto.h>
#include <odp_internal.h>
#include <odp/atomic.h>
#include <odp/spinlock.h>
#include <odp/sync.h>
#include <odp/debug.h>
#include <odp/align.h>
#include <odp/shared_memory.h>
#include <odp_crypto_internal.h>
#include <odp_debug_internal.h>
#include <odp/hints.h>
#include <odp/random.h>
#include <odp_packet_internal.h>
#include <odp/accdev.h>
#include <odp_uio_internal.h>
#include <odp_acc_dev_internal.h>
#include <odp_acc_comm.h>

#include <sys/stat.h>
#include <sys/types.h>
#include <sys/file.h>
#include <unistd.h>
#include <fcntl.h>
#include <dirent.h>

TAILQ_HEAD(, odp_acc_dev) odp_acc_dev_list;

struct odp_acc_dev *odp_check_dev_is_reg(char *dev_name)
{
	struct odp_acc_dev *acc_dev;
	int len;

	TAILQ_FOREACH(acc_dev, &odp_acc_dev_list, next)
	{
		if (strlen(dev_name) != strlen(acc_dev->data.name))
			continue;

		len = strlen(dev_name);
		if (strncmp(dev_name, acc_dev->data.name, len) == 0)
			return acc_dev;
	}
	return NULL;
}

int odp_creat_dev_file(struct odp_acc_dev *dev)
{
	int fd;
	char file_path_name[128];
	mode_t mode = S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH;
	int  ret;

	memset((void *)file_path_name, 0, sizeof(file_path_name));
	(void)strncpy(file_path_name, ODP_DEV_PATH, strlen(ODP_DEV_PATH) + 1);
	(void)strcat(file_path_name, dev->data.name);

	fd = access(file_path_name, F_OK);
	if (fd < 0) {
		fd = creat(file_path_name, mode);
		if (fd < 0) {
			printf("call creat failed!");
			return (-1);
		}
	}

	fd = open(file_path_name, O_WRONLY);
	if (fd < 0) {
		printf("call open failed!");
		return (-1);
	}

	ret = flock(fd, LOCK_NB | LOCK_EX);
	if (ret < 0) {
		printf("call flock failed!");
		close(fd);
		return (-1);
	}

	dev->fd = fd;
	return 0;
}

void odp_delete_dev_file(struct odp_acc_dev *dev)
{
	int ret;
	char file_path_name[128];

	ret = flock(dev->fd, LOCK_UN);
	if (ret < 0) {
		printf("call flock failed!");
		return;
	}

	close(dev->fd);

	memset((void *)file_path_name, 0, sizeof(file_path_name));
	(void)strncpy(file_path_name, ODP_DEV_PATH, strlen(ODP_DEV_PATH) + 1);
	(void)strcat(file_path_name, dev->data.name);

	ret = remove(file_path_name);
	if (ret < 0) {
		printf("call remove failed!");
		return;
	}
}

odp_acc_dev_handle odp_alloc_dev_handle(struct odp_acc_dev *dev)
{
	uint32_t i;

	for (i = 1; i < ODP_ACC_HANDLE_NUM; i++)
		if (ODP_NOTUSED == odp_handle_used_flag[i]) {
			odp_handle_used_flag[i] = ODP_USED;
			odp_handle_dev_ptr[i] = dev;
			return i;
		}

	return 0;
}

void odp_free_dev_handle(odp_acc_dev_handle dev_handle)
{
	odp_handle_used_flag[dev_handle] = ODP_NOTUSED;
	odp_handle_dev_ptr[dev_handle] = NULL;
}

int odp_acc_dev_init(void)
{
	int ret;

	TAILQ_INIT(&odp_acc_dev_list);

	if (NULL == opendir(ODP_DEV_PATH)) {
		ret = mkdir(ODP_DEV_PATH, S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);
		if (ret) {
			printf("call mkdir failed!");
			return (-1);
		}
	}

	return 0;
}

int odp_acc_dev_register(struct odp_acc_dev *dev)
{
	char dev_name_temp[ODP_ACC_NAME_MAX_LEN];

	if (!dev) {
		printf("dev is null!\n");
		return (-1);
	}

	if (!dev->dev_ops) {
		printf("dev_ops is null!\n");
		return (-1);
	}

	if (dev->dev_type >= ODP_ACC_DEV_TYPE_INVALID) {
		printf("Invalid dev_type = %u!\n", dev->dev_type);
		return (-1);
	}

	if (dev->data.sub_type >= ODP_ACC_DEV_SUB_TYPE_INVALID) {
		printf("Invalid sub_type = %u!\n", dev->data.sub_type);
		return (-1);
	}

	if (dev->data.pf_attr.pf_id >= ODP_ACC_PF_NUM) {
		printf("Invalid pf_id = %u!\n", dev->data.pf_attr.pf_id);
		return (-1);
	}

	if (odp_check_dev_is_reg(dev->data.name)) {
		printf("call odp_check_dev_is_reg failed!\n");
		return (-1);
	}

	dev->dev_handle = odp_alloc_dev_handle(dev);
	if (!dev->dev_handle) {
		printf("call odp_alloc_dev_handle failed!\n");
		return (-1);
	}

	memset(dev_name_temp, 0, ODP_ACC_NAME_MAX_LEN);
	(void)sprintf(dev_name_temp, dev->data.name, _odp_typeval(dev->dev_handle));
	(void)strncpy(dev->data.name, dev_name_temp, ODP_ACC_NAME_MAX_LEN);

	if (dev->data.sub_type == ODP_ACC_DEV_PF_SUB_TYPE)
		odp_acc_pf_ptr[dev->data.pf_attr.pf_id] = dev;

	odp_spinlock_init(&dev->s.lock);
	dev->s.inq_default  = ODP_QUEUE_INVALID;
	dev->s.outq_default = ODP_QUEUE_INVALID;

	dev->attached = ODP_FALSE;
	odp_spinlock_init(&dev->dev_lock);
	TAILQ_INSERT_TAIL(&odp_acc_dev_list, dev, next);

	return 0;
}

int odp_acc_dev_unregister(struct odp_acc_dev *dev)
{
	struct odp_acc_dev *dev_temp;
	struct odp_acc_dev *dev_next;
	int len;

	if (!dev) {
		printf("dev is null!\n");
		return (-1);
	}

	for (dev_temp = TAILQ_FIRST(&odp_acc_dev_list); dev_temp; dev_temp = dev_next) {
		if (strlen(dev_temp->data.name) == strlen(dev->data.name)) {
			len = strlen(dev->data.name);
			if (strncmp(dev_temp->data.name, dev->data.name, len) == 0) {
				if (dev_temp->attached != ODP_FALSE) {
					printf("dev is opened!\n");
					return (-1);
				}

				odp_free_dev_handle(dev_temp->dev_handle);
				TAILQ_REMOVE(&odp_acc_dev_list, dev_temp, next);

				return 0;
			}
		}

		dev_next = TAILQ_NEXT(dev_temp, next);
	}

	printf("dev is not find!\n");
	return (-1);
}

void odp_acc_dev_lock(odp_acc_dev_handle dev_handle)
{
	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return;
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return;
	}

	odp_spinlock_lock(&odp_handle_dev_ptr[dev_handle]->dev_lock);
}

void odp_acc_dev_unlock(odp_acc_dev_handle dev_handle)
{
	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return;
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return;
	}

	odp_spinlock_unlock(&odp_handle_dev_ptr[dev_handle]->dev_lock);
}

int odp_acc_get_dev_names(enum odp_acc_dev_type dev_type, char *dev_name[], uint32_t *dev_num)
{
	int i;
	struct odp_acc_dev *acc_dev;
	uint32_t dev_num_temp = 0;

	if (!dev_num) {
		printf("dev_num is null!\n");
		return (-1);
	}

	for (i = 0; i < *dev_num; i++)
		if (!dev_name[i]) {
			printf("dev_name is null!\n");
			return (-1);
		}

	if (dev_type >= ODP_ACC_DEV_TYPE_INVALID) {
		printf("Invalid dev_type = %u!\n", dev_type);
		return (-1);
	}

	TAILQ_FOREACH(acc_dev, &odp_acc_dev_list, next)
	{
		if (dev_type == acc_dev->dev_type) {
			if (dev_num_temp < (*dev_num))
				(void)strncpy(*(dev_name + dev_num_temp), acc_dev->data.name, ODP_ACC_NAME_MAX_LEN);

			dev_num_temp++;
		}
	}

	*dev_num = dev_num_temp;

	return 0;
}

int odp_acc_get_dev_caps(char *dev_name, struct odp_acc_caps *caps)
{
	struct odp_acc_dev *acc_dev;

	if (!dev_name) {
		printf("dev_name is null!\n");
		return (-1);
	}

	if (!caps) {
		printf("caps is null!\n");
		return (-1);
	}

	acc_dev = odp_check_dev_is_reg(dev_name);
	if (acc_dev) {
		*caps = acc_dev->data.caps;
		return 0;
	}

	printf("dev is not find!\n");
	return (-1);
}

int odp_acc_get_dev_numa_node(char *dev_name, int *numa_node)
{
	odp_acc_dev_handle dev_handle;

	dev_handle = odp_acc_get_dev_handle(dev_name);

	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return (-1);
	}

	*numa_node = odp_handle_dev_ptr[dev_handle]->uio_dev->numa_node;
	return 0;
}

odp_acc_dev_handle odp_acc_get_dev_handle(char *dev_name)
{
	struct odp_acc_dev *acc_dev;

	if (!dev_name) {
		printf("dev_name is null!\n");
		return ODP_ACC_HANDLE_INVALID;
	}

	acc_dev = odp_check_dev_is_reg(dev_name);
	if (acc_dev)
		return acc_dev->dev_handle;

	printf("dev is not find!\n");
	return ODP_ACC_HANDLE_INVALID;
}

int odp_acc_get_dev_type(odp_acc_dev_handle dev_handle, enum odp_acc_dev_type *dev_type)
{
	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (!dev_type) {
		printf("dev_type is null!\n");
		return (-1);
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return (-1);
	}

	*dev_type = odp_handle_dev_ptr[dev_handle]->dev_type;

	return 0;
}

int odp_acc_open_dev(odp_acc_dev_handle dev_handle)
{
	int iret;

	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return (-1);
	}

	if (odp_handle_dev_ptr[dev_handle]->attached == ODP_TRUE) {
		printf("dev is opened!\n");
		return 0;
	}

	iret = odp_creat_dev_file(odp_handle_dev_ptr[dev_handle]);
	if (iret) {
		printf("call odp_creat_dev_file failed!\n");
		return iret;
	}

	iret = odp_handle_dev_ptr[dev_handle]->dev_ops->acc_dev_open_t(odp_handle_dev_ptr[dev_handle]);
	if (iret) {
		odp_delete_dev_file(odp_handle_dev_ptr[dev_handle]);
		printf("call acc_dev_open_t failed!\n");
		return iret;
	}

	/* odp_atomic_store_u64(&(odp_handle_dev_ptr[dev_handle]->dev_pkt_num), 0);
	   odp_atomic_store_u64(&(odp_handle_dev_ptr[dev_handle]->dev_sessin_num), 0); */
	odp_handle_dev_ptr[dev_handle]->attached = ODP_TRUE;

	return 0;
}

int odp_acc_close_dev(odp_acc_dev_handle dev_handle)
{
	int iret;

	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return (-1);
	}

	if (odp_handle_dev_ptr[dev_handle]->attached == ODP_FALSE) {
		printf("dev is closed!\n");
		return 0;
	}

	if (odp_atomic_load_u64(&odp_handle_dev_ptr[dev_handle]->dev_sessin_num)) {
		printf("session not delete!\n");
		return (-1);
	}

	iret = odp_handle_dev_ptr[dev_handle]->dev_ops->acc_dev_close_t(odp_handle_dev_ptr[dev_handle]);
	if (iret) {
		printf("call acc_dev_close_t failed!\n");
		return iret;
	}

	odp_delete_dev_file(odp_handle_dev_ptr[dev_handle]);

	odp_handle_dev_ptr[dev_handle]->attached = ODP_FALSE;
	return 0;
}

int odp_acc_reset_dev(odp_acc_dev_handle dev_handle)
{
	int iret;

	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return (-1);
	}

	if (odp_atomic_load_u64(&odp_handle_dev_ptr[dev_handle]->dev_sessin_num)) {
		printf("session not delete!\n");
		return (-1);
	}

	iret = odp_handle_dev_ptr[dev_handle]->dev_ops->acc_dev_reset_t(odp_handle_dev_ptr[dev_handle]);
	if (iret) {
		printf("call acc_dev_reset_t failed!\n");
		return iret;
	}

	return 0;
}

int odp_acc_get_dev_status(odp_acc_dev_handle dev_handle, uint32_t *status)
{
	int iret;
	uint32_t open_status;

	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return (-1);
	}

	if (!status) {
		printf("status is null!\n");
		return (-1);
	}

	iret = odp_acc_get_open_status(dev_handle, &open_status);
	if (iret) {
		printf("call odp_acc_get_open_status failed!\n");
		return iret;
	}

	if (open_status == ODP_TRUE) {
		iret = odp_handle_dev_ptr[dev_handle]->dev_ops->acc_get_dev_status_t(odp_handle_dev_ptr[dev_handle],
										     status);
		if (iret) {
			printf("call acc_get_dev_status_t failed!\n");
			return iret;
		}
	} else {
		*status = 0;
	}

	return 0;
}

int odp_acc_get_open_status(odp_acc_dev_handle dev_handle, uint32_t *status)
{
	int iret;
	char file_path_name[128];

	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return (-1);
	}

	if (!status) {
		printf("status is null!\n");
		return (-1);
	}

	memset((void *)file_path_name, 0, sizeof(file_path_name));
	(void)strncpy(file_path_name, ODP_DEV_PATH, strlen(ODP_DEV_PATH) + 1);
	(void)strcat(file_path_name, odp_handle_dev_ptr[dev_handle]->data.name);

	iret = access(file_path_name, F_OK);
	if (iret < 0)
		*status = ODP_FALSE;
	else
		*status = ODP_TRUE;

	return 0;
}

int odp_acc_get_dev_stat(odp_acc_dev_handle dev_handle, struct odp_acc_dev_stat *stat)
{
	int iret;

	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return (-1);
	}

	if (!stat) {
		printf("stat is null!\n");
		return (-1);
	}

	iret = odp_handle_dev_ptr[dev_handle]->dev_ops->acc_get_dev_stat_t(odp_handle_dev_ptr[dev_handle], stat);
	if (iret) {
		printf("call acc_get_dev_stat_t failed!\n");
		return iret;
	}

	return 0;
}

void odp_acc_dev_fault_notification_reg(odp_acc_dev_handle dev_handle, const odp_acc_dev_fault_cb_func cb_func,
					void *callback_tag)
{
}

int odp_acc_clear_dev_stat(odp_acc_dev_handle dev_handle)
{
	int iret;

	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return (-1);
	}

	iret = odp_handle_dev_ptr[dev_handle]->dev_ops->acc_clear_dev_stat_t(odp_handle_dev_ptr[dev_handle]);
	if (iret) {
		printf("call acc_clear_dev_stat_t failed!\n");
		return iret;
	}

	return 0;
}

int odp_acc_get_pf_dev_names(enum odp_acc_dev_type dev_type, char *dev_name[], uint32_t *dev_num)
{
	int i;
	struct odp_acc_dev *acc_dev;
	uint32_t dev_num_temp = 0;

	if (!dev_num) {
		printf("dev_num is null!\n");
		return (-1);
	}

	for (i = 0; i < *dev_num; i++)
		if (!dev_name[i]) {
			printf("dev_name is null!\n");
			return (-1);
		}

	if (dev_type >= ODP_ACC_DEV_TYPE_INVALID) {
		printf("Invalid dev_type = %u!\n", dev_type);
		return (-1);
	}

	TAILQ_FOREACH(acc_dev, &odp_acc_dev_list, next)
	{
		if ((dev_type == acc_dev->dev_type) && (acc_dev->data.sub_type == ODP_ACC_DEV_PF_SUB_TYPE)) {
			if (dev_num_temp < (*dev_num))
				(void)strncpy(dev_name[dev_num_temp], acc_dev->data.name, ODP_ACC_NAME_MAX_LEN);

			dev_num_temp++;
		}
	}

	*dev_num = dev_num_temp;

	return 0;
}

int odp_acc_get_vf_dev_names(char *dev_name, char *vf_name[], uint32_t *vf_num)
{
	int i;
	struct odp_acc_dev *acc_dev;
	uint32_t dev_num_temp = 0;
	odp_acc_dev_handle  dev_handle;

	if (!dev_name) {
		printf("dev_name is null!\n");
		return (-1);
	}

	if (!vf_num) {
		printf("vf_num is null!\n");
		return (-1);
	}

	for (i = 0; i < *vf_num; i++)
		if (!vf_name[i]) {
			printf("vf_name is null!\n");
			return (-1);
		}

	dev_handle = odp_acc_get_dev_handle(dev_name);
	if ((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM)) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (!odp_handle_dev_ptr[dev_handle]) {
		printf("dev is not find!\n");
		return (-1);
	}

	TAILQ_FOREACH(acc_dev, &odp_acc_dev_list, next)
	{
		if ((odp_handle_dev_ptr[dev_handle]->data.pf_attr.pf_id == acc_dev->data.pf_attr.pf_id) &&
		    (acc_dev->data.sub_type == ODP_ACC_DEV_VF_SUB_TYPE)) {
			if (dev_num_temp < (*vf_num))
				(void)strncpy(vf_name[dev_num_temp], acc_dev->data.name, ODP_ACC_NAME_MAX_LEN);

			dev_num_temp++;
		}
	}

	*vf_num = dev_num_temp;

	return 0;
}

int odp_acc_get_pf_dev_attr(char *dev_name, struct odp_acc_pf_attr *attr)
{
	struct odp_acc_dev *acc_dev;

	if (!dev_name) {
		printf("dev_name is null!\n");
		return (-1);
	}

	if (!attr) {
		printf("attr is null!\n");
		return (-1);
	}

	acc_dev = odp_check_dev_is_reg(dev_name);
	if (acc_dev) {
		*attr = acc_dev->data.pf_attr;
		return 0;
	}

	printf("dev is not find!\n");
	return (-1);
}

uint32_t odp_acc_dev_recv_burst(odp_acc_dev_handle dev_handle, union odp_acc_op_result result[], uint32_t pkts_num)
{
	int iret;
	int i;

	if (odp_unlikely((dev_handle == 0) || (dev_handle >= ODP_ACC_HANDLE_NUM))) {
		printf("Invalid dev_handle = %lu!\n", dev_handle);
		return (-1);
	}

	if (odp_unlikely(!odp_handle_dev_ptr[dev_handle])) {
		printf("dev is not find!\n");
		return (-1);
	}

	if (odp_unlikely((pkts_num == 0) || (pkts_num > 8))) {
		printf("Invalid pkts_num = %u!\n", pkts_num);
		return (-1);
	}

	iret = odp_handle_dev_ptr[dev_handle]->rx_pkt_burst(odp_handle_dev_ptr[dev_handle], result, pkts_num);

	for (i = 0; i < iret; i++) {
		result[i].session = odp_ssn_invert_mng[dev_handle][result[i].session];

		odp_atomic_dec_u64(&odp_handle_dev_ptr[dev_handle]->dev_pkt_num);
		odp_atomic_dec_u64(&odp_ssn_mng[result[i].session].ssn_pkt_num);
	}

	return iret;
}

/*
 * @todo This is a serious hack to allow us to use packet buffer to convey
 *       crypto operation results by placing them at the very end of the
 *       packet buffer.  The issue should be resolved shortly once the issue
 *       of packets versus events on completion queues is closed.
 */
odp_acc_generic_op_result_t *get_op_result_from_event(odp_event_t ev)
{
	uint8_t *temp;
	odp_acc_generic_op_result_t *result;
	odp_buffer_t buf;

	/* HACK: Buffer is not packet any more in the API.
	 * Implementation still works that way. */
	buf = odp_buffer_from_event(ev);

	temp  = odp_buffer_addr(buf);
	temp += odp_buffer_size(buf);
	temp -= sizeof(*result);
	result = (odp_acc_generic_op_result_t *)(void *)temp;

	return result;
}

void odp_acc_result_to_compl(odp_event_t	      completion_event,
			     union odp_acc_op_result *result)
{
	odp_acc_generic_op_result_t *op_result;

	op_result = get_op_result_from_event(completion_event);
	op_result->magic  = OP_RESULT_MAGIC;
	op_result->result = *result;
}
