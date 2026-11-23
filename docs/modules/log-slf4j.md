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
