#!/bin/bash
#author: Wang Yu
#date: 10/11/2017
#description: To remove docker container and images

#Step 1: Make sure docker has started 
if [ -z "$(ps -aux | grep docker | grep -v grep)" ] ; then
    echo "Docker is not running, so not necessary to remove docker images"
    exit 0
fi

#Step 2: Delete mysql container and image
docker rm twemproxy
docker rmi openestuary/twemproxy
