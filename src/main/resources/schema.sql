create table IF NOT EXISTS USERS
(
    USER_ID   INTEGER auto_increment
        primary key,
    EMAIL     CHARACTER VARYING not null,
    LOGIN     CHARACTER VARYING not null,
    USER_NAME CHARACTER VARYING not null,
    BIRTHDAY  DATE              not null
);

create table IF NOT EXISTS FRIENDSHIP
(
    USER_ID   INTEGER not null
        references USERS (USER_ID) ON DELETE CASCADE,
    FRIEND_ID INTEGER not null
        references USERS (USER_ID) ON DELETE CASCADE,
    STATUS    BOOLEAN not null,
    primary key (USER_ID, FRIEND_ID)
);

create table IF NOT EXISTS MPA
(
    MPA_ID          INTEGER auto_increment
        primary key,
    MPA_NAME        CHARACTER VARYING not null UNIQUE,
    MPA_DESCRIPTION CHARACTER VARYING
);

create table IF NOT EXISTS GENRE
(
    GENRE_ID   INTEGER auto_increment
        primary key,
    GENRE_NAME CHARACTER VARYING UNIQUE
);

create table IF NOT EXISTS FILMS
(
    FILM_ID          INTEGER auto_increment
        primary key,
    FILM_NAME        CHARACTER VARYING      not null,
    FILM_DESCRIPTION CHARACTER VARYING(200) not null,
    RELEASE_DATE     DATE                   not null,
    DURATION         INTEGER                not null,
    MPA_ID           INTEGER                not null
        references MPA (MPA_ID) ON DELETE RESTRICT
);

create table IF NOT EXISTS FILM_GENRE
(
    FILM_ID  INTEGER not null
        references FILMS ON DELETE CASCADE,
    GENRE_ID INTEGER not null
        references GENRE ON DELETE CASCADE,
    primary key (FILM_ID, GENRE_ID)
);

create table IF NOT EXISTS LIKES
(
    FILM_ID INTEGER not null
        references FILMS ON DELETE CASCADE,
    USER_ID INTEGER not null
        references USERS ON DELETE CASCADE,
    primary key (FILM_ID, USER_ID)
);


