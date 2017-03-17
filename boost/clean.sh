#!/bin/bash


PWD=`pwd`
echo "pwd: #PWD"

sudo find ./workspace -name "boost-1.58.0*.tar.gz" | xargs sudo rm -fr
sudo rm ./workspace/packages/builddir/boost/tar/*.sh -fr
