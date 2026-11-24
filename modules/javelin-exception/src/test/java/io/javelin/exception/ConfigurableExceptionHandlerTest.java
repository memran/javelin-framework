package io.javelin.exception;

import io.javelin.core.HttpMethod;
import io.javelin.core.Request;
import io.javelin.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ConfigurableExceptionHandlerTest {
    @Test
    void productionUsesCustomPageWhenPresent(@TempDir Path dir) throws Exception {
        Files.writeString(dir.resolve("500.html"), "<h1>Custom failure</h1>");
        ConfigurableExceptionHandler handler = new ConfigurableExceptionHandler(
                false, SensitiveDataRedactor.defaults(), new FileErrorPageResolver(dir));

        Response response = handler.handle(new IllegalStateException("boom"), request(false));

        assertEquals(500, response.status());
        assertTrue(body(response).contains("Custom failure"));
    }

    @Test
    void productionFallsBackToGenericError(@TempDir Path dir) {
        ConfigurableExceptionHandler handler = new ConfigurableExceptionHandler(
                false, SensitiveDataRedactor.defaults(), new FileErrorPageResolver(dir));

        Response response = handler.handle(new IllegalStateException("boom"), request(false));

        assertEquals(500, response.status());
        assertTrue(body(response).contains("Internal Server Error"));
        assertFalse(body(response).contains("boom"));
    }

    @Test
    void detailedJsonRedactsExceptionMessage() throws Exception {
        ConfigurableExceptionHandler handler = new ConfigurableExceptionHandler(
                true, SensitiveDataRedactor.defaults(), new FileErrorPageResolver(Path.of("none")));

        Response response = handler.handle(
                new IllegalStateException("password=secret123"), request(true));

        assertEquals(500, response.status());
        String json = body(response);
        Map<?, ?> parsed = new com.fasterxml.jackson.databind.ObjectMapper().readValue(json, Map.class);
        assertEquals("password=\"***\"", parsed.get("message"));
        assertFalse(json.contains("secret123"));
        assertTrue(parsed.containsKey("trace"));
    }

    @Test
    void detailedModeUsesHtmlForBrowsers() {
        ConfigurableExceptionHandler handler = new ConfigurableExceptionHandler(
                true, SensitiveDataRedactor.defaults(), new FileErrorPageResolver(Path.of("none")));

        Response response = handler.handle(new IllegalStateException("<script>bad</script>"), request(false));

        assertTrue(body(response).contains("Internal Server Error"));
        assertFalse(body(response).contains("<script>bad</script>"));
        assertTrue(body(response).contains("&lt;script&gt;"));
    }

    private static Request request(boolean json) {
        Map<String, List<String>> headers = json
                ? Map.of("Accept", List.of("application/json"))
                : Map.of("Accept", List.of("text/html"));
        return new Request(HttpMethod.GET, "/boom", headers, Map.of(), Map.of(), new byte[0], "127.0.0.1");
    }

    private static String body(Response response) {
        return new String(response.body(), StandardCharsets.UTF_8);
    }
}
