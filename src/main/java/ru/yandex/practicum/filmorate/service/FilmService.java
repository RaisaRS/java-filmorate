package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmDoubleLikeException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;

import static java.util.Calendar.DECEMBER;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private static final LocalDate MIN_DAY_RELEASE = LocalDate.of(1895, DECEMBER, 28);

    private long id = 0;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film createFilm(Film film) {
        validateFim(film);
        film.setId(++id);
        return filmStorage.addFilm(film);
    }

    public Film deleteFilm(Film film) {
        validateFim(film);
        return filmStorage.deleteFilm(film);
    }

    public Film updateFilm(Film film) {
        validateFim(film);
        return filmStorage.updateFilm(film);
    }

    public Collection<Film> filmList() {
        return filmStorage.filmsList();
    }

    public Film getOneFilm(Long id) {
        return filmStorage.getOneFilm(id);
    }

    public void addLike(Long filmId, Long userId) {    //добавление лайка
        Film film = getOneFilm(filmId);
        User user = userStorage.getOneUser(userId);
        if (!film.getLikes().contains(user.getId())) {
            film.getLikes().add(user.getId());
            log.info("Пользователь с id= {} поставил лайк ", userId);
        } else {
            throw new FilmDoubleLikeException("Вы уже поставили лайк этому фильму");
        }

    }

    public void deleteLike(Long filmId, Long userId) {    //удаление лайка
        Film film = getOneFilm(filmId);
        User user = userStorage.getOneUser(userId);
        if (film.getLikes().contains(user.getId())) {
            film.getLikes().remove(user.getId());
            log.info("Пользователь с id= {} удалил лайк ", userId);
        } else {
            throw new ObjectNotFoundException("Здесь лайка не было и нет - удалять нечего");
        }
    }

    public Collection<Film> listPopularFilms(int count) {   //вывод 10 наиболее популярных по количеству лайков фильма
        return filmStorage.listPopularFilms(count);
    }

    private void validateFim(Film film) {
        if (film.getReleaseDate().isBefore(MIN_DAY_RELEASE)) {
            log.error("Дата релиза ранее установленной даты  {} 28.12.1895г.", film.getReleaseDate());
            throw new ValidationException("Дата релиза ранее установленной даты 28.12.1895г."
                    + film.getReleaseDate());
        }
    }
}
