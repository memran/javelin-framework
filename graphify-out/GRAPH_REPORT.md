# Graph Report - javelin-framework  (2026-11-27)

## Corpus Check
- 494 files · ~116,347 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 57 file(s) not represented in the graph (top: .stub 41, (none) 9, .xml 2)

## Summary
- 4925 nodes · 12362 edges · 286 communities (168 shown, 118 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1792 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `d6432718`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Override
- arraylist
- Override
- cli/ConsoleKernel.java
- CommandContext
- Collection
- io.javelin.core.Request
- .text
- StaticAssetResolver
- .from
- .handle
- Javelin.java
- Middleware
- DatabaseQueue
- Model
- CLI Reference
- Date
- Core Module
- ProviderRegistry
- .imageSupportDetectsAndReadsImageMetadata
- .instance
- Validation
- io.javelin.core.ServiceProvider
- MigrationRunner
- Build a secure web app and API
- JdkHttpServerIntegrationTest
- HtmlResponse
- Support Module
- NettyHttpServer
- Handle
- Input
- app.yaml
- StorageServiceProvider
- Container
- Console Module
- PasswordHashStrategy
- docker-app.yaml
- with
- Javelin Framework
- Config
- Deployment
- Disk
- Route
- Override
- RecordingLogger
- HttpServerAdapter
- Json
- Override
- pebble
- Agent Notes
- .write
- ValidationRule
- java.util.regex.Pattern
- Validator
- JdbcDatabase
- JdbcQueryBuilder
- ValidationRule
- with
- caddy
- package.json
- Application Flow
- io.javelin:javelin-starter
- CaffeineCache
- Logger
- FileQueue
- Module Authoring
- nginx
- objects
- io.javelin.core.queue.Job
- Str
- environment
- Docker Compose
- InMemoryQueue
- Database JDBC Module
- VersionCommand
- DownloadPolicy
- .stringify
- locale
- Env
- Application
- AdultAgeRule
- Quick Start
- WorkspaceStarterRule
- DefaultEventDispatcher
- private
- Scheduler
- MapConfig
- .resolve
- Config
- JsonLogEncoder
- UndertowHttpServerIntegrationTest
- io.javelin.core.Application
- need_command
- ScheduledTask
- .sustainsLoopbackThroughputThroughTheJdkAdapter
- Slf4jLogger
- io.javelin:javelin-parent
- Config
- XFileDisk
- NatsQueue.java
- AdultAgeRule
- Session
- .output
- Json
- org.junit.jupiter.api.Test
- Tracer
- Installation Guide
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- .has
- depends_on
- healthcheck
- .of
- NettyHttpServer
- org.junit.jupiter.api.Test
- services
- MultipartData
- app
- nats
- valkey
- Architecture
- PebbleViewExtensions
- ScheduleSpec
- ViewRenderer
- web.java
- instant
- MailComposer
- TestConfig
- CapturingQueue
- Security
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
- Request
- queue
- .resolve
- .manager
- ContainerTest
- ValkeyCache
- .load
- primary
- log
- Cache
- io.javelin.core.Database
- .handle
- Production Performance Tuning
- QueryBuilder
- tutorials/README.md
- DefaultValidator
- HttpServerDriverProvider.java
- database
- Obj
- Arr
- Override
- session
- ApplicationTest
- ExecutionRecord
- TestConfig
- TestConfig
- security
- HealthRegistry
- .text
- Scheduler Module
- ConsoleKernel
- security
- CliException
- cache
- InMemoryFileRecorder
- cors
- cache
- StubSession
- scheduler
- StructuredLogger
- scheduler
- CronExpression
- .register
- .handle
- UploadedFile
- Cache
- .middlewareServesHealthReadyAndMetrics
- JdbcDatabaseServiceProvider
- io.javelin.core.Response
- .login
- File
- MailManager
- SmtpTransport.java
- YamlConfig
- MetricsRegistry
- InMemorySchedulerLock
- DatabaseExecutionHistory
- provider
- MigrateCommand
- .run
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- Config Module
- Scheduling and events
- RecordingLogger
- .handle
- LimitedInputStream
- errors
- MailMessage
- Production deployment
- mail
- mail
- .register
- Cache Module
- Attachment
- MailSubmissionLogger
- observability
- .loadDirectory
- CircularA
- session
- Observability Module
- ProviderLoaderTest
- ProfileConfig
- provider
- HTTP Netty Module
- CaffeineCacheServiceProvider
- DemoScheduleJob
- Distributing providers
- cors
- errors
- JdkHttpServer
- .validate
- NoopTracer
- Javelin Docs
- .typedCacheAccessConvertsJsonBackToTheRequestedType
- HTTP Common Module
- Reliable background jobs
- Config
- TestConfig
- HTTP Request Lifecycle
- SchedulerLock
- NoopLogger
- MakeControllerCommand
- MakeModelCommand
- FileErrorPageResolver

## God Nodes (most connected - your core abstractions)
1. `Response` - 90 edges
2. `Application` - 85 edges
3. `Request` - 81 edges
4. `Collection` - 63 edges
5. `CommandContext` - 60 edges
6. `Router` - 53 edges
7. `ServiceProvider` - 52 edges
8. `Config` - 44 edges
9. `Handle` - 43 edges
10. `JobQueue` - 43 edges

## Surprising Connections (you probably didn't know these)
- `Router` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java
- `Routes` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java
- `Notes` --references--> `ServiceProvider`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `RequestLoggingMiddleware` --references--> `JsonLogEncoder`  [INFERRED]
  docs/modules/log-slf4j.md → modules/javelin-log-slf4j/src/main/java/io/javelin/log/slf4j/JsonLogEncoder.java
- `Notes` --references--> `ValkeyCacheServiceProvider`  [INFERRED]
  docs/modules/cache.md → modules/javelin-cache-valkey/src/main/java/io/javelin/cache/valkey/ValkeyCacheServiceProvider.java

## Import Cycles
- None detected.

## Communities (286 total, 118 thin omitted)

### Community 0 - "Override"
Cohesion: 0.12
Nodes (10): ConfigServiceProvider, ProviderSorter, ServiceProvider, ProviderSorterTest, CycleA, CycleB, FirstProvider, NeedsFirstProvider (+2 more)

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.06
Nodes (11): BenchCommand, DeployNativeConfigCommand, DeployServiceCommand, DeployValidateCommand, DoctorCommand, NewCommand, QueueStatusCommand, QueueWorkCommand (+3 more)

### Community 9 - "CommandContext"
Cohesion: 0.06
Nodes (15): AbstractCommand, CommandContext, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, DevCommand, MigrateCommand (+7 more)

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.15
Nodes (7): AutomationExampleController, BackendController, QueueExampleController, StorageExampleController, UserRegisteredEvent, InvoiceJob, SendEmailJob

### Community 13 - ".text"
Cohesion: 0.11
Nodes (9): Router, ResolvedRoute, RouterTest, CorePerformanceRegressionTest, DeepService, FactoryService, LeafService, MiddleService (+1 more)

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.17
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - ".handle"
Cohesion: 0.07
Nodes (11): File Uploads, Request, UploadedFile, RequiresAuthenticationMiddleware, CookieAndSessionContractTest, RequestAttributesTest, AuthorizationMiddlewareTest, CsrfMiddleware (+3 more)

### Community 21 - "Javelin.java"
Cohesion: 0.10
Nodes (20): Adding to the hardcoded provider list, Integrating with Starter, Provider registration order, Registration order matters, Default Service Providers, Javelin, JavelinStarterServiceProvider, Notes (+12 more)

### Community 22 - "Middleware"
Cohesion: 0.08
Nodes (10): make:middleware — Generate middleware, Bearer authentication and authorization, Middleware, Middleware, Next, RequiresPermissionMiddleware, SecurityResponses, RateLimitMiddleware (+2 more)

### Community 24 - "DatabaseQueue"
Cohesion: 0.14
Nodes (5): Database, QueryBuilder, TransactionCallback, DatabaseQueue, DatabaseQueueTest

### Community 25 - "Model"
Cohesion: 0.08
Nodes (10): Core interfaces you can implement, Model, Model instance methods, Model overridable methods, Behavior, Configuration, Database Queue Module, Database (+2 more)

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (41): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+33 more)

