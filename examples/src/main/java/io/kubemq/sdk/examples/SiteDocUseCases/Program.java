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
package io.kubemq.sdk.examples.SiteDocUseCases;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.net.ssl.SSLException;

import io.kubemq.sdk.Queue.AckAllMessagesResponse;
import io.kubemq.sdk.Queue.Message;
import io.kubemq.sdk.Queue.Queue;
import io.kubemq.sdk.Queue.ReceiveMessagesResponse;
import io.kubemq.sdk.Queue.SendBatchMessageResult;
import io.kubemq.sdk.Queue.SendMessageResult;
import io.kubemq.sdk.Queue.Transaction;
import io.kubemq.sdk.Queue.TransactionMessagesResponse;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.tools.Converter;

public class Program {

    public static void main(String[] args) throws ServerAddressNotSuppliedException, IOException, ClassNotFoundException, InterruptedException {
        
        // Ack_All_Messages_In_a_Queue();
        Send_Message_to_a_Queue();
        // Send_Message_to_a_Queue_with_Expiration();
        // Send_Message_to_a_Queue_with_Delay();
        // Send_Message_to_a_Queue_with_Deadletter_Queue();
        // Send_Batch_Messages();
        // Receive_Messages_from_a_Queue();
        // Peek_Messages_from_a_Queue();
      //  Transactional_Queue_Ack();
      //  Transactional_Queue_Reject();
        //Transactional_Queue_Extend_Visibility();
        Transactional_Queue_Resend_to_New_Queue();
        // Transactional_Queue_Resend_Modified_Message();

    }

