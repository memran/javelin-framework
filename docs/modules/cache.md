# Cache Module

Artifact: `io.javelin:javelin-cache`

`javelin-cache` provides a simple cache contract and an in-memory implementation.

## Public API

- `Cache`
- `InMemoryCache`
- `CacheServiceProvider`

## Example

```java
Cache cache = app.make(Cache.class);
cache.put("greeting", "hello");
String value = cache.get("greeting").orElse("missing");
```

## Function Usage

- `Cache.put(key, value, ttl)` stores a value for the requested duration.
- `Cache.get(key)` returns an optional cached value.
- `Cache.forget(key)` deletes one entry.
- `InMemoryCache` is the default lightweight implementation.
- `CacheServiceProvider` binds the cache into the container.

## Notes

- Use it for cheap, explicit application caching.
- Swap implementations through the container and service providers.
