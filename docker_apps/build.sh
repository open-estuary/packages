#!/bin/bash

#################################################################################
# Comment: Estuary Docker APP Application Build.sh 
# Author: Huang Jinhua
# Date : 2017/01/22
#################################################################################


BUILDDIR=$(cd $1; pwd)
DISTRO=$2
ROOTFS=$(cd $3; pwd)
KERNEL_DIR=$(cd $4; pwd)
CROSS=$5  # such as aarch64-linux-gnu- on X86 platform or "" on ARM64 platform
PACK_TYPE=$6 # such as "tar", "rpm", "deb" or "all"
PACK_SAVE_DIR=$(cd $7; pwd) #
INSTALL_DIR=$8


PACKAGE_DIR=`pwd`/packages/
CUR_PKG="docker_apps"
VERSION="1.0"

#
# Use ${CROSS}gcc to compile source codes under ${BUILDDIR}
# In addition, use ${INSTALL_DIR}/bin, ${INSTALL_DIR}/libs, and 
# ${INSTALL_DIR}/includes to install packages 
# 
# However if it needs to install two or more version of the same package
# it must be installed into ${INSTALL_DIR}/${CUR_PKG}/{bin,includes,libs} accordingly 
# 

#
# Generate the corresponding packages files 
#
#Generate tar file which MUST contain setup.sh 
if [ ! -d ${BUILDDIR}/tar ] ; then
    mkdir -p ${BUILDDIR}/tar
fi

if [ ! -d ${BUILDDIR}/rpm ] ; then
    mkdir -p ${BUILDDIR}/rpm
fi

if [ ! -d ${BUILDDIR}/deb ] ; then
    mkdir -p ${BUILDDIR}/deb
fi

if [ x"${PACK_TYPE}" == x"all" ] || [ x"${PACK_TYPE}" == x"tar" ] ; then
    pushd ${BUILDDIR}/tar
    sudo cp ${PACKAGE_DIR}/${CUR_PKG}/setup.sh ./
    sudo cp ${PACKAGE_DIR}/${CUR_PKG}/remove.sh ./
    sudo cp -fr ${PACKAGE_DIR}/${CUR_PKG}/lamp ./
    tar -czvf ../${CUR_PKG}-${VERSION}.tar.gz ./
    popd > /dev/null
    
    sudo cp ${BUILDDIR}/${CUR_PKG}-${VERSION}.tar.gz ${PACK_SAVE_DIR}/
fi

#Use rpmbuild to generete rpm file
if [ x"${PACK_TYPE}" == x"all" ] || [ x"${PACK_TYPE}" == x"rpm" ] ; then
    #Build rpm file firstly 
    sudo cp ${BUILDDIR}/${CUR_PKG}-${VERSION}.rpm ${PACK_SAVE_DIR}/
fi

#Generate deb files
if [ x"${PACK_TYPE}" == x"all" ] || [ x"${PACK_TYPE}" == x"deb" ] ; then
    #Build deb file firstly 
    sudo cp ${BUILDDIR}/${CUR_PKG}-${VERSION}.deb ${PACK_SAVE_DIR}/
fi

exit 0
