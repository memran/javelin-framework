# Deployment

This guide covers running Javelin in production: performance tuning, JVM flags, Docker containerization, reverse proxy setup, and health checks.

---

## Production Performance Tuning

### JVM flags

Start with these flags for a production server:

```bash
java \
  -XX:+UseZGC \
  -XX:ZCollectionInterval=30 \
  -XX:ConcGCThreads=2 \
  -XX:+UseStringDeduplication \
  -XX:+ExitOnOutOfMemoryError \
  -Xms256m -Xmx512m \
  -jar target/javelin-app.jar
```

| Flag | Purpose |
|---|---|
| `-XX:+UseZGC` | Low-pause garbage collector. Scales well from 256 MB to multi-TB heaps. |
| `-XX:ZCollectionInterval=30` | Forces a GC cycle every 30 seconds even if heap is healthy — prevents heap fragmentation buildup. |
| `-XX:ConcGCThreads=2` | Limits GC thread count on constrained hosts. |
| `-XX:+UseStringDeduplication` | Deduplicates identical strings in heap — saves 10-15% on typical web workloads. |
| `-XX:+ExitOnOutOfMemoryError` | Kills the process on OOM so the orchestrator can restart it cleanly. |
| `-Xms256m -Xmx512m` | Heap starts at 256 MB, caps at 512 MB. Javelin's virtual-threaded JDK HTTP server has minimal per-request overhead. |

### Virtual thread tuning

Javelin uses `Executors.newVirtualThreadPerTaskExecutor()` for request handling. The JDK manages virtual thread scheduling — no thread pool to tune.

```bash
# Optional: limit carrier pool parallelism
java -Djdk.virtualThreadScheduler.parallelism=8 -jar app.jar
```

Default parallelism is `Runtime.getRuntime().availableProcessors()`. Set it lower when running alongside other services.

### Connection pool (HikariCP)

Configure in `config/database.yaml`:

```yaml
database:
  connections:
    pgsql:
      max_pool_size: 10
      min_idle: 2
      connection_timeout: 5000
      idle_timeout: 300000
      max_lifetime: 1800000
```

Rules of thumb:
- `max_pool_size = (core_count * 2) + effective_spindle_count` — usually 8–12 is enough for virtual threads since they don't block on JDBC calls.
- `connection_timeout: 5000` — fail fast rather than queue requests.
- `max_lifetime < database's wait_timeout` — typically 30 minutes.

### Request size limit

Set a sane default in `config/app.yaml`:

```yaml
security:
  max_request_bytes: 2097152  # 2 MB — protects against oversized uploads
```

Override per-route for upload endpoints:

```java
import io.javelin.security.RequestSizeLimitMiddleware;

router.post("/uploads", handler)
    .middleware(new RequestSizeLimitMiddleware(50_000_000)); // 50 MB
```

### Rate limiting

Protect endpoints from abuse:

```java
import io.javelin.security.RateLimitMiddleware;

router.group("/api", List.of(
    new RateLimitMiddleware(100, 60_000)  // 100 req/min per IP
), api -> {
    api.get("/users", controller::list);
});
```

### Template caching

In `config/view.yaml`:

```yaml
view:
  cache: true        # enable Pebble template compilation cache
  suffix: ".peb"
  auto_escape: true
```

### Native image (GraalVM)

Build a native executable for sub-second startup and minimal memory:

```bash
javelin native
./target/javelin-app  # native binary, starts in ~20ms
```

Native images use less memory (typically 30-50 MB RSS) and eliminate JIT warmup time.

### JMX monitoring

```bash
java \
  -Dcom.sun.management.jmxremote \
  -Dcom.sun.management.jmxremote.port=9010 \
  -Dcom.sun.management.jmxremote.local.only=true \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.ssl=false \
  -jar target/javelin-app.jar
```

---

## Docker Compose

`javelin new` generates a ready-to-use `docker-compose.yml` and multi-stage `Dockerfile`: the first stage builds Tailwind CSS and Alpine.js assets with npm, the second packages the Java jar, and the runtime stage serves the jar with PostgreSQL. Compose also starts **Valkey** (Redis-compatible cache) and **NATS with JetStream** (durable queue), each with a health check, persistent volume and loopback-only host ports. Run it with `docker compose up --build` and override `DB_PASSWORD` in `.env` if needed.

