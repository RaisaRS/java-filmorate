package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class FriendshipDaoImpl implements FriendshipDao {
    private final JdbcTemplate jdbcTemplate;

    public FriendshipDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Long> allUsersFriends(Long id) {
        String sql = "SELECT friend_id " +
                "FROM friendship " +
                "WHERE user_id = ? " +
                "AND status = TRUE " +
                "UNION SELECT user_id " +
                "FROM friendship" +
                " WHERE friend_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNull) -> rs.getLong("FRIEND_ID"), id, id);
    }

    @Override
    public boolean addFriend(Long userId, Long friendId) {
        String sql = "INSERT INTO FRIENDSHIP (USER_ID, FRIEND_ID, STATUS) VALUES (?,?, false)";
        return jdbcTemplate.update(sql, friendId, userId) > 0;
    }

    @Override
    public boolean deleteFriend(Long userId, Long friendId) {
        String sql = "DELETE FROM FRIENDSHIP WHERE (USER_ID = ? AND FRIEND_ID = ?) OR (USER_ID = ? AND FRIEND_ID = ?)";
        return jdbcTemplate.update(sql, userId, friendId, friendId, userId) > 0;
    }

    @Override
    public boolean updateFriend(Long userId, Long friendId, boolean status) {
        String sql = "UPDATE FRIENDSHIP SET STATUS = ? WHERE USER_ID = ? AND FRIEND_ID = ?";
        return jdbcTemplate.update(sql, status, userId, friendId) > 0;
    }
}
