# Holon Platform samples

This repository contains a set of example projects using the [Holon Platform](https://holon-platform.com) modules, organized by argument.

## Documentation

See the [Holon platform reference guide](https://holon-platform.com/docs/current/reference).

## Available examples

### Core
Example | Description
------- | -----------
[property-model](core/property-model)| A `Property` model example, showing how to use `PropertySet` and `PropertyBox` APIs and how to obtain a property model from a Java Bean class
[spring-context](core/spring-context)| Core __Spring__ context support for Holon Platform `Context` resources
[spring-boot-context](core/spring-boot-context)| Core __Spring Boot__ support for Holon Platform `Context` resources

### Datastore
Example | Description
------- | -----------
[jdbc-datastore](datastore/jdbc-datastore)| `Datastore` API example using the **JDBC** Datastore implementation
[jpa-datastore](datastore/jpa-datastore)| `Datastore` API example using the **JPA** Datastore implementation

### JAX-RS

Example | Description
------- | -----------
[propertybox](jax-rs/propertybox)| JAX-RS server and client RESTful API using `PropertyBox` and JSON
[spring-boot-propertybox](jax-rs/spring-boot-propertybox)| JAX-RS server and client RESTful API using `PropertyBox`, JSON and __Spring Boot__
[spring-boot-datastore](jax-rs/spring-boot-datastore)| JAX-RS server and client RESTful API using `PropertyBox`, JSON, __Spring Boot__ and a JDBC `Datastore`
[spring-boot-auth-basic](jax-rs/spring-boot-auth-basic)| JAX-RS server and client RESTful API with __authentication__ and __authorization__ support using the HTTP `Basic` authorization scheme
[spring-boot-auth-jwt](jax-rs/spring-boot-auth-jwt)| JAX-RS server and client RESTful API with __JWT (JSON Web Token)__ based authentication
[swagger](jax-rs/swagger)| JAX-RS [Swagger](https://swagger.io) integration and auto-configuration with `PropertyBox` support
[swagger-groups](jax-rs/swagger-groups)| JAX-RS [Swagger](https://swagger.io) integration and auto-configuration using multiple API _groups_ with multiple Swagger API listing endpoints

### Vaadin UI

Example | Description
------- | -----------
[simple-app](ui-vaadin/simple-app)| A simple web application using __Vaadin__ as UI engine and the `Datastore` API for data management

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run the example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).
