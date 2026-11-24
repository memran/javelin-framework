# Cache Valkey Module

Artifact: `io.javelin:javelin-cache-valkey`

A `Cache` implementation backed by Valkey (or any Redis-protocol server) using the Lettuce client.

## ValkeyCache

```java
Cache cache = new ValkeyCache("redis://localhost:6379");

cache.put("greeting", "Hello", Duration.ofMinutes(5));
Optional<Object> value = cache.get("greeting");
cache.forget("greeting");
```

- Values are stored as JSON with `SETEX`, so the TTL is enforced by Valkey and entries expire even if the app stops.
- `get(key)` returns Jackson-native values (`Map`, `List`, `String`, `Number`) rather than the original Java class.
- `get(key, Class<T>)` converts the stored JSON to the requested type:

```java
Optional<User> user = cache.get("user:42", User.class);
```

- `close()` closes the connection and shuts down the client. Register it as a singleton so the connection is reused. The connection is opened lazily on first use.

## ValkeyCacheServiceProvider

Activation is explicit: list the provider in `config/app.yaml` and select the driver.

```yaml
cache:
  driver: valkey
  url: redis://valkey:6379

providers:
  - io.javelin.cache.valkey.ValkeyCacheServiceProvider
```

| Config key | Default | Meaning |
|---|---|---|
| `cache.driver` | `memory` | Must be `valkey` for the provider to bind |
| `cache.url` | `redis://localhost:6379` | Lettuce Redis URI |

When the driver is not `valkey` the provider does nothing, so the starter's `InMemoryCache` remains in place. The provider binds with `app.singleton(Cache.class, ...)`, overriding an earlier binding.

## Notes

- Valkey speaks the Redis protocol; use `redis://` (or `rediss://` for TLS) URLs.
- No server is started by the framework — use the generated `docker-compose.yml` or an external Valkey instance.
- Tests cover JSON encoding and provider selection; they do not require a running server.
