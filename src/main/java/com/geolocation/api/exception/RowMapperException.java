package com.geolocation.api.exception;

public class RowMapperException extends RuntimeException {

  public RowMapperException(String message, Throwable cause) {
    super(message, cause);
  }

  public RowMapperException(String message) {
    super(message);
  }

  public RowMapperException(Throwable cause) {
    super(cause);
  }

  public RowMapperException() {
    super("sql row mapping failed");
  }

}
