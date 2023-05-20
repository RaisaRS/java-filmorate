package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@Validated
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(@Qualifier(value = "FilmDbService") FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен POST {} запрос /films ", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        log.info("Получен PUT {} запрос /films ", film);
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Collection<Long> addLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        log.info("Получен PUT {} запрос: /{id}/like/{userId} ", userId);
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Collection<Long> removeLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        log.info("Получен DELETE {} запрос: /{id}/like/{userId} ", userId);
        return filmService.deleteLike(filmId, userId);
    }

    @GetMapping
    public Collection<Film> filmsList() {
        log.info("Получен GET запрос /films ");
        return filmService.filmList();
    }

    @GetMapping("/popular")
    public Collection<Film> listPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получен GET {} запрос: /popular ", count);
        return filmService.listPopularFilms(count);
    }

    @GetMapping("/{id}")
    public Film getOneFilm(@PathVariable long id) {
        log.info("Получен GET {} запрос: /{id} ", id);
        return filmService.getOneFilm(id);
    }
}
