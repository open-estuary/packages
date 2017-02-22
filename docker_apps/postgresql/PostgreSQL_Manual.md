* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This PostgreSQL [docker image] (https://docs.docker.com/) is based on [PostgreSQL 9.6](https://ftp.postgresql.org/pub/source/v9.6.1/postgresql-9.6.1.tar.gz).


## <a name="2">Build</a>
As for how to build this docker image, please refer to [PostgreSQL Docker file] (https://github.com/open-estuary/dockerfiles/tree/master/postgresql).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
PostgreSQL docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/postgresql/setup.sh) on ARM64 host to setup docker and PostgreSQL docker image accordingly
- Use docker `pull openestuary/postgresql` to install the PostgreSQL docker image  

## <a name="4">Start</a>
There are several of ways to start PostgreSQL docker container as follows:
- Start one container whose port 5432 is mapped to the host specific port such as 5432
  - Examples: `docker run -p 5432:5432 --name postgresql -d openestuary/postgresql:9.6`
- Start one container whose port 5432 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run -P --name postgresql -d openestuary/postgresql:9.6` to start the container
    - Execute `docker port postgresql` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run -p 5432:5432 -v /host/datadir:/u01/postgresql -d openestuary/postgresql:9.6`
            In addition, the database will be stored into /host/datadir/data directory while the WAL logs will be stored into /host/datadir/log directory. 
- Start one container which uses the specified configuration
  - Examples:`docker run -p 5432:5432 -v /host/configdir:/usr/local/postgresql/config -d openestuary/postgresql:9.6`
  - Examples:`docker run -p 5432:5432 -v /host/configdir:/usr/local/postgresql/config -e PGCONF=large -d openestuary/postgresql:9.6`
            
    In addition, it will use /host/configdir/postgresql.conf or /host/configdir/postgresql_small.conf if PGCONF is not specified. Otherwise, it will use /host/configdir/postgresql_large.conf if PGCONF=large is specified.

By default, the docker container only has 10G disk size. Therefore, it is suggested to specify data directory and the recommended [postgresql.conf](https://github.com/open-estuary/packages/blob/master/docker_apps/postgresql/postgresql.conf) . 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/postgresql/start.sh).
In addition, the [docker documents] (https://docs.docker.com/) will provide more docker commands.
                                                   
## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/postgresql/remove.sh) on ARM64 to remove PostgreSQL docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/postgresql` to remove docker containers and PostgreSQL docker images accordingly
