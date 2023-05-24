package ru.yandex.practicum.filmorate.storage.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;


@Component
@Slf4j
public class UserDbStorage implements UserStorage {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override

    public User addUser(User user) throws JsonProcessingException {
        isValidNameUser(user);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");
        user.setId(simpleJdbcInsert.executeAndReturnKey(user.userValues()).longValue());
        log.debug("Пользователь {} добавлен: ", objectMapper.writeValueAsString(user));
        return user;
    }

    @Override

    public User deleteUser(User user) throws JsonProcessingException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        if (jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(),
                user.getBirthday(), user.getId()) > 0) {
            log.debug("Пользователь {} удалён: ", objectMapper.writeValueAsString(user));
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sql = "UPDATE users SET email = ?, login = ?, user_name = ?," +
                " birthday = ? WHERE user_id = ?";
        if (jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(),
                user.getBirthday(), user.getId()) > 0) {
            log.info("Пользователь с id {} обновлён: ", user.getId());
            return user;
        } else {
            log.warn("Пользователь с id {} не найден. ", user.getId());
            throw new UserNotFoundException("Пользователь не найден " + user.getId());
        }
    }

    @Override
    public Collection<User> usersList() {
        String sql = "SELECT* FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rowMapperUser(rs));
    }

    @Override
    public User getOneUser(long id) {
        String sql = "SELECT* FROM users WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rowMapperUser(rs), id);
        } catch (DataRetrievalFailureException e) {
            log.warn("Пользователь с id {} не найден", id);
            throw new UserNotFoundException(String.format("Пользователь не найден: " + id));
        }
    }

    @Override
    @SneakyThrows
    public Collection<User> findAllByIds(Collection<Long> ids) {
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?"));
        return jdbcTemplate.query(
                String.format("SELECT* FROM users WHERE user_id  IN (%s)", inSql),
                ids.toArray(),
                (rs, rowNum) -> rowMapperUser(rs));
    }

    private void isValidNameUser(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            log.error("Имя пользователя пустое. Установлен логин {} в качестве имени.", user.getLogin());
            user.setName(user.getLogin());
        }
    }

    private User rowMapperUser(ResultSet rs) throws SQLException {
        long id = rs.getLong("user_id");
        String name = rs.getString("user_name");
        String login = rs.getString("login");
        String email = rs.getString("email");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        return User.builder()
                .id(id)
                .name(name)
                .login(login)
                .email(email)
                .birthday(birthday)
                .build();
    }
}

