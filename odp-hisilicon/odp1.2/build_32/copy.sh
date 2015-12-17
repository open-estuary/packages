#!/bin/sh

export IP=10.71.201.239
export PORT=22


scp -P $PORT ./ko/*.ko       root@$IP:/home/root
scp -P $PORT ./app/*         root@$IP:/home/root
scp -P $PORT ./user_drv/*.so root@$IP:/usr/lib/odp
scp -P $PORT ./bin/*.so      root@$IP:/usr/lib
