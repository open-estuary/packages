#!/bin/bash
#author: Bowen Liu
#date: 22/06/2016

#description: add docker apps script
#$1: target output directory
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables #######################################
###################################################################################

echo "/packages/docker_apps/build.sh: outputdir=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./docker_apps/build.sh <outputdir> <distrib> <rootfs> <kernal>" 
    exit
fi

ROOTFS=$3
current_dir=`pwd`/packages/docker_apps
echo "---- $current_dir ----"

###################################################################################
################################### Add Docker Apps ###############################
###################################################################################

sudo mkdir -p $ROOTFS/home/docker_apps
sudo cp -rf $current_dir/lamp $ROOTFS/home/docker_apps
   
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

echo "add docker apps finished"
exit 0
