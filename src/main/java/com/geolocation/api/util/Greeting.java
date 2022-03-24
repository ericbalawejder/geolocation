package com.geolocation.api.util;

public class Greeting {

    public static final String MESSAGE = """
                               Welcome to Geolocation.
                
                To retrieve IP based location information, use the path:
                                
                /api/geolocation/ip/<address>
                                
                where <address> is a valid IPv4 or IPv6 address.
                
                To upload a geolocation:
                
                /api/geolocation/upload
                
                To see all of the ip addresses in the database:
                
                /api/geolocation/geolocations
                
                To delete a geolocation from the database:
                
                /api/geolocation/delete/<address>
                """;

    private Greeting() {
        throw new IllegalStateException("utility class");
    }

}