### Community 28 - "Core Module"
Cohesion: 0.10
Nodes (18): Config, Core Module, DefaultExceptionHandler, Env, ExceptionHandler, HttpKernel, HttpMethod, Notes (+10 more)

### Community 30 - "ProviderRegistry"
Cohesion: 0.06
Nodes (11): ProviderInstallCommand, ProviderListCommand, JavelinVersion, ProviderConflict, ProviderManifest, ProviderRegistry, ProviderVersion, ProviderRegistryTest (+3 more)

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.15
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.07
Nodes (17): CSRF, Session authentication, SecretsServiceProvider, ExceptionHandlerServiceProvider, HttpAdapterConfig, HttpDriverConfig, UndertowHttpServiceProvider, ProviderRegistryServiceProvider (+9 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.11
Nodes (9): Backward compatibility, Database Migration Module, Dialects, MigrationRunner, MigrationDialect, MigrationRunner, MigrationFile, MigrationRecord (+1 more)

### Community 37 - "Build a secure web app and API"
Cohesion: 0.09
Nodes (13): Notes, 1. Enable the providers, 2. Build and start the demo, 4. Validation and error rendering, 5. JWT for the API, 6. CORS preflight, 7. Secrets, Build a secure web app and API (+5 more)

### Community 39 - "HtmlResponse"
Cohesion: 0.12
Nodes (5): HtmlResponse, JsonResponse, ValidationErrors, ValidationErrorsTest, ViewTest

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 41 - "NettyHttpServer"
Cohesion: 0.19
Nodes (3): NettyHttpServer, RequestHandler, ChannelInitializer<SocketChannel>

### Community 42 - "Handle"
Cohesion: 0.06
Nodes (9): 3. Browser login with CSRF, What happens on a bad CSRF token, Ai, Handle, Message, Reply, Http, Handle (+1 more)

### Community 44 - "app.yaml"
Cohesion: 0.12
Nodes (16): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+8 more)

