#!/bin/sh

export ABS_GCC_DIR="/opt/install"

export BUILD_PATH=$(cd "$(dirname "$0")"; pwd)
export ROOT_DIR=$BUILD_PATH/..

export OBJ_SO=libodp32.so
export OBJ_A=libodp32.a
export ODP_OBJ_FILENAME=odp.o
export HISI_OBJ_FILENAME=hisi.o

export GNU_PREFIX=arm-linux-gnueabi-
export CFLAGS="-O3 -c -pipe -march=armv7-a -Wall -Wunused -MD -MP -fPIC -fno-common -freg-struct-return -mfpu=vfp -mfloat-abi=softfp -fno-builtin -mno-sched-prolog -mlittle-endian -D__arm32__"


cp Makefile_odp $ROOT_DIR/platform/linux-generic
cd $ROOT_DIR/platform/linux-generic

make -s -f Makefile_odp clean
make -s -f Makefile_odp  -j16
cp $ODP_OBJ_FILENAME $BUILD_PATH/
make -s -f Makefile_odp clean
cd $BUILD_PATH

cp Makefile_hisilicon $ROOT_DIR/platform/linux-hisilicon
cd $ROOT_DIR/platform/linux-hisilicon

make -s -f Makefile_hisilicon clean
make -s -f Makefile_hisilicon  -j16
cp $HISI_OBJ_FILENAME $BUILD_PATH/
make -s -f Makefile_hisilicon clean
cd $BUILD_PATH

make -s -f Makefile clean
make -s -f Makefile $OBJ_A  -j16
make -s -f Makefile $OBJ_SO -j16

cp $OBJ_A $BUILD_PATH/bin/
cp $OBJ_SO $BUILD_PATH/bin/

rm *.o
rm *.a
rm *.so

