# Graph Report - javelin-framework  (2026-11-24)

## Corpus Check
- 258 files · ~70,068 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 43 file(s) not represented in the graph (top: .stub 31, (none) 8, .xml 1)

## Summary
- 2768 nodes · 6797 edges · 166 communities (85 shown, 81 thin omitted)
- Extraction: 87% EXTRACTED · 13% INFERRED · 0% AMBIGUOUS · INFERRED: 913 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `c632b049`
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
- list
- SecureHeadersMiddleware
- ServiceProvider
- UploadedFile
- YamlConfig
- Model
- CLI Reference
- Date
- Container
- NewCommand
- AppProfile
- .imageSupportDetectsAndReadsImageMetadata
- .instance
- Validation
- NettyHttpServerIntegrationTest.java
- NatsQueue.java
- MigrationRunner
- Security
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
- Arr
- HttpServerAdapter
- HtmlResponse
- JdbcDatabase
- pebble
- Agent Notes
- .write
- ValidationRule
- .Collection
- Validator
- Architecture
- JdbcQueryBuilder
- ValidationRule
- Starter Module
- package.json
- Request
- io.javelin:javelin-starter
- ValkeyCache
- JsonResponse
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
- Router
- Middleware
- MakeMigrationCommand.java
- ProfileConfig
- .text
- AdultAgeRule
- Quick Start
- WorkspaceStarterRule
- io.javelin.core.Application
- PebbleViewExtensions
- .run
- Queue
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- File
- Config Module
- commandline
- Docker Compose
- need_command
- Config
- Production Performance Tuning
- io.javelin:javelin-parent
- Config
- Job
- ValidationRuleRegistry
- ContainerTest
- .output
- .authenticationGuardRequiresAPrincipal
- AdultAgeRule
- HTTP Common Module
- Installation Guide
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- HTTP JDK Module
- depends_on
- healthcheck
- .application
- Javelin CLI
- services
- app
- nats
- valkey
- HTTP Undertow Module
- ProviderLoaderTest
- AiGenerateModuleCommand
- java.util.regex.Pattern
- MakeModelCommand
- HTTP Netty Module
- TestConfig
- Javelin Docs
- Database
- .tracksSingletonCloseablesWithoutDuplicates
- Core Abstractions
- build.sh script
- Model

## God Nodes (most connected - your core abstractions)
1. `Application` - 106 edges
2. `Request` - 82 edges
3. `Response` - 79 edges
4. `Router` - 71 edges
5. `CommandContext` - 68 edges
6. `Collection` - 63 edges
7. `ServiceProvider` - 63 edges
8. `HttpKernel` - 57 edges
9. `Input` - 45 edges
10. `Handle` - 43 edges

## Surprising Connections (you probably didn't know these)
- `Boot Flow` --references--> `Container`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/Container.java
- `Notes` --references--> `ValkeyCacheServiceProvider`  [INFERRED]
  docs/modules/cache.md → modules/javelin-cache-valkey/src/main/java/io/javelin/cache/valkey/ValkeyCacheServiceProvider.java
- `TemplateEngine` --references--> `TemplateEngine`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/cli/generator/TemplateEngine.java
- `ConsoleServiceProvider` --references--> `ConsoleServiceProvider`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/console/ConsoleServiceProvider.java
- `HTTP driver selection` --references--> `HttpServerDriverProvider`  [INFERRED]
  docs/modules/starter.md → modules/javelin-starter/src/main/java/io/javelin/starter/HttpServerDriverProvider.java

## Import Cycles
- None detected.

## Communities (166 total, 81 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.13
Nodes (8): ProviderSorter, ProviderSorterTest, CycleA, CycleB, FirstProvider, NeedsFirstProvider, SecondProvider, ThirdProvider

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.06
Nodes (15): AbstractCommand, CommandContext, AiChatCommand, DevCommand, DoctorCommand, MakeControllerCommand, MakeServiceCommand, MigrateCommand (+7 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 12 - "Javelin Framework"
Cohesion: 0.12
Nodes (16): Boot Flow, Build And Test, CLI, Database, Documentation, Example Routes, First Run, Install (+8 more)

### Community 15 - "StaticAssetResolver"
Cohesion: 0.16
Nodes (4): StaticAssetResolver, ByteRange, StaticCachePolicy, TrustedProxyResolver

### Community 18 - "SecureHeadersMiddleware"
Cohesion: 0.14
Nodes (12): routes:list — Display registered routes, TrustedProxyResolver, Notes, RateLimitMiddleware, RequestSizeLimitMiddleware, SecureHeadersMiddleware, Security Module, SecurityServiceProvider (+4 more)

### Community 21 - "ServiceProvider"
Cohesion: 0.10
Nodes (23): Adding to the hardcoded provider list, Config-List Provider Loading, Integrating with Starter, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Provider registration order, Registration order matters, The ServiceProvider Contract (+15 more)

### Community 24 - "YamlConfig"
Cohesion: 0.06
Nodes (28): Application Flow, Boot Sequence, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development) (+20 more)

