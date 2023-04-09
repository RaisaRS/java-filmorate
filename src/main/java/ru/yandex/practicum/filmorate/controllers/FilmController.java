package ru.yandex.practicum.filmorate.controllers;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private final Set<String> nameFilms = new HashSet<>();
    private int id =  0;

    public Set<String> getNameFilms() {
        return nameFilms;
    }


    @PostMapping  //валидация должна быть по ТЗ //ДОБАВЛЕНИЕ нового фильма
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.info("POST request received: {}",film);
        if (nameFilms.contains(film.getName())) {
            log.error("Фильм с таким названием уже существует.");
            throw new ValidationException("Фильм с таким названием уже существует." + film.getName());
        } else if (film.getName() == null || film.getName().isEmpty() || film.getName().isBlank()) {
            log.error("Название фильма {} отсутствует. ", film.getName());
            throw new ValidationException("Название фильма отсутствует. " + film.getName());
        } // тут надо проверить описание на макс длину 200 символов
        if (film.getDescription().length() > 200) {
            log.error("Описание фильма {} превышает заданную длину в 200 символов. ", film.getDescription());
            //film.descriptionLength(film.getDescription());
            throw new ValidationException("Описание фильма превышает заданную длину в 200 символов. ");
        }
        //проверка на дату релиза не ранее 28.12.1895г.
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза ранее установленной даты  {} 28.12.1895г.", film.getReleaseDate());
            throw new ValidationException("Дата релиза ранее установленной даты 28.12.1895г." + film.getReleaseDate());
        }
        //продолжительность фильма должна быть положительной
        if (film.getDuration() < 0) {
            log.error("Неверно указана продолжительность фильма {}. Введено отрицательное значение. ", film.getDuration());
            throw new ValidationException("Неверно указана продолжительность фильма. Введено отрицательное значение. "
                    + film.getDuration());
        }
        id++;
        film.setId(id);
        films.put(film.getId(), film);
        nameFilms.add(film.getName());
        log.info("Фильм добавлен: {}", film);
        return film;
    }

    @PutMapping //ОБНОВЛЕНИЕ фильма
    public Film putFilm(@NonNull  @NotBlank @Valid @RequestBody Film film) throws ValidationException {

        log.info("PUT request received: {}", film);
        if (!films.containsKey(film.getId())) {
            log.error("Фильм с таким идентификатором {} не существует", film.getId());
            throw new ValidationException("Фильм с таким идентификатором не существует " + film.getId());
        }
        if (film.getName() == null || film.getName().isEmpty() || film.getName().isBlank()) {
            log.error("Название фильма {} отсутствует. ", film.getName());
            throw new ValidationException("Название фильма отсутствует. " + film.getName());
        } // тут надо проверить описание на макс длину 200 символов
        if (film.getDescription().length() > 200) {
            log.error("Описание фильма {} превышает заданную длину в 200 символов. ", film.getDescription());
            //film.descriptionLength(film.getDescription());
            throw new ValidationException("Описание фильма превышает заданную длину в 200 символов. ");
        }
        //проверка на дату релиза не ранее 28.12.1895г.
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза ранее установленной даты  {} 28.12.1895г.", film.getReleaseDate());
            throw new ValidationException("Дата релиза ранее установленной даты 28.12.1895г." + film.getReleaseDate());
        }
        //продолжительность фильма должна быть положительной
        if (film.getDuration() <= 0) {
            log.error("Неверно указана продолжительность фильма {}. Введено отрицательное значение. ", film.getDuration());
            throw new ValidationException("Неверно указана продолжительность фильма. Введено отрицательное значение. "
                    + film.getDuration());
        }
        films.put(film.getId(), film);
        log.info("Фильм {} был обновлён {}", film.getId(), film);
        return film;
    }

    public Map<Integer, Film> getFilms() {
        return films;
    }

    @GetMapping //ПОЛУЧЕНИЕ всех фильмов
    public Collection<Film> filmsList() {
        return films.values();
    }
}
