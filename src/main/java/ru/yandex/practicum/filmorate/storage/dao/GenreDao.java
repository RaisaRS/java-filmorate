package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAllGenres();

    Genre getOneGenre(int id);

    List<Genre> getAllGenresByFilm(long id);
}
