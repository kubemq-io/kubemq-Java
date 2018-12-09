package com.tradency.kubemq.sdk.commandquery;

public class RequestParameters {

    /**
     * Represents the limit for waiting for response (Milliseconds)
     */
    private Integer timeout;

    /**
     * Represents if the request should be saved from Cache and under what "Key"(String) to save it
     */
    private String cacheKey;

    /**
     * Cache time to live : for how long does the request should be saved in Cache
     */
    private Integer cacheTTL;

    /**
     * Initializes a new instance of the com.tradency.kubemq.sdk.commandquery.RequestParameters
     */
    RequestParameters() {
    }

    /**
     * Initializes a new instance of the com.tradency.kubemq.sdk.commandquery.RequestParameters
     * with a set of chosen parameters to create a RequestChannel
     *
     * @param timeout  Represents the limit for waiting for response (Milliseconds)
     * @param cacheKey Represents if the request should be saved from Cache and under what "Key"(String) to save it
     * @param cacheTTL Cache time to live : for how long does the request should be saved in Cache
     */
    RequestParameters(int timeout, String cacheKey, int cacheTTL) {
        setTimeout(timeout);
        setCacheKey(cacheKey);
        setCacheTTL(cacheTTL);
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }

    public Integer getCacheTTL() {
        return cacheTTL;
    }

    public void setCacheTTL(Integer cacheTTL) {
        this.cacheTTL = cacheTTL;
    }

}
