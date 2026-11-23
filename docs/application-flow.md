# Application Flow

This document traces every step of a Javelin application's life: from startup through boot, HTTP request handling, middleware dispatch, error handling, and console command execution.

## Boot Sequence

### Phase 1: Entry Point

The application starts with `Javelin.run(Main.class, args)`:

```
Javelin.run(Main.class, args)
  │
  ├─ 1. resolveRoot(Main.class)
  │      │
  │      ├─ Check system property "javelin.root" → return if set
  │      ├─ Walk up from Main's codeSource looking for config/app.yaml
  │      └─ Fallback to System.getProperty("user.dir")
  │
  ├─ 2. create(root)
  │      │
  │      ├─ 2a. DotenvEnv.load(root/.env)
  │      │       Reads KEY=VALUE pairs. System env overrides file values.
  │      │
  │      ├─ 2b. YamlConfig.loadDirectory(root/config, env)
  │      │       Reads config/app.yaml + config/*.yaml.
  │      │       Resolves ${ENV_VAR:default} expressions.
  │      │
  │      ├─ 2c. new Application(config, env)
  │      │       │
  │      │       ├─ Creates Container
  │      │       ├─ instance(Application.class, this)
  │      │       ├─ instance(Container.class, container)
  │      │       ├─ instance(Config.class, config)
  │      │       ├─ instance(Env.class, env)
  │      │       ├─ instance(Router.class, new Router())
  │      │       └─ instance(ExceptionHandler.class, DefaultExceptionHandler)
  │      │
  │      ├─ 2d. Register 8 hardcoded service providers (in order):
  │      │       │
  │      │       ├─ Slf4jLogServiceProvider
  │      │       │     register: creates Slf4jLogger, binds as Logger
  │      │       │
  │      │       ├─ JdkHttpServiceProvider
  │      │       │     register: reads server.host/port, static_dir,
  │      │       │               trusted_proxies from config
  │      │       │               singleton(HttpKernel) → new HttpKernel(router, exceptionHandler)
  │      │       │               singleton(HttpServerAdapter) → new JdkHttpServer(config...)
  │      │       │
  │      │       ├─ PebbleViewServiceProvider
  │      │       │     register: reads view.template_dir/cache/suffix/auto_escape
  │      │       │               singleton(ViewRenderer) → new PebbleViewRenderer(config...)
  │      │       │
  │      │       ├─ JdbcDatabaseServiceProvider
  │      │       │     register: reads database.default + connections.*
  │      │       │               creates HikariConfig from config
  │      │       │               singleton(Database) → new JdbcDatabase(hikariConfig)
  │      │       │               singleton(MigrationRunner) → new MigrationRunner(db, migrationsDir)
  │      │       │
  │      │       ├─ SecurityServiceProvider
  │      │       │     register: singleton(PasswordHasher)
  │      │       │
  │      │       ├─ CacheServiceProvider
  │      │       │     register: singleton(Cache) → new InMemoryCache()
  │      │       │
  │      │       ├─ ConsoleServiceProvider
  │      │       │     register: singleton(ConsoleKernel) → new JavelinConsole(app)
  │      │       │
  │      │       └─ JavelinStarterServiceProvider
  │      │             register: reads migrations_dir, upload_dir, static_dir,
  │      │                       max_request_bytes from config
  │      │                       instance(WorkspaceDefaults, record)
  │      │
  │      └─ 2e. ProviderLoader.load(app)
  │              Reads config["providers"] list from YAML config.
  │              For each class name: Class.forName → instantiate → app.register(provider)
  │              (Useful for app-specific providers in config/app.yaml)
  │
  └─ 3. Return app (not yet booted)
```

### Phase 2: Boot

After `create()` returns, `Javelin.run()` calls `app.boot()`:

