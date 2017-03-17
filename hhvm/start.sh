#!/bin/bash

#################################################################################
# Comment: Estuary Application Start.sh for hhvm
# Author: Chris
# Date : 2017/02/25
#################################################################################

# This script is to start this package
# 

HOST=`uname -n`

PKG_NAME="hhvm"
PKG_VER="3.17.3"

export LD_LIBRARY_PATH=/usr/estuary/packages/boost-1.58.0/lib:$LD_LIBRARY_PATH

#create run and log directory
if [ ! -d /var/run/hhvm ];then
    mkdir -p /var/run/hhvm
fi

if [ ! -d /var/log/hhvm ];then
    mkdir -p /var/log/hhvm
fi

#start nginx service
if [ ! -e /usr/sbin/nginx ];then
    echo "[$PKG_NAME] nginx has not installed, please install it firtly"
    exit 0
else
    /usr/sbin/nginx -c /usr/estuary/etc/nginx/nginx.conf &
fi

#start hhvm service
if [ ! -e /usr/estuary/bin/hhvm ];then
    echo "[$PKG_NAME] hhvm has not installed ,please iinstall it firtly"
    exit 0
else
    /usr/estuary/bin/hhvm --mode daemon --config /usr/estuary/etc/hhvm/server.ini --config /usr/estuary/etc/hhvm/php.ini --config /usr/estuary/etc/hhvm/config.hdf
fi

echo "[$PKG_NAME] start hhvm service successfully"

exit 0

