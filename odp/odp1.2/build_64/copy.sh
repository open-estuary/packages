#!/bin/sh

export IP=192.168.1.227
#export PORT=22

#./plink root@$IP -P $PORT -pw huawei mkdir /usr/lib64
#./plink root@$IP -P $PORT -pw huawei mkdir /usr/lib64/odp

#scp -P $PORT ./ko/*.ko       root@$IP:/home/root
#scp -P $PORT ./app/*         root@$IP:/home/root
#scp -P $PORT ./user_drv/*.so root@$IP:/usr/lib64/odp
#scp -P $PORT ./bin/*.so      root@$IP:/usr/lib

#./plink $IP mkdir /usr/lib64
#./plink $IP mkdir /usr/lib64/odp

cp ./ko/*.ko		/home/yangfajun/ftp/ubuntu/home/root
cp ./app/*		/home/yangfajun/ftp/ubuntu/home/root
cp ./user_drv/*.so	/home/yangfajun/ftp/ubuntu/home/root
cp ./bin/*.so		/home/yangfajun/ftp/ubuntu/home/root

