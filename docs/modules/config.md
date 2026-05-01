# Config Module

Artifact: `io.javelin:javelin-config`

`javelin-config` loads `.env` values and YAML configuration into the core `Env` and `Config` contracts.

## Public API

- `DotenvEnv`
- `YamlConfig`
- `ConfigServiceProvider`

## Example

```java
Path root = Path.of(".");
Env env = DotenvEnv.load(root.resolve(".env"));
Config config = YamlConfig.loadDirectory(root.resolve("config"), env);

String appName = config.getString("app.name").orElse("Javelin");
int port = config.getInt("server.port").orElse(8080);
```

## Function Usage

- `DotenvEnv.load(path)` reads `.env` files into the core `Env` contract.
- `env.get(key)` returns an optional string when a variable exists.
- `env.get(key, fallback)` is the convenience form for default values.
- `YamlConfig.loadDirectory(path, env)` reads the config directory and resolves environment expressions.
- `config.getString(key)` reads a string value.
- `config.getInt(key)` and `config.getBoolean(key)` read typed values without manual parsing.
- `config.getStringList(key)` reads a string list from YAML configuration.
- `ConfigServiceProvider` wires the config implementation into the container.

## Notes

- `YamlConfig` supports `config/app.yaml` plus extra files in `config/`.
- Values can resolve `${ENV_VAR:default}` expressions.
