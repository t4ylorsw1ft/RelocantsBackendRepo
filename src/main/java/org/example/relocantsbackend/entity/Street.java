package org.example.relocantsbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "streets")
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int cityId;

    @Column(nullable = false)
    private String streetName;

    public Street() {}

    public Street(int cityId, String streetName) {
        this.cityId = cityId;
        this.streetName = streetName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}
