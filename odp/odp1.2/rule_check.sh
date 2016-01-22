#!/bin/sh
./scripts/checkpatch.pl -f ./user_drv/sec/*
./scripts/checkpatch.pl -f ./kernel_drv/sec/*
./scripts/checkpatch.pl -f ./user_drv/uio_enet/*
./scripts/checkpatch.pl -f ./kernel_drv/uio/*
./scripts/checkpatch.pl -f ./kernel_drv/kni/*
