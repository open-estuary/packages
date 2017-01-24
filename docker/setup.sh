#!/bin/bash
#author: Shameer
#date: 21/01/2016
#description: Docker install script
#$1: install directory 

###################################################################################
###################### Initialise variables ####################
###################################################################################

echo "Installing docker package..."
INSTALL_DIR=$1
CUR_DIR=$(cd `dirname $0`; pwd)

###################################################################################
############################# Install Docker #############################
###################################################################################
if [ ! -z "$(uname -a | grep "ebian")" ] ; then 
    tar xzvf ${CUR_DIR}/docker*.gz -C ${INSTALL_DIR}

    if [ -z "$(grep /usr/lib/docker /etc/etc/ld.so.conf.d/estuaryapps.conf 2>/dev/null)" ] ; then 
        echo "${INSTALL_DIR}/usr/lib/docker" >> /etc/ld.so.conf.d/estuaryapps.conf
    fi
fi

#On other platforms they could be installed via apt-get/yum directly

exit 0
