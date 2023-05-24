package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Friendship {
    @NotNull
    private long userId;
    @NotNull
    private long friendId;
    private boolean status;
}
