* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Example](#4)

## <a name="1">Introduction</a>

Kubernetes is an open-source platform for automating deployment, scaling, and operations of application containers across clusters of hosts, providing container-centric infrastructure. For more information, please refer to https://kubernetes.io.

## <a name="2">Build</a>

## <a name="3">Installation</a>
Currently the kubernetes could be installed via  [OpenEstuary Repostiory](https://github.com/open-estuary/distro-repo).

### Versions 
- v1.6.4 

## <a name="4">Example</a>
### How to setup Kubernete with monitoring on ARM64 platform 
- Excute the [kube_master_setup.sh](https://github.com/open-estuary/packages/blob/master/kubernetes/kube-config/kube_master_setup.sh) on the master node;
- Excute the [kube_node_setup.sh](https://github.com/open-estuary/packages/blob/master/kubernetes/kube-config/kube_node_setup.sh) `<token> <master ip>` on each working node(including master node);
  - The 'token' string could be gotten via `kubeadm token list` on the master node; 
- Excute the [kube_monitor_setup.sh](https://github.com/open-estuary/packages/blob/master/kubernetes/kube-config/kube_monitor_setup.sh) on the master node;
  - It will use `Grafana` + `influxdb` + `Heapster` + `cAdvisor(Kubelet)` to setup monitoring;
  
- Open one web browser, and enter `http://<master_node_ip>:30000`
  - To login in with userid `admin` and password `admin`;
  - To add one data source
   - Select `Type` as `InfluxDB`;
   - Input url as `http://monitoring-influxdb:8086`;
   - Input Database as `k8s`;
   - Input both user and password as `root`
