package com.minerylehome.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.minerylehome.navigation.NavigationRepository.NavigationRow;

import org.springframework.stereotype.Service;

@Service
public class NavigationService {

    private static final List<String> DEFAULT_NAVIGATION_ORDER = List.of(
            "default",
            "compact",
            "futuristic",
            "horizontal");

    private final NavigationRepository navigationRepository;

    public NavigationService(NavigationRepository navigationRepository) {
        this.navigationRepository = navigationRepository;
    }

    public Map<String, Object> getNavigationForUser(long userId) {
        List<NavigationRow> rows = navigationRepository.findNavigationForUser(userId);
        Map<String, List<NavigationRow>> rowsByKey = new HashMap<>();
        for (NavigationRow row : rows) {
            rowsByKey.computeIfAbsent(row.navigationKey(), key -> new ArrayList<>()).add(row);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        for (String navigationKey : DEFAULT_NAVIGATION_ORDER) {
            List<NavigationRow> keyRows = rowsByKey.remove(navigationKey);
            if (keyRows != null) {
                response.put(navigationKey, buildNavigation(keyRows));
            }
        }
        rowsByKey.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> response.put(entry.getKey(), buildNavigation(entry.getValue())));
        return response;
    }

    private List<NavigationItem> buildNavigation(List<NavigationRow> rows) {
        Map<Long, List<NavigationRow>> byParent = new HashMap<>();
        for (NavigationRow row : rows) {
            byParent.computeIfAbsent(row.parentNavigationSid(), key -> new ArrayList<>()).add(row);
        }
        return buildItems(byParent, null);
    }

    private List<NavigationItem> buildItems(Map<Long, List<NavigationRow>> byParent, Long parentSid) {
        List<NavigationRow> rows = new ArrayList<>(byParent.getOrDefault(parentSid, List.of()));
        rows.sort((left, right) -> Integer.compare(left.sortOrder(), right.sortOrder()));

        List<NavigationItem> items = new ArrayList<>();
        for (NavigationRow row : rows) {
            NavigationItem item = toNavigationItem(row);
            List<NavigationItem> children = buildItems(byParent, row.navigationSid());
            if (!children.isEmpty() || shouldIncludeChildren(row.type())) {
                item.children = children;
            }
            items.add(item);
        }
        return items;
    }

    private boolean shouldIncludeChildren(String type) {
        return "group".equals(type) || "aside".equals(type) || "collapsable".equals(type);
    }

    private NavigationItem toNavigationItem(NavigationRow row) {
        NavigationItem item = new NavigationItem();
        item.id = row.itemId();
        item.title = row.title();
        item.subtitle = row.subtitle();
        item.type = row.type();
        item.icon = row.icon();
        item.link = row.link();
        item.tooltip = row.tooltip();
        item.active = row.active();
        item.disabled = row.disabled();
        item.exactMatch = row.exactMatch();
        if (row.badgeTitle() != null || row.badgeClasses() != null) {
            item.badge = new NavigationBadge(row.badgeTitle(), row.badgeClasses());
        }
        return item;
    }

    private static final class NavigationItem {
        @SerializedName("id")
        private String id;
        private String title;
        private String subtitle;
        private String type;
        private String icon;
        private String link;
        private String tooltip;
        private NavigationBadge badge;
        private Boolean active;
        private Boolean disabled;
        @SerializedName("exactMatch")
        private Boolean exactMatch;
        private List<NavigationItem> children;
    }

    private record NavigationBadge(String title, String classes) {
    }
}
