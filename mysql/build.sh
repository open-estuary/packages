#!/bin/bash
#author: Bowen Liu
#date: 30/01/2016

#description: MySQL build & install script
#$1: target output directory
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables ####################
###################################################################################

echo "/packages/mysql/build.sh: outputdir=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./mysql/build.sh <outputdir> <distrib> <rootfs> <kernal>" 
    exit
fi

ROOTFS=$3
corenum=`cat /proc/cpuinfo | grep "processor"| wc -l`
current_dir=`pwd`/packages/mysql
echo "---- $current_dir ----"

###################################################################################
################################### Build MySQL ###################################
###################################################################################

#Not necessary to build MySQL so far because it will be installed from docker images
echo "MySQL will be installed through docker image later"

