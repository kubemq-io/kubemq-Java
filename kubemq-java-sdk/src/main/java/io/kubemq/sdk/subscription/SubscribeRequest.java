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
package io.kubemq.sdk.subscription;

import io.kubemq.sdk.grpc.Kubemq;

import java.util.Optional;

/**
 * Represents a set of parameters which the Subscriber uses to subscribe to the KubeMQ.
 */
public class SubscribeRequest {

    /**
     * Represents the type of Subscriber operation.
     */
    private SubscribeType subscribeType;

    /**
     * Represents an identifier that will subscribe to kubeMQ under.
     */
    private String clientID;

    /**
     * Represents the channel name that will subscribe to under kubeMQ.
     */
    private String channel;

    /**
     * Represents the group the channel is assign to.
     */
    private String group;

    /**
     * Represents the type of subscription to persistence using EventsStoreType.
     */
    private EventsStoreType eventsStoreType;

    /**
     * Represents the value of subscription to persistence queue.
     */
    private long eventsStoreTypeValue;

    /**
     * Initializes a new instance of SubscribeRequest.
     */
    public SubscribeRequest() {
    }

    /**
     * Initializes a new instance of the SubscribeRequest using a set of parameters
     *
     * @param subscribeType        Represents the type of Subscriber operation.
     * @param clientID             Represents an identifier that will subscribe to kubeMQ under
     * @param channel              Represents the channel name that will subscribe to under kubeMQ
     * @param group                Represents the group the channel is assign to
     * @param eventsStoreType      Represents the type of subscription to persistence using EventsStoreType
     * @param eventsStoreTypeValue Represents the value of subscription to persistence queue
     */
    public SubscribeRequest(SubscribeType subscribeType, String clientID, String channel, String group, EventsStoreType eventsStoreType, long eventsStoreTypeValue) {
        this.subscribeType = subscribeType;
        this.clientID = clientID;
        this.channel = channel;
        this.group = group;
        this.eventsStoreType = eventsStoreType;
        this.eventsStoreTypeValue = eventsStoreTypeValue;
    }

    public SubscribeRequest(Kubemq.Subscribe inner) {
        subscribeType = SubscribeType.values()[inner.getSubscribeTypeDataValue()];
        clientID = inner.getClientID();
        channel = inner.getChannel();
        group = Optional.ofNullable(inner.getGroup()).orElse("");
        eventsStoreTypeValue = inner.getEventsStoreTypeValue();
    }

    public Kubemq.Subscribe ToInnerSubscribeRequest() {
        return Kubemq.Subscribe.newBuilder()
                .setSubscribeTypeData(Kubemq.Subscribe.SubscribeType.forNumber(subscribeType.getValue()))
                .setClientID(clientID)
                .setChannel(channel)
                .setGroup(Optional.ofNullable(group).orElse(""))
                .setEventsStoreTypeData(Kubemq.Subscribe.EventsStoreType.forNumber(eventsStoreType ==null ?  0: eventsStoreType.getValue()))
                .setEventsStoreTypeValue(eventsStoreTypeValue)
                .build();
    }

    public boolean IsNotValidType(String subscriber) {
        if (subscriber.equals("CommandQuery")) {
            return (subscribeType != SubscribeType.Commands && subscribeType != SubscribeType.Queries);
        } else { // (subscriber == "Events")
            return (subscribeType != SubscribeType.Events && subscribeType != SubscribeType.EventsStore);
        }

    }

    public SubscribeType getSubscribeType() {
        return subscribeType;
    }

    public void setSubscribeType(SubscribeType subscribeType) {
        this.subscribeType = subscribeType;
    }

    /**
     * The unique client identifier that will subscribe to kubeMQ under
     *
     * @return String
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * clientID is a unique identifier that client will subscribe to kubeMQ under
     *
     * @param clientID must be unique among clients
     */
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public EventsStoreType getEventsStoreType() {
        return eventsStoreType;
    }

    public void setEventsStoreType(EventsStoreType eventsStoreType) {
        this.eventsStoreType = eventsStoreType;
    }

    public long getEventsStoreTypeValue() {
        return eventsStoreTypeValue;
    }

    public void setEventsStoreTypeValue(long eventsStoreTypeValue) {
        this.eventsStoreTypeValue = eventsStoreTypeValue;
    }

}
