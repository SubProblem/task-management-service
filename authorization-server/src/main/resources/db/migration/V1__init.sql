-- V1__Create_users_table.sql

-- Create the table if it doesn't already exist
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,  -- Auto-increment ID
    uuid VARCHAR(36) NOT NULL UNIQUE,  -- UUID for the user profile
    firstname VARCHAR(255) NOT NULL,  -- User's first name
    lastname VARCHAR(255) NOT NULL,  -- User's last name
    email VARCHAR(255) NOT NULL UNIQUE,  -- User's email, must be unique
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(36) UNIQUE,  -- User's phone number (optional, adjust length as needed)
    preferences TEXT,  -- JSON column to store user preferences
    role VARCHAR(50) NOT NULL  -- Role column, default value is 'user'
);

-- Create indexes if they don't already exist
CREATE INDEX IF NOT EXISTS idx_users_uuid ON users (uuid);
CREATE INDEX IF NOT EXISTS idx_users_email ON users (email);
