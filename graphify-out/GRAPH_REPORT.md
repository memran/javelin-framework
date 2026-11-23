# Graph Report - javelin-framework  (2026-11-24)

## Corpus Check
- 234 files · ~64,271 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 43 file(s) not represented in the graph (top: .stub 31, (none) 8, .xml 1)

## Summary
- 2516 nodes · 6198 edges · 171 communities (77 shown, 94 thin omitted)
- Extraction: 86% EXTRACTED · 14% INFERRED · 0% AMBIGUOUS · INFERRED: 847 edges (avg confidence: 0.87)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `6580dcd5`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- .run
- Override
- cli/ConsoleKernel.java
- Handle
- Collection
- Application
- StaticAssetResolver
- Database
- SecureHeadersMiddleware
- Javelin.java
- UploadedFile
- Env
- Model
- CLI Reference
- Date
- Javelin Framework
- commandline
- AppProfile
- .imageSupportDetectsAndReadsImageMetadata
- .load
- Validation
- NettyHttpServerIntegrationTest.java
- Config-Driven Modules
- MigrationRunner
- StaticAssetResolver.java
- JdkHttpServerIntegrationTest
- Response
- Support Module
- ProxyAddressMatcher
- Input
- database
- PebbleViewExtensions
- InMemoryCache
- Console Module
- org.junit.jupiter.api.Test
- docker-app.yaml
- Override
- NettyHttpServer
- Production Performance Tuning
- Cache
- ProjectLauncher
- RouteHandler
- Override
- PasswordHasher
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
- Arr
- Config
- package.json
- .commands
- io.javelin:javelin-starter
- .resolvesNestedSingletonDependencies
- .registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot
- JavelinConsole
- Module Authoring
- WorkspaceAgeRule
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- JdkHttpServer
- Str
- Router
- environment
- .returnsAllowHeaderAndEnforcesBearerAuthentication
- .Collection
- Database JDBC Module
- VersionCommand
- Pattern Reference
- Request
- path
- AppProfile
- MakeMigrationCommand
- Override
- Middleware
- WorkspaceStarterRule
- MultipartData
- PebbleViewRenderer
- ConsoleOutput
- Log
- AI Commands
- .handle
- Core Module
- MigrateCommand
- AdultAgeRule
- need_command
- io.javelin:javelin-parent
- .rollbackCommandReplaysYamlDownStatements
- web.java
- File
- JsonResponse
- .output
- ViewRenderer
- Obj
- io.pebbletemplates.pebble.extension.Filter
- Override
- Container
- db
- SupportUtilitiesTest
- dev.mjs
- HTTP Common Module
- depends_on
- healthcheck
- objects
- .application
- Javelin CLI
- services
- Security
- app
- nats
- valkey
- ProxyAddressMatcher
- .withAttribute
- Logger
- Slf4jLogger
- ValidationRuleRegistry
- commands/ServeCommand.java
- ServeCommand
- Application
- Module Dependency Graph
- HTTP JDK Module
- NoopLogger
- build.sh script
- Generator Helpers
- Response
- HttpKernel
- Request

## God Nodes (most connected - your core abstractions)
1. `Application` - 94 edges
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
- `Trusted proxy configuration` --references--> `JdkHttpServiceProvider`  [INFERRED]
  docs/deployment.md → modules/javelin-http-jdk/src/main/java/io/javelin/http/jdk/JdkHttpServiceProvider.java
- `TemplateEngine` --references--> `TemplateEngine`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/cli/generator/TemplateEngine.java
- `ConsoleServiceProvider` --references--> `ConsoleServiceProvider`  [INFERRED]
  docs/modules/console.md → modules/javelin-console/src/main/java/io/javelin/console/ConsoleServiceProvider.java
- `HTTP driver selection` --references--> `HttpServerDriverProvider`  [INFERRED]
  docs/modules/starter.md → modules/javelin-starter/src/main/java/io/javelin/starter/HttpServerDriverProvider.java
- `Static file serving` --references--> `SecureHeadersMiddleware`  [INFERRED]
  docs/modules/http-jdk.md → modules/javelin-security/src/main/java/io/javelin/security/SecureHeadersMiddleware.java

