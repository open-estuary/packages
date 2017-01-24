#!/bin/bash

#################################################################################
# Comment: Estuary Application Setup.sh Template
# Author: Huang Jinhua
# Date : 2017/01/22
#################################################################################


INSTALLDIR=$(cd $1; pwd)

#
# In addition, use ${INSTALL_DIR}/bin, ${INSTALL_DIR}/libs, and 
# ${INSTALL_DIR}/include to install packages 
# 
# However if it needs to install two or more version of the same package
# it must be installed into ${INSTALL_DIR}/${CUR_PKG}/{bin,include,libs} accordingly 
# In addition, it should create symbol links from ${INSTALL_DIR}/{bin,libs,include} to
# ${INSTALL_DIR}/${CUR_PKG}/{bin,libs,include}
#

#
# Return:
# 1) "exit 0" : return 'INSTALL_SUCCESS' if current setup is successful 
#               and it is NOT necessary to run this script again
# 2) "exit 2" : return 'INSTALL_ALWAYS' if current setup is successful 
#               but it still need to run this script during next boot up stage
# 3) "exit 1" : return INSTALL_FAILURE if any failure occurs 
#               so it will try to run this script later

exit 0

