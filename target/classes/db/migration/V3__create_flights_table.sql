CREATE TABLE flights (
                         id BIGSERIAL PRIMARY KEY,
                         from_city VARCHAR(10) NOT NULL,
                         to_city VARCHAR(10) NOT NULL,
                         date DATE NOT NULL,
                         time TIME NOT NULL,
                         price INT NOT NULL
);
