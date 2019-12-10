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
package io.kubemq.sdk.queue;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import javax.net.ssl.SSLException;

import io.grpc.stub.StreamObserver;
import io.kubemq.sdk.basic.GrpcClient;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.grpc.Kubemq.QueueMessage;
import io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest;
import io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse;
import io.kubemq.sdk.tools.IDGenerator;

/**
 * Advance manipulation of messages using stream
 */
public class Transaction extends GrpcClient {

    static Semaphore semaphore = new Semaphore(1);

    private Queue queue;
    protected StreamQueueMessagesResponse msg;
    protected StreamQueueMessagesResponse latestMsg;
    private StreamObserver<StreamQueueMessagesResponse> respStreamObserver;
    private StreamObserver<StreamQueueMessagesRequest> reqStreamObserver;

    private final Object lock = new Object();
    private Boolean streamResponded = false;

    private boolean visibilityExp;

    public boolean getExpirationStatus() {
        return this.visibilityExp;
    }

    public QueueMessage getCurrentHandledMessage() {
        if (msg == null) {
            return null;
        } else {
            return this.msg.getMessage();
        }
    }

    protected Transaction(Queue queue) throws ServerAddressNotSuppliedException {
        this.queue = queue;
        this._kubemqAddress = queue.getServerAddress();
    }

    /**
     * Receive queue messages request , waiting for response or timeout.
     * 
     * @param visibilitySeconds message access lock by receiver.
     * @param waitTimeSeconds   Wait time of request., default is from queue
     * @return Transaction response
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws IOException                       Error in response from stream
     */
    public TransactionMessagesResponse Receive(Integer visibilitySeconds, Integer waitTimeSeconds)
            throws ServerAddressNotSuppliedException, IOException {
        if (msg != null) {
            return new TransactionMessagesResponse("No Active queue message, visibility expired:" + visibilityExp, null,
                    null);
        }
        visibilityExp = false;
        Kubemq.StreamQueueMessagesResponse resp;
        OpenStream();

        resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder().setClientID(this.queue.getClientID())
                .setChannel(this.queue.getQueueName()).setRequestID(IDGenerator.Getid())
                .setStreamRequestTypeData(Kubemq.StreamRequestType.ReceiveMessage)
                .setVisibilitySeconds(visibilitySeconds).setWaitTimeSeconds(waitTimeSeconds).build());

