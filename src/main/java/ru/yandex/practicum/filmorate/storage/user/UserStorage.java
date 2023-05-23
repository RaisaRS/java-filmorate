package ru.yandex.practicum.filmorate.storage.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    User addUser(User user) throws JsonProcessingException;

    User deleteUser(User user) throws JsonProcessingException;

    User updateUser(User user);

    Collection<User> usersList();

    User getOneUser(long id);

    Collection<User> findAllByIds(Collection<Long> ids);
}

