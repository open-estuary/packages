# E-Commerce Micro-Service Solution Based on ARM64 Server 

* [Introduction](#1)
* [Software Architecture ](#2)
* [Installation & Deployment](#3)
* [REST API](#4)
* [Start](#5)
* [Others](#6)

## <a name="1">Introduction</a>

It is to demonstrate one e-commerce microservice solution with high performance、high scalability、high availability and high reliability based on ARM64 servers. 

In addition, it uses many middlewares provided by Spring Cloud such as:
  * `Eureka`: MicroService Service Discovery
  * `Zuul`: MicroService Api-Gateway
  * `Hystrix, Feign, Ribbon, Zipkin, ...`

## <a name="2">Software Architecture</a>

<center><a href="docs/estuary_e_commerce_micro_service_software_architecture.png"><img src="https://github.com/open-estuary/packages/blob/master/solutions/e-commerce-springcloud-microservices/docs/estuary_e_commerce_micro_service_software_architecture.png" border=0 width=1600></a></center>


## <a name="3">REST API</a>
E-Commerce MicroService REST API

Method | URI | Description | Parameters | Request JSON | Response JSON
--- | --- | ---| --- | --- | ---
`GET` | */order/* | | | | 
`POST` | /order/* | | | |
`GET`  | /cart/*  | | | |
`POST` | /cart/*  | | | |
`GET`  | /search/* | | | |
`POST` | /search/* | | | |

Eureka Service Example: 

<center><a href="docs/e_commerce_eureka_example.png"><img src="https://github.com/open-estuary/packages/blob/master/solutions/e-commerce-springcloud-microservices/docs/e_commerce_eureka_example.png" border=0 width=1600></a></center>


## <a name="4">Installation & Deployment</a>
* Build Packages 
  * `mvn clean package`
  * `mvn clean install`
* Automatic Deployement based on `Ansible` framework
  > As for how to deploy the whole solution on server clusters automatically and intelligently, please refer to [E-Commerce MircoService Ansible Deployment Solution](https://github.com/open-estuary/appbenchmark/tree/master/apps/e-commerce-solutions/e-commerce-springcloud-microservice)
  
## <a name="5">Start</a>
* Execute the sub package 
  * `mvn spring-boot:run`
* Setup the whole solution 
  > As mentioned above, please refer to [E-Commerce MircoService Ansible Deployment Solution](https://github.com/open-estuary/appbenchmark/tree/master/apps/e-commerce-solutions/e-commerce-springcloud-microservice)
  
## <a name="6">Others</a>


