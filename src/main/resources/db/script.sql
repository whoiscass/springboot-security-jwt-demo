-- DB Script (not needed for H2 in-memory, but for reference)

-- Create the users table
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created TIMESTAMP NOT NULL,
    modified TIMESTAMP NOT NULL,
    last_login TIMESTAMP,
    token VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Create the phones table
CREATE TABLE phones (
    id UUID PRIMARY KEY,
    number VARCHAR(50) NOT NULL,
    city_code VARCHAR(10) NOT NULL,
    country_code VARCHAR(10) NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);