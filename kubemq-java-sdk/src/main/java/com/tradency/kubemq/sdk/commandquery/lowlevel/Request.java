package com.tradency.kubemq.sdk.commandquery.lowlevel;

import com.google.protobuf.ByteString;
import com.tradency.kubemq.sdk.commandquery.RequestType;
import com.tradency.kubemq.sdk.grpc.Kubemq;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class Request {
    private static AtomicInteger _id = new AtomicInteger(0);

    /**
     * Represents a Request identifier.
     */
    private String requestId;
    /**
     * Represents the type of request operation using com.tradency.kubemq.sdk.commandquery.RequestType.
     */
    private RequestType requestType;
    /**
     * Represents the sender ID that the Request will be send under.
     */
    private String clientId;
    /**
     * Represents The channel name to send to using the KubeMQ.
     */
    private String channel;
    /**
     * Represents The channel name to return response to
     */
    private String replyChannel;
    /**
     * Represents text as java.lang.String.
     */
    private String metadata;
    /**
     * Represents The content of the com.tradency.kubemq.sdk.requestreply.lowlevel.Request.
     */
    private byte[] body;
    /**
     * Represents the limit for waiting for response (Milliseconds).
     */
    private int timeout;
    /**
     * Represents if the request should be saved from Cache and under what "Key"(System.String) to save it.
     */
    private String cacheKey;
    /**
     * Cache time to live : for how long does the request should be saved in Cache
     */
    private int cacheTTL;

    /**
     * Initializes a new instance of the com.tradency.kubemq.sdk.requestreply.lowlevel.Request.
     */
    public Request() {
    }

    /**
     * Initializes a new instance of the com.tradency.kubemq.sdk.requestreply.lowlevel.Request with a set of parameters.
     *
     * @param requestId    Represents a Request identifier.
     * @param requestType  Represents the type of request operation using com.tradency.kubemq.sdk.commandquery.RequestType.
     * @param clientId     Represents the sender ID that the Request will be send under.
     * @param channel      Represents The channel name to send to using the KubeMQ.
     * @param replyChannel Represents The channel name to return response to.
     * @param metadata     Represents text as java.lang.String.
     * @param body         Represents The content of the com.tradency.kubemq.sdk.requestreply.lowlevel.Request.
     * @param timeout      Represents the limit for waiting for response (Milliseconds).
     * @param cacheKey     Represents if the request should be saved from Cache and under what "Key"(java.lang.String) to save it.
     * @param cacheTTL     Cache time to live : for how long does the request should be saved in Cache.
     */
    public Request(String requestId, RequestType requestType, String clientId, String channel, String replyChannel, String metadata, byte[] body, int timeout, String cacheKey, int cacheTTL) {
        this.requestId = requestId;
        this.requestType = requestType;
        this.clientId = clientId;
        this.channel = channel;
        this.replyChannel = replyChannel;
        this.metadata = metadata;
        this.body = body;
        this.timeout = timeout;
        this.cacheKey = cacheKey;
        this.cacheTTL = cacheTTL;
    }

    Request(Kubemq.Request innerRequest) {
        this.requestId = StringUtils.isEmpty(innerRequest.getRequestID())
                ? GetNextId().toString()
                : innerRequest.getRequestID();
        this.requestType = RequestType.values()[(innerRequest.getRequestTypeDataValue())];
        this.clientId = Optional.ofNullable(innerRequest.getClientID()).orElse("");
        this.channel = innerRequest.getChannel();
        this.metadata = Optional.ofNullable(innerRequest.getMetadata()).orElse("");
        this.body = innerRequest.getBody().toByteArray();
        this.replyChannel = innerRequest.getReplyChannel();
        this.timeout = innerRequest.getTimeout();
        this.cacheKey = Optional.ofNullable(innerRequest.getCacheKey()).orElse("");
        this.cacheTTL = innerRequest.getCacheTTL();
    }

    /**
     * Convert a Request to an Kubemq.Request
     *
     * @return An Kubemq.Request
     */
    Kubemq.Request Convert() {
        return Kubemq.Request
                .newBuilder()
                .setRequestID((StringUtils.isEmpty(requestId)) ? GetNextId().toString() : requestId)
                .setClientID(Optional.ofNullable(clientId).orElse(""))
                .setChannel(channel)
                // ReplyChannel - Set only by KubeMQ server
                .setMetadata(Optional.ofNullable(metadata).orElse(""))
                .setBody(ByteString.copyFrom(body))
                .setTimeout(timeout)
                .setCacheKey(Optional.ofNullable(cacheKey).orElse(""))
                .setCacheTTL(cacheTTL)
                .build();
    }

    /**
     * Get an unique thread safety ID between 1 to 32,767
     *
     * @return unique ID between 1 to 32,767
     */
    private Integer GetNextId() {
        return _id.updateAndGet(i -> i == Integer.MAX_VALUE ? 1 : i + 1);
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    private void setReplyChannel(String replyChannel) {
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
