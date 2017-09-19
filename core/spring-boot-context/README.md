# Holon platform examples: Spring Boot context resources

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup a Holon Platform `Context` scope, using **Spring Boot** auot-configuration, to provide context resource instances as Spring beans. This way, any valid Spring bean can be obtained as a context resource using the Holon Platform standard `Context` API, for example through the **static** `Context.get().resource(Class<T> resourceType)` method.

The `holon-starter` Spring Boot starter is used.

## Topics

This example addresses the following topics:

* Use the Holon Platform core Spring Boot support to auto-configure the Spring beans Context scope
* Use the `Context` API to obtain context resources

## Example structure

The [ExampleResource](https://github.com/holon-platform/holon-examples/blob/master/core/spring-boot-context/src/main/java/com/holonplatform/example/core/context/ExampleResource.java) interface represents the example class to be made available as a `Context` resource.

The [Application](https://github.com/holon-platform/holon-examples/blob/master/core/spring-boot-context/src/main/java/com/holonplatform/example/core/context/Application.java) class is the Spring Boot starter class and shows how to use the `Context` API to obtain the `ExampleResource` context resource.

## Run this example

Run the `Application` class. The following output is expected:

```text
Message: Example resource
Message: Example resource
Message: Thread resource
```

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://holon-platform.com/docs/current/reference).

For the specific documentation about the components used in this example see:

* [Holon platform Context](https://holon-platform.com/docs/current/reference/holon-core.html#Context)
* [Holon platform core Spring Boot support](https://holon-platform.com/docs/current/reference/holon-core.html#SpringBoot)

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
