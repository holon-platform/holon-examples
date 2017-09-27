# Holon platform examples: JAX-RS Swagger documentation with multiple API groups

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup [Swagger](https://swagger.io) JAX-RS integration using the [Holon platform JAX-RS module](https://github.com/holon-platform/holon-jaxrs) __Spring Boot__ starters, with `PropertyBox` data type support, and using API _groups_ to provide multiple Swagger API documentation endpoints.

## Topics

This example addresses the following topics:

* Use the `@ApiPropertySetModel` annotation to declare a _property set_ as a named Swagger __model__ bound to `PropertyBox` type API operations requests and responses
* Configure multiple API _groups_ for Swagger API documentation relying on Holon JAX-RS Swagger __Spring Boot__ integration

## Example Data model

This example uses the [Examples shared data model](https://github.com/holon-platform/holon-examples/tree/master/model) artifact.

## Example structure

This JAX-RS server implements a simple __RESTful__ API to provide the `MProduct` and the `MCustomer` data model _entity_ management, backed by an in-memory store, using _Resteasy_ as JAX-RS implementation and _Undertow_ as servlet container.

The server API uses the `PropertyBox` class as data container and __JSON__ as data exchange format, leveraging on the [Holon platform JSON module](https://github.com/holon-platform/holon-json) _Jackson_ JAX-RS support.

### Products management API

The `products` package contains the __products__ management API, including the in-memory store and the [ProductEndpoint](src/main/java/com/holonplatform/example/jaxrs/swagger/products/ProductEndpoint.java) JAX-RS resource which represents the products management API.

The JAX-RS resource is declared as a singleton __Spring bean__ through the `@Component` annotation and it is auto-configured as JAX-RS resource by the Holon platform auto configuration facilities.

Some Swagger annotations are used to provide API operations description (`@ApiOperation`) and response details (`@ApiResponses`).

The [ProductModel](src/main/java/com/holonplatform/example/jaxrs/swagger/products/ProductModel.java) annotation is used to declare the product `PropertyBox` _property set_ (i.e. the `PropertySet` declared in the `MProduct` model class) and to declare a Swagger _model_ definition, named `Product` and bound to the product _property set_, through the `@ApiPropertySetModel` annotation.

### Customers management API

The `customers` package contains the __customers__ management API, including the in-memory store and the [CustomerEndpoint](src/main/java/com/holonplatform/example/jaxrs/swagger/customers/CustomerEndpoint.java) JAX-RS resource which represents the customers management API.

The JAX-RS resource is declared as a singleton __Spring bean__ through the `@Component` annotation and it is auto-configured as JAX-RS resource by the Holon platform auto configuration facilities.

Some Swagger annotations are used to provide API operations description (`@ApiOperation`) and response details (`@ApiResponses`).

The [CustomerModel](src/main/java/com/holonplatform/example/jaxrs/swagger/customers/CustomerModel.java) annotation is used to declare the customer `PropertyBox` _property set_ (i.e. the `PropertySet` declared in the `MCustomer` model class) and to declare a Swagger _model_ definition, named `Customer` and bound to the customer _property set_, through the `@ApiPropertySetModel` annotation.

### Multiple Swagger API listing endpoints configuration

The `holon-jaxrs-swagger` artifact of the [Holon platform JAX-RS module](https://github.com/holon-platform/holon-jaxrs), declared as dependency in project's `pom`, provides __Spring Boot__ integration to auto-configure the Swagger API listing endpoint, using the `holon.swagger.*` configuration properties (see [application.yml](src/main/resources/application.yml)).

In this example, the whole API is divided into two _groups_ to provide two different Swagger API listing endpoints:

__1.__ the `products` group, bound to the `products` package (containing the `ProductEndpoint` JAX-RS resource) and bound to the `docs/products` path. The Swagger API listing endpoint URL will be:

```text
http://localhost:8080/api/docs/products
```

__2.__ the `customers` group, bound to the `customers` package (containing the `CustomerEndpoint` JAX-RS resource) and bound to the `docs/customers` path. The Swagger API listing endpoint URL will be:

```text
http://localhost:8080/api/docs/customers
```

This is achieved using the following [application.yml](src/main/resources/application.yml) configuration properties:

```yaml
holon:
  swagger:
    version: "v1"
    title: "Swagger API groups"
    pretty-print: true
    
    api-groups:
      - group-id: "products"
        resource-package: "com.holonplatform.example.jaxrs.swagger.products"
        description: "Products management API"
        path: docs/products
      - group-id: "customers"
        resource-package: "com.holonplatform.example.jaxrs.swagger.customers"
        description: "Customers management API"
        path: docs/customers
```

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://docs.holon-platform.com/current/reference).

For the specific documentation about the modules and the components used in this example see:

* [Holon platform JAX-RS module reference documentation](https://docs.holon-platform.com/current/reference/holon-jaxrs.html)
* [Holon platform JAX-RS Swagger integration](https://docs.holon-platform.com/current/reference/holon-jaxrs.html#Swagger)
* Documentation about the [PropertyBox](https://docs.holon-platform.com/current/reference/holon-core.html#PropertyBox)  data structure

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
