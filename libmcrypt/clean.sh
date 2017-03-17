#!/bin/bash


PWD=`pwd`
echo "pwd: #PWD"

sudo find ./workspace -name "libmcrypt-master*.tar.gz" | xargs sudo rm -fr
sudo rm ./workspace/packages/builddir/libmcrypt/tar/*.sh -fr
