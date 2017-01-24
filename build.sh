#!/bin/bash

#################################################################################
# Comment: This script is to build Estuary packages 
# Author: Huang Jinhua
# Date : 2017/01/22
#################################################################################

###################################################################################
# build_packages_usage
###################################################################################
build_packages_usage()
{
cat << EOF
Usage: ./packages/build.sh  {packagenamelist | --distro=xxx --rootfs=xxx ...}
    --output: build output directory
    --distro: distro that the packages will be built for
    --rootfs: target rootfs which the package will be installed into
    --kernel: kernel output directory
    --file  : estuary configuration json file
    --cross : prefix of cross-compiler tools 
    --debug : print debug information

Example:
    Case 1: ./packages/build.sh mysql
    Case 2: ./packages/build.sh mysql,sysbench
    Case 3: ./packages/build.sh mysql --debug=on
    Case 4: ./packages/build.sh --file=./estuary/estuarycfg.json 
                                --builddir=./workspace
                                --debug=on
    Case 5: ./packages/build.sh --file=./estuary/estuarycfg.json 
                                --builddir=./workspace 
                                --distro=CentOS 
                                --rootfs=./workspace/distro/CentOS 
                                --kernel=./workspace/kernel 
                                --cross=aarch64-linux-gnu- 
EOF
}

#################################################################################
# Includes
#################################################################################
if [ -z "${TOPDIR}" ] ; then
    if [ ! -d "./estuary" ] ; then
        echo "Please execute this script under estuary project root directory"
        build_packages_usage
        exit 1
    fi

    TOPDIR=$(cd ./estuary; pwd)
fi

export PATH=$TOPDIR:$TOPDIR/include:$TOPDIR/submodules:$TOPDIR/deploy:$PATH
. ${TOPDIR}/Include.sh


#################################################################################
# Define necessary variables
#################################################################################
PACKAGE_DIR=$(cd ./packages; pwd)
DISTRO="CentOS"
ROOTFS=
KERNEL_DIR=
CROSS="aarch64-linux-gnu-"
CFG_FILE=""
DEBUG_ON=0

###################################################################################
# Get args
###################################################################################
PACKAGES=${1}
while test $# != 0
do
    case $1 in
        --*=*) ac_option=`expr "X$1" : 'X\([^=]*\)='` ; ac_optarg=`expr "X$1" : 'X[^=]*=\(.*\)'` ;;
        -*) ac_option=$1 ; ac_optarg=$2; ac_shift=shift ;;
        *) ac_option=$1 ;;
    esac
    case $ac_option in
        -o | --output) BUILDDIR=$ac_optarg ;;
        -b | --builddir) BUILDDIR=$ac_optarg ;;
        -d | --distro) DISTRO=$ac_optarg ;;
        -r | --rootfs) ROOTFS=$ac_optarg ;;
        -k | --kernel) KERNEL_DIR=$ac_optarg ;;
        -c | --cross) CROSS=$ac_optarg ;;
        -f | --file) CFG_FILE=$ac_optarg ;;
        --debug) DEBUG_ON=1 ;;
        --help) build_packages_usage; exit 1 ;;
    esac
    
    shift
done

BUILDDIR=${BUILDDIR:-build}  

if [ -z "${DISTRO}" ] ; then
    DISTRO="CentOS"
fi  
           
if [ -z "${ROOTFS}" ] ; then
    ROOTFS="${BUILDDIR}/distro/${DISTRO}"
fi
            
if [ -z "${KERNEL_DIR}" ] ; then
    KERNEL_DIR="${BUILDDIR}/kernel"
fi  

if [ "$(uname -m)" == "aarch64" ] ; then
    CROSS=""
else
    estuary_version=`get_estuary_version ./estuary`
    ESTUARY_FTP_CFGFILE="${estuary_version}.xml"
    
    if [ -f "${ESTUARY_FTP_CFGFILE}" ] ; then
        toolchain=`get_toolchain $ESTUARY_FTP_CFGFILE aarch64`
        toolchain_dir=`get_compress_file_prefix $toolchain`
        TOOLCHAIN_DIR=`cd toolchain/$toolchain_dir; pwd`
        CROSS=`get_cross_compile "x86_64" $TOOLCHAIN_DIR`
        if [ -z "$(echo $PATH | grep $TOOLCHAIN_DIR/bin 2>/dev/null)" ] ; then
            export PATH=$TOOLCHAIN_DIR/bin:$PATH
        fi
    else
        CROSS="aarch64-linux-gnu-"
        export PATH=`pwd`/toolchain/gcc-linaro-aarch64-linux-gnu-4.9-2014.09_linux/bin:$PATH
    fi
fi

if [ ! -d "${PACKAGE_DIR}" ] ; then
    echo "Please build packages under estuary root directory"
    build_package_usage 
    exit 1
fi

if [ ! -d ${ROOTFS} ] ; then
    echo "Please use ./estuary/build.sh to build the whole project firstly!"
    exit 0
fi


DEBUG_ARG=""
if [ ${DEBUG_ON} -eq 1 ] ; then
    DEBUG_ARG="--debug=on"
fi

CROSS=""

#If the json file is not specified, then just build and install packages 
#based on the first argument 
if [ -z "${CFG_FILE}" ] ; then
    if [ -z "${PACKAGES}" ] ; then
        build_packages_usage
        exit 1
    fi

    ${TOPDIR}/submodules/build-packages.sh --spec_packages=${PACKAGES} \
                                       --output=${BUILDDIR} \
                                       --cross=${CROSS} \
                                       --distro=${DISTRO} \
                                       --rootfs=${ROOTFS} \
                                       --kernel=${KERNEL_DIR} \
                                       --file=${CFG_FILE} \
                                       --cross=${CROSS} \
                                       ${DEBUG_ARG} 
                                      
else 
    ${TOPDIR}/submodules/build-packages.sh --output=${BUILDDIR} \
                                       --cross=${CROSS} \
                                       --distro=${DISTRO} \
                                       --rootfs=${ROOTFS} \
                                       --kernel=${KERNEL_DIR} \
                                       --file=${CFG_FILE} \
                                       --cross=${CROSS} \
                                       ${DEBUG_ARG}
fi

