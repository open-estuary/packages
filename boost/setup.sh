#!/bin/bash

#################################################################################
# Comment: Estuary Application Setup.sh for boost
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

PKG_NAME="boost"
PKG_VER="1.58.0"

case $HOST in
    centos)
        echo "[$PKG_NAME] install boost package on $HOST system"
    ;;
    debian | ubuntu)
	exit 0
	;;
    *)
        echo "[$PKG_NAME] unkown system : $HOST"
        exit 1
    ;;
esac

pushd $INSTALLDIR/packages/$PKG_NAME-$PKG_VER

if [ ! -e ./$PKG_NAME-$PKG_VER*.tar.gz ];then
    echo "[$PKG_NAME] the boost tarball is not exist"
    exit 1
fi

tar -xzf ./$PKG_NAME-$PKG_VER*.tar.gz

popd > /dev/null

exit 0

