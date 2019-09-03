/*
 * MIT License
 *
 * Copyright (c) 2018 KubeMQ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.kubemq.sdk.commandquery;

import com.google.protobuf.ByteString;
import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.tools.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
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

	private Map<String, String> tags;

    public Response(RequestReceive request) {
        requestID = request.getRequestId();
        replyChannel = request.getReplyChannel();
        setTimestamp(LocalDateTime.now(ZoneOffset.UTC));
        tags= request.getTags();
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
        tags= inner.getTagsMap();
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
                .putAllTags(Optional.ofNullable(tags).orElse(new HashMap<String,String>()))
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

    public Map<String,String> getTags(){
        return this.tags;
    }

}
