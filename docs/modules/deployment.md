# Deployment Module

Artifact: `io.javelin:javelin-deployment`

`javelin-deployment` adds production config validation, Linux service packaging, GraalVM metadata generation and a repeatable load test.

## Production validation

```bash
javelin deploy:validate
```

`ProductionValidator` reports explicit issues, for example:

- missing application name,
- unknown queue driver,
- wildcard `security.trusted_proxies`,
- `mail.driver: test` or placeholder JWT secrets in production,
- CORS wildcard combined with credentials,
- in-memory scheduler history/lock in production.

`javelin doctor` includes the same validation when run with an application context.

## Linux service

```bash
javelin deploy:service --app billing --user javelin
```

Generates `deploy/billing.service` with `WorkingDirectory`, `EnvironmentFile`, `ExecStart=java -jar ... serve`, restart policy and basic hardening (`NoNewPrivileges`, `PrivateTmp`).

## GraalVM metadata

```bash
javelin deploy:native-config
```

Generates starter `reflect-config.json` and `resource-config.json` under `resources/META-INF/native-image/javelin/`. Combine with `javelin native` (which runs `mvn -Pnative native:compile`).

## Benchmarks

```bash
javelin bench --url http://127.0.0.1:8080/health --concurrency 20 --seconds 10
```

`BenchmarkRunner` uses JDK HttpClient on virtual threads and reports total requests, successes/failures, requests per second and p50/p95 latency. It is intentionally small and repeatable rather than a full load-testing product.

## CI/CD

The repository includes:

- `.github/workflows/ci.yml` — JDK 25 setup, Maven cache, `mvn verify`, demo jar build and artifact upload on push/PR,
- `.github/workflows/release.yml` — tag-triggered release that collects all module and demo jars into a GitHub release.

## Stateless deployments

All stateful boundaries are adapters: database, cache, queue, storage and scheduler locks. Run multiple stateless instances behind a proxy, point them at shared PostgreSQL/Valkey/NATS services, and use `scheduler.lock: database` for scheduled work.
