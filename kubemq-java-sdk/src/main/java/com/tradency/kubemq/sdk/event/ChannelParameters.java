package com.tradency.kubemq.sdk.event;

public class ChannelParameters {

    /**
     * Represents The channel name to send to using the KubeMQ.
     */
    private String channelName;

    /**
     * Represents the sender ID that the messages will be send under.
     */
    private String clientID;

    /**
     * Represents if the messages should be send to persistence.
     */
    private boolean store;

    /**
     * Represents if the end user does not need the Result.
     */
    private boolean returnResult;

    /**
     * KubeMQ server address.
     */
    private String kubeMQAddress;

    public ChannelParameters() {
    }

    public ChannelParameters(String channelName, String clientID, boolean store, String kubeMQAddress) {
        this.channelName = channelName;
        this.clientID = clientID;
        this.store = store;
        this.kubeMQAddress = kubeMQAddress;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }

    public boolean isReturnResult() {
        return returnResult;
    }

    public void setReturnResult(boolean returnResult) {
        this.returnResult = returnResult;
    }

    public String getKubeMQAddress() {
        return kubeMQAddress;
    }

    public void setKubeMQAddress(String kubeMQAddress) {
        this.kubeMQAddress = kubeMQAddress;
    }

}
