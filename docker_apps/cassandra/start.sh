#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start cassandra docker 

#Default mode, it will map the host 9042 to docker container port 9042
# Warning: Please note that it uses 'numactl' to bring up mongod in docker container. 
#          However the corresponding syscalls (such as set_mempolicy) is prohibited in the docker container by default.
#          Therefore it is required to use "--security-opt" to override the default profile, 
#          or to disable default profile via "--security-opt seccomp=unconfined" 
#
docker run --security-opt seccomp=unconfined -p 9042:9042 --name cassandra-3.10 -d openestuary/cassandra:3.10

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run --security-opt seccomp=unconfined  -P --name cassandra -d openestuary/cassandra:3.10
#docker port cassandra
#

#
#Mode 2: Use host's directory, whichi is speicifed by "/host/datadir", to store data
#docker run --security-opt seccomp=unconfined -p 9042:9042 -v /host/datadir:/u01/cassandra -d openestuary/cassandra:3.10
#

#
#Mode 3: Use host's configurations which is specified by "/host/configdir/cassandra.conf"
#docker run --security-opt seccomp=unconfined -p 9042:9042 -v /host/configdir:/etc/cassandra -d openestuary/cassandra:3.10
#
