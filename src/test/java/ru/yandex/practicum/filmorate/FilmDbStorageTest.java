package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class FilmDbStorageTest {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final GenreStorage genreStorage;
    private final MpaStorage mpaStorage;

    @Test
    void shouldCreateFilmWithName() {
        Film testFilm = createTestFilm();
        filmStorage.addFilm(testFilm);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getOneFilm(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "TestName"));
    }

    @Test
    void shouldCreateFilmWithDescription() {
        Film testFilm = createTestFilm();
        filmStorage.addFilm(testFilm);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getOneFilm(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("description",
                                "description"));
    }

    @Test
    void shouldCreateFilmWithReleaseDate() {
        Film testFilm = createTestFilm();
        filmStorage.addFilm(testFilm);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getOneFilm(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("releaseDate",
                                LocalDate.of(2020, 10, 10)));
    }

    @Test
    void shouldCreateFilmWithDuration() {
        Film testFilm = createTestFilm();
        filmStorage.addFilm(testFilm);

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.getOneFilm(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("duration", 180));
    }

    @Test
    void shouldUpdateFilmById() {
        Film testFilm = createTestFilm();
        filmStorage.addFilm(testFilm);
        Film filmForUpdate = createFilmForUpdate();

        filmStorage.updateFilm(filmForUpdate);
        Optional<Film> filmOptionalUpdated = Optional.ofNullable(filmStorage.getOneFilm(1));

        assertThat(filmOptionalUpdated)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1L));
    }

    @Test
    void shouldUpdateFilmWithName() {
        Film testFilm = createTestFilm();
        filmStorage.addFilm(testFilm);
        Film filmForUpdate = createFilmForUpdate();

        filmStorage.updateFilm(filmForUpdate);
        Optional<Film> filmOptionalUpdated = Optional.ofNullable(filmStorage.getOneFilm(1));

        assertThat(filmOptionalUpdated)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("name", "AfterUpdateName"));
    }

    @Test
    void shouldUpdateFilmWithDescription() {
        Film testFilm = createTestFilm();
        filmStorage.addFilm(testFilm);
        Film filmForUpdate = createFilmForUpdate();

        filmStorage.updateFilm(filmForUpdate);
        Optional<Film> filmOptionalUpdated = Optional.ofNullable(filmStorage.getOneFilm(1));

        assertThat(filmOptionalUpdated)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("description",
                                "afterUpdateDescription"));
    }

    @Test
    void shouldUpdateFilmWithDuration() {
        Film testFilm = createTestFilm();
        filmStorage.addFilm(testFilm);
        Film filmForUpdate = createFilmForUpdate();

        filmStorage.updateFilm(filmForUpdate);
        Optional<Film> filmOptionalUpdated = Optional.ofNullable(filmStorage.getOneFilm(1));

        assertThat(filmOptionalUpdated)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("duration", 130));
    }

    @Test
    void shouldGetAllFilms() {
        Film film = createTestFilm();
        Film film1 = createFilmForUpdate();
        film1.setId(2);
        filmStorage.addFilm(film);
        filmStorage.addFilm(film1);

        List<Film> allFilms = filmStorage.filmsList();

        assertEquals(2, allFilms.size());
    }

    @Test
    void shouldAddLikeToFilm() throws JsonProcessingException {
        Film filmTest = createFirstFilm();
        filmStorage.addFilm(filmTest);
        User user = createFirstUser();
        userStorage.addUser(user);
        filmStorage.addLike(1, 1);

        List<Long> likes = filmStorage.getLikesByFilm(1);

        assertEquals(1, likes.size(), "Списки лайков не совпадают");
    }

    @Test
    void shouldDeleteLikesFromFilm() throws JsonProcessingException {
        Film filmTest = createFirstFilm();
        filmStorage.addFilm(filmTest);
        User user = createFirstUser();
        userStorage.addUser(user);
        filmStorage.addLike(1, 1);
        filmStorage.deleteLike(1, 1);

        List<Long> likes = filmStorage.getLikesByFilm(1);
        assertEquals(0, likes.size(), "Списки лайков не совпадают");
    }

    @Test
    void shouldGetPopularFilmsWithoutFilms() {
        List<Film> popularFilms = filmStorage.listPopularFilms(10);
        assertThat(popularFilms)
                .isNotNull()
                .isEqualTo(Collections.EMPTY_LIST);
    }

    private User createFirstUser() {
        return User.builder().id(1).name("name").login("login").email("email@mail.ru")
                .birthday(LocalDate.of(1984, 3, 22)).build();
    }

    private User createSecondUser() {
        return User.builder().id(2).name("UpdatedName").login("loginUpdated").email("updated@mail.ru")
                .birthday(LocalDate.of(2001, 5, 5)).build();
    }

    private Film createFirstFilm() {
        Mpa mpa = mpaStorage.getOneMpa(1);
        return Film.builder().id(1).name("FirstName").description("description")
                .releaseDate(LocalDate.of(2020, 10, 10)).duration(180)
                .mpa(mpa).build();
    }

    private Film createSecondFilm() {
        Mpa mpa = mpaStorage.getOneMpa(2);
        return Film.builder().id(1).name("SecondName").description("AfterUpdate")
                .releaseDate(LocalDate.of(2023, 5, 1)).duration(130).mpa(mpa).build();
    }

    private Film createTestFilm() {
        Mpa mpa = mpaStorage.getOneMpa(1);
        Film testFilm = Film.builder()
                .id(1).name("TestName")
                .description("description")
                .releaseDate(LocalDate.of(2020, 10, 10)).duration(180)
                .mpa(mpa)
                .build();
        List<Genre> genres = testFilm.getGenres();
        genres.add(genreStorage.getOneGenre(1));
        return testFilm;
    }

    private Film createFilmForUpdate() {
        Mpa mpa = mpaStorage.getOneMpa(2);
        Film filmForUpdate = Film.builder().id(1).name("AfterUpdateName").description("afterUpdateDescription")
                .releaseDate(LocalDate.of(2023, 5, 1)).duration(130).mpa(mpa).build();
        List<Genre> testGenres = filmForUpdate.getGenres();
        testGenres.add(genreStorage.getOneGenre(2));
        return filmForUpdate;
    }
}


