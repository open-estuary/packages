#!/bin/bash
#author: WuYanjun
#date: 04/02/2017


###################################################################################
###################### Initialise variables ####################
###################################################################################

echo "Initializing the openstack..."
INSTALL_DIR=$1
CUR_DIR=$(cd `dirname $0`; pwd)

tar xf ${CUR_DIR}/openstack.tar,gz -C ${INSTALL_DIR}

exit 0
