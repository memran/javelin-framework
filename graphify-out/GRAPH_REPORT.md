# Graph Report - javelin-framework  (2026-11-25)

## Corpus Check
- 339 files · ~86,661 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 52 file(s) not represented in the graph (top: .stub 37, (none) 9, .xml 2)

## Summary
- 3543 nodes · 8789 edges · 240 communities (124 shown, 116 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 1248 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `296d60e5`
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
- .handle
- Javelin.java
- Request
- .loadDirectory
- ModelTest
- CLI Reference
- Date
- Core Module
- list
- AppProfile
- .imageSupportDetectsAndReadsImageMetadata
- Override
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
- Agent Notes
- .write
- ValidationRule
- io.javelin.core.Application
- Validator
- JdbcDatabase
- JdbcQueryBuilder
- ValidationRule
- StructuredLogger
- caddy
- package.json
- Application Flow
- io.javelin:javelin-starter
- ValkeyCache
- Log
- Handle
- Module Authoring
- nginx
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- JdkHttpServer.java
- Str
- Router
- environment
- .boot
- Json
- Database JDBC Module
- VersionCommand
- RecordingLogger
- JdkHttpServer
- CliException
- Env
- Authenticated
- AdultAgeRule
- Quick Start
- WorkspaceStarterRule
- ConsoleOutput
- JavelinViewExtension
- HtmlResponse
- MapConfig
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- Config
- JsonLogEncoder
- MigrateCommand
- .make
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
- app
- nats
- valkey
- Architecture
- PebbleViewRenderer
- .run
- com.fasterxml.jackson.databind.ObjectMapper
- MultipartData
- Security
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
- .send
- TransactionCallback
- JsonResponse
- queue
- .resolve
- Layer 2 — Adapters
- Override
- errors
- SecretProvider
- primary
- log
- Config Module
- PebbleViewExtensions
- io.javelin.core.Database
- .handle
- Production Performance Tuning
- io.pebbletemplates.pebble.extension.Filter
- ProviderLoaderTest
- DefaultValidator
- Obj
- database
- java.util.regex.Pattern
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- .render
- session
- session
- ServiceProvider
- .commands
- TestConfig
- security
- errors
- .handle
- RateLimitMiddleware.java
- Cache Module
- security
- HTTP Undertow Module
- javelin
- cache
- HTTP Netty Module
- JacksonJsonServiceProvider.java
- YamlConfig
- StubSession
- File
- Logger
- StubSession
- Docker Compose
- MakeControllerCommand
- .kernelAppliesAuthenticationAndPermissionMiddlewareToARoute
- BackendController
- ProviderSorter
- .register
- .register
- AiGenerateModuleCommand
- Http
- NoopLogger
- cors
- cors
- Binding Strategies
- Pattern Reference
- Writing a Middleware Module
- server
- io.javelin.core.AppProfile
- TestCommand

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
- `Middleware reference (built-in)` --references--> `SecureHeadersMiddleware`  [INFERRED]
  docs/module-authoring.md → modules/javelin-security/src/main/java/io/javelin/security/SecureHeadersMiddleware.java
- `Static file serving` --references--> `SecureHeadersMiddleware`  [INFERRED]
  docs/modules/http-jdk.md → modules/javelin-security/src/main/java/io/javelin/security/SecureHeadersMiddleware.java
- `SecureHeadersMiddleware` --references--> `SecurityServiceProvider`  [INFERRED]
  docs/modules/security.md → modules/javelin-security/src/main/java/io/javelin/security/SecurityServiceProvider.java
- `Architecture at a glance` --references--> `ServiceProvider`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Bearer authentication` --references--> `BearerTokenResolver`  [INFERRED]
  docs/modules/security.md → modules/javelin-core/src/main/java/io/javelin/core/security/BearerTokenResolver.java

## Import Cycles
- None detected.

## Communities (240 total, 116 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.18
Nodes (6): ProviderSorterTest, CycleA, FirstProvider, NeedsFirstProvider, SecondProvider, ThirdProvider

### Community 4 - "Override"
Cohesion: 0.15
Nodes (3): Config, Env, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.05
Nodes (20): AbstractCommand, CommandContext, AiChatCommand, AiExplainCommand, AiInstallCommand, BuildCommand, CacheClearCommand, ConfigCacheCommand (+12 more)

### Community 11 - "Collection"
Cohesion: 0.05
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "io.javelin.core.Request"
Cohesion: 0.17
Nodes (5): routes:list — Display registered routes, ConfigurableExceptionHandler, ErrorPageResolver, FileErrorPageResolver, SecureHeadersMiddleware

### Community 13 - "Application"
Cohesion: 0.10
Nodes (8): RouteListCommand, ServeCommand, Application, JavelinStarterServiceProvider, ValidationRuleLoader, ValidationRuleLoaderTest, TestEnv, PebbleViewServiceProvider

### Community 15 - "StaticAssetResolver"
Cohesion: 0.23
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - ".from"
Cohesion: 0.22
Nodes (3): LoggingConfig, LoggingConfigTest, MapConfig

### Community 18 - ".handle"
Cohesion: 0.09
Nodes (7): Cookies and sessions, RequestAttributesTest, CsrfMiddleware, SessionAuthMiddleware, SessionPrincipalResolver, SessionAuthMiddlewareTest, StubSession

### Community 21 - "Javelin.java"
Cohesion: 0.07
Nodes (22): Main, Adding to the hardcoded provider list, Integrating with Starter, Provider registration order, Registration order matters, ProviderLoader, ServiceProvider, SecurityServiceProvider (+14 more)

### Community 22 - "Request"
Cohesion: 0.08
Nodes (5): HomeController, Request, UploadedFile, RequestInputTest, RequestMultipartTest

### Community 24 - ".loadDirectory"
Cohesion: 0.24
Nodes (3): EnvSecretProvider, TypeReference<>, TypeReference<>

### Community 25 - "ModelTest"
Cohesion: 0.10
Nodes (8): Model overridable methods, ModelTest, CastedArticle, SampleHydratedUser, SampleUser, Status, DRAFT, PUBLISHED

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (38): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+30 more)

### Community 28 - "Core Module"
Cohesion: 0.08
Nodes (26): Config, Core Module, Database, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse (+18 more)

### Community 30 - "AppProfile"
Cohesion: 0.16
Nodes (8): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, ChannelInitializer<SocketChannel>

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.10
Nodes (8): Example, Javelin Support, Public API, Arr, Image, Dimensions, Handle, Info

### Community 35 - "io.javelin.core.ServiceProvider"
Cohesion: 0.11
Nodes (13): Logging And Error Handling, JWT adapter, Application profile, Service provider, DevProfileMiddleware, ExceptionHandlerServiceProvider, HttpAdapterConfig, HttpDriverConfig (+5 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.14
Nodes (5): MigrationRunner, MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.16
Nodes (4): DefaultExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.07
Nodes (9): Bearer authentication and authorization, Middleware, Middleware, Next, RedirectResponse, Response, RequiresAuthenticationMiddleware, SecurityResponses (+1 more)

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
Cohesion: 0.18
Nodes (10): app, env, name, json, fail_on_unknown_properties, indent_output, write_nulls, providers (+2 more)

### Community 48 - "Container"
Cohesion: 0.06
Nodes (32): Application, Container, Container, Container, Binding, ContainerTest, CircularA, CircularB (+24 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "PasswordHashStrategy"
Cohesion: 0.11
Nodes (6): Password hashing, BcryptStrategy, PasswordHasher, PasswordHashStrategy, Pbdkdf2Strategy, PasswordHashStrategyTest

### Community 51 - "docker-app.yaml"
Cohesion: 0.14
Nodes (13): app, env, name, javelin, starter, json, fail_on_unknown_properties, indent_output (+5 more)

### Community 52 - "Override"
Cohesion: 0.15
Nodes (7): Core interfaces you can implement, Example: Implementing `Cache` (javelin-cache), Implementing a Core Interface, QueryBuilder, EditableUser, RecordingDatabase, RecordingQueryBuilder

### Community 54 - "Config"
Cohesion: 0.12
Nodes (5): Config, DatabaseConfig, Connection, DatabaseConfigTest, JacksonJsonConfig

### Community 55 - "Deployment"
Cohesion: 0.22
Nodes (9): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Migration Strategy, Performance Benchmarks, Reverse Proxy, Trusted proxy configuration (+1 more)

### Community 56 - ".handle"
Cohesion: 0.13
Nodes (6): Cookie-backed sessions, Cookie, CookieAndSessionContractTest, SessionConfig, SessionMiddleware, SessionMiddlewareTest

### Community 57 - "Route"
Cohesion: 0.10
Nodes (17): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+9 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "RecordingLogger"
Cohesion: 0.14
Nodes (4): QueryTimingConfig, SqlTimer, SqlTimerTest, RecordingLogger

### Community 60 - ".adapterFor"
Cohesion: 0.14
Nodes (9): JdkHttpServiceProvider, Configuration, Configuration, HTTP driver selection, HttpServerAdapter, HttpServerDriverProvider, HttpServerDriverProviderTest, DriverConfig (+1 more)

### Community 61 - "JsonMapper"
Cohesion: 0.13
Nodes (8): Json, API, Backward compatibility, Configuration, JSON Jackson Module, Registration, JsonMapper, JobTest

### Community 63 - "pebble"
Cohesion: 0.12
Nodes (15): driver, pebble, allow_override_core_operators, auto_escaping, cache, default_escaping_strategy, default_locale, greedy_match_method (+7 more)

### Community 65 - "Agent Notes"
Cohesion: 0.13
Nodes (14): Agent Notes, Architect Guidelines, Architecture Intent, Coding Principles, Current MVP Boundaries, Do, Do / Don’t, Don’t (+6 more)

### Community 66 - ".write"
Cohesion: 0.11
Nodes (4): GeneratorSupport, MakeControllerCommand, MakeModelCommand, MakeProviderCommand

### Community 68 - "io.javelin.core.Application"
Cohesion: 0.07
Nodes (16): web, Bearer authentication, CORS, CSRF, HSTS and CSP, PasswordHasher, RateLimitMiddleware, RequestSizeLimitMiddleware (+8 more)

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
Cohesion: 0.23
Nodes (21): io.javelin:javelin-cache, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-db-migration, io.javelin:javelin-demo-app (+13 more)

### Community 78 - "ValkeyCache"
Cohesion: 0.14
Nodes (4): Cache, ValkeyCache, ValkeyCacheTest, Cache

### Community 81 - "Module Authoring"
Cohesion: 0.07
Nodes (26): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+18 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 86 - "Router"
Cohesion: 0.15
Nodes (4): Router, ResolvedRoute, Routes, RouterTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - ".boot"
Cohesion: 0.25
Nodes (6): AppServiceProvider, Boot Sequence, Phase 1: Entry Point, Phase 2: Boot, Phase 3: Dispatch, Config-List Provider Loading

### Community 90 - "Database JDBC Module"
Cohesion: 0.20
Nodes (10): Connection configuration, Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Named connections and timing (+2 more)

### Community 94 - "CliException"
Cohesion: 0.09
Nodes (8): CliException, GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, NewCommand, FileGenerator, Names, TemplateEngine

### Community 96 - "Authenticated"
Cohesion: 0.19
Nodes (4): Authenticated, BearerAuthMiddleware, BearerTokenResolver, AuthorizationMiddlewareTest

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "ConsoleOutput"
Cohesion: 0.27
Nodes (3): ConsoleOutput, MigrationCommandTest, RecordingDatabase

### Community 101 - "JavelinViewExtension"
Cohesion: 0.30
Nodes (3): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction

### Community 102 - "HtmlResponse"
Cohesion: 0.19
Nodes (5): Service provider, HtmlResponse, View, ViewRenderer, ViewTest

### Community 104 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 106 - "JsonLogEncoder"
Cohesion: 0.21
Nodes (4): JsonLogEncoder, RequestLoggingMiddleware, JsonLogEncoder, JsonLogEncoderTest

### Community 108 - ".make"
Cohesion: 0.11
Nodes (5): ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, JdbcDatabaseServiceProvider, NoopLogger, JdbcDatabaseServiceProviderTest

### Community 128 - ".status"
Cohesion: 0.09
Nodes (8): DefaultExceptionHandlerTest, HttpKernelTest, ResponseTest, ValidationErrorsTest, HttpTimeoutConfigTest, StaticAssetResolverTest, CorsAndCsrfTest, ProfileWiringTest

### Community 129 - "HTTP Common Module"
Cohesion: 0.17
Nodes (10): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, HTTP JDK Module, JdkHttpServer, Notes (+2 more)

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
Cohesion: 0.21
Nodes (3): SupportUtilitiesTest, Person, Profile

### Community 134 - "dev.mjs"
Cohesion: 0.25
Nodes (3): bin, env, watchers

### Community 135 - "Session"
Cohesion: 0.11
Nodes (4): Session, SessionStore, InMemorySessionStore, MemorySession

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 138 - "Job"
Cohesion: 0.15
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
Cohesion: 0.17
Nodes (12): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Middleware pipeline, Module Boundaries (+4 more)

### Community 147 - "PebbleViewRenderer"
Cohesion: 0.19
Nodes (8): Automatic escaping, Layouts, includes and components, Options and caching, Rendering, Shared view data, View Pebble Module, PebbleViewRenderer, Options

### Community 149 - ".run"
Cohesion: 0.14
Nodes (3): JavelinConsole, ServeCommand, JavelinConsole

### Community 150 - "com.fasterxml.jackson.databind.ObjectMapper"
Cohesion: 0.21
Nodes (3): DefaultJsonMapper, JacksonJsonMapper, JacksonJsonMapperTest

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 156 - "Exception Module"
Cohesion: 0.29
Nodes (6): ConfigurableExceptionHandler, Configuration, Error pages, Exception Module, Service provider, ExceptionHandler

### Community 157 - ".load"
Cohesion: 0.23
Nodes (4): DotenvEnv, Entry, DotenvEnvTest, YamlConfigTest

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

### Community 175 - "queue"
Cohesion: 0.40
Nodes (5): queue, driver, jetstream, stream, url

### Community 177 - "Layer 2 — Adapters"
Cohesion: 0.33
Nodes (6): Layer 0 — Foundation, Layer 1 — Contracts, Layer 2 — Adapters, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Dependency Graph

### Community 179 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

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
Cohesion: 0.22
Nodes (8): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, YamlConfig, ConfigServiceProvider

### Community 185 - "io.javelin.core.Database"
Cohesion: 0.11
Nodes (7): Backward compatibility, Database Migration Module, Dialects, DatabaseManager, MigrationDialect, MigrationDialects, MigrationDialectsTest

### Community 187 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 189 - "ProviderLoaderTest"
Cohesion: 0.32
Nodes (4): ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder

### Community 190 - "DefaultValidator"
Cohesion: 0.19
Nodes (7): Relationship to `javelin-support`, Supported rules, Validation Module, Validator, DefaultValidator, Validator, DefaultValidatorTest

### Community 192 - "database"
Cohesion: 0.25
Nodes (8): database, connections, default, migrations_dir, query, log_all, slow_ms, timing

### Community 196 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 197 - "session"
Cohesion: 0.33
Nodes (6): session, cookie_name, http_only, same_site, secure, ttl_seconds

### Community 198 - "ServiceProvider"
Cohesion: 0.15
Nodes (7): Adding the module to the parent POM, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, pom.xml Setup, The ServiceProvider Contract, ServiceProvider, CycleB

### Community 199 - ".commands"
Cohesion: 0.10
Nodes (5): CommandRegistry, CommandRegistry, MakeModelCommand, ConsoleKernel, CommandGenerationTest

### Community 201 - "security"
Cohesion: 0.15
Nodes (13): enabled, audience, issuer, secret, bcrypt_cost, hash, security, csp (+5 more)

### Community 202 - "errors"
Cohesion: 0.50
Nodes (4): errors, pages_dir, redact, redact_keys

### Community 204 - "RateLimitMiddleware.java"
Cohesion: 0.21
Nodes (4): TrustedProxyResolver, Trusted proxy configuration, RateLimitMiddleware, Bucket

### Community 205 - "Cache Module"
Cohesion: 0.40
Nodes (5): Cache, Cache Module, CacheServiceProvider, InMemoryCache, Notes

### Community 206 - "security"
Cohesion: 0.17
Nodes (12): enabled, audience, issuer, secret, bcrypt_cost, hash, security, csp (+4 more)

### Community 207 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

### Community 208 - "javelin"
Cohesion: 0.67
Nodes (3): javelin, starter, http-driver

### Community 209 - "cache"
Cohesion: 0.67
Nodes (3): cache, driver, url

### Community 210 - "HTTP Netty Module"
Cohesion: 0.67
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 217 - "Logger"
Cohesion: 0.31
Nodes (9): Log, Logger, Logging SLF4J Module, LoggingConfig, Notes, Slf4jLogger, Slf4jLogServiceProvider, StructuredLogger (+1 more)

### Community 220 - "Docker Compose"
Cohesion: 0.22
Nodes (9): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running (+1 more)

### Community 231 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 232 - "cors"
Cohesion: 0.50
Nodes (4): cors, allow_credentials, allowed_origins, max_age_seconds

### Community 233 - "Binding Strategies"
Cohesion: 0.50
Nodes (4): `bind(A, B)` — reusable factory, Binding Strategies, `instance(T, object)` — pre-built singleton, `singleton(T, supplier)` — lazy singleton

### Community 234 - "Pattern Reference"
Cohesion: 0.50
Nodes (4): Checklist for new modules, Module type matrix, Pattern Reference, Standard service provider template

### Community 235 - "Writing a Middleware Module"
Cohesion: 0.50
Nodes (4): Example: Request timing middleware, Middleware reference (built-in), Service provider adds middleware to the router, Writing a Middleware Module

### Community 237 - "server"
Cohesion: 0.67
Nodes (3): server, host, port

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 826 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **116 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `.status`, `path`, `cli/ConsoleKernel.java`, `Javelin.java`, `.run`, `list`, `AppProfile`, `io.javelin.core.ServiceProvider`, `ApplicationTest`, `Container`, `Config Module`, `.adapterFor`, `ProviderLoaderTest`, `io.javelin.core.Application`, `ServiceProvider`, `.commands`, `ProviderSorter`, `.register`, `ConsoleOutput`, `HtmlResponse`, `.make`, `.output`?**
  _High betweenness centrality (0.073) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.09408033826638477 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `ProviderSorter`, `Agent Notes`, `ServiceProvider`, `io.javelin.core.ServiceProvider`, `HtmlResponse`, `ApplicationTest`, `Pattern Reference`, `Application`, `.adapterFor`, `Container`, `Module Authoring`, `Layer 2 — Adapters`, `Override`, `Javelin.java`, `Config Module`, `Core Module`, `ProviderLoaderTest`?**
  _High betweenness centrality (0.039) - this node is a cross-community bridge._
- **Should `cli/ConsoleKernel.java` be split into smaller, more focused modules?**
  _Cohesion score 0.05417236662106703 - nodes in this community are weakly interconnected._
- **Why does `Database` connect `Database` to `path`, `ConsoleOutput`, `MigrationRunner`, `Override`, `TransactionCallback`, `Layer 2 — Adapters`, `Override`, `Javelin.java`, `io.javelin.core.Database`, `Database JDBC Module`, `Core Module`, `ModelTest`?**
  _High betweenness centrality (0.032) - this node is a cross-community bridge._
- **Should `Collection` be split into smaller, more focused modules?**
  _Cohesion score 0.05384615384615385 - nodes in this community are weakly interconnected._