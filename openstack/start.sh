#!/bin/bash
#author: WuYanjun
#date: 04/02/2017


#set -x
function get_first_machine
{
    local xtrace
    xtrace=$(set +o | grep xtrace)
    set +o xtrace

    service_machine=
	string_name=$1
	service_machine=$(sed -n "/${string_name}/{n;p}" $ANSIBLE_HOSTS)
	if [[ ${service_machine:0:1} == '[' ]] || [[ x"$service_machine" == x"" ]]; then
	    echo "you must specify the ceph mon servers"
		exit 1
	fi
	result=$(echo ${service_machine} | grep "\[")
	if [[ x"$result" != x"" ]]; then
	    service_machine=$(sed -n "/${string_name}/{ n; s/\[//p; }" $ANSIBLE_HOSTS \
            | awk -F":" '{print $1}')
	fi
	echo $service_machine
    $xtrace
}

function install_openstack
{
    if [ -f /etc/debian_version ]; then
    	openstack_debian_pack=$(dpkg --list | grep erp-openstack)
	if [ x"$openstack_debian_pack" != x""  ] ; then
		sudo apt-get remove -y ${openstack_debian_pack}
	fi
        sudo apt-get install wget
    fi

    if [ -f /etc/redhat-release ]; then
    	openstack_rpm_pack=$(rpm -qa | grep erp-openstack)
	if [ x"$openstack_debian_pack" != x"" ] ; then
	    sudo yum remove -y ${openstack_rpm_pack}
	fi
        sudo yum install wget
    fi

    openstack help >/dev/null 2>&1
    [[ $? -eq 0 ]] && return

    sudo pip install python-openstackclient python-cinderclient  \
        python-keystoneclient python-neutronclient python-novaclient \
        python-glanceclient
}

function openstack_services
{
    source ${LOCAL_ADMIN}
    local xtrace
    xtrace=$(set +o | grep xtrace)
    set -o xtrace

    source ${LOCAL_ADMIN}
    openstack compute service list
    [[ $? -ne 0 ]] && die $LINENO "show compute service list failed"
    openstack network agent list 
    [[ $? -ne 0 ]] && die $LINENO "show network agent list failed"
    openstack volume service list
    [[ $? -ne 0 ]] && die $LINENO "show volume service list failed"
    openstack security group list 
    [[ $? -ne 0 ]] && die $LINENO "show security group list failed"
    openstack security group rule list default
    [[ $? -ne 0 ]] && die $LINENO "show security group rule list failed"
    $xtrace
}

