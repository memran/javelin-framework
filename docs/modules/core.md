# Core Module

Artifact: `io.javelin:javelin-core`

`javelin-core` is the foundation of the framework. It owns the container, routing, request and response contracts, kernel contracts, provider loading, and the core facades.

## Container

The container is the central registry of your application. It resolves class dependencies automatically through constructor injection.

### Application

`Application` is the top-level facade that composes the container, router, config, and environment.

```java
Config config = ...;
Env env = ...;
Application app = new Application(config, env);
```

- `app.container()` -- returns the raw `Container` instance for direct access.
- `app.bind(Class<T> abstraction, Class<? extends T> implementation)` -- registers a reusable implementation that is freshly resolved each time.
- `app.singleton(Class<T> type, Supplier<? extends T> factory)` -- registers a supplier that is called once and then cached for every subsequent resolution.
- `app.instance(Class<T> type, T object)` -- registers a pre-built object directly.
- `app.make(Class<T> type)` -- resolves any registered type or auto-resolves a concrete class.
- `app.has(Class<?> type)` -- checks whether a type is registered.
- `app.router()` -- returns the shared `Router` used to register routes.
- `app.config()` -- returns the `Config` contract.
- `app.env()` -- returns the `Env` contract.
- `app.register(ServiceProvider provider)` -- adds a service provider to the registration queue.
- `app.boot()` -- runs all registered providers and locks the container.

```java
app.bind(Database.class, JdbcDatabase.class);
app.instance("app.name", "MyApp");
MyService service = app.make(MyService.class);
```

### Container

`Container` handles all service resolution. It does not use reflection scanning — every binding is explicit.

```java
Container container = app.container();
container.bind(Logger.class, Slf4jLogger.class);
container.singleton(Cache.class, () -> new InMemoryCache());
container.instance(Config.class, myConfig);
Logger logger = container.make(Logger.class);
boolean exists = container.has(Logger.class);
```

- `container.bind(abstraction, implementation)` -- each `make()` returns a new instance.
- `container.singleton(type, factory)` -- the supplier is called at most once. Subsequent `make()` calls return the same instance.
- `container.instance(type, object)` -- registers an already-constructed value directly.
- `container.has(type)` -- returns `true` if the type is bound.
- `container.make(type)` -- resolves by constructor injection. Works with concrete classes automatically.

## Routing

### Router

`Router` registers HTTP routes and middleware. You get it via `app.router()`.

```java
Router router = app.router();

router.get("/", request -> Response.text("Home"));
router.post("/users", request -> Response.json(Map.of("created", true)));
router.put("/users/{id}", request -> {
    String id = request.param("id").orElseThrow();
    return Response.json(Map.of("updated", id));
});
router.patch("/users/{id}", request -> Response.noContent());
router.delete("/users/{id}", request -> Response.noContent());

router.get("/orders/{orderId:[0-9]+}", request ->
        Response.text(request.param("orderId").orElseThrow()));
```

- `router.get(path, handler)` -- registers a GET route with `{param}` placeholders. A parameter may carry a regex constraint: `{id:[0-9]+}` matches only digits, `{code:[A-Z]{3}}` only three uppercase letters. Invalid or blank constraints fail at registration. Without a constraint the value is any non-slash segment.
- `router.post(path, handler)` -- registers a POST route.
- `router.put(path, handler)` -- registers a PUT route.
- `router.patch(path, handler)` -- registers a PATCH route.
- `router.delete(path, handler)` -- registers a DELETE route.
- `router.add(RouteBuilder builder)` -- registers a route from a `Route.RouteBuilder`.
- `router.middleware(Middleware m)` -- adds global middleware applied to every route.
- `router.group(prefix, middleware, routesConsumer)` -- scopes routes under a prefix with shared middleware.
- `router.routes()` -- returns the list of registered `Route` objects.
- `router.allowedMethods(path)` -- returns the methods (in enum order) that match a path, including `HEAD` when `GET` is registered. The kernel uses this to build `Allow` on a 405 response.
- Static paths are indexed by method for direct lookup; only `{param}` routes are pattern-matched. A `HEAD` request with no explicit HEAD route falls back to the matching `GET` route.
- Constraints only narrow URL matching. Revalidate parameters in the handler (or with your validation layer) before using them for database lookups, filesystem paths or authorization decisions.
- Registering the same method and path twice throws `IllegalArgumentException` at boot, so route-table order never silently decides which handler wins. Different methods on the same path are allowed.

