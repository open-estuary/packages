* [1. Setup Deployment Environment](#1)
* [2. Deploy OpenStack](#2)

## Terms
**Deployment machine**: machine to run deployment scripts.

**Target machine**: machine to be deployed OpenStack.

## <a name="1">1. Setup Deployment Environment</a>
***
* Setup the network of target machines (Estaury test environtment is ready, test guy please skip this step)

TO BE ADD

* Install Estuary latest release Debian or CentOS on target machines (Install in /dev/sda disk)

* Bind the IP addresses of target machines (Estaury test environtment is ready, test guy please skip this step)

Bind the IP address through the dhcp router Or dhcp machine(which install a dhcp service, e.g. dnsmasq).

* Setup dns service (Estaury test environtment is ready, test guy please skip this step)

Setup dns of each target machine through the router Or dns machine(which install a dns service, e.g. dnsmasq).
For dnsmasq, please refer to official [manual](http://www.thekelleys.org.uk/dnsmasq/docs/dnsmasq-man.html).


**Or** simply just setup the /etc/hosts file of each machine, includine deployment and target machines.

* Setup the software environments for deployment machine and target machines

**_Note_**: All the bellow steps are operated on deployment machine. For estuary test environment, the deployment machine is **OpenLab2 BoardServer**.
```
$ mkdir openstack-deploy
$ cd openstack-deploy
$ git clone https://github.com/open-estuary/packages.git
```

* config the packages/openstack/config/target_machine_hosts (Estuary guys can skip this)

* Execute the scripts to deploy software environments.

```
$ cd openstack-deploy/packages
$ ./openstack/sh/setup_deployment_machine.sh <USER-ACCOUNT>
```
**_Note1_**: "USER-ACCOUNT" is the target machine user account used for deployment.

**_Note2_**: You neet to change the source for the target machines in the ```setup_target_machines.sh```. For CentOS, you can use 'http://repo.linaro.org/rpm/linaro-overlay/centos-7/linaro-overlay.repo' or 'http://114.119.4.74:8083/repos/centos/7/linaro-overlay.repo' to instead of "http://192.168.1.103:8083/repos/centos/7/linaro-overlay.repo". For Debian, you can use 'http://repo.linaro.org/debian/erp-16.12-stable' or 'http://114.119.4.74:8083/repos/debian/erp-16.12-stable/' to instead of 'http://192.168.1.103:8083/repos/debian/erp-16.12-stable/'. (Estuary guys can skip this)

**_Note3_**: In the process of script execute, you need to input several passwords. For *ansible* commands, you need to input password of "USER_ACCOUNT". For other commands, you need to install your own sudo password in the deployment machines.

**_Note4_**: After the scripts finished, You need to **REBOOT** the target machines so that the hostnames defined in target_machine_hosts take into effect

* Reboot the target machines

## <a name="2">2. Deploy OpenStack</a>
***
**_Note_**: All the bellow steps are operated on deployment machine. For estuary test environment, the deployment machine is **OpenLab2 BoardServer**.

* Patch the openstack-ref-architecture (Only for estuary test guy)
```
$ cd openstack-deploy/packages/openstack/openstack-ref-architecture
$ patch -p1 < ~/openstack-deploy/packages/openstack/patch/skip_tunnel_nic_task.patch
```

* Modify the secrets for the deployment (Estuary guys can skip this step)
Modify the variables in ```openstack-deploy/openstack/config/secrets``` folder according to your environment.


* Deploy the openstack
```
$ cd openstack-deploy/packages
$ ./openstack/sh/deployment.sh <USER-ACCOUNT>        ## It will take sometime ~30 minitues to finish the deployment
```
**_Note1_**: "USER-ACCOUNT" is the target machine user account used for deployment.

**_Note2_**: The default "openstack/config/secrets" files are for estuary test environment, please modifiy them according to your environment.

**_Note3_**: In the execute process, you need to input the password of "USER-ACCOUNT" account for target machines.
