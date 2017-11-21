#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start zuul docker 

#Default mode, it will map the host 8765 to docker container port 8765
docker run -p 8765:8765 --name zuul -d openestuary/zuul:5.0

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run -P --name zuul -d openestuary/zuul:5.0
#docker port zuul
#

#
#Mode 2: Server application.yml directory, whichi is speicifed by "/etc/micro-services/api-gateway"
#docker run -p 8765:8765 -v /etc/micro-services/api-gateway:/etc/micro-services/api-gateway -d openestuary/zuul:5.0
#
