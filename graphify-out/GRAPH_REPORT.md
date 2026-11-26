# Graph Report - javelin-framework  (2026-11-26)

## Corpus Check
- 491 files · ~112,890 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 57 file(s) not represented in the graph (top: .stub 41, (none) 9, .xml 2)

## Summary
- 4843 nodes · 12149 edges · 273 communities (169 shown, 104 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1718 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `885bcb35`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
- duration
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
- io.javelin.core.Application
- MigrationRunner
- .login
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
- with
- NettyHttpServerIntegrationTest
- Connection
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
- path
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
- Database
- arraylist
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
- TestConfig
- Slf4jLogger
- io.javelin:javelin-parent
- Config
- XFileDisk
- NatsQueue.java
- AdultAgeRule
- .handle
- .output
- Container
- .status
- Tracer
- Installation Guide
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- MemorySession
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
- PebbleViewExtensions
- ScheduleSpec
- cli/ConsoleKernel.java
- com.fasterxml.jackson.databind.ObjectMapper
- RetryPolicy
- MailComposer
- TestConfig
- BenchmarkRunner
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
- .router
- ValkeyCache
- YamlConfig
- primary
- log
- .typedCacheAccessConvertsJsonBackToTheRequestedType
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
- JobQueue
- ExecutionRecord
- java.util.regex.Pattern
- TestConfig
- security
- HealthRegistry
- .text
- Scheduler Module
- Cache Module
- security
- .execute
- cache
- InMemoryFileRecorder
- .ok
- cache
- ContainerTest
- StubSession
- scheduler
- StructuredLogger
- scheduler
- CronExpression
- Config
- .handle
- .load
- Cache
- .middlewareServesHealthReadyAndMetrics
- MigrationDialect
- Distributing providers
- CapturingQueue
- JacksonJsonServiceProvider.java
- MailManager
- StubSession
- AppProfile
- MetricsRegistry
- InMemorySchedulerLock
- Config
- provider
- MigrateCommand
- Security
- tutorials/README.md
- DatabaseExecutionHistory
- .run
- worker
- .handle
- errors
- Mail Module
- MailMessage
- mail
- mail
- Config Module
- Storage Module
- TestMailTransport
- .composerViewAndQueueDispatch
- ValidationRuleRegistry
- observability
- Status
- observability
- session
- Observability Module
- Scheduling and events
- ProfileConfig
- provider
- HTTP Netty Module
- HTTP Undertow Module
- .collect
- cors
- errors
- MakeModelCommand
- DefaultJsonMapper
- NoopTracer
- Javelin Docs

## God Nodes (most connected - your core abstractions)
1. `Request` - 85 edges
2. `Application` - 85 edges
3. `Router` - 76 edges
4. `Response` - 72 edges
5. `Collection` - 63 edges
6. `CommandContext` - 60 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 52 edges
9. `JobQueue` - 45 edges
10. `Config` - 44 edges

## Surprising Connections (you probably didn't know these)
- `Production validation` --references--> `ProductionValidator`  [INFERRED]
  docs/modules/deployment.md → modules/javelin-deployment/src/main/java/io/javelin/deployment/ProductionValidator.java
- `Endpoints` --references--> `ObservabilityServiceProvider`  [INFERRED]
  docs/modules/observability.md → modules/javelin-observability/src/main/java/io/javelin/observability/ObservabilityServiceProvider.java
- `Tracing` --references--> `Tracer`  [INFERRED]
  docs/modules/observability.md → modules/javelin-observability/src/main/java/io/javelin/observability/Tracer.java
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `make:middleware — Generate middleware` --references--> `Middleware`  [INFERRED]
  docs/cli-reference.md → modules/javelin-core/src/main/java/io/javelin/core/Middleware.java

## Import Cycles
- None detected.

## Communities (273 total, 104 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.09
Nodes (14): ConfigServiceProvider, ProviderSorter, ServiceProvider, ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder, ProviderSorterTest (+6 more)

### Community 2 - "duration"
Cohesion: 0.04
Nodes (3): AutomationExampleController, Json, RetryPolicy

### Community 4 - "Override"
Cohesion: 0.15
Nodes (3): Config, Env, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.06
Nodes (16): AbstractCommand, CliException, CommandContext, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, CacheClearCommand (+8 more)

### Community 9 - "Handle"
Cohesion: 0.05
Nodes (8): Ai, Handle, Message, Reply, File, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.06
Nodes (9): QueueExampleController, SecurityExampleController, StorageExampleController, InvoiceJob, SendEmailJob, FileErrorPageResolver, JwtTokenIssuer, SessionAuthMiddleware (+1 more)

### Community 13 - "CapturingQueue"
Cohesion: 0.23
Nodes (3): QueueFacadeTest, CapturingQueue, SendEmailJob

### Community 15 - "StaticAssetResolver"
Cohesion: 0.24
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.22
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - ".handle"
Cohesion: 0.10
Nodes (7): 3. Browser login with CSRF, What happens on a bad CSRF token, RequiresAuthenticationMiddleware, RequestAttributesTest, CsrfMiddleware, SessionAuthMiddlewareTest, StubSession

### Community 21 - "Javelin.java"
Cohesion: 0.06
Nodes (23): Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Registration order matters, The ServiceProvider Contract, ServiceProvider, JdkHttpServer, Static file serving, Trusted proxy resolution (+15 more)

### Community 22 - "Request"
Cohesion: 0.07
Nodes (6): Key Design Decisions, Request, MultipartData, PartDisposition, UploadedFile, RequestMultipartTest

### Community 25 - "Model"
Cohesion: 0.10
Nodes (4): Model overridable methods, Model, ModelTest, SampleHydratedUser

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (42): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+34 more)

