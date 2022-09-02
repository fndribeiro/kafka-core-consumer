## About Petbytes:
Petbytes is a project composed by microservices for studying purposes. It is a REST API with crud operations for pets.

The objective of this project is to implement microservice architecture with Java and Spring Boot.

This project has the following services:

#### 1 - Sring Cloud ConfigServer (https://github.com/fndribeiro/petbytes-config-server):
Spring Cloud Config provides server and client-side support for externalized configuration in a distributed
system. With the Config Server you have a central place to manage external properties for applications
across all environments. MongoDB server configuration is here for example.

#### 2 - Spring Cloud NetFlix EurekaServer (https://github.com/fndribeiro/petbytes-eureka-server):
Microservices service discovery & registration is a way for client applications and microservices to locate each other on a network. This
includes:
* A central server (or servers) that maintain a global view of addresses.
* Microservices connect to the central server to register their address when they start & ready
* Microservices need to send their heartbeats at regular intervals to central server about their health

#### 3 - ONGS Microservice (https://github.com/fndribeiro/petbytes-ongs):
ONGS are the non governmental organizations responsible for the pets. This microservice is meant to serve as host for pets available for adoption. "Adotantes"
microservice will search these APIs for pets.

#### 4 - Adotantes Microservice (https://github.com/fndribeiro/petbytes-adotantes):
"Adotantes" are the adopters of the pets. This microservice is meant to be part of the app where adopters search for availables pets for adoption and request them to 
non governmental organizations responsible for them.

#### Architecture:

![image](https://user-images.githubusercontent.com/45038374/163069745-1b1d193d-76cf-4680-9a80-79f5610646ad.png)

#### Code Design (Hexagonal):

![image](https://user-images.githubusercontent.com/45038374/163070322-9be68145-7bba-49e2-aa3f-65b30a0c5e2b.png)

#### Bounded Contexts:

![image](https://user-images.githubusercontent.com/45038374/163070411-cf5c4edb-f31f-45cd-85a5-e021938500e4.png)

#### Stack:

- Java 17
- Spring Boot
- Spring Data MongoDB: Persistance
- Spring Cloud Config: Configuration Server
- Spring Cloud Netflix: Service Discovery
- Resilience4J: Resiliency
- Zipkin: Tracing and Log Aggregation
- Swagger: Documentation (http://yourIp:port/swagger-ui/#/)

#### Deployment:

All services are build to run on a Kubernetes cluster.

Docker images are build with Spring Boot Maven Plugin and Packeto Buildpacks: mvn spring-boot:build-image

Docker push images on: https://hub.docker.com/repository/docker/fephs/

Kubernetes YAML files to run these services can be found in:
https://github.com/fndribeiro/petbytes-eureka-server/tree/master/kubernetes

Change deployment type to LoadBalancer or NodePort depending on your kubernetes cluster configuration.
