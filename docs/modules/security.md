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

## Notes

- Keep security defaults enabled in starter wiring.
- Use middleware for request protection, not global magic.
- `SecureHeadersMiddleware` and `RequestSizeLimitMiddleware` are applied automatically by the starter.
- `RateLimitMiddleware` must be added explicitly where needed.
