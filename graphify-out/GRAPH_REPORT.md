# Graph Report - javelin-framework  (2026-11-27)

## Corpus Check
- 494 files · ~114,190 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 57 file(s) not represented in the graph (top: .stub 41, (none) 9, .xml 2)

## Summary
- 4880 nodes · 12284 edges · 281 communities (158 shown, 123 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1748 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `935638a0`
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
- StubSession
- Javelin.java
- Request
- DatabaseQueue
- Model
- CLI Reference
- Date
- Core Module
- Handle
- ProviderRegistry
- .imageSupportDetectsAndReadsImageMetadata
- .instance
- Validation
- NettyHttpServerIntegrationTest.java
- io.javelin.core.ServiceProvider
- MigrationRunner
- Build a secure web app and API
- JdkHttpServerIntegrationTest
- Response
- Support Module
- .send
- Schedule.java
- Input
- app.yaml
- StorageServiceProvider
- NettyHttpServer.java
- Container
- Console Module
- PasswordHashStrategy
- docker-app.yaml
- with
- Json.java
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
- TransactionCallback
- io.javelin.core.Env
- Str
- Application
- environment
- Docker Compose
- InMemoryQueue
- Database JDBC Module
- VersionCommand
- HtmlResponse
- JdkHttpServer
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
- JavelinViewExtension
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
- RecordingLogger
- org.junit.jupiter.api.Test
- Tracer
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
- ConsoleKernel
- services
- File
- app
- nats
- valkey
- Architecture
- PebbleViewRenderer
- ScheduleSpec
- cli/ConsoleKernel.java
- com.fasterxml.jackson.databind.ObjectMapper
- RetryPolicy
- MailComposer
- TestConfig
- .sustainsLoopbackThroughputThroughTheJdkAdapter
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
- .handle
- queue
- .resolve
- .manager
- Security Module
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
- Queue
- ExecutionRecord
- TestConfig
- security
- HealthRegistry
- .text
- Scheduler Module
- Cache Module
- security
- Override
- cache
- InMemoryFileRecorder
- .validate
- cache
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
- JdbcDatabaseServiceProvider
- Distributing providers
- CapturingQueue
- JacksonJsonServiceProvider.java
- MailManager
- PebbleViewExtensions
- java.util.regex.Pattern
- MetricsRegistry
- InMemorySchedulerLock
- Config
- provider
- MigrateCommand
- Security
- tutorials/README.md
- DatabaseExecutionHistory
- io.pebbletemplates.pebble.extension.Filter
- worker
- .handle
- errors
- Mail Module
- Production deployment
- mail
- mail
- Config Module
- Storage Module
- TestMailTransport
- MailSubmissionLogger
- observability
- WorkspaceAgeRule
- observability
- session
- Observability Module
- Scheduling and events
- ProfileConfig
- provider
- HTTP Netty Module
- HTTP Undertow Module
- .render
- cors
- errors
- .stream
- DefaultJsonMapper
- NoopTracer
- Javelin Docs
- TestConfig
- Http
- HTTP Common Module
- Cache
- NoopLogger
- Database Queue Module
- SchedulerLock

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
- `Connection pool (HikariCP)` --references--> `Logger`  [INFERRED]
  docs/deployment.md → modules/javelin-core/src/main/java/io/javelin/core/Logger.java
- `Trusted proxy configuration` --references--> `JdkHttpServiceProvider`  [INFERRED]
  docs/deployment.md → modules/javelin-http-jdk/src/main/java/io/javelin/http/jdk/JdkHttpServiceProvider.java
- `Production validation` --references--> `ProductionValidator`  [INFERRED]
  docs/modules/deployment.md → modules/javelin-deployment/src/main/java/io/javelin/deployment/ProductionValidator.java
- `Notes` --references--> `ValkeyCacheServiceProvider`  [INFERRED]
  docs/modules/cache.md → modules/javelin-cache-valkey/src/main/java/io/javelin/cache/valkey/ValkeyCacheServiceProvider.java
- `TemplateEngine` --references--> `TemplateEngine`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/cli/generator/TemplateEngine.java

## Import Cycles
- None detected.

## Communities (281 total, 123 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.09
Nodes (14): ConfigServiceProvider, ProviderSorter, ServiceProvider, ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder, ProviderSorterTest (+6 more)

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.05
Nodes (18): AbstractCommand, CliException, CommandContext, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, CacheClearCommand (+10 more)

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.04
Nodes (18): AutomationExampleController, BackendController, QueueExampleController, SecurityExampleController, StorageExampleController, UserRegisteredEvent, InvoiceJob, SendEmailJob (+10 more)

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.22
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 21 - "Javelin.java"
Cohesion: 0.04
Nodes (38): AppServiceProvider, Main, Phase 2: Boot, Startup Time, Why startup is fast, Adding to the hardcoded provider list, Config-List Provider Loading, Integrating with Starter (+30 more)

### Community 22 - "Request"
Cohesion: 0.04
Nodes (17): UserController, Bearer authentication and authorization, Middleware, Middleware, Next, Request, MultipartData, PartDisposition (+9 more)

### Community 25 - "Model"
Cohesion: 0.10
Nodes (4): Model overridable methods, Database, Model, SampleUser

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (42): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+34 more)

