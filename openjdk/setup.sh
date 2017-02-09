#!/bin/bash

#################################################################################
# Comment: Estuary Application Setup.sh Template
# Author: Huang Jinhua
# Date : 2017/01/22
#################################################################################


INSTALLDIR=$(cd $1; pwd)

#
# In addition, use ${INSTALLDIR}/bin, ${INSTALLDIR}/libs, and 
# ${INSTALLDIR}/include to install packages 
# 
# However if it needs to install two or more version of the same package
# it must be installed into ${INSTALLDIR}/${CUR_PKG}/{bin,include,libs} accordingly 
# In addition, it should create symbol links from ${INSTALLDIR}/{bin,libs,include} to
# ${INSTALLDIR}/${CUR_PKG}/{bin,libs,include}
#

#
# Return:
# 1) "exit 0" : return 'INSTALL_SUCCESS' if current setup is successful 
#               and it is NOT necessary to run this script again
# 2) "exit 2" : return 'INSTALL_ALWAYS' if current setup is successful 
#               but it still need to run this script during next boot up stage
# 3) "exit 1" : return INSTALL_FAILURE if any failure occurs 
#               so it will try to run this script later

CUR_DIR=$(cd `dirname $0`; pwd)

JDK_FILE=$(ls ${CUR_DIR}/jdk*.tar.xz 2>/dev/null)
for packagefile in ${JDK_FILE[@]}; do
    xz -d ${packagefile}
done

JDK_FILE=$(ls ${CUR_DIR}/jdk*.tar 2>/dev/null)
for packagefile in ${JDK_FILE[@]}; do 
    tar -xvf ${packagefile} -C ${CUR_DIR}/
    
    packagedir=${packagefile:0:-4}
    
    if [ -d ${packagedir} ] ; then
        if [ -z "$(grep ${packagedir} /etc/profile)" ] ; then
           echo "export JAVA_HOME=${packagedir}" >> /etc/profile
           echo 'export PATH=${JAVA_HOME}/bin:$PATH' >> /etc/profile
           echo 'export CLASSPATH=.:${JAVA_HOME}/lib/dt.jar:${JAVA_HOME}/lib/tools.jar' >> /etc/profile
        fi
    fi
done

exit 0