        return new TransactionMessagesResponse(resp);

    }

    /**
     * Will mark Message dequeued on queue.
     * 
     * @return Transaction response.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws IOException                       Error in response from stream
     */
    public TransactionMessagesResponse AckMessage() throws ServerAddressNotSuppliedException, IOException {
        if (msg == null) {
            return new TransactionMessagesResponse("No Active queue message, visibility expired:" + visibilityExp, null,
                    null);
        }
        Kubemq.StreamQueueMessagesResponse resp;

        resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder().setClientID(this.queue.getClientID())
                .setChannel(this.queue.getQueueName()).setRequestID(IDGenerator.Getid())
                .setStreamRequestTypeData(Kubemq.StreamRequestType.AckMessage)
                .setRefSequence(msg.getMessage().getAttributes().getSequence()).build());

        return new TransactionMessagesResponse(resp);
    }

    /**
     * Will return message to queue.
     * 
     * @return Transaction response.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws IOException                       Error in response from stream
     */
    public TransactionMessagesResponse RejectMessage() throws ServerAddressNotSuppliedException, IOException {
        if (msg == null) {
            return new TransactionMessagesResponse("No Active queue message, visibility expired:" + visibilityExp, null,
                    null);
        }
        Kubemq.StreamQueueMessagesResponse resp;

        try {
            resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder()
                    .setClientID(this.queue.getClientID()).setChannel(this.queue.getQueueName())
                    .setRequestID(IDGenerator.Getid()).setStreamRequestTypeData(Kubemq.StreamRequestType.RejectMessage)
                    .setRefSequence(msg.getMessage().getAttributes().getSequence()).build());

        } catch (NullPointerException ex) {
            return new TransactionMessagesResponse("No Active queue message, visibility expired:" + visibilityExp, null,
                    null);
        }

        return new TransactionMessagesResponse(resp);
    }

    /**
     * Extend the visibility time for the current receive message
     * 
     * @param visibilitySeconds New viability time.
     * @return Transaction response.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws IOException                       Error in response from stream
     */
    public TransactionMessagesResponse ExtendVisibility(int visibilitySeconds)
            throws ServerAddressNotSuppliedException, IOException {
        if (msg == null) {
            return new TransactionMessagesResponse("No Active queue message, visibility expired:" + visibilityExp, null,
                    null);
        }
        Kubemq.StreamQueueMessagesResponse resp;

        resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder().setClientID(this.queue.getClientID())
                .setChannel(this.queue.getQueueName()).setRequestID(IDGenerator.Getid())
                .setStreamRequestTypeData(Kubemq.StreamRequestType.ModifyVisibility).build());

        return new TransactionMessagesResponse(resp);

    }

    /**
     * Resend the current received message to a new channel and ack the current
     * message.
     * 
     * @param queueName Resend queue name.
     * @return Transaction response.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws IOException                       Error in response from stream
     */
    public TransactionMessagesResponse ReSend(String queueName) throws ServerAddressNotSuppliedException, IOException {
        if (msg == null) {
            return new TransactionMessagesResponse("No Active queue message, visibility expired:" + visibilityExp, null,
                    null);
        }
        Kubemq.StreamQueueMessagesResponse resp;

        resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder().setClientID(this.queue.getClientID())
                .setChannel(queueName).setRequestID(IDGenerator.Getid())

                .setStreamRequestTypeData(Kubemq.StreamRequestType.ResendMessage).build());

        return new TransactionMessagesResponse(resp);
    }

    /**
     * Resend the current received message message to a new channel.
     * 
     * @param message New message
     * @return Transaction response.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws IOException                       Error in response from stream
     */
    public TransactionMessagesResponse Modify(Message message) throws ServerAddressNotSuppliedException, IOException {
        if (msg == null) {
            return new TransactionMessagesResponse("No Active queue message, visibility expired:" + visibilityExp, null,
                    null);
        }
        Kubemq.StreamQueueMessagesResponse resp;

        resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder().setClientID(this.queue.getClientID())
                .setRequestID(IDGenerator.Getid())
                .setStreamRequestTypeData(Kubemq.StreamRequestType.SendModifiedMessage)
                .setModifiedMessage(message.toQueueMessage()).build());

        return new TransactionMessagesResponse(resp);
    }

    private boolean OpenStream() {

        if (respStreamObserver == null) {

            respStreamObserver = new StreamObserver<Kubemq.StreamQueueMessagesResponse>() {

                @Override
                public void onNext(StreamQueueMessagesResponse value) {
                    synchronized (lock) {
                        if (value.getIsError()) {
                            if (value.getError().contains("Error 129")) {
                                msg = null;
                                visibilityExp = true;
                            }
                            ;
                        } else if (value
                                .getStreamRequestTypeData() == io.kubemq.sdk.grpc.Kubemq.StreamRequestType.ReceiveMessage) {
                            msg = value;
                        }
                        ;
                        latestMsg = value;
                        streamResponded = true;
                        lock.notify();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    msg = null;
                    latestMsg = null;
                    streamResponded = true;
                    lock.notify();
                }

                @Override
                public void onCompleted() {
                    msg = null;
                    latestMsg = null;
                    streamResponded = true;
                    reqStreamObserver = null;

                }
            };
        }
        return true;
    }

    private Kubemq.StreamQueueMessagesResponse StreamQueueMessage(Kubemq.StreamQueueMessagesRequest sr)
            throws SSLException, ServerAddressNotSuppliedException {

        if (reqStreamObserver == null) {
            reqStreamObserver = GetKubeMQAsyncClient().streamQueueMessage(respStreamObserver);
        }
        streamResponded = false;
        reqStreamObserver.onNext(sr);

        synchronized (lock) {
            while (!streamResponded) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return latestMsg;
    }

}