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

import java.util.concurrent.Semaphore;
import javax.net.ssl.SSLException;

import io.grpc.stub.StreamObserver;
import io.kubemq.sdk.Exceptions.TransactionException;
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
    ErrorObserver errorObserver;

    private final Object lock = new Object();
    boolean visibilityExp;

    TranState state = TranState.Ready;

    private enum TranState {
        Ready, StreamRegistered, StreamOpened, StreamClosing, StreamClosed, InTransaction, UNAUTHENTICATED

    }

    public QueueMessage getCurrentHandledMessage() {
        if (msg == null) {
            return null;
        } else {
            return this.msg.getMessage();
        }
    }

    public interface ErrorObserver {
        void onNext(Error error);
    }

    protected Transaction(Queue queue) throws ServerAddressNotSuppliedException {
        this.queue = queue;
        this._kubemqAddress = queue.getServerAddress();
        this._metadata = queue.getMetadata();
    }

    /**
     * Receive queue messages request , waiting for response or timeout.
     * 
     * @param visibilitySeconds message access lock by receiver.
     * @param waitTimeSeconds   Wait time of request., default is from queue
     * @param msgExpired        Async StreamObserver to handle transaction
     *                          expiration.
     * @return Transaction response
     * @throws TransactionException              Transaction return message is
     *                                           missing.
     * @throws AuthorizationException            Authorization KubeMQ token to be
     *                                           used for KubeMQ connection.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     */

    public TransactionMessagesResponse Receive(Integer visibilitySeconds, Integer waitTimeSeconds,
            ErrorObserver msgExpired)
            throws TransactionException,  ServerAddressNotSuppliedException, SSLException {
        errorObserver = msgExpired;
        if (state != TranState.Ready) {
            return new TransactionMessagesResponse("No Active queue message, visibility expired:" + visibilityExp, null,
                    null);
        }
        Kubemq.StreamQueueMessagesResponse resp;
        CreateNewObserver();

        resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder().setClientID(this.queue.getClientID())
                .setChannel(this.queue.getQueueName()).setRequestID(IDGenerator.Getid())
                .setStreamRequestTypeData(Kubemq.StreamRequestType.ReceiveMessage)
                .setVisibilitySeconds(visibilitySeconds).setWaitTimeSeconds(waitTimeSeconds).build());

        return new TransactionMessagesResponse(resp);

    }

    /**
     * Receive queue messages request , waiting for response or timeout.
     * 
     * @param visibilitySeconds message access lock by receiver.
     * @param waitTimeSeconds   Wait time of request., default is from queue
     * @return Transaction response
     * @throws TransactionException              Transaction return message is
     *                                           missing.
     * @throws AuthorizationException            Authorization KubeMQ token to be
     *                                           used for KubeMQ connection.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     */
    public TransactionMessagesResponse Receive(Integer visibilitySeconds, Integer waitTimeSeconds)
            throws TransactionException,  ServerAddressNotSuppliedException, SSLException {

        if (state != TranState.Ready) {
            return new TransactionMessagesResponse("No Active queue message, visibility expired:" + visibilityExp, null,
                    null);
        }
        Kubemq.StreamQueueMessagesResponse resp;
        CreateNewObserver();

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
     * @throws TransactionException              Transaction return message is
     *                                           missing.
     * @throws AuthorizationException            Authorization KubeMQ token to be
     *                                           used for KubeMQ connection.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     */
    public TransactionMessagesResponse AckMessage()
            throws TransactionException,  ServerAddressNotSuppliedException, SSLException {
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
     * @throws TransactionException              Transaction return message is
     *                                           missing.
     * @throws AuthorizationException            Authorization KubeMQ token to be
     *                                           used for KubeMQ connection.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     */
    public TransactionMessagesResponse RejectMessage()
            throws TransactionException,  ServerAddressNotSuppliedException, SSLException {
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
     * 
     * @return Transaction response.
     * @throws TransactionException              Transaction return message is
     *                                           missing.
     * @throws AuthorizationException            Authorization KubeMQ token to be
     *                                           used for KubeMQ connection.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     */
    public TransactionMessagesResponse ExtendVisibility(int visibilitySeconds)
            throws TransactionException,  ServerAddressNotSuppliedException, SSLException {
        if (msg == null) {
            return new TransactionMessagesResponse("No Active queue message, visibility expired:" + visibilityExp, null,
                    null);
        }
        Kubemq.StreamQueueMessagesResponse resp;

        resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder().setClientID(this.queue.getClientID())
                .setChannel(this.queue.getQueueName()).setRequestID(IDGenerator.Getid())
                .setVisibilitySeconds(visibilitySeconds)
                .setStreamRequestTypeData(Kubemq.StreamRequestType.ModifyVisibility).build());

        return new TransactionMessagesResponse(resp);

    }

    /**
     * Resend the current received message to a new channel and ack the current
     * message.
     * 
     * @param queueName Resend queue name.
     * @return Transaction response.
     * 
     * @throws TransactionException              Transaction return message is
     *                                           missing.
     * @throws AuthorizationException            Authorization KubeMQ token to be
     *                                           used for KubeMQ connection.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     */
    public TransactionMessagesResponse ReSend(String queueName)
            throws TransactionException,  ServerAddressNotSuppliedException, SSLException {
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
     * @throws TransactionException              Transaction return message is
     *                                           missing.
     * @throws AuthorizationException            Authorization KubeMQ token to be
     *                                           used for KubeMQ connection.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     */
    public TransactionMessagesResponse Modify(Message message)
            throws TransactionException, ServerAddressNotSuppliedException, SSLException {
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

    private boolean CreateNewObserver() {
        state = TranState.StreamOpened;
        visibilityExp = false;
        respStreamObserver = new StreamObserver<Kubemq.StreamQueueMessagesResponse>() {

            @Override
            public void onNext(StreamQueueMessagesResponse value) {
                synchronized (lock) {
                    if (value.getIsError())
                    // Error case
                    {
                        // VisabilityExpired
                        if (value.getError().contains("Error 129")) {
                            state = TranState.StreamClosing;
                            latestMsg = value;
                            visibilityExp = true;
                            onCompleted();
                            errorObserver.onNext(new Error(value.getError()));
                            // send error
                            return;
                        } else {
                            // Error in initial receive
                            if (value
                                    .getStreamRequestTypeData() == io.kubemq.sdk.grpc.Kubemq.StreamRequestType.ReceiveMessage) {
                                state = TranState.StreamClosing;
                                latestMsg = value;
                                onCompleted();
                                return;
                            }
                        }
                    }
                    // Other errors will not close the stream
                    else
                    // check if the initial msg received
                    {
                        if (value
                                .getStreamRequestTypeData() == io.kubemq.sdk.grpc.Kubemq.StreamRequestType.ReceiveMessage) {
                            msg = value;
                            state = TranState.InTransaction;
                        }
                        ;
                    }
                    latestMsg = value;
                    lock.notify();
                }
            }

            @Override
            public void onError(Throwable t) {
                if (t.getMessage().contains("UNAUTHENTICATED")) {
                    state = TranState.UNAUTHENTICATED;
                } else {
                    state = TranState.StreamClosing;
                }
                onCompleted();

            }

            @Override
            public void onCompleted() {
                if (state != TranState.StreamClosed) {
                    synchronized (lock) {
                        if (state != TranState.UNAUTHENTICATED) {
                            state = TranState.StreamClosed;
                        }
                        if (msg != null) {
                            msg = null;
                        }
                        lock.notify();
                    }
                }

            }
        };
        return true;
    }

    private Kubemq.StreamQueueMessagesResponse StreamQueueMessage(Kubemq.StreamQueueMessagesRequest sr)
            throws SSLException, ServerAddressNotSuppliedException, TransactionException

    {

        if (reqStreamObserver == null) {
            state = TranState.StreamRegistered;
            reqStreamObserver = GetKubeMQAsyncClient().streamQueueMessage(respStreamObserver);

        } else {

        }

        reqStreamObserver.onNext(sr);

        synchronized (lock) {
            while (true) {
                try {
                    lock.wait();
                    if (state != TranState.InTransaction) {
                        if (state == TranState.UNAUTHENTICATED) {
                           throw new TransactionException("UNAUTHENTICATED");
                        }
                        return latestMsg;

                    } else {
                        break;
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
                } 
                // catch (AuthorizationException e) {
                //     e.printStackTrace();
                //     throw e;
                // }

                finally {

                }
            }
        }
        return latestMsg;
    }

}