```
app.boot()
  │
  ├─ For each registered provider (in registration order):
  │     └─ provider.boot(app)
  │
  ├─ Slf4jLogServiceProvider.boot()
  │     └─ Log.use(logger)  → sets static Log facade to SLF4J
  │
  ├─ PebbleViewServiceProvider.boot()
  │     └─ View.use(renderer)  → sets static View facade to Pebble
  │
  ├─ SecurityServiceProvider.boot()
  │     ├─ app.router().middleware(new SecureHeadersMiddleware())
  │     └─ app.router().middleware(new RequestSizeLimitMiddleware(maxBytes))
  │
  ├─ JavelinStarterServiceProvider.boot()
  │     └─ new ValidationRuleLoader().load(app)
  │           ├─ Walks app/validation/*.java
  │           ├─ Maps to app.validation.<ClassName>
  │           ├─ Instantiates concrete ValidationRule subclasses
  │           ├─ Registers in ValidationRuleRegistry
  │           └─ Binds registry in container
  │
  └─ booted = true  → container is now locked (no more registrations)
```

### Phase 3: Dispatch

After boot, `app.make(ConsoleKernel.class).run(args)` is called. What happens next depends on the command:

```
ConsoleKernel.run(args)
  │
  ├─ Creates picocli CommandLine with all subcommands
  ├─ Parses args → matches subcommand
  │
  ├─ If "serve":
  │     └─ JavelinConsole.start(app)
  │           ├─ app.boot()  (idempotent — returns immediately if already booted)
  │           ├─ server = app.make(HttpServerAdapter.class)
  │           ├─ Runtime.addShutdownHook → server.stop(1)
  │           ├─ server.start()
  │           └─ CountDownLatch blocks forever
  │
  ├─ If "migrate":
  │     └─ app.make(MigrationRunner.class).migrate()
  │
  ├─ If "make:controller UserController":
  │     └─ Generates controller file from template
  │
  └─ If other commands:
        └─ Command is executed, output written to ConsoleOutput
```

## HTTP Request Lifecycle

When the server is running, each incoming HTTP request follows this path:

```
┌─────────────────────────────────────────────────────────────────┐
│                    JdkHttpServer.handle()                        │
│                                                                  │
│  1. Receive HttpExchange from JDK HttpServer                     │
│     (runs on virtual thread via Executors.newVirtualThread-      │
│      PerTaskExecutor())                                          │
│                                                                  │
│  2. Convert HttpExchange → Request                               │
│     ├─ HttpMethod.valueOf(exchange.getRequestMethod())           │
│     ├─ Normalized path from getRequestURI().getPath()            │
│     ├─ Headers from exchange.getRequestHeaders()                 │
│     ├─ Query parsed from getRequestURI().getRawQuery()           │
│     ├─ Body from exchange.getRequestBody().readAllBytes()        │
│     └─ Remote address via TrustedProxyResolver                   │
│          (resolves X-Forwarded-For only when peer is trusted)    │
│                                                                  │
│  3. Static asset check                                           │
│     ├─ StaticAssetResolver checks configured static directory    │
│     ├─ If file found → return Response immediately               │
│     └─ If not found → fall through to kernel                     │
│                                                                  │
│  4. HttpKernel.handle(request)                                   │
│     (see detailed flow below)                                    │
│                                                                  │
│  5. Convert Response → HttpExchange bytes                        │
│     ├─ Set response headers from response.headers()              │
│     ├─ HEAD request → sendResponseHeaders(status, -1), no body  │
│     ├─ Normal → sendResponseHeaders(status, body.length)         │
│     └─ exchange.getResponseBody().write(body)                    │
│                                                                  │
│  6. exchange.close()                                             │
└─────────────────────────────────────────────────────────────────┘
```

### HttpKernel Dispatch (Step 4 detailed)

