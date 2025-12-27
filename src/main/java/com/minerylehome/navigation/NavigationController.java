package com.minerylehome.navigation;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common/navigation")
public class NavigationController {

    private static final String CURRENT_USER_EMAIL = "evan.g.coyle@gmail.com";
    private static final String NAVIGATION_RESOURCE = "navigation.json";

    private final ObjectMapper objectMapper;

    public NavigationController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JsonNode> getNavigation() throws IOException {
        String userEmail = CURRENT_USER_EMAIL;
        JsonNode navigation = loadNavigationForUser(userEmail);
        return ResponseEntity.ok(navigation);
    }

    private JsonNode loadNavigationForUser(String userEmail) throws IOException {
        try (InputStream inputStream = new ClassPathResource(NAVIGATION_RESOURCE).getInputStream()) {
            return objectMapper.readTree(inputStream);
        }
    }
}
