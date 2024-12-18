package org.example.relocantsbackend.dto.documents;

public class AddDocumentDTO {
    private String titleRu;
    private String titlePl;
    private String descriptionRu;
    private String descriptionPl;
    private String defaultPhotoUrl;

    public AddDocumentDTO(String titleRu, String titlePl, String descriptionRu, String descriptionPl, String defaultPhotoUrl) {
        this.titleRu = titleRu;
        this.titlePl = titlePl;
        this.descriptionRu = descriptionRu;
        this.descriptionPl = descriptionPl;
        this.defaultPhotoUrl = defaultPhotoUrl;
    }

    // Геттеры и сеттеры

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