    private static void Transactional_Queue_Reject() throws ServerAddressNotSuppliedException, ClassNotFoundException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Transaction tran = queue.CreateTransaction();
       TransactionMessagesResponse resRec= tran.Receive(10, 10);
       if (resRec.getIsError()){
           System.out.printf("Message dequeue error, error:%s",resRec.getError());
            return;  
       }
       System.out.printf("MessageID: %d, Body:%s",resRec.getMessage().getMessageID(),Converter.FromByteArray(resRec.getMessage().getBody()));   
       System.out.println("Reject message");
       TransactionMessagesResponse resRej= tran.RejectMessage();
       if (resRej.getIsError()){
        System.out.printf("Message dequeue error, error:%s",resRej.getError());
         return;  
    }
   
    }

    private static void Transactional_Queue_Resend_Modified_Message() {

    }

    private static void Transactional_Queue_Resend_to_New_Queue() {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Transaction tran = queue.CreateTransaction();
       TransactionMessagesResponse resRec= tran.Receive(5, 10);
       if (resRec.getIsError()){
           System.out.printf("Message dequeue error, error:%s",resRec.getError());
            return;  
       }
       System.out.printf("MessageID: %d, Body:%s",resRec.getMessage().getMessageID(),Converter.FromByteArray(resRec.getMessage().getBody()));   

        
        System.out.println("Resend to new queue");
        TransactionMessagesResponse resResend= tran.ReSend("new-queue");
        if (resResend.getIsError()){
         System.out.printf("Message dequeue error, error:%s",resResend.getError());
          return;  }
          System.out.println("Done");
     
        }

    private static void Transactional_Queue_Extend_Visibility()
            throws ServerAddressNotSuppliedException, ClassNotFoundException, IOException, InterruptedException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Transaction tran = queue.CreateTransaction();
       TransactionMessagesResponse resRec= tran.Receive(5, 10);
       if (resRec.getIsError()){
           System.out.printf("Message dequeue error, error:%s",resRec.getError());
            return;  
       }
       System.out.printf("MessageID: %d, Body:%s",resRec.getMessage().getMessageID(),Converter.FromByteArray(resRec.getMessage().getBody()));   
       System.out.println("work for 1 seconds");
       Thread.sleep(1000);
       System.out.println("Need more time to process, extend visibility for more 3 seconds");
       TransactionMessagesResponse resExt = tran.ExtendVisibility(3);
       if (resExt.getIsError()){
        System.out.printf("Message dequeue error, error:%s",resExt.getError());
         return;  
    }
    System.out.println("Approved. work for 2.5 seconds");   
    Thread.sleep(2500);
    System.out.println("Work done... ack the message");
    TransactionMessagesResponse resAck = tran.AckMessage();
    if (resAck.getIsError())
    {
     System.out.printf("Ack message error:%s",resAck.getError());

    }
    System.out.println("Ack done");
}

    private static void Transactional_Queue_Ack()
            throws ServerAddressNotSuppliedException, InterruptedException, ClassNotFoundException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Transaction tran = queue.CreateTransaction();
    
        TransactionMessagesResponse resRec= tran.Receive(10, 10);
       if (resRec.getIsError()){
           System.out.printf("Message dequeue error, error:%s",resRec.getError());
            return;  
       }
       System.out.printf("MessageID: %d, Body:%s",resRec.getMessage().getMessageID(),Converter.FromByteArray(resRec.getMessage().getBody()));         
       System.out.println("Doing some work.....");
       
       Thread.sleep(1000);
       System.out.println("Done, ack the message");
        TransactionMessagesResponse resAck = tran.AckMessage();
       if (resAck.getIsError())
       {
        System.out.printf("Ack message error:%s",resAck.getError());
       }

    //    System.out.println("Checking for next message");
    //    resRec = transaction.Receive(10, 1);
    //    if (resRec.IsError)
    //    {
    //        Console.WriteLine($"Message dequeue error, error:{resRec.Error}");
    //        return;
    //    }

    }

    private static void Peek_Messages_from_a_Queue() throws ServerAddressNotSuppliedException, ClassNotFoundException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        ReceiveMessagesResponse resPek = queue.PeekQueueMessage(10, 1);
        if (resPek.getIsError()){
            System.out.printf("Message dequeue error, error:%s",resPek.getError());
            return;   
        }
        System.out.printf("Received %s Messages:",resPek.getMessagesReceived());
        for (Message msg : resPek.getMessages()) {
            System.out.printf("MessageID: %d, Body:%s",msg.getMessageID(),Converter.FromByteArray(msg.getBody()));         
        }
    }

    private static void Receive_Messages_from_a_Queue()
            throws ServerAddressNotSuppliedException, ClassNotFoundException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        ReceiveMessagesResponse resRec = queue.ReceiveQueueMessages(10, 1);
        if (resRec.getIsError()){
            System.out.printf("Message dequeue error, error:%s",resRec.getError());
            return;   
        }
        System.out.printf("Received %s Messages:",resRec.getMessagesReceived());
        for (Message msg : resRec.getMessages()) {
            System.out.printf("MessageID: %d, Body:%s",msg.getMessageID(),Converter.FromByteArray(msg.getBody()));         
        }
    }

    private static void Send_Batch_Messages() throws ServerAddressNotSuppliedException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Collection<Message> batch = new ArrayList<Message>(); 

        for (int i = 0; i < 10; i++) {
            batch.add(new Message()
            .setBody(Converter.ToByteArray("Batch Message "+i)));
        }
        
        SendBatchMessageResult resBatch = queue.SendQueueMessagesBatch(batch);
        if (resBatch.getHaveErrors())
        {
            System.out.print("Message sent batch has errors");
        }
        for (SendMessageResult resSend : resBatch.getResults()) {
            if( resSend.getIsError()){
                System.out.printf("Message enqueue error, error:%s",resSend.getError());
            }else {
                System.out.printf("Send to Queue Result: MessageID:%d, {}, Sent At:%t",resSend.getMessageID(),Converter.FromUnixTime(resSend.getSentAt()));

            }
            
        }       
    }

    private static void Send_Message_to_a_Queue_with_Deadletter_Queue()
            throws ServerAddressNotSuppliedException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
            SendMessageResult resSend = queue.SendQueueMessage(new Message()
            .setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
            .setMetadata("someMeta")
            .setMaxReciveCount(3)
            .setMaxReciveQueue("DeadLetterQueue")
            );           
            if (resSend.getIsError())
            {
                System.out.printf("Message enqueue error, error:%s",resSend.getError());
            }
    }

    private static void Send_Message_to_a_Queue_with_Delay() throws ServerAddressNotSuppliedException, IOException {
            Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
            SendMessageResult resSend = queue.SendQueueMessage(new Message()
            .setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
            .setMetadata("someMeta")
            .setDelay(3)
            );           
            if (resSend.getIsError())
            {
                System.out.printf("Message enqueue error, error:%s",resSend.getError());
            }
    }

    private static void Send_Message_to_a_Queue_with_Expiration()
            throws ServerAddressNotSuppliedException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        SendMessageResult resSend = queue.SendQueueMessage(new Message()
        .setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
        .setMetadata("someMeta")
        .setExpiration(5)
        );           
        if (resSend.getIsError())
        {
            System.out.printf("Message enqueue error, error:%s",resSend.getError());
        }
    }

    private static void Send_Message_to_a_Queue() throws ServerAddressNotSuppliedException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        SendMessageResult resSend = queue.SendQueueMessage(new Message()
        .setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
        .setMetadata("someMeta")
        );           
        if (resSend.getIsError())
        {
            System.out.printf("Message enqueue error, error:%s",resSend.getError());
        }
    }

    private static void Ack_All_Messages_In_a_Queue() throws SSLException, ServerAddressNotSuppliedException {

        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        AckAllMessagesResponse resAck = queue.AckAllQueueMessages();
        if (resAck.getIsError())
        {
            System.out.printf("AckAllQueueMessagesResponse error, error:%s",resAck.getError());
                   return;
        }
        System.out.printf("Ack All Messages:%d completed",resAck.getAffectedMessages());

    }
}
