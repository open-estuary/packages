#!/bin/bash
#author: Shiju Jose
#date: 05/11/2015
#description: postinstall scripts for Armor Tools

# Install Armor Tools packages
# Uncomment below line to install Armor tool's packages.
INSTALL_ARMOR_TOOLS="YES"

CUR_DIR=$(cd `dirname $0`; pwd)

if [ ! -z "${1}" ] ; then
    INSTALL_DIR=$(cd $1; pwd)
else 
    INSTALL_DIR="/usr/estuary/"
fi

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
    
    if [ ! -f "${CUR_DIR}/.install_status" ] ; then
        if [ -z "$(which sudo 2>/dev/null)" ] ; then
           if [ "$(which apt-get 2>/dev/null)" ] ; then
               apt-get install -y sudo
           elif [ "$(which yum 2>/dev/null)" ] ; then
               yum install -y sudo
           fi
        fi
           
        #Step1: Install pre-built binaries to /usr/estuary/bin
        sudo cp -fr ${CUR_DIR}/bin/* ${INSTALL_DIR}/bin/
        sudo cp -fr ${CUR_DIR}/usr/bin/* ${INSTALL_DIR}/bin/
        
        kernel_version=$(uname -r)
        if [ -d ${CUR_DIR}/lib/modules/$kernel_version/armor ] ; then
            ARMOR_KO_DIR="/lib/modules/$kernel_version/armor"
            if [ ! -d "${ARMOR_KO_DIR}" ] ; then
                sudo mkdir "${ARMOR_KO_DIR}"
            fi 
            sudo cp -fr ${CUR_DIR}/lib/modules/$kernel_version/armor/* ${ARMOR_KO_DIR}/
        fi

        #Step2: Install packages based on their types accordingly 
        packages_type=("*.tar.gz" "*.deb" "*.rpm")
        packages_cmd=("tar" "dpkg" "rpm")
        index=0
        while [[ ${index} -lt ${#packages_type[@]} ]] ; 
        do
            packages_list=$(ls ${CUR_DIR}/packages/${packages_type[$index]} 2>/dev/null)
            package_cmd=${packages_cmd[$index]}
            for pkg_file in ${packages_list[@]}; 
            do
                echo "Use ${package_cmd} to install ${pkg_file} ..."
                if [ "${package_cmd}" == "tar" ] ; then
                    sudo tar -zxf ${pkg_file} -C ${INSTALL_DIR}
                elif [ "${package_cmd}" == "rpm" ] ; then
                    sudo ${package_cmd} --force --nodeps --ignorearch --noscripts --nosignature -i ${pkg_file}
                else 
                    sudo dpkg --force-all -i ${pkg_file}
                fi
            done
            let "index++"
        done

        touch ${CUR_DIR}/.install_status
    fi

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
            #fix to resolve dependency packages. 
            apt-get -f -y install

            ln -s /bin/kmod /sbin/depmod
            ln -s /bin/kmod /sbin/modprobe
            ;;

        CentOS)
            #echo "CentOS Distribution"
            ;;

        esac

        echo "Finished installation of Armor tools packages Successfully"
    fi

# Workaround for the tools modules which are not getting installed on rootfs automatically.
depmod -a
modprobe lttng-clock
modprobe lttng-kprobes
modprobe lttng-probe-kvm
modprobe lttng-probe-sock
modprobe lttng-ring-buffer-metadata-client
modprobe lttng-probe-printk
modprobe lttng-probe-napi
modprobe lttng-probe-v4l2
modprobe lttng-statedump
modprobe lttng-probe-btrfs
modprobe lttng-ring-buffer-client-mmap-discard
modprobe lttng-probe-kmem
modprobe lttng-probe-compaction
modprobe lttng-ring-buffer-client-overwrite
modprobe lttng-ring-buffer-client-mmap-overwrite
modprobe lttng-probe-sunrpc
modprobe lttng-ftrace
modprobe lttng-probe-signal
modprobe lttng-probe-module
modprobe lttng-ring-buffer-client-discard
modprobe lttng-probe-timer
modprobe lttng-probe-net
modprobe lttng-probe-writeback
modprobe lttng-probe-gpio
modprobe lttng-probe-i2c
modprobe lttng-probe-udp
modprobe lttng-ring-buffer-metadata-mmap-client
modprobe lttng-lib-ring-buffer
modprobe lttng-probe-jbd2
modprobe lttng-probe-statedump
modprobe lttng-probe-ext4
modprobe lttng-probe-rcu
modprobe lttng-tracer
modprobe lttng-probe-power
modprobe lttng-probe-sched
modprobe lttng-probe-block
modprobe lttng-probe-vmscan
modprobe lttng-probe-scsi
modprobe lttng-probe-regmap
modprobe lttng-probe-skb
modprobe lttng-probe-regulator
modprobe lttng-probe-random
modprobe lttng-probe-workqueue
modprobe lttng-probe-irq

#Return INSTALL_ALWAYS(=2) so it will be installed during every boot stage
exit 2

