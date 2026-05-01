# View Pebble Module

Artifact: `io.javelin:javelin-view-pebble`

`javelin-view-pebble` integrates the Pebble template engine as the framework view backend.

## Public API

- `PebbleViewRenderer`
- `PebbleViewExtensions`
- `PebbleViewServiceProvider`

## Example

```java
HtmlResponse view = View.view("users/index", Map.of("name", "Javelin"));
```

## Function Usage

- `View.view(template, data)` renders a Pebble template once `PebbleViewServiceProvider` has installed the renderer.
- `View.view(template, "name", value, ...)` is the fluent form for passing a small list of variables.
- `View.use(renderer)` swaps the active rendering backend.
- `View.render(template, data)` renders a template name and model into an `HtmlResponse`.
- `PebbleViewRenderer` performs the Pebble template rendering.
- `PebbleViewExtensions` adds helper functions, filters, or tests to the template engine.
- `PebbleViewServiceProvider` wires the renderer into the container.

## Notes

- Configure Pebble in `config/view.yml`.
- Use extensions for filters, functions, tests, and globals.
