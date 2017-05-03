* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)
* [Performance](#6)

## <a name="1">Introduction</a>

This Ceph docker image is based on [Ceph](https://github.com/ceph/ceph) and ARM64 platform.

## <a name="2">Build</a>
As for how to build this docker image, please refer to [Ceph Docker file](https://github.com/open-estuary/dockerfiles/tree/master/ceph).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
Ceph docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/ceph/setup.sh) on ARM64 host to setup docker and Ceph docker image accordingly
- Use docker `pull openestuary/ceph` to install the Ceph docker image

### Versions 
- ceph-base
  - v300/10.2.7/kraken : based on Open-Estuary V300 CentOS and Ceph-10.2.7
  - latest: currently it is the same as v300 or jewel
- ceph
  - v300/11.1.1/kraken : based on Open-Estuary V300 CentOS and Ceph-11.1.1
  - latest: currently it is the same as v300 or kraken
## <a name="4">Start</a>

As for how to use Ceph docker images, please refer to:       
  - [Ceph Docker Image overview](https://github.com/sjtuhjh/ceph-docker)
  - [Ceph Base Image Manual](https://github.com/sjtuhjh/ceph-docker/blob/master/ceph-releases/jewel/centos/7/base/README.md)
  - [Ceph Daemon Image Manual](https://github.com/sjtuhjh/ceph-docker/blob/master/ceph-releases/kraken/centos/7/daemon/README.md)
 
## <a name="5">Remove</a>

## <a name="6">Performance</a>
### Performance Optimization 

### Benchmark Tests
