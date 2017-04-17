* [1. Setup Deployment Environment](#1)
* [2. Deploy OpenStack](#2)

## Terms
**Deployment machine**: machine to run deployment scripts.

**Target machine**: machine to be deployed OpenStack.

## <a name="1">1. Setup Deployment Environment</a>
***
* Setup the network of target machines (Estaury test environtment is ready, test guy please skip this step)

TO BE ADD

* Install Estuary latest release Debian or CentOS on target machines

* Bind the IP addresses of target machines (Estaury test environtment is ready, test guy please skip this step)

Bind the IP address through the dhcp router Or dhcp machine(which install a dhcp service, e.g. dnsmasq).

* Setup dns service (Estaury test environtment is ready, test guy please skip this step)

Setup dns of each target machine through the router Or dns machine(which install a dns service, e.g. dnsmasq).
For dnsmasq, please refer to official [manual](http://www.thekelleys.org.uk/dnsmasq/docs/dnsmasq-man.html).


**Or** simply just setup the /etc/hosts file of each machine, includine deployment and target machines.

## <a name="2">2. Deploy OpenStack</a>
***
**_Note_**: All the bellow steps are operated on deployment machine. For estuary test environment, the deployment machine is **OpenLab2 BoardServer**.
```
$ mkdir openstack-deploy
$ cd openstack-deploy
$ git clone -b <RELEASE-TAG> --depth=1 https://github.com/open-estuary/packages.git
$ ./openstack/sh/deployment.sh <USER-ACCOUNT>        ## It will take sometime ~30 minitues to finish the deployment
```
**_Note1_**: "RELEASE-TAG" is the Estuary release tag e.g. v3.1.

**_Note2_**: "USER-ACCOUNT" is the target machine user account used for deployment.

**_Note3_**: The default "openstack/config/target_machine_hosts" and "openstack/config/secrets" files are for estuary test environment, please modifiy them if your environment is different from the estuary test environment.
