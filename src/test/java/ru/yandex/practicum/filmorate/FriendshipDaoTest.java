package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FriendshipDaoTest {

    private final FriendshipStorage friendshipStorage;
    private final UserStorage userStorage;
    private final UserService userService;

    @Test
    void shouldAddFriend() throws JsonProcessingException {
        User user1 = createTestUser();
        User user2 = createSecondUser();
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        friendshipStorage.addFriend(1, 2);

        Collection<User> friendsOfUser1 = userStorage.findAllByIds(friendshipStorage.allUsersFriends(1));
        Collection<User> friendsOfUser2 = userStorage.findAllByIds(friendshipStorage.allUsersFriends(2));

        assertEquals(1, friendsOfUser1.size(), "Списки друзей не совпадают");
        assertEquals(0, friendsOfUser2.size(), "Списки друзей не совпадают");
    }

    @Test
    void shouldDeleteFriend() throws JsonProcessingException {
        User user1 = createTestUser();
        User user2 = createSecondUser();
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        friendshipStorage.addFriend(1, 2);
        friendshipStorage.deleteFriend(1, 2);

        Collection<Long> friendsOfUser1 = friendshipStorage.allUsersFriends(1);

        assertEquals(0, friendsOfUser1.size(), "Списки друзей не совпадают");
    }

    @Test
    void shouldGetCommonFriends() throws JsonProcessingException {
        User user1 = createTestUser();
        User user2 = createSecondUser();
        User user3 = createThirdUser();
        userStorage.addUser(user1);
        userStorage.addUser(user2);
        userStorage.addUser(user3);
        friendshipStorage.addFriend(1, 3);
        friendshipStorage.addFriend(2, 3);

        List<User> commonFriends = userService.listOfMutualFriends(1, 2);

        assertEquals(userStorage.getOneUser(3), commonFriends.get(0), "Списки общих друзей не совпадают");

    }

    private User createTestUser() {
        return User.builder().id(1).name("FirstUser").login("FirstLogin").email("first@mail.ru")
                .birthday(LocalDate.of(1984, 3, 22)).build();
    }

    private User createSecondUser() {
        return User.builder().id(2).name("SecondUser").login("SecondLogin").email("second@mail.ru")
                .birthday(LocalDate.of(2000, 2, 20)).build();
    }

    private User createThirdUser() {
        return User.builder().id(3).name("ThirdUser").login("ThirdLogin").email("third@mail.ru")
                .birthday(LocalDate.of(1812, 2, 10)).build();
    }
}


