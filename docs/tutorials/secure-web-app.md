# Build a secure web app and API

This tutorial walks through one app that serves both browser pages and a REST API. It uses the runnable `demo-app` in this repository; every endpoint below already exists in `demo-app/app/controllers/SecurityExampleController.java` and `demo-app/routes/web.java`.

You will use:

- cookie-backed sessions with `Secure`, `HttpOnly`, `SameSite=Lax` cookies,
- CSRF protection for browser form posts,
- validation with 422 responses for HTML and JSON clients,
- HS256 JWT tokens for API clients,
- configurable CORS,
- secret lookup from the environment or files.

Demo credentials: **alice / secret**.

## 1. Enable the providers

Providers are explicit. The relevant part of `config/app.yaml` is:

```yaml
session:
  cookie_name: javelin_session
  ttl_seconds: 1800
  secure: true
  http_only: true
  same_site: Lax

security:
  password:
    hash: pbkdf2       # or bcrypt
    bcrypt_cost: 12
  jwt:
    secret: ${APP_KEY:change-me}
    issuer: ""
    audience: ""
    ttl_seconds: 3600

cors:
  allowed_origins:
    - http://localhost:5173
  allow_credentials: false
  max_age_seconds: 600

secrets:
  dir: secrets

providers:
  - io.javelin.security.SessionServiceProvider
  - io.javelin.security.CsrfServiceProvider
  - io.javelin.security.SessionAuthServiceProvider
  - io.javelin.security.JwtAuthServiceProvider
  - io.javelin.security.CorsServiceProvider
  - io.javelin.validation.ValidationServiceProvider
  - io.javelin.config.SecretsServiceProvider
```

`SessionServiceProvider` must come before CSRF and session auth, because both need a session on the request. CORS is a no-op until `allowed_origins` is non-empty. CSRF skips Bearer requests, so API clients are unaffected.

## 2. Build and start the demo

```bash
mvn -pl demo-app -am package -DskipTests
java -jar demo-app/target/javelin-demo-app-0.1.0-SNAPSHOT.jar serve
```

The server listens on `http://127.0.0.1:8080`.

## 3. Browser login with CSRF

`GET /demo/session/login` renders a form. `CsrfMiddleware` puts a token in the session, in the request attribute `javelin.csrf`, and in the `X-CSRF-Token` response header. The controller writes it into a hidden `_token` field.

```bash
# Keep cookies in a jar so the session survives between requests.
curl -s -c cookies.txt http://127.0.0.1:8080/demo/session/login -o login.html

# Extract the token from the hidden field (PowerShell: use Select-String).
TOKEN=$(grep -o 'name="_token" value="[^"]*"' login.html | sed 's/.*value="//;s/"//')

curl -i -b cookies.txt -c cookies.txt \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  --data-urlencode "_token=$TOKEN" \
  --data-urlencode "username=alice" \
  --data-urlencode "password=secret" \
  http://127.0.0.1:8080/demo/session/login
```

A successful login returns `302` to `/demo/session/me`:

```bash
curl -s -b cookies.txt http://127.0.0.1:8080/demo/session/me
# {"permissions":["users.read"],"subject":"alice"}
```

Without the session cookie, the same URL returns `401` because `RequiresAuthenticationMiddleware` finds no principal. `SessionAuthMiddleware` is what turns the session's `javelin.user` value into that principal.

Log out with:

```bash
curl -i -b cookies.txt -c cookies.txt http://127.0.0.1:8080/demo/session/logout
# 302 to /demo/session/login and an expired session cookie
```

### What happens on a bad CSRF token

```bash
curl -i -b cookies.txt -X POST \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  --data 'username=alice&password=secret&_token=wrong' \
  http://127.0.0.1:8080/demo/session/login
# 403 Invalid CSRF token
```

## 4. Validation and error rendering

`SecurityExampleController.login` runs the `Validator` rules and passes the result to `ValidationErrors.of(request, errors)`:

```java
Map<String, List<String>> errors = validator.validate(values, Map.of(
        "username", List.of("required"),
        "password", List.of("required", "min:6")));
if (!errors.isEmpty()) {
    return ValidationErrors.of(request, errors);
}
```

An `Accept: application/json` request gets a 422 JSON body with an `errors` object. A browser request gets an escaped HTML fragment. Add your own rules to `DefaultValidator` or replace the `Validator` binding.

## 5. JWT for the API

`GET /demo/api/token` issues an HS256 token using `JwtTokenIssuer`:

```bash
curl -s http://127.0.0.1:8080/demo/api/token
# {"token":"eyJ...","token_type":"Bearer"}
```

`GET /demo/api/me` is guarded by `BearerAuthMiddleware` and the resolver provided by `JwtAuthServiceProvider`:

```bash
TOKEN=$(curl -s http://127.0.0.1:8080/demo/api/token | sed -n 's/.*"token":"\([^"]*\)".*/\1/p')

curl -s -H "Authorization: Bearer $TOKEN" http://127.0.0.1:8080/demo/api/me
# {"permissions":["users.read"],"subject":"alice"}

curl -i http://127.0.0.1:8080/demo/api/me
# 401 Unauthorized
```

The resolver rejects invalid signatures, unsupported algorithms, expired tokens and mismatched issuers/audiences. Signing and verification use the same `security.jwt.secret`; set it from `APP_KEY` in production.

## 6. CORS preflight

The kernel's automatic `OPTIONS` reply passes through global middleware, so CORS preflight works on a path that only defines GET/POST:

```bash
curl -i -X OPTIONS http://127.0.0.1:8080/demo/session/login \
  -H 'Origin: http://localhost:5173' \
  -H 'Access-Control-Request-Method: POST'
```

Expected headers include:

```
HTTP/1.1 204 No Content
Access-Control-Allow-Origin: http://localhost:5173
Access-Control-Allow-Methods: GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS
Access-Control-Max-Age: 600
Vary: Origin
```

An origin not in `allowed_origins` receives `403` on preflight and no CORS headers on normal requests.

## 7. Secrets

`SecretProvider` checks the environment first, then files under `secrets.dir`:

```java
String apiKey = app.make(SecretProvider.class)
        .get("EXTERNAL_API_KEY")
        .orElseThrow();
```

With `secrets.dir: secrets`, a file named `EXTERNAL_API_KEY` is read and trimmed. Path traversal outside the directory is rejected. Keep `.env` for development only; in production inject real secrets as environment variables or mounted files.

## Where to go next

- [Security module](../modules/security.md) — every middleware and config key used here.
- [Validation module](../modules/validation.md) — rule strings and custom validators.
- [Core security](../modules/core.md#bearer-authentication-and-authorization) — permission hooks and resolvers.
- [Deployment](../deployment.md) — HSTS/CSP, trusted proxies and TLS termination.
