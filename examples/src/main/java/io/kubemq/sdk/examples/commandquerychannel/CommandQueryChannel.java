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