### Community 26 - "CLI Reference"
Cohesion: 0.05
Nodes (38): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI, build — Package the application, cache:clear — Clear application cache, CLI Reference (+30 more)

### Community 30 - "AppProfile"
Cohesion: 0.20
Nodes (7): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.15
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 34 - "NettyHttpServerIntegrationTest.java"
Cohesion: 0.18
Nodes (5): Bearer authentication, Authenticated, BearerAuthMiddleware, BearerTokenResolver, RequiresPermissionMiddleware

### Community 36 - "MigrationRunner"
Cohesion: 0.15
Nodes (4): MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 37 - "Security"
Cohesion: 0.14
Nodes (3): PasswordHasher, PasswordHasher, Security

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.17
Nodes (4): DefaultExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.09
Nodes (4): ExceptionHandler, RedirectResponse, Response, SecurityResponses

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 41 - "ApplicationTest"
Cohesion: 0.15
Nodes (6): ApplicationTest, BaseProvider, DependentProvider, FirstResource, RecordingProvider, SecondResource

### Community 42 - "TrustedProxyResolver"
Cohesion: 0.21
Nodes (3): ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolverTest

### Community 44 - "database"
Cohesion: 0.09
Nodes (22): app, env, name, database, database, driver, host, password (+14 more)

### Community 48 - ".commands"
Cohesion: 0.07
Nodes (9): CommandRegistry, CommandRegistry, AiExplainCommand, AiInstallCommand, BuildCommand, CacheClearCommand, ConfigCacheCommand, TestCommand (+1 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "Application"
Cohesion: 0.06
Nodes (7): ConfigServiceProvider, JavelinConsole, ConfigServiceProvider, JavelinConsole, RouteListCommand, ServeCommand, Application

### Community 51 - "docker-app.yaml"
Cohesion: 0.06
Nodes (33): app, env, name, cache, driver, url, database, database (+25 more)

### Community 52 - "Override"
Cohesion: 0.10
Nodes (9): ModelTest, CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder, SampleUser, Status, DRAFT (+1 more)

### Community 53 - "NettyHttpServer"
Cohesion: 0.29
Nodes (3): NettyHttpServer, RequestHandler, ChannelInitializer<SocketChannel>

### Community 54 - "Deployment"
Cohesion: 0.22
Nodes (9): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Migration Strategy, Performance Benchmarks, Reverse Proxy, Trusted proxy configuration (+1 more)

### Community 55 - "Core Module"
Cohesion: 0.05
Nodes (29): Config, Core Module, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse, HttpKernel (+21 more)

### Community 56 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 57 - "RouteHandler"
Cohesion: 0.09
Nodes (18): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+10 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 60 - "HttpServerAdapter"
Cohesion: 0.11
Nodes (14): JdkHttpServiceProvider, Configuration, Configuration, Application profile, DevProfileMiddleware, HttpServerAdapter, HttpAdapterConfig, HttpDriverConfig (+6 more)

### Community 61 - "HtmlResponse"
Cohesion: 0.24
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

### Community 70 - "Architecture"
Cohesion: 0.15
Nodes (12): Architecture, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Layer 0 — Foundation, Layer 1 — Contracts, Layer 3 — Toolchain, Layer 4 — Aggregator (+4 more)

### Community 73 - "Starter Module"
Cohesion: 0.12
Nodes (9): Main, HTTP driver selection, Javelin, Notes, Starter Module, ValidationRuleLoader, WorkspaceDefaults, Javelin (+1 more)

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 76 - "Request"
Cohesion: 0.11
Nodes (4): Request, MultipartData, PartDisposition, RequestInputTest

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.28
Nodes (17): io.javelin:javelin-cache, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-demo-app, io.javelin:javelin-http-common (+9 more)

### Community 78 - "ValkeyCache"
Cohesion: 0.08
Nodes (11): Cache, Cache Module, CacheServiceProvider, InMemoryCache, Notes, Cache, InMemoryCache, Entry (+3 more)

### Community 81 - "Module Authoring"
Cohesion: 0.05
Nodes (42): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+34 more)

### Community 86 - "Router"
Cohesion: 0.14
Nodes (5): DefaultExceptionHandlerTest, HttpKernelTest, ResponseTest, RouterTest, StaticAssetResolverTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - ".returnsAllowHeaderAndEnforcesBearerAuthentication"
Cohesion: 0.19
Nodes (3): UndertowHttpServer, UndertowHttpServerIntegrationTest, RunningServer

### Community 89 - "Json"
Cohesion: 0.17
Nodes (3): HomeController, UserController, Json

