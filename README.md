# Holon Platform Examples

This repository contains a set of example projects using the [Holon Platform](https://holon-platform.com) modules, organized by argument.

## Holon Platform version

The examples in the _master_ branch refers to the Holon Platform version __5.2.x__.

- For the __5.0.x__ version examples, see the [5.0.x repository branch](https://github.com/holon-platform/holon-examples/tree/5.0.x).

- For the __5.1.x__ version examples, see the [5.1.x repository branch](https://github.com/holon-platform/holon-examples/tree/5.1.x).

## Documentation

See the [Holon platform reference guide](https://docs.holon-platform.com/current/reference).

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
[mongo-datastore](datastore/mongo-datastore)| `Datastore` API example using the **MongoDB** Datastore implementation

### JAX-RS

Example | Description
------- | -----------
[propertybox](jax-rs/propertybox)| JAX-RS server and client RESTful API using `PropertyBox` and JSON
[spring-boot-propertybox](jax-rs/spring-boot-propertybox)| JAX-RS server and client RESTful API using `PropertyBox`, JSON and __Spring Boot__
[spring-boot-datastore](jax-rs/spring-boot-datastore)| JAX-RS server and client RESTful API using `PropertyBox`, JSON, __Spring Boot__ and a JDBC `Datastore`
[spring-boot-auth-basic](jax-rs/spring-boot-auth-basic)| JAX-RS server and client RESTful API with __authentication__ and __authorization__ support using the HTTP `Basic` authorization scheme
[spring-boot-auth-jwt](jax-rs/spring-boot-auth-jwt)| JAX-RS server and client RESTful API with __JWT (JSON Web Token)__ based authentication
[swagger](jax-rs/swagger)| JAX-RS [Swagger/OpenAPI](https://swagger.io) __version 3__ integration and auto-configuration with `PropertyBox` support
[swagger-groups](jax-rs/swagger-groups)| JAX-RS [Swagger/OpenAPI](https://swagger.io) __version 3__ integration and auto-configuration using multiple API _groups_ and multiple Swagger API listing endpoints
[swagger-v2](jax-rs/swagger-v2)| JAX-RS [Swagger](https://swagger.io) __version 2__ integration and auto-configuration with `PropertyBox` support

### Vaadin UI

Example | Description
------- | -----------
[Vaadin Flow (10+) simple-app](ui-vaadin-flow/simple-app)| A simple web application using __Vaadin Flow__ as UI platform and the `Datastore` API for data management
[Vaadin 8 simple-app](ui-vaadin/simple-app)| A simple web application using __Vaadin 8__ as UI platform and the `Datastore` API for data management
[Vaadin 7 simple-app](ui-vaadin7/simple-app)| A simple web application using __Vaadin 7__ as UI platform and the `Datastore` API for data management

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run the example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE).
