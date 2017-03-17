#!/bin/bash

#################################################################################
# Comment: Estuary Application Remove.sh for boost
# Author: Chris
# Date : 2017/02/25
#################################################################################

# This script is to remove this package
# 
CUR_PKG="boost"
VERSION="1.58.0"


#remove the binary of boost
pushd /usr/estuary/packages

if [ -d $CUR_PKG-$VERSION/lib ]; then
    rm -fr $CUR_PKG-$VERSION/lib
fi

popd > /dev/null
exit 0

