package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Map<Long, User> allUsers() {
        return users;
    }

    @Override
    public User addUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User deleteUser(User user) {
        checkUser(user.getId(), "Пользователь не найден");
        return users.remove(user.getId());
    }

    @Override
    public User updateUser(User user) {
        checkUser(user.getId(), "Пользователь не найден.");
        long id = user.getId();
        if (users.containsKey(id)) {
            users.put(id, user);
            return user;
        } else {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    @Override
    public Collection<User> usersList() {
        return users.values();
    }

    public User getOneUser(Long id) {
        if (users.containsKey(id)) {
            return users.get(id);
        } else {
            throw new UserNotFoundException("Пользователь не найден id= " + id);
        }
    }

    @Override
    public Collection<User> getAllUserFriends(Long id) {
        User user = getOneUser(id);
        checkUser(id, "Пользоватеель не найден.");
        if (user != null) {
            Set<Long> friends = users.get(id).getFriends();
            if (!friends.isEmpty()) {
                return friends.stream()
                        .map(users::get)
                        .collect(Collectors.toList());
            }
        }
        throw new ObjectNotFoundException("У пользователя нет друзей.");
    }

    public void addFriend(Long userId, Long otherId) {  //добавление в друзья
        User user = getOneUser(userId);
        User otherUser = getOneUser(otherId);
        user.addFriend(otherId);
        otherUser.addFriend(userId);
    }

    public void deleteFriend(Long id, Long otherId) {  //удаление из друзей
        User user = getOneUser(id);
        User otherUser = getOneUser(otherId);
        isValidUsers(user, otherUser);
        checkUser(id, "Пользователь не найден");
        checkUser(otherId, "Пользователь не найден");
        if (user.getFriends().contains(otherId)) {
            user.getFriends().remove(otherId);
            otherUser.getFriends().remove(id);
        } else {
            throw new UserNotFoundException("Пользователь не существует");
        }
    }

    public Collection<User> listOfMutualFriends(Long id, Long otherId) { //вывод списка общих друзей
        User user = getOneUser(id);
        User otherUser = getOneUser(otherId);
        isValidUsers(user, otherUser);
        return user.getFriends().stream()
                .filter(otherUser.getFriends()::contains)
                .map(users::get)
                .collect(Collectors.toList());
    }

    private void isValidUsers(User user, User otherUser) { //если пользователей нет
        if (user == null || otherUser == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    public void checkUser(long id, String message) { //если пользователей нет в мапе - искл
        if (!users.containsKey(id)) {
            printErrorMessage(message + id);
        }
    }

    private void printErrorMessage(String message) {
        log.error(message);
        throw new FilmNotFoundException(message);
    }
}



