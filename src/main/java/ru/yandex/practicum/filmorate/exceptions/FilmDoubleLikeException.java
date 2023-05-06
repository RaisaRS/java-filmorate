package ru.yandex.practicum.filmorate.exceptions;

public class FilmDoubleLikeException extends RuntimeException {
    public FilmDoubleLikeException(String message) {
        super(message);
    }
}
