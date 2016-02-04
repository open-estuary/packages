#!/bin/sh

export BUILD_PATH=$(cd "$(dirname "$0")"; pwd)
export ROOT_DIR=$BUILD_PATH/..

export LINUX_ENDNESS=LITTLE

export B=P660
export ENV=CHIP
export HRD_OS=LINUX
export HRD_ENDNESS=$LINUX_ENDNESS
export HRD_ARCH=HRD_ARM64





