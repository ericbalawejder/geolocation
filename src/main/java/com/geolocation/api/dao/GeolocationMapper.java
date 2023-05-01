package com.geolocation.api.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import com.geolocation.api.entity.Geolocation;

public class GeolocationMapper implements RowMapper<Geolocation> {

  @Override
  public Geolocation map(ResultSet resultSet, StatementContext ctx) throws SQLException {
    return new Geolocation(
        resultSet.getString("query"),
        resultSet.getString("status"),
        resultSet.getString("country"),
        resultSet.getString("country_code"),
        resultSet.getString("region"),
        resultSet.getString("region_name"),
        resultSet.getString("city"),
        resultSet.getString("zip"),
        resultSet.getDouble("latitude"),
        resultSet.getDouble("longitude"),
        resultSet.getString("timezone"),
        resultSet.getString("isp"),
        resultSet.getString("org"),
        resultSet.getString("as_handle")
    );
  }

}
