package com.geolocation.api.exception;

import com.geolocation.api.response.GeolocationErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.jetty.http.HttpStatus;

@Provider
public class CacheExceptionMapper implements ExceptionMapper<CacheException> {

  @Override
  public Response toResponse(CacheException exception) {
    final GeolocationErrorResponse response = new GeolocationErrorResponse(
        HttpStatus.FAILED_DEPENDENCY_424, exception.getMessage(), System.currentTimeMillis());

    return Response.status(response.httpStatus())
        .entity(response)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

}
