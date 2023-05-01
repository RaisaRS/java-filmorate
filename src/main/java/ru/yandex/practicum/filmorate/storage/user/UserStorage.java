package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Map;

public interface UserStorage {
    Map<Long, User> allUsers();

    User addUser(User user);

    User deleteUser(User user);

    User updateUser(User user);

    Collection<User> usersList();

    User getOneUser(Long id);

    Collection<User> getAllUserFriends(Long id);

    void addFriend(Long userId, Long otherId);

    void deleteFriend(Long userId, Long otherId);

    Collection<User> listOfMutualFriends(Long id, Long otherId);
}

