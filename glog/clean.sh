#!/bin/bash


PWD=`pwd`
echo "pwd: #PWD"

sudo find ./workspace -name "glog-0.3.4*.tar.gz" | xargs sudo rm -fr
sudo rm ./workspace/packages/builddir/glog/tar/*.sh -fr
