CREATE DATABASE IF NOT EXISTS geolocations;

USE geolocations;

DROP TABLE IF EXISTS geolocation;

CREATE TABLE geolocation
(
    id INT AUTO_INCREMENT,
    query VARCHAR(200),
    status VARCHAR(200),
    country VARCHAR(200),
    countryCode VARCHAR(200),
    region VARCHAR(200),
    regionName VARCHAR(200),
    city VARCHAR(200),
    zip VARCHAR(200),
    lat DECIMAL(10,8),
    lon DECIMAL(11, 8),
    timezone VARCHAR(200),
    isp VARCHAR(200),
    org VARCHAR(200),
    asHandle VARCHAR(200),
    PRIMARY KEY (id)
);
