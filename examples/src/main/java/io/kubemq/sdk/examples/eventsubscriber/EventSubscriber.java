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
package io.kubemq.sdk.examples.eventsubscriber;

import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.EventReceive;
import io.kubemq.sdk.event.Subscriber;
import io.kubemq.sdk.examples.commonexample.BaseExample;
import io.kubemq.sdk.subscription.EventsStoreType;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;

import javax.net.ssl.SSLException;
import java.text.MessageFormat;

class EventSubscriber extends BaseExample {

    private Subscriber subscriber;

    EventSubscriber() throws ServerAddressNotSuppliedException, SSLException {
        super("EventSubscriber");
        Subscriber subscriber = new Subscriber();
        SubcribeToEventsWithoutStore();
        SubcribeToEventsWithStore();

    }

    private void SubcribeToEventsWithStore() throws ServerAddressNotSuppliedException, SSLException {
        subscriber = new Subscriber();
        SubscribeRequest subscribeRequest = CreateSubscribeRequest(SubscribeType.EventsStore, EventsStoreType.StartAtSequence, 2);
        EventReceive eventReceive = subscriber.SubscribeToEvents(subscribeRequest);
        HandleIncomingEvents(eventReceive);
    }

    private void SubcribeToEventsWithoutStore() throws ServerAddressNotSuppliedException, SSLException {
        subscriber = new Subscriber();
        SubscribeRequest subscribeRequest = CreateSubscribeRequest(SubscribeType.Events);
        EventReceive eventReceive = subscriber.SubscribeToEvents(subscribeRequest);
        HandleIncomingEvents(eventReceive);
    }

    private void HandleIncomingEvents(EventReceive message) {
        String body = new String(message.getBody());

        System.out.println(MessageFormat.format(
                "Subscriber Received Event: Metadata:''{0}'', Channel:''{1}'', Body:''{2}''",
                message.getMetadata(),
                message.getChannel(),
                body
        ));
    }
}
