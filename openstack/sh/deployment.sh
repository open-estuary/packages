#!/bin/bash
#author: WuYanjun
#date: 04/02/2017

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
    $xtrace
}

function prepare_openstack_services
{
    source ${LOCAL_ADMIN}
    local xtrace
    xtrace=$(set +o | grep xtrace)
    set -o xtrace
    

    if [ ! "$(openstack role show ${project_user} 2>/dev/null)" ]; then
        openstack role create ${project_user}
        [[ $? -ne 0 ]] && die $LINENO "create ${project_user} failed"
    fi
    if [ ! "$(openstack flavor show m1.tiny 2>/dev/null)" ]; then
        openstack flavor create --ram 512 --disk 10 --vcpus 1 --public m1.tiny
        [[ $? -ne 0 ]] && die $LINENO "create flavor m1.tiny failed"
    fi
    
    if [ ! -f ${image_file} ]; then
        wget ${image_address_url}
        [[ $? -ne 0 ]] && die $LINENO "download image file failed"
    fi
    if [ ! "$(openstack image show ${image_name} 2>/dev/null)" ]; then
        openstack image create --public --container-format bare --disk-format \
            qcow2 --file $image_file --property hw_firmware_type=uefi \
            --property os_command_line="console=ttyAMA0" \
            --property hw_disk_bus=scsi --property hw_scsi_model=virtio-scsi \
            --public ${image_name}
        [[ $? -ne 0 ]] && die $LINENO "create image failed"
    fi

    if [ ! "$(neutron net-show ${provider_net} 2>/dev/null)" ]; then
        neutron net-create ${provider_net} --router:external True \
            --provider:physical_network external --provider:network_type flat
        [[ $? -ne 0 ]] && die $LINENO "create provider net failed"
    fi

    if [ ! "$(neutron subnet-show ${provider_subnet} 2>/dev/null)" ]; then
        neutron subnet-create ${provider_net} --name ${provider_subnet} \
            --allocation-pool start=${provider_net_start_addr},end=${provider_net_stop_addr} \
            --disable-dhcp --gateway ${provider_net_gw_addr} \
            ${provider_net_start_addr%.*}.1/${provider_net_bits}
        [[ $? -ne 0 ]] && die $LINENO "create provider subnet failed"
    fi

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
    heat stack-create $heat_name -f ${filepath}/config/test.yml -P "image=$image_id;\
        public_network=$provider_net;flavor=$flavor_name;key_name=$key_name;\
        private_subnet=$demo_net_cidr"
    [[ $? -ne 0 ]] && die $LINENO "create an instance failed"
    echo "sleep for 100 seconds to wait to launch the first stack..."
    sleep 100

    instance_name=$(nova list | sed 's/ /\n/g' | grep -i "my-fisrt-stack")
    instance_run=$(openstack server list | grep Running)
    if [ x"$instance_run" == x"" ]; then
        instance_error=$(openstack server list | grep ERROR)
        if [ x"$instance_error" != x""  ]; then
            echo "Launch the instance ERROR. Please check the log file"
            exit 1
        fi
    fi

    vm_ip=`openstack server show ${instance_name} -c addresses -f value\
        | awk '{print $NF}'`
    qroute=$(ssh $USERNAME@${network_server} "ip netns | grep route")
    [[ -n $qroute ]] || die $LINENO "Does not have a qroute namspace"
    ping_result_1=$(ssh $USERNAME@${network_server} "ip netns exec \
        $qroute ping -c 4 $vm_ip" | grep " 0% packet loss")

    qdhcp=$(ssh $USERNAME@${network_server} "ip netns | grep dhcp")
    [[ -n $qdhcp ]] || die $LINENO "Does not have a qdhcp namspace"
    ping_result_2=$(ssh $USERNAME@${network_server} "ip netns exec \
        $qdhcp ping -c 4 $vm_ip" | grep " 0% packet loss")
    if [[ x"$ping_result_1" = x"" ]] || [[ x"$ping_result_2" = x"" ]]; then
        die $LINENO "from the network node's namespace, the instance' \
        fixed ip can not be accessed"
    fi

    $xtrace

    echo "Please use the \"ssh cirros@${vm_ip}\" to login the vm, "
    echo "the password is \"gocubsgo\""
}


