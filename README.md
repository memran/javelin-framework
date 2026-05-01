# Javelin Framework

Javelin is a lightweight Java framework inspired by MarwaPHP-style service providers while staying explicit, modular, and close to the JDK.

This repository is the first production-oriented MVP phase. It includes the boot flow, service container, provider loading, JDK HTTP runtime with virtual threads, routing, middleware, YAML and `.env` config, SLF4J logging, Pebble views, JDBC/Hikari database access, Picocli commands, security middleware, in-memory cache, a demo app, and unit tests.

## Modules

- `modules/javelin-core` - application, container, providers, router, request/response, facades, kernel contracts
- `modules/javelin-config` - `.env` and YAML config loading
- `modules/javelin-http-jdk` - JDK `HttpServer` adapter using virtual threads
- `modules/javelin-db-jdbc` - HikariCP-backed JDBC database and query builder
- `modules/javelin-log-slf4j` - SLF4J/Logback logger adapter
- `modules/javelin-view-pebble` - Pebble template renderer
- `modules/javelin-console` - Picocli command kernel and generators
- `modules/javelin-security` - secure headers, request limits, rate limiting, password hashing
- `modules/javelin-support` - short Laravel-style helper facades for strings, dates, arrays, files, objects, validation, HTML, and security
- `modules/javelin-cache` - simple in-memory cache contract and implementation
- `modules/javelin-starter` - batteries-included bootstrap
- `demo-app` - runnable demo application

## Documentation

Module-by-module API and usage examples live under [`docs/`](docs/README.md).

## Maven Split

Javelin is designed as two cooperating pieces:

- `javelin-core` and its adapter modules are the reusable framework library.
- `javelin-starter` is the default runtime composition for application code.
- `javelin-console` is the developer workspace toolchain that provides the `javelin` command.

For a library or application that wants to consume Javelin from Maven, depend on the framework artifacts directly:

```xml
<dependency>
    <groupId>io.javelin</groupId>
    <artifactId>javelin-starter</artifactId>
    <version>${javelin.version}</version>
</dependency>
```

Then keep your actual workspace as a normal Maven application project. That workspace can add Javelin plus any other libraries it needs, and the `javelin` command can be used for scaffolding, serving, code generation, migrations, diagnostics, and AI-assisted workflows.

The generated workspace template already follows that shape: the project POM depends on `javelin-starter`, while the workspace remains free to add its own application dependencies.
It also includes a workspace-local `README.md`, `install.ps1`, and `install.sh` that explain the app setup, verify Java and Maven, then run `mvn test` from inside the generated app.
The starter validation example is customizable too: `javelin new crm-app --validation-rule TeenAge` will generate `app/validation/TeenAgeRule.java`.

## Requirements

- Java 25
- Maven 3.9+

## Install

From the repository root, install the local `javelin` command with one line.

Windows PowerShell:

```powershell
powershell -ExecutionPolicy Bypass -File .\install.ps1
```

Linux/macOS:

```bash
sh ./install.sh
```

The installer builds `modules/javelin-console`, creates a launcher in `~/.javelin/bin`, and adds that folder to your user PATH. Open a new terminal after installation.

Verify:

```bash
javelin --help
```

After changing CLI source, rebuild the CLI jar:

```bash
mvn -pl :javelin-console -am package -DskipTests
```

When you generate a new workspace with `javelin new <name>`, use the workspace-local installer inside that app directory:

```bash
cd <name>
./install.sh
```

On Windows:

```powershell
cd <name>
.\install.ps1
```

## Boot Flow

1. Load `.env`
2. Load `config/app.yaml`
3. Create `Application`
4. Create `Container`
5. Register core bindings
6. Load configured service providers
7. Run `provider.register(app)`
8. Run `provider.boot(app)`
9. Start JDK `HttpServer` with virtual threads
10. Serve requests

## Example Routes

```java
app.router()
    .get("/", home::index)
    .get("/health", home::health)
    .get("/users/{id}", users::show);
```

## JSON And Views

```java
return Json.ok(Map.of("status", "ok"));
return View.render("users/index", Map.of("name", "Javelin"));
```

Pebble settings live in `config/view.yml`. Applications can register custom Pebble filters, functions, tests, globals, or full extensions from a service provider:

```java
app.make(PebbleViewExtensions.class)
    .extension(new JavelinViewExtension())
    .global("javelinRuntime", "JDK 25");
```

See `demo-app/app/views/JavelinViewExtension.java` for a custom filter and function example.

## Support Utilities

The `javelin-support` module provides small, stateless helper facades for common library and application tasks:

```java
import java.nio.file.Path;
import java.util.List;

import io.javelin.support.Arr;
import io.javelin.support.Date;
import io.javelin.support.File;
import io.javelin.support.Html;
import io.javelin.support.Obj;
import io.javelin.support.Security;
import io.javelin.support.Str;
import io.javelin.support.Validator;

String slug = Str.toSlug("Javelin Framework");
List<String> tags = Arr.compact(new String[] {"core", null, "support"});
Path path = File.safeResolve(Path.of("storage"), "logs", "app.log");
String today = Date.format(Date.today());
String escaped = Html.escape("<strong>safe</strong>");
String fallback = Obj.coalesce(null, "default");
String name = Validator.requireNonBlank("User", "name is required");
String token = Security.randomToken(16);
```

These helpers are intentionally strict and predictable:

- no hidden global state
- no annotation scanning
- no framework magic
- JDK-only behavior where practical

Use them when you want concise utility methods without pulling in a heavy framework abstraction.

## Database

```java
Database db = app.make(Database.class);
db.table("users").where("id", 1).first();
```

The query builder uses prepared statements for values. Keep table and column names framework-controlled or validated before passing dynamic identifiers.

## CLI

The repository includes local launcher scripts for the global-style `javelin` command:

- Windows: `javelin.cmd`
- Linux/macOS: `./javelin`

Build the CLI launcher after cloning or after changing CLI source:

```bash
mvn -pl :javelin-console -am package -DskipTests
```

If you installed with `install.ps1` or `install.sh`, use `javelin` directly instead of the repo-local launcher.

### First Run

Build the demo application once:

```bash
javelin --project demo-app build
```

Without installing, use the repo-local launcher:

```powershell
.\javelin.cmd --project demo-app build
```

Start the HTTP server:

```bash
javelin --project demo-app serve
```

Without installing:

```powershell
.\javelin.cmd --project demo-app serve
```

Then open:

```text
http://127.0.0.1:8080
```

From the repository root, app runtime commands default to `demo-app`, so this also works after the app has been built:

```bash
javelin serve
```

Without installing:

```powershell
.\javelin.cmd serve
```

### Project Selection

Use `--project <path>` to target an application folder:

```bash
javelin --project demo-app routes:list
javelin --project demo-app make:controller UserController
javelin --project demo-app make:model User
javelin --project demo-app migrate
```

`javelin build` is explicit. Other project commands, including `serve`, do not rebuild automatically; they run the existing application jar from `target/`.

If the jar does not exist yet, run:

```bash
javelin --project demo-app build
```

## Build And Test

Run all tests:

```bash
mvn test
```

Build the full reactor:

```bash
mvn package -DskipTests
```

Build only the demo app and required modules:

```bash
mvn -pl demo-app -am package -DskipTests
```

## Roadmap

Next phases should add migrations, validation, file uploads, trusted proxy parsing, static assets, CSRF/session support, Redis, queues, scheduler, WebSocket, OAuth, metrics, and deploy tooling.
