package ru.yandex.practicum.filmorate.storage.friendship;

import java.util.List;

public interface FriendshipStorage {
    List<Long> allUsersFriends(long id);

    boolean addFriend(long userId, long friendId);

    boolean deleteFriend(long userId, long friendId);

    boolean updateFriend(long userId, long friendId, boolean status);
}
