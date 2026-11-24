# Graph Report - javelin-framework  (2026-11-24)

## Corpus Check
- 256 files · ~68,537 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 43 file(s) not represented in the graph (top: .stub 31, (none) 8, .xml 1)

## Summary
- 2746 nodes · 6766 edges · 168 communities (82 shown, 86 thin omitted)
- Extraction: 87% EXTRACTED · 13% INFERRED · 0% AMBIGUOUS · INFERRED: 913 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `a7e3109a`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- ServiceProvider
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
- StaticAssetResolver.java
- JdkHttpServerIntegrationTest
- Response
- Support Module
- ApplicationTest
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
- Request
- io.javelin:javelin-starter
- ValkeyCache
- ConsoleOutput
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
- Middleware
- MakeMigrationCommand.java
- Env
- .text
- AdultAgeRule
- Application Flow
- WorkspaceStarterRule
- io.javelin.core.Application
- PebbleViewExtensions
- .run
- MultipartData
- AI Commands
- File
- Config Module
- MigrateCommand
- Docker Compose
- need_command
- Config
- MakeMigrationCommand
- io.javelin:javelin-parent
- Config
- Job
- ValidationRuleRegistry
- ContainerTest
- .withAttribute
- AdultAgeRule
- HTTP Common Module
- Full Worked Example
- Cache Valkey Module
- db
- SupportUtilitiesTest
- dev.mjs
- .load
- depends_on
- healthcheck
- .application
- Javelin CLI
- services
- Security
- app
- nats
- valkey
- io.pebbletemplates.pebble.extension.Filter
- ProviderLoaderTest
- java.util.regex.Pattern
- .rollbackCommandReplaysYamlDownStatements
- TestConfig
- Console Command Lifecycle
- Database
- .tracksSingletonCloseablesWithoutDuplicates
- ProfileConfig
- Core Abstractions
- build.sh script
- Model

## God Nodes (most connected - your core abstractions)
1. `Application` - 106 edges
2. `Request` - 82 edges
3. `Response` - 79 edges
4. `Router` - 71 edges
5. `CommandContext` - 68 edges
6. `ServiceProvider` - 63 edges
7. `Collection` - 63 edges
8. `HttpKernel` - 57 edges
9. `Input` - 45 edges
10. `Handle` - 43 edges

## Surprising Connections (you probably didn't know these)
- `Boot Flow` --references--> `Container`  [INFERRED]
  README.md → modules/javelin-core/src/main/java/io/javelin/core/Container.java
- `ProviderLoader` --references--> `ProviderLoader`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/ProviderLoader.java
- `Current MVP Boundaries` --references--> `ServiceProvider`  [INFERRED]
  agents.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Checklist for new modules` --references--> `ServiceProvider`  [INFERRED]
  docs/module-authoring.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Notes` --references--> `ServiceProvider`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java

## Import Cycles
- None detected.

## Communities (168 total, 86 thin omitted)

### Community 0 - "ServiceProvider"
Cohesion: 0.11
Nodes (10): ProviderSorter, ServiceProvider, DependentProvider, ProviderSorterTest, CycleA, CycleB, FirstProvider, NeedsFirstProvider (+2 more)

