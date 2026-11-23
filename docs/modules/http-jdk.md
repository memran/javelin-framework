# HTTP JDK Module

Artifact: `io.javelin:javelin-http-jdk`

`javelin-http-jdk` adapts the framework to the JDK `HttpServer` with virtual threads. It converts each `HttpExchange` into the core `Request` and `Response` model.

## JdkHttpServer

`JdkHttpServer` implements `HttpServerAdapter` and runs the JDK built-in HTTP server with virtual threads.

```java
HttpKernel kernel = app.make(HttpKernel.class);

// Minimal constructor
JdkHttpServer server = new JdkHttpServer("127.0.0.1", 8080, kernel);

// With trusted proxies (for correct client IP behind reverse proxy)
JdkHttpServer server = new JdkHttpServer(
    "0.0.0.0", 8080, kernel,
    List.of("127.0.0.1", "10.0.0.1")
);

// With trusted proxies and static file root
JdkHttpServer server = new JdkHttpServer(
    "127.0.0.1", 8080, kernel,
    List.of("127.0.0.1"),
    Path.of("public")
);

// With an explicit request body limit (bytes)
JdkHttpServer limited = new JdkHttpServer(
    "127.0.0.1", 8080, kernel,
    List.of(), Path.of("public"), 1_048_576
);

server.start();
// ... later on shutdown:
server.stop(5); // 5 second grace delay
server.close();
```

- `new JdkHttpServer(host, port, kernel)` -- creates a server with no trusted proxy resolution and no static file serving.
- `new JdkHttpServer(host, port, kernel, trustedProxies)` -- creates a server that resolves the real client IP from `X-Forwarded-For` or `Forwarded` headers only when the immediate peer is in `trustedProxies`.
- `new JdkHttpServer(host, port, kernel, trustedProxies, staticRoot)` -- adds static file serving with the default 1 MiB body limit.
- `new JdkHttpServer(host, port, kernel, trustedProxies, staticRoot, maxRequestBytes)` -- adds static file serving and an explicit body limit; bodies over the limit are rejected with 413 before being read into memory.
- `server.start()` -- begins listening and dispatching requests through the kernel using `Executors.newVirtualThreadPerTaskExecutor()`.
- `server.stop(int delaySeconds)` -- stops the server with a grace delay.
- `server.close()` -- calls `stop(0)` via `AutoCloseable`.

### Static file serving

```java
// With static root configured, files in "public/" are served automatically:
// GET /styles.css -> serves public/styles.css
// GET /js/app.js -> serves public/js/app.js

JdkHttpServer server = new JdkHttpServer("0.0.0.0", 8080, kernel,
    List.of("127.0.0.1"),
    Path.of("public")
);
```

The server checks the static directory first. If no matching file is found, the request falls through to the kernel/router. Static responses still pass through global middleware (for example `SecureHeadersMiddleware`), so security headers are applied to assets too.

### Trusted proxy resolution

```java
// When behind a reverse proxy like nginx:
JdkHttpServer server = new JdkHttpServer("0.0.0.0", 8080, kernel,
    List.of("127.0.0.1", "10.0.0.1")
);

// request.remoteAddress() will resolve the real client IP
// from X-Forwarded-For or Forwarded headers,
// but only when the immediate connection is from a trusted IP.
```

## JdkHttpServiceProvider

`JdkHttpServiceProvider` registers the HTTP adapter in the container when `javelin.starter.http-driver` is `jdk`. The starter's `HttpServerDriverProvider` selects between `jdk`, `netty` and `undertow` from that config key (with `server.driver` as a fallback).

Shared logic (static assets, trusted proxies, adapter config) lives in [HTTP Common](http-common.md).

```java
JdkHttpServiceProvider provider = new JdkHttpServiceProvider();
provider.register(app);
```

- `provider.register(app)` -- reads `server.host` and `server.port` from config (defaults: `"0.0.0.0"` and `8080`), reads `security.trusted_proxies`, `server.static_dir` and `security.max_request_bytes`, and registers `HttpKernel` and `HttpServerAdapter` as singletons.

```java
// Config keys used:
// server.host (default: "0.0.0.0")
// server.port (default: 8080)
// server.static_dir (default: "public")
// security.trusted_proxies (default: none)
// security.max_request_bytes (default: 1048576)
```

```java
// After provider registration:
HttpKernel kernel = app.make(HttpKernel.class);
HttpServerAdapter server = app.make(HttpServerAdapter.class);
server.start();
```

## Notes

- Uses `Executors.newVirtualThreadPerTaskExecutor()` for lightweight concurrency.
- Converts each `HttpExchange` into the core `Request` and `Response` model.
- Resolves trusted client IPs from forwarded headers only when the immediate peer is trusted.
- Static files are served before route matching — no route handler needed for assets — but still pass through global middleware.
- Unsupported HTTP methods return 405, malformed query strings return 400, and oversized bodies return 413.
- Empty response bodies are sent without a body, avoiding chunked framing.
