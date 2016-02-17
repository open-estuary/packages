#!/bin/bash
#author: Justin Zhao
#date: 31/10/2015
#author: Shiju Jose
#date: 05/11/2015
#description: Armor build & install script
#$1: target platform name
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

install_armor_tools_ubuntu()
{
    echo "CFGFILE = $CFGFILE"
    idx=0
    install=`jq -r ".tools[$idx].install" $CFGFILE`
    #echo "install= $install" 
    # Fix for dpkg -i ... error - syntax error: unknown group 'landscape' in statoverride file
    sudo sed -i '/landscape/d' $ROOTFS/var/lib/dpkg/statoverride

    while [ x"$install" != x"null" ];
    do
        if [ x"yes" = x"$install" ]; then
            name=`jq -r ".tools[$idx].name" $CFGFILE`
            case $name in
                "awk")
                    #default installed. 
                ;;
                "blktrace")
                    #supported run time installation on board. 
                ;;
                "crash")
                    sudo cp $armor_dir/binary/crash   $ROOTFS/usr/bin
                    #default installed. 
                ;;
                "dmidecode")
                    echo "dmidecode"
                    # build demidecode
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_dmidecode.sh $cross_gcc $ROOTFS
                    cd -
                    popd
                    # copy demidecode to rootfs
                    sudo cp $armor_build_dir/source/dmidecode/dmidecode $ROOTFS/usr/bin
                ;;
                "dstat")
                    #supported run time installation on board. 
                ;;
                "ethtool")
                    #default installed. 
                ;;
                "ftrace")
                    #default installed. 
                ;;
                "fsck")
                    #default installed. 
                ;;
                "gdb")
                    pushd $ROOTFS
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/libpython3.4_3.4.3-3_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/libc6-dbg_2.21-0ubuntu4_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/libc6-dev_2.21-0ubuntu4_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/gdbserver_7.9-1ubuntu1_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/gdb_7.9-1ubuntu1_arm64.deb >> $LOG_FILE 2>&1
                    popd     
                ;;
                "gprof")
                    #default installed. 
                    # build gprof test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_gprof_test.sh $ROOTFS
                    cd -
                    popd
                ;;
                "grep")
                    #default installed. 
                ;;
                "iostat")
                    pushd $ROOTFS
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/libsensors4_1%3a3.3.5-2_arm64.deb >> $LOG_FILE 2>&1   
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/sysstat_11.0.1-1_arm64.deb >> $LOG_FILE 2>&1
                    popd     
                ;;
                "iotop")
                    #supported run time installation on board. 
                ;;
                "iptables")
                    #supported run time installation on board. 
                ;;
                "kdb")
                    #default installed. 
                ;;
                "kgdb")
                    #default installed. 
                ;;
                "kprobes")
                    # build kprobes test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_kprobes_test.sh $kernel_build_dir
                    cd -
                    popd
                    # copy kprobes test binaries to rootfs
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test  $ROOTFS/usr/local/armor/test_scripts
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test.ko  $ROOTFS/usr/local/armor/test_scripts
                ;;
                "ktap")
                    # build  ktap code and install binaries into rootfs
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_ktap.sh $kernel_build_dir $ROOTFS
                    cd -
                    popd
                ;;
                "latencytop")
                    #supported run time installation on board. 
                ;;
                "lldptool")
                    #supported run time installation on board. 
                ;;
                "lscpu")
                    #default installed. 
                ;;
                "lspci")
                    #default installed. 
                ;;
                "ltrace")
                    #default installed. 
                ;;
                "lttng")
                    #supported run time installation on board. 
                    # build lttng kernel module and lttng uspace test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_lttng.sh $kernel_build_dir $ROOTFS
                    cd -
                    popd
                ;;
                "memwatch")
                    #memwatch to be integrated to the code to be tested. 
                ;;
                "mkfs")
                    #default installed. 
                ;;
                "mount")
                    #default installed. 
                ;;
                "netstat")
                    #default installed. 
                ;;
                "nicstat")
                    #supported run time installation on board. 
                ;;
                "oprofile")
                    #supported run time installation on board. 
                ;;
                "packETHcli")
                    # build packeth cli(command line) tool code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_packETHcli.sh $ROOTFS
                    cd -
                    popd
                ;;
                "perf")
                    pushd $ROOTFS
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/libdw1_0.160-0ubuntu3_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/libunwind8_1.1-3.2_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/linux-tools-common_3.19.0-39.44_all.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/linux-tools-3.19.0-23_3.19.0-23.24_arm64.deb >> $LOG_FILE 2>&1
                    popd
                ;;
                "pidstat")
                    pushd $ROOTFS
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/libsensors4_1%3a3.3.5-2_arm64.deb >> $LOG_FILE 2>&1   
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/sysstat_11.0.1-1_arm64.deb >> $LOG_FILE 2>&1 
                    popd     
                ;;
                "powertop")
                    #supported run time installation on board. 
                ;;
                "procps")
                    #default installed. 
                ;;
                "sar")
                    #supported run time installation on board. 
                ;;
                "sed")
                    #default installed. 
                ;;
                "setpci")
                    #default installed. 
                ;;
                "slabtop")
                    #supported run time installation on board. 
                ;;
                "strace")
                    #default installed 
                ;;
                "systemtap")
                    #supported run time installation on board. 
                ;;
                "swapon")
                    #default installed. 
                ;;
                "tail")
                    #default installed. 
                ;;
                "tcpdump")
                    #default installed. 
                ;;
                "tiptop")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/ubuntu/tiptop-2.3_arm64.deb >> $LOG_FILE 2>&1
                    popd
                ;;
                "top")
                    #default installed. 
                ;;
                "valgrind")
                    #supported run time installation on board. 
                    pushd $armor_build_dir
                    cd build_scripts/
                    # build valgrind test code
                    sh build_valgrind_test.sh $ROOTFS
                    cd -
                    popd
                ;;
                "vmstat")
                    #default installed. 
                ;;
            *)
            ;;
            esac
    fi
    let idx=$idx+1
    install=`jq -r ".tools[$idx].install" $CFGFILE`

