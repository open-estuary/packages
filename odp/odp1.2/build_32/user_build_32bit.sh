#!/bin/sh

export BUILD_PATH=$(cd "$(dirname "$0")"; pwd)
export ROOT_DIR=$BUILD_PATH/..
echo $ROOT_DIR


$ROOT_DIR/user_drv/uio_enet/build_32bit.sh
$ROOT_DIR/user_drv/sec/build_32bit.sh
#$ROOT_DIR/user_drv/mlx4/build.sh