function prepare_openstack_services
{
    source ${LOCAL_ADMIN}
    local xtrace
    xtrace=$(set +o | grep xtrace)
    set -o xtrace

    openstack role create ${project_user}
    [[ $? -ne 0 ]] && die $LINENO "create ${project_user} failed"
    openstack flavor create --ram 512 --disk 10 --vcpus 1 --public m1.tiny
    [[ $? -ne 0 ]] && die $LINENO "create flavor m1.tiny failed"


    wget ${image_address_url}
    [[ $? -ne 0 ]] && die $LINENO "download image file failed"
    openstack image create --public --container-format bare --disk-format \
        qcow2 --file $image_file --property hw_firmware_type=uefi \
        --property os_command_line="console=ttyAMA0" \
        --property hw_disk_bus=scsi --property hw_scsi_model=virtio-scsi \
        --public ${image_name}
    [[ $? -ne 0 ]] && die $LINENO "create image failed"


    neutron net-create ${provider_net} --router:external True \
        --provider:physical_network external --provider:network_type flat
    [[ $? -ne 0 ]] && die $LINENO "create provider net failed"


    neutron subnet-create ${provider_net} --name ${provider_subnet} \
        --allocation-pool start=${provider_net_start_addr},end=${provider_net_stop_addr} \
        --disable-dhcp --gateway ${provider_net_gw_addr} \
        ${provider_net_start_addr%.*}.1/${provider_net_bits}
    [[ $? -ne 0 ]] && die $LINENO "create provider subnet failed"


    openstack project create --domain ${DOMAIN} --description "Demo Project" \
        ${project_name}
    [[ $? -ne 0 ]] && die $LINENO "create demo project failed"
    openstack user create --domain ${DOMAIN} --password ${project_passwd} \
        ${project_name}
    [[ $? -ne 0 ]] && die $LINENO "create demo user failed"
    openstack role add --project ${project_name} --user ${project_name} \
        ${project_user}
    [[ $? -ne 0 ]] && die $LINENO "add role for demo project failed"


    openstack project list
    demo_project_id=$(openstack project show ${project_name} | \
        grep ' id ' | awk '{print $(NF-1)}')
    neutron net-create ${DEMO_NET} --tenant-id ${demo_project_id} \
         --provider:network_type=vxlan
    [[ $? -ne 0 ]] && die $LINENO "create demo network failed"
cat << EOF >${project_name}-admin.rc
#!/usr/bin/env bash

export OS_AUTH_URL=${public_url}:5000/v3

export OS_PROJECT_ID=${demo_project_id}
export OS_PROJECT_NAME="${project_name}"
export OS_USER_DOMAIN_NAME="Default"
if [ -z "$OS_USER_DOMAIN_NAME" ]; then unset OS_USER_DOMAIN_NAME; fi

export OS_USERNAME="${project_name}"
export OS_PASSWORD="${project_passwd}"

export OS_REGION_NAME="RegionOne"
if [ -z "$OS_REGION_NAME" ]; then unset OS_REGION_NAME; fi

export OS_INTERFACE=public
export OS_IDENTITY_API_VERSION=3
EOF
    cp -f ${project_name}-admin.rc ~/
    source ${project_name}-admin.rc
    neutron subnet-create ${DEMO_NET} --name ${DEMO_SUBNET} --gateway \
        ${demo_net_gw_addr} ${demo_net_gw_addr%.*}.0/${demo_net_bits}
    [[ $? -ne 0 ]] && die $LINENO "create demo subnet failed"
    neutron router-create ${DEMO_ROUTER}
    [[ $? -ne 0 ]] && die $LINENO "create demo router failed"
    neutron router-interface-add ${DEMO_ROUTER} ${DEMO_SUBNET} 
    [[ $? -ne 0 ]] && die $LINENO "add interface for demo router failed"
    neutron router-gateway-set ${DEMO_ROUTER} ${provider_net}
    [[ $? -ne 0 ]] && die $LINENO "create gw for demo router failed"
    neutron router-port-list  ${DEMO_ROUTER} # just for sanity
    [[ $? -ne 0 ]] && die $LINENO "show port list failed"
    output=$(ssh `whoami`@${network_server} "ip netns")
    result1=$(echo $output | grep qrouter)
    result2=$(echo $output | grep qdhcp)
    if [ x"$result1" = x"" ] || [ x"$result2" = x"" ]; then
        die $LINENO "the namespace in the network node are not present properly"
    fi
    $xtrace
}

