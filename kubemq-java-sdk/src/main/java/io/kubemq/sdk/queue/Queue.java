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
package io.kubemq.sdk.queue;

import io.kubemq.sdk.basic.GrpcClient;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;

import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.tools.IDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

import javax.net.ssl.SSLException;

/**
 * Represents a Queue pattern.
 */
public class Queue extends GrpcClient {

    private String queueName;
    private String clientID;
    private int maxNumberOfMessagesQueueMessages = 32;
    private int waitTimeSecondsQueueMessages = 1;   
    private static Logger logger = LoggerFactory.getLogger(Queue.class);

       
    /**
     * Distributed durable FIFO based queues with the following core
     * 
     * @param queueName     Represents The FIFO queue name to send to using the
     *                      KubeMQ.
     * @param clientID      Represents the sender ID that the messages will be send
     *                      under.
     * @param kubeMQAddress The address the of the KubeMQ including the GRPC Port
     *                      ,Example: "LocalHost:50000".
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     */
    public Queue(String queueName, String clientID, String kubeMQAddress)
            throws SSLException, ServerAddressNotSuppliedException {
        this.queueName = queueName;
        this.clientID = clientID;
        this._kubemqAddress = kubeMQAddress;
        this.Ping();
    }

    /**
     * Distributed durable FIFO based queues with the following core
     * 
     * @param queueName     Represents The FIFO queue name to send to using the
     *                      KubeMQ.
     * @param clientID      Represents the sender ID that the messages will be send
     *                      under.
     * @param kubeMQAddress The address the of the KubeMQ including the GRPC Port
     *                      ,Example: "LocalHost:50000".
    * @param authToken     Set KubeMQ JWT Auth token to be used for KubeMQ
     *                      connection. 
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     */
    public Queue(String queueName, String clientID, String kubeMQAddress, String authToken)
            throws SSLException, ServerAddressNotSuppliedException {
        this.queueName = queueName;
        this.clientID = clientID;
        this._kubemqAddress = kubeMQAddress;
        this.addAuthToken(authToken);
        this.Ping();
    }

    /**
     * Send single message
     * 
     * @param message Queue stored message
     * @return Queue request execution result.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws AuthorizationException   Authorization KubeMQ token to be used for KubeMQ connection. 
     */
    public SendMessageResult SendQueueMessage(Message message) throws SSLException, ServerAddressNotSuppliedException {
        if (StringUtils.isEmpty(message.getQueue())) {
            message.setQueue(this.queueName);
        }
        if (StringUtils.isEmpty(message.getClientID())) {
            message.setClientID(this.clientID);
        }
            Kubemq.SendQueueMessageResult rec = GetKubeMQClient().sendQueueMessage(message.toQueueMessage());

            return new SendMessageResult(rec);
       
      

    }

    /**
     * Sending queue messages array request , waiting for response or timeout
     * 
     * @param queueMessages Array of Messages
     * @return Queue request batch execution result.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws AuthorizationException    Authorization KubeMQ token to be used for KubeMQ connection. 
     */
    public SendBatchMessageResult SendQueueMessagesBatch(Iterable<Message> queueMessages)
            throws SSLException, ServerAddressNotSuppliedException{
    
            Kubemq.QueueMessagesBatchResponse rec = GetKubeMQClient()
            .sendQueueMessagesBatch(Kubemq.QueueMessagesBatchRequest.newBuilder().setBatchID(IDGenerator.Getid())
                    .addAllMessages(
                        toQueueMessages(queueMessages))
                    .build());

    return new SendBatchMessageResult(rec);
    }

    /**
     * Recessive messages from queue.
     * 
     * @param maxNumberOfMessagesQueueMessages Number of returned messages, default
     *                                         is 32
     * @param waitTimeSecondsQueueMessages     Wait delay time for received
     *                                         messages.
     * @return Queue response.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     */
    public ReceiveMessagesResponse ReceiveQueueMessages(Integer maxNumberOfMessagesQueueMessages,
            Integer waitTimeSecondsQueueMessages) throws SSLException, ServerAddressNotSuppliedException {

        if (maxNumberOfMessagesQueueMessages == null) {
            maxNumberOfMessagesQueueMessages = this.maxNumberOfMessagesQueueMessages;
        }
        if (waitTimeSecondsQueueMessages == null) {
            waitTimeSecondsQueueMessages = this.waitTimeSecondsQueueMessages;
        }

    
      
        Kubemq.ReceiveQueueMessagesResponse rec = GetKubeMQClient()
                .receiveQueueMessages(Kubemq.ReceiveQueueMessagesRequest.newBuilder().setRequestID(IDGenerator.Getid())
                        .setClientID(this.clientID).setChannel(this.queueName)
                        .setMaxNumberOfMessages(maxNumberOfMessagesQueueMessages)
                        .setWaitTimeSeconds(this.waitTimeSecondsQueueMessages).build());

        return new ReceiveMessagesResponse(rec);
    }

