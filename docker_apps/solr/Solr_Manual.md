* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This Solr [docker image](https://docs.docker.com/) is based on [Solr 6.6](http://lucene.apache.org/solr/).

## <a name="2">Build</a>
As for how to build this docker image, please refer to [Solr Docker file](https://github.com/open-estuary/dockerfiles/tree/master/solr).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
Solr docker image could be installed through one of following ways:  
- Use docker `pull openestuary/solr` to install the Solr docker image  

### Versions 
- 6.6 : based on Open-Estuary V3.1 CentOS and Solr6.6.
- latest: currently it is the same as 6.6. 

## <a name="4">Start</a>
There are several of ways to start Solr docker container as follows:
- Start one container whose port 8983 is mapped to the host specific port such as 8983
  - Examples: `docker run -p 8983:8983 --name solr -d openestuary/solr:6.6`
- Start one container whose port 8983 is mapped to the host port dynamically
  - Examples:
    - Execute `docker run -P --name solr -d openestuary/solr:6.6` to start the container
    - Execute `docker port solr` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run -p 8983:8983 -v /host/datadir:/opt/solr/mydata -d openestuary/solr:6.6`
- Start one container which uses the specified configuration, that is /host/configdir/solr.conf
  - Examples:`docker run -p 8983:8983 -v /host/configdir:/opt/solr/server/solr/mycore -d openestuary/solr:6.6`

For more information, please refer to [Solr official docker user manual](https://github.com/docker-solr/docker-solr). 
                                                   
## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/solr/remove.sh) on ARM64 to remove Solr docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/solr` to remove docker containers and Solr docker images accordingly
