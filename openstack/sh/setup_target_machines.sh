#!/bin/bash
#author: wuyanjun
#created date: 2017-04-14

hash iptables 2>/dev/null && iptables -F
hash setenforce 2>/dev/null && setenforce 0
service NetworkManager stop

if [ -f /etc/debian_version ]; then
    echo "APT::Get::AllowUnauthenticated 1;" >> /etc/apt/apt.conf.d/70debconf
    
    #echo deb http://repo.linaro.org/debian/erp-16.12-stable jessie main >> \
    echo deb http://192.168.1.103:8083/repos/debian/erp-16.12-stable/ jessie main >> \
        /etc/apt/sources.list
    #echo deb-src http://repo.linaro.org/debian/erp-16.12-stable jessie main  >> \
    echo deb-src http://192.168.1.103:8083/repos/debian/erp-16.12-stable/ jessie main >> \
        /etc/apt/sources.list
    echo deb http://ftp.debian.org/debian jessie-backports main  >> \
        /etc/apt/sources.list
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys E13D88F7E3C1D56C
    [ $? -ne 0 ] && echo "The Debian key cannot be installed"
    apt-get update -y
    [ $? -ne 0 ] && echo "The Debian cannot be updated"
    apt install -t jessie-backports systemd systemd-sysv libpam-systemd -y
    apt install sudo -y
    [ $? -ne 0 ] && echo "The Debian cannot update systemd"
fi

if [ -f /etc/redhat_version ]; then
    #wget http://repo.linaro.org/rpm/linaro-overlay/centos-7/linaro-overlay.repo \
    wget http://192.168.1.103:8083/repos/centos/7/linaro-overlay.repo \
        -O /etc/yum.repos.d/linaro-overlay.repo
    [ $? -ne 0 ] && echo "The CentOS source cannot be accessed"
    yum update -y
    yum install sudo -y
    [ $? -ne 0 ] && echo "The CentOS cannot be updated"
fi
