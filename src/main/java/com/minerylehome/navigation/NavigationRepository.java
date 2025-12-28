package com.minerylehome.navigation;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
public class NavigationRepository {

    private final DSLContext dsl;

    public NavigationRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<NavigationRow> findNavigationForUser(long userId) {
        return dsl.select(
                        DSL.field("navigation_sid", Long.class),
                        DSL.field("account_sid", Long.class),
                        DSL.field("parent_navigation_sid", Long.class),
                        DSL.field("navigation_key", String.class),
                        DSL.field("item_id", String.class),
                        DSL.field("title", String.class),
                        DSL.field("subtitle", String.class),
                        DSL.field("type", String.class),
                        DSL.field("icon", String.class),
                        DSL.field("link", String.class),
                        DSL.field("tooltip", String.class),
                        DSL.field("badge_title", String.class),
                        DSL.field("badge_classes", String.class),
                        DSL.field("active", Boolean.class),
                        DSL.field("disabled", Boolean.class),
                        DSL.field("exact_match", Boolean.class),
                        DSL.field("sort_order", Integer.class))
                .from(DSL.table(DSL.name("navigation")))
                .where(DSL.field("account_sid").eq(userId))
                .fetch(this::mapRow);
    }

    private NavigationRow mapRow(Record record) {
        return new NavigationRow(
                record.get(DSL.field("navigation_sid", Long.class)),
                record.get(DSL.field("account_sid", Long.class)),
                record.get(DSL.field("parent_navigation_sid", Long.class)),
                record.get(DSL.field("navigation_key", String.class)),
                record.get(DSL.field("item_id", String.class)),
                record.get(DSL.field("title", String.class)),
                record.get(DSL.field("subtitle", String.class)),
                record.get(DSL.field("type", String.class)),
                record.get(DSL.field("icon", String.class)),
                record.get(DSL.field("link", String.class)),
                record.get(DSL.field("tooltip", String.class)),
                record.get(DSL.field("badge_title", String.class)),
                record.get(DSL.field("badge_classes", String.class)),
                record.get(DSL.field("active", Boolean.class)),
                record.get(DSL.field("disabled", Boolean.class)),
                record.get(DSL.field("exact_match", Boolean.class)),
                record.get(DSL.field("sort_order", Integer.class)));
    }

    public record NavigationRow(
            long navigationSid,
            long userSid,
            Long parentNavigationSid,
            String navigationKey,
            String itemId,
            String title,
            String subtitle,
            String type,
            String icon,
            String link,
            String tooltip,
            String badgeTitle,
            String badgeClasses,
            Boolean active,
            Boolean disabled,
            Boolean exactMatch,
            Integer sortOrder) {
    }
}
