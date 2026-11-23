# Graph Report - javelin-framework  (2026-11-24)

## Corpus Check
- 227 files · ~62,712 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 535 file(s) not represented in the graph (top: .sha1 190, .repositories 126, .pom 126)
- WARNING: 535 of 762 files seen (70%) have no supported extractor and are missing from this graph entirely. It may look complete while representing only part of the codebase.

## Summary
- 2404 nodes · 5959 edges · 141 communities (70 shown, 71 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 835 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- .run
- CommandContext
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- Application
- StaticAssetResolver
- Model
- SecureHeadersMiddleware
- ServiceProvider
- TransactionCallback
- Env
- web.java
- CLI Reference
- Date
- MultipartData
- MakeMigrationCommand
- Core Module
- .imageSupportDetectsAndReadsImageMetadata
- .load
- Validation
- JdkHttpServerIntegrationTest.java
- Module Authoring
- MigrationRunner
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- JdkHttpServerIntegrationTest
- Response
- Support Module
- .start
- ProxyAddressMatcher
- Input
- database
- PebbleViewExtensions
- Model
- Console Module
- org.junit.jupiter.api.Test
- Application Flow
- Override
- NettyHttpServer
- Deployment
- Cache
- ProjectLauncher
- RouteHandler
- Override
- PasswordHasher
- HttpServerAdapter
- HtmlResponse
- Obj
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
- Arr
- JdkHttpServer
- .commands
- io.javelin:javelin-starter
- Security
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- JavelinConsole
- Full Worked Example
- WorkspaceAgeRule
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- .run
- Production Performance Tuning
- Router
- AppServiceProvider.java
- commandline
- .handle
- Database JDBC Module
- VersionCommand
- ConsoleOutput
- File
- AppProfile
- AdultAgeRule
- Javelin CLI
- Request
- WorkspaceStarterRule
- .text
- .resolve
- Log
- AI Commands
- Docker Compose
- Next
- MigrateCommand
- ValidationRuleRegistry
- need_command
- io.javelin:javelin-parent
- .handle
- UndertowHttpServer
- .output
- java.util.regex.Pattern
- ServeCommand
- Container
- SupportUtilitiesTest
- Binding Strategies
- Pattern Reference
- migrate — Run pending migrations
- Slf4jLogger
- .register
- HTTP JDK Module

## God Nodes (most connected - your core abstractions)
1. `Application` - 84 edges
2. `Request` - 72 edges
3. `CommandContext` - 72 edges
4. `Collection` - 63 edges
5. `Response` - 61 edges
6. `Router` - 47 edges
7. `AbstractCommand` - 47 edges
8. `Handle` - 43 edges
9. `Input` - 43 edges
10. `Model` - 42 edges

## Surprising Connections (you probably didn't know these)
- `Trusted proxy configuration` --references--> `JdkHttpServiceProvider`  [INFERRED]
  docs/deployment.md → modules/javelin-http-jdk/src/main/java/io/javelin/http/jdk/JdkHttpServiceProvider.java
- `RouteHandler` --references--> `RouteHandler`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/RouteHandler.java
- `ServiceProvider` --references--> `ServiceProvider`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `Notes` --references--> `ServiceProvider`  [INFERRED]
  docs/modules/core.md → modules/javelin-core/src/main/java/io/javelin/core/ServiceProvider.java
- `TemplateEngine` --references--> `TemplateEngine`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/cli/generator/TemplateEngine.java

## Import Cycles
- None detected.

## Communities (141 total, 71 thin omitted)

### Community 0 - ".run"
Cohesion: 0.13
Nodes (13): Main, Adding to the hardcoded provider list, Integrating with Starter, Provider registration order, HTTP driver selection, Javelin, JavelinStarterServiceProvider, Notes (+5 more)

### Community 2 - "CommandContext"
Cohesion: 0.09
Nodes (8): CommandContext, AiGenerateModuleCommand, GenerateCommandSupport, MakeControllerCommand, MakeModelCommand, MakeModuleCommand, MakeServiceCommand, ServeCommand

### Community 4 - "Override"
Cohesion: 0.14
Nodes (5): MigrationCommandTest, Config, Env, RecordingDatabase, RecordingQueryBuilder

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.06
Nodes (11): AbstractCommand, AiChatCommand, DevCommand, DoctorCommand, MigrateCommand, MigrateRollbackCommand, QueueStatusCommand, QueueWorkCommand (+3 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 11 - "Collection"
Cohesion: 0.06
Nodes (4): Collection, Group, Keyed, Partition

### Community 13 - "Application"
Cohesion: 0.12
Nodes (3): Config, JdbcDatabaseServiceProvider, PebbleViewServiceProvider

### Community 15 - "StaticAssetResolver"
Cohesion: 0.25
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - "Model"
Cohesion: 0.11
Nodes (9): Database, Model, Model instance methods, Model overridable methods, Model static methods, QueryBuilder, TransactionCallback, Database (+1 more)

### Community 18 - "SecureHeadersMiddleware"
Cohesion: 0.18
Nodes (13): routes:list — Display registered routes, Example: Request timing middleware, Middleware reference (built-in), Service provider adds middleware to the router, Writing a Middleware Module, Notes, PasswordHasher, RateLimitMiddleware (+5 more)

### Community 21 - "ServiceProvider"
Cohesion: 0.08
Nodes (14): Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Registration order matters, The ServiceProvider Contract, Default Service Providers, CacheServiceProvider, ConfigServiceProvider, ConsoleServiceProvider (+6 more)

### Community 24 - "Env"
Cohesion: 0.08
Nodes (15): Config interface methods, Config Module, ConfigServiceProvider, DotenvEnv, Environment expression resolution, Notes, YamlConfig, DotenvEnv (+7 more)

### Community 25 - "web.java"
Cohesion: 0.16
Nodes (4): HomeController, UserController, Json, JsonResponse

### Community 26 - "CLI Reference"
Cohesion: 0.07
Nodes (30): build — Package the application, cache:clear — Clear application cache, CLI Reference, Command summary, Config, config:cache — Cache configuration, dev — Development server, doctor — System diagnostics (+22 more)

### Community 27 - "Date"
Cohesion: 0.11
Nodes (3): Date, Parser, Str

### Community 30 - "Core Module"
Cohesion: 0.10
Nodes (21): Bearer authentication and authorization, Config, Core Module, DefaultExceptionHandler, Env, ExceptionHandler, File Uploads, HtmlResponse, JsonResponse, RedirectResponse (+13 more)

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".load"
Cohesion: 0.14
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 34 - "JdkHttpServerIntegrationTest.java"
Cohesion: 0.13
Nodes (6): Bearer authentication, Authenticated, BearerAuthMiddleware, BearerTokenResolver, RequiresPermissionMiddleware, RequestSizeLimitMiddleware

### Community 35 - "Module Authoring"
Cohesion: 0.11
Nodes (18): Adding Tests, Adding the module to the parent POM, Config-Driven Modules, Creating a New Interface, Creating your own facade, Expected YAML config shape, Getting config values in `boot()`, Minimal module (3 files) (+10 more)

### Community 36 - "MigrationRunner"
Cohesion: 0.14
Nodes (4): MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 39 - "Response"
Cohesion: 0.12
Nodes (3): RedirectResponse, Response, SecurityResponses

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 42 - "ProxyAddressMatcher"
Cohesion: 0.26
Nodes (3): ProxyAddressMatcher, ProxyNetwork, TrustedProxyResolver

### Community 44 - "database"
Cohesion: 0.09
Nodes (22): app, env, name, database, database, driver, host, password (+14 more)

### Community 46 - "PebbleViewExtensions"
Cohesion: 0.06
Nodes (17): JavelinViewExtension, HeadlineFilter, JavelinVersionFunction, Notes, Options, PebbleViewExtensions, PebbleViewRenderer, PebbleViewServiceProvider (+9 more)

### Community 49 - "Console Module"
Cohesion: 0.11
Nodes (16): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+8 more)

### Community 50 - "org.junit.jupiter.api.Test"
Cohesion: 0.11
Nodes (5): DefaultExceptionHandlerTest, HttpKernelTest, ResponseTest, RouterTest, StaticAssetResolverTest

### Community 51 - "Application Flow"
Cohesion: 0.11
Nodes (18): Application Flow, Boot Sequence, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development) (+10 more)

