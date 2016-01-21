#!/bin/bash
#author: LiuBowen
#date: 01/15/2016
# Install mysql on ubuntu

#Distribution=`cat /etc/issue| cut -d' ' -f 1`
#echo $Distribution
#ResolvConfFile=/etc/resolv.conf

#case "$Distribution" in
#  Ubuntu)
    
########
#    grep nameserver "$ResolvConfFile"
#    if [ "$?" == 1 ]; then
#      echo "nameserver 8.8.8.8" >> "$ResolvConfFile"
#      echo "--------configure $ResolvConfFile------"
#      source /etc/resolv.conf
#    fi
########
    apt-get install cmake <<EOF
Y
EOF
#    if [ "$?" == 1 ] ; then
#      echo "apt-get install cmake failed..."
#      exit 1
#    fi
    apt-get install g++ <<EOF
Y
EOF
#    if [ "$?" == 1 ] ; then
#      echo "apt-get install g++ failed..."
#      exit 1
#    fi
#######
    echo "Installing mysql on $Distribution..."
    cd percona-5.6.22-72.0
    cd BUILD
    sh autorun.sh
    cd ..
    cmake -DCMAKE_INSTALL_PREFIX=/u01/my3306 -DMYSQL_DATADIR=/u01/my3306/data -DMYSQL_USER=mysql -DSYSCONFDIR=/etc -DWITH_MYISAM_STORAGE_ENGINE=1 -DWITH_INNOBASE_STORAGE_ENGINE=1 -DWITH_MEMORY_STORAGE_ENGINE=1 -DMYSQL_UNIX_ADDR=/u01/my3306/run/mysql.sock -DMYSQL_TCP_PORT=3306 -DENABLED_LOCAL_INFILE=1 -DWITH_PARTITION_STORAGE_ENGINE=1 -DEXTRA_CHARSETS=all -DDEFAULT_CHARSET=utf8 -DDEFAULT_COLLATION=utf8_general_ci
#    if [ "$?" == 1 ] ; then
#      echo "cmake mysql failed..."
#      exit 1
#    fi
    make -j 16
    make install
