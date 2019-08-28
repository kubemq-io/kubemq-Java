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


import com.google.protobuf.ByteString;
import io.kubemq.sdk.grpc.Kubemq;
import io.kubemq.sdk.grpc.Kubemq.QueueMessage;
import io.kubemq.sdk.grpc.Kubemq.QueueMessageAttributes;
import io.kubemq.sdk.grpc.Kubemq.QueueMessagePolicy;
import io.kubemq.sdk.tools.Converter;
import io.kubemq.sdk.tools.IDGenerator;

import java.security.Policy;
import java.util.Dictionary;
import java.util.Map;






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
      * @param body The information that you want to pass.
      * @param metadata General information about the message body.
      * @param messageID Unique for message
      * @param tags Dictionary of string , string pair:A set of Key value pair that help categorize the message.
      */
    public Message(byte[] body, String metadata, String messageID, Map<String, String> tags) {
    this.body = body;
    this.metadata = metadata;
    this.tags = tags;
    }

    public Message(Kubemq.QueueMessage queueMessage)
    {
     
    }
   
    public Message() {      
	}

	public String getMessageID() {
      return this.messageID;
  }

  public String getClientID(){
    return this.clientID;    
  }

  public String getQueue(){
    return this.queueName;
  }

  public String getMetadata(){
    return this.metadata;
  }

  public byte[] getBody() {
    return this.body;
}
public Message setBody(byte[] body) {
 this.body = body;
 return this;
}

public Kubemq.QueueMessageAttributes getQueueMessageAttributes() {
  return this.attributes;
}

public Kubemq.QueueMessagePolicy getMessagePolicy() {
  return this.policy;
}

public Message setQueue(String queueName) {
  this.queueName = queueName;
  return this;
}

public Message setClientID(String clientID) {
  this.clientID =clientID;
  return this;
}

public Map<String,String> getTags() {
	return this.tags;
}

public Message setMetadata(String metadata) {
  this.metadata = metadata;
  return this;
}
public Message setPolicy(Kubemq.QueueMessagePolicy queueMessagePolicy) {
  this.policy = queueMessagePolicy;
  return this;
}

protected QueueMessage toQueueMessage() {
Kubemq.QueueMessage x =  Kubemq.QueueMessage.newBuilder()
.setMessageID(this.messageID)
.setClientID(this.clientID)
.setChannel(this.queueName)
.setBody(ByteString.copyFrom(this.body))
.setMetadata(this.metadata)
.build();
if(this.policy!=null){
    x.newBuilderForType().setPolicy(this.policy).build();
}
if(this.tags!=null){
  x.newBuilderForType().putAllTags(this.tags).build();
}
return x;
}

public Message setExpiration(int expiration) {
  this.policy.newBuilderForType().setExpirationSeconds(expiration).build();
  return this;
}

public Message setDelay(int delay) {
  this.policy.newBuilderForType().setDelaySeconds(delay).build();
  return this;
}

public Message setMaxReciveCount(int maxReciveCount) {
  this.policy.newBuilderForType().setMaxReceiveCount(maxReciveCount).build();
  return this;
}

public Message setMaxReciveQueue(String maxReciveQueue) {
  this.policy.newBuilderForType().setMaxReceiveQueue(maxReciveQueue).build();
  return this;
}
}