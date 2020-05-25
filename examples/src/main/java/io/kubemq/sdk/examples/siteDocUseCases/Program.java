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
package io.kubemq.sdk.examples.siteDocUseCases;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import javax.net.ssl.SSLException;
import io.grpc.stub.StreamObserver;
import io.kubemq.sdk.queue.AckAllMessagesResponse;
import io.kubemq.sdk.queue.Message;
import io.kubemq.sdk.queue.Queue;
import io.kubemq.sdk.queue.ReceiveMessagesResponse;
import io.kubemq.sdk.queue.SendBatchMessageResult;
import io.kubemq.sdk.queue.SendMessageResult;
import io.kubemq.sdk.queue.Transaction;
import io.kubemq.sdk.queue.TransactionMessagesResponse;
import io.kubemq.sdk.Exceptions.TransactionException;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.Request;
import io.kubemq.sdk.commandquery.RequestType;
import io.kubemq.sdk.commandquery.Responder;
import io.kubemq.sdk.commandquery.Response;
import io.kubemq.sdk.event.Event;
import io.kubemq.sdk.event.EventReceive;
import io.kubemq.sdk.event.Result;
import io.kubemq.sdk.event.Subscriber;
import io.kubemq.sdk.subscription.EventsStoreType;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;
import io.kubemq.sdk.tools.Converter;

public class Program {

    public static void main(String[] args)
            throws ServerAddressNotSuppliedException, IOException, ClassNotFoundException, InterruptedException {

        // Ack_All_Messages_In_a_Queue();
        // Send_Message_to_a_Queue();
        // Send_Message_to_a_Queue_with_Expiration();
        // Send_Message_to_a_Queue_with_Delay();
        // Send_Message_to_a_Queue_with_DeadLetter_Queue();
        // Send_Batch_Messages();
        // Receive_Messages_from_a_Queue();
        // Peek_Messages_from_a_Queue();
        // Transactional_Queue_Ack_BAD();
        // Transactional_Queue_Ack();
        // Transactional_Queue_Reject();
        // Transactional_Queue_Extend_Visibility();
        // Transactional_Queue_Resend_to_New_Queue();
        // Transactional_Queue_Resend_Modified_Message();

        // Receiving_Events();
        // Sending_Events_Single_Event();
        // Sending_Events_Stream_Events();

        // Receiving_Events_Store();
        // Sending_Events_Store_Single_Event_to_Store();
        // Sending_Events_Store_Stream_Events_Store();

        // Commands_Receiving_Commands_Requests();
        // Commands_Sending_Command_Request();
        // Commands_Sending_Command_Request_async();

        // Queries_Receiving_Query_Requests();
        // Queries_Sending_Query_Request();
        // Queries_Sending_Query_Request_async();
        Transactional_Queue_Reject_Loop();

        try {
            int read = System.in.read();
        } catch (IOException e) {
            System.out.println("Error:  I/O error occurred.");
        }

    }

    private static void Transactional_Queue_Reject_Loop() throws SSLException, ServerAddressNotSuppliedException
    {
        int cnt = 0;
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000","eyJhbGciOiJIUzI1NiJ9.e30.tNiB_q4Qk-ox-ZrEADaLi9gJpKZ9KJUSP16uqjHAdTE");
        while (true) {
            Transaction tran = queue.CreateTransaction();
            Transaction.ErrorObserver msgExpired = new Transaction.ErrorObserver() {

                @Override
                public void onNext(Error error) {
                    System.out.printf("\n!!!!!!!!!!!!!Message dequeue %s, %d!!!!!!!!!!!!!!\n", error.getMessage());
                    return;
                }
            };
            TransactionMessagesResponse resRec;

            try {
                resRec = tran.Receive(1, 1, msgExpired);
            } catch (TransactionException  e) {

                e.printStackTrace();
                continue;
            }
            cnt++;

            if (resRec.getIsError()) {
                System.out.printf("\n!!!!!!!!!!!!!Message dequeue error, error: %s, %d!!!!!!!!!!!!!!\n",
                        resRec.getError(), cnt);
                continue;
            } else {
                System.out.printf("\n!!!!!!!!!!!!!Message dequeue %s, %d!!!!!!!!!!!!!!\n",
                        resRec.getMessage().getMessageID(), cnt);
                try {
                    resRec = tran.ExtendVisibility(1);
                } catch (TransactionException  e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    continue;
                }
                if (resRec.getIsError()) {

                    System.out.printf("\n!!!!!!!!!!!!!Message dequeue error, error: %s, %d!!!!!!!!!!!!!!\n",
                            resRec.getError(), cnt);
                    continue;
                }

                try {
                    resRec = tran.RejectMessage();
                } catch (TransactionException  e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    continue;
                }

                if (resRec.getIsError()) {

                    System.out.printf("\n!!!!!!!!!!!!!Message dequeue error, error: %s, %d!!!!!!!!!!!!!!\n",
                            resRec.getError(), cnt);
                    continue;
                }
            }
        }
    }

