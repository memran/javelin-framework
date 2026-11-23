# Starter Module

Artifact: `io.javelin:javelin-starter`

`javelin-starter` wires the default runtime stack for applications. It is the main Maven dependency most apps should use. It composes core, config, HTTP, logging, views, database, security, cache, and console modules into a single boot sequence.

## Javelin

`Javelin` is the main entry point facade.

```java
public final class Main {
    public static void main(String[] args) {
        System.exit(Javelin.run(Main.class, args));
    }
}
```

- `Javelin.create(Path root)` -- builds the full `Application` stack for a workspace root. Loads environment, config, registers all default service providers (SLF4J logging, the selected HTTP driver, Pebble views, JDBC database, security, cache, console, and the starter provider). Returns an `Application` ready for additional registration and `boot()`.

```java
Path root = Path.of(".");
Application app = Javelin.create(root);
app.register(new MyServiceProvider());
app.boot();
```

- `Javelin.run(Class<?> mainClass, String[] args)` -- convenience method that resolves the workspace root from the main class location, calls `create()`, then `boot()`, and finally delegates to the `ConsoleKernel`. Returns the exit code. This is the standard entry point for production apps.

## Application profile

`Application.profile()` resolves `APP_ENV` from `.env` (falling back to `app.env` config, then `local`). In `dev`, the starter wires detailed exception responses and `DevProfileMiddleware` (`X-Javelin-Profile`, `Cache-Control: no-store`); in `prod`, responses stay generic. See [Application Profile](profile.md) for the full behavior table.

## HTTP driver selection

`HttpServerDriverProvider` wires exactly one HTTP adapter, chosen by an optional config prefix:

```yaml
javelin:
  starter:
    http-driver: netty   # netty (default) | jdk | undertow
```

- `javelin.starter.http-driver` is the preferred key. The legacy `server.driver` key is used as a fallback, and the default is `netty`.
- Only the selected adapter is wired. All three adapter dependencies are **optional** in the starter, so applications must add the matching `javelin-http-netty` (default), `javelin-http-jdk`, or `javelin-http-undertow` module to their own dependencies. The starter throws a clear error if the selected driver's module is missing.
- Unknown driver values fail fast at boot with an explanatory `IllegalStateException`.

```java
// Single-line app launch
System.exit(Javelin.run(MyApp.class, args));
```

## JavelinStarterServiceProvider

`JavelinStarterServiceProvider` is registered automatically by `Javelin.create()`. It resolves workspace paths from config and registers them.

```java
// Inside JavelinStarterServiceProvider.register():
// - Reads database.migrations_dir from config (default: "database/migrations")
// - Reads storage.upload_dir from config (default: "storage/uploads")
// - Reads server.static_dir from config (default: "public")
// - Reads security.max_request_bytes from config (default: 2097152)
// - Registers WorkspaceDefaults with these resolved paths
```

- `provider.register(app)` -- resolves workspace paths and registers `WorkspaceDefaults`.
- `provider.boot(app)` -- runs `ValidationRuleLoader.load(app)` to discover and register validation rules from `app/validation/`.

```java
// After boot, access workspace defaults:
WorkspaceDefaults defaults = app.make(WorkspaceDefaults.class);
Path root = defaults.root();
Path migrationsDir = defaults.migrationsDirectory();
Path uploadsDir = defaults.uploadsDirectory();
Path staticDir = defaults.staticDirectory();
long maxBytes = defaults.maxRequestBytes();
```

## WorkspaceDefaults

`WorkspaceDefaults` is an immutable record that exposes workspace filesystem defaults.

```java
WorkspaceDefaults d = new WorkspaceDefaults(
    Path.of("."),
    Path.of("database/migrations"),
    Path.of("storage/uploads"),
    Path.of("public"),
    2097152L
);

d.root();              // Path: "."
d.migrationsDirectory(); // Path: "database/migrations"
d.uploadsDirectory();    // Path: "storage/uploads"
d.staticDirectory();     // Path: "public"
d.maxRequestBytes();     // long: 2097152
```

## ValidationRuleLoader

`ValidationRuleLoader` discovers and registers custom validation rules.

```java
ValidationRuleLoader loader = new ValidationRuleLoader();
loader.load(app); // scans app/validation/ and registers rules
```

- `loader.load(app)` -- walks `app/validation/` directory under the workspace root for `.java` files. Maps each to `app.validation.<ClassName>`, instantiates concrete `ValidationRule` subclasses, and registers them into a `ValidationRuleRegistry` bound in the container. Also reads `validation.rules` from config for explicitly listed rules.

```java
// Access registered rules:
ValidationRuleRegistry registry = app.make(ValidationRuleRegistry.class);
Optional<ValidationRule> rule = registry.find("adult_age");
List<ValidationRule> allRules = registry.all();
```

## Default Service Providers

`Javelin.create()` registers these providers in order:

1. `Slf4jLogServiceProvider` -- binds `Slf4jLogger` as `Logger` and sets it as the active `Log` implementation.
2. `JdkHttpServiceProvider` -- registers `HttpKernel` and `JdkHttpServer` singletons.
3. `PebbleViewServiceProvider` -- registers `PebbleViewRenderer` as `ViewRenderer`.
4. `JdbcDatabaseServiceProvider` -- binds `JdbcDatabase` as `Database` and registers `MigrationRunner`.
5. `SecurityServiceProvider` -- registers `PasswordHasher` and adds `SecureHeadersMiddleware` + `RequestSizeLimitMiddleware` to the router.
6. `CacheServiceProvider` -- binds `InMemoryCache` as `Cache` singleton.
7. `ConsoleServiceProvider` -- registers `JavelinConsole` as `ConsoleKernel`.
8. `JavelinStarterServiceProvider` -- resolves workspace paths and loads validation rules.

## Notes

- This is the preferred dependency for application and workspace code.
- `Javelin.run()` is the standard entry point that handles workspace root resolution, boot, and console dispatch.
- `Javelin.create()` is useful when you need to register additional providers before `boot()`.
