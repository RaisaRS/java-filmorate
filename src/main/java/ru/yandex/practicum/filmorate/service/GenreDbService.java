package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;

@Service("GenreDbService ")
@Slf4j
@RequiredArgsConstructor
public class GenreDbService implements GenreService {
    private final GenreStorage genreStorage;

    @Override
    public Collection<Genre> genreList() {
        log.info("Выведены все жанры");
        return genreStorage.getAllGenres();
    }

    @Override
    public Genre getOneGenre(int id) {
        log.info("Получен жанр с id {} ", id);
        return genreStorage.getOneGenre(id);
    }
}