### Community 28 - "Core Module"
Cohesion: 0.09
Nodes (22): Config, Core Module, Database, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse (+14 more)

### Community 30 - "ProviderRegistry"
Cohesion: 0.06
Nodes (11): ProviderInstallCommand, ProviderListCommand, JavelinVersion, ProviderConflict, ProviderManifest, ProviderRegistry, ProviderVersion, ProviderRegistryTest (+3 more)

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.08
Nodes (13): HTTP driver selection, Javelin, JavelinStarterServiceProvider, Starter Module, ValidationRuleLoader, WorkspaceDefaults, JavelinStarterServiceProvider, ValidationRuleLoader (+5 more)

### Community 34 - "NettyHttpServerIntegrationTest.java"
Cohesion: 0.07
Nodes (4): Authenticated, RequiresPermissionMiddleware, SessionAuthMiddleware, SessionPrincipalResolver

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.06
Nodes (31): JdkHttpServiceProvider, Configuration, Configuration, Application Profile, Programmatic use, Selecting the profile, What changes, Service provider (+23 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.12
Nodes (9): Backward compatibility, Database Migration Module, Dialects, MigrationRunner, MigrationDialect, MigrationRunner, MigrationFile, MigrationRecord (+1 more)

### Community 37 - "Build a secure web app and API"
Cohesion: 0.15
Nodes (11): Notes, 1. Enable the providers, 2. Build and start the demo, 4. Validation and error rendering, 5. JWT for the API, 6. CORS preflight, 7. Secrets, Build a secure web app and API (+3 more)

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.13
Nodes (7): DefaultExceptionHandler, ExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer, UndertowHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.07
Nodes (8): HomeController, JsonResponse, RedirectResponse, Response, SecurityResponses, ValidationErrors, ResponseTest, ValidationErrorsTest

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 44 - "app.yaml"
Cohesion: 0.12
Nodes (16): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+8 more)

### Community 46 - "StorageServiceProvider"
Cohesion: 0.21
Nodes (3): StorageConfig, DiskConfig, StorageServiceProvider

### Community 47 - "NettyHttpServer.java"
Cohesion: 0.05
Nodes (8): ExceptionConfig, ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolver, TrustedProxyResolverTest, NettyHttpServer, RequestHandler, ChannelInitializer<SocketChannel>

### Community 48 - "Container"
Cohesion: 0.05
Nodes (35): Container, Container, Worker, Reliability, Container, Binding, ContainerTest, CircularA (+27 more)

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

### Community 54 - "Config"
Cohesion: 0.11
Nodes (7): Service provider, CaffeineCacheConfig, Config, DatabaseConfig, Connection, DatabaseConfigTest, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.20
Nodes (10): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Logging And Error Handling, Migration Strategy, Performance Benchmarks, Reverse Proxy (+2 more)

