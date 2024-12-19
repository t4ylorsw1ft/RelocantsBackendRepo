package org.example.relocantsbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String cityRu;

    @Column(nullable = false)
    private String cityPl;

    public City() {}

    public City(String cityRu, String cityPl) {
        this.cityRu = cityRu;
        this.cityPl = cityPl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityRu() {
        return cityRu;
    }

    public void setCityRu(String cityRu) {
        this.cityRu = cityRu;
    }

    public String getCityPl() {
        return cityPl;
    }

    public void setCityPl(String cityPl) {
        this.cityPl = cityPl;
    }

}
