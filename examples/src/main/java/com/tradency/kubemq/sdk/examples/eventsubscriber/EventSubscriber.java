package com.tradency.kubemq.sdk.examples.eventsubscriber;

import com.tradency.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import com.tradency.kubemq.sdk.event.EventReceive;
import com.tradency.kubemq.sdk.event.Subscriber;
import com.tradency.kubemq.sdk.examples.commonexample.BaseExample;
import com.tradency.kubemq.sdk.subscription.EventsStoreType;
import com.tradency.kubemq.sdk.subscription.SubscribeRequest;
import com.tradency.kubemq.sdk.subscription.SubscribeType;

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
