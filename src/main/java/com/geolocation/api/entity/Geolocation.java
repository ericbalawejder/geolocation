package com.geolocation.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Geolocation {

    private long id;
    private String query;
    private String status;
    private String country;
    private String countryCode;
    private String region;
    private String regionName;
    private String city;
    private String zip;
    private double lat;
    private double lon;
    private String timezone;
    private String isp;
    private String org;
    private String asHandle;

    protected Geolocation() {
    }

    public Geolocation(long id, String query, String status, String country, String countryCode,
                       String region, String regionName, String city, String zip, double lat,
                       double lon, String timezone, String isp, String org, String asHandle) {
        this.id = id;
        this.query = query;
        this.status = status;
        this.country = country;
        this.countryCode = countryCode;
        this.region = region;
        this.regionName = regionName;
        this.city = city;
        this.zip = zip;
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.isp = isp;
        this.org = org;
        this.asHandle = asHandle;
    }

    public long getId() {
        return id;
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

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
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
        return id == that.id &&
                Double.compare(that.lat, lat) == 0 &&
                Double.compare(that.lon, lon) == 0 &&
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
        return Objects.hash(id, query, status, country, countryCode, region, regionName,
                city, zip, lat, lon, timezone, isp, org, asHandle);
    }

    @Override
    public String toString() {
        return "Geolocation{" +
                "id=" + id +
                ", query='" + query + '\'' +
                ", status='" + status + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", region='" + region + '\'' +
                ", regionName='" + regionName + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", timezone='" + timezone + '\'' +
                ", isp='" + isp + '\'' +
                ", org='" + org + '\'' +
                ", asHandle='" + asHandle + '\'' +
                '}';
    }

}
