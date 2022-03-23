package com.geolocation.api.service;

import com.geolocation.api.dao.GeolocationDao;
import com.geolocation.api.entity.Geolocation;
import com.geolocation.api.exception.GeolocationException;

import java.util.List;

public record GeolocationService(GeolocationDao geolocationDao) {

    public List<Geolocation> getGeolocations() {
        return geolocationDao.getGeolocations();
    }

    public Geolocation getGeolocation(String query) {
        return geolocationDao.getGeolocation(query)
                .orElseThrow(GeolocationException::new);
    }

    public void insertGeolocation(Geolocation geolocation) {
        geolocationDao.insertGeolocation(geolocation);
    }

    public void deleteGeolocation(String query) {
        geolocationDao.deleteGeolocation(query);
    }

}