### Community 28 - "Core Module"
Cohesion: 0.09
Nodes (23): Config, Core Module, Database, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse (+15 more)

### Community 30 - "ProviderRegistry"
Cohesion: 0.06
Nodes (11): ProviderInstallCommand, ProviderListCommand, JavelinVersion, ProviderConflict, ProviderManifest, ProviderRegistry, ProviderVersion, ProviderRegistryTest (+3 more)

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.15
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "io.javelin.core.Application"
Cohesion: 0.05
Nodes (28): BackendController, Service provider, CORS, 1. Configure, CaffeineCacheServiceProvider, ValkeyCacheServiceProvider, SecretsServiceProvider, JdbcDatabaseServiceProvider (+20 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.16
Nodes (5): MigrationRunner, MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 37 - ".login"
Cohesion: 0.13
Nodes (9): 1. Enable the providers, 2. Build and start the demo, 4. Validation and error rendering, 5. JWT for the API, 6. CORS preflight, Build a secure web app and API, Where to go next, ValidationErrors (+1 more)

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.14
Nodes (5): DefaultExceptionHandler, ExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.08
Nodes (9): Middleware, Next, RedirectResponse, Response, RequiresPermissionMiddleware, SecurityResponses, RateLimitMiddleware, Bucket (+1 more)

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
Cohesion: 0.12
Nodes (16): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+8 more)

### Community 46 - "StorageServiceProvider"
Cohesion: 0.19
Nodes (3): StorageConfig, DiskConfig, StorageServiceProvider

