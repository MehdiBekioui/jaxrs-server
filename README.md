# Jaxrs Server

**Jaxrs Server** provides a server based on [Netty framework](http://netty.io/) to expose your RESTful API.

## Contents

- [Features](#features)
- [Get it](#get-it)
- [Use it](#use-it)
	- [Default case example](#default-case-example)
	- [Swagger management example](#swagger-management-example)
	- [Security](#security)
- [Configuration](#configuration)
	- [General](#general)
	- [CORS](#cors)
	- [Swagger](#swagger)

## Features

* Expose resources annotated with [Path](https://docs.oracle.com/javaee/7/api/javax/ws/rs/Path.html)
* Expose providers annotated with [Provider](https://docs.oracle.com/javaee/7/api/javax/ws/rs/ext/Provider.html)
* Enable Cross-Origin Resource sharing
* Provide [Swagger](http://swagger.io/) resource to the following url http://host**/swagger-api**
* Enable security to use embedded [jaxrs-security](https://github.com/MehdiBekioui/jaxrs-security) library

## Get it

Add the following to your Maven configuration:

```xml
<dependency>
	<groupId>com.bekioui.jaxrs</groupId>
	<artifactId>jaxrs-server</artifactId>
	<version>1.1.0</version>
</dependency>
```

## Use it

#### Default case example

Add the following to your Spring configuration file:

```java
@Configuration
@Import({ JaxrsServerConfig.class })
public class SpringConfig {

  @Bean
  public DeploymentResourceDescriptor getDeploymentResourceDescriptor() {
    return () -> MyResource.class.getPackage();
  }
  
}
```

* This bean is required.
* You can define many descriptors.
* If you want to deploy all of your resources, return the highest package level.

#### Swagger management example

You need to enable CORS and Swagger first (look [Configuration](#configuration)).

Add the following to your Spring configuration file:

```java
@Bean
public SwaggerResourceDescriptor getSwaggerResourceDescriptor() {
  return () -> MyOtherResource.class.getPackage();
}
```

* This bean is not required.
* You can define many descriptors.
* **DeploymentResourceDescriptor** is used by default if this bean is not defined with swagger enabled.
* If you want to expose a part of your API, return the selected package.

#### Security

You need to enable security first (look [Configuration](#configuration)).

Look [jaxrs-security](https://github.com/MehdiBekioui/jaxrs-security) library for more informations.

## Configuration

#### General

| Property                          					       |          								 |
|--------------------------------------------------------------|-----------------------------------------|
| jaxrs.server.rootResourcePath     						   |										 |
| jaxrs.server.port                 						   | default: 8080                           |
| jaxrs.server.maxRequestSize      						       | default: 10 485 760 (1024 * 1024 * 10)  |
| jaxrs.server.security.enabled          					   | default: false                          |

#### CORS

| Property                          					       |          								 |
|--------------------------------------------------------------|-----------------------------------------|
| jaxrs.server.cors.enabled          					       | default: false                          |
| jaxrs.server.cors.allowCredentials						   | 				                         |
| jaxrs.server.cors.allowedHeaders  						   |										 |
| jaxrs.server.cors.allowedMethods  	   					   |										 |
| jaxrs.server.cors.maxAge         						       |										 |
| jaxrs.server.cors.exposedHeaders 						       |										 |
| jaxrs.server.cors.allowedOrigins   					       | 										 |

#### Swagger

| Property                          					       |          								 |
|--------------------------------------------------------------|-----------------------------------------|
| jaxrs.server.swagger.enabled          					   | default: false                          |
| jaxrs.server.swagger.host								       | default: localhost:8080				 |
| jaxrs.server.swagger.info.contact.email 				       |										 |
| jaxrs.server.swagger.info.contact.name 				       |										 |
| jaxrs.server.swagger.info.contact.url 					   |										 |
| jaxrs.server.swagger.info.description 					   |										 |
| jaxrs.server.swagger.info.license.name 				       |										 |
| jaxrs.server.swagger.info.license.url 					   |										 |
| jaxrs.server.swagger.info.license.vendorExtensions.name      |										 |
| jaxrs.server.swagger.info.license.vendorExtensions.object    |										 |
| jaxrs.server.swagger.info.termsOfService 				       |										 |
| jaxrs.server.swagger.info.title 						       |										 |
| jaxrs.server.swagger.info.vendorExtensions.name 		       |										 |
| jaxrs.server.swagger.info.vendorExtensions.object 		   |										 |
| jaxrs.server.swagger.info.version 						   |										 |

## Credits

[Romain Sertelon](https://github.com/rsertelon)

## License
	
	Copyright (C) 2016 Mehdi Bekioui (consulting@bekioui.com)
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
		http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.	