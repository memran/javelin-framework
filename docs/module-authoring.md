# Module Authoring

This guide covers how to create, structure, and integrate Javelin modules. Modules are the unit of composition in the framework — each module has one job, depends on `javelin-core` for contracts, and wires itself through a `ServiceProvider`.

## Table of Contents

- [Module Anatomy](#module-anatomy)
- [pom.xml Setup](#pomxml-setup)
- [The ServiceProvider Contract](#the-serviceprovider-contract)
- [Binding Strategies](#binding-strategies)
- [Implementing a Core Interface](#implementing-a-core-interface)
- [Creating a New Interface](#creating-a-new-interface)
- [Writing a Middleware Module](#writing-a-middleware-module)
- [Static Facade Integration](#static-facade-integration)
- [Config-Driven Modules](#config-driven-modules)
- [Adding Tests](#adding-tests)
- [Integrating with Starter](#integrating-with-starter)
- [Config-List Provider Loading](#config-list-provider-loading)
- [Pattern Reference](#pattern-reference)
- [Full Worked Example](#full-worked-example)

---

## Module Anatomy

Every Javelin module follows the same directory structure:

```
modules/javelin-<name>/
  pom.xml
  src/
    main/
      java/io/javelin/<name>/
        <Feature>.java              (interface or main class)
        <Feature>Impl.java          (concrete implementation)
        <Feature>ServiceProvider.java  (registers into the container)
    test/
      java/io/javelin/<name>/
        <Feature>Test.java          (optional, JUnit 5)
```

### Minimal module (3 files)

A module that implements a core interface needs exactly three source files:

```
modules/javelin-example/
  pom.xml
  src/main/java/io/javelin/example/
    ExampleServiceProvider.java
    ExampleImpl.java
```

### Module with a new interface (4 files)

A module that introduces a new contract adds an interface file:

```
modules/javelin-example/
  pom.xml
  src/main/java/io/javelin/example/
    Example.java                    ← new interface
    ExampleImpl.java                ← implementation
    ExampleServiceProvider.java     ← wiring
```

---

## pom.xml Setup

Every module uses `javelin-parent` as its parent POM and depends on `javelin-core`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>io.javelin</groupId>
        <artifactId>javelin-parent</artifactId>
        <version>0.1.0-SNAPSHOT</version>
        <relativePath>../../pom.xml</relativePath>
    </parent>
    <artifactId>javelin-example</artifactId>
    <dependencies>
        <dependency>
            <groupId>io.javelin</groupId>
            <artifactId>javelin-core</artifactId>
            <version>${project.version}</version>
        </dependency>
        <!-- External dependencies go here if needed -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

**Key points:**
- `<relativePath>` must point to the root `pom.xml` (two levels up from `modules/<name>/`).
- `javelin-core` is always the minimum dependency — it brings the container, `ServiceProvider`, `Application`, and all contracts.
- External dependencies (e.g., Jackson for YAML, SLF4J for logging) are added only when needed.
- JUnit 5 is available for tests (managed in parent's `dependencyManagement`).

### Adding the module to the parent POM

After creating the module directory, add it to `pom.xml` at the project root:

```xml
<modules>
    ...
    <module>modules/javelin-example</module>
</modules>
```

---

## The ServiceProvider Contract

`ServiceProvider` is the wiring interface for every module. It has two phases:

```java
// io.javelin.core.ServiceProvider
public interface ServiceProvider {
    void register(Application app);

    default void boot(Application app) {
    }
}
```

### Phase 1: `register(Application app)`

Called during the registration phase, **before** any provider is booted. Only bind services into the container — do not resolve services here.

```java
@Override
public void register(Application app) {
    app.bind(ServiceInterface.class, ServiceImplementation.class);
    app.singleton(ServiceInterface.class, ServiceImplementation::new);
    app.instance(ServiceInterface.class, prebuiltInstance);
}
```

### Phase 2: `boot(Application app)`

Called after **all** providers have been registered. Safe to resolve services, add middleware to the router, set static facades, or perform initialization.

```java
@Override
public void boot(Application app) {
    // Resolve and use services
    MyService service = app.make(MyService.class);
    service.start();

    // Add middleware to the router
    app.router().middleware(new MyMiddleware());

    // Set a static facade
    SomeFacade.use(app.make(SomeInterface.class));

    // Read config
    String value = app.config().getString("my.key", "default");
}
```

### Registration order matters

Providers are booted in the same order they were registered. The starter registers providers in this order:

1. `Slf4jLogServiceProvider` — logging must be available first
2. `JdkHttpServiceProvider` — HTTP kernel
3. `PebbleViewServiceProvider` — template rendering
4. `JdbcDatabaseServiceProvider` — database
5. `SecurityServiceProvider` — middleware (adds global middleware to router)
6. `CacheServiceProvider` — caching
7. `ConsoleServiceProvider` — CLI
8. `JavelinStarterServiceProvider` — workspace defaults + validation rules
9. `ProviderLoader` — loads config-listed providers from `config["providers"]`

If your module's `boot()` depends on another module's service being registered, register your provider after it.

---

## Binding Strategies

The container supports three binding strategies:

### `instance(T, object)` — pre-built singleton

Register an already-constructed value. Always returns the same object.

```java
app.instance(Config.class, myConfig);
app.instance(Path.class, root);
```

Use for: config objects, pre-built utilities, value objects.

### `bind(A, B)` — reusable factory

Each call to `make(A)` creates a new instance of B via constructor injection.

```java
app.bind(UserRepository.class, JdbcUserRepository.class);
```

Use for: stateless services, repository implementations.

### `singleton(T, supplier)` — lazy singleton

The supplier is called once. Subsequent `make(T)` calls return the cached instance.

```java
app.singleton(Cache.class, InMemoryCache::new);
app.singleton(HttpKernel.class, () -> new HttpKernel(router, exceptionHandler));
```

Use for: stateful services, shared resources (connection pools, caches).

---

## Implementing a Core Interface

The most common module pattern is implementing one of the core contracts. Here's how each adapter module does it:

### Example: Implementing `Cache` (javelin-cache)

**Step 1: Write the implementation**

```java
package io.javelin.example;

import io.javelin.cache.Cache;
import java.time.Clock;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryCache implements Cache {
    private final Map<String, Entry> values = new ConcurrentHashMap<>();
    private final Clock clock;

    public InMemoryCache() {
        this(Clock.systemUTC());
    }

    InMemoryCache(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void put(String key, Object value, Duration ttl) {
        values.put(key, new Entry(value, clock.millis() + ttl.toMillis()));
    }

    @Override
    public Optional<Object> get(String key) {
        Entry entry = values.get(key);
        if (entry == null) return Optional.empty();
        if (clock.millis() >= entry.expiresAt()) {
            values.remove(key);
            return Optional.empty();
        }
        return Optional.ofNullable(entry.value());
    }

    @Override
    public void forget(String key) {
        values.remove(key);
    }

    private record Entry(Object value, long expiresAt) {}
}
```

**Step 2: Write the service provider**

```java
package io.javelin.example;

import io.javelin.cache.Cache;
import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

public final class CacheServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(Cache.class, InMemoryCache::new);
    }
}
```

**Step 3: Users wire it in their app**

```java
Application app = Javelin.create(root);
app.register(new CacheServiceProvider());
app.boot();

Cache cache = app.make(Cache.class);
```

### Core interfaces you can implement

| Interface | Package | Method Contract |
|---|---|---|
| `Cache` | `io.javelin.cache` | `put(key, value, ttl)`, `get(key)`, `forget(key)` |
| `Database` | `io.javelin.core` | `table(name)`, `execute(sql)`, `transaction(callback)` |
| `QueryBuilder` | `io.javelin.core` | `where()`, `first()`, `get()`, `insert()`, `update()`, `delete()` |
| `Logger` | `io.javelin.core` | `info(msg)`, `warn(msg)`, `error(msg, throwable)` |
| `ViewRenderer` | `io.javelin.core` | `render(template, data)` |
| `HttpServerAdapter` | `io.javelin.core` | `start()`, `stop(delay)`, `close()` |
| `Config` | `io.javelin.core` | `getString(key)`, `getInt(key)`, `getBoolean(key)`, `getStringList(key)` |
| `Env` | `io.javelin.core` | `get(key)`, `get(key, fallback)` |
| `ExceptionHandler` | `io.javelin.core` | `handle(throwable, request)` |
| `Middleware` | `io.javelin.core` | `handle(request, next)` |
| `RouteHandler` | `io.javelin.core` | `handle(request)` |
| `ConsoleKernel` | `io.javelin.core` | `run(args)` |
| `ServiceProvider` | `io.javelin.core` | `register(app)`, `boot(app)` |

---

## Creating a New Interface

If your module introduces a new capability, define the interface in your module rather than in core. This keeps core lean.

```java
// modules/javelin-example/src/main/java/io/javelin/example/RateLimiter.java
package io.javelin.example;

public interface RateLimiter {
    boolean tryAcquire(String key);
    int remaining(String key);
}
```

Then provide a default implementation and a service provider:

```java
public final class TokenBucketRateLimiter implements RateLimiter {
    private final int capacity;
    private final Map<String, AtomicInteger> buckets = new ConcurrentHashMap<>();

    public TokenBucketRateLimiter(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean tryAcquire(String key) {
        return buckets.computeIfAbsent(key, k -> new AtomicInteger(capacity))
                .getAndUpdate(n -> n > 0 ? n - 1 : 0) > 0;
    }

    @Override
    public int remaining(String key) {
        return buckets.getOrDefault(key, new AtomicInteger(0)).get();
    }
}
```

```java
public final class RateLimiterServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(RateLimiter.class,
            () -> new TokenBucketRateLimiter(
                app.config().getInt("ratelimiter.capacity", 100)));
    }
}
```

---

## Writing a Middleware Module

Middleware modules provide cross-cutting request processing. The pattern has three parts: the middleware class, the service provider (which registers it on the router), and config integration.

### Example: Request timing middleware

```java
package io.javelin.example;

import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

public final class RequestTimerMiddleware implements Middleware {
    @Override
    public Response handle(Request request, Next next) throws Exception {
        long start = System.nanoTime();
        try {
            return next.handle(request);
        } finally {
            long elapsed = System.nanoTime() - start;
            System.out.printf("%s %s - %.2f ms%n",
                request.method(), request.path(), elapsed / 1_000_000.0);
        }
    }
}
```

### Service provider adds middleware to the router

```java
public final class RequestTimerServiceProvider implements ServiceProvider {
    @Override
    public void boot(Application app) {
        // Global — applies to every route
        app.router().middleware(new RequestTimerMiddleware());
    }
}
```

Users can also add middleware to specific routes or groups:

```java
app.router().get("/slow-query", handler)
    .middleware(new RequestTimerMiddleware());

app.router().group("/api", List.of(new RequestTimerMiddleware()), api -> {
    api.get("/users", userController::list);
});
```

### Middleware reference (built-in)

The security module provides three middleware implementations you can use as style references:

| Middleware | Purpose |
|---|---|
| `SecureHeadersMiddleware` | Adds `X-Content-Type-Options`, `X-Frame-Options`, `Referrer-Policy`, `Permissions-Policy` headers |
| `RequestSizeLimitMiddleware` | Returns 413 if `request.body().length > maxBytes` |
| `RateLimitMiddleware` | Returns 429 if client exceeds request count within a time window |

---

## Static Facade Integration

Some modules provide a static facade for convenience (e.g., `Log`, `View`, `Json`). The `boot()` phase is where the facade is wired.

### Pattern: service provider sets the static facade

```java
// Slf4jLogServiceProvider.boot()
@Override
public void boot(Application app) {
    Log.use(app.make(Logger.class));
}

// PebbleViewServiceProvider.boot()
@Override
public void boot(Application app) {
    View.use(app.make(ViewRenderer.class));
}
```

### Creating your own facade

```java
// In your module
public final class Metrics {
    private static volatile MetricCollector collector;

    public static void use(MetricCollector c) {
        collector = c;
    }

    public static void increment(String name) {
        var c = collector;
        if (c != null) c.increment(name);
    }

    public static void record(String name, long value) {
        var c = collector;
        if (c != null) c.record(name, value);
    }
}
```

```java
// In your service provider
@Override
public void boot(Application app) {
    Metrics.use(app.make(MetricCollector.class));
}
```

Facades are optional. Prefer container injection in application code and use facades only for operations that are truly ubiquitous (logging, metrics, rendering).

---

## Config-Driven Modules

If your module reads configuration, read it in the service provider and pass values to your implementation.

### Pattern: read config in `register()` or `boot()`

```java
public final class MyServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        int poolSize = app.config().getInt("mymodule.pool_size", 10);
        String endpoint = app.config().getString("mymodule.endpoint", "http://localhost");

        app.singleton(MyService.class, () -> new MyService(poolSize, endpoint));
    }
}
```

### Expected YAML config shape

Your module's docs should specify the config keys it expects:

```yaml
# config/my_module.yaml (loaded automatically by YamlConfig)
my_module:
  endpoint: ${MY_ENDPOINT:http://localhost:8080}
  pool_size: 10
  timeout_seconds: 30
  features:
    - caching
    - compression
```

### Getting config values in `boot()`

```java
@Override
public void boot(Application app) {
    int maxBytes = app.config().getInt("security.max_request_bytes", 1_048_576);
    app.router().middleware(new RequestSizeLimitMiddleware(maxBytes));
}
```

---

## Adding Tests

Test files go in `src/test/java/io/javelin/<name>/`. JUnit 5 is available via the parent POM.

```java
package io.javelin.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryCacheTest {
    @Test
    void putAndGet() {
        var cache = new InMemoryCache();
        cache.put("key", "value", java.time.Duration.ofMinutes(5));
        assertEquals("value", cache.get("key").orElseThrow());
    }

    @Test
    void expireRemovesEntry() throws InterruptedException {
        var cache = new InMemoryCache();
        cache.put("key", "value", java.time.Duration.ofMillis(10));
        Thread.sleep(20);
        assertTrue(cache.get("key").isEmpty());
    }

    @Test
    void forgetRemovesEntry() {
        var cache = new InMemoryCache();
        cache.put("key", "value", java.time.Duration.ofMinutes(5));
        cache.forget("key");
        assertTrue(cache.get("key").isEmpty());
    }
}
```

### Running tests

```bash
# Test a single module
mvn -pl modules/javelin-example test

# Test all modules
mvn test
```

### Testing service providers

For integration-style tests that verify provider wiring:

```java
class MyServiceProviderTest {
    @Test
    void registersService() {
        Application app = new Application(
            YamlConfig.load(Path.of("src/test/resources/config.yaml"), env),
            env
        );
        app.register(new MyServiceProvider());
        app.boot();

        assertTrue(app.has(MyService.class));
        assertNotNull(app.make(MyService.class));
    }
}
```

---

## Integrating with Starter

To make your module available in the default starter stack, register it in `Javelin.create()`.

### Adding to the hardcoded provider list

Edit `modules/javelin-starter/src/main/java/io/javelin/starter/Javelin.java`:

```java
public static Application create(Path root) {
    // ... existing providers ...
    app.register(new MyServiceProvider());    // ← add your provider here
    new ProviderLoader().load(app);
    return app;
}
```

This requires a new dependency in `javelin-starter/pom.xml`:

```xml
<dependency>
    <groupId>io.javelin</groupId>
    <artifactId>javelin-example</artifactId>
    <version>${project.version}</version>
</dependency>
```

### Provider registration order

If your module's `boot()` depends on another module's service, register it after that module. For example, `SecurityServiceProvider.boot()` adds middleware to the router, so it must be registered after `JdkHttpServiceProvider` (which registers `HttpKernel`), but before `ConsoleServiceProvider`.

---

## Config-List Provider Loading

For app-specific providers that are not part of the default stack, list them in `config/app.yaml`:

```yaml
# config/app.yaml
providers:
  - app.MyAppServiceProvider
  - com.example.CustomServiceProvider
```

These are loaded by `ProviderLoader` via `Class.forName` and `app.register()`:

```java
// ProviderLoader.load()
for (String className : app.config().getStringList("providers")) {
    app.register(instantiate(className));
}
```

This avoids the need to hardcode every provider in `Javelin.java`. Use this pattern for:
- Application-specific providers in workspace projects
- Optional third-party modules
- Environment-specific wiring

---

## Pattern Reference

### Module type matrix

| Module Type | Files Needed | Registers | Example |
|---|---|---|---|
| Core interface implementation | Implementation + ServiceProvider | The implementation class | `javelin-cache` |
| New interface + implementation | Interface + Implementation + ServiceProvider | The implementation behind the interface | Custom module |
| Middleware only | Middleware class + ServiceProvider | Middleware on router during `boot()` | `javelin-security` middleware |
| CLI commands | Command class + optional ServiceProvider | `ConsoleKernel` singleton | `javelin-console` |
| Config/environment | Implementation (of Config/Env) + ServiceProvider | Config and Env instances | `javelin-config` |
| Bridge/adapter | Adapter + ServiceProvider | The adapted interface | `javelin-log-slf4j`, `javelin-http-jdk` |

### Standard service provider template

```java
package io.javelin.mymodule;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

public final class MyModuleServiceProvider implements ServiceProvider {

    @Override
    public void register(Application app) {
        // 1. Read config
        String value = app.config().getString("mymodule.key", "default");

        // 2. Bind services
        app.singleton(MyService.class, () -> new MyService(value));
        app.bind(MyInterface.class, MyImplementation.class);
    }

    @Override
    public void boot(Application app) {
        // 3. Post-registration wiring
        MyService service = app.make(MyService.class);
        service.initialize();

        // 4. Add middleware if applicable
        app.router().middleware(new MyMiddleware());
    }
}
```

### Checklist for new modules

- [ ] `pom.xml` uses `javelin-parent` with correct `<relativePath>`
- [ ] Module depends on `javelin-core` (and any other javelin modules needed)
- [ ] Module is listed in root `pom.xml` `<modules>`
- [ ] `ServiceProvider` implementation exists with clear `register()` and `boot()`
- [ ] Config keys are documented (if the module reads config)
- [ ] Tests exist for the public behavior
- [ ] Factory methods use `app.config()` rather than hardcoded values
- [ ] Implementation classes are `final` unless designed for extension
- [ ] Interface types are used for binding (not concrete classes)

---

## Full Worked Example

Let's build a complete `javelin-metrics` module from scratch.

### 1. Create the directory structure

```
modules/javelin-metrics/
  pom.xml
  src/main/java/io/javelin/metrics/
    MetricCollector.java
    SimpleMetricCollector.java
    MetricsServiceProvider.java
  src/test/java/io/javelin/metrics/
    SimpleMetricCollectorTest.java
```

### 2. pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>io.javelin</groupId>
        <artifactId>javelin-parent</artifactId>
        <version>0.1.0-SNAPSHOT</version>
        <relativePath>../../pom.xml</relativePath>
    </parent>
    <artifactId>javelin-metrics</artifactId>
    <dependencies>
        <dependency>
            <groupId>io.javelin</groupId>
            <artifactId>javelin-core</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

### 3. Interface

```java
// MetricCollector.java
package io.javelin.metrics;

public interface MetricCollector {
    void increment(String name);
    void record(String name, long value);
    long count(String name);
}
```

### 4. Implementation

```java
// SimpleMetricCollector.java
package io.javelin.metrics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class SimpleMetricCollector implements MetricCollector {
    private final Map<String, AtomicLong> counters = new ConcurrentHashMap<>();

    @Override
    public void increment(String name) {
        counters.computeIfAbsent(name, k -> new AtomicLong()).incrementAndGet();
    }

    @Override
    public void record(String name, long value) {
        counters.computeIfAbsent(name, k -> new AtomicLong()).addAndGet(value);
    }

    @Override
    public long count(String name) {
        return counters.getOrDefault(name, new AtomicLong()).get();
    }
}
```

### 5. Service provider

```java
// MetricsServiceProvider.java
package io.javelin.metrics;

import io.javelin.core.Application;
import io.javelin.core.ServiceProvider;

public final class MetricsServiceProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        app.singleton(MetricCollector.class, SimpleMetricCollector::new);
    }

    @Override
    public void boot(Application app) {
        app.router().middleware(new MetricsMiddleware(app.make(MetricCollector.class)));
    }
}
```

### 6. Optional middleware

```java
// MetricsMiddleware.java
package io.javelin.metrics;

import io.javelin.core.Middleware;
import io.javelin.core.Request;
import io.javelin.core.Response;

final class MetricsMiddleware implements Middleware {
    private final MetricCollector collector;

    MetricsMiddleware(MetricCollector collector) {
        this.collector = collector;
    }

    @Override
    public Response handle(Request request, Next next) throws Exception {
        collector.increment("request." + request.method().name().toLowerCase());
        long start = System.nanoTime();
        try {
            Response response = next.handle(request);
            collector.increment("response." + response.status());
            return response;
        } catch (Exception e) {
            collector.increment("response.500");
            throw e;
        } finally {
            collector.record("request.duration_ns", System.nanoTime() - start);
        }
    }
}
```

### 7. Tests

```java
// SimpleMetricCollectorTest.java
package io.javelin.metrics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SimpleMetricCollectorTest {
    @Test
    void incrementAndCount() {
        SimpleMetricCollector c = new SimpleMetricCollector();
        c.increment("hits");
        c.increment("hits");
        assertEquals(2, c.count("hits"));
    }

    @Test
    void unknownMetricReturnsZero() {
        SimpleMetricCollector c = new SimpleMetricCollector();
        assertEquals(0, c.count("nonexistent"));
    }

    @Test
    void recordAddsValue() {
        SimpleMetricCollector c = new SimpleMetricCollector();
        c.record("total", 100);
        c.record("total", 50);
        assertEquals(150, c.count("total"));
    }
}
```

### 8. Add to parent POM

```xml
<module>modules/javelin-metrics</module>
```

### 9. Usage

```java
// In an application:
Application app = Javelin.create(root);
app.register(new MetricsServiceProvider());
app.boot();

MetricCollector metrics = app.make(MetricCollector.class);
metrics.increment("user.login");
long logins = metrics.count("user.login");
```

The module is now a first-class Javelin module with the same structure, conventions, and integration patterns as every other module in the framework.
