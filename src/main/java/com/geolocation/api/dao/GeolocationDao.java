package com.geolocation.api.dao;

import com.geolocation.api.entity.Geolocation;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterRowMapper(GeolocationMapper.class)
public interface GeolocationDao {

    @SqlQuery("SELECT * FROM geolocation")
    List<Geolocation> getGeolocations();

    @SqlQuery("SELECT * FROM geolocation WHERE query = :query")
    Geolocation getGeolocation(@Bind("query") String query);

    @SqlUpdate("INSERT INTO geolocation (`id`, `query`, `status`, `country`, `countryCode`, " +
            "`region`, `regionName`, `city`, `zip`, `lat`, `lon`, `timezone`, `isp`, `org`, `asHandle`) " +
            "VALUES (:id, :query, :status, :country, :countryCode, :region, :regionName, :city, :zip," +
            ":lat, :lon, :timezone, :isp, :org, :asHandle);")
    void insertGeolocation(@BindBean Geolocation geolocation);

    @SqlUpdate("DELETE FROM geolocation WHERE id = :id")
    int deleteGeolocation(@Bind("id") int id);

}
