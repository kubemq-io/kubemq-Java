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

import com.google.protobuf.ByteString;
import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.grpc.Kubemq.QueueMessage;
import io.kubemq.sdk.grpc.Kubemq.QueueMessageAttributes;
import io.kubemq.sdk.grpc.Kubemq.QueueMessagePolicy;
import io.kubemq.sdk.tools.IDGenerator;
import java.util.Map;

/**
 * Queue stored message
 */
public class Message {

  private String messageID;
  private String clientID;
  private String metadata;
  private byte[] body;
  private QueueMessageAttributes attributes;
  private QueueMessagePolicy policy;
  private Map<String, String> tags;
  private String queueName;

  /**
   * Queue stored message
   * 
   * @param body      The information that you want to pass.
   * @param metadata  General information about the message body.
   * @param messageID Unique for message
   * @param tags      Dictionary of string , string pair:A set of Key value pair
   *                  that help categorize the message.
   */
  public Message(byte[] body, String metadata, String messageID, Map<String, String> tags) {
    this.body = body;
    this.metadata = metadata;
    this.tags = tags;
    this.messageID = messageID;    
  }

  /**
   * Queue stored message
   */
  public Message() {
  }

  protected Message(Kubemq.QueueMessage queueMessage) {
    this.attributes = queueMessage.getAttributes();
    this.policy = queueMessage.getPolicy();
    this.clientID = queueMessage.getClientID();
    this.metadata = queueMessage.getMetadata();
    this.body = queueMessage.getBody().toByteArray();
    this.tags = queueMessage.getTagsMap();
    this.queueName = queueMessage.getChannel();
    this.messageID =queueMessage.getMessageID();
  }

  /**
   * Unique for message
   * 
   * @return Unique ID
   */
  public String getMessageID() {
    return this.messageID;
  }

  /**
   * Represents the sender ID that the messages will be send under.
   * 
   * @return sender ID
   */
  public String getClientID() {
    return this.clientID;
  }

  /**
   * Represents the sender ID that the messages will be send under
   * 
   * @param clientID
   * @return this Message
   */
  public Message setClientID(String clientID) {
    this.clientID = clientID;
    return this;
  }

  /**
   * Represents The FIFO queue name to send to using the KubeMQ.
   * 
   * @return Queue name
   */
  public String getQueue() {
    return this.queueName;
  }

  /**
   * Represents The FIFO queue name to send to using the KubeMQ.
   * 
   * @param queueName Queue name
   * @return this Message
   */
  public Message setQueue(String queueName) {
    this.queueName = queueName;
    return this;
  }

  /**
   * General information about the message body.
   * 
   * @return Metadata
   */
  public String getMetadata() {
    return this.metadata;
  }

  /**
   * General information about the message body.
   * 
   * @param metadata General information
   * @return this Message
   */
  public Message setMetadata(String metadata) {
    this.metadata = metadata;
    return this;
  }

  /**
   * The information that you want to pass.
   * 
   * @return Message encoded body
   */
  public byte[] getBody() {
    return this.body;
  }

  /**
   * The information that you want to pass.
   * 
   * @param body Message encoded body
   * @return this Message
   */
  public Message setBody(byte[] body) {
    this.body = body;
    return this;
  }

  /**
   * A set of Key value pair that help categorize the message.
   * 
   * @return Message tags
   */
  public Map<String, String> getTags() {
    return this.tags;
  }

  /**
   * A set of Key value pair that help categorize the message.
   * 
   * @param key   Tag key
   * @param value Tag value
   * @return this Message
   */
  public Message setTag(String key, String value) {
    this.tags.putIfAbsent(key, value);
    return this;
  }

  /**
   * Information of received message
   * 
   * @return Message attributes
   */
  public Kubemq.QueueMessageAttributes getQueueMessageAttributes() {
    return this.attributes;
  }

  /**
   * Information of received message
   * 
   * @return Message policy
   */
  public Kubemq.QueueMessagePolicy getMessagePolicy() {
    return this.policy;
  }

  /**
   * Message policy max number of recived message before routing to "Dead letter"
   * queue.
   * 
   * @param maxReciveCount
   * @return this Message
   */
  public Message setMaxReciveCount(int maxReciveCount) {
    if (this.policy==null){
      this.policy = QueueMessagePolicy.getDefaultInstance();
    }
    this.policy = this.policy.toBuilder().setMaxReceiveCount(maxReciveCount).build();
    
    return this;
  }

  /**
   * Message policy max "Dead letter" queue
   * 
   * @param maxReciveQueue "Dead letter" queue name when maxReciveCount hit.
   * @return this Message
   */
  public Message setMaxReciveQueue(String maxReciveQueue) {
    if (this.policy==null){
      this.policy = QueueMessagePolicy.getDefaultInstance();
    }
    this.policy = this.policy.toBuilder().setMaxReceiveQueue(maxReciveQueue).build();
    return this;
  }

  /**
   * Message policy message expiration in seconds
   * 
   * @param expiration expiration in seconds
   * @return this Message
   */
  public Message setExpiration(int expiration) {
    if (this.policy==null){
      this.policy = QueueMessagePolicy.getDefaultInstance();
    }
  
    this.policy = this.policy.toBuilder().setExpirationSeconds(expiration).build();
    return this;
  }

  /**
   * Message policy message delay delivery
   * 
   * @param delay delay delivery in seconds
   * @return this Message
   */
  public Message setDelay(int delay) {
    if (this.policy==null){
      this.policy = QueueMessagePolicy.getDefaultInstance();
    }
    this.policy =  this.policy.toBuilder().setDelaySeconds(delay).build();
    return this;
  }

  protected QueueMessage toQueueMessage() {
    Kubemq.QueueMessage tempmsg = Kubemq.QueueMessage.newBuilder()
        .setMessageID(this.messageID == null ? IDGenerator.Getid() : this.getMessageID()).setClientID(this.clientID)
        .setChannel(this.queueName).setBody(ByteString.copyFrom(this.body))
        .setMetadata(this.metadata==null ? "" : this.metadata )
        .setPolicy(this.policy==null ? Kubemq.QueueMessagePolicy.getDefaultInstance() : this.policy)
        .setAttributes(this.attributes==null ? Kubemq.QueueMessageAttributes.getDefaultInstance() : this.attributes)
        .build();        
  
    return tempmsg;
  }

}