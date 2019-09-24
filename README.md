---
title: Java
lang: en-US
description: 'Java KubeMQ SDK reference'
tags: ['pub/sub','message broker','KubeMQ','kubernetes','docker','cloud native','message queue','java']
---

# Java

The **KubeMQ SDK for Java** enables Java developers to communicate with [KubeMQ](https://kubemq.io/) server.

## Table of Content
[[toc]]

## General SDK description
The SDK implements all communication patterns available through the KubeMQ server:
- Events
- EventStore
- Command
- Query
- Queue

### Prerequisites

KubeMQ-SDK-Java works with JDK 8+

### Installing

The recommended way to use the SDK for Java in your project is to consume it from Maven.
https://oss.sonatype.org/service/local/repositories/releases/content/io/kubemq/sdk/kubemq-sdk-Java/0.1.6/kubemq-sdk-Java-0.1.6.jar
To build with Gradle, add the dependency below to your build.gradle file.

``` java
compile group: 'io.kubemq.sdk', name: 'kubemq-java-sdk', version: '1.0.1'
```

## Configurations
The only required configuration setting is the KubeMQ server address.

Configuration can be set by using one of the following:
- Environment Variable
- 'Java Property'


### Configuration via Environment Variable
Set `KubeMQServerAddress` to the KubeMQ Server Address


### Configuration via Java Property
by passing the -DKubeMQServerAddress= option to the JVM)
Within the code

### Configuration via code
When setting the KubeMQ server address within the code, simply pass the address as a parameter to the various constructors.
See exactly how in the code examples in this document.

## Generating Documentation

Javadoc is used for documentation. You can generate HTML locally with the following:

``` bash
.gradlew javadoc
```

## Running the examples

