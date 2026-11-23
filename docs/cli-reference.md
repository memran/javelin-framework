# CLI Reference

This page documents every `javelin` CLI command with full examples and typical output. All commands run from your Javelin project root.

---

## Getting Started

```bash
# Show available commands
javelin --help

# Show version
javelin version

# Run diagnostics
javelin doctor
```

---

## new — Create a workspace

Scaffolds a complete Javelin project in a new directory.

```bash
javelin new my-blog
```

```
Project created: my-blog
Next: cd my-blog && npm install && npm run build
Or with bun: bun install && bun run build
Then: ./install.sh, javelin serve, or docker compose up --build
```

Every new project includes:

- Java skeleton: `pom.xml`, `Main.java`, `app/`, `routes/`, `config/`, `tests/`, `logback.xml`
- HTTP driver: Netty adapter dependency with `javelin.starter.http-driver: netty` in config
- Welcome page: `resources/views/welcome.peb` rendered by `HomeController`, using the local `/css/app.css` and `/js/app.js` with an Alpine.js interaction
- Frontend sources: `resources/assets/app.css` (Tailwind v4) and `resources/assets/app.js` (Alpine.js)
- Frontend build: `package.json`, `scripts/dev.mjs`, output into `public/css` and `public/js`
- Docker: `docker-compose.yml` (app + PostgreSQL + Valkey cache + NATS/JetStream), `Dockerfile` (asset build → jar → runtime), `.dockerignore`
- Install scripts: `install.sh`, `install.ps1`
- Build scripts: `build.sh`, `build.ps1` with `assets`, `java`, `all` modes; the Dockerfile calls these during `docker compose build`

### Options

| Flag | Description |
|---|---|
| `--force` | Allow writing into an existing directory and overwrite generated files |
| `--validation-rule <Name>` | Generate a specific starter validation rule (default: `AdultAgeRule`) |

```bash
javelin new --force my-blog
javelin new --validation-rule TeenAgeRule teen-app
```

The `package.json` scripts run with either `npm` or `bun`. For watch mode use `npm run dev`, or `bun run dev:bun` when only bun is installed. The Dockerfile builds assets with npm for reproducible images; locally either runtime works.

---

## serve — Production server

Starts the HTTP server for production. Blocks until Ctrl+C.

```bash
javelin serve
```

```
Javelin running at http://127.0.0.1:8080
```

### Config

Reads `server.host` and `server.port` from `config/app.yaml`:

```yaml
server:
  host: 0.0.0.0
  port: 8080
```

```bash
# Or override with system properties
javelin serve -Djavelin.root=/app -Dserver.port=9000
```

---

## dev — Development server

Starts the application in development mode with rebuild-on-change. Watches source files and restarts automatically.

```bash
javelin dev
```

```
[dev] Building project...
[dev] Starting server...
Javelin running at http://127.0.0.1:8080
[dev] Watching for changes...
[dev] Change detected: src/main/java/app/controllers/HomeController.java
[dev] Rebuilding...
[dev] Restarting server...
Javelin running at http://127.0.0.1:8080
```

---

## make:controller — Generate a controller

Scaffolds a route handler class with an example multipart upload method.

```bash
javelin make:controller UserController
```

```
Created: src/main/java/app/controllers/UserController.java
```

Generated file (`src/main/java/app/controllers/UserController.java`):

```java
package app.controllers;

import io.javelin.core.*;

public final class UserController {
    public Response index(Request request) {
        return Response.json(Map.of("users", new String[]{}));
    }

    public Response store(Request request) {
        request.saveFile("avatar", Path.of("storage/uploads"));
        return Response.json(Map.of("created", true), 201);
    }
}
```

### Route registration

After generating, register the controller routes:

```java
import app.controllers.UserController;

UserController users = app.make(UserController.class);
app.router()
    .get("/users", users::index)
    .post("/users", users::store);
```

---

## make:model — Generate a model

Scaffolds a domain model class extending `Model`.

```bash
javelin make:model Product
```

```
Created: src/main/java/app/models/Product.java
```

Generated file (`src/main/java/app/models/Product.java`):

```java
package app.models;

import io.javelin.core.*;

public final class Product extends Model {
    public Product(Database database) {
        super(database);
    }

    public Product(Database database, Long id) {
        super(database, id);
    }

    @Override
    protected List<String> fillable() {
        return List.of("name", "price", "description");
    }

    @Override
    protected Map<String, Class<?>> casts() {
        return Map.of("price", Double.class);
    }
}
```

---

## make:service — Generate a service

Scaffolds a service class for business logic.

```bash
javelin make:service PaymentService
```

```
Created: src/main/java/app/services/PaymentService.java
```

```java
package app.services;

public final class PaymentService {
    public String process(String orderId) {
        return "processed";
    }
}
```

---

## make:provider — Generate a service provider

Scaffolds a service provider for wiring custom services.

```bash
javelin make:provider CacheProvider
```

```
Created: src/main/java/app/providers/CacheProvider.java
```

