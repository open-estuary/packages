#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start redis docker 

#Default mode, it will map the host 6379 to docker container port 6379
docker run -p 6379:6379 --name redis-4.0.2 -d openestuary/redis:4.0.2

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run -P --name redis -d openestuary/redis:3.2.4
#docker port redis
#

#
#Mode 2: Use host's directory, whichi is speicifed by "/host/datadir", to store data
#docker run -p 6379:6379 -v /host/datadir:/usr/local/redis/db -d openestuary/redis:3.2.4
#

#
#Mode 3: Use host's configurations which is specified by "/host/configdir/redis.conf"
#docker run -p 6379:6379 -v /host/configdir:/usr/local/redis/config -d openestuary/redis:3.2.4
#