### Community 57 - "Route"
Cohesion: 0.08
Nodes (19): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+11 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.24
Nodes (3): QueryTimingConfig, SqlTimer, SqlTimerTest

### Community 60 - ".adapterFor"
Cohesion: 0.16
Nodes (6): HttpServerAdapter, HttpServerAdapter, HttpServerDriverProviderTest, DriverConfig, DriverEnv, Features

### Community 61 - "JsonMapper"
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
Cohesion: 0.16
Nodes (7): Cache Caffeine Module, Configuration, Developer API, CaffeineCache, Expiry<String, Entry>, Entry, CaffeineCacheTest

### Community 79 - "Logger"
Cohesion: 0.16
Nodes (8): Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger, Log, Logger

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (42): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+34 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 84 - "io.javelin.core.Env"
Cohesion: 0.24
Nodes (3): EnvSecretProvider, TypeReference<>, TypeReference<>

### Community 86 - "Application"
Cohesion: 0.06
Nodes (11): JavelinConsole, JavelinConsole, RouteListCommand, ServeCommand, Application, ApplicationTest, BaseProvider, DependentProvider (+3 more)

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - "Docker Compose"
Cohesion: 0.22
Nodes (9): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running (+1 more)

### Community 89 - "InMemoryQueue"
Cohesion: 0.11
Nodes (4): JobQueue, ClaimedJob, QueueWorker, QueueWorkerConfig

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 92 - "HtmlResponse"
Cohesion: 0.22
Nodes (4): Service provider, HtmlResponse, View, ViewRenderer

### Community 93 - "JdkHttpServer"
Cohesion: 0.15
Nodes (4): HttpTimeoutConfig, JdkHttpServer, RequestRejected, UndertowHttpServer

### Community 94 - "locale"
Cohesion: 0.07
Nodes (10): AiGenerateModuleCommand, GenerateCommandSupport, MakeMiddlewareCommand, MakeMigrationCommand, MakeModuleCommand, MakeProviderCommand, NewCommand, FileGenerator (+2 more)

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "DefaultEventDispatcher"
Cohesion: 0.10
Nodes (11): Developer API, Errors, Events Module, Service provider, DefaultEventDispatcher, EventDispatcher, EventListener, Events (+3 more)

### Community 101 - "private"
Cohesion: 0.12
Nodes (17): private, public, temporary, domain, driver, public, root, domain (+9 more)

### Community 102 - "Scheduler"
Cohesion: 0.16
Nodes (6): Scheduler, SchedulerConfig, SchedulerLock, Interval, TaskDefinition, SchedulerTest

### Community 104 - "JavelinViewExtension"
Cohesion: 0.30
Nodes (3): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction

### Community 106 - "JsonLogEncoder"
Cohesion: 0.21
Nodes (4): JsonLogEncoder, RequestLoggingMiddleware, JsonLogEncoder, JsonLogEncoderTest

### Community 107 - "SmtpTransport.java"
Cohesion: 0.11
Nodes (4): MailMessage, SmtpConfig, SmtpTransport, Authenticator

### Community 108 - "io.javelin.core.Application"
Cohesion: 0.05
Nodes (14): web, Queued listeners, CaffeineCacheServiceProvider, ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, EventServiceProvider, MailServiceProvider, DbPoolStats (+6 more)

### Community 114 - "TestConfig"
Cohesion: 0.15
Nodes (4): GraalvmConfigGenerator, SystemdUnit, DeploymentTest, TestConfig

### Community 125 - ".handle"
Cohesion: 0.12
Nodes (6): Cookies and sessions, Cookie, CookieAndSessionContractTest, SessionConfig, SessionMiddleware, SessionMiddlewareTest

### Community 128 - "org.junit.jupiter.api.Test"
Cohesion: 0.06
Nodes (16): CommandGenerationTest, DefaultExceptionHandlerTest, HttpKernelTest, RequestInputTest, ViewTest, CorePerformanceRegressionTest, DeepService, FactoryService (+8 more)

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
Cohesion: 0.36
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

