package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserService {
    User addUser(User user);

    User updateUser(User user);

    Collection<User> usersList();

    User getOneUser(Long id);

    Collection<User> getAllUserFriends(Long id);

    Collection<User> listOfMutualFriends(Long id, Long friendId);

    Collection<Long> addFriend(Long userId, Long friendId);

    void deleteFriend(Long userId, Long friendId);
}
