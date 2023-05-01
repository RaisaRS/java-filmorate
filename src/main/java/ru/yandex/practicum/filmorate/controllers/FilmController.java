package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен POST запрос");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        log.info("Получен PUT запрос");
        return filmService.updateFilm(film);
    }

    @DeleteMapping
    public Film deleteFilm(@Valid @RequestBody Film film) {
        log.info("Получен DELETE запрос");
        return filmService.deleteFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Long filmId, @PathVariable long userId) {
        log.info("Получен PUT запрос");
        filmService.addLike(filmId, userId);
    }

    @GetMapping
    public Collection<Film> filmsList() {
        log.info("Получен GET запрос");
        return filmService.filmList();
    }

    @GetMapping("/popular")
    public Collection<Film> listPopularFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получен GET запрос");
        return filmService.listPopularFilms(count);
    }

    @GetMapping("/{id}")
    public Film getOneFilm(@PathVariable Long id) {
        log.info("Получен GET запрос");
        return filmService.getOneFilm(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        log.info("Получен DELETE запрос");
        filmService.deleteLike(filmId, userId);
    }
}
