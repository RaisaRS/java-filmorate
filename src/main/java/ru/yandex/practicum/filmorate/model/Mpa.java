package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class Mpa {
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}
