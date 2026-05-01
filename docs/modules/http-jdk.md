# HTTP JDK Module

Artifact: `io.javelin:javelin-http-jdk`

`javelin-http-jdk` adapts the framework to the JDK `HttpServer` with virtual threads.

## Public API

- `JdkHttpServer`
- `JdkHttpServiceProvider`

## Example

```java
HttpKernel kernel = app.make(HttpKernel.class);
JdkHttpServer server = new JdkHttpServer(
    "127.0.0.1",
    8080,
    kernel,
    List.of("127.0.0.1"),
    Path.of("public")
);
server.start();
```

## Function Usage

- `new JdkHttpServer(host, port, kernel, trustedProxies, staticRoot)` creates the JDK HTTP server adapter around the core kernel.
- `start()` begins listening and dispatching requests through the kernel.
- `JdkHttpServiceProvider` registers the adapter in the container so `starter` can resolve it automatically.
- `JdkHttpServer` serves files from the configured static directory before the request reaches the kernel, and falls back to the kernel/router when no asset matches.
- Real-server integration tests cover static assets, multipart uploads, and trusted proxy header resolution through the actual JDK `HttpServer`.

## Notes

- Uses `Executors.newVirtualThreadPerTaskExecutor()`.
- Converts each `HttpExchange` into the core `Request` and `Response` model.
- Resolves trusted client IPs from forwarded headers only when the immediate peer is trusted.
