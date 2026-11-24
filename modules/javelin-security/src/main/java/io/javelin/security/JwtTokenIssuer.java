package io.javelin.security;

import io.javelin.core.Json;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/** Issues HS256 JWTs compatible with {@link JwtTokenResolver}. */
public final class JwtTokenIssuer {
    private final byte[] secret;
    private final String issuer;
    private final String audience;
    private final int ttlSeconds;

    public JwtTokenIssuer(String secret, String issuer, String audience, int ttlSeconds) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("JWT secret must not be blank");
        }
        if (ttlSeconds < 1) {
            throw new IllegalArgumentException("JWT ttl must be at least 1 second");
        }
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.issuer = issuer == null ? "" : issuer;
        this.audience = audience == null ? "" : audience;
        this.ttlSeconds = ttlSeconds;
    }

    public String issue(String subject, Set<String> permissions) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("JWT subject must not be blank");
        }
        long now = Instant.now().getEpochSecond();
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put("sub", subject);
        claims.put("iat", now);
        claims.put("exp", now + ttlSeconds);
        if (!issuer.isBlank()) {
            claims.put("iss", issuer);
        }
        if (!audience.isBlank()) {
            claims.put("aud", audience);
        }
        claims.put("permissions", permissions == null ? Set.of() : permissions);
        String header = encode(Json.stringify(Map.of("alg", "HS256", "typ", "JWT")));
        String payload = encode(Json.stringify(claims));
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret, "HmacSHA256"));
            String signature = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(mac.doFinal((header + "." + payload).getBytes(StandardCharsets.UTF_8)));
            return header + "." + payload + "." + signature;
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to sign JWT", exception);
        }
    }

    private static String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
}
