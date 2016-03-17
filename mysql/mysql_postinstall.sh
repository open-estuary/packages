#!/bin/bash
#author: Bowen Liu
#date: 14/02/2016
#description: postinstall scripts for mysql

Distribution=`sed -n 1p /etc/issue| cut -d' ' -f 1`
# Temp fix for OpenSuse distribution as the format of /etc/issue in OpenSuse is different
Distribution1=`sed -n 1p /etc/issue| cut -d' ' -f 3`
if [ "$Distribution1" = 'openSUSE' ]; then
    Distribution=`sed -n 1p /etc/issue| cut -d' ' -f 3`
fi

echo "Installing Mysql on $Distribution..."
    
case "$Distribution" in
    Fedora)
        ;;
    openSUSE)
        ;;
    Ubuntu)
        ;;
    Debian)
    ;;
    esac
    
mysql_path="/u01"
if [ -d "$mysql_path" ]; then
    groupadd mysql
    useradd -g mysql mysql
    cd ..
    mkdir /u01/mysql
    cp -rf /u01/my3306/share /u01/mysql
    mkdir /u01/my3306/tmp
    mkdir /u01/my3306/log
    mkdir /u01/my3306/run
    cd /u01
    chown -R mysql.root ./
    cd /u01/my3306
    scripts/mysql_install_db --basedir=/u01/my3306 --datadir=/u01/my3306/data --user=mysql
    /u01/my3306/bin/mysqld_safe --defaults-file=/etc/my.cnf --basedir=/u01/my3306 --datadir=/u01/my3306/data &
    export PATH=/u01/my3306/bin:$PATH
    echo "export PATH=/u01/my3306/bin:$PATH">>/etc/profile
    systemctl enable mysql
    echo "mysql install finished successfully"
else
    echo "WARNING:mysql should be made from aarch64!"
fi
