* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This MySql [docker image] (https://docs.docker.com/) is based on [Percona Server 5.6.22](https://www.percona.com/downloads/Percona-Server-5.6/Percona-Server-5.6.22-72.0/source/tpercona-server-5.6.22-72.0.tar.gzarball/percona-server-5.6.22-72.0.tar.gz).


## <a name="2">Build</a>
As for how to build this docker image, please refer to [Percona Server Docker file] (https://github.com/open-estuary/dockerfiles/tree/master/mysql/percona-server-5.6).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
MySql docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/mysql/setup.sh) on ARM64 host to setup docker and MySql docker image accordingly
- Use docker `pull openestuary/mysql` to install the MySql docker image  

## <a name="4">Start</a>
There are several of ways to start MySql docker container as follows:
- Start one container whose port 3306 is mapped to the host specific port such as 3306
  - Examples: `docker run -p 3306:3306 --name mysql -d openestuary/mysql:latest`
- Start one container whose port 3306 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run -P --name mysql -d openestuary/mysql:latest` to start the container
    - Execute `docker port mysql` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run -p 3306:3306 -v /host/datadir:/u01/my3306/data -d openestuary/mysql:latest`
- Start one container which uses the specified configuration, that is /host/configdir/my.conf
  - Examples:`docker run -p 3306:3306 -v /host/configdir:/usr/local/mysql/config -d openestuary/mysql:latest`
- Default account: user `mysql`, password `Estuary12#$`

By default, the docker container only has 10G disk size. Therefore, it is suggested to specify data directory and the recommended [my.conf](https://github.com/open-estuary/packages/blob/master/docker_apps/mysql/my.conf) . 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/mysql/start.sh).
In addition, the [docker documents] (https://docs.docker.com/) will provide more docker commands.
                                                   
## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/mysql/remove.sh) on ARM64 to remove MySql docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/mysql` to remove docker containers and MySql docker images accordingly
