package com.tradency.kubemq.sdk.event;

import com.tradency.kubemq.sdk.grpc.Kubemq;

public class Result {

    private String eventId;
    private boolean sent;
    private String error;

    public Result() {
    }

    public Result(Kubemq.Result innerReport) {
        setEventId(innerReport.getEventID());
        setSent(innerReport.getSent());
        setError(innerReport.getError());
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
