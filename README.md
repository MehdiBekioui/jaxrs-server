# Jaxrs Server

**Jaxrs Server** provides a server based on [Netty framework](http://netty.io/) to expose your RESTful API.

## Features

* Expose resources annotated with [Path](https://docs.oracle.com/javaee/7/api/javax/ws/rs/Path.html)
* Expose providers annotated with [Provider](https://docs.oracle.com/javaee/7/api/javax/ws/rs/ext/Provider.html)
* Enable Cross-Origin Resource sharing
* Provide [Swagger](http://swagger.io/) resource to the following url http://host**/swagger-api**

## Get it
> Not yet available

Add the following to your Maven configuration:

```xml
<dependency>
	<groupId>com.bekioui.jaxrs</groupId>
	<artifactId>jaxrs-server</artifactId>
	<version>1.0.0</version>
</dependency>
```

## Use it

Add the following to your Spring configuration file:

```java
@Configuration
@Import({ NettyConfig.class })
public class SpringConfig {

}
```

## Configure it

| Property                          					   |          								 |
|----------------------------------------------------------|-----------------------------------------|
| netty.jaxrs.rootResourcePath     						   |										 |
| netty.jaxrs.port                 						   | default: 8080                           |
| netty.jaxrs.maxRequestSize      						   | default: 10 485 760 (1024 * 1024 * 10)  |
| netty.jaxrs.cors.enabled          					   | default: false                          |
| netty.jaxrs.cors.allowCredentials						   | 				                         |
| netty.jaxrs.cors.allowedHeaders  						   |										 |
| netty.jaxrs.cors.allowedMethods  	   					   |										 |
| netty.jaxrs.cors.maxAge         						   |										 |
| netty.jaxrs.cors.exposedHeaders 						   |										 |
| netty.jaxrs.cors.allowedOrigins   					   | 										 |
| netty.jaxrs.swagger.enabled          					   | default: false                          |
| netty.jaxrs.swagger.host								   | default: localhost:8080				 |
| netty.jaxrs.swagger.info.contact.email 				   |										 |
| netty.jaxrs.swagger.info.contact.name 				   |										 |
| netty.jaxrs.swagger.info.contact.url 					   |										 |
| netty.jaxrs.swagger.info.description 					   |										 |
| netty.jaxrs.swagger.info.license.name 				   |										 |
| netty.jaxrs.swagger.info.license.url 					   |										 |
| netty.jaxrs.swagger.info.license.vendorExtensions.name   |										 |
| netty.jaxrs.swagger.info.license.vendorExtensions.object |										 |
| netty.jaxrs.swagger.info.termsOfService 				   |										 |
| netty.jaxrs.swagger.info.title 						   |										 |
| netty.jaxrs.swagger.info.vendorExtensions.name 		   |										 |
| netty.jaxrs.swagger.info.vendorExtensions.object 		   |										 |
| netty.jaxrs.swagger.info.version 						   |										 |

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