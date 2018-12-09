package io.kubemq.sdk.examples.commandquerychannel;

import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.*;
import io.kubemq.sdk.examples.commonexample.BaseExample;

import javax.net.ssl.SSLException;

class CommandQueryChannel extends BaseExample {

    private Channel requestChannel;
    private ChannelParameters requestChannelParameters;

    CommandQueryChannel() throws ServerAddressNotSuppliedException, SSLException {
        super("CommandQueryChannel");
        SendQueryRequest();
        SendCommandRequest();
    }

    private void SendQueryRequest() throws ServerAddressNotSuppliedException, SSLException {
        requestChannelParameters = CreateRequestChannelParam(RequestType.Query);
        requestChannel = new Channel(requestChannelParameters);
        Response request = requestChannel.SendRequest(CreateChannelRequest());
    }

    private void SendCommandRequest() throws ServerAddressNotSuppliedException, SSLException {
        requestChannelParameters = CreateRequestChannelParam(RequestType.Command);
        requestChannel = new Channel(requestChannelParameters);
        requestChannel.SendRequest(CreateChannelRequest());
    }

    private Request CreateChannelRequest() {
        Request request = new Request();
        request.setMetadata("CommandQueryChannel");
        request.setBody("Request".getBytes());
        return request;
    }

    private ChannelParameters CreateRequestChannelParam(RequestType requestType) {
        ChannelParameters parameters = new ChannelParameters();
        parameters.setChannelName(getChannelName());
        parameters.setClientID(getClientID());
        parameters.setTimeout(getTimeout());
        parameters.setCacheKey("");
        parameters.setCacheTTL(0);
        parameters.setRequestType(requestType);
        return parameters;
    }
}
