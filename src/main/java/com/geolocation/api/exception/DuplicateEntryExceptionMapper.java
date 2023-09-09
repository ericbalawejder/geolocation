package com.geolocation.api.exception;

import com.geolocation.api.response.GeolocationErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.jetty.http.HttpStatus;

@Provider
public class DuplicateEntryExceptionMapper implements ExceptionMapper<DuplicateEntryException> {

  @Override
  public Response toResponse(DuplicateEntryException exception) {
    final GeolocationErrorResponse response = new GeolocationErrorResponse(
        HttpStatus.CONFLICT_409, exception.getMessage(), System.currentTimeMillis());

    return Response.status(response.httpStatus())
        .entity(response)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }

}
