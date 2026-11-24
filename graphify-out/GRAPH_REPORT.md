# Graph Report - javelin-framework  (2026-11-25)

## Corpus Check
- 278 files · ~76,896 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 50 file(s) not represented in the graph (top: .stub 35, (none) 9, .xml 2)

## Summary
- 3014 nodes · 7445 edges · 171 communities (88 shown, 83 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1017 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `dd4a274b`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- io.javelin.core.Request
- Application
- StaticAssetResolver
- .from
- Middleware
- ServiceProvider
- UploadedFile
- YamlConfig
- Model
- CLI Reference
- Date
- Container
- NewCommand.java
- AppProfile
- .imageSupportDetectsAndReadsImageMetadata
- .instance
- Validation
- NettyHttpServerIntegrationTest.java
- io.javelin.core.Application
- MigrationRunner
- JdkHttpServerIntegrationTest
- Response
- Support Module
- ApplicationTest
- TrustedProxyResolver
- Input
- database
- map
- ConsoleKernel
- Console Module
- docker-app.yaml
- Override
- NettyHttpServer
- Deployment
- Logger
- ProjectLauncher
- Route
- Override
- HtmlResponse
- .adapterFor
- Architecture
- pebble
- Agent Notes
- .write
- ValidationRule
- SecureHeadersMiddleware
- Validator
- .handle
- JdbcQueryBuilder
- ValidationRule
- MakeMigrationCommand
- caddy
- package.json
- Request
- io.javelin:javelin-starter
- ValkeyCache
- Env
- Obj
- Module Authoring
- nginx
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- JdkHttpServer.java
- Str
- Router
- environment
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- JsonResponse
- Database JDBC Module
- VersionCommand
- Router
- Request
- GenerateCommandSupport
- ProfileConfig
- .handle
- AdultAgeRule
- Quick Start
- WorkspaceStarterRule
- Core Module
- PebbleViewExtensions
- .run
- MapConfig
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- Config
- AI Commands
- MigrateCommand
- Docker Compose
- need_command
- Config
- Production Performance Tuning
- io.javelin:javelin-parent
- Config
- NatsQueue.java
- AdultAgeRule
- ValidationRuleRegistry
- .success
- NettyHttpServer
- HTTP Common Module
- Installation Guide
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- HTTP JDK Module
- depends_on
- healthcheck
- Job
- .application
- Javelin CLI
- services
- Arr
- app
- nats
- valkey
- ProviderLoaderTest
- migrate — Run pending migrations
- File
- Model
- Security
- TestConfig
- Javelin Docs
- Database
- Exception Module
- Queue
- PasswordHasher
- WorkspaceAgeRule
- build.sh script
- Writing a Middleware Module
- HTTP Undertow Module
- JobHandler
- HTTP Netty Module
- serve — Production server

## God Nodes (most connected - your core abstractions)
1. `Application` - 97 edges
2. `Router` - 80 edges
3. `Request` - 78 edges
4. `CommandContext` - 68 edges
5. `Response` - 68 edges
6. `Collection` - 63 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 56 edges
9. `Input` - 45 edges
10. `Handle` - 43 edges

## Surprising Connections (you probably didn't know these)
- `LoggingConfig` --references--> `Slf4jLogServiceProvider`  [INFERRED]
  docs/modules/log-slf4j.md → modules/javelin-log-slf4j/src/main/java/io/javelin/log/slf4j/Slf4jLogServiceProvider.java
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Trusted proxy configuration` --references--> `JdkHttpServiceProvider`  [INFERRED]
  docs/deployment.md → modules/javelin-http-jdk/src/main/java/io/javelin/http/jdk/JdkHttpServiceProvider.java
- `Router` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java
- `Routes` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java

## Import Cycles
- None detected.

## Communities (171 total, 83 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.10
Nodes (11): ConfigServiceProvider, ConfigServiceProvider, ProviderSorter, ServiceProvider, ProviderSorterTest, CycleA, CycleB, FirstProvider (+3 more)

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.04
Nodes (27): AbstractCommand, CliException, CommandContext, AiChatCommand, AiExplainCommand, AiGenerateModuleCommand, AiInstallCommand, BuildCommand (+19 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.05
Nodes (12): BackendController, web, StructuredLogger, ConfigurableExceptionHandler, ErrorPageResolver, FileErrorPageResolver, SensitiveDataRedactor, ConfigurableExceptionHandlerTest (+4 more)

### Community 13 - "Application"
Cohesion: 0.06
Nodes (6): JavelinConsole, JavelinConsole, RouteListCommand, ServeCommand, Application, JdbcDatabaseServiceProvider

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.20
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - "Middleware"
Cohesion: 0.08
Nodes (12): Layer 0 — Foundation, Layer 1 — Contracts, Layer 2 — Adapters, Layer 3 — Toolchain, Module Dependency Graph, TrustedProxyResolver, Trusted proxy configuration, Middleware (+4 more)

### Community 21 - "ServiceProvider"
Cohesion: 0.06
Nodes (28): Main, Layer 4 — Aggregator, Adding to the hardcoded provider list, Integrating with Starter, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Provider registration order, Registration order matters (+20 more)

### Community 24 - "YamlConfig"
Cohesion: 0.05
Nodes (30): Application Flow, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development), Error Handling (+22 more)

### Community 25 - "Model"
Cohesion: 0.08
Nodes (8): Model, ModelTest, CastedArticle, SampleHydratedUser, SampleUser, Status, DRAFT, PUBLISHED

### Community 26 - "CLI Reference"
Cohesion: 0.07
Nodes (28): build — Package the application, cache:clear — Clear application cache, CLI Reference, Command summary, config:cache — Cache configuration, dev — Development server, doctor — System diagnostics, Exit codes (+20 more)

### Community 28 - "Container"
Cohesion: 0.06
Nodes (32): Application, Container, Container, Container, Binding, ContainerTest, CircularA, CircularB (+24 more)

### Community 30 - "AppProfile"
Cohesion: 0.16
Nodes (8): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, ChannelInitializer<SocketChannel>

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.10
Nodes (6): ExceptionConfig, ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "io.javelin.core.Application"
Cohesion: 0.09
Nodes (17): Logging And Error Handling, Cache, Cache Module, CacheServiceProvider, Notes, Application profile, ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest (+9 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.15
Nodes (4): MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.15
Nodes (5): DefaultExceptionHandler, ExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.11
Nodes (4): RedirectResponse, Response, SecurityResponses, ResponseTest

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 41 - "ApplicationTest"
Cohesion: 0.15
Nodes (6): ApplicationTest, BaseProvider, DependentProvider, FirstResource, RecordingProvider, SecondResource

### Community 42 - "TrustedProxyResolver"
Cohesion: 0.12
Nodes (4): ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolver, Html

### Community 44 - "database"
Cohesion: 0.06
Nodes (33): app, env, name, database, database, driver, host, password (+25 more)

### Community 49 - "Console Module"
Cohesion: 0.10
Nodes (17): AbstractCommand, CLI Commands, CliException, Command, CommandContext, CommandRegistry, Console Module, ConsoleKernel (cli package) (+9 more)

### Community 51 - "docker-app.yaml"
Cohesion: 0.04
Nodes (47): app, env, name, cache, driver, url, database, database (+39 more)

### Community 52 - "Override"
Cohesion: 0.13
Nodes (10): Core interfaces you can implement, Example: Implementing `Cache` (javelin-cache), Implementing a Core Interface, Database, QueryBuilder, TransactionCallback, QueryBuilder, EditableUser (+2 more)

### Community 54 - "Deployment"
Cohesion: 0.22
Nodes (9): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Migration Strategy, Performance Benchmarks, Reverse Proxy, Trusted proxy configuration (+1 more)

### Community 55 - "Logger"
Cohesion: 0.06
Nodes (16): Log, Logger, JsonLogEncoder, Logging SLF4J Module, LoggingConfig, Notes, RequestLoggingMiddleware, Slf4jLogger (+8 more)

### Community 56 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 57 - "Route"
Cohesion: 0.08
Nodes (19): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+11 more)

### Community 58 - "Override"
Cohesion: 0.14
Nodes (4): TransactionCallback, MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "HtmlResponse"
Cohesion: 0.23
Nodes (4): HtmlResponse, View, ViewRenderer, ViewTest

### Community 60 - ".adapterFor"
Cohesion: 0.14
Nodes (9): HttpServerAdapter, JdkHttpServiceProvider, Configuration, Configuration, HttpServerAdapter, HttpServerDriverProvider, HttpServerDriverProviderTest, DriverConfig (+1 more)

### Community 62 - "Architecture"
Cohesion: 0.17
Nodes (12): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Middleware pipeline, Module Boundaries (+4 more)

### Community 63 - "pebble"
Cohesion: 0.12
Nodes (15): driver, pebble, allow_override_core_operators, auto_escaping, cache, default_escaping_strategy, default_locale, greedy_match_method (+7 more)

### Community 65 - "Agent Notes"
Cohesion: 0.13
Nodes (14): Agent Notes, Architect Guidelines, Architecture Intent, Coding Principles, Current MVP Boundaries, Do, Do / Don’t, Don’t (+6 more)

### Community 66 - ".write"
Cohesion: 0.13
Nodes (4): GeneratorSupport, MakeControllerCommand, MakeModelCommand, MakeProviderCommand

### Community 68 - "SecureHeadersMiddleware"
Cohesion: 0.24
Nodes (10): routes:list — Display registered routes, Bearer authentication, Notes, PasswordHasher, RateLimitMiddleware, RequestSizeLimitMiddleware, SecureHeadersMiddleware, Security Module (+2 more)

### Community 70 - ".handle"
Cohesion: 0.17
Nodes (5): Bearer authentication and authorization, Middleware, RequiresAuthenticationMiddleware, RequestAttributesTest, AuthorizationMiddlewareTest

### Community 71 - "JdbcQueryBuilder"
Cohesion: 0.13
Nodes (3): JdbcDatabase, JdbcQueryBuilder, Condition

### Community 74 - "caddy"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, caddy

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.27
Nodes (18): io.javelin:javelin-cache, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-demo-app, io.javelin:javelin-exception (+10 more)

### Community 78 - "ValkeyCache"
Cohesion: 0.10
Nodes (7): InMemoryCache, Cache, InMemoryCache, Entry, ValkeyCache, ValkeyCacheTest, Cache

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (37): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+29 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 86 - "Router"
Cohesion: 0.15
Nodes (3): DefaultExceptionHandlerTest, HttpKernelTest, StaticAssetResolverTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 89 - "JsonResponse"
Cohesion: 0.14
Nodes (4): HomeController, UserController, Json, JsonResponse

### Community 90 - "Database JDBC Module"
Cohesion: 0.22
Nodes (8): Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Notes, QueryBuilder

### Community 94 - "GenerateCommandSupport"
Cohesion: 0.19
Nodes (3): GenerateCommandSupport, FileGenerator, TemplateEngine

### Community 96 - ".handle"
Cohesion: 0.18
Nodes (4): Authenticated, BearerAuthMiddleware, BearerTokenResolver, BearerAuthMiddlewareTest

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "Core Module"
Cohesion: 0.11
Nodes (19): Config, Core Module, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse, HttpKernel (+11 more)

### Community 101 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (17): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Notes, Options, PebbleViewExtensions, PebbleViewRenderer, PebbleViewServiceProvider (+9 more)

### Community 102 - ".run"
Cohesion: 0.14
Nodes (4): CommandGenerationTest, RequestInputTest, HttpTimeoutConfigTest, TrustedProxyResolverTest

### Community 104 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 106 - "AI Commands"
Cohesion: 0.40
Nodes (5): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI

### Community 108 - "Docker Compose"
Cohesion: 0.12
Nodes (14): AppServiceProvider, Boot Sequence, Phase 1: Entry Point, Phase 2: Boot, Phase 3: Dispatch, Docker Compose, Dockerfile, .dockerignore (+6 more)

### Community 116 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 129 - "HTTP Common Module"
Cohesion: 0.40
Nodes (5): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy

### Community 130 - "Installation Guide"
Cohesion: 0.25
Nodes (8): 1. Requirements, 2. Install the `javelin` CLI, 3. Make the framework artifacts available, 4. Create and verify a workspace, 5. Docker install (optional), 6. Troubleshooting, 7. Updating, Installation Guide

### Community 131 - "Cache Valkey Module"
Cohesion: 0.50
Nodes (4): Cache Valkey Module, Notes, ValkeyCache, ValkeyCacheServiceProvider

### Community 132 - "db"
Cohesion: 0.25
Nodes (8): environment, image, restart, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, db

### Community 133 - "SupportUtilitiesTest"
Cohesion: 0.26
Nodes (3): SupportUtilitiesTest, Person, Profile

### Community 134 - "dev.mjs"
Cohesion: 0.25
Nodes (3): bin, env, watchers

### Community 135 - "HTTP JDK Module"
Cohesion: 0.40
Nodes (5): HTTP JDK Module, JdkHttpServer, Notes, Static file serving, Trusted proxy resolution

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - "Job"
Cohesion: 0.25
Nodes (6): Queue contracts, Contracts (javelin-core), Job, RetryPolicy, JobTest, Features

### Community 140 - "Javelin CLI"
Cohesion: 0.29
Nodes (6): AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image

### Community 141 - "services"
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 143 - "app"
Cohesion: 0.29
Nodes (7): build, ports, restart, context, dockerfile, services, app

### Community 144 - "nats"
Cohesion: 0.33
Nodes (6): command, image, ports, restart, volumes, nats

### Community 145 - "valkey"
Cohesion: 0.33
Nodes (6): valkey, command, image, ports, restart, volumes

### Community 147 - "ProviderLoaderTest"
Cohesion: 0.32
Nodes (4): ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder

### Community 149 - "migrate — Run pending migrations"
Cohesion: 0.67
Nodes (3): migrate — Run pending migrations, On first run, What happens

### Community 151 - "Model"
Cohesion: 0.40
Nodes (5): Model, Model instance methods, Model overridable methods, Model static methods, User

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 156 - "Exception Module"
Cohesion: 0.40
Nodes (5): ConfigurableExceptionHandler, Configuration, Error pages, Exception Module, Service provider

### Community 157 - "Queue"
Cohesion: 0.25
Nodes (5): NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, Queue

### Community 161 - "build.sh script"
Cohesion: 0.83
Nodes (3): build.sh script, build_assets(), build_java()

### Community 162 - "Writing a Middleware Module"
Cohesion: 0.50
Nodes (4): Example: Request timing middleware, Middleware reference (built-in), Service provider adds middleware to the router, Writing a Middleware Module

### Community 163 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

### Community 167 - "HTTP Netty Module"
Cohesion: 0.67
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DELETE`, `GET`, `HEAD`, `OPTIONS` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 729 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **83 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `.instance`, `path`, `io.javelin.core.Application`, `Override`, `cli/ConsoleKernel.java`, `ApplicationTest`, `.application`, `map`, `.adapterFor`, `ProviderLoaderTest`, `ServiceProvider`, `AppProfile`, `Container`, `NewCommand.java`, `.success`?**
  _High betweenness centrality (0.101) - this node is a cross-community bridge._
- **Are the 21 inferred relationships involving `Router` (e.g. with `Layer 1 — Contracts` and `Application`) actually correct?**
  _`Router` has 21 INFERRED edges - model-reasoned connections that need verification._
- **What connects `io.javelin:javelin-parent`, `DELETE`, `GET` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.10336817653890824 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `Agent Notes`, `io.javelin.core.Application`, `Core Module`, `ApplicationTest`, `Application`, `.adapterFor`, `Module Authoring`, `Middleware`, `ProviderLoaderTest`, `Override`, `ServiceProvider`, `Container`?**
  _High betweenness centrality (0.070) - this node is a cross-community bridge._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.07692307692307693 - nodes in this community are weakly interconnected._
- **Why does `Collection` connect `Collection` to `Input`, `SupportUtilitiesTest`, `objects`?**
  _High betweenness centrality (0.048) - this node is a cross-community bridge._