## Import Cycles
- None detected.

## Communities (171 total, 94 thin omitted)

### Community 0 - ".run"
Cohesion: 0.13
Nodes (8): AppServiceProvider, Main, Boot Sequence, Phase 1: Entry Point, Phase 2: Boot, Phase 3: Dispatch, Config-List Provider Loading, Javelin

### Community 5 - "cli/ConsoleKernel.java"
Cohesion: 0.05
Nodes (18): AbstractCommand, CommandContext, AiChatCommand, AiGenerateModuleCommand, BuildCommand, DevCommand, DoctorCommand, MakeServiceCommand (+10 more)

### Community 9 - "Handle"
Cohesion: 0.06
Nodes (7): Ai, Handle, Message, Reply, Http, Handle, Response

### Community 15 - "StaticAssetResolver"
Cohesion: 0.25
Nodes (3): StaticAssetResolver, ByteRange, StaticCachePolicy

### Community 17 - "Database"
Cohesion: 0.13
Nodes (7): Layer 2 — Adapters, Core interfaces you can implement, Database, QueryBuilder, TransactionCallback, Database, QueryBuilder

### Community 18 - "SecureHeadersMiddleware"
Cohesion: 0.12
Nodes (15): routes:list — Display registered routes, Example: Request timing middleware, Middleware reference (built-in), Service provider adds middleware to the router, Writing a Middleware Module, Bearer authentication, Notes, PasswordHasher (+7 more)

### Community 21 - "Javelin.java"
Cohesion: 0.10
Nodes (25): Adding to the hardcoded provider list, Integrating with Starter, Phase 1: `register(Application app)`, Phase 2: `boot(Application app)`, Provider registration order, Registration order matters, The ServiceProvider Contract, Application profile (+17 more)

### Community 24 - "Env"
Cohesion: 0.05
Nodes (32): Application Flow, CLI Dispatch, Configuration Resolution, Console Command Lifecycle, Container Resolution, Content Negotiation on 404, Dev Command (Development), Error Handling (+24 more)

### Community 25 - "Model"
Cohesion: 0.10
Nodes (6): Model, Model instance methods, Model overridable methods, Model static methods, Model, SampleHydratedUser

### Community 26 - "CLI Reference"
Cohesion: 0.06
Nodes (33): build — Package the application, cache:clear — Clear application cache, CLI Reference, Command summary, Config, config:cache — Cache configuration, dev — Development server, doctor — System diagnostics (+25 more)

### Community 28 - "Javelin Framework"
Cohesion: 0.12
Nodes (16): Boot Flow, Build And Test, CLI, Database, Documentation, Example Routes, First Run, Install (+8 more)

### Community 29 - "commandline"
Cohesion: 0.10
Nodes (7): GenerateCommandSupport, MakeControllerCommand, MakeModelCommand, MakeModuleCommand, FileGenerator, Names, TemplateEngine

### Community 30 - "AppProfile"
Cohesion: 0.16
Nodes (8): Application Profile, Programmatic use, Selecting the profile, What changes, AppProfile, DEV, PROD, ChannelInitializer<SocketChannel>

### Community 31 - ".imageSupportDetectsAndReadsImageMetadata"
Cohesion: 0.15
Nodes (7): Example, Javelin Support, Public API, Image, Dimensions, Handle, Info

### Community 32 - ".load"
Cohesion: 0.13
Nodes (5): ValidationRuleLoader, ValidationRuleLoaderTest, EmptyValidationConfig, TestConfig, TestEnv

### Community 34 - "NettyHttpServerIntegrationTest.java"
Cohesion: 0.31
Nodes (3): Authenticated, BearerAuthMiddleware, RequiresPermissionMiddleware

### Community 35 - "Config-Driven Modules"
Cohesion: 0.50
Nodes (4): Config-Driven Modules, Expected YAML config shape, Getting config values in `boot()`, Pattern: read config in `register()` or `boot()`

### Community 36 - "MigrationRunner"
Cohesion: 0.16
Nodes (4): MigrationRunner, MigrationFile, MigrationRecord, TypeReference<>

