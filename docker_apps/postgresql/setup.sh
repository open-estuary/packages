#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017

echo "Try to install PostgreSQL docker image ..."

#Step 1: Install docker firstly
if [ -z "$(which docker 2>/dev/null)" ]; then
    INSTALL_CMD=""
    if [ ! -z "$(which yum 2>/dev/null)" ] ; then
        INSTALL_CMD="yum"
    elif [ ! -z "$(which apt-get 2>/dev/null)" ] ; then
        INSTALL_CMD="apt-get"
    else 
        echo "Not know how to install docker ..."
        exit 0
    fi
    ${INSTALL_CMD} install -y -q docker docker.io
fi

#Step 2: Start docker service
if [ -z "$(ps -aux | grep docker | grep -v grep)" ] ; then
    service docker start
fi

#Step 3: Try to pull 9.6 postgresql docker image
if [ -z "$(docker images | grep 'openestuary/postgresql' | grep "9.6" | grep -v grep)" ]  ; then
    docker pull openestuary/postgresql:9.6
else 
    echo "PostgreSQL-9.6 docker image has been pulled"
fi