### Community 48 - "Javelin Framework"
Cohesion: 0.09
Nodes (23): Architecture at a glance, Build And Test, Cache And Queues, CLI, Database, Documentation, Example Routes, Features (+15 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.11
Nodes (6): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, PasswordHashStrategyTest

### Community 51 - "docker-app.yaml"
Cohesion: 0.11
Nodes (17): app, env, name, cors, allow_credentials, allowed_origins, max_age_seconds, javelin (+9 more)

### Community 52 - "with"
Cohesion: 0.10
Nodes (19): jobs, verify, name, on, pull_request, push, branches, branches (+11 more)

### Community 54 - "Connection"
Cohesion: 0.29
Nodes (3): DatabaseConfig, Connection, DatabaseConfigTest

### Community 55 - "Deployment"
Cohesion: 0.20
Nodes (10): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Logging And Error Handling, Migration Strategy, Performance Benchmarks, Reverse Proxy (+2 more)

### Community 56 - "Disk"
Cohesion: 0.11
Nodes (5): Disk, DownloadPolicy, StorageManager, StoredFile, Storage

### Community 57 - "Route"
Cohesion: 0.09
Nodes (17): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+9 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.14
Nodes (5): QueryTimingConfig, SqlTimer, SqlTimerTest, RecordingLogger, LoggingTracer

### Community 60 - ".adapterFor"
Cohesion: 0.12
Nodes (9): HTTP JDK Module, JdkHttpServiceProvider, Notes, Configuration, Configuration, HttpServerAdapter, HttpServerDriverProviderTest, DriverConfig (+1 more)

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
Cohesion: 0.11
Nodes (18): Application Flow, Boot Sequence, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development) (+10 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.17
Nodes (34): io.javelin:javelin-cache, io.javelin:javelin-cache-caffeine, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration (+26 more)

### Community 78 - "CaffeineCache"
Cohesion: 0.15
Nodes (8): Cache Caffeine Module, Configuration, Developer API, Service provider, CaffeineCache, Expiry<String, Entry>, Entry, CaffeineCacheTest

### Community 79 - "Logger"
Cohesion: 0.13
Nodes (9): Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger, Log, NoopLogger (+1 more)

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (42): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+34 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 83 - "Database"
Cohesion: 0.11
Nodes (8): Layer 2 — Adapters, Core interfaces you can implement, Behavior, Configuration, Database Queue Module, Database, TransactionCallback, SampleUser

### Community 86 - "Application"
Cohesion: 0.06
Nodes (17): JavelinConsole, Application profile, HTTP driver selection, Javelin, JavelinStarterServiceProvider, Notes, Starter Module, ValidationRuleLoader (+9 more)

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - "Docker Compose"
Cohesion: 0.22
Nodes (9): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running (+1 more)

### Community 89 - "InMemoryQueue"
Cohesion: 0.16
Nodes (3): ClaimedJob, QueueWorker, QueueWorkerConfig

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 92 - "HtmlResponse"
Cohesion: 0.20
Nodes (4): HtmlResponse, View, ViewRenderer, ViewTest

### Community 93 - "JdkHttpServer.java"
Cohesion: 0.08
Nodes (10): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, HttpTimeoutConfig, JdkHttpServer, RequestRejected (+2 more)

### Community 94 - "locale"
Cohesion: 0.06
Nodes (11): AiGenerateModuleCommand, GenerateCommandSupport, MakeMiddlewareCommand, MakeMigrationCommand, MakeModuleCommand, MakeProviderCommand, MakeServiceCommand, NewCommand (+3 more)

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "DefaultEventDispatcher"
Cohesion: 0.12
Nodes (7): UserRegisteredEvent, DefaultEventDispatcher, EventDispatcher, EventListener, Events, EventDispatcherTest, UserRegistered

### Community 101 - "private"
Cohesion: 0.12
Nodes (17): private, public, temporary, domain, driver, public, root, domain (+9 more)

### Community 102 - "Scheduler"
Cohesion: 0.14
Nodes (7): ExecutionHistory, Scheduler, SchedulerConfig, SchedulerLock, TaskDefinition, SchedulerTest, SchedulerLock

### Community 106 - "JsonLogEncoder"
Cohesion: 0.21
Nodes (4): JsonLogEncoder, RequestLoggingMiddleware, JsonLogEncoder, JsonLogEncoderTest

### Community 107 - "SmtpTransport.java"
Cohesion: 0.12
Nodes (3): SmtpConfig, SmtpTransport, Authenticator

### Community 108 - "io.javelin.core.Application"
Cohesion: 0.06
Nodes (7): CaffeineCacheConfig, ValkeyCacheServiceProviderTest, Config, ExceptionConfig, JacksonJsonConfig, MailServiceProvider, QueueServiceProvider

### Community 114 - "TestConfig"
Cohesion: 0.15
Nodes (4): GraalvmConfigGenerator, SystemdUnit, DeploymentTest, TestConfig

### Community 125 - ".handle"
Cohesion: 0.08
Nodes (8): Cookies and sessions, Cookie, Session, SessionStore, CookieAndSessionContractTest, SessionConfig, SessionMiddleware, SessionMiddlewareTest

### Community 127 - "Container"
Cohesion: 0.19
Nodes (3): Container, Binding, Boot Flow

### Community 128 - ".status"
Cohesion: 0.09
Nodes (6): DefaultExceptionHandlerTest, HttpKernelTest, ResponseTest, BearerAuthMiddlewareTest, StaticAssetResolverTest, ProfileWiringTest

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
Cohesion: 0.12
Nodes (12): Queue contracts, Contracts, Developer API, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module (+4 more)

### Community 139 - "MigrationRunner"
Cohesion: 0.22
Nodes (3): MigrateCommand, MigrateRollbackCommand, MigrationRunner

### Community 140 - ".commands"
Cohesion: 0.08
Nodes (12): CommandRegistry, AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry (+4 more)

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
Cohesion: 0.12
Nodes (16): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Layer 0 — Foundation, Layer 1 — Contracts, Layer 3 — Toolchain (+8 more)

### Community 147 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (17): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Service provider (+9 more)

### Community 148 - "ScheduleSpec"
Cohesion: 0.15
Nodes (7): Schedules, Cron, Daily, Interval, Monthly, Weekly, ScheduleSpec

### Community 149 - "cli/ConsoleKernel.java"
Cohesion: 0.06
Nodes (11): BenchCommand, DeployNativeConfigCommand, DeployServiceCommand, DeployValidateCommand, DoctorCommand, QueueStatusCommand, QueueWorkCommand, ScheduleRunCommand (+3 more)

### Community 151 - "RetryPolicy"
Cohesion: 0.14
Nodes (10): Developer API, Errors, Events Module, Queued listeners, Service provider, QueuedEvent, JobHandlerRegistry, Registration (+2 more)

### Community 154 - "BenchmarkRunner"
Cohesion: 0.17
Nodes (9): Benchmarks, CI/CD, Deployment Module, GraalVM metadata, Linux service, Production validation, Stateless deployments, BenchmarkRunner (+1 more)

### Community 155 - "Build a secure web app and API"
Cohesion: 0.14
Nodes (6): Notes, 7. Secrets, CompositeSecretProvider, FileSecretProvider, SecretProvider, SecretProviderTest

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
Cohesion: 0.13
Nodes (4): Router, ResolvedRoute, Routes, RouterTest

### Community 174 - "Authenticated"
Cohesion: 0.11
Nodes (11): Bearer authentication and authorization, Middleware, Bearer authentication, JWT adapter, Safety, 3. Private downloads, Authenticated, BearerAuthMiddleware (+3 more)

### Community 175 - "queue"
Cohesion: 0.15
Nodes (13): dir, queue, driver, file, jetstream, retry, stream, url (+5 more)

### Community 178 - ".router"
Cohesion: 0.07
Nodes (18): web, Adding to the hardcoded provider list, Integrating with Starter, Provider registration order, TrustedProxyResolver, Cookie-backed sessions, CSRF, HSTS and CSP (+10 more)

### Community 179 - "ValkeyCache"
Cohesion: 0.27
Nodes (3): InMemoryCache, Cache, ValkeyCache

### Community 180 - "YamlConfig"
Cohesion: 0.19
Nodes (4): YamlConfig, TypeReference<>, TypeReference<>, YamlConfigTest

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 183 - ".typedCacheAccessConvertsJsonBackToTheRequestedType"
Cohesion: 0.19
Nodes (6): Model, Model instance methods, Model static methods, ValkeyCacheTest, Cache, User

### Community 186 - ".handle"
Cohesion: 0.15
Nodes (5): ConfigurableExceptionHandler, ErrorPageResolver, SensitiveDataRedactor, ConfigurableExceptionHandlerTest, SensitiveDataRedactorTest

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 188 - "QueryBuilder"
Cohesion: 0.13
Nodes (5): QueryBuilder, CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder

### Community 189 - "Queue Adapters"
Cohesion: 0.14
Nodes (12): Choosing an adapter, File (`javelin-queue-file`), NATS JetStream (`javelin-queue-nats`), Queue Adapters, RabbitMQ (`javelin-queue-rabbitmq`), Valkey / Redis (`javelin-queue-valkey`), 1. Configure the queue, 2. Define and dispatch a job (+4 more)

### Community 190 - "DefaultValidator"
Cohesion: 0.18
Nodes (8): Relationship to `javelin-support`, Service provider, Supported rules, Validation Module, Validator, DefaultValidator, Validator, DefaultValidatorTest

### Community 191 - "TestConfig"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 192 - "database"
Cohesion: 0.25
Nodes (8): database, connections, default, migrations_dir, query, log_all, slow_ms, timing

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 197 - "JobQueue"
Cohesion: 0.15
Nodes (6): Configuration, Queue Module, Reliability, Worker, JobQueue, Queue

### Community 198 - "ExecutionRecord"
Cohesion: 0.21
Nodes (3): DemoScheduleJob, ExecutionRecord, InMemoryExecutionHistory

### Community 201 - "security"
Cohesion: 0.14
Nodes (14): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+6 more)

