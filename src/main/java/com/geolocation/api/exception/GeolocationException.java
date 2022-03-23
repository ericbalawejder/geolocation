package com.geolocation.api.exception;

public class GeolocationException extends RuntimeException {

    public GeolocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeolocationException(String message) {
        super(message);
    }

    public GeolocationException(Throwable cause) {
        super(cause);
    }

    public GeolocationException() {
        super("ip not found");
    }

}
