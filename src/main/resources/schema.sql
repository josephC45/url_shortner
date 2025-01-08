CREATE TABLE url (
    id SERIAL PRIMARY KEY,           
    url_hash CHAR(7) NOT NULL,   -- URL hash, adjust size based on hash length
    short_url VARCHAR(64) NOT NULL,      -- Shortened URL, exactly 7 characters
    long_url TEXT NOT NULL 
);