# Graph Report - javelin-framework  (2026-11-26)

## Corpus Check
- 457 files · ~108,572 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 57 file(s) not represented in the graph (top: .stub 41, (none) 9, .xml 2)

## Summary
- 4605 nodes · 11614 edges · 261 communities (161 shown, 100 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1649 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `692b8c70`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- io.javelin.core.Request
- CapturingQueue
- StaticAssetResolver
- .from
- .handle
- Javelin.java
- Request
- DatabaseQueue
- Model
- CLI Reference
- Date
- Core Module
- ProviderRegistry
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
- XFileDisk
- NettyHttpServer.java
- Javelin Framework
- Console Module
- PasswordHashStrategy
- docker-app.yaml
- Schedule.java
- NettyHttpServerIntegrationTest
- Config
- Deployment
- Disk
- Route
- Override
- SqlTimer
- .adapterFor
- Json
- Override
- pebble
- docs/README.md
- Agent Notes
- .write
- ValidationRule
- path
- Validator
- JdbcDatabase
- JdbcQueryBuilder
- ValidationRule
- ServiceProvider
- caddy
- package.json
- Application Flow
- io.javelin:javelin-starter
- CaffeineCache
- Log
- FileQueue
- Module Authoring
- nginx
- Database Queue Module
- Str
- Application
- environment
- Docker Compose
- InMemoryQueue
- Database JDBC Module
- VersionCommand
- HtmlResponse
- JdkHttpServer.java
- locale
- Env
- ValkeyQueue
- AdultAgeRule
- Quick Start
- WorkspaceStarterRule
- DefaultEventDispatcher
- private
- Scheduler
- MapConfig
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- Config
- JsonLogEncoder
- SmtpTransport.java
- io.javelin.core.Application
- need_command
- ScheduledTask
- JavelinViewExtension
- Slf4jLogger
- io.javelin:javelin-parent
- Config
- cli/ConsoleKernel.java
- NatsQueue.java
- AdultAgeRule
- .handle
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
- MigrationRunner
- .commands
- services
- Files and caching
- app
- nats
- valkey
- Architecture
- PebbleViewRenderer
- ScheduleSpec
- QueueWorkCommand.java
- com.fasterxml.jackson.databind.ObjectMapper
- RetryPolicy
- MailComposer
- TestConfig
- Override
- Build a secure web app and API
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
- Router
- Authenticated
- queue
- .resolve
- .manager
- Middleware
- ValkeyCache
- YamlConfig
- primary
- log
- Cache
- io.javelin.core.Database
- .handle
- Production Performance Tuning
- QueryBuilder
- Queue Adapters
- DefaultValidator
- TestConfig
- database
- Obj
- Arr
- Override
- session
- io.javelin.core.queue.Job
- ExecutionRecord
- PebbleViewExtensions
- TestConfig
- security
- io.pebbletemplates.pebble.extension.Filter
- .handle
- Scheduler Module
- Cache Module
- security
- .execute
- cache
- ProviderLoaderTest
- RecordingLogger
- cache
- ContainerTest
- StubSession
- scheduler
- Logger
- scheduler
- CronExpression
- Config
- Provider Loader Module
- .load
- Cache
- WorkspaceAgeRule
- JdbcDatabaseServiceProvider.java
- Distributing providers
- .register
- NoopLogger
- MailManager
- .registerProviders
- .render
- InMemorySchedulerLock
- Config
- provider
- MigrateCommand
- NoopLogger
- tutorials/README.md
- CLI
- .run
- Full Worked Example
- .loadDirectory
- standardcharsets
- errors
- Mail Module
- MailMessage
- mail
- mail
- Config Module
- json
- TestMailTransport
- cors
- json
- CircularA
- Status
- Model
- HTTP Netty Module
- HTTP Undertow Module

## God Nodes (most connected - your core abstractions)
1. `Request` - 85 edges
2. `Application` - 85 edges
3. `Router` - 76 edges
4. `Response` - 72 edges
5. `Collection` - 63 edges
6. `CommandContext` - 62 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 52 edges
9. `JobQueue` - 45 edges
10. `Config` - 44 edges

## Surprising Connections (you probably didn't know these)
- `Service provider` --references--> `ProviderRegistryServiceProvider`  [INFERRED]
  docs/modules/provider-loader.md → modules/javelin-provider-loader/src/main/java/io/javelin/providers/ProviderRegistryServiceProvider.java
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Boot Flow` --references--> `Container`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/Container.java
- `make:middleware — Generate middleware` --references--> `Middleware`  [INFERRED]
  docs/cli-reference.md → modules/javelin-core/src/main/java/io/javelin/core/Middleware.java
- `1. Generate a provider` --references--> `ServiceProvider`  [INFERRED]
  docs/tutorials/provider-distribution.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java

## Import Cycles
- None detected.

## Communities (261 total, 100 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.18
Nodes (6): ProviderSorterTest, CycleB, FirstProvider, NeedsFirstProvider, SecondProvider, ThirdProvider

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.05
Nodes (17): AbstractCommand, CommandContext, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, CacheClearCommand, ConfigCacheCommand (+9 more)

### Community 9 - "Handle"
Cohesion: 0.05
Nodes (8): Ai, Handle, Message, Reply, File, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.05
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.09
Nodes (9): AutomationExampleController, BackendController, QueueExampleController, SecurityExampleController, StorageExampleController, UserRegisteredEvent, InvoiceJob, SendEmailJob (+1 more)

### Community 13 - "CapturingQueue"
Cohesion: 0.23
Nodes (3): QueueFacadeTest, CapturingQueue, SendEmailJob

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.22
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - ".handle"
Cohesion: 0.11
Nodes (4): Cookies and sessions, RequestAttributesTest, SessionAuthMiddlewareTest, StubSession

### Community 21 - "Javelin.java"
Cohesion: 0.05
Nodes (34): Adding to the hardcoded provider list, Integrating with Starter, Provider registration order, Registration order matters, ProviderLoader, ServiceProvider, JdkHttpServer, Static file serving (+26 more)

### Community 22 - "Request"
Cohesion: 0.07
Nodes (6): UserController, Request, MultipartData, PartDisposition, UploadedFile, RequestMultipartTest

### Community 25 - "Model"
Cohesion: 0.09
Nodes (6): Model overridable methods, Database, Model, ModelTest, SampleHydratedUser, SampleUser

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (42): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+34 more)

### Community 28 - "Core Module"
Cohesion: 0.09
Nodes (22): Config, Core Module, Database, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse (+14 more)

### Community 30 - "ProviderRegistry"
Cohesion: 0.06
Nodes (10): ProviderInstallCommand, JavelinVersion, ProviderConflict, ProviderManifest, ProviderRegistry, ProviderVersion, ProviderRegistryTest, BillingProvider (+2 more)

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.12
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 34 - "NettyHttpServerIntegrationTest.java"
Cohesion: 0.13
Nodes (3): RequiresPermissionMiddleware, SessionAuthMiddleware, SessionPrincipalResolver

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.07
Nodes (25): CORS, CSRF, Session authentication, Application profile, CaffeineCacheServiceProvider, ValkeyCacheServiceProvider, SecretsServiceProvider, DevProfileMiddleware (+17 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.15
Nodes (5): MigrationRunner, MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.19
Nodes (5): DefaultExceptionHandler, ExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.06
Nodes (7): HomeController, JsonResponse, RedirectResponse, Response, SecurityResponses, ValidationErrors, ResponseTest

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
Cohesion: 0.09
Nodes (21): app, env, name, cors, allow_credentials, allowed_origins, max_age_seconds, javelin (+13 more)

### Community 46 - "XFileDisk"
Cohesion: 0.07
Nodes (10): Configuration, File recorder, Service provider, Storage Module, InMemoryFileRecorder, StorageConfig, DiskConfig, StorageServiceProvider (+2 more)

### Community 47 - "NettyHttpServer.java"
Cohesion: 0.07
Nodes (4): ExceptionConfig, NettyHttpServer, RequestHandler, ChannelInitializer<SocketChannel>

### Community 48 - "Javelin Framework"
Cohesion: 0.10
Nodes (21): Architecture at a glance, Boot Flow, Build And Test, Cache And Queues, Database, Documentation, Example Routes, Features (+13 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.11
Nodes (6): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, PasswordHashStrategyTest

### Community 51 - "docker-app.yaml"
Cohesion: 0.10
Nodes (19): app, env, name, errors, pages_dir, redact, redact_keys, javelin (+11 more)

### Community 54 - "Config"
Cohesion: 0.11
Nodes (7): Service provider, CaffeineCacheConfig, Config, DatabaseConfig, Connection, DatabaseConfigTest, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.20
Nodes (10): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Logging And Error Handling, Migration Strategy, Performance Benchmarks, Reverse Proxy (+2 more)

### Community 56 - "Disk"
Cohesion: 0.09
Nodes (6): Disk, DownloadPolicy, StorageManager, StoredFile, Storage, StorageTest

### Community 57 - "Route"
Cohesion: 0.09
Nodes (14): Route, HttpMethod, DELETE, GET, HEAD, OPTIONS, PATCH, POST (+6 more)

### Community 58 - "Override"
Cohesion: 0.14
Nodes (4): TransactionCallback, MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "SqlTimer"
Cohesion: 0.27
Nodes (3): QueryTimingConfig, SqlTimer, SqlTimerTest

### Community 60 - ".adapterFor"
Cohesion: 0.13
Nodes (10): HTTP JDK Module, JdkHttpServiceProvider, Notes, Configuration, Configuration, HttpServerAdapter, HttpServerDriverProvider, HttpServerDriverProviderTest (+2 more)

### Community 61 - "Json"
Cohesion: 0.11
Nodes (9): Json, API, Backward compatibility, Configuration, JSON Jackson Module, Registration, Json, JsonMapper (+1 more)

### Community 62 - "Override"
Cohesion: 0.19
Nodes (3): MigrationDialectsTest, RecordingDatabase, QueryBuilder

### Community 63 - "pebble"
Cohesion: 0.12
Nodes (15): driver, pebble, allow_override_core_operators, auto_escaping, cache, default_escaping_strategy, default_locale, greedy_match_method (+7 more)

### Community 64 - "docs/README.md"
Cohesion: 0.15
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 65 - "Agent Notes"
Cohesion: 0.13
Nodes (14): Agent Notes, Architect Guidelines, Architecture Intent, Coding Principles, Current MVP Boundaries, Do, Do / Don’t, Don’t (+6 more)

### Community 66 - ".write"
Cohesion: 0.11
Nodes (4): GeneratorSupport, MakeControllerCommand, MakeModelCommand, MakeProviderCommand

### Community 73 - "ServiceProvider"
Cohesion: 0.14
Nodes (9): Checklist for new modules, Module type matrix, Pattern Reference, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Standard service provider template, The ServiceProvider Contract, ServiceProvider (+1 more)

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
Cohesion: 0.18
Nodes (32): io.javelin:javelin-cache, io.javelin:javelin-cache-caffeine, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration (+24 more)

### Community 78 - "CaffeineCache"
Cohesion: 0.16
Nodes (7): Cache Caffeine Module, Configuration, Developer API, CaffeineCache, Expiry<String, Entry>, Entry, CaffeineCacheTest

### Community 81 - "Module Authoring"
Cohesion: 0.07
Nodes (27): Adding Tests, Adding the module to the parent POM, `bind(A, B)` — reusable factory, Binding Strategies, Config-Driven Modules, Config-List Provider Loading, Creating a New Interface, Creating your own facade (+19 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 83 - "Database Queue Module"
Cohesion: 0.50
Nodes (3): Behavior, Configuration, Database Queue Module

### Community 86 - "Application"
Cohesion: 0.06
Nodes (14): JavelinConsole, Application Profile, Programmatic use, Selecting the profile, What changes, JavelinConsole, RouteListCommand, ServeCommand (+6 more)

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - "Docker Compose"
Cohesion: 0.22
Nodes (9): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running (+1 more)

### Community 89 - "InMemoryQueue"
Cohesion: 0.17
Nodes (3): ClaimedJob, QueueWorker, QueueWorkerConfig

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 92 - "HtmlResponse"
Cohesion: 0.23
Nodes (5): Service provider, HtmlResponse, View, ViewRenderer, ViewTest

### Community 93 - "JdkHttpServer.java"
Cohesion: 0.09
Nodes (4): HttpTimeoutConfig, JdkHttpServer, RequestRejected, UndertowHttpServer

### Community 94 - "locale"
Cohesion: 0.09
Nodes (6): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, MakeProviderCommand, FileGenerator, TemplateEngine

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "DefaultEventDispatcher"
Cohesion: 0.12
Nodes (8): Developer API, DefaultEventDispatcher, EventDispatcher, EventListener, Events, QueuedEvent, EventDispatcherTest, UserRegistered

### Community 101 - "private"
Cohesion: 0.12
Nodes (17): private, public, temporary, domain, driver, public, root, domain (+9 more)

### Community 102 - "Scheduler"
Cohesion: 0.16
Nodes (6): Scheduler, SchedulerConfig, SchedulerLock, TaskDefinition, SchedulerTest, SchedulerLock

### Community 106 - "JsonLogEncoder"
Cohesion: 0.21
Nodes (4): JsonLogEncoder, RequestLoggingMiddleware, JsonLogEncoder, JsonLogEncoderTest

### Community 107 - "SmtpTransport.java"
Cohesion: 0.12
Nodes (3): SmtpConfig, SmtpTransport, Authenticator

### Community 108 - "io.javelin.core.Application"
Cohesion: 0.06
Nodes (10): Developer API, 1. Configure, ValkeyCacheServiceProviderTest, JdbcDatabaseServiceProvider, JdbcDatabaseServiceProviderTest, EventServiceProvider, MailServiceProvider, NatsQueueServiceProviderTest (+2 more)

### Community 113 - "ScheduledTask"
Cohesion: 0.18
Nodes (3): DemoScheduleJob, ScheduledTask, Schedule

### Community 114 - "JavelinViewExtension"
Cohesion: 0.30
Nodes (3): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction

### Community 122 - "cli/ConsoleKernel.java"
Cohesion: 0.10
Nodes (4): CliException, AiGenerateModuleCommand, MakeServiceCommand, Names

### Community 125 - ".handle"
Cohesion: 0.15
Nodes (6): Cookie-backed sessions, Cookie, CookieAndSessionContractTest, SessionConfig, SessionMiddleware, SessionMiddlewareTest

### Community 128 - ".status"
Cohesion: 0.11
Nodes (5): DefaultExceptionHandlerTest, HttpKernelTest, ValidationErrorsTest, StaticAssetResolverTest, CorsAndCsrfTest

### Community 129 - "HTTP Common Module"
Cohesion: 0.29
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
Cohesion: 0.33
Nodes (3): SupportUtilitiesTest, Person, Profile

### Community 134 - "dev.mjs"
Cohesion: 0.25
Nodes (3): bin, env, watchers

### Community 135 - "Session"
Cohesion: 0.08
Nodes (5): Session, SessionStore, StubSession, InMemorySessionStore, MemorySession

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - "Job"
Cohesion: 0.10
Nodes (14): Queue contracts, Configuration, Developer API, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module (+6 more)

### Community 139 - "MigrationRunner"
Cohesion: 0.22
Nodes (3): MigrateCommand, MigrateRollbackCommand, MigrationRunner

### Community 140 - ".commands"
Cohesion: 0.08
Nodes (10): CommandRegistry, AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry (+2 more)

### Community 141 - "services"
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 142 - "Files and caching"
Cohesion: 0.33
Nodes (6): 1. Disks and cache are configured explicitly, 2. Write and read files, 4. S3 or MinIO, 5. Caching, Files and caching, Notes

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

### Community 148 - "ScheduleSpec"
Cohesion: 0.15
Nodes (6): Cron, Daily, Interval, Monthly, Weekly, ScheduleSpec

### Community 149 - "QueueWorkCommand.java"
Cohesion: 0.08
Nodes (7): MakeMiddlewareCommand, NewCommand, ProviderListCommand, QueueStatusCommand, QueueWorkCommand, ScheduleRunCommand, ServeCommand

### Community 150 - "com.fasterxml.jackson.databind.ObjectMapper"
Cohesion: 0.21
Nodes (3): DefaultJsonMapper, JacksonJsonMapper, JacksonJsonMapperTest

### Community 151 - "RetryPolicy"
Cohesion: 0.12
Nodes (10): Errors, Events Module, Queued listeners, Service provider, Contracts, RetryPolicy, JobHandlerRegistry, Registration (+2 more)

### Community 155 - "Build a secure web app and API"
Cohesion: 0.09
Nodes (13): Notes, 1. Enable the providers, 2. Build and start the demo, 4. Validation and error rendering, 5. JWT for the API, 6. CORS preflight, 7. Secrets, Build a secure web app and API (+5 more)

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

### Community 173 - "Router"
Cohesion: 0.14
Nodes (7): RouteHandler, Router, Routes, Routing, Router, ResolvedRoute, RouterTest

### Community 174 - "Authenticated"
Cohesion: 0.11
Nodes (9): Bearer authentication, Safety, 3. Private downloads, Authenticated, BearerAuthMiddleware, BearerTokenResolver, AuthorizationMiddlewareTest, BearerAuthMiddlewareTest (+1 more)

### Community 175 - "queue"
Cohesion: 0.10
Nodes (20): dir, queue, driver, file, jetstream, retry, stream, url (+12 more)

### Community 176 - ".resolve"
Cohesion: 0.16
Nodes (3): JwtTokenIssuer, JwtTokenResolver, JwtTokenResolverTest

### Community 178 - "Middleware"
Cohesion: 0.10
Nodes (13): Bearer authentication and authorization, Middleware, TrustedProxyResolver, Trusted proxy configuration, 3. Browser login with CSRF, What happens on a bad CSRF token, Middleware, Next (+5 more)

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 185 - "io.javelin.core.Database"
Cohesion: 0.12
Nodes (6): Backward compatibility, Database Migration Module, Dialects, DatabaseManager, MigrationDialect, DatabaseExecutionHistory

### Community 186 - ".handle"
Cohesion: 0.12
Nodes (6): ConfigurableExceptionHandler, ErrorPageResolver, FileErrorPageResolver, SensitiveDataRedactor, ConfigurableExceptionHandlerTest, SensitiveDataRedactorTest

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 188 - "QueryBuilder"
Cohesion: 0.09
Nodes (14): Layer 0 — Foundation, Layer 1 — Contracts, Layer 2 — Adapters, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Dependency Graph, Core interfaces you can implement, Example: Implementing `Cache` (javelin-cache) (+6 more)

### Community 189 - "Queue Adapters"
Cohesion: 0.14
Nodes (12): Choosing an adapter, File (`javelin-queue-file`), NATS JetStream (`javelin-queue-nats`), Queue Adapters, RabbitMQ (`javelin-queue-rabbitmq`), Valkey / Redis (`javelin-queue-valkey`), 1. Configure the queue, 2. Define and dispatch a job (+4 more)

### Community 190 - "DefaultValidator"
Cohesion: 0.20
Nodes (8): Relationship to `javelin-support`, Service provider, Supported rules, Validation Module, Validator, DefaultValidator, Validator, DefaultValidatorTest

### Community 191 - "TestConfig"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 192 - "database"
Cohesion: 0.25
Nodes (8): database, connections, default, migrations_dir, query, log_all, slow_ms, timing

### Community 195 - "Override"
Cohesion: 0.17
Nodes (3): MailTest, CapturingQueue, TestConfig

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 197 - "io.javelin.core.queue.Job"
Cohesion: 0.16
Nodes (3): CapturingQueue, JobQueue, Queue

### Community 198 - "ExecutionRecord"
Cohesion: 0.27
Nodes (3): ExecutionHistory, ExecutionRecord, InMemoryExecutionHistory

### Community 201 - "security"
Cohesion: 0.14
Nodes (14): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+6 more)

### Community 203 - ".handle"
Cohesion: 0.08
Nodes (5): RequestLoggingMiddleware, RequestLoggingMiddlewareTest, RecordingLogger, CorsConfig, CorsMiddleware

### Community 204 - "Scheduler Module"
Cohesion: 0.18
Nodes (10): Application, Container, Container, Worker, Configuration, Developer API, Dispatching to queues, Reliability (+2 more)

### Community 205 - "Cache Module"
Cohesion: 0.40
Nodes (5): Cache, Cache Module, CacheServiceProvider, InMemoryCache, Notes

### Community 206 - "security"
Cohesion: 0.15
Nodes (13): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+5 more)

### Community 208 - "cache"
Cohesion: 0.33
Nodes (6): cache, caffeine, driver, url, default_ttl_seconds, maximum_size

### Community 209 - "ProviderLoaderTest"
Cohesion: 0.32
Nodes (4): ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder

### Community 213 - "cache"
Cohesion: 0.40
Nodes (5): cache, caffeine, driver, default_ttl_seconds, maximum_size

### Community 214 - "ContainerTest"
Cohesion: 0.23
Nodes (4): ContainerTest, CloseableResource, Dependency, Service

### Community 216 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 217 - "Logger"
Cohesion: 0.18
Nodes (10): Log, Logger, Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger (+2 more)

### Community 218 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 221 - "Provider Loader Module"
Cohesion: 0.29
Nodes (6): Configuration, Diagnostics, Distribution, Manifest, Provider Loader Module, Service provider

### Community 222 - ".load"
Cohesion: 0.24
Nodes (5): Startup Time, Why startup is fast, DotenvEnv, Entry, DotenvEnvTest

### Community 226 - "Distributing providers"
Cohesion: 0.29
Nodes (6): 1. Generate a provider, 2. Enable or disable it, 3. Inspect and diagnose, 4. Install a versioned provider JAR, 5. CLI used by a distributed app, Distributing providers

### Community 229 - "MailManager"
Cohesion: 0.24
Nodes (3): MailManager, MailTransport, Mail

### Community 235 - "provider"
Cohesion: 0.40
Nodes (5): provider, directory, disabled, enabled, strict

### Community 238 - "tutorials/README.md"
Cohesion: 0.12
Nodes (11): 1. Compose and send, 2. Plain text, attachments and inline images, 3. Production SMTP, 4. Reliability and safety, Sending mail, Tutorials, 2. Schedule tasks, 3. Dispatch events (+3 more)

### Community 239 - "CLI"
Cohesion: 0.67
Nodes (3): CLI, First Run, Project Selection

### Community 241 - ".run"
Cohesion: 0.18
Nodes (4): AppServiceProvider, Main, Phase 2: Boot, Javelin

### Community 242 - "Full Worked Example"
Cohesion: 0.20
Nodes (10): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+2 more)

### Community 243 - ".loadDirectory"
Cohesion: 0.24
Nodes (3): EnvSecretProvider, TypeReference<>, TypeReference<>

### Community 245 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 246 - "Mail Module"
Cohesion: 0.20
Nodes (7): Address validation, Configuration, Logging, Mail Module, Queued delivery and failures, Test transport, MailSubmissionLogger

### Community 248 - "mail"
Cohesion: 0.25
Nodes (8): mail, driver, from, retry, test, backoff_ms, max_attempts, dir

### Community 249 - "mail"
Cohesion: 0.25
Nodes (8): mail, driver, from, retry, test, backoff_ms, max_attempts, dir

### Community 250 - "Config Module"
Cohesion: 0.20
Nodes (9): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, Secret providers, YamlConfig (+1 more)

### Community 251 - "json"
Cohesion: 0.50
Nodes (4): json, fail_on_unknown_properties, indent_output, write_nulls

### Community 253 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 254 - "json"
Cohesion: 0.50
Nodes (4): json, fail_on_unknown_properties, indent_output, write_nulls

### Community 256 - "Status"
Cohesion: 0.67
Nodes (3): Status, DRAFT, PUBLISHED

### Community 262 - "Model"
Cohesion: 0.40
Nodes (5): Model, Model instance methods, Model static methods, ValkeyCacheTest, User

### Community 263 - "HTTP Netty Module"
Cohesion: 0.33
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 264 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 1041 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **100 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `Override`, `cli/ConsoleKernel.java`, `QueueWorkCommand.java`, `Javelin.java`, `.instance`, `io.javelin.core.ServiceProvider`, `ApplicationTest`, `map`, `.adapterFor`, `TestConfig`, `path`, `ServiceProvider`, `Scheduler Module`, `ProviderLoaderTest`, `.register`, `.registerProviders`, `io.javelin.core.Application`, `standardcharsets`, `Config Module`, `.output`, `Container`?**
  _High betweenness centrality (0.053) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `optional` be split into smaller, more focused modules?**
  _Cohesion score 0.07188160676532769 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `ServiceProvider`, `Agent Notes`, `Distributing providers`, `io.javelin.core.ServiceProvider`, `.registerProviders`, `ApplicationTest`, `Scheduler Module`, `Core Module`, `.adapterFor`, `Javelin Framework`, `Module Authoring`, `ProviderLoaderTest`, `standardcharsets`, `Javelin.java`, `Application`, `Config Module`, `QueryBuilder`, `Provider Loader Module`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.13675213675213677 - nodes in this community are weakly interconnected._
- **Why does `Middleware` connect `Middleware` to `NettyHttpServerIntegrationTest.java`, `io.javelin.core.ServiceProvider`, `map`, `io.javelin.core.Application`, `Router`, `Authenticated`, `map`, `NettyHttpServerIntegrationTest`, `Request`, `Route`, `CLI Reference`, `QueryBuilder`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **Should `cli/ConsoleKernel.java` be split into smaller, more focused modules?**
  _Cohesion score 0.05069124423963134 - nodes in this community are weakly interconnected._