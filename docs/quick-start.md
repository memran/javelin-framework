# Quick Start

This gets you from zero to a running Javelin app in a few minutes. For detailed setup and troubleshooting, see the [Installation Guide](installation.md).

## 1. Prerequisites

- **Java 25** (`java -version`, with `JAVA_HOME` set)
- **Maven 3.9+** (`mvn -v`)
- Optional: **npm** or **bun** for frontend assets, **Docker** for the full Compose stack

## 2. Install the CLI

From this repository root:

```bash
# macOS / Linux
sh ./install.sh
```

```powershell
# Windows PowerShell
powershell -ExecutionPolicy Bypass -File .\install.ps1
```

Open a new terminal and verify:

```bash
javelin --help
```

## 3. Create an app

```bash
javelin new my-app
cd my-app
```

This generates a flat Maven workspace — no `src/` tree:

```
my-app/
  Main.java  pom.xml  .env  README.md
  build.sh  build.ps1  install.sh  install.ps1
  package.json  Dockerfile  docker-compose.yml
  app/controllers/  app/providers/  app/validation/  app/views/
  config/app.yaml  config/view.yml
  resources/views/welcome.peb
  resources/assets/app.css  resources/assets/app.js
  routes/web.java  scripts/dev.mjs
  public/  database/migrations/  storage/  tests/  modules/
```

Useful flags: `--validation-rule TeenAge` changes the generated starter rule, and `--force` allows writing into an existing directory.

## 4. Build the frontend assets

The welcome page uses the locally built Tailwind CSS and Alpine.js bundle — there is no CDN reference.

```bash
bun install && bun run build
# or
npm install && npm run build
```

Equivalently, `./build.sh` (or `.\build.ps1`) auto-detects bun, then npm. Output lands in `public/css/app.css` and `public/js/app.js`.

For watch mode: `npm run dev`, or `bun run dev:bun`.

## 5. Verify the workspace

```bash
./install.sh          # macOS / Linux
.\install.ps1         # Windows
```

The workspace installer checks Java and Maven, then runs `mvn test`. Pass `--skip-build` / `-SkipBuild` to skip the test run.

> The generated POM references `io.javelin:javelin-starter:0.1.0-SNAPSHOT`. If that artifact is not in your local Maven repository, run `mvn install` once from this framework repository root first (see [Installation](installation.md#3-make-the-framework-artifacts-available)).

## 6. Run the app

```bash
javelin serve
```

Open <http://127.0.0.1:8080>. You should see the welcome page with working Tailwind styling and an Alpine.js click counter. `GET /health` returns `{"status":"ok"}`.

Press Ctrl+C to stop; the runtime shutdown hook now closes the HTTP server, cache and queue clients.

## 7. Add a route

Edit `routes/web.java`:

```java
import io.javelin.core.Response;
import static io.javelin.core.Routes.add;
import static io.javelin.core.Routes.get;

add(app.router(), get("/hello/{name:[A-Za-z]+}", request ->
        Response.text("Hello, " + request.param("name").orElseThrow())));
```

The `{name:[A-Za-z]+}` syntax is an inline constraint — `/hello/Ada` matches, `/hello/123` returns 404. Revalidate parameters before database or filesystem use.

Other useful commands: `javelin routes:list`, `javelin make:controller User`, `javelin make:migration create_users_table`, `javelin migrate`, `javelin doctor`. See the [CLI Reference](cli-reference.md).

## 8. Configure the app

- `.env` holds local secrets and overrides. `SERVER_PORT`, `APP_ENV`, `DATABASE_HOST`, `CACHE_DRIVER` and `QUEUE_DRIVER` are read from here or the process environment.
- `config/app.yaml` holds the rest. Defaults: `server.host=127.0.0.1`, `server.port=8080`, `javelin.starter.http-driver=netty`.
- `APP_ENV=prod` (or `production`) enables production behavior; anything else is dev. See [Application Profile](modules/profile.md).

Note that a `.env` value overrides a process environment variable of the same name.

## 9. Frontend assets

| File | Purpose |
|---|---|
| `resources/assets/app.css` | Tailwind v4 entry, `@source` scan rules, custom CSS |
| `resources/assets/app.js` | Alpine.js bootstrap, bundled by esbuild |
| `public/css/app.css` | Built stylesheet served by Javelin |
| `public/js/app.js` | Built JavaScript served by Javelin |

Edit the sources, rebuild with `build.sh`, and refresh. The welcome page links to `/css/app.css` and `/js/app.js`.

## 10. Full stack with Docker (optional)

The generated `docker-compose.yml` starts the app, PostgreSQL, Valkey and NATS with JetStream. The Compose environment selects the `valkey` cache and `nats` queue providers.

For the generated app's own Compose file, run from the app directory:

```bash
docker compose up --build
```

Because `0.1.0-SNAPSHOT` is not published, this requires framework artifacts reachable from the image's Maven repository. To try the stack against this repository's source instead, build the demo-app stack from the repo root:

```bash
docker compose -f demo-app/docker-compose.yml up --build
```

See [Deployment](deployment.md) for details, including the demo endpoints that let you exercise cache and queue behavior.

## Next reading

- [Installation Guide](installation.md) — prerequisites, CLI install, local artifact install, troubleshooting
- [CLI Reference](cli-reference.md) — every command and its output
- [Core](modules/core.md) — container, providers, routing, request/response
- [Cache Valkey](modules/cache-valkey.md) and [Queue NATS](modules/queue-nats.md) — backend adapters
