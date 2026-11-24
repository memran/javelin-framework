package io.javelin.validation;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class DefaultValidatorTest {
    @Test
    void reportsFieldErrorsForFailingRules() {
        Map<String, List<String>> errors = new DefaultValidator().validate(
                Map.of("email", "not-an-email", "age", "abc"),
                Map.of("email", List.of("required", "email"),
                        "age", List.of("required", "integer"),
                        "name", List.of("required")));

        assertEquals(3, errors.size());
        assertTrue(errors.get("email").stream().anyMatch(message -> message.contains("email")));
        assertTrue(errors.containsKey("age"));
        assertTrue(errors.containsKey("name"));
    }

    @Test
    void returnsEmptyErrorsForValidInput() {
        Map<String, List<String>> errors = new DefaultValidator().validate(
                Map.of("email", "ada@example.com", "role", "admin"),
                Map.of("email", List.of("required", "email"), "role", List.of("oneOf:admin,user")));
        assertTrue(errors.isEmpty());
    }

    @Test
    void rejectsUnknownRules() {
        assertThrows(IllegalArgumentException.class,
                () -> new DefaultValidator().validate(Map.of(), Map.of("x", List.of("nonsense"))));
    }
}
