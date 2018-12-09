package com.tradency.kubemq.sdk.commandquery;

/**
 * Represents the Request used in requestreply com.tradency.kubemq.sdk.requestreply.channel
 */
public class Request {

    /**
     * Represents a Request identifier
     */
    private String requestId;

    /**
     * Represents text as String
     */
    private String metadata;

    /**
     * Represents The content of the com.tradency.kubemq.sdk.commandquery.Request
     */
    private byte[] body;

    /**
     * Initializes a new instance of the com.tradency.kubemq.sdk.commandquery.Request
     * for com.tradency.kubemq.sdk.requestreply.channel use
     */
    public Request() {

    }

    /**
     * Initializes a new instance of the com.tradency.kubemq.sdk.commandquery.Request
     * for com.tradency.kubemq.sdk.requestreply.channel use with a set of parameters
     *
     * @param requestId Represents a Request identifier
     * @param metadata  Represents text as String
     * @param body      Represents The content of the com.tradency.kubemq.sdk.commandquery.Request
     */
    public Request(String requestId, String metadata, byte[] body) {
        setRequestId(requestId);
        setMetadata(metadata);
        setBody(body);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
