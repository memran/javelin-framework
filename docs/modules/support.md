# Support Module

Artifact: `io.javelin:javelin-support`

`javelin-support` provides small, strict helper facades for common string, array, file, image, HTTP client, AI client, object, collection, validation, HTML, and security tasks. All helpers are stateless and JDK-only.

## Collection

`Collection<T>` is an immutable, iterable wrapper around a list of values with fluent transformation methods.

### Creating collections

```java
Collection<String> names = Collection.of("Alice", "Bob", "Charlie");
Collection<String> fromList = Collection.from(List.of("a", "b"));
Collection<String> empty = Collection.empty();
```

### Querying

```java
Collection<String> c = Collection.of("a", "b", "c");

int size = c.size();       // 3
int count = c.count();     // 3
boolean empty = c.isEmpty();
String first = c.first();  // "a"
String last = c.last();    // "c"
boolean hasA = c.contains("a");
boolean hasAny = c.containsAny("a", "z"); // true
boolean hasAll = c.containsAll("a", "b");  // true
List<String> values = c.values();
```

### Transformation (all return new collections)

```java
Collection<Integer> numbers = Collection.of(1, null, 2, null, 3);

Collection<Integer> compacted = numbers.compact(); // [1, 2, 3]

Collection<String> mapped = numbers.map(n -> "n:" + n); // ["n:1", "n:null", ...]

Collection<Integer> flatMapped = Collection.of(List.of(1, 2), List.of(3))
    .flatMap(List::stream); // [1, 2, 3]

Collection<Integer> filtered = numbers.filter(n -> n != null && n > 1); // [2, 3]
Collection<Integer> rejected = numbers.reject(n -> n == null);          // [1, 2, 3]
Collection<Integer> uniqued = Collection.of(1, 1, 2, 2).unique();       // [1, 2]

Collection<Integer> reversed = Collection.of(1, 2, 3).reverse();        // [3, 2, 1]

Collection<Integer> taken = Collection.of(1, 2, 3, 4).take(2);          // [1, 2]
Collection<Integer> skipped = Collection.of(1, 2, 3, 4).skip(2);         // [3, 4]

Collection<Integer> chunked = Collection.of(1, 2, 3, 4, 5).chunk(2);
// [[1, 2], [3, 4], [5]]

Collection<Integer> sorted = Collection.of(3, 1, 2).sort(Comparator.naturalOrder()); // [1, 2, 3]
Collection<String> sortedBy = Collection.of("aaa", "a", "bb")
    .sortBy(String::length); // ["a", "bb", "aaa"]

Collection<Integer> merged = Collection.of(1, 2).concat(Collection.of(3, 4)); // [1, 2, 3, 4]
```

### Reduction

```java
Collection<Integer> nums = Collection.of(1, 2, 3, 4, 5);

double sum = nums.sum();                         // 15.0
double sumMapped = nums.sum(n -> n * 2);         // 30.0
OptionalDouble avg = nums.average();              // 3.0
OptionalDouble avgMapped = nums.average(n -> n * 2); // 6.0

Optional<Integer> min = nums.min(); // Optional[1]
Optional<Integer> max = nums.max(); // Optional[5]

String joined = Collection.of("a", "b", "c").join(", "); // "a, b, c"

Integer reduced = nums.reduce(0, (acc, n) -> acc + n); // 15
Optional<Integer> reduced2 = nums.reduce((a, b) -> a + b); // Optional[15]
```

### Plucking and grouping

```java
Collection<Map<String, Object>> users = Collection.of(
    Map.of("id", 1, "name", "Alice"),
    Map.of("id", 2, "name", "Bob")
);

Collection<Object> names = users.pluck(u -> u.get("name")); // ["Alice", "Bob"]

// pluckPath for nested data:
Collection<Map<String, Object>> data = Collection.of(
    Map.of("user", Map.of("name", "Alice")),
    Map.of("user", Map.of("name", "Bob"))
);
Collection<Object> plucked = data.pluckPath("user.name"); // ["Alice", "Bob"]

// Group by key:
Collection<Collection.Group<Integer, String>> byLength =
    Collection.of("a", "bb", "cc", "ddd")
        .groupBy(String::length);
// Group(key=1, values=["a"]), Group(key=2, values=["bb", "cc"]), Group(key=3, values=["ddd"])

// Key by:
Collection<Collection.Keyed<Integer, String>> keyed =
    Collection.of("Alice", "Bob")
        .keyBy(s -> s.length());
// Keyed(key=5, value="Alice"), Keyed(key=3, value="Bob")
```

### Partition

