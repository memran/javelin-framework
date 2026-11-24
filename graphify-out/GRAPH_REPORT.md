# Graph Report - javelin-framework  (2026-11-24)

## Corpus Check
- 251 files · ~66,912 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 43 file(s) not represented in the graph (top: .stub 31, (none) 8, .xml 1)

## Summary
- 2648 nodes · 6517 edges · 159 communities (85 shown, 74 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 890 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `4a10ed71`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- Starter Module
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- Javelin Framework
- Application
- StaticAssetResolver
- JavelinViewExtension.java
- SecureHeadersMiddleware
- Javelin.java
- UploadedFile
- Env
- Model
- CLI Reference
- Date
- Container
- commandline
- AppProfile
- .imageSupportDetectsAndReadsImageMetadata
- .instance
- Validation
- NettyHttpServerIntegrationTest.java
- NatsQueue.java
- MigrationRunner
- JdkHttpServerIntegrationTest
- Response
- Support Module
- TrustedProxyResolver
- Input
- database
- org.junit.jupiter.api.Test
- PebbleViewRenderer
- InMemoryCache
- Console Module
- Application
- docker-app.yaml
- Override
- NettyHttpServer
- Production Performance Tuning
- Core Module
- ProjectLauncher
- RouteHandler
- Override
- Arr
- HttpServerAdapter
- HtmlResponse
- JdbcDatabase
- pebble
- docs/README.md
- Agent Notes
- .write
- ValidationRule
- .adapterFor
- Validator
- Architecture
- JdbcQueryBuilder
- ValidationRule
- ViewRenderer
- io.javelin.core.Application
- package.json
- .commands
- io.javelin:javelin-starter
- ValkeyCache
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- Obj
- Module Authoring
- WorkspaceAgeRule
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- JdkHttpServer
- Str
- Router
- environment
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- Json
- Database JDBC Module
- VersionCommand
- Override
- Request
- GenerateCommandSupport
- AppProfile
- Logger
- AdultAgeRule
- Router
- WorkspaceStarterRule
- Slf4jLogger
- PebbleViewExtensions
- .run
- Log
- AI Commands
- Config Module
- MigrateCommand
- Docker Compose
- need_command
- Config
- io.javelin:javelin-parent
- Config
- Job
- ValidationRuleRegistry
- TrustedProxyResolver
- .output
- NoopLogger
- AdultAgeRule
- HTTP Common Module
- Cache Module
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- Response
- depends_on
- healthcheck
- objects
- .application
- Javelin CLI
- services
- HttpKernel
- app
- nats
- valkey
- Request
- Status
- Layer 2 — Adapters
- HTTP JDK Module
- build.sh script

## God Nodes (most connected - your core abstractions)
1. `Application` - 89 edges
2. `Request` - 82 edges
3. `Response` - 79 edges
4. `Router` - 71 edges
5. `CommandContext` - 70 edges
6. `Collection` - 63 edges
7. `HttpKernel` - 57 edges
8. `Input` - 45 edges
9. `AbstractCommand` - 45 edges
10. `Handle` - 43 edges

## Surprising Connections (you probably didn't know these)
- `Notes` --references--> `ValkeyCacheServiceProvider`  [INFERRED]
  docs/modules/cache.md → modules/javelin-cache-valkey/src/main/java/io/javelin/cache/valkey/ValkeyCacheServiceProvider.java
- `TemplateEngine` --references--> `TemplateEngine`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/cli/generator/TemplateEngine.java
- `ConsoleServiceProvider` --references--> `ConsoleServiceProvider`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/console/ConsoleServiceProvider.java
- `HTTP driver selection` --references--> `HttpServerDriverProvider`  [INFERRED]
  docs/modules/starter.md → modules/javelin-starter/src/main/java/io/javelin/starter/HttpServerDriverProvider.java
- `Trusted proxy configuration` --references--> `JdkHttpServiceProvider`  [INFERRED]
  docs/deployment.md → modules/javelin-http-jdk/src/main/java/io/javelin/http/jdk/JdkHttpServiceProvider.java

## Import Cycles
- None detected.

## Communities (159 total, 74 thin omitted)

### Community 0 - "Starter Module"
Cohesion: 0.11
Nodes (9): Main, HTTP driver selection, Javelin, Notes, Starter Module, ValidationRuleLoader, WorkspaceDefaults, Javelin (+1 more)

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.05
Nodes (18): AbstractCommand, CommandContext, AiGenerateModuleCommand, BuildCommand, ConfigCacheCommand, DevCommand, DoctorCommand, MigrateCommand (+10 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "Javelin Framework"
Cohesion: 0.12
Nodes (16): Boot Flow, Build And Test, CLI, Database, Documentation, Example Routes, First Run, Install (+8 more)

### Community 15 - "StaticAssetResolver"
Cohesion: 0.24
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - "JavelinViewExtension.java"
Cohesion: 0.25
Nodes (3): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction

### Community 18 - "SecureHeadersMiddleware"
Cohesion: 0.15
Nodes (14): routes:list — Display registered routes, Example: Request timing middleware, Middleware reference (built-in), Service provider adds middleware to the router, Writing a Middleware Module, Notes, PasswordHasher, RateLimitMiddleware (+6 more)

### Community 21 - "Javelin.java"
Cohesion: 0.13
Nodes (18): Adding to the hardcoded provider list, Integrating with Starter, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Provider registration order, Registration order matters, The ServiceProvider Contract, Default Service Providers (+10 more)

### Community 22 - "UploadedFile"
Cohesion: 0.06
Nodes (6): MultipartData, PartDisposition, UploadedFile, RequestMultipartTest, File, Security

### Community 24 - "Env"
Cohesion: 0.06
Nodes (29): Application Flow, Boot Sequence, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development) (+21 more)

### Community 25 - "Model"
Cohesion: 0.08
Nodes (6): Model overridable methods, Database, Model, ModelTest, SampleHydratedUser, SampleUser

### Community 26 - "CLI Reference"
Cohesion: 0.06
Nodes (33): build — Package the application, cache:clear — Clear application cache, CLI Reference, Command summary, Config, config:cache — Cache configuration, dev — Development server, doctor — System diagnostics (+25 more)

### Community 28 - "Container"
Cohesion: 0.20
Nodes (8): Application, Container, Container, Container, Binding, ContainerTest, Dependency, Service

### Community 30 - "AppProfile"
Cohesion: 0.22
Nodes (7): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.14
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 34 - "NettyHttpServerIntegrationTest.java"
Cohesion: 0.13
Nodes (5): Bearer authentication, Authenticated, BearerAuthMiddleware, BearerTokenResolver, RequiresPermissionMiddleware

### Community 36 - "MigrationRunner"
Cohesion: 0.15
Nodes (4): MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.19
Nodes (4): DefaultExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.08
Nodes (5): HomeController, JsonResponse, RedirectResponse, Response, SecurityResponses

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 42 - "TrustedProxyResolver"
Cohesion: 0.21
Nodes (3): ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolverTest

### Community 44 - "database"
Cohesion: 0.09
Nodes (22): app, env, name, database, database, driver, host, password (+14 more)

### Community 49 - "Console Module"
Cohesion: 0.10
Nodes (17): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+9 more)