function launch_an_instance
{
    source ${project_name}-admin.rc
    local xtrace
    xtrace=$(set +o | grep xtrace)
    set -o xtrace

    image_id=$(openstack image list | grep ${image_name} | awk '{print $2}')
    flavor_name=$(openstack flavor list | grep True | awk '{print $4}')
    openstack keypair create ${key_name} > ${key_name}.pem
    chmod 600 ${key_name}.pem
    openstack security group rule create --proto icmp ${DOMAIN}
    openstack security group rule create --proto tcp --dst-port 22 ${DOMAIN}
    openstack security group list
    net_id=$(neutron net-list | grep ${DEMO_NET} | awk '{print $2}')
    openstack server create --flavor ${flavor_name} --image ${image_id} \
        --nic net-id=${net_id} --security-group ${DOMAIN} \
        --key-name ${key_name} ${instance_name}
    [[ $? -ne 0 ]] && die $LINENO "create an instance failed"
    sleep 100

    instance_run=$(openstack server list | grep Running)
    if [ x"$instance_run" == x"" ]; then
        instance_error=$(openstack server list | grep ERROR)
        if [ x"$instance_error" != x""  ]; then
            echo "Launch the instance ERROR. Please check the log file"
            exit 1
        fi
    fi
    openstack server list
    [[ $? -ne 0 ]] && die $LINENO "list instances failed"
    vm_ip=`nova list | grep Running | awk -F '=| ' '{print $(NF-1)}'`
    qroute=$(ssh `whoami`@${network_server} "ip netns | grep route")
    [[ -n $qroute ]] || die $LINENO "Does not have a qroute namspace"
    ping_result_1=$(ssh `whoami`@${network_server} "ip netns exec \
        $qroute ping -c 4 $vm_ip" | grep " 0% packet loss")

    qdhcp=$(ssh `whoami`@${network_server} "ip netns | grep dhcp")
    [[ -n $qdhcp ]] || die $LINENO "Does not have a qdhcp namspace"
    ping_result_2=$(ssh `whoami`@${network_server} "ip netns exec \
        $qdhcp ping -c 4 $vm_ip" | grep " 0% packet loss")
    if [[ x"$ping_result_1" = x"" ]] || [[ x"$ping_result_2" = x"" ]]; then
        die $LINENO "from the network node's namespace, the instance' \
        fixed ip can not be accessed"
    fi

    float_ip=$(openstack floating ip create ${provider_net} | \
        grep floating_ip_address | awk '{print $(NF-1)}')
    if [[ x"$float_ip" = x"" ]]; then
        die $LINENO "Can not get a floating ip from the allocate ip pool"
    fi
    openstack server add floating ip ${instance_name} ${float_ip}
    [[ $? -ne 0 ]] && die $LINENO "Can not attach the float ip to the instance"
    ping_float_ip=$(ssh `whoami`@${network_server} "ping -c 4 ${float_ip}" | \
        grep " 0% packet loss")
    if [[ x"$ping_float_ip" = x"" ]]; then
        die $LINENO "The instance floating ip cannot be accessed"
    fi
    $xtrace
    echo "Please use the \"ssh cirros@${float_ip}\" to login the vm, \
        the password is \"gocubsgo\""
}

if [ -f /etc/debian_version ]; then
    sudo apt-get -q=2 update
    sudo apt-get install -y git libyaml-dev libxml2-dev libxslt1-dev \
        libmysqlclient-dev libffi-dev libssl-dev libvirt-dev python-dev \
        pkg-config libvirt-dev python-virtualenv git python-setuptools \
        python-jinja2 python-yaml
fi

if [ -f /etc/redhat-release ]; then
    CMD=yum

    if [ -f /etc/fedora-release ]; then
	CMD=dnf
    fi

    sudo $CMD install -y gcc make libyaml-devel libxml2-devel libxslt-devel \
        mysql-devel libffi-devel openssl-devel libvirt-devel python-devel \
        pkgconfig python-virtualenv git python-setuptools python-jinja2 \
        python-yaml
fi

sudo easy_install pip
sudo pip install -U pip
sudo pip install ansible paramiko PyYAML Jinja2 httplib2 six
sudo pip install -U  ansible

filepath=$(cd "$(dirname "$0")"; pwd)

