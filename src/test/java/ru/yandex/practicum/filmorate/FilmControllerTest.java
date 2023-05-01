package ru.yandex.practicum.filmorate;

public class FilmControllerTest extends FilmorateApplicationTests {

   /* private static FilmController filmController;
    private static Film film;

    @BeforeEach
    void initEach() {
        film = new Film(1L, "name", "description", LocalDate.of(2005, 1, 17),
                125);
    }

    @AfterEach
    void finishedTest() {
        filmController.getFilms().clear();
    }

    @BeforeAll
    static void init() {
        filmController = new FilmController();
    }

    @Test
    public void shouldAddFilmTest() {
        assertEquals(film, filmController.addFilm(film));
        filmController.filmsList();
        assertNotNull(filmController.getFilms());
    }

    @Test
    public void inCorrectIdTest() {
        filmController.addFilm(film);
        film.setId(5L);
        Exception exception = assertThrows(ValidationException.class, () -> filmController.putFilm(film));
        String expectedMessage = "Фильм с таким идентификатором не существует " + film.getId();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldNameIsEmpty() {
        filmController.addFilm(film);
        film.setName("");
        Exception exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        String expectedMessage = "Название фильма отсутствует. " + film.getName();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldNameNull() {
        Film film = new Film(1L, null, "description", LocalDate.of(2005, 1, 17),
                125);
        Exception exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        String expectedMessage = "Название фильма отсутствует. " + film.getName();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldLoginNotNull() {
        Film film = new Film(1L, "name", "Description///////////////////////////////" +
                "/////////////////////////////////////////////////////////////////////////////////////" +
                "/////////////////////////////////////////////////////////////////////////////////////" +
                "/////////////////////", LocalDate.of(2005, 1, 17),
                125);
        Exception exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        String expectedMessage = "Описание фильма превышает заданную длину в 200 символов. ";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldBirthdayNotFuture() {
        film.setReleaseDate(LocalDate.of(1888, 1, 17));
        Exception exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        String expectedMessage = "Дата релиза ранее установленной даты 28.12.1895г." + film.getReleaseDate();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldDurationIsPositive() {
        film.setDuration(-1);
        Exception exception = assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        String expectedMessage = "Неверно указана продолжительность фильма. Введено отрицательное значение. "
                + film.getDuration();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldUpdateTestOne() {
        film.setId(0L);
        Exception exception = assertThrows(ValidationException.class, () -> filmController.putFilm(film));
        String expectedMessage = "Фильм с таким идентификатором не существует " + film.getId();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldUpdateTestTwo() {
        filmController.addFilm(film);
        film.setReleaseDate(LocalDate.of(1888, 12, 8));
        Exception exception = assertThrows(ValidationException.class, () -> filmController.putFilm(film));
        String expectedMessage = "Дата релиза ранее установленной даты 28.12.1895г." + film.getReleaseDate();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldUpdateTestThree() {
        filmController.addFilm(film);
        film.setDescription("описаниеФильмаНеДолжноПревышатьДлинуВДвестиСимволовВЭтомТекстеВосемьдесятТриСимвола" +
                "описаниеФильмаНеДолжноПревышатьДлинуВДвестиСимволовВЭтомТекстеВосемьдесятТриСимвола" +
                "описаниеФильмаНеДолжноПревышатьДлинуВДвестиСимволовВЭтомТекстеВосемьдесятТриСимвола");
        Exception exception = assertThrows(ValidationException.class, () -> filmController.putFilm(film));
        String expectedMessage = "Описание фильма превышает заданную длину в 200 символов. ";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldUpdateTestFour() {
        filmController.addFilm(film);
        film.setDuration(0);
        Exception exception = assertThrows(ValidationException.class, () -> filmController.putFilm(film));
        String expectedMessage = "Неверно указана продолжительность фильма. Введено отрицательное значение. "
                + film.getDuration();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldUpdateTestFive() {
        filmController.addFilm(film);
        film.setDuration(-5);
        Exception exception = assertThrows(ValidationException.class, () -> filmController.putFilm(film));
        String expectedMessage = "Неверно указана продолжительность фильма. Введено отрицательное значение. "
                + film.getDuration();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldGetListFilms() {
        Film film1 = new Film(2L, "name1", "description1", LocalDate.of(2005, 1, 17),
                125);
        filmController.addFilm(film);
        filmController.addFilm(film1);
        assertFalse(filmController.filmsList().isEmpty());
        assertEquals(2, filmController.filmsList().size());
    }*/
}
