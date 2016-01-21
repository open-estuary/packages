    groupadd mysql
    useradd -g mysql mysql
    cd ..
    cp my-sigle.cnf  /etc/my.cnf
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
    echo "mysql install finished"
