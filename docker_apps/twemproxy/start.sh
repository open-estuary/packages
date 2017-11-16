#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start mysql docker 

#Default mode, it will map the host 3306 to docker container port 3306
docker run -p 3306:3306 --name twemproxy -d openestuary/twemproxy:0.4.1

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run -P --name mysql -d openestuary/mysql:latest
#docker port mysql
#

#
#Mode 2: Use host's directory, whichi is speicifed by "/host/datadir", to store data
#docker run -p 3306:3306 -v /host/datadir:/u01/my3306/data -d openestuary/mysql:latest
#

#
#Mode 3: Use host's configurations which is specified by "/host/configdir/my.conf"
#docker run -p 3306:3306 -v /host/configdir/my.conf:/usr/local/mysql/config -d openestuary/mysql:latest
#
