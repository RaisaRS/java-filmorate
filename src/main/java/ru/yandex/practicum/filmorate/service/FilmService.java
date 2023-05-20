package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmService {
    Film createFilm(Film film);

    Film updateFilm(Film film);

    List<Film> filmList();

    Film getOneFilm(long id);

    Collection<Long> addLike(long filmId, long userId);

    Collection<Long> deleteLike(long filmId, long userId);

    Collection<Film> listPopularFilms(int count);
}
