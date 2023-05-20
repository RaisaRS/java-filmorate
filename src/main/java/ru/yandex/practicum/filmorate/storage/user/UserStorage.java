package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User addUser(User user);

    User deleteUser(User user);

    User updateUser(User user);

    Collection<User> usersList();

    User getOneUser(Long id);

    Collection<User> findAllByIds(Collection<Long> ids);
}

