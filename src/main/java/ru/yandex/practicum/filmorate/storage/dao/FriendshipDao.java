package ru.yandex.practicum.filmorate.storage.dao;

import java.util.Collection;

public interface FriendshipDao {
    Collection<Long> allUsersFriends(Long id);

    boolean addFriend(Long userId, Long friendId);

    boolean deleteFriend(Long userId, Long friendId);

    boolean updateFriend(Long userId, Long friendId, boolean status);
}
