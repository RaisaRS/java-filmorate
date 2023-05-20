package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaDao {
    Collection<Mpa> mpaList();

    Mpa getOneMpa(int id);
}
