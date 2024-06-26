# quarkus-rest-client-example

This project is to leran how to define OpenAPI specification and generate REST client using Quarkus.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.


## Reference

- [eclipse/microprofile-open-api: Microprofile open api](https://github.com/eclipse/microprofile-open-api)
- [MicroProfile OpenAPI Specification](https://download.eclipse.org/microprofile/microprofile-open-api-3.0-RC4/microprofile-openapi-spec-3.0-RC4.html)

### Issues related to Query parameters

- [Quarkus RestEasy Reactive Not Exploding List Type Query Parameter · quarkusio/quarkus · Discussion \#27115](https://github.com/quarkusio/quarkus/discussions/27115)