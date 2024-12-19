package org.example.relocantsbackend.dto.subscriptions;

public class SubscriptionResponseDTO {

    private int relatedId;
    private String fullName;
    private boolean isSubscribed;

    public SubscriptionResponseDTO() {
    }

    public SubscriptionResponseDTO(int relatedId, String fullName, boolean isSubscribed) {
        this.relatedId = relatedId;
        this.fullName = fullName;
        this.isSubscribed = isSubscribed;
    }

    public int getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(int relatedId) {
        this.relatedId = relatedId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}

