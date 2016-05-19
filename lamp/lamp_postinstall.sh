#!/bin/bash
#author: Bowen Liu
#date: 19/05/2016
#description: postinstall scripts for lamp

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
ip_addr=`ifconfig eth0 | grep 'inet addr' | awk -F "[:]" '{print $2}' | awk '{print $1}'`
echo $ip_addr
sed -i "s/192.168.1.246/$ip_addr/g" `grep 192.168.1.246 -rl /root/Discuz`
sed -i '$i\docker load --input /root/openestuary_apache.tar.gz' /etc/rc.local
sed -i '$i\docker load --input /root/openestuary_mysql.tar.gz' /etc/rc.loacl
sed -i '$i\docker run -d -p 32768:80 -v /root/Discuz:/var/www/html openestuary/apache' /etc/rc.local
sed -i '$i\docker run -d -p 32769:3306 -v /root/mysql_data:/u01/my3306/data openestuary/mysql' /etc/rc.local
