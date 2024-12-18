package org.example.relocantsbackend.dto.documents;

public class DocumentDTO {
    private int id;
    private String titleRu;
    private String descriptionRu;

    public DocumentDTO(int id, String titleRu, String descriptionRu) {
        this.id = id;
        this.titleRu = titleRu;
        this.descriptionRu = descriptionRu;
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

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }
}
