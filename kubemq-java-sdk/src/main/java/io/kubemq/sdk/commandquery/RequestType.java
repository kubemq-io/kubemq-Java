package io.kubemq.sdk.commandquery;

public enum RequestType {
    RequestTypeUnknown(0),
    Command(1),
    Query(2);

    private int value;

    private RequestType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
