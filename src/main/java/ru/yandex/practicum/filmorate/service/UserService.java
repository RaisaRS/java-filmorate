package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;
    private long id = 0;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        user.setId(++id);
        isValidNameUser(user);
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        isValidNameUser(user);
        return userStorage.updateUser(user);
    }

    public Collection<User> usersList() {
        return userStorage.usersList();
    }

    public User getOneUser(Long id) {
        return userStorage.getOneUser(id);
    }

    //Пока пользователям не надо одобрять заявки в друзья — добавляем сразу.
    // То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
    public void addFriend(Long userId, Long otherId) {  //добавление в друзья
        userStorage.addFriend(userId, otherId);
        log.info("Пользовтель {} стал другом пользователя {}. Дружба взаимна ", userId, otherId);
    }

    public void deleteFriend(Long userId, Long otherId) {  //удаление из друзей
        userStorage.deleteFriend(userId, otherId);
        log.info("Пользовтель {} перестал быть другом пользователя {}. Дружба закончилась ", userId, otherId);
    }

    public Collection<User> getAllUserFriends(Long id) {
        return userStorage.getAllUserFriends(id);
    }

    public Collection<User> listOfMutualFriends(Long id, Long otherId) { //вывод списка общих друзей
        return userStorage.listOfMutualFriends(id, otherId);
    }

    private void isValidNameUser(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            log.error("Имя пользователя пустое. Установлен логин {} в качестве имени.", user.getLogin());
            user.setName(user.getLogin());
        }
    }
}



