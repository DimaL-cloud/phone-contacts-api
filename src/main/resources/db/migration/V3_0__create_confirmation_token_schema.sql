CREATE TABLE IF NOT EXISTS confirmation_token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    created_date TIMESTAMP NOT NULL,
    user_id INT NOT NULL
);
