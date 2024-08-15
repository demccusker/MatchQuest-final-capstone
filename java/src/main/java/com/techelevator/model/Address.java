package com.techelevator.model;

public class Address {
    private Integer tournamentId;
    private String streetNumber;
    private String city;
    private String province;
    private String country;

    public Address() {}
    public Address(Integer tournamentId, String streetNumber, String city, String province, String country) {
        this.tournamentId = tournamentId;
        this.streetNumber = streetNumber;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
