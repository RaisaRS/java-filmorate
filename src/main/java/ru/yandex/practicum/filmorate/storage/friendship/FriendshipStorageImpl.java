package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendshipStorageImpl implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendshipStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Long> allUsersFriends(long id) {
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
    public boolean addFriend(long userId, long friendId) {
        String sql = "INSERT INTO FRIENDSHIP (USER_ID, FRIEND_ID, STATUS) VALUES (?,?, false)";
        return jdbcTemplate.update(sql, friendId, userId) > 0;
    }

    @Override
    public boolean deleteFriend(long userId, long friendId) {
        String sql = "DELETE FROM FRIENDSHIP WHERE (USER_ID = ? AND FRIEND_ID = ?) OR (USER_ID = ? AND FRIEND_ID = ?)";
        return jdbcTemplate.update(sql, userId, friendId, friendId, userId) > 0;
    }

    @Override
    public boolean updateFriend(long userId, long friendId, boolean status) {
        String sql = "UPDATE FRIENDSHIP SET STATUS = ? WHERE USER_ID = ? AND FRIEND_ID = ?";
        return jdbcTemplate.update(sql, status, userId, friendId) > 0;
    }
}
