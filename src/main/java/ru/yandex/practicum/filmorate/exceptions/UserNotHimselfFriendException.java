package ru.yandex.practicum.filmorate.exceptions;

public class UserNotHimselfFriendException extends RuntimeException {
    public UserNotHimselfFriendException(String message) {
        super(message);
    }
}
