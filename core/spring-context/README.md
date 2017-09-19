# Holon platform examples: Spring context resources

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup a Holon Platform `Context` scope using **Spring** application context to provide context resource instances as Spring beans. This way, any valid Spring bean can be obtained as a context resource using the Holon Platform standard `Context` API, for example through the **static** `Context.get().resource(Class<T> resourceType)` method.

## Topics

This example addresses the following topics:

* Use the `@EnableBeanContext` annotation to setup the Spring beans Context scope
* Use the `Context` API to obtain context resources

## Example structure

The [MProduct](https://github.com/holon-platform/holon-examples/blob/master/core/property-model/src/main/java/com/holonplatform/example/core/property/model/MProduct.java) class represents the property model for a simple _product_ entity.

The [TestPropertyModel](https://github.com/holon-platform/holon-examples/blob/master/core/property-model/src/test/java/com/holonplatform/example/core/property/test/TestPropertyModel.java) class is a JUnit test class to show how to use the `Property` and `VirtualProperty` interfaces, the `PropertySet` and `PropertyBox` APIs and how to obtain a property model introspecting a Java bean class.

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://holon-platform.com/docs/current/reference).

For the specific documentation about the components used in this example see:

* [Holon platform Property model](https://holon-platform.com/docs/current/reference/holon-core.html#Property)
* [Holon platform data validation](https://holon-platform.com/docs/current/reference/holon-core.html#Validators)
* [Holon platform Context](https://holon-platform.com/docs/current/reference/holon-core.html#Context)
* [Holon platform beans property model](https://holon-platform.com/docs/current/reference/holon-core.html#Beans)

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
