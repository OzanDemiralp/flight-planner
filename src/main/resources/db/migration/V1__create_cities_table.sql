CREATE TABLE cities (
                        id SERIAL PRIMARY KEY,
                        code VARCHAR(10) NOT NULL UNIQUE,
                        name VARCHAR(100) NOT NULL
);
