package com.geolocation.api.dao;

import com.geolocation.api.entity.Geolocation;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeolocationMapper implements RowMapper<Geolocation> {

    @Override
    public Geolocation map(ResultSet resultSet, StatementContext ctx) throws SQLException {
        return new Geolocation(
                resultSet.getString("query"),
                resultSet.getString("status"),
                resultSet.getString("country"),
                resultSet.getString("countryCode"),
                resultSet.getString("region"),
                resultSet.getString("regionName"),
                resultSet.getString("city"),
                resultSet.getString("zip"),
                resultSet.getDouble("latitude"),
                resultSet.getDouble("longitude"),
                resultSet.getString("timezone"),
                resultSet.getString("isp"),
                resultSet.getString("org"),
                resultSet.getString("asHandle")
        );
    }

}
