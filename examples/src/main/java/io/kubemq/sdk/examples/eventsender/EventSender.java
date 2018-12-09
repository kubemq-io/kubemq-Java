package io.kubemq.sdk.examples.eventsender;

import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.lowlevel.Event;
import io.kubemq.sdk.event.lowlevel.Sender;
import io.kubemq.sdk.examples.commonexample.BaseExample;

import javax.net.ssl.SSLException;

class EventSender extends BaseExample {

    EventSender() throws ServerAddressNotSuppliedException, SSLException {
        super("EventSender");
        SendLowLevelMessages();
    }

    private void SendLowLevelMessages() throws ServerAddressNotSuppliedException, SSLException {
        Sender sender = new Sender();
        Event event = CreateLowLevelEventWithoutStore();
        sender.SendEvent(event);
    }
}
