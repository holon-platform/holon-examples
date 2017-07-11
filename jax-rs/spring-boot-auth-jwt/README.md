# Holon platform examples: JAX-RS Authentication and Authorization using JWT

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup:

* A JAX-RS server to issue __JWT (JSON Web Token)__ tokens relying on a account store.
* A JAX-RS __RESTful server__ to provide a protected API relying on JWT `Bearer` authentication schema.

## Topics

This example addresses the following topics:

* Use the `holon-auth-jwt` module to configure and manage _JWT_ tokens
* Configure JAX-RS resources _authentication_ using the `@Authenticate` annotation and a [Realm](https://holon-platform.com/docs/current/reference/holon-core.html#Realm) with a _JWT_ authenticator
* Configure JAX-RS resources _authorization_ using `javax.annotation.security.*` annotations.
* Setup a __JAX-RS client__ using a `RestClient` and perform invocations with the HTTP `Bearer` Authorization header.

## Example structure

This example is composed by 3 artifacts:

* The [JWT token issuer and account manager](issuer)
* The [JAX-RS API resource server](server)
* A [JAX-RS client](client) which invokes the __issuer__ server to obtain a JWT token and use it to access the __API server__ operations

### JWT issuer server

This artifact relies on the [Holon platform JAX-RS module](https://github.com/holon-platform/holon-jaxrs) __Spring Boot__ support to setup a web server (using __HTTPS__) to provide _JWT tokens_ through the [JwtIssuerEndpoint](issuer/src/main/java/com/holonplatform/example/jaxrs/springboot/auth/jwt/JwtIssuerEndpoint.java) to an _authenticated_ client account.

Client authentication is performed using the Holon Platform [Realm](https://holon-platform.com/docs/current/reference/holon-core.html#Realm) API, with a HTTP `Basic` scheme message resolver and an _Account_ authenticator.

The _accounts_ and _roles_ data is stored in a __H2__ in-memory database, initialized with the `schema.sql` and `data.sql` scripts, accessed through a JDBC `Datastore` by the [AccountService](issuer/src/main/java/com/holonplatform/example/jaxrs/springboot/auth/jwt/AccountService.java).

A client can obtain a JWT token using the `https://localhost:8443/jwt/issue` URL, providing its authentication credentials (account id and secret) with a HTTP `Basic` scheme authorization header.

### JAX-RS API server

A JAX-RS server, configured using the [Holon platform JAX-RS module](https://github.com/holon-platform/holon-jaxrs) __Spring Boot__ support, makes available a [ProtectedEndpoint](server/src/main/java/com/holonplatform/example/jaxrs/springboot/auth/jwt/ProtectedEndpoint.java) resource, using _JWT_ for authentication and `javax.annotation.security.*` annotations for operations authorization control.

Authentication is performed using the Holon Platform [Realm](https://holon-platform.com/docs/current/reference/holon-core.html#Realm) API, with a HTTP `Bearer` scheme message resolver and a _JWT_ based authenticator.

The _JWT_ configuration used by both _issuer_ and _server_ modules is automatically setted up using the `application.yml` `holon.jwt.*` configuration properties. In the specific case, the _issuer_ and _server_ modules shares the __signing key__ used to build and to verify the JWT token.

### Client

The [Client](client/src/test/java/com/holonplatform/example/jaxrs/springboot/auth/jwt/Client.java) class uses a `RestClient` to perform the following operations:

* Obtain a _JWT Token_ invoking the `https://localhost:8443/jwt/issue` URL, providing its authentication credentials (`act1` as account id and `act1secret` as password) with a HTTP `Basic` scheme authorization header.

* Uses the obtained _JWT Token_ as HTTP `Bearer` authorization header value to invoke the _server_ API at the  `http://localhost:8080/api` URL.

The `Client` main method output will be:

```text
JWT token: eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE0OTk3ODgzMzIsImp0aSI6ImJjYTZmYmU1L...[OMITTED]
Roles: ROLE1,ROLE3
Role1: role1
Role2: 403 - Forbidden
```

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://holon-platform.com/docs/current/reference).

For the specific documentation about the modules and the components used in this example see:

* [Holon platform Authentication and Authorization architecture documentation](https://holon-platform.com/docs/current/reference/holon-core#Auth.html)
* [Holon platform JAX-RS module reference documentation](https://holon-platform.com/docs/current/reference/holon-jaxrs.html)

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
