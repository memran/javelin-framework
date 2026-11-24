# Javelin Framework

Javelin is a lightweight, modular Java web framework inspired by MarwaPHP-style service providers. It stays explicit, small, and close to the JDK: no annotation scanning, no hidden global state, no ORM-style abstraction, and no Spring-style magic.

This repository is the first production-oriented MVP phase. It includes a deterministic boot flow, a small DI container, provider-based wiring, three pluggable HTTP drivers (Netty by default, JDK and Undertow alternatives), routing with named routes, middleware and bearer-token security, YAML + `.env` config, SLF4J logging, Pebble views, HikariCP-backed JDBC with YAML migrations, an in-memory and a Valkey-backed cache, a NATS JetStream queue with job retries, a Picocli CLI with project generators, static assets, and a runnable demo app.

## Why Javelin

Most Java web frameworks trade startup cost and transparency for convenience. They scan the classpath, discover annotations at runtime, and hide wiring behind proxies and reflection. That is a poor fit for small services, CLIs, embedded apps, and teams that want to see exactly what their program does.

Javelin was built to answer a simple question: *what is the smallest coherent framework that still gives you routing, dependency injection, configuration, views, database access, security, caching, and queuing?*

The answers that shaped the project:

- **Explicit over automatic.** You register providers and routes in code. There is nothing hidden to discover and no startup scan.
- **Fast, predictable startup.** The boot sequence is linear, with no lazy initialization of core infrastructure. Dependency graphs stay shallow.
- **JDK-first.** Standard library types are used wherever they are enough (virtual threads, `java.net.http`, records, `Duration`, `Path`). External libraries are behind interfaces.
- **Replaceable adapters.** HTTP server, database, logger, view renderer, and cache all sit behind core contracts. Swap a backend by registering a different service provider, without touching application code.
- **Cheap to learn and debug.** Small, single-purpose classes and direct names mean the flow is easy to follow with a normal debugger.
- **Production-oriented defaults.** Secure headers, request limits, trusted-proxy handling, bounded timeouts, and dev/prod profiles are on by default in starter wiring.

It is deliberately *not* Spring Boot and deliberately *not* an ORM. If you need those ecosystems, use them. If you want control, transparency, and a small runtime, Javelin is built for that.

## Features

- **Dependency injection** — type-keyed container with instance, transient, and singleton bindings plus deterministic constructor auto-resolution, factory bindings, and circular-dependency detection.
- **Service providers** — a two-phase `register()` / `boot()` lifecycle with explicit ordering; adapters are activated by listing providers in config, never by scanning.
- **Routing** — method + path routes with `{parameters}`, inline constraints, named routes with URL generation, automatic `HEAD` and `OPTIONS` responses, `405` + `Allow`, and duplicate route/name rejection.
- **Middleware** — a chain-of-responsibility pipeline for global and per-route middleware, including secure headers, request-size limits, rate limiting, bearer-token authentication, and optional request logging with request/correlation IDs and duration.
- **Logging and error handling** — SLF4J/Logback with structured context in the MDC, config-driven levels, a dependency-free JSON encoder with rotating file output, plus a global exception handler with dev-only stack traces, custom HTML error pages, and sensitive-data redaction.
- **Three HTTP drivers** — Netty (event loops, default), JDK `HttpServer` (virtual threads), and Undertow (worker dispatch), all behind one `HttpServerAdapter` contract.
- **Configuration** — YAML config files and `.env` loading with `${ENV_VAR}` resolution, `APP_ENV` dev/prod profiles, and explicit config keys with documented defaults.
- **Security defaults** — secure response headers, configurable trusted proxies (IP/CIDR), request size limits, password hashing, bearer auth with 401/403 responses, and dev/prod error handling.
- **Static assets** — ETag and `304` support, byte ranges, cache headers, `nosniff`, path-traversal and symlink containment, and a middleware-friendly pipeline.
- **Database** — bounded HikariCP pools, multiple named connections, a prepared-statement query builder with CRUD and pagination, transactions with rollback, slow-query logging, bundled PostgreSQL/MySQL/MariaDB/SQLite drivers, and dialect-aware YAML migrations with history and batch rollback.
- **Views** — Pebble templates with layouts, includes, blocks, reusable macros, shared globals, automatic escaping and configurable caching; a `View` facade for convenient rendering.
- **JSON and web responses** — a configurable Jackson mapper behind `Json`/`JsonResponse`, HTML/JSON/redirect/file/stream responses, secure static assets, cookies, opt-in sessions, and 422 validation-error rendering for both HTML and JSON clients.
- **Caching** — a small `Cache` contract with an in-memory TTL implementation and a Lettuce-backed Valkey adapter for distributed caching.
- **Queues** — a `Job` / `RetryPolicy` contract with a NATS JetStream adapter, push consumers, and retry with attempt tracking.
- **CLI and generators** — Picocli commands to scaffold apps, controllers, models, migrations, and validation rules. Generated projects include Docker Compose, a multi-stage Dockerfile, Tailwind CSS, Alpine.js, npm/bun scripts with auto-detection, build scripts, and opt-in Nginx/Caddy proxy configs.
- **Support helpers** — small, stateless facades for strings, arrays, dates, files, images, HTTP client, AI client, objects, validation, HTML, and security.

