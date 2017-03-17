#!/bin/bash

#################################################################################
# Comment: Estuary Application Setup.sh for jemalloc
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

PKG_NAME="jemalloc"
PKG_VER="4.3.1"

case $HOST in
    centos)
        echo "[$PKG_NAME] install package on $HOST system"
    ;;
    ubuntu | debian)
        exit 0
    ;;
    *)
        echo "[$PKG_NAME] do not support to install on $HOST system"
        exit 1
    ;;
esac

pushd $INSTALLDIR/packages/$PKG_NAME-$PKG_VER

if [ ! -e ./$PKG_NAME-$PKG_VER*.tar.gz ];then
    echo "[$PKG_NAME] the tarball is not exist"
    exit 1
fi

if [ ! -d $INSTALLDIR/lib ];then
    mkdir -p $INSTALLDIR/lib
fi

tar -xzf ./$PKG_NAME-$PKG_VER*.tar.gz
cp -fr ./lib/* $INSTALLDIR/lib
rm -fr ./lib

popd > /dev/null

exit 0

