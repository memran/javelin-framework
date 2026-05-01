package io.javelin.http.jdk;

import com.sun.net.httpserver.Headers;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

final class TrustedProxyResolver {
    private final List<String> trustedProxies;
    private final boolean trustAll;

    TrustedProxyResolver(List<String> trustedProxies) {
        this.trustedProxies = trustedProxies == null ? List.of() : trustedProxies.stream()
                .map(value -> value == null ? "" : value.trim())
                .filter(value -> !value.isEmpty())
                .toList();
        this.trustAll = this.trustedProxies.contains("*");
    }

    String resolve(String remoteAddress, Headers headers) {
        Objects.requireNonNull(remoteAddress, "remoteAddress");
        Objects.requireNonNull(headers, "headers");
        if (!isTrusted(remoteAddress)) {
            return remoteAddress;
        }
        return forwardedFor(headers).orElse(remoteAddress);
    }

    private boolean isTrusted(String remoteAddress) {
        if (trustAll) {
            return true;
        }
        return trustedProxies.contains(remoteAddress);
    }

    private Optional<String> forwardedFor(Headers headers) {
        Optional<String> xForwardedFor = firstHeader(headers, "X-Forwarded-For");
        if (xForwardedFor.isPresent()) {
            return xForwardedFor
                    .map(TrustedProxyResolver::firstAddress)
                    .filter(value -> !value.isBlank());
        }
        Optional<String> forwarded = firstHeader(headers, "Forwarded");
        return forwarded
                .map(TrustedProxyResolver::forwardedFor)
                .filter(value -> !value.isBlank());
    }

    private static Optional<String> firstHeader(Headers headers, String name) {
        return headers.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(name))
                .flatMap(entry -> entry.getValue().stream())
                .findFirst();
    }

    private static String firstAddress(String value) {
        return value.split(",")[0].trim();
    }

    private static String forwardedFor(String header) {
        for (String token : header.split(";")) {
            String part = token.trim();
            if (part.toLowerCase(Locale.ROOT).startsWith("for=")) {
                String value = part.substring(4).trim();
                if (value.startsWith("\"") && value.endsWith("\"") && value.length() >= 2) {
                    value = value.substring(1, value.length() - 1);
                }
                if (value.startsWith("[") && value.endsWith("]") && value.length() >= 2) {
                    value = value.substring(1, value.length() - 1);
                }
                int comma = value.indexOf(',');
                return comma >= 0 ? value.substring(0, comma).trim() : value.trim();
            }
        }
        return "";
    }
}
