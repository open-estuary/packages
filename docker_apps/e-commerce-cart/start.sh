#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: To start e-commerce-cart docker 

#Default mode, it will map the host 8001 to docker container port 8001
docker run -p 8001:8001 --name e-commerce-cart -d openestuary/e-commerce-cart:5.0

#
#Mode 1: Use "-P" to choose host port dynamically 
#docker run -P --name e-commerce-cart -d openestuary/e-commerce-cart:5.0
#docker port e-commerce-cart
#

#
#Mode 2: Server application.yml directory, whichi is speicifed by "/etc/e-commerce/cart"
#docker run -p 8001:8001 -v /etc/e-commerce/cart:/etc/e-commerce/cart -d openestuary/e-commerce-cart:5.0
#
