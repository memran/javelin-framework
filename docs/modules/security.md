# Security Module

Artifact: `io.javelin:javelin-security`

`javelin-security` provides explicit security middleware and password hashing helpers.

## PasswordHasher

`PasswordHasher` uses PBKDF2WithHmacSHA256 with 210,000 iterations for password hashing.

```java
PasswordHasher hasher = new PasswordHasher();

// Hash a password for storage
String hash = hasher.hash("user-secret-123");
// $Pbkdf2-Sha256$210000$...

// Verify against stored hash
boolean correct = hasher.verify("user-secret-123", hash);  // true
boolean wrong = hasher.verify("wrong-password", hash);      // false
```

- `hasher.hash(String password)` -- creates a PBKDF2 hash with a random salt. The output string encodes all parameters (algorithm, iterations, salt, hash) for storage in a single column.
- `hasher.verify(String password, String hash)` -- checks a plaintext password against a stored hash. Returns `true` if the password matches.

```java
// Typical usage in authentication:
PasswordHasher hasher = app.make(PasswordHasher.class);

// During registration:
String storedHash = hasher.hash(request.input().text("password").orElseThrow());

// During login:
if (hasher.verify(inputPassword, storedHash)) {
    // password matches
} else {
    // authentication failed
}
```

## SecureHeadersMiddleware

`SecureHeadersMiddleware` adds safe default HTTP security headers to every response.

```java
SecureHeadersMiddleware middleware = new SecureHeadersMiddleware();
```

Applied headers:
- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `Referrer-Policy: strict-origin-when-cross-origin`
- `Permissions-Policy: geolocation=(), camera=(), microphone=()`

Registered automatically by `SecurityServiceProvider` on all routes.

## RequestSizeLimitMiddleware

`RequestSizeLimitMiddleware` rejects requests whose body exceeds the configured size limit.

```java
// Limit to 1 MB
RequestSizeLimitMiddleware middleware = new RequestSizeLimitMiddleware(1_048_576);
```

- `new RequestSizeLimitMiddleware(int maxBytes)` -- creates a middleware that returns a 413 (Payload Too Large) response if `request.body().length > maxBytes`.

```java
// Register on specific routes:
router.post("/upload", uploadHandler).middleware(new RequestSizeLimitMiddleware(10_485_760));

// Or globally:
router.middleware(new RequestSizeLimitMiddleware(2_097_152));
```

## RateLimitMiddleware

`RateLimitMiddleware` limits repeated requests from the same client IP.

```java
// Allow 100 requests per 60 seconds per client IP
RateLimitMiddleware middleware = new RateLimitMiddleware(100, 60_000);
```

- `new RateLimitMiddleware(int maxRequests, long windowMillis)` -- creates a middleware that returns a 429 (Too Many Requests) response when the client exceeds the limit within the time window. Tracks clients by `request.remoteAddress()`.

```java
// Apply to an API group:
router.group("/api", List.of(new RateLimitMiddleware(60, 60_000)), api -> {
    api.get("/users", userController::index);
    api.post("/users", userController::store);
});

// Apply to a specific route:
router.get("/search", searchHandler)
    .middleware(new RateLimitMiddleware(30, 60_000));
```

## SecurityServiceProvider

`SecurityServiceProvider` wires security defaults into the application.

```java
SecurityServiceProvider provider = new SecurityServiceProvider();
provider.register(app);
provider.boot(app);
```

- `provider.register(app)` -- registers `PasswordHasher` as a singleton in the container.
- `provider.boot(app)` -- reads `security.max_request_bytes` from config and adds `SecureHeadersMiddleware` and `RequestSizeLimitMiddleware` to the router.

```java
// Config keys used:
// security.max_request_body: 2097152 (2 MB default)
// security.trusted_proxies: (list of trusted proxy IPs)
```

```java
// After security provider is booted:
PasswordHasher hasher = app.make(PasswordHasher.class);
```

## Trusted proxy configuration

To resolve `Request.remoteAddress()` correctly behind a reverse proxy, configure trusted proxies:

```yaml
# config/app.yaml
security:
  trusted_proxies:
    - 127.0.0.1
    - 10.0.0.0/8
```

This is read by `JdkHttpServiceProvider` and passed to the configured HTTP adapter (Netty, JDK or Undertow). Client IP resolution from `X-Forwarded-For` or `Forwarded` headers only happens when the immediate connection is from a trusted IP or CIDR range. Wildcard trust (`"*"`) is rejected; see [HTTP Common](http-common.md) for the chain-parsing rules.

## Bearer authentication