```
HttpKernel.handle(Request request)
  │
  ├─ Router.resolve(method, path)
  │     │
  │     ├─ Iterates registered routes in registration order
  │     ├─ For each route: compare HttpMethod, then regex match path
  │     │     Route.compile("/users/{id}") → Pattern "^/users/([^/]+)$"
  │     │
  │     ├─ If match found:
  │     │     └─ Return ResolvedRoute(route, {id: "42"}) with parsed params
  │     │
  │     └─ If no match:
  │           └─ Return 404
  │               ├─ If Accept: application/json → JsonResponse.error("Not Found", 404)
  │               └─ Otherwise → Response.errorPage(404, ...) HTML page
  │
  ├─ Request routedRequest = request.withParams(resolved.params())
  │     (returns new Request with route params merged)
  │
  ├─ Build middleware pipeline:
  │     pipeline = router.globalMiddleware() + route.middleware()
  │
  └─ dispatch(pipeline, 0, routedRequest, handler)
        │
        └─ Recursive chain:
             │
             ├─ If index == middleware.size():
             │     └─ handler.handle(request)  → user code runs
             │
             └─ middleware.get(index).handle(request, next)
                   │
                   ├─ Middleware does its work
                   ├─ Calls next.handle(request) to continue pipeline
                   │     → dispatch(pipeline, index+1, request, handler)
                   └─ Returns Response (either from next or short-circuited)
```

### Middleware Pipeline Walkthrough

For a request to `POST /api/users` with global middleware `[SecureHeaders, RequestSizeLimit]` and route middleware `[Auth, RateLimit]`:

```
dispatch([SecureHeaders, RequestSizeLimit, Auth, RateLimit], 0, request, handler)
  │
  ├─ SecureHeaders.handle(request, next)
  │     ├─ Adds security headers to response later
  │     └─ next.handle(request)
  │           │
  │           ├─ dispatch([...], 1, request, handler)
  │           │
  │           ├─ RequestSizeLimit.handle(request, next)
  │           │     ├─ Checks request.body().length <= maxBytes
  │           │     ├─ If exceeded → return 413 response (short-circuit)
  │           │     └─ Else → next.handle(request)
  │           │           │
  │           │           ├─ dispatch([...], 2, request, handler)
  │           │           │
  │           │           ├─ Auth.handle(request, next)
  │           │           │     ├─ Checks auth token in header
  │           │           │     ├─ If invalid → return 401 response
  │           │           │     └─ Else → next.handle(request)
  │           │           │           │
  │           │           │           ├─ dispatch([...], 3, request, handler)
  │           │           │           │
  │           │           │           ├─ RateLimit.handle(request, next)
  │           │           │           │     ├─ Checks request count from IP
  │           │           │           │     ├─ If exceeded → return 429 response
  │           │           │           │     └─ Else → next.handle(request)
  │           │           │           │           │
  │           │           │           │           ├─ dispatch([...], 4, request, handler)
  │           │           │           │           │  index == size → handler.handle(request)
  │           │           │           │           │
  │           │           │           │           └─ Response ← user's RouteHandler
  │           │           │           │
  │           │           │           └─ Response ← propagates back up
  │           │           │
  │           │           └─ Response ← propagates back up
  │           │
  │           └─ Response ← propagates back up
  │
  └─ Response ← SecureHeaders adds headers to final response
```

### Error Handling

Any exception thrown in the pipeline or handler is caught by `HttpKernel.handle()`:

```
HttpKernel.handle(request)
  │
  └─ try:
       │
       ├─ Router.resolve(...)
       ├─ dispatch(...)
       └─ handler.handle(...)
     │
     └─ catch (Throwable throwable):
           └─ exceptionHandler.handle(throwable, request)
                 │
                 ├─ DefaultExceptionHandler:
                 │     └─ Returns 500 HTML error page
                 │
                 └─ Custom ExceptionHandler:
                       └─ Log + return appropriate error response
```

### Content Negotiation on 404

The router's 404 response depends on the `Accept` header:

```
Router.resolve() → no match
  │
  ├─ Accept: application/json
  │     └─ JsonResponse.error("Not Found", 404)
  │
  └─ Otherwise
        └─ Response.errorPage(404, "Not Found", "The requested resource could not be found.")
              └─ HtmlResponse with status 404
```

