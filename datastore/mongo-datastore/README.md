# Holon platform examples: MongoDB Datastore

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to deal with the basic operations of the `Datastore` API, using a **Mongo** Datastore implementation. 

**Spring Boot** is used for `Datastore` configuration, enabled by using the `holon-starter-mongo-datastore` starter. See the example [pom](https://github.com/holon-platform/holon-examples/blob/master/datastore/mongo-datastore/pom.xml).

## Topics

This example addresses the following topics:

* Automatic setup of a **MongoDB Datastore** using the Holon Platform Spring Boot integration
* Management of a simple data _entity_ using the `Datastore` API (create, update, delete, query)

## Example structure

The [Product](https://github.com/holon-platform/holon-examples/blob/master/datastore/mongo-datastore/src/main/java/com/holonplatform/example/datastore/mongo/Product.java) class represents the property model for a simple _product_ document schema.

Please note:

* The `TARGET` field of the [Product](https://github.com/holon-platform/holon-examples/blob/master/datastore/mongo-datastore/src/main/java/com/holonplatform/example/datastore/mongo/Product.java) class is the definition of a named `DataTarget` which refers to the `products` collection name to be used with `Datastore` operations.

* The [TestDatastore](https://github.com/holon-platform/holon-examples/blob/master/datastore/mongo-datastore/src/test/java/com/holonplatform/example/datastore/mongo/test/TestDatastore.java) class is a JUnit test class to show how to use the `Datastore` API to create, update delete and query the _products_, relying on the `PropertyBox` type to handle the property values.

## Run this example

Run the unit test class using `mvn test` or your favorite IDE command.

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://docs.holon-platform.com/current/reference).

For the specific documentation about the components used in this example see:

* [Holon platform Property model](https://docs.holon-platform.com/current/reference/holon-core.html#Property)
* [Holon platform Datastore API](https://docs.holon-platform.com/current/reference/holon-core.html#Datastore)
* [The MongoDB Datastore](https://docs.holon-platform.com/current/reference/holon-datastore-mongo.html)

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
