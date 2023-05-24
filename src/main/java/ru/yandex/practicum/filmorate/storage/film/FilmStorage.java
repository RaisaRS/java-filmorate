package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    List<Film> filmsList();

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film getOneFilm(long id);

    List<Film> listPopularFilms(int count);

    boolean addGenreToFilm(long filmId, int genreId);

    boolean deleteAllGenresFromFilm(long filmId);

    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    List<Long> getLikesByFilm(long filmId);
}
