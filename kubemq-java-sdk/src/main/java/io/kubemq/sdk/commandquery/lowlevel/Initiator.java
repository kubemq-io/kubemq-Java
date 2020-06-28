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
package io.kubemq.sdk.commandquery.lowlevel;

import io.kubemq.sdk.basic.GrpcClient;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.Response;
import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.grpc.Kubemq.PingResult;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;

/**
 * Represents the instance that is responsible to send requests to the kubemq.
 */
public class Initiator extends GrpcClient {

    private static Logger logger = LoggerFactory.getLogger(Initiator.class);

    /**
     * Initialize a new Initiator to send requests and handle response.
     * KubeMQAddress will be parsed from Config or environment parameter.
     */
    public Initiator() {
        this(null);
    }

    /**
     * Initialize a new Initiator to send requests and handle response.
     *
     * @param KubeMQAddress KubeMQ server address.
     */
    public Initiator(String KubeMQAddress) {
        this._kubemqAddress = KubeMQAddress;
    }

    /**
     * Initialize a new Initiator to send requests and handle response.
     *
     * @param KubeMQAddress KubeMQ server address.
     * @param authToken     Set KubeMQ JWT Auth token to be used for KubeMQ
     *                      connection.
     */
    public Initiator(String KubeMQAddress, String authToken) {
        this._kubemqAddress = KubeMQAddress;
        this.addAuthToken(authToken);
    }

    /**
     * Async publish a single request using the KubeMQ, response will return in the
     * passed observer.
     *
     * @param request                The io.kubemq.sdk.requestreply.lowlevel.request
     *                               that will be sent to the kubeMQ
     * @param responseStreamObserver Stream Observer for getting the response once
     *                               receiving response.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     */
    public void SendRequest(Request request, final StreamObserver<Response> responseStreamObserver)
            throws ServerAddressNotSuppliedException, SSLException {
        Kubemq.Request innerRequest = request.Convert();
        GetKubeMQAsyncClient().sendRequest(innerRequest, new StreamObserver<Kubemq.Response>() {
            @Override
            public void onNext(Kubemq.Response value) {
                responseStreamObserver.onNext(new Response(value));
            }

            @Override
            public void onError(Throwable t) {
                responseStreamObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseStreamObserver.onCompleted();
                try {
                    shutdown();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    shutdownNow();
                    // Preserve interrupt status
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    /**
     * Publish a single request using the KubeMQ.
     *
     * @param request The io.kubemq.sdk.requestreply.lowlevel.request that will be
     *                sent to the kubeMQ.
     * @return the response that received from the KubeMQ server for the request
     *         that was sent.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     * @throws InterruptedException
     */
    public Response SendRequest(Request request) throws SSLException, ServerAddressNotSuppliedException {

        Kubemq.Request innerRequest = request.Convert();

        // Send request and wait for response
        Kubemq.Response innerResponse = GetKubeMQClient().sendRequest(innerRequest);
        return new Response(innerResponse);

    }
    // convert InnerResponse to Response and return response to end user

    private void LogRequest(Request request) {
        logger.trace("Initiator->SendRequest. ID:'{}', Channel:'{}', ReplyChannel:'{}'", request.getRequestId(),
                request.getChannel(), request.getReplyChannel());
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
        return GetKubeMQClient().ping(null);
    }

}
