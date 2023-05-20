package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.error("Получен статус 400  {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), "Ошибка валидациию");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(final UserNotFoundException e) {
        log.debug("Получен статус 404 Not found {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), "Пользователь не найден.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleFilmNotFoundException(final FilmNotFoundException e) {
        log.debug("Получен статус 404 Not found {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), "Фильм не найден.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleObjectNotFoundException(final ObjectNotFoundException e) {
        log.debug("Получен статус 404 Not found {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), "Объект не найден.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFilmDoubleLikeException(final FilmDoubleLikeException e) {
        log.error("Получен статус 400 Bad request {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), "Нельзя поставиь лайк дважды.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMpaNotFoundException(final MpaNotFoundException e) {
        log.error("Получен статус 404  {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), "Рейтинг отсутствует.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataRetrievalFailureException(final DataRetrievalFailureException e) {
        log.error("Получен статус 400 Bad request {}", e.getMessage());
        return new ErrorResponse(e.getMessage(), "Ошибка при извлечении данных.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmptyResultDataAccessException(final NullPointerException e) {
        log.warn("Получен статус 404  {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), "Данные отсутствуют.");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final RuntimeException e) {
        log.error("Получен статус 500 Internal server error {}", e.getMessage(), e);
        return new ErrorResponse(e.getMessage(), "Сервер прилёг отдохнуть. Но обещал вернуться;)");
    }
}

