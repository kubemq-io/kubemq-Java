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
package io.kubemq.sdk.examples.commandqueryresponder;

import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.Responder;
import io.kubemq.sdk.commandquery.Response;
import io.kubemq.sdk.examples.commonexample.BaseExample;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;

import javax.net.ssl.SSLException;
import java.text.MessageFormat;

class CommandQueryResponder extends BaseExample {

    private Responder.RequestResponseObserver HandleIncomingRequests;
    private Responder responder;

    CommandQueryResponder() throws ServerAddressNotSuppliedException, SSLException {
        super("CommandQueryResponder");
        responder = new Responder();
        // todo fix example - reimplement as in the C#
        HandleIncomingRequests = requestReceive -> {
            System.out.println(MessageFormat.format(
                    "Subscriber Received Event: Metadata:''{0}'', Channel:''{1}''",
                    requestReceive.getMetadata(),
                    requestReceive.getChannel()
            ));

            Response response = new Response(requestReceive);
            response.setBody("OK".getBytes());
            response.setMetadata("Received Request");
            response.setClientID("Response Return");
            return response;
        };
        CreateSubscribeToQueries();
        CreateSubscribeToCommands();
    }

    private void CreateSubscribeToCommands() throws ServerAddressNotSuppliedException, SSLException {
        SubscribeRequest subscribeRequest = CreateSubscribeRequest(SubscribeType.Queries);
        responder.SubscribeToRequests(subscribeRequest, HandleIncomingRequests);
    }

    private void CreateSubscribeToQueries() throws ServerAddressNotSuppliedException, SSLException {
        SubscribeRequest subscribeRequest = CreateSubscribeRequest(SubscribeType.Commands);
        responder.SubscribeToRequests(subscribeRequest, HandleIncomingRequests);
    }

}
