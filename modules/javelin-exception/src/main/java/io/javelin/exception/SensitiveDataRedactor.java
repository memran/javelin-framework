package io.javelin.exception;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/** Masks sensitive key/value pairs before they are logged or sent to a client. */
public final class SensitiveDataRedactor {
    public static final String MASK = "***";

    public static final Set<String> DEFAULT_KEYS = Set.of(
            "password", "passwd", "secret", "token", "authorization", "cookie", "set-cookie", "setcookie",
            "api_key", "apikey", "access_token", "accesstoken", "refresh_token", "refreshtoken",
            "private_key", "privatekey", "credit_card", "creditcard", "cvv", "ssn");

    private static final Pattern HEADER_VALUE = Pattern.compile(
            "(?i)\\b(authorization|cookie|set-cookie)(\\s*[:=]\\s*)([^\\r\\n,]+)");

    private final Set<String> normalizedKeys;
    private final Pattern pairPattern;

    public SensitiveDataRedactor(Collection<String> keys) {
        Set<String> normalized = new LinkedHashSet<>();
        Set<String> textKeys = new LinkedHashSet<>();
        for (String key : keys) {
            if (key == null || key.isBlank()) {
                continue;
            }
            String trimmed = key.trim();
            textKeys.add(trimmed);
            String value = normalize(trimmed);
            if (!value.isEmpty()) {
                normalized.add(value);
                textKeys.add(value);
            }
        }
        this.normalizedKeys = Set.copyOf(normalized);
        this.pairPattern = textKeys.isEmpty()
                ? Pattern.compile("(?!)")
                : Pattern.compile("(?i)(\"?)(%s)\\1(\\s*[:=]\\s*)(\"[^\"]*\"|'[^']*'|[^\\s,;}\\]]+)"
                        .formatted(textKeys.stream().map(Pattern::quote).collect(Collectors.joining("|"))));
    }

    public static SensitiveDataRedactor defaults() {
        return new SensitiveDataRedactor(DEFAULT_KEYS);
    }

    /** Returns an instance that masks nothing, for explicitly disabled redaction. */
    public static SensitiveDataRedactor none() {
        return new SensitiveDataRedactor(Set.of());
    }

    public String redact(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        String masked = pairPattern.matcher(value).replaceAll("$1$2$1$3\"" + MASK + "\"");
        return HEADER_VALUE.matcher(masked).replaceAll("$1$2" + MASK);
    }

    public Map<String, String> redact(Map<String, ?> values) {
        Map<String, String> result = new LinkedHashMap<>();
        for (Map.Entry<String, ?> entry : values.entrySet()) {
            String key = entry.getKey();
            result.put(key, normalizedKeys.contains(normalize(key)) ? MASK : String.valueOf(entry.getValue()));
        }
        return Map.copyOf(result);
    }

    public boolean isSensitive(String key) {
        return normalizedKeys.contains(normalize(key));
    }

    private static String normalize(String key) {
        return key.toLowerCase(java.util.Locale.ROOT).replace("_", "").replace("-", "");
    }
}
