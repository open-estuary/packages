#!/bin/bash

sudo yum install -y kubernetes-master
sudo yum install -y kubernetes-client
sudo yum install -y kubernetes-kubeadm

sudo systemctl stop firewalld
sudo kubeadm init --skip-preflight-checks --pod-network-cidr=10.244.0.0/16

#Allow master node to deploy pods
sudo kubectl --kubeconfig /etc/kubernetes/admin.conf taint nodes --all node-role.kubernetes.io/master-

