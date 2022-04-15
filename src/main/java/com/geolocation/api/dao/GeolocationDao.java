package com.geolocation.api.dao;

import com.geolocation.api.entity.Geolocation;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.Timestamped;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterRowMapper(GeolocationMapper.class)
public interface GeolocationDao {

    @SqlQuery("SELECT * FROM geolocation")
    List<Geolocation> getGeolocations();

    @SqlQuery("SELECT * FROM geolocation WHERE query = :query")
    Optional<Geolocation> getGeolocation(@Bind("query") String query);

    @SqlUpdate("INSERT INTO geolocation (`query`, `status`, `country`, " +
            "`country_code`, `region`, `region_name`, `city`, `zip`, `latitude`, " +
            "`longitude`, `timezone`,`isp`, `org`, `as_handle`) " +
            "VALUES (:query, :status, :country, :countryCode, :region, :regionName, :city, :zip," +
            ":latitude, :longitude, :timezone, :isp, :org, :asHandle);")
    @Timestamped
    void insertGeolocation(@BindBean Geolocation geolocation);

    @SqlUpdate("DELETE FROM geolocation WHERE query = :query")
    void deleteGeolocation(@Bind("query") String query);

}
