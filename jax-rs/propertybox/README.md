# Holon platform examples: JAX-RS and PropertyBox

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup __RESTful server and client__ applications using the [Holon platform JAX-RS module](https://github.com/holon-platform/holon-jaxrs), with both sides `PropertyBox` data container support using _JSON_ as data exchange format, leveraging on the [Holon platform JSON module](https://github.com/holon-platform/holon-json) _Jackson_ JAX-RS support.

## Topics

This example addresses the following topics:

* Setup a __JAX-RS server RESTful API__ with [PropertyBox](https://holon-platform.com/docs/current/reference/holon-core.html#PropertyBox) JSON support.
* Use the `@PropertySetRef` annotation to handle `PropertyBox` type requests
* Setup a __JAX-RS client__ with [PropertyBox](https://holon-platform.com/docs/current/reference/holon-core.html#PropertyBox) JSON support.
* Obtain and use [RestClient](https://holon-platform.com/docs/current/reference/holon-core.html#RestClient) to invoke API operations involving `PropertyBox` object types and handle errors.

## Example Data model

This example uses the [Examples shared data model](https://github.com/holon-platform/holon-examples/tree/master/model) artifact, which is declared as dependency both for the __server__ and the __client__ module.

## Example structure

This example project is composed of two modules: __server__ and __client__.

### Server module

The __server__ module is a JAX-RS server implementation using _Jersey_ and _Grizzly web server_ to provide a simple __RESTful__ API to provide the `MProduct` data model _entity_ management, backed by an in-memory store. The `holon-jaxrs-server` artifact provides support and dependencies for JAX-RS server setup.

The server API uses the `PropertyBox` class as data container and __JSON__ as data exchange format, leveraging on the [Holon platform JSON module](https://github.com/holon-platform/holon-json) _Jackson_ JAX-RS support. The _Jackson_ support for the `PropertyBox` type is provided by the `holon-jackson-jaxrs` artifact and it is automatically configured in the JAX-RS server.

The `ProductEndpoint` class represents the API endpoint and provides operations to get a product, get all products and create/update/delete a product.

To start the server run the `Server` class `main` method, a _Jersey Grizzly_ server will be started and listening to port `8080` at `localhost`, exposing the API endpoint at the `/api` path, until any key is pressed on console.

### Client module

The __server__ module is a JAX-RS client implementation using _Jersey_, leveraging on the `holon-jaxrs-client` artifact and on the `holon-jackson-jaxrs` artifact for `PropertyBox` type __JSON__ support using  _Jackson_.

The main `Client` class performs a set of API operations (expecting the __server__ module running and listening to the `http://localhost:8080/api` base URI) using a default `RestClient` instance obtained through the static `forTarget()` method, which creates a default `RestClient` implementation relying on the available `RestClientFactory`s and setting a default base target URI.

Since the `holon-jaxrs-client` artifact is present in classpath, the actual `RestClient` implementation will be the standard platform JAX-RS _Client_ based implementation. 

The `Client` main method output will be:

```text
Created URI: http://localhost:8080/api/products/1
Created id: 1
Updated description: Updated
Created URI: http://localhost:8080/api/products/2
Products: 1 - Updated; 2 - Product 2
Deleted product with id: 1
Deleted product with id: 2
Products count: 0
```

### Using Gson as JSON provider

To use [Gson](https://github.com/google/gson) as JSON provider instead of _Jackson_, simply replace the `holon-jackson-jaxrs` dependency with the `holon-gson-jaxrs` dependency in both server and client project `pom`.

### Using Spring Boot

To learn how to use __Spring Boot__ for JAX-RS server setup, see the [JAX-RS and PropertyBox with Spring Boot example](../spring-boot-propertybox).

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://holon-platform.com/docs/current/reference).

For the specific documentation about the modules and the components used in this example see:

* [Holon platform JSON module reference documentation](https://holon-platform.com/docs/current/reference/holon-json.html)
* [Holon platform JAX-RS module reference documentation](https://holon-platform.com/docs/current/reference/holon-jaxrs.html)
* Documentation about the [PropertyBox](https://holon-platform.com/docs/current/reference/holon-core.html#PropertyBox)  data structure
* Documentation about the [RestClient](https://holon-platform.com/docs/current/reference/holon-core.html#RestClient) platform RESTful client

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