### Community 202 - "HealthRegistry"
Cohesion: 0.23
Nodes (3): Health, HealthIndicator, HealthRegistry

### Community 204 - "Scheduler Module"
Cohesion: 0.22
Nodes (8): Container, Container, Configuration, Developer API, Dispatching to queues, Reliability, Scheduler Module, Schedules

### Community 205 - "Cache Module"
Cohesion: 0.50
Nodes (4): Cache, Cache Module, CacheServiceProvider, Notes

### Community 206 - "security"
Cohesion: 0.15
Nodes (13): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+5 more)

### Community 208 - "cache"
Cohesion: 0.33
Nodes (6): cache, caffeine, driver, url, default_ttl_seconds, maximum_size

### Community 210 - ".ok"
Cohesion: 0.23
Nodes (3): HomeController, UserController, JsonResponse

### Community 213 - "cache"
Cohesion: 0.40
Nodes (5): cache, caffeine, driver, default_ttl_seconds, maximum_size

### Community 214 - "ContainerTest"
Cohesion: 0.23
Nodes (6): ContainerTest, CircularA, CircularB, CloseableResource, Dependency, Service

### Community 216 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 217 - "StructuredLogger"
Cohesion: 0.15
Nodes (4): Log, Logger, StructuredLogger, RequestLoggingMiddleware