```java
package app.providers;

import io.javelin.core.*;

public final class CacheProvider implements ServiceProvider {
    @Override
    public void register(Application app) {
        // app.singleton(MyService.class, MyService::new);
    }

    @Override
    public void boot(Application app) {
        // app.router().middleware(new MyMiddleware());
    }
}
```

Register it in `config/app.yaml`:

```yaml
providers:
  - app.providers.CacheProvider
```

---

## make:module — Generate a module

Creates a service-provider stub for a feature module. Workspace projects use a flat layout, so no `src/` tree is generated.

```bash
javelin make:module Payment
```

```
Created: modules/PaymentModule
```

The generated `modules/PaymentModule/PaymentModule.java` declares package `modules.paymentmodule`. Add that class name to `providers:` in `config/app.yaml` to activate it; generated POMs compile `modules/**/*.java`.

---

## make:migration — Generate a migration

Creates a timestamped YAML migration file.

```bash
javelin make:migration create-users-table
```

```
Created: database/migrations/20250101000001-create-users-table.yml
```

```yaml
name: Create users table
up:
  - CREATE TABLE users (
      id BIGSERIAL PRIMARY KEY,
      name VARCHAR(255) NOT NULL
    )
down:
  - DROP TABLE IF EXISTS users
```

### Migration types

```bash
# Table creation scaffold
javelin make:migration --type create-table --table users

# Column addition scaffold
javelin make:migration --type add-column --table users --column avatar_path

# Data seed scaffold
javelin make:migration --type seed --table users
```

**create-table** output (`database/migrations/20250101000002-create-users.yml`):

```yaml
name: Create users table
up:
  - CREATE TABLE users (
      id BIGSERIAL PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      email VARCHAR(255) NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    )
down:
  - DROP TABLE IF EXISTS users
```

**add-column** output:

```yaml
name: Add avatar_path to users
up:
  - ALTER TABLE users ADD COLUMN IF NOT EXISTS avatar_path VARCHAR(500)
down:
  - ALTER TABLE users DROP COLUMN IF EXISTS avatar_path
```

**seed** output:

```yaml
name: Seed users table
up:
  - INSERT INTO users (name, email) VALUES ('Admin', 'admin@example.com')
down:
  - DELETE FROM users
```

---

## migrate — Run pending migrations

Executes all migration files that have not yet been applied.

```bash
javelin migrate
```

```
Migrated: 20250101000001 Create users table
Migrated: 20250101000002 Add avatar_path to users
Migrated: 20250101000003 Seed users table
```

### What happens

1. Reads all `.yml` files from `database/migrations/` sorted by filename.
2. Checks the `javelin_migrations` table for already-applied files.
3. Executes each pending migration's `up` statements in a single transaction per file.
4. Records each applied migration in `javelin_migrations`.
5. Returns the list of applied migration names.

### On first run

The `migrate` command automatically creates the `javelin_migrations` tracking table if it does not exist:

```sql
CREATE TABLE IF NOT EXISTS javelin_migrations (
    id SERIAL PRIMARY KEY,
    migration VARCHAR(255) NOT NULL,
    batch INTEGER NOT NULL,
    executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## migrate:rollback — Roll back migrations

Reverts the last batch of migrations.

```bash
javelin migrate:rollback
```

```
Rolled back: 20250101000003 Seed users table
Rolled back: 20250101000002 Add avatar_path to users
```

Each migration's `down` statements are executed in reverse order. A "batch" is all migrations applied in a single `migrate` run.

---

## routes:list — Display registered routes

Lists all registered routes with their HTTP method, path, and middleware count.

```bash
javelin routes:list
```

```
GET    /                (2 middleware)
GET    /health          (2 middleware)
GET    /users           (2 middleware)
POST   /users           (3 middleware)
POST   /uploads         (1 middleware)
GET    /users/{id}      (2 middleware)
DELETE /users/{id}      (2 middleware)

Total routes: 7
```

The middleware count includes global middleware (e.g., `SecureHeadersMiddleware`, `RequestSizeLimitMiddleware`) plus any route-specific middleware.

---

## seed — Run database seeds

Executes all pending seed-type migrations.

```bash
javelin seed
```

```
Seeded: 20250101000003 Seed users table
```

---

## test — Run tests

Delegates to Maven to run the test suite.

```bash
javelin test
```

```
[INFO] Scanning for projects...
[INFO] Running tests...
[INFO] Tests run: 42, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

```bash
# Run a specific test class
javelin test -Dtest=UserControllerTest
```

---

## build — Package the application

Builds the project into a runnable JAR.

```bash
javelin build
```

```
[INFO] Building jar: target/javelin-app-1.0.0.jar
[INFO] BUILD SUCCESS
```

```bash
# Skip tests for faster builds
javelin build -DskipTests
```

---

## native — Build a GraalVM native image

Builds a native executable using GraalVM `native-image`. Requires GraalVM JDK to be installed.

```bash
javelin native
```

```
[INFO] Building native image...
[INFO] ====================================================================
[INFO] Produced artifacts:
[INFO] target/javelin-app (executable) — 18 MB
[INFO] ====================================================================
[INFO] BUILD SUCCESS
```

Run the native binary directly:

```bash
./target/javelin-app
```

---

## queue:work — Process the queue

