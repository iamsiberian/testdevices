CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    device_id BIGINT REFERENCES devices(id),
    action_type VARCHAR(256),
    date TIMESTAMP (6) WITH TIME ZONE
);