### Community 46 - "StorageServiceProvider"
Cohesion: 0.21
Nodes (3): StorageConfig, DiskConfig, StorageServiceProvider

### Community 48 - "Container"
Cohesion: 0.17
Nodes (3): Container, Binding, InstanceService

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.11
Nodes (6): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, PasswordHashStrategyTest

### Community 51 - "docker-app.yaml"
Cohesion: 0.10
Nodes (19): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+11 more)

### Community 52 - "with"
Cohesion: 0.09
Nodes (21): jobs, verify, name, on, pull_request, push, branches, branches (+13 more)

### Community 53 - "Javelin Framework"
Cohesion: 0.09
Nodes (23): Architecture at a glance, Boot Flow, Build And Test, Cache And Queues, CLI, Database, Documentation, Example Routes (+15 more)

### Community 54 - "Config"
Cohesion: 0.11
Nodes (6): Config, DatabaseConfig, Connection, DatabaseConfigTest, ExceptionConfig, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.20
Nodes (10): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Logging And Error Handling, Migration Strategy, Performance Benchmarks, Reverse Proxy (+2 more)

### Community 56 - "Disk"
Cohesion: 0.09
Nodes (9): Configuration, File recorder, Service provider, Storage Module, Disk, StorageManager, StoredFile, Storage (+1 more)

### Community 57 - "Route"
Cohesion: 0.08
Nodes (18): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+10 more)

### Community 58 - "Override"
Cohesion: 0.13
Nodes (4): TransactionCallback, MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.25
Nodes (3): QueryTimingConfig, SqlTimer, SqlTimerTest

### Community 60 - "HttpServerAdapter"
Cohesion: 0.09
Nodes (16): HttpServerAdapter, HTTP JDK Module, JdkHttpServer, JdkHttpServiceProvider, Notes, Static file serving, Trusted proxy resolution, Behavior (+8 more)

### Community 61 - "Json"
Cohesion: 0.23
Nodes (7): Json, API, Backward compatibility, Configuration, JSON Jackson Module, Registration, JsonMapper

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

### Community 72 - "ValidationRule"
Cohesion: 0.15
Nodes (3): AbstractWorkspaceRule, WorkspaceAgeRule, ValidationRule

