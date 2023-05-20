/*MERGE INTO MPA
    KEY (MPA_ID)
    VALUES (1, 'G', 'У фильма нет возрастных ограничений'),
           (2, 'PG', 'Детям рекомендуется смотреть фильм с родителями'),
           (3, 'PG-13', 'Детям до 13 лет просмотр не желателен'),
           (4, 'R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
           (5, 'NC-17', 'лицам до 18 лет просмотр запрещён');

MERGE INTO GENRE
    KEY (GENRE_ID)
    VALUES (1, 'Комедия'),
           (2, 'Драма'),
           (3, 'Мультфильм'),
           (4, 'Триллер'),
           (5, 'Документальный'),
           (6, 'Боевик');*/
INSERT INTO MPA (MPA_NAME, MPA_DESCRIPTION)
VALUES ('G', 'У фильма нет возрастных ограничений');
INSERT INTO MPA (MPA_NAME, MPA_DESCRIPTION)
VALUES ('PG', 'Детям рекомендуется смотреть фильм с родителями');
INSERT INTO MPA(MPA_NAME, MPA_DESCRIPTION)
VALUES ('PG-13', 'Детям до 13 лет просмотр не желателен');
INSERT INTO MPA(MPA_NAME, MPA_DESCRIPTION)
VALUES ('R', 'Лицам до 17 лет просматривать фильм можно только в присутствии взрослого');
INSERT INTO MPA (MPA_NAME, MPA_DESCRIPTION)
VALUES ('NC-17', 'Лицам до 18 лет просмотр запрещён');

INSERT INTO GENRE (GENRE_NAME)
VALUES ('Комедия');
INSERT INTO GENRE (GENRE_NAME)
VALUES ('Драма');
INSERT INTO GENRE (GENRE_NAME)
VALUES ('Мультфильм');
INSERT INTO GENRE (GENRE_NAME)
VALUES ('Триллер');
INSERT INTO GENRE (GENRE_NAME)
VALUES ('Документальный');
INSERT INTO GENRE (GENRE_NAME)
VALUES ('Боевик');
