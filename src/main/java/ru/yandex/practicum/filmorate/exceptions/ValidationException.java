package ru.yandex.practicum.filmorate.exceptions;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
    //тут надо создать свою аннотацию для использования этого исключения
}