```java
Collection.Partition<Integer> partition =
    Collection.of(1, 2, 3, 4, 5).partition(n -> n > 3);

Collection<Integer> matching = partition.matching();       // [4, 5]
Collection<Integer> rejected = partition.rejected();        // [1, 2, 3]
```

### Converting to Map

```java
Collection<String> items = Collection.of("a", "bb", "ccc");

Map<Integer, String> byLength = items.toMap(String::length);
// {1="a", 2="bb", 3="ccc"}

Map<Integer, Integer> withValues = items.toMap(String::length, String::length);
// {1=1, 2=2, 3=3}
```

### Conditional chaining and debugging

```java
Collection<Integer> result = Collection.of(1, 2, 3)
    .tap(c -> System.out.println("Before filter: " + c.join(",")))
    .filter(n -> n > 1)
    .tap(c -> System.out.println("After filter: " + c.join(",")));

Collection<Integer> conditional = Collection.of(1, 2, 3, 4, 5)
    .when(true, c -> c.filter(n -> n > 3))   // applies: [4, 5]
    .unless(false, c -> c.filter(n -> n > 2)); // does not apply

String firstMatch = Collection.of("x", "y", "z").firstWhere(s -> s.equals("y")); // "y"
String withFallback = Collection.of("x", "z").firstWhere(s -> s.equals("y"), "default"); // "default"
```

### Flatten

```java
Collection<Object> nested = Collection.of(
    List.of(1, 2),
    Collection.of(3, 4),
    5
);
Collection<Object> flat = nested.flatten(); // [1, 2, 3, 4, 5]
```

### Record types

```java
Collection.Group<String, Integer> group = new Collection.Group<>("even", Collection.of(2, 4));
group.key();    // "even"
group.values(); // Collection[2, 4]

Collection.Keyed<Integer, String> keyed = new Collection.Keyed<>(3, "Bob");
keyed.key();   // 3
keyed.value(); // "Bob"

Collection.Partition<Integer> p = new Collection.Partition<>(
    Collection.of(4, 5), Collection.of(1, 2, 3)
);
p.matching(); // [4, 5]
p.rejected(); // [1, 2, 3]
```

## Input

`Input` wraps user input from requests with sanitization methods.

### Creating input

```java
Input input = Input.from(Map.of(
    "name", "  Alice  ",
    "email", "<b>alice@example.com</b>",
    "file", "../../etc/passwd",
    "tags", "java,clojure,scala",
    "age", "25",
    "active", "true"
));
Input empty = Input.empty();
```

### Reading values

```java
Map<String, String> allValues = input.values();
Optional<String> raw = input.raw("name");          // "  Alice  "

// Sanitized helpers:
Optional<String> text = input.text("name");         // "Alice" (trimmed, no HTML)
String withDefault = input.text("name", "Guest");   // "Alice" or "Guest"

// File paths:
Optional<String> filename = input.filename("file"); // "etc_passwd" (sanitized)
String filename = input.file("file", "default");    // alias for filename
String fileSafe = input.file("file", "default.txt");

// Arrays:
Collection<String> tags = input.array("tags"); // Collection["java", "clojure", "scala"]

// Typed:
Optional<Integer> age = input.integer("age");
int age = input.integer("age", 0);
Optional<Boolean> active = input.bool("active");
boolean active = input.bool("active", false);
```

### Transformation

```java
Input merged = input.merge(Map.of("role", "admin"));
Input subset = input.only("name", "email");
Input without = input.except("file");

Input tapped = input.tap(in -> System.out.println("Values: " + in.values()));
```

### Validation

```java
Validation validation = input.validate(); // uses default validation rules
// or with custom registry:
ValidationRuleRegistry registry = app.make(ValidationRuleRegistry.class);
Validation validation = input.validate(registry);
```

## Validation

`Validation` provides a fluent validation chain.

```java
Validation v = Validation.of(Input.from(Map.of(
    "name", "Al",
    "email", "not-an-email",
    "age", "150",
    "role", "superadmin"
)));

try {
    v.required("name")
        .required("email")
        .minLength("name", 3)
        .email("email")
        .integer("age")
        .between("age", 18, 120)
        .oneOf("role", List.of("admin", "user", "moderator"))
        .validate(); // throws if any validation fails
} catch (ValidationException e) {
    Map<String, List<String>> errors = e.errors();
    // errors: {email=[...], age=[...], role=[...]}
}
```

### Validation methods (all return `Validation` for chaining)

