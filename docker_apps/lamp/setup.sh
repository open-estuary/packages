#!/bin/bash

#Step 1: Start docker service
if [ -z "$(ps -aux | grep docker | grep -v grep)" ] ; then
        service docker start
fi

#Step 2: Download docker images
docker pull openestuary/apache
docker pull openestuary/mysql

