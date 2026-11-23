package io.javelin.http.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Resolves the client address from forwarded headers only when the direct peer is an explicitly
 * trusted proxy. The forwarded chain is read right-to-left, skipping trusted proxies, so a client
 * cannot choose its own address by adding entries to the left of the chain.
 */
public final class TrustedProxyResolver {
    private final ProxyAddressMatcher matcher;

    public TrustedProxyResolver(List<String> trustedProxies) {
        this.matcher = new ProxyAddressMatcher(trustedProxies);
    }

    public String resolve(String remoteAddress, Map<String, List<String>> headers) {
        Objects.requireNonNull(remoteAddress, "remoteAddress");
        Objects.requireNonNull(headers, "headers");
        if (!matcher.matches(remoteAddress)) {
            return remoteAddress;
        }
        List<String> chain = forwardedChain(headers);
        for (int index = chain.size() - 1; index >= 0; index--) {
            String candidate = chain.get(index);
            if (ProxyAddressMatcher.parseLiteral(candidate) != null && !matcher.matches(candidate)) {
                return ProxyAddressMatcher.normalize(candidate);
            }
        }
        return remoteAddress;
    }

    private static List<String> forwardedChain(Map<String, List<String>> headers) {
        List<String> chain = new ArrayList<>();
        collect(headers, "X-Forwarded-For", chain, value -> value);
        if (!chain.isEmpty()) {
            return chain;
        }
        collect(headers, "Forwarded", chain, TrustedProxyResolver::forwardedFor);
        return chain;
    }

    private static void collect(Map<String, List<String>> headers, String name, List<String> target,
                                Function<String, String> extractor) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(name) || entry.getValue() == null) {
                continue;
            }
            for (String value : entry.getValue()) {
                for (String part : value.split(",")) {
                    String address = extractor.apply(part).trim();
                    if (!address.isEmpty()) {
                        target.add(address);
                    }
                }
            }
        }
    }

    private static String forwardedFor(String header) {
        for (String token : header.split(";")) {
            String part = token.trim();
            if (part.toLowerCase(Locale.ROOT).startsWith("for=")) {
                return part.substring(4).trim();
            }
        }
        return "";
    }
}
