package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User addUser(User user);

    User updateUser(User user);

    Collection<User> usersList();

    User getOneUser(long id);

    Collection<User> getAllUserFriends(long id);

    List<User> listOfMutualFriends(long id, long friendId);

    Collection<Long> addFriend(long userId, long friendId);

    void deleteFriend(long userId, long friendId);
}
