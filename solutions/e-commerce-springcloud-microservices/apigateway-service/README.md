* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>
Implementation of an API gateway that is the single entry point for all clients. The API gateway handles requests in one of two ways. Some requests are simply proxied/routed to the appropriate service. It handles other requests by fanning out to multiple services.

## <a name="2">Build</a>
run the order "mvn clean package" to build the project, and meanwhile it will generate a jar under target directory.

## <a name="3">Installation</a>
run the order "mvn install".

## <a name="4">Start</a>
run the order "mvn spring-boot:run". Eureka server should be run before api gateway service.

## <a name="5">Remove</a>


