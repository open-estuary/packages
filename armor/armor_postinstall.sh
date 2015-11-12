#!/bin/bash
#author: Shiju Jose
#date: 05/11/2015
#description: postinstall scripts for Armor Tools

# Install Armor Tools packages
# Uncomment below line to install Armor tool's packages.
INSTALL_ARMOR_TOOLS="YES"
if [ "$INSTALL_ARMOR_TOOLS" = 'YES' ]; then
    Distribution=`cat /etc/issue| cut -d' ' -f 1`
    echo "Installing Armor tools packages on $Distribution..." 
   
    #add path to the perf tool 
    echo "export PATH=/usr/lib/linux-tools-3.19.0-23:\$PATH" >> ~/.bashrc

    case "$Distribution" in
        Ubuntu) 
            #echo "Ubuntu Distribution"
            apt-get install -y sysstat  # sar
            apt-get install -y gdb  
            apt-get install -y iptables  
           
            apt-get install -y dstat  
            apt-get install -y iotop  
            apt-get install -y blktrace  
            apt-get install -y nicstat  
            apt-get install -y libconfig9  
            apt-get install -y lldpad  
            apt-get install -y oprofile  
            apt-get install -y latencytop 
            apt-get install -y systemtap  
            apt-get install -y crash 
	    #Install perf tool
	    apt-get install -y linux-tools-3.19.0-23 

            #install lttng packages
            apt-get install -y lttng-tools
            apt-get install -y liblttng-ust-dev
            depmod -a
            modprobe lttng-probe-kvm
            modprobe lttng-probe-sock
            modprobe lttng-probe-asoc
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
            modprobe lttng-types
            modprobe lttng-probe-net
            modprobe lttng-probe-writeback
            modprobe lttng-probe-gpio
            modprobe lttng-probe-udp
            modprobe lttng-kretprobes
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
            modprobelttng-probe-jbd
            modprobe lttng-probe-vmscan
            modprobe lttng-kprobes
            modprobe lttng-probe-scsi
            modprobe lttng-probe-regmap
            modprobe lttng-probe-skb
            modprobe lttng-probe-ext3
            modprobe lttng-probe-regulator
            modprobe lttng-probe-random
            modprobe lttng-probe-workqueue
            modprobe lttng-probe-irq

            # install prebuilt deb packages   
            dpkg -i /usr/local/armor/binary/dmidecode-1.0-arm64.deb
            dpkg -i /usr/local/armor/binary/tiptop-2.3_arm64.deb
            ;;
           
        Fedora) 
            #echo "Fedora Distribution"
            dnf install -y sysstat.aarch64 # sar
            dnf install -y dmidecode.aarch64
            dnf install -y tcpdump.aarch64
            dnf install -y ethtool.aarch64
           
            dnf install -y dstat
            dnf install -y tiptop.aarch64
            dnf install -y iotop.noarch
            dnf install -y blktrace.aarch64
            dnf install -y nicstat.aarch64
            dnf install -y lldpad.aarch64
            dnf install -y oprofile.aarch64
            dnf install -y latencytop.aarch64
            dnf install -y systemtap.aarch64
            dnf install -y crash.aarch64

            # install prebuilt rpm packages 
            ;;
         
        OpenSuse) 
            #echo "OpenSuse Distribution"
            zypper install -y ltrace
            zypper install -y pcp-import-iostat2pcp # for sar, iostat etc.
            zypper install -y dmidecode
            zypper install -y strace
            zypper install -y net-tools-deprecated
            zypper install -y net-tools
            
            zypper install -y dstat 
            zypper install -y procps
            zypper install -y iotop
            zypper install -y blktrace
            zypper install -y oprofile
            zypper install -y systemtap
            
            # install prebuilt rpm packages 
            rpm -i /usr/local/armor/binary/lldpad-1.0.1-0.aarch64.rpm 
            rpm -i /usr/local/armor/binary/nicstat-1.95-0.aarch64.rpm 
            rpm -i /usr/local/armor/binary/tiptop-2.3-0.aarch64.rpm
            ;;
        esac
        echo "Finished installation of Armor tools packages" 
    fi

