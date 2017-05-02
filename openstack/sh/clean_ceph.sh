#!/bin/bash


sudo systemctl stop ceph*
sudo umount /var/lib/ceph/osd/ceph-*

if [ -f /etc/debian_version ]; then
	sudo  apt-get purge ceph* -y
	sudo  apt-get purge rados* -y
fi


if [ -f /etc/redhat-release ]; then
	sudo yum remove ceph* -y
	sudo yum remove librados* -y
fi

sudo rm -rvf /var/lib/ceph
sudo rm -rvf /run/ceph
sudo rm -rvf /run/lock/ceph-disk

## note for the osds you need to format the osd disks
DISK_LIST=$(fdisk -l|grep "Disk /"|cut -c 11-13)
SYSTEM_DISK=$(lsblk |grep /|cut -c 3-5)

for DISK in ${DISK_LIST}
do
	if [ "${DISK}" != "${SYSTEM_DISK}" ]; then
		echo "sudo mkfs.ext4  ${DISK}"
	fi

done
