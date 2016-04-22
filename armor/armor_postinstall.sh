#!/bin/bash
#author: Shiju Jose
#date: 05/11/2015
#description: postinstall scripts for Armor Tools

# Install Armor Tools packages
# Uncomment below line to install Armor tool's packages.
INSTALL_ARMOR_TOOLS="YES"

if [ "$INSTALL_ARMOR_TOOLS" = 'YES' ]; then
    Distribution=`sed -n 1p /etc/issue| cut -d' ' -f 1`
    # Temp fix for OpenSuse distribution as the format of /etc/issue in OpenSuse is different
    Distribution1=`sed -n 1p /etc/issue| cut -d' ' -f 3`
    if [ "$Distribution1" = 'openSUSE' ]; then
        Distribution=`sed -n 1p /etc/issue| cut -d' ' -f 3`
    fi
    # fix for CentOS
    if [ "$Distribution" = '\S' ]; then
        Distribution=`uname -n`
        if [ "$Distribution" = 'cent-est' ]; then
            Distribution=CentOS
        fi
    fi

    echo "Installing Armor tools packages on $Distribution..." 

    case "$Distribution" in
        Ubuntu)
            #echo "Ubuntu Distribution"
            ;;
           
        Fedora) 
            #echo "Fedora Distribution"
            ;;
         
        openSUSE)
            #echo "OpenSuse Distribution"
            ;;
            
        Debian)
            #echo "Debian Distribution"

            ln -s /bin/kmod /sbin/depmod
            ln -s /bin/kmod /sbin/modprobe
            ;;

        CentOS)
            #echo "CentOS Distribution"
            ;;

        esac

        echo "Finished installation of Armor tools packages Successfully"
    fi

