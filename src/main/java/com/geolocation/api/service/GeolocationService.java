package com.geolocation.api.service;

import com.geolocation.api.dao.GeolocationDao;

public class GeolocationService {

    private final GeolocationDao geolocationDao;

    public GeolocationService(GeolocationDao geolocationDao) {
        this.geolocationDao = geolocationDao;
    }

}
