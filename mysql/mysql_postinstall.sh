#!/bin/bash
#author: Huang Jinhua
#date: 01/05/2017
#description: postinstall scripts for mysql

Distribution=`sed -n 1p /etc/issue| cut -d' ' -f 1`
# Temp fix for OpenSuse distribution as the format of /etc/issue in OpenSuse is different
Distribution1=`sed -n 1p /etc/issue| cut -d' ' -f 3`
if [ "$Distribution1" = 'openSUSE' ]; then
    Distribution=`sed -n 1p /etc/issue| cut -d' ' -f 3`
fi

echo "Installing Mysql on $Distribution..."

#Step 1: Install docker firstly
if [ -z "$(which docker > /dev/null)" ]; then
    INSTALL_CMD=""
    if [ ! -z "$(which yum > /dev/null)" ] ; then
        INSTALL_CMD="yum"
    elif [ ! -z "$(which apt-get > /dev/null)" ] ; then
        INSTALL_CMD="apt-get"
    else 
        echo "Not know how to install docker ..."
        exit 0
    fi
    ${INSTALL_CMD} install -y -q docker
fi

#Step 2: Start docker service
if [ -z "$(ps -aux | grep docker | grep -v grep)" ] ; then
    service docker start
fi

#Step 3: Try to pull latest mysql docker image
if [ -z "$(docker images | grep openestuary/mysql | grep -v grep)"] ; then
    docker pull openestuary/mysql
fi

