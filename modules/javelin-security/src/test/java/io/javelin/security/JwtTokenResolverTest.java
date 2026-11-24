package io.javelin.security;

import io.javelin.core.Json;
import io.javelin.core.security.Authenticated;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class JwtTokenResolverTest {
    private static final String SECRET = "integration-test-secret";

    @Test
    void acceptsValidTokenAndExtractsPermissions() {
        String token = token(Map.of(
                "sub", "alice",
                "exp", Instant.now().getEpochSecond() + 600,
                "iss", "javelin",
                "permissions", List.of("users.read", "users.write")), SECRET);
        JwtTokenResolver resolver = new JwtTokenResolver(SECRET, "javelin", "");

        Authenticated principal = resolver.resolve(token).orElseThrow();

        assertEquals("alice", principal.subject());
        assertTrue(principal.hasPermission("users.read"));
        assertTrue(principal.hasPermission("users.write"));
    }

    @Test
    void issuerTokensAreAcceptedByResolver() {
        JwtTokenIssuer issuer = new JwtTokenIssuer(SECRET, "javelin", "my-api", 600);
        JwtTokenResolver resolver = new JwtTokenResolver(SECRET, "javelin", "my-api");
        String token = issuer.issue("alice", java.util.Set.of("users.read"));

        Authenticated principal = resolver.resolve(token).orElseThrow();

        assertEquals("alice", principal.subject());
        assertTrue(principal.hasPermission("users.read"));
    }

    @Test
    void rejectsInvalidSignatureExpiredTokenAndWrongIssuer() {
        JwtTokenResolver resolver = new JwtTokenResolver(SECRET, "javelin", "");
        String valid = token(Map.of("sub", "alice", "exp", Instant.now().getEpochSecond() + 600, "iss", "javelin"), SECRET);
        String wrongSignature = token(Map.of("sub", "alice", "iss", "javelin"), "other-secret");
        String expired = token(Map.of("sub", "alice", "exp", 1, "iss", "javelin"), SECRET);
        String wrongIssuer = token(Map.of("sub", "alice", "iss", "someone-else"), SECRET);

        assertTrue(resolver.resolve(valid).isPresent());
        assertFalse(resolver.resolve(wrongSignature).isPresent());
        assertFalse(resolver.resolve(expired).isPresent());
        assertFalse(resolver.resolve(wrongIssuer).isPresent());
        assertFalse(resolver.resolve("not-a-jwt").isPresent());
    }

    private static String token(Map<String, Object> claims, String signingSecret) {
        String header = encode(Json.stringify(Map.of("alg", "HS256", "typ", "JWT")));
        String payload = encode(Json.stringify(claims));
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(signingSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            String signature = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(mac.doFinal((header + "." + payload).getBytes(StandardCharsets.UTF_8)));
            return header + "." + payload + "." + signature;
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    private static String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
}
