package kz.trendprice.server.catalogbffservice.controller;

import kz.trendprice.securitystarter.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("debug/jwt")
public class JwtDegubController {

    private final UserClient userClient;

    @GetMapping("/parse")
    public ResponseEntity<Map<String, Object>> parse(@RequestHeader("Authorization") String authorization) {
        System.err.println(authorization);
        String token = authorization.substring(7);
        System.err.println(token);
        var user = userClient.parse(token);

        return ResponseEntity.ok(
                Map.of(
                        "userId", user.userId(),
                        "username", user.username(),
                        "email", user.email(),
                        "roles", user.roles()
                )
        );
    }

    @GetMapping("/admin-check")
    public ResponseEntity<Map<String, Object>> adminCheck(@RequestHeader("Authorization") String authorization) {
        System.err.println(authorization);
        String token = authorization.substring(7);
        System.err.println(token);

        return ResponseEntity.ok(
                Map.of(
                        "isValid", userClient.isTokenValid(token),
                        "isAdmin", userClient.hasRole(token, "ADMIN")
                )
        );
    }
}
