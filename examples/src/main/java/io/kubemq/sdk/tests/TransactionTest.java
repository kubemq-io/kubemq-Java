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

package io.kubemq.sdk.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.net.ssl.SSLException;

import org.junit.Test;

import io.kubemq.sdk.Queue.Message;
import io.kubemq.sdk.Queue.Queue;
import io.kubemq.sdk.Queue.ReceiveMessagesResponse;
import io.kubemq.sdk.Queue.Transaction;
import io.kubemq.sdk.Queue.TransactionMessagesResponse;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.tools.Converter;

public class TransactionTest {

 //  @Test
    public void Recive_NoMessages_Test() throws ServerAddressNotSuppliedException, IOException {
        Queue queue = createQueue("rec");
        ackQueue(queue);
        Transaction tran = queue.CreateTransaction();
        TransactionMessagesResponse resp = tran.Receive(10, 1);

        assertEquals(true, (resp.getIsError()));
        assertEquals(true, resp.getError().contains("Error 138"));

    }

 //  @Test
    public void Recive_1Messages_Ack_Test() throws ServerAddressNotSuppliedException, IOException {
        Queue queue = createQueue("1Messages");
        ackQueue(queue);
        queue.SendQueueMessage(new Message(Converter.ToByteArray("hi"), "", "1", null));
        Transaction tran = queue.CreateTransaction();
        TransactionMessagesResponse resp = tran.Receive(10, 1);

        assertEquals("1", resp.getMessage().getMessageID());

        resp = tran.AckMessage();

        assertFalse(resp.getIsError());
    }

  //  @Test
    public void Recive_VisabilityExpire_Test() throws ServerAddressNotSuppliedException, IOException, InterruptedException {
        Queue queue = createQueue("Visability");
        ackQueue(queue);
        queue.SendQueueMessage(new Message(Converter.ToByteArray("hi"), "", "1", null));
        Transaction tran = queue.CreateTransaction();
        TransactionMessagesResponse resp = tran.Receive(2, 1);

        assertEquals("1", resp.getMessage().getMessageID());
        Thread.sleep(2000);  
        resp = tran.AckMessage();
        assertTrue(resp.getIsError());
    }

   // @Test
    public void Recive_VisabilityExtend_Test() throws ServerAddressNotSuppliedException, IOException, InterruptedException {
        Queue queue = createQueue("VisabilityExt");
        ackQueue(queue);
        queue.SendQueueMessage(new Message(Converter.ToByteArray("hi"), "", "1", null));
        Transaction tran = queue.CreateTransaction();
        TransactionMessagesResponse resp = tran.Receive(1, 1);
        assertEquals("1", resp.getMessage().getMessageID());
        resp = tran.ExtendVisibility(2);
        assertFalse(resp.getIsError());
        Thread.sleep(1000);
        resp = tran.AckMessage();
        assertFalse(resp.getIsError());
    }

    @Test
    public void Recive_Resend_Test() throws ServerAddressNotSuppliedException, IOException, InterruptedException {
        Queue queue = createQueue("Resend");
        ackQueue(queue);
        queue.SendQueueMessage(new Message(Converter.ToByteArray("hi"), "", "1", null));
        Transaction tran = queue.CreateTransaction();
        TransactionMessagesResponse resp = tran.Receive(3, 1);
        assertEquals("1", resp.getMessage().getMessageID());
        resp = tran.ReSend("Resend2");
        assertFalse(resp.getIsError());
      
        Queue  queue2 = createQueue("Resend2");
         
        ReceiveMessagesResponse respe = queue2.PeekQueueMessage(1, 1);
         assertEquals(1, respe.getMessagesReceived());

    }

    private void ackQueue(Queue queue) throws SSLException, ServerAddressNotSuppliedException {
        queue.AckAllQueueMessages();
    }

    private Queue createQueue(String queueName) throws SSLException, ServerAddressNotSuppliedException {
        if (queueName == null) {
            queueName = "testQueue";
        }
        return new Queue(queueName, "JavaTester", 1, 1, "localhost:50000");

    }
}