package ru.yandex.practicum.filmorate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service("UserDbService")
@Slf4j
public class UserDbService implements UserService {
    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    @Autowired
    public UserDbService(UserStorage userStorage, FriendshipStorage friendshipStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }

    public User addUser(User user) throws JsonProcessingException {
        log.info("Пользователь {} добавлен. ", user.getName());
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        log.info("Пользователь {} обновлен. ", user.getName());
        return userStorage.updateUser(user);
    }

    @Override
    public Collection<User> usersList() {
        log.info("Получен список пользователей");
        return userStorage.usersList();
    }

    public User getOneUser(long id) {
        log.info("Пользователь {} получен. ", id);
        return userStorage.getOneUser(id);
    }

    @Override
    public Collection<User> getAllUserFriends(long id) {
        Collection<Long> ids = friendshipStorage.allUsersFriends(id);
        log.info("Получен список id  друзей пользователя {} ", id);
        return userStorage.findAllByIds(ids);
    }

    @Override
    public List<User> listOfMutualFriends(long id, long otherId) { //вывод списка общих друзей
        getOneUser(id);
        getOneUser(otherId);
        log.info("Получен список общих друзей пользователей с id {} и id {} ", id, otherId);
        return friendshipStorage.allUsersFriends(id).stream()
                .filter(friendshipStorage.allUsersFriends(otherId)::contains)
                .map(this::getOneUser)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Long> addFriend(long userId, long friendId) {  //добавление в друзья
        getOneUser(userId);
        getOneUser(friendId);
        boolean userFriendOne = friendshipStorage.allUsersFriends(userId).contains(friendId);
        boolean userFriendTwo = friendshipStorage.allUsersFriends(friendId).contains(userId);
        if (!userFriendOne && !userFriendTwo) {
            friendshipStorage.addFriend(userId, friendId);
        } else if (userFriendOne && userFriendTwo) {
            friendshipStorage.updateFriend(userId, friendId, true);
        } else {
            log.debug("Пользовтель {} уже отправил заявку в друзья пользователю {}." +
                    " Дружба неопределённая. ", userId, friendId);
        }
        log.info("Пользователю с id {}  добавлен друг с id {} ", userId, friendId);
        return friendshipStorage.allUsersFriends(userId);
    }

    @Override
    public void deleteFriend(long userId, long friendId) {  //удаление из друзей
        getOneUser(userId);
        getOneUser(friendId);
        boolean isUserFriendOne = friendshipStorage.allUsersFriends(userId).contains(friendId);
        boolean isUserFriendTwo = friendshipStorage.allUsersFriends(friendId).contains(userId);
        if (!isUserFriendOne) {
            log.info("Пользовтель {} никогда не был другом пользователя {}. ", userId, friendId);
            throw new UserNotFoundException("Пользователи никогда не были друзьями" + userId + "," + friendId);
        } else if (!isUserFriendTwo) {
            friendshipStorage.deleteFriend(userId, friendId);
        } else {
            if (!friendshipStorage.updateFriend(userId, friendId, false)) {
                friendshipStorage.deleteFriend(friendId, userId);
                friendshipStorage.deleteFriend(userId, friendId);
            }
        }
    }

    private void validateBeforeAdd(User user) {
        if (user.getLogin().contains(" ")) {
            log.error("Логин не может содержать пробелы");
            throw new ValidationException("Логин не может содержать пробелы");
        }
        for (User user1 : usersList()) {
            if (user1.getEmail().equals(user.getEmail())) {
                log.error("Пользователь с email = {} уже существует", user.getEmail());
                throw new ValidationException(format("Пользователь с email = %s уже существует", user.getEmail()));
            }
            if (user1.getLogin().equals(user.getLogin())) {
                log.error("Пользователь с login = {} уже существует", user.getLogin());
                throw new ValidationException(format("Пользователь с login = %s уже существует", user.getLogin()));
            }
        }
    }
}




