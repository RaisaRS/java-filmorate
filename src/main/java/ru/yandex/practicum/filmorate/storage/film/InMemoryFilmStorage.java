package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>(); //хранение объектов - фильмов
    private final Comparator<Film> sortingComparator = (f1, f2) -> f2.getLikes().size()
            - f1.getLikes().size(); //сортировка фильмов по популярности

    @Override
    public Collection<Film> filmsList() {
        return films.values();
    }

    @Override
    public Film addFilm(Film film) {  //добавление фильма
        //film.setId(film.getId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film deleteFilm(Film film) {   //удаление фильма
        checkFilm(film.getId(), "Удалить нельзя, фильм не существует " + film.getId());
        return films.remove(film.getId());

    }

    @Override
    public Film updateFilm(Film film) {   //обновление фильма
        checkFilm(film.getId(), "Обновление невозможно, фильм не существует " + film.getId());
        Long id = film.getId();
        if (films.containsKey(id)) {
            films.put(film.getId(), film);
            return film;
        } else {
            throw new FilmNotFoundException("Фильм не найден.");
        }
    }

    @Override
    public Film getOneFilm(Long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new FilmNotFoundException("Фильм не найден");
        }
    }

    public Collection<Film> listPopularFilms(int count) {   //вывод 10 наиболее популярных
        Collection<Film> popularFilms = films.values();     //по количеству лайков фильма
        if (!popularFilms.isEmpty()) {
            return popularFilms.stream()
                    .sorted(sortingComparator)
                    .limit(count)
                    .collect(Collectors.toList());
        }
        return null;
        //  throw new ObjectNotFoundException("Список фильмов не найден.");
    }

    private void checkFilm(long id, String message) {
        if (!films.containsKey(id)) {
            printErrorMessage(message + id);
        }
    }

    private void printErrorMessage(String message) {
        log.error(message);
        throw new FilmNotFoundException(message);
    }
}