### Community 38 - "JdkHttpServerIntegrationTest"
Cohesion: 0.13
Nodes (5): DefaultExceptionHandler, ExceptionHandler, HttpKernel, JdkHttpServerIntegrationTest, RunningServer

### Community 39 - "Response"
Cohesion: 0.11
Nodes (4): RedirectResponse, Response, SecurityResponses, ResponseTest

### Community 40 - "Support Module"
Cohesion: 0.05
Nodes (40): Ad-hoc rules, Ai, Arr, Collection, Conditional chaining and debugging, Conditional configuration, Converting to Map, Creating collections (+32 more)

### Community 44 - "database"
Cohesion: 0.09
Nodes (22): app, env, name, database, database, driver, host, password (+14 more)

### Community 49 - "Console Module"
Cohesion: 0.12
Nodes (13): AbstractCommand, CLI Commands, CliException, Command, CommandContext, Console Module, ConsoleKernel (cli package), ConsoleOutput (+5 more)

### Community 51 - "docker-app.yaml"
Cohesion: 0.06
Nodes (33): app, env, name, cache, driver, url, database, database (+25 more)

### Community 52 - "Override"
Cohesion: 0.11
Nodes (9): ModelTest, CastedArticle, EditableUser, RecordingDatabase, RecordingQueryBuilder, SampleUser, Status, DRAFT (+1 more)

### Community 54 - "Production Performance Tuning"
Cohesion: 0.07
Nodes (26): Caddy alternative, Connection pool (HikariCP), Deployment, Docker Compose, Dockerfile, .dockerignore, Environment file, Full stack (with nginx reverse proxy and Redis) (+18 more)

### Community 55 - "Cache"
Cohesion: 0.18
Nodes (8): Example: Implementing `Cache` (javelin-cache), Implementing a Core Interface, Cache, Cache Module, CacheServiceProvider, InMemoryCache, Notes, Cache

### Community 56 - "ProjectLauncher"
Cohesion: 0.19
Nodes (5): JavelinCli, ProjectLauncher, BuildPlan, Invocation, ParsedArgs

### Community 57 - "RouteHandler"
Cohesion: 0.08
Nodes (19): Route, RouteHandler, Router, Routes, Routing, HttpMethod, DELETE, GET (+11 more)

### Community 58 - "Override"
Cohesion: 0.20
Nodes (3): MigrationRunnerTest, RecordingDatabase, RecordingQueryBuilder

### Community 60 - "HttpServerAdapter"
Cohesion: 0.16
Nodes (13): JdkHttpServiceProvider, Configuration, Configuration, ConfigServiceProvider, DevProfileMiddleware, HttpServerAdapter, ServiceProvider, HttpAdapterConfig (+5 more)

### Community 61 - "HtmlResponse"
Cohesion: 0.29
Nodes (3): HtmlResponse, View, ViewTest

### Community 63 - "pebble"
Cohesion: 0.12
Nodes (15): driver, pebble, allow_override_core_operators, auto_escaping, cache, default_escaping_strategy, default_locale, greedy_match_method (+7 more)

### Community 64 - "docs/README.md"
Cohesion: 0.13
Nodes (9): Behavior, HTTP Netty Module, Programmatic use, Behavior, HTTP Undertow Module, Programmatic use, Javelin Docs, Module Index (+1 more)

### Community 65 - "Agent Notes"
Cohesion: 0.13
Nodes (14): Agent Notes, Architect Guidelines, Architecture Intent, Coding Principles, Current MVP Boundaries, Do, Do / Don’t, Don’t (+6 more)

### Community 66 - ".write"
Cohesion: 0.11
Nodes (4): GeneratorSupport, MakeControllerCommand, MakeModelCommand, MakeProviderCommand

### Community 68 - ".adapterFor"
Cohesion: 0.20
Nodes (3): HttpServerDriverProviderTest, DriverConfig, DriverEnv

### Community 70 - "Architecture"
Cohesion: 0.17
Nodes (11): Architecture, Container (DI), Core Abstractions, Core owns contracts; adapters implement them, Guiding Principles, Middleware pipeline, Module Boundaries, Module Export Diagram (+3 more)

### Community 72 - "ValidationRule"
Cohesion: 0.15
Nodes (3): AdultAgeRule, AbstractWorkspaceRule, ValidationRule

