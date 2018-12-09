package io.kubemq.sdk.examples.eventchannel;

import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.Channel;
import io.kubemq.sdk.event.ChannelParameters;
import io.kubemq.sdk.event.Event;
import io.kubemq.sdk.examples.commonexample.BaseExample;

import javax.net.ssl.SSLException;

class EventChannel extends BaseExample {

    EventChannel() throws ServerAddressNotSuppliedException, SSLException {
        super("EventChannel");
        ChannelParameters channelParameters = CreateMessageChannelParam();
        Channel channel = new Channel(channelParameters);
        channel.SendEvent(CreateChannelMessage());

    }

    private Event CreateChannelMessage() {
        Event event = new Event();
        event.setMetadata("EventChannel");
        event.setBody("Event".getBytes());
        return event;
    }

    private ChannelParameters CreateMessageChannelParam() {
        ChannelParameters parameters = new ChannelParameters();
        parameters.setChannelName(this.getChannelName());
        parameters.setClientID("EventChannelID");
        parameters.setStore(true);
        parameters.setReturnResult(false);
        return parameters;
    }

}
