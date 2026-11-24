# Logging SLF4J Module

Artifact: `io.javelin:javelin-log-slf4j`

`javelin-log-slf4j` bridges the core logging contract to SLF4J and Logback. It provides both a concrete `Logger` implementation and a service provider that wires it into the application.

## Slf4jLogger

`Slf4jLogger` adapts the core `Logger` interface to SLF4J.

```java
// Default constructor creates logger named "javelin"
Logger logger = new Slf4jLogger();

// With a specific SLF4J logger instance
Logger logger = new Slf4jLogger(LoggerFactory.getLogger("myapp"));

logger.info("Server started on port 8080");
logger.warn("Disk space is below 10%");
logger.error("Failed to connect to database", new SQLException("timeout"));
```

- `new Slf4jLogger()` -- creates a logger with the name `"javelin"`.
- `new Slf4jLogger(org.slf4j.Logger logger)` -- wraps a custom SLF4J logger instance.
- `logger.info(message)` -- logs an informational message at INFO level.
- `logger.warn(message)` -- logs a warning message at WARN level.
- `logger.error(message, throwable)` -- logs an error with a stack trace at ERROR level.

```java
// Using the core Log facade after provider setup:
Log.info("Application booted");
Log.warn("Rate limit approaching");
Log.error("Request failed", exception);
```

## Slf4jLogServiceProvider

`Slf4jLogServiceProvider` installs SLF4J logging into the container.

```java
Slf4jLogServiceProvider provider = new Slf4jLogServiceProvider();
provider.register(app);
provider.boot(app);
```

- `provider.register(app)` -- creates a `Slf4jLogger` and registers it as `Logger` singleton in the container.
- `provider.boot(app)` -- calls `Log.use(logger)` to set the SLF4J adapter as the active static logger.

```java
// After the provider is registered and booted:
Logger logger = app.make(Logger.class);
logger.info("This logs through SLF4J/Logback");
```

## Notes

- Register the logging provider early in the starter stack (it is the first provider registered by `Javelin.create()`).
- Keep logger access through the core `Logger` contract or the static `Log` facade.
- Uses Logback as the default SLF4J implementation via the Maven dependency graph.

## StructuredLogger

`Slf4jLogger` also implements the optional `io.javelin.core.StructuredLogger` contract, which adds `debug` and context-aware overloads. Context entries are placed in the SLF4J MDC for the duration of the call and restored afterwards.

```java
StructuredLogger logger = app.make(StructuredLogger.class);
logger.debug("Cache warmed");
logger.info("User signed in", Map.of("user_id", id, "correlation_id", correlationId));
logger.error("Job failed", exception, Map.of("job_id", job.id()));

Log.debug("Cache warmed");
Log.info("User signed in", Map.of("user_id", id));
```

The plain `Logger` contract is unchanged, so existing implementations keep working.

## LoggingConfig

`Slf4jLogServiceProvider` registers a `LoggingConfig` built from explicit `log.*` keys and applies levels to the active Logback context during `boot()`. It never rewrites `logback.xml`.

| Key | Default | Meaning |
|---|---|---|
| `log.level.root` | `INFO` | Root logger level (`TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`, `OFF`, `ALL`). |
| `log.level.<logger>` | — | Per-logger level; keys are read from config, e.g. `log.level.io.netty: WARN`. |
| `log.request_logging` | `true` | Enable request logging middleware. |
| `log.request_id_header` | `X-Request-ID` | Header used to accept or return the request ID. |
| `log.correlation_id_header` | `X-Correlation-ID` | Header used to accept or return the correlation ID. |

Invalid level names fail fast with a clear error.

## RequestLoggingMiddleware

The provider registers the middleware automatically when `log.request_logging` is enabled. It:

1. accepts an incoming request/correlation ID or generates one (`UUID`),
2. puts both into the SLF4J MDC as `request_id` and `correlation_id`,
3. logs method, path, status and duration in milliseconds, or logs an error with the same fields when a request fails,
4. returns both IDs as response headers,
5. clears the MDC in a `finally` block.

The MDC fields are included automatically by `JsonLogEncoder`.

## JsonLogEncoder

`io.javelin.log.slf4j.JsonLogEncoder` is a small Logback encoder with no extra dependency. It writes one JSON object per line with `timestamp`, `level`, `logger`, `thread`, `message`, `mdc` and an optional `exception` stack trace.

Generated projects include `logback-prod.xml` using this encoder for both stdout and a `SizeAndTimeBasedRollingPolicy` file appender (10 MB files, 30 days, 1 GB cap). Activate it explicitly:

```bash
java -Dlogback.configurationFile=logback-prod.xml -jar app.jar serve
# or set LOG_LEVEL / LOG_PATH when running the generated Docker image
```

Use the plain `logback.xml` for development console output.
