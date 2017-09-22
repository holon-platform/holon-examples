# Holon platform examples: Vaadin UI

_This is one of the [Holon Platform](https://holon-platform.com) example projects._

This example shows how to setup a simple web application using [Vaadin](https://vaadin.com) __8__ as UI engine and the Holon Platform Vaadin module, wich provides Vaadin UI builders and integration with the Holon Platform foundation architecture, such as the `Property` model and the `Datastore` API.

The Holon Platform `ViewNavigator` will be used to configure and manage the application's _views_. 

## Topics

This example addresses the following topics:

* Setup a web application using the Holon Platform __Vaadin__ integration and __Spring Boot__ auto-configuration support 
* Use the `ViewNavigator` to configure the application _views_ and hanlde navigation operations
* Use the Holon Platform `Property` model and `Datastore` API with the platform Vaadin integration components to display and manage a data entity.

## Example structure

The [Product](src/main/java/com/holonplatform/example/ui/vaadin/app/model/Product.java) class represents the property model for a simple _product_ data entity.

A __JDBC__ `Datastore` backed by a __H2 database__ is used for product data persistence, using a table named `products` created using the `schema.sql` file and populated using the `data.sql` file.

This application is composed by three _views_:

* [Home](src/main/java/com/holonplatform/example/ui/vaadin/app/views/Home.java): the home view, listing all the available products and allowing to display the product data by clicking on a row
* [View](src/main/java/com/holonplatform/example/ui/vaadin/app/views/View.java): displays a product in an application window, providing the "Edit" and "Delete" operations
* [Manage](src/main/java/com/holonplatform/example/ui/vaadin/app/views/Manage.java): manages the product data, providing an input form to create or update a product

## Run this example

This example is configured using __Spring Boot__, so you just have to run the `Application` class, open a web browser and navigate to:

`http://localhost:8080`

## Documentation

The complete _Holon Platform reference guide_ is available [here](https://holon-platform.com/docs/current/reference).

For the specific documentation about the modules and the components used in this example see:

* Documentation about the [Datastore](https://holon-platform.com/docs/current/reference/holon-core.html#Datastore)  API
* Documentation about the [Vaadin UI module](https://holon-platform.com/docs/current/reference/holon-vaadin.html)

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).

## Holon Platform Examples

See [Holon Platform Examples](https://github.com/holon-platform/holon-examples) for the examples directory.
