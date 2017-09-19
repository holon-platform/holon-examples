# Holon platform examples: Property model

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to use the `Property` model to define and configure a simple data entity, which can be represented through a `PropertySet` and managed using a `PropertyBox` to collect, inspect and trasport the property values. 
All the APIs used in this example are provided by the [Holon platform Core module](https://github.com/holon-platform/holon-core).

## Topics

This example addresses the following topics:

* Use the `Property` interface to define a data model
* Use a `PropertySet` to represent a model entity definition
* Work with `VirtualProperty`s
* Deal with `Property` configuration and property value *presentation*
* Use a `PropertyBox` to collect, inspect and transport the property values
* Obtain a `Property` model from a Java bean class

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
