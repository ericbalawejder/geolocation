package com.geolocation.api.resource;

import com.geolocation.api.dao.GeolocationDao;
import com.geolocation.api.entity.Geolocation;
import com.geolocation.api.service.GeolocationService;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(DropwizardExtensionsSupport.class)
class GeolocationResourceTest {

  private static final GeolocationDao DAO = mock(GeolocationDao.class);
  private static final GeolocationService SERVICE = mock(GeolocationService.class);
  private static final ResourceExtension EXT = ResourceExtension.builder()
      .addResource(new GeolocationService(DAO))
      .addResource(new GeolocationResource(SERVICE))
      .build();

  @BeforeEach
  void setup() {
    openMocks(this);
  }

  @AfterEach
  void tearDown() {
    reset(DAO);
  }

  @Test
  void getGeolocationTest() {
    final Geolocation expected = new Geolocation("55.48.0.1", "success", "United States",
        "US", "AZ", "Arizona", "Sierra Vista", "85613",
        31.5552, -110.35, "America/Phoenix", "DoD Network Information Center",
        "USAISC", "AS356 DoD Network Information Center", OffsetDateTime.parse("2023-01-01T00:00:00Z"));

    when(SERVICE.getGeolocation("55.48.0.1")).thenReturn(Optional.of(expected));

    final Geolocation actual = EXT.target("/api/geolocation/ip/55.48.0.1")
        .request()
        .accept(MediaType.APPLICATION_JSON)
        .get(Geolocation.class);

    assertThat(expected).isEqualTo(actual);
    verify(SERVICE).getGeolocation(expected.getQuery());
  }

  @Test
  void helloTest() {
    assertThat(EXT.target("/api/geolocation/hello")
        .request()
        .get(String.class))
        .isEqualTo("Welcome to Geolocation");
  }

}