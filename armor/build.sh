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
    echo "install= $install" 
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
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/libpython3.4_3.4.3-3_arm64.deb
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/libc6-dbg_2.21-0ubuntu4_arm64.deb
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/libc6-dev_2.21-0ubuntu4_arm64.deb
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/gdbserver_7.9-1ubuntu1_arm64.deb
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/gdb_7.9-1ubuntu1_arm64.deb
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
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/libsensors4_1%3a3.3.5-2_arm64.deb    
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/sysstat_11.0.1-1_arm64.deb 
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
                "perf")
                    pushd $ROOTFS
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/libdw1_0.160-0ubuntu3_arm64.deb
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/libunwind8_1.1-3.2_arm64.deb
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/linux-tools-common_3.19.0-39.44_all.deb
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/linux-tools-3.19.0-23_3.19.0-23.24_arm64.deb
                    popd
                ;;
                "pidstat")
                    pushd $ROOTFS
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/libsensors4_1%3a3.3.5-2_arm64.deb    
                    sudo dpkg --force-all --root=$ROOTFS -i $armor_build_dir/binary/deb/sysstat_11.0.1-1_arm64.deb 
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
                    sudo dpkg --force-architecture --root=$ROOTFS -i $armor_build_dir/binary/deb/tiptop-2.3_arm64.deb
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
} #install_armor_tools_ubuntu

install_armor_tools_fedora()
{
    echo "CFGFILE = $CFGFILE"
    idx=0
    install=`jq -r ".tools[$idx].install" $CFGFILE`
    echo "install= $install" 
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
                    #supported run time installation on board. 
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
                    #supported run time installation on board. 
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
                "perf")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscript --root=$ROOTFS -i $armor_build_dir/binary/rpm/perf-4.0.4-301.fc22.aarch64.rpm
                    popd
                ;;
                "pidstat")
                    #default installed. 
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
                    #supported run time installation on board. 
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
    echo "CFGFILE = $CFGFILE"
    idx=0
    install=`jq -r ".tools[$idx].install" $CFGFILE`
    echo "install= $install" 
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
                    #supported run time installation on board. 
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
                    sudo rpm --force --nodeps --ignorearch --noscript --root=$ROOTFS -i usr/local/armor/binary/rpm/lldpad-1.0.1-0.aarch64.rpm
                    popd
                ;;
                "lscpu")
                    #default installed. 
                ;;
                "lspci")
                    #default installed. 
                ;;
                "ltrace")
                    #supported run time installation on board.    
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
                    #supported run time installation on board. 
                ;;
                "nicstat")
                    pushd $ROOTFS
                    sudo rpm --force --nodeps --ignorearch --noscript --root=$ROOTFS -i usr/local/armor/binary/rpm/nicstat-1.95-0.aarch64.rpm
                    popd 
                ;;
                "oprofile")
                    #supported run time installation on board. 
                ;;
                "perf")
                    #supported run time installation on board. 
                ;;
                "pidstat")
                    #supported run time installation on board. 
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
                    #supported run time installation on board. 
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
                    sudo rpm --force --nodeps --ignorearch --noscript --root=$ROOTFS -i usr/local/armor/binary/rpm/tiptop-2.3-0.aarch64.rpm
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
    echo "CFGFILE = $CFGFILE"
    idx=0
    install=`jq -r ".tools[$idx].install" $CFGFILE`
    echo "install= $install" 
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
                    #supported run time installation on board. 
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
                    #supported run time installation on board. 
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
                    sudo dpkg --force-architecture --root=$ROOTFS -i usr/local/armor/binary/deb/liburcu2_0.8.5-1ubuntu1_arm64.deb
                    sudo dpkg --force-architecture --root=$ROOTFS -i usr/local/armor/binary/deb/liblttng-ctl0_2.5.2-1ubuntu1_arm64.deb
                    sudo dpkg --force-architecture --root=$ROOTFS -i usr/local/armor/binary/deb/liblttng-ust-ctl2_2.5.1-1ubuntu2_arm64.deb
                    sudo dpkg --force-architecture --root=$ROOTFS -i usr/local/armor/binary/deb/lttng-tools_2.5.2-1ubuntu1_arm64.deb
                    sudo dpkg --force-architecture --root=$ROOTFS -i usr/local/armor/binary/deb/liblttng-ust0_2.5.1-1ubuntu2_arm64.deb
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
                    sudo dpkg --force-architecture --root=$ROOTFS -i usr/local/armor/binary/deb/libopagent1_1.0.0-0ubuntu9_arm64.deb
                    sudo dpkg --force-architecture --root=$ROOTFS -i usr/local/armor/binary/deb/oprofile_1.0.0-0ubuntu9_arm64.deb
                    popd
                ;;
                "perf")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i usr/local/armor/binary/deb/linux-tools-common_3.19.0-39.44_all.deb
                    sudo dpkg --force-architecture --root=$ROOTFS -i usr/local/armor/binary/deb/linux-tools-3.19.0-23_3.19.0-23.24_arm64.deb
                    popd                     
                ;;
                "pidstat")
                    #supported run time installation on board. 
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
                    #supported run time installation on board. 
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
                    #supported run time installation on board. 
                ;;
                "tiptop")
                    pushd $ROOTFS
                    sudo dpkg --force-architecture --root=$ROOTFS -i usr/local/armor/deb/binary/tiptop-2.3_arm64.deb
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
        sudo cp $armor_dir/binary/deb/*.deb   $ROOTFS/usr/local/armor/binary
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_ubuntu.cfg  $ROOTFS/usr/local/armor/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_ubuntu.json
        install_armor_tools_ubuntu
        ;;
    Fedora)
        sudo cp $armor_dir/binary/rpm/*.rpm   $ROOTFS/usr/local/armor/binary
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_fedora.cfg  $ROOTFS/usr/local/armor/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_fedora.json
        install_armor_tools_fedora
        ;;
    OpenSuse)
        sudo cp $armor_dir/binary/rpm/*.rpm   $ROOTFS/usr/local/armor/binary
        # copy build scripts to the rootfs
        sudo cp $armor_dir/build_scripts/build_lttng_tools_opensuse.sh   $ROOTFS/usr/local/armor/build_scripts/
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_opensuse.cfg  $ROOTFS/usr/local/armor/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_opensuse.json
        install_armor_tools_opensuse
        ;;
    Debian)
        sudo cp $armor_dir/binary/deb/*.deb   $ROOTFS/usr/local/armor/binary
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

