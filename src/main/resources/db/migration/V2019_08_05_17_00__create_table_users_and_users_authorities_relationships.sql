CREATE TABLE users (
        id BIGSERIAL PRIMARY KEY,
        first_name VARCHAR(256) NOT NULL,
        last_name VARCHAR(256) NOT NULL,
        phone VARCHAR(256) NOT NULL,
        email VARCHAR(256) NOT NULL UNIQUE,
        password VARCHAR(256) NOT NULL
);

CREATE INDEX user_email ON users(email);

CREATE TABLE users_authorities_relationships (
        user_id BIGINT REFERENCES users(id),
        authority_id BIGINT REFERENCES authorities(id)
);

