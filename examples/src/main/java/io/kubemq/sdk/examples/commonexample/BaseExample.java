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
package io.kubemq.sdk.examples.commonexample;

import io.kubemq.sdk.commandquery.RequestType;
import io.kubemq.sdk.commandquery.lowlevel.Request;
import io.kubemq.sdk.event.lowlevel.Event;
import io.kubemq.sdk.subscription.EventsStoreType;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Random;

public class BaseExample {

    private Logger logger;
    private String channelName;
    private String clientID;
    private int timeout;

    public BaseExample(String _ClientId) {
        clientID = _ClientId;
        timeout = 111000;
        channelName = "MyTestChannelName";
        logger = LoggerFactory.getLogger(BaseExample.class);
    }

    private Event CreateNewEvent() {
        logger.debug("Start Creating Event");
        Event message = new Event();
        message.setMetadata("MessageMetaData");
        message.setBody(MessageFormat.format("Event Created on time {0}", Instant.now()).getBytes());
        return message;
    }

    protected Event CreateLowLevelEventWithoutStore() {
        Event message = CreateNewEvent();
        message.setStore(false);
        message.setChannel(channelName);
        message.setClientID(clientID);
        message.setReturnResult(false);
        return message;
    }

    protected SubscribeRequest CreateSubscribeRequest(
            SubscribeType subscriptionType,
            EventsStoreType eventsStoreType,
            int TypeValue,
            String group
    ) {
        SubscribeRequest subscribeRequest = new SubscribeRequest();

        subscribeRequest.setChannel(channelName);
        subscribeRequest.setClientID(generateRandomClientID());
        subscribeRequest.setEventsStoreType(eventsStoreType);
        subscribeRequest.setEventsStoreTypeValue(TypeValue);
        subscribeRequest.setGroup(group);
        subscribeRequest.setSubscribeType(subscriptionType);

        return subscribeRequest;
    }

    protected SubscribeRequest CreateSubscribeRequest(SubscribeType subscriptionType) {
        return CreateSubscribeRequest(subscriptionType, EventsStoreType.Undefined, 0, "");
    }

    protected SubscribeRequest CreateSubscribeRequest(SubscribeType subscriptionType, EventsStoreType eventsStoreType, int TypeValue) {
        return CreateSubscribeRequest(subscriptionType, eventsStoreType, TypeValue, "");
    }

    protected Request CreateLowLevelRequest(RequestType requestType) {
        Request request = new Request();

        request.setBody("Request".getBytes());
        request.setMetadata("MyMetaData");
        request.setCacheKey("");
        request.setCacheTTL(0);
        request.setChannel(channelName);
        request.setClientId(clientID);
        request.setTimeout(timeout);
        request.setRequestType(requestType);

        return request;
    }

    private String generateRandomClientID() {
        Random random = new Random();
        int low = 9;
        int high = 19999;
        return Integer.toString(random.nextInt(high - low) + low);
    }

    protected int getTimeout() {
        return timeout;
    }

    protected void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    protected String getChannelName() {
        return channelName;
    }

    protected void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    protected String getClientID() {
        return clientID;
    }

    protected void setClientID(String clientID) {
        this.clientID = clientID;
    }

}
