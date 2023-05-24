package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MpaDbService implements MpaService {
    private final MpaStorage mpaStorage;

    @Override
    public List<Mpa> mpaList() {
        log.info("Выведены все рейтинги");
        return mpaStorage.mpaList();
    }

    @Override
    public Mpa getOneMpa(int id) {
        log.info("Получен рейтинг с id {} ", id);
        return mpaStorage.getOneMpa(id);
    }
}
