# Holon platform examples: Vaadin Flow UI

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup a simple web application using [Vaadin Flow](https://vaadin.com/flow) as UI engine and the Holon Platform Vaadin Flow module, wich provides integration with the Holon Platform foundation architecture, such as the `Property` model and the `Datastore` API.

## Topics

This example addresses the following topics:

* Setup a web application using the Holon Platform __Vaadin Flow__ integration and __Spring Boot__ auto-configuration support.
@ Use the `@QueryParameter` annotation to inject query parameter values in routing targets.
* Use the `Navigator` API to handle application _routing_.
* Use the Holon Platform `Property` model and `Datastore` API with the Vaadin components to display and manage a data entity.

## Example structure

The [Product](src/main/java/com/holonplatform/example/ui/vaadin/app/model/Product.java) class represents the property model for a simple _product_ data entity.

A __JDBC__ `Datastore` backed by a __H2 database__ is used for product data persistence, using a table named `products` created using the `schema.sql` file and populated using the `data.sql` file.

This application is composed by three _routes_:

* [Home](src/main/java/com/holonplatform/example/ui/vaadin/app/routes/Home.java): the home view, listing all the available products and allowing to edit the product data by clicking on a row
* [Manage](src/main/java/com/holonplatform/example/ui/vaadin/app/routes/Manage.java): manages the product data, providing an input form to create or update a product

## Run this example

This example is configured using __Spring Boot__, so you just have to run the `Application` class, open a web browser and navigate to:

`http://localhost:8080`

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://docs.holon-platform.com/current/reference).

For the specific documentation about the modules and the components used in this example see:

* Documentation about the [Datastore](https://docs.holon-platform.com/current/reference/holon-core.html#Datastore)  API
* Documentation about the [Vaadin Flow module](https://docs.holon-platform.com/current/reference/holon-vaadin-flow.html)

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
