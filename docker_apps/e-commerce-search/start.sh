#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start e-commerce-search docker 

#Default mode, it will map the host 8002 to docker container port 8002
docker run -p 8002:8002 --name e-commerce-search -d openestuary/e-commerce-search:5.0

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run -P --name e-commerce-search -d openestuary/e-commerce-search:5.0
#docker port e-commerce-search
#

#
#Mode 2: Server application.yml directory, whichi is speicifed by "/etc/e-commerce/search"
#docker run -p 8002:8002 -v /etc/e-commerce/search:/etc/e-commerce/search -d openestuary/e-commerce-search:5.0
#
