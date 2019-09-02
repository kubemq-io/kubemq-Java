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

import io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult;

/**
 * Queue request execution result.
 */
public class SendMessageResult {    
    private SendQueueMessageResult sendQueueMessageResult;

    protected SendMessageResult(SendQueueMessageResult rec) {
        this.sendQueueMessageResult = rec;
    }
    /**
     * Unique for message
     * @return Message ID.
     */
    public String getMessageID() {
        return this.sendQueueMessageResult.getMessageID();
    }
    
    /**
     * Returned from KubeMQ, false if no error.
     * @return False if no error.
     */
    public Boolean getIsError(){
      return this.sendQueueMessageResult.getIsError();    
    }
    /**
     * Error message, valid only if IsError true.
     * @return Error message.
     */
    public String getError(){
        return this.sendQueueMessageResult.getError();    
      }
      /**
       * Message expiration time.
       * @return Message expiration in Unix format.
       */
    public Long getExpirationAt(){
      return this.sendQueueMessageResult.getExpirationAt();
    }
  /**
   * Message sent time.
   * @return Message sent in Unix format.
   */
    public Long getSentAt(){
      return this.sendQueueMessageResult.getSentAt();
    }
  
    /**
     * Message delayed delivery by KubeMQ.
     * @return Delayed delivery in Unix format.
     */
    public long getDelayedTo() {
        return this.sendQueueMessageResult.getDelayedTo();
  }
	

    

}