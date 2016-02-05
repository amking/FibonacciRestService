#FibonacciRestService

FibonacciRestService provides the restful service to calculate the fibonacci sequence of given size. It is developed using Java language, and the following framework or libraries are used:
* Glassfish as the web container
* Jersey as the Restful framework
* Guava Cache as the In-Memory Cache system

Before start the service, please make sure the following tools are installed:
* JRE1.8
* Maven

To start up the service, please type the following command:
```
$ mvn compile exec:java -q
Fibnocci Sequence Restful Service is Starting ...
Feb 05, 2016 11:53:49 AM org.glassfish.grizzly.http.server.NetworkListener start
INFO: Started listener bound to [localhost:8080]
Feb 05, 2016 11:53:49 AM org.glassfish.grizzly.http.server.HttpServer start
INFO: [HttpServer] Started.
Application started.
Try out http://localhost:8080/Sequences
Stop the application using CTRL+C

```

## Interface Description
The customer is allowed to send GET request to get the pretty printed sequence, the URI should be in the following format:
http://{Host:Port}/Sequences/{fibonacci sequence size}

## Constraints
* Now this service only provides plain-text type response, but it is very easy to support application-json and xml type response.
* Now the service will use the in-memory cache to cache the fibonacci sequence fragments in the server side, but we have already define the common interface to interact with all kinds of cache system (memcache, redis, etc), so it is very easy to extend. 
* The https protocol is not supported yet.
* The sequence size should be less than 10000, if the customer types a big size, the restful service will return 413 response code, which means the request entity is too large to handle.


## Highlight of this project

### Error Handling
As the restful service, we will use http code to identify the error status. For example:
* If the fibnoacci sequence size param is an integer less than zero, the 400 response will be generated.
* If the fibonacci sequence size param is not an integer, the 400 response will be generated.
* If the customer types a big sequence size (> 10000), due to the 

### Browser Side Cache
The fibonacci restful service is idempotent, which means that we can allow the browser to cache the service result.
We have specified the Cache-Control entity in the response to make sure the browser will cache the response is a long time.

### Server Side Cache
The In-Memory cache is introduced in the server side, and it is very easy to extend to the stand-alone or distributed cache systems.

### No Shared States
No shared states means that we can deploy a lot of the services in different machines, and use the load balance to achieve better performance.

## Test Cases
The start the unit test, please invoke: mvn test

The following cases are involved:
* Unit test for the fibonacci sequence compute related classes, both happy cases and edge cases are considered
* Unit test at the restful service level, the following test cases are involved:
** Response Code Test for all the scenarios (400, 404, 200, 413, etc)
** Expected returned content validation
** Http Options Test 

## TODO
* To get the benchmark, if time allowed, will use apache ab to do the performance test.

