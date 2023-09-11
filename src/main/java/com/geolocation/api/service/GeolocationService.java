package com.geolocation.api.service;

import com.geolocation.api.dao.GeolocationDao;
import com.geolocation.api.entity.Geolocation;
import com.geolocation.api.exception.CacheException;
import com.geolocation.api.exception.DuplicateEntryException;
import com.geolocation.api.exception.GeolocationNotFoundException;
import com.geolocation.api.exception.IPAddressFormatException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GeolocationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(GeolocationService.class);
  private static final int CACHE_SIZE = 4;
  private static final int CACHE_DURATION = 1;
  private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;

  private final GeolocationDao geolocationDao;
  private final LoadingCache<String, Optional<Geolocation>> cache;

  public GeolocationService(GeolocationDao geolocationDao) {
    this.geolocationDao = geolocationDao;
    this.cache = buildCache();
  }

  public Optional<Geolocation> getGeolocation(String query) {
    if (!InetAddressValidator.getInstance().isValid(query)) {
      throw new IPAddressFormatException();
    }
    try {
      return cache.get(query);
    } catch (ExecutionException | UncheckedExecutionException e) {
      throw new CacheException("cache failure", e);
    }
  }

  public List<Geolocation> getGeolocations() {
    return geolocationDao.getGeolocations();
  }

  public void insertGeolocation(Geolocation geolocation) {
    try {
      geolocationDao.insertGeolocation(geolocation);
    } catch (UnableToExecuteStatementException e) {
      throw new DuplicateEntryException();
    }
  }

  public synchronized void deleteGeolocation(String query) {
    final boolean isDeleted = geolocationDao.deleteGeolocation(query);
    if (isDeleted) {
      cache.invalidate(query);
      LOGGER.info(cache.asMap().toString());
    } else {
      throw new GeolocationNotFoundException();
    }
  }

  private LoadingCache<String, Optional<Geolocation>> buildCache() {
    final CacheLoader<String, Optional<Geolocation>> cacheLoader = new CacheLoader<>() {
      @Nonnull
      @Override
      public Optional<Geolocation> load(@Nonnull String query) {
        final Optional<Geolocation> geolocation = geolocationDao.getGeolocation(query);
        if (geolocation.isPresent()) {
          LOGGER.info("load in cache");
        }
        return geolocation;
      }
    };
    return CacheBuilder.newBuilder()
        .maximumSize(CACHE_SIZE)
        .expireAfterAccess(CACHE_DURATION, TIME_UNIT)
        .build(cacheLoader);
  }

}
