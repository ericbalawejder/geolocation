package com.geolocation.api.configuration;

import org.jdbi.v3.core.Jdbi;

import com.geolocation.api.dao.GeolocationDao;
import com.geolocation.api.resource.GeolocationResource;
import com.geolocation.api.resource.RuntimeExceptionMapper;
import com.geolocation.api.service.GeolocationService;

import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;

public class GeolocationApplication extends Application<GeolocationConfiguration> {

  public static void main(String[] args) throws Exception {
    new GeolocationApplication().run(args);
  }

  @Override
  public void run(GeolocationConfiguration configuration, Environment environment) {
    System.out.println("Geolocation Application");
    final JdbiFactory factory = new JdbiFactory();
    final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
    final GeolocationDao geolocationDao = jdbi.onDemand(GeolocationDao.class);
    final GeolocationService geolocationService = new GeolocationService(geolocationDao);
    final GeolocationResource geolocationResource = new GeolocationResource(geolocationService);
    environment.jersey().register(geolocationResource);
    environment.jersey().register(new RuntimeExceptionMapper());
  }

}
