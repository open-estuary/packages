#!/bin/sh

export IP=10.71.201.240
export PORT=22

./plink root@$IP -P $PORT -pw r mkdir /usr/lib64
./plink root@$IP -P $PORT -pw r mkdir /usr/lib64/odp

scp -P $PORT ./ko/*.ko       root@$IP:/home/root
scp -P $PORT ./app/*         root@$IP:/home/root
scp -P $PORT ./test/*.sh      root@$IP:/home/root
scp -P $PORT ./user_drv/*.so root@$IP:/usr/lib64/odp
scp -P $PORT ./bin/*.so      root@$IP:/usr/lib



