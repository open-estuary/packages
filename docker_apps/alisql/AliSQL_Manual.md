* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This AliSQL [docker image] (https://docs.docker.com/) is based on [AliSQL Server 5.6](https://github.com/alibaba/AliSQL/archive/AliSQL-5.6.32-1.tar.gz).


## <a name="2">Build</a>
As for how to build this docker image, please refer to [AliSQL Server Docker file] (https://github.com/open-estuary/dockerfiles/tree/master/mysql/alisql).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
AliSQL docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/alisql/setup.sh) on ARM64 host to setup docker and AliSQL docker image accordingly
- Use docker `pull openestuary/alisql` to install the AliSQL docker image  

## <a name="4">Start</a>
There are several of ways to start AliSQL docker container as follows:
- Start one container whose port 3306 is mapped to the host specific port such as 3306
  - Examples: `docker run -p 3306:3306 --name alisql -d openestuary/alisql:latest`
- Start one container whose port 3306 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run -P --name alisql -d openestuary/alisql:latest` to start the container
    - Execute `docker port alisql` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run -p 3306:3306 -v /host/datadir:/u01/my3306/data -d openestuary/alisql:latest`
- Start one container which uses the specified configuration, that is /host/configdir/my.conf
  - Examples:`docker run -p 3306:3306 -v /host/configdir:/usr/local/mysql/config -d openestuary/alisql:latest`
- Default account: user `alisql`, password `Estuary12#$`

By default, the docker container only has 10G disk size. Therefore, it is suggested to specify data directory and the recommended [my.conf](https://github.com/open-estuary/packages/blob/master/docker_apps/alisql/my.conf) . 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/alisql/start.sh).
In addition, the [docker documents] (https://docs.docker.com/) will provide more docker commands.
                                                   
## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/alisql/remove.sh) on ARM64 to remove AliSQL docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/alisql` to remove docker containers and AliSQL docker images accordingly
