package ru.yandex.practicum.filmorate.exceptions;

public class FilmNotFoundException extends NullPointerException {
    public FilmNotFoundException(String message) {
        super(message);
    }
}
