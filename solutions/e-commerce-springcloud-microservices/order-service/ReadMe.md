* [Introduction](#1)
* [Build ](#2)
* [Installation](#3)
* [Start](#4)
* [Remove](#5)

## <a name="1">Introduction</a>
Order Service is a simple realization of order business in electric business.It include CRUD interface.Except Spring, it also uses mybatis and mysql.

## <a name="2">Build</a>
run the order "mvn clean package" to build the project, and meanwhile it will generate a jar under target directory.

## <a name="3">Installation</a>
run the order "mvn install".

## <a name="4">Start</a>
run the order "mvn spring-boot:run". Eureka server should be run before order service. Make sure you have MySQL and Redis running on localhost (on default ports). Of course, you can create a application.yml to specify the IP address.

## <a name="5">Remove</a>


