#!/bin/bash
#author: WuYanjun
#date: 04/02/2017

set +x

function delete_net
{
    if [ ! -f ${LOCAL_DEMO} ]; then
	return
    fi
    source ${LOCAL_DEMO}

    local xtrace
    xtrace=$(set +o | grep xtrace)
    set -o xtrace
    openstack server list
    if [ $? -ne 0 ];then
        return
    fi

    stack_name=$heat_name
    if [ "heat stack-list $stack_name 2>/dev/null" ]; then
        ( y | heat stack-delete $stack_name )
        [[ $? -ne 0 ]] && echo  $LINENO "delete stack failed"
    fi

    if [[ "openstack keypair list | grep ${key_name} 2>/dev/null" ]]; then
        openstack keypair delete ${key_name}
        [[ $? -ne 0 ]] && echo  $LINENO "delete keypair failed"
    fi
    
    $xtrace

    rm -fr ${LOCAL_DEMO}  >/dev/null
}

function delete_openstack_other_service
{
    unset OS_PROJECT_ID
    unset OS_PROJECT_NAME
    unset OS_USER_DOMAIN_NAME
    unset OS_USERNAME
    unset OS_PASSWORD
    unset OS_REGION_NAME
    unset OS_INTERFACE
    unset OS_IDENTITY_API_VERSION

    if [ ! -f ${LOCAL_ADMIN} ];then
    return
    fi

    source ${LOCAL_ADMIN}

    local xtrace
    xtrace=$(set +o | grep xtrace)
    set -o xtrace

    if [[ "neutron net-show ${DEMO_NET} 2>/dev/null" ]];then
        neutron net-delete ${DEMO_NET}
        [[ $? -ne 0 ]] && echo  $LINENO "delete net ${DEMO_NET} failed"
    fi
    if [[ "openstack project show ${project_name} 2>/dev/null" ]];then
        openstack project delete ${project_name}
        [[ $? -ne 0 ]] && echo  $LINENO "delete project ${project_name} failed"
    fi

    if [[ "openstack user show ${project_name} 2>/dev/null" ]];then
        openstack user delete ${project_name}
        [[ $? -ne 0 ]] && echo  $LINENO "delete user ${project_name} failed"
    fi

    subnet_ids=`neutron subnet-list | awk '{print $2}' | grep -v "id"`
    for subnet in ${subnet_ids[*]};do
        neutron subnet-delete $subnet
        [[ $? -ne 0 ]] && echo  $LINENO "delete subnet $subnet failed"
    done

    net_ids=`neutron net-list | awk '{print $2}' | grep -v "id"`
    for net in ${net_ids[*]};do
        neutron net-delete $net
        [[ $? -ne 0 ]] && echo  $LINENO "delete network $net failed"
    done

    if [[ "openstack image show ${image_name} 2>/dev/null" ]];then
        openstack image delete ${image_name}
        [[ $? -ne 0 ]] && echo  $LINENO "delete image ${image_name} failed"
    fi

    if [[ "openstack flavor show "m1.tiny" 2>/dev/null" ]];then
        openstack flavor delete m1.tiny
        [[ $? -ne 0 ]] && echo  $LINENO "delete flavor m1.tiny failed"
    fi

    if [[ "openstack role show ${project_user} 2>/dev/null" ]]; then
        openstack role delete ${project_user}
        [[ $? -ne 0 ]] && echo  $LINENO "delete role ${project_user} failed"
    fi

    security_groups=`openstack security group list | awk '{print $2}' | grep -v 'ID'`
    for security_group in ${security_groups[*]};do 
        if [[ `openstack security group show $security_group | grep icmp` ]]; then
            openstack security group delete $security_group
            [[ $? -ne 0 ]] && echo  $LINENO "delete security group icmp rule failed"
        else
            if [[ `openstack security group show $security_group | grep tcp` ]]; then
                openstack security group delete $security_group
                [[ $? -ne 0 ]] && echo  $LINENO "delete security group icmp rule failed"
            fi
        fi
    done

    $xtrace

    rm -fr ${LOCAL_ADMIN} >/dev/null
}

filepath=$(cd "$(dirname "$0")"; pwd)


source $filepath/config/openstack_cfg.sh
source $filepath/sh/common.sh
LOCAL_ADMIN=~/nova-admin.rc
LOCAL_DEMO=~/${project_name}-admin.rc

delete_net
delete_openstack_other_service
