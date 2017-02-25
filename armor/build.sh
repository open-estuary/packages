#!/bin/bash
#author: Justin Zhao
#date: 31/10/2015
#author: Shiju Jose
#date: 05/11/2015
#author: Huang Jinhua
#date: 02/04/2017
#description: Armor build script
#$1: target output directory
#$2: target distributions name
#$3: target rootfs directory(absolutely)
#$4: kernel build directory(absolutely)
#return: 0: build success, other: failed

build_armor_tools_ubuntu()
{
    echo "CFGFILE = $CFGFILE"
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
                    sudo cp $armor_dir/binary/crash   $armor_result_dir/bin/
                    #default installed. 
                ;;
                "dmidecode")
                    echo "dmidecode"
                    # build demidecode
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_dmidecode.sh $cross_gcc $armor_result_dir
                    cd -
                    popd
                    # copy demidecode to rootfs
                    sudo cp $armor_build_dir/source/dmidecode/dmidecode $armor_result_dir/bin
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
                    #could be installed via apt-get  
                ;;
                "gprof")
                    #default installed. 
                    # build gprof test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_gprof_test.sh "${CROSS}" $armor_result_dir
                    cd -
                    popd
                ;;
                "grep")
                    #default installed. 
                ;;
                "iostat")
                    #could be installed via 'apt-get install sysstat' 
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
                    sh build_kprobes_test.sh "${CROSS}" $kernel_build_dir $armor_result_dir
                    cd -
                    popd
                    # copy kprobes test binaries to rootfs
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test  $armor_result_dir/test_scripts
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test.ko  $armor_result_dir/test_scripts
                ;;
                "ktap")
                    # build  ktap code and install binaries into rootfs
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_ktap.sh "${CROSS}" $kernel_build_dir $armor_result_dir
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
                    sh build_lttng.sh "${CROSS}"  $kernel_build_dir $armor_result_dir
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
                    sh build_packETHcli.sh $armor_result_dir
                    cd -
                    popd
                ;;
                "perf")
                    # copy prebuilt perf and dependent binaries to the rootfs and extract
                    sudo cp $armor_build_dir/binary/perf/*.gz   ${armor_result_dir}/packages/
                ;;
                "pidstat")
                    # could be installed via "apt-get install sysstat"
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
                    #could be installed via "apt-get install tiptop"
                ;;
                "top")
                    #default installed. 
                ;;
                "valgrind")
                    #supported run time installation on board. 
                    pushd $armor_build_dir
                    cd build_scripts/
                    # build valgrind test code
                    sh build_valgrind_test.sh "${CROSS}" $armor_result_dir
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
sh build_armor_utility.sh "${CROSS}" $armor_result_dir

cd -
popd
} #build_armor_tools_ubuntu

build_armor_tools_fedora()
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
                    sh build_dmidecode.sh $cross_gcc $armor_result_dir
                    cd -
                    popd
                    # copy demidecode to rootfs
                    sudo cp $armor_build_dir/source/dmidecode/dmidecode $armor_result_dir/bin
                ;;
                "dstat")
                    #supported run time installation on board. 
                ;;
                "df")
                    #default installed. 
                ;;
                "ethtool")
                    #Ethtool need to be improved later
                    #sudo cp $armor_dir/binary/fedora/ethtool*.rpm ${armor_result_dir}/packages
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
                    #could be installed via "yum install sysstat" 
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
                    sh build_kprobes_test.sh "${CROSS}" $kernel_build_dir $armor_result_dir
                    cd -
                    popd
                    # copy kprobes test binaries to rootfs
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test  $armor_result_dir/test_scripts
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test.ko  $armor_result_dir/test_scripts
                ;;
                "ktap")
                    # build  ktap code and install binaries into rootfs
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_ktap.sh "${CROSS}" $kernel_build_dir $armor_result_dir
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
                    # build lttng kernel module and lttng uspace test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_lttng.sh "${CROSS}"  $kernel_build_dir $armor_result_dir
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
                    sh build_packETHcli.sh $armor_result_dir
                    cd -
                    popd
                ;;
                "perf")
                    # copy prebuilt perf and dependent binaries to the rootfs and extract
                    sudo cp $armor_dir/binary/perf/*.gz   $armor_result_dir/packages
                ;;
                "pidstat")
                    # could be installed via "yum install sysstat" 
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
                    #could be installed via "yum install" directly 
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
                    sh build_valgrind_test.sh "${CROSS}" $armor_result_dir
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
sh build_armor_utility.sh "${CROSS}" $armor_result_dir

cd -
popd
} #build_armor_tools_fedora

build_armor_tools_opensuse()
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
                    sudo cp $armor_dir/binary/crash   $armor_result_dir/bin
                    #default installed. 
                ;;
                "dmidecode")
                    echo "dmidecode"
                    # build demidecode
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_dmidecode.sh $cross_gcc $armor_result_dir
                    cd -
                    popd
                    # copy demidecode to rootfs
                    sudo cp $armor_build_dir/source/dmidecode/dmidecode $armor_result_dir/bin
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
                    sh build_gprof_test.sh "${CROSS}" $armor_result_dir
                    cd -
                    popd
                ;;
                "grep")
                    #default installed. 
                ;;
                "iostat")
                    #could be installed directly via "yum instal pcp-import-iostat"
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
                    sh build_kprobes_test.sh "${CROSS}" $kernel_build_dir $armor_result_dir
                    cd -
                    popd
                    # copy kprobes test binaries to rootfs
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test  $armor_result_dir/test_scripts
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test.ko  $armor_result_dir/test_scripts
                ;;
                "ktap")
                    # build  ktap code and install binaries into rootfs
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_ktap.sh "${CROSS}" $kernel_build_dir $armor_result_dir
                    cd -
                    popd
                ;;
                "latencytop")
                    #supported run time installation on board. 
                ;;
                "lldptool")
                    #could be installed via "yum install" directly
                ;;
                "lscpu")
                    #default installed. 
                ;;
                "lspci")
                    #default installed. 
                ;;
                "ltrace")
                    #could be installed via "yum install" directly
                ;;
                "lttng")
                    # build lttng kernel module and lttng uspace test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_lttng.sh "${CROSS}"  $kernel_build_dir $armor_result_dir
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
                    #could be installed via "yum install nicstat" directly 
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
                    sh build_packETHcli.sh $armor_result_dir
                    cd -
                    popd
                ;;
                "perf")
                    # copy prebuilt perf and dependent binaries to the rootfs and extract
                    sudo cp $armor_dir/binary/perf/*.gz   $armor_result_dir/packages
                ;;
                "pidstat")
                    # could be installed via "yum install" directly
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
                    #could be installed via "yum install" directly
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
                    #could be installed via "yum install" directly 
                ;;
                "top")
                    #default installed. 
                ;;
                "valgrind")
                    #supported run time installation on board. 
                    pushd $armor_build_dir
                    cd build_scripts/
                    # build valgrind test code
                    sh build_valgrind_test.sh "${CROSS}" $armor_result_dir
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
sh build_armor_utility.sh "${CROSS}" $armor_result_dir

cd -
popd
} #build_armor_tools_opensuse


build_armor_tools_debian()
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
                    sudo cp $armor_dir/binary/crash   $armor_result_dir/bin
                    #default installed. 
                ;;
                "dmidecode")
                    echo "dmidecode"
                    # build demidecode
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_dmidecode.sh $cross_gcc $armor_result_dir
                    cd -
                    popd
                    # copy demidecode to rootfs
                    sudo cp $armor_build_dir/source/dmidecode/dmidecode $armor_result_dir/bin
                ;;
                "dstat")
                    #supported run time installation on board. 
                ;;
                "du")
                    #default installed. 
                ;;                
                "ethtool")
                    #could be installed via "apt-get install" directly
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
                    sh build_gprof_test.sh "${CROSS}" $armor_result_dir
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
                    #could be installed via "apt-get install" directly
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
                    sh build_kprobes_test.sh "${CROSS}" $kernel_build_dir $armor_result_dir
                    cd -
                    popd
                    # copy kprobes test binaries to rootfs
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test  $armor_result_dir/test_scripts
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test.ko  $armor_result_dir/test_scripts
                ;;
                "ktap")
                    # build  ktap code and install binaries into rootfs
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_ktap.sh "${CROSS}" $kernel_build_dir $armor_result_dir
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
                    sudo cp $armor_dir/binary/lspci    ${armor_result_dir}/bin
                ;;
                "ltrace")
                    sudo cp $armor_dir/binary/ltrace   ${armor_result_dir}/bin
                ;;
                "lttng")
                    # build lttng kernel module and lttng uspace test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_lttng.sh "${CROSS}"  $kernel_build_dir $armor_result_dir
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
                    #supported run time installation on board. 
                ;;
                "nicstat")
                    #supported run time installation on board. 
                ;;
                "oprofile")
                    #could be installed via "apt-get install" directly
                ;;
                "packETHcli")
                    # build packeth cli(command line) tool code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_packETHcli.sh $armor_result_dir
                    cd -
                    popd
                ;;
                "perf")
                    # copy prebuilt perf and dependent binaries to the rootfs and extract
                    sudo cp $armor_dir/binary/perf/*.gz   $armor_result_dir/packages
                    ;;
                "pidstat")
                    # could be installed via "apt-get install" directly
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
                    sudo cp $armor_dir/binary/setpci   ${armor_result_dir}/bin
                ;;
                "slabtop")
                    #supported run time installation on board. 
                ;;
                "strace")
                    #could be installed via "apt-get install" directly
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
                    #could be installed via "apt-get install" directly
                ;;
                "tiptop")
                    #could be installed via "apt-get install" directly
                ;;
                "top")
                    #default installed. 
                ;;
                "valgrind")
                    #supported run time installation on board. 
                    pushd $armor_build_dir
                    cd build_scripts/
                    # build valgrind test code
                    sh build_valgrind_test.sh "${CROSS}" $armor_result_dir
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
sh build_armor_utility.sh "${CROSS}" $armor_result_dir

cd -
popd
} #build_armor_tools_debian

build_armor_tools_centos()
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
                    sh build_dmidecode.sh $cross_gcc $armor_result_dir
                    cd -
                    popd
                    # copy demidecode to rootfs
                    sudo cp $armor_build_dir/source/dmidecode/dmidecode $armor_result_dir/bin
                ;;
                "dstat")
                    #supported run time installation on board. 
                ;;
                "df")
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
                    #default installed. 
                ;;
                "gprof")
                    #default installed. 
                ;;
                "grep")
                    #default installed. 
                ;;
                "iostat")
                    #could be install via "yum install pcp-import-iostat2pcp" directly
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
                    sh build_kprobes_test.sh "${CROSS}" $kernel_build_dir $armor_result_dir
                    cd -
                    popd
                    # copy kprobes test binaries to rootfs
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test  $armor_result_dir/test_scripts
                    sudo cp $armor_build_dir/source/test_code/kprobes_test_code/kprobe_test.ko  $armor_result_dir/test_scripts
                ;;
                "ktap")
                    # build  ktap code and install binaries into rootfs
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_ktap.sh "${CROSS}" $kernel_build_dir $armor_result_dir
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
                    # build lttng kernel module and lttng uspace test code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_lttng.sh "${CROSS}"  $kernel_build_dir $armor_result_dir
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
                    #supported run time installation on board. 
                ;;
                "nicstat")
                    #could be installed via "yum install nicstat" directly
                ;;
                "oprofile")
                    #supported run time installation on board. 
                ;;
                "packETHcli")
                    # build packeth cli(command line) tool code
                    pushd $armor_build_dir
                    cd build_scripts/
                    sh build_packETHcli.sh $armor_result_dir
                    cd -
                    popd
                ;;
                "perf")
                    # copy prebuilt perf and dependent binaries to the rootfs and extract
                    sudo cp $armor_dir/binary/perf/*.gz   $armor_result_dir/packages
                ;;
                "pidstat")
                    #could be installed via "yum install sysstat" directly
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
                    #could be installed via "yum install tcpdump" directly
                ;;
                "tiptop")
                    #could be installed via "yum install tiptop" directly
                ;;
                "top")
                    #default installed. 
                ;;
                "valgrind")
                    #supported run time installation on board. 
                    pushd $armor_build_dir
                    cd build_scripts/
                    # build valgrind test code
                    sh build_valgrind_test.sh "${CROSS}" $armor_result_dir
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
sh build_armor_utility.sh "${CROSS}" $armor_result_dir

cd -
popd
} #build_armor_tools_centos


###################################################################################
###################### Initialise variables ####################
###################################################################################

if [ "$1" = '' ] || [ "$2" = '' ] ||  [ "$3" = '' ]  || [ "$4" = '' ]; then
    echo "Invalid parameter passed. Usage ./armor/build.sh <outputdir> <distrib> <rootfs> <kernal>" 
    exit
fi

OUTPUT_DIR=$(cd $1; pwd)
DISTRO=$2
ROOTFS=$3

KERNEL_DIR=$(cd $4; pwd)
CROSS=$5  # such as aarch64-linux-gnu- on X86 platform or "" on ARM64 platform
PACK_TYPE=$6 # such as "tar", "rpm", "deb" or "all"
PACK_SAVE_DIR=$(cd $7; pwd) #
INSTALL_DIR=$8

armor_dir=`pwd`/armor
armor_build_dir=${OUTPUT_DIR}/src/
armor_result_dir=${OUTPUT_DIR}/result
kernel_build_dir=${KERNEL_DIR}
pkg_dir=`pwd`/packages
LOG_FILE=$armor_build_dir"/armor_build_log"


###################################################################################
############################# Setup host environmenta #############################
###################################################################################
cross_gcc="${CROSS}"gcc
cross_prefix="${CROSS}"

###################################################################################
############################# Build Armor Tools #############################
###################################################################################
echo "Begin to build Armor Tools ..."
if [ ! -z "$(ls ${PACK_SAVE_DIR}/armor*.tar.gz 2>/dev/null)" ] ; then
    echo "Armor tools have been built successfully before"
    exit 0
fi

# copy armor folder to the build directory 
if [ ! -d ${armor_build_dir} ] ; then
    mkdir -p ${armor_build_dir}
fi

if [ ! -d ${armor_result_dir} ] ; then
    mkdir -p ${armor_result_dir}
fi

cp -rf $armor_dir/* $armor_build_dir

# create armor folders 
if [ ! -d ${armor_result_dir}/packages ] ; then
    mkdir -p ${armor_result_dir}/packages
fi

if [ ! -d ${armor_result_dir}/bin ] ; then
    mkdir -p ${armor_result_dir}/bin
fi

if [ ! -d ${armor_result_dir}/usr/bin ] ; then
    mkdir -p ${armor_result_dir}/usr/bin
fi

if [ ! -d ${armor_result_dir}/build_scripts ] ; then
    mkdir -p ${armor_result_dir}/build_scripts
fi

if [ ! -d ${armor_result_dir}/config ] ; then
    mkdir -p ${armor_result_dir}/config
fi

if [ ! -d ${armor_result_dir}/source ] ; then
    mkdir -p ${armor_result_dir}/source
fi

if [ ! -d ${armor_result_dir}/test_scripts ] ; then
    mkdir -p ${armor_result_dir}/test_scripts
fi

kernel_version=$(sudo cat $kernel_build_dir/include/config/kernel.release)
echo "kernel_version=$kernel_version"
sudo mkdir $ROOTFS/lib/modules/$kernel_version/armor

if [ ! -d ${armor_result_dir}/lib/modules/$kernel_version/armor ] ; then
    mkdir -p ${armor_result_dir}/lib/modules/$kernel_version/armor
fi
# copy prebuilt binaries to the rootfs

case $DISTRO in
    Ubuntu)
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_ubuntu.cfg  ${armor_result_dir}/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_ubuntu.json
        build_armor_tools_ubuntu
        ;;
    Fedora)
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_fedora.cfg  ${armor_result_dir}/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_fedora.json
        build_armor_tools_fedora
        ;;
    OpenSuse)
        # copy build scripts to the rootfs
        sudo cp $armor_dir/build_scripts/build_lttng_tools_opensuse.sh   ${armor_result_dir}/build_scripts/
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_opensuse.cfg  ${armor_result_dir}/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_opensuse.json
        build_armor_tools_opensuse
        ;;
    Debian)
        # copy prebuilt binarieis(not supported in the distribution) to the rootfs
        sudo cp $armor_dir/binary/lsmod    ${armor_result_dir}/bin
        sudo cp $armor_dir/binary/kmod     ${armor_result_dir}/bin
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_debian.cfg  ${armor_result_dir}/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_debian.json
        build_armor_tools_debian
    ;;
    CentOS)
        sudo cp $armor_dir/source/armor_utility/cfg/armor_pkg_info_centos.cfg  ${armor_result_dir}/config/armor_pkg_info.cfg
        CFGFILE=$pkg_dir/armor/armorcfg_centos.json
        build_armor_tools_centos
    ;; 

    esac

sudo cp -rf $armor_dir/testing/test_scripts   $armor_result_dir/test_scripts/

if [ x"${PACK_TYPE}" == x"tar" ] || [ x"${PACK_TYPE}" == x"all" ] ; then
    #Generate the corresponding files under the specifed directory
    pushd ${armor_result_dir}
    cp ${pkg_dir}/armor/setup.sh ${armor_result_dir}/
    cp ${pkg_dir}/armor/remove.sh ${armor_result_dir}/
    sudo tar -czf ${build_dir}/armor.tar.gz .
    popd > /dev/null
    sudo cp ${build_dir}/armor.tar.gz ${PACK_SAVE_DIR}/
fi

exit 0

