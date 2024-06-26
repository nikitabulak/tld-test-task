set mode PostgreSQL;

CREATE TABLE IF NOT EXISTS regions
(
    id         bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name       varchar(50) UNIQUE                                  NOT NULL,
    short_name varchar(10) UNIQUE                                  NOT NULL,
    PRIMARY KEY (id)
);