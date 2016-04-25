#!/bin/sh

export IP=10.71.201.243
export PORT=22

./plink root@$IP -P $PORT -pw huawei mkdir /usr/lib64
./plink root@$IP -P $PORT -pw huawei mkdir /usr/lib64/odp

scp -P $PORT ./objs/ko/*.ko            root@$IP:/home/root
scp -P $PORT ./objs/examples/*         root@$IP:/home/root
scp -P $PORT ./objs/drv/*.so           root@$IP:/usr/lib64/odp
scp -P $PORT ./objs/lib/*.so           root@$IP:/usr/lib

#./plink root@$IP -P $PORT -pw huawei mkdir /home/test
#scp -P $PORT ./objs/test/validation/*           root@$IP:/home/test
#scp -P $PORT ./objs/test/run_all_validations.sh root@$IP:/home/test

