#!/bin/bash


PWD=`pwd`
echo "pwd: #PWD"

sudo find ./workspace -name "jemalloc-4.3.1*.tar.gz" | xargs sudo rm -fr
sudo rm ./workspace/packages/builddir/jemalloc/tar/*.sh -fr
