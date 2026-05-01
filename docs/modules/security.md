# Security Module

Artifact: `io.javelin:javelin-security`

`javelin-security` provides explicit security middleware and password hashing helpers.

## Public API

- `SecurityServiceProvider`
- `SecureHeadersMiddleware`
- `RequestSizeLimitMiddleware`
- `RateLimitMiddleware`
- `PasswordHasher`

## Example

```java
PasswordHasher hasher = new PasswordHasher();
String hash = hasher.hash("secret");
boolean ok = hasher.verify("secret", hash);
```

## Function Usage

- `PasswordHasher.hash(raw)` creates a password hash for storage.
- `PasswordHasher.verify(raw, hash)` checks whether a password matches a stored hash.
- `SecureHeadersMiddleware` adds safe default HTTP headers.
- `RequestSizeLimitMiddleware` rejects oversized requests before they reach handlers.
- `RateLimitMiddleware` limits repeated requests from the same client.
- `SecurityServiceProvider` wires the security middleware into starter defaults.
- Configure `security.trusted_proxies` to let the HTTP adapter resolve `Request.remoteAddress()` from `X-Forwarded-For` or `Forwarded` headers only when the immediate peer is trusted.

## Notes

- Keep security defaults enabled in starter wiring.
- Use middleware for request protection, not global magic.
