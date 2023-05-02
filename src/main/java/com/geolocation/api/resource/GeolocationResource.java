package com.geolocation.api.resource;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Singleton;
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

import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geolocation.api.entity.Geolocation;
import com.geolocation.api.exception.IPAddressFormatException;
import com.geolocation.api.service.GeolocationService;

@Path("/api/geolocation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class GeolocationResource {

  // SSL unavailable for this endpoint, order a key at https://members.ip-api.com/
  private static final URI ENDPOINT = URI.create("http://ip-api.com/json/");
  private static final Logger LOGGER = LoggerFactory.getLogger(GeolocationResource.class);

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
  @Path("/ip/{query}")
  public Response getGeolocation(@PathParam("query") String query) {
    if (!InetAddressValidator.getInstance().isValid(query)) {
      throw new IPAddressFormatException();
    }
    final Geolocation geolocation = geolocationService.getGeolocation(query)
        .orElseGet(() -> makeExternalAPICall(query));

    return Response.ok()
        .entity(geolocation)
        .build();
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

  private Geolocation makeExternalAPICall(String query) {
    try {
      LOGGER.info("making external api call to " + ENDPOINT + query);
      return ClientBuilder.newClient()
          .target(ENDPOINT.toURL() + query)
          .request()
          .get(Geolocation.class);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

}
