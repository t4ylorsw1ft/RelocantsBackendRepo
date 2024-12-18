package org.example.relocantsbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    @Column(nullable = false)
    private String titleRu;

    @Column(nullable = false)
    private String titlePl;

    private String descriptionRu;
    private String descriptionPl;

    private String defaultPhotoUrl;

    public Document() {}

    public Document(int userId, String titleRu, String titlePl, String descriptionRu, String descriptionPl, String defaultPhotoUrl) {
        this.userId = userId;
        this.titleRu = titleRu;
        this.titlePl = titlePl;
        this.descriptionRu = descriptionRu;
        this.descriptionPl = descriptionPl;
        this.defaultPhotoUrl = defaultPhotoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getDefaultPhotoUrl() {
        return defaultPhotoUrl;
    }

    public void setDefaultPhotoUrl(String defaultPhotoUrl) {
        this.defaultPhotoUrl = defaultPhotoUrl;
    }
}
