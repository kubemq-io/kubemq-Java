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
import java.util.Dictionary;

import io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse;
import io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult;

public class SendBatchMessageResult {

	private QueueMessagesBatchResponse queueMessagesBatchResponse;

    public SendBatchMessageResult(QueueMessagesBatchResponse rec) {
        queueMessagesBatchResponse =rec;
    }  
    
    public String getBatchID(){
        return this.queueMessagesBatchResponse.getBatchID();
    }

    public Boolean getHaveErrors(){
        return this.queueMessagesBatchResponse.getHaveErrors();
    }

	public Iterable<SendMessageResult> getResults() {
        Collection<SendMessageResult> batch = new ArrayList<SendMessageResult>();
        for (SendQueueMessageResult var :  this.queueMessagesBatchResponse.getResultsList()) {
            batch.add(new SendMessageResult(var));            
        }
        return batch;       
	}
    





    
}