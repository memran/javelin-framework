# HTTP Undertow Module

Artifact: `io.javelin:javelin-http-undertow`

`UndertowHttpServer` adapts Javelin to Undertow 2.3. It uses Undertow's XNIO I/O threads for socket handling and dispatches request processing to worker threads so blocking request bodies and response writes are safe.

## Configuration

Set the driver in `config/app.yaml`:

```yaml
javelin:
  starter:
    http-driver: undertow
server:
  host: 0.0.0.0
  port: 8080
security:
  trusted_proxies:
    - 127.0.0.1
  max_request_bytes: 1048576
```

`UndertowHttpServiceProvider` registers `HttpKernel` and `HttpServerAdapter` only when `javelin.starter.http-driver` is `undertow` (legacy `server.driver` still works). The starter's `HttpServerDriverProvider` selects it automatically. Add the `javelin-http-undertow` module to your application dependencies — it is optional in the starter.

## Programmatic use

```java
HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());
UndertowHttpServer server = new UndertowHttpServer(
    "127.0.0.1", 8080, kernel,
    List.of("127.0.0.1"), Path.of("public"), 1_048_576
);
server.start();
server.stop(0);
```

- Constructors mirror `JdkHttpServer`; the six-argument form sets the maximum request body size.
- `start()` creates and starts the Undertow server.
- `stop(delaySeconds)` delegates to Undertow's graceful stop.

## Behavior

- Static assets are resolved through `HttpKernel` and global middleware.
- Unsupported methods return 405, malformed query strings return 400, and oversized bodies return 413.
- `HEAD` requests fall back to the matching `GET` route; response bodies are suppressed.
- Requests arriving on an I/O thread are dispatched to the worker pool before any blocking read or write.
