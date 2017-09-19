# Holon platform examples: JDBC Datastore

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to deal with the basic operations of the `Datastore` API, using a **JDBC** Datastore implementation and a **H2** in-memory database. 

**Spring Boot** is used for automatic `DataSource` and `Datastore` configuration, activated by the `holon-starter-jdbc-datastore-hikaricp` starter. See the example [pom](https://github.com/holon-platform/holon-examples/blob/master/datastore/jdbc-datastore/pom.xml).

NOTE: This is the **JDBC** version of the [JPA Datastore example](https://github.com/holon-platform/holon-examples/datastore/jpa-datastore).

## Topics

This example addresses the following topics:

* Automatic setup of a **JDBC Datastore** using the Holon Platform Spring Boot integration
* Management of a simple data _entity_ using the `Datastore` API (create, update, delete, query)

## Example structure

The [schema.sql](https://github.com/holon-platform/holon-examples/blob/master/datastore/jdbc-datastore/src/test/resources/schema.sql) script creates the `products` table at application startup time.

The [Product](https://github.com/holon-platform/holon-examples/blob/master/datastore/jdbc-datastore/src/main/java/com/holonplatform/example/datastore/jdbc/Product.java) class represents the property model for a simple _product_ entity.

Please note:

* The `WITHDRAWN` [Product](https://github.com/holon-platform/holon-examples/blob/master/datastore/jdbc-datastore/src/main/java/com/holonplatform/example/datastore/jdbc/Product.java) property is configured with a **property value converter** to automatically convert the `integer` type of the database table column to the `Boolean` type.

* The `TARGET` field of the [Product](https://github.com/holon-platform/holon-examples/blob/master/datastore/jdbc-datastore/src/main/java/com/holonplatform/example/datastore/jdbc/Product.java) class is the definition of a named `DataTarget` which refers to the `products` table name to be used with `Datastore` operations.

The [TestDatastore](https://github.com/holon-platform/holon-examples/blob/master/datastore/jdbc-datastore/src/test/java/com/holonplatform/example/datastore/jdbc/test/TestDatastore.java) class is a JUnit test class to show how to use the `Datastore` API to create, update delete and query the _products_, relying on the `PropertyBox` type to handle the property values.

## Run this example

Run the unit test class using `mvn test` or your favorite IDE command.

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://holon-platform.com/docs/current/reference).

For the specific documentation about the components used in this example see:

* [Holon platform Property model](https://holon-platform.com/docs/current/reference/holon-core.html#Property)
* [Holon platform Datastore API](https://holon-platform.com/docs/current/reference/holon-core.html#Datastore)
* [The JDBC Datastore](https://holon-platform.com/docs/current/reference/holon-datastore-jdbc.html)

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
