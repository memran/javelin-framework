# Exception Module

Artifact: `io.javelin:javelin-exception`

`javelin-exception` centralizes error handling: one global handler with production-safe defaults, custom error pages loaded from disk, and redaction of sensitive values before details are shown or logged.

Register `ExceptionHandlerServiceProvider` in `config/app.yaml` to replace the core default:

```yaml
providers:
  - io.javelin.exception.ExceptionHandlerServiceProvider
```

## Configuration

| Key | Default | Meaning |
|---|---|---|
| `errors.detailed` | `true` in `dev`, otherwise `false` | Include exception type, message and stack trace in the response. Keep off in production. |
| `errors.redact` | `true` | Mask sensitive key/value pairs in detailed messages and traces. |
| `errors.redact_keys` | built-in list | Additional or replacement keys to mask (e.g. `internal_note`). |
| `errors.pages_dir` | `errors` | Directory containing `<status>.html` or `error.html` pages. |

The built-in redaction list covers `password`, `token`, `authorization`, `cookie`, `api_key`, `access_token`, `refresh_token`, `private_key`, `credit_card`, `cvv` and `ssn`. Redaction matches `key=value`, `"key": "value"` and header-style `Authorization: Bearer ...` forms; it also masks matching map keys.

## ConfigurableExceptionHandler

```java
ExceptionHandler handler = new ConfigurableExceptionHandler(
        false,
        SensitiveDataRedactor.defaults(),
        new FileErrorPageResolver(Path.of("errors")));
app.instance(ExceptionHandler.class, handler);
```

- In production mode it first tries the configured error page (`errors/500.html`, then `errors/error.html`), then falls back to the built-in HTML or JSON error response based on the request's `Accept` header.
- In detailed mode it returns the exception type, redacted message and stack trace as JSON for API clients, or an escaped HTML page for browsers.
- `FileErrorPageResolver` returns an empty result when no page exists, so error handling never fails because a file is missing.

## Error pages

Generated projects include `errors/500.html`. Add other pages by status, for example `errors/404.html`. Pages are plain HTML files — no template engine, no reflection, and no classpath scanning. The resolver reads only the two explicit filenames.

## Service provider

`ExceptionHandlerServiceProvider.register(app)` builds the redactor and page resolver from config, then installs the handler as the single `ExceptionHandler` binding. `HttpKernel` already delegates all uncaught exceptions to that contract, so no application code changes are needed.
