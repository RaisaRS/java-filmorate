package ru.yandex.practicum.filmorate.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Calendar.DECEMBER;

@Service("FilmDbService")
@Slf4j
public class FilmDbService implements FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;
    private final GenreDao genreDao;
    private static final LocalDate MIN_DAY_RELEASE = LocalDate.of(1895, DECEMBER, 28);
    private final Comparator<Film> sortingComparator = (f1, f2) -> f2.getLikes().size()
            - f1.getLikes().size();

    @Autowired
    public FilmDbService(@Qualifier(value = "FilmDbStorage") FilmStorage filmStorage, UserService userService, GenreDao genreDao) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.genreDao = genreDao;
    }

    @Override
    public Film createFilm(Film film) {
        validateFim(film);
        return filmStorage.addFilm(film);
    }

    @Override
    public Film updateFilm(Film film) {
        validateFim(film);
        return filmStorage.updateFilm(film);
    }

    @Override
    public List<Film> filmList() {
        return filmStorage.filmsList().stream()
                .peek(film -> genreDao.getAllGenresByFilm(film.getId())
                        .forEach(film::addGenre))
                .collect(Collectors.toList());
    }

    @Override
    public Film getOneFilm(long id) {
        Film film = filmStorage.getOneFilm(id);
        genreDao.getAllGenresByFilm(film.getId()).forEach(film::addGenre);
        return film;
    }

    @Override
    public Collection<Long> addLike(long filmId, long userId) {    //добавление лайка
        getOneFilm(filmId);
        userService.getOneUser(userId);
        filmStorage.addLike(filmId, userId);
        return filmStorage.getLikesByFilm(filmId);
    }

    @Override
    public Collection<Long> deleteLike(long filmId, long userId) {    //удаление лайка
        getOneFilm(filmId);
        userService.getOneUser(userId);
        filmStorage.deleteLike(filmId, userId);
        return filmStorage.getLikesByFilm(filmId);
    }

    @Override
    @SneakyThrows
    public Collection<Film> listPopularFilms(int count) {   //вывод популярных по количеству лайков фильмов
        return filmStorage.listPopularFilms(count).stream()
                .peek(film -> genreDao.getAllGenresByFilm(film.getId())
                        .forEach(film::createGenre))
                .collect(Collectors.toList());
    }

    private void validateFim(Film film) {
        if (film.getReleaseDate().isBefore(MIN_DAY_RELEASE)) {
            log.error("Дата релиза ранее установленной даты  {} 28.12.1895г.", film.getReleaseDate());
            throw new ValidationException("Дата релиза ранее установленной даты 28.12.1895г." + film.getReleaseDate());
        }
    }
}