```java
Validation.of(input)
    .required("name")                                   // field must not be blank
    .requiredIf("tax_id", true)                         // required conditionally
    .minLength("password", 8)                           // minimum string length
    .maxLength("bio", 500)                              // maximum string length
    .length("username", 3, 20)                         // exact length range
    .integer("age")                                     // must be parseable as integer
    .between("age", 18, 120)                            // integer within range
    .matches("color", Pattern.compile("#[0-9a-fA-F]{6}")) // regex match
    .email("email")                                     // RFC-like email pattern
    .oneOf("role", List.of("admin", "user"))            // must be in allowed set
    .pathExists("upload_dir")                           // filesystem path exists
    .fileExists("config_path")                          // file exists
    .directoryExists("output_dir")                      // directory exists
    .readableFile("log_path")                           // file is readable
    .writableFile("cache_path")                         // file is writable
    .hasExtension("avatar", "jpg", "png")               // file extension check
    .maxBytes("upload", 5_242_880)                      // max file size
    .rule(customRule1, customRule2)                     // custom composed rules
    .custom(myRule)                                     // single custom rule
    .rule("adult_age")                                  // registry-loaded rule by name
    .validate();                                        // run validation (throws or returns Input)
```

### Getting errors without exception

```java
Validation v = Validation.of(input)
    .required("email")
    .email("email");

Map<String, List<String>> errors = v.errors(); // empty if valid, populated if not
Input validated = v.validate(); // throws if invalid
```

## ValidationRule

`ValidationRule` is the abstract base for reusable validation rules.

```java
// Custom rule as a subclass:
public final class AdultAgeRule extends ValidationRule {
    public String name() { return "adult_age"; }
    public String key()  { return "age"; }

    protected boolean passes(Input input) {
        return input.integer("age").map(a -> a >= 18).orElse(false);
    }

    protected String message() {
        return "You must be at least 18 years old";
    }
}

// Using the rule:
ValidationRule rule = new AdultAgeRule();
Optional<String> error = rule.validate(input); // Optional.empty() if passes, else error message
```

### Ad-hoc rules

```java
ValidationRule evenRule = ValidationRule.of(
    "even", "number",
    input -> input.integer("number").map(n -> n % 2 == 0).orElse(false),
    "Number must be even"
);
```

### Rule composition

```java
ValidationRule combined = rule1.and(rule2);  // passes only if both pass
ValidationRule either = rule1.or(rule2);     // passes if either passes
ValidationRule not = rule1.negate("Must not be adult");  // inverts
ValidationRule named = rule1.named("custom_name");       // override name
ValidationRule rekeyed = rule1.onKey("other_field");      // override field key
```

## ValidationRuleRegistry

`ValidationRuleRegistry` stores rules for lookup by name.

```java
ValidationRuleRegistry registry = new ValidationRuleRegistry();

registry.register(new AdultAgeRule());
registry.register(ValidationRule.of("strong_password", "password",
    input -> input.text("password").map(p -> p.length() >= 8).orElse(false),
    "Password must be at least 8 characters"
));

Optional<ValidationRule> rule = registry.find("adult_age");
List<ValidationRule> allRules = registry.all();
```

## Str

String utility methods.

```java
Str.isBlank(null);           // true
Str.isBlank("");             // true
Str.isBlank("  ");           // true
Str.isNotBlank("hello");     // true

Str.trimToNull("  ");        // null
Str.trimToNull(" hi ");      // "hi"
Str.defaultIfBlank("", "default");   // "default"
Str.defaultIfBlank("hello", "default"); // "hello"

Str.toSlug("Hello World!");         // "hello-world"
Str.toSnakeCase("userName");        // "user_name"
Str.toCamelCase("user_name");       // "userName"
Str.capitalize("hello");            // "Hello"
Str.repeat("ab", 3);                // "ababab"

Str.mask("alice@example.com", 2, 4, '*');
// "al******************om"
// shows first 2 and last 4 chars, masks the rest with '*'
```

## Arr

Array utility methods.

```java
String[] arr = {"a", "b", null, "c"};

Arr.isEmpty(arr);              // false
Arr.isNotEmpty(arr);           // true
Arr.first(arr);                // "a"
Arr.last(arr);                 // "c"
Arr.contains(arr, "b");        // true

List<String> list = Arr.toList(arr);     // ["a", "b", null, "c"]
List<String> compact = Arr.compact(arr); // ["a", "b", "c"]

List<List<String>> chunks = Arr.chunk(arr, 2);
// [["a", "b"], [null, "c"]]

List<String> listChunk = Arr.chunk(List.of("a", "b", "c", "d"), 2);
// [["a", "b"], ["c", "d"]]
```

