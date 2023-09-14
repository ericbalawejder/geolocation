package com.geolocation.api.dao;

import com.geolocation.api.entity.Geolocation;
import com.geolocation.api.exception.RowMapperException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class GeolocationMapper implements RowMapper<Geolocation> {

  @Override
  public Geolocation map(ResultSet resultSet, StatementContext ctx) {
    try {
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
          resultSet.getString("as_handle"),
          convertToOffsetDateTime(resultSet.getTimestamp("create_date")));
    } catch (SQLException | DateTimeException e) {
      throw new RowMapperException(e.getMessage(), e.getCause());
    }
  }

  private static OffsetDateTime convertToOffsetDateTime(Timestamp timestamp) {
    return OffsetDateTime.ofInstant(Instant.ofEpochMilli(timestamp.getTime()), ZoneId.of("UTC"));
  }

}
