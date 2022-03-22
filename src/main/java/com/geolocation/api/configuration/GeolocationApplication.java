package com.geolocation.api.configuration;

import com.geolocation.api.resource.GeolocationResource;
import com.geolocation.api.dao.GeolocationDao;
import com.geolocation.api.service.GeolocationService;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

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
    }

}
