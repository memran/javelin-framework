# Architecture

Javelin is a modular Java web framework designed around explicit composition, shallow dependency graphs, and cheap startup. There is no annotation scanning, no hidden global state, and no ORM-style abstraction. Everything is wired through service providers and constructor injection.

## Guiding Principles

- **Explicit over automatic** — no annotation scanning, no classpath magic. Providers are registered explicitly.
- **Small, composable modules** — each module has one job. Core owns contracts; adapters implement them; starter wires defaults.
- **JDK-first** — use standard library types and patterns before bringing in external dependencies.
- **Deterministic startup** — the boot sequence is linear and predictable. No lazy initialization of core infrastructure.
- **Replaceable adapters** — every adapter (HTTP server, database, logger, view renderer, cache) is behind a core interface. Swap implementations by changing the service provider.

## Module Dependency Graph

```
                    ┌──────────────────┐
                    │  javelin-support  │  (no deps — JDK-only utilities)
                    └────────┬─────────┘
                             │
                    ┌────────▼─────────┐
                    │   javelin-core    │  (contracts, container, routing, kernel)
                    └────────┬─────────┘
                             │
         ┌───────────────────┼───────────────────┬──────────────────┐
         │                   │                   │                  │
         ▼                   ▼                   ▼                  ▼
 ┌──────────────┐   ┌──────────────┐   ┌────────────────┐  ┌──────────────┐
 │ javelin-config│   │ javelin-http │   │  javelin-db    │  │ javelin-log  │
 │  (YAML+.env)  │   │   -jdk      │   │  -jdbc         │  │  -slf4j      │
 └──────────────┘   │  (JDK Http)  │   │ (HikariCP+JDBC)│  │(SLF4J bridge)│
                    └──────────────┘   └────────────────┘  └──────────────┘
         │                   │                   │                  │
         ▼                   ▼                   ▼                  ▼
 ┌──────────────┐   ┌──────────────┐   ┌────────────────┐  ┌──────────────┐
 │javelin-view  │   │javelin-sec   │   │ javelin-cache  │  │ javelin-     │
 │  -pebble     │   │  -urity      │   │  (InMemory)    │  │  console     │
 │(Pebble templ)│   │(hash+middle) │   └────────────────┘  │(CLI+scaffold)│
 └──────────────┘   └──────────────┘                       └──────┬───────┘
                                                                   │
                                                    ┌──────────────┘
                                                    │
                                             ┌──────▼───────┐
                                             │javelin-starter│  (wires everything)
                                             └──────────────┘
```

### Layer 0 — Foundation

| Module | Purpose | Dependencies |
|---|---|---|
| `javelin-support` | Stateless JDK-only helpers: `Collection`, `Str`, `File`, `Validation`, `Http` client, `Ai` client, `Image`, etc. | None |

### Layer 1 — Contracts

| Module | Purpose | Dependencies |
|---|---|---|
| `javelin-core` | `Application`, `Container`, `Router`, `Request`/`Response`, `Model`, `ServiceProvider`, `Middleware`, `HttpKernel`, facades (`Log`, `View`, `Json`) | `javelin-support` |

### Layer 2 — Adapters

Each adapter module depends only on `javelin-core` and implements one of its interfaces:

| Module | Implements | Provides |
|---|---|---|
| `javelin-config` | `Config`, `Env` | YAML config loader, `.env` file loader, `${ENV_VAR}` resolution |
| `javelin-http-jdk` | `HttpServerAdapter` | JDK `HttpServer` with virtual threads, static file serving, trusted proxy resolution |
| `javelin-db-jdbc` | `Database`, `QueryBuilder` | HikariCP connection pool, YAML migration runner |
| `javelin-log-slf4j` | `Logger` | SLF4J/Logback bridge |
| `javelin-view-pebble` | `ViewRenderer` | Pebble template engine with extensions, filters, globals |
| `javelin-security` | `Middleware` | Password hashing, secure headers, request size limiting, rate limiting |
| `javelin-cache` | `Cache` | In-memory TTL-based cache |

### Layer 3 — Toolchain

| Module | Purpose | Dependencies |
|---|---|---|
| `javelin-console` | CLI commands (`javelin new`, `serve`, `make:*`, `migrate`, etc.), picocli-based command dispatch, file generation | `javelin-core`, `javelin-db-jdbc`, `javelin-support` |

### Layer 4 — Aggregator

| Module | Purpose | Dependencies |
|---|---|---|
| `javelin-starter` | Convenience dependency — wires the full default stack, provides `Javelin.run()` entry point, auto-loads validation rules | All modules above |

## Module Boundaries

### Core owns contracts; adapters implement them

```
javelin-core (contracts)         javelin-http-jdk (adapter)
┌─────────────────────┐          ┌─────────────────────┐
│ HttpServerAdapter   │◄────────│ JdkHttpServer       │
│   (interface)       │          │   (implements)      │
└─────────────────────┘          └─────────────────────┘

┌─────────────────────┐          ┌─────────────────────┐
│ Database (interface) │◄────────│ JdbcDatabase         │
└─────────────────────┘          └─────────────────────┘

┌─────────────────────┐          ┌─────────────────────┐
│ Logger (interface)  │◄────────│ Slf4jLogger          │
└─────────────────────┘          └─────────────────────┘

┌─────────────────────┐          ┌─────────────────────┐
│ ViewRenderer        │◄────────│ PebbleViewRenderer   │
└─────────────────────┘          └─────────────────────┘
```

