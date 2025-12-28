package com.minerylehome.navigation;

import java.util.Map;

import com.google.gson.Gson;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common/navigation")
public class NavigationController {

    private static final long CURRENT_USER_ID = 1L;

    private final Gson gson = new Gson();
    private final NavigationService navigationService;

    public NavigationController(NavigationService navigationService) {
        this.navigationService = navigationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getNavigation() {
        Map<String, Object> navigation = navigationService.getNavigationForUser(CURRENT_USER_ID);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(gson.toJson(navigation));
    }
}