```java
router.group("/admin", List.of(authMiddleware), admin -> {
    admin.get("/dashboard", request -> View.view("admin/dashboard"));
    admin.get("/users", request -> Response.json(userService.listAll()));
});
```

### Route

`Route` represents a single registered route. You rarely construct one directly — use the static factories instead.

```java
RouteBuilder builder = Route.get("/health", request -> Response.json(Map.of("status", "ok")));
Route route = builder.middleware(new LoggingMiddleware()).name("health").build();

router.add(builder); // register through the builder (the route is built for you)

String path = route.path();
HttpMethod method = route.method();
RouteHandler handler = route.handler();
List<Middleware> middleware = route.middleware();
Optional<String> name = route.name();
```

- `Route.get/post/put/patch/delete(path, handler)` -- creates a `RouteBuilder`.
- `RouteBuilder.middleware(Middleware m)` -- adds route-specific middleware.
- `RouteBuilder.name(String name)` -- names the route for later reference.
- `RouteBuilder.build()` -- produces the final `Route`.
- `route.method()` -- returns the `HttpMethod` enum.
- `route.path()` -- returns the path pattern like `/users/{id}`.
- `route.handler()` -- returns the `RouteHandler`.
- `route.middleware()` -- returns the list of middleware for this route.
- `route.name()` -- returns the optional route name.

### Routes

`Routes` provides static helpers for defining routes outside of the `Router` instance.

```java
Routes.get("/health", request -> Response.text("ok"))
    .middleware(new LoggingMiddleware())
    .name("health");

Routes.group(router, "/api", List.of(new AuthMiddleware()), api -> {
    api.get("/users", userController::index);
});
```

- `Routes.get/post/put/patch/delete` -- same as `Route.get(...)`, returns a `RouteBuilder`.
- `Routes.add(router, builder)` -- convenience for `router.add(builder)`.
- `Routes.middleware(router, m)` -- convenience for `router.middleware(m)`.
- `Routes.group(router, prefix, routes)` -- scopes routes under a prefix.
- `Routes.group(router, prefix, middleware, routes)` -- scopes routes with prefix and middleware.

### RouteHandler

`RouteHandler` is a `@FunctionalInterface` with one method:

```java
@FunctionalInterface
public interface RouteHandler {
    Response handle(Request request) throws Exception;
}
```

```java
RouteHandler handler = request -> {
    String name = request.query("name").orElse("Guest");
    return Response.text("Hello, " + name);
};
```

## Request

`Request` wraps an incoming HTTP request. It is immutable — route parameters are attached via `withParams()` by the router.

```java
Request request = new Request(
    HttpMethod.GET,
    "/users/42?sort=asc",
    Map.of("Accept", List.of("application/json")),
    Map.of("sort", "asc"),
    Map.of("id", "42"),
    new byte[0],
    "127.0.0.1"
);

HttpMethod method = request.method();
String path = request.path();
Optional<String> accept = request.header("Accept");
Optional<String> sort = request.query("sort");
Optional<String> id = request.param("id");
byte[] body = request.body();
String bodyText = request.bodyAsString();
String remoteAddr = request.remoteAddress();
Map<String, String> allQuery = request.queryParameters();
```

