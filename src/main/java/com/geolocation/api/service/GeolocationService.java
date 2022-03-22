package com.geolocation.api.service;

import com.geolocation.api.dao.GeolocationDao;
import com.geolocation.api.entity.Geolocation;

import java.util.List;

public class GeolocationService {

    private final GeolocationDao geolocationDao;

    public GeolocationService(GeolocationDao geolocationDao) {
        this.geolocationDao = geolocationDao;
    }

    public List<Geolocation> getGeolocations() {
        return this.geolocationDao.getGeolocations();
    }

    public Geolocation getGeolocation(String query) {
        return this.geolocationDao.getGeolocation(query);
    }

    public void insertGeolocation(Geolocation geolocation) {
        this.geolocationDao.insertGeolocation(geolocation);
    }

    public int deleteGeolocation(String query) {
        return this.geolocationDao.deleteGeolocation(query);
    }

}
