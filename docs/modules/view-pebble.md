# View Pebble Module

Artifact: `io.javelin:javelin-view-pebble`

`javelin-view-pebble` integrates the Pebble template engine as the framework view backend.

## Rendering

```java
PebbleViewRenderer renderer = new PebbleViewRenderer("resources/views");
HtmlResponse html = renderer.render("users/index", Map.of("name", "Javelin"));

// Filesystem root (used by the service provider when a Path is bound)
PebbleViewRenderer fileRenderer = new PebbleViewRenderer(
        Path.of("."), "resources/views", PebbleViewRenderer.Options.defaults(), extensions.extensions());
```

Constructors accept a classpath prefix or a filesystem root plus prefix, with optional `Options` and `List<Extension>`. `renderer.share(key, value)` adds a global variable available in every template; `render(template, data)` merges per-request data over globals.

## Options and caching

`Options` is a record in this order:

```java
new PebbleViewRenderer.Options(
        ".peb",                 // suffix
        true,                   // cache compiled templates
        true,                   // auto-escaping
        false,                  // strict variables
        false,                  // new-line trimming
        Optional.empty(),       // default locale
        Optional.empty(),       // max rendered size
        false,                  // allow overriding core operators
        Optional.empty(),       // default escaping strategy
        false,                  // literal decimals as integers
        false,                  // literal numbers as BigDecimals
        false                   // greedy method matching
);
```

`Options.defaults()` uses `.peb`, caching **off**, auto-escaping **on**, strict variables off and new-line trimming off. The service provider reads `config/view.yml`:

| Key | Default | Meaning |
|---|---|---|
| `view.prefix` | `resources/views` | Template directory/prefix |
| `view.suffix` | `.peb` | Template file suffix |
| `view.pebble.cache` | `false` | Cache compiled templates |
| `view.pebble.auto_escaping` | `true` | Escape `{{ ... }}` output by default |
| `view.pebble.strict_variables` | `false` | Fail on undefined variables |
| `view.pebble.new_line_trimming` | `false` | Trim new lines |
| `view.pebble.default_locale` | — | Locale tag |
| `view.pebble.max_rendered_size` | — | Render size limit |
| `view.pebble.default_escaping_strategy` | — | Escaping strategy name |

Enable caching in production with `view.pebble.cache: true`; leave it off in development to see template edits immediately.

## Layouts, includes and components

Pebble's native tags are available without extra wiring. Generated projects ship:

- `resources/views/layouts/app.peb` — a base layout with `{% block content %}`
- `resources/views/components/alert.peb` — a reusable `{% macro alert(message) %}`
- `resources/views/welcome.peb` — extends the layout and imports the component

```peb
{# resources/views/pages/show.peb #}
{% extends "layouts/app" %}
{% import "components/alert" as ui %}
{% block content %}
    {{ ui.alert("Saved") }}
    <h1>{{ user.name }}</h1>
    {% include "partials/footer" %}
{% endblock %}
```

Templates are resolved relative to the configured prefix, so `layouts/app` loads `layouts/app.peb`.

## Automatic escaping

`{{ value }}` is HTML-escaped when `view.pebble.auto_escaping` is true. Use `{{ value|raw }}` only for content you already sanitized:

```peb
{{ user.bio }}       {# escaped #}
{{ trustedHtml|raw }} {# not escaped #}
```

`Response.errorPage(...)` and `ValidationErrors.html(...)` also escape their HTML output independently of Pebble.

## Shared view data

```java
PebbleViewExtensions extensions = new PebbleViewExtensions()
        .filter("slugify", new SlugifyFilter())
        .function("greet", new GreetFunction())
        .test("admin", new AdminTest())
        .global("siteName", "Javelin")
        .extension(new MyCustomExtension());

PebbleViewRenderer renderer = new PebbleViewRenderer(
        "resources/views", PebbleViewRenderer.Options.defaults(), List.of(extensions));
```

`PebbleViewServiceProvider` registers `PebbleViewExtensions` in the container, so applications can add filters, functions, tests or globals from their own provider before `boot()`.

## Service provider

`provider.register(app)` binds `ViewRenderer` and `PebbleViewExtensions`; `provider.boot(app)` calls `View.use(renderer)`. Application code uses the facade:

```java
return View.render("users/index", Map.of("users", users));
```

See [CLI Reference](../cli-reference.md) for the full list of generated files.
