CREATE SCHEMA IF NOT EXISTS tenant1;
CREATE SCHEMA IF NOT EXISTS tenant2;
CREATE SCHEMA IF NOT EXISTS tenant3;

CREATE TABLE IF NOT EXISTS tenant1.boardgame
(
    id         bigserial
        constraint boardgame_pk
            primary key,
    name       varchar,
    minplayers int,
    maxplayers int
);

CREATE UNIQUE INDEX IF NOT EXISTS boardgame_id_uindex
    ON tenant1.boardgame (id);

CREATE TABLE IF NOT EXISTS tenant2.boardgame
(
    id         bigserial
        constraint boardgame_pk
            primary key,
    name       varchar,
    minplayers int,
    maxplayers int
);

CREATE UNIQUE INDEX IF NOT EXISTS boardgame_id_uindex
    ON tenant2.boardgame (id);

CREATE TABLE IF NOT EXISTS tenant3.boardgame
(
    id         bigserial
        constraint boardgame_pk
            primary key,
    name       varchar,
    minplayers int,
    maxplayers int
);

CREATE UNIQUE INDEX IF NOT EXISTS boardgame_id_uindex
    ON tenant3.boardgame (id);