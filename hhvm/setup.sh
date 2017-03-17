#!/bin/bash

#################################################################################
# Comment: Estuary Application Setup.sh for hhvm
# Author: Chris
# Date : 2017/02/25
#################################################################################


INSTALLDIR=$(cd $1; pwd)

#
# In addition, use ${INSTALLDIR}/bin, ${INSTALLDIR}/lib, and 
# ${INSTALLDIR}/include to install packages 
# 
# However if it needs to install two or more version of the same package
# it must be installed into ${INSTALLDIR}/${CUR_PKG}/{bin,include,libs} accordingly 
# In addition, it should create symbol links from ${INSTALLDIR}/{bin,libs,include} to
# ${INSTALLDIR}/${CUR_PKG}/{bin,libs,include}
#

#
# Return:
# 1) "exit 0" : return 'INSTALL_SUCCESS' if current setup is successful 
#               and it is NOT necessary to run this script again
# 2) "exit 2" : return 'INSTALL_ALWAYS' if current setup is successful 
#               but it still need to run this script during next boot up stage
# 3) "exit 1" : return INSTALL_FAILURE if any failure occurs 
#               so it will try to run this script later

HOST=`uname -n`

PKG_NAME="hhvm"
PKG_VER="3.17.3"

case $HOST in
    centos)
        echo "[$PKG_NAME] install package on $HOST system"
        # install hhvm dependent packages
        yum install tbb libdwarf freetype libjpeg-turbo ImageMagick libmemcached libxslt libyaml libtiff fontconfig libXext libXt libtool-ltdl \
        libSM libICE libX11 libgomp cyrus-sasl jbigkit libxcb libXau -y
    ;;
    ubuntu)
        echo "[$PKG_NAME] install package on $HOST system"
        #update the system
        apt-get update -y
        
        # install hhvm dependent packages
        apt-get install libjemalloc-dev libonig-dev libcurl3 libgoogle-glog-dev libtbb-dev libfreetype6-dev libjpeg-turbo8-dev libvpx-dev libxslt1-dev \
        libmagickwand-dev libc-client2007e-dev libmemcached-dev libmcrypt-dev libpq-dev libboost-dev libboost-filesystem-dev libboost-program-options-dev \
        libboost-regex-dev libboost-system-dev libboost-thread-dev libboost-context-dev -y
    ;;
    debian)
        echo "[$PKG_NAME] Do not support install hhvm on $HOST system"
        exit 0
    ;;
    *)
        echo "[$PKG_NAME] do not support install hhvm on $HOST system"
        exit 1
    ;;
esac

pushd $INSTALLDIR/packages/$PKG_NAME-$PKG_VER

if [ ! -e ./${PKG_NAME}-${PKG_VER}-${HOST}.aarch64.tar.gz ];then
    echo "[$PKG_NAME] the tarball is not exist"
    exit 1
fi

if [ ! -d ${INSTALLDIR}/bin ];then
    mkdir -p $INSTALLDIR/bin
fi

if [ ! -d ${INSTALLDIR}/etc/hhvm ];then
    mkdir -p ${INSTALLDIR}/etc/hhvm
fi

tar -xzf ./${PKG_NAME}-${PKG_VER}-${HOST}.aarch64.tar.gz
cp -fr ./bin/* $INSTALLDIR/bin
cp -fr ./hhvm/config.hdf $INSTALLDIR/etc/hhvm
cp -fr ./hhvm/php.ini $INSTALLDIR/etc/hhvm
cp -fr ./hhvm/server.ini $INSTALLDIR/etc/hhvm
rm -fr ./bin ./hhvm

popd > /dev/null

echo "setup hhvm successfully"

exit 0

