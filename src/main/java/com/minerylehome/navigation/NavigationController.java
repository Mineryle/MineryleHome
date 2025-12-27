package com.minerylehome.navigation;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

    private final Gson gson = new Gson();

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getNavigation() throws IOException {
        String userEmail = CURRENT_USER_EMAIL;
        JsonElement navigation = loadNavigationForUser(userEmail);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(gson.toJson(navigation));
    }

    private JsonElement loadNavigationForUser(String userEmail) throws IOException {
        try (InputStream inputStream = new ClassPathResource(NAVIGATION_RESOURCE).getInputStream()) {
            String json = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return JsonParser.parseString(json);
        }
    }
}
