#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start mariadb docker 

#Default mode, it will map the host 3306 to docker container port 3306
docker run -p 3306:3306 --name mariadb -d openestuary/mariadb:latest

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run -P --name mariadb -d openestuary/mariadb:latest
#docker port mariadb
#

#
#Mode 2: Use host's directory, whichi is speicifed by "/host/datadir", to store data
#docker run -p 3306:3306 -v /host/datadir:/u01/mariadb/data -d openestuary/mariadb:latest
#

#
#Mode 3: Use host's configurations which is specified by "/host/configdir/my.conf"
#docker run -p 3306:3306 -v /host/configdir/my.conf:/usr/local/mariadb/config -d openestuary/mariadb:latest
#