### Community 140 - "ConsoleKernel"
Cohesion: 0.12
Nodes (9): CommandRegistry, AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry (+1 more)

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
Cohesion: 0.12
Nodes (17): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Layer 0 — Foundation, Layer 1 — Contracts (+9 more)

### Community 147 - "PebbleViewRenderer"
Cohesion: 0.17
Nodes (8): Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Shared view data, View Pebble Module, PebbleViewRenderer, Options

### Community 148 - "ScheduleSpec"
Cohesion: 0.18
Nodes (5): Cron, Daily, Monthly, Weekly, ScheduleSpec

### Community 149 - "cli/ConsoleKernel.java"
Cohesion: 0.06
Nodes (10): BenchCommand, DeployNativeConfigCommand, DeployServiceCommand, DeployValidateCommand, DoctorCommand, QueueStatusCommand, QueueWorkCommand, ScheduleRunCommand (+2 more)

### Community 151 - "RetryPolicy"
Cohesion: 0.19
Nodes (6): Contracts, RetryPolicy, JobHandlerRegistry, Registration, QueueWorkerTest, InMemoryQueue

### Community 154 - ".sustainsLoopbackThroughputThroughTheJdkAdapter"
Cohesion: 0.09
Nodes (13): Benchmarks, CI/CD, Deployment Module, GraalVM metadata, Linux service, Performance regression suite, Production validation, Stateless deployments (+5 more)

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
Nodes (4): Router, RouterTest, NettyHttpServerIntegrationTest, RunningServer

### Community 175 - "queue"
Cohesion: 0.15
Nodes (13): dir, queue, driver, file, jetstream, retry, stream, url (+5 more)

### Community 176 - ".resolve"
Cohesion: 0.16
Nodes (3): JwtTokenIssuer, JwtTokenResolver, JwtTokenResolverTest

### Community 178 - "Security Module"
Cohesion: 0.10
Nodes (15): TrustedProxyResolver, Bearer authentication, Cookie-backed sessions, CSRF, HSTS and CSP, JWT adapter, PasswordHasher, RateLimitMiddleware (+7 more)

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 183 - ".typedCacheAccessConvertsJsonBackToTheRequestedType"
Cohesion: 0.29
Nodes (5): Model, Model instance methods, Model static methods, ValkeyCacheTest, User

### Community 185 - "io.javelin.core.Database"
Cohesion: 0.16
Nodes (3): DatabaseManager, JdbcDatabaseServiceProviderTest, DatabaseSchedulerLock

### Community 186 - ".handle"
Cohesion: 0.22
Nodes (3): SensitiveDataRedactor, ConfigurableExceptionHandlerTest, SensitiveDataRedactorTest

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 188 - "QueryBuilder"
Cohesion: 0.09
Nodes (12): Layer 2 — Adapters, Core interfaces you can implement, QueryBuilder, ModelTest, CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder (+4 more)

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

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 197 - "Queue"
Cohesion: 0.26
Nodes (3): Queue, QueueFacadeTest, SendEmailJob

### Community 198 - "ExecutionRecord"
Cohesion: 0.18
Nodes (4): DemoScheduleJob, ExecutionHistory, ExecutionRecord, InMemoryExecutionHistory

### Community 201 - "security"
Cohesion: 0.14
Nodes (14): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+6 more)

### Community 202 - "HealthRegistry"
Cohesion: 0.24
Nodes (3): Health, HealthIndicator, HealthRegistry

### Community 204 - "Scheduler Module"
Cohesion: 0.40
Nodes (5): Configuration, Developer API, Dispatching to queues, Scheduler Module, Schedules

### Community 205 - "Cache Module"
Cohesion: 0.40
Nodes (5): Cache, Cache Module, CacheServiceProvider, InMemoryCache, Notes

### Community 206 - "security"
Cohesion: 0.15
Nodes (13): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+5 more)

### Community 208 - "cache"
Cohesion: 0.33
Nodes (6): cache, caffeine, driver, url, default_ttl_seconds, maximum_size

### Community 213 - "cache"
Cohesion: 0.40
Nodes (5): cache, caffeine, driver, default_ttl_seconds, maximum_size

