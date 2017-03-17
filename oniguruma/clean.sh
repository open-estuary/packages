#!/bin/bash


PWD=`pwd`
echo "pwd: #PWD"

sudo find ./workspace -name "oniguruma-6.1.3*.tar.gz" | xargs sudo rm -fr
sudo rm ./workspace/packages/builddir/oniguruma/tar/*.sh -fr