- `request.method()` -- returns `HttpMethod` enum.
- `request.path()` -- returns the raw path string.
- `request.header(name)` -- returns an optional header value.
- `request.query(name)` -- returns an optional query parameter value.
- `request.param(name)` -- returns an optional route parameter value (from `{placeholders}`).
- `request.body()` -- returns raw `byte[]` body.
- `request.bodyAsString()` -- decodes the body as UTF-8.
- `request.input()` -- returns an `Input` wrapper merging query and form data.
- `request.json(Class<T> type)` -- parses the JSON body into a typed object.
- `request.remoteAddress()` -- returns the client IP string.
- `request.queryParameters()` -- returns all query parameters as a map.
- `request.attribute(name)` -- returns an optional request attribute set by middleware (for example the authenticated principal).
- `request.withAttribute(name, value)` -- returns a copy with an attribute attached; the original request is unchanged. Attributes survive route-parameter attachment.

### File Uploads

```java
// Single file
Optional<UploadedFile> avatar = request.file("avatar");
Optional<UploadedFile> upload = request.upload("avatar"); // alias
Path saved = request.saveFile("avatar", Path.of("uploads/"));

// Multiple files
List<UploadedFile> photos = request.files("photos");
List<Path> savedPhotos = request.saveFiles("photos", Path.of("uploads/"));

// All uploads
List<UploadedFile> allUploads = request.uploads();
Map<String, List<UploadedFile>> allFiles = request.files();
```

### UploadedFile

```java
UploadedFile file = request.file("document").orElseThrow();

String field = file.fieldName();
String clientName = file.clientFilename();
String safeName = file.safeFilename();
String contentType = file.contentType();
long size = file.size();
byte[] content = file.body();

Path saved = file.saveTo(Path.of("uploads/doc.pdf"));
Path dirSaved = file.saveToDirectory(Path.of("uploads/"));
```

## Response

### Response static factories

```java
Response.text("Hello World");
Response.text("Created", 201);

Response.json(Map.of("user", "Alice"));
Response.json(Map.of("error", "not found"), 404);

Response.html("<h1>Welcome</h1>");
Response.html("<h1>Error</h1>", 500);

Response.xml("<feed><title>Javelin</title></feed>");
Response.xml("<error/>", 400);

Response.redirect("/login");
Response.redirect("/login", 302);

Response.download(csvBytes, "report.csv");
Response.download(pdfBytes, "report.pdf", "application/pdf");
Response.download(content, "file.bin", "application/octet-stream", 200);

Response.stream(inputStream, "text/plain");
Response.stream(inputStream, "application/json", 200);

Response.noContent();
Response.noContent(201);
```

### HtmlResponse, JsonResponse, RedirectResponse

```java
HtmlResponse html = new HtmlResponse("<h1>Hello</h1>");
HtmlResponse htmlWithStatus = new HtmlResponse("<h1>Error</h1>", 500);

JsonResponse ok = JsonResponse.ok(Map.of("message", "success"));
JsonResponse custom = JsonResponse.of(422, Map.of("error", "validation failed"));
JsonResponse err = JsonResponse.error("not found", 404);

RedirectResponse redirect = new RedirectResponse("/login");
RedirectResponse redirectPerm = new RedirectResponse("/new-url", 301);
```

### Response instance methods

```java
Response res = Response.text("Hello");
int status = res.status();
Map<String, String> headers = res.headers();
byte[] body = res.body();
Response withAllow = res.withHeader("Allow", "GET, HEAD"); // returns a copy
```

## Middleware

`Middleware` intercepts requests before they reach route handlers.

```java
Middleware logger = (request, next) -> {
    System.out.println("Request: " + request.method() + " " + request.path());
    Response response = next.handle(request);
    System.out.println("Response status: " + response.status());
    return response;
};

// Route-specific
router.get("/dashboard", handler).middleware(logger);

// Global
router.middleware(logger);

// Group
router.group("/admin", List.of(authMiddleware), admin -> { ... });
```

### Bearer authentication and authorization

These middleware live in `javelin-core` (package `io.javelin.core.security`). They stay agnostic of any user model: you supply the token resolver as a function.

