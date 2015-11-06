#!/bin/bash
#author: Shameer
#date: 05/11/2015
# Install docker on ubuntu

INSTALL_DOCKER_PACKAGES="YES"
if [ "$INSTALL_DOCKER_PACKAGES" = 'YES' ]; then
    Distribution=`cat /etc/issue| cut -d' ' -f 1`
    echo $Distribution

    case "$Distribution" in
        Ubuntu)
            #echo "Ubuntu Distribution"
            sudo apt-get -y update
            echo "Installing Docker on $Distribution..."
            sudo apt-get install -y docker.io
        ;;
        *)
            echo "Docker not installed as it is not supported/verified on $Distribution..."
            exit 1
        esac
        echo "Finished installation of Docker"
    fi

