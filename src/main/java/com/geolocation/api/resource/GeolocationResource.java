package com.geolocation.api.resource;

import com.geolocation.api.entity.Geolocation;
import com.geolocation.api.exception.GeolocationException;
import com.geolocation.api.exception.IPAddressException;
import com.geolocation.api.service.GeolocationService;
import com.geolocation.api.util.Greeting;
import org.apache.commons.validator.routines.InetAddressValidator;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
                .entity(Greeting.MESSAGE)
                .build();
    }

    @GET
    @Path("/ip/{query}")
    public Response getGeolocation(@PathParam("query") String query) {
        if (!InetAddressValidator.getInstance().isValid(query)) {
            throw new IPAddressException();
        }
        try {
            final Geolocation geolocation = geolocationService.getGeolocation(query);

            return Response.ok()
                    .entity(geolocation)
                    .build();

        } catch (GeolocationException e) {
            // SSL unavailable for this endpoint, order a key at https://members.ip-api.com/
            return ClientBuilder.newClient()
                    .target("http://ip-api.com/json/" + query)
                    .request()
                    .get(Response.class);
        }
    }

    @GET
    @Path("/geolocations")
    public Response getGeolocations() {
        final List<Geolocation> geolocations = geolocationService.getGeolocations();

        return Response.ok()
                .entity(geolocations)
                .build();
    }

    @POST
    @Path("/upload")
    public Response insertGeolocation(@NotNull Geolocation geolocation) throws URISyntaxException {
        geolocationService.insertGeolocation(geolocation);

        return Response.created(new URI("/api/geolocation/" + geolocation.getQuery()))
                .entity("geolocation uploaded successfully")
                .build();
    }

    @DELETE
    @Path("/delete/{query}")
    public Response deleteGeolocation(@PathParam("query") String query) {
        geolocationService.deleteGeolocation(query);

        return Response.ok()
                .entity("geolocation deleted successfully")
                .build();
    }

}
