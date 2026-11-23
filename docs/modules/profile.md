# Application Profile

The application profile is an explicit, startup-time setting that changes both framework and HTTP adapter behavior. It is not discovered from the network request — it comes from `.env` or configuration.

## Selecting the profile

```dotenv
# .env
APP_ENV=dev
```

```yaml
# config/app.yaml (fallback when APP_ENV is absent)
app:
  env: local
```

Resolution order in `AppProfile.from(env, config)`:

1. `APP_ENV` environment variable (from `.env` or the process environment)
2. `app.env` config key
3. `local` (the default)

Values `prod` and `production` select `PROD`; every other value (`dev`, `local`, blank, unknown) selects `DEV`. The resolved profile is available as `app.profile()` and is registered in the container as `AppProfile`.

## What changes

| Area | `dev` | `prod` |
|---|---|---|
| Error responses | Detailed HTML with escaped stack trace, or JSON with `message`, `type`, `trace` | Generic 500 page/JSON, no internal details |
| Middleware | `DevProfileMiddleware` adds `X-Javelin-Profile: dev` and `Cache-Control: no-store` | No dev headers |
| Netty event-loop workers | 2 | `max(4, 2 × available processors)` |
| Netty `SO_BACKLOG` | 128 | 1024 |
| Netty `SO_KEEPALIVE` | off | on |
| Netty `TCP_NODELAY` | on | on |

Security middleware (secure headers, request-size limits) remains enabled in both profiles.

Static assets pass through global middleware, so dev `no-store` headers also apply to assets.

## Programmatic use

```java
AppProfile profile = AppProfile.parse("prod");
boolean detailed = profile.isDev();
```

Constructing a `NettyHttpServer` directly defaults to `PROD` for backward compatibility. Pass an `AppProfile` explicitly to the seven-argument constructor to select behavior; the Javelin starter does this automatically from `APP_ENV`.
