package io.javelin.http.jdk;

import com.sun.net.httpserver.Headers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class TrustedProxyResolverTest {
    @Test
    void resolvesForwardedClientIpOnlyForTrustedRemoteProxies() {
        TrustedProxyResolver resolver = new TrustedProxyResolver(List.of("10.0.0.1"));
        Headers headers = new Headers();
        headers.add("X-Forwarded-For", "203.0.113.10, 10.0.0.1");

        assertEquals("203.0.113.10", resolver.resolve("10.0.0.1", headers));
        assertEquals("10.0.0.2", resolver.resolve("10.0.0.2", headers));
    }

    @Test
    void supportsTheStandardForwardedHeaderWhenProxyIsTrusted() {
        TrustedProxyResolver resolver = new TrustedProxyResolver(List.of("*"));
        Headers headers = new Headers();
        headers.add("Forwarded", "for=\"[2001:db8::1]\";proto=https;host=example.com");

        assertEquals("2001:db8::1", resolver.resolve("127.0.0.1", headers));
    }
}
