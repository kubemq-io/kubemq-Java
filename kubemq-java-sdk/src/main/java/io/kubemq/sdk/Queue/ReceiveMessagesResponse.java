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

import java.util.ArrayList;
import java.util.Collection;

import io.kubemq.sdk.grpc.Kubemq.QueueMessage;
import io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse;

/**
 * Queue response.
 */
public class ReceiveMessagesResponse {

    private ReceiveQueueMessagesResponse receiveQueueMessagesResponse;

    protected ReceiveMessagesResponse(ReceiveQueueMessagesResponse rec) {
        receiveQueueMessagesResponse = rec;
    }

    /**
     * Unique for Request
     * 
     * @return Request ID.
     */
    public String getRequestID() {
        return this.receiveQueueMessagesResponse.getRequestID();
    }

    /**
     * Returned from KubeMQ, false if no error.
     * 
     * @return False if no error.
     */
    public Boolean getIsError() {
        return this.receiveQueueMessagesResponse.getIsError();
    }

    /**
     * Error message, valid only if IsError true.
     * 
     * @return Error message.
     */
    public String getError() {
        return this.receiveQueueMessagesResponse.getError();
    }

    /**
     * Indicate if the request was peek, true if peek.
     * 
     * @return True if peek.
     */
    public Boolean getIsPeek() {
        return this.receiveQueueMessagesResponse.getIsPeak();
    }

    /**
     * Collection of Messages.
     * 
     * @return Collection of Messages.
     */
    public Iterable<? extends Message> getMessages() {

        Collection<Message> cltn = new ArrayList<Message>(); 
        for (QueueMessage queueMessage : this.receiveQueueMessagesResponse.getMessagesList()) {
            cltn.add(new Message(queueMessage));
        }
        return cltn;
    }

    /**
     * Count of messages expired.
     * 
     * @return Number of messages expired.
     */
    public int getMessagesExpired() {
        return this.receiveQueueMessagesResponse.getMessagesExpired();
    }

    /**
     * Count of received messages.
     * 
     * @return Number of received messages.
     */
    public int getMessagesReceived() {
        return this.receiveQueueMessagesResponse.getMessagesReceived();
    }

}