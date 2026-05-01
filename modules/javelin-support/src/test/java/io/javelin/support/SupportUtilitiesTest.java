package io.javelin.support;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SupportUtilitiesTest {
    @Test
    void stringSupportCoversCommonTransforms() {
        assertTrue(Str.isBlank("  "));
        assertFalse(Str.isNotBlank("  "));
        assertNull(Str.trimToNull("  "));
        assertEquals("fallback", Str.defaultIfBlank("", "fallback"));
        assertEquals("javelin-framework", Str.toSlug("Javelin Framework"));
        assertEquals("javelinFramework", Str.toCamelCase("javelin framework"));
        assertEquals("javelin_framework", Str.toSnakeCase("Javelin Framework"));
        assertEquals("Javelin", Str.capitalize("javelin"));
        assertEquals("***", Str.repeat("*", 3));
        assertEquals("se**et", Str.mask("secret", 2, 2, '*'));
    }

    @Test
    void arraySupportHandlesNullableArrays() {
        String[] values = {"alpha", null, "beta", "gamma"};

        assertFalse(Arr.isEmpty(values));
        assertEquals("alpha", Arr.first(values));
        assertEquals("gamma", Arr.last(values));
        assertTrue(Arr.contains(values, "beta"));
        assertEquals(Arrays.asList("alpha", null, "beta", "gamma"), Arr.toList(values));
        assertEquals(List.of("alpha", "beta", "gamma"), Arr.compact(values));
        assertEquals(Arrays.asList(Arrays.asList("alpha", null), Arrays.asList("beta", "gamma")), Arr.chunk(values, 2));
    }

    @Test
    void fileSupportReadsWritesAndGuardsPaths() throws Exception {
        Path root = Files.createTempDirectory("javelin-support");
        Path nested = File.safeResolve(root, "nested", "note.txt");
        File.writeString(nested, "hello");

        assertTrue(Files.exists(nested));
        assertEquals("hello", File.readString(nested));
        assertEquals("txt", File.extension(nested));
        assertEquals("note", File.baseName(nested));
        assertTrue(File.hasExtension(nested, ".txt"));
        assertThrows(IllegalArgumentException.class, () -> File.safeResolve(root, "..", "escape.txt"));
    }

    @Test
    void imageSupportDetectsAndReadsImageMetadata() throws Exception {
        Path root = Files.createTempDirectory("javelin-support-image");
        Path imageFile = root.resolve("logo.png");
        BufferedImage image = new BufferedImage(2, 3, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(image, "png", imageFile.toFile());

        assertTrue(Image.isImage(imageFile));
        assertTrue(Image.of(imageFile).isImage());
        assertTrue(Image.of(imageFile).exists());
        assertEquals(imageFile, Image.of(imageFile).path());
        assertEquals(new Image.Dimensions(2, 3), Image.dimensions(imageFile));
        assertEquals(new Image.Dimensions(2, 3), Image.of(imageFile).dimensions());
        assertEquals(2, Image.info(imageFile).orElseThrow().width());
        assertEquals(3, Image.info(imageFile).orElseThrow().height());
        assertEquals("png", Image.info(imageFile).orElseThrow().format());
        assertEquals(2, Image.of(imageFile).width());
        assertEquals(3, Image.of(imageFile).height());
        assertEquals("png", Image.of(imageFile).format());
        assertEquals(2, Image.read(imageFile).getWidth());
        assertEquals(3, Image.read(imageFile).getHeight());
        assertEquals(2, Image.of(imageFile).read().getWidth());
    }

    @Test
    void httpSupportPerformsFluentRequests() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/ping", exchange -> respond(exchange, 200, "pong", "text/plain; charset=utf-8"));
        server.createContext("/json", exchange -> {
            String accept = exchange.getRequestHeaders().getFirst("Accept");
            respond(exchange, 200, accept == null ? "{}" : "{\"accept\":\"" + accept + "\"}", "application/json");
        });
        server.createContext("/echo", exchange -> {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            respond(exchange, 200, exchange.getRequestMethod() + ":" + body, "text/plain; charset=utf-8");
        });
        server.createContext("/form", exchange -> {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            respond(exchange, 200, decodeForm(body), "text/plain; charset=utf-8");
        });
        server.createContext("/header", exchange -> {
            String value = exchange.getRequestHeaders().getFirst("X-Test");
            respond(exchange, 200, value == null ? "missing" : value, "text/plain; charset=utf-8");
        });
        server.createContext("/download", exchange -> respond(exchange, 200, "downloaded", "application/octet-stream"));
        server.start();

        try {
            String baseUrl = "http://127.0.0.1:" + server.getAddress().getPort();
            Http.Response ping = Http.of(baseUrl)
                    .timeout(Duration.ofSeconds(5))
                    .get("/ping");
            Http.Response json = Http.of(baseUrl).getJson("/json");
            Http.Response echo = Http.of(baseUrl)
                    .withHeader("X-Test", "alpha")
                    .postText("/echo", "hello");
            Http.Response form = Http.of(baseUrl)
                    .postForm("/form", Map.of("name", "Alice", "role", "admin"));
            Http.Response header = Http.of(baseUrl)
                    .withHeaders(Map.of("X-Test", "bravo"))
                    .get("/header");
            Path downloadTarget = Files.createTempDirectory("javelin-http").resolve("download.bin");
            Path downloaded = Http.of(baseUrl).download("/download", downloadTarget);

            assertEquals(200, ping.status());
            assertTrue(ping.successful());
            assertEquals("pong", ping.bodyText());
            assertEquals("text/plain; charset=utf-8", ping.contentType().orElseThrow());

            assertEquals(200, json.status());
            assertEquals("{\"accept\":\"application/json\"}", json.bodyText());

            assertEquals(200, echo.status());
            assertEquals("POST:hello", echo.bodyText());

            assertEquals(200, form.status());
            assertTrue(form.bodyText().contains("name=Alice"));
            assertTrue(form.bodyText().contains("role=admin"));

            assertEquals(200, header.status());
            assertEquals("bravo", header.bodyText());
            assertEquals("alpha", Http.of(baseUrl).withHeader("X-Test", "alpha").get("/header").bodyText());

            assertEquals(downloadTarget, downloaded);
            assertEquals("downloaded", Files.readString(downloaded));
        } finally {
            server.stop(0);
        }
    }

    @Test
    void aiSupportChatsAndStreamsAgainstAnOpenaiCompatibleEndpoint() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/v1/chat/completions", exchange -> {
            String request = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            String response;
            if (request.contains("\"stream\":true")) {
                response = """
                        data: {"choices":[{"delta":{"content":"hel"}}]}

                        data: {"choices":[{"delta":{"content":"lo"}}]}

                        data: [DONE]
                        """;
                respond(exchange, 200, response, "text/event-stream; charset=utf-8");
            } else {
                response = """
                        {"choices":[{"message":{"content":"hello"}}]}
                        """;
                respond(exchange, 200, response, "application/json");
            }
        });
        server.start();

        try {
            Path configDir = Files.createTempDirectory("javelin-ai");
            Path config = configDir.resolve("ai.properties");
            Files.writeString(config, """
                    provider=openai-compatible
                    base_url=http://127.0.0.1:%d/v1
                    chat_path=/chat/completions
                    model=test-model
                    api_key=secret
                    system=Be helpful
                    timeout_seconds=5
                    """.formatted(server.getAddress().getPort()));

            Ai.Handle ai = Ai.from(config);
            Ai.Reply reply = ai.chat("hello");
            List<String> chunks = new ArrayList<>();
            Ai.Reply streamed = ai.stream("hello", chunks::add);

            assertEquals("hello", reply.text());
            assertFalse(reply.streamed());
            assertEquals("hello", streamed.text());
            assertTrue(streamed.streamed());
            assertEquals(List.of("hel", "lo"), chunks);
        } finally {
            server.stop(0);
        }
    }

    private static String decodeForm(String body) {
        return java.util.Arrays.stream(body.split("&"))
                .map(part -> part.split("=", 2))
                .map(pair -> pair.length == 2
                        ? URLDecoder.decode(pair[0], StandardCharsets.UTF_8) + "=" + URLDecoder.decode(pair[1], StandardCharsets.UTF_8)
                        : "")
                .filter(value -> !value.isBlank())
                .sorted()
                .reduce((left, right) -> left + "," + right)
                .orElse("");
    }

    private static void respond(HttpExchange exchange, int status, String body, String contentType) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        } finally {
            exchange.close();
        }
    }

    @Test
    void dateSupportParsesFormatsAndComparesDates() {
        LocalDate date = LocalDate.of(2026, 5, 1);
        LocalDateTime dateTime = LocalDateTime.of(2026, 5, 1, 13, 45, 12);

        assertEquals(date, Date.requireDate("2026-05-01", "invalid date"));
        assertEquals(dateTime, Date.requireDateTime("2026-05-01T13:45:12", "invalid datetime"));
        assertEquals("2026-05-01", Date.format(date));
        assertEquals("2026-05-01T13:45:12", Date.format(dateTime));
        assertEquals(LocalDate.of(2026, 5, 6), Date.addDays(date, 5));
        assertEquals(LocalDate.of(2026, 4, 26), Date.subtractDays(date, 5));
        assertTrue(Date.parseDate("2026-05-01").isPresent());
        assertTrue(Date.parseDateTime("2026-05-01T13:45:12").isPresent());
        assertTrue(Date.parseDate("not-a-date").isEmpty());
    }

    @Test
    void objectSupportCoversNullFallbackAndTypeChecks() {
        assertEquals("fallback", Obj.coalesce(null, "fallback"));
        assertEquals("value", Obj.coalesce("value", () -> "fallback"));
        assertTrue(Obj.equalsAny("b", "a", "b", "c"));
        assertTrue(Obj.isType("value", String.class));
        assertEquals("value", Obj.requireType("value", String.class));
        assertThrows(IllegalArgumentException.class, () -> Obj.requireType(1, String.class));
        assertNotNull(Obj.hash("a", 1, true));
    }

    @Test
    void validationSupportRejectsInvalidValues() {
        assertEquals("text", Validator.requireNonBlank("text", "missing"));
        assertEquals(5, Validator.requireBetween(5, 1, 10, "out of range"));
        assertEquals("abc", Validator.requireMatches("abc", Pattern.compile("[a-z]+"), "pattern"));
        assertEquals("green", Validator.requireOneOf("green", List.of("red", "green"), "allowed"));
        assertThrows(IllegalArgumentException.class, () -> Validator.requireNonBlank("  ", "missing"));
        assertThrows(IllegalArgumentException.class, () -> Validator.requireBetween(0, 1, 10, "out of range"));
    }

    @Test
    void htmlSupportEscapesAndStripsTags() {
        assertEquals("&lt;tag attr=&quot;1&quot;&gt;&amp;&#39;", Html.escape("<tag attr=\"1\">&'"));
        assertEquals("Hello world", Html.stripTags("<p>Hello <strong>world</strong></p>"));
    }

    @Test
    void securitySupportHashesTokensAndSanitizesNames() {
        assertTrue(Security.constantTimeEquals("secret", "secret"));
        assertFalse(Security.constantTimeEquals("secret", "SECRET"));
        assertEquals(Security.sha256Hex("abc"), Security.sha256Hex("abc"));
        assertEquals("_CON.txt", Security.sanitizeFilename("../../CON.txt"));
        assertTrue(Security.randomToken(16).length() > 0);
    }

    @Test
    void inputSupportSanitizesAndFiltersValues() {
        Map<String, String> values = new java.util.LinkedHashMap<>();
        values.put("name", " <b>Alice</b> ");
        values.put("age", " 29 ");
        values.put("active", "yes");
        values.put("file", "../../CON.txt");
        values.put("tags", "one, <b>two</b> , three");
        values.put("bio", "<p>Hello</p>");
        values.put("empty", "   ");
        Input input = Input.from(values);

        assertEquals("Alice", input.text("name").orElseThrow());
        assertEquals("fallback", input.text("missing", "fallback"));
        assertEquals(29, input.integer("age").orElseThrow());
        assertTrue(input.bool("active").orElseThrow());
        assertEquals("_CON.txt", input.filename("file").orElseThrow());
        assertEquals("_CON.txt", input.file("file").orElseThrow());
        assertEquals(List.of("one", "two", "three"), input.array("tags").values());
        assertEquals(List.of("name", "age", "active", "file", "tags", "bio", "empty"), new ArrayList<>(input.values().keySet()));
        assertEquals(List.of("name", "age"), new ArrayList<>(input.only("name", "age").values().keySet()));
        assertEquals(List.of("age", "active", "file", "tags", "bio", "empty"), new ArrayList<>(input.except("name").values().keySet()));
    }

    @Test
    void validationSupportChainsRulesAndFailsFast() {
        Input input = Input.from(Map.of(
                "name", "<b>Alice</b>",
                "email", "alice@example.com",
                "age", "29",
                "role", "admin"
        ));

        Validation validation = Validation.of(input)
                .required("name")
                .requiredIf("role", true)
                .minLength("name", 3)
                .email("email")
                .integer("age")
                .between("age", 18, 40)
                .oneOf("role", List.of("admin", "editor"))
                .matches("name", Pattern.compile("[A-Za-z]+"));

        assertEquals(input, validation.validate());
        assertTrue(validation.errors().isEmpty());

        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class, () ->
                Validation.of(Input.from(Map.of("email", "not-an-email")))
                        .required("name")
                        .requiredIf("role", true)
                        .email("email")
                        .validate()
        );

        assertTrue(failure.getMessage().contains("name"));
    }

    @Test
    void validationSupportLoadsCustomRulesFromARegistry() {
        ValidationRuleRegistry registry = new ValidationRuleRegistry();
        registry.register(new AdultAgeRule());

        Input input = Input.from(Map.of("age", "21"));
        assertEquals(input, Validation.of(input, registry).rule("adult-age").validate());
        assertTrue(registry.find("adult-age").isPresent());
        assertEquals(1, registry.all().size());
    }

    @Test
    void validationSupportComposesRules() {
        ValidationRule adult = ValidationRule.of(
                "adult-age",
                "age",
                input -> input.integer("age").orElse(0) >= 18,
                "must be 18 or older"
        );
        ValidationRule requiredAdult = ValidationRule.of(
                "required-age",
                "age",
                input -> input.integer("age").isPresent(),
                "is required"
        );

        Input valid = Input.from(Map.of("age", "21"));
        Input invalid = Input.from(Map.of("age", "16"));

        assertTrue(adult.and(requiredAdult).validate(valid).isEmpty());
        assertTrue(adult.and(requiredAdult).validate(invalid).isPresent());
        assertTrue(adult.negate("must not be adult").validate(invalid).isEmpty());
        assertTrue(adult.named("age-adult").validate(valid).isEmpty());
        assertTrue(adult.onKey("years").validate(valid).isEmpty());
    }

    @Test
    void validationSupportChecksFilePathsAndExtensions() throws Exception {
        Path root = Files.createTempDirectory("javelin-validation-files");
        Path file = root.resolve("avatar.png");
        Path directory = root.resolve("uploads");
        Files.writeString(file, "png");
        Files.createDirectories(directory);

        Input input = Input.from(Map.of(
                "file", file.toString(),
                "directory", directory.toString(),
                "missing", root.resolve("missing.txt").toString(),
                "wrong", root.resolve("wrong.exe").toString()
        ));

        Validation valid = Validation.of(input)
                .fileExists("file")
                .readableFile("file")
                .writableFile("file")
                .directoryExists("directory")
                .pathExists("file")
                .hasExtension("file", "png", "jpg")
                .maxBytes("file", 1024);

        assertEquals(input, valid.validate());
        assertTrue(valid.errors().isEmpty());

        IllegalArgumentException failure = assertThrows(IllegalArgumentException.class, () ->
                Validation.of(input)
                        .fileExists("missing")
                        .directoryExists("file")
                        .hasExtension("wrong", "png")
                        .maxBytes("file", 1)
                        .validate()
        );

        assertTrue(failure.getMessage().contains("missing"));
        assertTrue(failure.getMessage().contains("file"));
    }

    @Test
    void collectionSupportChainsImmutableOperations() {
        Collection<String> collection = Collection.of("alice", null, "bob", "alice");

        Collection<String> transformed = collection
                .compact()
                .map(String::toUpperCase)
                .filter(value -> value.startsWith("A") || value.startsWith("B"))
                .unique()
                .concat(Collection.of("CAROL"))
                .reverse();

        assertEquals(4, collection.size());
        assertEquals("alice", collection.first());
        assertEquals("alice", collection.last());
        assertEquals(4, collection.count());
        assertFalse(collection.isEmpty());
        assertTrue(collection.contains(null));
        assertThrows(UnsupportedOperationException.class, () -> collection.values().add("x"));
        assertEquals(List.of("CAROL", "BOB", "ALICE"), transformed.values());
        assertEquals("CAROL,BOB,ALICE", transformed.join(","));
        assertEquals(List.of("CAROL", "BOB"), transformed.take(2).values());
        assertEquals(List.of("ALICE"), transformed.skip(2).values());
        assertEquals(2, transformed.chunk(2).size());
        assertEquals(List.of("CAROL", "BOB"), transformed.chunk(2).first().values());
        assertEquals(List.of("ALICE"), transformed.chunk(2).last().values());
        assertEquals(List.of("ALICE", "BOB", "CAROL"), transformed.sort(String::compareToIgnoreCase).values());
        assertEquals(List.of("a", "cc", "bbb"), Collection.of("bbb", "a", "cc").sortBy(String::length).values());
        assertEquals("lpha", Collection.of("alpha", "beta", "gamma").pluck(value -> value.substring(1)).first());
        assertEquals(2, Collection.of("a", "b", "c").when(true, values -> values.take(2)).count());
        assertEquals(2, Collection.of("a", "b", "c").when(true, values -> values.take(2)).size());
        assertEquals(3, Collection.of("a", "b", "c").unless(true, values -> values.take(2)).size());
        assertEquals(List.of("a", "b"), Collection.of("a", "b", "c").tap(values -> assertEquals(3, values.size())).take(2).values());
    }

    @Test
    void collectionSupportGroupsAndKeysValues() {
        Collection<String> names = Collection.of("alice", "adam", "bob", "bruce", "carol");

        Collection<Collection.Group<Character, String>> grouped = names.groupBy(name -> name.charAt(0));
        Collection<Collection.Keyed<Integer, String>> keyed = names.keyBy(String::length);

        assertEquals(3, grouped.size());
        assertEquals(Character.valueOf('a'), grouped.first().key());
        assertEquals(List.of("alice", "adam"), grouped.first().values().values());
        assertEquals(Character.valueOf('c'), grouped.last().key());
        assertEquals(List.of("carol"), grouped.last().values().values());

        assertEquals(5, keyed.size());
        assertEquals(Integer.valueOf(5), keyed.first().key());
        assertEquals("alice", keyed.first().value());
        assertEquals(Integer.valueOf(5), keyed.last().key());
        assertEquals("carol", keyed.last().value());

        Collection<String> filtered = names.only(name -> name.contains("o")).except(name -> name.startsWith("c"));
        assertEquals(List.of("bob"), filtered.values());
        assertEquals(List.of("alice", "adam", "bob", "bruce", "carol"), names.pluck(value -> value).values());
    }

    @Test
    void collectionSupportFlattensPartitionsAndFindsFirstMatches() {
        Collection<Object> nested = Collection.of(
                List.of("alice", "bob"),
                Collection.of("carol", "dave"),
                new String[]{"erin"},
                null
        );

        Collection<Object> flattened = nested.flatten();
        Collection<Object> flatMapped = Collection.of("alpha", "beta")
                .flatMap(value -> List.<Object>of(value, value.length()));
        Collection.Partition<String> partition = Collection.of("alpha", "beta", "gamma", "delta")
                .partition(value -> value.startsWith("a"));

        assertEquals(Arrays.asList("alice", "bob", "carol", "dave", "erin", null), flattened.values());
        assertEquals(List.of("alpha", 5, "beta", 4), flatMapped.values());
        assertEquals(List.of("alpha"), partition.matching().values());
        assertEquals(List.of("beta", "gamma", "delta"), partition.rejected().values());
        assertEquals("beta", Collection.of("alpha", "beta", "gamma").firstWhere(value -> value.startsWith("b")));
        assertEquals("fallback", Collection.of("alpha", "beta").firstWhere(value -> value.startsWith("z"), "fallback"));
        assertNull(Collection.of("alpha").firstWhere(value -> value.startsWith("z")));
    }

    @Test
    void collectionSupportAggregatesValues() {
        Collection<Integer> numbers = Collection.of(2, 4, 6, 8);
        Collection<String> names = Collection.of("delta", "beta", "gamma", "alpha");

        assertEquals(20.0d, numbers.sum());
        assertEquals(5.0d, Collection.of(2, 4, 6, 8).average().orElseThrow());
        assertTrue(Collection.empty().average().isEmpty());
        assertEquals(8, numbers.max().orElseThrow());
        assertEquals(2, numbers.min().orElseThrow());
        assertEquals("delta-beta-gamma-alpha", names.reduce((left, right) -> left + "-" + right).orElseThrow());
        assertEquals("seed-delta-beta-gamma-alpha", names.reduce("seed", (left, right) -> left + "-" + right));
        assertEquals(20.0d, numbers.sum(number -> number.doubleValue()));
        assertEquals(5.0d, numbers.average(number -> number.doubleValue()).orElseThrow());
    }

    @Test
    void collectionSupportChecksMembershipAndProjectsMaps() {
        Collection<String> names = Collection.of("alice", "bob", "carol");

        Map<String, Integer> byInitial = names.toMap(value -> value.substring(0, 1), String::length);
        Collection<Object> people = Collection.of(
                Map.of("name", "alice", "profile", Map.of("city", "dhaka")),
                new Person("bob", new Profile("london")),
                Map.of("name", "carol", "profile", Map.of("city", "rome"))
        );

        assertTrue(names.containsAny("zoe", "bob"));
        assertTrue(names.containsAll("alice", "bob"));
        assertFalse(names.containsAll("alice", "zoe"));
        assertEquals(List.of("a", "b", "c"), new ArrayList<>(byInitial.keySet()));
        assertEquals(Integer.valueOf(5), byInitial.get("a"));
        assertEquals(List.of(5, 3, 5), new ArrayList<>(names.toMap(value -> value.substring(0, 1), String::length).values()));
        assertEquals(List.of("dhaka", "london", "rome"), people.pluckPath("profile.city").values());
        assertEquals(List.of("alice", "bob", "carol"), people.pluckPath("name").values());
    }

    private record Person(String name, Profile profile) {
    }

    private record Profile(String city) {
    }

    private static final class AdultAgeRule extends ValidationRule {
        @Override
        public String name() {
            return "adult-age";
        }

        @Override
        public String key() {
            return "age";
        }

        @Override
        protected boolean passes(Input input) {
            return input.integer("age").orElse(0) >= 18;
        }

        @Override
        protected String message() {
            return "must be 18 or older";
        }
    }
}