```java
import io.javelin.core.security.*;

BearerTokenResolver resolver = token -> "secret-token".equals(token)
        ? Optional.of(new Authenticated("alice", Set.of("users.read")))
        : Optional.empty();

router.add(Route.get("/users/{id}", request -> {
            Authenticated user = (Authenticated) request
                    .attribute(BearerAuthMiddleware.PRINCIPAL_ATTRIBUTE).orElseThrow();
            return Response.text(user.subject() + ":" + request.param("id").orElseThrow());
        })
        .middleware(new BearerAuthMiddleware(resolver))
        .middleware(new RequiresPermissionMiddleware("users.read")));
```

- `BearerAuthMiddleware` reads `Authorization: Bearer <token>`, resolves it, and returns 401 with `WWW-Authenticate: Bearer` for missing or invalid tokens. On success it attaches the `Authenticated` principal as a request attribute.
- `RequiresAuthenticationMiddleware` returns 401 when no principal is present.
- `RequiresPermissionMiddleware` returns 401 when no principal is present and 403 when the principal lacks the required permission.
- `Authenticated` is a record of `subject` and an immutable `permissions` set with a `hasPermission(String)` helper.

Use `router.add(Route.get(...).middleware(...))` for route-level middleware — `router.get(...)` registers the route immediately, so middleware chained after it would not apply. For a whole group, pass the middleware to `router.group(...)` instead.

## ServiceProvider

`ServiceProvider` is the module wiring interface.

```java
public class MyServiceProvider implements ServiceProvider {
    public void register(Application app) {
        app.bind(MyInterface.class, MyImplementation.class);
    }

    public void boot(Application app) {
        MyService service = app.make(MyService.class);
        service.initialize();
    }
}
```

- `provider.register(app)` -- called first. Only bind things; don't use resolved services.
- `provider.boot(app)` -- called after all providers have registered. Safe to resolve services.

## HttpKernel

`HttpKernel` turns a `Request` into a `Response` through the router and exception handler.

```java
HttpKernel kernel = new HttpKernel(router, new DefaultExceptionHandler());
Response response = kernel.handle(request);
```

- `new HttpKernel(router, exceptionHandler)` -- constructs the kernel with a router and error handler.
- `kernel.withStaticFallback(resolver)` -- returns a kernel that first consults `resolver` (a `Function<Request, Optional<Response>>`) and runs any match through global middleware before returning it. Adapters use this for static assets.
- `kernel.handle(request)` -- dispatches the request through middleware, routing, and the matched handler.
- When no route matches the method but the path exists for other methods, the kernel returns 405 with an `Allow` header listing those methods (including `HEAD` when `GET` is registered); otherwise it returns 404.

### ExceptionHandler

```java
ExceptionHandler handler = (throwable, request) -> {
    System.err.println("Error processing " + request.path() + ": " + throwable.getMessage());
    return Response.text("Server Error", 500);
};
```

### DefaultExceptionHandler

The built-in handler returned by the container returns a 500 error page.

```java
DefaultExceptionHandler handler = new DefaultExceptionHandler();
Response error = handler.handle(exception, request);
```

## HttpServerAdapter

`HttpServerAdapter` is the contract for server implementations.

```java
HttpServerAdapter server = app.make(HttpServerAdapter.class);
server.start();

// On shutdown
server.stop(5); // 5 second grace delay
server.close();
```

## Config

`Config` provides typed configuration access.

```java
Optional<String> name = config.getString("app.name");
String name = config.getString("app.name", "Javelin");
Optional<Integer> port = config.getInt("server.port");
int port = config.getInt("server.port", 8080);
Optional<Boolean> debug = config.getBoolean("app.debug");
boolean debug = config.getBoolean("app.debug", false);
List<String> cors = config.getStringList("app.cors_origins");
```

## Env

`Env` reads environment variables.

```java
Optional<String> dbUrl = env.get("DATABASE_URL");
String dbUrl = env.get("DATABASE_URL", "jdbc:h2:mem:default");
```

## Database

`Database` is the data access contract.

```java
QueryBuilder query = database.table("users");
int affected = database.execute("DELETE FROM temp_sessions WHERE expires < NOW()");
String result = database.transaction(db -> {
    db.table("accounts").insert(Map.of("name", "Acme"));
    return "done";
});
database.close();
```

