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
package io.kubemq.sdk.event;

import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.lowlevel.Sender;
import io.kubemq.sdk.grpc.Kubemq.PingResult;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.SSLException;

/**
 * Represents a Sender with predefined parameters.
 */
public class Channel {

    private Sender sender;

    private String channelName;
    private String clientID;
    private boolean store;
    private boolean returnResult;

    public Channel(ChannelParameters parameters) throws SSLException, ServerAddressNotSuppliedException {
        this(
                parameters.getChannelName(),
                parameters.getClientID(),
                parameters.isStore(),
                parameters.getKubeMQAddress(),
                parameters.getAuthToken()
        );
    }

    /**
     * Initializes a new instance of the MessageChannel class using a set of
     * parameters.
     *
     * @param channelName   Represents The channel name to send to using the KubeMQ.
     * @param clientID      Represents the sender ID that the messages will be send
     *                      under.
     * @param store         Represents if the messages should be set to persistence.
     * @param kubeMQAddress Represents The address of the KubeMQ server.
     * @param authToken     Set KubeMQ JWT Auth token to be used for KubeMQ
     *                      connection.
     * @throws ServerAddressNotSuppliedException
     * @throws SSLException
     */
    public Channel(String channelName, String clientID, boolean store, String kubeMQAddress, String authToken)
            throws SSLException, ServerAddressNotSuppliedException {
        this.channelName = channelName;
        this.clientID = clientID;
        this.store = store;     

        isValid();

        sender = new Sender(kubeMQAddress, authToken);
    }

      /**
     * Initializes a new instance of the MessageChannel class using a set of
     * parameters.
     *
     * @param channelName   Represents The channel name to send to using the KubeMQ.
     * @param clientID      Represents the sender ID that the messages will be send
     *                      under.
     * @param store         Represents if the messages should be set to persistence.
     * @param kubeMQAddress Represents The address of the KubeMQ server.    
     * @throws ServerAddressNotSuppliedException
     * @throws SSLException
     */
    public Channel(String channelName, String clientID, boolean store, String kubeMQAddress){
        this.channelName = channelName;
        this.clientID = clientID;
        this.store = store;     

        isValid();

        sender = new Sender(kubeMQAddress, null);
    }

    /**
     * Send a single message using the KubeMQ.
     *
     * @param event The io.kubemq.sdk.pubsub.Event to send using KubeMQ.
     * @return io.kubemq.sdk.event.MessageDeliveryReport that contain info regarding message status.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public Result SendEvent(Event event) throws ServerAddressNotSuppliedException, SSLException {
        return sender.SendEvent(CreateLowLevelEvent(event));
    }

    public Result SendEvent(Event event, boolean returnResult) throws ServerAddressNotSuppliedException, SSLException {
        return sender.SendEvent(CreateLowLevelEvent(event, returnResult));
    }

    /**
     * Publish constant stream of messages.
     *
     * @param messageDeliveryReportStreamObserver Observer for Delivery Reports.
     * @return StreamObserver used to stream messages
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public StreamObserver<Event> StreamEvent(final StreamObserver<Result> messageDeliveryReportStreamObserver) throws ServerAddressNotSuppliedException, SSLException {
        StreamObserver<io.kubemq.sdk.event.lowlevel.Event> observer = sender.StreamEvent(messageDeliveryReportStreamObserver);
        return new StreamObserver<Event>() {

            @Override
            public void onNext(Event value) {
                observer.onNext(CreateLowLevelEvent(value));
            }

            @Override
            public void onError(Throwable t) {
                observer.onError(t);
            }

            @Override
            public void onCompleted() {
                observer.onCompleted();
            }
        };
    }

    /**
     * Publish constant stream of messages.
     *
     * @param messageDeliveryReportStreamObserver Observer for Delivery Reports.
     * @param returnResult                        Represents if the end user does not need the Result.
     * @return StreamObserver used to stream messages
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public StreamObserver<Event> StreamEvent(final StreamObserver<Result> messageDeliveryReportStreamObserver, boolean returnResult) throws ServerAddressNotSuppliedException, SSLException {
        StreamObserver<io.kubemq.sdk.event.lowlevel.Event> observer = sender.StreamEvent(messageDeliveryReportStreamObserver);
        return new StreamObserver<Event>() {

            @Override
            public void onNext(Event value) {
                observer.onNext(CreateLowLevelEvent(value, returnResult));
            }

            @Override
            public void onError(Throwable t) {
                observer.onError(t);
            }

            @Override
            public void onCompleted() {
                observer.onCompleted();
            }
        };
    }

   /**
     * Ping check Kubemq response.
     * 
     * @return PingResult
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     */
    public PingResult Ping() throws SSLException, ServerAddressNotSuppliedException {
        return sender.Ping();
    }

    private void isValid() {
        if (StringUtils.isEmpty(channelName)) {
            throw new IllegalArgumentException("Parameter channelName is mandatory");
        }
    }

    private io.kubemq.sdk.event.lowlevel.Event CreateLowLevelEvent(Event notification) {
        return new io.kubemq.sdk.event.lowlevel.Event(
                getChannelName(),
                notification.getMetadata(),
                notification.getBody(),
                notification.getEventId(),
                getClientID(),              
                isStore(),
                notification.getTags()
        );
    }


    private io.kubemq.sdk.event.lowlevel.Event CreateLowLevelEvent(Event notification, boolean returnResult) {
        io.kubemq.sdk.event.lowlevel.Event event = CreateLowLevelEvent(notification);
        event.setReturnResult(returnResult);
        return event;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }

    public boolean isReturnResult() {
        return returnResult;
    }

    public void setReturnResult(boolean returnResult) {
        this.returnResult = returnResult;
    }

}