### Community 52 - "Override"
Cohesion: 0.10
Nodes (9): ModelTest, CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder, SampleUser, Status, DRAFT (+1 more)

### Community 54 - "Deployment"
Cohesion: 0.20
Nodes (9): Caddy alternative, Deployment, Graceful Shutdown, Health Checks, Migration Strategy, Performance Benchmarks, Reverse Proxy, Trusted proxy configuration (+1 more)

### Community 55 - "Cache"
Cohesion: 0.15
Nodes (8): Cache, Cache Module, CacheServiceProvider, InMemoryCache, Notes, Cache, InMemoryCache, Entry

### Community 56 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 57 - "RouteHandler"
Cohesion: 0.13
Nodes (5): Route, CompiledRoute, RouteBuilder, RouteHandler, Routes

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 60 - "HttpServerAdapter"
Cohesion: 0.11
Nodes (17): HttpServerAdapter, JdkHttpServiceProvider, Behavior, Configuration, HTTP Netty Module, Programmatic use, Behavior, Configuration (+9 more)

### Community 61 - "HtmlResponse"
Cohesion: 0.19
Nodes (6): View, ViewRenderer, HtmlResponse, View, ViewRenderer, ViewTest

### Community 63 - "pebble"
Cohesion: 0.12
Nodes (15): driver, pebble, allow_override_core_operators, auto_escaping, cache, default_escaping_strategy, default_locale, greedy_match_method (+7 more)

