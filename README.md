# Jaxrs Server

**Jaxrs Server** provides a server based on [Netty framework](http://netty.io/) to expose your RESTful API.

## Contents

- [Features](#features)
- [Get it](#get-it)
- [Use it](#use-it)
	- [Default case example](#default-case-example)
	- [Swagger management example](#swagger-management-example)
	- [Security management example](#security-management-example)
- [Configuration](#configuration)
	- [General](#general)
	- [CORS](#cors)
	- [Swagger](#swagger)

## Features

* Expose resources annotated with [Path](https://docs.oracle.com/javaee/7/api/javax/ws/rs/Path.html)
* Expose providers annotated with [Provider](https://docs.oracle.com/javaee/7/api/javax/ws/rs/ext/Provider.html)
* Enable Cross-Origin Resource sharing
* Provide [Swagger](http://swagger.io/) resource to the following url http://host**/swagger-api**
* Enable security
* Applying javax.annotation.security to JAX-RS resource methods

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

#### Security management example

You can apply **javax.annotation.security** to your JAX-RS resource methods like **RolesAllowed**.

```java
@Path("/contracts")
public interface ContractResource {

  @GET
  @RolesAllowed("ADMIN")
  List<String> findAll();
	
}
```

For this example we have a simple token DTO to represent an user.

```java
@AutoValue
public abstract class Token {

  @JsonCreator
  public static Token create(@JsonProperty("name") String name, @JsonProperty("roles") Set<String> roles) {
    return new AutoValue_Token(name, roles);
  }

  @JsonProperty("name")
  public abstract String name();

  @JsonProperty("roles")
  public abstract Set<String> roles();

}
```

You need to enable security and authorization first (look [Configuration](#configuration)).

Authorization filter needs to deserialize the authorization header in order to update the request security context. You need to define the deserializer in your Spring configuration file.

```java
@Bean
public AuthorizationDeserializer getAuthorizationDeserializer() {
  return authorization -> {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      Token token = objectMapper.readValue(authorization, Token.class);
      return new AuthorizationContext() {
        @Override
        public Principal getPrincipal() {
          return () -> token.name();
        }
        @Override
        public Set<String> getRoles() {
          return token.roles();
        }
      };
    } catch (IOException e) {
      return null;
    }
  };
}
```

* This bean is required if authorization is enabled.
* You can only enable security and use your own authorization filter (do not enable authorization in this case).

## Configuration

#### General

| Property                          					       |          								 |
|--------------------------------------------------------------|-----------------------------------------|
| jaxrs.server.rootResourcePath     						   |										 |
| jaxrs.server.port                 						   | default: 8080                           |
| jaxrs.server.maxRequestSize      						       | default: 10 485 760 (1024 * 1024 * 10)  |
| jaxrs.server.security.enabled          					   | default: false                          |
| jaxrs.server.authorization.enabled          				   | default: false                          |

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