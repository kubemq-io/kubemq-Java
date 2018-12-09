package io.kubemq.sdk.subscription;

/**
 * Type of subscription operation pattern
 */
public enum SubscribeType {

    /**
     * Default
     */
    SubscribeTypeUndefined(0),

    /**
     * PubSub event
     */
    Events(1),

    /**
     * PubSub event with persistence
     */
    EventsStore(2),

    /**
     * ReqRep perform action
     */
    Commands(3),

    /**
     * ReqRep return data
     */
    Queries(4);

    private int value;

    private SubscribeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