### Community 50 - "Application"
Cohesion: 0.07
Nodes (7): ConfigServiceProvider, ConfigServiceProvider, JavelinConsole, RouteListCommand, ServeCommand, Application, ConsoleKernel

### Community 51 - "docker-app.yaml"
Cohesion: 0.06
Nodes (33): app, env, name, cache, driver, url, database, database (+25 more)

### Community 52 - "Override"
Cohesion: 0.09
Nodes (11): Database, Model, Model instance methods, Model static methods, QueryBuilder, TransactionCallback, QueryBuilder, CastedArticle (+3 more)

### Community 53 - "NettyHttpServer"
Cohesion: 0.19
Nodes (3): NettyHttpServer, RequestHandler, ChannelInitializer<SocketChannel>

### Community 54 - "Production Performance Tuning"
Cohesion: 0.11
Nodes (18): Caddy alternative, Connection pool (HikariCP), Deployment, Graceful Shutdown, Health Checks, JMX monitoring, JVM flags, Migration Strategy (+10 more)

### Community 55 - "Core Module"
Cohesion: 0.15
Nodes (13): Config, Core Module, Env, HttpMethod, HttpServerAdapter, Json, Log, Logger (+5 more)

### Community 56 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 57 - "RouteHandler"
Cohesion: 0.09
Nodes (19): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+11 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 60 - "HttpServerAdapter"
Cohesion: 0.16
Nodes (13): Core interfaces you can implement, JdkHttpServiceProvider, Configuration, Configuration, Application profile, DevProfileMiddleware, HttpServerAdapter, HttpAdapterConfig (+5 more)

