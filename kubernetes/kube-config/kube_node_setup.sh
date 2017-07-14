#!/bin/bash
CUR_DIR=$(cd `dirname $0`; pwd)

MASTER_IP=$1
TOKEN=$2

if [ -z "${TOKEN}" ] ; then
    echo "Please run 'kubeadm token list' to get token firstly"
    echo "Usage:./kube_node_setup.sh <master ip> <token>"
    exit 0
fi

sudo yum install -y kubernetes-node
sudo yum install -y kubernetes-client
sudo yum install -y kubernetes-kubeadm

sudo systemctl stop firewalld

sudo kubectl --kubeconfig /etc/kubernetes/admin.conf apply -f ${CUR_DIR}/kube-flannel/kube-flannel-rbac.yml
sudo kubectl --kubeconfig /etc/kubernetes/admin.conf apply -f ${CUR_DIR}/kube-flannel/kube-flannel.yml

sudo kubeadm join --token ${TOKEN} ${MASTER_IP}:6443 --skip-preflight-checks