if [ $# -eq 0 ]; then
    echo "You should use './deployment.sh '\$USERNAME' to point out which user"
    echo  "you are going to use."
    exit 1
else
    USERNAME=$1
fi

filedir=$(cd "$(dirname "$0")"; pwd)
filepath=$(cd "$(dirname "$filedir")"; pwd)

source $filepath/config/openstack_cfg.sh
source $filepath/sh/common.sh

pushd ${filepath}
    if [ ! -d openstack-ref-architecture ]; then
        git clone https://git.linaro.org/leg/sdi/openstack-ref-architecture.git
    fi
    pushd openstack-ref-architecture 
    install_dir=$PWD
    if [ ! -d ansible/secrets ]; then
        echo "You have not config secrets in the openstack folder"
        ln -s ${filepath}/config/secrets  ${install_dir}/ansible/secrets
    fi
    #cp -r ${filepath}/config/secrets  ./ansible
    export ANSIBLE_HOSTS=$install_dir/ansible/secrets/hosts

    pushd ansible
    ##############################
    # deploy the ceph mon services
    ##############################
    ceph_mon_server=`get_first_machine ceph_monitor_servers`
    echo "Begin to install Ceph Monitor"
    echo "ansible-playbook -K -v -i ./secrets/hosts ./site.yml --tags ceph-mon -u $USERNAME"
    echo "Please input the sudo password of $USERNAME"
    if ! ansible-playbook -K -v -i ./secrets/hosts ./site.yml --tags ceph-mon -u $USERNAME; then
        echo "Ceph MON installation failed"
        exit 1
    fi
    ceph_mon_status=$(ssh $USERNAME@$ceph_mon_server "ceph -s")
    ceph_mon_result=$(echo $ceph_mon_status | grep -E 'health| monmap')
    if [[ x"$ceph_mon_result" == x"" ]]; then
        echo "Ceph MON installation failed"
        exit 1
    fi

    ##############################
    # deploy the ceph osd services
    ##############################
    ceph_osd_server=`get_first_machine ceph_osd_servers`
    echo "Begin to install Ceph OSD"
    echo "ansible-playbook -K -v -i ./secrets/hosts ./site.yml --tags ceph-osd -u $USERNAME"
    echo "Please input the sudo password of $USERNAME"
    if ! ansible-playbook -K -v -i ./secrets/hosts ./site.yml --tags ceph-osd -u $USERNAME; then
        echo "Ceph OSD installation failed"
        #exit 1
    fi

    echo "sleep for 80 seconds to wait the ceph osds ready..."
    sleep 80
    ceph_osd_result=$(ssh $USERNAME@${ceph_osd_server} "ceph osd tree")
    ceph_osd_result=$(ssh $USERNAME@${ceph_osd_server} "ceph -s")
    ceph_osd_tree=$(echo ${ceph_osd_result} | grep 'HEALTH_ERR')
    if [[ x"$ceph_osd_tree" != x"" ]]; then
        echo "Ceph OSD installation failed"
        exit 1
    fi

    echo "Begin to install whole OpenStack Services"
    echo "ansible-playbook -K -v -i ./secrets/hosts ./site.yml -u $USERNAME"
    echo "Please input the sudo password of $USERNAME"
    if ! ansible-playbook -K -v -i ./secrets/hosts ./site.yml -u $USERNAME; then
        echo "OpenStack installation failed"
        exit 1
    fi
    popd
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
linaro_ansible_path=${filepath}/openstack-ref-architecture/ansible
cp ${adminrc_path}  ${LOCAL_PATH}
keystone_admin_passwd=$(find ${linaro_ansible_path}/secrets \
    -name 'deployment-vars' | xargs cat | \
grep 'keystone_admin_pass:' | awk '{print $NF}')
public_url=http://$(find ${linaro_ansible_path}/secrets \
    -name 'deployment-vars' | xargs cat | \
    grep 'public_api_host:' | grep -o '[0-9.]\+')
sed -i "s/{{keystone_admin_pass}}/${keystone_admin_passwd}/g" ${LOCAL_ADMIN}
sed -i "s#{{public_api_host}}#${public_url}#g" ${LOCAL_ADMIN}
cat ${LOCAL_ADMIN}
network_server=`get_first_machine networking_servers`
scp ${LOCAL_ADMIN}  $USERNAME@${network_server}:~
cp -f ${LOCAL_ADMIN}  ~/

openstack_services
prepare_openstack_services
#launch_an_instance

