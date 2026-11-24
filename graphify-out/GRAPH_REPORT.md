# Graph Report - javelin-framework  (2026-11-25)

## Corpus Check
- 310 files · ~82,442 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 52 file(s) not represented in the graph (top: .stub 37, (none) 9, .xml 2)

## Summary
- 3327 nodes · 8231 edges · 213 communities (119 shown, 94 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1139 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `112b6669`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
- .run
- cli/ConsoleKernel.java
- Handle
- Collection
- io.javelin.core.Request
- Application
- StaticAssetResolver
- .from
- Request
- ServiceProvider
- MultipartData
- YamlConfig
- Model
- CLI Reference
- Date
- Core Module
- AppProfile
- .imageSupportDetectsAndReadsImageMetadata
- .load
- Validation
- NettyHttpServerIntegrationTest.java
- io.javelin.core.ServiceProvider
- MigrationRunner
- Security.java
- JdkHttpServerIntegrationTest
- Response
- Support Module
- ApplicationTest
- TrustedProxyResolver
- Input
- app.yaml
- Container
- Console Module
- Javelin Framework
- docker-app.yaml
- Override
- NettyHttpServer
- Config
- Docker Compose
- .handle
- Route
- Override
- RecordingLogger
- .adapterFor
- JsonMapper
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
- Application Flow
- io.javelin:javelin-starter
- ValkeyCache
- Log
- Arr
- Module Authoring
- nginx
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- JdkHttpServer.java
- Str
- Router
- environment
- .run
- Json
- Database JDBC Module
- VersionCommand
- RecordingLogger
- JdkHttpServer
- GenerateCommandSupport
- Env
- Middleware
- AdultAgeRule
- Quick Start
- WorkspaceStarterRule
- ConsoleOutput
- JavelinViewExtension
- HtmlResponse
- MapConfig
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- Config
- JsonLogEncoder
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
- SensitiveDataRedactor
- .status
- HTTP Common Module
- Installation Guide
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- Session
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
- Architecture
- PebbleViewRenderer
- NewCommand
- com.fasterxml.jackson.databind.ObjectMapper
- Full Worked Example
- Security
- TestConfig
- Javelin Docs
- Database
- Exception Module
- .load
- primary
- WorkspaceAgeRule
- build.sh script
- log
- database
- server
- .modelUsesTableConventionAndPersistsThroughTheDatabase
- MapConfig
- Cache
- .handle
- TransactionCallback
- ProfileConfig
- queue
- MakeMigrationCommand
- Layer 2 — Adapters
- Override
- errors
- ContainerTest
- primary
- log
- Config Module
- PebbleViewExtensions
- JdbcDatabaseServiceProvider.java
- .handle
- Production Performance Tuning
- io.pebbletemplates.pebble.extension.Filter
- ProviderLoaderTest
- RecordingLogger
- Obj
- database
- java.util.regex.Pattern
- .render
- session
- session
- CommandRegistry
- TestConfig
- Cache
- errors
- json
- json
- Cache Module
- Response
- HTTP Undertow Module
- javelin
- cache
- HTTP Netty Module

## God Nodes (most connected - your core abstractions)
1. `Application` - 93 edges
2. `Request` - 87 edges
3. `Router` - 80 edges
4. `Response` - 74 edges
5. `CommandContext` - 68 edges
6. `Collection` - 63 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 54 edges
9. `Config` - 44 edges
10. `Handle` - 43 edges

## Surprising Connections (you probably didn't know these)
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Boot Flow` --references--> `Container`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/Container.java
- `Router` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java
- `Routes` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java
- `HttpServerAdapter` --references--> `HttpServerAdapter`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/HttpServerAdapter.java

## Import Cycles
- None detected.

## Communities (213 total, 94 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.12
Nodes (10): ConfigServiceProvider, ProviderSorter, ServiceProvider, ProviderSorterTest, CycleA, CycleB, FirstProvider, NeedsFirstProvider (+2 more)

### Community 4 - ".run"
Cohesion: 0.06
Nodes (11): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs, CommandGenerationTest, MigrationCommandTest, Config (+3 more)

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.04
Nodes (29): AbstractCommand, CliException, CommandContext, AiChatCommand, AiExplainCommand, AiGenerateModuleCommand, AiInstallCommand, BuildCommand (+21 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.14
Nodes (4): ConfigurableExceptionHandler, ErrorPageResolver, FileErrorPageResolver, RequestLoggingMiddleware

### Community 13 - "Application"
Cohesion: 0.06
Nodes (16): JavelinConsole, Application profile, HTTP driver selection, Javelin, JavelinStarterServiceProvider, Notes, Starter Module, ValidationRuleLoader (+8 more)

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.22
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - "Request"
Cohesion: 0.08
Nodes (5): Cookies and sessions, ExceptionHandler, Request, RequestAttributesTest, RequestInputTest

### Community 21 - "ServiceProvider"
Cohesion: 0.09
Nodes (16): Adding to the hardcoded provider list, Integrating with Starter, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Provider registration order, Registration order matters, The ServiceProvider Contract, ServiceProvider (+8 more)

### Community 22 - "MultipartData"
Cohesion: 0.07
Nodes (5): MultipartData, PartDisposition, UploadedFile, RequestMultipartTest, File

### Community 24 - "YamlConfig"
Cohesion: 0.22
Nodes (3): YamlConfig, TypeReference<>, TypeReference<>

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (38): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+30 more)

### Community 28 - "Core Module"
Cohesion: 0.09
Nodes (21): Config, Container, Container, Core Module, Database, DefaultExceptionHandler, Env, ExceptionHandler (+13 more)

### Community 30 - "AppProfile"
Cohesion: 0.29
Nodes (7): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".load"
Cohesion: 0.14
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.20
Nodes (10): Logging And Error Handling, Cookie-backed sessions, Notes, ExceptionHandlerServiceProvider, HttpAdapterConfig, HttpDriverConfig, JdkHttpServiceProvider, NettyHttpServiceProvider (+2 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.09
Nodes (10): Backward compatibility, Database Migration Module, Dialects, MigrationRunner, DatabaseManager, MigrationDialect, MigrationRunner, MigrationFile (+2 more)

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.13
Nodes (6): DefaultExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer, UndertowHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.08
Nodes (8): HomeController, JsonResponse, RedirectResponse, Response, ValidationErrors, ResponseTest, RateLimitMiddleware, Bucket

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 41 - "ApplicationTest"
Cohesion: 0.15
Nodes (6): ApplicationTest, BaseProvider, DependentProvider, FirstResource, RecordingProvider, SecondResource

### Community 42 - "TrustedProxyResolver"
Cohesion: 0.16
Nodes (4): ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolver, TrustedProxyResolverTest

### Community 44 - "app.yaml"
Cohesion: 0.20
Nodes (9): app, env, name, providers, security, max_request_bytes, server, host (+1 more)

### Community 49 - "Console Module"
Cohesion: 0.10
Nodes (17): AbstractCommand, CLI Commands, CliException, Command, CommandContext, CommandRegistry, Console Module, ConsoleKernel (cli package) (+9 more)

### Community 50 - "Javelin Framework"
Cohesion: 0.10
Nodes (21): Architecture at a glance, Boot Flow, Build And Test, Cache And Queues, CLI, Database, Documentation, Example Routes (+13 more)

### Community 51 - "docker-app.yaml"
Cohesion: 0.18
Nodes (10): app, env, name, javelin, starter, providers, security, max_request_bytes (+2 more)

### Community 52 - "Override"
Cohesion: 0.16
Nodes (4): CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder

### Community 53 - "NettyHttpServer"
Cohesion: 0.23
Nodes (3): NettyHttpServer, RequestHandler, ChannelInitializer<SocketChannel>

### Community 54 - "Config"
Cohesion: 0.11
Nodes (6): Config, DatabaseConfig, Connection, DatabaseConfigTest, ExceptionConfig, JacksonJsonConfig

### Community 55 - "Docker Compose"
Cohesion: 0.11
Nodes (18): Caddy alternative, Deployment, Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Graceful Shutdown (+10 more)

### Community 56 - ".handle"
Cohesion: 0.17
Nodes (3): Cookie, CookieAndSessionContractTest, SessionMiddlewareTest

### Community 57 - "Route"
Cohesion: 0.09
Nodes (18): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+10 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.22
Nodes (3): QueryTimingConfig, SqlTimer, SqlTimerTest

### Community 60 - ".adapterFor"
Cohesion: 0.15
Nodes (8): JdkHttpServiceProvider, Configuration, Configuration, HttpServerAdapter, HttpServerDriverProvider, HttpServerDriverProviderTest, DriverConfig, DriverEnv

### Community 61 - "JsonMapper"
Cohesion: 0.16
Nodes (8): Json, API, Backward compatibility, Configuration, JSON Jackson Module, Registration, JsonMapper, JacksonJsonServiceProvider

### Community 62 - "Override"
Cohesion: 0.21
Nodes (3): MigrationDialectsTest, RecordingDatabase, QueryBuilder

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
Cohesion: 0.11
Nodes (18): routes:list — Display registered routes, Example: Request timing middleware, Middleware reference (built-in), Service provider adds middleware to the router, Writing a Middleware Module, HTTP JDK Module, JdkHttpServer, Notes (+10 more)

### Community 73 - "Logger"
Cohesion: 0.25
Nodes (3): Log, Logger, StructuredLogger

### Community 74 - "caddy"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, caddy

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 76 - "Application Flow"
Cohesion: 0.11
Nodes (18): Application Flow, Boot Sequence, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development) (+10 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.24
Nodes (20): io.javelin:javelin-cache, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration, io.javelin:javelin-demo-app (+12 more)

### Community 78 - "ValkeyCache"
Cohesion: 0.19
Nodes (4): InMemoryCache, ValkeyCache, ValkeyCacheTest, User

### Community 79 - "Log"
Cohesion: 0.12
Nodes (9): Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger, Log, NoopLogger (+1 more)

### Community 81 - "Module Authoring"
Cohesion: 0.07
Nodes (26): Adding Tests, Adding the module to the parent POM, `bind(A, B)` — reusable factory, Binding Strategies, Checklist for new modules, Config-Driven Modules, Creating a New Interface, Creating your own facade (+18 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 86 - "Router"
Cohesion: 0.14
Nodes (4): Router, ResolvedRoute, HttpKernelTest, RouterTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - ".run"
Cohesion: 0.13
Nodes (8): AppServiceProvider, Main, Phase 2: Boot, Startup Time, Why startup is fast, Config-List Provider Loading, Application, Javelin

### Community 89 - "Json"
Cohesion: 0.11
Nodes (4): BackendController, UserController, web, Json

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 94 - "GenerateCommandSupport"
Cohesion: 0.19
Nodes (3): GenerateCommandSupport, FileGenerator, TemplateEngine

### Community 96 - "Middleware"
Cohesion: 0.08
Nodes (10): Layer 1 — Contracts, Bearer authentication and authorization, Middleware, Middleware, Authenticated, BearerAuthMiddleware, BearerTokenResolver, RequiresAuthenticationMiddleware (+2 more)

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 101 - "JavelinViewExtension"
Cohesion: 0.30
Nodes (3): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction

### Community 102 - "HtmlResponse"
Cohesion: 0.20
Nodes (5): Service provider, HtmlResponse, View, ViewRenderer, ViewTest

### Community 104 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 106 - "JsonLogEncoder"
Cohesion: 0.21
Nodes (4): JsonLogEncoder, RequestLoggingMiddleware, JsonLogEncoder, JsonLogEncoderTest

### Community 108 - ".make"
Cohesion: 0.09
Nodes (7): ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, JdbcDatabaseServiceProvider, NoopLogger, JdbcDatabaseServiceProviderTest, NatsQueueServiceProvider, NatsQueueServiceProviderTest

### Community 128 - ".status"
Cohesion: 0.11
Nodes (5): Next, DefaultExceptionHandlerTest, ValidationErrorsTest, StaticAssetResolverTest, ProfileWiringTest

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
Cohesion: 0.31
Nodes (3): SupportUtilitiesTest, Person, Profile

### Community 134 - "dev.mjs"
Cohesion: 0.25
Nodes (3): bin, env, watchers

### Community 135 - "Session"
Cohesion: 0.07
Nodes (7): Session, SessionStore, StubSession, InMemorySessionStore, MemorySession, SessionConfig, SessionMiddleware

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - "Job"
Cohesion: 0.13
Nodes (12): Queue contracts, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, Job, JobHandler (+4 more)

### Community 140 - "Javelin CLI"
Cohesion: 0.29
Nodes (6): AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image

### Community 141 - "services"
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 142 - "ModelTest"
Cohesion: 0.22
Nodes (6): ModelTest, SampleHydratedUser, SampleUser, Status, DRAFT, PUBLISHED

### Community 143 - "app"
Cohesion: 0.29
Nodes (7): build, ports, restart, context, dockerfile, services, app

### Community 144 - "nats"
Cohesion: 0.33
Nodes (6): command, image, ports, restart, volumes, nats

### Community 145 - "valkey"
Cohesion: 0.33
Nodes (6): valkey, command, image, ports, restart, volumes

### Community 146 - "Architecture"
Cohesion: 0.17
Nodes (12): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Middleware pipeline, Module Boundaries (+4 more)

### Community 147 - "PebbleViewRenderer"
Cohesion: 0.19
Nodes (8): Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Shared view data, View Pebble Module, PebbleViewRenderer, Options

### Community 150 - "com.fasterxml.jackson.databind.ObjectMapper"
Cohesion: 0.21
Nodes (3): DefaultJsonMapper, JacksonJsonMapper, JacksonJsonMapperTest

### Community 151 - "Full Worked Example"
Cohesion: 0.20
Nodes (10): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+2 more)

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 155 - "Database"
Cohesion: 0.14
Nodes (5): Core interfaces you can implement, Example: Implementing `Cache` (javelin-cache), Implementing a Core Interface, Database, QueryBuilder

### Community 156 - "Exception Module"
Cohesion: 0.40
Nodes (5): ConfigurableExceptionHandler, Configuration, Error pages, Exception Module, Service provider

### Community 157 - ".load"
Cohesion: 0.22
Nodes (4): DotenvEnv, Entry, DotenvEnvTest, YamlConfigTest

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

### Community 167 - ".modelUsesTableConventionAndPersistsThroughTheDatabase"
Cohesion: 0.18
Nodes (4): Model, Model instance methods, Model overridable methods, Model static methods

### Community 171 - "Cache"
Cohesion: 0.21
Nodes (3): Cache, InMemoryCache, Entry

### Community 175 - "queue"
Cohesion: 0.40
Nodes (5): queue, driver, jetstream, stream, url

### Community 177 - "Layer 2 — Adapters"
Cohesion: 0.40
Nodes (5): Layer 0 — Foundation, Layer 2 — Adapters, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Dependency Graph

### Community 179 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 180 - "ContainerTest"
Cohesion: 0.25
Nodes (6): ContainerTest, CircularA, CircularB, CloseableResource, Dependency, Service

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 183 - "Config Module"
Cohesion: 0.20
Nodes (7): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, YamlConfig

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 189 - "ProviderLoaderTest"
Cohesion: 0.28
Nodes (4): ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder

### Community 192 - "database"
Cohesion: 0.25
Nodes (8): database, connections, default, migrations_dir, query, log_all, slow_ms, timing

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 197 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 202 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 203 - "json"
Cohesion: 0.50
Nodes (4): json, fail_on_unknown_properties, indent_output, write_nulls

### Community 204 - "json"
Cohesion: 0.50
Nodes (4): json, fail_on_unknown_properties, indent_output, write_nulls

### Community 205 - "Cache Module"
Cohesion: 0.50
Nodes (4): Cache, Cache Module, CacheServiceProvider, Notes

### Community 206 - "Response"
Cohesion: 0.50
Nodes (4): HtmlResponse, JsonResponse, RedirectResponse, Response, Response instance methods, Response static factories

### Community 207 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

### Community 208 - "javelin"
Cohesion: 0.67
Nodes (3): javelin, starter, http-driver

### Community 209 - "cache"
Cohesion: 0.67
Nodes (3): cache, driver, url

### Community 210 - "HTTP Netty Module"
Cohesion: 0.67
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 784 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **94 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `.status`, `path`, `.run`, `cli/ConsoleKernel.java`, `ServiceProvider`, `NewCommand`, `Core Module`, `AppProfile`, `.load`, `io.javelin.core.ServiceProvider`, `ApplicationTest`, `map`, `Container`, `Config`, `.adapterFor`, `ProviderLoaderTest`, `.run`, `ConsoleOutput`, `.make`?**
  _High betweenness centrality (0.099) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.12012012012012012 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `Middleware`, `Agent Notes`, `io.javelin.core.ServiceProvider`, `ApplicationTest`, `Application`, `.adapterFor`, `Module Authoring`, `Javelin Framework`, `ServiceProvider`, `.run`, `Database`, `Core Module`, `ProviderLoaderTest`?**
  _High betweenness centrality (0.057) - this node is a cross-community bridge._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.10512820512820513 - nodes in this community are weakly interconnected._
- **Why does `Request` connect `Request` to `.status`, `Middleware`, `HTTP Common Module`, `NettyHttpServerIntegrationTest.java`, `JdkHttpServerIntegrationTest`, `Response`, `Session`, `io.javelin.core.Request`, `.handle`, `map`, `objects`, `Router`, `com.fasterxml.jackson.databind.ObjectMapper`, `MultipartData`, `Json`, `.handle`, `list`, `Route`?**
  _High betweenness centrality (0.042) - this node is a cross-community bridge._
- **Should `.run` be split into smaller, more focused modules?**
  _Cohesion score 0.06110102843315184 - nodes in this community are weakly interconnected._