## Console Command Lifecycle

### CLI Dispatch

When `javelin <command>` is run from the terminal:

```
JavelinConsole.run(args)
  │
  └─ new io.javelin.cli.ConsoleKernel(app).run(args)
        │
        ├─ Creates picocli CommandLine with "javelin" as root command
        ├─ Registers all subcommands from CommandRegistry:
        │     new, serve, dev, routes:list,
        │     make:controller, make:service, make:model, make:module, make:migration,
        │     migrate, migrate:rollback, seed,
        │     test, build, native,
        │     queue:work, queue:status,
        │     schedule:run,
        │     cache:clear, config:cache,
        │     doctor, version,
        │     ai:install, ai:chat, ai:generate, ai:explain
        │
        ├─ picocli parses args → matches subcommand
        │
        ├─ If match:
        │     └─ Execute command.run()
        │
        ├─ If parse error:
        │     └─ CliException → output.error(message) + output.info("Fix: " + fix)
        │
        └─ Return exit code (0 = success, 1 = error)
```

### Generator Commands

Commands like `make:controller`, `make:model`, and `make:migration` follow this pattern:

```
GenerateCommandSupport.generate(rawName, suffix, rootPackage, rootDir, template)
  │
  ├─ Names.className(rawName, suffix)      → "UserController"
  ├─ Names.packageName(rawName, rootPackage) → "app.controller"
  ├─ Build target file path from package + class name
  │
  ├─ generator.write(target, template, values, force)
  │     ├─ TemplateEngine.render(template, values)
  │     │     Replaces {{className}}, {{packageName}}, etc.
  │     ├─ Write to target path
  │     └─ Return written path
  │
  └─ ConsoleOutput.success("Created: ...")
```

### Migration Commands

```
"migrate" command
  │
  ├─ app.make(MigrationRunner.class).migrate()
  │     ├─ Read migration files from database/migrations/*.yml
  │     ├─ Sort by filename (timestamp prefix)
  │     ├─ Check javelin_migrations table for already-applied files
  │     ├─ For each pending migration:
  │     │     ├─ Parse YAML → List<String> up statements
  │     │     ├─ Execute each statement via Database.execute(sql)
  │     │     └─ Record in javelin_migrations table
  │     └─ Return list of applied migration names

"migrate:rollback" command
  │
  ├─ app.make(MigrationRunner.class).rollback()
  │     ├─ Find last batch from javelin_migrations
  │     ├─ For each migration in batch (reverse order):
  │     │     ├─ Parse YAML → List<String> down statements
  │     │     └─ Execute each statement
  │     └─ Return list of rolled-back migration names
```

### Serve Command (Production)

```
"serve" command
  │
  ├─ app.boot()   (idempotent)
  ├─ server = app.make(HttpServerAdapter.class)  → JdkHttpServer
  ├─ Runtime.addShutdownHook → server.stop(1)
  ├─ server.start()
  ├─ Print: "Javelin running at http://{host}:{port}"
  └─ new CountDownLatch(1).await()  → blocks until shutdown
```

### Dev Command (Development)

The `dev` command delegates to `javelin dev` which typically runs the Maven process with hot-reload capabilities through the project launcher mechanism. The kernel resolves the workspace, locates the Javelin project, builds it, and starts the server in a watch loop.

## Container Resolution

When `app.make(ServiceType.class)` is called:

```
make(ServiceType.class)
  │
  ├─ Check instances map (pre-built singletons)
  │     └─ If found → return immediately
  │
  ├─ Check bindings map (factory-based)
  │     ├─ If singleton binding:
  │     │     ├─ Check instances map again (double-checked locking)
  │     │     ├─ If not found → factory.get() → cache in instances → return
  │     │     └─ If found → return cached
  │     └─ If non-singleton binding:
  │           └─ factory.get() → return (new instance each time)
  │
  ├─ If interface → throw (must be explicitly bound)
  │
  └─ Concrete class auto-resolution:
        ├─ Find constructor with most parameters
        ├─ For each parameter type:
        │     └─ Recursively make(paramType)
        └─ constructor.newInstance(resolved args) → return
```

