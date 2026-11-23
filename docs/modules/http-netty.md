# HTTP Netty Module

Artifact: `io.javelin:javelin-http-netty`

`NettyHttpServer` adapts Javelin to Netty 4.2 with an event-loop architecture. It uses `HttpServerCodec`, `HttpObjectAggregator`, and a virtual-thread-free NIO event loop group.

## Configuration

Set the driver in `config/app.yaml`:

```yaml
javelin:
  starter:
    http-driver: netty
server:
  host: 0.0.0.0
  port: 8080
security:
  trusted_proxies:
    - 127.0.0.1
  max_request_bytes: 1048576
```

`NettyHttpServiceProvider` registers `HttpKernel` and `HttpServerAdapter` when `javelin.starter.http-driver` is `netty` — the default (legacy `server.driver` still works). The starter's `HttpServerDriverProvider` selects it automatically. Add the `javelin-http-netty` module to your application dependencies — it is optional in the starter but is the default profile in the demo app.

## Programmatic use

```java
HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());
NettyHttpServer server = new NettyHttpServer(
    "127.0.0.1", 8080, kernel,
    List.of("127.0.0.1"), Path.of("public"), 1_048_576
);
server.start();
server.stop(5);
```

- Constructors mirror `JdkHttpServer`; the six-argument form sets the maximum request body size.
- `start()` binds the socket using one boss event loop and a worker group sized to the available processors.
- `stop(delaySeconds)` closes the channel and shuts down both event loop groups.

## Behavior

- Static assets are resolved through `HttpKernel` and global middleware.
- Unsupported methods return 405, malformed URIs return 400, and oversized bodies return 413.
- `HEAD` requests fall back to the matching `GET` route; response bodies are suppressed.
- Keep-alive connections are preserved for successful responses.
- The application profile tunes event-loop workers and socket options: `dev` uses 2 workers and a 128 backlog; `prod` uses `max(4, 2 × CPUs)` workers, a 1024 backlog and `SO_KEEPALIVE`. See [Application Profile](profile.md).
- Netty 4.2 disables `sun.misc.Unsafe` by default on Java 24+ and probes it with a DEBUG-level log that includes a stack trace. It is harmless; the demo app's `logback.xml` sets `io.netty` to WARN so it does not appear at startup.
