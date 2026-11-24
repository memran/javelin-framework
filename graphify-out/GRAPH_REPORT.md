# Graph Report - javelin-framework  (2026-11-25)

## Corpus Check
- 292 files · ~79,649 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 50 file(s) not represented in the graph (top: .stub 35, (none) 9, .xml 2)

## Summary
- 3150 nodes · 7785 edges · 184 communities (90 shown, 94 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1061 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `b83e57d3`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
- path
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- io.javelin.core.Response
- Application
- StaticAssetResolver
- .from
- Request
- ServiceProvider
- UploadedFile
- YamlConfig
- Model
- CLI Reference
- Date
- Container
- AppProfile
- .imageSupportDetectsAndReadsImageMetadata
- .instance
- Validation
- NettyHttpServerIntegrationTest.java
- io.javelin.core.ServiceProvider
- MigrationRunner
- JdkHttpServerIntegrationTest
- Response
- Support Module
- ApplicationTest
- TrustedProxyResolver
- Input
- database
- map
- objects
- .commands
- Console Module
- DevCommand
- docker-app.yaml
- Override
- NettyHttpServer
- Config
- JsonLogEncoder
- ProjectLauncher
- Route
- Override
- RecordingLogger
- .adapterFor
- io.javelin.core.Database
- Override
- pebble
- Agent Notes
- .write
- ValidationRule
- SecureHeadersMiddleware
- Validator
- JdbcDatabase
- JdbcQueryBuilder
- ValidationRule
- Logger
- caddy
- package.json
- Request
- io.javelin:javelin-starter
- ValkeyCache
- Log
- Obj
- Module Authoring
- nginx
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- Str
- Router
- environment
- .run
- com.fasterxml.jackson.databind.ObjectMapper
- Database JDBC Module
- VersionCommand
- RecordingLogger
- JdkHttpServer
- GenerateCommandSupport
- Env
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
- .make
- need_command
- Config
- Slf4jLogger
- io.javelin:javelin-parent
- Config
- NatsQueue.java
- AdultAgeRule
- ValidationRuleRegistry
- .output
- SensitiveDataRedactor
- NettyHttpServer
- HTTP Common Module
- Installation Guide
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- io.javelin.core.Request
- depends_on
- healthcheck
- Job
- ProfileConfig
- Javelin CLI
- services
- ModelTest
- app
- nats
- valkey
- .dispatch
- .rollbackCommandReplaysYamlDownStatements
- migrate — Run pending migrations
- File
- .modelUsesTableConventionAndPersistsThroughTheDatabase
- Security
- TestConfig
- Javelin Docs
- Database
- Exception Module
- Queue
- primary
- WorkspaceAgeRule
- build.sh script
- log
- database
- server
- .jobPayloadSurvivesJsonRoundTrip
- MapConfig
- BackendController
- MigrationRunner
- TransactionCallback
- ProfileConfig
- queue
- QueueStatusCommand
- QueueWorkCommand
- ScheduleRunCommand
- errors
- Command
- FileErrorPageResolver
- javelin

## God Nodes (most connected - your core abstractions)
1. `Application` - 93 edges
2. `Router` - 80 edges
3. `Request` - 78 edges
4. `CommandContext` - 68 edges
5. `Response` - 68 edges
6. `Collection` - 63 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 54 edges
9. `Input` - 45 edges
10. `Config` - 44 edges

## Surprising Connections (you probably didn't know these)
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Connection pool (HikariCP)` --references--> `Logger`  [INFERRED]
  docs/deployment.md → modules/javelin-core/src/main/java/io/javelin/core/Logger.java
- `Trusted proxy configuration` --references--> `JdkHttpServiceProvider`  [INFERRED]
  docs/deployment.md → modules/javelin-http-jdk/src/main/java/io/javelin/http/jdk/JdkHttpServiceProvider.java
- `Router` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java
- `Routes` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java

## Import Cycles
- None detected.

## Communities (184 total, 94 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.08
Nodes (15): ConfigServiceProvider, ConfigServiceProvider, ProviderSorter, ServiceProvider, ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder (+7 more)

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.08
Nodes (13): AbstractCommand, CommandContext, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, ConfigCacheCommand, MigrateRollbackCommand (+5 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Response"
Cohesion: 0.21
Nodes (3): ConfigurableExceptionHandler, ErrorPageResolver, ConfigurableExceptionHandlerTest

### Community 13 - "Application"
Cohesion: 0.06
Nodes (7): JavelinConsole, ServeCommand, JavelinConsole, RouteListCommand, ServeCommand, Application, ConsoleKernel

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.22
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - "Request"
Cohesion: 0.06
Nodes (13): UserController, Key Design Decisions, Bearer authentication and authorization, Middleware, Middleware, Next, Request, RequiresAuthenticationMiddleware (+5 more)

### Community 21 - "ServiceProvider"
Cohesion: 0.10
Nodes (24): Adding to the hardcoded provider list, Integrating with Starter, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Provider registration order, Registration order matters, The ServiceProvider Contract, ServiceProvider (+16 more)

### Community 24 - "YamlConfig"
Cohesion: 0.05
Nodes (30): Application Flow, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development), Error Handling (+22 more)

### Community 26 - "CLI Reference"
Cohesion: 0.07
Nodes (30): build — Package the application, cache:clear — Clear application cache, CLI Reference, Command summary, Config, config:cache — Cache configuration, dev — Development server, doctor — System diagnostics (+22 more)

### Community 28 - "Container"
Cohesion: 0.06
Nodes (32): Application, Container, Container, Container, Binding, ContainerTest, CircularA, CircularB (+24 more)

### Community 30 - "AppProfile"
Cohesion: 0.15
Nodes (8): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, ChannelInitializer<SocketChannel>

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.10
Nodes (8): Example, Javelin Support, Public API, Arr, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.14
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.11
Nodes (18): JdkHttpServiceProvider, Behavior, Configuration, HTTP Netty Module, Programmatic use, Behavior, Configuration, HTTP Undertow Module (+10 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.15
Nodes (5): MigrationRunner, MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.14
Nodes (6): DefaultExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer, UndertowHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.06
Nodes (9): HomeController, HtmlResponse, JsonResponse, RedirectResponse, Response, SecurityResponses, DefaultExceptionHandlerTest, ResponseTest (+1 more)

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
Cohesion: 0.04
Nodes (44): app, env, name, primary, database, connections, default, migrations_dir (+36 more)

### Community 48 - ".commands"
Cohesion: 0.08
Nodes (7): AiGenerateModuleCommand, CacheClearCommand, DoctorCommand, MakeControllerCommand, MakeModelCommand, MakeServiceCommand, ConsoleKernel

### Community 49 - "Console Module"
Cohesion: 0.13
Nodes (14): AbstractCommand, CLI Commands, CliException, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput, ConsoleServiceProvider (+6 more)

### Community 51 - "docker-app.yaml"
Cohesion: 0.18
Nodes (10): app, env, name, cache, driver, url, providers, security (+2 more)

### Community 52 - "Override"
Cohesion: 0.17
Nodes (4): QueryBuilder, EditableUser, RecordingDatabase, RecordingQueryBuilder

### Community 54 - "Config"
Cohesion: 0.15
Nodes (5): Config, DatabaseConfig, Connection, DatabaseConfigTest, ExceptionConfig

### Community 55 - "JsonLogEncoder"
Cohesion: 0.06
Nodes (32): Caddy alternative, Connection pool (HikariCP), Deployment, Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis) (+24 more)

### Community 56 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 57 - "Route"
Cohesion: 0.08
Nodes (18): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+10 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.14
Nodes (4): QueryTimingConfig, SqlTimer, SqlTimerTest, RecordingLogger

### Community 60 - ".adapterFor"
Cohesion: 0.13
Nodes (9): HTTP JDK Module, JdkHttpServer, Notes, Static file serving, Trusted proxy resolution, HttpServerAdapter, HttpServerDriverProviderTest, DriverConfig (+1 more)

### Community 61 - "io.javelin.core.Database"
Cohesion: 0.13
Nodes (6): Backward compatibility, Database Migration Module, Dialects, DatabaseManager, JdbcDatabaseServiceProviderTest, MigrationDialect

### Community 62 - "Override"
Cohesion: 0.19
Nodes (3): MigrationDialectsTest, RecordingDatabase, QueryBuilder

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
Cohesion: 0.13
Nodes (15): routes:list — Display registered routes, Example: Request timing middleware, Middleware reference (built-in), Service provider adds middleware to the router, Writing a Middleware Module, Bearer authentication, Notes, PasswordHasher (+7 more)

### Community 73 - "Logger"
Cohesion: 0.18
Nodes (10): Log, Logger, Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger (+2 more)

### Community 74 - "caddy"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, caddy

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.25
Nodes (19): io.javelin:javelin-cache, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration, io.javelin:javelin-demo-app (+11 more)

### Community 78 - "ValkeyCache"
Cohesion: 0.05
Nodes (29): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Layer 0 — Foundation, Layer 1 — Contracts, Layer 2 — Adapters (+21 more)

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (39): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+31 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 86 - "Router"
Cohesion: 0.11
Nodes (6): Router, ResolvedRoute, HttpKernelTest, RouterTest, StaticAssetResolverTest, ProfileWiringTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - ".run"
Cohesion: 0.19
Nodes (6): Main, Boot Sequence, Phase 1: Entry Point, Phase 2: Boot, Phase 3: Dispatch, Javelin

### Community 89 - "com.fasterxml.jackson.databind.ObjectMapper"
Cohesion: 0.11
Nodes (3): Json, View, ViewRenderer

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 94 - "GenerateCommandSupport"
Cohesion: 0.10
Nodes (7): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, NewCommand, FileGenerator, Names, TemplateEngine

### Community 96 - ".handle"
Cohesion: 0.20
Nodes (3): BearerAuthMiddleware, BearerTokenResolver, BearerAuthMiddlewareTest

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "Core Module"
Cohesion: 0.09
Nodes (23): Config, Core Module, Database, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse (+15 more)

### Community 101 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (17): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Notes, Options, PebbleViewExtensions, PebbleViewRenderer, PebbleViewServiceProvider (+9 more)

### Community 104 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 106 - "AI Commands"
Cohesion: 0.40
Nodes (5): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI

### Community 108 - ".make"
Cohesion: 0.08
Nodes (9): AppServiceProvider, web, Config-List Provider Loading, ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, JdbcDatabaseServiceProvider, NoopLogger, NatsQueueServiceProvider (+1 more)

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
Nodes (4): Html, SupportUtilitiesTest, Person, Profile

### Community 134 - "dev.mjs"
Cohesion: 0.25
Nodes (3): bin, env, watchers

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - "Job"
Cohesion: 0.19
Nodes (6): Queue contracts, Contracts (javelin-core), Job, JobHandler, RetryPolicy, Features

### Community 140 - "Javelin CLI"
Cohesion: 0.17
Nodes (8): CommandRegistry, AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry

### Community 141 - "services"
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 142 - "ModelTest"
Cohesion: 0.18
Nodes (6): ModelTest, CastedArticle, SampleHydratedUser, Status, DRAFT, PUBLISHED

### Community 143 - "app"
Cohesion: 0.29
Nodes (7): build, ports, restart, context, dockerfile, services, app

### Community 144 - "nats"
Cohesion: 0.33
Nodes (6): command, image, ports, restart, volumes, nats

### Community 145 - "valkey"
Cohesion: 0.33
Nodes (6): valkey, command, image, ports, restart, volumes

### Community 147 - ".rollbackCommandReplaysYamlDownStatements"
Cohesion: 0.31
Nodes (3): MigrationCommandTest, Env, RecordingDatabase

### Community 149 - "migrate — Run pending migrations"
Cohesion: 0.67
Nodes (3): migrate — Run pending migrations, On first run, What happens

### Community 151 - ".modelUsesTableConventionAndPersistsThroughTheDatabase"
Cohesion: 0.13
Nodes (5): Model, Model instance methods, Model overridable methods, Model static methods, SampleUser

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 156 - "Exception Module"
Cohesion: 0.40
Nodes (5): ConfigurableExceptionHandler, Configuration, Error pages, Exception Module, Service provider

### Community 157 - "Queue"
Cohesion: 0.29
Nodes (5): NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, Queue

### Community 159 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 161 - "build.sh script"
Cohesion: 0.83
Nodes (3): build.sh script, build_assets(), build_java()

### Community 162 - "log"
Cohesion: 0.22
Nodes (9): netty, io, root, log, channel, correlation_id_header, level, request_id_header (+1 more)

### Community 163 - "database"
Cohesion: 0.25
Nodes (8): database, connections, default, migrations_dir, query, log_all, slow_ms, timing

### Community 166 - "server"
Cohesion: 0.25
Nodes (8): server, host, idle_timeout_seconds, port, request_timeout_seconds, static_cache_immutable, static_cache_seconds, static_dir

### Community 175 - "queue"
Cohesion: 0.40
Nodes (5): queue, driver, jetstream, stream, url

### Community 179 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 182 - "javelin"
Cohesion: 0.67
Nodes (3): javelin, starter, http-driver

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 749 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **94 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `path`, `cli/ConsoleKernel.java`, `.rollbackCommandReplaysYamlDownStatements`, `ServiceProvider`, `Container`, `list`, `AppProfile`, `.instance`, `io.javelin.core.ServiceProvider`, `ApplicationTest`, `map`, `objects`, `.commands`, `Config`, `.adapterFor`, `com.fasterxml.jackson.databind.ObjectMapper`, `.registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot`, `.make`, `.output`?**
  _High betweenness centrality (0.065) - this node is a cross-community bridge._
- **Are the 21 inferred relationships involving `Router` (e.g. with `Layer 1 — Contracts` and `Application`) actually correct?**
  _`Router` has 21 INFERRED edges - model-reasoned connections that need verification._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.08333333333333333 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `Agent Notes`, `io.javelin.core.ServiceProvider`, `Core Module`, `ApplicationTest`, `Application`, `ValkeyCache`, `Module Authoring`, `ServiceProvider`, `com.fasterxml.jackson.databind.ObjectMapper`, `Container`?**
  _High betweenness centrality (0.054) - this node is a cross-community bridge._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.08787878787878788 - nodes in this community are weakly interconnected._
- **Why does `Collection` connect `Collection` to `list`, `SupportUtilitiesTest`?**
  _High betweenness centrality (0.052) - this node is a cross-community bridge._