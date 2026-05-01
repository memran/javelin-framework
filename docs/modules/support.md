# Support Module

Artifact: `io.javelin:javelin-support`

`javelin-support` provides small, strict helper facades for common string, array, file, object, collection, validation, HTML, and security tasks.

## Public API

- `Collection`
- `Input`
- `Str`
- `Arr`
- `File`
- `Date`
- `Obj`
- `Validator`
- `Validation`
- `Html`
- `Security`

## Example

```java
String slug = Str.toSlug("Javelin Framework");
String safe = Security.sanitizeFilename("../../CON.txt");
String name = Validator.requireNonBlank("Alice", "name is required");
String today = Date.format(Date.today());
Collection<String> names = Collection.of("alice", null, "bob")
    .compact()
    .map(String::toUpperCase)
    .filter(value -> value.startsWith("A") || value.startsWith("B"));
Input input = Input.from(Map.of("name", "<b>Alice</b>", "file", "../../CON.txt"))
    .merge(Map.of("active", "yes"));
Validation validation = Validation.of(input)
    .required("name")
    .email("email");
```

## Function Usage

- `Collection.of(...)` creates an immutable, array-backed collection.
- `Collection.from(collection)` wraps any existing Java collection.
- `Collection.containsAny(...)` and `containsAll(...)` check membership across multiple candidates.
- `Collection.map`, `flatMap`, `filter`, `reject`, `only`, `except`, `concat`, `unique`, `reverse`, `take`, `skip`, and `sort` return new collections so calls can be chained safely.
- `Collection.compact()` removes null values.
- `Collection.chunk(size)` splits values into smaller collections.
- `Collection.flatten()` unwraps nested arrays, iterables, and support collections.
- `Collection.groupBy(...)` creates grouped slices and returns `Collection.Group` items.
- `Collection.keyBy(...)` keeps the original values while attaching computed keys through `Collection.Keyed`.
- `Collection.pluck(...)` maps each element to a projected value.
- `Collection.pluckPath(path)` reads dot-separated values from nested maps, records, and simple bean getters.
- `Collection.toMap(...)` creates an ordered map from collection values.
- `Collection.partition(predicate)` splits values into matching and rejected collections.
- `Collection.firstWhere(predicate)` returns the first match, or `null` when nothing matches.
- `Collection.reduce(...)` folds values into a single result.
- `Collection.sum()` and `average()` work on numeric collections, while the mapper overloads handle projected numeric values.
- `Collection.min()` and `max()` return the smallest and largest comparable values.
- `Collection.join(delimiter)` turns the values into a string.
- `Collection.tap(callback)` is for inspection in the middle of a chain.
- `Collection.when(condition, callback)` and `unless(condition, callback)` apply conditional transformations.
- `Input.raw(key)` reads the original value.
- `Input.text(key)` strips HTML tags and trims whitespace before returning user input.
- `Input.filename(key)` applies filename sanitization for upload or path-related fields.
- `Input.file(key)` is an alias for `filename(key)`.
- `Input.array(key)` parses comma-separated input into a sanitized `Collection<String>`.
- `Input.integer(key)` and `bool(key)` parse typed values from HTML input.
- `Input.only(...)`, `except(...)`, `merge(...)`, and `tap(...)` keep input handling fluent.
- `Input.validate(registry)` starts a fluent validation chain with custom app rules loaded into a registry.
- `Validation.of(input)` starts a fluent validation chain.
- `Validation.required`, `requiredIf`, `minLength`, `maxLength`, `length`, `integer`, `between`, `matches`, `email`, and `oneOf` collect rule failures before `validate()` throws once.
- `Validation.custom(rule)` applies a developer-defined rule object.
- `Validation.rule(name)` applies a registry-loaded custom rule by name.
- `ValidationRule` is the abstract base for app-defined rules.
- `ValidationRuleRegistry` stores rules loaded from the app folder or added explicitly by the app.
- `Str.isBlank`, `trimToNull`, and `defaultIfBlank` handle null-safe string cleanup.
- `Str.toSlug`, `toSnakeCase`, `toCamelCase`, `capitalize`, `repeat`, and `mask` transform strings in common app tasks.
- `Arr.isEmpty`, `first`, `last`, `contains`, `toList`, `compact`, and `chunk` provide small array/list helpers.
- `File.ensureDirectory`, `ensureParentDirectory`, `readString`, and `writeString` cover JDK file operations with explicit errors.
- `File.extension`, `baseName`, `hasExtension`, and `safeResolve` are useful for file-name and path handling.
- `Date.today()` and `Date.now()` use UTC so values stay predictable across environments.
- `Date.parseDate(...)` and `parseDateTime(...)` read ISO-8601 input.
- `Date.format(...)`, `addDays(...)`, `subtractDays(...)`, `isPast(...)`, and `isFuture(...)` cover the common date tasks.
- `Obj.coalesce`, `equalsAny`, `hash`, `isType`, and `requireType` help with object-level checks and casting.
- `Validator.require*` methods enforce preconditions and return the validated value when appropriate.
- `Html.escape` and `stripTags` handle HTML string safety.
- `Security.constantTimeEquals`, `sha256Hex`, `randomToken`, and `sanitizeFilename` cover security-oriented utility work.

## Notes

- The helpers are stateless and JDK-only.
- Use them when you want Laravel-style convenience without hidden framework behavior.
