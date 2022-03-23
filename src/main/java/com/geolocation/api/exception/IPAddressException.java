package com.geolocation.api.exception;

public class IPAddressException extends RuntimeException {

    public IPAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public IPAddressException(String message) {
        super(message);
    }

    public IPAddressException(Throwable cause) {
        super(cause);
    }

    public IPAddressException() {
        super("ip address format is not valid");
    }

}
