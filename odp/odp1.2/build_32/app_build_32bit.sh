#!/bin/sh

export BUILD_PATH=$(cd "$(dirname "$0")"; pwd)
export ROOT_DIR=$BUILD_PATH/..

$ROOT_DIR/example/classifier/build_32bit.sh 
$ROOT_DIR/example/generator/build_32bit.sh
$ROOT_DIR/example/icmp_reply/build.sh
$ROOT_DIR/example/ipsec/build_32bit.sh
$ROOT_DIR/example/l2fwd/build_32bit.sh
$ROOT_DIR/example/loop/build_32bit.sh
$ROOT_DIR/example/packet/build_32bit.sh
$ROOT_DIR/example/sec/build_32bit.sh
$ROOT_DIR/example/timer/build_32bit.sh


