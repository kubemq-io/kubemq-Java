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
package io.kubemq.sdk.event.lowlevel;

import io.kubemq.sdk.basic.GrpcClient;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.Result;
import io.kubemq.sdk.grpc.Kubemq;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;

/**
 * Represents the instance that is responsible to send messages to the kubemq.
 */
public class Sender extends GrpcClient {

    private static Logger logger = LoggerFactory.getLogger(Sender.class);

    /**
     * Initialize a new Sender.
     * KubeMQAddress will be parsed from Config or environment parameter.
     */
    public Sender() {
        this(null);
    }

    /**
     * Initialize a new Sender under the requested KubeMQ Server Address.
     *
     * @param KubeMQAddress KubeMQ server address.
     */
    public Sender(String KubeMQAddress) {
        this._kubemqAddress = KubeMQAddress;
    }

    /**
     * Publish a single message using the KubeMQ.
     *
     * @param notification Event to add to the queue
     * @return io.kubemq.sdk.event.MessageDeliveryReport that contain info regarding message status.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public Result SendEvent(Event notification) throws ServerAddressNotSuppliedException, SSLException {
        Kubemq.Event event = notification.ToInnerEvent();
        Kubemq.Result innerResult = GetKubeMQClient().sendEvent(event);

        if (innerResult == null) {
            return null;
        }

        return new Result(innerResult);
    }

    /**
     * Publish a single message using the KubeMQ without waiting for response.
     *
     * @param notification Event to add to the queue
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public void StreamEventWithoutResponse(Event notification) throws ServerAddressNotSuppliedException, SSLException {
        notification.setReturnResult(false);
        GetKubeMQAsyncClient().sendEventsStream(null).onNext(notification.ToInnerEvent());
    }

    /**
     * Publish a constant stream of messages.
     *
     * @param messageDeliveryReportStreamObserver Observer for Delivery Reports.
     * @return StreamObserver used to stream messages
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public StreamObserver<Event> StreamEvent(final StreamObserver<Result> messageDeliveryReportStreamObserver) throws ServerAddressNotSuppliedException, SSLException {
        StreamObserver<Kubemq.Event> streamObserver = GetKubeMQAsyncClient().sendEventsStream(new StreamObserver<Kubemq.Result>() {
            @Override
            public void onNext(Kubemq.Result value) {
                LogResponse(value);

                // Convert KubeMQ.Grpc.MessageDeliveryReport to outer Report
                Result result = new Result(value);

                // Activate end-user Delivery Report Delegate
                messageDeliveryReportStreamObserver.onNext(result);
            }

            @Override
            public void onError(Throwable t) {
                logger.error(t.getMessage());
                messageDeliveryReportStreamObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                logger.trace("StreamMessageWithoutDeliveryReport completed.");
                messageDeliveryReportStreamObserver.onCompleted();
            }
        });

        return new StreamObserver<Event>() {
            @Override
            public void onNext(Event notification) {
                if (notification.isReturnResult()) {
                    streamObserver.onNext(notification.ToInnerEvent());
                }
            }

            @Override
            public void onError(Throwable t) {
                streamObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                streamObserver.onCompleted();
            }
        };
    }

    private void LogResponse(Kubemq.Result response) {
        logger.info(
                "Sender received 'Delivery Report': EventID:'{}', Sent: '{}', Error:'{}'",
                response.getEventID(),
                response.getSent(),
                response.getError()
        );
    }
}
