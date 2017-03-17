#!/bin/bash


PWD=`pwd`
echo "pwd: #PWD"

sudo find ./workspace -name "hhvm-3.17.3*.tar.gz" | xargs sudo rm -fr
sudo rm ./workspace/packages/builddir/hhvm/tar/*.sh -fr
