package io.javelin.http.common;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TrustedProxyResolverTest {
    @Test
    void resolvesForwardedClientIpOnlyForTrustedRemoteProxies() {
        TrustedProxyResolver resolver = new TrustedProxyResolver(List.of("10.0.0.1"));
        Map<String, List<String>> headers = Map.of("X-Forwarded-For", List.of("203.0.113.10, 10.0.0.1"));

        assertEquals("203.0.113.10", resolver.resolve("10.0.0.1", headers));
        assertEquals("10.0.0.2", resolver.resolve("10.0.0.2", headers));
    }

    @Test
    void supportsTheStandardForwardedHeaderWhenProxyIsTrusted() {
        TrustedProxyResolver resolver = new TrustedProxyResolver(List.of("127.0.0.1"));
        Map<String, List<String>> headers = Map.of("Forwarded", List.of("for=\"[2001:db8::1]\";proto=https;host=example.com"));

        assertEquals("2001:db8::1", resolver.resolve("127.0.0.1", headers));
    }

    @Test
    void skipsTrustedProxiesFromTheRightAndReturnsTheRightmostUntrustedAddress() {
        TrustedProxyResolver resolver = new TrustedProxyResolver(List.of("10.0.0.1", "10.0.0.2"));
        Map<String, List<String>> headers = Map.of(
                "X-Forwarded-For", List.of("203.0.113.10, 10.0.0.2, 10.0.0.1"));

        assertEquals("203.0.113.10", resolver.resolve("10.0.0.1", headers));
    }

    @Test
    void ignoresMalformedForwardedAddresses() {
        TrustedProxyResolver resolver = new TrustedProxyResolver(List.of("10.0.0.1"));
        Map<String, List<String>> headers = Map.of(
                "X-Forwarded-For", List.of("unknown, 10.0.0.1"));

        assertEquals("10.0.0.1", resolver.resolve("10.0.0.1", headers));
    }

    @Test
    void supportsCidrRanges() {
        TrustedProxyResolver resolver = new TrustedProxyResolver(List.of("10.0.0.0/8"));
        Map<String, List<String>> headers = Map.of("X-Forwarded-For", List.of("203.0.113.10"));

        assertEquals("203.0.113.10", resolver.resolve("10.1.2.3", headers));
        assertEquals("192.168.1.5", resolver.resolve("192.168.1.5", headers));
    }

    @Test
    void rejectsWildcardProxyTrust() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class,
                () -> new TrustedProxyResolver(List.of("*")));

        assertTrue(error.getMessage().contains("Wildcard proxy trust is not allowed"));
    }

    @Test
    void rejectsHostnamesAndInvalidCidrs() {
        assertThrows(IllegalArgumentException.class, () -> new TrustedProxyResolver(List.of("proxy.internal")));
        assertThrows(IllegalArgumentException.class, () -> new TrustedProxyResolver(List.of("10.0.0.0/33")));
    }
}
