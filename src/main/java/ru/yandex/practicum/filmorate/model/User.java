package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
public class User {

    private long id;
    @NotBlank
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
    private final Set<Long> friends = new HashSet<>();  //айди юзеров, подавших заявки в друзья

    public void addFriend(Long id) {
        friends.add(id);
    }

    public Map<String, Object> userValues() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("user_name", name);
        values.put("birthday", birthday);
        return values;
    }
}