### Community 61 - "HtmlResponse"
Cohesion: 0.29
Nodes (3): HtmlResponse, View, ViewTest

### Community 63 - "pebble"
Cohesion: 0.12
Nodes (15): driver, pebble, allow_override_core_operators, auto_escaping, cache, default_escaping_strategy, default_locale, greedy_match_method (+7 more)

### Community 64 - "docs/README.md"
Cohesion: 0.12
Nodes (9): Behavior, HTTP Netty Module, Programmatic use, Behavior, HTTP Undertow Module, Programmatic use, Javelin Docs, Module Index (+1 more)

### Community 65 - "Agent Notes"
Cohesion: 0.13
Nodes (14): Agent Notes, Architect Guidelines, Architecture Intent, Coding Principles, Current MVP Boundaries, Do, Do / Don’t, Don’t (+6 more)

### Community 66 - ".write"
Cohesion: 0.13
Nodes (4): GeneratorSupport, MakeControllerCommand, MakeModelCommand, MakeProviderCommand

### Community 68 - ".adapterFor"
Cohesion: 0.20
Nodes (3): HttpServerDriverProviderTest, DriverConfig, DriverEnv

### Community 70 - "Architecture"
Cohesion: 0.15
Nodes (12): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Middleware pipeline, Module Boundaries (+4 more)

### Community 73 - "ViewRenderer"
Cohesion: 0.18
Nodes (8): Notes, Options, PebbleViewExtensions, PebbleViewRenderer, PebbleViewServiceProvider, Using templates in routes, View Pebble Module, ViewRenderer

