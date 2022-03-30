package com.geolocation.api.exception;

public class ExternalAPIRequestException extends RuntimeException {

    public ExternalAPIRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalAPIRequestException(String message) {
        super(message);
    }

    public ExternalAPIRequestException(Throwable cause) {
        super(cause);
    }

    public ExternalAPIRequestException() {
        super("external api request failed");
    }

}