### Community 64 - "docs/README.md"
Cohesion: 0.20
Nodes (3): Javelin Docs, Module Index, Reading Order

### Community 65 - "Agent Notes"
Cohesion: 0.13
Nodes (14): Agent Notes, Architect Guidelines, Architecture Intent, Coding Principles, Current MVP Boundaries, Do, Do / Don’t, Don’t (+6 more)

### Community 66 - ".write"
Cohesion: 0.11
Nodes (4): GeneratorSupport, MakeControllerCommand, MakeModelCommand, MakeProviderCommand

### Community 68 - ".adapterFor"
Cohesion: 0.22
Nodes (3): HttpServerDriverProviderTest, DriverConfig, DriverEnv

### Community 70 - "Architecture"
Cohesion: 0.15
Nodes (12): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Key Design Decisions, Middleware pipeline, Module Boundaries (+4 more)

### Community 71 - "JdbcQueryBuilder"
Cohesion: 0.11
Nodes (3): JdbcDatabase, JdbcQueryBuilder, Condition

### Community 76 - ".commands"
Cohesion: 0.07
Nodes (10): CommandRegistry, CommandRegistry, AiInstallCommand, BuildCommand, CacheClearCommand, ConfigCacheCommand, NativeCommand, NewCommand (+2 more)

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.32
Nodes (15): io.javelin:javelin-cache, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-demo-app, io.javelin:javelin-http-common, io.javelin:javelin-http-jdk (+7 more)

### Community 79 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 80 - "JavelinConsole"
Cohesion: 0.22
Nodes (3): JavelinConsole, JavelinConsole, ConsoleKernel

### Community 81 - "Full Worked Example"
Cohesion: 0.20
Nodes (10): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+2 more)

### Community 83 - ".returnsAllowHeaderAndEnforcesBearerAuthentication"
Cohesion: 0.19
Nodes (7): Core interfaces you can implement, Example: Implementing `Cache` (javelin-cache), Implementing a Core Interface, ExceptionHandler, HttpKernel, NettyHttpServerIntegrationTest, RunningServer

### Community 85 - "Production Performance Tuning"
Cohesion: 0.22
Nodes (9): Connection pool (HikariCP), JMX monitoring, JVM flags, Native image (GraalVM), Production Performance Tuning, Rate limiting, Request size limit, Template caching (+1 more)

### Community 86 - "Router"
Cohesion: 0.09
Nodes (23): Layer 0 — Foundation, Layer 1 — Contracts, Layer 2 — Adapters, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Dependency Graph, Route, RouteHandler (+15 more)

### Community 87 - "AppServiceProvider.java"
Cohesion: 0.12
Nodes (8): AppServiceProvider, web, Phase 2: Boot, Startup Time, Why startup is fast, Config-List Provider Loading, ProviderLoader, ProviderLoader

### Community 88 - "commandline"
Cohesion: 0.17
Nodes (3): CliException, Names, ProcessTasks

