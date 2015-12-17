#!/bin/sh

export BUILD_PATH=$(cd "$(dirname "$0")"; pwd)
export ROOT_DIR=$BUILD_PATH/..

export LINUX_ENDNESS=LITTLE

export B=P660
export ENV=CHIP
export HRD_OS=LINUX
export HRD_ENDNESS=$LINUX_ENDNESS
export HRD_ARCH=HRD_ARM64



#$ROOT_DIR/user_drv/uio_enet/build.sh
#$ROOT_DIR/user_drv/sec/build.sh
#$ROOT_DIR/user_drv/mlx4/build.sh


