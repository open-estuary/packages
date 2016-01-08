#!/bin/bash
#author: Justin Zhao
#date: 31/10/2015
#author: Shiju Jose
#date: 05/11/2015
#description: Armor build & install script
#$1: target platform name
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables ####################
###################################################################################

echo "/packages/armor/build.sh: Platform=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./armor/build.sh <platform> <distrib> <rootfs> <kernal>" 
    exit
fi

PLATFORM=$1
DISTRO=$2
ROOTFS=$3

armor_dir=armor
build_dir=`pwd`/build/$PLATFORM
armor_build_dir=$build_dir/$armor_dir
kernel_build_dir=$4

#echo "build_dir=$build_dir"
#echo "armor_build_dir=$armor_build_dir"

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
############################# Build Armor Tools #############################
###################################################################################
echo "Building and install Armor Tools ..."
# copy armor folder to the build directory 
rm -rf $armor_build_dir
cp -rf $armor_dir $build_dir

# create armor folders in the rootfs    
sudo mkdir $ROOTFS/usr/local/armor/
sudo mkdir $ROOTFS/usr/local/armor/binary
sudo mkdir $ROOTFS/usr/local/armor/source
sudo mkdir $ROOTFS/usr/local/armor/test_scripts
sudo mkdir $ROOTFS/usr/local/armor/build_scripts

pushd $armor_build_dir

cd testing/build_scripts/

# build demidecode
sh build_dmidecode.sh $cross_gcc $ROOTFS

# build kprobes test code
sh build_kprobes_test.sh $kernel_build_dir

# build lttng kernel module and lttng uspace test code
sh build_lttng.sh $kernel_build_dir $ROOTFS

# build  ktap code
sh build_ktap.sh $kernel_build_dir $ROOTFS

# build gprof test code
sh build_gprof_test.sh $ROOTFS

# build valgrind test code
sh build_valgrind_test.sh $ROOTFS

cd -
popd

###################################################################################
############################# Install Armor Tools #############################
###################################################################################

echo "Installing package armor ..."

# copy prebuilt binaries to the rootfs
case $DISTRO in
    Fedora)
        sudo cp $armor_dir/testing/binary/*.rpm   $ROOTFS/usr/local/armor/binary
        ;;
    OpenSuse)
        sudo cp $armor_dir/testing/binary/*.rpm   $ROOTFS/usr/local/armor/binary
        # copy build scripts to the rootfs
        sudo cp $armor_dir/testing/build_scripts/build_lttng_tools_opensuse.sh   $ROOTFS/usr/local/armor/build_scripts/
        ;;
    Debian | Ubuntu)
        sudo cp $armor_dir/testing/binary/*.deb   $ROOTFS/usr/local/armor/binary
    ;;
    esac
sudo cp $armor_dir/testing/binary/crash   $ROOTFS/usr/local/armor/binary

#copy armor test scripts and prebuilt test executables to rootfs
sudo cp -rf $armor_dir/testing/test_scripts   $ROOTFS/usr/local/armor/

# copy demidecode to rootfs
sudo cp $armor_build_dir/testing/source/dmidecode/dmidecode $ROOTFS/usr/local/armor/binary

# copy kprobes test binaries to rootfs
sudo cp $armor_build_dir/testing/source/test_code/kprobes_test_code/kprobe_test  $ROOTFS/usr/local/armor/test_scripts
sudo cp $armor_build_dir/testing/source/test_code/kprobes_test_code/kprobe_test.ko  $ROOTFS/usr/local/armor/test_scripts

exit 0
