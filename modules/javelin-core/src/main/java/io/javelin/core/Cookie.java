package io.javelin.core;

/** Small value type for request cookies and Set-Cookie response headers. */
public record Cookie(String name, String value, int maxAgeSeconds, String path, String domain,
                     boolean secure, boolean httpOnly, String sameSite) {
    public Cookie {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Cookie name must not be blank");
        }
        value = value == null ? "" : value;
    }

    public static Cookie session(String name, String value) {
        return new Cookie(name, value, -1, "/", null, true, true, "Lax");
    }

    public static Cookie persistent(String name, String value, int maxAgeSeconds) {
        return new Cookie(name, value, maxAgeSeconds, "/", null, true, true, "Lax");
    }

    public static Cookie expired(String name) {
        return new Cookie(name, "", 0, "/", null, true, true, "Lax");
    }

    public String headerValue() {
        StringBuilder header = new StringBuilder().append(name).append('=').append(value);
        append(header, "Path", path);
        if (domain != null && !domain.isBlank()) {
            append(header, "Domain", domain);
        }
        if (maxAgeSeconds >= 0) {
            append(header, "Max-Age", String.valueOf(maxAgeSeconds));
        }
        if (httpOnly) {
            header.append("; HttpOnly");
        }
        if (secure) {
            header.append("; Secure");
        }
        if (sameSite != null && !sameSite.isBlank()) {
            append(header, "SameSite", sameSite);
        }
        return header.toString();
    }

    private static void append(StringBuilder header, String name, String value) {
        if (value != null && !value.isBlank()) {
            header.append("; ").append(name).append('=').append(value);
        }
    }
}
