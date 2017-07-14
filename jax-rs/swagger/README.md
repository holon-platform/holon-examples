# Holon platform examples: JAX-RS and PropertyBox Swagger API documentation

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup a [Swagger](https://swagger.io) API documentation endpoint using the [Holon platform JAX-RS module](https://github.com/holon-platform/holon-jaxrs) __Spring Boot__ starters, with `PropertyBox` data type support.

## Topics

This example addresses the following topics:

* Use the `@ApiPropertySetModel` annotation to declare a _property set_ as a named Swagger __model__ bound to `PropertyBox` type API operations requests and responses
* Use the Holon JAX-RS Swagger integrations for __Spring Boot__ to automatically setup and configure a Swagger API listing endpoint.

## Example Data model

This example uses the [Examples shared data model](https://github.com/holon-platform/holon-examples/tree/master/model) artifact.

## Example structure

This JAX-RS server implements a simple __RESTful__ API to provide the `MProduct` data model _entity_ management, backed by an in-memory store, using _Jersey_ as JAX-RS implementation and _Tomcat_ as servlet container.

The server API uses the `PropertyBox` class as data container and __JSON__ as data exchange format, leveraging on the [Holon platform JSON module](https://github.com/holon-platform/holon-json) _Jackson_ JAX-RS support.

The [ProductEndpoint](src/main/java/com/holonplatform/example/jaxrs/swagger/ProductEndpoint.java) class represents the API endpoint and provides operations to get a product, get all products and create/update/delete a product. It is declared as a singleton __Spring bean__ through the `@Component` annotation and it is auto-configured as JAX-RS resource by the Holon platform auto configuration facilities.

Some Swagger annotations are used to provide API operations description (`@ApiOperation`) and response details (`@ApiResponses`).

### The `@ApiPropertySetModel` annotation

The `@ApiPropertySetModel` annotation is used to create a Swagger __model__ definition, with a given name, from a Holon `PropertySet` declaration. 

The `@PropertySetRef` annotation is used to declare the `PropertySet` which is bound to a `PropertyBox` request or response object type.

To improve code organization, consistency and readability, the [ProductModel](src/main/java/com/holonplatform/example/jaxrs/swagger/ProductModel.java) annotation is defined, which is in turn annotated with `@ApiPropertySetModel` (using the __Product__ model name) and `@PropertySetRef` (declaring the `MProduct` class as the source of the product `PropertySet`).

The `ProductModel` annotation is used in API operations to qualify a `PropertySet` type request body or response type.

This way, a `Product` _model_ definition will be declared in the Swagger API definitions and used as a reference for each `PropertySet` type request or response element.

### Swagger API listing endpoint configuration

The `holon-jaxrs-swagger` artifact of the [Holon platform JAX-RS module](https://github.com/holon-platform/holon-jaxrs), declared as dependency in project's `pom`, provides __Spring Boot__ integration to auto-configure the Swagger API listing endpoint, using the `holon.swagger.*` configuration properties (see [application.yml](src/main/resources/application.yml)).

This way, a JAX-RS __Swagger API listing endpoint__ to generate and provide the Swagger API documentation is automatically configured and listing at the `api-docs` path by default:

```text
http://localhost:8080/api/api-docs
```

The endpoint provides the Swagger definitions in __JSON__ by default, but the `type` parameter can be used to specify the response format: `json` or `yaml`.

```text
http://localhost:8080/api/api-docs?type=yaml
```

To change the path of the API listing endpoint, the `holon.swagger.path` property can be used.
So, for example, setting `holon.swagger.path=docs` will result in the following Swagger API listing URL:

```text
http://localhost:8080/api/docs?type=yaml
```

See the [Swagger JAX-RS integration documentation](https://holon-platform.com/docs/current/reference/holon-jaxrs.html#Swagger) for details and other configuration options.

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://holon-platform.com/docs/current/reference).

For the specific documentation about the modules and the components used in this example see:

* [Holon platform JAX-RS module reference documentation](https://holon-platform.com/docs/current/reference/holon-jaxrs.html)
* [Holon platform JAX-RS Swagger integration](https://holon-platform.com/docs/current/reference/holon-jaxrs.html#Swagger)
* Documentation about the [PropertyBox](https://holon-platform.com/docs/current/reference/holon-core.html#PropertyBox)  data structure

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
