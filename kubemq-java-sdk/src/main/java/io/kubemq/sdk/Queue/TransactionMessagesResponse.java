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

import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse;

/**
 * Transaction response
 */
public class TransactionMessagesResponse {
    private StreamQueueMessagesResponse streamQueueMessagesResponse;
    private Message message;

	protected TransactionMessagesResponse(Kubemq.StreamQueueMessagesResponse streamQueueMessagesResponse){
        this.streamQueueMessagesResponse = streamQueueMessagesResponse;        
    }

    protected TransactionMessagesResponse(String errorMessage, Message msg, String requestID){
        this.streamQueueMessagesResponse=   Kubemq.StreamQueueMessagesResponse.newBuilder()
        .setIsError(true)     
        .setError(errorMessage)
        .setRequestID(requestID != null ? requestID: "")
        .build();
        this.message = msg;
    }

    /**
     * Unique for Request
     * @return Request ID.
     */
    public String getRequestID() {
        return this.streamQueueMessagesResponse.getRequestID();
    }
  
      /**
     * Returned from KubeMQ, false if no error.
     * 
     * @return False if no error.
     */
    public Boolean getIsError() {
        return this.streamQueueMessagesResponse.getIsError();
    }

    /**
     * Error message, valid only if IsError true.
     * 
     * @return Error message.
     */
    public String getError() {
        return this.streamQueueMessagesResponse.getError();
    }

    /**
     * The received Message
     * @return Transaction message.
     */
    public  Message getMessage(){
        if (this.message==null){
            this.message =new Message(this.streamQueueMessagesResponse.getMessage());
        }
        return message;
    }
    /**
     * Request action: ReceiveMessage, AckMessage, RejectMessage, ModifyVisibility, ResendMessage,  SendModifiedMessage, Unknown
     * @return ReceiveMessage, AckMessage, RejectMessage, ModifyVisibility, ResendMessage,  SendModifiedMessage, Unknown.
     */
    public Kubemq.StreamRequestType gStreamRequestType() {
        return this.streamQueueMessagesResponse.getStreamRequestTypeData();
    }


}