#!/bin/bash
#author: Bowen Liu
#date: 19/05/2016

#description: MySQL build & install script
#$1: target platform name
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables #######################################
###################################################################################

echo "/packages/lamp/build.sh: Platform=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./lamp/build.sh <platform> <distrib> <rootfs> <kernal>" 
    exit
fi

ROOTFS=$3
corenum=`cat /proc/cpuinfo | grep "processor"| wc -l`
current_dir=`pwd`/packages/lamp
prebuild_dir=`pwd`/prebuild
echo "---- $current_dir ----"
echo "---- $prebuild_dir ----"

###################################################################################
################################### Build LAMP ###################################
###################################################################################
sudo cp $prebuild_dir/openestuary_apache.tar.gz  $ROOTFS/root
sudo cp $prebuild_dir/openestuary_mysql.tar.gz $ROOTFS/root
sudo cp -rf $current_dir/Discuz $ROOTFS/root
sudo cp -rf $current_dir/mysql_data $ROOTFS/root
case $DISTRO in
    Fedora)
        ;;
    OpenSuse)
        ;;
    Ubuntu)
        ;;
    Debian)
    ;;
    esac

exit 0
