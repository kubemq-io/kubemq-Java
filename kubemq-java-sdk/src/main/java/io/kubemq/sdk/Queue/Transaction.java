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

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLException;

import io.grpc.ClientCall;
import io.grpc.netty.shaded.io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.WaitStrategy;
import io.grpc.stub.ClientCalls;
import io.grpc.stub.StreamObserver;
import io.kubemq.sdk.basic.GrpcClient;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;

import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest;
import io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse;
import io.kubemq.sdk.grpc.kubemqGrpc.kubemqStub;
import io.kubemq.sdk.tools.IDGenerator;
import io.kubemq.sdk.grpc.kubemqGrpc;


public class Transaction extends GrpcClient {
    static Semaphore semaphore = new Semaphore(1);

    private Queue queue;
    protected StreamQueueMessagesResponse msg;
    private  StreamObserver<StreamQueueMessagesResponse> resp;
    private StreamObserver<StreamQueueMessagesRequest> reqStreamObserver; 

    protected Transaction(Queue queue) throws ServerAddressNotSuppliedException {
        this.queue = queue;
        this._kubemqAddress=queue.getServerAddress();
    }

    public TransactionMessagesResponse Modify(Message setMetadata)
            throws SSLException, ServerAddressNotSuppliedException {
        if(!OpenStream())     {
            return new TransactionMessagesResponse("active queue message wait for ack/reject",null,null);
        }
        Kubemq.StreamQueueMessagesResponse resp;
        try {
            resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder()
                    .setClientID(this.queue.getClientID())                   
                    .setRequestID(IDGenerator.Getid())
                    .setStreamRequestTypeData(Kubemq.StreamRequestType.SendModifiedMessage)
                    .setModifiedMessage(setMetadata.toQueueMessage())
                    .build());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

       
        return new TransactionMessagesResponse(resp);
	}

	public TransactionMessagesResponse ExtendVisibility(int visibilitySeconds)
            throws SSLException, ServerAddressNotSuppliedException {
        if(!OpenStream())     {
            return new TransactionMessagesResponse("active queue message wait for ack/reject",null,null);
        }
        Kubemq.StreamQueueMessagesResponse resp;
        try {
            resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder()
                    .setClientID(this.queue.getClientID())
                    .setChannel(this.queue.getQueueName())
                    .setRequestID(IDGenerator.Getid())
                    .setStreamRequestTypeData(Kubemq.StreamRequestType.ModifyVisibility)
                    .build());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

       
        return new TransactionMessagesResponse(resp);

	}

    public TransactionMessagesResponse Receive(Integer visibilitySeconds, Integer waitTimeSeconds)
            throws SSLException, ServerAddressNotSuppliedException {
        if(!OpenStream())     {
            return new TransactionMessagesResponse("active queue message wait for ack/reject",null,null);
        }
        Kubemq.StreamQueueMessagesResponse resp;
        try {
            resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder()
                    .setClientID(this.queue.getClientID())
                    .setChannel(this.queue.getQueueName())
                    .setRequestID(IDGenerator.Getid())
                    .setStreamRequestTypeData(Kubemq.StreamRequestType.ReceiveMessage)
                    .setVisibilitySeconds(visibilitySeconds)
                    .setWaitTimeSeconds(waitTimeSeconds).build());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

       
        return new TransactionMessagesResponse(resp);

    }

    public TransactionMessagesResponse AckMessage()
            throws SSLException, ServerAddressNotSuppliedException {
        if(!OpenStream())     {
            return new TransactionMessagesResponse("active queue message wait for ack/reject",null,null);
        }
        Kubemq.StreamQueueMessagesResponse resp;
        try {
            resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder()
                    .setClientID(this.queue.getClientID())
                    .setChannel(this.queue.getQueueName())
                    .setRequestID(IDGenerator.Getid())
                    .setStreamRequestTypeData(Kubemq.StreamRequestType.AckMessage)
                    .setRefSequence(msg.getMessage().getAttributes().getSequence()).build());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return new TransactionMessagesResponse(resp);
	}

    private boolean OpenStream() {
        
        if (resp == null){

            
        resp =new StreamObserver<Kubemq.StreamQueueMessagesResponse>() {

            @Override
            public void onNext(StreamQueueMessagesResponse value) {
                synchronized(lock){
                    msg = value;
                    bool = false;
                    lock.notify();
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {
                reqStreamObserver =null;   
            }
        };
    }
        return true;
    }
    private final Object lock = new Object();
    private Boolean bool = true;
    private Kubemq.StreamQueueMessagesResponse StreamQueueMessage(Kubemq.StreamQueueMessagesRequest sr) throws SSLException, ServerAddressNotSuppliedException, InterruptedException {
     
         if (reqStreamObserver == null){
        reqStreamObserver = GetKubeMQAsyncClient().streamQueueMessage(resp);
         }
        reqStreamObserver.onNext(sr);
   
        synchronized(lock){
            while (bool) {
              lock.wait();                
            }
           }
      return msg;
    }

	public TransactionMessagesResponse RejectMessage() throws SSLException, ServerAddressNotSuppliedException {
        if(!OpenStream())     {
            return new TransactionMessagesResponse("active queue message wait for ack/reject",null,null);
        }
        Kubemq.StreamQueueMessagesResponse resp;
        try {
            resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder()
                    .setClientID(this.queue.getClientID())
                    .setChannel(this.queue.getQueueName())
                    .setRequestID(IDGenerator.Getid()).setStreamRequestTypeData(Kubemq.StreamRequestType.RejectMessage)
                    .setRefSequence(msg.getMessage().getAttributes().getSequence())
                    .build());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return new TransactionMessagesResponse(resp);
	}
	public TransactionMessagesResponse ReSend(String string) throws SSLException, ServerAddressNotSuppliedException {
        if(!OpenStream())     {
            return new TransactionMessagesResponse("active queue message wait for ack/reject",null,null);
        }
        Kubemq.StreamQueueMessagesResponse resp;
        try {
            resp = StreamQueueMessage(Kubemq.StreamQueueMessagesRequest.newBuilder()
                    .setClientID(this.queue.getClientID())
                    .setChannel(this.queue.getQueueName())
                    .setRequestID(IDGenerator.Getid())
                    .setStreamRequestTypeData(Kubemq.StreamRequestType.ResendMessage)            
                    .build());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return new TransactionMessagesResponse(resp);
	}



}