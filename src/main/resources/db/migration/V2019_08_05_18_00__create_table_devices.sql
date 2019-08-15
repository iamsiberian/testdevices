CREATE TABLE devices (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(256) NOT NULL,
    owner VARCHAR(256) NOT NULL,
    model VARCHAR(256) NOT NULL,
    os_type VARCHAR(256) NOT NULL,
    description VARCHAR(256)
);