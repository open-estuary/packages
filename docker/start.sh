#!/bin/bash
#author: Shameer
#date: 05/11/2015
# Enable docker service for boot start.

#Check docker is installed or not.
if hash docker >/dev/null 2>&1; then
    echo "Docker is installed. Will try to enable and run the service now"
    Distribution=`sed -n 1p /etc/issue| cut -d' ' -f 1`
    # Temp fix for OpenSuse distribution as the format of /etc/issue in OpenSuse is different
    if [ "$Distribution" = 'Welcome' ]; then
        Distribution=`sed -n 1p /etc/issue| cut -d' ' -f 3`
    fi
    # fix for CentOS
    if [ "$Distribution" = '\S' ]; then
        Distribution=`uname -n`
        if [ "$Distribution" = 'cent-est' ]; then
            Distribution=CentOS
        fi
    fi

    echo $Distribution
    case "$Distribution" in
        Debian)
            #if modprobe is missing in Debian default, Install it first
            if ! hash modprobe >/dev/null 2>&1; then
                apt-get install -y module-init-tools
            fi
            apt-get install -y iptables
            #Create and add docker group
            groupadd docker
            gpasswd -a ${USER} docker
            systemctl enable docker
            systemctl start docker

            if [ $? != 0 ];
            then
                echo "Docker service start failed..may still work by starting the daemon manually"
            fi
        ;;
        Ubuntu|openSUSE|Fedora|CentOS)
            sudo groupadd docker
            sudo gpasswd -a ${USER} docker
            sudo systemctl enable docker
            sudo systemctl start docker
            if [ $? != 0 ];
            then
                echo "Docker service start failed..may still work by starting the daemon manually"
            fi
            ;;
        *)
            echo "Unsupported Distro"
     esac
else
    echo "Docker is not installed!."
fi