### Community 216 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 217 - "StructuredLogger"
Cohesion: 0.20
Nodes (3): Log, Logger, StructuredLogger

### Community 218 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 221 - ".handle"
Cohesion: 0.12
Nodes (4): RequestLoggingMiddleware, RequestLoggingMiddlewareTest, CorsConfig, CorsMiddleware

### Community 222 - ".load"
Cohesion: 0.31
Nodes (3): DotenvEnv, Entry, DotenvEnvTest

### Community 225 - "JdbcDatabaseServiceProvider"
Cohesion: 0.20
Nodes (3): JdbcDatabaseServiceProvider, NoopLogger, MigrationDialects

### Community 226 - "Distributing providers"
Cohesion: 0.29
Nodes (6): 1. Generate a provider, 2. Enable or disable it, 3. Inspect and diagnose, 4. Install a versioned provider JAR, 5. CLI used by a distributed app, Distributing providers

### Community 229 - "MailManager"
Cohesion: 0.23
Nodes (3): MailManager, MailTransport, Mail

### Community 235 - "provider"
Cohesion: 0.40
Nodes (5): provider, directory, disabled, enabled, strict

### Community 238 - "tutorials/README.md"
Cohesion: 0.12
Nodes (12): 1. Disks and cache are configured explicitly, 2. Write and read files, 4. S3 or MinIO, 5. Caching, Files and caching, Notes, 1. Compose and send, 2. Plain text, attachments and inline images (+4 more)

### Community 242 - "worker"
Cohesion: 0.29
Nodes (7): worker, concurrency, enabled, lease_seconds, poll_interval_ms, shutdown_seconds, timeout_seconds

### Community 243 - ".handle"
Cohesion: 0.19
Nodes (3): ObservabilityMiddleware, RuntimeSnapshot, StartupProfile

### Community 245 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 246 - "Mail Module"
Cohesion: 0.33
Nodes (6): Address validation, Configuration, Developer API, Mail Module, Queued delivery and failures, Test transport

### Community 247 - "Production deployment"
Cohesion: 0.25
Nodes (7): 1. Observe the application, 2. Validate configuration, 3. Package and run, 4. Linux service, 5. Benchmark before release, 6. CI/CD and native images, Production deployment

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
Cohesion: 0.25
Nodes (5): Configuration, Developer API, File recorder, Service provider, Storage Module

### Community 255 - "observability"
Cohesion: 0.33
Nodes (6): observability, enabled, health_path, metrics_path, ready_path, tracing

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
Cohesion: 0.29
Nodes (6): 1. Configure, 2. Schedule tasks, 3. Dispatch events, 4. Multi-instance deployments, 5. Manual runs, Scheduling and events

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

### Community 275 - "HTTP Common Module"
Cohesion: 0.40
Nodes (5): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy

### Community 279 - "Database Queue Module"
Cohesion: 0.50
Nodes (3): Behavior, Configuration, Database Queue Module

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DEV`, `PROD`, `DRAFT`, `PUBLISHED` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 1115 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **123 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `.instance`, `org.junit.jupiter.api.Test`, `io.javelin.core.ServiceProvider`, `Override`, `cli/ConsoleKernel.java`, `path`, `io.javelin.core.Application`, `map`, `Container`, `cli/ConsoleKernel.java`, `MigrationCommandTest.java`, `Javelin.java`, `Config`, `.adapterFor`, `.output`, `TestConfig`?**
  _High betweenness centrality (0.041) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DEV`, `PROD` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.08880666049953746 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `.instance`, `Agent Notes`, `Distributing providers`, `io.javelin.core.ServiceProvider`, `Core Module`, `Container`, `Module Authoring`, `Architecture`, `Javelin.java`, `Application`, `QueryBuilder`?**
  _High betweenness centrality (0.032) - this node is a cross-community bridge._
- **Should `map` be split into smaller, more focused modules?**
  _Cohesion score 0.0684811237928007 - nodes in this community are weakly interconnected._
- **Why does `Module Authoring` connect `Module Authoring` to `ServiceProvider`, `Javelin.java`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.13675213675213677 - nodes in this community are weakly interconnected._