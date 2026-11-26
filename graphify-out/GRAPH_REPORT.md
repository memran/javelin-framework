# Graph Report - javelin-framework  (2026-11-27)

## Corpus Check
- 503 files · ~118,656 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 59 file(s) not represented in the graph (top: .stub 43, (none) 9, .xml 2)

## Summary
- 5021 nodes · 12579 edges · 283 communities (168 shown, 115 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1817 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `2b09a4af`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Application
- supplier
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
- JdkHttpServer.java
- io.javelin.core.ServiceProvider
- MigrationRunner
- Build a secure web app and API
- JdkHttpServerIntegrationTest
- Response
- Support Module
- NettyHttpServer
- Handle
- Input
- app.yaml
- map
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
- .handle
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
- duration
- otel-collector-config.yml
- Str
- environment
- Docker Compose
- JobQueue
- Database JDBC Module
- VersionCommand
- Files and caching
- .stringify
- locale
- Env
- .of
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
- NatsQueue.java
- AdultAgeRule
- Session
- .output
- JacksonJsonServiceProvider.java
- .status
- Tracer
- Installation Guide
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- XFileDisk
- depends_on
- healthcheck
- .of
- EventDispatcher
- InMemoryFileRecorder
- services
- io.javelin.core.Request
- app
- nats
- valkey
- Architecture
- PebbleViewExtensions
- ScheduleSpec
- HtmlResponse
- .handle
- Schedule.java
- MailComposer
- TestConfig
- CapturingQueue
- OpenTelemetryTracerTest.java
- CapturingQueue
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
- Security
- queue
- .resolve
- .manager
- ContainerTest
- ValkeyCache
- .load
- primary
- log
- MigrationRunner
- io.javelin.core.Database
- .handle
- Production Performance Tuning
- QueryBuilder
- Queue Adapters
- DefaultValidator
- AppProfile
- database
- Obj
- DeployServiceCommand.java
- Override
- session
- LimitedInputStream
- ExecutionRecord
- ObservabilityMiddleware
- TestConfig
- security
- HealthRegistry
- .text
- Scheduler Module
- ConsoleKernel
- security
- cache
- StubSession
- cors
- cache
- Storage Module
- StubSession
- scheduler
- StructuredLogger
- scheduler
- CronExpression
- Config
- SecurityExampleController
- UploadedFile
- Cache
- .middlewareServesHealthReadyAndMetrics
- JdbcDatabaseServiceProvider
- io.javelin.core.Response
- otel-collector
- Mail Module
- MailManager
- SmtpTransport.java
- YamlConfig
- MetricsRegistry
- InMemorySchedulerLock
- CorePerformanceRegressionTest
- Exception Module
- MigrateCommand
- Cache Module
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- Config Module
- tutorials/README.md
- RecordingLogger
- .metricsResponse
- aggregate-performance.py
- errors
- io.javelin.core.Env
- Production deployment
- mail
- mail
- .register
- Provider Loader Module
- Attachment
- MailSubmissionLogger
- observability
- AiGenerateModuleCommand
- CircularA
- session
- Observability Module
- DefaultJsonMapper
- QueuedEvent
- provider
- HTTP Netty Module
- Javelin Docs
- .initChannel
- Status
- cors
- errors
- observability
- NoopTracer
- .typedCacheAccessConvertsJsonBackToTheRequestedType
- HTTP Common Module
- server
- Config
- HTTP Undertow Module
- SchedulerLock
- NoopLogger
- com.fasterxml.jackson.databind.ObjectMapper
- Database

## God Nodes (most connected - your core abstractions)
1. `Application` - 85 edges
2. `Response` - 84 edges
3. `Request` - 83 edges
4. `Collection` - 63 edges
5. `CommandContext` - 60 edges
6. `Router` - 53 edges
7. `ServiceProvider` - 52 edges
8. `Config` - 44 edges
9. `Handle` - 43 edges
10. `JobQueue` - 43 edges

## Surprising Connections (you probably didn't know these)
- `Current MVP Boundaries` --references--> `ServiceProvider`  [INFERRED]
  agents.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Production validation` --references--> `ProductionValidator`  [INFERRED]
  docs/modules/deployment.md → modules/javelin-deployment/src/main/java/io/javelin/deployment/ProductionValidator.java
- `Endpoints` --references--> `ObservabilityServiceProvider`  [INFERRED]
  docs/modules/observability.md → modules/javelin-observability/src/main/java/io/javelin/observability/ObservabilityServiceProvider.java
- `Tracing` --references--> `Tracer`  [INFERRED]
  docs/modules/observability.md → modules/javelin-observability/src/main/java/io/javelin/observability/Tracer.java
- `Notes` --references--> `ValkeyCacheServiceProvider`  [INFERRED]
  docs/modules/cache.md → modules/javelin-cache-valkey/src/main/java/io/javelin/cache/valkey/ValkeyCacheServiceProvider.java

## Import Cycles
- None detected.

## Communities (283 total, 115 thin omitted)

### Community 0 - "Application"
Cohesion: 0.05
Nodes (25): JavelinConsole, ConfigServiceProvider, JavelinConsole, RouteListCommand, ServeCommand, Application, ProviderSorter, ServiceProvider (+17 more)

### Community 4 - "Override"
Cohesion: 0.16
Nodes (4): Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.06
Nodes (11): BenchCommand, DeployNativeConfigCommand, DeployValidateCommand, DoctorCommand, MakeMiddlewareCommand, QueueStatusCommand, QueueWorkCommand, ScheduleRunCommand (+3 more)

### Community 9 - "CommandContext"
Cohesion: 0.05
Nodes (18): AbstractCommand, CliException, CommandContext, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, CacheClearCommand (+10 more)

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.18
Nodes (4): AutomationExampleController, BackendController, QueueExampleController, UserRegisteredEvent

### Community 13 - ".text"
Cohesion: 0.13
Nodes (4): Router, Routes, HttpKernelTest, RouterTest

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (4): StaticAssetResolver, ByteRange, EtagEntry, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.22
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - ".handle"
Cohesion: 0.11
Nodes (6): RequiresAuthenticationMiddleware, RequestAttributesTest, AuthorizationMiddlewareTest, CsrfMiddleware, CorsAndCsrfTest, SessionAuthMiddlewareTest

### Community 21 - "Javelin.java"
Cohesion: 0.05
Nodes (28): AppServiceProvider, Main, Phase 2: Boot, Startup Time, Why startup is fast, Adding to the hardcoded provider list, Config-List Provider Loading, Integrating with Starter (+20 more)

### Community 22 - "Request"
Cohesion: 0.05
Nodes (16): HomeController, UserController, Layer 1 — Contracts, Cookies and sessions, DevProfileMiddleware, ExceptionHandler, HttpKernel, Middleware (+8 more)

### Community 25 - "Model"
Cohesion: 0.11
Nodes (5): Model overridable methods, Model, ModelTest, SampleHydratedUser, SampleUser

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (42): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+34 more)

### Community 28 - "Core Module"
Cohesion: 0.12
Nodes (16): Config, Core Module, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HttpKernel, HttpMethod (+8 more)

### Community 30 - "ProviderRegistry"
Cohesion: 0.06
Nodes (11): ProviderInstallCommand, ProviderListCommand, JavelinVersion, ProviderConflict, ProviderManifest, ProviderRegistry, ProviderVersion, ProviderRegistryTest (+3 more)

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.10
Nodes (8): Example, Javelin Support, Public API, Arr, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.08
Nodes (14): Application profile, HTTP driver selection, Javelin, JavelinStarterServiceProvider, Starter Module, ValidationRuleLoader, WorkspaceDefaults, JavelinStarterServiceProvider (+6 more)

### Community 34 - "JdkHttpServer.java"
Cohesion: 0.12
Nodes (3): JdkHttpServer, RequestRejected, UndertowHttpServer

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.06
Nodes (31): Service provider, Cookie-backed sessions, CORS, CSRF, HSTS and CSP, PasswordHasher, RateLimitMiddleware, RequestSizeLimitMiddleware (+23 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.12
Nodes (9): Backward compatibility, Database Migration Module, Dialects, MigrationRunner, MigrationDialect, MigrationRunner, MigrationFile, MigrationRecord (+1 more)

### Community 37 - "Build a secure web app and API"
Cohesion: 0.09
Nodes (13): Notes, 1. Enable the providers, 2. Build and start the demo, 4. Validation and error rendering, 5. JWT for the API, 6. CORS preflight, 7. Secrets, Build a secure web app and API (+5 more)

### Community 39 - "Response"
Cohesion: 0.07
Nodes (6): DefaultExceptionHandler, HtmlResponse, JsonResponse, RedirectResponse, Response, ValidationErrors

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 42 - "Handle"
Cohesion: 0.06
Nodes (9): 3. Browser login with CSRF, What happens on a bad CSRF token, Ai, Handle, Message, Reply, Http, Handle (+1 more)

### Community 44 - "app.yaml"
Cohesion: 0.14
Nodes (13): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+5 more)

### Community 46 - "StorageServiceProvider"
Cohesion: 0.21
Nodes (3): StorageConfig, DiskConfig, StorageServiceProvider

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.11
Nodes (6): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, PasswordHashStrategyTest

### Community 51 - "docker-app.yaml"
Cohesion: 0.11
Nodes (18): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+10 more)

### Community 52 - "with"
Cohesion: 0.08
Nodes (27): runs-on, steps, JAVELIN_NATS_URL, JAVELIN_RABBITMQ_URI, jobs, broker-tests, verify, name (+19 more)

### Community 53 - "Javelin Framework"
Cohesion: 0.08
Nodes (24): Architecture at a glance, Boot Flow, Build And Test, Cache And Queues, CLI, Database, Documentation, Example Routes (+16 more)

### Community 54 - "Config"
Cohesion: 0.10
Nodes (8): CaffeineCacheConfig, Config, DatabaseConfig, Connection, DatabaseConfigTest, ExceptionConfig, HttpAdapterConfig, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.20
Nodes (10): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Logging And Error Handling, Migration Strategy, Performance Benchmarks, Reverse Proxy (+2 more)

### Community 56 - "Disk"
Cohesion: 0.11
Nodes (5): Disk, DownloadPolicy, StorageManager, StoredFile, Storage

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
Cohesion: 0.08
Nodes (11): HTTP JDK Module, JdkHttpServiceProvider, Notes, Configuration, Configuration, HttpServerAdapter, HttpServerDriverProvider, HttpServerDriverProviderTest (+3 more)

### Community 61 - "Json"
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

### Community 68 - ".handle"
Cohesion: 0.17
Nodes (5): Cookie, CookieAndSessionContractTest, SessionConfig, SessionMiddleware, SessionMiddlewareTest

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
Cohesion: 0.16
Nodes (7): Cache Caffeine Module, Configuration, Developer API, CaffeineCache, Expiry<String, Entry>, Entry, CaffeineCacheTest

### Community 79 - "Logger"
Cohesion: 0.14
Nodes (8): Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger, Log, Logger

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (42): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+34 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 83 - "duration"
Cohesion: 0.07
Nodes (5): InvoiceJob, SendEmailJob, Json, EmailAddress, NatsQueueIntegrationTest

### Community 84 - "otel-collector-config.yml"
Cohesion: 0.11
Nodes (18): verbosity, exporters, debug, endpoint, endpoint, protocols, traces, processors (+10 more)

### Community 87 - "environment"
Cohesion: 0.12
Nodes (16): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+8 more)

### Community 88 - "Docker Compose"
Cohesion: 0.22
Nodes (9): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running (+1 more)

### Community 89 - "JobQueue"
Cohesion: 0.11
Nodes (4): JobQueue, QueueWorker, QueueWorkerConfig, Queue

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 92 - "Files and caching"
Cohesion: 0.20
Nodes (8): 1. Disks and cache are configured explicitly, 2. Write and read files, 3. Private downloads, 4. S3 or MinIO, 5. Caching, Files and caching, Notes, AuthenticatedDownloadPolicy

### Community 94 - "locale"
Cohesion: 0.08
Nodes (8): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, MakeProviderCommand, NewCommand, FileGenerator, Names, TemplateEngine

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "DefaultEventDispatcher"
Cohesion: 0.26
Nodes (4): DefaultEventDispatcher, EventListener, EventDispatcherTest, UserRegistered

### Community 101 - "private"
Cohesion: 0.12
Nodes (17): private, public, temporary, domain, driver, public, root, domain (+9 more)

### Community 102 - "Scheduler"
Cohesion: 0.16
Nodes (6): Scheduler, SchedulerConfig, SchedulerLock, Interval, TaskDefinition, SchedulerTest

### Community 104 - ".resolve"
Cohesion: 0.16
Nodes (4): ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolver, TrustedProxyResolverTest

### Community 106 - "JsonLogEncoder"
Cohesion: 0.21
Nodes (4): JsonLogEncoder, RequestLoggingMiddleware, JsonLogEncoder, JsonLogEncoderTest

### Community 108 - "io.javelin.core.Application"
Cohesion: 0.05
Nodes (13): web, 1. Configure, ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, EventServiceProvider, MailServiceProvider, DbPoolStats, ObservabilityServiceProvider (+5 more)

### Community 114 - ".sustainsLoopbackThroughputThroughTheJdkAdapter"
Cohesion: 0.06
Nodes (16): Benchmarks, CI/CD, Deployment Module, GraalVM metadata, Linux service, Performance regression suite, Production validation, Stateless deployments (+8 more)

### Community 123 - "NatsQueue.java"
Cohesion: 0.10
Nodes (5): NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, NatsQueue

### Community 125 - "Session"
Cohesion: 0.08
Nodes (5): Session, SessionStore, StubSession, InMemorySessionStore, MemorySession

### Community 128 - ".status"
Cohesion: 0.09
Nodes (10): HtmlResponse, JsonResponse, RedirectResponse, Response, Response instance methods, Response static factories, DefaultExceptionHandlerTest, ResponseTest, ValidationErrorsTest, StaticAssetResolverTest (+2 more)

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

### Community 136 - "depends_on"
Cohesion: 0.22
Nodes (9): depends_on, condition, db, nats, otel-collector, valkey, condition, condition (+1 more)

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - ".of"
Cohesion: 0.08
Nodes (18): Queue contracts, Errors, Events Module, Queued listeners, Service provider, Configuration, Contracts, Developer API (+10 more)

### Community 141 - "services"
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 142 - "io.javelin.core.Request"
Cohesion: 0.08
Nodes (5): StorageExampleController, ErrorPageResolver, FileErrorPageResolver, SessionAuthMiddleware, SessionPrincipalResolver

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
Nodes (17): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Layer 0 — Foundation, Layer 2 — Adapters (+9 more)

### Community 147 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (16): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Service provider (+8 more)

### Community 148 - "ScheduleSpec"
Cohesion: 0.18
Nodes (5): Cron, Daily, Monthly, Weekly, ScheduleSpec

### Community 149 - "HtmlResponse"
Cohesion: 0.23
Nodes (3): View, ViewRenderer, ViewTest

### Community 150 - ".handle"
Cohesion: 0.16
Nodes (8): Bearer authentication and authorization, Middleware, Bearer authentication, JWT adapter, Authenticated, BearerAuthMiddleware, BearerTokenResolver, BearerAuthMiddlewareTest

### Community 156 - "CapturingQueue"
Cohesion: 0.23
Nodes (3): QueueFacadeTest, CapturingQueue, SendEmailJob

### Community 157 - "RabbitMqQueue"
Cohesion: 0.18
Nodes (4): RabbitMqQueue, Claim, RabbitMqQueueIntegrationTest, ClaimedJob

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
Cohesion: 0.22
Nodes (9): server, host, idle_timeout_seconds, port, request_timeout_seconds, static_after_routes, static_cache_immutable, static_cache_seconds (+1 more)

### Community 167 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 172 - "private"
Cohesion: 0.12
Nodes (17): private, public, temporary, domain, driver, public, root, domain (+9 more)

### Community 175 - "queue"
Cohesion: 0.11
Nodes (19): dir, queue, driver, file, retry, stream, url, valkey (+11 more)

### Community 176 - ".resolve"
Cohesion: 0.16
Nodes (3): JwtTokenIssuer, JwtTokenResolver, JwtTokenResolverTest

### Community 178 - "ContainerTest"
Cohesion: 0.19
Nodes (8): ContainerTest, Alpha, Ambiguous, CloseableResource, Dependency, Failing, Service, Zeta

### Community 179 - "ValkeyCache"
Cohesion: 0.27
Nodes (3): InMemoryCache, Cache, ValkeyCache

### Community 180 - ".load"
Cohesion: 0.31
Nodes (3): DotenvEnv, Entry, DotenvEnvTest

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 183 - "MigrationRunner"
Cohesion: 0.18
Nodes (4): MigrateCommand, MigrateRollbackCommand, MigrationCommandTest, MigrationRunner

### Community 185 - "io.javelin.core.Database"
Cohesion: 0.12
Nodes (4): DatabaseManager, JdbcDatabaseServiceProviderTest, DatabaseExecutionHistory, DatabaseSchedulerLock

### Community 186 - ".handle"
Cohesion: 0.16
Nodes (4): ConfigurableExceptionHandler, SensitiveDataRedactor, ConfigurableExceptionHandlerTest, SensitiveDataRedactorTest

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 188 - "QueryBuilder"
Cohesion: 0.15
Nodes (4): CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder

### Community 189 - "Queue Adapters"
Cohesion: 0.13
Nodes (13): Broker-backed integration tests, Choosing an adapter, File (`javelin-queue-file`), NATS JetStream (`javelin-queue-nats`), Queue Adapters, RabbitMQ (`javelin-queue-rabbitmq`), Valkey / Redis (`javelin-queue-valkey`), 1. Configure the queue (+5 more)

### Community 190 - "DefaultValidator"
Cohesion: 0.20
Nodes (8): Relationship to `javelin-support`, Service provider, Supported rules, Validation Module, Validator, DefaultValidator, Validator, DefaultValidatorTest

### Community 191 - "AppProfile"
Cohesion: 0.25
Nodes (7): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD

### Community 192 - "database"
Cohesion: 0.25
Nodes (8): database, connections, default, migrations_dir, query, log_all, slow_ms, timing

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 198 - "ExecutionRecord"
Cohesion: 0.18
Nodes (4): DemoScheduleJob, ExecutionHistory, ExecutionRecord, InMemoryExecutionHistory

### Community 201 - "security"
Cohesion: 0.14
Nodes (14): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+6 more)

### Community 202 - "HealthRegistry"
Cohesion: 0.23
Nodes (3): Health, HealthIndicator, HealthRegistry

### Community 204 - "Scheduler Module"
Cohesion: 0.20
Nodes (9): Container, Container, Worker, Configuration, Developer API, Dispatching to queues, Reliability, Scheduler Module (+1 more)

### Community 205 - "ConsoleKernel"
Cohesion: 0.08
Nodes (10): CommandRegistry, AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry (+2 more)

### Community 206 - "security"
Cohesion: 0.15
Nodes (13): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+5 more)

### Community 208 - "cache"
Cohesion: 0.33
Nodes (6): cache, caffeine, driver, url, default_ttl_seconds, maximum_size

### Community 210 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 213 - "cache"
Cohesion: 0.40
Nodes (5): cache, caffeine, driver, default_ttl_seconds, maximum_size

### Community 214 - "Storage Module"
Cohesion: 0.29
Nodes (5): Configuration, Developer API, File recorder, Service provider, Storage Module

### Community 216 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 217 - "StructuredLogger"
Cohesion: 0.20
Nodes (3): Log, Logger, StructuredLogger

### Community 218 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 222 - "UploadedFile"
Cohesion: 0.07
Nodes (5): MultipartData, PartDisposition, UploadedFile, RequestMultipartTest, File

### Community 225 - "JdbcDatabaseServiceProvider"
Cohesion: 0.20
Nodes (3): JdbcDatabaseServiceProvider, NoopLogger, MigrationDialects

### Community 226 - "io.javelin.core.Response"
Cohesion: 0.12
Nodes (4): RequestLoggingMiddleware, RequestLoggingMiddlewareTest, CorsConfig, CorsMiddleware

### Community 227 - "otel-collector"
Cohesion: 0.33
Nodes (6): command, image, ports, restart, volumes, otel-collector

### Community 228 - "Mail Module"
Cohesion: 0.33
Nodes (6): Address validation, Configuration, Developer API, Mail Module, Queued delivery and failures, Test transport

### Community 229 - "MailManager"
Cohesion: 0.26
Nodes (3): MailManager, MailTransport, Mail

### Community 230 - "SmtpTransport.java"
Cohesion: 0.12
Nodes (3): SmtpConfig, SmtpTransport, Authenticator

### Community 231 - "YamlConfig"
Cohesion: 0.17
Nodes (4): YamlConfig, TypeReference<>, TypeReference<>, YamlConfigTest

### Community 234 - "CorePerformanceRegressionTest"
Cohesion: 0.27
Nodes (7): CorePerformanceRegressionTest, DeepService, FactoryService, InstanceService, LeafService, MiddleService, SingletonService

### Community 235 - "Exception Module"
Cohesion: 0.40
Nodes (5): ConfigurableExceptionHandler, Configuration, Error pages, Exception Module, Service provider

### Community 237 - "Cache Module"
Cohesion: 0.50
Nodes (4): Cache, Cache Module, CacheServiceProvider, Notes

### Community 238 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 239 - "Config Module"
Cohesion: 0.25
Nodes (8): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, Secret providers, YamlConfig

### Community 241 - "tutorials/README.md"
Cohesion: 0.12
Nodes (11): 1. Compose and send, 2. Plain text, attachments and inline images, 3. Production SMTP, 4. Reliability and safety, Sending mail, Tutorials, 2. Schedule tasks, 3. Dispatch events (+3 more)

### Community 245 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 247 - "Production deployment"
Cohesion: 0.13
Nodes (13): 1. Observe the application, 2. Validate configuration, 3. Package and run, 4. Linux service, 5. Benchmark before release, 6. CI/CD and native images, Production deployment, 1. Generate a provider (+5 more)

### Community 248 - "mail"
Cohesion: 0.25
Nodes (8): mail, driver, from, retry, test, backoff_ms, max_attempts, dir

### Community 249 - "mail"
Cohesion: 0.25
Nodes (8): mail, driver, from, retry, test, backoff_ms, max_attempts, dir

### Community 251 - "Provider Loader Module"
Cohesion: 0.17
Nodes (7): Configuration, Diagnostics, Distribution, Manifest, Provider Loader Module, Service provider, ProviderRegistryServiceProvider

### Community 253 - "MailSubmissionLogger"
Cohesion: 0.19
Nodes (4): Logging, MailMessage, MailSubmissionLogger, MailTest

### Community 255 - "observability"
Cohesion: 0.33
Nodes (6): observability, enabled, health_path, metrics_path, ready_path, tracing

### Community 258 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 259 - "Observability Module"
Cohesion: 0.33
Nodes (5): Endpoints, Health checks, Metrics, Observability Module, Tracing

### Community 262 - "provider"
Cohesion: 0.40
Nodes (5): provider, directory, disabled, enabled, strict

### Community 263 - "HTTP Netty Module"
Cohesion: 0.67
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 264 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 266 - "Status"
Cohesion: 0.67
Nodes (3): Status, DRAFT, PUBLISHED

### Community 267 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 268 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 269 - "observability"
Cohesion: 0.33
Nodes (6): observability, enabled, health_path, metrics_path, ready_path, tracing

### Community 273 - ".typedCacheAccessConvertsJsonBackToTheRequestedType"
Cohesion: 0.19
Nodes (6): Model, Model instance methods, Model static methods, ValkeyCacheTest, Cache, User

### Community 275 - "HTTP Common Module"
Cohesion: 0.20
Nodes (8): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, TrustedProxyResolver, HttpTimeoutConfig, EtagStats

### Community 276 - "server"
Cohesion: 0.50
Nodes (4): server, host, port, static_after_routes

### Community 278 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

### Community 286 - "Database"
Cohesion: 0.11
Nodes (9): Core interfaces you can implement, Database, QueryBuilder, TransactionCallback, Behavior, Configuration, Database Queue Module, Database (+1 more)

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 1148 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **115 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `.instance`, `io.javelin.core.ServiceProvider`, `cli/ConsoleKernel.java`, `CommandContext`, `io.javelin.core.Application`, `map`, `Scheduler Module`, `.registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot`, `Container`, `map`, `Javelin.java`, `Config`, `MigrationRunner`, `.register`, `HttpServerAdapter`, `optional`, `.output`, `AppProfile`?**
  _High betweenness centrality (0.038) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Application` be split into smaller, more focused modules?**
  _Cohesion score 0.047497446373850866 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `Application` to `.instance`, `Agent Notes`, `io.javelin.core.ServiceProvider`, `HttpServerAdapter`, `Module Authoring`, `Javelin.java`, `Request`, `Production deployment`, `Javelin Framework`, `Provider Loader Module`, `Core Module`, `Database`?**
  _High betweenness centrality (0.033) - this node is a cross-community bridge._
- **Should `cli/ConsoleKernel.java` be split into smaller, more focused modules?**
  _Cohesion score 0.05501165501165501 - nodes in this community are weakly interconnected._
- **Why does `Request` connect `Request` to `.status`, `io.javelin.core.Response`, `io.javelin.core.ServiceProvider`, `.handle`, `Response`, `io.javelin.core.Request`, `.text`, `map`, `.handle`, `duration`, `UploadedFile`, `NettyHttpServerIntegrationTest.java`, `.handle`, `Route`, `com.fasterxml.jackson.databind.ObjectMapper`, `map`?**
  _High betweenness centrality (0.031) - this node is a cross-community bridge._
- **Should `CommandContext` be split into smaller, more focused modules?**
  _Cohesion score 0.053313587560162905 - nodes in this community are weakly interconnected._