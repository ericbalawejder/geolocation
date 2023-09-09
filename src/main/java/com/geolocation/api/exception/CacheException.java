package com.geolocation.api.exception;

public class CacheException extends RuntimeException {

  public CacheException(String message, Throwable cause) {
    super(message, cause);
  }

  public CacheException(String message) {
    super(message);
  }

  public CacheException(Throwable cause) {
    super(cause);
  }

  public CacheException() {
    super("cache failure");
  }

}