done

pushd $armor_build_dir
cd build_scripts/

# build armor utility
sh build_armor_utility.sh $ROOTFS

cd -
popd
    # revert the previously deleted line for landscape user/group in $ROOTFS/var/lib/dpkg/statoverride file
    sudo sed -i -e "\$aroot landscape 4754 /usr/lib/landscape/apt-update" $ROOTFS/var/lib/dpkg/statoverride
} #install_armor_tools_ubuntu

install_armor_tools_fedora()
{
    #echo "CFGFILE = $CFGFILE"
    idx=0
    install=`jq -r ".tools[$idx].install" $CFGFILE`
    #echo "install= $install" 
    while [ x"$install" != x"null" ];
    do
        if [ x"yes" = x"$install" ]; then
            name=`jq -r ".tools[$idx].name" $CFGFILE`
            case $name in
                "awk")
                    #default installed. 
                ;;
                "blktrace")
                    #supported run time installation on board. 
                ;;
                "crash")
                    #supported run time installation on board. 
                ;;
                "dmidecode")
                    echo "dmidecode"
                    # build demidecode
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_dmidecode.sh $cross_gcc $ROOTFS
                    cd -
                    popd
                    # copy demidecode to rootfs
                    sudo cp $armor_build_dir/source/dmidecode/dmidecode $ROOTFS/usr/bin
                ;;
                "dstat")
                    #supported run time installation on board. 
                ;;
                "df")
                    #default installed. 
                ;;
                "ethtool")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/fedora/ethtool-3.18-1.fc22.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "ftrace")
                    #default installed. 
                ;;
                "fsck")
                    #default installed. 
                ;;
                "gdb")
                    #default installed. 
                ;;
                "gprof")
                    #default installed. 
                ;;
                "grep")
                    #default installed. 
                ;;
                "iostat")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/fedora/sysstat-11.1.2-3.fc22.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "iotop")
                    #supported run time installation on board. 
                ;;
                "iptables")
                    #default installed.
                ;;
                "kdb")
                    #default installed. 
                ;;
                "kgdb")
                    #default installed. 
                ;;
                "kprobes")
                    #default installed. 
                    # build kprobes test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_kprobes_test.sh $kernel_build_dir
                    cd -
                    popd
                    # copy kprobes test binaries to rootfs
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test  $ROOTFS/usr/local/armor/test_scripts
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test.ko  $ROOTFS/usr/local/armor/test_scripts
                ;;
                "ktap")
                    # build  ktap code and install binaries into rootfs
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_ktap.sh $kernel_build_dir $ROOTFS
                    cd -
                    popd
                ;;
                "latencytop")
                    #supported run time installation on board. 
                ;;
                "lldptool")
                    #supported run time installation on board. 
                ;;
                "lscpu")
                    #default installed. 
                ;;
                "lspci")
                    #supported run time installation on board. 
                ;;
                "ltrace")
                    #default installed. 
                ;;
                "lttng")
                    #supported run time installation on board. 
                ;;
                "memwatch")
                    #memwatch to be integrated to the code to be tested. 
                ;;
                "mkfs")
                    #default installed. 
                ;;
                "mount")
                    #default installed. 
                ;;
                "netstat")
                    #supported run time installation on board. 
                ;;
                "nicstat")
                    #supported run time installation on board. 
                ;;
                "oprofile")
                    #supported run time installation on board. 
                ;;
                "packETHcli")
                    # build packeth cli(command line) tool code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_packETHcli.sh $ROOTFS
                    cd -
                    popd
                ;;
                "perf")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/fedora/numactl-libs-2.0.10-2.fc22.aarch64.rpm >> $LOG_FILE 2>&1
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/fedora/numactl-2.0.10-2.fc22.aarch64.rpm >> $LOG_FILE 2>&1
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/fedora/perf-4.3.4-200.fc22.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "pidstat")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/fedora/sysstat-11.1.2-3.fc22.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "powertop")
                    #supported run time installation on board. 
                ;;
                "procps")
                    #default installed. 
                ;;
                "sar")
                    #supported run time installation on board. 
                ;;
                "sed")
                    #default installed. 
                ;;
                "setpci")
                    #supported run time installation on board. 
                ;;
                "slabtop")
                    #default installed.
                ;;
                "strace")
                    #default installed.
                ;;
                "swapon")
                    #default installed. 
                ;;
                "systemtap")
                    #supported run time installation on board. 
                ;;
                "tail")
                    #default installed. 
                ;;
                "tcpdump")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/fedora/tcpdump-4.7.4-2.fc22.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "tiptop")
                    #supported run time installation on board. 
                ;;
                "top")
                    #default installed. 
                ;;
                "valgrind")
                    #supported run time installation on board. 
                    pushd $armor_build_dir
                    cd build_scripts/
                    # build valgrind test code
                    sh build_valgrind_test.sh $ROOTFS
                    cd -
                    popd
                ;;
                "vmstat")
                    #default installed. 
                ;;
            *)
            ;;
            esac
    fi
    let idx=$idx+1
    install=`jq -r ".tools[$idx].install" $CFGFILE`