### Community 218 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 221 - ".handle"
Cohesion: 0.23
Nodes (3): CorsConfig, CorsMiddleware, CorsAndCsrfTest

### Community 222 - ".load"
Cohesion: 0.31
Nodes (3): DotenvEnv, Entry, DotenvEnvTest

### Community 224 - ".middlewareServesHealthReadyAndMetrics"
Cohesion: 0.24
Nodes (3): ObservabilityConfig, ObservabilityTest, TestConfig

### Community 225 - "MigrationDialect"
Cohesion: 0.18
Nodes (5): Backward compatibility, Database Migration Module, Dialects, MigrationDialect, MigrationDialects

### Community 226 - "Distributing providers"
Cohesion: 0.29
Nodes (6): 1. Generate a provider, 2. Enable or disable it, 3. Inspect and diagnose, 4. Install a versioned provider JAR, 5. CLI used by a distributed app, Distributing providers

### Community 229 - "MailManager"
Cohesion: 0.24
Nodes (3): MailManager, MailTransport, Mail

### Community 231 - "AppProfile"
Cohesion: 0.25
Nodes (7): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD

### Community 235 - "provider"
Cohesion: 0.40
Nodes (5): provider, directory, disabled, enabled, strict

### Community 238 - "tutorials/README.md"
Cohesion: 0.12
Nodes (13): 1. Compose and send, 2. Plain text, attachments and inline images, 3. Production SMTP, 4. Reliability and safety, Sending mail, 1. Observe the application, 2. Validate configuration, 3. Package and run (+5 more)

