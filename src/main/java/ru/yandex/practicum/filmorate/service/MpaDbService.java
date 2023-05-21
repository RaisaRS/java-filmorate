package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MpaDbService implements MpaService {
    private final MpaDao mpaDao;

    @Override
    public List<Mpa> mpaList() {
        return mpaDao.mpaList();
    }

    @Override
    public Mpa getOneMpa(int id) {
        return mpaDao.getOneMpa(id);
    }
}
