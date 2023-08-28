package com.geolocation.api.configuration;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class GeolocationConfiguration extends Configuration {

  @Valid
  @NotNull
  private DataSourceFactory database = new DataSourceFactory();

  @JsonProperty("database")
  public void setDataSourceFactory(DataSourceFactory factory) {
    this.database = factory;
  }

  @JsonProperty("database")
  public DataSourceFactory getDataSourceFactory() {
    return database;
  }

}