### Community 4 - "Override"
Cohesion: 0.17
Nodes (3): Config, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.04
Nodes (27): AbstractCommand, CliException, CommandContext, AiChatCommand, AiExplainCommand, AiGenerateModuleCommand, AiInstallCommand, BuildCommand (+19 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.05
Nodes (4): Collection, Group, Keyed, Partition

### Community 12 - "Javelin Framework"
Cohesion: 0.12
Nodes (16): Boot Flow, Build And Test, CLI, Database, Documentation, Example Routes, First Run, Install (+8 more)

### Community 13 - "Application"
Cohesion: 0.10
Nodes (4): ValkeyCacheServiceProviderTest, Config, JdbcDatabaseServiceProvider, PebbleViewServiceProvider

### Community 15 - "StaticAssetResolver"
Cohesion: 0.16
Nodes (4): StaticAssetResolver, ByteRange, StaticCachePolicy, TrustedProxyResolver

### Community 17 - "JavelinViewExtension.java"
Cohesion: 0.30
Nodes (3): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction

### Community 18 - "SecureHeadersMiddleware"
Cohesion: 0.12
Nodes (18): routes:list — Display registered routes, Example: Request timing middleware, Middleware reference (built-in), Service provider adds middleware to the router, Writing a Middleware Module, HTTP JDK Module, JdkHttpServer, Notes (+10 more)

### Community 21 - "Javelin.java"
Cohesion: 0.06
Nodes (25): Main, Adding to the hardcoded provider list, Integrating with Starter, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Provider registration order, Registration order matters, The ServiceProvider Contract (+17 more)

### Community 24 - "YamlConfig"
Cohesion: 0.19
Nodes (4): YamlConfig, TypeReference<>, TypeReference<>, YamlConfigTest

### Community 26 - "CLI Reference"
Cohesion: 0.06
Nodes (33): build — Package the application, cache:clear — Clear application cache, CLI Reference, Command summary, Config, config:cache — Cache configuration, dev — Development server, doctor — System diagnostics (+25 more)

### Community 30 - "AppProfile"
Cohesion: 0.20
Nodes (7): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".instance"
Cohesion: 0.10
Nodes (9): JavelinStarterServiceProvider, ValidationRuleLoader, JavelinStarterServiceProviderTest, TestConfig, TestEnv, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig (+1 more)

### Community 34 - "NettyHttpServerIntegrationTest.java"
Cohesion: 0.19
Nodes (5): Bearer authentication, Authenticated, BearerAuthMiddleware, BearerTokenResolver, RequiresPermissionMiddleware

### Community 36 - "MigrationRunner"
Cohesion: 0.16
Nodes (4): MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.17
Nodes (5): DefaultExceptionHandler, ExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.09
Nodes (4): JsonResponse, RedirectResponse, Response, SecurityResponses

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 41 - "ApplicationTest"
Cohesion: 0.15
Nodes (5): ApplicationTest, BaseProvider, FirstResource, RecordingProvider, SecondResource

### Community 42 - "TrustedProxyResolver"
Cohesion: 0.21
Nodes (3): ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolverTest

### Community 44 - "database"
Cohesion: 0.09
Nodes (22): app, env, name, database, database, driver, host, password (+14 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "Application"
Cohesion: 0.08
Nodes (6): JavelinConsole, ConfigServiceProvider, JavelinConsole, RouteListCommand, ServeCommand, Application

### Community 51 - "docker-app.yaml"
Cohesion: 0.06
Nodes (33): app, env, name, cache, driver, url, database, database (+25 more)

### Community 52 - "Override"
Cohesion: 0.11
Nodes (9): ModelTest, CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder, SampleUser, Status, DRAFT (+1 more)

### Community 53 - "NettyHttpServer"
Cohesion: 0.29
Nodes (3): NettyHttpServer, RequestHandler, ChannelInitializer<SocketChannel>

### Community 54 - "Production Performance Tuning"
Cohesion: 0.11
Nodes (18): Caddy alternative, Connection pool (HikariCP), Deployment, Graceful Shutdown, Health Checks, JMX monitoring, JVM flags, Migration Strategy (+10 more)

### Community 55 - "Core Module"
Cohesion: 0.05
Nodes (30): Config, Core Module, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse, HttpKernel (+22 more)

### Community 56 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 57 - "RouteHandler"
Cohesion: 0.08
Nodes (18): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+10 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 60 - "HttpServerAdapter"
Cohesion: 0.15
Nodes (12): JdkHttpServiceProvider, Configuration, Configuration, Application profile, DevProfileMiddleware, HttpServerAdapter, HttpAdapterConfig, HttpDriverConfig (+4 more)

### Community 61 - "HtmlResponse"
Cohesion: 0.22
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
Cohesion: 0.26
Nodes (3): HttpServerDriverProviderTest, DriverConfig, DriverEnv

### Community 70 - "Architecture"
Cohesion: 0.17
Nodes (11): Architecture, Core owns contracts; adapters implement them, Guiding Principles, Layer 0 — Foundation, Layer 1 — Contracts, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Boundaries (+3 more)

### Community 73 - "ViewRenderer"
Cohesion: 0.22
Nodes (8): Notes, Options, PebbleViewExtensions, PebbleViewRenderer, PebbleViewServiceProvider, Using templates in routes, View Pebble Module, ViewRenderer

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.28
Nodes (17): io.javelin:javelin-cache, io.javelin:javelin-cache-valkey, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-demo-app, io.javelin:javelin-http-common (+9 more)

### Community 78 - "ValkeyCache"
Cohesion: 0.17
Nodes (3): ValkeyCache, ValkeyCacheTest, Cache

### Community 81 - "Module Authoring"
Cohesion: 0.07
Nodes (29): Adding Tests, Adding the module to the parent POM, `bind(A, B)` — reusable factory, Binding Strategies, Checklist for new modules, Config-Driven Modules, Config-List Provider Loading, Creating a New Interface (+21 more)

### Community 86 - "Router"
Cohesion: 0.11
Nodes (7): Router, ResolvedRoute, DefaultExceptionHandlerTest, HttpKernelTest, ResponseTest, RouterTest, StaticAssetResolverTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - ".returnsAllowHeaderAndEnforcesBearerAuthentication"
Cohesion: 0.19
Nodes (3): UndertowHttpServer, UndertowHttpServerIntegrationTest, RunningServer

### Community 90 - "Database JDBC Module"
Cohesion: 0.22
Nodes (8): Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Notes, QueryBuilder

### Community 93 - "Middleware"
Cohesion: 0.10
Nodes (8): Bearer authentication and authorization, Middleware, Middleware, Next, RequiresAuthenticationMiddleware, RateLimitMiddleware, Bucket, RequestSizeLimitMiddleware

### Community 94 - "MakeMigrationCommand.java"
Cohesion: 0.13
Nodes (5): GenerateCommandSupport, MakeModuleCommand, FileGenerator, Names, TemplateEngine

### Community 98 - "Application Flow"
Cohesion: 0.12
Nodes (15): Application Flow, Boot Sequence, Configuration Resolution, Container Resolution, Content Negotiation on 404, Error Handling, HTTP Request Lifecycle, HttpKernel Dispatch (Step 4 detailed) (+7 more)

### Community 100 - "io.javelin.core.Application"
Cohesion: 0.21
Nodes (3): BackendController, AppServiceProvider, web

### Community 104 - "AI Commands"
Cohesion: 0.40
Nodes (5): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI

### Community 106 - "Config Module"
Cohesion: 0.25
Nodes (7): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, YamlConfig

### Community 108 - "Docker Compose"
Cohesion: 0.12
Nodes (15): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running (+7 more)

### Community 123 - "Job"
Cohesion: 0.12
Nodes (11): Queue contracts, Contracts (javelin-core), NatsQueue, NatsQueueServiceProvider, Notes, Queue NATS Module, Job, JobHandler (+3 more)

### Community 125 - "ContainerTest"
Cohesion: 0.25
Nodes (6): ContainerTest, CircularA, CircularB, CloseableResource, Dependency, Service

### Community 127 - ".withAttribute"
Cohesion: 0.24
Nodes (3): UserController, Key Design Decisions, RequestAttributesTest

### Community 129 - "HTTP Common Module"
Cohesion: 0.25
Nodes (7): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, TrustedProxyResolver, Trusted proxy configuration

### Community 130 - "Full Worked Example"
Cohesion: 0.20
Nodes (10): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+2 more)

### Community 131 - "Cache Valkey Module"
Cohesion: 0.50
Nodes (4): Cache Valkey Module, Notes, ValkeyCache, ValkeyCacheServiceProvider

### Community 132 - "db"
Cohesion: 0.25
Nodes (8): environment, image, restart, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, db

### Community 133 - "SupportUtilitiesTest"
Cohesion: 0.33
Nodes (3): SupportUtilitiesTest, Person, Profile

### Community 134 - "dev.mjs"
Cohesion: 0.25
Nodes (3): bin, env, watchers

### Community 135 - ".load"
Cohesion: 0.31
Nodes (3): DotenvEnv, Entry, DotenvEnvTest

### Community 136 - "depends_on"
Cohesion: 0.29
Nodes (7): depends_on, condition, db, nats, valkey, condition, condition

### Community 137 - "healthcheck"
Cohesion: 0.57
Nodes (7): healthcheck, interval, retries, test, timeout, healthcheck, healthcheck

### Community 140 - "Javelin CLI"
Cohesion: 0.17
Nodes (8): CommandRegistry, AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image, CommandRegistry

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

### Community 147 - "ProviderLoaderTest"
Cohesion: 0.28
Nodes (4): ProviderLoaderTest, BaseLoadedProvider, DependentLoadedProvider, LoadedOrder

### Community 154 - "Console Command Lifecycle"
Cohesion: 0.33
Nodes (6): CLI Dispatch, Console Command Lifecycle, Dev Command (Development), Generator Commands, Migration Commands, Serve Command (Production)

### Community 155 - "Database"
Cohesion: 0.13
Nodes (7): Layer 2 — Adapters, Core interfaces you can implement, Database, QueryBuilder, TransactionCallback, Database, QueryBuilder

### Community 156 - ".tracksSingletonCloseablesWithoutDuplicates"
Cohesion: 0.33
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
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED`, `DEV`, `PROD` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 662 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **86 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `ServiceProvider`, `path`, `cli/ConsoleKernel.java`, `.application`, `Application`, `ProviderLoaderTest`, `Javelin.java`, `.rollbackCommandReplaysYamlDownStatements`, `.run`, `.tracksSingletonCloseablesWithoutDuplicates`, `Container`, `NewCommand`, `AppProfile`, `.instance`, `ApplicationTest`, `org.junit.jupiter.api.Test`, `HttpServerAdapter`, `.adapterFor`, `ConsoleOutput`?**
  _High betweenness centrality (0.106) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DRAFT`, `PUBLISHED` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `ServiceProvider` be split into smaller, more focused modules?**
  _Cohesion score 0.11336032388663968 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `.instance`, `Agent Notes`, `Architecture`, `ApplicationTest`, `Application`, `HttpServerAdapter`, `Module Authoring`, `Application`, `ProviderLoaderTest`, `Javelin.java`, `Core Module`, `Database`, `.tracksSingletonCloseablesWithoutDuplicates`?**
  _High betweenness centrality (0.055) - this node is a cross-community bridge._
- **Should `path` be split into smaller, more focused modules?**
  _Cohesion score 0.10338680926916222 - nodes in this community are weakly interconnected._
- **Why does `Database` connect `Database` to `Override`, `MigrationRunner`, `Override`, `objects`, `org.junit.jupiter.api.Test`, `Application`, `Override`, `Javelin.java`, `Model`, `Database JDBC Module`, `JdbcDatabase`?**
  _High betweenness centrality (0.053) - this node is a cross-community bridge._
- **Should `cli/ConsoleKernel.java` be split into smaller, more focused modules?**
  _Cohesion score 0.04285048905449464 - nodes in this community are weakly interconnected._