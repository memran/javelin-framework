# Cache Caffeine Module

Artifact: `io.javelin:javelin-cache-caffeine`

`javelin-cache-caffeine` implements the core `Cache` contract with [Caffeine](https://github.com/ben-manes/caffeine). It is selected automatically when `cache.driver` is `memory` (the default) or `caffeine`; a `valkey` driver leaves the Valkey adapter in charge.

## Developer API

```java
import io.javelin.caches.Cache;
import java.time.Duration;

Cache.put("users_count", 100, Duration.ofMinutes(5));
Optional<Object> value = Cache.get("users_count");
Cache.forget("users_count");
```

The facade class is `io.javelin.caches.Cache`. The contract used for injection is `io.javelin.cache.Cache`:

```java
io.javelin.cache.Cache cache = app.make(io.javelin.cache.Cache.class);
cache.put("key", value, Duration.ofSeconds(30));
```

## Configuration

| Key | Default | Meaning |
|---|---|---|
| `cache.caffeine.maximum_size` | `10000` | Maximum entries; Caffeine evicts using its window/TinyLFU policy |
| `cache.caffeine.default_ttl_seconds` | `0` | Optional default write expiry; `0` means use each `put` TTL |

Each `put(key, value, ttl)` supplies its own expiration, so entries can have different lifetimes. `forget(key)` invalidates one entry; `CaffeineCache.invalidateAll()` clears the cache.

## Service provider

`CaffeineCacheServiceProvider`:

1. reads `cache.caffeine.*` into `CaffeineCacheConfig`,
2. binds a `CaffeineCache` and the `Cache` contract,
3. calls `io.javelin.caches.Cache.use(...)` during `boot()`.

Generated projects register this provider before the Valkey provider, so setting `cache.driver: valkey` switches backends without changing application code.
