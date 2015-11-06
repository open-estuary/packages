#!/bin/bash
#author: Shameer
#date: 05/11/2015
# Install Pacakges required to run docker on ubuntu

INSTALL_DOCKER_PACKAGES="YES"
if [ "$INSTALL_DOCKER_PACKAGES" = 'YES' ]; then
    Distribution=`cat /etc/issue| cut -d' ' -f 1`
    echo $Distribution

    case "$Distribution" in
        Ubuntu)
            #echo "Ubuntu Distribution"
            sudo apt-get -y update
            echo "Installing packages required for Docker on $Distribution..."
            sudo apt-get install -y iptables
            sudo apt-get install -y bridge-utils
            sudo apt-get install -y golang
            sudo apt-get install -y btrfs-tools libsqlite3-dev libdevmapper-dev
        ;;
        *)
            echo "Docker pacakges not installed as it is not supported/verified on $Distribution..."
            exit 1
        esac
        echo "Finished installation of packages for Docker"
    fi

