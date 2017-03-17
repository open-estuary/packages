#!/bin/bash

#################################################################################
# Comment: Estuary Application Build.sh for hhvm
# Author: Chris
# Date : 2017/02/25
#################################################################################


BUILDDIR=$(cd $1; pwd)
DISTRO=$2
ROOTFS=$(cd $3; pwd)
KERNEL_DIR=$(cd $4; pwd)
CROSS=$5  # such as aarch64-linux-gnu- on X86 platform or "" on ARM64 platform
PACK_TYPE=$6 # such as "tar", "rpm", "deb" or "all"
PACK_SAVE_DIR=$(cd $7; pwd) #
INSTALL_DIR=$(cd $8; pwd) 


PACKAGE_DIR=`pwd`/packages
CUR_PKG="hhvm"
VERSION="3.17.3"

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

case $DISTRO in
    CentOS | Ubuntu)
        echo "begin to build $CUR_PKG-$VERSION ..."
    ;;
    Debian)
        exit 0
    ;;
    *)
        echo "unkown system: $DISTRO"
        exit 0
    ;;
esac

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
    cp ${PACKAGE_DIR}/${CUR_PKG}/setup.sh ./
    cp ${PACKAGE_DIR}/${CUR_PKG}/start.sh ./
    cp ${PACKAGE_DIR}/${CUR_PKG}/remove.sh ./   
    cp ${PACKAGE_DIR}/${CUR_PKG}/${CUR_PKG}-${VERSION}-${HOST}*.tar.gz ./
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
