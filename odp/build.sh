#!/bin/bash
#author: fajun yang
#date: 5/2/2016
#description: odp-hisi build & install script
#$1: target platform name
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################### Initialise variables ####################

echo "/packages/docker/build.sh: Platform=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./docker/build.sh <platform> <distrib> <rootfs> <kernal>"
    exit
fi

echo "Installing odp package..."

PLATFORM=$1
DISTRO=$2
ROOTFS=$3
KERNEL_BUILD_DIR=$4
ODP_ADDR=`pwd`/packages/odp

############################# build odp  #####################

echo "init huge tables..."
HUGE_PATH="$ROOTFS/mnt/huge/"
if [ ! -d "$HUGE_PATH" ]; then
sudo mkdir "$HUGE_PATH"
fi
sudo mount none $ROOTFS/mnt/huge -t hugetlbfs
echo 400 > $ROOTFS/proc/sys/vm/nr_hugepages

LOCALARCH=`uname -m`
if [[ x"$LOCALARCH" =~ x"x86" ]]; then
	BUILDTYPE="cross"
	echo "arch is $LOCALARCH, build type is $BUILDTYPE"
else
	BUILDTYPE="native"
	echo "arch is $LOCALARCH, build type is $BUILDTYPE"
fi

echo "building the odp project..."

if [ x"$PLATFORM" = x"D01" ]; then
	TARGETARCH="ARM32"
	BUILDADDR=$ODP_ADDR/odp1.2/build_32    
else
	TARGETARCH="ARM64"
	BUILDADDR=$ODP_ADDR/odp1.2/build_64
fi

if [ x"BUILDTYPE" = x"cross" ]; then

	if [ x"$TARGETARCH" = x"ARM32" ]; then
		GNU_PREFIX=arm-linux-gnueabi-
	else
		GNU_PREFIX=aarch64-linux-gnu-
	fi

	echo "when build type is cross, we copy the files to use directly..."
	# as the cross enviroment is not ok, we do so.
else

	GNU_PREFIX=

	echo "when build type is native, we copy the files to use directly..."
	# add the build scriptes to build odp project in later version
fi

WORKADDR="$ROOTFS/home/odp"
echo "create a odp workspace($WORKADDR)..."
if [ ! -d "$WORKADDR" ]; then
        sudo mkdir "$WORKADDR"
fi

echo "copy odp kernel driver and app to workspace..."
sudo cp $BUILDADDR/ko/*.ko $WORKADDR/
sudo cp $BUILDADDR/app/* $WORKADDR/
echo "copy odp user driver to $ROOTFS/usr/lib/odp..."
if [ ! -d "$ROOTFS/usr/lib/odp" ]; then
	sudo mkdir "$ROOTFS/usr/lib/odp"
fi
sudo cp $BUILDADDR/user_drv/*.so $ROOTFS/usr/lib/odp
echo "copy odp libs to $ROOTFS/usr/lib/..."
sudo cp $BUILDADDR/bin/*.so $ROOTFS/usr/lib/

echo "odp drive and app copy finished!"
echo "odp build finished!"

############################# Install odp finished #####################
