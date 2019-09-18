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
package io.kubemq.sdk.examples.get_Started.rPC_Send_a_Command_Channel;

import java.io.IOException;

import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.ChannelParameters;
import io.kubemq.sdk.commandquery.Request;
import io.kubemq.sdk.commandquery.RequestType;
import io.kubemq.sdk.commandquery.Response;
import io.kubemq.sdk.tools.Converter;

public class Program {

    public static void main(String[] args) throws IOException {

        String ChannelName = "testing_Command_channel", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";
        ChannelParameters channelParameters = new ChannelParameters();
        channelParameters.setChannelName(ChannelName);
        channelParameters.setClientID(ClientID);
        channelParameters.setKubeMQAddress(KubeMQServerAddress);
        channelParameters.setRequestType(RequestType.Command);
        channelParameters.setTimeout(10000);
        io.kubemq.sdk.commandquery.Channel channel = new io.kubemq.sdk.commandquery.Channel(channelParameters);
        Request request = new Request();
        request.setBody(Converter.ToByteArray("hello kubemq - sending a command, please reply"));
        Response result;
        try {
            result = channel.SendRequest(request);
            if (!result.isExecuted()) {
                System.out.printf("Response error: %s", result.getError());
                return;
            }
            System.out.printf("Response Received: %s, ExecutedAt: %s", result.getRequestID(), result.getTimestamp().toString());
        } catch (ServerAddressNotSuppliedException e) {
            System.out.printf("ServerAddressNotSuppliedException: %s", e.toString());
            e.printStackTrace();
		}
        
    
    }
}
