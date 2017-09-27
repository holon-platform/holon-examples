# Holon platform examples: JAX-RS and PropertyBox using Spring Boot and a Datastore

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup __RESTful server and client__ applications using the [Holon platform JAX-RS module](https://github.com/holon-platform/holon-jaxrs) __Spring Boot__ support, with both sides `PropertyBox` data container support using _JSON_ as data exchange format, leveraging on the [Holon platform JSON module](https://github.com/holon-platform/holon-json) _Jackson_ JAX-RS support, and a __JDBC__ `Datastore` as data store.

This is the `Datastore` version of the [JAX-RS and PropertyBox using Spring Boot example](../spring-boot-propertybox).

## Topics

This example addresses the following topics:

* Setup a __JAX-RS server RESTful API__ with [PropertyBox](https://docs.holon-platform.com/current/reference/holon-core.html#PropertyBox) JSON support using __Spring Boot__.
* Use the `@PropertySetRef` annotation to handle `PropertyBox` type requests
* Setup a __JAX-RS client__ with [PropertyBox](https://docs.holon-platform.com/current/reference/holon-core.html#PropertyBox) JSON support.
* Obtain and use [RestClient](https://docs.holon-platform.com/current/reference/holon-core.html#RestClient) to invoke API operations involving `PropertyBox` object types and handle errors.
* Use a __JDBC__ `Datastore` for data persistence.

## Example structure

This JAX-RS server implements a simple __RESTful__ API to provide the [Product](src/main/java/com/holonplatform/example/jaxrs/springboot/datastore/Product.java) data model _entity_ management, backed by a __H2 database__ table named `products`, using a __JDBC__ `Datastore` for data management.

The `schema.sql` file creates the `products` table in the _test_ H2 schema at application startup.

The server API uses the `PropertyBox` class as data container and __JSON__ as data exchange format, leveraging on the [Holon platform JSON module](https://github.com/holon-platform/holon-json) _Jackson_ JAX-RS support.

The [ProductEndpoint](src/main/java/com/holonplatform/example/jaxrs/springboot/datastore/ProductEndpoint.java) class represents the API endpoint and provides operations to get a product, get all products and create/update/delete a product. It is declared as a singleton __Spring bean__ through the `@Component` annotation and it is auto-configured as JAX-RS resource by the Holon platform auto configuration facilities.

The [ProductStore](src/main/java/com/holonplatform/example/jaxrs/springboot/datastore/ProductStore.java) Spring bean uses the __JDBC__ `Datastore` to access and manage the products data using the `products` database table. The JDBC `Datastore` is automatically configured and bound to the `DataSource` declared in the `application.yml` properties file by the Holon platform DataStore auto configuration classes, imported as dependency using the _Holon JDBC Datastore with HikariCP starter_ declared in the project's `pom` file:

```xml
<dependency>
	<groupId>com.holon-platform.jdbc</groupId>
	<artifactId>holon-starter-jdbc-datastore-hikaricp</artifactId>
</dependency>
```

The [Client](src/test/java/com/holonplatform/example/jaxrs/springboot/datastore/test/Client.java) unit test class performs a set of API operations using a default `RestClient` instance obtained through the static `forTarget()` method, which creates a default `RestClient` implementation relying on the available `RestClientFactory`s (in this example, a standard platform JAX-RS _Client_ based implementation will be created) and setting a default base target URI.

The `Client` test class output will be:

```text
Created URI: http://localhost:{port}/api/products/1
Created id: 1
Updated description: Updated
Created URI: http://localhost:{port}/api/products/2
Products: 1 - Updated; 2 - Product 2
Deleted product with id: 1
Deleted product with id: 2
Products count: 0
```

### Spring boot starters

The Holon platform provides a set of __Spring Boot__ _starters_ to quickly setup a JAX-RS server with different server runtimes and JSON providers.

In this example, the _starter_ dependency declared in the project `pom` uses _Jersey_ as JAX-RS server implementation, _Tomcat_ as embedded servlet container and _Jackson_ as JSON provider:

```xml
<dependency>
	<groupId>com.holon-platform.jaxrs</groupId>
	<artifactId>holon-starter-jersey</artifactId>
</dependency>
```

You can simply replace this dependency with another one among the available the Holon Platform JAX-RS _starters_ to use a different JAX-RS implementation / embedded servlet container / JSON provider combination, for example to use [Undertow](http://undertow.io) as servlet container and/or [Gson](https://github.com/google/gson) as JSON provider or [Resteasy](http://resteasy.jboss.org) as JAX-RS implementation:

Starter Artifact id | Description
------------------- | -----------
`holon-starter-jersey` | Spring Boot JAX-RS _server_ starter using __Jersey__, __Tomcat__ and __Jackson__ as JSON provider
`holon-starter-jersey-gson` | Spring Boot JAX-RS _server_ starter using __Jersey__, __Tomcat__ and __Gson__ as JSON provider
`holon-starter-jersey-undertow` | Spring Boot JAX-RS _server_ starter using __Jersey__, __Undertow__ and __Jackson__ as JSON provider
`holon-starter-jersey-undertow-gson` | Spring Boot JAX-RS _server_ starter using __Jersey__, __Undertow__ and __Gson__ as JSON provider
`holon-starter-jersey-client` | Spring Boot JAX-RS _client_ starter using __Jersey__ and __Jackson__ as JSON provider
`holon-starter-jersey-client-gson` | Spring Boot JAX-RS _client_ starter using __Jersey__ and __Gson__ as JSON provider
`holon-starter-resteasy` | Spring Boot JAX-RS _server_ starter using __Resteasy__, __Tomcat__ and __Jackson__ as JSON provider
`holon-starter-resteasy-gson` | Spring Boot JAX-RS _server_ starter using __Resteasy__, __Tomcat__ and __Gson__ as JSON provider
`holon-starter-resteasy-undertow` | Spring Boot JAX-RS _server_ starter using __Resteasy__, __Undertow__ and __Jackson__ as JSON provider
`holon-starter-resteasy-undertow-gson` | Spring Boot JAX-RS _server_ starter using __Resteasy__, __Undertow__ and __Gson__ as JSON provider

For the __client__ side:

Starter Artifact id | Description
------------------- | -----------
`holon-starter-resteasy-client` | Spring Boot JAX-RS _client_ starter using __Resteasy__ and __Jackson__ as JSON provider
`holon-starter-resteasy-client-gson` | Spring Boot JAX-RS _client_ starter using __Resteasy__ and __Gson__ as JSON provider

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://docs.holon-platform.com/current/reference).

For the specific documentation about the modules and the components used in this example see:

* [Holon platform JSON module reference documentation](https://docs.holon-platform.com/current/reference/holon-json.html)
* [Holon platform JAX-RS module reference documentation](https://docs.holon-platform.com/current/reference/holon-jaxrs.html)
* Documentation about the [PropertyBox](https://docs.holon-platform.com/current/reference/holon-core.html#PropertyBox)  data structure
* Documentation about the [RestClient](https://docs.holon-platform.com/current/reference/holon-core.html#RestClient) platform RESTful client
* Documentation about the [Datastore](https://docs.holon-platform.com/current/reference/holon-core.html#Datastore)  API

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