done

pushd $armor_build_dir
cd build_scripts/

# build armor utility
sh build_armor_utility.sh $ROOTFS

cd -
popd
} #install_armor_tools_fedora

install_armor_tools_opensuse()
{
    #echo "CFGFILE = $CFGFILE"
    idx=0
    install=`jq -r ".tools[$idx].install" $CFGFILE`
    #echo "install= $install" 
    while [ x"$install" != x"null" ];
    do
        if [ x"yes" = x"$install" ]; then
            name=`jq -r ".tools[$idx].name" $CFGFILE`
            case $name in
                "awk")
                    #default installed. 
                ;;
                "blktrace")
                    #supported run time installation on board. 
                ;;
                "crash")
                    sudo cp $armor_dir/binary/crash   $ROOTFS/usr/bin
                    #default installed. 
                ;;
                "dmidecode")
                    echo "dmidecode"
                    # build demidecode
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_dmidecode.sh $cross_gcc $ROOTFS
                    cd -
                    popd
                    # copy demidecode to rootfs
                    sudo cp $armor_build_dir/source/dmidecode/dmidecode $ROOTFS/usr/bin
                ;;
                "dstat")
                    #supported run time installation on board. 
                ;;
                "du")
                    #default installed. 
                ;;                
                "ethtool")
                    #default installed. 
                ;;
                "ftrace")
                    #default installed. 
                ;;
                "fsck")
                    #default installed. 
                ;;
                "gdb")
                    #supported run time installation on board. 
                ;;
                "gprof")
                    #default installed. 
                    # build gprof test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_gprof_test.sh $ROOTFS
                    cd -
                    popd
                ;;
                "grep")
                    #default installed. 
                ;;
                "iostat")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/pcp-import-iostat2pcp-3.10.4-1.8.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "iotop")
                    #supported run time installation on board. 
                ;;
                "iptables")
                    #default installed. 
                ;;
                "kdb")
                    #default installed. 
                ;;
                "kgdb")
                    #default installed. 
                ;;
                "kprobes")
                    #default installed
                    # build kprobes test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_kprobes_test.sh $kernel_build_dir
                    cd -
                    popd
                    # copy kprobes test binaries to rootfs
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test  $ROOTFS/usr/local/armor/test_scripts
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test.ko  $ROOTFS/usr/local/armor/test_scripts
                ;;
                "ktap")
                    # build  ktap code and install binaries into rootfs
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_ktap.sh $kernel_build_dir $ROOTFS
                    cd -
                    popd
                ;;
                "latencytop")
                    #supported run time installation on board. 
                ;;
                "lldptool")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/lldpad-1.0.1-0.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "lscpu")
                    #default installed. 
                ;;
                "lspci")
                    #default installed. 
                ;;
                "ltrace")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/ltrace-0.7.91-1.3.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "lttng")
                    #TBD. 
                ;;
                "memwatch")
                    #memwatch to be integrated to the code to be tested. 
                ;;
                "mkfs")
                    #default installed. 
                ;;
                "mount")
                    #default installed. 
                ;;
                "netstat")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/nicstat-1.95-0.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "nicstat")
                    #supported run time installation on board.    
                ;;
                "oprofile")
                    #supported run time installation on board. 
                ;;
                "packETHcli")
                    # build packeth cli(command line) tool code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_packETHcli.sh $ROOTFS
                    cd -
                    popd
                ;;
                "perf")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/libslang2-2.3.0-1.2.aarch64.rpm >> $LOG_FILE 2>&1
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/libnuma1-2.0.10-5.1.aarch64.rpm >> $LOG_FILE 2>&1
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/libunwind-1.1-14.1.aarch64.rpm >> $LOG_FILE 2>&1
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/perf-4.4.0-44.1.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "pidstat")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/sysstat-11.0.8-1.1.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "powertop")
                    #supported run time installation on board. 
                ;;
                "procps")
                    #supported run time installation on board. 
                ;;
                "sar")
                    #supported run time installation on board. 
                ;;
                "sed")
                    #default installed. 
                ;;
                "setpci")
                    #default installed. 
                ;;
                "slabtop")
                    #default installed. 
                ;;
                "strace")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/strace-4.11-1.1.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "swapon")
                    #default installed. 
                ;;
                "systemtap")
                    #supported run time installation on board. 
                ;;
                "tail")
                    #default installed. 
                ;;
                "tcpdump")
                    #default installed. 
                ;;
                "tiptop")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscripts --nosignature --root=$ROOTFS -i $armor_build_dir/binary/opensuse/tiptop-2.3-0.aarch64.rpm >> $LOG_FILE 2>&1
                    popd
                ;;
                "top")
                    #default installed. 
                ;;
                "valgrind")
                    #supported run time installation on board. 
                    pushd $armor_build_dir
                    cd build_scripts/
                    # build valgrind test code
                    sh build_valgrind_test.sh $ROOTFS
                    cd -
                    popd
                ;;
                "vmstat")
                    #default installed. 
                ;;
            *)
            ;;
            esac
    fi
    let idx=$idx+1
    install=`jq -r ".tools[$idx].install" $CFGFILE`
