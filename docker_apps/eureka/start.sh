#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start eureka docker 

#Default mode, it will map the host 8761 to docker container port 8761
docker run -p 8761:8761 --name eureka -d openestuary/eureka:5.0

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run -P --name eureka -d openestuary/eureka:5.0
#docker port eureka
#

#
#Mode 2: Server application.yml directory, whichi is speicifed by "/etc/micro-services/discovery"
#docker run -p 8761:8761 -v /etc/micro-services/discovery:/etc/micro-services/discovery -d openestuary/eureka:5.0
#
