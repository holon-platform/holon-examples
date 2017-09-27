# Holon platform examples: JAX-RS Authentication and Authorization using Spring Boot

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup __RESTful server and client__ applications using the [Holon platform JAX-RS module](https://github.com/holon-platform/holon-jaxrs) __Spring Boot__ support, and enable _authentication_ and _authorization_ using the HTTP `Basic` scheme. 

## Topics

This example addresses the following topics:

* Configure JAX-RS resources _authentication_ using the `@Authenticate` annotation and a [Realm](https://docs.holon-platform.com/current/reference/holon-core.html#Realm) as backend.
* Configure JAX-RS resources _authorization_ using `javax.annotation.security.*` annotations.
* Setup a __JAX-RS client__ using a `RestClient` and perform invocations with the HTTP `Basic` Authorization header.

## Example structure

The [ProtectedEndpoint](src/main/java/com/holonplatform/example/jaxrs/springboot/auth/basic/ProtectedEndpoint.java) class represents a protected API endpoint, using the `@Authenticate` annotation to require __authentication__ to access the endpoint operation methods.

Each method is annotated with a `javax.annotation.security.*` annotation to enable role-based __authorization__ control for the API operation represented by the method.

The authentication and authorization strategy is implemented using a Holon Platform `Realm` structure, configured with the following components:

* A HTTP `Basic` scheme `AuthenticationTokenResolver`, to process request messages and extract a suitable authentication token;
* An _Account_ `Authenticator` based on an `AccountProvider` class, used to obtain the accounts information, which uses the authentication token described above to perform account authentication.

Thanks to the Holon Platform Spring Boot support, the `Realm` bean is automatically detected by the JAX-RS authentication feature, which is automatically registered in the JAX-RS server application and triggered when the `@Authenticate` annotation is found on a JAX-RS resource class or method.

If message authentication fails, a `401 - Unauthorized` response HTTP code is returned. If method role-based authorization control is not successful, a `403 - Forbidden` response HTTP code is returned.

The [Client](src/test/java/com/holonplatform/example/jaxrs/springboot/auth/basic/test/Client.java) unit test class performs a set of API operations using a default `RestClient` instance obtained through the `forTarget()` method, which creates a default `RestClient` implementation relying on the available `RestClientFactory`s (in this example, a standard platform JAX-RS _Client_ based implementation will be created) and setting a default base target URI.

The client authentication credentials are provided using the `RestClient` `authorizationBasic(username, password)` request configuration method, which sets up a HTTP `Authorization` header, using the `Basic` scheme, for the request. 

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://docs.holon-platform.com/current/reference).

For the specific documentation about the modules and the components used in this example see:

* [Holon platform Authentication and Authorization architecture documentation](https://docs.holon-platform.com/current/reference/holon-core#Auth.html)
* [Holon platform JAX-RS module reference documentation](https://docs.holon-platform.com/current/reference/holon-jaxrs.html)

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