done

pushd $armor_build_dir
cd build_scripts/

# build armor utility
sh build_armor_utility.sh $ROOTFS

cd -
popd
} #install_armor_tools_opensuse


install_armor_tools_debian()
{
    #echo "CFGFILE = $CFGFILE"
    idx=0
    install=`jq -r ".tools[$idx].install" $CFGFILE`
    #echo "install= $install" 
    while [ x"$install" != x"null" ];
    do
        if [ x"yes" = x"$install" ]; then
            name=`jq -r ".tools[$idx].name" $CFGFILE`
            case $name in
                "awk")
                    #default installed. 
                ;;
                "blktrace")
                    #supported run time installation on board. 
                ;;
                "crash")
                    sudo cp $armor_dir/binary/crash   $ROOTFS/usr/bin
                    #default installed. 
                ;;
                "dmidecode")
                    echo "dmidecode"
                    # build demidecode
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_dmidecode.sh $cross_gcc $ROOTFS
                    cd -
                    popd
                    # copy demidecode to rootfs
                    sudo cp $armor_build_dir/source/dmidecode/dmidecode $ROOTFS/usr/bin
                ;;
                "dstat")
                    #supported run time installation on board. 
                ;;
                "du")
                    #default installed. 
                ;;                
                "ethtool")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/ethtool_1%3a3.16-1_arm64.deb  >> $LOG_FILE 2>&1
                    popd
                ;;
                "ftrace")
                    #default installed. 
                ;;
                "fsck")
                    #default installed. 
                ;;
                "gdb")
                    #default installed. 
                ;;
                "gprof")
                    #default installed. 
                    # build gprof test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_gprof_test.sh $ROOTFS
                    cd -
                    popd
                ;;
                "grep")
                    #default installed. 
                ;;
                "iostat")
                    #supported run time installation on board. 
                ;;
                "iotop")
                    #supported run time installation on board. 
                ;;
                "iptables")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/iptables_1.4.21-2+b1_arm64.deb >> $LOG_FILE 2>&1
                    popd
                ;;
                "kdb")
                    #default installed. 
                ;;
                "kgdb")
                    #default installed. 
                ;;
                "kprobes")
                    #default installed
                    # build kprobes test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_kprobes_test.sh $kernel_build_dir
                    cd -
                    popd
                    # copy kprobes test binaries to rootfs
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test  $ROOTFS/usr/local/armor/test_scripts
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test.ko  $ROOTFS/usr/local/armor/test_scripts
                ;;
                "ktap")
                    # build  ktap code and install binaries into rootfs
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_ktap.sh $kernel_build_dir $ROOTFS
                    cd -
                    popd
                ;;
                "latencytop")
                    #supported run time installation on board. 
                ;;
                "lldptool")
                    #supported run time installation on board. 
                ;;
                "lscpu")
                    #default installed. 
                ;;
                "lspci")
                    #default installed. 
                ;;
                "ltrace")
                    #default installed.    
                ;;
                "lttng")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/liburcu2_0.8.5-1ubuntu1_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/liblttng-ctl0_2.5.2-1ubuntu1_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/liblttng-ust-ctl2_2.5.1-1ubuntu2_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/lttng-tools_2.5.2-1ubuntu1_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/liblttng-ust0_2.5.1-1ubuntu2_arm64.deb >> $LOG_FILE 2>&1
                    popd
                ;;
                "memwatch")
                    #memwatch to be integrated to the code to be tested. 
                ;;
                "mkfs")
                    #default installed. 
                ;;
                "mount")
                    #default installed. 
                ;;
                "netstat")
                    #supported run time installation on board. 
                ;;
                "nicstat")
                    #supported run time installation on board. 
                ;;
                "oprofile")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/libopagent1_1.0.0-0ubuntu9_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/oprofile_1.0.0-0ubuntu9_arm64.deb >> $LOG_FILE 2>&1 
                    popd
                ;;
                "packETHcli")
                    # build packeth cli(command line) tool code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_packETHcli.sh $ROOTFS
                    cd -
                    popd
                ;;
                "perf")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/linux-tools-common_3.19.0-39.44_all.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/linux-tools-3.19.0-23_3.19.0-23.24_arm64.deb >> $LOG_FILE 2>&1
                    popd                     
                ;;
                "pidstat")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/cron_3.0pl1-127+deb8u1_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/libsensors4_1%3a3.3.5-2_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/exim4-config_4.84-8+deb8u2_all.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/exim4-base_4.84-8+deb8u2_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/exim4-daemon-light_4.84-8+deb8u2_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/bsd-mailx_8.1.2-0.20141216cvs-2_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/exim4_4.84-8+deb8u2_all.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/sysstat_11.0.1-1_arm64.deb >> $LOG_FILE 2>&1 
                    popd
                ;;
                "powertop")
                    #supported run time installation on board. 
                ;;
                "procps")
                    #supported run time installation on board. 
                ;;
                "sar")
                    #supported run time installation on board. 
                ;;
                "sed")
                    #default installed. 
                ;;
                "setpci")
                    #default installed. 
                ;;
                "slabtop")
                     #supported run time installation on board. 
                ;;
                "strace")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/debian/strace_4.9-2_arm64.deb >> $LOG_FILE 2>&1
                    popd
                ;;
                "swapon")
                    #default installed. 
                ;;
                "sysdig")
                    #supported run time installation on board. 
                ;;
                "systemtap")
                    #supported run time installation on board. 
                ;;
                "tail")
                    #default installed. 
                ;;
                "tcpdump")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/libpcap0.8_1.6.2-2_arm64.deb >> $LOG_FILE 2>&1
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/tcpdump_4.6.2-5+deb8u1_arm64.deb >> $LOG_FILE 2>&1
                    popd
                ;;
                "tiptop")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i  $armor_build_dir/binary/debian/tiptop-2.3_arm64.deb >> $LOG_FILE 2>&1
                    popd
                ;;
                "top")
                    #default installed. 
                ;;
                "valgrind")
                    #supported run time installation on board. 
                    pushd $armor_build_dir
                    cd build_scripts/
                    # build valgrind test code
                    sh build_valgrind_test.sh $ROOTFS
                    cd -
                    popd
                ;;
                "vmstat")
                    #default installed. 
                ;;
            *)
            ;;
            esac
    fi
    let idx=$idx+1
    install=`jq -r ".tools[$idx].install" $CFGFILE`
