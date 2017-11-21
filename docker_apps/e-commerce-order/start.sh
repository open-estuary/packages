#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start e-commerce-order docker 

#Default mode, it will map the host 8000 to docker container port 8000
docker run -p 8000:8000 --name e-commerce-order -d openestuary/e-commerce-order:5.0

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run -P --name e-commerce-order -d openestuary/e-commerce-order:5.0
#docker port e-commerce-order
#

#
#Mode 2: Server application.yml directory, whichi is speicifed by "/etc/e-commerce/order"
#docker run -p 8000:8000 -v /etc/e-commerce/order:/etc/e-commerce/order -d openestuary/e-commerce-order:5.0
#
