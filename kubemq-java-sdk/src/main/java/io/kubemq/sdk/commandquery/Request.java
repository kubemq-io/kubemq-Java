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

/**
 * Represents the Request used in requestreply io.kubemq.sdk.requestreply.channel
 */
public class Request {

    /**
     * Represents a Request identifier
     */
    private String requestId;

    /**
     * Represents text as String
     */
    private String metadata;

    /**
     * Represents The content of the Request
     */
    private byte[] body;

    /**
     * Initializes a new instance of the Request
     * for io.kubemq.sdk.requestreply.channel use
     */
    public Request() {

    }

    /**
     * Initializes a new instance of the Request
     * for io.kubemq.sdk.requestreply.channel use with a set of parameters
     *
     * @param requestId Represents a Request identifier
     * @param metadata  Represents text as String
     * @param body      Represents The content of the Request
     */
    public Request(String requestId, String metadata, byte[] body) {
        setRequestId(requestId);
        setMetadata(metadata);
        setBody(body);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}
