#!/bin/bash
#author: Shameer
#date: 21/01/2016
#description: Docker install script
#$1: target platform name
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables ####################
###################################################################################

echo "Installing docker package..."

echo "/packages/docker/build.sh: Platform=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./docker/build.sh <platform> <distrib> <rootfs> <kernal>"
    exit
fi

PLATFORM=$1
DISTRO=$2
ROOTFS=$3
KERNEL_BUILD_DIR=$4
docker_dir=`pwd`/packages/docker

###################################################################################
############################# Install Docker #############################
###################################################################################


# copy prebuilt binaries to the rootfs and untar

sudo cp $docker_dir/binary/*.gz   $ROOTFS/
pushd $ROOTFS/
sudo tar xvf docker*.gz -C $ROOTFS/
sudo rm docker*.gz

#Create the libdevmapper sym link used by docker for distros
case $DISTRO in
    Fedora)
        cd lib64
        sudo ln -s libdevmapper.so.1.02 libdevmapper.so.1.02.1
        cd -
        ;;
    OpenSuse)
        cd lib64
        sudo ln -s libdevmapper.so.1.02 libdevmapper.so.1.02.1
        cd -
        ;;
    Ubuntu)
        ;;
    Debian)
    ;;
    esac

popd

echo "Installing docker package done."

exit 0
