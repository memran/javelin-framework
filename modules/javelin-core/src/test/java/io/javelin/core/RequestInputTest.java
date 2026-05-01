package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RequestInputTest {
    @Test
    void parsesQueryAndFormInputThroughFluentWrapper() {
        Request request = new Request(
                HttpMethod.POST,
                "/profile",
                Map.of("Content-Type", List.of("application/x-www-form-urlencoded")),
                Map.of("page", "2"),
                Map.of(),
                "name=%3Cb%3EAlice%3C%2Fb%3E&file=../../CON.txt&active=yes&age=29&tags=one%2C+%3Cb%3Etwo%3C%2Fb%3E%2C+three".getBytes(StandardCharsets.UTF_8),
                "127.0.0.1"
        );

        var input = request.input();

        assertEquals("Alice", input.text("name").orElseThrow());
        assertEquals(29, input.integer("age").orElseThrow());
        assertTrue(input.bool("active").orElseThrow());
        assertEquals("_CON.txt", input.filename("file").orElseThrow());
        assertEquals("_CON.txt", input.file("file").orElseThrow());
        assertEquals("2", input.raw("page").orElseThrow());
        assertEquals(List.of("one", "two", "three"), input.array("tags").values());
        assertEquals(List.of("page", "name", "file", "active", "age", "tags"), input.values().keySet().stream().toList());
    }
}