### Community 241 - ".run"
Cohesion: 0.13
Nodes (8): AppServiceProvider, Main, Phase 2: Boot, Startup Time, Why startup is fast, Config-List Provider Loading, Application, Javelin

### Community 242 - "worker"
Cohesion: 0.29
Nodes (7): worker, concurrency, enabled, lease_seconds, poll_interval_ms, shutdown_seconds, timeout_seconds

### Community 243 - ".handle"
Cohesion: 0.17
Nodes (3): ObservabilityMiddleware, RuntimeSnapshot, StartupProfile

### Community 245 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 246 - "Mail Module"
Cohesion: 0.22
Nodes (7): Address validation, Configuration, Logging, Mail Module, Queued delivery and failures, Test transport, MailSubmissionLogger

### Community 248 - "mail"
Cohesion: 0.25
Nodes (8): mail, driver, from, retry, test, backoff_ms, max_attempts, dir

### Community 249 - "mail"
Cohesion: 0.25
Nodes (8): mail, driver, from, retry, test, backoff_ms, max_attempts, dir

### Community 250 - "Config Module"
Cohesion: 0.25
Nodes (8): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, Secret providers, YamlConfig

### Community 251 - "Storage Module"
Cohesion: 0.29
Nodes (5): Configuration, Developer API, File recorder, Service provider, Storage Module

### Community 255 - "observability"
Cohesion: 0.33
Nodes (6): observability, enabled, health_path, metrics_path, ready_path, tracing

### Community 256 - "Status"
Cohesion: 0.67
Nodes (3): Status, DRAFT, PUBLISHED

### Community 257 - "observability"
Cohesion: 0.33
Nodes (6): observability, enabled, health_path, metrics_path, ready_path, tracing

### Community 258 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 259 - "Observability Module"
Cohesion: 0.33
Nodes (5): Endpoints, Health checks, Metrics, Observability Module, Tracing

### Community 260 - "Scheduling and events"
Cohesion: 0.33
Nodes (5): 2. Schedule tasks, 3. Dispatch events, 4. Multi-instance deployments, 5. Manual runs, Scheduling and events

### Community 262 - "provider"
Cohesion: 0.40
Nodes (5): provider, directory, disabled, enabled, strict

### Community 263 - "HTTP Netty Module"
Cohesion: 0.67
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 264 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

### Community 267 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 268 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 272 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DELETE`, `GET` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 1111 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **104 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `.instance`, `io.javelin.core.Application`, `path`, `cli/ConsoleKernel.java`, `AppProfile`, `ApplicationTest`, `io.javelin.core.Application`, `.commands`, `Scheduler Module`, `map`, `.router`, `cli/ConsoleKernel.java`, `Javelin.java`, `.output`, `Container`?**
  _High betweenness centrality (0.080) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.08985507246376812 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `Agent Notes`, `Distributing providers`, `io.javelin.core.Application`, `ApplicationTest`, `Javelin Framework`, `Module Authoring`, `Architecture`, `Database`, `.run`, `Javelin.java`, `Application`, `Core Module`?**
  _High betweenness centrality (0.039) - this node is a cross-community bridge._
- **Should `duration` be split into smaller, more focused modules?**
  _Cohesion score 0.044078754040552455 - nodes in this community are weakly interconnected._
- **Why does `JobQueue` connect `JobQueue` to `duration`, `io.javelin.core.Request`, `CapturingQueue`, `cli/ConsoleKernel.java`, `RetryPolicy`, `DatabaseQueue`, `RabbitMqQueue`, `io.javelin.core.Application`, `Javelin Framework`, `Queue Adapters`, `Override`, `path`, `FileQueue`, `Database`, `InMemoryQueue`, `ValkeyQueue`, `CapturingQueue`, `NatsQueue.java`, `.composerViewAndQueueDispatch`?**
  _High betweenness centrality (0.033) - this node is a cross-community bridge._
- **Should `cli/ConsoleKernel.java` be split into smaller, more focused modules?**
  _Cohesion score 0.05819209039548023 - nodes in this community are weakly interconnected._