### Starter wires defaults; apps override through providers

```
javelin-starter
  └─ Javelin.create(root)
       ├─ DotenvEnv.load()           → Env
       ├─ YamlConfig.loadDirectory() → Config
       ├─ new Application(config, env)
       ├─ register Slf4jLogServiceProvider
       ├─ register JdkHttpServiceProvider
       ├─ register PebbleViewServiceProvider
       ├─ register JdbcDatabaseServiceProvider
       ├─ register SecurityServiceProvider
       ├─ register CacheServiceProvider
       ├─ register ConsoleServiceProvider
       ├─ register JavelinStarterServiceProvider
       ├─ ProviderLoader.load()      → config["providers"]
       └─ return app
```

To replace an adapter, register a different provider before `boot()`:

```java
Application app = Javelin.create(root);
app.register(new MyCustomCacheServiceProvider()); // replaces InMemoryCache
app.boot();
```

## Core Abstractions

### Container (DI)

The container is a simple type-keyed registry with three binding strategies:

```
instance(T, object)    → pre-built object, always returned as-is
bind(A, B)             → new B() each time make(A) is called
singleton(T, supplier) → supplier called once, result cached
make(T)                → auto-constructs concrete classes by resolving constructor parameters
```

Auto-resolution finds the constructor with the most parameters and recursively resolves each one from the container. Interfaces must be explicitly bound.

### Service Provider lifecycle

```
register(app)     → called first, in registration order. Bind things only.
boot(app)         → called after all providers are registered. Safe to resolve services.
```

### Middleware pipeline

```
Request → [global middleware] → [route middleware] → RouteHandler → Response
                                  ↑_____________________|
                              each middleware can short-circuit
```

Static fallback is consulted before routing and runs global middleware only. A matching file therefore shadows a route at the same path and bypasses route-level middleware (for example authentication), so avoid overlapping static and route paths.

Middleware is a `@FunctionalInterface` following chain-of-responsibility:

```java
Response handle(Request request, Next next) throws Exception;
```

Each middleware decides whether to pass the request downstream (`next.handle(request)`) or return a response immediately.

### Request → Response flow

```
HTTP request
  │
  ▼
JdkHttpServer                     (adapter: convert HttpExchange → Request)
  │
  ├─ StaticAssetResolver?         (serve file or fall through)
  │
  ▼
HttpKernel.handle(request)        (core: dispatch through pipeline)
  │
  ├─ Router.resolve(method, path) (match route, parse {params})
  │     └─ no match → 404 (JSON or HTML)
  │
  ▼
Middleware pipeline               (global + route-specific)
  │
  ▼
RouteHandler.handle(request)      (user code → Response)
  │
  ▼
Response → JdkHttpServer          (convert Response → HttpExchange bytes)
```

## Module Export Diagram

Each module's public API surface:

```
javelin-support
  Collection, Input, Validation, ValidationRule, ValidationRuleRegistry
  Str, Arr, File, Obj, Html, Security, Date, Image, Http, Ai

javelin-core
  Application, Container, Router, Route, Routes, RouteHandler
  Request, Response, HtmlResponse, JsonResponse, RedirectResponse
  Model, QueryBuilder, TransactionCallback
  Config, Env, Database, Logger, View, ViewRenderer
  Middleware, ServiceProvider, ExceptionHandler
  HttpKernel, HttpServerAdapter, ProviderLoader
  Log, Json, HttpMethod, UploadedFile

javelin-config
  DotenvEnv, YamlConfig, ConfigServiceProvider

javelin-http-jdk
  JdkHttpServer, JdkHttpServiceProvider

javelin-db-jdbc
  JdbcDatabase, MigrationRunner, JdbcDatabaseServiceProvider

javelin-log-slf4j
  Slf4jLogger, Slf4jLogServiceProvider

javelin-view-pebble
  PebbleViewRenderer, PebbleViewExtensions, PebbleViewServiceProvider

javelin-security
  PasswordHasher, SecureHeadersMiddleware, RequestSizeLimitMiddleware
  RateLimitMiddleware, SecurityServiceProvider

javelin-cache
  Cache, InMemoryCache, CacheServiceProvider

javelin-console
  JavelinConsole, ConsoleServiceProvider, ConsoleKernel
  CommandRegistry, CommandContext, Command, AbstractCommand, CliException
  ConsoleOutput, FileGenerator, TemplateEngine, Names

javelin-starter
  Javelin, WorkspaceDefaults, JavelinStarterServiceProvider, ValidationRuleLoader
```

## Key Design Decisions

| Decision | Why |
|---|---|
| No annotation scanning | Startup is fast and predictable. All wiring is visible in code. |
| Constructor injection | Dependencies are explicit. No hidden magic setters. |
| Interface-first adapters | Swap any backend without changing application code. |
| ServiceProvider two-phase lifecycle | Registration before resolution prevents ordering bugs. |
| Static facades (`Log`, `View`) | Convenience for common operations while the real implementation is swappable through the provider. |
| Immutable Request | Route parameters are attached via `withParams()`, not mutation. Middleware can safely inspect the original request. |
| Model with explicit Database | No hidden global connection. Every model instance knows its database. |
| YAML migrations | Human-readable, version-controlled, framework-independent schema definitions. |
