* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>
Netflix Eureka is a service registry. It provides a REST API for service instance registration management and for querying available instances. Netflix Ribbon is an IPC client that works with Eureka to load balance requests across the available service instances.

When using client-side discovery, the client is responsible for determining the network locations of available service instances and load balancing requests across them. The client queries a service registry, which is a database of available service instances. The client then uses a load balancing algorithm to select one of the available service instances and makes a request.

## <a name="2">Build</a>
run the order "mvn clean package" to build the project, and meanwhile it will generate a jar under target directory.

## <a name="3">Installation</a>
run the order "mvn install".

## <a name="4">Start</a>
run the order "mvn spring-boot:run".

## <a name="5">Remove</a>