## Architecture at a glance

```
javelin-support        (JDK-only utilities, no dependencies)
        │
        ▼
javelin-core           (contracts, container, routing, kernel, security primitives)
        │
        ├── config / http adapters / db-jdbc / log-slf4j / view-pebble / security / cache
        │
        ├── cache-valkey / queue-nats     (distributed backends)
        │
        ▼
javelin-starter        (wires the default stack)
        ▲
        │
javelin-console        (CLI + generators, developer toolchain)
```

Core owns the contracts; adapters implement them; the starter wires defaults; application routes load through configured `ServiceProvider` classes. See [Architecture](docs/architecture.md) and [Application Flow](docs/application-flow.md) for the full dependency graph and request lifecycle.

## Modules

- `modules/javelin-core` - application, container, providers, router, request/response, facades, kernel contracts, queue/cache contracts
- `modules/javelin-config` - `.env` and YAML config loading
- `modules/javelin-http-common` - shared static asset, trusted proxy, and adapter/timeout config helpers
- `modules/javelin-http-jdk` - JDK `HttpServer` adapter using virtual threads
- `modules/javelin-http-netty` - Netty adapter using event loops
- `modules/javelin-http-undertow` - Undertow adapter using worker dispatch
- `modules/javelin-db-jdbc` - HikariCP-backed JDBC database, query builder, named connections, query timing
- `modules/javelin-db-migration` - YAML migrations, history table, batch rollback, dialect-aware DDL
- `modules/javelin-log-slf4j` - SLF4J/Logback adapter, structured logging, request IDs, JSON encoder, config-driven levels
- `modules/javelin-exception` - global exception handler, custom error pages, sensitive-data redaction
- `modules/javelin-view-pebble` - Pebble template renderer, layouts, components, escaping, caching
- `modules/javelin-json-jackson` - configurable Jackson mapper for JSON responses and parsing
- `modules/javelin-console` - Picocli command kernel and generators
- `modules/javelin-security` - secure headers, request limits, rate limiting, password hashing
- `modules/javelin-support` - short Laravel-style helper facades for strings, dates, arrays, files, images, HTTP client, AI client, objects, validation, HTML, and security
- `modules/javelin-cache` - simple in-memory cache contract and implementation
- `modules/javelin-cache-valkey` - Lettuce-backed Valkey cache adapter
- `modules/javelin-queue-nats` - NATS JetStream queue adapter with job retries
- `modules/javelin-starter` - batteries-included bootstrap
- `demo-app` - runnable demo application and Docker stack

## Documentation

- [Quick Start](docs/quick-start.md) — create and run your first app
- [Installation](docs/installation.md) — prerequisites, CLI install, local artifacts, troubleshooting
- [Architecture](docs/architecture.md) — design principles and module dependency graph
- [Exception handling](docs/modules/exception.md) — global handler, error pages and redaction
- [Application Flow](docs/application-flow.md) — boot sequence and request lifecycle
- [Deployment](docs/deployment.md) — production tuning, Docker Compose, proxies, health checks, benchmarks
- [CLI Reference](docs/cli-reference.md) — every `javelin` command
- Module-by-module API and usage examples live under [`docs/`](docs/README.md).

## Requirements

- JDK 25+ (compiled for Java 25; newer JDKs are supported)
- Maven 3.9+

## Performance

Local, client-inclusive benchmarks measured on the development machine: Windows 11, 12 logical CPUs, Temurin JDK 25 with default JVM flags, demo app started with `serve`, application logging suppressed. Client and server ran on the same host using `hey` v0.1.5 with 50 keep-alive connections. Each 10-second run returned only `200` responses.

| Driver | `GET /health` (JSON, 15 bytes) | `GET /` (HTML, ~4.9 KB) |
|---|---|---|
| `jdk` | ~20,600 req/s | ~8,900 req/s |
| `netty` | ~21,400 req/s | ~11,900 req/s |
| `undertow` | ~25,000 req/s | ~9,300 req/s |

Undertow was fastest for the tiny JSON response; Netty was fastest for the larger HTML response. Differences below roughly 15–20% should be treated as noise on this machine, and logging level matters (DEBUG template lookups measurably reduce throughput). These are not a server ceiling — hardware, OS networking, JVM flags, middleware, and the client tool all change the result. Earlier draft figures of ~28,000–30,000 req/s were estimates and were removed.

To reproduce:

