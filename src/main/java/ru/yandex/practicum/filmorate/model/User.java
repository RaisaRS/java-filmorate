package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    private  int id;
    @NotNull(message = "email не может отсутствовать")
    @NotBlank(message = "email не может быть пустым")
    @Email(message = "email должен содержать символ - @")
    private String email;
    @NotNull(message = "Логин не может быть пустым или содержать пробелы.")
    @NotBlank(message = "Логин не может быть пустым или содержать пробелы.")
    private String login;
    private String name;
    @Past(message = "Дата рождения не может быть в будущем.")
    private LocalDate birthday;

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
