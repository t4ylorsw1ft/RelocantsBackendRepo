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

    public Instruction() {}

    public Instruction(String titleRu, String titlePl) {
        this.titleRu = titleRu;
        this.titlePl = titlePl;
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
}

