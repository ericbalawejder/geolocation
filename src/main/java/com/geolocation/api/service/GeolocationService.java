package com.geolocation.api.service;

import com.geolocation.api.dao.GeolocationDao;
import com.geolocation.api.entity.Geolocation;
import com.geolocation.api.exception.DuplicateEntryException;
import com.geolocation.api.exception.GeolocationNotFoundException;

import java.util.List;

public record GeolocationService(GeolocationDao geolocationDao) {

    public Geolocation getGeolocation(String query) {
        return geolocationDao.getGeolocation(query)
                .orElseThrow(GeolocationNotFoundException::new);
    }

    public List<Geolocation> getGeolocations() {
        return geolocationDao.getGeolocations();
    }

    public void insertGeolocation(Geolocation geolocation) {
        try {
            geolocationDao.insertGeolocation(geolocation);
        } catch (Exception e) {
            throw new DuplicateEntryException();
        }
    }

    public void deleteGeolocation(String query) {
        final Geolocation geolocation = geolocationDao.getGeolocation(query)
                .orElseThrow(GeolocationNotFoundException::new);

        geolocationDao.deleteGeolocation(geolocation.getQuery());
    }

}
