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
package io.kubemq.sdk.examples.get_Started.pubSub_Publish_to_a_Channel;

import java.io.IOException;

import javax.net.ssl.SSLException;

import io.kubemq.sdk.Exceptions.AuthorizationException;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.Event;
import io.kubemq.sdk.event.Result;
import io.kubemq.sdk.tools.Converter;

public class Program {

    public static void main(String[] args) {

        String channelName = "testing_event_channel", clientID = "hello-world-subscriber",
                kubeMQAddress = "localhost:50000";

        io.kubemq.sdk.event.Channel chan = new io.kubemq.sdk.event.Channel(channelName, clientID, false, kubeMQAddress);

        Event event = new Event();
        try {
            event.setBody(Converter.ToByteArray("hello kubemq - sending single event"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            Result res = chan.SendEvent(event);
        } catch (SSLException | AuthorizationException | ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
       

    }
}
