package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserDbStorageTest {

    private final UserStorage userStorage;

    @Test
    void shouldCreateUserWithId() throws JsonProcessingException {
        User userForTest = createTesstUser();
        userStorage.addUser(userForTest);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getOneUser(1L));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1L));
    }

    @Test
    void shouldCreateUserWithEmail() throws JsonProcessingException {
        User userForTest = createTesstUser();
        userStorage.addUser(userForTest);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getOneUser(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("email", "email@mail.ru"));
    }

    @Test
    void shouldCreateUserWithLogin() throws JsonProcessingException {
        User userForTest = createTesstUser();
        userStorage.addUser(userForTest);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getOneUser(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("login", "login"));
    }

    @Test
    void shouldCreateUserWithName() throws JsonProcessingException {
        User userForTest = createTesstUser();
        userStorage.addUser(userForTest);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getOneUser(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "name"));
    }

    @Test
    void shouldCreateUserWithBirthday() throws JsonProcessingException {
        User userForTest = createTesstUser();
        userStorage.addUser(userForTest);
        Optional<User> userOptional = Optional.ofNullable(userStorage.getOneUser(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("birthday",
                                LocalDate.of(1984, 3, 22)));
    }

    @Test
    void shouldUpdateUserWithId() throws JsonProcessingException {
        User userForTest = createTesstUser();
        userStorage.addUser(userForTest);
        User forUpdate = updateUserTest();
        userStorage.updateUser(forUpdate);
        Optional<User> updateUser = Optional.ofNullable(userStorage.getOneUser(1L));

        assertThat(updateUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1L));
    }

    @Test
    void shouldUpdateUserWithEmail() throws JsonProcessingException {
        User userForTest = createTesstUser();
        userStorage.addUser(userForTest);
        User forUpdate = updateUserTest();
        userStorage.updateUser(forUpdate);
        Optional<User> updateUser = Optional.ofNullable(userStorage.getOneUser(1));

        assertThat(updateUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("email", "updated@mail.ru"));
    }

    @Test
    void shouldUpdatedUserByLogin() throws JsonProcessingException {
        User userForTest = createTesstUser();
        userStorage.addUser(userForTest);
        User forUpdate = updateUserTest();
        userStorage.updateUser(forUpdate);
        Optional<User> updateUser = Optional.ofNullable(userStorage.getOneUser(1));

        assertThat(updateUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("login", "updatedLogin"));
    }

    @Test
    void shouldUpdateUserWithName() throws JsonProcessingException {
        User userForTest = createTesstUser();
        userStorage.addUser(userForTest);
        User forUpdate = updateUserTest();
        userStorage.updateUser(forUpdate);
        Optional<User> updateUser = Optional.ofNullable(userStorage.getOneUser(1));

        assertThat(updateUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "updatedLogin"));
    }

    @Test
    void shouldUpdateUserWithBirthday() throws JsonProcessingException {
        User userForTest = createTesstUser();
        userStorage.addUser(userForTest);
        User forUpdate = updateUserTest();
        userStorage.updateUser(forUpdate);
        Optional<User> updateUser = Optional.ofNullable(userStorage.getOneUser(1));

        assertThat(updateUser)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("birthday",
                                LocalDate.of(2000, 7, 15)));
    }

    @Test
    void shouldGetAllUsers() throws JsonProcessingException {
        User user = createTesstUser();
        User user1 = updateUserTest();
        user1.setId(2);
        userStorage.addUser(user);
        userStorage.addUser(user1);

        Collection<User> allUsers = userStorage.usersList();

        assertEquals(2, allUsers.size(), "Список пользователей не соответствует ожидаемому");
    }

    private User createTesstUser() {
        return User.builder().id(1L).email("email@mail.ru").login("login").name("name")
                .birthday(LocalDate.of(1984, 3, 22)).build();
    }

    private User updateUserTest() {
        return User.builder().id(1L).email("updated@mail.ru").login("updatedLogin").name("updatedLogin")
                .birthday(LocalDate.of(2000, 7, 15)).build();
    }
}