- `database.table(name)` -- returns a `QueryBuilder` for the given table.
- `database.execute(sql)` -- executes raw SQL (used by migration runner).
- `database.transaction(callback)` -- wraps operations in a transaction.
- `database.close()` -- releases the connection pool.

### QueryBuilder

```java
QueryBuilder qb = database.table("users");

qb.where("active", true);
Optional<Map<String, Object>> user = qb.first();
List<Map<String, Object>> users = qb.get();
List<Map<String, Object>> page = qb.paginate(1, 20); // page 1, 20 per page

long newId = database.table("users").insert(Map.of("name", "Alice"));
int updated = database.table("users").where("id", 1).update(Map.of("name", "Bob"));
int deleted = database.table("users").where("id", 1).delete();
```

### TransactionCallback

```java
TransactionCallback<String> callback = db -> {
    long id = db.table("orders").insert(Map.of("total", 100));
    db.table("logs").insert(Map.of("message", "Order " + id + " created"));
    return "ok";
};

String result = database.transaction(callback);
```

## Model

`Model` is the base class for workspace domain models.

```java
// Define a model
public final class User extends Model {
    public User(Database database) {
        super(database);
    }

    public User(Database database, Long id) {
        super(database, id);
    }

    @Override
    protected List<String> fillable() {
        return List.of("name", "email");
    }

    @Override
    protected Map<String, Class<?>> casts() {
        return Map.of(
            "created_at", LocalDateTime.class,
            "is_admin", Boolean.class,
            "status", Status.class
        );
    }
}

// Create and save
User user = new User(db);
user.fill(Map.of("name", "Alice", "email", "alice@example.com"));
user.save();

// Find
Optional<User> found = Model.find(db, User.class, 1L);
User mustExist = Model.findOrFail(db, User.class, 1L);

// Query
QueryBuilder qb = Model.query(db, User.class);
QueryBuilder filtered = Model.where(db, User.class, "active", true);

// First match
Optional<User> alice = Model.firstWhere(db, User.class, "email", "alice@example.com");

// All rows
List<User> allUsers = Model.all(db, User.class);

// Instance methods
Optional<Long> id = user.id();
boolean exists = user.exists();
String table = user.table();
long newId = user.save();
int deleted = user.delete();
Map<String, Object> data = user.toMap();
Optional<Object> attr = user.attribute("name");
user.fill(Map.of("name", "Bob"));
user.forceFill(Map.of("id", 999, "name", "Bob")); // bypasses guarded checks
```

### Model static methods

- `Model.find(database, type, id)` -- loads a single row by primary key into the model type.
- `Model.findOrFail(database, type, id)` -- same as `find` but throws when missing.
- `Model.query(database, type)` -- returns a fresh `QueryBuilder` for the model's table.
- `Model.where(database, type, column, value)` -- returns a filtered `QueryBuilder`.
- `Model.firstWhere(database, type, column, value)` -- loads the first matching row.
- `Model.all(database, type)` -- loads every row in the table.
- `Model.defaultTableName(type)` -- derives the table name from the class name (e.g. `User` -> `users`, `BlogPost` -> `blog_posts`).

### Model instance methods

- `model.save()` -- inserts or updates depending on whether the model has an ID.
- `model.delete()` -- deletes the row by primary key.
- `model.id()` -- returns the optional primary key value.
- `model.exists()` -- returns `true` if the model has been persisted.
- `model.table()` -- returns the table name.
- `model.toMap()` -- returns all attributes as a map.
- `model.attribute(key)` -- reads a cast or raw attribute value.
- `model.fill(values)` -- mass-assigns using `fillable()` and `guarded()` rules.
- `model.forceFill(values)` -- mass-assigns bypassing fillable/guarded (except primary key).

### Model overridable methods

