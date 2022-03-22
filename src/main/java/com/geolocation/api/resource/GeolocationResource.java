package com.geolocation.api.resource;

import com.geolocation.api.entity.Geolocation;
import com.geolocation.api.service.GeolocationService;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/api/geolocation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GeolocationResource {

    private final GeolocationService geolocationService;

    public GeolocationResource(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GET
    @Path("/hello")
    public Response hello() {
        return Response.ok()
                .entity("Welcome to Geolocation")
                .build();
    }

    @GET
    @Path("/geolocations")
    public Response getGeolocations() {
        return Response.ok()
                .entity(this.geolocationService.getGeolocations())
                .build();
    }

    @GET
    @Path("{query}")
    public Response getGeolocation(@PathParam("query") String query) {
        return Response.ok()
                .entity(this.geolocationService.getGeolocation(query))
                .build();
    }

    @POST
    public Response insertGeolocation(@NotNull Geolocation geolocation) throws URISyntaxException {
        this.geolocationService.insertGeolocation(geolocation);
        return Response.created(new URI("/api/geolocation/" + geolocation.getQuery()))
                .build();
    }

    @DELETE
    @Path("{query}")
    public Response deleteGeolocation(@PathParam("query") String query) {
        return Response.ok()
                .entity(this.geolocationService.deleteGeolocation(query))
                .build();
    }

}
