package com.minerylehome.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minerylehome.auth.AuthSessionAttributes;

@RestController
@RequestMapping("/api/common/user")
@Validated
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> getUser(HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return userRepository.findUserProfile(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> updateUser(@Valid @RequestBody UpdateUserRequest request, HttpSession session) {
        Long userId = getUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return userRepository.findUserProfile(userId)
                .map(existing -> {
                    UserProfile updates = request.user();
                    UserProfile merged = new UserProfile(
                            existing.id(),
                            coalesce(updates.name(), existing.name()),
                            coalesce(updates.email(), existing.email()),
                            coalesce(updates.avatar(), existing.avatar()),
                            coalesce(updates.status(), existing.status()));
                    return ResponseEntity.ok(userRepository.updateUserProfile(userId, merged));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private String coalesce(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }

    private Long getUserId(HttpSession session) {
        Object id = session.getAttribute(AuthSessionAttributes.USER_ID);
        if (id instanceof Long userId) {
            return userId;
        }
        return null;
    }

    public record UpdateUserRequest(@Valid UserProfile user) {
    }

    public record UserProfile(
            @NotBlank String id,
            @NotBlank String name,
            @Email String email,
            String avatar,
            String status) {
    }
}
