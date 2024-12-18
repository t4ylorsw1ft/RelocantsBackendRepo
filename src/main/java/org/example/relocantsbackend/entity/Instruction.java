package org.example.relocantsbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "instructions")
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String titleRu;

    @Column(nullable = false)
    private String titlePl;

    @Column(nullable = false)
    private String descriptionRu;

    @Column(nullable = false)
    private String descriptionPl;

    public Instruction() {}

    public Instruction(String titleRu, String titlePl, String descriptionRu, String descriptionPl) {
        this.titleRu = titleRu;
        this.titlePl = titlePl;
        this.descriptionRu = descriptionRu;
        this.descriptionPl = descriptionPl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public void setTitleRu(String titleRu) {
        this.titleRu = titleRu;
    }

    public String getTitlePl() {
        return titlePl;
    }

    public void setTitlePl(String titlePl) {
        this.titlePl = titlePl;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }
    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }
    public String getDescriptionPl() {
        return descriptionPl;
    }
    public void setDescriptionPl(String descriptionPl) {
        this.descriptionPl = descriptionPl;
    }
}

