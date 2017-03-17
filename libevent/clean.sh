#!/bin/bash


PWD=`pwd`
echo "pwd: #PWD"

sudo find ./workspace -name "libevent-2.1.7*.tar.gz" | xargs sudo rm -fr
sudo rm ./workspace/packages/builddir/libevent/tar/*.sh -fr