### Community 73 - "with"
Cohesion: 0.10
Nodes (19): jobs, release, name, on, push, contents, tags, permissions (+11 more)

### Community 74 - "caddy"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, caddy

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 76 - "Application Flow"
Cohesion: 0.12
Nodes (16): Application Flow, Boot Sequence, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Dev Command (Development), Generator Commands (+8 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.17
Nodes (34): io.javelin:javelin-cache, io.javelin:javelin-cache-caffeine, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration (+26 more)

### Community 78 - "CaffeineCache"
Cohesion: 0.16
Nodes (7): Cache Caffeine Module, Configuration, Developer API, CaffeineCache, Expiry<String, Entry>, Entry, CaffeineCacheTest

### Community 79 - "Logger"
Cohesion: 0.15
Nodes (9): Logging SLF4J Module, LoggingConfig, Notes, RequestLoggingMiddleware, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger, Log (+1 more)

### Community 81 - "Module Authoring"
Cohesion: 0.04
Nodes (46): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+38 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 84 - "io.javelin.core.queue.Job"
Cohesion: 0.13
Nodes (6): JobQueue, ClaimedJob, Queue, QueueFacadeTest, CapturingQueue, SendEmailJob

### Community 87 - "environment"
Cohesion: 0.14
Nodes (14): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+6 more)

### Community 88 - "Docker Compose"
Cohesion: 0.17
Nodes (10): AppServiceProvider, Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration (+2 more)

### Community 89 - "InMemoryQueue"
Cohesion: 0.11
Nodes (6): JobHandlerRegistry, Registration, QueueWorker, QueueWorkerConfig, QueueWorkerTest, InMemoryQueue

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 92 - "DownloadPolicy"
Cohesion: 0.17
Nodes (10): Safety, 1. Disks and cache are configured explicitly, 2. Write and read files, 3. Private downloads, 4. S3 or MinIO, 5. Caching, Files and caching, Notes (+2 more)

### Community 94 - "locale"
Cohesion: 0.06
Nodes (10): AiGenerateModuleCommand, GenerateCommandSupport, MakeMiddlewareCommand, MakeMigrationCommand, MakeModuleCommand, MakeProviderCommand, MakeServiceCommand, FileGenerator (+2 more)

### Community 95 - "Env"
Cohesion: 0.13
Nodes (3): Env, AppProfileTest, ProfileConfig

### Community 96 - "Application"
Cohesion: 0.11
Nodes (5): JavelinConsole, JavelinConsole, RouteListCommand, ServeCommand, Application

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "DefaultEventDispatcher"
Cohesion: 0.10
Nodes (12): Developer API, Errors, Events Module, Queued listeners, Service provider, DefaultEventDispatcher, EventDispatcher, EventListener (+4 more)

### Community 101 - "private"
Cohesion: 0.12
Nodes (17): private, public, temporary, domain, driver, public, root, domain (+9 more)

### Community 102 - "Scheduler"
Cohesion: 0.20
Nodes (6): Scheduler, SchedulerConfig, SchedulerLock, Interval, TaskDefinition, SchedulerTest

### Community 104 - ".resolve"
Cohesion: 0.16
Nodes (4): ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolver, TrustedProxyResolverTest

### Community 106 - "JsonLogEncoder"
Cohesion: 0.23
Nodes (3): JsonLogEncoder, JsonLogEncoder, JsonLogEncoderTest

### Community 108 - "io.javelin.core.Application"
Cohesion: 0.08
Nodes (5): Developer API, JacksonJsonServiceProvider, ObservabilityServiceProvider, QueueServiceProvider, CorsServiceProvider

### Community 114 - ".sustainsLoopbackThroughputThroughTheJdkAdapter"
Cohesion: 0.09
Nodes (13): Benchmarks, CI/CD, Deployment Module, GraalVM metadata, Linux service, Performance regression suite, Production validation, Stateless deployments (+5 more)

### Community 123 - "NatsQueue.java"
Cohesion: 0.10
Nodes (5): NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, NatsQueue

### Community 125 - "Session"
Cohesion: 0.06
Nodes (10): Cookie-backed sessions, Cookie, Session, SessionStore, StubSession, InMemorySessionStore, MemorySession, SessionConfig (+2 more)

