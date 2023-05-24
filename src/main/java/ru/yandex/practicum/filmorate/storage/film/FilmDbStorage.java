package ru.yandex.practicum.filmorate.storage.film;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmDoubleLikeException;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static java.lang.String.format;
import static java.util.Calendar.DECEMBER;

@Component("FilmDbStorage")
@Slf4j
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final LocalDate MIN_DAY_RELEASE = LocalDate.of(1895, DECEMBER, 28);

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> filmsList() {
        String sql = "SELECT f.*, m.mpa_name AS mpa_name FROM films AS f JOIN mpa AS m ON f.mpa_id = m.mpa_id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rowMapperFilm(rs));
    }

    @Override
    @SneakyThrows
    public Film addFilm(Film film) {
        validateFim(film);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("films")
                .usingGeneratedKeyColumns("film_id");
        long id = simpleJdbcInsert.executeAndReturnKey(film.filmValue()).longValue();
        film.setId(id);
        film.getGenres().forEach(genre -> addGenreToFilm(id, genre.getId()));
        log.info("Фильм {} сохранен", film.getName());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        validateFim(film);
        String sql = "UPDATE films SET " +
                "film_name = ?, film_description = ?, release_date = ?, duration = ?, mpa_id = ?" +
                " WHERE film_id = ?";
        if (jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId()) > 0) {
            deleteAllGenresFromFilm(film.getId());
            film.getGenres().forEach(genre -> addGenreToFilm(film.getId(), genre.getId()));// приклеить жанры
            return film;
        }
        log.warn("Фильм с id {} не найден. ", film.getId());
        throw new FilmNotFoundException("Фильм не найден " + film.getId());
    }


    @Override
    public Film getOneFilm(long id) {
        String sql = "SELECT f.*, m.mpa_name FROM films AS f JOIN mpa AS m ON f.mpa_id = m.mpa_id " +
                "WHERE f.film_id = ?";
        try {
            Film film = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> rowMapperFilm(rs), id);

            return film;
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException(format("Фильм с id= %s отсутствут", id));
        }
    }

    @Override
    public List<Film> listPopularFilms(int count) {
        String sql = "SELECT f.*, m.mpa_name " +
                "FROM films AS f " +
                "JOIN mpa AS m ON f.mpa_id = m.mpa_id " +
                "LEFT OUTER JOIN (SELECT film_id, COUNT (user_id) AS likes_pop " +
                "FROM likes GROUP BY film_id ORDER BY likes_pop DESC LIMIT ?)" +
                " AS top_films ON f.film_id = top_films.film_id ORDER BY top_films.likes_pop DESC LIMIT  ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> rowMapperFilm(rs), count, count);
    }

    @Override
    public void addLike(long filmId, long userId) {
        try {
            String sql = "INSERT INTO Likes (film_id, user_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, filmId, userId);
        } catch (EmptyResultDataAccessException e) {
            log.error("Пользователь id = {} уже поставил лайк фильму id = {}", userId, filmId);
            throw new FilmDoubleLikeException(format("Пользователь id = %s уже поставил лайк фильму id = %s",
                    userId, filmId));
        }
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        String sql = "DELETE FROM Likes WHERE (film_id = ? AND user_id = ?)";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Long> getLikesByFilm(long filmId) {
        String sql = "SELECT user_id FROM Likes WHERE film_id = ?";
        try {
            return jdbcTemplate.query(sql, (rs, rowNun) -> rs.getLong("user_id"), filmId);
        } catch (EmptyResultDataAccessException e) {
            log.debug("Фильма с id={} отсутствуетю", filmId);
            throw new NullPointerException(format("Фильм с id= %s отсутствует", filmId));
        }
    }

    @Override
    public boolean addGenreToFilm(long filmId, int genreId) {
        String sql = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        return jdbcTemplate.update(sql, filmId, genreId) > 0;
    }

    @Override
    public boolean deleteAllGenresFromFilm(long filmId) {
        String sql = "DELETE FROM film_genre WHERE film_id = ?";
        return jdbcTemplate.update(sql, filmId) > 0;
    }

    private void validateFim(Film film) {
        if (film.getReleaseDate().isBefore(MIN_DAY_RELEASE)) {
            log.error("Дата релиза ранее установленной даты  {} 28.12.1895г.", film.getReleaseDate());
            throw new ValidationException("Дата релиза ранее установленной даты 28.12.1895г." + film.getReleaseDate());
        }
    }

    private Film rowMapperFilm(ResultSet rs) throws SQLException {
        long id = rs.getLong("film_id");
        String name = rs.getString("film_name");
        String description = rs.getString("film_description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        int mpaId = rs.getInt("mpa_id");
        String mpaName = rs.getString("mpa_name");
        Mpa mpa = Mpa.builder()
                .id(mpaId)
                .name(mpaName)
                .build();
        return Film.builder()
                .id(id)
                .name(name)
                .description(description)
                .releaseDate(releaseDate)
                .duration(duration)
                .mpa(mpa)
                .build();
    }
}