### Community 75 - "package.json"
Cohesion: 0.11
Nodes (19): devDependencies, alpinejs@^3.13.0, esbuild@^0.24.0, tailwindcss@^4.0.0, @tailwindcss/cli@^4.0.0, name, private, scripts (+11 more)

### Community 76 - ".commands"
Cohesion: 0.11
Nodes (5): AiExplainCommand, AiInstallCommand, CacheClearCommand, ConfigCacheCommand, ConsoleKernel

### Community 77 - "io.javelin:javelin-starter"
Cohesion: 0.32
Nodes (15): io.javelin:javelin-cache, io.javelin:javelin-config, io.javelin:javelin-console, io.javelin:javelin-core, io.javelin:javelin-db-jdbc, io.javelin:javelin-demo-app, io.javelin:javelin-http-common, io.javelin:javelin-http-jdk (+7 more)

### Community 78 - ".resolvesNestedSingletonDependencies"
Cohesion: 0.47
Nodes (3): ContainerTest, Dependency, Service

### Community 79 - ".registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot"
Cohesion: 0.33
Nodes (3): JavelinStarterServiceProviderTest, TestConfig, TestEnv

### Community 81 - "Module Authoring"
Cohesion: 0.07
Nodes (28): 1. Create the directory structure, 2. pom.xml, 3. Interface, 4. Implementation, 5. Service provider, 6. Optional middleware, 7. Tests, 8. Add to parent POM (+20 more)

### Community 86 - "Router"
Cohesion: 0.26
Nodes (3): Router, HttpKernelTest, RouterTest

### Community 87 - "environment"
Cohesion: 0.13
Nodes (15): environment, APP_ENV, CACHE_DRIVER, DATABASE_HOST, DATABASE_NAME, DATABASE_PASSWORD, DATABASE_PORT, DATABASE_USERNAME (+7 more)

### Community 88 - ".returnsAllowHeaderAndEnforcesBearerAuthentication"
Cohesion: 0.19
Nodes (3): UndertowHttpServer, UndertowHttpServerIntegrationTest, RunningServer

### Community 90 - "Database JDBC Module"
Cohesion: 0.22
Nodes (8): Database JDBC Module, Database operations, JdbcDatabase, JdbcDatabaseServiceProvider, Migration YAML format, MigrationRunner, Notes, QueryBuilder

### Community 92 - "Pattern Reference"
Cohesion: 0.50
Nodes (4): Checklist for new modules, Module type matrix, Pattern Reference, Standard service provider template

### Community 98 - "Middleware"
Cohesion: 0.12
Nodes (7): Bearer authentication and authorization, Middleware, Middleware, Next, RequiresAuthenticationMiddleware, AuthorizationMiddlewareTest, RequestSizeLimitMiddleware

### Community 104 - "AI Commands"
Cohesion: 0.40
Nodes (5): ai:chat — Chat with AI, AI Commands, ai:explain — Explain code, ai:generate — Generate a module, ai:install — Configure AI

### Community 106 - "Core Module"
Cohesion: 0.15
Nodes (13): Config, Core Module, Env, HttpMethod, HttpServerAdapter, Json, Log, Logger (+5 more)

### Community 120 - ".rollbackCommandReplaysYamlDownStatements"
Cohesion: 0.31
Nodes (3): MigrationCommandTest, Env, RecordingDatabase

### Community 123 - "web.java"
Cohesion: 0.15
Nodes (4): HomeController, UserController, web, Json

### Community 127 - "ViewRenderer"
Cohesion: 0.22
Nodes (8): Notes, Options, PebbleViewExtensions, PebbleViewRenderer, PebbleViewServiceProvider, Using templates in routes, View Pebble Module, ViewRenderer

### Community 130 - "Override"
Cohesion: 0.24
Nodes (3): PebbleViewRendererTest, Function, Filter

### Community 132 - "db"
Cohesion: 0.25
Nodes (8): environment, image, restart, volumes, POSTGRES_DB, POSTGRES_PASSWORD, POSTGRES_USER, db