The [examples](https://github.com/kubemq-io/Java_SDK/tree/v1.0.1/examples)
are standalone projects that showcase the usage of the SDK.

To run the examples, you need to have a running instance of KubeMQ.

You can use the Gradle tasks to run the examples:

``` bash
.gradlew commandQueryChannel
.gradlew commandQueryInitiator
.gradlew commandQueryResponder
.gradlew commandQueryResponderAsync
.gradlew eventChannel
.gradlew eventSender
.gradlew eventSubscriber
```

## Building from source

Once you check out the code from GitHub, you can build it using Gradle.

``` bash
.gradlew build
```

## Running the tests

To run the automated tests for this system execute:

``` bash
.gradlew test
```

## Main Concepts

- Metadata: The metadata allows us to pass additional information with the event. Can be in any form that can be presented as a string, i.e., struct, JSON, XML and many more.
- Body: The actual content of the event. Can be in any form that is serializable into a byte array, i.e., string, struct, JSON, XML, Collection, binary file and many more.
- ClientID: Displayed in logs, tracing, and KubeMQ dashboard(When using Events Store, it must be unique).
- Tags: Set of Key value pair that help categorize the message

### Event/EventStore/Command/Query

- Channel: Represents the endpoint target. One-to-one or one-to-many. Real-Time Multicast.
- Group: Optional parameter when subscribing to a channel. A set of subscribers can define the same group so that only one of the subscribers within the group will receive a specific event. Used mainly for load balancing. Subscribing without the group parameter ensures receiving all the channel messages. (When using Grouping all the programs that are assigned to the group need to have to same channel name)
- Event Store: The Event Store represents a persistence store, should be used when need to store data on a volume.

### Queue

- Queue: Represents a unique FIFO queue name, used in queue pattern.
- Transaction: Represents an Rpc stream for single message transaction.


### Event/EventStore/Command/Query SubscribeRequest Object:

A struct that is used to initialize SubscribeToEvents/SubscribeToRequest, the SubscribeRequest contains the following:

- SubscribeType - Mandatory - Enum that represents the subscription type:
- Events - if there is no need for Persistence.
- EventsStore - If you want to receive Events from persistence. See Main concepts.
- Command - Should be used when a response is not needed.
- Query - Should be used when a response is needed.
- ClientID - Mandatory - See Main concepts
- Channel - Mandatory - See Main concepts
- Group - Optional - See Main concepts
- EventsStoreType - Mandatory - set the type event store to subscribe to Main concepts.

## Queue

KubeMQ supports distributed durable FIFO based queues with the following core features:

- Exactly One Delivery - Only one message guarantee will deliver to the subscriber
- Single and Batch Messages Send and Receive - Single and multiple messages in one call
- RPC and Stream Flow - RPC flow allows an insert and pulls messages in one call. Stream flow allows single message consuming in a transactional way
- Message Policy - Each message can be configured with expiration and delay timers. Also, each message can specify a dead-letter queue for un-processed messages attempts
- Long Polling - Consumers can wait until a message available in the queue to consume
- Peak Messages - Consumers can peak into a queue without removing them from the queue
- Ack All Queue Messages - Any client can mark all the messages in a queue as discarded and will not be available anymore to consume
- Visibility timers - Consumers can pull a message from the queue and set a timer which will cause the message not be visible to other consumers. This timer can be extended as needed.
- Resend Messages - Consumers can send back a message they pulled to a new queue or send a modified message to the same queue for further processing.

### Send Message to a Queue

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  SendMessageResult resSend = queue.SendQueueMessage(new Message()
          .setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
          .setMetadata("someMeta"));
  if (resSend.getIsError()) {
      System.out.printf("Message enqueue error, error: %s", resSend.getError());
  }
```    

 ### Send Message to a Queue with Expiration 

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  SendMessageResult resSend = queue
          .SendQueueMessage(new Message()
          .setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
          .setMetadata("someMeta")
          .setExpiration(5));
  if (resSend.getIsError()) {
      System.out.printf("Message enqueue error, error: %s", resSend.getError());
  }
```

### Send Message to a Queue with Delay

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  SendMessageResult resSend = queue.SendQueueMessage(new Message()
          .setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
          .setMetadata("someMeta")
          .setDelay(3));
  if (resSend.getIsError()) {
      System.out.printf("Message enqueue error, error: %s", resSend.getError());
  }
```

### Send Message to a Queue with Dead-letter Queue

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  SendMessageResult resSend = queue
          .SendQueueMessage(new Message()
          .setBody(Converter.ToByteArray("some-simple_queue-queue-message"))
          .setMetadata("someMeta")
          .setMaxReciveCount(3)
          .setMaxReciveQueue("DeadLetterQueue"));
  if (resSend.getIsError()) {
      System.out.printf("Message enqueue error, error: %s", resSend.getError());
  }
```

### Send Batch Messages

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  Collection<Message> batch = new ArrayList<Message>();

  for (int i = 0; i < 10; i++) {
      batch.add(new Message()
      .setBody(Converter.ToByteArray("Batch Message " + i)));
  }

  SendBatchMessageResult resBatch = queue.SendQueueMessagesBatch(batch);
  if (resBatch.getHaveErrors()) {
      System.out.print("Message sent batch has errors");
  }
  for (SendMessageResult resSend : resBatch.getResults()) {
      if (resSend.getIsError()) {
          System.out.printf("Message enqueue error, error: %s", resSend.getError());
      } else {
          System.out.printf("Send to Queue Result: MessageID: %s, Sent At:%s", resSend.getMessageID(),
                Converter.FromUnixTime(resSend.getSentAt()).toString());

      }

  }
```

### Receive Messages from a Queue

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  ReceiveMessagesResponse resRec = queue.ReceiveQueueMessages(10, 1);
  if (resRec.getIsError()) {
      System.out.printf("Message dequeue error, error: %s", resRec.getError());
      return;
  }
  System.out.printf("Received Messages %s:", resRec.getMessagesReceived());
  for (Message msg : resRec.getMessages()) {
      System.out.printf("MessageID: %s, Body:%s", msg.getMessageID(), Converter.FromByteArray(msg.getBody()));
  }
```

### Peek Messages from a Queue

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  ReceiveMessagesResponse resPek = queue.PeekQueueMessage(10, 1);
  if (resPek.getIsError()) {
      System.out.printf("Message dequeue error, error: %s", resPek.getError());
      return;
  }
  System.out.printf("Received Messages: %s", resPek.getMessagesReceived());
  for (Message msg : resPek.getMessages()) {
      System.out.printf("MessageID: %s, Body: %s", msg.getMessageID(), Converter.FromByteArray(msg.getBody()));
  }        
```
### Ack All Messages In a Queue

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  AckAllMessagesResponse resAck = queue.AckAllQueueMessages();
  if (resAck.getIsError()) {
      System.out.printf("AckAllQueueMessagesResponse error, error: %s", resAck.getError());
      return;
  }
  System.out.printf("Ack All Messages: %d completed", resAck.getAffectedMessages());
```

### Transactional Queue - Ack
```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  Transaction tran = queue.CreateTransaction();

  TransactionMessagesResponse resRec = tran.Receive(10, 10);
  if (resRec.getIsError()) {
      System.out.printf("Message dequeue error, error: %s", resRec.getError());
      return;
  }
  System.out.printf("MessageID: %d, Body: %s", resRec.getMessage().getMessageID(),
          Converter.FromByteArray(resRec.getMessage().getBody()));
  System.out.println("Doing some work.....");

  Thread.sleep(1000);
  System.out.println("Done, ack the message");
  TransactionMessagesResponse resAck = tran.AckMessage();
  if (resAck.getIsError()) {
      System.out.printf("Ack message error: %s", resAck.getError());
  }
```

### Transactional Queue - Reject

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  Transaction tran = queue.CreateTransaction();
  TransactionMessagesResponse resRec = tran.Receive(10, 10);
  if (resRec.getIsError()) {
      System.out.printf("Message dequeue error, error: %s", resRec.getError());
      return;
  }
  System.out.printf("MessageID: %d, Body: %s", resRec.getMessage().getMessageID(),
          Converter.FromByteArray(resRec.getMessage().getBody()));
  System.out.println("Reject message");
  TransactionMessagesResponse resRej = tran.RejectMessage();
  if (resRej.getIsError()) {
      System.out.printf("Message dequeue error, error: %s", resRej.getError());
      return;
  }
```

### Transactional Queue - Extend Visibility

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  Transaction tran = queue.CreateTransaction();
  TransactionMessagesResponse resRec = tran.Receive(5, 10);
  if (resRec.getIsError()) {
      System.out.printf("Message dequeue error, error: %s", resRec.getError());
      return;
  }
  System.out.printf("MessageID: %d, Body: %s", resRec.getMessage().getMessageID(),
          Converter.FromByteArray(resRec.getMessage().getBody()));
  System.out.println("work for 1 seconds");
  Thread.sleep(1000);
  System.out.println("Need more time to process, extend visibility for more 3 seconds");
  TransactionMessagesResponse resExt = tran.ExtendVisibility(3);
  if (resExt.getIsError()) {
      System.out.printf("Message dequeue error, error: %s", resExt.getError());
      return;
  }
  System.out.println("Approved. work for 2.5 seconds");
  Thread.sleep(2500);
  System.out.println("Work done... ack the message");
  TransactionMessagesResponse resAck = tran.AckMessage();
  if (resAck.getIsError()) {
      System.out.printf("Ack message error: %s", resAck.getError());

  }
  System.out.println("Ack done");
```

### Transactional Queue - Resend to New Queue

```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  Transaction tran = queue.CreateTransaction();
  TransactionMessagesResponse resRec = tran.Receive(500, 10);
  if (resRec.getIsError()) {
      System.out.printf("Message dequeue error, error: %s", resRec.getError());
      return;
  }
  System.out.printf("MessageID: %d, Body:%s", resRec.getMessage().getMessageID(),
          Converter.FromByteArray(resRec.getMessage().getBody()));
  TransactionMessagesResponse resMod = tran
          .Modify(resRec.getMessage().setQueue("receiverB").setMetadata("new meatdata"));
  if (resMod.getIsError()) {
      System.out.printf("Message Modify error, error::%s", resMod.getError());
      return;
  }
```

### Transactional Queue - Resend Modified Message
```java
  Queue queue = new Queue("QueueName", "ClientID", "localhost:50000");
  Transaction tran = queue.CreateTransaction();
  TransactionMessagesResponse resRec = tran.Receive(5, 10);
  if (resRec.getIsError()) {
      System.out.printf("Message dequeue error, error: %s", resRec.getError());
      return;
  }
  System.out.printf("MessageID: %d, Body:%s", resRec.getMessage().getMessageID(),
          Converter.FromByteArray(resRec.getMessage().getBody()));

  System.out.println("Resend to new queue");
  TransactionMessagesResponse resResend = tran.ReSend("new-queue");
  if (resResend.getIsError()) {
      System.out.printf("Message dequeue error, error: %s", resResend.getError());
      return;
  }
  System.out.println("Done");
```

## Event

### Sending Events

#### Single event
```java
  String ChannelName = "testing_event_channel", ClientID = "hello-world-sender",
          KubeMQServerAddress = "localhost:50000";

  io.kubemq.sdk.event.Channel channel = new io.kubemq.sdk.event.Channel(ChannelName, ClientID, false,
          KubeMQServerAddress);
  Event event = new Event();
  event.setBody(Converter.ToByteArray("hello kubemq - sending single event"));
  Result result;
  try {
      result = channel.SendEvent(event);
      if (!result.isSent()) {
          System.out.println("Could not send single message");
          return;
      }
  } catch (ServerAddressNotSuppliedException e) {
      System.out.printf("Could not send single message: %s", e.getMessage());
      e.printStackTrace();
  }

```

#### Stream Events
```java
  String ChannelName = "testing_event_channel", ClientID = "hello-world-sender",
          KubeMQServerAddress = "localhost:50000";

  io.kubemq.sdk.event.Channel channel = new io.kubemq.sdk.event.Channel(ChannelName, ClientID, false,
          KubeMQServerAddress);
  Event event = new Event();
  event.setBody(Converter.ToByteArray("hello kubemq - sending single event"));

  StreamObserver<Result> streamResObserver = new StreamObserver<Result>() {

      @Override
      public void onNext(Result value) {
          System.out.printf("Sent event: %s", value.getEventId());
      }

      @Override
      public void onError(Throwable t) {
          System.out.printf("Could not send single message");
      }

      @Override
      public void onCompleted() {

      }
  };

  StreamObserver<Event> stream = channel.StreamEvent(streamResObserver);
  stream.onNext(event);
```

### Receiving Events

```java
  String ChannelName = "testing_event_channel", ClientID = "hello-world-subscriber",
          KubeMQServerAddress = "localhost:50000";
  Subscriber subscriber = new Subscriber(KubeMQServerAddress);
  SubscribeRequest subscribeRequest = new SubscribeRequest();
  subscribeRequest.setChannel(ChannelName);
  subscribeRequest.setClientID(ClientID);
  subscribeRequest.setSubscribeType(SubscribeType.Events);

  StreamObserver<EventReceive> streamObserver = new StreamObserver<EventReceive>() {

      @Override
      public void onNext(EventReceive value) {
          try {
              System.out.printf("Event Received: EventID: %d, Channel: %s, Metadata: %s, Body: %s",
                      value.getEventId(), value.getChannel(), value.getMetadata(),
                      Converter.FromByteArray(value.getBody()));
          } catch (ClassNotFoundException e) {
              System.out.printf("ClassNotFoundException: %s", e.getMessage());
              e.printStackTrace();
          } catch (IOException e) {
              System.out.printf("IOException:  %s", e.getMessage());
              e.printStackTrace();
          }

      }

      @Override
      public void onError(Throwable t) {
          System.out.printf("Event Received Error: %s", t.toString());
      }

      @Override
      public void onCompleted() {

      }
  };
  subscriber.SubscribeToEvents(subscribeRequest, streamObserver);

```

## Event Store

### Subscription Options  

KubeMQ supports 6 types of subscriptions:  

- StartFromNewEvents - start event store subscription with only new events  

- StartFromFirstEvent - replay all the stored events from the first available sequence and continue stream new events from this point  

- StartFromLastEvent - replay the last event and continue stream new events from this point  

- StartFromSequence - replay events from specific event sequence number and continue stream new events from this point  

- StartFromTime - replay events from specific time continue stream new events from this point  

- StartFromTimeDelta - replay events from specific current time - delta duration in seconds, continue stream new events from this point  
### Sending Event Store

#### Single Event Store

```java
  String ChannelName = "testing_event_channel", ClientID = "hello-world-sender",
          KubeMQServerAddress = "localhost:50000";

  io.kubemq.sdk.event.Channel channel = new io.kubemq.sdk.event.Channel(ChannelName, ClientID, false,
          KubeMQServerAddress);
  for (int i = 0; i < 10; i++) {
      Event event = new Event();
      event.setBody(Converter.ToByteArray("hello kubemq - sending single event"));
      event.setEventId("event-Store-" + i);
      try {
          channel.SendEvent(event);
      } catch (SSLException e) {
          System.out.printf("SSLException: %s", e.getMessage());
          e.printStackTrace();
      } catch (ServerAddressNotSuppliedException e) {
          System.out.printf("ServerAddressNotSuppliedException: %s", e.getMessage());
          e.printStackTrace();
      }
  }
```

#### Strem Events Store

```java
  String ChannelName = "testing_event_channel", ClientID = "hello-world-sender",
          KubeMQServerAddress = "localhost:50000";

  io.kubemq.sdk.event.Channel channel = new io.kubemq.sdk.event.Channel(ChannelName, ClientID, false,
          KubeMQServerAddress);

  StreamObserver<Result> streamResObserver = new StreamObserver<Result>() {

      @Override
      public void onNext(Result value) {
          System.out.printf("Stream event: %s", value.getEventId());
      }

      @Override
      public void onError(Throwable t) {
          System.out.printf("Could not send single message");
      }

      @Override
      public void onCompleted() {

      }
  };

  StreamObserver<Event> stream = channel.StreamEvent(streamResObserver);
  for (int i = 0; i < 10; i++) {
      Event event = new Event();
      event.setBody(Converter.ToByteArray("hello kubemq - sending single event"));
      event.setEventId("event-Store-" + i);
      stream.onNext(event);
  }
```

### Receiving Events Store

```java
  String ChannelName = "testing_event_channel", ClientID = "hello-world-subscriber",
          KubeMQServerAddress = "localhost:50000";
  Subscriber subscriber = new Subscriber(KubeMQServerAddress);
  SubscribeRequest subscribeRequest = new SubscribeRequest();
  subscribeRequest.setChannel(ChannelName);
  subscribeRequest.setClientID(ClientID);
  subscribeRequest.setSubscribeType(SubscribeType.EventsStore);
  subscribeRequest.setEventsStoreType(EventsStoreType.StartAtSequence);

  StreamObserver<EventReceive> streamObserver = new StreamObserver<EventReceive>() {

      @Override
      public void onNext(EventReceive value) {
          try {
              System.out.printf("Event Received: EventID: %s, Channel: %s, Metadata: %s, Body: %s",
                      value.getEventId(), value.getChannel(), value.getMetadata(),
                      Converter.FromByteArray(value.getBody()));
          } catch (ClassNotFoundException e) {
              System.out.printf("ClassNotFoundException: %s", e.getMessage());
              e.printStackTrace();
          } catch (IOException e) {
              System.out.printf("IOException: %s", e.getMessage());
              e.printStackTrace();
          }
      }

      @Override
      public void onError(Throwable t) {
          System.out.printf("onError:  %s", t.getMessage());
      }

      @Override
      public void onCompleted() {

      }

  };
  subscriber.SubscribeToEvents(subscribeRequest, streamObserver);
```

## Commands

### Concept

Commands implement synchronous messaging pattern which the sender send a request and wait for a specific amount of time to get a response.  
The response can be successful or not. This is the responsibility of the responder to return with the result of the command within the time the sender set in the request  

#### Receiving Commands Requests  
```java
  String ChannelName = "testing_Command_channel", ClientID = "hello-world-sender",
          KubeMQServerAddress = "localhost:50000";
  Responder.RequestResponseObserver HandleIncomingRequests;
  Responder responder = new Responder(KubeMQServerAddress);
  HandleIncomingRequests = request -> {

      Response response = new Response(request);
      response.setCacheHit(false);
      response.setError("None");
      response.setClientID(ClientID);
      response.setBody("OK".getBytes());
      response.setExecuted(true);
      response.setMetadata("OK");
      response.setTimestamp(LocalDateTime.now());
      return response;
  };
  SubscribeRequest subscribeRequest = new SubscribeRequest();
  subscribeRequest.setChannel(ChannelName);
  subscribeRequest.setClientID(ClientID);
  subscribeRequest.setSubscribeType(SubscribeType.Commands);

  new Thread() {
      public void run() {
          try {
              responder.SubscribeToRequests(subscribeRequest, HandleIncomingRequests);
          } catch (SSLException e) {
              System.out.printf("SSLException:%s", e.getMessage());
              e.printStackTrace();
          } catch (ServerAddressNotSuppliedException e) {
              System.out.printf("ServerAddressNotSuppliedException:%s", e.getMessage());
              e.printStackTrace();
          }
      }
  }.start();
```

### Sending Command Request

```java
  String ChannelName = "testing_Command_channel", ClientID = "hello-world-sender",
          KubeMQServerAddress = "localhost:50000";
  io.kubemq.sdk.commandquery.ChannelParameters channelParameters = new io.kubemq.sdk.commandquery.ChannelParameters();
  channelParameters.setChannelName(ChannelName);
  channelParameters.setClientID(ClientID);
  channelParameters.setKubeMQAddress(KubeMQServerAddress);
  channelParameters.setRequestType(RequestType.Command);
  channelParameters.setTimeout(10000);
  io.kubemq.sdk.commandquery.Channel channel = new io.kubemq.sdk.commandquery.Channel(channelParameters);
  Request request = new Request();
  request.setBody(Converter.ToByteArray("hello kubemq - sending a command, please reply"));
  Response result = channel.SendRequest(request);
  if (!result.isExecuted()) {
      System.out.printf("Response error: %s", result.getError());
      return;
  }
  System.out.printf("Response Received: %s, ExecutedAt: %d", result.getRequestID(), result.getTimestamp());
```

### Sending Command Request Async  

```java
  String ChannelName = "testing_Command_channel", ClientID = "hello-world-sender",
          KubeMQServerAddress = "localhost:50000";
  io.kubemq.sdk.commandquery.ChannelParameters channelParameters = new io.kubemq.sdk.commandquery.ChannelParameters();
  channelParameters.setChannelName(ChannelName);
  channelParameters.setClientID(ClientID);
  channelParameters.setKubeMQAddress(KubeMQServerAddress);
  channelParameters.setRequestType(RequestType.Command);
  channelParameters.setTimeout(1000);
  io.kubemq.sdk.commandquery.Channel channel = new io.kubemq.sdk.commandquery.Channel(channelParameters);
  Request request = new Request();
  request.setBody(Converter.ToByteArray("hello kubemq - sending a command, please reply"));
  StreamObserver<Response> response = new StreamObserver<Response>() {

      @Override
      public void onNext(Response value) {
          if (!value.isExecuted()) {
              System.out.printf("Response error: %s", value.getError());
          }
      }

      @Override
      public void onError(Throwable t) {
          System.out.printf("RPC Error: %s", t.getMessage());
      }

      @Override
      public void onCompleted() {

      }
  };
  channel.SendRequestAsync(request, response);
```

## Queries

### Concept

Queries implement synchronous messaging pattern which the sender send a request and wait for a specific amount of time to get a response.  

The response must include metadata or body together with an indication of successful or not operation. This is the responsibility of the responder to return with the result of the query within the time the sender set in the request.

### Receiving Query Requests

```java
  String ChannelName = "testing_Command_channel", ClientID = "hello-world-sender",
          KubeMQServerAddress = "localhost:50000";
  Responder.RequestResponseObserver HandleIncomingRequests;
  Responder responder = new Responder(KubeMQServerAddress);
  HandleIncomingRequests = request -> {

      Response response = new Response(request);
      response.setCacheHit(false);
      response.setError("None");
      response.setClientID(ClientID);
      response.setBody("got your query, you are good to goo".getBytes());
      response.setExecuted(true);
      response.setMetadata("this is a response");
      response.setTimestamp(LocalDateTime.now());
      return response;
  };
  SubscribeRequest subscribeRequest = new SubscribeRequest();
  subscribeRequest.setChannel(ChannelName);
  subscribeRequest.setClientID(ClientID);
  subscribeRequest.setSubscribeType(SubscribeType.Queries);

  new Thread() {
      public void run() {

          try {
              responder.SubscribeToRequests(subscribeRequest, HandleIncomingRequests);
          } catch (SSLException e) {
              System.out.printf("SSLException: %s", e.getMessage());
              e.printStackTrace();
          } catch (ServerAddressNotSuppliedException e) {
              System.out.printf("ServerAddressNotSuppliedException: %s", e.getMessage());
              e.printStackTrace();
          }
      }
  }.start();
```

### Sending Query Requests

```java
  String ChannelName = "testing_Command_channel", ClientID = "hello-world-sender",
          KubeMQServerAddress = "localhost:50000";
  io.kubemq.sdk.commandquery.ChannelParameters channelParameters = new io.kubemq.sdk.commandquery.ChannelParameters();
  channelParameters.setChannelName(ChannelName);
  channelParameters.setClientID(ClientID);
  channelParameters.setKubeMQAddress(KubeMQServerAddress);
  channelParameters.setRequestType(RequestType.Query);
  channelParameters.setTimeout(1000);
  io.kubemq.sdk.commandquery.Channel channel = new io.kubemq.sdk.commandquery.Channel(channelParameters);
  Request request = new Request();
  request.setBody(Converter.ToByteArray("hello kubemq - sending a query, please reply"));
  Response result = channel.SendRequest(request);
  if (!result.isExecuted()) {

      System.out.printf("Response error: %s", result.getError());
      return;
  }
  System.out.printf("Response Received: %s, ExecutedAt: %d", result.getRequestID(), result.getTimestamp());
```

### Sending Query Requests async

```java
  String ChannelName = "testing_Command_channel", ClientID = "hello-world-sender",
          KubeMQServerAddress = "localhost:50000";
  io.kubemq.sdk.commandquery.ChannelParameters channelParameters = new io.kubemq.sdk.commandquery.ChannelParameters();
  channelParameters.setChannelName(ChannelName);
  channelParameters.setClientID(ClientID);
  channelParameters.setKubeMQAddress(KubeMQServerAddress);
  channelParameters.setRequestType(RequestType.Query);
  channelParameters.setTimeout(1000);
  io.kubemq.sdk.commandquery.Channel channel = new io.kubemq.sdk.commandquery.Channel(channelParameters);
  Request request = new Request();
  request.setBody(Converter.ToByteArray("hello kubemq - sending a query, please reply"));
  StreamObserver<Response> response = new StreamObserver<Response>() {

      @Override
      public void onNext(Response value) {
          if (!value.isExecuted()) {

              System.out.printf("Response error: %s", value.getError());
              System.out.printf("Response Received: %s, ExecutedAt %d", value.getRequestID(),
                      value.getTimestamp());
          }

      }

      @Override
      public void onError(Throwable t) {
          System.out.printf("onError: %s", t.getMessage());
      }

      @Override
      public void onCompleted() {

      }
  };
  channel.SendRequestAsync(request, response);  
```
