package io.javelin.security;

import io.javelin.core.Json;
import io.javelin.core.security.Authenticated;
import io.javelin.core.security.BearerTokenResolver;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/** Verifies HS256 JWTs and maps standard claims to an Authenticated principal. */
public final class JwtTokenResolver implements BearerTokenResolver {
    private static final String ALGORITHM = "HmacSHA256";

    private final byte[] secret;
    private final String issuer;
    private final String audience;

    public JwtTokenResolver(String secret, String issuer, String audience) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException("JWT secret must not be blank");
        }
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.issuer = issuer == null ? "" : issuer;
        this.audience = audience == null ? "" : audience;
    }

    @Override
    public Optional<Authenticated> resolve(String token) {
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }
        String[] parts = token.split("\\.", -1);
        if (parts.length != 3) {
            return Optional.empty();
        }
        try {
            Map<?, ?> header = Json.parse(decode(parts[0]), Map.class);
            if (!"HS256".equals(String.valueOf(header.get("alg")))) {
                return Optional.empty();
            }
            byte[] expected = sign(parts[0] + "." + parts[1]);
            byte[] supplied = Base64.getUrlDecoder().decode(parts[2]);
            if (!MessageDigest.isEqual(expected, supplied)) {
                return Optional.empty();
            }
            Map<?, ?> claims = Json.parse(decode(parts[1]), Map.class);
            if (!validTimes(claims) || !validAudience(claims)) {
                return Optional.empty();
            }
            String subject = string(claims.get("sub"));
            if (subject.isBlank()) {
                return Optional.empty();
            }
            return Optional.of(new Authenticated(subject, permissions(claims)));
        } catch (RuntimeException exception) {
            return Optional.empty();
        }
    }

    private boolean validTimes(Map<?, ?> claims) {
        long now = Instant.now().getEpochSecond();
        Object expires = claims.get("exp");
        if (expires instanceof Number number && number.longValue() <= now) {
            return false;
        }
        Object notBefore = claims.get("nbf");
        return !(notBefore instanceof Number number && number.longValue() > now);
    }

    private boolean validAudience(Map<?, ?> claims) {
        if (!issuer.isBlank() && !issuer.equals(string(claims.get("iss")))) {
            return false;
        }
        if (audience.isBlank()) {
            return true;
        }
        Object value = claims.get("aud");
        if (value instanceof List<?> list) {
            return list.stream().anyMatch(audience::equals);
        }
        return audience.equals(string(value));
    }

    private static Set<String> permissions(Map<?, ?> claims) {
        Set<String> values = new LinkedHashSet<>();
        Object list = claims.get("permissions");
        if (list instanceof List<?> items) {
            items.stream().map(JwtTokenResolver::string).filter(value -> !value.isBlank()).forEach(values::add);
        }
        Object scope = claims.get("scope");
        for (String part : string(scope).split(" ")) {
            if (!part.isBlank()) {
                values.add(part);
            }
        }
        return Set.copyOf(values);
    }

    private byte[] sign(String data) throws RuntimeException {
        try {
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(new SecretKeySpec(secret, ALGORITHM));
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to verify JWT signature", exception);
        }
    }

    private static String decode(String value) {
        return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    }

    private static String string(Object value) {
        return value == null ? "" : value.toString();
    }
}
