# View Pebble Module

Artifact: `io.javelin:javelin-view-pebble`

`javelin-view-pebble` integrates the Pebble template engine as the framework view backend.

## PebbleViewRenderer

`PebbleViewRenderer` implements `ViewRenderer` and renders Pebble templates.

```java
// Basic renderer — loads templates from classpath with "templates/" prefix
PebbleViewRenderer renderer = new PebbleViewRenderer("templates/");

// With custom options
PebbleViewRenderer renderer = new PebbleViewRenderer("templates/",
    PebbleViewRenderer.Options.defaults());

// With custom options and extensions
PebbleViewRenderer renderer = new PebbleViewRenderer("templates/",
    new PebbleViewRenderer.Options(true, ".peb", true, false, true,
        Optional.empty(), Optional.empty(), false, Optional.empty(),
        false, false, false),
    List.of(new MyExtension()));

// From a filesystem root
PebbleViewRenderer renderer = new PebbleViewRenderer(Path.of("src/main/resources"), "templates/");

// Render a template
HtmlResponse html = renderer.render("index", Map.of("title", "Hello"));
```

- `new PebbleViewRenderer(String prefix)` -- loads templates from the classpath with the given prefix.
- `new PebbleViewRenderer(String prefix, Options options)` -- loads templates with custom Pebble options.
- `new PebbleViewRenderer(String prefix, Options options, List<Extension> extensions)` -- loads templates with options and Pebble extensions.
- `new PebbleViewRenderer(Path root, String prefix)` -- loads templates from a filesystem root path.
- `new PebbleViewRenderer(Path root, String prefix, Options options)` -- filesystem root with options.
- `new PebbleViewRenderer(Path root, String prefix, Options options, List<Extension> extensions)` -- filesystem root with options and extensions.
- `renderer.share(String key, Object value)` -- sets a global template variable available in every template.
- `renderer.render(String template, Map<String, Object> data)` -- renders the template and returns an `HtmlResponse`.

### Options

```java
PebbleViewRenderer.Options opts = PebbleViewRenderer.Options.defaults();

// Or build custom options:
PebbleViewRenderer.Options custom = new PebbleViewRenderer.Options(
    true,            // cache compiled templates
    ".peb",          // template file suffix
    true,            // auto-escape
    false,           // strict variables
    true,            // newline trimming
    Optional.empty(), // default locale
    Optional.empty(), // max rendered size
    false,           // allow override core operators
    Optional.empty(), // default escaping strategy
    false,           // literal decimal as integer
    false,           // literal numbers as big decimals
    false            // greedy match method
);
```

- `Options.defaults()` -- returns a pre-built default configuration with caching enabled, `.peb` suffix, auto-escaping on, and newline trimming on.

## PebbleViewExtensions

`PebbleViewExtensions` adds custom filters, functions, tests, and globals to the Pebble template engine. All methods return `PebbleViewExtensions` for fluent chaining.

```java
PebbleViewExtensions extensions = new PebbleViewExtensions()
    .filter("slugify", new SlugifyFilter())
    .function("greet", new GreetFunction())
    .test("admin", new AdminTest())
    .global("siteName", "Javelin")
    .extension(new MyCustomExtension());
```

- `extensions.extension(Extension ext)` -- adds a complete Pebble extension.
- `extensions.filter(String name, Filter filter)` -- registers a template filter (e.g., `{{ title | slugify }}`).
- `extensions.function(String name, Function function)` -- registers a template function (e.g., `{{ greet("Alice") }}`).
- `extensions.test(String name, Test test)` -- registers a template test (e.g., `{% if user is admin %}`).
- `extensions.global(String name, Object value)` -- registers a global variable available in all templates.

```java
// Wiring extensions into the renderer:
PebbleViewRenderer renderer = new PebbleViewRenderer(
    "templates/",
    PebbleViewRenderer.Options.defaults(),
    List.of(new PebbleViewExtensions()
        .filter("upper", (Filter) (value, args) -> value.toString().toUpperCase())
        .build()) // extensions list containing the custom filter
);
```

Note: `PebbleViewExtensions` itself implements `Extension`, so you can pass it directly in the extensions list.

## PebbleViewServiceProvider

`PebbleViewServiceProvider` wires the Pebble renderer into the container.

```java
PebbleViewServiceProvider provider = new PebbleViewServiceProvider();
provider.register(app);
provider.boot(app);
```

- `provider.register(app)` -- reads `view.template_dir`, `view.cache`, `view.suffix`, `view.auto_escape` from config, creates a `PebbleViewRenderer`, and registers it as `ViewRenderer` singleton. Also registers `PebbleViewExtensions`.
- `provider.boot(app)` -- calls `View.use(renderer)` to activate Pebble as the view backend.

```java
// Config keys used (config/view.yaml):
// view.template_dir: "templates"
// view.cache: true
// view.suffix: ".peb"
// view.auto_escape: true
```

```java
// After provider boot:
HtmlResponse html = View.view("users/index", Map.of("name", "Javelin"));

// Or with varargs for simple templates:
HtmlResponse html = View.view("greeting", "name", "Alice", "role", "Admin");
```

## Using templates in routes

```java
app.router().get("/", request ->
    View.view("home", Map.of("title", "Welcome"))
);

app.router().get("/users/{id}", request -> {
    String id = request.param("id").orElseThrow();
    return View.view("users/show", Map.of("userId", id));
});

// With global shared variables:
PebbleViewRenderer renderer = app.make(ViewRenderer.class);
renderer.share("appName", "Javelin");
// Now all templates have access to {{ appName }}
```

## Notes

- Configure Pebble in `config/view.yml`.
- Use extensions for filters, functions, tests, and globals.
- Templates with the `.peb` suffix are resolved from the configured template directory.
