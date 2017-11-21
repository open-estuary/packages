* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This Zuul [docker image](https://docs.docker.com/) is based on [OpenJdk 1.8](http://openjdk.java.net/install/).


## <a name="2">Build</a>
As for how to build this docker image, please refer to [Zuul Docker file](https://github.com/open-estuary/dockerfiles/tree/master/micro-service/api/Dockerfile).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
Zuul docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/zuul/setup.sh) on ARM64 host to setup docker and Zuul docker image accordingly
- Use docker `pull openestuary/zuul` to install the Zuul docker image  

### Versions 
- v500/5.0 : based on Open-Estuary V500 CentOS and OpenJdk 1.8

## <a name="4">Start</a>
There are several of ways to start Zuul docker container as follows:
- Start one container whose port 8765 is mapped to the host specific port such as 8765
  - Examples: `docker run -p 8765:8765 --name zuul -d openestuary/zuul:5.0`
- Start one container whose port 8765 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run -P --name zuul -d openestuary/zuul:5.0` to start the container
    - Execute `docker port zuul` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run -p 8765:8765 -v /etc/micro-services/api-gateway:/etc/micro-services/api-gateway -d openestuary/zuul:5.0`

By default, the docker container only has 10G disk size. Therefore, it is suggested to specify data directory and the recommended [application.yml](https://github.com/open-estuary/packages/blob/master/docker_apps/zuul/application.yml) . 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/zuul/start.sh).
In addition, the [docker documents](https://docs.docker.com/) will provide more docker commands.

## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/zuul/remove.sh) on ARM64 to remove Micro-Service-Zuul docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/zuul` to remove docker containers and Micro-Service-Zuul docker images accordingly