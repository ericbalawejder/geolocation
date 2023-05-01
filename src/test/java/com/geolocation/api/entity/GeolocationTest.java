package com.geolocation.api.entity;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.jackson.Jackson;

class GeolocationTest {

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Test
  public void serializesToJSON() throws Exception {
    final Geolocation geolocation = new Geolocation("55.48.0.1", "success", "United States",
        "US", "AZ", "Arizona", "Sierra Vista", "85613", 31.5552, -110.35, "America/Phoenix",
        "DoD Network Information Center", "USAISC", "AS356 DoD Network Information Center");

    final String expected = MAPPER.writeValueAsString(
        MAPPER.readValue(fixture("fixtures/geolocation.json"), Geolocation.class));

    assertThat(MAPPER.writeValueAsString(geolocation)).isEqualTo(expected);
  }

  @Test
  public void deserializesFromJSON() throws Exception {
    final Geolocation geolocation = new Geolocation("55.48.0.1", "success", "United States",
        "US", "AZ", "Arizona", "Sierra Vista", "85613", 31.5552, -110.35, "America/Phoenix",
        "DoD Network Information Center", "USAISC", "AS356 DoD Network Information Center");

    assertThat(MAPPER.readValue(fixture("fixtures/geolocation.json"), Geolocation.class))
        .isEqualTo(geolocation);
  }

  /**
   * If the external api response ever contains extra fields, the @JsonIgnoreProperties annotation
   * will ignore them for serialization.
   */
  @Test
  public void deserializesFromJSONWithExtraFieldsInResponse() throws Exception {
    final Geolocation geolocation = new Geolocation("55.48.0.1", "success", "United States",
        "US", "AZ", "Arizona", "Sierra Vista", "85613", 31.5552, -110.35, "America/Phoenix",
        "DoD Network Information Center", "USAISC", "AS356 DoD Network Information Center");

    assertThat(MAPPER.readValue(fixture("fixtures/api-response.json"), Geolocation.class))
        .isEqualTo(geolocation);
  }

  @Test
  public void deserializesFromJSONWithMissingFieldsInResponse() throws Exception {
    final Geolocation geolocation = new Geolocation("55.48.0.1", null, "United States",
        "US", null, "Arizona", "Sierra Vista", "85613", 31.5552, -110.35, null,
        "DoD Network Information Center", null, "AS356 DoD Network Information Center");

    assertThat(MAPPER.readValue(fixture("fixtures/api-response2.json"), Geolocation.class))
        .isEqualTo(geolocation);
  }

}