    private static void Transactional_Queue_Ack_BAD() throws SSLException, ServerAddressNotSuppliedException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Transaction tran = queue.CreateTransaction();

        TransactionMessagesResponse resRec;

        try {
            resRec = tran.Receive(1, 10);
        } catch (TransactionException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void Send_Message_to_a_Queue() throws SSLException, ServerAddressNotSuppliedException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");

        SendMessageResult resSend;
        try {
            resSend = queue.SendQueueMessage(new Message()
                    .setBody(Converter.ToByteArray("some-simple_queue-queue-message")).setMetadata("someMeta"));
            if (resSend.getIsError()) {
                System.out.printf("Message enqueue error, error: %s", resSend.getError());
            }
        } catch ( ServerAddressNotSuppliedException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void Send_Message_to_a_Queue_with_Expiration() throws SSLException, ServerAddressNotSuppliedException

    {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");

        SendMessageResult resSend;
        try {
            resSend = queue
                    .SendQueueMessage(new Message().setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
                            .setMetadata("someMeta").setExpiration(5));
            if (resSend.getIsError()) {
                System.out.printf("Message enqueue error, error: %s", resSend.getError());
            }
        } catch ( ServerAddressNotSuppliedException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void Send_Message_to_a_Queue_with_Delay() throws SSLException, ServerAddressNotSuppliedException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        SendMessageResult resSend;
        try {
            resSend = queue
                    .SendQueueMessage(new Message().setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
                            .setMetadata("someMeta").setDelay(3));
            if (resSend.getIsError()) {
                System.out.printf("Message enqueue error, error: %s", resSend.getError());
            }
        } catch ( ServerAddressNotSuppliedException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void Send_Message_to_a_Queue_with_DeadLetter_Queue()
            throws SSLException, ServerAddressNotSuppliedException

    {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        SendMessageResult resSend;
        try {
            resSend = queue
                    .SendQueueMessage(new Message().setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
                            .setMetadata("someMeta").setMaxReciveCount(3).setMaxReciveQueue("DeadLetterQueue"));
            if (resSend.getIsError()) {
                System.out.printf("Message enqueue error, error: %s", resSend.getError());
            }
        } catch ( ServerAddressNotSuppliedException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void Send_Batch_Messages() throws ServerAddressNotSuppliedException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Collection<Message> batch = new ArrayList<Message>();

        for (int i = 0; i < 10000; i++) {
            batch.add(new Message().setBody(Converter.ToByteArray("Batch Message " + i)));
        }

        SendBatchMessageResult resBatch;
        try {
            resBatch = queue.SendQueueMessagesBatch(batch);

            if (resBatch.getHaveErrors()) {
                System.out.print("Message sent batch has errors");
            }
            for (SendMessageResult resSend : resBatch.getResults()) {
                if (resSend.getIsError()) {
                    System.out.printf("Message enqueue error, error: %s", resSend.getError());
                } else {
                    System.out.printf("Send to Queue Result: MessageID: %s, Sent At:%s", resSend.getMessageID(),
                            Converter.FromUnixTime(resSend.getSentAt()).toString());

                }

            }
        } catch (SSLException  | ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void Receive_Messages_from_a_Queue()
            throws ServerAddressNotSuppliedException, ClassNotFoundException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        ReceiveMessagesResponse resRec;
        try {
            resRec = queue.ReceiveQueueMessages(10, 1);
            if (resRec.getIsError()) {
                System.out.printf("Message dequeue error, error: %s", resRec.getError());
                return;
            }
            System.out.printf("Received Messages %s:", resRec.getMessagesReceived());
            for (Message msg : resRec.getMessages()) {
                System.out.printf("MessageID: %s, Body:%s", msg.getMessageID(), Converter.FromByteArray(msg.getBody()));
            }
        } catch (SSLException  | ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void Peek_Messages_from_a_Queue()
            throws ServerAddressNotSuppliedException, ClassNotFoundException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        ReceiveMessagesResponse resPek;
        try {
            resPek = queue.PeekQueueMessage(10, 1);
            if (resPek.getIsError()) {
                System.out.printf("Message dequeue error, error: %s", resPek.getError());
                return;
            }
            System.out.printf("Received Messages: %s", resPek.getMessagesReceived());
            for (Message msg : resPek.getMessages()) {
                System.out.printf("MessageID: %s, Body: %s", msg.getMessageID(),
                        Converter.FromByteArray(msg.getBody()));
            }
        } catch (SSLException  | ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void Ack_All_Messages_In_a_Queue() throws SSLException, ServerAddressNotSuppliedException {

        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        AckAllMessagesResponse resAck;
        try {
            resAck = queue.AckAllQueueMessages();
            if (resAck.getIsError()) {
                System.out.printf("AckAllQueueMessagesResponse error, error: %s", resAck.getError());
                return;
            }
            System.out.printf("Ack All Messages: %d completed", resAck.getAffectedMessages());
        } catch (SSLException  | ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void Transactional_Queue_Ack()
            throws InterruptedException, ServerAddressNotSuppliedException, ClassNotFoundException, IOException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");

        Transaction tran = queue.CreateTransaction();

        TransactionMessagesResponse resRec;
        try {
            resRec = tran.Receive(10, 10);
            if (resRec.getIsError()) {
                System.out.printf("Message dequeue error, error: %s", resRec.getError());
                return;
            }
            System.out.printf("MessageID: %s, Body: %s", resRec.getMessage().getMessageID(),
                    Converter.FromByteArray(resRec.getMessage().getBody()));

        } catch (TransactionException  | SSLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        System.out.println("Doing some work.....");

        Thread.sleep(1000);

        System.out.println("Done, ack the message");
        TransactionMessagesResponse resAck;
        try {
            resAck = tran.AckMessage();
            if (resAck.getIsError()) {
                System.out.printf("Ack message error: %s", resAck.getError());
            }
        } catch (TransactionException  | SSLException | ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

    }

    private static void Transactional_Queue_Reject()
            throws ClassNotFoundException, IOException, ServerAddressNotSuppliedException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Transaction tran;

        tran = queue.CreateTransaction();
        TransactionMessagesResponse resRec;
        try {
            resRec = tran.Receive(10, 10);

            if (resRec.getIsError()) {
                System.out.printf("Message dequeue error, error: %s", resRec.getError());
                return;
            }
            System.out.printf("MessageID: %s, Body: %s", resRec.getMessage().getMessageID(),
                    Converter.FromByteArray(resRec.getMessage().getBody()));

        } catch (ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Reject message");
        TransactionMessagesResponse resRej;
        try {
            resRej = tran.RejectMessage();
            if (resRej.getIsError()) {
                System.out.printf("Message dequeue error, error: %s", resRej.getError());
                return;
            }
        } catch (ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

    }

    private static void Transactional_Queue_Extend_Visibility()
            throws ClassNotFoundException, IOException, InterruptedException, ServerAddressNotSuppliedException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Transaction tran = queue.CreateTransaction();
        TransactionMessagesResponse resRec;
        try {
            resRec = tran.Receive(5, 10);
            if (resRec.getIsError()) {
                System.out.printf("Message dequeue error, error: %s", resRec.getError());
                return;
            }
            System.out.printf("MessageID: %s, Body: %s", resRec.getMessage().getMessageID(),
                    Converter.FromByteArray(resRec.getMessage().getBody()));
        } catch (TransactionException | SSLException | ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        System.out.println("work for 1 seconds");
        Thread.sleep(1000);
        System.out.println("Need more time to process, extend visibility for more 3 seconds");
        TransactionMessagesResponse resExt;
        try {
            resExt = tran.ExtendVisibility(3);
            if (resExt.getIsError()) {
                System.out.printf("Message dequeue error, error: %s", resExt.getError());
                return;
            }
        } catch (ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        System.out.println("Approved. work for 2.5 seconds");
        Thread.sleep(2500);
        System.out.println("Work done... ack the message");
        TransactionMessagesResponse resAck;
        try {
            resAck = tran.AckMessage();
            if (resAck.getIsError()) {
                System.out.printf("Ack message error: %s", resAck.getError());

            }
        } catch (ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        System.out.println("Ack done");
    }

    private static void Transactional_Queue_Resend_Modified_Message() throws ClassNotFoundException, IOException,
            ServerAddressNotSuppliedException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Transaction tran = queue.CreateTransaction();
        TransactionMessagesResponse resRec;
        try {
            resRec = tran.Receive(500, 10);
            if (resRec.getIsError()) {
                System.out.printf("Message dequeue error, error: %s", resRec.getError());
                return;
            }
            System.out.printf("MessageID: %s, Body:%s", resRec.getMessage().getMessageID(),
                    Converter.FromByteArray(resRec.getMessage().getBody()));
        } catch (TransactionException | SSLException | ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        TransactionMessagesResponse resMod;
        try {
            resMod = tran.Modify(resRec.getMessage().setQueue("receiverB").setMetadata("new metadata"));
            if (resMod.getIsError()) {
                System.out.printf("Message Modify error, error::%s", resMod.getError());
                return;
            }
        } catch (ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

    }

    private static void Transactional_Queue_Resend_to_New_Queue() throws ClassNotFoundException, IOException,
            ServerAddressNotSuppliedException {
        Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
        Transaction tran = queue.CreateTransaction();
        TransactionMessagesResponse resRec;
        try {
            resRec = tran.Receive(5, 10);
            if (resRec.getIsError()) {
                System.out.printf("Message dequeue error, error: %s", resRec.getError());
                return;
            }
            System.out.printf("MessageID: %s, Body:%s", resRec.getMessage().getMessageID(),
                    Converter.FromByteArray(resRec.getMessage().getBody()));

        } catch (TransactionException | SSLException | ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
        System.out.println("Resend to new queue");
        TransactionMessagesResponse resResend;
        try {
            resResend = tran.ReSend("new-queue");
            if (resResend.getIsError()) {
                System.out.printf("Message dequeue error, error: %s", resResend.getError());
                return;
            }
        } catch (ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Done");

    }

    private static void Receiving_Events() throws SSLException, ServerAddressNotSuppliedException {
        String ChannelName = "testing_event_channel", ClientID = "hello-world-subscriber",
                KubeMQServerAddress = "localhost:50000";
        Subscriber subscriber = new Subscriber(KubeMQServerAddress);
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setChannel(ChannelName);
        subscribeRequest.setClientID(ClientID);
        subscribeRequest.setSubscribeType(SubscribeType.Events);

        StreamObserver<EventReceive> streamObserver = new StreamObserver<EventReceive>() {

            @Override
            public void onNext(EventReceive value) {
                try {
                    System.out.printf("Event Received: EventID: %s, Channel: %s, Metadata: %s, Body: %s",
                            value.getEventId(), value.getChannel(), value.getMetadata(),
                            Converter.FromByteArray(value.getBody()));
                } catch (ClassNotFoundException e) {
                    System.out.printf("ClassNotFoundException: %s", e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.printf("IOException:  %s", e.getMessage());
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("Event Received Error: %s", t.toString());
                return;
            }

            @Override
            public void onCompleted() {

            }
        };
        subscriber.SubscribeToEvents(subscribeRequest, streamObserver);

    }

    private static void Sending_Events_Stream_Events() throws IOException, ServerAddressNotSuppliedException {
        String ChannelName = "testing_event_channel", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";

        io.kubemq.sdk.event.Channel channel = new io.kubemq.sdk.event.Channel(ChannelName, ClientID, false,
                KubeMQServerAddress);
        Event event = new Event();
        event.setBody(Converter.ToByteArray("hello kubemq - sending single event"));

        StreamObserver<Result> streamResObserver = new StreamObserver<Result>() {

            @Override
            public void onNext(Result value) {
                System.out.printf("Sent event: %s", value.getEventId());
            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("Could not send single message");
            }

            @Override
            public void onCompleted() {

            }
        };

        StreamObserver<Event> stream = channel.StreamEvent(streamResObserver);
        stream.onNext(event);

    }

    private static void Sending_Events_Single_Event() throws IOException {
        String ChannelName = "testing_event_channel", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";

        io.kubemq.sdk.event.Channel channel = new io.kubemq.sdk.event.Channel(ChannelName, ClientID, false,
                KubeMQServerAddress);
        Event event = new Event();
        event.setBody(Converter.ToByteArray("hello kubemq - sending single event"));
        Result result;

        try {
            result = channel.SendEvent(event);
            if (!result.isSent()) {
                System.out.println("Could not send single message");
                return;
            }
        } catch (SSLException | ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void Receiving_Events_Store() throws SSLException, ServerAddressNotSuppliedException {
        String ChannelName = "testing_store_channel", ClientID = "hello-world-subscribe1r",
                KubeMQServerAddress = "localhost:50000";
        Subscriber subscriber = new Subscriber(KubeMQServerAddress);
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setChannel(ChannelName);
        subscribeRequest.setClientID(ClientID);
        subscribeRequest.setSubscribeType(SubscribeType.EventsStore);
        subscribeRequest.setEventsStoreType(EventsStoreType.StartAtSequence);
        subscribeRequest.setEventsStoreTypeValue(1);

        StreamObserver<EventReceive> streamObserver = new StreamObserver<EventReceive>() {

            @Override
            public void onNext(EventReceive value) {
                try {
                    System.out.printf("Event Received: EventID: %s, Channel: %s, Metadata: %s, Body: %s",
                            value.getEventId(), value.getChannel(), value.getMetadata(),
                            Converter.FromByteArray(value.getBody()));
                } catch (ClassNotFoundException e) {
                    System.out.printf("ClassNotFoundException: %s", e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.printf("IOException: %s", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("onError:  %s", t.getMessage());
            }

            @Override
            public void onCompleted() {

            }

        };
        subscriber.SubscribeToEvents(subscribeRequest, streamObserver);

    }

    private static void Sending_Events_Store_Stream_Events_Store()
            throws IOException, ServerAddressNotSuppliedException {
        String ChannelName = "testing_store_channel", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";

        io.kubemq.sdk.event.Channel channel = new io.kubemq.sdk.event.Channel(ChannelName, ClientID, true,
                KubeMQServerAddress);

        StreamObserver<Result> streamResObserver = new StreamObserver<Result>() {

            @Override
            public void onNext(Result value) {
                System.out.printf("Stream event: %s", value.getEventId());
            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("Could not send single message");
            }

            @Override
            public void onCompleted() {

            }
        };

        StreamObserver<Event> stream = channel.StreamEvent(streamResObserver);
        for (int i = 0; i < 10; i++) {
            Event event = new Event();
            event.setBody(Converter.ToByteArray("hello kubemq - sending single event"));
            event.setEventId("event-Store-" + i);
            stream.onNext(event);
        }

    }

    private static void Sending_Events_Store_Single_Event_to_Store() throws IOException {
        String ChannelName = "testing_store_channel", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";

        io.kubemq.sdk.event.Channel channel = new io.kubemq.sdk.event.Channel(ChannelName, ClientID, true,
                KubeMQServerAddress);
        for (int i = 0; i < 10; i++) {
            Event event = new Event();
            event.setBody(Converter.ToByteArray("hello kubemq - sending single event"));
            event.setEventId("event-Store-" + i);
            try {
                channel.SendEvent(event);
            } catch (ServerAddressNotSuppliedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    private static void Commands_Receiving_Commands_Requests() {
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
                } catch (SSLException |  ServerAddressNotSuppliedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }.start();

    }

    private static void Commands_Sending_Command_Request() throws IOException {
        String ChannelName = "testing_Command_channel", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";
        io.kubemq.sdk.commandquery.ChannelParameters channelParameters = new io.kubemq.sdk.commandquery.ChannelParameters();
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
        } catch (SSLException |  ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        System.out.printf("Response Received: %s, ExecutedAt: %s", result.getRequestID(), result.getTimestamp());
    }

    private static void Commands_Sending_Command_Request_async() throws ServerAddressNotSuppliedException, IOException {
        String ChannelName = "cmd", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";
        io.kubemq.sdk.commandquery.ChannelParameters channelParameters = new io.kubemq.sdk.commandquery.ChannelParameters();
        channelParameters.setChannelName(ChannelName);
        channelParameters.setClientID(ClientID);
        channelParameters.setKubeMQAddress(KubeMQServerAddress);
        channelParameters.setRequestType(RequestType.Command);
        channelParameters.setTimeout(1000);
        io.kubemq.sdk.commandquery.Channel channel = new io.kubemq.sdk.commandquery.Channel(channelParameters);
        Request request = new Request();
        request.setBody(Converter.ToByteArray("hello kubemq - sending a command, please reply"));
        StreamObserver<Response> response = new StreamObserver<Response>() {

            @Override
            public void onNext(Response value) {
                if (!value.isExecuted()) {
                    System.out.printf("Response error: %s", value.getError());
                } else {
                    System.out.printf("Response Received: %s, ExecutedAt %s", value.getRequestID(),
                            value.getTimestamp());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("RPC Error: %s", t.getMessage());
            }

            @Override
            public void onCompleted() {

            }
        };
        channel.SendRequestAsync(request, response);
        response.onCompleted();

    }

    private static void Queries_Receiving_Query_Requests() {
        String ChannelName = "testing_Queries_channel", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";
        Responder.RequestResponseObserver HandleIncomingRequests;
        Responder responder = new Responder(KubeMQServerAddress);
        HandleIncomingRequests = request -> {

            Response response = new Response(request);
            response.setCacheHit(false);
            response.setError("None");
            response.setClientID(ClientID);
            response.setBody("got your query, you are good to goo".getBytes());
            response.setExecuted(true);
            response.setMetadata("this is a response");
            response.setTimestamp(LocalDateTime.now());
            return response;
        };
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setChannel(ChannelName);
        subscribeRequest.setClientID(ClientID);
        subscribeRequest.setSubscribeType(SubscribeType.Queries);

        new Thread() {
            public void run() {

                try {
                    responder.SubscribeToRequests(subscribeRequest, HandleIncomingRequests);
                } catch (SSLException |  ServerAddressNotSuppliedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }.start();

    }

    private static void Queries_Sending_Query_Request() throws IOException {
        String ChannelName = "testing_Queries_channel", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";
        io.kubemq.sdk.commandquery.ChannelParameters channelParameters = new io.kubemq.sdk.commandquery.ChannelParameters();
        channelParameters.setChannelName(ChannelName);
        channelParameters.setClientID(ClientID);
        channelParameters.setKubeMQAddress(KubeMQServerAddress);
        channelParameters.setRequestType(RequestType.Query);
        channelParameters.setTimeout(1000);
        io.kubemq.sdk.commandquery.Channel channel = new io.kubemq.sdk.commandquery.Channel(channelParameters);
        Request request = new Request();
        request.setBody(Converter.ToByteArray("hello kubemq - sending a query, please reply"));
        Response result;

        try {
            result = channel.SendRequest(request);

            if (!result.isExecuted()) {

                System.out.printf("Response error: %s", result.getError());
                return;
            }
            System.out.printf("Response Received: %s, ExecutedAt: %s", result.getRequestID(), result.getTimestamp());
        } catch (SSLException |  ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void Queries_Sending_Query_Request_async() throws IOException, ServerAddressNotSuppliedException {
        String ChannelName = "testing_Queries_channel", ClientID = "hello-world-sender",
                KubeMQServerAddress = "localhost:50000";
        io.kubemq.sdk.commandquery.ChannelParameters channelParameters = new io.kubemq.sdk.commandquery.ChannelParameters();
        channelParameters.setChannelName(ChannelName);
        channelParameters.setClientID(ClientID);
        channelParameters.setKubeMQAddress(KubeMQServerAddress);
        channelParameters.setRequestType(RequestType.Query);
        channelParameters.setTimeout(100000);
        io.kubemq.sdk.commandquery.Channel channel = new io.kubemq.sdk.commandquery.Channel(channelParameters);
        Request request = new Request();
        request.setBody(Converter.ToByteArray("hello kubemq - sending a query, please reply"));
        StreamObserver<Response> response = new StreamObserver<Response>() {

            @Override
            public void onNext(Response value) {
                if (!value.isExecuted()) {
                    System.out.printf("Response error: %s", value.getError());
                } else {
                    System.out.printf("Response Received: %s, ExecutedAt %s", value.getRequestID(),
                            value.getTimestamp());
                }

            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("onError: %s", t.getMessage());
            }

            @Override
            public void onCompleted() {

            }
        };
        channel.SendRequestAsync(request, response);
    }

}