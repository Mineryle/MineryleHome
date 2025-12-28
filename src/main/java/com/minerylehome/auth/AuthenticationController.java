package com.minerylehome.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import jakarta.servlet.http.HttpSession;
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

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request, HttpSession session) {
        System.out.println("Auth attempt for username=" + request.username());
        return authenticationService.findUserByEmail(request.username())
                .map(user -> {
                    System.out.println("Auth user found id=" + user.id() + " email=" + user.email());
                    if (!authenticationService.passwordMatches(request.password(), user.passwordHash())) {
                        System.out.println("Auth password mismatch for user id=" + user.id());
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(false));
                    }
                    System.out.println("Auth password matched for user id=" + user.id());
                    long userSessionId = authenticationService.createSession(user.id(), session.getId());
                    session.setAttribute(AuthSessionAttributes.USER_ID, user.id());
                    session.setAttribute(AuthSessionAttributes.USER_SESSION_ID, userSessionId);
                    return ResponseEntity.ok(new AuthResponse(true));
                })
                .orElseGet(() -> {
                    System.out.println("Auth failed for username=" + request.username());
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(false));
                });
    }

    public record AuthRequest(
            @NotBlank String username,
            @NotBlank String password) {
    }

    public record AuthResponse(boolean authenticated) {
    }
}
