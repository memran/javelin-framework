package io.javelin.exception;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class SensitiveDataRedactorTest {
    @Test
    void masksCommonKeyPairsInText() {
        SensitiveDataRedactor redactor = SensitiveDataRedactor.defaults();
        String text = "password=secret123 {\"token\": \"abc.def\", \"api_key\": \"xyz\"}";
        String masked = redactor.redact(text);
        assertTrue(masked.contains("password=\"***\""), masked);
        assertTrue(masked.contains("\"token\": \"***\""), masked);
        assertTrue(masked.contains("\"api_key\": \"***\""), masked);
        assertFalse(masked.contains("secret123"));
        assertFalse(masked.contains("abc.def"));
    }

    @Test
    void masksWholeAuthorizationHeaderValue() {
        SensitiveDataRedactor redactor = SensitiveDataRedactor.defaults();
        String masked = redactor.redact("Authorization: Bearer super-secret-token");
        assertTrue(masked.contains("Authorization: ***"), masked);
        assertFalse(masked.contains("super-secret-token"));
    }

    @Test
    void masksConfiguredMapKeys() {
        SensitiveDataRedactor redactor = SensitiveDataRedactor.defaults();
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("username", "alice");
        values.put("access_token", "abc");
        values.put("Set-Cookie", "session=1");
        Map<String, String> masked = redactor.redact(values);
        assertEquals("alice", masked.get("username"));
        assertEquals("***", masked.get("access_token"));
        assertEquals("***", masked.get("Set-Cookie"));
        assertTrue(redactor.isSensitive("api-key"));
    }

    @Test
    void noneMasksNothing() {
        SensitiveDataRedactor redactor = SensitiveDataRedactor.none();
        assertEquals("password=secret", redactor.redact("password=secret"));
        assertEquals("secret", redactor.redact(Map.of("password", "secret")).get("password"));
        assertTrue(redactor.isSensitive("password") == false);
    }

    @Test
    void customKeysReplaceDefaults() {
        SensitiveDataRedactor redactor = new SensitiveDataRedactor(Set.of("internal_note"));
        assertEquals("internal_note=\"***\"", redactor.redact("internal_note=hello").trim());
        assertFalse(redactor.isSensitive("password"));
    }
}
