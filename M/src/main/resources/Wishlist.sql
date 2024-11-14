
CREATE DATABASE IF NOT EXISTS wishlist;


USE wishlist;

-- user table
CREATE TABLE IF NOT EXISTS users (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255)
    );

-- Wishlist table, uden items.
CREATE TABLE IF NOT EXISTS wishlists (
    wishlist_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    wishlist_name VARCHAR(255) NOT NULL,FOREIGN KEY
    (user_id) REFERENCES users(user_id) ON DELETE CASCADE
    );
