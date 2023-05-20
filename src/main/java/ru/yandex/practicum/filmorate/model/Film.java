package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
public class Film {

    private long id;
    @NotNull
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive
    private int duration;
    @NotNull
    private Mpa mpa;
    private final Set<Genre> genres = new HashSet<>();//список жанров
    private final Set<Long> likes = new HashSet<>(); //айди юзеров, поставивших лайк фильму

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void addLike(Long userId) {
        likes.add(userId);
    }

    public boolean deleteLike(long id) {
        return likes.remove(id);
    }

    public void createGenre(Genre genre) {
        genres.add(genre);
    }

    public List<Genre> getGenres() {
        return genres.stream()
                .sorted(Comparator.comparing(Genre::getId))
                .collect(Collectors.toList());
    }

    public boolean deleteGenre(Genre genre) {
        return genres.remove(genre);
    }

    public Map<String, Object> filmValue() {
        Map<String, Object> values = new HashMap<>();
        values.put("film_name", name);
        values.put("film_description", description);
        values.put("release_date", releaseDate);
        values.put("duration", duration);
        values.put("mpa_id", mpa.getId());
        return values;
    }

}
