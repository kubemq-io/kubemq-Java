package io.kubemq.sdk.event;

import io.kubemq.sdk.grpc.Kubemq;

public class EventReceive {

    private String eventId;
    private String channel;
    private String metadata;
    private byte[] body;
    private long timestamp;
    private long sequence;

    public EventReceive() {
    }

    public EventReceive(Kubemq.EventReceive inner) {
        eventId = inner.getEventID();
        channel = inner.getChannel();
        metadata = inner.getMetadata();
        body = inner.getBody().toByteArray();
        timestamp = inner.getTimestamp();
        sequence = inner.getSequence();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

}