Bearer-token and permission middleware are part of `javelin-core` (package `io.javelin.core.security`), not this module. They are intentionally small: you supply a `BearerTokenResolver` function and the middleware produces 401/403 responses. See [Core — Bearer authentication and authorization](core.md#bearer-authentication-and-authorization) for the full example.

## Cookie-backed sessions

`SessionServiceProvider` is opt-in; add it to `config.providers`:

```yaml
providers:
  - io.javelin.security.SessionServiceProvider

session:
  cookie_name: javelin_session
  ttl_seconds: 1800
  secure: true
  http_only: true
  same_site: Lax
```

It binds a `SessionStore` and registers `SessionMiddleware`, which attaches a `Session` to each request and returns the session cookie on the response.

```java
Session session = request.session().orElseThrow();
session.put("user_id", 42);
session.save();                 // optional: middleware also saves after the handler
session.invalidate();            // middleware sends an expired cookie
```

Core also provides the `Cookie` value type and response helpers:

```java
return Response.text("ok").withCookie(Cookie.session("pref", "dark"));
return Response.redirect("/login").withoutCookie("javelin_session");
String theme = request.cookie("pref").orElse("light");
```

`InMemorySessionStore` is process-local and intended for single-instance deployments. Register a different `SessionStore` provider for shared sessions across instances. Cookie defaults are `Secure`, `HttpOnly`, `Path=/`, `SameSite=Lax`; keep `secure: true` behind HTTPS. Multiple `Set-Cookie` values are joined with `", "` in the single header supported by the response model.

## CORS

`CorsServiceProvider` enables configurable CORS. With no `cors.allowed_origins`, its middleware does nothing.

```yaml
cors:
  allowed_origins:
    - https://app.example.com
  allow_credentials: false
  max_age_seconds: 600
```

`CorsMiddleware` handles preflight (`OPTIONS` with `Access-Control-Request-Method`) and returns 204 with `Allow-Origin`, `Allow-Methods`, `Allow-Headers`, `Max-Age` and `Vary: Origin`. Unknown origins are rejected on preflight with 403 and receive no CORS headers on actual requests. `*` must be listed explicitly; combine it with `allow_credentials: false` (the middleware echoes the concrete origin when credentials are enabled). The kernel's automatic OPTIONS response now passes through global middleware, so preflight works on routes that only define GET.

## CSRF

`CsrfServiceProvider` adds the synchronizer-token pattern for browser sessions. Register it after `SessionServiceProvider`.

```yaml
providers:
  - io.javelin.security.SessionServiceProvider
  - io.javelin.security.CsrfServiceProvider
```

Safe methods (`GET`, `HEAD`, `OPTIONS`) create a token in the session and return it as `X-CSRF-Token`. Unsafe methods must supply the same token in `X-CSRF-Token` or the `_token` form field; mismatches return 403. Comparison uses `MessageDigest.isEqual`. Requests with a Bearer token are skipped, so API clients are not forced through browser CSRF.

## Session authentication

`SessionAuthServiceProvider` resolves a principal from `javelin.user` in the session and exposes it under the same attribute used by `RequiresAuthenticationMiddleware` and `RequiresPermissionMiddleware`.

```java
session.put("javelin.user", new Authenticated("alice", Set.of("users.read")));
// or simply: session.put("javelin.user", "alice");  // no permissions
```

Register it after `SessionServiceProvider`; it is a no-op when there is no session or no principal.

## JWT adapter

`JwtTokenResolver` verifies HS256 JWTs with JDK crypto and maps `sub` and `permissions`/`scope` claims to `Authenticated`.

```yaml
security:
  jwt:
    secret: ${APP_KEY:}       # falls back to the APP_KEY environment value
    issuer: javelin
    audience: my-api
```

`JwtAuthServiceProvider` binds a `BearerTokenResolver`; combine it with core's `BearerAuthMiddleware` on protected routes:

```java
app.router().get("/me", user::show)
        .middleware(new BearerAuthMiddleware(app.make(BearerTokenResolver.class)));
```

Tokens with an invalid signature, unsupported `alg`, expired `exp`, future `nbf` or mismatched issuer/audience are rejected.

## Password hashing

`PasswordHasher` delegates to a `PasswordHashStrategy`. The default is PBKDF2 (`210,000` iterations, unchanged legacy format); BCrypt is available:

```yaml
security:
  password:
    hash: bcrypt      # or pbkdf2
    bcrypt_cost: 12
```

`verify(...)` also accepts legacy PBKDF2 and BCrypt hashes regardless of the active strategy, so changing strategies does not lock out existing users.

## HSTS and CSP

`SecureHeadersMiddleware` always sets the four baseline headers. HSTS and CSP are opt-in:

```yaml
security:
  hsts:
    enabled: true
    max_age_seconds: 31536000
  csp: "default-src 'self'"
```

HSTS is only emitted for HTTPS requests (or when no `X-Forwarded-Proto` is present).

## Notes

- Keep security defaults enabled in starter wiring.
- Use middleware for request protection, not global magic.
- `SecureHeadersMiddleware` and `RequestSizeLimitMiddleware` are applied automatically by the starter.
- `RateLimitMiddleware`, CORS, CSRF, session auth and JWT are explicit providers — add only what your app needs.
- Secrets should come from `SecretProvider` (environment first, then files); see [Config](config.md#secret-providers).
