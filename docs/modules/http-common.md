# HTTP Common Module

Artifact: `io.javelin:javelin-http-common`

Shared building blocks used by every Javelin HTTP adapter (`javelin-http-jdk`, `javelin-http-netty`, `javelin-http-undertow`). This module contains no server implementation.

## TrustedProxyResolver

```java
TrustedProxyResolver resolver = new TrustedProxyResolver(List.of("127.0.0.1"));
String clientIp = resolver.resolve(remoteAddress, requestHeaders);
```

- Resolves `X-Forwarded-For`, falling back to the standard `Forwarded` header.
- Only resolves when the immediate peer matches an explicitly configured IP or CIDR range, for example `127.0.0.1` or `10.0.0.0/8`. Hostnames are rejected.
- `"*"` is rejected at configuration time: there is no wildcard trust, in any environment.
- The forwarded chain is read right-to-left, skipping trusted proxies. The rightmost untrusted IP becomes `Request.remoteAddress()`; malformed tokens are ignored. A client therefore cannot spoof its address by prepending entries to the chain.
- If no untrusted address can be identified, the direct peer address is used.

## StaticAssetResolver

```java
StaticAssetResolver resolver = new StaticAssetResolver(Path.of("public"));
Optional<Response> response = resolver.resolve(method, path);
```

- Serves `GET` and `HEAD` requests for files under the configured root, including `index.html` for directories.
- Blocks path traversal (`..`), paths that normalize outside the root, and symlinks that resolve outside it.
- Returns `Optional.empty()` when no file matches, so the adapter falls through to the router.
- Adds `Cache-Control`, a content-based `ETag` computed with a streaming digest, `Accept-Ranges: bytes` and `X-Content-Type-Options: nosniff` to static responses.
- Full `GET` and `HEAD` responses stream from `Response.file(...)`; satisfiable ranges stream from `Response.fileRange(...)`. Neither buffers file bytes in a `byte[]`, and conditional requests read no body at all.
- `If-None-Match` (including `*` and weak `W/` ETags) produces a 304; `Range: bytes=start-end`, `bytes=start-` and `bytes=-suffix` produce a 206 with `Content-Range`, while an unsatisfiable range produces 416. Adapters pass request headers through via `resolve(Request request)`.
- Digests are cached by size and last-modified time for at most one minute and at most 1024 files. `resolver.etagStats()` returns hits, misses and `hitRate()`; wire it into your metrics registry with `Observability.metrics().gauge("static.etag.hit_rate", resolver.etagStats().hitRate())`.

### StaticCachePolicy

```java
new StaticAssetResolver(root, new StaticCachePolicy(31_536_000, true));
```

| Policy | `Cache-Control` |
|---|---|
| Default (`3600`, not immutable) | `public, max-age=3600` |
| Immutable (fingerprinted files only) | `public, max-age=<seconds>, immutable` |

Use `immutable` only for fingerprinted asset URLs; otherwise browsers can keep stale content even after a deploy.

## HttpDriverConfig

```java
String driver = HttpDriverConfig.driver(app.config());
```

Reads the optional `javelin.starter.http-driver` key, falls back to the legacy `server.driver` key, and finally defaults to `netty`. Blank values are treated as unset. Adapters and the starter share this resolver so selection behaves identically everywhere.

## HttpAdapterConfig

```java
HttpAdapterConfig config = HttpAdapterConfig.from(app.config(), root);
```

Reads the shared adapter settings:

| Key | Default |
|---|---|
| `server.host` | `127.0.0.1` |
| `server.port` | `8080` |
| `server.static_dir` | `public` |
| `security.trusted_proxies` | none |
| `security.max_request_bytes` | `1048576` |
| `server.static_cache_seconds` | `3600` |
| `server.static_cache_immutable` | `false` |
| `server.static_after_routes` | `false` |
| `server.request_timeout_seconds` | `30` |
| `server.idle_timeout_seconds` | `60` |

`HttpTimeoutConfig` validates positive durations. Netty returns 408 on a read timeout and closes idle connections; Undertow uses `REQUEST_PARSE_TIMEOUT` and `IDLE_TIMEOUT`; the JDK adapter applies the request timeout via a watchdog that closes the exchange (it has no native idle timeout).
