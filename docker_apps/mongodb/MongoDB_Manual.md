* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This MongoDB [docker image] (https://docs.docker.com/) is based on [MongoDB 3.4](https://github.com/mongodb/mongo).


## <a name="2">Build</a>
As for how to build this docker image, please refer to [MongoDB Docker file] (https://github.com/open-estuary/dockerfiles/tree/master/mongodb).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
MongoDB docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/mongodb/setup.sh) on ARM64 host to setup docker and MongoDB docker image accordingly
- Use docker `pull openestuary/mongodb` to install the MongoDB docker image  

## <a name="4">Start</a>
There are several of ways to start MongoDB docker container as follows:
- Start one container whose port 27017 is mapped to the host specific port such as 27017
  > Warning: 
  > Please note that it uses 'numactl' to bring up mongod in docker container. 
  > However the corresponding syscalls (such as set_mempolicy) is prohibited in the docker container by default.
  > Therefore it is required to use "--security-opt" to override the default profile, or to disable default profile via "--security-opt seccomp=unconfined" 
  - Examples: `docker run --security-opt seccomp=unconfined -p 27017:27017 --name mongodb -d openestuary/mongodb:3.4`
- Start one container whose port 27017 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run --security-opt seccomp=unconfined -P --name mongodb -d openestuary/mongodb:3.4` to start the container
    - Execute `docker port mongodb` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run --security-opt seccomp=unconfined -p 27017:27017 -v /host/datadir:/u01/mongodb -d openestuary/mongodb:3.4`

    In addition, the database will be stored into /host/datadir/data directory while the WAL logs will be stored into /host/datadir/log directory. 
- Start one container which uses the specified configuration
  - Examples:`docker run --security-opt seccomp=unconfined -p 27017:27017 -v /host/configdir:/usr/local/mongodb/config -d openestuary/mongodb:3.4`
  - Examples:`docker run --security-opt seccomp=unconfined -p 27017:27017 -v /host/configdir:/usr/local/mongodb/config -d openestuary/mongodb:3.4`
            
    In addition, it will use /host/configdir/mongodb.conf.
- Default account: user `mongodb`, password `Estuary12#$`

By default, the docker container only has 10G disk size. Therefore, it is suggested to specify data directory and the recommended [mongodb.conf](https://github.com/open-estuary/packages/blob/master/docker_apps/mongodb/mongodb.conf) . 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/mongodb/start.sh).
In addition, the [docker documents] (https://docs.docker.com/) will provide more docker commands.
                                                   
## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/mongodb/remove.sh) on ARM64 to remove MongoDB docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/mongodb` to remove docker containers and MongoDB docker images accordingly
