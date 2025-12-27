package com.minerylehome.user;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common/user")
@Validated
public class UserController {

    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String DEFAULT_AVATAR = "images/avatars/brian-hughes.jpg";

    private UserProfile userProfile = new UserProfile(
            USER_ID,
            "Evan Coyle",
            "evan.g.coyle@gmail.com",
            DEFAULT_AVATAR,
            "online");

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> getUser() {
        return ResponseEntity.ok(userProfile);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        UserProfile updates = request.user();
        userProfile = new UserProfile(
                userProfile.id(),
                coalesce(updates.name(), userProfile.name()),
                coalesce(updates.email(), userProfile.email()),
                coalesce(updates.avatar(), userProfile.avatar()),
                coalesce(updates.status(), userProfile.status()));
        return ResponseEntity.ok(userProfile);
    }

    private String coalesce(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
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