### Community 89 - ".handle"
Cohesion: 0.20
Nodes (3): RequiresAuthenticationMiddleware, RequestAttributesTest, AuthorizationMiddlewareTest

### Community 90 - "Database JDBC Module"
Cohesion: 0.22
Nodes (8): Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Notes, QueryBuilder

### Community 95 - "AppProfile"
Cohesion: 0.11
Nodes (9): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, AppProfileTest (+1 more)

### Community 97 - "Javelin CLI"
Cohesion: 0.29
Nodes (6): AI Commands, Architecture, Build, Core Commands, Javelin CLI, Native Image

### Community 98 - "Request"
Cohesion: 0.10
Nodes (4): Request, UploadedFile, RequestInputTest, RequestMultipartTest

### Community 103 - "Log"
Cohesion: 0.14
Nodes (7): Logging SLF4J Module, Notes, Slf4jLogger, Slf4jLogServiceProvider, Log, NoopLogger, Logger

### Community 104 - "AI Commands"
Cohesion: 0.40
Nodes (5): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI

### Community 105 - "Docker Compose"
Cohesion: 0.25
Nodes (8): Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis), Minimal setup, nginx configuration, Running

### Community 106 - "Next"
Cohesion: 0.14
Nodes (9): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, TrustedProxyResolver, Trusted proxy configuration, RateLimitMiddleware (+1 more)

### Community 131 - "Container"
Cohesion: 0.07
Nodes (24): Application, Container, Container, Container, Binding, ContainerTest, Dependency, Service (+16 more)

### Community 133 - "SupportUtilitiesTest"
Cohesion: 0.26
Nodes (3): SupportUtilitiesTest, Person, Profile

### Community 134 - "Binding Strategies"
Cohesion: 0.50
Nodes (4): `bind(A, B)` — reusable factory, Binding Strategies, `instance(T, object)` — pre-built singleton, `singleton(T, supplier)` — lazy singleton

### Community 135 - "Pattern Reference"
Cohesion: 0.50
Nodes (4): Checklist for new modules, Module type matrix, Pattern Reference, Standard service provider template

### Community 136 - "migrate — Run pending migrations"
Cohesion: 0.67
Nodes (3): migrate — Run pending migrations, On first run, What happens

### Community 139 - ".register"
Cohesion: 0.21
Nodes (4): Application profile, DevProfileMiddleware, ProfileWiringTest, ProfileConfig

### Community 141 - "HTTP JDK Module"
Cohesion: 0.40
Nodes (5): HTTP JDK Module, JdkHttpServer, Notes, Static file serving, Trusted proxy resolution

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DEV`, `PROD`, `DRAFT`, `PUBLISHED` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 562 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **71 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `ServiceProvider` to `.load`, `ServeCommand`, `CommandContext`, `Container`, `Override`, `cli/ConsoleKernel.java`, `list`, `.register`, `.commands`, `Application`, `HttpServerAdapter`, `path`, `JavelinConsole`, `.registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot`, `map`, `AppServiceProvider.java`, `ConsoleOutput`, `AppProfile`?**
  _High betweenness centrality (0.120) - this node is a cross-community bridge._
- **What connects `io.javelin:javelin-parent`, `DEV`, `PROD` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.run` be split into smaller, more focused modules?**
  _Cohesion score 0.12554112554112554 - nodes in this community are weakly interconnected._
- **Why does `Database` connect `Model` to `Override`, `MigrationRunner`, `list`, `JdbcQueryBuilder`, `Override`, `Model`, `.returnsAllowHeaderAndEnforcesBearerAuthentication`, `Override`, `ServiceProvider`, `Router`, `TransactionCallback`, `Database JDBC Module`?**
  _High betweenness centrality (0.076) - this node is a cross-community bridge._
- **Should `CommandContext` be split into smaller, more focused modules?**
  _Cohesion score 0.09359605911330049 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `ServiceProvider` to `Agent Notes`, `Module Authoring`, `Pattern Reference`, `Application`, `.returnsAllowHeaderAndEnforcesBearerAuthentication`, `Router`, `AppServiceProvider.java`, `Core Module`?**
  _High betweenness centrality (0.051) - this node is a cross-community bridge._
- **Should `Override` be split into smaller, more focused modules?**
  _Cohesion score 0.13675213675213677 - nodes in this community are weakly interconnected._