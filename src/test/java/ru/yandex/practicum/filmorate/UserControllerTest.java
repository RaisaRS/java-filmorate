package ru.yandex.practicum.filmorate;

public class UserControllerTest extends FilmorateApplicationTests {

    /*static UserController userController;
    private static User user;
    private static User user1;

    @BeforeAll
    static void init() {
        userController = new UserController(userService);
    }

    @BeforeEach
    void initEach() {
        user = new User(1, "popular@mail.ru", "login", "name",
                LocalDate.of(1984, 3, 22));
    }

    @AfterEach
    void finishedTest() {
        userController.getUsers().clear();
        userController.getUserEmails().clear();
    }

    @Test
    public void shouldAddUserTest() {
        assertEquals(user, userController.addUser(user));
        userController.usersList();
        assertNotNull(userController.getUsers());
    }

    @Test
    public void shouldEmailContainsSymbol() {
        user.setEmail("parapapam.pam");
        Exception exception = assertThrows(ValidationException.class, () -> userController.addUser(user));
        String expectedMessage = "Электронная почта введена некорректно: отсутствует символ @, либо незаполнена. "
                + user.getEmail();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldEmailNotNull() {
        User user = new User(1, "", "login", "name", LocalDate.of(1984, 3, 22));
        //user.setEmail(" ");
        Exception exception = assertThrows(ValidationException.class, () -> userController.addUser(user));
        String expectedMessage = "Электронная почта введена некорректно: отсутствует символ @, либо незаполнена. "
                + user.getEmail();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldLoginNotNull() {
        user.setLogin(null);
        Exception exception = assertThrows(ValidationException.class, () -> userController.addUser(user));
        String expectedMessage = "Логин отсутствует или содержит пробелы. ";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldLoginNotWhitespace() {
        user.setLogin(" log in");
        Exception exception = assertThrows(ValidationException.class, () -> userController.addUser(user));
        String expectedMessage = "Логин отсутствует или содержит пробелы. ";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void ifNameBlankLoginBestName() {
        user.setName(null);
        assertEquals(userController.addUser(user), user);
    }

    @Test
    public void shouldBirthdayNotFuture() {
        user.setBirthday(LocalDate.of(2025, 1, 17));
        Exception exception = assertThrows(ValidationException.class, () -> userController.addUser(user));
        String expectedMessage = "Введена некорректная дата рождения." + user.getBirthday();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldUpdateTest() {
        user.setId(99);
        user.setBirthday(LocalDate.of(2025, 12, 8));
        user.setEmail("email.email");
        user.setName(null);
        user.setLogin("login");
        Exception exception = assertThrows(ValidationException.class, () -> userController.putUser(user));
        String expectedMessage = "Пользователь с таким идентификатором не существует " + user.getId();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldGetListUsers() {
        user1 = new User(2, "popular1@mail.ru", "login1", "name1",
                LocalDate.of(1994, 5, 22));
        userController.addUser(user);
        userController.addUser(user1);
        assertFalse(userController.usersList().isEmpty());
        assertEquals(2, userController.usersList().size());
    }*/
}
