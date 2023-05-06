package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Получен POST {} запрос /films ", film);
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        log.info("Получен PUT {} запрос /films ", film);
        return filmService.updateFilm(film);
    }

    @DeleteMapping
    public Film deleteFilm(@Valid @RequestBody Film film) {
        log.info("Получен DELETE {} запрос /films ", film);
        return filmService.deleteFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        log.info("Получен PUT {} запрос: /{id}/like/{userId} ", userId);
        filmService.addLike(filmId, userId);
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
    public Film getOneFilm(@PathVariable Long id) {
        log.info("Получен GET {} запрос: /{id} ", id);
        return filmService.getOneFilm(id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") long filmId, @PathVariable long userId) {
        log.info("Получен DELETE {} запрос: /{id}/like/{userId} ", userId);
        filmService.deleteLike(filmId, userId);
    }
}
