#!/bin/bash
#author: fajun yang
#date: 6/2/2016
#description: postinstall scripts for Armor Tools

###################### install odp driver ###########################

WORKADDR="/home/odp"
if [ ! -d "$WORKADDR" ]; then
	echo "error : no odp work space , build.sh run failed!"
	exit -1
fi

echo "init huge tables..."
HUGE_PATH="/mnt/huge/"
if [ ! -d "$HUGE_PATH" ]; then
sudo mkdir "$HUGE_PATH"
fi
sudo mount none $ROOTFS/mnt/huge -t hugetlbfs
echo 400 > $ROOTFS/proc/sys/vm/nr_hugepages
echo 0 > /proc/sys/kernel/randomize_va_space

echo "insmod uio kernel driver..."
cd $WORKADDR
insmod pv660_hns.ko

cd -
echo "install finished!"
