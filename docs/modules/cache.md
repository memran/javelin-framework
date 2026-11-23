# Cache Module

Artifact: `io.javelin:javelin-cache`

`javelin-cache` provides a simple cache contract and an in-memory implementation for cheap, explicit application caching.

## Cache

`Cache` is the interface for all cache implementations.

```java
Cache cache = app.make(Cache.class);

// Store a value for 5 minutes
cache.put("greeting", "Hello World", Duration.ofMinutes(5));

// Store a value for 1 hour
cache.put("user_42", userData, Duration.ofHours(1));

// Retrieve
Optional<Object> value = cache.get("greeting");
String greeting = (String) cache.get("greeting").orElse("default");

// Remove
cache.forget("greeting");
```

- `cache.put(String key, Object value, Duration ttl)` -- stores a value with a time-to-live. After the TTL expires, `get()` returns empty.
- `cache.get(String key)` -- returns `Optional<Object>` containing the cached value, or empty if the key does not exist or has expired.
- `cache.forget(String key)` -- removes a single entry from the cache.

```java
// Typical usage pattern:
public class UserService {
    private final Cache cache;
    private final Database db;

    public UserService(Cache cache, Database db) {
        this.cache = cache;
        this.db = db;
    }

    public Map<String, Object> findById(long id) {
        String key = "user_" + id;
        return (Map<String, Object>) cache.get(key)
            .orElseGet(() -> {
                var user = db.table("users").where("id", id).first().orElseThrow();
                cache.put(key, user, Duration.ofMinutes(30));
                return user;
            });
    }

    public void update(long id, Map<String, Object> data) {
        db.table("users").where("id", id).update(data);
        cache.forget("user_" + id); // invalidate cache
    }
}
```

## InMemoryCache

`InMemoryCache` is the default lightweight implementation backed by a `ConcurrentHashMap`.

```java
InMemoryCache cache = new InMemoryCache();

cache.put("key", "value", Duration.ofSeconds(30));
cache.get("key");  // Optional["value"]
cache.forget("key");
cache.close();     // clears all entries
```

- `new InMemoryCache()` -- creates an empty in-memory cache.
- `cache.put(key, value, ttl)` -- stores with TTL. Uses `System.nanoTime()` for expiration tracking.
- `cache.get(key)` -- returns value if present and not expired.
- `cache.forget(key)` -- removes the entry.
- `cache.close()` -- clears all cached entries.

## CacheServiceProvider

`CacheServiceProvider` binds the cache into the container.

```java
CacheServiceProvider provider = new CacheServiceProvider();
provider.register(app);
```

- `provider.register(app)` -- creates an `InMemoryCache` and registers it as `Cache` singleton.

```java
// After provider registration:
Cache cache = app.make(Cache.class);
cache.put("started_at", Instant.now().toString(), Duration.ofHours(1));
```

## Notes

- Use it for cheap, explicit application caching (database query results, API responses, computed values).
- Swap implementations through the container and service providers — implement `Cache` and bind it.
- The in-memory implementation is suitable for single-instance applications.
- For distributed caching, implement `Cache` with Valkey/Redis or another backend. Generated projects already start Valkey in `docker-compose.yml` and expose `cache.driver` / `cache.url` config keys, but no Valkey adapter is bundled — bind your own implementation through a service provider.
