#!/bin/bash

#################################################################################
# Comment: Estuary Application Remove.sh for hhvm
# Author: Chris
# Date : 2017/02/25
#################################################################################

# This script is to remove this package
# 

#kill the nginx and hhvm process
unset LD_LIBRARY_PATH

count=`ps -aux | grep nginx | wc -l`
if [ $count -gt 0 ];then
    kill -9 $(pidof nginx)
fi

count=`ps -aux | grep hhvm | wc -l`
if [ $count -gt 0 ];then
    kill -9 $(pidof hhvm)
fi

#remove the binary and configuration files of hhvm
if [ -d /usr/estuary/bin/hhvm ];then
    rm -fr /usr/estuary/bin/hhvm 
fi

if [ -d /usr/estuary/etc/hhvm ];then
    rm -fr /usr/estuary/etc/hhvm
fi

if [ -d /usr/estuary/etc/nginx ];then
    rm -fr /usr/estuary/etc/nginx
fi

exit 0

