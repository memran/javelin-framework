# Javelin Docs

This folder documents the public API of each Javelin module with small usage examples and short function-usage notes.

- [Installation](installation.md) — Prerequisites, CLI install, local artifacts, Docker, troubleshooting
- [Quick Start](quick-start.md) — Create, build and run your first Javelin app
- [Architecture](architecture.md) — Module dependency graph, design principles, module boundaries, core abstractions
- [Application Flow](application-flow.md) — Boot sequence, HTTP request lifecycle, middleware pipeline, console command flow, container resolution
- [Module Authoring](module-authoring.md) — How to create, structure, test, and integrate a Javelin module
- [CLI Reference](cli-reference.md) — Every `javelin` command with examples and output
- [Deployment](deployment.md) — Production tuning, Docker Compose, reverse proxy, health checks

## Module Index

- [Core](modules/core.md) — Application, Container, Router, Request, Response, Model, View, Log, Middleware, ServiceProvider, HttpKernel, QueryBuilder
- [Application Profile](modules/profile.md) — APP_ENV dev/prod behavior, detailed errors, Netty tuning
- [Starter](modules/starter.md) — Javelin, WorkspaceDefaults, ValidationRuleLoader, default provider stack
- [Config](modules/config.md) — DotenvEnv, YamlConfig, ConfigServiceProvider, environment expression resolution
- [HTTP Common](modules/http-common.md) — shared static asset resolution, trusted proxy resolution, adapter config
- [HTTP JDK](modules/http-jdk.md) — JdkHttpServer, static file serving, trusted proxy resolution, virtual threads
- [HTTP Netty](modules/http-netty.md) — NettyHttpServer, event-loop concurrency, driver selection
- [HTTP Undertow](modules/http-undertow.md) — UndertowHttpServer, worker dispatch, driver selection
- [Database JDBC](modules/db-jdbc.md) — JdbcDatabase, QueryBuilder, MigrationRunner, YAML migrations, transactions
- [Logging SLF4J](modules/log-slf4j.md) — Slf4jLogger, structured logging, request IDs, MDC, JSON encoder, config-driven levels
- [Exception](modules/exception.md) — global handler, custom error pages, environment-dependent details, sensitive-data redaction
- [View Pebble](modules/view-pebble.md) — PebbleViewRenderer, PebbleViewExtensions, template options, global variables
- [Console](modules/console.md) — CLI commands, JavelinConsole, CommandRegistry, generator helpers, AI commands
- [Security](modules/security.md) — PasswordHasher, SecureHeadersMiddleware, RequestSizeLimitMiddleware, RateLimitMiddleware, trusted proxies (Bearer auth lives in [Core](modules/core.md#bearer-authentication-and-authorization))
- [Cache](modules/cache.md) — Cache interface, InMemoryCache, CacheServiceProvider, TTL-based storage
- [Cache Valkey](modules/cache-valkey.md) — Lettuce-backed Valkey cache provider and config keys
- [Queue NATS](modules/queue-nats.md) — Job/RetryPolicy contracts, JetStream publish/subscribe and provider config
- [Support](modules/support.md) — Collection, Input, Validation, ValidationRule, Str, Arr, File, Image, Http, Ai, Date, Obj, Html, Security

## Reading Order

If you are new to Javelin:

1. Follow the [Quick Start](quick-start.md) for a working app, or [Installation](installation.md) for detailed setup.
2. Start with [Architecture](architecture.md) to understand the module system and design philosophy.
3. Read [Core](modules/core.md) for all foundational contracts.
4. Read [Starter](modules/starter.md) for the default boot path and entry point.
5. Read [Application Flow](application-flow.md) to trace exactly what happens during boot and request handling.
6. Read [Module Authoring](module-authoring.md) when you need to create or extend a module.
7. Read [Config](modules/config.md) for environment and YAML configuration.
8. Use the module pages for the feature you want to add:
   - [HTTP adapters](modules/http-common.md) for driver selection (`jdk`, `netty`, `undertow`) and the server adapter
   - [Database JDBC](modules/db-jdbc.md) for data access and migrations
   - [View Pebble](modules/view-pebble.md) for template rendering
   - [Security](modules/security.md) for hashing and middleware
   - [Cache Valkey](modules/cache-valkey.md) and [Queue NATS](modules/queue-nats.md) for distributed backends
   - [Logging SLF4J](modules/log-slf4j.md) for logging
9. Use [Console](modules/console.md) for the workspace toolchain and scaffolding commands.
10. Use [CLI Reference](cli-reference.md) for detailed `javelin` command examples with output.
11. See [Deployment](deployment.md) when you are ready to go to production, then [Support](modules/support.md) for helper utilities.
