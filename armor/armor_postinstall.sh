#!/bin/bash
#author: Shiju Jose
#date: 05/11/2015
#description: postinstall scripts for Armor Tools
armor_post_log="/usr/bin/estuary/postinstall/armor_post_install_log"
err_flag=0

# Install Armor Tools packages
# Uncomment below line to install Armor tool's packages.
INSTALL_ARMOR_TOOLS="YES"
if [ "$INSTALL_ARMOR_TOOLS" = 'YES' ]; then
    Distribution=`cat /etc/issue| cut -d' ' -f 1`
    echo "Installing Armor tools packages on $Distribution..." >> $armor_post_log 

    #add path to the perf tool 
    echo "export PATH=/usr/lib/linux-tools-3.19.0-23:\$PATH" >> ~/.bashrc
    rm $armor_post_log

    case "$Distribution" in
        Ubuntu)
            #echo "Ubuntu Distribution"

            sudo apt-get -y update
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get -y update failed"  >> $armor_post_log
               err_flag=1
            fi

            #Install perf tool
            sudo apt-get install -y linux-tools-3.19.0-23
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y linux-tools-3.19.0-23 failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y sysstat  # sar
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y sysstat failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y gdb
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y gdb failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y iptables
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y iptables failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y dstat
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y dstat failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y iotop
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y iotop  failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y blktrace
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y blktrace failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y nicstat
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y nicstat failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y libconfig9
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y libconfig9 failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y lldpad
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y lldpad failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y oprofile
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y  oprofile failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y latencytop
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y latecytop failed"   >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y systemtap
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y systemtap failed" >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y crash
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y  crash failed"  >> $armor_post_log
               err_flag=1
            fi

            #install lttng packages
            sudo apt-get install -y lttng-tools
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y lttng-tools failed"  >> $armor_post_log
               err_flag=1
            fi

            sudo apt-get install -y liblttng-ust-dev
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: apt-get install -y liblttng-ust-dev failed" >> $armor_post_log
               err_flag=1
            fi

            # temporary workaround for modules are not getting installed.
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
            modprobe lttng-probe-jbd
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
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dpkg -i /usr/local/armor/binary/dmidecode-1.0-arm64.deb failed"
               err_flag=1
            fi

            dpkg -i /usr/local/armor/binary/tiptop-2.3_arm64.deb
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dpkg -i /usr/local/armor/binary/tiptop-2.3_arm64.deb  failed"
               err_flag=1
            fi  
            ;;
           
        Fedora) 
            #echo "Fedora Distribution"
            dnf install -y sysstat.aarch64 # sar
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y sysstat.aarch64 failed"
               err_flag=1
            fi

            dnf install -y dmidecode.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y dmidecode.aarch64 failed"
               err_flag=1
            fi

            dnf install -y tcpdump.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y tcpdump.aarch64 failed"
               err_flag=1
            fi

            dnf install -y ethtool.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y ethtool.aarch64 failed"
               err_flag=1
            fi
           
            dnf install -y dstat
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y dstat failed"
               err_flag=1
            fi

            dnf install -y tiptop.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y tiptop.aarch64 failed"
               err_flag=1
            fi

            dnf install -y iotop.noarch
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y iotop.noarch failed"
               err_flag=1
            fi

            dnf install -y blktrace.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y blktrace.aarch64 failed"
               err_flag=1
            fi

            dnf install -y nicstat.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y nicstat.aarch64 failed"
               err_flag=1
            fi

            dnf install -y lldpad.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y lldpad.aarch64 failed"
               err_flag=1
            fi

            dnf install -y oprofile.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y oprofile.aarch64 failed"
               err_flag=1
            fi

            dnf install -y latencytop.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y latencytop.aarch64 failed"
               err_flag=1
            fi

            dnf install -y systemtap.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y systemtap.aarch64 failed"
               err_flag=1
            fi

            dnf install -y crash.aarch64
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dnf install -y crash.aarch64 failed"
               err_flag=1
            fi

            # install prebuilt rpm packages 
            ;;
         
        OpenSuse) 
            #echo "OpenSuse Distribution"
            zypper install -y ltrace
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y ltrace failed"
               err_flag=1
            fi

            zypper install -y pcp-import-iostat2pcp # for sar, iostat etc.
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y pcp-import-iostat2pcp failed"
               err_flag=1
            fi
            
            zypper install -y dmidecode
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y dmidecode failed"
               err_flag=1
            fi

            zypper install -y strace
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y strace failed"
               err_flag=1
            fi

            zypper install -y net-tools-deprecated
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y net-tools-deprecated failed"
               err_flag=1
            fi

            zypper install -y net-tools
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y net-tools failed"
               err_flag=1
            fi
            
            zypper install -y dstat 
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y dstat failed"
               err_flag=1
            fi

            zypper install -y procps
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y procps failed"
               err_flag=1
            fi

            zypper install -y iotop
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y iotop failed"
               err_flag=1
            fi

            zypper install -y blktrace
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y blktrace failed"
               err_flag=1
            fi

            zypper install -y oprofile
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y oprofile failed"
               err_flag=1
            fi

            zypper install -y systemtap
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: zypper install -y systemtap failed"
               err_flag=1
            fi
            
            # install prebuilt rpm packages 
            rpm -i /usr/local/armor/binary/lldpad-1.0.1-0.aarch64.rpm 
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: rpm -i /usr/local/armor/binary/lldpad-1.0.1-0.aarch64.rpm failed"
               err_flag=1
            fi

            rpm -i /usr/local/armor/binary/nicstat-1.95-0.aarch64.rpm 
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: rpm -i /usr/local/armor/binary/nicstat-1.95-0.aarch64.rpm failed"
               err_flag=1
            fi

            rpm -i /usr/local/armor/binary/tiptop-2.3-0.aarch64.rpm
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: rpm -i /usr/local/armor/binary/tiptop-2.3-0.aarch64.rpm failed"
               err_flag=1
            fi
            ;;
        esac

        if [ $err_flag -eq 1 ]; then
           echo "Finished installation of Armor tools packages with Errors" >> $armor_post_log
           exit 1
        fi 
        echo "Finished installation of Armor tools packages Successfully" >> $armor_post_log
    fi