### Community 90 - "Database JDBC Module"
Cohesion: 0.22
Nodes (8): Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Notes, QueryBuilder

### Community 93 - "Middleware"
Cohesion: 0.11
Nodes (6): Bearer authentication and authorization, Middleware, Middleware, Next, RequiresAuthenticationMiddleware, RequestSizeLimitMiddleware

### Community 94 - "MakeMigrationCommand.java"
Cohesion: 0.10
Nodes (6): GenerateCommandSupport, MakeMigrationCommand, MakeModuleCommand, FileGenerator, Names, TemplateEngine

### Community 98 - "Quick Start"
Cohesion: 0.17
Nodes (12): 10. Full stack with Docker (optional), 1. Prerequisites, 2. Install the CLI, 3. Create an app, 4. Build the frontend assets, 5. Verify the workspace, 6. Run the app, 7. Add a route (+4 more)

### Community 101 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (17): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Notes, Options, PebbleViewExtensions, PebbleViewRenderer, PebbleViewServiceProvider (+9 more)

### Community 103 - "Queue"
Cohesion: 0.24
Nodes (5): NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, Queue

### Community 104 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 106 - "Config Module"
Cohesion: 0.29
Nodes (6): Config interface methods, Config Module, DotenvEnv, Environment expression resolution, Notes, YamlConfig

### Community 107 - "commandline"
Cohesion: 0.17
Nodes (3): CliException, ProcessTasks, MigrateCommand

### Community 108 - "Docker Compose"
Cohesion: 0.13
Nodes (12): AppServiceProvider, Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration (+4 more)

### Community 116 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 123 - "Job"
Cohesion: 0.16
Nodes (6): Queue contracts, Contracts (javelin-core), Job, JobHandler, RetryPolicy, JobTest

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
Cohesion: 0.33
Nodes (5): services, volumes, natsdata, pgdata, valkeydata

### Community 143 - "app"
Cohesion: 0.33
Nodes (6): build, ports, restart, context, dockerfile, app

### Community 144 - "nats"
Cohesion: 0.33
Nodes (6): command, image, ports, restart, volumes, nats

### Community 145 - "valkey"
Cohesion: 0.33
Nodes (6): valkey, command, image, ports, restart, volumes

### Community 146 - "HTTP Undertow Module"
Cohesion: 0.50
Nodes (3): Behavior, HTTP Undertow Module, Programmatic use

### Community 147 - "ProviderLoaderTest"
Cohesion: 0.32
Nodes (4): ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder

### Community 152 - "HTTP Netty Module"
Cohesion: 0.67
Nodes (3): Behavior, HTTP Netty Module, Programmatic use

### Community 154 - "Javelin Docs"
Cohesion: 0.67
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 155 - "Database"
Cohesion: 0.12
Nodes (7): Layer 2 — Adapters, Core interfaces you can implement, Database, QueryBuilder, TransactionCallback, Database, QueryBuilder

### Community 156 - ".tracksSingletonCloseablesWithoutDuplicates"
Cohesion: 0.40
Nodes (3): Application, Container, Container

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
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 680 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **81 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `path`, `Override`, `cli/ConsoleKernel.java`, `.application`, `Application`, `list`, `ProviderLoaderTest`, `ServiceProvider`, `.tracksSingletonCloseablesWithoutDuplicates`, `Container`, `AppProfile`, `NewCommand`, `.instance`, `ApplicationTest`, `.commands`, `Core Module`, `HttpServerAdapter`, `Starter Module`, `Collection.java`, `.registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot`, `.output`?**
  _High betweenness centrality (0.094) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DEV`, `PROD` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.13333333333333333 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `ServiceProvider`, `Agent Notes`, `path`, `Architecture`, `ApplicationTest`, `HttpServerAdapter`, `Module Authoring`, `Application`, `ProviderLoaderTest`, `Core Module`, `Database`, `.tracksSingletonCloseablesWithoutDuplicates`?**
  _High betweenness centrality (0.053) - this node is a cross-community bridge._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.12183908045977011 - nodes in this community are weakly interconnected._
- **Why does `Request` connect `Request` to `ioexception`, `.application`, `StaticAssetResolver`, `list`, `SecureHeadersMiddleware`, `UploadedFile`, `NettyHttpServerIntegrationTest.java`, `JdkHttpServerIntegrationTest`, `Response`, `org.junit.jupiter.api.Test`, `objects`, `NettyHttpServer.java`, `RouteHandler`, `JsonResponse`, `JdkHttpServer`, `Router`, `Json`, `Middleware`, `.text`, `io.javelin.core.Application`, `.authenticationGuardRequiresAPrincipal`?**
  _High betweenness centrality (0.048) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.13675213675213677 - nodes in this community are weakly interconnected._