#!/bin/bash
#author: Shiju Jose
#date: 05/11/2015
#description: postinstall scripts for Armor Tools
armor_post_install_dir="/usr/bin/estuary/postinstall/"
armor_post_log="/usr/bin/estuary/postinstall/armor_post_install_log"

#error flag to capture apt install errors
err_apt_install_flag=0

#error flag for apt-get update
err_apt_update_flag=0

# Install Armor Tools packages
# Uncomment below line to install Armor tool's packages.
INSTALL_ARMOR_TOOLS="YES"
if [ "$INSTALL_ARMOR_TOOLS" = 'YES' ]; then
    #delete log file
    rm $armor_post_log
    
    Distribution=`sed -n 1p /etc/issue| cut -d' ' -f 1`
    # Temp fix for OpenSuse distribution as the format of /etc/issue in OpenSuse is different
    if [ "$Distribution" = 'Welcome' ]; then
        Distribution=`sed -n 1p /etc/issue| cut -d' ' -f 3`
    fi
    echo "Installing Armor tools packages on $Distribution..." | tee -a $armor_post_log 
    

    case "$Distribution" in
        Ubuntu)
            #echo "Ubuntu Distribution"

<<COMMENT_OUT_APT_UPDATE
            if [ ! -e $armor_post_install_dir/.apt-update ]; then  
                sudo apt-get -y update
                if [ $? -ne 0 ]; then
                    echo "armor_postinstall: apt-get -y update failed" | tee -a $armor_post_log
                    err_apt_update_flag=1
                else
                    touch  $armor_post_install_dir/.apt-update
                fi 
            fi
COMMENT_OUT_APT_UPDATE

               #add path for the perf binary 
               echo "export PATH=/usr/lib/linux-tools-3.19.0-23:\$PATH" >> ~/.bashrc
               echo "export PATH=/usr/lib/linux-tools-3.19.0-23:\$PATH" >> /etc/profile
               # Fix for Bug ID Mantis-40: sudo perf is not working
               OLD_STR="secure_path\=\""
               NEW_STR="secure_path\=\"\/usr\/lib\/linux\-tools\-3\.19\.0\-23\:"
               sed -i -e "s/$OLD_STR/$NEW_STR/g" /etc/sudoers

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
            ;;
           
        Fedora) 
            #echo "Fedora Distribution"

            # temporary workaround for lttng modules are not getting installed.
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
            ;;
         
        openSUSE)
            #echo "OpenSuse Distribution"
            # install prebuilt rpm packages 
            ;;

        Debian)
            #echo "Debian Distribution"

            ln -s /bin/kmod /sbin/depmod
            ln -s /bin/kmod /sbin/modprobe
            apt-get -f -y install
            
            #Install perf tool
               #add path for the perf binary 
               #echo "export PATH=/usr/lib/linux-tools-3.19.0-23:\$PATH" >> ~/.bashrc
               #echo "export PATH=/usr/lib/linux-tools-3.19.0-23:\$PATH" >> /etc/profile
               # Fix for Bug ID Mantis-40: sudo perf is not working
               #OLD_STR="secure_path\=\""
               #NEW_STR="secure_path\=\"\/usr\/lib\/linux\-tools\-3\.19\.0\-23\:"
               #sed -i -e "s/$OLD_STR/$NEW_STR/g" /etc/sudoers
 
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
            dpkg -i /usr/local/armor/binary/tiptop-2.3_arm64.deb
            if [ $? -ne 0 ]; then
               echo "armor_postinstall: dpkg -i /usr/local/armor/binary/tiptop-2.3_arm64.deb  failed"  | tee -a $armor_post_log
               err_apt_install_flag=1
            fi  
            ;;

        esac

        if [ $err_apt_install_flag -eq 1 ]; then
           echo "Finished installation of Armor tools packages with Errors" | tee -a $armor_post_log
	   echo "########## Armor Installation didnot complete as expected ############"
	   echo "Please refer the logfile:${armor_post_log} for the failed packages"
	   echo "Please install them manually after correcting the network"
	   echo " The command to install is also available in the logfile"
           exit 0
        fi 
        echo "Finished installation of Armor tools packages Successfully" | tee -a $armor_post_log
    fi

