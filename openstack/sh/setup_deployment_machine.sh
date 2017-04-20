#!/bin/bash
#author: wuyanjun
#date: 2017-04-14
set -x
function install_openstack_client
{
    if [ -f /etc/debian_version ]; then
        sudo apt-get install wget
    fi

    if [ -f /etc/redhat-release ]; then
        sudo yum install wget
    fi

    heat help > /dev/null 2>&1
    [[ $? -ne 0 ]] && sudo pip install python-heatclient

    openstack help >/dev/null 2>&1
    [[ $? -eq 0 ]] && return


    sudo pip install python-openstackclient python-cinderclient  \
        python-keystoneclient python-neutronclient python-novaclient \
        python-glanceclient
}

if [ $# -eq 0 ]; then
    echo "You should use './openstack/sh/deployment.sh '\$USERNAME' to point out which user you are going to use."
    exit 1
else
    USERNAME=$1
fi

if [ -f /etc/debian_version ]; then
    sudo apt-get -q=2 update
    sudo apt-get install -y git libyaml-dev libxml2-dev libxslt1-dev \
        libmysqlclient-dev libffi-dev libssl-dev libvirt-dev python-dev \
        pkg-config libvirt-dev python-virtualenv git python-setuptools \
        python-jinja2 python-yaml
fi

if [ -f /etc/redhat-release ]; then
    sudo yum install -y gcc make libyaml-devel libxml2-devel libxslt-devel \
        mysql-devel libffi-devel openssl-devel libvirt-devel python-devel \
        pkgconfig python-virtualenv git python-setuptools python-jinja2 \
        python-yaml
fi

sudo easy_install pip
sudo pip install ansible==2.2 paramiko PyYAML Jinja2 httplib2 six

install_openstack_client

if [ ! -f  ~/.ssh/id_rsa ] && [  ! -f ~/.ssh/id_dsa ]; then
    ssh-keygen  -t rsa -f ~/.ssh/id_rsa  -P ''
fi

filedir=$(cd "$(dirname "$0")"; pwd)
filepath=$(cd "$(dirname "$filedir")"; pwd)
pushd ${filepath}
if [ ! -d openstack-ref-architecture ]; then
    git clone https://git.linaro.org/leg/sdi/openstack-ref-architecture.git
fi
pushd openstack-ref-architecture
if [ ! -d ansible/secrets ]; then
    echo "You have not config secrets in the openstack folder"
    ln -s ${filepath}/config/secrets  ${install_dir}/ansible/secrets
fi
popd
popd

#cat ${filepath}/config/target_machine_hosts | sudo tee -a /etc/hosts
clients_ips=(`cat ${filepath}/config/target_machine_hosts |awk '{print $1}'`)
clients_hosts=(`cat ${filepath}/config/target_machine_hosts |awk '{print $2}'`)

# copy public keys to all machines
awk '{if ($0~/'"[0-9]+"'|localhost/)print $1}' \
    ${filepath}/config/target_machine_hosts \
    | xargs -i ssh-copy-id -i ~/.ssh/id_rsa.pub  ${USERNAME}@{}

temp_hosts=$(mktemp /tmp/temp.XXXX)

# modify the machines hostname/hosts and reboot the machines
for((i=0;i<${#clients_ips[@]};i++)); do
    ssh $USERNAME@${clients_ips[$i]} "echo ${client_hosts[$i]} > /etc/hostname"
    ssh $USERNAME@${clients_hosts[$i]} "cat /etc/hostname"
    echo ${clients_ips[$i]}  >> ${temp_hosts}
done


ansible all -u $USERNAME -i ${temp_hosts} -m copy -a \
    "src=${filepath}/config/target_machine_hosts dest=~/"   2>/dev/null
ansible all -u $USERNAME -K -i ${temp_hosts} -m shell -a \
    "cat ~/target_machine_hosts >> /etc/hosts" --sudo

ansible all -u $USERNAME -i ${temp_hosts} -m copy -a \
    "src=${filepath}/sh/setup_target_machines.sh dest=~/"   2>/dev/null
[ $? -ne 0 ] && die $LINENO "can not connect all the target machines"

ansible all -u $USERNAME -K -i ${temp_hosts} -m shell \
    -a "/bin/bash ~/setup_target_machines.sh"
rm -fr ${temp_hosts}

set +x
echo "You need to reboot all target machines"
