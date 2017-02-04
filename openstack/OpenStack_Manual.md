* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This OpenStack installation script is based on aarch64 OpenStack Newton version (which is enabled by the Linaro), and now can only be deployed in CentOS. In the deply phase, it needs at least 3 machines to fulfill the requirements (the controller node, the network node and the compute node cannot be the same machine. And the network node need 3 ethernet port, while the compute node need 2).


## <a name="2">Build</a>
The build process mainly git clone the ansible scripts and the other scripts which will be used by anibsle.

## <a name="3">Installation</a>
OpenStack Installtion is realized in the start.sh, and it needs to trigger by manual.

## <a name="4">Start</a>

Before we start to install OpenStack. We need to do following tips.
#### Change hostnames of machines and the hostnames are different with each other
#### set the hostnames of machines in the /etc/hosts
For example:
`[root@test3 ref-openstack]# cat /etc/hosts
127.0.0.1   localhost 
::1         localhost localhost.localdomain localhost6 localhost6.localdomain6

192.168.1.175 test1
192.168.1.217 test2
192.168.1.248 test3
`
#### tranfer the public key of deployed machine to all the machines which will install openstack
For example: `ssh-keygen; ssh-copy-id root@test1; ssh-copy-id root@test2`


#### change the paramaters in openstack-ref-architecture
    `cd openstack-ref-architecture
     mv deployXXX.example secrets/deployXXXX
     change the hosts info and url info in the secrets/deployXXXX
     mv hosts.example hosts
     modify the machine name in hosts according the hostnames
     change the ceph_host_osds and eth ports in group_vars/all
     delete the web frontends in the site.yaml
     `

#### After all parameters has been changed, execute `./install.sh` to install opensatck in all machines. Either we can directly refer the openstack-ref-architecture/README.md to install openstack.
If the install.sh execute failed,  use the `./remove.sh` to delete all the services and reexecute `./install.sh` to install.

## <a name="5">Remove</a>
-  The remove.sh can not remove all the installed softwares it only remove the openstack user, role, project, networks and other services.
