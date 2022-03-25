package com.geolocation.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Geolocation {

    private String query;
    private String status;
    private String country;
    @ColumnName("country_code")
    private String countryCode;
    private String region;
    @ColumnName("region_name")
    private String regionName;
    private String city;
    private String zip;
    private double latitude;
    private double longitude;
    private String timezone;
    private String isp;
    private String org;
    @ColumnName("as_handle")
    private String asHandle;

    protected Geolocation() {
    }

    public Geolocation(String query, String status, String country, String countryCode,
                       String region, String regionName, String city, String zip, double latitude,
                       double longitude, String timezone, String isp, String org, String asHandle) {
        this.query = query;
        this.status = status;
        this.country = country;
        this.countryCode = countryCode;
        this.region = region;
        this.regionName = regionName;
        this.city = city;
        this.zip = zip;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
        this.isp = isp;
        this.org = org;
        this.asHandle = asHandle;
    }

    public String getQuery() {
        return query;
    }

    public String getStatus() {
        return status;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getRegion() {
        return region;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    @JsonProperty(value = "lat")
    public double getLatitude() {
        return latitude;
    }

    @JsonProperty(value = "lon")
    public double getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getIsp() {
        return isp;
    }

    public String getOrg() {
        return org;
    }

    @JsonProperty(value = "as")
    public String getAsHandle() {
        return asHandle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geolocation that = (Geolocation) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0 &&
                query.equals(that.query) &&
                Objects.equals(status, that.status) &&
                Objects.equals(country, that.country) &&
                Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(region, that.region) &&
                Objects.equals(regionName, that.regionName) &&
                Objects.equals(city, that.city) &&
                Objects.equals(zip, that.zip) &&
                Objects.equals(timezone, that.timezone) &&
                Objects.equals(isp, that.isp) &&
                Objects.equals(org, that.org) &&
                Objects.equals(asHandle, that.asHandle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query, status, country, countryCode, region, regionName,
                city, zip, latitude, longitude, timezone, isp, org, asHandle);
    }

    @Override
    public String toString() {
        return "Geolocation{" +
                ", query='" + query + '\'' +
                ", status='" + status + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", region='" + region + '\'' +
                ", regionName='" + regionName + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", lat=" + latitude +
                ", lon=" + longitude +
                ", timezone='" + timezone + '\'' +
                ", isp='" + isp + '\'' +
                ", org='" + org + '\'' +
                ", asHandle='" + asHandle + '\'' +
                '}';
    }

}
