#!/bin/bash

#Step 1: Download html files for Apache web servers for test

if [ ! -f ./Discuz_X3.3_SC_UTF8.zip ] ; then
    wget http://download.comsenz.com/DiscuzX/3.3/Discuz_X3.3_SC_UTF8.zip
fi

tar -zxvf Discuz_X3.3_SC_UTF8.zip

INSTALL_DIR="/usr/local/lamp/Discuz"
if [ ! -d ${INSTALL_DIR} ] ; then
    mkdir -p ${INSTALL_DIR}
fi

cp Discuz_X3.3_SC_UTF8.zip ${INSTALL_DIR}/
pushd ${INSTALL_DIR} > /dev/null
unzip Discuz_X3.3_SC_UTF8.zip
popd > /dev/null

#Step 2:Start docker images
sed -i "s/192.168.1.246/$ip_addr/g" `grep 192.168.1.246 -rl ${INSTALL_DIR}/*`
docker run -d -p 32768:80 -v ${INSTALL_DIR}:/var/www/html openestuary/apache
docker run -d -p 32769:3306 openestuary/mysql

sleep 10
echo "************** Now you can enjoy the web service offered by Docker! **************"
