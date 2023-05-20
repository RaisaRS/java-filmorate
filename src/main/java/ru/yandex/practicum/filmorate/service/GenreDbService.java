package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.util.Collection;

@Service("GenreDbService ")
@Slf4j
@RequiredArgsConstructor
public class GenreDbService implements GenreService {
    private final GenreDao genreDao;

    @Override
    public Collection<Genre> genreList() {
        return genreDao.getAllGenres();
    }

    @Override
    public Genre getOneGenre(int id) {
        return genreDao.getOneGenre(id);
    }
}
