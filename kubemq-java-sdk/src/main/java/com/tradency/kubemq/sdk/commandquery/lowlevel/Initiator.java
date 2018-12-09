package com.tradency.kubemq.sdk.commandquery.lowlevel;

import com.tradency.kubemq.sdk.basic.GrpcClient;
import com.tradency.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import com.tradency.kubemq.sdk.commandquery.Response;
import com.tradency.kubemq.sdk.grpc.Kubemq;
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
        _kubemqAddress = KubeMQAddress;
    }

    /**
     * Async publish a single request using the KubeMQ, response will return in the passed observer.
     *
     * @param request                The com.tradency.kubemq.sdk.requestreply.lowlevel.request that will be sent to the kubeMQ
     * @param responseStreamObserver Stream Observer for getting the response once receiving response.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public void SendRequest(Request request, final StreamObserver<Response> responseStreamObserver) throws ServerAddressNotSuppliedException, SSLException {
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
            }
        });
    }

    /**
     * Publish a single request using the KubeMQ.
     *
     * @param request The com.tradency.kubemq.sdk.requestreply.lowlevel.request that will be sent to the kubeMQ.
     * @return the response that received from the KubeMQ server for the request that was sent.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public Response SendRequest(Request request) throws ServerAddressNotSuppliedException, SSLException {

        Kubemq.Request innerRequest = request.Convert();

        // Send request and wait for response
        Kubemq.Response innerResponse = GetKubeMQClient().sendRequest(innerRequest);

        // convert InnerResponse to Response and return response to end user
        return new Response(innerResponse);
    }

    private void LogRequest(Request request) {
        logger.trace(
                "Initiator->SendRequest. ID:'{}', Channel:'{}', ReplyChannel:'{}'",
                request.getRequestId(),
                request.getChannel(),
                request.getReplyChannel()
        );
    }

}
