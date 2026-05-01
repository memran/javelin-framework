# Javelin Support

`javelin-support` provides small, composable helper facades for common application and library tasks.

## Public API

- `Collection` for array-backed fluent collection chains
- `Input` for sanitized fluent request input access
- `Str` for string transforms and checks
- `Arr` for array helpers
- `File` for file and path helpers
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
```

The module is intentionally dependency-light and JDK-only.

The `Collection` helper is immutable and chainable. Use `map`, `flatMap`, `flatten`, `filter`, `partition`, `groupBy`, `firstWhere`, `pluckPath`, `toMap`, and the aggregate helpers when you want Laravel-style collection flows without mutating the source data.
`Input` gives you sanitized access to HTML form/query values, and `Validation` lets you chain validation rules before throwing once at the end.
`Date` gives you UTC-based `java.time` parsing, formatting, and simple date arithmetic with predictable behavior.
