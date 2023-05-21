package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendshipDao;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service("UserDbService")
@Slf4j
public class UserDbService implements UserService {
    private final UserStorage userStorage;
    private final FriendshipDao friendshipDao;

    @Autowired
    public UserDbService(UserStorage userStorage, FriendshipDao friendshipDao) {
        this.userStorage = userStorage;
        this.friendshipDao = friendshipDao;
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    @Override
    public Collection<User> usersList() {
        return userStorage.usersList();
    }

    public User getOneUser(long id) {
        return userStorage.getOneUser(id);
    }

    @Override
    public Collection<User> getAllUserFriends(long id) {
        Collection<Long> ids = friendshipDao.allUsersFriends(id);
        return userStorage.findAllByIds(ids);
    }

    @Override
    public List<User> listOfMutualFriends(long id, long otherId) { //вывод списка общих друзей
        getOneUser(id);
        getOneUser(otherId);
        return friendshipDao.allUsersFriends(id).stream()
                .filter(friendshipDao.allUsersFriends(otherId)::contains)
                .map(this::getOneUser)
                .collect(Collectors.toList());
    }

    //Пока пользователям не надо одобрять заявки в друзья — добавляем сразу.
    // То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
    // теперь дружба стала неоднозначная и совсем не прозрачная,
    // как же разобраться какая односторонняяя, а какая двусторонняя?
    @Override
    public Collection<Long> addFriend(long userId, long friendId) {  //добавление в друзья
        getOneUser(userId);
        getOneUser(friendId);
        boolean userFriendOne = friendshipDao.allUsersFriends(userId).contains(friendId);
        boolean userFriendTwo = friendshipDao.allUsersFriends(friendId).contains(userId);
        if (!userFriendOne && !userFriendTwo) {
            friendshipDao.addFriend(userId, friendId);
        } else if (userFriendOne && userFriendTwo) {
            friendshipDao.updateFriend(userId, friendId, true);
        } else {
            log.debug("Пользовтель {} уже отправил заявку в друзья пользователю {}." +
                    " Дружба неопределённая. ", userId, friendId);
        }
        return friendshipDao.allUsersFriends(userId);
    }

    @Override
    public void deleteFriend(long userId, long friendId) {  //удаление из друзей
        getOneUser(userId);
        getOneUser(friendId);
        boolean isUserFriendOne = friendshipDao.allUsersFriends(userId).contains(friendId);
        boolean isUserFriendTwo = friendshipDao.allUsersFriends(friendId).contains(userId);
        if (!isUserFriendOne) {
            log.info("Пользовтель {} никогда не был другом пользователя {}. ", userId, friendId);
            throw new UserNotFoundException("Пользователи никогда не были друзьями" + userId + "," + friendId);
        } else if (!isUserFriendTwo) {
            friendshipDao.deleteFriend(userId, friendId);
        } else {
            if (!friendshipDao.updateFriend(userId, friendId, false)) {
                friendshipDao.deleteFriend(friendId, userId);
                friendshipDao.deleteFriend(userId, friendId);
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




