#!/bin/bash
#author: WuYanjun
#date: 04/02/2017
#description: OpenStack install script
#$1: target output directory
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

###################################################################################
###################### Initialise variables ####################
###################################################################################

openstack_dir=`pwd`/packages/openstack

BUILDDIR=$(cd $1; pwd)
DISTRO=$2
ROOTFS=$(cd $3; pwd)
KERNEL_DIR=$(cd $4; pwd)
CROSS=$5  # such as aarch64-linux-gnu- on X86 platform or "" on ARM64 platform   
PACK_TYPE=$6 # such as "tar", "rpm", "deb" or "all"
PACK_SAVE_DIR=$(cd $7; pwd) #
INSTALL_DIR=$(cd $8; pwd)

###################################################################################
############################# Install OpenStack #############################
###################################################################################
tmp_dir=tar_$(date +%y%m%d%H%M%S)
if [ ! -d ${BUILDDIR}/$tmp_dir ] ; then
    sudo mkdir ${BUILDDIR}/$tmp_dir
fi

pushd ${BUILDDIR}/$tmp_dir
sudo cp ${openstack_dir}/setup.sh ./
sudo cp ${openstack_dir}/remove.sh ./
sudo cp ${openstack_dir}/common.sh ./
sudo cp ${openstack_dir}/openstack_cfg.sh ./
git clone  https://git.linaro.org/leg/sdi/openstack-ref-architecture.git

sudo tar -zcf ${BUILDDIR}/openstack.tar.gz setup.sh remove.sh common.sh \
    openstack_cfg.sh openstack_ref_architecture
popd > /dev/null

sudo cp ${BUILDDIR}/openstack.tar.gz ${PACK_SAVE_DIR}/

sudo rm ${BUILDDIR}/$tmp_dir
exit 0
