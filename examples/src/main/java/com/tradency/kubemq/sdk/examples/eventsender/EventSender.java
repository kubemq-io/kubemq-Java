package com.tradency.kubemq.sdk.examples.eventsender;

import com.tradency.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import com.tradency.kubemq.sdk.event.lowlevel.Event;
import com.tradency.kubemq.sdk.event.lowlevel.Sender;
import com.tradency.kubemq.sdk.examples.commonexample.BaseExample;

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
