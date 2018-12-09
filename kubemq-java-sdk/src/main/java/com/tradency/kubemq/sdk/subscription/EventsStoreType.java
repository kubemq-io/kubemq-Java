package com.tradency.kubemq.sdk.subscription;

public enum EventsStoreType {
    Undefined(0),
    StartNewOnly(1),
    StartFromFirst(2),
    StartFromLast(3),
    StartAtSequence(4),
    StartAtTime(5),
    StartAtTimeDelta(6);

    private int value;

    private EventsStoreType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
