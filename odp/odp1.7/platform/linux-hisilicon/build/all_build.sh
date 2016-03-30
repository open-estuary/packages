#!/bin/sh

./scripts/odp_build.sh
./scripts/app_build.sh
./scripts/drv_build.sh
#./scripts/test_build.sh
./copy.sh

