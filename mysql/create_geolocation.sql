CREATE DATABASE IF NOT EXISTS geolocations;

USE geolocations;

DROP TABLE IF EXISTS geolocation;

CREATE TABLE geolocation
(
    query VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(255),
    country VARCHAR(255),
    countryCode VARCHAR(255),
    region VARCHAR(255),
    regionName VARCHAR(255),
    city VARCHAR(255),
    zip VARCHAR(255),
    latitude DECIMAL(10,8),
    longitude DECIMAL(11, 8),
    timezone VARCHAR(255),
    isp VARCHAR(255),
    org VARCHAR(255),
    asHandle VARCHAR(255),
    PRIMARY KEY (query)
);
