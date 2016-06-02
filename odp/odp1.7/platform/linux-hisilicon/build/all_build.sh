#!/bin/sh

export AARCH64_CC=aarch64-linux-gnu-gcc
export AARCH64_LD=aarch64-linux-gnu-ld
export AARCH64_AR=aarch64-linux-gnu-ar
export AARCH64_OBJDUMP=aarch64-linux-gnu-objdump

export AARCH32_CC=arm-linux-gnueabi-gcc
export AARCH32_LD=arm-linux-gnueabi-ld
export AARCH32_AR=arm-linux-gnueabi-ar
export AARCH32_OBJDUMP=arm-linux-gnueabi-objdump

./scripts/odp_build.sh
./scripts/app_build.sh
./scripts/drv_build.sh
#./scripts/test_build.sh
#./copy.sh

