#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start zipkin-server docker 

#Default mode, it will map the host 9411 to docker container port 9411
docker run -p 9411:9411 --name zipkin-server -d openestuary/zipkin-server:5.0

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run -P --name zipkin-server -d openestuary/zipkin-server:5.0
#docker port zipkin-server
#

#
#Mode 2: Server application.yml directory, whichi is speicifed by "/etc/micro-services/zipkin"
#docker run -p 9411:9411 -v /etc/micro-services/zipkin:/etc/micro-services/zipkin -d openestuary/zipkin-server:5.0
#
