#!/bin/sh

export BUILD_PATH=$(cd "$(dirname "$0")"; pwd)
export ROOT_DIR=$BUILD_PATH/..


$ROOT_DIR/example/classifier/build.sh
$ROOT_DIR/example/generator/build.sh
$ROOT_DIR/example/icmp_reply/build.sh
$ROOT_DIR/example/ipsec/build.sh
$ROOT_DIR/example/l2fwd/build.sh
$ROOT_DIR/example/loop/build.sh
$ROOT_DIR/example/packet/build.sh
$ROOT_DIR/example/sec/build.sh
$ROOT_DIR/example/timer/build.sh


