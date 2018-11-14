package com.yk.mobileassignment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_id")
    @Expose
    private int id;
    @SerializedName("coord")
    @Expose
    private Coord coord;

    public City(String name, String country) {
        this.country = country;
        this.name = name;
    }

    public String getDisplayName() {
        return name + ", " + country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    @Override
    public String toString() {
        return "City{" +
                "country='" + country + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", coord=" + coord +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (country != null ? !country.equals(city.country) : city.country != null) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        return coord != null ? coord.equals(city.coord) : city.coord == null;
    }

}
