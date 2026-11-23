package io.javelin.core;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class RequestAttributesTest {
    @Test
    void attributesSurviveRouteParameterAttachment() {
        Request request = new Request(HttpMethod.GET, "/users/42", Map.of(), Map.of(), Map.of(),
                new byte[0], "127.0.0.1").withAttribute("principal", "alice");

        Request routed = request.withParams(Map.of("id", "42"));

        assertEquals(Optional.of("alice"), routed.attribute("principal"));
        assertEquals(Optional.of("42"), routed.param("id"));
        assertTrue(request.attribute("missing").isEmpty());
    }

    @Test
    void withAttributeDoesNotMutateTheOriginalRequest() {
        Request original = new Request(HttpMethod.GET, "/", Map.of(), Map.of(), Map.of(),
                new byte[0], "127.0.0.1");

        Request updated = original.withAttribute("principal", "alice");

        assertTrue(original.attribute("principal").isEmpty());
        assertEquals(Optional.of("alice"), updated.attribute("principal"));
    }
}