### Community 74 - "io.javelin.core.Application"
Cohesion: 0.15
Nodes (3): BackendController, web, Cache

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 76 - ".commands"
Cohesion: 0.07
Nodes (10): CommandRegistry, CommandRegistry, AiChatCommand, AiExplainCommand, AiInstallCommand, CacheClearCommand, MakeControllerCommand, MakeModelCommand (+2 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.28
Nodes (17): io.javelin:javelin-cache, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-demo-app, io.javelin:javelin-http-common (+9 more)

### Community 78 - "ValkeyCache"
Cohesion: 0.15
Nodes (5): InMemoryCache, ValkeyCache, ValkeyCacheTest, Cache, User

### Community 79 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (39): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+31 more)

### Community 83 - ".returnsAllowHeaderAndEnforcesBearerAuthentication"
Cohesion: 0.17
Nodes (3): BearerAuthMiddlewareTest, NettyHttpServerIntegrationTest, RunningServer

### Community 86 - "Router"
Cohesion: 0.10
Nodes (5): DefaultExceptionHandlerTest, HttpKernelTest, ResponseTest, RouterTest, StaticAssetResolverTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - ".returnsAllowHeaderAndEnforcesBearerAuthentication"
Cohesion: 0.21
Nodes (3): UndertowHttpServer, UndertowHttpServerIntegrationTest, RunningServer

### Community 90 - "Database JDBC Module"
Cohesion: 0.22
Nodes (8): Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Notes, QueryBuilder

### Community 93 - "Request"
Cohesion: 0.07
Nodes (10): Bearer authentication and authorization, Middleware, ExceptionHandler, Next, Request, RequiresAuthenticationMiddleware, RequestAttributesTest, AuthorizationMiddlewareTest (+2 more)

### Community 94 - "GenerateCommandSupport"
Cohesion: 0.11
Nodes (6): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, FileGenerator, Names, TemplateEngine

### Community 96 - "Logger"
Cohesion: 0.43
Nodes (5): Logging SLF4J Module, Notes, Slf4jLogger, Slf4jLogServiceProvider, Logger

### Community 98 - "Router"
Cohesion: 0.19
Nodes (4): Layer 1 — Contracts, Middleware, Router, RequestSizeLimitMiddleware

### Community 101 - "PebbleViewExtensions"
Cohesion: 0.22
Nodes (3): PebbleViewExtensions, RegisteredExtension, PebbleViewRendererTest

### Community 104 - "AI Commands"
Cohesion: 0.40
Nodes (5): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI

### Community 106 - "Config Module"
Cohesion: 0.29
Nodes (6): Config interface methods, Config Module, DotenvEnv, Environment expression resolution, Notes, YamlConfig

### Community 108 - "Docker Compose"
Cohesion: 0.14
Nodes (11): AppServiceProvider, Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration (+3 more)

### Community 123 - "Job"
Cohesion: 0.10
Nodes (11): Queue contracts, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, Job, JobHandler (+3 more)

### Community 129 - "HTTP Common Module"
Cohesion: 0.25
Nodes (7): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, TrustedProxyResolver, Trusted proxy configuration

### Community 130 - "Cache Module"
Cohesion: 0.50
Nodes (4): Cache, Cache Module, CacheServiceProvider, Notes

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

### Community 135 - "Response"
Cohesion: 0.50
Nodes (4): HtmlResponse, JsonResponse, RedirectResponse, Response, Response instance methods, Response static factories

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 140 - "Javelin CLI"
Cohesion: 0.29
Nodes (6): AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image

### Community 141 - "services"
Cohesion: 0.33
Nodes (5): services, volumes, natsdata, pgdata, valkeydata

### Community 142 - "HttpKernel"
Cohesion: 0.67
Nodes (3): DefaultExceptionHandler, ExceptionHandler, HttpKernel

### Community 143 - "app"
Cohesion: 0.33
Nodes (6): build, ports, restart, context, dockerfile, app

### Community 144 - "nats"
Cohesion: 0.33
Nodes (6): command, image, ports, restart, volumes, nats

### Community 145 - "valkey"
Cohesion: 0.33
Nodes (6): valkey, command, image, ports, restart, volumes

### Community 146 - "Request"
Cohesion: 0.67
Nodes (3): File Uploads, Request, UploadedFile

### Community 147 - "Status"
Cohesion: 0.67
Nodes (3): Status, DRAFT, PUBLISHED

### Community 155 - "Layer 2 — Adapters"
Cohesion: 0.40
Nodes (5): Layer 0 — Foundation, Layer 2 — Adapters, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Dependency Graph

### Community 156 - "HTTP JDK Module"
Cohesion: 0.40
Nodes (5): HTTP JDK Module, JdkHttpServer, Notes, Static file serving, Trusted proxy resolution

### Community 161 - "build.sh script"
Cohesion: 0.83
Nodes (3): build.sh script, build_assets(), build_java()

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 658 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **74 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `Starter Module`, `path`, `Override`, `cli/ConsoleKernel.java`, `objects`, `.application`, `Application`, `Javelin.java`, `Container`, `AppProfile`, `.instance`, `org.junit.jupiter.api.Test`, `HttpServerAdapter`, `.adapterFor`, `ViewRenderer`, `.commands`, `.registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot`, `Log`, `.output`?**
  _High betweenness centrality (0.086) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `Starter Module` be split into smaller, more focused modules?**
  _Cohesion score 0.11428571428571428 - nodes in this community are weakly interconnected._
- **Why does `Collection` connect `Collection` to `objects`, `SupportUtilitiesTest`?**
  _High betweenness centrality (0.069) - this node is a cross-community bridge._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.10793650793650794 - nodes in this community are weakly interconnected._
- **Why does `Request` connect `Request` to `HTTP Common Module`, `path`, `NettyHttpServerIntegrationTest.java`, `Router`, `StaticAssetResolver.java`, `JdkHttpServerIntegrationTest`, `Response`, `io.javelin.core.Application`, `.application`, `org.junit.jupiter.api.Test`, `NettyHttpServer.java`, `.returnsAllowHeaderAndEnforcesBearerAuthentication`, `JdkHttpServer`, `Router`, `UploadedFile`, `Json`, `RateLimitMiddleware.java`, `RouteHandler`?**
  _High betweenness centrality (0.051) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.13675213675213677 - nodes in this community are weakly interconnected._