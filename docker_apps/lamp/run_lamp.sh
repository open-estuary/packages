#!/bin/bash
ip_addr=`ifconfig eth0 | grep 'inet addr' | awk -F "[:]" '{print $2}' | awk '{print $1}'`
echo $ip_addr

status=1
while [ $status != 0 ];
    do
    docker pull openestuary/apache
    status=$?
done;

status=1
while [ $status != 0 ];
    do
    docker pull openestuary/mysql
    status=$?
done;

tar -zxf Discuz.tgz
sed -i "s/192.168.1.246/$ip_addr/g" `grep 192.168.1.246 -rl ./Discuz`
docker run -d -p 32768:80 -v /usr/local/sbin/lamp/Discuz:/var/www/html openestuary/apache
docker run -d -p 32769:3306 -v /usr/local/sbin/lamp/mysql_data:/u01/my3306/data openestuary/mysql

sleep 10
echo "************** Now you can enjoy the web service offered by Docker! **************"
