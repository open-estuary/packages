* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>

This LAMP(Linux, Apache, MySQL and PHP) [docker image](https://docs.docker.com/) is based on [Apache](https://www.apache.org) and [MySQL](https://github.com/open-estuary/packages/blob/master/docker_apps/mysql/MySql_Manual.md).

## <a name="2">Build</a>
- As for how to build apache docker image, please refer to [Apache Docker file](https://github.com/open-estuary/dockerfiles/tree/master/apache).
- As for how to build mysql docker image, please refer to [MySQL Docker file](https://github.com/open-estuary/dockerfiles/tree/master/mysql).

## <a name="3">Installation</a>
The whole LAMP contains Apache and mysql docker images which could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/lamp/setup.sh) on ARM64 host to setup docker images accordingly
- Use docker `pull openestuary/apache` and `pull openestuary/mysql` 

## <a name="4">Start</a>
> In order to simulate one web server, we could use [Discuz](http://download.comsenz.com/DiscuzX) as web source pages. 
> Please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/lamp/start.sh) for an example. 
- Start Apache
  - Start one container whose port 80 is mapped to the host specific port such as 80
    - Examples: `docker run -p 80:80 --name lamp-apache -d openestuary/apache`
  - Start one container whose port 80 is mapped to the host port dynamically
    - Examples:
      - Execute `docker run -P --name lamp-apache -d openestuary/apache` to start the container
      - Execute `docker port lamp-apache` to check the dynamic port
  - Start one container which uses the specified host data diretory 
    - Examples:`docker run -p 80:80 --name lamp-apache -v /usr/local/lamp/Discuz:/var/www/html -d openestuary/apache`
    
- Start MySql
  - Examples: `docker run -p 3306:3306 --name lamp-mysql -d openestuary/mysql`. For more information, please refer to [MySQL Manual](https://github.com/open-estuary/packages/blob/master/docker_apps/mysql/MySql_Manual.md)
                                                        
## <a name="5">Remove</a>
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/apache` and `docker rmi openestuary/mysql` to remove docker containers and docker images accordingly
