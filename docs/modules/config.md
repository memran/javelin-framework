# Config Module

Artifact: `io.javelin:javelin-config`

`javelin-config` loads `.env` values and YAML configuration into the core `Env` and `Config` contracts.

## DotenvEnv

`DotenvEnv` reads `.env` files and merges them with system environment variables. System variables take precedence.

```java
Path root = Path.of(".");
Env env = DotenvEnv.load(root.resolve(".env"));

// Read values
Optional<String> dbUrl = env.get("DATABASE_URL");
String dbUrl = env.get("DATABASE_URL", "jdbc:h2:mem:default");
```

- `DotenvEnv.load(Path path)` -- parses the `.env` file at the given path. Lines are `KEY=VALUE` format. Merges with `System.getenv()` where system variables override file values.
- `env.get(key)` -- returns `Optional<String>` for the variable.
- `env.get(key, fallback)` -- returns the value or the fallback string if not set.

```java
// Example .env file:
// APP_NAME=Javelin
// DATABASE_URL=jdbc:postgresql://localhost:5432/myapp
// DEBUG=true

Env env = DotenvEnv.load(root.resolve(".env"));
String appName = env.get("APP_NAME", "Javelin");
boolean debug = Boolean.parseBoolean(env.get("DEBUG", "false"));
```

## YamlConfig

`YamlConfig` loads YAML configuration files and resolves `${ENV_VAR:default}` expressions using the provided `Env`.

```java
Path root = Path.of(".");
Env env = DotenvEnv.load(root.resolve(".env"));

// Load a single file
Config config = YamlConfig.load(root.resolve("config/app.yaml"), env);

// Load a directory (app.yaml + extra namespace files)
Config config = YamlConfig.loadDirectory(root.resolve("config"), env);
```

- `YamlConfig.load(Path path, Env env)` -- loads a single YAML file. Supports nested key access via dot notation.
- `YamlConfig.loadDirectory(Path directory, Env env)` -- loads `app.yaml` plus any additional `*.yaml` files in the directory as namespaced config (e.g., `database.yaml` becomes accessible under `database.*`).

```java
// Example config/app.yaml:
// app:
//   name: Javelin
//   debug: ${DEBUG:false}
// server:
//   port: ${PORT:8080}
// app:
//   cors_origins:
//     - http://localhost:3000
//     - https://myapp.com

String name = config.getString("app.name").orElse("Javelin");
int port = config.getInt("server.port").orElse(8080);
boolean debug = config.getBoolean("app.debug").orElse(false);
List<String> origins = config.getStringList("app.cors_origins");
```

### Config interface methods

- `config.getString(String key)` -- returns `Optional<String>`.
- `config.getString(String key, String fallback)` -- returns the value or fallback.
- `config.getInt(String key)` -- returns `Optional<Integer>`.
- `config.getInt(String key, int fallback)` -- returns the value or fallback.
- `config.getLong(String key)` / `config.getLong(String key, long fallback)` -- long variant for timeouts and sizes.
- `config.getBoolean(String key)` -- returns `Optional<Boolean>`; use `.orElse(fallback)` for a default.
- `config.getStringList(String key)` -- returns `List<String>` (empty list if key missing).
- `config.keys()` -- returns the set of dotted leaf keys (`YamlConfig` only; the default returns an empty set).

```java
// Dot-separated nested access
String dbHost = config.getString("database.connections.pgsql.host", "localhost");
int maxConnections = config.getInt("database.connections.pgsql.max_connections", 10);
```

### Environment expression resolution

YAML values can reference environment variables with optional defaults:

```yaml
database:
  url: ${DATABASE_URL:jdbc:postgresql://localhost:5432/myapp}
  password: ${DB_PASSWORD}
```

If `DATABASE_URL` is not set in the environment, the fallback `jdbc:postgresql://localhost:5432/myapp` is used.
If `DB_PASSWORD` is not set and has no fallback, the literal string `${DB_PASSWORD}` is used as the value.

## ConfigServiceProvider

`ConfigServiceProvider` wires environment and configuration into the container so they are available through `app.env()` and `app.config()`.

```java
ConfigServiceProvider provider = new ConfigServiceProvider();
provider.register(app);
```

- `provider.register(app)` -- loads `.env` via `DotenvEnv.load()`, loads `config/` directory via `YamlConfig.loadDirectory()`, and registers both `Env` and `Config` instances into the container.

```java
// After provider registration:
Env env = app.env();
Config config = app.config();
```

## Secret providers

`SecretProvider` gives secrets one explicit lookup path. Register `SecretsServiceProvider` to bind a composite provider that checks the environment first, then files in `secrets.dir`:

```yaml
secrets:
  dir: secrets
```

```java
String dbPassword = app.make(SecretProvider.class)
        .get("DB_PASSWORD")
        .orElseThrow();
```

- `EnvSecretProvider` reads `Env` (process environment plus `.env`).
- `FileSecretProvider` reads one trimmed value per file from a directory and rejects paths that escape it.
- `CompositeSecretProvider` tries providers in order.
- `APP_KEY` is used as the JWT signing fallback but is not generated or stored by the framework; supply it yourself. Keep `.env` for local development and use a real secret store in production.

## Notes

- `YamlConfig` supports `config/app.yaml` plus extra files in `config/`.
- Values can resolve `${ENV_VAR:default}` expressions.
- System environment variables always take precedence over `.env` file values.
