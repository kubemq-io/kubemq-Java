package com.tradency.kubemq.sdk.commandquery;

import com.tradency.kubemq.sdk.basic.GrpcClient;
import com.tradency.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import com.tradency.kubemq.sdk.grpc.Kubemq;
import com.tradency.kubemq.sdk.grpc.kubemqGrpc;
import com.tradency.kubemq.sdk.subscription.SubscribeRequest;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.util.Iterator;

/**
 * An instance that responsible on receiving request from the kubeMQ.
 */
public class Responder extends GrpcClient {

    private static Logger logger = LoggerFactory.getLogger(Responder.class);

    /**
     * Initialize a new Responder to subscribe to Response.
     * KubeMQAddress will be parsed from Config or environment parameter.
     */
    public Responder() {
        this(null);
    }

    /**
     * Initialize a new Responder to subscribe to Response.
     *
     * @param KubeMQAddress KubeMQ server address.
     */
    public Responder(String KubeMQAddress) {
        _kubemqAddress = KubeMQAddress;
    }

    /**
     * Register to kubeMQ Channel using com.tradency.kubemq.sdk.subscription.SubscribeRequest.
     *
     * @param subscribeRequest        list represent by com.tradency.kubemq.sdk.subscription.SubscribeRequest that will determine the subscription configuration.
     * @param requestResponseObserver RequestResponseObserver to perform when receiving com.tradency.kubemq.sdk.requestreplay.RequestReceive.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public void SubscribeToRequests(SubscribeRequest subscribeRequest, final RequestResponseObserver requestResponseObserver) throws ServerAddressNotSuppliedException, SSLException {

        ValidateSubscribeRequest(subscribeRequest);

        Kubemq.Subscribe innerSubscribeRequest = subscribeRequest.ToInnerSubscribeRequest();

        Iterator<Kubemq.Request> call = GetKubeMQClient().subscribeToRequests(innerSubscribeRequest);

        // await for requests form GRPC stream.
        while (call.hasNext()) {
            // Received requests form GRPC stream.
            Kubemq.Request innerRequest = call.next();

            LogRequest(innerRequest);

            // Convert KubeMQ.Grpc.Request to RequestReceive
            RequestReceive request = new RequestReceive(innerRequest);

            // Activate end-user request handler and receive the response
            Response response = requestResponseObserver.onNext(request);

            // Convert
            Kubemq.Response innerResponse = response.Convert();

            LogResponse(innerResponse);

            // Send Response via GRPC
            GetKubeMQClient().sendResponse(innerResponse);
        }
    }

    public void SubscribeToRequestsAsync(SubscribeRequest subscribeRequest, final RequestResponseAsyncObserver requestResponseAsyncObserver) throws ServerAddressNotSuppliedException, SSLException {

        ValidateSubscribeRequest(subscribeRequest);

        Kubemq.Subscribe innerSubscribeRequest = subscribeRequest.ToInnerSubscribeRequest();

        kubemqGrpc.kubemqStub client = GetKubeMQAsyncClient();

        // await for requests form GRPC stream.
        client.subscribeToRequests(innerSubscribeRequest, new StreamObserver<Kubemq.Request>() {
            @Override
            public void onNext(Kubemq.Request innerRequest) {
                LogRequest(innerRequest);

                // Convert KubeMQ.Grpc.Request to RequestReceive
                RequestReceive request = new RequestReceive(innerRequest);

                // Activate end-user request handler and receive the response
                Response response = requestResponseAsyncObserver.onNext(request);

                // Convert
                Kubemq.Response innerResponse = response.Convert();

                LogResponse(innerResponse);

                // Send Response via GRPC
                client.sendResponse(innerResponse, new StreamObserver<Kubemq.Empty>() {
                    @Override
                    public void onNext(Kubemq.Empty value) {
                        logger.trace("Response successfully sent.");
                    }

                    @Override
                    public void onError(Throwable t) {
                        logger.trace("Response sent with error {}.", t.getMessage());
                    }

                    @Override
                    public void onCompleted() {
                        logger.trace("Sending response completed.");
                    }
                });
            }

            @Override
            public void onError(Throwable t) {
                requestResponseAsyncObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                requestResponseAsyncObserver.onCompleted();
            }
        });
    }

    private void ValidateSubscribeRequest(SubscribeRequest subscribeRequest) {
        if (StringUtils.isBlank(subscribeRequest.getChannel())) {
            throw new IllegalArgumentException("Parameter Channel is mandatory");
        }

        if (subscribeRequest.IsNotValidType("CommandQuery"))// SubscribeType
        {
            throw new IllegalArgumentException("Invalid Subscribe Type for this Class");
        }
    }

    private void LogRequest(Kubemq.Request request) {
        logger.trace(
                "Responder InnerRequest. RequestID:'{}', Channel:'{}', ReplyChannel:'{}'",
                request.getRequestID(),
                request.getChannel(),
                request.getReplyChannel()
        );
    }

    private void LogResponse(Kubemq.Response response) {
        logger.trace(
                "Responder InnerResponse. ID:'{}', ReplyChannel:'{}'",
                response.getRequestID(),
                response.getReplyChannel()
        );
    }

    public interface RequestResponseObserver {
        Response onNext(RequestReceive requestReceive);
    }

    public interface RequestResponseAsyncObserver {
        Response onNext(RequestReceive requestReceive);

        void onError(Throwable t);

        void onCompleted();
    }

}
