#!/bin/bash
#author: Bowen Liu
#date: 30/01/2016

#description: Redis build & install script
#$1: target platform name
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables ####################
###################################################################################

echo "/packages/redis/build.sh: Platform=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./redis/build.sh <platform> <distrib> <rootfs> <kernal>" 
    exit
fi

ROOTFS=$3

current_dir=`pwd`/packages/redis
echo "---- $current_dir ----"

###################################################################################
################################### Build Redis ###################################
###################################################################################
LOCALARCH=`uname -m`
if [ x"$LOCALARCH" = x"x86_64" ]; then
    exit 1
fi

if [ x"$LOCALARCH" = x"aarch64" ]; then
    cd $current_dir/redis-3.0.7
    make
    mkdir $ROOTFS/usr/local/redis
    sudo cp redis.conf $ROOTFS/usr/local/redis
    cd ./src
    sudo cp redis-server $ROOTFS/usr/local/redis
    sudo cp redis-cli $ROOTFS/usr/local/redis
    sudo cp redis-benchmark $ROOTFS/usr/local/redis

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

    echo "redis make install finished"
    exit 0
fi

