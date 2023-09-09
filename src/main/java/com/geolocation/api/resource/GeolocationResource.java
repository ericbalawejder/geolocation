package com.geolocation.api.resource;

import com.geolocation.api.entity.Geolocation;
import com.geolocation.api.exception.ExternalAPIRequestException;
import com.geolocation.api.service.GeolocationService;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
  public Response insertGeolocation(@NotNull Geolocation geolocation) {
    try {
      final URI url = new URI("/api/geolocation/" + geolocation.getQuery());
      geolocationService.insertGeolocation(geolocation);

      return Response.created(url)
          .entity("geolocation uploaded successfully")
          .build();
    } catch (URISyntaxException e) {
      throw new RuntimeException("the given string violates RFC2396: " + e);
    }
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
      throw new ExternalAPIRequestException("external api call failed", e);
    }
  }

}
