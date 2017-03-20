#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start mongodb docker 

#Default mode, it will map the host 27017 to docker container port 27017
# Warning: Please note that it uses 'numactl' to bring up mongod in docker container. 
#          However the corresponding syscalls (such as set_mempolicy) is prohibited in the docker container by default.
#          Therefore it is required to use "--security-opt" to override the default profile, 
#          or to disable default profile via "--security-opt seccomp=unconfined" 
#
docker run --security-opt seccomp=unconfined -p 27017:27017 --name mongodb-3.4 -d openestuary/mongodb:3.4

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run --security-opt seccomp=unconfined  -P --name mongodb -d openestuary/mongodb:3.4
#docker port mongodb
#

#
#Mode 2: Use host's directory, whichi is speicifed by "/host/datadir", to store data
#docker run --security-opt seccomp=unconfined -p 27017:27017 -v /host/datadir:/usr/local/mongodb/db -d openestuary/mongodb:3.4
#

#
#Mode 3: Use host's configurations which is specified by "/host/configdir/mongodb.conf"
#docker run --security-opt seccomp=unconfined -p 27017:27017 -v /host/configdir:/usr/local/mongodb/config/ -d openestuary/mongodb:3.4
#
