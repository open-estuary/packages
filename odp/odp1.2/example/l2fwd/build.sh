#!/bin/bash

export BUILD_PATH=$(cd "$(dirname "$0")"; pwd)
export ROOT_DIR=$BUILD_PATH

export BUILD_DIR="build_64"


make -s -f $ROOT_DIR/Makefile clean

make -s -f $ROOT_DIR/Makefile  -j16

make -s -f $ROOT_DIR/Makefile clean
