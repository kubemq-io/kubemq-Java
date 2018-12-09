package com.tradency.kubemq.sdk.commandquery;

import com.tradency.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import com.tradency.kubemq.sdk.commandquery.lowlevel.Initiator;
import com.tradency.kubemq.sdk.commandquery.lowlevel.Request;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.SSLException;
import java.text.MessageFormat;

/**
 * Represents a Initiator with predefined parameters.
 */
public class Channel {

    private Initiator _initiator;

    private RequestType requestType;

    private String channelName;
    private String clientId;
    private int timeout;
    private String cacheKey;
    private int cacheTTL;

    /**
     * Initializes a new instance of the RequestChannel class using RequestChannelParameters.
     *
     * @param parameters Channel Parameters
     */
    public Channel(ChannelParameters parameters) {
        this(
                parameters.getRequestType(),
                parameters.getChannelName(),
                parameters.getClientID(),
                parameters.getTimeout(),
                parameters.getCacheKey(),
                parameters.getCacheTTL(),
                parameters.getKubeMQAddress()
        );
    }

    /**
     * Initializes a new instance of the RequestChannel class using a set of parameters.
     *
     * @param channelName   Represents The channel name to send to using the KubeMQ.
     * @param clientId      Represents the sender ID that the Request will be send under.
     * @param timeout       Represents the limit for waiting for response (Milliseconds).
     * @param cacheKey      Represents if the request should be saved from Cache and under what "Key"(java.lang.String) to save it.
     * @param cacheTTL      Cache time to live : for how long does the request should be saved in Cache
     * @param kubeMQAddress KubeMQ server address.
     */
    public Channel(RequestType requestsType, String channelName, String clientId, int timeout, String cacheKey, int cacheTTL, String kubeMQAddress) {
        this.requestType = requestsType;
        this.channelName = channelName;
        this.clientId = clientId;
        this.timeout = timeout;
        this.cacheKey = cacheKey;
        this.cacheTTL = cacheTTL;

        isValid();

        _initiator = new Initiator(kubeMQAddress);
    }

    /**
     * Send a single blocking request using the KubeMQ.\
     * response will return as StreamObserver.
     *
     * @param request  The com.tradency.kubemq.sdk.requestreply.lowlevel.request that will be sent to the kubeMQ.
     * @param response StreamObserver for the response for the request that was sent.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public void SendRequest(com.tradency.kubemq.sdk.commandquery.Request request, final StreamObserver<Response> response) throws ServerAddressNotSuppliedException, SSLException {
        response.onNext(_initiator.SendRequest(CreateLowLevelRequest(request)));
    }

    /**
     * Send a single blocking request using the KubeMQ with override parameters.
     * response will return as StreamObserver.
     *
     * @param request        The com.tradency.kubemq.sdk.requestreply.lowlevel.request that will be sent to the kubeMQ.
     * @param overrideParams Allow overwriting "Timeout" "CacheKey" and "CacheTTL" for a single Request.
     * @param response       StreamObserver for the response for the request that was sent.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public void SendRequest(com.tradency.kubemq.sdk.commandquery.Request request, RequestParameters overrideParams, final StreamObserver<Response> response) throws ServerAddressNotSuppliedException, SSLException {
        response.onNext(_initiator.SendRequest(CreateLowLevelRequest(request, overrideParams)));
    }

    /**
     * Send a single async request using the KubeMQ.
     *
     * @param request  The com.tradency.kubemq.sdk.requestreply.lowlevel.request that will be sent to the kubeMQ.
     * @param response StreamObserver for the response for the request that was sent.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public void SendRequestAsync(com.tradency.kubemq.sdk.commandquery.Request request, final StreamObserver<Response> response) throws ServerAddressNotSuppliedException, SSLException {
        _initiator.SendRequest(CreateLowLevelRequest(request), response);
    }

    /**
     * Send a single async request using the KubeMQ with override parameters.
     *
     * @param request        The com.tradency.kubemq.sdk.requestreply.lowlevel.request that will be sent to the kubeMQ.
     * @param overrideParams Allow overwriting "Timeout" "CacheKey" and "CacheTTL" for a single Request.
     * @param response       StreamObserver for the response for the request that was sent.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public void SendRequestAsync(com.tradency.kubemq.sdk.commandquery.Request request, RequestParameters overrideParams, final StreamObserver<Response> response) throws ServerAddressNotSuppliedException, SSLException {
        _initiator.SendRequest(CreateLowLevelRequest(request, overrideParams), response);
    }

    /**
     * Send a single request using the KubeMQ.
     *
     * @param request The com.tradency.kubemq.sdk.requestreply.lowlevel.request that will be sent to the kubeMQ.
     * @return response for the request that was sent.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public Response SendRequest(com.tradency.kubemq.sdk.commandquery.Request request) throws ServerAddressNotSuppliedException, SSLException {
        return _initiator.SendRequest(CreateLowLevelRequest(request));
    }

    /**
     * Send a single request using the KubeMQ with override parameters.
     *
     * @param request        The com.tradency.kubemq.sdk.requestreply.lowlevel.request that will be sent to the kubeMQ.
     * @param overrideParams Allow overwriting "Timeout" "CacheKey" and "CacheTTL" for a single Request.
     * @return response for the request that was sent.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    public Response SendRequest(com.tradency.kubemq.sdk.commandquery.Request request, RequestParameters overrideParams) throws ServerAddressNotSuppliedException, SSLException {
        return _initiator.SendRequest(CreateLowLevelRequest(request, overrideParams));
    }

    private void isValid() {
        if (StringUtils.isEmpty(channelName)) {
            throw new IllegalArgumentException("Parameter channelName is mandatory");
        }
        if (requestType == RequestType.RequestTypeUnknown) {
            throw new IllegalArgumentException("Invalid Request Type");
        }
        if (timeout < 0) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "Parameter timeout must be between 1 and {0}",
                    Integer.MAX_VALUE
            ));
        }
    }

    private Request CreateLowLevelRequest(com.tradency.kubemq.sdk.commandquery.Request request) {
        Request innerRequest = new Request();

        innerRequest.setRequestType(this.requestType);
        innerRequest.setChannel(channelName);
        innerRequest.setClientId(clientId);
        innerRequest.setTimeout(timeout);
        innerRequest.setCacheKey(cacheKey);
        innerRequest.setCacheTTL(cacheTTL);
        innerRequest.setRequestId(request.getRequestId());
        innerRequest.setBody(request.getBody());
        innerRequest.setMetadata(request.getMetadata());

        return innerRequest;
    }


    private Request CreateLowLevelRequest(com.tradency.kubemq.sdk.commandquery.Request request, RequestParameters overrideParams) {
        Request req = CreateLowLevelRequest(request);

        if (overrideParams.getTimeout() != null) {
            req.setTimeout(overrideParams.getTimeout());
        }

        if (overrideParams.getCacheKey() != null) {
            req.setCacheKey(overrideParams.getCacheKey());
        }

        if (overrideParams.getCacheTTL() != null) {
            req.setCacheTTL(overrideParams.getCacheTTL());
        }

        return req;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public int getCacheTTL() {
        return cacheTTL;
    }

    public void setCacheTTL(int cacheTTL) {
        this.cacheTTL = cacheTTL;
    }


}
