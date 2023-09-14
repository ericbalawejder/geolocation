package com.geolocation.api.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

class GeolocationTest {

  private static final ObjectMapper MAPPER = newObjectMapper();

  @Test
  void serializesToJSON() throws Exception {
    final Geolocation geolocation = new Geolocation("55.48.0.1", "success", "United States",
        "US", "AZ", "Arizona", "Sierra Vista", "85613", 31.5552, -110.35, "America/Phoenix",
        "DoD Network Information Center", "USAISC", "AS356 DoD Network Information Center",
        OffsetDateTime.parse("2023-01-01T00:00:00Z"));

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(getClass().getResource("/fixtures/geolocation.json"), Geolocation.class));

    assertThat(MAPPER.writeValueAsString(geolocation)).isEqualTo(expected);
  }

  @Test
  void deserializesFromJSON() throws Exception {
    final Geolocation geolocation = new Geolocation("55.48.0.1", "success", "United States",
        "US", "AZ", "Arizona", "Sierra Vista", "85613", 31.5552, -110.35, "America/Phoenix",
        "DoD Network Information Center", "USAISC", "AS356 DoD Network Information Center",
        OffsetDateTime.parse("2023-01-01T00:00:00Z"));

    assertThat(MAPPER.readValue(getClass().getResource("/fixtures/geolocation.json"), Geolocation.class))
        .isEqualTo(geolocation);
  }

  /**
   * If the external api response ever contains extra fields, the @JsonIgnoreProperties annotation
   * will ignore them for serialization.
   */
  @Test
  void deserializesFromJSONWithExtraFieldsInResponse() throws Exception {
    final Geolocation geolocation = new Geolocation("55.48.0.1", "success", "United States",
        "US", "AZ", "Arizona", "Sierra Vista", "85613", 31.5552, -110.35, "America/Phoenix",
        "DoD Network Information Center", "USAISC", "AS356 DoD Network Information Center",
        OffsetDateTime.parse("2023-01-01T00:00:00Z"));

    assertThat(MAPPER.readValue(getClass().getResource("/fixtures/api-response.json"), Geolocation.class))
        .isEqualTo(geolocation);
  }

  @Test
  void deserializesFromJSONWithMissingFieldsInResponse() throws Exception {
    final Geolocation geolocation = new Geolocation("55.48.0.1", null, "United States",
        "US", null, "Arizona", "Sierra Vista", "85613", 31.5552, -110.35, null,
        "DoD Network Information Center", null, "AS356 DoD Network Information Center",
        OffsetDateTime.parse("2023-01-01T00:00:00Z"));

    assertThat(MAPPER.readValue(getClass().getResource("/fixtures/api-response2.json"), Geolocation.class))
        .isEqualTo(geolocation);
  }

}