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

import io.kubemq.sdk.basic.GrpcClient;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.grpc.Kubemq.PingResult;
import io.kubemq.sdk.grpc.Kubemq.QueueMessage;
import io.kubemq.sdk.tools.Converter;
import io.kubemq.sdk.tools.IDGenerator;
import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.SSLException;

/**
 * Represents a Queue pattern.
 */
public class Queue extends GrpcClient {
    
    private String queueName;
    private String clientID;
    private int maxNumberOfMessagesQueueMessages = 32;
    private int waitTimeSecondsQueueMessages =1;
    private static Logger logger = LoggerFactory.getLogger(Queue.class);
  
   
    /**
     * Distributed durable FIFO based queues with the following core 
     * @param queueName Represents The FIFO queue name to send to using the KubeMQ.
     * @param clientID Represents the sender ID that the messages will be send under.
     * @param maxNumberOfMessagesQueueMessages Number of received messages in request.
     * @param waitTimeSecondsQueueMessages Wait time for received messages.
     * @param kubeMQAddress The address the of the KubeMQ including the GRPC Port ,Example: "LocalHost:50000".
     * @throws SSLException    Indicates some kind of error detected by an SSL subsystem.
     * @throws ServerAddressNotSuppliedException    KubeMQ server address can not be determined.
     */
    public Queue(String queueName, String clientID, Integer maxNumberOfMessagesQueueMessages, Integer waitTimeSecondsQueueMessages, String kubeMQAddress)
            throws SSLException, ServerAddressNotSuppliedException
    {
        this.queueName = queueName;
        this.clientID = clientID;
        this._kubemqAddress = kubeMQAddress;
        this.maxNumberOfMessagesQueueMessages = maxNumberOfMessagesQueueMessages;
        this.waitTimeSecondsQueueMessages = waitTimeSecondsQueueMessages;      
        this.Ping();
    }

     public Queue(String queueName, String clientID, String kubeMQAddress)
            throws SSLException, ServerAddressNotSuppliedException {
        this.queueName = queueName;
        this.clientID = clientID;
        this._kubemqAddress = kubeMQAddress;       
        this.Ping();
	}

		/// <summary>
        /// Send single message
        /// </summary>
        /// <param name="message"></param>
        /// <returns></returns>
        public SendMessageResult SendQueueMessage(Message message) throws SSLException, ServerAddressNotSuppliedException
        {
            if (StringUtils.isEmpty(message.getQueue()))
            {
                message.setQueue(this.queueName);
            }
            if (StringUtils.isEmpty(message.getClientID()))            
            {
                message.setClientID(this.clientID);
            }          

            Kubemq.SendQueueMessageResult rec = GetKubeMQClient().sendQueueMessage(message.toQueueMessage());//Converter.ConvertQueueMessage(message));            
         

            return new SendMessageResult(rec);

        }

     
      

          /// <summary>
        /// Sending queue messages array request , waiting for response or timeout 
        /// </summary>
        /// <param name="queueMessages">Array of Messages</param>     
        /// <returns></returns>
        public SendBatchMessageResult SendQueueMessagesBatch(Iterable<Message> queueMessages)
            throws SSLException, ServerAddressNotSuppliedException
        {
            
          
                Kubemq.QueueMessagesBatchResponse rec = GetKubeMQClient().sendQueueMessagesBatch(Kubemq.QueueMessagesBatchRequest.newBuilder()
                .setBatchID(IDGenerator.Getid())
                .addAllMessages(Converter.ToQueueMessages(queueMessages, this.getClientID(),this.getQueueName()))
                .build());
              
                return new SendBatchMessageResult(rec);
        }

           /// <summary>
        /// Recessive messages from queues
        /// </summary>
        /// <param name="maxNumberOfMessagesQueueMessages">number of returned messages, default is 32</param>
        /// <returns></returns>
  

