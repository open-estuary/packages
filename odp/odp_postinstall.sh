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

echo "insmod uio kernel driver..."
cd $WORKADDR
insmod uio_enet_drv.ko

cd -
echo "install finished!"
