package com.geolocation.api.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/geolocation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GeolocationController {

    @GET
    @Path("/hello")
    public Response getLocation() {
        return Response.ok()
                .entity("Welcome to Geolocation")
                .build();
    }

}
