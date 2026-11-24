# Graph Report - javelin-framework  (2026-11-24)

## Corpus Check
- 260 files · ~72,580 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 47 file(s) not represented in the graph (top: .stub 33, (none) 9, .xml 1)

## Summary
- 2833 nodes · 6919 edges · 172 communities (90 shown, 82 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 937 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `1cef3379`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- Javelin Framework
- Application
- StaticAssetResolver
- arraylist
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
- PasswordHasher
- JdkHttpServerIntegrationTest
- Response
- Support Module
- ApplicationTest
- TrustedProxyResolver
- Input
- database
- .commands
- Console Module
- Application
- docker-app.yaml
- Override
- NettyHttpServer
- Deployment
- Core Module
- ProjectLauncher
- RouteHandler
- Override
- Application Flow
- .adapterFor
- JdbcDatabase
- pebble
- Agent Notes
- .write
- ValidationRule
- SecureHeadersMiddleware
- Validator
- .withAttribute
- JdbcQueryBuilder
- ValidationRule
- .run
- caddy
- package.json
- Request
- io.javelin:javelin-starter
- ValkeyCache
- optional
- Obj
- Module Authoring
- nginx
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- JdkHttpServer.java
- Str
- Router
- environment
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- duration
- Database JDBC Module
- VersionCommand
- Router
- Request
- MakeMigrationCommand.java
- ProfileConfig
- .handle
- AdultAgeRule
- Quick Start
- WorkspaceStarterRule
- Core Module
- PebbleViewExtensions
- .run
- Route
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
- .load
- .output
- HttpMethod
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
- ProfileConfig
- Javelin CLI
- services
- Arr
- app
- nats
- valkey
- .run
- ProviderLoaderTest
- migrate — Run pending migrations
- File
- Full Worked Example
- Security
- TestConfig
- Javelin Docs
- Database
- Queue
- Config Module
- WorkspaceAgeRule
- build.sh script
- .jobPayloadSurvivesJsonRoundTrip
- Routing
- Routes
- Binding Strategies
- AiGenerateModuleCommand

## God Nodes (most connected - your core abstractions)
1. `Application` - 100 edges
2. `Router` - 80 edges
3. `Request` - 78 edges
4. `CommandContext` - 68 edges
5. `Response` - 68 edges
6. `Collection` - 63 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 58 edges
9. `Input` - 45 edges
10. `Handle` - 43 edges

## Surprising Connections (you probably didn't know these)
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Boot Flow` --references--> `Container`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/Container.java
- `Notes` --references--> `ValkeyCacheServiceProvider`  [INFERRED]
  docs/modules/cache.md → modules/javelin-cache-valkey/src/main/java/io/javelin/cache/valkey/ValkeyCacheServiceProvider.java
- `TemplateEngine` --references--> `TemplateEngine`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/cli/generator/TemplateEngine.java
- `ConsoleServiceProvider` --references--> `ConsoleServiceProvider`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/console/ConsoleServiceProvider.java

## Import Cycles
- None detected.

## Communities (172 total, 82 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.12
Nodes (9): ProviderSorter, ServiceProvider, ProviderSorterTest, CycleA, CycleB, FirstProvider, NeedsFirstProvider, SecondProvider (+1 more)

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.07
Nodes (15): AbstractCommand, CliException, CommandContext, AiInstallCommand, BuildCommand, DoctorCommand, MigrateCommand, MigrateRollbackCommand (+7 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "Javelin Framework"
Cohesion: 0.10
Nodes (21): Architecture at a glance, Boot Flow, Build And Test, Cache And Queues, CLI, Database, Documentation, Example Routes (+13 more)

### Community 13 - "Application"
Cohesion: 0.10
Nodes (4): web, Config, JdbcDatabaseServiceProvider, PebbleViewServiceProvider

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 18 - "Middleware"
Cohesion: 0.14
Nodes (8): Bearer authentication and authorization, Middleware, Middleware, Next, RequiresAuthenticationMiddleware, RateLimitMiddleware, Bucket, RequestSizeLimitMiddleware

### Community 21 - "ServiceProvider"
Cohesion: 0.08
Nodes (24): Adding to the hardcoded provider list, Integrating with Starter, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Provider registration order, Registration order matters, The ServiceProvider Contract, ServiceProvider (+16 more)

### Community 24 - "YamlConfig"
Cohesion: 0.19
Nodes (4): YamlConfig, TypeReference<>, TypeReference<>, YamlConfigTest

### Community 25 - "Model"
Cohesion: 0.07
Nodes (10): Model, Model instance methods, Model overridable methods, Model, ModelTest, SampleHydratedUser, SampleUser, Status (+2 more)

### Community 26 - "CLI Reference"
Cohesion: 0.07
Nodes (28): build — Package the application, cache:clear — Clear application cache, CLI Reference, Command summary, Config, config:cache — Cache configuration, dev — Development server, doctor — System diagnostics (+20 more)

### Community 28 - "Container"
Cohesion: 0.06
Nodes (27): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Layer 0 — Foundation, Layer 1 — Contracts, Layer 3 — Toolchain (+19 more)

### Community 30 - "AppProfile"
Cohesion: 0.16
Nodes (8): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, ChannelInitializer<SocketChannel>

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.16
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "io.javelin.core.Application"
Cohesion: 0.08
Nodes (23): HttpServerAdapter, JdkHttpServiceProvider, Behavior, Configuration, HTTP Netty Module, Programmatic use, Behavior, Configuration (+15 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.14
Nodes (4): MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.16
Nodes (5): DefaultExceptionHandler, ExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.07
Nodes (9): HomeController, new — Create a workspace, Options, HtmlResponse, JsonResponse, RedirectResponse, Response, SecurityResponses (+1 more)

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 41 - "ApplicationTest"
Cohesion: 0.15
Nodes (6): ApplicationTest, BaseProvider, DependentProvider, FirstResource, RecordingProvider, SecondResource

### Community 42 - "TrustedProxyResolver"
Cohesion: 0.16
Nodes (4): ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolver, TrustedProxyResolverTest

### Community 44 - "database"
Cohesion: 0.09
Nodes (22): app, env, name, database, database, driver, host, password (+14 more)

### Community 48 - ".commands"
Cohesion: 0.07
Nodes (10): CommandRegistry, CommandRegistry, CacheClearCommand, ConfigCacheCommand, DevCommand, MakeControllerCommand, MakeModelCommand, MakeServiceCommand (+2 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "Application"
Cohesion: 0.07
Nodes (5): JavelinConsole, JavelinConsole, RouteListCommand, ServeCommand, Application

### Community 51 - "docker-app.yaml"
Cohesion: 0.05
Nodes (36): app, env, name, cache, driver, url, database, database (+28 more)

### Community 52 - "Override"
Cohesion: 0.15
Nodes (4): CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder

### Community 54 - "Deployment"
Cohesion: 0.22
Nodes (9): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Migration Strategy, Performance Benchmarks, Reverse Proxy, Trusted proxy configuration (+1 more)

### Community 55 - "Core Module"
Cohesion: 0.10
Nodes (8): Logging SLF4J Module, Notes, Slf4jLogger, Slf4jLogServiceProvider, Log, NoopLogger, Logger, Slf4jLogger

### Community 56 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "Application Flow"
Cohesion: 0.13
Nodes (15): Application Flow, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development), Error Handling (+7 more)

### Community 60 - ".adapterFor"
Cohesion: 0.20
Nodes (3): HttpServerDriverProviderTest, DriverConfig, DriverEnv

### Community 63 - "pebble"
Cohesion: 0.12
Nodes (15): driver, pebble, allow_override_core_operators, auto_escaping, cache, default_escaping_strategy, default_locale, greedy_match_method (+7 more)

### Community 65 - "Agent Notes"
Cohesion: 0.13
Nodes (14): Agent Notes, Architect Guidelines, Architecture Intent, Coding Principles, Current MVP Boundaries, Do, Do / Don’t, Don’t (+6 more)

### Community 66 - ".write"
Cohesion: 0.11
Nodes (4): GeneratorSupport, MakeControllerCommand, MakeModelCommand, MakeProviderCommand

### Community 68 - "SecureHeadersMiddleware"
Cohesion: 0.16
Nodes (14): routes:list — Display registered routes, Example: Request timing middleware, Middleware reference (built-in), Service provider adds middleware to the router, Writing a Middleware Module, Bearer authentication, Notes, PasswordHasher (+6 more)

### Community 70 - ".withAttribute"
Cohesion: 0.24
Nodes (3): UserController, Key Design Decisions, RequestAttributesTest

### Community 73 - ".run"
Cohesion: 0.12
Nodes (10): AppServiceProvider, Main, Boot Sequence, Phase 1: Entry Point, Phase 2: Boot, Phase 3: Dispatch, Startup Time, Why startup is fast (+2 more)

### Community 74 - "caddy"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, caddy

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.28
Nodes (17): io.javelin:javelin-cache, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-demo-app, io.javelin:javelin-http-common (+9 more)

### Community 78 - "ValkeyCache"
Cohesion: 0.07
Nodes (12): BackendController, Cache, Cache Module, CacheServiceProvider, InMemoryCache, Notes, Cache, InMemoryCache (+4 more)

### Community 81 - "Module Authoring"
Cohesion: 0.08
Nodes (24): Adding Tests, Adding the module to the parent POM, Checklist for new modules, Config-Driven Modules, Creating a New Interface, Creating your own facade, Example: Implementing `Cache` (javelin-cache), Expected YAML config shape (+16 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 86 - "Router"
Cohesion: 0.10
Nodes (5): DefaultExceptionHandlerTest, HttpKernelTest, ResponseTest, StaticAssetResolverTest, ProfileWiringTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 90 - "Database JDBC Module"
Cohesion: 0.22
Nodes (8): Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Notes, QueryBuilder

### Community 93 - "Request"
Cohesion: 0.14
Nodes (3): Request, PartDisposition, RequestInputTest

### Community 94 - "MakeMigrationCommand.java"
Cohesion: 0.10
Nodes (6): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, FileGenerator, Names, TemplateEngine

### Community 96 - ".handle"
Cohesion: 0.17
Nodes (4): Authenticated, BearerAuthMiddleware, BearerTokenResolver, BearerAuthMiddlewareTest

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "Core Module"
Cohesion: 0.11
Nodes (18): Config, Core Module, DefaultExceptionHandler, Env, ExceptionHandler, HtmlResponse, JsonResponse, RedirectResponse, HttpKernel, HttpMethod (+10 more)

### Community 101 - "PebbleViewExtensions"
Cohesion: 0.05
Nodes (19): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Notes, Options, PebbleViewExtensions, PebbleViewRenderer, PebbleViewServiceProvider (+11 more)

### Community 102 - ".run"
Cohesion: 0.16
Nodes (3): CommandGenerationTest, AuthorizationMiddlewareTest, HttpTimeoutConfigTest

### Community 104 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 106 - "AI Commands"
Cohesion: 0.40
Nodes (5): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI

### Community 108 - "Docker Compose"
Cohesion: 0.22
Nodes (9): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running (+1 more)

### Community 116 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 125 - ".load"
Cohesion: 0.19
Nodes (5): ConfigServiceProvider, ConfigServiceProvider, DotenvEnv, Entry, DotenvEnvTest

### Community 127 - "HttpMethod"
Cohesion: 0.16
Nodes (12): File Uploads, Request, UploadedFile, HttpMethod, DELETE, GET, HEAD, OPTIONS (+4 more)

### Community 128 - "NettyHttpServer"
Cohesion: 0.17
Nodes (3): HttpTimeoutConfig, NettyHttpServer, UndertowHttpServer

### Community 129 - "HTTP Common Module"
Cohesion: 0.25
Nodes (7): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, TrustedProxyResolver, Trusted proxy configuration

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
Cohesion: 0.19
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
Cohesion: 0.23
Nodes (6): Queue contracts, Contracts (javelin-core), Job, JobHandler, RetryPolicy, Features

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

### Community 151 - "Full Worked Example"
Cohesion: 0.20
Nodes (10): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+2 more)

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 155 - "Database"
Cohesion: 0.16
Nodes (9): Layer 2 — Adapters, Core interfaces you can implement, Database, Model static methods, QueryBuilder, TransactionCallback, User, Database (+1 more)

### Community 157 - "Queue"
Cohesion: 0.29
Nodes (5): NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, Queue

### Community 159 - "Config Module"
Cohesion: 0.29
Nodes (6): Config interface methods, Config Module, DotenvEnv, Environment expression resolution, Notes, YamlConfig

### Community 161 - "build.sh script"
Cohesion: 0.83
Nodes (3): build.sh script, build_assets(), build_java()

### Community 163 - "Routing"
Cohesion: 0.40
Nodes (5): Route, RouteHandler, Router, Routes, Routing

### Community 167 - "Binding Strategies"
Cohesion: 0.50
Nodes (4): `bind(A, B)` — reusable factory, Binding Strategies, `instance(T, object)` — pre-built singleton, `singleton(T, supplier)` — lazy singleton

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DEV`, `PROD`, `DRAFT`, `PUBLISHED` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 705 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **82 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `path`, `Override`, `cli/ConsoleKernel.java`, `Application`, `ProviderLoaderTest`, `ServiceProvider`, `Container`, `NewCommand.java`, `AppProfile`, `.instance`, `io.javelin.core.Application`, `ApplicationTest`, `list`, `.commands`, `.adapterFor`, `map`, `optional`, `Router`, `PebbleViewExtensions`, `.registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot`, `.load`, `.output`?**
  _High betweenness centrality (0.092) - this node is a cross-community bridge._
- **Are the 21 inferred relationships involving `Router` (e.g. with `Layer 1 — Contracts` and `Application`) actually correct?**
  _`Router` has 21 INFERRED edges - model-reasoned connections that need verification._
- **What connects `io.javelin:javelin-parent`, `DEV`, `PROD` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.12162162162162163 - nodes in this community are weakly interconnected._
- **Why does `Database` connect `Database` to `path`, `Override`, `MigrationRunner`, `Override`, `Application`, `Override`, `ServiceProvider`, `Model`, `Database JDBC Module`, `JdbcDatabase`?**
  _High betweenness centrality (0.059) - this node is a cross-community bridge._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.1265597147950089 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `Agent Notes`, `io.javelin.core.Application`, `Core Module`, `PebbleViewExtensions`, `ApplicationTest`, `Javelin Framework`, `Application`, `Module Authoring`, `Application`, `ProviderLoaderTest`, `ServiceProvider`, `Database`, `Container`, `.load`?**
  _High betweenness centrality (0.059) - this node is a cross-community bridge._