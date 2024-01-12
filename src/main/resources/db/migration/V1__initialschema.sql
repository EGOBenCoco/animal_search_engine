CREATE SCHEMA IF NOT EXISTS animal_search;


CREATE TABLE IF NOT EXISTS animal (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    breed VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    age INT NOT NULL,
    description VARCHAR(150) NOT NULL
);
CREATE TABLE IF NOT EXISTS consumer (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    enabled TINYINT(1) DEFAULT 1
);
CREATE TABLE IF NOT EXISTS animal_location (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    city VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS announcement (
    ad_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    header VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    consumer_id INT NOT NULL,
    animal_id INT,
    animal_location_id INT,
    FOREIGN KEY (consumer_id) REFERENCES consumer(id),
    FOREIGN KEY (animal_id) REFERENCES animal(id),
    FOREIGN KEY (animal_location_id) REFERENCES animal_location(id)
);
CREATE TABLE IF NOT EXISTS comment (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    text VARCHAR(100) NOT NULL,
    consumer_id INT NOT NULL,
    ad_id INT NOT NULL,
    FOREIGN KEY (consumer_id) REFERENCES consumer(id),
    FOREIGN KEY (ad_id) REFERENCES announcement(ad_id)
);

CREATE TABLE IF NOT EXISTS contact_info (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    value VARCHAR(50) NOT NULL,
    consumer_id INT NOT NULL,
    FOREIGN KEY (consumer_id) REFERENCES consumer(id)
);
CREATE TABLE IF NOT EXISTS role (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL
);
CREATE TABLE IF NOT EXISTS consumers_roles (
    consumer_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (consumer_id, role_id),
    FOREIGN KEY (consumer_id) REFERENCES consumer(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);
CREATE TABLE IF NOT EXISTS announcement_photos (
    ad_id INT NOT NULL,
    photo_urls VARCHAR(255) NOT NULL,
    PRIMARY KEY (ad_id, photo_urls),
    FOREIGN KEY (ad_id) REFERENCES announcement(ad_id)
);


