package com.peteydalite.EmployeeSkills.model;

import java.util.UUID;

public class Address {
    private UUID id;
    private String street;
    private String suite;
    private String city;
    private String region;
    private Long postal;
    private String country;

    public Address() {};

    public Address(UUID id, String street, String suite, String city, String region, Long postal, String country) {
        this.id = id;
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.region = region;
        this.postal = postal;
        this.country = country;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getPostal() {
        return postal;
    }

    public void setPostal(Long postal) {
        this.postal = postal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", suite='" + suite + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", postal=" + postal +
                ", country='" + country + '\'' +
                '}';
    }
}
