package ru.yandex.practicum.filmorate.controllers;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private final Set<String> userEmails = new HashSet<>();
    private int id =  0;

    public Set<String> getUserEmails() {
        return userEmails;
    }

    @PostMapping  //валидация должна быть по ТЗ //СОЗДАНИЕ пользователя
    public User addUser(@Valid @RequestBody User user) throws ValidationException {
       log.info("POST request received: {}", user);
       if (userEmails.contains(user.getEmail())) {
           log.error("Пользователь с таким адресом электронной почты уже существует.");
           throw new ValidationException("Пользователь с таким адресом электронной почты уже существует.");
       } //тут надо добавить проверку на @  и проверка на пустоту //проверяется аннотациями @Email, @NotBlank, @NotNull
        if (!user.getEmail().contains("@") || user.getEmail() == null || user.getEmail().isEmpty()
               || user.getEmail().isBlank()) {
           log.error("Электронная почта {} введена некорректно: отсутствует символ @, либо незаполнена. ", user.getEmail());
           throw new ValidationException("Электронная почта введена некорректно: отсутствует символ @, либо незаполнена. "
                   + user.getEmail());
       } // тут надо проверить логин на пустоту  содержание пробелов - методы поодстроки
       if (user.getLogin() == null || user.getLogin().contains(" ")) {
           log.error("Логин {} отсутствует или содержит пробелы. ", user.getLogin());
           throw new ValidationException("Логин отсутствует или содержит пробелы. ");
       } //установка логина вместо имени пользователя
       if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
           log.error("Имя пользователя пустое. Установлен логин {} в качестве имени.", user.getLogin());
           user.setName(user.getLogin());
       } //тут надо проверить дату рождения
       if (user.getBirthday().isAfter(LocalDate.now())) {
           log.error("Введена некорректная дата рождения {}.", user.getBirthday());
           throw new ValidationException("Введена некорректная дата рождения." + user.getBirthday());
       }
       id++;
       user.setId(id);
       users.put(user.getId(), user);
       userEmails.add(user.getEmail());
       log.info("Пользователь добавлен: {}", user);
       return user;
    }

    @PutMapping //ОБНОВЛЕНИЕ пользователя
    public User putUser(@Valid @RequestBody User user) throws ValidationException {

        log.info("PUT request received: {}", user);
        if (!users.containsKey(user.getId())) {
            log.error("Пользователь с таким идентификатором {} не существует", user.getId());
            throw new ValidationException("Пользователь с таким идентификатором не существует " + user.getId());
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")) {
            log.error("Логин {} отсутствует или содержит пробелы. ", user.getLogin());
            user.setLogin(user.getLogin());
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            log.error("Имя пользователя пустое. Установите логин {} в качестве имени.", user.getLogin());
            user.setName(user.getLogin());
        }
        if (!user.getBirthday().isAfter(ChronoLocalDate.from(Instant.now()))) {
            log.error("Введена некорректная дата рождения {}.", user.getBirthday());
            user.setBirthday(user.getBirthday());
        }
        users.put(user.getId(), user);
        log.info("Пользователь {} был обновлён {}", user.getId(), user);
        return user;
    }

    public Map<Integer, User> getUsers() {
        return users;
    }

    @GetMapping //ПОЛУЧЕНИЕ списка всех пользователей
    public Collection<User> usersList() {
        return users.values();
    }
}
