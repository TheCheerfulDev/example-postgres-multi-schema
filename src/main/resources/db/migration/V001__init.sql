CREATE TABLE IF NOT EXISTS boardgame
(
    id         bigserial
        constraint boardgame_pk
            primary key,
    name       varchar,
    minplayers int,
    maxplayers int
);

CREATE UNIQUE INDEX IF NOT EXISTS boardgame_id_uindex
    ON boardgame (id);