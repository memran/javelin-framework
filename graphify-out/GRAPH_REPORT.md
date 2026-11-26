# Graph Report - javelin-framework  (2026-11-26)

## Corpus Check
- 427 files · ~102,755 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 52 file(s) not represented in the graph (top: .stub 37, (none) 9, .xml 2)

## Summary
- 4327 nodes · 10985 edges · 241 communities (140 shown, 101 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1570 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `44fc0873`
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
- XFileDisk
- Javelin Framework
- Console Module
- PasswordHashStrategy
- docker-app.yaml
- standardcharsets
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
- docs/README.md
- Agent Notes
- .write
- ValidationRule
- optional
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
- Log
- FileQueue
- Module Authoring
- nginx
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- Str
- Application
- environment
- Docker Compose
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
- DefaultEventDispatcher
- private
- Scheduler
- MapConfig
- Build a secure web app and API
- Config
- JsonLogEncoder
- MigrateCommand
- io.javelin.core.Application
- need_command
- ScheduledTask
- Config
- Slf4jLogger
- io.javelin:javelin-parent
- Config
- NatsQueue.java
- AdultAgeRule
- Security Module
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
- ScheduleSpec
- QueueWorkCommand.java
- com.fasterxml.jackson.databind.ObjectMapper
- InMemoryQueue
- MultipartData
- TestConfig
- Javelin Docs
- SecretProvider
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
- Authenticated
- queue
- .resolve
- .manager
- Middleware
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
- .handle
- session
- CapturingQueue
- ExecutionRecord
- .commands
- TestConfig
- security
- Queue
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
- .register
- FileSecretProvider
- worker
- Cache
- WorkspaceAgeRule
- JdbcDatabaseServiceProvider.java
- Scheduling and events
- Module Dependency Graph
- NoopLogger
- DefaultJsonMapper
- NoopLogger
- InMemorySchedulerLock
- errors
- errors
- json
- SchedulerLock
- .initChannel
- CLI

## God Nodes (most connected - your core abstractions)
1. `Application` - 88 edges
2. `Request` - 85 edges
3. `Router` - 76 edges
4. `Response` - 72 edges
5. `Collection` - 63 edges
6. `CommandContext` - 62 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 50 edges
9. `JobQueue` - 44 edges
10. `Config` - 44 edges

## Surprising Connections (you probably didn't know these)
- `Developer API` --references--> `ScheduledTask`  [INFERRED]
  docs/modules/scheduler.md → modules/javelin-scheduler/src/main/java/io/javelin/scheduler/ScheduledTask.java
- `2. Schedule tasks` --references--> `ScheduledTask`  [INFERRED]
  docs/tutorials/scheduling-and-events.md → modules/javelin-scheduler/src/main/java/io/javelin/scheduler/ScheduledTask.java
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Boot Flow` --references--> `Container`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/Container.java
- `Errors` --references--> `RetryPolicy`  [INFERRED]
  docs/modules/events.md → modules/javelin-core/src/main/java/io/javelin/core/queue/RetryPolicy.java

## Import Cycles
- None detected.

## Communities (241 total, 101 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.13
Nodes (9): ProviderSorter, ServiceProvider, ProviderSorterTest, CycleA, CycleB, FirstProvider, NeedsFirstProvider, SecondProvider (+1 more)

### Community 4 - "Override"
Cohesion: 0.13
Nodes (6): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder, MigrationRunner

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.06
Nodes (17): AbstractCommand, CommandContext, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, CacheClearCommand, ConfigCacheCommand (+9 more)

### Community 9 - "Handle"
Cohesion: 0.05
Nodes (8): Ai, Handle, Message, Reply, File, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.10
Nodes (7): AutomationExampleController, BackendController, QueueExampleController, StorageExampleController, InvoiceJob, SendEmailJob, web

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.16
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - ".handle"
Cohesion: 0.10
Nodes (6): RequestAttributesTest, CsrfMiddleware, SessionAuthMiddleware, SessionPrincipalResolver, SessionAuthMiddlewareTest, StubSession

### Community 21 - "Javelin.java"
Cohesion: 0.07
Nodes (21): Main, Adding to the hardcoded provider list, Config-List Provider Loading, Integrating with Starter, Provider registration order, Registration order matters, ServiceProvider, Default Service Providers (+13 more)

### Community 22 - "Request"
Cohesion: 0.08
Nodes (5): UserController, Cookies and sessions, Request, UploadedFile, RequestMultipartTest

### Community 25 - "Model"
Cohesion: 0.09
Nodes (6): Model overridable methods, Behavior, Configuration, Database Queue Module, Database, Model

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (39): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+31 more)

### Community 28 - "Core Module"
Cohesion: 0.08
Nodes (24): Bearer authentication and authorization, Config, Core Module, Database, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads (+16 more)

### Community 30 - "AppProfile"
Cohesion: 0.22
Nodes (7): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.12
Nodes (6): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv, ValidationRuleRegistry

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.07
Nodes (20): CSRF, Session authentication, 1. Configure, CaffeineCacheServiceProvider, SecretsServiceProvider, RequiresAuthenticationMiddleware, EventServiceProvider, HttpAdapterConfig (+12 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.16
Nodes (5): MigrationRunner, MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.17
Nodes (4): DefaultExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.07
Nodes (9): HomeController, HtmlResponse, JsonResponse, RedirectResponse, Response, ValidationErrors, ResponseTest, ValidationErrorsTest (+1 more)

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
Cohesion: 0.10
Nodes (20): app, env, name, cors, allow_credentials, allowed_origins, max_age_seconds, javelin (+12 more)

### Community 45 - "map"
Cohesion: 0.09
Nodes (3): RequestInputTest, HttpTimeoutConfigTest, PasswordHashStrategyTest

### Community 46 - "XFileDisk"
Cohesion: 0.09
Nodes (5): InMemoryFileRecorder, StorageConfig, DiskConfig, StorageServiceProvider, XFileDisk

### Community 48 - "Javelin Framework"
Cohesion: 0.10
Nodes (20): Architecture at a glance, Boot Flow, Build And Test, Cache And Queues, Database, Documentation, Example Routes, Install (+12 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.07
Nodes (6): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, Security

### Community 51 - "docker-app.yaml"
Cohesion: 0.10
Nodes (19): app, env, name, cors, allow_credentials, allowed_origins, max_age_seconds, javelin (+11 more)

### Community 52 - "standardcharsets"
Cohesion: 0.07
Nodes (3): UserRegisteredEvent, DemoScheduleJob, Schedules

### Community 54 - "Config"
Cohesion: 0.10
Nodes (8): Service provider, CaffeineCacheConfig, Config, DatabaseConfig, Connection, DatabaseConfigTest, ExceptionConfig, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.20
Nodes (10): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Logging And Error Handling, Migration Strategy, Performance Benchmarks, Reverse Proxy (+2 more)

### Community 56 - "Disk"
Cohesion: 0.09
Nodes (11): Configuration, Developer API, File recorder, Service provider, Storage Module, Disk, DownloadPolicy, StorageManager (+3 more)

### Community 57 - "Route"
Cohesion: 0.08
Nodes (20): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+12 more)

### Community 58 - "Override"
Cohesion: 0.13
Nodes (4): TransactionCallback, MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.22
Nodes (3): QueryTimingConfig, SqlTimer, SqlTimerTest

### Community 60 - ".adapterFor"
Cohesion: 0.08
Nodes (22): HttpServerAdapter, HTTP JDK Module, JdkHttpServer, JdkHttpServiceProvider, Notes, Static file serving, Trusted proxy resolution, Behavior (+14 more)

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

### Community 74 - "caddy"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, caddy

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 76 - "Application Flow"
Cohesion: 0.11
Nodes (19): Application Flow, Boot Sequence, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development) (+11 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.18
Nodes (30): io.javelin:javelin-cache, io.javelin:javelin-cache-caffeine, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration (+22 more)

### Community 78 - "CaffeineCache"
Cohesion: 0.16
Nodes (7): Cache Caffeine Module, Configuration, Developer API, CaffeineCache, Expiry<String, Entry>, Entry, CaffeineCacheTest

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (43): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+35 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 86 - "Application"
Cohesion: 0.07
Nodes (7): ConfigServiceProvider, JavelinConsole, ConfigServiceProvider, JavelinConsole, RouteListCommand, ServeCommand, Application

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - "Docker Compose"
Cohesion: 0.12
Nodes (12): AppServiceProvider, Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration (+4 more)

### Community 89 - "InMemoryQueue"
Cohesion: 0.09
Nodes (9): Configuration, Queue Module, Reliability, JobHandlerRegistry, Registration, JobQueue, ClaimedJob, QueueWorker (+1 more)

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 93 - "JdkHttpServer"
Cohesion: 0.10
Nodes (5): ExceptionHandler, HttpTimeoutConfig, JdkHttpServer, RequestRejected, UndertowHttpServer

### Community 94 - "locale"
Cohesion: 0.10
Nodes (6): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, FileGenerator, Names, TemplateEngine

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

### Community 103 - "MapConfig"
Cohesion: 0.24
Nodes (3): ExceptionHandlerServiceProvider, ExceptionHandlerServiceProviderTest, MapConfig

### Community 104 - "Build a secure web app and API"
Cohesion: 0.22
Nodes (9): 1. Enable the providers, 2. Build and start the demo, 3. Browser login with CSRF, 4. Validation and error rendering, 5. JWT for the API, 6. CORS preflight, Build a secure web app and API, What happens on a bad CSRF token (+1 more)

### Community 106 - "JsonLogEncoder"
Cohesion: 0.23
Nodes (3): RequestLoggingMiddleware, JsonLogEncoder, JsonLogEncoderTest

### Community 125 - "Security Module"
Cohesion: 0.11
Nodes (11): CORS, HSTS and CSP, JWT adapter, PasswordHasher, RateLimitMiddleware, RequestSizeLimitMiddleware, SecureHeadersMiddleware, Security Module (+3 more)

### Community 128 - ".status"
Cohesion: 0.10
Nodes (5): DefaultExceptionHandlerTest, HttpKernelTest, RouterTest, StaticAssetResolverTest, CorsAndCsrfTest

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

### Community 135 - "Session"
Cohesion: 0.06
Nodes (11): Cookie-backed sessions, Cookie, Session, SessionStore, CookieAndSessionContractTest, StubSession, InMemorySessionStore, MemorySession (+3 more)

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - "Job"
Cohesion: 0.11
Nodes (12): Queue contracts, Contracts, Developer API, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module (+4 more)

### Community 139 - "ProfileConfig"
Cohesion: 0.15
Nodes (10): Application profile, Javelin, JavelinStarterServiceProvider, Starter Module, ValidationRuleLoader, WorkspaceDefaults, DevProfileMiddleware, WorkspaceDefaults (+2 more)

### Community 140 - ".commands"
Cohesion: 0.07
Nodes (13): CommandRegistry, AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry (+5 more)

### Community 141 - "services"
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 142 - "DownloadPolicy"
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
Cohesion: 0.18
Nodes (11): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Middleware pipeline, Module Boundaries, Module Export Diagram (+3 more)

### Community 147 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (17): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Service provider (+9 more)

### Community 148 - "ScheduleSpec"
Cohesion: 0.15
Nodes (5): Cron, Daily, Monthly, Weekly, ScheduleSpec

### Community 149 - "QueueWorkCommand.java"
Cohesion: 0.12
Nodes (5): NewCommand, QueueStatusCommand, QueueWorkCommand, ScheduleRunCommand, ServeCommand

### Community 152 - "MultipartData"
Cohesion: 0.23
Nodes (3): Key Design Decisions, MultipartData, PartDisposition

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 155 - "SecretProvider"
Cohesion: 0.13
Nodes (11): Config interface methods, Config Module, DotenvEnv, Environment expression resolution, Notes, Secret providers, YamlConfig, Notes (+3 more)

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

### Community 174 - "Authenticated"
Cohesion: 0.16
Nodes (5): SecurityExampleController, Safety, 3. Private downloads, Authenticated, AuthenticatedDownloadPolicy

### Community 175 - "queue"
Cohesion: 0.15
Nodes (13): dir, queue, driver, file, jetstream, retry, stream, url (+5 more)

### Community 176 - ".resolve"
Cohesion: 0.16
Nodes (3): JwtTokenIssuer, JwtTokenResolver, JwtTokenResolverTest

### Community 178 - "Middleware"
Cohesion: 0.09
Nodes (7): Middleware, Next, SecurityResponses, AuthorizationMiddlewareTest, RateLimitMiddleware, Bucket, RequestSizeLimitMiddleware

### Community 179 - "ValkeyCache"
Cohesion: 0.27
Nodes (3): InMemoryCache, Cache, ValkeyCache

### Community 180 - "YamlConfig"
Cohesion: 0.09
Nodes (10): Startup Time, Why startup is fast, DotenvEnv, Entry, EnvSecretProvider, YamlConfig, TypeReference<>, TypeReference<> (+2 more)

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
Cohesion: 0.11
Nodes (7): Backward compatibility, Database Migration Module, Dialects, DatabaseManager, JdbcDatabaseServiceProviderTest, MigrationDialect, DatabaseExecutionHistory

### Community 186 - ".handle"
Cohesion: 0.12
Nodes (6): ConfigurableExceptionHandler, ErrorPageResolver, FileErrorPageResolver, SensitiveDataRedactor, ConfigurableExceptionHandlerTest, SensitiveDataRedactorTest

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 188 - "QueryBuilder"
Cohesion: 0.07
Nodes (15): Layer 2 — Adapters, Core interfaces you can implement, Example: Implementing `Cache` (javelin-cache), Implementing a Core Interface, QueryBuilder, ModelTest, CastedArticle, EditableUser (+7 more)

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

### Community 195 - ".handle"
Cohesion: 0.20
Nodes (4): Bearer authentication, BearerAuthMiddleware, BearerTokenResolver, BearerAuthMiddlewareTest

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 198 - "ExecutionRecord"
Cohesion: 0.24
Nodes (3): ExecutionHistory, ExecutionRecord, InMemoryExecutionHistory

### Community 201 - "security"
Cohesion: 0.14
Nodes (14): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+6 more)

### Community 202 - "Queue"
Cohesion: 0.30
Nodes (3): Queue, QueueFacadeTest, SendEmailJob

### Community 203 - ".handle"
Cohesion: 0.11
Nodes (4): RequestLoggingMiddleware, CorsConfig, CorsMiddleware, SecureHeadersMiddleware

### Community 204 - "Scheduler Module"
Cohesion: 0.18
Nodes (10): Application, Container, Container, Worker, Configuration, Developer API, Dispatching to queues, Reliability (+2 more)

### Community 205 - "Cache Module"
Cohesion: 0.50
Nodes (4): Cache, Cache Module, CacheServiceProvider, Notes

### Community 206 - "security"
Cohesion: 0.15
Nodes (13): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+5 more)

### Community 208 - "cache"
Cohesion: 0.33
Nodes (6): cache, caffeine, driver, url, default_ttl_seconds, maximum_size

### Community 209 - "ProviderLoaderTest"
Cohesion: 0.28
Nodes (4): ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder

### Community 213 - "cache"
Cohesion: 0.40
Nodes (5): cache, caffeine, driver, default_ttl_seconds, maximum_size

### Community 214 - "ContainerTest"
Cohesion: 0.25
Nodes (6): ContainerTest, CircularA, CircularB, CloseableResource, Dependency, Service

### Community 216 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 217 - "Logger"
Cohesion: 0.16
Nodes (11): Log, Logger, JsonLogEncoder, Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider (+3 more)

### Community 218 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 222 - "worker"
Cohesion: 0.29
Nodes (7): worker, concurrency, enabled, lease_seconds, poll_interval_ms, shutdown_seconds, timeout_seconds

### Community 226 - "Scheduling and events"
Cohesion: 0.33
Nodes (5): 2. Schedule tasks, 3. Dispatch events, 4. Multi-instance deployments, 5. Manual runs, Scheduling and events

### Community 227 - "Module Dependency Graph"
Cohesion: 0.40
Nodes (5): Layer 0 — Foundation, Layer 1 — Contracts, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Dependency Graph

### Community 234 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 235 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 236 - "json"
Cohesion: 0.50
Nodes (4): json, fail_on_unknown_properties, indent_output, write_nulls

### Community 239 - "CLI"
Cohesion: 0.67
Nodes (3): CLI, First Run, Project Selection

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 972 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **101 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `Override`, `cli/ConsoleKernel.java`, `ProfileConfig`, `.commands`, `QueueWorkCommand.java`, `Javelin.java`, `AppProfile`, `.instance`, `io.javelin.core.ServiceProvider`, `ApplicationTest`, `map`, `Config`, `.adapterFor`, `TestConfig`, `optional`, `Scheduler Module`, `ProviderLoaderTest`, `io.javelin.core.Application`, `Security Module`, `.output`, `Container`?**
  _High betweenness centrality (0.065) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.13277310924369748 - nodes in this community are weakly interconnected._
- **Why does `Database` connect `Model` to `Scheduling and events`, `optional`, `Override`, `Override`, `Scheduler Module`, `Core Module`, `map`, `.execute`, `Javelin.java`, `DatabaseQueue`, `io.javelin.core.Database`, `Database JDBC Module`, `QueryBuilder`?**
  _High betweenness centrality (0.025) - this node is a cross-community bridge._
- **Should `objects` be split into smaller, more focused modules?**
  _Cohesion score 0.08571428571428572 - nodes in this community are weakly interconnected._
- **Why does `JobQueue` connect `InMemoryQueue` to `ValkeyQueue`, `objects`, `io.javelin.core.ServiceProvider`, `CapturingQueue`, `Docker Compose`, `Queue`, `Scheduler Module`, `io.javelin.core.Request`, `CapturingQueue`, `FileQueue`, `QueueWorkCommand.java`, `RabbitMqQueue`, `InMemoryQueue`, `DatabaseQueue`, `Model`, `NatsQueue.java`, `.adapterFor`, `Queue Adapters`?**
  _High betweenness centrality (0.024) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.12561576354679804 - nodes in this community are weakly interconnected._