done

pushd $armor_build_dir
cd build_scripts/

# build armor utility
sh build_armor_utility.sh $ROOTFS

cd -
popd
} #install_armor_tools_debian

###################################################################################
###################### Initialise variables ####################
###################################################################################

echo "/packages/armor/build.sh: Platform=$1, distro=$2, rootfs=$3, kernel=$4"

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./armor/build.sh <platform> <distrib> <rootfs> <kernal>" 
    exit
fi

PLATFORM=$1
DISTRO=$2
ROOTFS=$3

armor_dir=armor
build_dir=`pwd`/build/$PLATFORM
armor_build_dir=$build_dir/$armor_dir
kernel_build_dir=$4
pkg_dir=`pwd`/packages
LOG_FILE=$armor_build_dir"/armor_build_log"

#echo "build_dir=$build_dir"
#echo "armor_build_dir=$armor_build_dir"

###################################################################################
############################# Setup host environmenta #############################
###################################################################################
# Detect and dertermine some environment variables
LOCALARCH=`uname -m`
if [ x"$PLATFORM" = x"D01" ]; then
    TARGETARCH="ARM32"
else
    TARGETARCH="ARM64"
fi

if [ x"$TARGETARCH" = x"ARM32" ]; then
    cross_gcc=arm-linux-gnueabihf-gcc
    cross_prefix=arm-linux-gnueabihf
