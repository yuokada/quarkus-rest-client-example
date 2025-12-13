# quarkus-rest-client-example

This project is to leran how to define OpenAPI specification and generate REST client using Quarkus.

## Development Flow

1. **Start the server in dev mode**

   ```shell
   ./mvnw compile quarkus:dev -pl server
   ```

   Live Coding is enabled and the Dev UI is available at `http://localhost:8080/q/dev/`. When you need to refresh the OpenAPI document, launch the server on another port (for example, `-Dquarkus.http.port=8888`) and run `make download_openapi_yaml` to store the definition in `server/openapi-definition/openapi.yaml`, making it easy to compare with generated clients.

2. **Develop REST clients**

   After updating the server OpenAPI spec, run `./mvnw quarkus:generate-code -pl client` to regenerate the REST client stubs. Follow it with `./mvnw test -pl client` to execute unit and integration tests for the client and ensure that the generated code stays in sync.

3. **Shared tooling**

   Download the Java formatter via `make get_google_java_format`, then apply `make fix_import_only` or `make reformat_java_files` before submitting changes.

## Deploy and Run

1. **Package the server**

   ```shell
   ./mvnw package -pl server
   ```

   A runnable JAR (`quarkus-run.jar`) and its dependencies are produced in `server/target/quarkus-app/`. Add `-Dquarkus.package.type=uber-jar` if you need a single executable JAR.

2. **Build a fat (über) JAR**

   ```shell
   ./mvnw package -Dquarkus.package.type=uber-jar -pl server
   ```

   The resulting file `server/target/quarkus-rest-client-example-server-*-runner.jar` is self-contained and can be copied to any JVM 17 host without the accompanying `lib` directory.

3. **Run the packaged JAR**

   ```shell
   java -jar server/target/quarkus-app/quarkus-run.jar
   ```

   If you produced an über-jar, start it with `java -jar server/target/*-runner.jar`.

4. **Build container images**

   The `quarkus-container-image-jib` plugin lets you produce OCI images and push them to a registry. Provide `quarkus.container-image.*` properties in `application.properties` or via the command line.

   ```shell
   ./mvnw package -Dquarkus.container-image.build=true -pl server
   ./mvnw package -Dquarkus.container-image.build=true -Dquarkus.container-image.push=true -pl server
   ```

5. **Optional native binary**

   With GraalVM or Mandrel configured, run `./mvnw package -Dnative -pl server` to produce a native executable and start it directly from `server/target/*-runner`.


## Reference

- [eclipse/microprofile-open-api: Microprofile open api](https://github.com/eclipse/microprofile-open-api)
- [MicroProfile OpenAPI Specification](https://download.eclipse.org/microprofile/microprofile-open-api-3.0-RC4/microprofile-openapi-spec-3.0-RC4.html)

### Issues related to Query parameters

- [Quarkus RestEasy Reactive Not Exploding List Type Query Parameter · quarkusio/quarkus · Discussion \#27115](https://github.com/quarkusio/quarkus/discussions/27115)