```bash
# Terminal 1: set javelin.starter.http-driver in config/app.yaml (netty | jdk | undertow)
java -jar demo-app/target/javelin-demo-app-0.1.0-SNAPSHOT.jar serve

# Terminal 2:
hey -z 10s -c 50 http://127.0.0.1:8080/health
hey -z 10s -c 50 http://127.0.0.1:8080/
```

See [Performance Benchmarks](docs/deployment.md#performance-benchmarks) for the full methodology.

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
6. Load configured service providers (in registration order: `register()` first, then `boot()`)
7. Start the configured HTTP driver (Netty by default; JDK and Undertow are pluggable)
8. Serve requests

## Example Routes

```java
app.router()
    .get("/", home::index)
    .get("/health", home::health)
    .get("/users/{id}", users::show);
```

Routes can be named and later turned into paths:

```java
app.router().add(Route.get("/users/{id}", users::show).name("users.show"));

String path = app.router().path("users.show", Map.of("id", "42")); // /users/42
```

## JSON And Views

```java
return Json.ok(Map.of("status", "ok"));
return View.render("users/index", Map.of("name", "Javelin"));
return Response.redirect("/dashboard");
```

The same controller can serve both HTML and JSON clients:

```java
if (!errors.isEmpty()) {
    return ValidationErrors.of(request, errors); // 422 JSON or HTML
}

Session session = request.session().orElseThrow();
session.put("lastViewed", request.path());
return Response.text("ok").withCookie(Cookie.session("pref", "dark"));
```

Pebble settings live in `config/view.yml`. Applications can register custom Pebble filters, functions, tests, globals, or full extensions from a service provider:

```java
app.make(PebbleViewExtensions.class)
    .extension(new JavelinViewExtension())
    .global("javelinRuntime", "JDK 25+");
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

DatabaseManager manager = app.make(DatabaseManager.class);
Database reporting = manager.database("reporting");
```

The query builder uses prepared statements for values. Keep table and column names framework-controlled or validated before passing dynamic identifiers.
YAML migration files live under `database/migrations`, and `javelin migrate` / `javelin migrate:rollback` execute them through the JDBC migration runner.

## Cache And Queues

Cache and queue adapters are opt-in: list their service providers in config, then resolve the contract from the container.

```java
Cache cache = app.make(Cache.class);
cache.put("greeting", "hello", Duration.ofMinutes(5));

Queue queue = app.make(Queue.class);
queue.subscribe("emails.welcome", job -> sendWelcome(job.payload(Map.class)), RetryPolicy.DEFAULT);
queue.publish(Job.of("emails.welcome", Map.of("userId", 42)));
```

- `javelin-cache` provides an in-memory TTL cache; `javelin-cache-valkey` uses Lettuce against Valkey/Redis.
- `javelin-queue-nats` publishes to NATS JetStream and consumes with retries and attempt tracking.

Both are wired only when configured, and generated Compose files start Valkey and NATS/JetStream alongside the app and PostgreSQL.

## Security

Security defaults are on in starter wiring. Configure them under `security` in `config/app.yaml`:

- `secure_headers` — `X-Content-Type-Options`, `X-Frame-Options`, referrer and permissions policies
- `max_request_bytes` — request size limit (413 when exceeded)
- `trusted_proxies` — IP/CIDR allowlist for `X-Forwarded-*` resolution; Docker bridge ranges are trusted in generated configs
- Bearer authentication and permission middleware live in core (`BearerAuthMiddleware`, `RequiresPermissionMiddleware`)

In production, keep detailed error pages off via `APP_ENV=prod`, terminate TLS at a trusted proxy, and restrict `trusted_proxies` to your real proxy network.

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

`javelin make:migration` generates YAML files under `database/migrations`, and `javelin migrate` / `javelin migrate:rollback` execute those files through the JDBC migration runner.
Use `--type create-table`, `--type add-column`, or `--type seed` to scaffold the common migration shapes quickly.

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

Build a lean demo jar for one HTTP driver. Each profile includes only that adapter and writes the driver into `config/app.yaml` under `javelin.starter.http-driver`:

```bash
mvn -pl demo-app -am package -DskipTests -Pdriver-netty      # default
mvn -pl demo-app -am package -DskipTests -Pdriver-jdk
mvn -pl demo-app -am package -DskipTests -Pdriver-undertow
```

The driver is written into the packaged `config/app.yaml` under `javelin.starter.http-driver`. When you run a profile-built jar from inside the source tree, Javelin finds the *source* `config/app.yaml` first (which contains the build placeholder); pass `-Djavelin.root=<dir-with-packaged-config>` or run the jar from outside the repository so the packaged config is used.

## Roadmap

The MVP is complete. Natural next additions include:

- Multipart upload parsing and a larger static-assets story
- Integration tests for the JDK HTTP adapter
- A scheduler and recurring-job support
- WebSocket support
- OAuth / external identity providers
- Metrics and observability hooks

Have an idea? Open an issue or a pull request — contributions that keep the explicit, provider-based, JDK-first design are welcome.
