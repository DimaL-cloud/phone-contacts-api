CREATE TABLE IF NOT EXISTS contact (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    image_url TEXT UNIQUE,
    uuid UUID NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS emails (
    contact_id INT NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS phone_numbers (
    contact_id INT NOT NULL,
    phone_number VARCHAR(255) NOT NULL
);
