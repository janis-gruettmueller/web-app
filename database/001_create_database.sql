DROP DATABASE IF EXISTS spotify;

-- Create the database
CREATE DATABASE spotify;

-- Use the database
USE spotify;

-- Create the Account table
CREATE TABLE accounts (
    account_id BIGINT NOT NULL,
    subscription_status VARCHAR(50), -- active, inactive, cancelled, renewed
    subscription_type VARCHAR(50), -- free, premium, family
    subscription_price FLOAT,
    subscription_start DATE,
    subscription_end DATE,
    is_auto_extension BOOLEAN, -- if True monthly extension, else no automatic extension
    payment_type VARCHAR(50), -- credit card, paypal, bank account
    payment_status INT, -- payed, waiting, failed
    last_payment_date DATE,
    billing_last_name VARCHAR(100),
    billing_first_name VARCHAR(100),
    billing_country VARCHAR(50),
    billing_city VARCHAR(50),
    billing_street VARCHAR(100),
    billing_zipcode BIGINT,
    PRIMARY KEY (account_id)
);

-- Create the User table
CREATE TABLE users (
    user_id BIGINT,
    user_name VARCHAR(25),
    email VARCHAR(50),
    password_hash VARCHAR(255),
    PRIMARY KEY (user_id)
);

-- Create the Song table
CREATE TABLE songs (
    song_id BIGINT NOT NULL,
    title VARCHAR(100),
    duration TIME,
    release_date DATE,
    is_single BOOLEAN, -- true or false
    PRIMARY KEY (song_id)
);

-- Create the Album table
CREATE TABLE albums (
    album_id BIGINT NOT NULL,
    album_type VARCHAR(50), -- single or album
    title VARCHAR(100),
    release_date DATE,
    PRIMARY KEY (album_id)
);

-- Create the Artist table
CREATE TABLE artists (
    artist_id BIGINT NOT NULL,
    artist_name VARCHAR(100),
    genre VARCHAR(50),
    PRIMARY KEY (artist_id)
);

-- Create the Playlist table
CREATE TABLE playlists (
    playlist_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    playlist_name VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    PRIMARY KEY (playlist_id, user_id)
);

-- Create mapping table for Account and User
CREATE TABLE account_users (
    account_id BIGINT,
    user_id BIGINT,
    account_role VARCHAR(50), -- owner or member
    FOREIGN KEY (account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    PRIMARY KEY (account_id, user_id)
);

-- Create mapping table for Playlist and Song
CREATE TABLE playlist_songs (
    playlist_id BIGINT,
    song_id BIGINT,
    -- song_position INT,
    FOREIGN KEY (playlist_id) REFERENCES playlists(playlist_id),
    FOREIGN KEY (song_id) REFERENCES songs(song_id),
    PRIMARY KEY (playlist_id, song_id)
);

-- Create mapping table for Album and Song
CREATE TABLE album_songs (
    album_id BIGINT,
    song_id BIGINT,
    track_number INT,
    FOREIGN KEY (album_id) REFERENCES albums(album_id),
    FOREIGN KEY (song_id) REFERENCES songs(song_id),
    PRIMARY KEY (album_id, song_id)
);

-- Create mapping table for Artist and Song
CREATE TABLE artist_songs (
    artist_id BIGINT,
    song_id BIGINT,
    artist_role VARCHAR(50), -- featured or main
    FOREIGN KEY (artist_id) REFERENCES artists(artist_id),
    FOREIGN KEY (song_id) REFERENCES songs(song_id),
    PRIMARY KEY (artist_id, song_id)
);

-- Create mapping table for Artist and Album
CREATE TABLE artist_albums (
    artist_id BIGINT,
    album_id BIGINT,
    FOREIGN KEY (artist_id) REFERENCES artists(artist_id),
    FOREIGN KEY (album_id) REFERENCES albums(album_id),
    PRIMARY KEY (artist_id, album_id)
);