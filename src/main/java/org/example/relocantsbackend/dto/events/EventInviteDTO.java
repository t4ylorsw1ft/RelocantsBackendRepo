package org.example.relocantsbackend.dto.events;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventInviteDTO {

    @JsonProperty("related_user_id")
    private int relatedUserId;

    @JsonProperty("event_id")
    private int eventId;

    public int getRelatedUserId() {
        return relatedUserId;
    }

    public void setRelatedUserId(int relatedUserId) {
        this.relatedUserId = relatedUserId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}

