package org.example.relocantsbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "steps")
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int instructionId;

    @Column(nullable = false)
    private String titleRu;

    @Column(nullable = false)
    private String titlePl;

    private String descriptionRu;
    private String descriptionPl;

    private int documentId;

    public Step() {}

    public Step(int instructionId, String titleRu, String titlePl, String descriptionRu, String descriptionPl, int documentId) {
        this.instructionId = instructionId;
        this.titleRu = titleRu;
        this.titlePl = titlePl;
        this.descriptionRu = descriptionRu;
        this.descriptionPl = descriptionPl;
        this.documentId = documentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(int instructionId) {
        this.instructionId = instructionId;
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

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }
}
