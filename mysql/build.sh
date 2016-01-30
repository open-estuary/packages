#!/bin/bash
#author: Bowen Liu
#date: 30/01/2016

#description: MySQL build & install script
#$1: target platform name
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables ####################
###################################################################################

echo "/packagesmysql/build.sh: Platform=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./mysql/build.sh <platform> <distrib> <rootfs> <kernal>" 
    exit
fi

PLATFORM=$1
DISTRO=$2
ROOTFS=$3

mysql_dir=mysql
build_dir=`pwd`/build/$PLATFORM
mysql_build_dir=$build_dir/$mysql_dir
kernel_build_dir=$4


###################################################################################
############################# Setup host environmenta #############################
###################################################################################
# Detect and dertermine some environment variables
LOCALARCH=`uname -m`
if [ x"$PLATFORM" = x"D01" ]; then
    TARGETARCH="ARM32"
else
    TARGETARCH="ARM64"
fi

if [ x"$TARGETARCH" = x"ARM32" ]; then
    cross_gcc=arm-linux-gnueabihf-gcc
    cross_prefix=arm-linux-gnueabihf
else
    cross_gcc=aarch64-linux-gnu-gcc
    cross_prefix=aarch64-linux-gnu
fi

###################################################################################
################################### Build MySQL ###################################
###################################################################################
echo "Installing mysql ..."
cd percona-5.6.22-72.0
cd BUILD
sh autorun.sh
cd ..
cmake -DCMAKE_INSTALL_PREFIX=$DIR/u01/my3306 -DMYSQL_DATADIR=$DIR/u01/my3306/data -DMYSQL_USER=mysql -DSYSCONFDIR=$DIR/etc -DWITH_MYISAM_STORAGE_ENGINE=1 -DWITH_INNOBASE_STORAGE_ENGINE=1 -DWITH_MEMORY_STORAGE_ENGINE=1 -DMYSQL_UNIX_ADDR=$DIR/u01/my3306/run/mysql.sock -DMYSQL_TCP_PORT=3306 -DENABLED_LOCAL_INFILE=1 -DWITH_PARTITION_STORAGE_ENGINE=1 -DEXTRA_CHARSETS=all -DDEFAULT_CHARSET=utf8 -DDEFAULT_COLLATION=utf8_general_ci
make -j16
make DESTDIR=$DIR install
mkdir $DIR/etc
cp my-sigle.cnf  $DIR/etc/my.cnf
tar cjvf mysql.tar.bz2 $DIR/*

exit 0

