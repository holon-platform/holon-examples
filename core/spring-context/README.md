# Holon platform examples: Spring context resources

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup a Holon Platform `Context` scope using **Spring** application context to provide context resource instances as Spring beans. This way, any valid Spring bean can be obtained as a context resource using the Holon Platform standard `Context` API, for example through the **static** `Context.get().resource(Class<T> resourceType)` method.

## Topics

This example addresses the following topics:

* Use the `@EnableBeanContext` annotation to setup the Spring beans Context scope
* Use the `Context` API to obtain context resources

## Example structure

The [ExampleResource](https://github.com/holon-platform/holon-examples/blob/master/core/spring-context/src/main/java/com/holonplatform/example/core/context/ExampleResource.java) interface represents the example class to be made available as a `Context` resource.

The [Main](https://github.com/holon-platform/holon-examples/blob/master/core/spring-context/src/main/java/com/holonplatform/example/core/context/Main.java) class contains the code to setup the Spring context and use the `Context` API to obtain the `ExampleResource` context resource.

## Run this example

Run the `Main` Java class. The following output is expected:

```text
Message: Example resource
Message: Example resource
Message: Thread resource
```

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://docs.holon-platform.com/current/reference).

For the specific documentation about the components used in this example see:

* [Holon platform Context](https://docs.holon-platform.com/current/reference/holon-core.html#Context)
* [Holon platform Spring context support](https://docs.holon-platform.com/current/reference/holon-core.html#SpringContextScope)

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