		public ReceiveMessagesResponse ReceiveQueueMessages(Integer maxNumberOfMessagesQueueMessages, Integer  waitTimeSecondsQueueMessages)
            throws SSLException, ServerAddressNotSuppliedException
        {

           if (maxNumberOfMessagesQueueMessages==null){
            maxNumberOfMessagesQueueMessages = this.maxNumberOfMessagesQueueMessages;
           }
           if (waitTimeSecondsQueueMessages==null){
            waitTimeSecondsQueueMessages = this.waitTimeSecondsQueueMessages;
           }

                Kubemq.ReceiveQueueMessagesResponse rec = GetKubeMQClient().receiveQueueMessages(Kubemq.ReceiveQueueMessagesRequest.newBuilder()
                .setRequestID(IDGenerator.Getid())
                .setClientID(this.clientID)
                .setChannel(this.queueName)
                .setMaxNumberOfMessages(maxNumberOfMessagesQueueMessages)
                .setWaitTimeSeconds(this.waitTimeSecondsQueueMessages)
                .build()                
                );

                return new ReceiveMessagesResponse(rec);         
        }

           /// <summary>
        /// QueueMessagesRequest for peak queue messages
        /// </summary>
        /// <param name="maxNumberOfMessagesQueueMessages">number of returned messages, default is 32 </param>
        /// <returns></returns>
        public ReceiveMessagesResponse PeekQueueMessage(Integer maxNumberOfMessagesQueueMessages,Integer  waitTimeSecondsQueueMessages)
            throws SSLException, ServerAddressNotSuppliedException {
            if (maxNumberOfMessagesQueueMessages==null){
                maxNumberOfMessagesQueueMessages = this.maxNumberOfMessagesQueueMessages;
               }
               if (waitTimeSecondsQueueMessages==null){
                waitTimeSecondsQueueMessages = this.waitTimeSecondsQueueMessages;
               }
                    Kubemq.ReceiveQueueMessagesResponse rec = GetKubeMQClient().receiveQueueMessages(Kubemq.ReceiveQueueMessagesRequest.newBuilder()
                    .setRequestID(IDGenerator.Getid())
                    .setClientID(this.clientID)
                    .setChannel(this.queueName)
                    .setMaxNumberOfMessages(maxNumberOfMessagesQueueMessages)
                    .setWaitTimeSeconds(waitTimeSecondsQueueMessages)
                    .setIsPeak(true)
                    .build()                
                    );
    
                    return new ReceiveMessagesResponse(rec);        
        }
        
        ///Mark all the messages as dequeued on queue.
        public AckAllMessagesResponse AckAllQueueMessages() throws SSLException, ServerAddressNotSuppliedException
        {
                Kubemq.AckAllQueueMessagesResponse rec = GetKubeMQClient().ackAllQueueMessages(Kubemq.AckAllQueueMessagesRequest.newBuilder()
                .setRequestID(IDGenerator.Getid())
                .setChannel(this.queueName)
                .setClientID(this.clientID)
                .setWaitTimeSeconds(this.waitTimeSecondsQueueMessages)
                .build()
                );

                return new AckAllMessagesResponse(rec);
        }

        public Kubemq.PingResult Ping() throws SSLException, ServerAddressNotSuppliedException
        {
            Kubemq.PingResult rec = GetKubeMQClient().ping(Kubemq.Empty.newBuilder().build());
            logger.debug("Queue KubeMQ address: '{}' ,ping result: '{}'", _kubemqAddress,rec);
            return rec;

        }


    public String getQueueName() {
        return queueName;
    }

    public String getClientID() {
        return clientID;
    }
 
    public int  getMaxNumberOfMessagesQueueMessages() {
        return maxNumberOfMessagesQueueMessages;
    }
    public int  getWaitTimeSecondsQueueMessages() {
        return waitTimeSecondsQueueMessages;
    }
    public void WaitTimeSecondsQueueMessages(int waitTimeSecondsQueueMessages) {
        this.waitTimeSecondsQueueMessages = waitTimeSecondsQueueMessages;
    }

}