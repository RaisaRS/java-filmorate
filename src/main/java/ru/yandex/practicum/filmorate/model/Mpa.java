package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Mpa {
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
