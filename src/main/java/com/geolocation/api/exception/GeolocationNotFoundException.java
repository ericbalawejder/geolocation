package com.geolocation.api.exception;

public class GeolocationNotFoundException extends RuntimeException {

  public GeolocationNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public GeolocationNotFoundException(String message) {
    super(message);
  }

  public GeolocationNotFoundException(Throwable cause) {
    super(cause);
  }

  public GeolocationNotFoundException() {
    super("ip not found");
  }

}
