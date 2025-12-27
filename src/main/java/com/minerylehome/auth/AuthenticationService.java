package com.minerylehome.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final DSLContext dsl;

    public AuthenticationService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<UserRecord> findUserByEmail(String email) {
        Record record = dsl.select(
                        DSL.field("user_sid", Long.class),
                        DSL.field("email", String.class),
                        DSL.field("password_hash", String.class),
                        DSL.field("name", String.class),
                        DSL.field("avatar", String.class),
                        DSL.field("status", String.class))
                .from(DSL.table(DSL.name("user")))
                .where(DSL.field("email").eq(email))
                .fetchOne();
        if (record == null) {
            return Optional.empty();
        }
        return Optional.of(new UserRecord(
                record.get(DSL.field("user_sid", Long.class)),
                record.get(DSL.field("email", String.class)),
                record.get(DSL.field("password_hash", String.class)),
                record.get(DSL.field("name", String.class)),
                record.get(DSL.field("avatar", String.class)),
                record.get(DSL.field("status", String.class))));
    }

    public boolean passwordMatches(String rawPassword, String storedHash) {
        String candidate = hashPassword(rawPassword);
        return MessageDigest.isEqual(
                candidate.getBytes(StandardCharsets.UTF_8),
                storedHash.getBytes(StandardCharsets.UTF_8));
    }

    public long createSession(long userId, String sessionId) {
        return dsl.insertInto(DSL.table("user_session"))
                .columns(DSL.field("user_sid"), DSL.field("session_id"))
                .values(userId, sessionId)
                .returningResult(DSL.field("user_session_sid", Long.class))
                .fetchOne()
                .value1();
    }

    public void recordActivity(long userSessionId, String method, String path, int status) {
        dsl.insertInto(DSL.table("user_session_activity"))
                .columns(
                        DSL.field("user_session_sid"),
                        DSL.field("request_method"),
                        DSL.field("request_path"),
                        DSL.field("response_status"))
                .values(userSessionId, method, path, status)
                .execute();
        dsl.update(DSL.table("user_session"))
                .set(DSL.field("last_activity_at"), DSL.currentTimestamp())
                .where(DSL.field("user_session_sid").eq(userSessionId))
                .execute();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 algorithm not available", ex);
        }
    }

    public record UserRecord(
            long id,
            String email,
            String passwordHash,
            String name,
            String avatar,
            String status) {
    }
}
