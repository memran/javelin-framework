# JSON Jackson Module

Artifact: `io.javelin:javelin-json-jackson`

`javelin-json-jackson` supplies a configurable Jackson implementation of the core `JsonMapper` contract. Core keeps its own default mapper for backward compatibility; registering this provider makes Jackson the active mapper for `Json.stringify`, `Json.parse`, `Json.convert` and all `JsonResponse` bodies.

## Registration

```yaml
providers:
  - io.javelin.json.jackson.JacksonJsonServiceProvider
```

Generated projects register this provider by default.

## Configuration

| Key | Default | Meaning |
|---|---|---|
| `json.indent_output` | `false` | Pretty-print JSON |
| `json.fail_on_unknown_properties` | `false` | Fail deserialization on unknown fields |
| `json.write_nulls` | `true` | Include null fields |
| `json.date_format` | `""` | Optional `SimpleDateFormat` pattern |

## API

```java
// Use the framework facade
Json.ok(users);                       // 200 JSON response
Json.stringify(user);                 // uses the active mapper
User parsed = Json.parse(json, User.class);
User converted = Json.convert(map, User.class);

// Access the configured mapper directly when needed
ObjectMapper mapper = app.make(ObjectMapper.class);
JsonMapper active = app.make(JsonMapper.class);
```

`JacksonJsonMapper` accepts an externally supplied `ObjectMapper`, so applications can add modules (for example Java time support) without changing framework code:

```java
ObjectMapper configured = new ObjectMapper().registerModule(new JavaTimeModule());
app.instance(JsonMapper.class, new JacksonJsonMapper(configured));
```

Registering a custom `JsonMapper` instance after the provider replaces the container binding; call `Json.use(...)` if you also need the static facade to pick it up.

## Backward compatibility

`io.javelin.core.Json` still works without this module: it falls back to an internal Jackson `ObjectMapper`. `JsonResponse` now serializes through the active mapper, so both paths produce the same output by default.
