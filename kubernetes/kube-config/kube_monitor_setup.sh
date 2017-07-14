#!/bin/bash

CUR_DIR=$(cd `dirname $0`; pwd)

kubectl --kubeconfig /etc/kubernetes/admin.conf create -f ${CUR_DIR}/kube-monitor
