# Validation Module

Artifact: `io.javelin:javelin-validation`

`javelin-validation` provides an explicit `Validator` contract for form and API input. The older fluent `io.javelin.support.Validation` remains unchanged in `javelin-support`; this module is the string-rule, container-bound alternative.

## Validator

```java
Validator validator = app.make(Validator.class);
Map<String, List<String>> errors = validator.validate(
        Map.of("email", "bad", "role", "admin"),
        Map.of(
                "email", List.of("required", "email"),
                "role", List.of("oneOf:admin,user")));
```

The result maps field names to messages and is empty when valid. Feed it directly into `ValidationErrors` for a 422 response:

```java
return ValidationErrors.of(request, errors); // JSON or HTML by Accept header
```

## Supported rules

| Rule | Meaning |
|---|---|
| `required` | value must be present and non-blank |
| `email` | basic email shape |
| `min:N` / `max:N` | string length bounds |
| `integer` | optional integer value |
| `oneOf:a,b` | exact membership |
| `regex:PATTERN` | full-string regex match |

Unknown rule names fail fast with `IllegalArgumentException`. Rules are code/config controlled; do not build them from untrusted client input.

## Service provider

`ValidationServiceProvider` binds `DefaultValidator` as the `Validator` singleton. Register it in `config.providers` (generated projects do this by default) and register a different `Validator` instance in your own provider to replace it.

## Relationship to `javelin-support`

- `io.javelin.support.Validation` — fluent, chainable rule methods over `Input`; unchanged.
- `io.javelin.support.ValidationRuleRegistry` / `ValidationRule` — custom rule hooks used by the starter's validation-rule loader.
- `io.javelin.validation.Validator` — map-based rule strings with a container binding for both HTML controllers and REST endpoints.
