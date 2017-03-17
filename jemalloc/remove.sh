#!/bin/bash

#################################################################################
# Comment: Estuary Application Remove.sh for jemalloc
# Author: Chris
# Date : 2017/02/25
#################################################################################

# This script is to remove this package
# 
CUR_PKG="jemalloc"

#remove the binary of jemalloc
count=`find / -name "lib$CUR_PKG.so*" | wc -l`
if [ $count -gt 0 ];then
    rm -fr /usr/estuary/lib/lib$CUR_PKG.so*
fi

exit 0

