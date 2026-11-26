# Graph Report - javelin-framework  (2026-11-27)

## Corpus Check
- 502 files · ~117,943 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 57 file(s) not represented in the graph (top: .stub 41, (none) 9, .xml 2)

## Summary
- 4978 nodes · 12511 edges · 289 communities (168 shown, 121 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1812 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `1554f2aa`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Override
- locale
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
- Response
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
- HtmlResponse
- Support Module
- NettyHttpServer
- Handle
- Input
- app.yaml
- XFileDisk
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
- docs/README.md
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
- list
- JobQueue
- Str
- environment
- Docker Compose
- InMemoryQueue
- Database JDBC Module
- VersionCommand
- Authenticated
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
- AppServiceProvider.java
- NatsQueue.java
- AdultAgeRule
- Session
- .output
- .headers
- Tracer
- Installation Guide
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- depends_on
- healthcheck
- .of
- NettyHttpServer
- org.junit.jupiter.api.Test
- services
- Request
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
- DefaultExceptionHandler
- queue
- .resolve
- .manager
- CloseableResource
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
- Full Worked Example
- cache
- StubSession
- cors
- cache
- StubSession
- scheduler
- StructuredLogger
- scheduler
- CronExpression
- Config
- ProviderRegistryTest
- UploadedFile
- Cache
- .middlewareServesHealthReadyAndMetrics
- JdbcDatabaseServiceProvider
- io.javelin.core.Response
- ProviderVersion
- ProviderInstallCommand
- MailManager
- SmtpTransport.java
- YamlConfig
- MetricsRegistry
- InMemorySchedulerLock
- CorePerformanceRegressionTest
- .composerViewAndQueueDispatch
- MigrateCommand
- .run
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- Config Module
- EventServiceProvider
- RecordingLogger
- StartupProfile
- aggregate-performance.py
- errors
- .get
- tutorials/README.md
- mail
- mail
- RequiresAuthenticationMiddleware
- Provider Loader Module
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
- profile.md
- ProviderListCommand
- WorkspaceAgeRule
- Distributing providers
- cors
- errors
- observability
- Sending mail
- NoopTracer
- .typedCacheAccessConvertsJsonBackToTheRequestedType
- HTTP Common Module
- server
- Config
- HTTP Undertow Module
- MakeServiceCommand.java
- SchedulerLock
- NoopLogger
- JavelinVersion
- ProviderConflict
- FileErrorPageResolver
- com.fasterxml.jackson.databind.ObjectMapper
- Database
- io.javelin.core.AppProfile

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
- `Trusted proxy configuration` --references--> `JdkHttpServiceProvider`  [INFERRED]
  docs/deployment.md → modules/javelin-http-jdk/src/main/java/io/javelin/http/jdk/JdkHttpServiceProvider.java
- `Current MVP Boundaries` --references--> `ServiceProvider`  [INFERRED]
  agents.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Router` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java
- `Routes` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java
- `Notes` --references--> `ServiceProvider`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java

## Import Cycles
- None detected.

## Communities (289 total, 121 thin omitted)

### Community 0 - "Override"
Cohesion: 0.12
Nodes (9): ProviderSorter, ServiceProvider, ProviderSorterTest, CycleA, CycleB, FirstProvider, NeedsFirstProvider, SecondProvider (+1 more)

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.06
Nodes (11): BenchCommand, DeployNativeConfigCommand, DeployServiceCommand, DeployValidateCommand, DoctorCommand, MakeMiddlewareCommand, QueueStatusCommand, QueueWorkCommand (+3 more)

### Community 9 - "CommandContext"
Cohesion: 0.05
Nodes (18): AbstractCommand, CliException, CommandContext, AiChatCommand, AiExplainCommand, AiGenerateModuleCommand, AiInstallCommand, BuildCommand (+10 more)

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.13
Nodes (7): AutomationExampleController, BackendController, QueueExampleController, StorageExampleController, UserRegisteredEvent, InvoiceJob, SendEmailJob

### Community 13 - ".text"
Cohesion: 0.13
Nodes (4): Router, ResolvedRoute, HttpKernelTest, RouterTest

### Community 15 - "StaticAssetResolver"
Cohesion: 0.17
Nodes (5): HttpAdapterConfig, StaticAssetResolver, ByteRange, EtagEntry, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.17
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - ".handle"
Cohesion: 0.13
Nodes (4): SecurityExampleController, RequestAttributesTest, CsrfMiddleware, SessionAuthMiddlewareTest

### Community 21 - "Javelin.java"
Cohesion: 0.07
Nodes (29): Adding to the hardcoded provider list, Integrating with Starter, Provider registration order, Registration order matters, ProviderLoader, HSTS and CSP, PasswordHasher, RateLimitMiddleware (+21 more)

### Community 22 - "Response"
Cohesion: 0.04
Nodes (18): Layer 0 — Foundation, Layer 1 — Contracts, Layer 2 — Adapters, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Dependency Graph, TrustedProxyResolver, Trusted proxy configuration (+10 more)

### Community 25 - "Model"
Cohesion: 0.08
Nodes (7): Model overridable methods, Behavior, Configuration, Database Queue Module, Database, Model, SampleHydratedUser

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (42): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+34 more)

### Community 28 - "Core Module"
Cohesion: 0.12
Nodes (17): Bearer authentication and authorization, Config, Core Module, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HttpKernel (+9 more)

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.16
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.07
Nodes (25): ConfigurableExceptionHandler, Configuration, Error pages, Exception Module, Service provider, CORS, Application profile, SecretsServiceProvider (+17 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.12
Nodes (9): Backward compatibility, Database Migration Module, Dialects, MigrationRunner, MigrationDialect, MigrationRunner, MigrationFile, MigrationRecord (+1 more)

### Community 37 - "Build a secure web app and API"
Cohesion: 0.14
Nodes (6): Notes, 7. Secrets, CompositeSecretProvider, FileSecretProvider, SecretProvider, SecretProviderTest

### Community 39 - "HtmlResponse"
Cohesion: 0.18
Nodes (3): JsonResponse, ValidationErrors, ValidationErrorsTest

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 42 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 44 - "app.yaml"
Cohesion: 0.14
Nodes (13): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+5 more)

### Community 46 - "XFileDisk"
Cohesion: 0.09
Nodes (5): InMemoryFileRecorder, StorageConfig, DiskConfig, StorageServiceProvider, XFileDisk

### Community 48 - "Container"
Cohesion: 0.14
Nodes (10): Container, Binding, ContainerTest, Alpha, Ambiguous, Dependency, Failing, Service (+2 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.07
Nodes (7): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, PasswordHashStrategyTest, Security

### Community 51 - "docker-app.yaml"
Cohesion: 0.11
Nodes (18): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+10 more)

### Community 52 - "with"
Cohesion: 0.09
Nodes (21): jobs, verify, name, on, pull_request, push, branches, branches (+13 more)

### Community 53 - "Javelin Framework"
Cohesion: 0.09
Nodes (23): Architecture at a glance, Boot Flow, Build And Test, Cache And Queues, CLI, Database, Documentation, Example Routes (+15 more)

### Community 54 - "Config"
Cohesion: 0.09
Nodes (8): Service provider, CaffeineCacheConfig, Config, DatabaseConfig, Connection, DatabaseConfigTest, ValidationIssue, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.20
Nodes (10): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Logging And Error Handling, Migration Strategy, Performance Benchmarks, Reverse Proxy (+2 more)

### Community 56 - "Disk"
Cohesion: 0.11
Nodes (5): Disk, StorageManager, StoredFile, Storage, StorageTest

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
Nodes (11): HttpServerAdapter, JdkHttpServiceProvider, Configuration, Configuration, ServeCommand, HttpServerAdapter, HttpServerDriverProvider, HttpServerDriverProviderTest (+3 more)

### Community 61 - "Json"
Cohesion: 0.15
Nodes (9): Json, API, Backward compatibility, Configuration, JSON Jackson Module, Registration, Json, DefaultJsonMapper (+1 more)

### Community 62 - "Override"
Cohesion: 0.21
Nodes (3): MigrationDialectsTest, RecordingDatabase, QueryBuilder

### Community 63 - "pebble"
Cohesion: 0.12
Nodes (15): driver, pebble, allow_override_core_operators, auto_escaping, cache, default_escaping_strategy, default_locale, greedy_match_method (+7 more)

### Community 64 - "docs/README.md"
Cohesion: 0.14
Nodes (3): Javelin Docs, Module Index, Reading Order

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
Cohesion: 0.12
Nodes (17): Application Flow, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development), Error Handling (+9 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.17
Nodes (34): io.javelin:javelin-cache, io.javelin:javelin-cache-caffeine, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration (+26 more)

### Community 78 - "CaffeineCache"
Cohesion: 0.16
Nodes (7): Cache Caffeine Module, Configuration, Developer API, CaffeineCache, Expiry<String, Entry>, Entry, CaffeineCacheTest

### Community 79 - "Logger"
Cohesion: 0.15
Nodes (8): Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger, Log, Logger

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (36): Adding Tests, Adding the module to the parent POM, `bind(A, B)` — reusable factory, Binding Strategies, Checklist for new modules, Config-Driven Modules, Config-List Provider Loading, Creating a New Interface (+28 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 84 - "JobQueue"
Cohesion: 0.21
Nodes (4): JobQueue, Queue, QueueFacadeTest, SendEmailJob

### Community 87 - "environment"
Cohesion: 0.14
Nodes (14): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+6 more)

### Community 88 - "Docker Compose"
Cohesion: 0.10
Nodes (16): AppServiceProvider, Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration (+8 more)

### Community 89 - "InMemoryQueue"
Cohesion: 0.10
Nodes (6): JobHandlerRegistry, Registration, QueueWorker, QueueWorkerConfig, QueueWorkerTest, InMemoryQueue

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 92 - "Authenticated"
Cohesion: 0.12
Nodes (10): JWT adapter, Configuration, File recorder, Safety, Service provider, Storage Module, 3. Private downloads, Authenticated (+2 more)

### Community 94 - "locale"
Cohesion: 0.08
Nodes (8): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, MakeProviderCommand, NewCommand, FileGenerator, Names, TemplateEngine

### Community 96 - "Application"
Cohesion: 0.10
Nodes (5): JavelinConsole, JavelinConsole, RouteListCommand, ServeCommand, Application

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "DefaultEventDispatcher"
Cohesion: 0.12
Nodes (6): DefaultEventDispatcher, EventDispatcher, EventListener, Events, EventDispatcherTest, UserRegistered

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
Nodes (11): Developer API, CaffeineCacheServiceProvider, JacksonJsonServiceProvider, MailServiceProvider, DbPoolStats, ObservabilityServiceProvider, PoolStats, NatsQueueServiceProviderTest (+3 more)

### Community 114 - ".sustainsLoopbackThroughputThroughTheJdkAdapter"
Cohesion: 0.06
Nodes (16): Benchmarks, CI/CD, Deployment Module, GraalVM metadata, Linux service, Performance regression suite, Production validation, Stateless deployments (+8 more)

### Community 123 - "NatsQueue.java"
Cohesion: 0.10
Nodes (5): NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, NatsQueue

### Community 125 - "Session"
Cohesion: 0.05
Nodes (12): Cookies and sessions, Cookie-backed sessions, Cookie, Session, SessionStore, CookieAndSessionContractTest, StubSession, InMemorySessionStore (+4 more)

### Community 128 - ".headers"
Cohesion: 0.13
Nodes (7): HtmlResponse, JsonResponse, RedirectResponse, Response, Response instance methods, Response static factories, ResponseTest, StaticAssetResolverTest, CorsAndCsrfTest

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

### Community 138 - ".of"
Cohesion: 0.11
Nodes (12): Queue contracts, Configuration, Contracts, Developer API, Contracts (javelin-core), Queue Module, Reliability, Job (+4 more)

### Community 140 - "org.junit.jupiter.api.Test"
Cohesion: 0.11
Nodes (6): CommandGenerationTest, AuthorizationMiddlewareTest, HttpTimeoutConfig, HttpTimeoutConfigTest, RequestLoggingMiddlewareTest, PebbleViewRendererTest

### Community 141 - "services"
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 142 - "Request"
Cohesion: 0.07
Nodes (7): UserController, web, Key Design Decisions, Request, MultipartData, PartDisposition, RequestInputTest

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
Cohesion: 0.18
Nodes (11): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Middleware pipeline, Module Boundaries, Module Export Diagram (+3 more)

### Community 147 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (17): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Service provider (+9 more)

### Community 148 - "ScheduleSpec"
Cohesion: 0.18
Nodes (5): Cron, Daily, Monthly, Weekly, ScheduleSpec

### Community 149 - "HtmlResponse"
Cohesion: 0.21
Nodes (4): HomeController, HtmlResponse, View, ViewTest

### Community 150 - ".handle"
Cohesion: 0.21
Nodes (4): Bearer authentication, BearerAuthMiddleware, BearerTokenResolver, BearerAuthMiddlewareTest

### Community 157 - "RabbitMqQueue"
Cohesion: 0.20
Nodes (3): RabbitMqQueue, RabbitMqQueueIntegrationTest, ClaimedJob

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

### Community 179 - "ValkeyCache"
Cohesion: 0.27
Nodes (3): InMemoryCache, Cache, ValkeyCache

### Community 180 - ".load"
Cohesion: 0.22
Nodes (4): DotenvEnv, Entry, DotenvEnvTest, YamlConfigTest

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 183 - "MigrationRunner"
Cohesion: 0.22
Nodes (3): MigrateCommand, MigrateRollbackCommand, MigrationRunner

### Community 185 - "io.javelin.core.Database"
Cohesion: 0.11
Nodes (4): DatabaseManager, JdbcDatabaseServiceProviderTest, DatabaseExecutionHistory, DatabaseSchedulerLock

### Community 186 - ".handle"
Cohesion: 0.14
Nodes (5): ConfigurableExceptionHandler, ErrorPageResolver, SensitiveDataRedactor, ConfigurableExceptionHandlerTest, SensitiveDataRedactorTest

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 188 - "QueryBuilder"
Cohesion: 0.10
Nodes (11): Core interfaces you can implement, QueryBuilder, ModelTest, CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder, SampleUser (+3 more)

### Community 189 - "Queue Adapters"
Cohesion: 0.13
Nodes (13): Broker-backed integration tests, Choosing an adapter, File (`javelin-queue-file`), NATS JetStream (`javelin-queue-nats`), Queue Adapters, RabbitMQ (`javelin-queue-rabbitmq`), Valkey / Redis (`javelin-queue-valkey`), 1. Configure the queue (+5 more)

### Community 190 - "DefaultValidator"
Cohesion: 0.18
Nodes (8): Relationship to `javelin-support`, Service provider, Supported rules, Validation Module, Validator, DefaultValidator, Validator, DefaultValidatorTest

### Community 191 - "AppProfile"
Cohesion: 0.16
Nodes (8): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, ChannelInitializer<SocketChannel>

### Community 192 - "database"
Cohesion: 0.25
Nodes (8): database, connections, default, migrations_dir, query, log_all, slow_ms, timing

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 197 - "ApplicationTest"
Cohesion: 0.15
Nodes (6): ApplicationTest, BaseProvider, DependentProvider, FirstResource, RecordingProvider, SecondResource

### Community 198 - "ExecutionRecord"
Cohesion: 0.18
Nodes (4): DemoScheduleJob, ExecutionHistory, ExecutionRecord, InMemoryExecutionHistory

### Community 199 - "TestConfig"
Cohesion: 0.24
Nodes (3): BillingProvider, CoreProvider, TestConfig

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

### Community 207 - "Full Worked Example"
Cohesion: 0.20
Nodes (10): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+2 more)

### Community 208 - "cache"
Cohesion: 0.33
Nodes (6): cache, caffeine, driver, url, default_ttl_seconds, maximum_size

### Community 210 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 213 - "cache"
Cohesion: 0.40
Nodes (5): cache, caffeine, driver, default_ttl_seconds, maximum_size

### Community 215 - "StubSession"
Cohesion: 0.17
Nodes (3): SessionAuthMiddleware, SessionPrincipalResolver, StubSession

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
Cohesion: 0.12
Nodes (3): UploadedFile, RequestMultipartTest, File

### Community 224 - ".middlewareServesHealthReadyAndMetrics"
Cohesion: 0.23
Nodes (3): ObservabilityConfig, ObservabilityTest, TestConfig

### Community 225 - "JdbcDatabaseServiceProvider"
Cohesion: 0.20
Nodes (3): JdbcDatabaseServiceProvider, NoopLogger, MigrationDialects

### Community 226 - "io.javelin.core.Response"
Cohesion: 0.10
Nodes (6): RequestLoggingMiddleware, ObservabilityMiddleware, RuntimeSnapshot, CorsConfig, CorsMiddleware, SecureHeadersMiddleware

### Community 229 - "MailManager"
Cohesion: 0.24
Nodes (3): MailManager, MailTransport, Mail

### Community 230 - "SmtpTransport.java"
Cohesion: 0.12
Nodes (3): SmtpConfig, SmtpTransport, Authenticator

### Community 234 - "CorePerformanceRegressionTest"
Cohesion: 0.32
Nodes (6): CorePerformanceRegressionTest, DeepService, FactoryService, LeafService, MiddleService, SingletonService

### Community 237 - ".run"
Cohesion: 0.19
Nodes (6): Main, Boot Sequence, Phase 1: Entry Point, Phase 2: Boot, Phase 3: Dispatch, Javelin

### Community 238 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.19
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 239 - "Config Module"
Cohesion: 0.17
Nodes (9): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, Secret providers, YamlConfig (+1 more)

### Community 241 - "EventServiceProvider"
Cohesion: 0.14
Nodes (13): Developer API, Errors, Events Module, Queued listeners, Service provider, 1. Configure, 2. Schedule tasks, 3. Dispatch events (+5 more)

### Community 245 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 247 - "tutorials/README.md"
Cohesion: 0.12
Nodes (14): 1. Disks and cache are configured explicitly, 2. Write and read files, 4. S3 or MinIO, 5. Caching, Files and caching, Notes, 1. Observe the application, 2. Validate configuration (+6 more)

### Community 248 - "mail"
Cohesion: 0.25
Nodes (8): mail, driver, from, retry, test, backoff_ms, max_attempts, dir

### Community 249 - "mail"
Cohesion: 0.25
Nodes (8): mail, driver, from, retry, test, backoff_ms, max_attempts, dir

### Community 250 - "RequiresAuthenticationMiddleware"
Cohesion: 0.10
Nodes (15): CSRF, Session authentication, 1. Enable the providers, 2. Build and start the demo, 3. Browser login with CSRF, 4. Validation and error rendering, 5. JWT for the API, 6. CORS preflight (+7 more)

### Community 251 - "Provider Loader Module"
Cohesion: 0.29
Nodes (6): Configuration, Diagnostics, Distribution, Manifest, Provider Loader Module, Service provider

### Community 253 - "MailSubmissionLogger"
Cohesion: 0.15
Nodes (9): Address validation, Configuration, Developer API, Logging, Mail Module, Queued delivery and failures, Test transport, MailMessage (+1 more)

### Community 255 - "observability"
Cohesion: 0.33
Nodes (6): observability, enabled, health_path, metrics_path, ready_path, tracing

### Community 256 - ".loadDirectory"
Cohesion: 0.24
Nodes (3): EnvSecretProvider, TypeReference<>, TypeReference<>

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

### Community 263 - "profile.md"
Cohesion: 0.33
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 266 - "Distributing providers"
Cohesion: 0.29
Nodes (6): 1. Generate a provider, 2. Enable or disable it, 3. Inspect and diagnose, 4. Install a versioned provider JAR, 5. CLI used by a distributed app, Distributing providers

### Community 267 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 268 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 269 - "observability"
Cohesion: 0.33
Nodes (6): observability, enabled, health_path, metrics_path, ready_path, tracing

### Community 270 - "Sending mail"
Cohesion: 0.33
Nodes (5): 1. Compose and send, 2. Plain text, attachments and inline images, 3. Production SMTP, 4. Reliability and safety, Sending mail

### Community 273 - ".typedCacheAccessConvertsJsonBackToTheRequestedType"
Cohesion: 0.19
Nodes (6): Model, Model instance methods, Model static methods, ValkeyCacheTest, Cache, User

### Community 275 - "HTTP Common Module"
Cohesion: 0.17
Nodes (10): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, HTTP JDK Module, JdkHttpServer, Notes (+2 more)

### Community 276 - "server"
Cohesion: 0.50
Nodes (4): server, host, port, static_after_routes

### Community 278 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

### Community 286 - "Database"
Cohesion: 0.67
Nodes (3): Database, QueryBuilder, TransactionCallback

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 1133 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **121 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Config` connect `Config` to `.instance`, `io.javelin.core.ServiceProvider`, `Override`, `io.javelin.core.AppProfile`, `ProfileConfig`, `YamlConfig`, `MapConfig`, `MapConfig`, `map`, `.registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot`, `.from`, `list`, `MigrationCommandTest.java`, `RecordingLogger`, `HttpServerAdapter`, `linkedhashmap`, `Env`?**
  _High betweenness centrality (0.033) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.12162162162162163 - nodes in this community are weakly interconnected._
- **Why does `Request` connect `Request` to `locale`, `ProfileConfig`, `NettyHttpServer`, `io.javelin.core.Request`, `.text`, `org.junit.jupiter.api.Test`, `.handle`, `HtmlResponse`, `Response`, `.handle`, `com.fasterxml.jackson.databind.ObjectMapper`, `path`, `io.javelin.core.ServiceProvider`, `HtmlResponse`, `map`, `DefaultExceptionHandler`, `Route`, `list`, `NettyHttpServerIntegrationTest.java`, `MigrationCommandTest.java`, `UploadedFile`, `io.javelin.core.Response`, `Session`?**
  _High betweenness centrality (0.027) - this node is a cross-community bridge._
- **Should `locale` be split into smaller, more focused modules?**
  _Cohesion score 0.08923076923076922 - nodes in this community are weakly interconnected._
- **Why does `Response` connect `Response` to `.headers`, `locale`, `NettyHttpServer`, `org.junit.jupiter.api.Test`, `.text`, `Request`, `.handle`, `HtmlResponse`, `.handle`, `path`, `JdkHttpServer.java`, `HtmlResponse`, `NettyHttpServer`, `map`, `DefaultExceptionHandler`, `NettyHttpServer.java`, `list`, `NettyHttpServerIntegrationTest.java`, `MigrationCommandTest.java`, `io.javelin.core.Response`, `Session`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.13675213675213677 - nodes in this community are weakly interconnected._