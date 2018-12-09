package io.kubemq.sdk.commandquery;

import com.google.protobuf.ByteString;
import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.tools.Converter;

import java.time.LocalDateTime;
import java.util.Optional;

public class Response {

    /**
     * Represents the sender ID that the Response will be send under
     */
    private String clientID;

    /**
     * Represents a Response identifier
     */
    private String requestID;

    /**
     * Represents text as System.String
     */
    private String metadata;
    /**
     * Represents The content of the Response
     */
    private byte[] body;
    /**
     * Represents if the response was received from Cache
     */
    private boolean cacheHit;
    /**
     * Represents if the response Time.
     */
    private LocalDateTime timestamp;
    /**
     * Represents if the response was executed.
     */
    private boolean executed;
    /**
     * Error message
     */
    private String error;

    /**
     * Channel name for the Response.
     * Set and used internally by KubeMQ server
     */
    private String replyChannel;

    public Response(RequestReceive request) {
        requestID = request.getRequestId();
        replyChannel = request.getReplyChannel();
    }

    public Response(Kubemq.Response inner) {
        clientID = Optional.ofNullable(inner.getClientID()).orElse("");
        requestID = inner.getRequestID();
        replyChannel = inner.getReplyChannel();
        metadata = Optional.ofNullable(inner.getMetadata()).orElse("");
        body = inner.getBody().toByteArray();
        cacheHit = inner.getCacheHit();
        timestamp = Converter.FromUnixTime(inner.getTimestamp());
        executed = inner.getExecuted();
        error = inner.getError();
    }

    Kubemq.Response Convert() {
        return Kubemq.Response.newBuilder()
                .setClientID(Optional.ofNullable(clientID).orElse(""))
                .setRequestID(requestID)
                .setReplyChannel(replyChannel)
                .setMetadata(Optional.ofNullable(metadata).orElse(""))
                .setBody(ByteString.copyFrom(body))
                .setCacheHit(cacheHit)
                .setTimestamp(Converter.ToUnixTime(timestamp))
                .setExecuted(executed)
                .setError(Optional.ofNullable(error).orElse(""))
                .build();
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
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

    public boolean isCacheHit() {
        return cacheHit;
    }

    public void setCacheHit(boolean cacheHit) {
        this.cacheHit = cacheHit;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
