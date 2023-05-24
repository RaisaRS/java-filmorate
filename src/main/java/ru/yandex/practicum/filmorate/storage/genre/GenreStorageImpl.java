package ru.yandex.practicum.filmorate.storage.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Slf4j
public class GenreStorageImpl implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenres() {
        String sql = "SELECT * FROM genre ORDER BY genre_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rowMapperGenre(rs));
    }

    @Override
    public Genre getOneGenre(int id) {
        String sql = "SELECT * FROM genre WHERE genre_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rowMapperGenre(rs), id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Жанр с id {} не найден", id);
            throw new FilmNotFoundException("Жанр с id  не найден " + id);
        }
    }

    @Override
    public List<Genre> getAllGenresByFilm(long id) {
        String sql = "SELECT g.* FROM film_genre AS fg JOIN genre AS g ON" +
                " fg.genre_id = g.genre_id WHERE fg.film_id = ? ORDER BY g.genre_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rowMapperGenre(rs), id);
    }

    private Genre rowMapperGenre(ResultSet rs) throws SQLException {
        int genreId = rs.getInt("genre_id");
        String genreName = rs.getString("genre_name");
        return Genre.builder()
                .id(genreId)
                .name(genreName)
                .build();
    }
}
