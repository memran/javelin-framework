# Logging SLF4J Module

Artifact: `io.javelin:javelin-log-slf4j`

`javelin-log-slf4j` bridges the core logging contract to SLF4J and Logback.

## Public API

- `Slf4jLogger`
- `Slf4jLogServiceProvider`

## Example

```java
Logger logger = app.make(Logger.class);
logger.info("Server started");
logger.error("Failed to load config", exception);
```

## Function Usage

- `Slf4jLogger` adapts the core `Logger` interface to SLF4J.
- `logger.info(message)` records regular operational events.
- `logger.warn(message)` records recoverable problems.
- `logger.error(message, throwable)` records failures with stack traces.
- `Slf4jLogServiceProvider` installs the adapter into the container.

## Notes

- Register the logging provider early in the starter stack.
- Keep logger access through the core `Logger` contract.
