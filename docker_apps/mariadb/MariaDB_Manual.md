* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This MariaDB [docker image] (https://docs.docker.com/) is based on [MariaDB 10.1.20](http://sfo1.mirrors.digitalocean.com/mariadb//mariadb-10.1.21/source/mariadb-10.1.21.tar.gz).


## <a name="2">Build</a>
As for how to build this docker image, please refer to [MariaDB Docker file] (https://github.com/open-estuary/dockerfiles/tree/master/mariadb/mariadb).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
MariaDB docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/mariadb/setup.sh) on ARM64 host to setup docker and MariaDB docker image accordingly
- Use docker `pull openestuary/mariadb` to install the MariaDB docker image  

## <a name="4">Start</a>
There are several of ways to start MariaDB docker container as follows:
- Start one container whose port 3306 is mapped to the host specific port such as 3306
  - Examples: `docker run -p 3306:3306 --name mariadb -d openestuary/mariadb:latest`
- Start one container whose port 3306 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run -P --name mariadb -d openestuary/mariadb:latest` to start the container
    - Execute `docker port mariadb` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run -p 3306:3306 -v /host/datadir:/u01/mariadb/data -d openestuary/mariadb:latest`
- Start one container which uses the specified configuration, that is /host/configdir/my.conf
  - Examples:`docker run -p 3306:3306 -v /host/configdir:/usr/local/mariadb/config -d openestuary/mariadb:latest`

- Default account: user `mysql`, password `Estuary12#$`

By default, the docker container only has 10G disk size. Therefore, it is suggested to specify data directory and the recommended [my.conf](https://github.com/open-estuary/packages/blob/master/docker_apps/mariadb/my.conf) . 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/mariadb/start.sh).
In addition, the [docker documents] (https://docs.docker.com/) will provide more docker commands.

## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/mariadb/remove.sh) on ARM64 to remove MariaDB docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/mariadb` to remove docker containers and MariaDB docker images accordingly
