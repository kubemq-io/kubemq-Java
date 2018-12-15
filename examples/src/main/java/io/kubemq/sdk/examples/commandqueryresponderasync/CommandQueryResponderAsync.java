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
package io.kubemq.sdk.examples.commandqueryresponderasync;

import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.RequestReceive;
import io.kubemq.sdk.commandquery.Responder;
import io.kubemq.sdk.commandquery.Response;
import io.kubemq.sdk.examples.commonexample.BaseExample;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;
import org.slf4j.Logger;

import javax.net.ssl.SSLException;
import java.text.MessageFormat;
import java.time.LocalDateTime;

class CommandQueryResponderAsync extends BaseExample {

    private Responder.RequestResponseAsyncObserver HandleIncomingRequests;
    private Responder responder;

    CommandQueryResponderAsync() throws ServerAddressNotSuppliedException, SSLException {
        super("CommandQueryResponder");
        String clientId = this.getClientID();
        Logger log = this.logger;
        responder = new Responder();
        HandleIncomingRequests = new Responder.RequestResponseAsyncObserver() {
            @Override
            public Response onNext(RequestReceive request) {
                log.warn(MessageFormat.format(
                        "Subscriber Received Event: Metadata:''{0}'', Channel:''{1}''",
                        request.getMetadata(),
                        request.getChannel()
                ));

                Response response = new Response(request);
                response.setCacheHit(false);
                response.setError("None");
                response.setClientID(clientId);
                response.setBody("OK".getBytes());
                response.setExecuted(true);
                response.setMetadata("OK");
                response.setTimestamp(LocalDateTime.now());
                return response;
            }

            @Override
            public void onError(Throwable t) {
                log.warn(MessageFormat.format("Error: {1}", t.getMessage()));
            }

            @Override
            public void onCompleted() {
                log.info("Subscriber subscription completed.");
            }
        };
        CreateSubscribeToQueries();
        CreateSubscribeToCommands();
    }

    private void CreateSubscribeToCommands() throws ServerAddressNotSuppliedException, SSLException {
        SubscribeRequest subscribeRequest = CreateSubscribeRequest(SubscribeType.Queries);
        responder.SubscribeToRequestsAsync(subscribeRequest, HandleIncomingRequests);
    }

    private void CreateSubscribeToQueries() throws ServerAddressNotSuppliedException, SSLException {
        SubscribeRequest subscribeRequest = CreateSubscribeRequest(SubscribeType.Commands);
        responder.SubscribeToRequestsAsync(subscribeRequest, HandleIncomingRequests);
    }

}
