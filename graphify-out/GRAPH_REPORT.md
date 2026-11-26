# Graph Report - javelin-framework  (2026-11-26)

## Corpus Check
- 443 files · ~105,455 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 54 file(s) not represented in the graph (top: .stub 38, (none) 9, .xml 2)

## Summary
- 4473 nodes · 11343 edges · 266 communities (145 shown, 121 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1616 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `0c667592`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
- list
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- io.javelin.core.Request
- io.javelin.core.queue.Job
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
- Schedule.java
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
- Database
- Str
- Application
- environment
- Docker Compose
- InMemoryQueue
- Database JDBC Module
- VersionCommand
- RecordingLogger
- UndertowHttpServer.java
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
- .stream
- Config
- JsonLogEncoder
- SmtpTransport.java
- io.javelin.core.Application
- need_command
- ScheduledTask
- Handle
- Slf4jLogger
- io.javelin:javelin-parent
- Config
- CliException
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
- .register
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
- MailComposer
- TestConfig
- Javelin Docs
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
- Security
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
- CapturingQueue
- ExecutionRecord
- .commands
- TestConfig
- security
- .composerViewAndQueueDispatch
- .handle
- Scheduler Module
- Cache Module
- security
- .execute
- cache
- ProviderLoaderTest
- StorageServiceProvider
- cache
- ContainerTest
- StubSession
- scheduler
- Logger
- scheduler
- CronExpression
- .register
- FileSecretProvider
- .load
- Cache
- WorkspaceAgeRule
- JdbcDatabaseServiceProvider.java
- JdkHttpServer
- .register
- NoopLogger
- MailManager
- DefaultJsonMapper
- InMemoryFileRecorder
- InMemorySchedulerLock
- File
- errors
- .register
- SchedulerLock
- tutorials/README.md
- CLI
- .run
- Full Worked Example
- .loadDirectory
- java.util.regex.Pattern
- StubSession
- Mail Module
- MailMessage
- mail
- mail
- Config Module
- DatabaseExecutionHistory
- TestMailTransport
- ValidationRuleRegistry
- session
- Validation Module
- .boot
- ProfileConfig
- Ai
- Http
- cors
- Model
- HTTP Netty Module
- HTTP Undertow Module

## God Nodes (most connected - your core abstractions)
1. `Application` - 88 edges
2. `Request` - 85 edges
3. `Router` - 76 edges
4. `Response` - 72 edges
5. `Collection` - 63 edges
6. `CommandContext` - 62 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 50 edges
9. `JobQueue` - 45 edges
10. `Config` - 44 edges

## Surprising Connections (you probably didn't know these)
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Boot Flow` --references--> `Container`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/Container.java
- `JsonLogEncoder` --references--> `JsonLogEncoder`  [INFERRED]
  docs/modules/log-slf4j.md → modules/javelin-log-slf4j/src/main/java/io/javelin/log/slf4j/JsonLogEncoder.java
- `RequestLoggingMiddleware` --references--> `JsonLogEncoder`  [INFERRED]
  docs/modules/log-slf4j.md → modules/javelin-log-slf4j/src/main/java/io/javelin/log/slf4j/JsonLogEncoder.java
- `Notes` --references--> `ValkeyCacheServiceProvider`  [INFERRED]
  docs/modules/cache.md → modules/javelin-cache-valkey/src/main/java/io/javelin/cache/valkey/ValkeyCacheServiceProvider.java

## Import Cycles
- None detected.

## Communities (266 total, 121 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.13
Nodes (9): ProviderSorter, ServiceProvider, ProviderSorterTest, CycleA, CycleB, FirstProvider, NeedsFirstProvider, SecondProvider (+1 more)

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.06
Nodes (17): AbstractCommand, CommandContext, AiChatCommand, AiGenerateModuleCommand, BuildCommand, ConfigCacheCommand, DevCommand, DoctorCommand (+9 more)

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.05
Nodes (12): AutomationExampleController, QueueExampleController, SecurityExampleController, StorageExampleController, UserRegisteredEvent, InvoiceJob, SendEmailJob, Json (+4 more)

### Community 13 - "io.javelin.core.queue.Job"
Cohesion: 0.13
Nodes (6): JobQueue, ClaimedJob, Queue, QueueFacadeTest, CapturingQueue, SendEmailJob

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.17
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - ".handle"
Cohesion: 0.10
Nodes (7): Cookies and sessions, RequiresAuthenticationMiddleware, RequestAttributesTest, AuthorizationMiddlewareTest, CsrfMiddleware, CorsAndCsrfTest, SessionAuthMiddlewareTest

### Community 21 - "Javelin.java"
Cohesion: 0.13
Nodes (13): Adding to the hardcoded provider list, Integrating with Starter, Provider registration order, Registration order matters, ServiceProvider, Default Service Providers, CacheServiceProvider, ConsoleServiceProvider (+5 more)

### Community 22 - "Request"
Cohesion: 0.07
Nodes (7): UserController, ExceptionHandler, Request, MultipartData, PartDisposition, UploadedFile, RequestMultipartTest

### Community 25 - "Model"
Cohesion: 0.12
Nodes (3): Model overridable methods, Model, SampleHydratedUser

### Community 26 - "CLI Reference"
Cohesion: 0.04
Nodes (49): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+41 more)

### Community 28 - "Core Module"
Cohesion: 0.09
Nodes (22): Config, Core Module, Database, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse (+14 more)

### Community 30 - "AppProfile"
Cohesion: 0.16
Nodes (8): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, ChannelInitializer<SocketChannel>

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.17
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.09
Nodes (19): Logging And Error Handling, JdkHttpServiceProvider, Configuration, Configuration, Service provider, SecretsServiceProvider, DevProfileMiddleware, ExceptionHandlerServiceProvider (+11 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.16
Nodes (5): MigrationRunner, MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.13
Nodes (6): DefaultExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer, UndertowHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.06
Nodes (11): HomeController, HtmlResponse, JsonResponse, RedirectResponse, Response, SecurityResponses, ValidationErrors, View (+3 more)

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
Nodes (20): app, env, name, errors, pages_dir, redact, redact_keys, javelin (+12 more)

### Community 48 - "Javelin Framework"
Cohesion: 0.10
Nodes (20): Architecture at a glance, Boot Flow, Build And Test, Cache And Queues, Database, Documentation, Example Routes, Install (+12 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.11
Nodes (6): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, PasswordHashStrategyTest

### Community 51 - "docker-app.yaml"
Cohesion: 0.11
Nodes (17): app, env, name, cors, allow_credentials, allowed_origins, max_age_seconds, javelin (+9 more)

### Community 54 - "Config"
Cohesion: 0.11
Nodes (6): Config, DatabaseConfig, Connection, DatabaseConfigTest, ExceptionConfig, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.22
Nodes (9): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Migration Strategy, Performance Benchmarks, Reverse Proxy, Trusted proxy configuration (+1 more)

### Community 56 - "Disk"
Cohesion: 0.10
Nodes (8): Configuration, File recorder, Service provider, Storage Module, Disk, StorageManager, StoredFile, Storage

### Community 57 - "Route"
Cohesion: 0.08
Nodes (19): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+11 more)

### Community 58 - "Override"
Cohesion: 0.13
Nodes (4): TransactionCallback, MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.14
Nodes (4): QueryTimingConfig, SqlTimer, SqlTimerTest, RecordingLogger

### Community 60 - ".adapterFor"
Cohesion: 0.15
Nodes (6): HttpServerAdapter, HttpServerAdapter, HttpServerDriverProviderTest, DriverConfig, DriverEnv, Features

### Community 61 - "JsonMapper"
Cohesion: 0.23
Nodes (7): Json, API, Backward compatibility, Configuration, JSON Jackson Module, Registration, JsonMapper

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
Nodes (31): io.javelin:javelin-cache, io.javelin:javelin-cache-caffeine, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration (+23 more)

### Community 78 - "CaffeineCache"
Cohesion: 0.16
Nodes (7): Cache Caffeine Module, Configuration, Developer API, CaffeineCache, Expiry<String, Entry>, Entry, CaffeineCacheTest

### Community 81 - "Module Authoring"
Cohesion: 0.06
Nodes (31): Adding Tests, Adding the module to the parent POM, `bind(A, B)` — reusable factory, Binding Strategies, Checklist for new modules, Config-Driven Modules, Creating a New Interface, Creating your own facade (+23 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 83 - "Database"
Cohesion: 0.13
Nodes (7): Layer 2 — Adapters, Core interfaces you can implement, Behavior, Configuration, Database Queue Module, Database, QueryBuilder

### Community 86 - "Application"
Cohesion: 0.11
Nodes (5): JavelinConsole, JavelinConsole, RouteListCommand, ServeCommand, Application

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - "Docker Compose"
Cohesion: 0.12
Nodes (13): AppServiceProvider, Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration (+5 more)

### Community 89 - "InMemoryQueue"
Cohesion: 0.13
Nodes (4): JobHandlerRegistry, Registration, QueueWorker, QueueWorkerConfig

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 94 - "locale"
Cohesion: 0.11
Nodes (6): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, FileGenerator, Names, TemplateEngine

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "DefaultEventDispatcher"
Cohesion: 0.08
Nodes (19): Developer API, Errors, Events Module, Queued listeners, Service provider, 1. Configure, 2. Schedule tasks, 3. Dispatch events (+11 more)

### Community 101 - "private"
Cohesion: 0.12
Nodes (17): private, public, temporary, domain, driver, public, root, domain (+9 more)

### Community 102 - "Scheduler"
Cohesion: 0.16
Nodes (6): Scheduler, SchedulerConfig, SchedulerLock, Interval, TaskDefinition, SchedulerTest

### Community 107 - "SmtpTransport.java"
Cohesion: 0.12
Nodes (3): SmtpConfig, SmtpTransport, Authenticator

### Community 108 - "io.javelin.core.Application"
Cohesion: 0.08
Nodes (5): web, Developer API, JdbcDatabaseServiceProvider, QueueServiceProvider, SchedulerServiceProvider

### Community 114 - "Handle"
Cohesion: 0.18
Nodes (3): Handle, Message, Reply

### Community 122 - "CliException"
Cohesion: 0.13
Nodes (4): CliException, AiExplainCommand, AiInstallCommand, NewCommand

### Community 125 - "Security Module"
Cohesion: 0.09
Nodes (17): TrustedProxyResolver, Cookie-backed sessions, CORS, CSRF, HSTS and CSP, PasswordHasher, RateLimitMiddleware, RequestSizeLimitMiddleware (+9 more)

### Community 128 - ".status"
Cohesion: 0.13
Nodes (4): DefaultExceptionHandlerTest, HttpKernelTest, StaticAssetResolverTest, ProfileWiringTest

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
Cohesion: 0.31
Nodes (3): SupportUtilitiesTest, Person, Profile

### Community 134 - "dev.mjs"
Cohesion: 0.25
Nodes (3): bin, env, watchers

### Community 135 - "Session"
Cohesion: 0.06
Nodes (10): Cookie, Session, SessionStore, CookieAndSessionContractTest, StubSession, InMemorySessionStore, MemorySession, SessionConfig (+2 more)

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - "Job"
Cohesion: 0.11
Nodes (15): Queue contracts, Configuration, Contracts, Developer API, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes (+7 more)

### Community 139 - ".register"
Cohesion: 0.18
Nodes (10): Application profile, HTTP driver selection, Javelin, JavelinStarterServiceProvider, Notes, Starter Module, ValidationRuleLoader, WorkspaceDefaults (+2 more)

### Community 140 - ".commands"
Cohesion: 0.07
Nodes (12): CommandRegistry, AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry (+4 more)

### Community 141 - "services"
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 142 - "DownloadPolicy"
Cohesion: 0.14
Nodes (9): 1. Disks and cache are configured explicitly, 2. Write and read files, 3. Private downloads, 4. S3 or MinIO, 5. Caching, Files and caching, Notes, AuthenticatedDownloadPolicy (+1 more)

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
Nodes (16): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Layer 0 — Foundation, Layer 3 — Toolchain (+8 more)

### Community 147 - "PebbleViewExtensions"
Cohesion: 0.05
Nodes (18): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Service provider (+10 more)

### Community 148 - "ScheduleSpec"
Cohesion: 0.18
Nodes (5): Cron, Daily, Monthly, Weekly, ScheduleSpec

### Community 149 - "QueueWorkCommand.java"
Cohesion: 0.10
Nodes (5): QueueStatusCommand, QueueWorkCommand, ScheduleRunCommand, ServeCommand, MigrateCommand

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 155 - "Build a secure web app and API"
Cohesion: 0.18
Nodes (9): Notes, 1. Enable the providers, 2. Build and start the demo, 6. CORS preflight, 7. Secrets, Build a secure web app and API, Where to go next, CompositeSecretProvider (+1 more)

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
Nodes (4): Router, RouterTest, NettyHttpServerIntegrationTest, RunningServer

### Community 175 - "queue"
Cohesion: 0.10
Nodes (20): dir, queue, driver, file, jetstream, retry, stream, url (+12 more)

### Community 178 - "Middleware"
Cohesion: 0.07
Nodes (13): Layer 1 — Contracts, Bearer authentication and authorization, Middleware, Bearer authentication, Middleware, Next, Authenticated, BearerAuthMiddleware (+5 more)

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 186 - ".handle"
Cohesion: 0.15
Nodes (5): ConfigurableExceptionHandler, ErrorPageResolver, SensitiveDataRedactor, ConfigurableExceptionHandlerTest, SensitiveDataRedactorTest

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 188 - "QueryBuilder"
Cohesion: 0.11
Nodes (9): ModelTest, CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder, SampleUser, Status, DRAFT (+1 more)

### Community 189 - "Queue Adapters"
Cohesion: 0.14
Nodes (12): Choosing an adapter, File (`javelin-queue-file`), NATS JetStream (`javelin-queue-nats`), Queue Adapters, RabbitMQ (`javelin-queue-rabbitmq`), Valkey / Redis (`javelin-queue-valkey`), 1. Configure the queue, 2. Define and dispatch a job (+4 more)

### Community 190 - "DefaultValidator"
Cohesion: 0.39
Nodes (3): 4. Validation and error rendering, DefaultValidator, DefaultValidatorTest

### Community 191 - "TestConfig"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

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

### Community 203 - ".handle"
Cohesion: 0.13
Nodes (3): RequestLoggingMiddleware, CorsConfig, CorsMiddleware

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
Cohesion: 0.28
Nodes (4): ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder

### Community 210 - "StorageServiceProvider"
Cohesion: 0.23
Nodes (3): StorageConfig, DiskConfig, StorageServiceProvider

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
Cohesion: 0.15
Nodes (12): Log, Logger, JsonLogEncoder, Logging SLF4J Module, LoggingConfig, Notes, RequestLoggingMiddleware, Slf4jLogger (+4 more)

### Community 218 - "scheduler"
Cohesion: 0.25
Nodes (8): scheduler, enabled, history, lock, lock_ttl_seconds, owner, poll_interval_ms, timezone

### Community 220 - ".register"
Cohesion: 0.22
Nodes (3): ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, Config

### Community 222 - ".load"
Cohesion: 0.17
Nodes (6): Startup Time, Why startup is fast, ConfigServiceProvider, DotenvEnv, Entry, DotenvEnvTest

### Community 223 - "Cache"
Cohesion: 0.13
Nodes (3): Cache, Cache, ValkeyCacheTest

### Community 225 - "JdbcDatabaseServiceProvider.java"
Cohesion: 0.15
Nodes (6): Backward compatibility, Database Migration Module, Dialects, MigrationDialect, MigrationDialects, MigrationDialectsTest

### Community 227 - ".register"
Cohesion: 0.17
Nodes (3): JWT adapter, 5. JWT for the API, JwtAuthServiceProvider

### Community 229 - "MailManager"
Cohesion: 0.24
Nodes (3): MailManager, MailTransport, Mail

### Community 235 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 236 - ".register"
Cohesion: 0.22
Nodes (4): BackendController, Service provider, CaffeineCacheConfig, CaffeineCacheServiceProvider

### Community 238 - "tutorials/README.md"
Cohesion: 0.20
Nodes (6): 1. Compose and send, 2. Plain text, attachments and inline images, 3. Production SMTP, 4. Reliability and safety, Sending mail, Tutorials

### Community 239 - "CLI"
Cohesion: 0.67
Nodes (3): CLI, First Run, Project Selection

### Community 242 - "Full Worked Example"
Cohesion: 0.20
Nodes (10): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+2 more)

### Community 243 - ".loadDirectory"
Cohesion: 0.29
Nodes (3): EnvSecretProvider, TypeReference<>, TypeReference<>

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

### Community 254 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 255 - "Validation Module"
Cohesion: 0.33
Nodes (5): Relationship to `javelin-support`, Supported rules, Validation Module, Validator, Validator

### Community 261 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 262 - "Model"
Cohesion: 0.50
Nodes (4): Model, Model instance methods, Model static methods, User

### Community 263 - "HTTP Netty Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 264 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 1004 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **121 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `.status`, `ServiceProvider`, `Override`, `cli/ConsoleKernel.java`, `.register`, `.commands`, `QueueWorkCommand.java`, `Javelin.java`, `AppProfile`, `.instance`, `io.javelin.core.ServiceProvider`, `ApplicationTest`, `map`, `Config`, `.adapterFor`, `TestConfig`, `optional`, `Scheduler Module`, `ProviderLoaderTest`, `.load`, `.register`, `io.javelin.core.Application`, `.output`, `Container`?**
  _High betweenness centrality (0.043) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.12698412698412698 - nodes in this community are weakly interconnected._
- **Why does `Database` connect `Database` to `JdbcDatabaseServiceProvider.java`, `DefaultEventDispatcher`, `optional`, `Override`, `Override`, `Scheduler Module`, `QueryBuilder`, `Javelin.java`, `DatabaseQueue`, `Model`, `Database JDBC Module`, `Core Module`?**
  _High betweenness centrality (0.038) - this node is a cross-community bridge._
- **Should `list` be split into smaller, more focused modules?**
  _Cohesion score 0.06400409626216078 - nodes in this community are weakly interconnected._
- **Why does `Request` connect `Request` to `.status`, `JdkHttpServer`, `NettyHttpServerIntegrationTest.java`, `optional`, `JdkHttpServerIntegrationTest`, `Response`, `Session`, `.handle`, `io.javelin.core.Request`, `map`, `Middleware`, `SupportUtilitiesTest.java`, `.handle`, `Security Module`, `com.fasterxml.jackson.databind.ObjectMapper`, `Route`, `UndertowHttpServer.java`?**
  _High betweenness centrality (0.028) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.13675213675213677 - nodes in this community are weakly interconnected._