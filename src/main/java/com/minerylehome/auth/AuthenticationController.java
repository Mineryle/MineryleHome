package com.minerylehome.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthenticationController {

    private static final String EXPECTED_USERNAME = "evan.g.coyle@gmail.com";
    private static final String EXPECTED_PASSWORD = "gcsu1234";

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        boolean usernameMatches = MessageDigest.isEqual(
                request.username().getBytes(StandardCharsets.UTF_8),
                EXPECTED_USERNAME.getBytes(StandardCharsets.UTF_8));
        boolean passwordMatches = MessageDigest.isEqual(
                request.password().getBytes(StandardCharsets.UTF_8),
                EXPECTED_PASSWORD.getBytes(StandardCharsets.UTF_8));

        if (usernameMatches && passwordMatches) {
            return ResponseEntity.ok(new AuthResponse(true));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(false));
    }

    public record AuthRequest(
            @NotBlank String username,
            @NotBlank String password) {
    }

    public record AuthResponse(boolean authenticated) {
    }
}
