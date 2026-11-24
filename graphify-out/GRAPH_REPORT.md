# Graph Report - javelin-framework  (2026-11-25)

## Corpus Check
- 365 files · ~92,006 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 52 file(s) not represented in the graph (top: .stub 37, (none) 9, .xml 2)

## Summary
- 3802 nodes · 9443 edges · 229 communities (126 shown, 103 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1352 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `04f5a43b`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
- path
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- io.javelin.core.Request
- Application
- StaticAssetResolver
- .from
- Authenticated
- Javelin.java
- MultipartData
- .loadDirectory
- Model
- CLI Reference
- Date
- Core Module
- AppProfile
- .imageSupportDetectsAndReadsImageMetadata
- .instance
- Validation
- Security Module
- MigrationRunner
- JdkHttpServerIntegrationTest
- Request
- Support Module
- ApplicationTest
- TrustedProxyResolver
- Input
- app.yaml
- XFileDisk
- Container
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
- docs/README.md
- Agent Notes
- .write
- ValidationRule
- MigrationCommandTest.java
- Validator
- JdbcDatabase
- JdbcQueryBuilder
- ValidationRule
- Json.java
- caddy
- package.json
- Application Flow
- io.javelin:javelin-starter
- CaffeineCache
- Logger
- .login
- Module Authoring
- nginx
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- JdkHttpServer.java
- Str
- Router
- environment
- .make
- ConsoleOutput
- Database JDBC Module
- VersionCommand
- RecordingLogger
- JdkHttpServer
- locale
- Env
- .stringify
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
- DownloadPolicy
- app
- nats
- valkey
- Architecture
- PebbleViewRenderer
- NewCommand
- com.fasterxml.jackson.databind.ObjectMapper
- .handle
- JavelinViewExtension
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
- ProjectLauncher
- MapConfig
- Cache
- private
- MakeMigrationCommand
- HtmlResponse
- queue
- .resolve
- .manager
- Override
- PebbleViewExtensions
- SecretProvider
- primary
- log
- Config Module
- io.javelin.core.Database
- .handle
- Production Performance Tuning
- ModelTest
- io.pebbletemplates.pebble.extension.Filter
- DefaultValidator
- TestConfig
- database
- Obj
- .ok
- session
- session
- .commands
- TestConfig
- security
- errors
- .handle
- TransactionCallback
- Cache Module
- security
- HTTP Undertow Module
- cache
- profile.md
- cache
- StubSession
- public
- StructuredLogger
- json
- Docker Compose
- errors
- Generator Helpers
- Cache
- Storage Module
- .main
- NoopLogger
- cors

## God Nodes (most connected - your core abstractions)
1. `Application` - 88 edges
2. `Request` - 85 edges
3. `Router` - 76 edges
4. `Response` - 72 edges
5. `CommandContext` - 68 edges
6. `Collection` - 63 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 50 edges
9. `Config` - 44 edges
10. `Handle` - 43 edges

## Surprising Connections (you probably didn't know these)
- `CacheServiceProvider` --references--> `CacheServiceProvider`  [INFERRED]
  docs/modules/cache.md → modules/javelin-cache/src/main/java/io/javelin/cache/CacheServiceProvider.java
- `Configuration` --references--> `Disk`  [INFERRED]
  docs/modules/storage.md → modules/javelin-storage/src/main/java/io/javelin/storage/Disk.java
- `File recorder` --references--> `InMemoryFileRecorder`  [INFERRED]
  docs/modules/storage.md → modules/javelin-storage/src/main/java/io/javelin/storage/InMemoryFileRecorder.java
- `Service provider` --references--> `StorageServiceProvider`  [INFERRED]
  docs/modules/storage.md → modules/javelin-storage/src/main/java/io/javelin/storage/StorageServiceProvider.java
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java

## Import Cycles
- None detected.

## Communities (229 total, 103 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.09
Nodes (14): ConfigServiceProvider, ProviderSorter, ServiceProvider, ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder, ProviderSorterTest (+6 more)

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.04
Nodes (27): AbstractCommand, CliException, CommandContext, AiChatCommand, AiExplainCommand, AiGenerateModuleCommand, AiInstallCommand, BuildCommand (+19 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (9): 3. Browser login with CSRF, What happens on a bad CSRF token, Ai, Handle, Message, Reply, Http, Handle (+1 more)

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.13
Nodes (3): StorageExampleController, ErrorPageResolver, FileErrorPageResolver

### Community 13 - "Application"
Cohesion: 0.06
Nodes (5): JavelinConsole, JavelinConsole, RouteListCommand, ServeCommand, Application

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.22
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - "Authenticated"
Cohesion: 0.18
Nodes (6): Bearer authentication, JWT adapter, Authenticated, BearerAuthMiddleware, BearerTokenResolver, AuthorizationMiddlewareTest

### Community 21 - "Javelin.java"
Cohesion: 0.06
Nodes (29): Main, Adding to the hardcoded provider list, Integrating with Starter, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Provider registration order, Registration order matters, The ServiceProvider Contract (+21 more)

### Community 22 - "MultipartData"
Cohesion: 0.06
Nodes (6): MultipartData, PartDisposition, UploadedFile, RequestMultipartTest, File, Security

### Community 24 - ".loadDirectory"
Cohesion: 0.25
Nodes (4): EnvSecretProvider, YamlConfig, TypeReference<>, TypeReference<>

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (38): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+30 more)

### Community 28 - "Core Module"
Cohesion: 0.06
Nodes (28): Config, Core Module, Database, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse (+20 more)

### Community 30 - "AppProfile"
Cohesion: 0.22
Nodes (7): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.10
Nodes (8): Example, Javelin Support, Public API, Arr, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.14
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "Security Module"
Cohesion: 0.17
Nodes (11): CORS, CSRF, HSTS and CSP, PasswordHasher, RateLimitMiddleware, RequestSizeLimitMiddleware, SecureHeadersMiddleware, Security Module (+3 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.15
Nodes (5): MigrationRunner, MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.14
Nodes (5): DefaultExceptionHandler, ExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Request"
Cohesion: 0.04
Nodes (19): HomeController, Layer 1 — Contracts, Bearer authentication and authorization, Cookies and sessions, Middleware, Middleware, Next, RedirectResponse (+11 more)

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
Nodes (16): app, env, name, cors, allow_credentials, allowed_origins, max_age_seconds, javelin (+8 more)

### Community 46 - "XFileDisk"
Cohesion: 0.09
Nodes (5): InMemoryFileRecorder, StorageConfig, DiskConfig, StorageServiceProvider, XFileDisk

### Community 48 - "Container"
Cohesion: 0.05
Nodes (33): Application, Container, Container, Container, Binding, ContainerTest, CircularA, CircularB (+25 more)

### Community 49 - "Console Module"
Cohesion: 0.13
Nodes (12): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+4 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.10
Nodes (6): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, PasswordHashStrategyTest

### Community 51 - "docker-app.yaml"
Cohesion: 0.14
Nodes (13): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+5 more)

### Community 52 - "Override"
Cohesion: 0.16
Nodes (3): CastedArticle, EditableUser, RecordingQueryBuilder

### Community 54 - "Config"
Cohesion: 0.10
Nodes (8): Service provider, CaffeineCacheConfig, Config, DatabaseConfig, Connection, DatabaseConfigTest, ExceptionConfig, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.20
Nodes (10): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Logging And Error Handling, Migration Strategy, Performance Benchmarks, Reverse Proxy (+2 more)

### Community 56 - "Disk"
Cohesion: 0.14
Nodes (4): Disk, StorageManager, StoredFile, Storage

### Community 57 - "Route"
Cohesion: 0.08
Nodes (19): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+11 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.15
Nodes (4): QueryTimingConfig, SqlTimer, SqlTimerTest, RecordingLogger

### Community 60 - ".adapterFor"
Cohesion: 0.15
Nodes (8): JdkHttpServiceProvider, Configuration, Configuration, HttpServerAdapter, HttpServerDriverProvider, HttpServerDriverProviderTest, DriverConfig, DriverEnv

### Community 61 - "JsonMapper"
Cohesion: 0.19
Nodes (7): Json, API, Backward compatibility, Configuration, JSON Jackson Module, Registration, JsonMapper

### Community 62 - "Override"
Cohesion: 0.18
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

### Community 73 - "Json.java"
Cohesion: 0.11
Nodes (3): UserController, web, Json

### Community 74 - "caddy"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, caddy

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 76 - "Application Flow"
Cohesion: 0.13
Nodes (15): Application Flow, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development), Error Handling (+7 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.22
Nodes (23): io.javelin:javelin-cache, io.javelin:javelin-cache-caffeine, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration (+15 more)

### Community 78 - "CaffeineCache"
Cohesion: 0.16
Nodes (7): Cache Caffeine Module, Configuration, Developer API, CaffeineCache, Expiry<String, Entry>, Entry, CaffeineCacheTest

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (43): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+35 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 83 - ".returnsAllowHeaderAndEnforcesBearerAuthentication"
Cohesion: 0.22
Nodes (3): UndertowHttpServer, UndertowHttpServerIntegrationTest, RunningServer

### Community 86 - "Router"
Cohesion: 0.14
Nodes (4): Router, RouterTest, NettyHttpServerIntegrationTest, RunningServer

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - ".make"
Cohesion: 0.11
Nodes (8): AppServiceProvider, Boot Sequence, Phase 1: Entry Point, Phase 2: Boot, Phase 3: Dispatch, JdbcDatabaseServiceProvider, NoopLogger, JacksonJsonServiceProvider

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 94 - "locale"
Cohesion: 0.12
Nodes (5): GenerateCommandSupport, MakeModuleCommand, FileGenerator, Names, TemplateEngine

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 101 - "private"
Cohesion: 0.12
Nodes (17): private, public, temporary, domain, driver, public, root, domain (+9 more)

### Community 102 - "ViewRenderer"
Cohesion: 0.19
Nodes (6): View, ViewRenderer, Service provider, View, ViewRenderer, ViewTest

### Community 104 - "Build a secure web app and API"
Cohesion: 0.25
Nodes (8): 1. Enable the providers, 2. Build and start the demo, 4. Validation and error rendering, 5. JWT for the API, 6. CORS preflight, 7. Secrets, Build a secure web app and API, Where to go next

### Community 106 - "JsonLogEncoder"
Cohesion: 0.21
Nodes (4): JsonLogEncoder, RequestLoggingMiddleware, JsonLogEncoder, JsonLogEncoderTest

### Community 108 - "io.javelin.core.Application"
Cohesion: 0.06
Nodes (18): Developer API, CaffeineCacheServiceProvider, ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, SecretsServiceProvider, ExceptionHandlerServiceProvider, HttpAdapterConfig, HttpDriverConfig (+10 more)

### Community 128 - ".status"
Cohesion: 0.08
Nodes (9): CookieAndSessionContractTest, DefaultExceptionHandlerTest, HttpKernelTest, BearerAuthMiddlewareTest, ValidationErrorsTest, HttpTimeoutConfigTest, StaticAssetResolverTest, CorsAndCsrfTest (+1 more)

### Community 129 - "HTTP Common Module"
Cohesion: 0.13
Nodes (12): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, TrustedProxyResolver, HTTP JDK Module, JdkHttpServer (+4 more)

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
Cohesion: 0.05
Nodes (12): routes:list — Display registered routes, Cookie-backed sessions, Cookie, Session, SessionStore, StubSession, InMemorySessionStore, MemorySession (+4 more)

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - "Job"
Cohesion: 0.16
Nodes (11): Queue contracts, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, Job, JobHandler (+3 more)

### Community 140 - "Javelin CLI"
Cohesion: 0.17
Nodes (8): CommandRegistry, AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry

### Community 141 - "services"
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 142 - "DownloadPolicy"
Cohesion: 0.15
Nodes (10): Safety, 1. Disks and cache are configured explicitly, 2. Write and read files, 3. Private downloads, 4. S3 or MinIO, 5. Caching, Files and caching, Notes (+2 more)

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

### Community 147 - "PebbleViewRenderer"
Cohesion: 0.17
Nodes (8): Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Shared view data, View Pebble Module, PebbleViewRenderer, Options

### Community 152 - "JavelinViewExtension"
Cohesion: 0.30
Nodes (3): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 155 - "Database"
Cohesion: 0.15
Nodes (5): Layer 2 — Adapters, Core interfaces you can implement, Database, QueryBuilder, RecordingDatabase

### Community 156 - "Exception Module"
Cohesion: 0.40
Nodes (5): ConfigurableExceptionHandler, Configuration, Error pages, Exception Module, Service provider

### Community 157 - ".load"
Cohesion: 0.18
Nodes (6): Startup Time, Why startup is fast, DotenvEnv, Entry, DotenvEnvTest, YamlConfigTest

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

### Community 167 - "ProjectLauncher"
Cohesion: 0.26
Nodes (4): ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 172 - "private"
Cohesion: 0.17
Nodes (12): private, temporary, domain, driver, public, root, storage, default (+4 more)

### Community 175 - "queue"
Cohesion: 0.40
Nodes (5): queue, driver, jetstream, stream, url

### Community 180 - "SecretProvider"
Cohesion: 0.15
Nodes (5): Notes, CompositeSecretProvider, FileSecretProvider, SecretProvider, SecretProviderTest

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 183 - "Config Module"
Cohesion: 0.20
Nodes (8): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, Secret providers, YamlConfig

### Community 185 - "io.javelin.core.Database"
Cohesion: 0.13
Nodes (7): Backward compatibility, Database Migration Module, Dialects, DatabaseManager, JdbcDatabaseServiceProviderTest, MigrationDialect, MigrationDialects

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 188 - "ModelTest"
Cohesion: 0.20
Nodes (6): ModelTest, SampleHydratedUser, SampleUser, Status, DRAFT, PUBLISHED

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

### Community 197 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 201 - "security"
Cohesion: 0.14
Nodes (14): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+6 more)

