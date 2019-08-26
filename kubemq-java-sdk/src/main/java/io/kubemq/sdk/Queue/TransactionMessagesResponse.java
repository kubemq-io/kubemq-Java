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

public class TransactionMessagesResponse {
    private StreamQueueMessagesResponse streamQueueMessagesResponse;
    private Message message;

	public TransactionMessagesResponse(Kubemq.StreamQueueMessagesResponse streamQueueMessagesResponse){
        this.streamQueueMessagesResponse = streamQueueMessagesResponse;        
    }
    public TransactionMessagesResponse(String errorMessage, Message msg, String requestID){
        this.streamQueueMessagesResponse=   Kubemq.StreamQueueMessagesResponse.newBuilder()
        .setIsError(true)     
        .setError(errorMessage)
        .setRequestID(requestID)
        .build();
        this.message = msg;
    }

    public String getRequestID() {
        return this.streamQueueMessagesResponse.getRequestID();
    }
  
    public Boolean getIsError(){
        return this.streamQueueMessagesResponse.getIsError();    
      }
    public String getError(){
        return this.streamQueueMessagesResponse.getError();    
    } 

    public  Message getMessage(){
        if (this.message==null){
            this.message =new Message(this.streamQueueMessagesResponse.getMessage());
        }
        return message;
    }
    public Kubemq.StreamRequestType gStreamRequestType() {
        return this.streamQueueMessagesResponse.getStreamRequestTypeData();
    }


}