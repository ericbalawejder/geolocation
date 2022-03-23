package com.geolocation.api.exception;

import com.geolocation.api.response.GeolocationErrorResponse;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * I'm looking to handle custom exceptions here like Spring Boot with the @ControllerAdvice
 * class level annotation and @ExceptionHandler for each given method. It appears that all exceptions
 * are going through the toResponse(RuntimeException exception) method at the bottom of this class.
 *
 * My custom responses (GeolocationErrorResponse and GeolocationResponse) are not being serialized
 * properly and the status code appears with an empty json block.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

    public Response handleIPAddressException(IPAddressException exc) {
        final GeolocationErrorResponse response = new GeolocationErrorResponse(
                HttpStatus.BAD_REQUEST_400, exc.getMessage(), System.currentTimeMillis());

        return Response.status(response.httpStatus())
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public Response handleGeolocationException(GeolocationException exc) {
        final GeolocationErrorResponse response = new GeolocationErrorResponse(
                HttpStatus.NOT_FOUND_404, exc.getMessage(), System.currentTimeMillis());

        return Response.status(response.httpStatus())
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @Override
    public Response toResponse(RuntimeException exception) {
        /*
        final GeolocationErrorResponse response = new GeolocationErrorResponse(
                HttpStatus.NOT_FOUND_404, "¯\\_(ツ)_/¯", System.currentTimeMillis());

        return Response.status(response.httpStatus())
                .entity(response)
                .type(MediaType.APPLICATION_JSON)
                .build();
         */
        return Response.status(404)
                .entity(exception.getMessage())
                .build();
    }

}
