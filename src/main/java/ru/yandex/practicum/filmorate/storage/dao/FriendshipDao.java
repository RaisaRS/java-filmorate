package ru.yandex.practicum.filmorate.storage.dao;

import java.util.List;

public interface FriendshipDao {
    List<Long> allUsersFriends(long id);

    boolean addFriend(long userId, long friendId);

    boolean deleteFriend(long userId, long friendId);

    boolean updateFriend(long userId, long friendId, boolean status);
}
