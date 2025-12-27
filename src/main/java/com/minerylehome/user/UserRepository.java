package com.minerylehome.user;

import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final DSLContext dsl;

    public UserRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<UserController.UserProfile> findUserProfile(long userId) {
        Record record = dsl.select(
                        DSL.field("user_sid", Long.class),
                        DSL.field("name", String.class),
                        DSL.field("email", String.class),
                        DSL.field("avatar", String.class),
                        DSL.field("status", String.class))
                .from(DSL.table(DSL.name("user")))
                .where(DSL.field("user_sid").eq(userId))
                .fetchOne();
        if (record == null) {
            return Optional.empty();
        }
        return Optional.of(new UserController.UserProfile(
                Long.toString(record.get(DSL.field("user_sid", Long.class))),
                record.get(DSL.field("name", String.class)),
                record.get(DSL.field("email", String.class)),
                record.get(DSL.field("avatar", String.class)),
                record.get(DSL.field("status", String.class))));
    }

    public UserController.UserProfile updateUserProfile(long userId, UserController.UserProfile profile) {
        dsl.update(DSL.table(DSL.name("user")))
                .set(DSL.field("name"), profile.name())
                .set(DSL.field("email"), profile.email())
                .set(DSL.field("avatar"), profile.avatar())
                .set(DSL.field("status"), profile.status())
                .set(DSL.field("updated_at"), DSL.currentTimestamp())
                .where(DSL.field("user_sid").eq(userId))
                .execute();
        return findUserProfile(userId).orElse(profile);
    }
}