pushd ${filepath}/openstack-ref-architecture
    install_dir=$PWD

    export ANSIBLE_HOSTS=$install_dir/ansible/hosts

	if [ -f /etc/redhat-release ] || [ -f /etc/fedora-release ]; then
	    ansible all -a "sudo iptables -F" --sudo
    	ansible all -a "sudo setenforce 0" --sudo
	    ansible all -a "sudo service NetworkManager stop" --sudo
	    #ansible all -m sudo wget http://repo.linaro.org/rpm/linaro-staging/centos-7/linaro-staging.repo \
            #-O /etc/yum.repos.d/linaro-staging.repo
	    ansible all -a "sudo wget http://repo.linaro.org/rpm/linaro-overlay/centos-7/linaro-overlay.repo \
            -O /etc/yum.repos.d/linaro-overlay.repo" --sudo
            [ $? -ne 0 ] || die $LINENO "The CentOS source cannot be accessed"
	    ansible all -a "sudo yum update -y" --sudo
            [ $? -ne 0 ] || die $LINENO "The CentOS cannot be updated"
	fi

    if [ -f /etc/debian_version ]; then
	    ansible all -a "sudo iptables -F" --sudo
        ansible all -m shell -a "sudo echo deb http://people.linaro.org/~marcin.juszkiewicz/test/debian-venvs-packages/  ./ > /etc/apt/sources.list.d/test-sdi-venvs.list " --sudo
        ansible all -m shell -a 'sudo echo "APT::Get::AllowUnauthenticated 1;" >> /etc/apt/apt.conf.d/70debconf' --sudo
        ansible all -m shell -a "sudo echo deb http://repo.linaro.org/debian/erp-16.12-stable jessie main >> /etc/apt/sources.list" --sudo
        ansible all -m shell -a "sudo echo deb-src http://repo.linaro.org/debian/erp-16.12-stable jessie main  >> /etc/apt/sources.list" --sudo
        ansible all -m shell -a "sudo echo deb http://ftp.debian.org/debian jessie-backports main  >> /etc/apt/sources.list" --sudo
	ansible all -m shell -a "sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys E13D88F7E3C1D56C"
        [ $? -ne 0 ] || die $LINENO "The Debian key cannot be installed"
        ansible all -m shell -a "sudo apt-get update -y" --sudo
        [ $? -ne 0 ] || die $LINENO "The Debian cannot be updated"
        ansible all -m shell -a "sudo apt install -t jessie-backports systemd systemd-sysv libpam-systemd -y" --sudo
        [ $? -ne 0 ] || die $LINENO "The Debian cannot update systemd"
    fi

  pushd ansible
	##############################
	# deploy the ceph mon services
	##############################
	ceph_mon_server=`get_first_machine ceph_monitor_servers`
	if ! ansible-playbook -K -v -i ./hosts ./site.yml --tags ceph-mon; then
		echo "Ceph MON installation failed"
		exit 1
	fi
	ceph_mon_status=$(ssh `whoami`@$ceph_mon_server "ceph -s")
	ceph_mon_result=$(echo $ceph_mon_status | grep -E 'health| monmap')
	if [[ x"$ceph_mon_result" == x"" ]]; then
		echo "Ceph MON installation failed"
		exit 1
	fi

	##############################
	# deploy the ceph osd services
	##############################
	ceph_osd_server=`get_first_machine ceph_osd_servers`
	if ! ansible-playbook -K -v -i ./hosts ./site.yml --tags ceph-osd; then
		echo "Ceph OSD installation failed"
		exit 1
	fi
	sleep 60
	ceph_osd_result=$(ssh `whoami`@${ceph_osd_server} "ceph osd tree")
	ceph_osd_result=$(ssh `whoami`@${ceph_osd_server} "ceph -s")
	ceph_osd_tree=$(echo ${ceph_osd_result} | grep 'HEALTH_ERR')
	if [[ x"$ceph_osd_tree" != x"" ]]; then
		echo "Ceph OSD installation failed"
		exit 1
	fi

	if ! ansible-playbook -K -v -i ./hosts ./site.yml; then 
		echo "OpenStack installation failed"
		exit 1	
	fi
  popd
popd

##############################
# get the nova-admin.rc file
##############################
unset OS_PROJECT_ID
unset OS_PROJECT_NAME
unset OS_USER_DOMAIN_NAME
unset OS_USERNAME
unset OS_PASSWORD
unset OS_REGION_NAME
unset OS_INTERFACE
unset OS_IDENTITY_API_VERSION
adminrc_path=$(find $filepath -name  'nova-admin.rc')

LOCAL_PATH=$PWD
LOCAL_ADMIN=$PWD/nova-admin.rc
cp ${adminrc_path}  ${LOCAL_PATH}
keystone_admin_passwd=$(find . -name 'deployment-vars' | xargs cat | \
grep 'keystone_admin_pass:' | awk '{print $NF}')
public_url=http://$(find . -name 'deployment-vars' | xargs cat | \
    grep 'public_api_host:' | grep -o '[0-9.]\+')
sed -i "s/{{keystone_admin_pass}}/${keystone_admin_passwd}/g" ${LOCAL_ADMIN}
sed -i "s#{{public_api_host}}#${public_url}#g" ${LOCAL_ADMIN}
cat ${LOCAL_ADMIN}
network_server=`get_first_machine networking_servers`
scp ${LOCAL_ADMIN}  `whoami`@${network_server}:~
cp -f ${LOCAL_ADMIN}  ~/

image_file="cirros-d161201-aarch64-disk.img"

source $filepath/openstack_cfg.sh
source $filepath/common.sh

install_openstack 
openstack_services
prepare_openstack_services
launch_an_instance

