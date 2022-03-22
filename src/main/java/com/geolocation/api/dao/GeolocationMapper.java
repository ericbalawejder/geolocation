package com.geolocation.api.dao;

import com.geolocation.api.entity.Geolocation;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GeolocationMapper implements RowMapper<Geolocation> {

    @Override
    public Geolocation map(ResultSet rs, StatementContext ctx) throws SQLException {
        return null;
    }

}
