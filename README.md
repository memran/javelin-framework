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
- `modules/javelin-cache` - simple in-memory cache contract and implementation
- `modules/javelin-starter` - batteries-included bootstrap
- `demo-app` - runnable demo application

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
