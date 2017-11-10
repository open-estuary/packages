* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)
* [Performance Optimization](#6)
* [Performance Benchmark](#7)

## <a name="1">Introduction</a>

This Twemproxy [docker image](https://docs.docker.com/) is based on [Twemproxy](https://codeload.github.com/twitter/twemproxy/zip/master).


## <a name="2">Build</a>
As for how to build this docker image, please refer to [Twemproxy Docker file](https://github.com/open-estuary/dockerfiles/tree/master/twemproxy).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
Twemproxy docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/twemproxy/setup.sh) on ARM64 host to setup docker and Twemproxy docker image accordingly
- Use docker `pull openestuary/twemproxy` to install the Twemproxy docker image  

### Versions 
- v500/0.4.1 : based on Open-Estuary V500 CentOS and Percona Server 0.4.1
- latest: currently it is the same as v500 or 0.4.1. 

## <a name="4">Start</a>
There are several of ways to start Twemproxy docker container as follows:
- Start one container whose port 3306 is mapped to the host specific port such as 3306
  - Examples: `docker run -p 3306:3306 --name twemproxy -d openestuary/twemproxy:0.4.1`
- Start one container whose port 3306 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run -P --name twemproxy -d openestuary/twemproxy:0.4.1` to start the container
    - Execute `docker port twemproxy` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run -p 3306:3306 -v /host/datadir:/u01/my3306/data -d openestuary/twemproxy:0.4.1`

By default, the docker container only has 10G disk size. 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/twemproxy/start.sh).
In addition, the [docker documents](https://docs.docker.com/) will provide more docker commands.
                                                   
## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/twemproxy/remove.sh) on ARM64 to remove Twemproxy docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/twemproxy` to remove docker containers and Twemproxy docker images accordingly

## <a name="6">Performance Optimization</a>


## <a name="7">Performance Benchmark</a>
TBD 
