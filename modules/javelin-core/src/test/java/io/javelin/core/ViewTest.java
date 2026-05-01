package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class ViewTest {
    @Test
    void viewHelperRendersTemplatesWithMapAndVarargsData() {
        View.use((template, data) -> new HtmlResponse(template + ":" + data.get("name")));

        try {
            Response mapResponse = View.view("welcome", Map.of("name", "Javelin"));
            Response argsResponse = View.view("welcome", "name", "Pebble");

            assertEquals(200, mapResponse.status());
            assertEquals("welcome:Javelin", new String(mapResponse.body(), StandardCharsets.UTF_8));
            assertEquals("welcome:Pebble", new String(argsResponse.body(), StandardCharsets.UTF_8));
        } finally {
            View.use((template, data) -> new HtmlResponse(""));
        }
    }
}
