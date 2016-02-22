#!/bin/bash
#author: Bowen Liu
#date: 30/01/2016

#description: Nginx build & install script
#$1: target platform name
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables ####################
###################################################################################

echo "/packages/nginx/build.sh: Platform=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./nginx/build.sh <platform> <distrib> <rootfs> <kernal>" 
    exit
fi

ROOTFS=$3
corenum=`cat /proc/cpuinfo | grep "processor"| wc -l`
current_dir=`pwd`/packages/nginx
echo "---- $current_dir ----"

###################################################################################
################################### Build Nginx ###################################
###################################################################################
LOCALARCH=`uname -m`
if [ x"$LOCALARCH" = x"x86_64" ]; then
    exit 1
fi

if [ x"$LOCALARCH" = x"aarch64" ]; then
    sudo apt-get install g++ <<EOF
Y
EOF
    pcre_path="$ROOTFS/usr/local/pcre"
    if [ ! -d "$pcre_path" ]; then
    	cd $current_dir/pcre-8.37
    	./configure --prefix=$ROOTFS/usr/local/pcre
    	make -j${corenum} 
    	sudo make install
    fi
    cd $current_dir/zlib-1.2.8
    ./configure --prefix=$ROOTFS/usr/local/zlib
    make -j${corenum}
    sudo make install
    cd $current_dir/nginx-1.8.0
    ./configure --prefix=$ROOTFS/usr/local/nginx --with-pcre=$current_dir/pcre-8.37 --with-zlib=$current_dir/zlib-1.2.8
    make -j${corenum}
    sudo make install

case $DISTRO in
    Fedora)
        ;;
    OpenSuse)
        ;;
    Ubuntu)
        ;;
    Debian)
    ;;
    esac

    echo "nginx make install finished"
    exit 0
fi

