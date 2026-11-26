# Graph Report - javelin-framework  (2026-11-26)

## Corpus Check
- 395 files · ~98,380 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 52 file(s) not represented in the graph (top: .stub 37, (none) 9, .xml 2)

## Summary
- 4086 nodes · 10343 edges · 232 communities (133 shown, 99 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1478 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `0b3d9aa4`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
- objects
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- io.javelin.core.Request
- io.javelin.core.queue.Job
- StaticAssetResolver
- .from
- Authenticated
- Javelin.java
- Request
- DatabaseQueue
- Model
- CLI Reference
- Date
- Core Module
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
- app.yaml
- map
- StorageServiceProvider
- NettyHttpServer.java
- Javelin Framework
- Console Module
- PasswordHashStrategy
- docker-app.yaml
- Override
- NettyHttpServer
- Config
- Deployment
- Disk
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
- Validator
- JdbcDatabase
- JdbcQueryBuilder
- ValidationRule
- JacksonJsonServiceProvider.java
- caddy
- package.json
- Application Flow
- io.javelin:javelin-starter
- CaffeineCache
- Logger
- FileQueue
- Module Authoring
- nginx
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- Str
- Router
- environment
- .boot
- InMemoryQueue
- Database JDBC Module
- VersionCommand
- RecordingLogger
- JdkHttpServer
- locale
- Env
- ValkeyQueue
- AdultAgeRule
- Quick Start
- WorkspaceStarterRule
- StubSession
- private
- ViewRenderer
- MapConfig
- Build a secure web app and API
- Config
- JsonLogEncoder
- MigrateCommand
- io.javelin.core.Application
- need_command
- Config
- Slf4jLogger
- io.javelin:javelin-parent
- Config
- NatsQueue.java
- AdultAgeRule
- ValidationRuleRegistry
- .output
- Container
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
- .commands
- services
- DownloadPolicy
- app
- nats
- valkey
- Architecture
- PebbleViewExtensions
- MemorySession
- QueueWorkCommand.java
- com.fasterxml.jackson.databind.ObjectMapper
- UploadedFile
- XFileDisk
- TestConfig
- Javelin Docs
- Database
- Exception Module
- RabbitMqQueue
- primary
- worker
- build.sh script
- log
- database
- server
- ProjectLauncher
- MapConfig
- Cache
- private
- NettyHttpServerIntegrationTest
- JsonResponse
- queue
- .resolve
- .manager
- RateLimitMiddleware.java
- ValkeyCache
- YamlConfig
- primary
- log
- .typedCacheAccessConvertsJsonBackToTheRequestedType
- io.javelin.core.Database
- .handle
- Production Performance Tuning
- ModelTest
- Queue Adapters
- DefaultValidator
- TestConfig
- database
- Obj
- Arr
- Cookie
- session
- session
- InMemoryFileRecorder
- .commands
- TestConfig
- security
- File
- .handle
- MigrationRunner
- Cache Module
- security
- HTTP Undertow Module
- cache
- tutorials/README.md
- profile.md
- cache
- ContainerTest
- StubSession
- Full Worked Example
- StructuredLogger
- Security
- json
- StubSession
- ProxyAddressMatcher
- worker
- Cache
- Storage Module
- JdbcDatabaseServiceProvider.java
- private
- Module Dependency Graph
- NoopLogger
- cors
- DefaultJsonMapper
- cors

## God Nodes (most connected - your core abstractions)
1. `Application` - 88 edges
2. `Request` - 85 edges
3. `Router` - 76 edges
4. `Response` - 72 edges
5. `CommandContext` - 64 edges
6. `Collection` - 63 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 50 edges
9. `Config` - 44 edges
10. `JobQueue` - 43 edges

## Surprising Connections (you probably didn't know these)
- `Reliability` --references--> `JobQueue`  [INFERRED]
  docs/modules/queue.md → modules/javelin-queue/src/main/java/io/javelin/queue/JobQueue.java
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Boot Flow` --references--> `Container`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/Container.java
- `Notes` --references--> `ValkeyCacheServiceProvider`  [INFERRED]
  docs/modules/cache.md → modules/javelin-cache-valkey/src/main/java/io/javelin/cache/valkey/ValkeyCacheServiceProvider.java
- `TemplateEngine` --references--> `TemplateEngine`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/cli/generator/TemplateEngine.java

## Import Cycles
- None detected.

## Communities (232 total, 99 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.06
Nodes (19): JavelinConsole, ConfigServiceProvider, JavelinConsole, RouteListCommand, ServeCommand, Application, ProviderSorter, ServiceProvider (+11 more)

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.05
Nodes (18): AbstractCommand, CliException, CommandContext, AiChatCommand, AiGenerateModuleCommand, BuildCommand, ConfigCacheCommand, DevCommand (+10 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.06
Nodes (10): BackendController, QueueExampleController, SecurityExampleController, StorageExampleController, InvoiceJob, SendEmailJob, ErrorPageResolver, FileErrorPageResolver (+2 more)

### Community 13 - "io.javelin.core.queue.Job"
Cohesion: 0.13
Nodes (7): Contracts, JobQueue, ClaimedJob, Queue, QueueFacadeTest, CapturingQueue, SendEmailJob

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.22
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - "Authenticated"
Cohesion: 0.11
Nodes (10): Bearer authentication and authorization, Middleware, JWT adapter, Authenticated, BearerAuthMiddleware, BearerTokenResolver, RequiresAuthenticationMiddleware, RequestAttributesTest (+2 more)

### Community 21 - "Javelin.java"
Cohesion: 0.08
Nodes (18): routes:list — Display registered routes, Adding to the hardcoded provider list, Integrating with Starter, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Provider registration order, Registration order matters, The ServiceProvider Contract (+10 more)

### Community 22 - "Request"
Cohesion: 0.08
Nodes (5): UserController, Request, MultipartData, PartDisposition, RequestInputTest

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (38): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+30 more)

### Community 28 - "Core Module"
Cohesion: 0.10
Nodes (20): Application, Config, Container, Container, Core Module, DefaultExceptionHandler, Env, ExceptionHandler (+12 more)

### Community 30 - "AppProfile"
Cohesion: 0.16
Nodes (8): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, ChannelInitializer<SocketChannel>

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.09
Nodes (14): HTTP driver selection, Javelin, JavelinStarterServiceProvider, Notes, Starter Module, ValidationRuleLoader, WorkspaceDefaults, JavelinStarterServiceProvider (+6 more)

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.06
Nodes (29): Bearer authentication, CORS, CSRF, HSTS and CSP, PasswordHasher, RateLimitMiddleware, RequestSizeLimitMiddleware, SecureHeadersMiddleware (+21 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.17
Nodes (5): MigrationRunner, MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.16
Nodes (4): DefaultExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.08
Nodes (6): Middleware, Next, RedirectResponse, Response, SecurityResponses, RequestSizeLimitMiddleware

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 41 - "ApplicationTest"
Cohesion: 0.15
Nodes (6): ApplicationTest, BaseProvider, DependentProvider, FirstResource, RecordingProvider, SecondResource

### Community 44 - "app.yaml"
Cohesion: 0.12
Nodes (16): app, env, name, errors, pages_dir, redact, redact_keys, javelin (+8 more)

### Community 46 - "StorageServiceProvider"
Cohesion: 0.21
Nodes (3): StorageConfig, DiskConfig, StorageServiceProvider

### Community 48 - "Javelin Framework"
Cohesion: 0.09
Nodes (23): Architecture at a glance, Boot Flow, Build And Test, Cache And Queues, CLI, Database, Documentation, Example Routes (+15 more)

### Community 49 - "Console Module"
Cohesion: 0.10
Nodes (17): AbstractCommand, CLI Commands, CliException, Command, CommandContext, CommandRegistry, Console Module, ConsoleKernel (cli package) (+9 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.10
Nodes (6): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, PasswordHashStrategyTest

### Community 51 - "docker-app.yaml"
Cohesion: 0.11
Nodes (17): app, env, name, errors, pages_dir, redact, redact_keys, javelin (+9 more)

### Community 52 - "Override"
Cohesion: 0.25
Nodes (3): QueryBuilder, RecordingDatabase, RecordingQueryBuilder

### Community 54 - "Config"
Cohesion: 0.09
Nodes (7): Service provider, CaffeineCacheConfig, Config, DatabaseConfig, Connection, DatabaseConfigTest, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.11
Nodes (19): Caddy alternative, Deployment, Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Graceful Shutdown (+11 more)

### Community 56 - "Disk"
Cohesion: 0.11
Nodes (5): Disk, DownloadPolicy, StorageManager, StoredFile, Storage

### Community 57 - "Route"
Cohesion: 0.08
Nodes (21): File Uploads, Request, Route, RouteHandler, Router, Routes, Routing, UploadedFile (+13 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.15
Nodes (4): QueryTimingConfig, SqlTimer, SqlTimerTest, RecordingLogger

### Community 60 - ".adapterFor"
Cohesion: 0.12
Nodes (13): HTTP JDK Module, JdkHttpServer, JdkHttpServiceProvider, Notes, Static file serving, Trusted proxy resolution, Configuration, Configuration (+5 more)

### Community 61 - "JsonMapper"
Cohesion: 0.20
Nodes (7): Json, API, Backward compatibility, Configuration, JSON Jackson Module, Registration, JsonMapper

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
Cohesion: 0.11
Nodes (4): GeneratorSupport, MakeControllerCommand, MakeModelCommand, MakeProviderCommand

### Community 72 - "ValidationRule"
Cohesion: 0.15
Nodes (3): AbstractWorkspaceRule, WorkspaceAgeRule, ValidationRule

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
Cohesion: 0.19
Nodes (28): io.javelin:javelin-cache, io.javelin:javelin-cache-caffeine, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration (+20 more)

### Community 78 - "CaffeineCache"
Cohesion: 0.16
Nodes (7): Cache Caffeine Module, Configuration, Developer API, CaffeineCache, Expiry<String, Entry>, Entry, CaffeineCacheTest

### Community 79 - "Logger"
Cohesion: 0.12
Nodes (9): Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger, Log, NoopLogger (+1 more)

### Community 81 - "Module Authoring"
Cohesion: 0.06
Nodes (30): Adding Tests, Adding the module to the parent POM, `bind(A, B)` — reusable factory, Binding Strategies, Checklist for new modules, Config-Driven Modules, Creating a New Interface, Creating your own facade (+22 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 86 - "Router"
Cohesion: 0.17
Nodes (3): Router, ResolvedRoute, RouterTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - ".boot"
Cohesion: 0.14
Nodes (7): AppServiceProvider, Main, Phase 2: Boot, Startup Time, Why startup is fast, Config-List Provider Loading, Javelin

### Community 89 - "InMemoryQueue"
Cohesion: 0.09
Nodes (6): JobHandlerRegistry, Registration, QueueWorker, QueueWorkerConfig, QueueWorkerTest, InMemoryQueue

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 93 - "JdkHttpServer"
Cohesion: 0.11
Nodes (5): ExceptionHandler, HttpTimeoutConfig, JdkHttpServer, RequestRejected, UndertowHttpServer

### Community 94 - "locale"
Cohesion: 0.10
Nodes (6): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, FileGenerator, Names, TemplateEngine

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 101 - "private"
Cohesion: 0.17
Nodes (12): public, temporary, domain, driver, max_bytes, root, storage, default (+4 more)

### Community 102 - "ViewRenderer"
Cohesion: 0.13
Nodes (7): HomeController, View, ViewRenderer, HtmlResponse, View, ViewRenderer, ViewTest

### Community 104 - "Build a secure web app and API"
Cohesion: 0.25
Nodes (8): 1. Enable the providers, 2. Build and start the demo, 3. Browser login with CSRF, 5. JWT for the API, 6. CORS preflight, Build a secure web app and API, What happens on a bad CSRF token, Where to go next

### Community 106 - "JsonLogEncoder"
Cohesion: 0.21
Nodes (4): JsonLogEncoder, RequestLoggingMiddleware, JsonLogEncoder, JsonLogEncoderTest

### Community 108 - "io.javelin.core.Application"
Cohesion: 0.06
Nodes (8): web, ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, JdbcDatabaseServiceProvider, NatsQueueServiceProvider, NatsQueueServiceProviderTest, QueueServiceProvider, JwtAuthServiceProvider

### Community 128 - ".status"
Cohesion: 0.08
Nodes (9): DefaultExceptionHandlerTest, HttpKernelTest, ResponseTest, BearerAuthMiddlewareTest, ValidationErrorsTest, HttpTimeoutConfigTest, StaticAssetResolverTest, CorsAndCsrfTest (+1 more)

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
Cohesion: 0.17
Nodes (4): Html, SupportUtilitiesTest, Person, Profile

### Community 134 - "dev.mjs"
Cohesion: 0.25
Nodes (3): bin, env, watchers

### Community 135 - "Session"
Cohesion: 0.09
Nodes (8): Cookies and sessions, Cookie-backed sessions, Session, SessionStore, CsrfMiddleware, SessionConfig, SessionMiddleware, SessionMiddlewareTest

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - "Job"
Cohesion: 0.10
Nodes (14): Queue contracts, Configuration, Developer API, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module (+6 more)

### Community 140 - ".commands"
Cohesion: 0.08
Nodes (11): AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry, AiExplainCommand (+3 more)

### Community 141 - "services"
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 142 - "DownloadPolicy"
Cohesion: 0.20
Nodes (8): 1. Disks and cache are configured explicitly, 2. Write and read files, 3. Private downloads, 4. S3 or MinIO, 5. Caching, Files and caching, Notes, AuthenticatedDownloadPolicy

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

### Community 147 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (16): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Service provider (+8 more)

### Community 149 - "QueueWorkCommand.java"
Cohesion: 0.18
Nodes (4): NewCommand, QueueStatusCommand, QueueWorkCommand, ServeCommand

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 155 - "Database"
Cohesion: 0.08
Nodes (13): Layer 2 — Adapters, Core interfaces you can implement, Example: Implementing `Cache` (javelin-cache), Implementing a Core Interface, Database, QueryBuilder, TransactionCallback, Behavior (+5 more)

### Community 156 - "Exception Module"
Cohesion: 0.40
Nodes (5): ConfigurableExceptionHandler, Configuration, Error pages, Exception Module, Service provider

### Community 159 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 160 - "worker"
Cohesion: 0.13
Nodes (15): dir, queue, driver, file, retry, worker, backoff, initial_ms (+7 more)

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

### Community 167 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 172 - "private"
Cohesion: 0.12
Nodes (17): private, public, temporary, domain, driver, public, root, domain (+9 more)

### Community 174 - "JsonResponse"
Cohesion: 0.19
Nodes (3): 4. Validation and error rendering, JsonResponse, ValidationErrors

### Community 175 - "queue"
Cohesion: 0.15
Nodes (13): dir, queue, driver, file, jetstream, retry, stream, url (+5 more)

### Community 176 - ".resolve"
Cohesion: 0.16
Nodes (3): JwtTokenIssuer, JwtTokenResolver, JwtTokenResolverTest

### Community 178 - "RateLimitMiddleware.java"
Cohesion: 0.19
Nodes (4): TrustedProxyResolver, Trusted proxy configuration, RateLimitMiddleware, Bucket

### Community 179 - "ValkeyCache"
Cohesion: 0.27
Nodes (3): InMemoryCache, Cache, ValkeyCache

### Community 180 - "YamlConfig"
Cohesion: 0.05
Nodes (22): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, Secret providers, YamlConfig (+14 more)

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 183 - ".typedCacheAccessConvertsJsonBackToTheRequestedType"
Cohesion: 0.19
Nodes (6): Model, Model instance methods, Model static methods, ValkeyCacheTest, Cache, User

### Community 185 - "io.javelin.core.Database"
Cohesion: 0.15
Nodes (6): Backward compatibility, Database Migration Module, Dialects, DatabaseManager, JdbcDatabaseServiceProviderTest, MigrationDialect

### Community 186 - ".handle"
Cohesion: 0.17
Nodes (4): ConfigurableExceptionHandler, SensitiveDataRedactor, ConfigurableExceptionHandlerTest, SensitiveDataRedactorTest

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 188 - "ModelTest"
Cohesion: 0.12
Nodes (7): ModelTest, CastedArticle, EditableUser, SampleHydratedUser, Status, DRAFT, PUBLISHED

### Community 189 - "Queue Adapters"
Cohesion: 0.14
Nodes (12): Choosing an adapter, File (`javelin-queue-file`), NATS JetStream (`javelin-queue-nats`), Queue Adapters, RabbitMQ (`javelin-queue-rabbitmq`), Valkey / Redis (`javelin-queue-valkey`), 1. Configure the queue, 2. Define and dispatch a job (+4 more)

### Community 190 - "DefaultValidator"
Cohesion: 0.39
Nodes (3): Service provider, DefaultValidator, DefaultValidatorTest

### Community 191 - "TestConfig"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 192 - "database"
Cohesion: 0.25
Nodes (8): database, connections, default, migrations_dir, query, log_all, slow_ms, timing

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 197 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 201 - "security"
Cohesion: 0.14
Nodes (14): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+6 more)

### Community 204 - "MigrationRunner"
Cohesion: 0.22
Nodes (3): MigrateCommand, MigrateRollbackCommand, MigrationRunner

### Community 205 - "Cache Module"
Cohesion: 0.50
Nodes (4): Cache, Cache Module, CacheServiceProvider, Notes

### Community 206 - "security"
Cohesion: 0.15
Nodes (13): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+5 more)

### Community 207 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

### Community 208 - "cache"
Cohesion: 0.33
Nodes (6): cache, caffeine, driver, url, default_ttl_seconds, maximum_size

### Community 209 - "tutorials/README.md"
Cohesion: 0.20
Nodes (6): Relationship to `javelin-support`, Supported rules, Validation Module, Validator, Tutorials, Validator

### Community 210 - "profile.md"
Cohesion: 0.33
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 213 - "cache"
Cohesion: 0.40
Nodes (5): cache, caffeine, driver, default_ttl_seconds, maximum_size

### Community 214 - "ContainerTest"
Cohesion: 0.25
Nodes (6): ContainerTest, CircularA, CircularB, CloseableResource, Dependency, Service

### Community 216 - "Full Worked Example"
Cohesion: 0.20
Nodes (10): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+2 more)

### Community 217 - "StructuredLogger"
Cohesion: 0.17
Nodes (4): Log, Logger, StructuredLogger, RequestLoggingMiddleware

### Community 219 - "json"
Cohesion: 0.50
Nodes (4): json, fail_on_unknown_properties, indent_output, write_nulls

### Community 222 - "worker"
Cohesion: 0.29
Nodes (7): worker, concurrency, enabled, lease_seconds, poll_interval_ms, shutdown_seconds, timeout_seconds

### Community 224 - "Storage Module"
Cohesion: 0.29
Nodes (5): Configuration, Developer API, File recorder, Service provider, Storage Module

### Community 226 - "private"
Cohesion: 0.40
Nodes (5): private, domain, driver, public, root

### Community 227 - "Module Dependency Graph"
Cohesion: 0.40
Nodes (5): Layer 0 — Foundation, Layer 1 — Contracts, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Dependency Graph

### Community 229 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 232 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 936 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **99 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `ServiceProvider` to `.instance`, `io.javelin.core.ServiceProvider`, `Override`, `cli/ConsoleKernel.java`, `ApplicationTest`, `ProfileConfig`, `.commands`, `map`, `io.javelin.core.Application`, `.adapterFor`, `AppProfile`, `QueueWorkCommand.java`, `Javelin.java`, `Config`, `Core Module`, `.output`, `Container`?**
  _High betweenness centrality (0.048) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.0642243328810493 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `.instance`, `Agent Notes`, `Module Dependency Graph`, `io.javelin.core.ServiceProvider`, `ApplicationTest`, `.adapterFor`, `Javelin Framework`, `Module Authoring`, `Javelin.java`, `Database`, `Core Module`?**
  _High betweenness centrality (0.041) - this node is a cross-community bridge._
- **Should `objects` be split into smaller, more focused modules?**
  _Cohesion score 0.09988385598141696 - nodes in this community are weakly interconnected._
- **Why does `Collection` connect `Collection` to `arraylist`, `SupportUtilitiesTest`?**
  _High betweenness centrality (0.038) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.13675213675213677 - nodes in this community are weakly interconnected._