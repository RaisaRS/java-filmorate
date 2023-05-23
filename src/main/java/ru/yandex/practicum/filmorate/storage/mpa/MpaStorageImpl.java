package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
public class MpaStorageImpl implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> mpaList() {
        String sql = "SELECT * FROM mpa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rowMapperMpa(rs));
    }

    @Override
    public Mpa getOneMpa(int id) {
        String sql = "SELECT* FROM mpa WHERE mpa_id =?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rowMapperMpa(rs), id);
        } catch (EmptyResultDataAccessException e) {
            throw new MpaNotFoundException("Рейтинг с id не найден: " + id);
        }
    }

    private Mpa rowMapperMpa(ResultSet rs) throws SQLException {
        int id = rs.getInt("mpa_id");
        String name = rs.getString("mpa_name");
        String description = rs.getString("mpa_description");
        return Mpa.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }
}
