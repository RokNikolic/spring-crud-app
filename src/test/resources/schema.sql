-- Schema to auto generate the table for the in memory H2 database, using the PostgreSQL dialect

SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS "product" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price NUMERIC(15, 2) NOT NULL
);
