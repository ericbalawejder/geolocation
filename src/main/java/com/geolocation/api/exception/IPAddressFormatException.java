package com.geolocation.api.exception;

public class IPAddressFormatException extends RuntimeException {

  public IPAddressFormatException(String message, Throwable cause) {
    super(message, cause);
  }

  public IPAddressFormatException(String message) {
    super(message);
  }

  public IPAddressFormatException(Throwable cause) {
    super(cause);
  }

  public IPAddressFormatException() {
    super("ip address format is not valid");
  }

}
