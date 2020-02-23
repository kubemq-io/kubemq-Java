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
package io.kubemq.sdk.examples.get_Started.rPC_Subscribe_to_a_Channel;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.naming.AuthenticationException;
import javax.net.ssl.SSLException;

import io.kubemq.sdk.Exceptions.AuthorizationException;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.Responder;
import io.kubemq.sdk.commandquery.Response;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;

public class Program {

    public static void main(String[] args) throws IOException {
        String ChannelName = "testing_Command_channel", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";
        Responder.RequestResponseObserver HandleIncomingRequests;
        Responder responder = new Responder(KubeMQServerAddress);
        HandleIncomingRequests = request -> {

            Response response = new Response(request);
            response.setCacheHit(false);
            response.setError("None");
            response.setClientID(ClientID);
            response.setBody("OK".getBytes());
            response.setExecuted(true);
            response.setMetadata("OK");
            response.setTimestamp(LocalDateTime.now());
            return response;
        };
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setChannel(ChannelName);
        subscribeRequest.setClientID(ClientID);
        subscribeRequest.setSubscribeType(SubscribeType.Commands);

        new Thread() {
            public void run() {

                try {
                    responder.SubscribeToRequests(subscribeRequest, HandleIncomingRequests);
                } catch (SSLException | AuthorizationException | ServerAddressNotSuppliedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                  
            }
        }.start();
    }
}