### Community 202 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 203 - ".handle"
Cohesion: 0.08
Nodes (6): RequestInputTest, RequestLoggingMiddleware, RequestLoggingMiddlewareTest, CorsConfig, CorsMiddleware, CsrfMiddleware

### Community 205 - "Cache Module"
Cohesion: 0.33
Nodes (5): Cache, Cache Module, CacheServiceProvider, InMemoryCache, Notes

### Community 206 - "security"
Cohesion: 0.15
Nodes (13): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+5 more)

### Community 207 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

### Community 208 - "cache"
Cohesion: 0.33
Nodes (6): cache, caffeine, driver, url, default_ttl_seconds, maximum_size

### Community 210 - "profile.md"
Cohesion: 0.33
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 213 - "cache"
Cohesion: 0.40
Nodes (5): cache, caffeine, driver, default_ttl_seconds, maximum_size

### Community 216 - "public"
Cohesion: 0.40
Nodes (5): public, domain, driver, max_bytes, root

### Community 217 - "StructuredLogger"
Cohesion: 0.18
Nodes (10): Log, Logger, Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger (+2 more)

### Community 219 - "json"
Cohesion: 0.50
Nodes (4): json, fail_on_unknown_properties, indent_output, write_nulls

### Community 220 - "Docker Compose"
Cohesion: 0.22
Nodes (9): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running (+1 more)

