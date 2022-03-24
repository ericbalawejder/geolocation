package com.geolocation.api.exception;

public class DuplicateEntryException extends RuntimeException {

    public DuplicateEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntryException(String message) {
        super(message);
    }

    public DuplicateEntryException(Throwable cause) {
        super(cause);
    }

    public DuplicateEntryException() {
        super("duplicate entry found");
    }

}