### Community 128 - "org.junit.jupiter.api.Test"
Cohesion: 0.07
Nodes (10): HtmlResponse, JsonResponse, RedirectResponse, Response, Response instance methods, Response static factories, RedirectResponse, Response, DefaultExceptionHandlerTest, HttpKernelTest (+2 more)

### Community 129 - "Tracer"
Cohesion: 0.16
Nodes (3): Observability, OpenTelemetryTracer, Tracer

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

### Community 135 - ".has"
Cohesion: 0.08
Nodes (7): 1. Configure, EventServiceProvider, MailServiceProvider, DbPoolStats, PoolStats, NatsQueueServiceProviderTest, SchedulerServiceProvider

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - ".of"
Cohesion: 0.12
Nodes (11): Queue contracts, Configuration, Contracts, Developer API, Contracts (javelin-core), Queue Module, Reliability, Job (+3 more)

### Community 140 - "org.junit.jupiter.api.Test"
Cohesion: 0.14
Nodes (4): CommandGenerationTest, RequestInputTest, HttpTimeoutConfigTest, PebbleViewRendererTest

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

### Community 146 - "Architecture"
Cohesion: 0.17
Nodes (12): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Middleware pipeline, Module Boundaries (+4 more)

### Community 147 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (16): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Service provider (+8 more)

### Community 148 - "ScheduleSpec"
Cohesion: 0.15
Nodes (6): Schedules, Cron, Daily, Monthly, Weekly, ScheduleSpec

### Community 149 - "ViewRenderer"
Cohesion: 0.15
Nodes (10): Layer 0 — Foundation, Layer 1 — Contracts, Layer 2 — Adapters, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Dependency Graph, View, ViewRenderer (+2 more)

### Community 150 - "web.java"
Cohesion: 0.09
Nodes (14): web, Bearer authentication, CORS, HSTS and CSP, JWT adapter, PasswordHasher, RateLimitMiddleware, RequestSizeLimitMiddleware (+6 more)

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

### Community 174 - "Request"
Cohesion: 0.08
Nodes (8): HomeController, UserController, Cookies and sessions, DefaultExceptionHandler, ExceptionHandler, HttpKernel, Request, BearerAuthMiddlewareTest

### Community 175 - "queue"
Cohesion: 0.11
Nodes (19): dir, queue, driver, file, retry, stream, url, valkey (+11 more)

### Community 176 - ".resolve"
Cohesion: 0.16
Nodes (3): JwtTokenIssuer, JwtTokenResolver, JwtTokenResolverTest

### Community 178 - "ContainerTest"
Cohesion: 0.19
Nodes (8): ContainerTest, Alpha, Ambiguous, CloseableResource, Dependency, Failing, Service, Zeta

### Community 180 - ".load"
Cohesion: 0.22
Nodes (4): DotenvEnv, Entry, DotenvEnvTest, YamlConfigTest

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 185 - "io.javelin.core.Database"
Cohesion: 0.18
Nodes (3): DatabaseManager, JdbcDatabaseServiceProviderTest, DatabaseSchedulerLock

### Community 186 - ".handle"
Cohesion: 0.13
Nodes (5): ConfigurableExceptionHandler, ErrorPageResolver, SensitiveDataRedactor, ConfigurableExceptionHandlerTest, SensitiveDataRedactorTest

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 188 - "QueryBuilder"
Cohesion: 0.10
Nodes (10): QueryBuilder, ModelTest, CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder, SampleUser, Status (+2 more)

### Community 189 - "tutorials/README.md"
Cohesion: 0.12
Nodes (12): Choosing an adapter, File (`javelin-queue-file`), NATS JetStream (`javelin-queue-nats`), Queue Adapters, RabbitMQ (`javelin-queue-rabbitmq`), Valkey / Redis (`javelin-queue-valkey`), 1. Compose and send, 2. Plain text, attachments and inline images (+4 more)

### Community 190 - "DefaultValidator"
Cohesion: 0.18
Nodes (8): Relationship to `javelin-support`, Service provider, Supported rules, Validation Module, Validator, DefaultValidator, Validator, DefaultValidatorTest

