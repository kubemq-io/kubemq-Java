package com.tradency.kubemq.sdk.event;

public class Event {
    private String eventId;
    private String metadata;
    private byte[] body;

    public Event() {
    }

    public Event(String eventId, String metadata, byte[] body) {
        this.eventId = eventId;
        this.metadata = metadata;
        this.body = body;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}