### Community 133 - "SupportUtilitiesTest"
Cohesion: 0.22
Nodes (3): SupportUtilitiesTest, Person, Profile

### Community 134 - "dev.mjs"
Cohesion: 0.25
Nodes (3): bin, env, watchers

### Community 135 - "HTTP Common Module"
Cohesion: 0.18
Nodes (9): HTTP Common Module, HttpAdapterConfig, HttpDriverConfig, StaticAssetResolver, StaticCachePolicy, TrustedProxyResolver, Trusted proxy configuration, RateLimitMiddleware (+1 more)

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

### Community 148 - "Logger"
Cohesion: 0.43
Nodes (5): Logging SLF4J Module, Notes, Slf4jLogger, Slf4jLogServiceProvider, Logger

### Community 153 - "Application"
Cohesion: 0.67
Nodes (3): Application, Container, Container

### Community 155 - "Module Dependency Graph"
Cohesion: 0.40
Nodes (5): Layer 0 — Foundation, Layer 1 — Contracts, Layer 3 — Toolchain, Layer 4 — Aggregator, Module Dependency Graph

### Community 156 - "HTTP JDK Module"
Cohesion: 0.40
Nodes (5): HTTP JDK Module, JdkHttpServer, Notes, Static file serving, Trusted proxy resolution

### Community 161 - "build.sh script"
Cohesion: 0.83
Nodes (3): build.sh script, build_assets(), build_java()

### Community 162 - "Generator Helpers"
Cohesion: 0.50
Nodes (4): FileGenerator, Generator Helpers, Names, TemplateEngine

### Community 163 - "Response"
Cohesion: 0.50
Nodes (4): HtmlResponse, JsonResponse, RedirectResponse, Response, Response instance methods, Response static factories

### Community 166 - "HttpKernel"
Cohesion: 0.67
Nodes (3): DefaultExceptionHandler, ExceptionHandler, HttpKernel

### Community 167 - "Request"
Cohesion: 0.67
Nodes (3): File Uploads, Request, UploadedFile

## Knowledge Gaps
- **12 isolated node(s):** `io.javelin:javelin-parent`, `DEV`, `PROD`, `DRAFT`, `PUBLISHED` (+7 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 640 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **94 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `Application` connect `Application` to `.run`, `files`, `Container`, `cli/ConsoleKernel.java`, `.application`, `list`, `Javelin.java`, `commands/ServeCommand.java`, `ServeCommand`, `AppProfile`, `.load`, `StaticAssetResolver.java`, `HttpServerAdapter`, `.adapterFor`, `Config`, `.commands`, `.registersWorkspaceDefaultsAndLoadsValidationRulesOnBoot`, `JavelinConsole`, `path`, `.rollbackCommandReplaysYamlDownStatements`, `web.java`?**
  _High betweenness centrality (0.115) - this node is a cross-community bridge._
- **Are the 18 inferred relationships involving `Router` (e.g. with `Layer 1 — Contracts` and `Application`) actually correct?**
  _`Router` has 18 INFERRED edges - model-reasoned connections that need verification._
- **What connects `io.javelin:javelin-parent`, `DEV`, `PROD` to the rest of the system?**
  _12 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `.run` be split into smaller, more focused modules?**
  _Cohesion score 0.13450292397660818 - nodes in this community are weakly interconnected._
- **Why does `ServiceProvider` connect `HttpServerAdapter` to `.run`, `Agent Notes`, `StaticAssetResolver.java`, `Core Module`, `Application`, `JavelinConsole`, `Database`, `Module Authoring`, `Javelin.java`, `Module Dependency Graph`, `Pattern Reference`?**
  _High betweenness centrality (0.061) - this node is a cross-community bridge._
- **Should `files` be split into smaller, more focused modules?**
  _Cohesion score 0.1225296442687747 - nodes in this community are weakly interconnected._
- **Why does `CommandContext` connect `cli/ConsoleKernel.java` to `MakeMigrationCommand`, `files`, `Config`, `.commands`, `Application`, `commands/ServeCommand.java`, `.rollbackCommandReplaysYamlDownStatements`, `.output`, `VersionCommand`, `commandline`, `path`?**
  _High betweenness centrality (0.054) - this node is a cross-community bridge._