### Community 191 - "HttpServerDriverProvider.java"
Cohesion: 0.13
Nodes (13): Configuration, Application Profile, Programmatic use, Selecting the profile, What changes, Application profile, HTTP driver selection, AppProfile (+5 more)

### Community 192 - "database"
Cohesion: 0.25
Nodes (8): database, connections, default, migrations_dir, query, log_all, slow_ms, timing

### Community 195 - "Override"
Cohesion: 0.17
Nodes (3): MailTest, CapturingQueue, TestConfig

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 197 - "ApplicationTest"
Cohesion: 0.15
Nodes (6): ApplicationTest, BaseProvider, DependentProvider, FirstResource, RecordingProvider, SecondResource

### Community 198 - "ExecutionRecord"
Cohesion: 0.27
Nodes (3): ExecutionHistory, ExecutionRecord, InMemoryExecutionHistory

### Community 199 - "TestConfig"
Cohesion: 0.15
Nodes (4): GraalvmConfigGenerator, SystemdUnit, DeploymentTest, TestConfig

### Community 201 - "security"
Cohesion: 0.14
Nodes (14): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+6 more)

### Community 202 - "HealthRegistry"
Cohesion: 0.24
Nodes (3): Health, HealthIndicator, HealthRegistry

### Community 204 - "Scheduler Module"
Cohesion: 0.18
Nodes (10): Application, Container, Container, Worker, Configuration, Developer API, Dispatching to queues, Reliability (+2 more)

### Community 205 - "ConsoleKernel"
Cohesion: 0.12
Nodes (9): CommandRegistry, AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry (+1 more)

### Community 206 - "security"
Cohesion: 0.15
Nodes (13): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+5 more)

### Community 207 - "CliException"
Cohesion: 0.18
Nodes (3): CliException, CacheClearCommand, ConfigCacheCommand

### Community 208 - "cache"
Cohesion: 0.33
Nodes (6): cache, caffeine, driver, url, default_ttl_seconds, maximum_size

### Community 210 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 213 - "cache"
Cohesion: 0.40
Nodes (5): cache, caffeine, driver, default_ttl_seconds, maximum_size

### Community 216 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 217 - "StructuredLogger"
Cohesion: 0.22
Nodes (3): Log, Logger, StructuredLogger

### Community 218 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 220 - ".register"
Cohesion: 0.22
Nodes (3): ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, Config

### Community 225 - "JdbcDatabaseServiceProvider"
Cohesion: 0.21
Nodes (3): JdbcDatabaseServiceProvider, NoopLogger, MigrationDialects

### Community 226 - "io.javelin.core.Response"
Cohesion: 0.15
Nodes (5): CorsConfig, CorsMiddleware, SecureHeadersMiddleware, SessionAuthMiddleware, SessionPrincipalResolver

### Community 229 - "MailManager"
Cohesion: 0.24
Nodes (3): MailManager, MailTransport, Mail

### Community 235 - "provider"
Cohesion: 0.40
Nodes (5): provider, directory, disabled, enabled, strict

### Community 238 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 239 - "Config Module"
Cohesion: 0.25
Nodes (8): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, Secret providers, YamlConfig

### Community 241 - "Scheduling and events"
Cohesion: 0.33
Nodes (5): 2. Schedule tasks, 3. Dispatch events, 4. Multi-instance deployments, 5. Manual runs, Scheduling and events

### Community 243 - ".handle"
Cohesion: 0.19
Nodes (3): ObservabilityMiddleware, RuntimeSnapshot, StartupProfile

### Community 245 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 246 - "MailMessage"
Cohesion: 0.21
Nodes (3): MailMessage, SmtpConfig, SmtpTransport

### Community 247 - "Production deployment"
Cohesion: 0.25
Nodes (7): 1. Observe the application, 2. Validate configuration, 3. Package and run, 4. Linux service, 5. Benchmark before release, 6. CI/CD and native images, Production deployment

### Community 248 - "mail"
Cohesion: 0.25
Nodes (8): mail, driver, from, retry, test, backoff_ms, max_attempts, dir

### Community 249 - "mail"
Cohesion: 0.25
Nodes (8): mail, driver, from, retry, test, backoff_ms, max_attempts, dir

