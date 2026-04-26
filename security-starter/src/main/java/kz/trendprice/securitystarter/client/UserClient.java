package kz.trendprice.securitystarter.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@Component
public class UserClient {

    private final String secret;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserClient(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public boolean isTokenValid(String token) {
        parseAndValidate(token);
        return true;
    }

    public AuthenticatedUser parse(String token) {
        Map<String, Object> claims = parseAndValidate(token);

        String username = claims.get("sub") == null ? null : claims.get("sub").toString();
        Long userId = toLong(claims.get("uid"));
        String email = claims.get("email") == null ? null : claims.get("email").toString();
        Set<String> roles = extractRoles(claims);

        return new AuthenticatedUser(
                userId,
                username,
                email,
                roles
        );
    }

    public Authentication toAuthentication(String token) {
        AuthenticatedUser user = parse(token);

        List<SimpleGrantedAuthority> authorities = user.roles().stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    public String extractUsername(String token) {
        return parse(token).username();
    }

    public Long extractUserId(String token) {
        return parse(token).userId();
    }

    public String extractEmail(String token) {
        return parse(token).email();
    }

    public Set<String> extractRoles(String token) {
        return parse(token).roles();
    }

    public boolean hasRole(String token, String role) {
        String normalizedRole = normalizeRole(role);
        return parse(token).roles().contains(normalizedRole);
    }

    private Map<String, Object> parseAndValidate(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new RuntimeException("Invalid token format");
            }

            String data = parts[0] + "." + parts[1];
            String expectedSig = hmacSha256Base64Url(data, secret);

            if (!constantTimeEquals(expectedSig, parts[2])) {
                throw new RuntimeException("Bad signature");
            }

            String payloadJson = new String(
                    Base64.getUrlDecoder().decode(parts[1]),
                    StandardCharsets.UTF_8
            );

            Map<String, Object> claims = objectMapper.readValue(
                    payloadJson,
                    new TypeReference<>() {}
            );

            Object expObj = claims.get("exp");
            if (expObj == null) {
                throw new RuntimeException("Missing claim: exp");
            }

            long exp = ((Number) expObj).longValue();
            long now = Instant.now().getEpochSecond();

            if (now >= exp) {
                throw new RuntimeException("Token expired");
            }

            return claims;
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT: " + e.getMessage(), e);
        }
    }

    private Set<String> extractRoles(Map<String, Object> claims) {
        Object rolesObj = claims.get("roles");

        if (rolesObj instanceof List<?> list) {
            return list.stream()
                    .map(String::valueOf)
                    .collect(java.util.stream.Collectors.toCollection(LinkedHashSet::new));
        }

        return Set.of();
    }

    private Long toLong(Object value) {
        if (value instanceof Integer i) return i.longValue();
        if (value instanceof Long l) return l;
        if (value instanceof String s) return Long.parseLong(s);
        return null;
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return "";
        }

        String normalized = role.trim().toUpperCase();
        if (normalized.startsWith("ROLE_")) {
            return normalized;
        }

        return "ROLE_" + normalized;
    }

    private String hmacSha256Base64Url(String data, String secret) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"
            );
            mac.init(keySpec);
            byte[] sig = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return base64Url(sig);
        } catch (Exception e) {
            throw new IllegalStateException("JWT signing failed: " + e.getMessage(), e);
        }
    }

    private String base64Url(byte[] bytes) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }

        int res = 0;
        for (int i = 0; i < a.length(); i++) {
            res |= a.charAt(i) ^ b.charAt(i);
        }
        return res == 0;
    }

    public record AuthenticatedUser(
            Long userId,
            String username,
            String email,
            Set<String> roles
    ) {}
}