## Configuration Resolution

When `config.getString("database.connections.pgsql.host")` is called:

```
YamlConfig (backed by nested Map<String, Object>)
  │
  ├─ Split key by "." → ["database", "connections", "pgsql", "host"]
  ├─ Walk nested maps:
  │     map["database"] → Map
  │       map["connections"] → Map
  │         map["pgsql"] → Map
  │           map["host"] → "localhost"
  │
  └─ Return Optional.of("localhost")

With ${ENV_VAR} resolution:
  │
  ├─ YAML value: "${DB_HOST:localhost}"
  ├─ env.get("DB_HOST") → Optional.of("db.example.com") if set
  │     └─ Return "db.example.com"
  └─ env.get("DB_HOST") → Optional.empty() if not set
        └─ Return "localhost" (default after colon)
```

## Lifecycle Diagram (Full)

```
┌──────────────────────────────────────────────────────────────────┐
│ STARTUP                                                           │
│                                                                   │
│  Javelin.run()                                                    │
│    │                                                              │
│    ├─ resolveRoot()         ── locate config/app.yaml             │
│    ├─ DotenvEnv.load()      ── read .env                          │
│    ├─ YamlConfig.load()     ── read config/ directory             │
│    ├─ new Application()     ── bootstrap container                │
│    ├─ register(8 providers) ── wire modules                       │
│    ├─ ProviderLoader.load() ── load config providers              │
│    ├─ app.boot()            ── boot all providers                 │
│    └─ ConsoleKernel.run()   ── dispatch command                   │
│         │                                                         │
│         ├─ "serve" → start HTTP server                            │
│         │    └─ CountDownLatch.await()  ← BLOCKS HERE             │
│         │         │                                                │
│         │         ├─ HTTP Request ──► Request Lifecycle           │
│         │         │    │                                           │
│         │         │    └─ Thread pool (virtual threads)            │
│         │         │         │                                      │
│         │         │         ├─ JdkHttpServer.handle()             │
│         │         │         ├─ StaticAssetResolver                │
│         │         │         ├─ HttpKernel.handle()                │
│         │         │         ├─ Router.resolve()                   │
│         │         │         ├─ Middleware pipeline                 │
│         │         │         └─ RouteHandler.handle()              │
│         │         │              └─ Response returned              │
│         │         │                                                │
│         │         └─ Shutdown signal → server.stop()              │
│         │                                                         │
│         ├─ "migrate" → MigrationRunner.migrate()                  │
│         ├─ "make:*"   → FileGenerator.write()                     │
│         └─ other      → command.run()                             │
│                                                                   │
└──────────────────────────────────────────────────────────────────┘
```

## Startup Time

The boot sequence is designed to be fast. On a typical developer machine:

| Phase | Time |
|---|---|
| `DotenvEnv.load()` | < 1 ms |
| `YamlConfig.loadDirectory()` | ~ 5 ms |
| `new Application()` | < 1 ms |
| Register 8 providers | < 1 ms |
| `ProviderLoader.load()` | ~ 2-10 ms (depends on provider class count) |
| `app.boot()` | ~ 2-5 ms |
| `ConsoleKernel.run()` | ~ 50-100 ms (picocli initialization) |
| **Total startup** | **~ 50-120 ms** |

### Why startup is fast

- No classpath scanning — all providers are listed explicitly
- No annotation processing — no reflection for stereotypes
- No ORM bootstrap — no entity manager factory, no connection pool warmup (connection pool is lazy)
- JDK `HttpServer` starts instantly — no servlet container, no deployment descriptor parsing
- Picocli is the only reflective component and it processes only registered commands
