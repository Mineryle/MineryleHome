package com.minerylehome.user;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserController.UserProfile> findUserProfile(long userId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT id, name, email, avatar, status FROM \"user\" WHERE id = ?",
                    (rs, rowNum) -> new UserController.UserProfile(
                            Long.toString(rs.getLong("id")),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("avatar"),
                            rs.getString("status")),
                    userId));
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    public UserController.UserProfile updateUserProfile(long userId, UserController.UserProfile profile) {
        jdbcTemplate.update(
                "UPDATE \"user\" SET name = ?, email = ?, avatar = ?, status = ?, updated_at = NOW() WHERE id = ?",
                profile.name(),
                profile.email(),
                profile.avatar(),
                profile.status(),
                userId);
        return findUserProfile(userId).orElse(profile);
    }
}
