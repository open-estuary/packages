#!/bin/sh
mkdir /mnt/huge
mount none /mnt/huge -t hugetlbfs
echo 200 > /proc/sys/vm/nr_hugepages
insmod uio_enet_drv.ko
insmod sec_drv.ko