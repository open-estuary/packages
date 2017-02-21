* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This Redis [docker image] (https://docs.docker.com/) is based on [Redis 3.2](http://download.redis.io/releases/redis-3.2.4.tar.gz).


## <a name="2">Build</a>
As for how to build this docker image, please refer to [Redis Docker file] (https://github.com/open-estuary/dockerfiles/tree/master/redis).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
Redis docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/redis/setup.sh) on ARM64 host to setup docker and Redis docker image accordingly
- Use docker `pull openestuary/redis` to install the Redis docker image  

## <a name="4">Start</a>
There are several of ways to start Redis docker container as follows:
- Start one container whose port 6379 is mapped to the host specific port such as 6379
  - Examples: `docker run -p 6379:6379 --name redis -d openestuary/redis:3.2.4`
- Start one container whose port 6379 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run -P --name redis -d openestuary/redis:3.2.4` to start the container
    - Execute `docker port redis` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run -p 6379:6379 -v /host/datadir:/usr/local/redis/db -d openestuary/redis:3.2.4`
- Start one container which uses the specified configuration, that is /host/configdir/redis.conf
  - Examples:`docker run -p 6379:6379 -v /host/configdir:/usr/local/redis/config -d openestuary/redis:3.2.4`

By default, the docker container only has 10G disk size. Therefore, it is suggested to specify data directory and the recommended [redis.conf](https://github.com/open-estuary/packages/blob/master/docker_apps/redis/redis.conf) . 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/redis/start.sh).
In addition, the [docker documents] (https://docs.docker.com/) will provide more docker commands.
                                                   
## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/redis/remove.sh) on ARM64 to remove Redis docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/redis` to remove docker containers and Redis docker images accordingly