else
    cross_gcc=aarch64-linux-gnu-gcc
    cross_prefix=aarch64-linux-gnu
fi

###################################################################################
############################# Build Armor Tools #############################
###################################################################################
echo "Building and install Armor Tools ..."
# copy armor folder to the build directory 
rm -rf $armor_build_dir
cp -rf $armor_dir $build_dir

# create armor folders in the rootfs    
sudo mkdir $ROOTFS/usr/local/armor/
sudo mkdir $ROOTFS/usr/local/armor/binary
sudo mkdir $ROOTFS/usr/local/armor/build_scripts
sudo mkdir $ROOTFS/usr/local/armor/config
sudo mkdir $ROOTFS/usr/local/armor/source
sudo mkdir $ROOTFS/usr/local/armor/test_scripts


# copy prebuilt binaries to the rootfs

case $DISTRO in
    Ubuntu)
        sudo cp $armor_dir/binary/ubuntu/*.deb   $ROOTFS/usr/local/armor/binary
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_ubuntu.cfg  $ROOTFS/usr/local/armor/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_ubuntu.json
        install_armor_tools_ubuntu
        ;;
    Fedora)
        sudo cp $armor_dir/binary/fedora/*.rpm   $ROOTFS/usr/local/armor/binary
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_fedora.cfg  $ROOTFS/usr/local/armor/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_fedora.json
        install_armor_tools_fedora
        ;;
    OpenSuse)
        sudo cp $armor_dir/binary/opensuse/*.rpm   $ROOTFS/usr/local/armor/binary
        # copy build scripts to the rootfs
        sudo cp $armor_dir/build_scripts/build_lttng_tools_opensuse.sh   $ROOTFS/usr/local/armor/build_scripts/
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_opensuse.cfg  $ROOTFS/usr/local/armor/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_opensuse.json
        install_armor_tools_opensuse
        ;;
    Debian)
        sudo cp $armor_dir/binary/debian/*.deb   $ROOTFS/usr/local/armor/binary
        # copy prebuilt binarieis(not supported in the distribution) to the rootfs
        sudo cp $armor_dir/binary/ltrace   $ROOTFS/usr/bin
        sudo cp $armor_dir/binary/lspci    $ROOTFS/usr/bin
        sudo cp $armor_dir/binary/setpci   $ROOTFS/usr/bin
        sudo cp $armor_dir/binary/lsmod    $ROOTFS/usr/bin
        sudo cp $armor_dir/binary/kmod     $ROOTFS/bin
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_debian.cfg  $ROOTFS/usr/local/armor/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_debian.json
        install_armor_tools_debian
    ;;
    esac

sudo cp -rf $armor_dir/testing/test_scripts   $ROOTFS/usr/local/armor/

exit 0

