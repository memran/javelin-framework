# Installation Guide

This guide covers installing the Javelin CLI, making the framework artifacts available to generated apps, verifying the installation, and fixing common setup problems. For the fastest path to a running app, start with the [Quick Start](quick-start.md).

## 1. Requirements

| Requirement | Version | Used for |
|---|---|---|
| JDK | 25 | Compiling and running the framework/apps (`maven.compiler.release=25`) |
| Maven | 3.9+ | Building framework modules and generated workspaces |
| npm or bun | any recent | Building Tailwind CSS and Alpine.js assets (optional at runtime) |
| Docker with Compose | v2+ | Running the app + PostgreSQL + Valkey + NATS stack (optional) |

Verify:

```bash
java -version
mvn -v
bun --version   # or: npm --version
docker compose version
```

Set `JAVA_HOME` to the JDK 25 installation if `java` resolves to an older JDK.

## 2. Install the `javelin` CLI

The CLI is built from `modules/javelin-console`. The root installers build it, place a launcher in `~/.javelin/bin`, and add that directory to your user PATH.

macOS / Linux:

```bash
sh ./install.sh
```

Windows PowerShell (from the repository root):

```powershell
powershell -ExecutionPolicy Bypass -File .\install.ps1
```

If you already built the CLI jar and only want to recreate the launcher:

```bash
sh ./install.sh --skip-build
```

```powershell
.\install.ps1 -SkipBuild
```

Open a new terminal, then verify:

```bash
javelin --help
```

The launcher points directly at `modules/javelin-console/target/javelin-console-0.1.0-SNAPSHOT-all.jar`, so rebuilding that jar is enough to update the CLI — no reinstall needed.

To rebuild the CLI jar after changing CLI source:

```bash
mvn -pl :javelin-console -am package -DskipTests
```

## 3. Make the framework artifacts available

Generated apps depend on `io.javelin:javelin-starter:0.1.0-SNAPSHOT` and the adapter/cache/queue modules. Version `0.1.0-SNAPSHOT` is not published to Maven Central, so install the framework into your local Maven repository once:

```bash
mvn clean install
```

This builds all modules (including `demo-app`) and installs them under `~/.m2/repository/io/javelin`. Without this step, `mvn test` inside a generated workspace fails with "Could not resolve dependencies".

Run the full test suite at any time:

```bash
mvn verify
```

## 4. Create and verify a workspace

```bash
javelin new my-app
cd my-app
```

The generated workspace includes its own `install.sh` / `install.ps1`. They check for Java 25 and Maven 3.9+, then run `mvn test`:

```bash
./install.sh
```

```powershell
.\install.ps1
```

Skip the build with `--skip-build` / `-SkipBuild`.

Build the frontend assets (npm or bun):

```bash
bun install && bun run build
# or: npm install && npm run build
```

Then serve:

```bash
javelin serve
```

See the [Quick Start](quick-start.md) for the full first-app walkthrough.

## 5. Docker install (optional)

Install Docker Desktop (or Docker Engine + Compose v2), then verify `docker compose version`.

To run the repository's demo stack with Valkey, NATS/JetStream and PostgreSQL:

```bash
docker compose -f demo-app/docker-compose.yml up --build
```

The build context is the repository root; the Dockerfile builds the frontend assets, then the Maven reactor (`-pl demo-app -am`), then packages the runtime image.

Generated apps contain their own standalone `docker-compose.yml`. Those builds resolve Javelin artifacts from Maven, so `0.1.0-SNAPSHOT` must be available in a repository the build can reach. For local development against this checkout, prefer the demo-app stack above.

Stop and clean up:

```bash
docker compose -f demo-app/docker-compose.yml down
```

## 6. Troubleshooting

| Symptom | Cause | Fix |
|---|---|---|
| `javelin` is not recognized | PATH change needs a new shell | Open a new terminal; check `~/.javelin/bin` (Windows: `%USERPROFILE%\.javelin\bin`) is on PATH |
| `Unsupported class file major version` or compiler errors | Wrong JDK | Install JDK 25 and point `JAVA_HOME` at it |
| `Could not resolve io.javelin:javelin-starter:0.1.0-SNAPSHOT` | Framework not installed locally | Run `mvn clean install` from the repo root |
| `javelin new` refuses to write | Directory exists | Choose another name or pass `--force` |
| `mvn clean` fails to delete a jar | A running server holds the file | Stop `javelin serve` / the demo app, then retry |
| CSS loads but has no styling | Tailwind never saw the templates | Ensure the asset build copies/scans `resources/views` and `app/views`; use `build.sh` and rebuild assets |
| `APP_ENV=prod` behaves like dev | BOM or typo in `.env` | Write `.env` as plain UTF-8; values `prod`/`production` select production |
| Port 8080 already in use | Another process owns the port | Change `SERVER_PORT` in `.env` or stop the other process |
| `Connection refused` to Valkey/NATS | Providers active without servers | Start the Compose services, or set `CACHE_DRIVER=memory` / `QUEUE_DRIVER=none` locally |

## 7. Updating

After pulling framework changes:

```bash
mvn clean install     # refresh local artifacts
mvn -pl :javelin-console -am package -DskipTests   # refresh the CLI jar (launcher reuses it)
```

Inside an existing generated workspace, rebuild assets with `./build.sh` and rerun `./install.sh` to verify the workspace still builds.
