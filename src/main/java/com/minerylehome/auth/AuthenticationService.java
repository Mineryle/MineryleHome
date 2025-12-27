package com.minerylehome.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JdbcTemplate jdbcTemplate;

    public AuthenticationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserRecord> findUserByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT id, email, password_hash, name, avatar, status FROM \"user\" WHERE email = ?",
                    (rs, rowNum) -> new UserRecord(
                            rs.getLong("id"),
                            rs.getString("email"),
                            rs.getString("password_hash"),
                            rs.getString("name"),
                            rs.getString("avatar"),
                            rs.getString("status")),
                    email));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public boolean passwordMatches(String rawPassword, String storedHash) {
        String candidate = hashPassword(rawPassword);
        return MessageDigest.isEqual(
                candidate.getBytes(StandardCharsets.UTF_8),
                storedHash.getBytes(StandardCharsets.UTF_8));
    }

    public long createSession(long userId, String sessionId) {
        return jdbcTemplate.queryForObject(
                "INSERT INTO user_session (user_id, session_id) VALUES (?, ?) RETURNING id",
                Long.class,
                userId,
                sessionId);
    }

    public void recordActivity(long userSessionId, String method, String path, int status) {
        jdbcTemplate.update(
                "INSERT INTO user_session_activity (user_session_id, request_method, request_path, response_status) "
                        + "VALUES (?, ?, ?, ?)",
                userSessionId,
                method,
                path,
                status);
        jdbcTemplate.update(
                "UPDATE user_session SET last_activity_at = NOW() WHERE id = ?",
                userSessionId);
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
