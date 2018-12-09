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
     * Initializes a new instance of the RequestParameters
     */
    RequestParameters() {
    }

    /**
     * Initializes a new instance of the RequestParameters
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
