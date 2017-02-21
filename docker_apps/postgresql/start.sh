#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start postgresql docker 

#Default mode, it will map the host 5432 to docker container port 5432
docker run -p 5432:5432 --name postgresql-9.6 -d openestuary/postgresql:9.6

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run -P --name postgresql -d openestuary/postgresql:9.6
#docker port postgresql
#

#
#Mode 2: Use host's directory, whichi is speicifed by "/host/datadir", to store data
#docker run -p 5432:5432 -v /host/datadir:/usr/local/postgresql/db -d openestuary/postgresql:9.6
#

#
#Mode 3: Use host's configurations which is specified by "/host/configdir/postgresql.conf"
#docker run -p 5432:5432 -v /host/configdir:/usr/local/postgresql/config/ -d openestuary/postgresql:9.6
#
