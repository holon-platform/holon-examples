# Holon platform examples: JPA Datastore

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to deal with the basic operations of the `Datastore` API, using a **JPA** Datastore implementation and a **H2** in-memory database. 

**Spring Boot** is used for automatic `DataSource`, ORM (*Hibernate* in this example) and `Datastore` configuration, activated by the `holon-starter-jpa-hibernate` starter. See the example [pom](https://github.com/holon-platform/holon-examples/blob/master/datastore/jpa-datastore/pom.xml).

NOTE: This is the **JPA** version of the [JDBC Datastore example](https://github.com/holon-platform/holon-examples/tree/master/datastore/jdbc-datastore).

## Topics

This example addresses the following topics:

* Automatic setup of a **JPA Datastore** using the Holon Platform Spring Boot integration
* Management of a simple data _entity_ using the `Datastore` API (create, update, delete, query)

## Example structure

The [schema.sql](https://github.com/holon-platform/holon-examples/blob/master/datastore/jpa-datastore/src/test/resources/schema.sql) script creates the `products` table at application startup time.

The [Product](https://github.com/holon-platform/holon-examples/blob/master/datastore/jpa-datastore/src/main/java/com/holonplatform/example/datastore/jpa/Product.java) class represents the property model for a simple _product_ entity.

The [ProductEntity](https://github.com/holon-platform/holon-examples/blob/master/datastore/jpa-datastore/src/main/java/com/holonplatform/example/datastore/jpa/ProductEntity.java) class is the JPA _entity_ which maps the `products` table.

Please note:

* The `WITHDRAWN` [Product](https://github.com/holon-platform/holon-examples/blob/master/datastore/jpa-datastore/src/main/java/com/holonplatform/example/datastore/jpa/Product.java) property is configured with a **property value converter** to automatically convert the `integer` type of the database table column to the `Boolean` type.

* The `TARGET` field of the [Product](https://github.com/holon-platform/holon-examples/blob/master/datastore/jpa-datastore/src/main/java/com/holonplatform/example/datastore/jpa/Product.java) class is the definition of a `DataTarget` which refers to the `ProductEntity` JPA entity to be used with `Datastore` operations.

The [TestDatastore](https://github.com/holon-platform/holon-examples/blob/master/datastore/jdbc-datastore/src/test/java/com/holonplatform/example/datastore/jdbc/test/TestDatastore.java) class is a JUnit test class to show how to use the `Datastore` API to create, update delete and query the _products_, relying on the `PropertyBox` type to handle the property values.

## Run this example

Run the unit test class using `mvn test` or your favorite IDE command.

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://docs.holon-platform.com/current/reference).

For the specific documentation about the components used in this example see:

* [Holon platform Property model](https://docs.holon-platform.com/current/reference/holon-core.html#Property)
* [Holon platform Datastore API](https://docs.holon-platform.com/current/reference/holon-core.html#Datastore)
* [The JPA Datastore](https://docs.holon-platform.com/current/reference/holon-datastore-jpa.html)

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
