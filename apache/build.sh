#!/bin/bash
#author: Bowen Liu
#date: 18/02/2016

#description: Apache build & install script
#$1: target platform name
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables ####################
###################################################################################

echo "/packages/apache/build.sh: Platform=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./apache/build.sh <platform> <distrib> <rootfs> <kernal>" 
    exit
fi

ROOTFS=$3
corenum=`cat /proc/cpuinfo | grep "processor"| wc -l`
current_dir=`pwd`/packages/apache
echo "---- $current_dir ----"

###################################################################################
################################### Build Apache ###################################
###################################################################################
LOCALARCH=`uname -m`
if [ x"$LOCALARCH" = x"x86_64" ]; then
    exit 1
fi

if [ x"$LOCALARCH" = x"aarch64" ]; then
    echo "Installing apache ..."
    sudo apt-get install g++ <<EOF
Y
EOF
    cd $current_dir/apr-1.5.2
    ./configure --prefix=$ROOTFS/usr/local/apr
    make -j${corenum}
    sudo make install
    echo "apr install finished"
    
    cd $current_dir/apr-util-1.5.4
    ./configure --prefix=$ROOTFS/usr/local/apr-util -with-apr=$ROOTFS/usr/local/apr/bin/apr-1-config
    make -j${corenum}
    sudo make install
    echo "apr-util install finished"
    
    pcre_path="$ROOTFS/usr/local/pcre" 
    if [ ! -d "$pcre_path" ]; then
    	cd $current_dir/pcre-8.37
    	./configure --prefix=$ROOTFS/usr/local/pcre
    	make -j${corenum}
   	    sudo make install
        echo "pcre install finished"
    fi
    
    cd $current_dir/httpd-2.2.29
    ./configure --prefix=$ROOTFS/usr/local/apache2 --with-apr=$ROOTFS/usr/local/apr --with-apr-util=$ROOTFS/usr/local/apr-util --with-pcre=$ROOTFS/usr/local/pcre -with-mpm=worker
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

    echo "apache make install finished"
    exit 0
fi

