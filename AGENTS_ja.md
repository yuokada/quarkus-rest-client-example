# Repository Guidelines

## プロジェクト構成とモジュール
- ルート `pom.xml` は `server` と `client` のマルチモジュールを束ね、追加モジュールは `pom.xml` の `<modules>` に登録します。
- `server/src/main/java` に Quarkus REST サーバー、`server/src/test/java` に JUnit テスト、`server/openapi-definition/openapi.yaml` に生成済み OpenAPI 仕様が置かれます。
- `client/src/main/java` には REST クライアント実装と DTO、`client/src/test/java` にはクライアント用の統合テストがあります。
- 共通の整形用 JAR や補助スクリプトはリポジトリ直下に配置され、Makefile ターゲットで利用します。

## ビルド・テスト・開発コマンド
- `./mvnw compile quarkus:dev -pl server` : サーバーモジュールをホットリロード付きで起動し、Dev UI を `http://localhost:8080/q/dev/` に公開します。
- `./mvnw package -pl server` : `target/quarkus-app/` にランナブル JAR を生成し、`-Dquarkus.package.type=uber-jar` で単一 JAR も選択できます。
- `./mvnw test -pl client` : クライアントのユニット／統合テストを実行し、自動生成クライアントの整合性を確認します。
- `make get_google_java_format` で整形ツールを取得し、`make fix_import_only` や `make reformat_java_files` で既存コードを揃えます。

## コーディングスタイルと命名
- Java 17 / UTF-8 を前提とし、4 スペースインデントと Google Java Format (`google-java-format-all-deps.jar`) に従います。
- パッケージ名は `io.github.yuokada.rest.<機能>` を用い、HTTP リソースは `*Resource`、REST クライアントは `*ServiceClient`、データ転送は `*Dto` で統一します。
- 設定値やエンドポイントパスは `application.properties` に集約し、定数は `@ConfigProperty` で注入します。

## テストガイドライン
- 標準の JUnit 5 / RestAssured / Quarkus Test Framework を使用し、クラス名はユニット用 `*Test`、ネイティブ・統合用 `*IT` を守ります。
- `./mvnw verify -pl server -DskipITs=false` で Failsafe を含む一連の検証を行い、主要 API については 80% 以上のカバレッジを維持してください。
- テストデータ生成には `Instancio` や `DataFaker` 依存を活用し、OpenAPI 変更時は `server/openapi-definition/openapi.yaml` との一致を必ず確認します。

## コミットと Pull Request
- `git log` が示す通りメッセージは英語の命令形で要点のみ（例: `Bump quarkus to 3.27`, `Update openapi.yaml`）。機能追加時は `feat:`, 修正時は `fix:` を頭に付けると履歴が読みやすくなります。
- PR では概要、テスト結果（実行した Maven / Make コマンド）、関連 Issue や再現手順、必要であれば Dev UI / REST クライアントのスクリーンショットや OpenAPI diff を添付してください。
- OpenAPI を更新した場合は再生成手順（`./mvnw quarkus:generate-code` 実行の有無）を説明し、変更した YAML や生成クラスにコメントでリンクするのが望ましいです。

## セキュリティと設定のヒント
- ローカルで OpenAPI を再生成する際は `server` を別ポート（例: `-Dquarkus.http.port=8888`）で起動し、`make download_openapi_yaml` で YAML を更新すると過去バージョンとの比較が容易です。
- 機密値は環境変数や `application.properties` のプロファイル切り替えで管理し、平文コミットを避けてください。