    /// <summary>
    /// QueueMessagesRequest for peak queue messages
    /// </summary>
    /// <param name="maxNumberOfMessagesQueueMessages">number of returned messages,
    /// default is 32 </param>
    /// <returns></returns>
    /**
     * Peek queue messages.
     * 
     * @param maxNumberOfMessagesQueueMessages Number of returned messages, default
     *                                         is 32
     * @param waitTimeSecondsQueueMessages     Wait delay time for received
     *                                         messages.
     * @return Queue response.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws AuthorizationException    Authorization KubeMQ token to be used for KubeMQ connection.  
     */
    public ReceiveMessagesResponse PeekQueueMessage(Integer maxNumberOfMessagesQueueMessages,
            Integer waitTimeSecondsQueueMessages) throws SSLException, ServerAddressNotSuppliedException {
        if (maxNumberOfMessagesQueueMessages == null) {
            maxNumberOfMessagesQueueMessages = this.maxNumberOfMessagesQueueMessages;
        }
        if (waitTimeSecondsQueueMessages == null) {
            waitTimeSecondsQueueMessages = this.waitTimeSecondsQueueMessages;
        }
      
            Kubemq.ReceiveQueueMessagesResponse rec = GetKubeMQClient()
            .receiveQueueMessages(Kubemq.ReceiveQueueMessagesRequest.newBuilder().setRequestID(IDGenerator.Getid())
                    .setClientID(this.clientID).setChannel(this.queueName)
                    .setMaxNumberOfMessages(maxNumberOfMessagesQueueMessages)
                    .setWaitTimeSeconds(waitTimeSecondsQueueMessages).setIsPeak(true).build());

    return new ReceiveMessagesResponse(rec);
      
      
    }

    /// Mark all the messages as dequeued on queue.
    /**
     * Mark all the messages as dequeued on queue.
     * 
     * @return Queue response.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     * @throws AuthorizationException   Authorization KubeMQ token to be used for KubeMQ connection.
     */
    public AckAllMessagesResponse AckAllQueueMessages() throws SSLException, ServerAddressNotSuppliedException  {
    
        Kubemq.AckAllQueueMessagesResponse rec = GetKubeMQClient().ackAllQueueMessages(Kubemq.AckAllQueueMessagesRequest
        .newBuilder().setRequestID(IDGenerator.Getid()).setChannel(this.queueName).setClientID(this.clientID)
        .setWaitTimeSeconds(this.waitTimeSecondsQueueMessages).build());
        return new AckAllMessagesResponse(rec);
         
    }

    /**
     * Ping KubeMQ address to check Grpc connection
     * 
     * @return Kubemq.PingResult.
     * @throws SSLException                      Indicates some kind of error
     *                                           detected by an SSL subsystem.
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     */
    public Kubemq.PingResult Ping() throws SSLException, ServerAddressNotSuppliedException{
        Kubemq.PingResult rec = GetKubeMQClient().ping(null);
        logger.debug("Queue KubeMQ address: '{}' ,ping result: '{}'", _kubemqAddress, rec);
        return rec;

    }

    /**
     * Represents The FIFO queue name to send to using the KubeMQ.
     * 
     * @return Queue name
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * Represents the sender ID that the messages will be send under.
     * 
     * @return Client ID
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * Number of received messages, used as defult in peek and receive.
     * 
     * @return Number of received messages.
     */
    public int getMaxNumberOfMessagesQueueMessages() {
        return maxNumberOfMessagesQueueMessages;
    }



    /**
     * Wait time for received messages, used as default in peek and receive.
     * 
     * @param maxNumberOfMessagesQueueMessages Number of returned messages, default
     *                                         is 32
     */
    public void setMaxNumberOfMessagesQueueMessages(int maxNumberOfMessagesQueueMessages){
        this.maxNumberOfMessagesQueueMessages = maxNumberOfMessagesQueueMessages;
    }

    /**
     * Wait time for received messages, used as default in peek and receive.
     * 
     * @return Wait time in seconds.
     */
    public int getWaitTimeSecondsQueueMessages() {
        return waitTimeSecondsQueueMessages;
    }

    /**
     * Wait time for received messages, used as defult in peek and receive.
     * 
     * @param waitTimeSecondsQueueMessages Wait time in seconds.
     */
    public void setWaitTimeSecondsQueueMessages(int waitTimeSecondsQueueMessages) {
        this.waitTimeSecondsQueueMessages = waitTimeSecondsQueueMessages;
    }

    /**
     * Advance manipulation of messages using stream
     * 
     * @return Static Transaction stream
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be
     *                                           determined.
     */
    public Transaction CreateTransaction() throws ServerAddressNotSuppliedException {  
     
        return new Transaction(this);
    }

    private  Iterable<? extends Kubemq.QueueMessage> toQueueMessages(Iterable<Message> queueMessages) {
        Collection<Kubemq.QueueMessage> cltn = new ArrayList<Kubemq.QueueMessage>(); 

            for (Message item : queueMessages){
               if (item.getQueue()==null)
                {
                    item.setQueue(this.queueName);
                }
                
                if (item.getClientID()==null)
                {
                    item.setClientID(this.clientID);
                }      
                cltn.add(item.toQueueMessage()); 
        }
            return cltn;
}


}