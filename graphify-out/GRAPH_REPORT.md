# Graph Report - javelin-framework  (2026-11-25)

## Corpus Check
- 343 files · ~88,068 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 52 file(s) not represented in the graph (top: .stub 37, (none) 9, .xml 2)

## Summary
- 3578 nodes · 8907 edges · 215 communities (120 shown, 95 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1273 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `4fa5a4f7`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
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
- ModelTest
- CLI Reference
- Date
- Core Module
- AppProfile
- .imageSupportDetectsAndReadsImageMetadata
- .instance
- Validation
- io.javelin.core.ServiceProvider
- MigrationRunner
- JdkHttpServerIntegrationTest
- Request
- Support Module
- ApplicationTest
- TrustedProxyResolver
- Input
- app.yaml
- Container
- Console Module
- PasswordHashStrategy
- docker-app.yaml
- Override
- NettyHttpServer
- Config
- Deployment
- .handle
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
- io.javelin.core.Request
- caddy
- package.json
- Application Flow
- io.javelin:javelin-starter
- ValkeyCache
- Logger
- .login
- Module Authoring
- nginx
- Arr
- JdkHttpServer.java
- Str
- Router
- environment
- .boot
- Database JDBC Module
- VersionCommand
- RecordingLogger
- JdkHttpServer
- CliException
- Env
- .stringify
- AdultAgeRule
- Quick Start
- WorkspaceStarterRule
- StubSession
- Cache
- QueryBuilder
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
- app
- nats
- valkey
- Architecture
- PebbleViewExtensions
- NewCommand
- com.fasterxml.jackson.databind.ObjectMapper
- .handle
- .decode
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
- jwt
- json
- Response
- queue
- .resolve
- Response
- Model
- app
- SecretProvider
- primary
- log
- Config Module
- io.javelin.core.Database
- .handle
- Production Performance Tuning
- DefaultValidator
- database
- session
- session
- .commands
- TestConfig
- security
- errors
- .handle
- Cache Module
- security
- HTTP Undertow Module
- HTTP Netty Module
- YamlConfig
- StubSession
- StructuredLogger
- Docker Compose
- .commands
- .kernelAppliesAuthenticationAndPermissionMiddlewareToARoute
- BackendController
- NoopLogger
- cors
- cors
- server

## God Nodes (most connected - your core abstractions)
1. `Application` - 90 edges
2. `Request` - 85 edges
3. `Router` - 76 edges
4. `Response` - 72 edges
5. `CommandContext` - 68 edges
6. `Collection` - 63 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 52 edges
9. `Config` - 44 edges
10. `Handle` - 43 edges

## Surprising Connections (you probably didn't know these)
- `5. JWT for the API` --references--> `JwtAuthServiceProvider`  [INFERRED]
  docs/tutorials/secure-web-app.md → modules/javelin-security/src/main/java/io/javelin/security/JwtAuthServiceProvider.java
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `1. Enable the providers` --references--> `SessionServiceProvider`  [INFERRED]
  docs/tutorials/secure-web-app.md → modules/javelin-security/src/main/java/io/javelin/security/SessionServiceProvider.java
- `Notes` --references--> `ValkeyCacheServiceProvider`  [INFERRED]
  docs/modules/cache.md → modules/javelin-cache-valkey/src/main/java/io/javelin/cache/valkey/ValkeyCacheServiceProvider.java
- `TemplateEngine` --references--> `TemplateEngine`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/cli/generator/TemplateEngine.java

## Import Cycles
- None detected.

## Communities (215 total, 95 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.08
Nodes (17): Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, The ServiceProvider Contract, ConfigServiceProvider, ProviderSorter, ServiceProvider, ProviderLoaderTest, BaseLoadedProvider (+9 more)

### Community 4 - "Override"
Cohesion: 0.15
Nodes (3): Config, Env, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.05
Nodes (20): AbstractCommand, CommandContext, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, CacheClearCommand, DevCommand (+12 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (9): 3. Browser login with CSRF, What happens on a bad CSRF token, Ai, Handle, Message, Reply, Http, Handle (+1 more)

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.19
Nodes (3): ConfigurableExceptionHandler, ErrorPageResolver, FileErrorPageResolver

### Community 13 - "Application"
Cohesion: 0.07
Nodes (8): JavelinConsole, JavelinConsole, RouteListCommand, ServeCommand, Application, JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.22
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - "Authenticated"
Cohesion: 0.10
Nodes (8): Bearer authentication and authorization, Middleware, Authenticated, RequiresAuthenticationMiddleware, SecurityResponses, RequestAttributesTest, AuthorizationMiddlewareTest, SessionAuthMiddlewareTest

### Community 21 - "Javelin.java"
Cohesion: 0.07
Nodes (25): Adding to the hardcoded provider list, Integrating with Starter, Provider registration order, Registration order matters, ServiceProvider, Application profile, Default Service Providers, HTTP driver selection (+17 more)

### Community 22 - "MultipartData"
Cohesion: 0.07
Nodes (5): MultipartData, PartDisposition, UploadedFile, RequestMultipartTest, File

### Community 24 - ".loadDirectory"
Cohesion: 0.29
Nodes (3): EnvSecretProvider, TypeReference<>, TypeReference<>

### Community 25 - "ModelTest"
Cohesion: 0.13
Nodes (7): Model overridable methods, ModelTest, SampleHydratedUser, SampleUser, Status, DRAFT, PUBLISHED

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (39): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+31 more)

### Community 28 - "Core Module"
Cohesion: 0.11
Nodes (19): Config, Core Module, Database, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HttpKernel (+11 more)

### Community 30 - "AppProfile"
Cohesion: 0.16
Nodes (8): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, ChannelInitializer<SocketChannel>

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.10
Nodes (8): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info, Obj

### Community 32 - ".instance"
Cohesion: 0.11
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.07
Nodes (25): Bearer authentication, CORS, CSRF, HSTS and CSP, JWT adapter, PasswordHasher, RateLimitMiddleware, RequestSizeLimitMiddleware (+17 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.15
Nodes (5): MigrationRunner, MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.21
Nodes (4): DefaultExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Request"
Cohesion: 0.06
Nodes (12): UserController, Layer 1 — Contracts, DevProfileMiddleware, ExceptionHandler, Middleware, Next, Request, RequiresPermissionMiddleware (+4 more)

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
Cohesion: 0.14
Nodes (13): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+5 more)

### Community 48 - "Container"
Cohesion: 0.06
Nodes (31): Container, Container, Container, Binding, ContainerTest, CircularA, CircularB, CloseableResource (+23 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.07
Nodes (7): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, PasswordHashStrategyTest, Security

### Community 51 - "docker-app.yaml"
Cohesion: 0.14
Nodes (13): cache, driver, url, errors, pages_dir, redact, redact_keys, javelin (+5 more)

### Community 52 - "Override"
Cohesion: 0.15
Nodes (4): CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder

### Community 54 - "Config"
Cohesion: 0.13
Nodes (6): Config, DatabaseConfig, Connection, DatabaseConfigTest, ExceptionConfig, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.20
Nodes (10): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Logging And Error Handling, Migration Strategy, Performance Benchmarks, Reverse Proxy (+2 more)

### Community 56 - ".handle"
Cohesion: 0.17
Nodes (4): CookieAndSessionContractTest, SessionConfig, SessionMiddleware, SessionMiddlewareTest

### Community 57 - "Route"
Cohesion: 0.08
Nodes (19): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+11 more)

### Community 58 - "Override"
Cohesion: 0.14
Nodes (4): TransactionCallback, MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.17
Nodes (4): QueryTimingConfig, SqlTimer, SqlTimerTest, RecordingLogger

### Community 60 - ".adapterFor"
Cohesion: 0.15
Nodes (8): JdkHttpServiceProvider, Configuration, Configuration, HttpServerAdapter, HttpServerDriverProvider, HttpServerDriverProviderTest, DriverConfig, DriverEnv

### Community 61 - "JsonMapper"
Cohesion: 0.12
Nodes (8): Json, API, Backward compatibility, Configuration, JSON Jackson Module, Registration, Json, JsonMapper

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

### Community 73 - "io.javelin.core.Request"
Cohesion: 0.17
Nodes (3): RequestLoggingMiddleware, CsrfMiddleware, SecureHeadersMiddleware

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
Cohesion: 0.23
Nodes (21): io.javelin:javelin-cache, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration, io.javelin:javelin-demo-app (+13 more)

### Community 79 - "Logger"
Cohesion: 0.14
Nodes (8): Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger, Log, Logger

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (42): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+34 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 86 - "Router"
Cohesion: 0.10
Nodes (6): Router, RouterTest, NettyHttpServerIntegrationTest, RunningServer, UndertowHttpServerIntegrationTest, RunningServer

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - ".boot"
Cohesion: 0.13
Nodes (9): AppServiceProvider, Main, Phase 2: Boot, Startup Time, Why startup is fast, Config-List Provider Loading, Application, Notes (+1 more)

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 94 - "CliException"
Cohesion: 0.09
Nodes (6): CliException, ConfigCacheCommand, MakeMigrationCommand, FileGenerator, Names, TemplateEngine

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 102 - "QueryBuilder"
Cohesion: 0.29
Nodes (4): Layer 2 — Adapters, Core interfaces you can implement, QueryBuilder, ViewRenderer

### Community 104 - "Build a secure web app and API"
Cohesion: 0.29
Nodes (7): 1. Enable the providers, 2. Build and start the demo, 4. Validation and error rendering, 5. JWT for the API, 6. CORS preflight, Build a secure web app and API, Where to go next

### Community 106 - "JsonLogEncoder"
Cohesion: 0.23
Nodes (4): JsonLogEncoder, RequestLoggingMiddleware, JsonLogEncoder, JsonLogEncoderTest

### Community 108 - "io.javelin.core.Application"
Cohesion: 0.07
Nodes (7): web, ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, JacksonJsonServiceProvider, NatsQueueServiceProvider, NatsQueueServiceProviderTest, JwtAuthServiceProvider

### Community 128 - ".status"
Cohesion: 0.07
Nodes (9): DefaultExceptionHandlerTest, HttpKernelTest, ValidationErrorsTest, HttpTimeoutConfigTest, StaticAssetResolverTest, RequestLoggingMiddlewareTest, CorsAndCsrfTest, ProfileWiringTest (+1 more)

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
Cohesion: 0.07
Nodes (8): Cookies and sessions, Cookie-backed sessions, Cookie, Session, SessionStore, StubSession, InMemorySessionStore, MemorySession

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - "Job"
Cohesion: 0.17
Nodes (11): Queue contracts, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, Job, JobHandler (+3 more)

### Community 140 - "Javelin CLI"
Cohesion: 0.29
Nodes (6): AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image

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
Nodes (16): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Layer 0 — Foundation, Layer 3 — Toolchain (+8 more)

### Community 147 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (16): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Service provider (+8 more)

### Community 150 - "com.fasterxml.jackson.databind.ObjectMapper"
Cohesion: 0.21
Nodes (3): DefaultJsonMapper, JacksonJsonMapper, JacksonJsonMapperTest

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 156 - "Exception Module"
Cohesion: 0.40
Nodes (5): ConfigurableExceptionHandler, Configuration, Error pages, Exception Module, Service provider

### Community 157 - ".load"
Cohesion: 0.31
Nodes (3): DotenvEnv, Entry, DotenvEnvTest

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
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 172 - "jwt"
Cohesion: 0.40
Nodes (5): audience, issuer, secret, ttl_seconds, jwt

### Community 173 - "json"
Cohesion: 0.50
Nodes (4): json, fail_on_unknown_properties, indent_output, write_nulls

### Community 174 - "Response"
Cohesion: 0.07
Nodes (8): HomeController, HtmlResponse, JsonResponse, Response, ValidationErrors, View, ResponseTest, ViewTest

### Community 175 - "queue"
Cohesion: 0.40
Nodes (5): queue, driver, jetstream, stream, url

### Community 177 - "Response"
Cohesion: 0.50
Nodes (4): HtmlResponse, JsonResponse, RedirectResponse, Response, Response instance methods, Response static factories

### Community 178 - "Model"
Cohesion: 0.50
Nodes (4): Model, Model instance methods, Model static methods, User

### Community 179 - "app"
Cohesion: 0.67
Nodes (3): app, env, name

### Community 180 - "SecretProvider"
Cohesion: 0.14
Nodes (6): Notes, 7. Secrets, CompositeSecretProvider, FileSecretProvider, SecretProvider, SecretProviderTest

### Community 181 - "primary"
Cohesion: 0.20
Nodes (10): primary, connection_timeout_ms, database, driver, host, max_pool_size, minimum_idle, password (+2 more)

### Community 182 - "log"
Cohesion: 0.20
Nodes (10): netty, io, root, log, channel, correlation_id_header, level, path (+2 more)

### Community 183 - "Config Module"
Cohesion: 0.25
Nodes (8): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, Secret providers, YamlConfig

### Community 185 - "io.javelin.core.Database"
Cohesion: 0.15
Nodes (6): Backward compatibility, Database Migration Module, Dialects, DatabaseManager, JdbcDatabaseServiceProviderTest, MigrationDialect

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 190 - "DefaultValidator"
Cohesion: 0.20
Nodes (8): Relationship to `javelin-support`, Service provider, Supported rules, Validation Module, Validator, DefaultValidator, Validator, DefaultValidatorTest

### Community 192 - "database"
Cohesion: 0.25
Nodes (8): database, connections, default, migrations_dir, query, log_all, slow_ms, timing

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 197 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 199 - ".commands"
Cohesion: 0.13
Nodes (4): ConsoleKernel, CommandGenerationTest, MigrationCommandTest, RecordingDatabase

### Community 201 - "security"
Cohesion: 0.14
Nodes (14): enabled, audience, issuer, secret, ttl_seconds, bcrypt_cost, hash, security (+6 more)

### Community 202 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 205 - "Cache Module"
Cohesion: 0.33
Nodes (5): Cache, Cache Module, CacheServiceProvider, InMemoryCache, Notes

### Community 206 - "security"
Cohesion: 0.25
Nodes (8): enabled, bcrypt_cost, hash, security, csp, hsts, max_request_bytes, password

### Community 207 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

### Community 210 - "HTTP Netty Module"
Cohesion: 0.67
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 217 - "StructuredLogger"
Cohesion: 0.25
Nodes (3): Log, Logger, StructuredLogger

### Community 220 - "Docker Compose"
Cohesion: 0.22
Nodes (9): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running (+1 more)

### Community 221 - ".commands"
Cohesion: 0.08
Nodes (8): CommandRegistry, CommandRegistry, AiGenerateModuleCommand, GenerateCommandSupport, MakeControllerCommand, MakeModelCommand, MakeModuleCommand, MakeServiceCommand

### Community 231 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 232 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 237 - "server"
Cohesion: 0.67
Nodes (3): server, host, port

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 834 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **95 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `.instance`, `io.javelin.core.ServiceProvider`, `optional`, `cli/ConsoleKernel.java`, `.commands`, `ApplicationTest`, `io.javelin.core.Application`, `SupportUtilitiesTest.java`, `Container`, `Javelin.java`, `NewCommand`, `AppProfile`, `Config`, `.adapterFor`, `.output`?**
  _High betweenness centrality (0.089) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.08081632653061224 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `Agent Notes`, `io.javelin.core.ServiceProvider`, `QueryBuilder`, `Request`, `ApplicationTest`, `Application`, `.adapterFor`, `Container`, `Module Authoring`, `Javelin.java`, `.boot`, `Core Module`?**
  _High betweenness centrality (0.039) - this node is a cross-community bridge._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.11904761904761904 - nodes in this community are weakly interconnected._
- **Should `cli/ConsoleKernel.java` be split into smaller, more focused modules?**
  _Cohesion score 0.05393000573723465 - nodes in this community are weakly interconnected._
- **Should `Handle` be split into smaller, more focused modules?**
  _Cohesion score 0.057946069994262765 - nodes in this community are weakly interconnected._