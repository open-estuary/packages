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
#    apt-get install cmake
#    if [ "$?" == 1 ] ; then
#      echo "apt-get install cmake failed..."
#      exit 1
#    fi
#    apt-get install g++
#    if [ "$?" == 1 ] ; then
#      echo "apt-get install g++ failed..."
#      exit 1
#    fi
#######
    echo "Installing mysql on $Distribution..."
    cd ./percona-server-5.6.22-72.0
    cd ./BUILD
    sh autorun.sh
    cd ..
    cmake -DCMAKE_INSTALL_PREFIX=/u01/my3306 -DMYSQL_DATADIR=/u01/my3306/data -DMYSQL_USER=mysql -DSYSCONFDIR=/etc  -DWITH_MYISAM_STORAGE_ENGINE=1 -DWITH_INNOBASE_STORAGE_ENGINE=1-DWITH_MEMORY_STORAGE_ENGINE=1 -DMYSQL_UNIX_ADDR=/u01/my3306/run/mysql.sock -DMYSQL_TCP_PORT=3306 -DENABLED_LOCAL_INFILE=1  -DWITH_PARTITION_STORAGE_ENGINE=1 -DEXTRA_CHARSETS=all -DDEFAULT_CHARSET=utf8  -DDEFAULT_COLLATION=utf8_general_ci
    if [ "$?" == 1 ] ; then
      echo "cmake mysql failed..."
      exit 1
    fi
    make -j 16
    make install
    groupadd mysql
    useradd –g mysql mysql
    cp ./my-sigle.cnf  /etc/my.cnf
    mkdir /u01/mysql
    cp –rf /u01/my3306/share /u01/mysql
    mkdir /u01/my3306/tmp
    mkdir /u01/my3306/log
    mkdir /u01/my3306/run
    cd /u01
    chown –R mysql.root ./
    cd /u01/my3306
    scripts/mysql_install_db --basedir=/u01/my3306 --datadir=/u01/my3306/data --user=mysql
    /u01/my3306/bin/mysqld_safe --defaults-file=/etc/my.cnf --basedir=/u01/my3306 --datadir=/u01/my3306/data &
    /u01/my3306/bin/mysql –uroot
    SET PASSWORD = PASSWORD('123456');
    UPDATE mysql.user SET password=PASSWORD('123456') WHERE User='mysql';
    GRANT ALL PRIVILEGES ON *.* TO mysql@localhost IDENTIFIED BY '123456' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON *.* TO mysql@"%" IDENTIFIED BY '123456' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON *.* TO root@localhost IDENTIFIED BY '123456' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON *.* TO root@"%" IDENTIFIED BY '123456' WITH GRANT OPTION;
    exit
#  ;;
#  *)
#  esac
echo "Finished installation of mysql"