- `tableName()` -- override to customize the table name (default: snake_case plural of class name).
- `primaryKey()` -- override to customize the primary key column (default: `id`).
- `fillable()` -- return `List.of("field1", "field2")` to allow only those fields.
- `guarded()` -- return `List.of("field")` to block specific fields (default blocks `id`).
- `casts()` -- return a map of attribute name to `Class` for automatic type conversion. Supports `Integer`, `Long`, `Boolean`, `LocalDate`, `LocalDateTime`, and enums.

## View

`View` is the facade for template rendering.

```java
View.use(new PebbleViewRenderer("templates/"));

HtmlResponse rendered = View.render("index", Map.of("title", "Hello"));
HtmlResponse withView = View.view("index", Map.of("title", "Hello"));
HtmlResponse simple = View.view("index", "title", "Hello", "count", 42);
```

- `View.use(renderer)` -- sets the active `ViewRenderer`.
- `View.render(template, data)` -- renders a template with a data map.
- `View.view(template)` -- renders a template with no data.
- `View.view(template, data)` -- alias for `render`.
- `View.view(template, "key1", val1, "key2", val2, ...)` -- varargs form for simple templates.

### ViewRenderer

```java
ViewRenderer renderer = (template, data) -> {
    String html = "<h1>" + data.get("title") + "</h1>";
    return new HtmlResponse(html);
};
View.use(renderer);
```

## Log

`Log` is the static logging facade.

```java
Log.use(new Slf4jLogger());
Log.info("Server started on port 8080");
Log.warn("Disk space is low");
Log.error("Failed to connect to database", exception);
```

- `Log.use(logger)` -- sets the active `Logger` implementation.
- `Log.info(message)` -- log an informational message.
- `Log.warn(message)` -- log a warning.
- `Log.error(message, throwable)` -- log an error with exception.

### Logger

```java
Logger logger = app.make(Logger.class);
logger.info("User created: " + userId);
logger.warn("Rate limit approaching for IP " + ip);
logger.error("Payment processing failed", paymentException);
```

## Json

`Json` provides static helpers for JSON responses.

```java
JsonResponse ok = Json.ok(Map.of("message", "success"));
JsonResponse err = Json.error("validation failed", 422);

String json = Json.stringify(Map.of("id", 1));
Map<?, ?> data = Json.parse(json, Map.class);
User user = Json.convert(data, User.class);
```

## Queue contracts

`io.javelin.core.queue` holds the queue contracts. Backends live in adapter modules.

```java
Job job = Job.of("emails.welcome", Map.of("to", "ada@example.com"));
Job restored = Job.parse(Json.stringify(job));
Map<?, ?> payload = restored.payload(Map.class);

RetryPolicy retry = new RetryPolicy(5, Duration.ofSeconds(2));
```

- `Job` is an immutable `id` / `subject` / JSON `payload` / `attempts` record with `of`, `parse`, `withAttempts` and typed `payload(Class<T>)`.
- `RetryPolicy` validates `maxAttempts >= 1` and a non-negative `backoff`; `RetryPolicy.DEFAULT` is 3 attempts / 1 second.
- `JobHandler.handle(Job)` is the handler contract; throwing signals a retry.
- `Queue` exposes `publish(Job)`, `subscribe(subject, handler, retryPolicy)` and `close()`.

## HttpMethod

```java
HttpMethod method = HttpMethod.GET;
switch (method) {
    case GET -> ...
    case POST -> ...
    case PUT -> ...
    case PATCH -> ...
    case DELETE -> ...
}
```

## ProviderLoader

`ProviderLoader` discovers and runs service providers from `META-INF/javelin/providers`.

```java
ProviderLoader loader = new ProviderLoader();
loader.load(app); // loads providers declared in META-INF
```

## Notes

- Keep registration explicit. No automatic scanning.
- Use `ServiceProvider` to wire adapters and application modules.
- Prefer deterministic constructor injection through the container.
- `Model` keeps the database dependency explicit — no hidden global state.
- Override `fillable()` or `guarded()` to control mass assignment. By default, the `id` field is guarded.
- Override `casts()` to normalize incoming strings into typed fields including `LocalDate`, `LocalDateTime`, `Integer`, `Long`, `Boolean`, and enum types.
