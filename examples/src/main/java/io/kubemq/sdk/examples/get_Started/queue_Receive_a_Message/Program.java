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
package io.kubemq.sdk.examples.get_Started.queue_Receive_a_Message;

import java.io.IOException;

import javax.net.ssl.SSLException;

import io.kubemq.sdk.queue.Message;
import io.kubemq.sdk.queue.Queue;
import io.kubemq.sdk.queue.ReceiveMessagesResponse;
import io.kubemq.sdk.Exceptions.AuthorizationException;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.tools.Converter;

public class Program {

    public static void main(String[] args) throws ServerAddressNotSuppliedException, ClassNotFoundException {
        
        
        String queueName = "hello-world-queue", clientID = "test-queue-client-id2", kubeMQServerAddress = "localhost:50000";


        Queue queue = null;
        try{
            queue = new io.kubemq.sdk.queue.Queue(queueName,clientID,kubeMQServerAddress);
            queue.setMaxNumberOfMessagesQueueMessages(2);
            queue.setWaitTimeSecondsQueueMessages(1);
            
        } catch (ServerAddressNotSuppliedException e) {
            System.out.println("Error: Can not determine KubeMQ server address.");
        } catch (AuthorizationException e) {
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
        
            System.out.printf("MessageID:%s, Body:%s",msg.getMessageID(), Converter.FromByteArray(msg.getBody()));
          }
          


        } catch (IOException e) {
            System.out.println("Error:  I/O error occurred.");
        }
    }
}
