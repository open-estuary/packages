* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This E-Commerce-Cart [docker image](https://docs.docker.com/) is based on [OpenJdk 1.8](http://openjdk.java.net/install/).


## <a name="2">Build</a>
As for how to build this docker image, please refer to [E-Commerce-Cart Docker file](https://github.com/open-estuary/dockerfiles/tree/master/micro-service/cart/Dockerfile).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
E-Commerce-Cart docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/e-commerce-cart/setup.sh) on ARM64 host to setup docker and E-Commerce-Cart docker image accordingly
- Use docker `pull openestuary/e-commerce-cart` to install the E-Commerce-Cart docker image  

### Versions 
- v500/5.0 : based on Open-Estuary V500 CentOS and OpenJdk 1.8

## <a name="4">Start</a>
There are several of ways to start E-Commerce-Cart docker container as follows:
- Start one container whose port 8761 is mapped to the host specific port such as 8761
  - Examples: `docker run -p 8761:8761 --name e-commerce-cart -d openestuary/e-commerce-cart:5.0`
- Start one container whose port 8761 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run -P --name e-commerce-cart -d openestuary/e-commerce-cart:5.0` to start the container
    - Execute `docker port e-commerce-cart` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run -p 8761:8761 -v /etc/e-commerce/cart:/etc/e-commerce/cart -d openestuary/e-commerce-cart:5.0`

By default, the docker container only has 10G disk size. Therefore, it is suggested to specify data directory and the recommended [application.yml](https://github.com/open-estuary/packages/blob/master/docker_apps/e-commerce-cart/application.yml) . 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/e-commerce-cart/start.sh).
In addition, the [docker documents](https://docs.docker.com/) will provide more docker commands.

## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/e-commerce-cart/remove.sh) on ARM64 to remove Micro-Service-Cart docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/e-commerce-cart` to remove docker containers and Micro-Service-Cart docker images accordingly