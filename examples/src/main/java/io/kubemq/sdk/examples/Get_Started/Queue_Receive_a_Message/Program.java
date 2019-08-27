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
package io.kubemq.sdk.examples.Get_Started.Queue_Receive_a_Message;

import io.kubemq.sdk.Queue.Message;
import io.kubemq.sdk.Queue.Queue;
import io.kubemq.sdk.Queue.ReceiveMessagesResponse;
import io.kubemq.sdk.Queue.SendMessageResult;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.tools.Converter;

import javax.net.ssl.SSLException;
import java.io.IOException;

public class Program {

    public static void main(String[] args) throws ServerAddressNotSuppliedException {
        
        
        String queueName = "hello-world-queue";
        String clientID = "test-queue-client-id2";
        String kubeMQServerAddress = "localhost:50000";


        Queue queue = null;
        try{
            queue = new io.kubemq.sdk.Queue.Queue(queueName,clientID,1,2,kubeMQServerAddress);
        } catch (ServerAddressNotSuppliedException e) {
            System.out.println("Error: Can not determine KubeMQ server address.");
        } catch (io.grpc.StatusRuntimeException e) {
            System.out.println("Error: KubeMQ is unreachable.");
        } catch (SSLException e) {
            System.out.println("Error: error detected by an SSL subsystem");
        }

        try {
            ReceiveMessagesResponse res=  queue.ReceiveQueueMessages(2,null);            
          if(res.getIsError()  )       {
            System.out.println("message enqueue error, error:{res.Error}");
          }
          
          System.out.println("Received {msg.MessagesReceived} Messages:");

          for (Message msg : res.getMessages()) {              
        
            System.out.println("MessageID: {item.MessageID}, Body:{KubeMQ.SDK.csharp.Tools.Converter.FromByteArray(item.Body)}");
          }
          


        } catch (IOException e) {
            System.out.println("Error:  I/O error occurred.");
        }
    }
}
