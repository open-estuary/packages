#!/bin/bash
#author: Shameer
#date: 21/01/2016
#description: Docker install script
#$1: target output directory
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables ####################
###################################################################################

docker_dir=`pwd`/packages/docker

BUILDDIR=$(cd $1; pwd)
DISTRO=$2
ROOTFS=$(cd $3; pwd)
KERNEL_DIR=$(cd $4; pwd)
CROSS=$5  # such as aarch64-linux-gnu- on X86 platform or "" on ARM64 platform   
PACK_TYPE=$6 # such as "tar", "rpm", "deb" or "all"
PACK_SAVE_DIR=$(cd $7; pwd) #
INSTALL_DIR=$8

###################################################################################
############################# Install Docker #############################
###################################################################################
if [ ! -d ${BUILDDIR}/tar ] ; then
    sudo mkdir ${BUILDDIR}/tar
fi

pushd ${BUILDDIR}/tar
sudo cp ${docker_dir}/setup.sh ./
sudo cp ${docker_dir}/start.sh ./
if [ "${DISTRO}" == "Debian" ] ; then
    sudo cp ${docker_dir}/binary/*.gz ./
    sudo tar -zcf ${BUILDDIR}/docker.tar.gz ./
else 
#It could use apt-get/yum to install docker directly on other platforms
#So just pack its setup.sh 
    
    sudo tar -zcf ${BUILDDIR}/docker.tar.gz setup.sh start.sh
fi
popd > /dev/null

sudo cp ${BUILDDIR}/docker.tar.gz ${PACK_SAVE_DIR}/
exit 0