The generated `config/app.yaml` carries `cache` and `queue` settings (`CACHE_DRIVER`, `VALKEY_URL`, `QUEUE_DRIVER`, `NATS_URL`, `NATS_JETSTREAM`, `NATS_STREAM`) and lists `ValkeyCacheServiceProvider` and `NatsQueueServiceProvider` explicitly. The Compose environment selects `valkey` and `nats`, so the [`javelin-cache-valkey`](modules/cache-valkey.md) and [`javelin-queue-nats`](modules/queue-nats.md) adapters bind automatically in the container. Locally the defaults are `memory` / `none`, so no servers are needed. The queue CLI commands remain placeholders; applications register handlers with `Queue.subscribe(...)`.

The `demo-app` in this repository mirrors that stack for local testing. It has its own `docker-compose.yml`, `Dockerfile`, `package.json` (Tailwind + Alpine), `resources/assets/`, and a non-filtered `docker-app.yaml` for container config. Because `demo-app` is a Maven module, the Compose build context is the repository root:

```bash
docker compose -f demo-app/docker-compose.yml up --build
```

The demo app includes endpoints for exercising the adapters against the Compose services: `GET /cache/set?value=hello`, `GET /cache/get`, and `GET /queue/publish?message=hi` (add `&failFirst=true` to force one failed delivery and verify retries). Its `AppServiceProvider` registers a `demo.message` subscriber that appends each delivered job to `storage/queue-received.txt`. These routes fall back to the in-memory cache and skip the subscriber when the drivers are not selected, so local defaults remain server-free.

Both Dockerfiles call `build.sh` during image build: the asset stage runs `sh build.sh assets`, and the jar stage runs `sh build.sh java`. The asset stage copies `resources/views` and `app/views` as well as `resources/assets` — Tailwind's `@source` rules scan those templates, so omitting them produces a stylesheet with base styles but no utility classes. The same script is used locally, so container and local builds take the same path:

```bash
cd demo-app
./build.sh            # assets + jar (auto-detects bun, else npm)
./build.sh assets     # frontend only
./build.sh java       # Maven package only
```

On Windows use `.\build.ps1` with the same modes. Watch mode is `npm run dev` / `bun run dev:bun`.

### Minimal setup

Suitable for single-host deployments with PostgreSQL:

```yaml
# docker-compose.yml
services:
  app:
    image: javelin-app:latest
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    environment:
      DATABASE_URL: jdbc:postgresql://db:5432/javelin
      DB_USERNAME: javelin
      DB_PASSWORD: ${DB_PASSWORD}
    depends_on:
      db:
        condition: service_healthy
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/health"]
      interval: 15s
      timeout: 5s
      retries: 3
      start_period: 10s
    restart: unless-stopped

  db:
    image: postgres:16-alpine
    volumes:
      - pgdata:/var/lib/postgresql/data
    environment:
      POSTGRES_DB: javelin
      POSTGRES_USER: javelin
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U javelin"]
      interval: 5s
      timeout: 3s
      retries: 5
    restart: unless-stopped

volumes:
  pgdata:
```

### Full stack (with nginx reverse proxy and Redis)

```yaml
# docker-compose.full.yml
services:
  nginx:
    image: nginx:alpine
    ports:
      - "80:80"
      - "443:443"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf:ro
      - ./public:/var/www/public:ro
      - ./ssl:/etc/nginx/ssl:ro
    depends_on:
      - app
    restart: unless-stopped

  app:
    image: javelin-app:latest
    build:
      context: .
      dockerfile: Dockerfile
    expose:
      - "8080"
    environment:
      SERVER_HOST: 0.0.0.0
      SERVER_PORT: 8080
      # Trusted proxies are configured in config/app.yaml under
      # security.trusted_proxies, not through an environment variable.
      DATABASE_URL: jdbc:postgresql://db:5432/javelin
      DB_USERNAME: javelin
      DB_PASSWORD: ${DB_PASSWORD}
      REDIS_URL: redis://redis:6379
    depends_on:
      db:
        condition: service_healthy
      redis:
        condition: service_started
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/health"]
      interval: 15s
      timeout: 5s
      retries: 3
      start_period: 15s
    deploy:
      resources:
        limits:
          cpus: "1.0"
          memory: 512M
    restart: unless-stopped

  db:
    image: postgres:16-alpine
    volumes:
      - pgdata:/var/lib/postgresql/data
    environment:
      POSTGRES_DB: javelin
      POSTGRES_USER: javelin
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U javelin"]
      interval: 5s
      timeout: 3s
      retries: 5
    deploy:
      resources:
        limits:
          cpus: "0.5"
          memory: 256M
    restart: unless-stopped

  redis:
    image: redis:7-alpine
    volumes:
      - redisdata:/data
    healthcheck:
      test: ["CMD", "redis-cli", "ping"]
      interval: 5s
      timeout: 3s
      retries: 5
    deploy:
      resources:
        limits:
          cpus: "0.25"
          memory: 128M
    restart: unless-stopped

volumes:
  pgdata:
  redisdata:
```