Starts a worker that processes queued jobs.

```bash
javelin queue:work
```

```
Queue worker started. Processing jobs...
[2025-01-01T12:00:00] Processed: send-welcome-email-42
[2025-01-01T12:00:01] Processed: generate-report-17
^C
Shutting down queue worker...
```

---

## queue:status — Check queue status

Displays the current state of the queue.

```bash
javelin queue:status
```

```
Queue: default
  Pending: 12
  Processing: 2
  Failed: 1
  Completed: 384
```

---

## schedule:run — Run scheduled tasks

Executes all due scheduled tasks.

```bash
javelin schedule:run
```

```
[2025-01-01T12:00:00] Running: cleanup-expired-sessions
[2025-01-01T12:00:00] Running: send-daily-digest
```

---

## cache:clear — Clear application cache

Invalidates all cached entries.

```bash
javelin cache:clear
```

```
Cache cleared.
```

---

## config:cache — Cache configuration

Compiles configuration into a cached format for faster boot.

```bash
javelin config:cache
```

```
Configuration cached successfully.
```

---

## doctor — System diagnostics

Runs a series of checks on the environment and configuration.

```bash
javelin doctor
```

```
Javelin Doctor
══════════════
✓ Java version: 25
✓ Project root: /home/user/my-blog
✓ config/app.yaml: found
✓ config/database.yaml: found
✓ .env: found
✓ database/migrations/: found
  - 3 migration files
✓ Database connection: OK (PostgreSQL 16)
✓ Required modules: 10/10
⚠ Queue driver not configured

System: OK
```

---

## version — Show version

```bash
javelin version
```

```
Javelin Framework 0.1.0-SNAPSHOT
```

```bash
javelin --version
```

```
0.1.0-SNAPSHOT
```

---

## AI Commands

### ai:install — Configure AI

Creates `.javelin/ai.properties` with default configuration.

```bash
javelin ai:install
```

```
AI configuration created at .javelin/ai.properties
Edit the file to set your API endpoint and key.
```

Generated file (`.javelin/ai.properties`):

```properties
ai.base_url=https://api.openai.com
ai.chat_path=/v1/chat/completions
ai.model=gpt-4
ai.timeout_seconds=60
```

### ai:chat — Chat with AI

Sends a prompt to the configured AI provider.

```bash
javelin ai:chat "generate a UserController with validation"
```

```
AI: Here's a UserController with validation:

public final class UserController {
    public Response store(Request request) {
        Validation.of(request.input())
            .required("name")
            .email("email")
            .minLength("password", 8)
            .validate();

        return Response.json(Map.of("created", true), 201);
    }
}
```

Streaming mode:

```bash
javelin ai:chat --stream "write a poem about Java"
```

```
AI: Java, mighty and evergreen...
     (tokens stream in real-time)
```

### ai:generate — Generate a module

Uses AI to generate a complete module.

```bash
javelin ai:generate "blog module with posts and comments"
```

```
AI generated module: modules/javelin-blog/
  pom.xml
  src/main/java/io/javelin/blog/
    BlogServiceProvider.java
    Post.java
    Comment.java
    BlogController.java
Module created successfully.
```

### ai:explain — Explain code

Sends the current project context to AI for explanation.

```bash
javelin ai:explain "how does routing work"
```

```
AI: Javelin routing works through the Router class. Routes are registered
via app.router().get("/path", handler) and matched in order using regex
compilation. Path parameters like {id} are extracted and available via
request.param("id"). Middleware wraps each route through a recursive
dispatch chain in HttpKernel.
```

---

## System property overrides

These system properties affect CLI behavior:

```bash
# Override project root
javelin serve -Djavelin.root=/var/www/my-app

# Override server port
javelin serve -Dserver.port=9000

# Override config values inline
javelin migrate -Ddatabase.url=jdbc:postgresql://prod-host:5432/myapp
```

## Exit codes

| Code | Meaning |
|---|---|
| `0` | Success |
| `1` | General error |
| `1` | CLI parse error (with fix suggestion) |

## Command summary

| Command | Description |
|---|---|
| `new` | Create a new workspace |
| `serve` | Start production server |
| `dev` | Start dev server with hot reload |
| `routes:list` | List all registered routes |
| `make:controller` | Scaffold a controller |
| `make:service` | Scaffold a service |
| `make:model` | Scaffold a model |
| `make:provider` | Scaffold a service provider |
| `make:module` | Scaffold a module |
| `make:migration` | Scaffold a YAML migration |
| `migrate` | Run pending migrations |
| `migrate:rollback` | Roll back last migration batch |
| `seed` | Run database seeds |
| `test` | Run the test suite |
| `build` | Package the application |
| `native` | Build GraalVM native image |
| `queue:work` | Process the job queue |
| `queue:status` | Show queue status |
| `schedule:run` | Run scheduled tasks |
| `cache:clear` | Clear application cache |
| `config:cache` | Cache configuration |
| `doctor` | System diagnostics |
| `version` | Show version |
| `ai:install` | Configure AI provider |
| `ai:chat` | Chat with AI |
| `ai:generate` | Generate a module via AI |
| `ai:explain` | Explain code via AI |
