# Repository Guidelines

## Project Structure and Modules
- The root `pom.xml` orchestrates the `server` and `client` Maven modules. Add new modules by editing the `<modules>` section and keeping each module self-contained.
- `server/src/main/java` hosts the Quarkus REST endpoints, while `server/src/test/java` contains JUnit tests. Generated OpenAPI artifacts live under `server/openapi-definition/openapi.yaml`.
- `client/src/main/java` includes REST client implementations and DTOs generated from the spec, and `client/src/test/java` contains integration tests that assert client–server compatibility.
- Shared helper tools (for example, `google-java-format-all-deps.jar` and the Make targets) live at the repository root and can be invoked from any module.

## Build, Test, and Development Commands
- `./mvnw compile quarkus:dev -pl server`: launch the server module with hot reload and the Dev UI at `http://localhost:8080/q/dev/`.
- `./mvnw package -pl server`: build a runnable JAR in `server/target/quarkus-app/`; append `-Dquarkus.package.type=uber-jar` for a fat JAR.
- `./mvnw test -pl client`: execute unit and integration tests for the generated REST client.
- `make get_google_java_format`: download the formatter; use `make fix_import_only` or `make reformat_java_files` to align existing sources.

## Coding Style and Naming
- Target Java 17 with UTF-8 encoding, four-space indentation, and Google Java Format (`google-java-format-all-deps.jar`).
- Use the package prefix `io.github.yuokada.rest.<feature>`. Name HTTP resources `*Resource`, REST clients `*ServiceClient`, and DTOs `*Dto` to keep responsibilities evident.
- Store configuration such as endpoints or credentials in `application.properties` and inject them via `@ConfigProperty` or Quarkus config mappings.

## Testing Guidelines
- Rely on JUnit 5, RestAssured, and Quarkus Test Framework. Follow the suffix conventions: `*Test` for unit tests and `*IT` for integration/native tests.
- Run `./mvnw verify -pl server -DskipITs=false` for full verification (Surefire + Failsafe) and keep key API paths at or above 80% line coverage.
- Use `Instancio` and `DataFaker` to generate deterministic sample data. When the OpenAPI spec changes, regenerate the client and compare it with `server/openapi-definition/openapi.yaml` before merging.

## Commit and Pull Request Guidelines
- Keep commit subjects in concise imperative English, mirroring the existing history (e.g., `Bump quarkus to 3.27`, `Update openapi.yaml`). Prefix with `feat:` or `fix:` when it clarifies intent.
- Pull requests must include a short summary, the Maven/Make commands executed, linked issues or reproduction steps, and screenshots or diffs for Dev UI or OpenAPI updates when applicable.
- When the OpenAPI document changes, describe the regeneration process (`./mvnw quarkus:generate-code` for the client) and reference the updated YAML or generated sources directly in the PR description.

## Security and Configuration Tips
- When regenerating OpenAPI locally, run the server on a secondary port (e.g., `-Dquarkus.http.port=8888`) and execute `make download_openapi_yaml` so the checked-in spec matches the running instance.
- Keep secrets out of version control by using environment variables or Quarkus profiles, and never commit plaintext credentials or tokens.
