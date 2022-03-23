package com.geolocation.api.response;

// I would like to use the type HttpStatus but the Response only allows an int type.
public record GeolocationErrorResponse(int httpStatus, String message, long timestamp) {
}
