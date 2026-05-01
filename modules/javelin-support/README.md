# Javelin Support

`javelin-support` provides small, composable helper facades for common application and library tasks.

## Public API

- `Collection` for array-backed fluent collection chains
- `Input` for sanitized fluent request input access
- `Str` for string transforms and checks
- `Arr` for array helpers
- `File` for file and path helpers
- `Image` for image metadata checks and loading
- `Http` for fluent JDK HTTP client requests
- `Ai` for OpenAI-compatible chat and streaming helpers
- `Date` for strict `java.time` parsing, formatting, and comparisons
- `Obj` for object-level helpers
- `Validator` for strict validation primitives
- `Validation` for chainable input validation rules
- `Html` for escaping and tag stripping
- `Security` for small security-oriented helpers

## Example

```java
import java.nio.file.Path;

import io.javelin.support.Collection;
import io.javelin.support.Arr;
import io.javelin.support.File;
import io.javelin.support.Ai;
import io.javelin.support.Http;
import io.javelin.support.Date;
import io.javelin.support.Security;
import io.javelin.support.Str;
import io.javelin.support.Validator;

String slug = Str.toSlug("Hello World");
String masked = Str.mask("secret-value", 2, 2, '*');
String first = Arr.first(new String[] {"alpha", "beta"});
String safeName = Security.sanitizeFilename("../../CON.txt");
String userName = Validator.requireNonBlank("Alice", "name is required");
String createdAt = Date.format(Date.now());
File.writeString(File.safeResolve(Path.of("storage"), "notes", "a.txt"), "hello");
Collection<String> users = Collection.of("alice", null, "bob")
    .compact()
    .map(String::toUpperCase);
Input input = Input.from(Map.of("name", "<b>Alice</b>", "file", "../../CON.txt"));
Validation validation = Validation.of(input).required("name").email("email");
Http.Response response = Http.of("http://127.0.0.1:8080")
    .withHeader("X-Token", "demo")
    .get("/ping");
Ai.Reply aiReply = Ai.of("http://127.0.0.1:11434/v1")
    .model("llama3.1")
    .chat("Say hello in one short sentence.");
```

The module is intentionally dependency-light and JDK-only.

The `Collection` helper is immutable and chainable. Use `map`, `flatMap`, `flatten`, `filter`, `partition`, `groupBy`, `firstWhere`, `pluckPath`, `toMap`, and the aggregate helpers when you want Laravel-style collection flows without mutating the source data.
`Input` gives you sanitized access to HTML form/query values, and `Validation` lets you chain validation rules before throwing once at the end.
`Validation` also includes path/file checks like `fileExists`, `directoryExists`, `readableFile`, `writableFile`, `hasExtension`, and `maxBytes`, plus rule composition through `ValidationRule.of(...)`, `and(...)`, `or(...)`, and `negate(...)`.
`Image` gives you lightweight detection, metadata, and reading helpers for image files without pulling in a larger media stack.
`Image.of(path)` returns a fluent handle for `path()`, `isImage()`, `width()`, `height()`, `format()`, `dimensions()`, and `read()`.
`Http` gives you a fluent wrapper over JDK `HttpClient` with base URLs, headers, timeouts, and typed response access.
`Http` also includes `getJson(...)`, `postForm(...)`, and `download(...)` for the common API client cases.
`Ai` gives you an OpenAI-compatible chat client with `chat(...)`, `stream(...)`, and a config-file loader for `.javelin/ai.properties`.
`Date` gives you UTC-based `java.time` parsing, formatting, and simple date arithmetic with predictable behavior.
