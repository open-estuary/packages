#!/bin/sh

export IP=10.71.201.244
export PORT=23

./plink root@$IP -P $PORT -pw huawei mkdir /usr/lib64
./plink root@$IP -P $PORT -pw huawei mkdir /usr/lib64/odp

scp -P $PORT ./objs/ko/*.ko           				 root@$IP:/home/root
scp -P $PORT ./objs/examples/*        				 root@$IP:/home/root
scp -P $PORT ./objs/drv/*.so          				 root@$IP:/usr/lib64/odp
scp -P $PORT ./objs/lib/*.so           				 root@$IP:/usr/lib
scp -P $PORT ./scripts/odp_nic_binding.sh            root@$IP:/home/root

./plink root@$IP -P $PORT -pw huawei mkdir /home/test
scp -P $PORT ./objs/test/validation/*           root@$IP:/home/test
scp -P $PORT ./objs/test/run_all_validations.sh root@$IP:/home/test

