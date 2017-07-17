#!/bin/bash
CUR_DIR=$(cd `dirname $0`; pwd)

sudo kubectl --kubeconfig /etc/kubernetes/admin.conf apply -f ${CUR_DIR}/kube-dashboard

