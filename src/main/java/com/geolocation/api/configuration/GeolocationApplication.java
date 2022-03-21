package com.geolocation.api.configuration;

import com.geolocation.api.controller.GeolocationController;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class GeolocationApplication extends Application<GeolocationConfiguration> {

    public static void main(String[] args) throws Exception {
        new GeolocationApplication().run(args);
    }

    @Override
    public void run(GeolocationConfiguration configuration, Environment environment) {
        System.out.println("Geolocation Application");
        final GeolocationController geolocationController = new GeolocationController();
        environment.jersey().register(geolocationController);
    }

}
