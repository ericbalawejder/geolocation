package com.geolocation.api.service;

import com.geolocation.api.dao.GeolocationDao;
import com.geolocation.api.entity.Geolocation;
import com.geolocation.api.exception.DuplicateEntryException;
import com.geolocation.api.exception.GeolocationNotFoundException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GeolocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeolocationService.class);
    private final GeolocationDao geolocationDao;
    private final LoadingCache<String, Optional<Geolocation>> cache;

    public GeolocationService(GeolocationDao geolocationDao) {
        this.geolocationDao = geolocationDao;
        this.cache = buildCache();
    }

    public Optional<Geolocation> getGeolocation(String query) throws ExecutionException {
        return cache.get(query);
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

        final String ip = geolocation.getQuery();
        geolocationDao.deleteGeolocation(ip);
        cache.invalidate(ip);
        LOGGER.info(cache.asMap().toString());
    }

    private LoadingCache<String, Optional<Geolocation>> buildCache() {
        final CacheLoader<String, Optional<Geolocation>> cacheLoader = new CacheLoader<>() {
            @Override
            public Optional<Geolocation> load(String query) {
                final Optional<Geolocation> geolocation =
                        geolocationDao.getGeolocation(query);
                if (geolocation.isPresent()) {
                    LOGGER.info("load in cache");
                }
                return geolocation;
            }
        };

        return CacheBuilder.newBuilder()
                .maximumSize(4)
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .build(cacheLoader);
    }

}
