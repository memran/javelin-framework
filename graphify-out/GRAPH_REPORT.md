# Graph Report - javelin-framework  (2026-11-24)

## Corpus Check
- 260 files · ~71,357 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 47 file(s) not represented in the graph (top: .stub 33, (none) 9, .xml 1)

## Summary
- 2827 nodes · 6905 edges · 166 communities (93 shown, 73 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 933 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `fd88490c`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
- path
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- Javelin Framework
- Application
- StaticAssetResolver
- RateLimitMiddleware.java
- ServiceProvider
- UploadedFile
- YamlConfig
- Model
- CLI Reference
- Date
- Container
- commandline
- AppProfile
- .imageSupportDetectsAndReadsImageMetadata
- .instance
- Validation
- HttpServerDriverProvider.java
- MigrationRunner
- PasswordHasher
- JdkHttpServerIntegrationTest
- Response
- Support Module
- ApplicationTest
- TrustedProxyResolver
- Input
- database
- .commands
- Console Module
- Application
- docker-app.yaml
- Override
- NettyHttpServer
- Deployment
- Core Module
- ProjectLauncher
- RouteHandler
- Override
- Application Flow
- HttpServerAdapter
- HtmlResponse
- TransactionCallback
- pebble
- Agent Notes
- .write
- ValidationRule
- SecureHeadersMiddleware
- Validator
- Architecture
- JdbcQueryBuilder
- ValidationRule
- .run
- caddy
- package.json
- Request
- io.javelin:javelin-starter
- ValkeyCache
- Obj
- Module Authoring
- nginx
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- JdkHttpServer.java
- Str
- Router
- environment
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- Json
- Database JDBC Module
- VersionCommand
- Router
- Request
- MakeMigrationCommand.java
- ProfileConfig
- Middleware
- AdultAgeRule
- Quick Start
- WorkspaceStarterRule
- JsonResponse
- PebbleViewExtensions
- .run
- Console Command Lifecycle
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- Config
- AI Commands
- MigrateCommand
- Docker Compose
- need_command
- Config
- Production Performance Tuning
- io.javelin:javelin-parent
- Config
- NatsQueue.java
- ValidationRuleRegistry
- ContainerTest
- .output
- .authenticationGuardRequiresAPrincipal
- HTTP Common Module
- Installation Guide
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- HTTP JDK Module
- depends_on
- healthcheck
- ProfileConfig
- Javelin CLI
- services
- Writing a Middleware Module
- app
- nats
- valkey
- Cache Module
- ProviderLoaderTest
- migrate — Run pending migrations
- java.util.regex.Pattern
- MakeModelCommand
- serve — Production server
- TestConfig
- Javelin Docs
- Database
- .tracksSingletonCloseablesWithoutDuplicates
- make:controller — Generate a controller
- new — Create a workspace
- Core Abstractions
- build.sh script
- Model

## God Nodes (most connected - your core abstractions)
1. `Application` - 100 edges
2. `Router` - 80 edges
3. `Request` - 78 edges
4. `CommandContext` - 68 edges
5. `Response` - 68 edges
6. `Collection` - 63 edges
7. `HttpKernel` - 58 edges
8. `ServiceProvider` - 57 edges
9. `Input` - 45 edges
10. `Handle` - 43 edges

## Surprising Connections (you probably didn't know these)
- `Router` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java
- `Routes` --references--> `Router`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/Router.java
- `HttpAdapterConfig` --references--> `HttpTimeoutConfig`  [INFERRED]
  docs/modules/http-common.md → modules/javelin-http-common/src/main/java/io/javelin/http/common/HttpTimeoutConfig.java
- `Trusted proxy configuration` --references--> `JdkHttpServiceProvider`  [INFERRED]
  docs/deployment.md → modules/javelin-http-jdk/src/main/java/io/javelin/http/jdk/JdkHttpServiceProvider.java
- `ProviderLoader` --references--> `ProviderLoader`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/ProviderLoader.java

## Import Cycles
- None detected.

## Communities (166 total, 73 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.10
Nodes (14): Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, The ServiceProvider Contract, Application, ConfigServiceProvider, ProviderSorter, ServiceProvider, ProviderSorterTest (+6 more)

### Community 4 - "Override"
Cohesion: 0.13
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.05
Nodes (18): AbstractCommand, CommandContext, AiChatCommand, AiGenerateModuleCommand, BuildCommand, DevCommand, DoctorCommand, MakeServiceCommand (+10 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "Javelin Framework"
Cohesion: 0.12
Nodes (16): Boot Flow, Build And Test, CLI, Database, Documentation, Example Routes, First Run, Install (+8 more)

### Community 13 - "Application"
Cohesion: 0.08
Nodes (6): Config-List Provider Loading, Notes, ValkeyCacheServiceProvider, ValkeyCacheServiceProviderTest, Config, NatsQueueServiceProviderTest

### Community 15 - "StaticAssetResolver"
Cohesion: 0.18
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 18 - "RateLimitMiddleware.java"
Cohesion: 0.21
Nodes (4): TrustedProxyResolver, Trusted proxy configuration, RateLimitMiddleware, Bucket

### Community 21 - "ServiceProvider"
Cohesion: 0.08
Nodes (26): Startup Time, Why startup is fast, Adding to the hardcoded provider list, Integrating with Starter, Provider registration order, Registration order matters, ServiceProvider, Application profile (+18 more)

### Community 22 - "UploadedFile"
Cohesion: 0.09
Nodes (4): UploadedFile, RequestMultipartTest, File, Security

### Community 24 - "YamlConfig"
Cohesion: 0.09
Nodes (14): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, YamlConfig, DotenvEnv (+6 more)

### Community 26 - "CLI Reference"
Cohesion: 0.08
Nodes (24): build — Package the application, cache:clear — Clear application cache, CLI Reference, Command summary, config:cache — Cache configuration, dev — Development server, doctor — System diagnostics, Exit codes (+16 more)

### Community 29 - "commandline"
Cohesion: 0.16
Nodes (3): CliException, NewCommand, ServeCommand

### Community 30 - "AppProfile"
Cohesion: 0.16
Nodes (8): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, ChannelInitializer<SocketChannel>

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.10
Nodes (8): Example, Javelin Support, Public API, Arr, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.13
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 35 - "HttpServerDriverProvider.java"
Cohesion: 0.11
Nodes (7): AppServiceProvider, DevProfileMiddleware, HttpAdapterConfig, HttpDriverConfig, NettyHttpServiceProvider, UndertowHttpServiceProvider, NatsQueueServiceProvider

### Community 36 - "MigrationRunner"
Cohesion: 0.15
Nodes (4): MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.18
Nodes (5): DefaultExceptionHandler, ExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.11
Nodes (3): RedirectResponse, Response, SecurityResponses

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 41 - "ApplicationTest"
Cohesion: 0.15
Nodes (6): ApplicationTest, BaseProvider, DependentProvider, FirstResource, RecordingProvider, SecondResource

### Community 42 - "TrustedProxyResolver"
Cohesion: 0.16
Nodes (4): ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolver, TrustedProxyResolverTest

### Community 44 - "database"
Cohesion: 0.09
Nodes (22): app, env, name, database, database, driver, host, password (+14 more)

### Community 48 - ".commands"
Cohesion: 0.08
Nodes (8): CommandRegistry, CommandRegistry, AiExplainCommand, AiInstallCommand, CacheClearCommand, ConfigCacheCommand, MakeControllerCommand, ConsoleKernel

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "Application"
Cohesion: 0.08
Nodes (5): JavelinConsole, JavelinConsole, RouteListCommand, ServeCommand, Application

### Community 51 - "docker-app.yaml"
Cohesion: 0.05
Nodes (36): app, env, name, cache, driver, url, database, database (+28 more)

### Community 52 - "Override"
Cohesion: 0.10
Nodes (9): ModelTest, CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder, SampleHydratedUser, Status, DRAFT (+1 more)

### Community 54 - "Deployment"
Cohesion: 0.22
Nodes (9): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Migration Strategy, Performance Benchmarks, Reverse Proxy, Trusted proxy configuration (+1 more)

### Community 55 - "Core Module"
Cohesion: 0.05
Nodes (27): Config, Core Module, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse, HttpKernel (+19 more)

### Community 56 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 57 - "RouteHandler"
Cohesion: 0.10
Nodes (17): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+9 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 59 - "Application Flow"
Cohesion: 0.15
Nodes (12): Application Flow, Boot Sequence, Configuration Resolution, Container Resolution, Content Negotiation on 404, Error Handling, HTTP Request Lifecycle, HttpKernel Dispatch (Step 4 detailed) (+4 more)

### Community 60 - "HttpServerAdapter"
Cohesion: 0.08
Nodes (16): HttpServerAdapter, JdkHttpServiceProvider, Behavior, Configuration, HTTP Netty Module, Programmatic use, Behavior, Configuration (+8 more)

### Community 61 - "HtmlResponse"
Cohesion: 0.31
Nodes (3): HtmlResponse, View, ViewTest

### Community 63 - "pebble"
Cohesion: 0.12
Nodes (15): driver, pebble, allow_override_core_operators, auto_escaping, cache, default_escaping_strategy, default_locale, greedy_match_method (+7 more)

### Community 65 - "Agent Notes"
Cohesion: 0.13
Nodes (14): Agent Notes, Architect Guidelines, Architecture Intent, Coding Principles, Current MVP Boundaries, Do, Do / Don’t, Don’t (+6 more)

### Community 66 - ".write"
Cohesion: 0.11
Nodes (4): GeneratorSupport, MakeControllerCommand, MakeModelCommand, MakeProviderCommand

### Community 68 - "SecureHeadersMiddleware"
Cohesion: 0.24
Nodes (10): routes:list — Display registered routes, Bearer authentication, Notes, PasswordHasher, RateLimitMiddleware, RequestSizeLimitMiddleware, SecureHeadersMiddleware, Security Module (+2 more)

### Community 70 - "Architecture"
Cohesion: 0.15
Nodes (12): Architecture, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Layer 0 — Foundation, Layer 1 — Contracts, Layer 3 — Toolchain, Layer 4 — Aggregator (+4 more)

### Community 71 - "JdbcQueryBuilder"
Cohesion: 0.13
Nodes (3): JdbcDatabase, JdbcQueryBuilder, Condition

### Community 72 - "ValidationRule"
Cohesion: 0.15
Nodes (3): AbstractWorkspaceRule, WorkspaceAgeRule, ValidationRule

### Community 73 - ".run"
Cohesion: 0.25
Nodes (3): Main, Phase 2: Boot, Javelin

### Community 74 - "caddy"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, caddy

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.28
Nodes (17): io.javelin:javelin-cache, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-demo-app, io.javelin:javelin-http-common (+9 more)

### Community 78 - "ValkeyCache"
Cohesion: 0.10
Nodes (6): Cache, InMemoryCache, Entry, ValkeyCache, ValkeyCacheTest, Cache

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (38): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+30 more)

### Community 82 - "nginx"
Cohesion: 0.29
Nodes (7): depends_on, image, ports, profiles, restart, volumes, nginx

### Community 84 - "JdkHttpServer.java"
Cohesion: 0.11
Nodes (4): HttpTimeoutConfig, JdkHttpServer, RequestRejected, UndertowHttpServer

### Community 86 - "Router"
Cohesion: 0.13
Nodes (5): DefaultExceptionHandlerTest, HttpKernelTest, ResponseTest, StaticAssetResolverTest, ProfileWiringTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 90 - "Database JDBC Module"
Cohesion: 0.22
Nodes (8): Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Notes, QueryBuilder

### Community 92 - "Router"
Cohesion: 0.15
Nodes (4): Router, ResolvedRoute, Routes, RouterTest

### Community 93 - "Request"
Cohesion: 0.07
Nodes (6): HomeController, UserController, Next, Request, RequestAttributesTest, BearerAuthMiddlewareTest

### Community 94 - "MakeMigrationCommand.java"
Cohesion: 0.11
Nodes (6): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, FileGenerator, Names, TemplateEngine

### Community 96 - "Middleware"
Cohesion: 0.10
Nodes (7): Bearer authentication and authorization, Middleware, Middleware, Authenticated, BearerAuthMiddleware, BearerTokenResolver, RequiresPermissionMiddleware

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 100 - "JsonResponse"
Cohesion: 0.13
Nodes (3): BackendController, JsonResponse, RequestSizeLimitMiddleware

### Community 101 - "PebbleViewExtensions"
Cohesion: 0.05
Nodes (20): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, View, ViewRenderer, Notes, Options, PebbleViewExtensions (+12 more)

### Community 103 - "Console Command Lifecycle"
Cohesion: 0.33
Nodes (6): CLI Dispatch, Console Command Lifecycle, Dev Command (Development), Generator Commands, Migration Commands, Serve Command (Production)

### Community 104 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 106 - "AI Commands"
Cohesion: 0.40
Nodes (5): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI

### Community 108 - "Docker Compose"
Cohesion: 0.22
Nodes (9): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running (+1 more)

### Community 116 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 123 - "NatsQueue.java"
Cohesion: 0.07
Nodes (12): Queue contracts, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, Job, JobHandler (+4 more)

### Community 125 - "ContainerTest"
Cohesion: 0.25
Nodes (6): ContainerTest, CircularA, CircularB, CloseableResource, Dependency, Service

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
Cohesion: 0.26
Nodes (3): SupportUtilitiesTest, Person, Profile

### Community 134 - "dev.mjs"
Cohesion: 0.25
Nodes (3): bin, env, watchers

### Community 135 - "HTTP JDK Module"
Cohesion: 0.40
Nodes (5): HTTP JDK Module, JdkHttpServer, Notes, Static file serving, Trusted proxy resolution

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
Cohesion: 0.40
Nodes (4): volumes, natsdata, pgdata, valkeydata

### Community 142 - "Writing a Middleware Module"
Cohesion: 0.50
Nodes (4): Example: Request timing middleware, Middleware reference (built-in), Service provider adds middleware to the router, Writing a Middleware Module

### Community 143 - "app"
Cohesion: 0.29
Nodes (7): build, ports, restart, context, dockerfile, services, app

### Community 144 - "nats"
Cohesion: 0.33
Nodes (6): command, image, ports, restart, volumes, nats

### Community 145 - "valkey"
Cohesion: 0.33
Nodes (6): valkey, command, image, ports, restart, volumes

### Community 146 - "Cache Module"
Cohesion: 0.50
Nodes (4): Cache, Cache Module, CacheServiceProvider, InMemoryCache

### Community 147 - "ProviderLoaderTest"
Cohesion: 0.32
Nodes (4): ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder

### Community 149 - "migrate — Run pending migrations"
Cohesion: 0.67
Nodes (3): migrate — Run pending migrations, On first run, What happens

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 155 - "Database"
Cohesion: 0.15
Nodes (7): Layer 2 — Adapters, Core interfaces you can implement, Database, QueryBuilder, TransactionCallback, Database, QueryBuilder

### Community 160 - "Core Abstractions"
Cohesion: 0.40
Nodes (5): Container (DI), Core Abstractions, Middleware pipeline, Request → Response flow, Service Provider lifecycle

### Community 161 - "build.sh script"
Cohesion: 0.83
Nodes (3): build.sh script, build_assets(), build_java()

### Community 162 - "Model"
Cohesion: 0.40
Nodes (5): Model, Model instance methods, Model overridable methods, Model static methods, User

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DEV`, `PROD`, `DRAFT`, `PUBLISHED` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 701 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **73 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `path`, `Override`, `cli/ConsoleKernel.java`, `Application`, `ProviderLoaderTest`, `ServiceProvider`, `.tracksSingletonCloseablesWithoutDuplicates`, `Container`, `AppProfile`, `commandline`, `.instance`, `HttpServerDriverProvider.java`, `ApplicationTest`, `org.junit.jupiter.api.Test`, `objects`, `.commands`, `HttpServerAdapter`, `.registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot`, `.output`?**
  _High betweenness centrality (0.112) - this node is a cross-community bridge._
- **Are the 21 inferred relationships involving `Router` (e.g. with `Layer 1 — Contracts` and `Application`) actually correct?**
  _`Router` has 21 INFERRED edges - model-reasoned connections that need verification._
- **What connects `io.javelin:javelin-parent`, `DEV`, `PROD` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.09745293466223699 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `Agent Notes`, `HttpServerDriverProvider.java`, `Architecture`, `ApplicationTest`, `Module Authoring`, `Application`, `ProviderLoaderTest`, `ServiceProvider`, `Core Module`, `Database`, `HttpServerAdapter`?**
  _High betweenness centrality (0.051) - this node is a cross-community bridge._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.0851063829787234 - nodes in this community are weakly interconnected._
- **Why does `Database` connect `Database` to `path`, `Override`, `MigrationRunner`, `Override`, `JdbcQueryBuilder`, `Override`, `ServiceProvider`, `Model`, `Database JDBC Module`, `TransactionCallback`?**
  _High betweenness centrality (0.046) - this node is a cross-community bridge._