## File

File system utilities.

```java
Path dir = File.ensureDirectory(Path.of("storage/logs"));
Path parent = File.ensureParentDirectory(Path.of("storage/logs/app.log"));

String content = File.readString(Path.of("config/app.yaml"));
File.writeString(Path.of("output.txt"), "Hello World");

String ext = File.extension(Path.of("document.pdf"));  // "pdf"
String base = File.baseName(Path.of("/path/file.txt")); // "file"
boolean isPdf = File.hasExtension(Path.of("doc.pdf"), "pdf"); // true

Path safePath = File.safeResolve(Path.of("/base"), "sub", "file.txt");
// /base/sub/file.txt — prevents path traversal

Path safeResolved = File.safeResolve(Path.of("/base"), "../../etc/passwd");
// /base/etc/passwd — traversal is neutralized
```

## Image

Image inspection utilities.

```java
Path imagePath = Path.of("photo.jpg");

boolean isImage = Image.isImage(imagePath);
Optional<Image.Info> info = Image.info(imagePath);

if (info.isPresent()) {
    int w = info.get().width();
    int h = info.get().height();
    String fmt = info.get().format();  // "JPEG", "PNG", etc.
    Image.Dimensions dims = info.get().dimensions();
    int dw = dims.width();
    int dh = dims.height();
}

Image.Dimensions dims2 = Image.dimensions(imagePath);
BufferedImage buf = Image.read(imagePath);
```

### Fluent Image.Handle

```java
Image.Handle handle = Image.of(imagePath);

handle.exists();       // boolean
handle.isImage();      // boolean
handle.path();         // Path
handle.width();        // int
handle.height();       // int
handle.format();       // String ("JPEG", "PNG", ...)
handle.info();         // Optional<Image.Info>
handle.dimensions();   // Image.Dimensions
handle.read();         // BufferedImage

Image.Handle checked = Image.of(imagePath)
    .when(isProduction, img -> img /* validate dimensions */)
    .unless(isProduction, img -> img /* skip check in dev */);
```

## Http

HTTP client utility using JDK `HttpClient`.

```java
Http.Handle client = Http.of("https://api.example.com");
// or
Http.Handle client = Http.client().baseUrl("https://api.example.com");

// GET
Http.Response response = client.get("/users");
int status = response.status();
Map<String, List<String>> headers = response.headers();
byte[] body = response.body();
String text = response.bodyText();
boolean ok = response.successful(); // status 2xx
Optional<String> ct = response.contentType();
Optional<String> header = response.header("Content-Type");

// Convenience methods
Http.Response json = client.getJson("/users/1");
Http.Response head = client.head("/resource");
Http.Response deleted = client.delete("/users/1");

// POST/PUT/PATCH with JSON body
Http.Response created = client.postJson("/users", "{\"name\":\"Alice\"}");
Http.Response updated = client.putJson("/users/1", "{\"name\":\"Bob\"}");
Http.Response patched = client.patchJson("/users/1", "{\"name\":\"Bob\"}");

// Form and text
Http.Response form = client.postForm("/login", Map.of("username", "alice", "password", "secret"));
Http.Response textResp = client.postText("/echo", "Hello");

// Download to file
Path saved = client.download("/files/report.pdf", Path.of("downloads/report.pdf"));

// Custom request
Http.Response custom = client.send("GET", "/path", null, null);

// Configuration
Http.Handle configured = Http.of("https://api.example.com")
    .timeout(Duration.ofSeconds(10))
    .withHeader("Authorization", "Bearer token123")
    .withHeaders(Map.of("X-Custom", "value"))
    .when(useRetry, c -> c.timeout(Duration.ofSeconds(30)))
    .unless(debug, c -> c.withHeader("X-Debug", "off"));
```

## Ai

AI client utility for OpenAI-compatible chat APIs.

```java
// From config file (generated by ai:install)
Ai.Handle ai = Ai.from(Path.of(".javelin/ai.properties"));

// Or configure manually
Ai.Handle ai = Ai.of("https://api.openai.com")
    .chatPath("/v1/chat/completions")
    .provider("openai")
    .model("gpt-4")
    .apiKey("sk-...")
    .system("You are a helpful assistant.")
    .temperature(0.7)
    .timeout(Duration.ofSeconds(60))
    .withHeader("X-Custom", "value");
```

### Non-streaming chat

