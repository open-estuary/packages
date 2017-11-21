* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This Eureka [docker image](https://docs.docker.com/) is based on [OpenJdk 1.8](http://openjdk.java.net/install/).


## <a name="2">Build</a>
As for how to build this docker image, please refer to [Eureka Docker file](https://github.com/open-estuary/dockerfiles/blob/master/micro-service/discovery/Dockerfile).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
Eureka docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/eureka/setup.sh) on ARM64 host to setup docker and Eureka docker image accordingly
- Use docker `pull openestuary/eureka` to install the Eureka docker image  

### Versions 
- v500/5.0 : based on Open-Estuary V500 CentOS and OpenJdk 1.8

## <a name="4">Start</a>
There are several of ways to start Eureka docker container as follows:
- Start one container whose port 8761 is mapped to the host specific port such as 8761
  - Examples: `docker run -p 8761:8761 --name eureka -d openestuary/eureka:5.0`
- Start one container whose port 8761 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run -P --name eureka -d openestuary/eureka:5.0` to start the container
    - Execute `docker port eureka` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run -p 8761:8761 -v /etc/micro-services/discovery:/etc/micro-services/discovery -d openestuary/eureka:5.0`

By default, the docker container only has 10G disk size. Therefore, it is suggested to specify data directory and the recommended [application.yml](https://github.com/open-estuary/packages/blob/master/docker_apps/eureka/application.yml) . 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/eureka/start.sh).
In addition, the [docker documents](https://docs.docker.com/) will provide more docker commands.

## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/eureka/remove.sh) on ARM64 to remove Micro-Service-Eureka docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/eureka` to remove docker containers and Micro-Service-Eureka docker images accordingly