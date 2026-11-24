package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class ValidationErrorsTest {
    @Test
    void jsonResponseUses422AndCarriesFieldErrors() {
        JsonResponse response = ValidationErrors.json(Map.of("email", List.of("Required")));
        assertEquals(422, response.status());
        String body = new String(response.body(), StandardCharsets.UTF_8);
        assertTrue(body.contains("\"errors\""));
        assertTrue(body.contains("email"));
    }

    @Test
    void htmlResponseEscapesFieldAndMessage() {
        HtmlResponse response = ValidationErrors.html(Map.of("<field>", List.of("<script>bad</script>")));
        String body = new String(response.body(), StandardCharsets.UTF_8);
        assertTrue(body.contains("&lt;field&gt;"));
        assertTrue(!body.contains("<script>"));
    }

    @Test
    void negotiatesFormatFromAcceptHeader() {
        Request json = request("application/json");
        Request html = request("text/html");
        assertEquals("application/json; charset=utf-8", ValidationErrors.of(json, Map.of()).headers().get("Content-Type"));
        assertEquals("text/html; charset=utf-8", ValidationErrors.of(html, Map.of()).headers().get("Content-Type"));
    }

    private static Request request(String accept) {
        return new Request(HttpMethod.POST, "/users", Map.of("Accept", List.of(accept)),
                Map.of(), Map.of(), new byte[0], "127.0.0.1");
    }
}
