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
package io.kubemq.sdk.Queue;

import io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse;

/**
 * Queue purge messages request execution result (will not delete data).
 */
public class AckAllMessagesResponse {

	private AckAllQueueMessagesResponse ackAllQueueMessagesResponse;

    protected AckAllMessagesResponse(AckAllQueueMessagesResponse rec) {
        ackAllQueueMessagesResponse =rec;        
    }
    
    /**
     * Unique for Request.
     * @return Request ID.
     */
    public String getRequestID() {
        return this.ackAllQueueMessagesResponse.getRequestID();
    }
   /**
     * Returned from KubeMQ, false if no error.
     * 
     * @return False if no error.
     */
    public Boolean getIsError() {
        return this.ackAllQueueMessagesResponse.getIsError();
    }

    /**
     * Error message, valid only if IsError true.
     * 
     * @return Error message.
     */
    public String getError() {
        return this.ackAllQueueMessagesResponse.getError();
    }

    /**
     * Number of affected messages.
     * @return Number of affected messages.
     */
    public long getAffectedMessages(){
        return this.ackAllQueueMessagesResponse.getAffectedMessages();
    }

}