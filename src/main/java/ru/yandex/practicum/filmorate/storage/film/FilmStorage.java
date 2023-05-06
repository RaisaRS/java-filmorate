package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Collection<Film> filmsList();

    Film addFilm(Film film);

    Film deleteFilm(Film film);

    Film updateFilm(Film film);

    Film getOneFilm(Long id);

    public Collection<Film> listPopularFilms(int count);
}
