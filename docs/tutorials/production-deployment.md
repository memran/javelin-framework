# Production deployment

This tutorial uses the generated app layout and the runnable `demo-app`.

## 1. Observe the application

Generated apps enable the observability endpoints by default:

```bash
curl http://127.0.0.1:8080/health
curl http://127.0.0.1:8080/ready
curl -H 'Accept: application/json' http://127.0.0.1:8080/metrics
```

`/metrics` includes counters, JVM memory/threads, virtual-thread scheduler parallelism, startup milestones and HikariCP pool usage. Add your own checks with `Observability.health().ready(...)`.

## 2. Validate configuration

```bash
javelin deploy:validate
```

Fix `ERROR` findings before deploying; `WARN` findings are recommendations for multi-instance setups. `javelin doctor` reports the same issues alongside basic file checks.

## 3. Package and run

```bash
javelin build
java -jar target/app.jar serve
```

The generated Dockerfile builds assets, packages the jar and runs it on a JRE 25 runtime. Compose provides PostgreSQL, Valkey and NATS so the app itself stays stateless.

## 4. Linux service

```bash
javelin deploy:service --app billing --user javelin
sudo cp deploy/billing.service /etc/systemd/system/
sudo systemctl enable --now billing
```

## 5. Benchmark before release

```bash
javelin bench --url http://127.0.0.1:8080/health --concurrency 20 --seconds 10
```

Record hardware and JVM flags alongside results; see `docs/deployment.md` for earlier measured numbers and caveats.

## 6. CI/CD and native images

The repository's GitHub Actions workflows verify every push, build the demo jar and publish release artifacts on `v*` tags. For GraalVM:

```bash
javelin deploy:native-config
javelin native
```

The generated metadata is a starter: it covers Javelin's provider loader, `Application` and common resource paths. Add `reflect-config.json` / `resource-config.json` entries for application-specific providers, reflected DTOs and templates before building a native binary.
