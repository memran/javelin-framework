# HTTP JDK Module

Artifact: `io.javelin:javelin-http-jdk`

`javelin-http-jdk` adapts the framework to the JDK `HttpServer` with virtual threads.

## Public API

- `JdkHttpServer`
- `JdkHttpServiceProvider`

## Example

```java
HttpKernel kernel = app.make(HttpKernel.class);
JdkHttpServer server = new JdkHttpServer("127.0.0.1", 8080, kernel);
server.start();
```

## Function Usage

- `new JdkHttpServer(host, port, kernel)` creates the JDK HTTP server adapter around the core kernel.
- `start()` begins listening and dispatching requests through the kernel.
- `JdkHttpServiceProvider` registers the adapter in the container so `starter` can resolve it automatically.

## Notes

- Uses `Executors.newVirtualThreadPerTaskExecutor()`.
- Converts each `HttpExchange` into the core `Request` and `Response` model.