```java
Ai.Reply reply = ai.chat("What is Javelin?");

reply.provider();     // "openai"
reply.model();        // "gpt-4"
reply.text();         // "Javelin is a Java web framework..."
reply.rawPayload();   // raw JSON response
reply.streamed();     // false

// With message history
List<Ai.Message> messages = List.of(
    Ai.Message.system("You are a helpful assistant."),
    Ai.Message.user("What is Javelin?"),
    Ai.Message.assistant("Javelin is a Java web framework..."),
    Ai.Message.user("Show me an example")
);
Ai.Reply reply2 = ai.chat(messages);
```

### Streaming chat

```java
StringBuilder full = new StringBuilder();
Ai.Reply reply = ai.stream("Write a poem", token -> {
    full.append(token);
    System.out.print(token); // print tokens as they arrive
});
// full.toString() contains the complete response
reply.streamed(); // true

// Streaming with message history
Ai.Reply streamed = ai.stream(messages, System.out::print);
```

### Conditional configuration

```java
Ai.Handle handle = Ai.client()
    .baseUrl("https://api.openai.com")
    .model("gpt-4")
    .when(isStreaming, h -> h.temperature(0.8))
    .unless(useCache, h -> h.timeout(Duration.ofSeconds(5)));
```

## Date

Date utility methods (all UTC-based).

```java
LocalDate today = Date.today();
LocalDateTime now = Date.now();

// Parsing
Optional<LocalDate> parsed = Date.parseDate("2024-01-15");
Optional<LocalDateTime> parsedDt = Date.parseDateTime("2024-01-15T10:30:00");

LocalDate required = Date.requireDate("2024-01-15", "Invalid date");
LocalDateTime requiredDt = Date.requireDateTime("2024-01-15T10:30:00", "Invalid datetime");

// Formatting
String formatted = Date.format(LocalDate.now());       // "2024-01-15"
String formattedDt = Date.format(LocalDateTime.now()); // "2024-01-15T10:30:00"

// Manipulation
LocalDate later = Date.addDays(today, 7);
LocalDate earlier = Date.subtractDays(today, 3);

// Comparison
boolean past = Date.isPast(today.minusDays(1));   // true
boolean future = Date.isFuture(today.plusDays(1)); // true
```

## Obj

Object utility methods.

```java
String value = Obj.coalesce(null, "default");           // "default"
String value2 = Obj.coalesce(null, () -> compute());    // lazy fallback

boolean anyMatch = Obj.equalsAny("b", "a", "b", "c");  // true

int hash = Obj.hash("a", 42, null);    // combined hash of all values

boolean isString = Obj.isType("hello", String.class);   // true
String casted = Obj.requireType(obj, String.class); // casts or throws
```

## Validator

Precondition checking utilities.

```java
Validator.require(age >= 18, "Must be adult");
Validator.requireState(isInitialized, "Not initialized");

String nonNull = Validator.requireNonNull(name, "name is required");
String nonBlank = Validator.requireNonBlank(input, "input must not be blank");
String exactLen = Validator.requireLength("abc", 1, 10, "length out of range");

int between = Validator.requireBetween(25, 18, 120, "age out of range");
long betweenL = Validator.requireBetween(1000L, 1L, 10000L, "value out of range");

String matching = Validator.requireMatches("abc123",
    Pattern.compile("\\w+"), "must be alphanumeric");

String choice = Validator.requireOneOf("admin",
    List.of("admin", "user"), "invalid role");

Collection<String> nonEmpty = Validator.requireNotEmpty(List.of("a"), "list is empty");
Map<String, String> nonEmptyMap = Validator.requireNotEmpty(Map.of("k", "v"), "map is empty");
```

## Html

HTML sanitization utilities.

```java
String escaped = Html.escape("<script>alert('xss')</script>");
// "&lt;script&gt;alert(&#x27;xss&#x27;)&lt;/script&gt;"

String stripped = Html.stripTags("<p>Hello <b>World</b></p>");
// "Hello World"
```

## Security

Security-oriented utility methods.

```java
boolean equal = Security.constantTimeEquals("token123", "token123"); // true
boolean notEqual = Security.constantTimeEquals("token123", "token456");

String hash = Security.sha256Hex("hello");
// "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824"

String token = Security.randomToken(32); // 32 random bytes, hex-encoded = 64 char string

String safe = Security.sanitizeFilename("../../etc/passwd");
// "etc_passwd" — removes path traversal, replaces non-alphanumeric
```

## Notes

- All helpers are stateless and JDK-only.
- Use them when you want Laravel-style convenience without hidden framework behavior.
- `ValidationRule` subclasses placed in `app/validation/` are auto-loaded by the starter.
- `Collection`, `Input`, and `Validation` are designed for fluent chaining — each method returns a new instance.
