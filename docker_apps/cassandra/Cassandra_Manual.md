* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)
* [Performance](#6)

## <a name="1">Introduction</a>

This Cassandra docker image is based on [Cassandra 3.10](https://cassandra.apache.org) and ARM64 platform.

## <a name="2">Build</a>
As for how to build this docker image, please refer to [Cassandra Docker file](https://github.com/open-estuary/dockerfiles/tree/master/cassandra).
In addition, all docker images are stored into [OpenEstuary Docker Hub](https://cloud.docker.com/app/openestuary).

## <a name="3">Installation</a>
Cassandra docker image could be installed through one of following ways:  
- Use the [setup.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/cassandra/setup.sh) on ARM64 host to setup docker and Cassandra docker image accordingly
- Use docker `pull openestuary/cassandra` to install the Cassandra docker image  

## <a name="4">Start</a>
There are several of ways to start Cassandra docker container as follows:
- Start one container whose port 9042 is mapped to the host specific port such as 9042
  > Warning: 
  > By default, it will try to use 'numactl' to bring up Cassandra in docker container. 
  > However the corresponding syscalls (such as set_mempolicy) is prohibited in the docker container by default.
  > Therefore it is required to use "--security-opt" to override the default profile, or to disable default profile via "--security-opt seccomp=unconfined".

  > In addition, the following ports will be exported if necessary:
  >  - 7000: intra-node communication
  >  - 7001: TLS intra-node communication
  >  - 7199: JMX
  >  - 9042: CQL
  >  - 9160: thrift service
  - Examples: `docker run --security-opt seccomp=unconfined -p 9042:9042 --name cassandra -d openestuary/cassandra:3.10`
- Start one container whose ports are mapped to host ports dynamically
  - Examples:
    - Execute `docker run --security-opt seccomp=unconfined -P --name cassandra -d openestuary/cassandra:3.10` to start the container
    - Execute `docker port cassandra` to check the dynamic port
- Start one container which uses the specified host data diretory 
  - Examples:`docker run --security-opt seccomp=unconfined -p 9042:9042 -v /host/datadir:/u01/cassandra -d openestuary/cassandra:3.10`

- Start one container which uses the specified configuration
  - Examples:`docker run --security-opt seccomp=unconfined -p 9042:9042 -v /host/configdir:/etc/cassandra -d openestuary/cassandra:3.10         
    Consequently, it will use /host/configdir/cassandra.yaml and other configuration files.
- Default account: user `cassandra`, password `cassandra`

```
   Usually it could use `cqlsh <ip address> <port(default 9042)>` to connect cassandra node.
   Please note that the <ip address> should not be '127.0.0.1'.
```

By default, the docker container only has 10G disk size. Therefore, it is suggested to specify data directory and the recommended [cassandra.conf](https://github.com/open-estuary/packages/blob/master/docker_apps/cassandra/cassandra.conf) . 
As for more examples, please refer to [start.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/cassandra/start.sh).
In addition, the [docker documents](https://docs.docker.com/) will provide more docker commands.
                                                   
## <a name="5">Remove</a>
- Execute the [remove.sh](https://github.com/open-estuary/packages/blob/master/docker_apps/cassandra/remove.sh) on ARM64 to remove Cassandra docker container and images 
- Execute `docker rm <dockercontainer_id>` and `docker rmi openestuary/cassandra` to remove docker containers and Cassandra docker images accordingly

## <a name="6">Performance</a>
### Performance Optimization 

### Benchmark Tests
