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
package io.kubemq.sdk.event.lowlevel;

import com.google.protobuf.ByteString;
import io.kubemq.sdk.grpc.Kubemq;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents the Event used in pubsub to send information using the KubeMQ
 */
public class Event {

    private static AtomicInteger _id = new AtomicInteger(0);
    private boolean returnResult;
    /**
     * Represents The channel name to send to using the KubeMQ
     */
    private String channel;
    /**
     * Represents text as System.String
     */
    private String metadata;
    /**
     * Represents The content of the Event
     */
    private byte[] body;
    /**
     * Represents a Event identifier
     */
    private String eventId;
    /**
     * Represents the sender ID that the messages will be send under
     */
    private String clientID;
    /**
     * Represents if the messages should be send to persistence
     */
    private boolean store;

    /**
     * Initializes a new instance of the Event
     */
    public Event() {
    }

    /**
     * Initializes a new instance of the Event defined by a set of parameters
     *
     * @param channel  Represents The channel name to send to using the KubeMQ
     * @param metadata Represents text as System.String
     * @param body     Represents The content of the Event
     * @param eventId  Represents a Event identifier
     * @param clientID Represents the sender ID that the messages will be send under
     * @param store    Represents if the messages should be send to persistence
     */
    public Event(String channel, String metadata, byte[] body, String eventId, String clientID, boolean store) {
        this.channel = channel;
        this.metadata = metadata;
        this.body = body;
        this.eventId = eventId;
        this.clientID = clientID;
        this.store = store;
    }

    Event(Kubemq.Event event) {
        channel = event.getChannel();
        metadata = event.getMetadata();
        body = event.getBody().toByteArray();
        eventId = StringUtils.isEmpty(event.getEventID())
                ? GetNextId().toString()
                : event.getEventID();
        clientID = event.getClientID();
        store = event.getStore();
    }

    Kubemq.Event ToInnerEvent() {
        return Kubemq.Event.newBuilder()
                .setChannel(channel)
                .setMetadata(Optional.ofNullable(metadata).orElse(""))
                .setBody(ByteString.copyFrom(body))
                .setEventID(StringUtils.isEmpty(eventId)
                        ? GetNextId().toString()
                        : eventId)
                .setClientID(clientID)
                .setStore(store)
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

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

}
