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
package io.kubemq.sdk.commandquery;

public class ChannelParameters {

    /**
     * Represents the type of request operation using RequestType.
     */
    private RequestType requestType;

    /**
     * Represents The channel name to send to using the KubeMQ
     */
    private String channelName;
    /**
     * Represents the sender ID that the messages will be send under
     */
    private String clientID;
    /**
     * Represents the limit for waiting for response (Milliseconds)
     */
    private int timeout;
    /**
     * Represents if the request should be saved from Cache and under what "Key"(String) to save it
     */
    private String cacheKey;
    /**
     * Cache time to live : for how long does the request should be saved in Cache
     */
    private int cacheTTL;
    /**
     * Represents The address of the KubeMQ server
     */
    private String kubeMQAddress;
    /**
     * Represents JWT token to be used for KubeMQ authentication.
     */
    private String authToken;


    /**
     * Initializes a new instance of the io.kubemq.sdk.commandquery.RequestChannelParameters class
     */
    public ChannelParameters() {
    }

    /**
     * Initializes a new instance of the io.kubemq.sdk.commandquery.RequestChannelParameters class
     * with set parameters
     *
     * @param requestType   Represents the type of request operation using RequestType.
     * @param channelName   Represents The channel name to send to using the KubeMQ
     * @param clientID      Represents the sender ID that the messages will be send under
     * @param timeout       Represents the limit for waiting for response (Milliseconds)
     * @param cacheKey      Cache time to live : for how long does the request should be saved in Cache
     * @param cacheTTL      Represents The address of the KubeMQ server
     * @param kubeMQAddress Represents The address of the KubeMQ server
     * @param authToken Represents JWT token to be used for KubeMQ authentication.
     */
    public ChannelParameters(RequestType requestType, String channelName, String clientID, int timeout, String cacheKey, int cacheTTL, String kubeMQAddress, String authToken) {
        this.requestType = requestType;
        this.channelName = channelName;
        this.clientID = clientID;
        this.timeout = timeout;
        this.cacheKey = cacheKey;
        this.cacheTTL = cacheTTL;
        this.kubeMQAddress = kubeMQAddress;
        this.authToken = authToken;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
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

    public String getKubeMQAddress() {
        return kubeMQAddress;
    }

    public void setKubeMQAddress(String kubeMQAddress) {
        this.kubeMQAddress = kubeMQAddress;
    }

    public String getAuthToken(){
        return this.authToken;
    }

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }
  

}