### Community 251 - "Cache Module"
Cohesion: 0.40
Nodes (5): Cache, Cache Module, CacheServiceProvider, InMemoryCache, Notes

### Community 253 - "MailSubmissionLogger"
Cohesion: 0.17
Nodes (7): Address validation, Configuration, Logging, Mail Module, Queued delivery and failures, Test transport, MailSubmissionLogger

### Community 255 - "observability"
Cohesion: 0.33
Nodes (6): observability, enabled, health_path, metrics_path, ready_path, tracing

### Community 258 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 259 - "Observability Module"
Cohesion: 0.33
Nodes (5): Endpoints, Health checks, Metrics, Observability Module, Tracing

### Community 260 - "ProviderLoaderTest"
Cohesion: 0.32
Nodes (4): ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder

### Community 262 - "provider"
Cohesion: 0.40
Nodes (5): provider, directory, disabled, enabled, strict

### Community 263 - "HTTP Netty Module"
Cohesion: 0.67
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 264 - "CaffeineCacheServiceProvider"
Cohesion: 0.29
Nodes (3): Service provider, CaffeineCacheConfig, CaffeineCacheServiceProvider

### Community 266 - "Distributing providers"
Cohesion: 0.29
Nodes (6): 1. Generate a provider, 2. Enable or disable it, 3. Inspect and diagnose, 4. Install a versioned provider JAR, 5. CLI used by a distributed app, Distributing providers

### Community 267 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 268 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 272 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 273 - ".typedCacheAccessConvertsJsonBackToTheRequestedType"
Cohesion: 0.40
Nodes (3): Model static methods, ValkeyCacheTest, User

### Community 275 - "HTTP Common Module"
Cohesion: 0.22
Nodes (8): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, TrustedProxyResolver, Trusted proxy configuration, HttpTimeoutConfig

### Community 276 - "Reliable background jobs"
Cohesion: 0.33
Nodes (6): 1. Configure the queue, 2. Define and dispatch a job, 3. Register a handler, 4. Worker behavior, 5. Switch backend without changing code, Reliable background jobs

### Community 279 - "HTTP Request Lifecycle"
Cohesion: 0.40
Nodes (5): Content Negotiation on 404, Error Handling, HTTP Request Lifecycle, HttpKernel Dispatch (Step 4 detailed), Middleware Pipeline Walkthrough

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 1120 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **118 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `ServiceProvider` connect `Override` to `Application`, `Agent Notes`, `ProviderLoaderTest`, `ApplicationTest`, `Distributing providers`, `Scheduler Module`, `Module Authoring`, `ViewRenderer`, `Javelin.java`, `Javelin Framework`, `Model`, `Core Module`, `HttpServerDriverProvider.java`?**
  _High betweenness centrality (0.038) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.11806543385490754 - nodes in this community are weakly interconnected._
- **Why does `Application` connect `Application` to `Override`, `Override`, `cli/ConsoleKernel.java`, `ProviderLoaderTest`, `.has`, `ProfileConfig`, `CommandContext`, `Javelin.java`, `ViewRenderer`, `Core Module`, `path`, `.instance`, `map`, `Container`, `Config`, `HttpServerAdapter`, `HttpServerDriverProvider.java`, `ApplicationTest`, `Scheduler Module`, `Env`, `io.javelin.core.Application`, `.registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot`, `.register`, `.output`?**
  _High betweenness centrality (0.030) - this node is a cross-community bridge._
- **Should `arraylist` be split into smaller, more focused modules?**
  _Cohesion score 0.054426705370101594 - nodes in this community are weakly interconnected._
- **Why does `Request` connect `Request` to `org.junit.jupiter.api.Test`, `arraylist`, `ProfileConfig`, `io.javelin.core.Request`, `.text`, `MultipartData`, `.handle`, `HTTP Common Module`, `Middleware`, `HtmlResponse`, `map`, `Route`, `objects`, `NettyHttpServerIntegrationTest.java`, `.handle`, `UploadedFile`, `.login`, `Session`, `map`?**
  _High betweenness centrality (0.026) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.13675213675213677 - nodes in this community are weakly interconnected._