Generated projects ship a simpler, working version of this stack plus ready-made proxy files. Both `nginx.conf` and `Caddyfile` forward to `app:8080` and pass `X-Forwarded-*` headers. The proxy services are opt-in Compose profiles:

```bash
docker compose --profile nginx up --build       # app at http://localhost:8081
docker compose --profile caddy up --build       # app at http://localhost:8082
```

For `demo-app` in this repository, use `docker compose -f demo-app/docker-compose.yml --profile nginx up --build`. The app config trusts `127.0.0.1` and Docker's `172.16.0.0/12` bridge network so forwarded headers are honoured only from those peers.

### Timeouts

| Config key | Default | Applied by |
|---|---|---|
| `server.request_timeout_seconds` | `30` | Netty `ReadTimeoutHandler` (408 on read timeout), Undertow `REQUEST_PARSE_TIMEOUT`, JDK watchdog closes the exchange |
| `server.idle_timeout_seconds` | `60` | Netty `IdleStateHandler`, Undertow `IDLE_TIMEOUT`; the JDK `HttpServer` has no native idle timeout |

### Dockerfile

```dockerfile
# Dockerfile — multi-stage build
FROM maven:3.9-eclipse-temurin-25-alpine AS builder
WORKDIR /build
COPY pom.xml .
COPY modules/ modules/
RUN mvn -pl modules/javelin-starter -am package -DskipTests -q

FROM eclipse-temurin:25-jre-alpine
RUN apk add --no-cache curl
WORKDIR /app
COPY --from=builder /build/modules/javelin-starter/target/*.jar app.jar
COPY config/ config/
COPY public/ public/
COPY database/ database/
EXPOSE 8080
HEALTHCHECK --interval=15s --timeout=5s --retries=3 \
  CMD curl -f http://localhost:8080/health || exit 1
ENTRYPOINT ["java", \
  "-XX:+UseZGC", \
  "-XX:+ExitOnOutOfMemoryError", \
  "-Xms256m", "-Xmx512m", \
  "-jar", "app.jar"]
```

### nginx configuration

```nginx
# nginx.conf
upstream javelin {
    server app:8080;
    keepalive 64;
}

server {
    listen 80;
    server_name example.com;
    return 301 https://$server_name$request_uri;
}

server {
    listen 443 ssl http2;
    server_name example.com;

    ssl_certificate     /etc/nginx/ssl/cert.pem;
    ssl_certificate_key /etc/nginx/ssl/key.pem;

    # Static files — served by nginx directly
    location /assets/ {
        root /var/www/public;
        expires 1y;
        add_header Cache-Control "public, immutable";
    }

    location /storage/ {
        root /var/www/public;
        expires 7d;
        add_header Cache-Control "public";
    }

    # Dynamic requests — proxy to Javelin
    location / {
        proxy_pass http://javelin;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header Connection "";
        proxy_read_timeout 60s;
    }
}
```

### .dockerignore

```
.git/
.gitignore
target/
*.md
demo-app/
.m2repo/
```

### Environment file

```bash
# .env.production
DB_PASSWORD=change-me-in-production
JAVA_TOOL_OPTIONS=-XX:+UseZGC -Xms256m -Xmx512m
```

### Running

```bash
# Build and start
docker compose --env-file .env.production up -d

# View logs
docker compose logs -f app

# Run migrations
docker compose exec app java -jar app.jar migrate

# Scale horizontally
docker compose up -d --scale app=3
```

---

## Reverse Proxy

### Why use a reverse proxy

