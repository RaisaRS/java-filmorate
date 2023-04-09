package model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDateTime birthday;

    public User(int id, String email, String login, String name, LocalDateTime birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }


}
