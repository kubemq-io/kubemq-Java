package com.tradency.kubemq.sdk.commandquery;

import com.tradency.kubemq.sdk.grpc.Kubemq;

public class RequestReceive {

    /**
     * Represents a Request identifier
     */
    private String requestId;

    /**
     * Represents the type of request operation using com.tradency.kubemq.sdk.commandquery.RequestType.
     */
    private RequestType requestType;

    /**
     * Represents the sender ID that the Request return from
     */
    private String clientID;

    /**
     * Represents The channel name to send to using the KubeMQ
     */
    private String channel;

    /**
     * Represents The channel name that the response returned from
     */
    private String replyChannel;

    /**
     * Represents text as String
     */
    private String metadata;

    /**
     * Represents The content of the com.tradency.kubemq.sdk.commandquery.RequestReceive
     */
    private byte[] body;

    /**
     * Represents the limit for waiting for response (Milliseconds)
     */
    private int timeout;

    /**
     * Represents if the request should be saved from Cache and under what "Key"(String) to save it
     */
    private String cacheKey;

    /**
     * Cache time to live : for how long does the request should be saved in Cache
     */
    private int cacheTTL;

    RequestReceive(Kubemq.Request innerRequest) {
        setRequestId(innerRequest.getRequestID());
        setRequestType(RequestType.values()[(innerRequest.getRequestTypeDataValue())]);
        setClientID(innerRequest.getClientID());
        setChannel(innerRequest.getChannel());
        setMetadata(innerRequest.getMetadata());
        setBody(innerRequest.getBody().toByteArray());
        setReplyChannel(innerRequest.getReplyChannel());
        setTimeout(innerRequest.getTimeout());
        setCacheKey(innerRequest.getCacheKey());
        setCacheTTL(innerRequest.getCacheTTL());
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getReplyChannel() {
        return replyChannel;
    }

    public void setReplyChannel(String replyChannel) {
        this.replyChannel = replyChannel;
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

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public int getCacheTTL() {
        return cacheTTL;
    }

    public void setCacheTTL(int cacheTTL) {
        this.cacheTTL = cacheTTL;
    }
}
