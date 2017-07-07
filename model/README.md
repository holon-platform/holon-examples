# Holon platform examples: Shared data model

This artifact represents a __commom data model definition__ to be used by the [Holon Platform](https://holon-platform.com) example projects.

Each data model _entity_ is represented by a `final` class, which name is composed by the `M` prefix and the data model _entity_ name.

Each data model class provides at least:

* A set of `Property` which represent the data model _entity_ attributes;
* A default `PropertySet` which collects all the data model _entity_ properties.

See the [Holon Platform reference guide](https://holon-platform.com/docs/current/reference/holon-core.html#Property) for information about the Holon Platform `Property` architecture.

## Example projects

See [Holon platform examples](https://github.com/holon-platform/holon-examples).

## System requirements

The Holon Platform is built using __Java 8__, so you need a JRE/JDK version 8 or above to build and run this example projects.

## License

All the [Holon Platform](https://holon-platform.com) modules and examples are _Open Source_ software released under the [Apache 2.0 license](LICENSE.md).
