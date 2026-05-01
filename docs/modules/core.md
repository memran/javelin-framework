# Core Module

Artifact: `io.javelin:javelin-core`

`javelin-core` is the foundation of the framework. It owns the container, routing, request and response contracts, kernel contracts, provider loading, and the core facades.

## Public API

- `Application`
- `Container`
- `Router`
- `Route`, `Routes`, `RouteHandler`
- `Request`, `Response`, `HtmlResponse`, `JsonResponse`, `RedirectResponse`, `Model`
- `Config`, `Env`, `Database`, `Logger`
- `Middleware`, `ServiceProvider`
- `HttpKernel`, `HttpServerAdapter`
- `View`, `ViewRenderer`
- `QueryBuilder`, `TransactionCallback`
- `Json`, `Log`

## Example

```java
Application app = new Application(config, env);
app.register(new MyServiceProvider());

app.router()
    .get("/", request -> View.view("home", Map.of("title", "Hello")))
    .get("/health", request -> Response.json(Map.of("status", "ok")))
    .get("/feed.xml", request -> Response.xml("""
        <feed><title>Javelin</title></feed>
        """));

Database db = app.make(Database.class);
User user = new User(db);
user.save();
Model.find(db, User.class, 1L).ifPresent(found -> System.out.println(found.table()));
```

Enum-backed fields are also supported through `casts()`:

```java
enum Status {
    DRAFT,
    PUBLISHED
}

final class Post extends Model {
    Post(Database database) {
        super(database);
    }

    @Override
    protected Map<String, Class<?>> casts() {
        return Map.of("status", Status.class);
    }
}

Post post = new Post(db);
post.forceFill(Map.of("status", "published"));
Status status = post.attribute("status").map(Status.class::cast).orElseThrow();
```

## Function Usage

- `Application(Config, Env)` creates the container and registers core singletons.
- `app.register(provider)` adds a service provider before boot. Use this for module wiring.
- `app.boot()` runs each provider once. Call it after registration, before serving requests.
- `app.router()` returns the shared router used to register routes.
- `Router.get/post/put/patch/delete(path, handler)` adds HTTP routes with explicit handlers.
- `Router.middleware(middleware)` registers middleware that wraps every request.
- `Routes.group(router, prefix, routes)` scopes routes under a prefix, which is useful for admin or API sections.
- `Request.header/query/param` read incoming data without hidden parsing.
- `Request.bodyAsString()` returns the request body as UTF-8 text.
- `Request.json(type)` parses the body into a typed object.
- `Request.input()` merges query and HTML form values into a fluent `Input` wrapper.
- `Request.file(name)` returns the first uploaded file for a multipart field.
- `Request.upload(name)` is an alias for `file(name)`.
- `Request.files(name)` returns all uploaded files for a multipart field.
- `Request.uploads()` returns every uploaded file across the request as a flat list.
- `Request.saveFile(name, directory)` stores the first uploaded file in a directory and returns the saved path.
- `Request.saveFiles(name, directory)` stores all uploaded files for a multipart field and returns their saved paths.
- `UploadedFile.saveTo(path)` and `UploadedFile.saveToDirectory(directory)` persist multipart uploads explicitly.
- `Response.json/html/text/xml` build content-type-aware responses.
- `Response.redirect(download/noContent/errorPage)` covers redirects, attachments, empty responses, and HTML error pages.
- `View.use(renderer)` swaps the active template renderer.
- `View.render(template, data)` renders a template to an `HtmlResponse`.
- `View.view(template, data)` is the fluent alias for rendering a template with a `Map<String, Object>`.
- `View.view(template, "name", value, ...)` accepts alternating variable names and values for simple template calls.
- `Database.execute(sql)` runs framework-managed raw SQL, which is used by the migration runner for table creation and schema statements.
- `Database.transaction(callback)` wraps a unit of work in a transaction and returns the callback result.
- `Model.find(database, type, id)` loads a single row into a concrete workspace model.
- `Model.findOrFail(database, type, id)` fails fast when the row is missing.
- `Model.query(database, type)` returns the base `QueryBuilder` for the model's default table.
- `Model.where(database, type, column, value)` starts a filtered query for that model table.
- `Model.firstWhere(database, type, column, value)` loads the first matching row into a model.
- `Model.all(database, type)` loads every row for the model's default table.
- `Model.fill(values)` applies mass assignment using `fillable()` and `guarded()` rules.
- `Model.forceFill(values)` bypasses mass-assignment rules while still protecting the primary key.
- Override `fillable()` to allow only a known list of fields, or `guarded()` to block a few sensitive ones.
- Override `casts()` to convert string input into `LocalDate`, `LocalDateTime`, `Integer`, `Long`, `Boolean`, or enum values during fill and hydration.
- `Logger.info/warn/error` are the only logging entry points.

## Notes

- Keep registration explicit.
- Use providers to wire adapters and application modules.
- Prefer deterministic constructor injection through the container.
- `Response` provides Laravel-style factories for `json`, `html`, `text`, `xml`, and `errorPage`.
- The response helpers also accept explicit status codes, for example `Response.text("Accepted", 202)`.
- `Response.redirect(...)` returns a redirect response and `Response.download(...)` returns an attachment response.
- `Response.stream(...)` materializes an `InputStream` into a response body, and `Response.noContent()` returns a 204 response with an empty body.
- `Model` is the Laravel-style base class for workspace models. It keeps the database dependency explicit, defaults table names from the class name, and provides `save()`, `delete()`, `table()`, and `id()` helpers.
- `Model.attribute(name)` reads hydrated values from loaded models, while `fill(...)` is used internally by the static loaders.
- `Model.fill(...)` and `Model.forceFill(...)` are the explicit mass-assignment entry points.
- `Model.casts()` lets workspace models normalize incoming strings into typed fields, including date fields via `io.javelin.support.Date` and enum-backed status fields.
- Override `attributes()` in your workspace model to declare the columns you want persisted.