| Reason | Detail |
|---|---|
| SSL termination | Nginx/Caddy handle TLS with better performance and simpler config than JDK HttpServer. |
| Static files | Serve assets directly from nginx without hitting Java. |
| Rate limiting | Nginx `limit_req` is more efficient than per-request middleware. |
| Request buffering | Protects the app from slow clients and large headers. |
| Multiple apps | Host several services on the same port via path-based routing. |

### Trusted proxy configuration

When behind nginx, configure trusted proxies so Javelin resolves the real client IP:

```yaml
# config/app.yaml
security:
  trusted_proxies:
    - 10.0.0.0/8
    - 172.16.0.0/12
    - 192.168.0.0/16
```

This is passed to `JdkHttpServer` via `JdkHttpServiceProvider`. The server only reads `X-Forwarded-For` when the immediate connection comes from a trusted IP.

### Caddy alternative

```caddyfile
# Caddyfile
example.com {
    reverse_proxy app:8080 {
        header_up X-Real-IP {remote_host}
        header_up X-Forwarded-For {remote_host}
    }

    root * /var/www/public
    @static {
        path /assets/* /storage/*
    }
    handle @static {
        file_server
        header Cache-Control "public, max-age=31536000"
    }
}
```

---

## Migration Strategy

Run migrations as part of the deploy process, before the new container starts serving traffic:

```bash
# Deploy script
docker compose run --rm app java -jar app.jar migrate
docker compose up -d app
```

Or as a Kubernetes init container:

```yaml
initContainers:
  - name: migrations
    image: javelin-app:latest
    command: ["java", "-jar", "app.jar", "migrate"]
```

---

## Health Checks

Register a health check endpoint:

```java
app.router().get("/health", request ->
    Response.json(Map.of(
        "status", "ok",
        "app", app.config().getString("app.name", "javelin").orElse("unknown")
    ))
);
```

Add a readiness check that validates database connectivity:

```java
app.router().get("/ready", request -> {
    try {
        app.make(Database.class).execute("SELECT 1");
        return Response.json(Map.of("status", "ready"));
    } catch (Exception e) {
        return Response.json(Map.of("status", "not ready", "reason", e.getMessage()), 503);
    }
});
```

---

## Graceful Shutdown

Javelin's `JdkHttpServer.stop(delaySeconds)` accepts a grace period. The `serve` command registers a shutdown hook:

```java
Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(1)));
```

In Docker, set the stop grace period:

```yaml
services:
  app:
    stop_grace_period: 10s
```

---

## Performance Benchmarks

Measured on the development machine: Windows 11, 12 logical CPUs, Temurin JDK 25 with default JVM flags, demo app started with `serve`, application logging suppressed. Client and server ran on the same host using `hey` v0.1.5 with 50 keep-alive connections. Each run lasted 10 seconds and returned only 200s.

| Driver | `GET /health` (JSON, 15 bytes) | `GET /` (HTML, ~4.9 KB) |
|---|---|---|
| `jdk` | ~20,600 req/s | ~8,900 req/s |
| `netty` | ~21,400 req/s | ~11,900 req/s |
| `undertow` | ~25,000 req/s | ~9,300 req/s |

Undertow was fastest for the tiny JSON response; Netty was fastest for the larger HTML response. Differences below roughly 15–20% should be treated as noise on this machine — repeat runs of the same driver varied by that much. Logging level matters too: DEBUG logging of template lookups reduced throughput measurably, so suppress application logging when benchmarking.

These are local, client-inclusive numbers on a single machine. They are not a server ceiling, and they vary with hardware, OS networking, JVM flags, and the client tool. Earlier revisions of this document listed ~28,000–30,000 req/s figures that were estimates rather than measurements; those were removed.

To reproduce:

```bash
# Terminal 1: pick a driver in config/app.yaml
# (javelin.starter.http-driver: netty | jdk | undertow; netty is the default).
# Suppress application DEBUG logging for a clean measurement.
java -jar demo-app/target/javelin-demo-app-0.1.0-SNAPSHOT.jar serve

# Terminal 2:
hey -z 10s -c 50 http://127.0.0.1:8080/health
hey -z 10s -c 50 http://127.0.0.1:8080/
```

Record the hardware, JVM flags, middleware set, and client tool alongside any numbers you publish.