### Community 221 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 222 - "Generator Helpers"
Cohesion: 0.50
Nodes (4): FileGenerator, Generator Helpers, Names, TemplateEngine

### Community 223 - "Cache"
Cohesion: 0.15
Nodes (3): BackendController, Cache, Cache

### Community 224 - "Storage Module"
Cohesion: 0.50
Nodes (4): Configuration, File recorder, Service provider, Storage Module

### Community 232 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 886 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **103 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `path`, `Override`, `cli/ConsoleKernel.java`, `ProfileConfig`, `NewCommand`, `Javelin.java`, `list`, `AppProfile`, `.instance`, `map`, `ApplicationTest`, `assertequals`, `Container`, `Config`, `.adapterFor`, `MigrationCommandTest.java`, `.run`, `.make`, `ConsoleOutput`, `io.javelin.core.Application`?**
  _High betweenness centrality (0.066) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.08880666049953746 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `Agent Notes`, `Request`, `ApplicationTest`, `io.javelin.core.Application`, `Application`, `.adapterFor`, `Container`, `Module Authoring`, `Javelin.java`, `Database`, `Core Module`?**
  _High betweenness centrality (0.040) - this node is a cross-community bridge._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.1048780487804878 - nodes in this community are weakly interconnected._
- **Why does `Javelin Framework` connect `Container` to `docs/README.md`, `Job`?**
  _High betweenness centrality (0.035) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.13675213675213677 - nodes in this community are weakly interconnected._