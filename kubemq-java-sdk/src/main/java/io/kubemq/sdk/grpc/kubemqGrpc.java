package io.kubemq.sdk.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.16.1)",
    comments = "Source: kubemq.proto")
public final class kubemqGrpc {

  private kubemqGrpc() {}

  public static final String SERVICE_NAME = "kubemq.kubemq";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Event,
      io.kubemq.sdk.grpc.Kubemq.Result> getSendEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendEvent",
      requestType = io.kubemq.sdk.grpc.Kubemq.Event.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Event,
      io.kubemq.sdk.grpc.Kubemq.Result> getSendEventMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Event, io.kubemq.sdk.grpc.Kubemq.Result> getSendEventMethod;
    if ((getSendEventMethod = kubemqGrpc.getSendEventMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getSendEventMethod = kubemqGrpc.getSendEventMethod) == null) {
          kubemqGrpc.getSendEventMethod = getSendEventMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.Event, io.kubemq.sdk.grpc.Kubemq.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "SendEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Event.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Result.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("SendEvent"))
                  .build();
          }
        }
     }
     return getSendEventMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Event,
      io.kubemq.sdk.grpc.Kubemq.Result> getSendEventsStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendEventsStream",
      requestType = io.kubemq.sdk.grpc.Kubemq.Event.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.Result.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Event,
      io.kubemq.sdk.grpc.Kubemq.Result> getSendEventsStreamMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Event, io.kubemq.sdk.grpc.Kubemq.Result> getSendEventsStreamMethod;
    if ((getSendEventsStreamMethod = kubemqGrpc.getSendEventsStreamMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getSendEventsStreamMethod = kubemqGrpc.getSendEventsStreamMethod) == null) {
          kubemqGrpc.getSendEventsStreamMethod = getSendEventsStreamMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.Event, io.kubemq.sdk.grpc.Kubemq.Result>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "SendEventsStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Event.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Result.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("SendEventsStream"))
                  .build();
          }
        }
     }
     return getSendEventsStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Subscribe,
      io.kubemq.sdk.grpc.Kubemq.EventReceive> getSubscribeToEventsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubscribeToEvents",
      requestType = io.kubemq.sdk.grpc.Kubemq.Subscribe.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.EventReceive.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Subscribe,
      io.kubemq.sdk.grpc.Kubemq.EventReceive> getSubscribeToEventsMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Subscribe, io.kubemq.sdk.grpc.Kubemq.EventReceive> getSubscribeToEventsMethod;
    if ((getSubscribeToEventsMethod = kubemqGrpc.getSubscribeToEventsMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getSubscribeToEventsMethod = kubemqGrpc.getSubscribeToEventsMethod) == null) {
          kubemqGrpc.getSubscribeToEventsMethod = getSubscribeToEventsMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.Subscribe, io.kubemq.sdk.grpc.Kubemq.EventReceive>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "SubscribeToEvents"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Subscribe.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.EventReceive.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("SubscribeToEvents"))
                  .build();
          }
        }
     }
     return getSubscribeToEventsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Subscribe,
      io.kubemq.sdk.grpc.Kubemq.Request> getSubscribeToRequestsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubscribeToRequests",
      requestType = io.kubemq.sdk.grpc.Kubemq.Subscribe.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.Request.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Subscribe,
      io.kubemq.sdk.grpc.Kubemq.Request> getSubscribeToRequestsMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Subscribe, io.kubemq.sdk.grpc.Kubemq.Request> getSubscribeToRequestsMethod;
    if ((getSubscribeToRequestsMethod = kubemqGrpc.getSubscribeToRequestsMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getSubscribeToRequestsMethod = kubemqGrpc.getSubscribeToRequestsMethod) == null) {
          kubemqGrpc.getSubscribeToRequestsMethod = getSubscribeToRequestsMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.Subscribe, io.kubemq.sdk.grpc.Kubemq.Request>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "SubscribeToRequests"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Subscribe.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Request.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("SubscribeToRequests"))
                  .build();
          }
        }
     }
     return getSubscribeToRequestsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Request,
      io.kubemq.sdk.grpc.Kubemq.Response> getSendRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendRequest",
      requestType = io.kubemq.sdk.grpc.Kubemq.Request.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Request,
      io.kubemq.sdk.grpc.Kubemq.Response> getSendRequestMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Request, io.kubemq.sdk.grpc.Kubemq.Response> getSendRequestMethod;
    if ((getSendRequestMethod = kubemqGrpc.getSendRequestMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getSendRequestMethod = kubemqGrpc.getSendRequestMethod) == null) {
          kubemqGrpc.getSendRequestMethod = getSendRequestMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.Request, io.kubemq.sdk.grpc.Kubemq.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "SendRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Response.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("SendRequest"))
                  .build();
          }
        }
     }
     return getSendRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Response,
      io.kubemq.sdk.grpc.Kubemq.Empty> getSendResponseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendResponse",
      requestType = io.kubemq.sdk.grpc.Kubemq.Response.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Response,
      io.kubemq.sdk.grpc.Kubemq.Empty> getSendResponseMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Response, io.kubemq.sdk.grpc.Kubemq.Empty> getSendResponseMethod;
    if ((getSendResponseMethod = kubemqGrpc.getSendResponseMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getSendResponseMethod = kubemqGrpc.getSendResponseMethod) == null) {
          kubemqGrpc.getSendResponseMethod = getSendResponseMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.Response, io.kubemq.sdk.grpc.Kubemq.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "SendResponse"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Response.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Empty.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("SendResponse"))
                  .build();
          }
        }
     }
     return getSendResponseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.QueueMessage,
      io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult> getSendQueueMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendQueueMessage",
      requestType = io.kubemq.sdk.grpc.Kubemq.QueueMessage.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.QueueMessage,
      io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult> getSendQueueMessageMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.QueueMessage, io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult> getSendQueueMessageMethod;
    if ((getSendQueueMessageMethod = kubemqGrpc.getSendQueueMessageMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getSendQueueMessageMethod = kubemqGrpc.getSendQueueMessageMethod) == null) {
          kubemqGrpc.getSendQueueMessageMethod = getSendQueueMessageMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.QueueMessage, io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "SendQueueMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.QueueMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("SendQueueMessage"))
                  .build();
          }
        }
     }
     return getSendQueueMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest,
      io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse> getSendQueueMessagesBatchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendQueueMessagesBatch",
      requestType = io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest,
      io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse> getSendQueueMessagesBatchMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest, io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse> getSendQueueMessagesBatchMethod;
    if ((getSendQueueMessagesBatchMethod = kubemqGrpc.getSendQueueMessagesBatchMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getSendQueueMessagesBatchMethod = kubemqGrpc.getSendQueueMessagesBatchMethod) == null) {
          kubemqGrpc.getSendQueueMessagesBatchMethod = getSendQueueMessagesBatchMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest, io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "SendQueueMessagesBatch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("SendQueueMessagesBatch"))
                  .build();
          }
        }
     }
     return getSendQueueMessagesBatchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest,
      io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse> getReceiveQueueMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReceiveQueueMessages",
      requestType = io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest,
      io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse> getReceiveQueueMessagesMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest, io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse> getReceiveQueueMessagesMethod;
    if ((getReceiveQueueMessagesMethod = kubemqGrpc.getReceiveQueueMessagesMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getReceiveQueueMessagesMethod = kubemqGrpc.getReceiveQueueMessagesMethod) == null) {
          kubemqGrpc.getReceiveQueueMessagesMethod = getReceiveQueueMessagesMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest, io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "ReceiveQueueMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("ReceiveQueueMessages"))
                  .build();
          }
        }
     }
     return getReceiveQueueMessagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest,
      io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse> getStreamQueueMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamQueueMessage",
      requestType = io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest,
      io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse> getStreamQueueMessageMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest, io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse> getStreamQueueMessageMethod;
    if ((getStreamQueueMessageMethod = kubemqGrpc.getStreamQueueMessageMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getStreamQueueMessageMethod = kubemqGrpc.getStreamQueueMessageMethod) == null) {
          kubemqGrpc.getStreamQueueMessageMethod = getStreamQueueMessageMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest, io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "StreamQueueMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("StreamQueueMessage"))
                  .build();
          }
        }
     }
     return getStreamQueueMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest,
      io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse> getAckAllQueueMessagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AckAllQueueMessages",
      requestType = io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest,
      io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse> getAckAllQueueMessagesMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest, io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse> getAckAllQueueMessagesMethod;
    if ((getAckAllQueueMessagesMethod = kubemqGrpc.getAckAllQueueMessagesMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getAckAllQueueMessagesMethod = kubemqGrpc.getAckAllQueueMessagesMethod) == null) {
          kubemqGrpc.getAckAllQueueMessagesMethod = getAckAllQueueMessagesMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest, io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "AckAllQueueMessages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("AckAllQueueMessages"))
                  .build();
          }
        }
     }
     return getAckAllQueueMessagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Empty,
      io.kubemq.sdk.grpc.Kubemq.PingResult> getPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Ping",
      requestType = io.kubemq.sdk.grpc.Kubemq.Empty.class,
      responseType = io.kubemq.sdk.grpc.Kubemq.PingResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Empty,
      io.kubemq.sdk.grpc.Kubemq.PingResult> getPingMethod() {
    io.grpc.MethodDescriptor<io.kubemq.sdk.grpc.Kubemq.Empty, io.kubemq.sdk.grpc.Kubemq.PingResult> getPingMethod;
    if ((getPingMethod = kubemqGrpc.getPingMethod) == null) {
      synchronized (kubemqGrpc.class) {
        if ((getPingMethod = kubemqGrpc.getPingMethod) == null) {
          kubemqGrpc.getPingMethod = getPingMethod = 
              io.grpc.MethodDescriptor.<io.kubemq.sdk.grpc.Kubemq.Empty, io.kubemq.sdk.grpc.Kubemq.PingResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "kubemq.kubemq", "Ping"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.kubemq.sdk.grpc.Kubemq.PingResult.getDefaultInstance()))
                  .setSchemaDescriptor(new kubemqMethodDescriptorSupplier("Ping"))
                  .build();
          }
        }
     }
     return getPingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static kubemqStub newStub(io.grpc.Channel channel) {
    return new kubemqStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static kubemqBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new kubemqBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static kubemqFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new kubemqFutureStub(channel);
  }

  /**
   */
  public static abstract class kubemqImplBase implements io.grpc.BindableService {

    /**
     */
    public void sendEvent(io.kubemq.sdk.grpc.Kubemq.Event request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Result> responseObserver) {
      asyncUnimplementedUnaryCall(getSendEventMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Event> sendEventsStream(
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Result> responseObserver) {
      return asyncUnimplementedStreamingCall(getSendEventsStreamMethod(), responseObserver);
    }

    /**
     */
    public void subscribeToEvents(io.kubemq.sdk.grpc.Kubemq.Subscribe request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.EventReceive> responseObserver) {
      asyncUnimplementedUnaryCall(getSubscribeToEventsMethod(), responseObserver);
    }

    /**
     */
    public void subscribeToRequests(io.kubemq.sdk.grpc.Kubemq.Subscribe request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Request> responseObserver) {
      asyncUnimplementedUnaryCall(getSubscribeToRequestsMethod(), responseObserver);
    }

    /**
     */
    public void sendRequest(io.kubemq.sdk.grpc.Kubemq.Request request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Response> responseObserver) {
      asyncUnimplementedUnaryCall(getSendRequestMethod(), responseObserver);
    }

    /**
     */
    public void sendResponse(io.kubemq.sdk.grpc.Kubemq.Response request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getSendResponseMethod(), responseObserver);
    }

    /**
     */
    public void sendQueueMessage(io.kubemq.sdk.grpc.Kubemq.QueueMessage request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult> responseObserver) {
      asyncUnimplementedUnaryCall(getSendQueueMessageMethod(), responseObserver);
    }

    /**
     */
    public void sendQueueMessagesBatch(io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSendQueueMessagesBatchMethod(), responseObserver);
    }

    /**
     */
    public void receiveQueueMessages(io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getReceiveQueueMessagesMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest> streamQueueMessage(
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getStreamQueueMessageMethod(), responseObserver);
    }

    /**
     */
    public void ackAllQueueMessages(io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getAckAllQueueMessagesMethod(), responseObserver);
    }

    /**
     */
    public void ping(io.kubemq.sdk.grpc.Kubemq.Empty request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.PingResult> responseObserver) {
      asyncUnimplementedUnaryCall(getPingMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSendEventMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.Event,
                io.kubemq.sdk.grpc.Kubemq.Result>(
                  this, METHODID_SEND_EVENT)))
          .addMethod(
            getSendEventsStreamMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.Event,
                io.kubemq.sdk.grpc.Kubemq.Result>(
                  this, METHODID_SEND_EVENTS_STREAM)))
          .addMethod(
            getSubscribeToEventsMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.Subscribe,
                io.kubemq.sdk.grpc.Kubemq.EventReceive>(
                  this, METHODID_SUBSCRIBE_TO_EVENTS)))
          .addMethod(
            getSubscribeToRequestsMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.Subscribe,
                io.kubemq.sdk.grpc.Kubemq.Request>(
                  this, METHODID_SUBSCRIBE_TO_REQUESTS)))
          .addMethod(
            getSendRequestMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.Request,
                io.kubemq.sdk.grpc.Kubemq.Response>(
                  this, METHODID_SEND_REQUEST)))
          .addMethod(
            getSendResponseMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.Response,
                io.kubemq.sdk.grpc.Kubemq.Empty>(
                  this, METHODID_SEND_RESPONSE)))
          .addMethod(
            getSendQueueMessageMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.QueueMessage,
                io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult>(
                  this, METHODID_SEND_QUEUE_MESSAGE)))
          .addMethod(
            getSendQueueMessagesBatchMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest,
                io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse>(
                  this, METHODID_SEND_QUEUE_MESSAGES_BATCH)))
          .addMethod(
            getReceiveQueueMessagesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest,
                io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse>(
                  this, METHODID_RECEIVE_QUEUE_MESSAGES)))
          .addMethod(
            getStreamQueueMessageMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest,
                io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse>(
                  this, METHODID_STREAM_QUEUE_MESSAGE)))
          .addMethod(
            getAckAllQueueMessagesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest,
                io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse>(
                  this, METHODID_ACK_ALL_QUEUE_MESSAGES)))
          .addMethod(
            getPingMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.kubemq.sdk.grpc.Kubemq.Empty,
                io.kubemq.sdk.grpc.Kubemq.PingResult>(
                  this, METHODID_PING)))
          .build();
    }
  }

  /**
   */
  public static final class kubemqStub extends io.grpc.stub.AbstractStub<kubemqStub> {
    private kubemqStub(io.grpc.Channel channel) {
      super(channel);
    }

    private kubemqStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected kubemqStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new kubemqStub(channel, callOptions);
    }

    /**
     */
    public void sendEvent(io.kubemq.sdk.grpc.Kubemq.Event request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Result> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendEventMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Event> sendEventsStream(
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Result> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getSendEventsStreamMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void subscribeToEvents(io.kubemq.sdk.grpc.Kubemq.Subscribe request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.EventReceive> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSubscribeToEventsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void subscribeToRequests(io.kubemq.sdk.grpc.Kubemq.Subscribe request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Request> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getSubscribeToRequestsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendRequest(io.kubemq.sdk.grpc.Kubemq.Request request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Response> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendResponse(io.kubemq.sdk.grpc.Kubemq.Response request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendResponseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendQueueMessage(io.kubemq.sdk.grpc.Kubemq.QueueMessage request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendQueueMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendQueueMessagesBatch(io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendQueueMessagesBatchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void receiveQueueMessages(io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReceiveQueueMessagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesRequest> streamQueueMessage(
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getStreamQueueMessageMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void ackAllQueueMessages(io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAckAllQueueMessagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ping(io.kubemq.sdk.grpc.Kubemq.Empty request,
        io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.PingResult> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class kubemqBlockingStub extends io.grpc.stub.AbstractStub<kubemqBlockingStub> {
    private kubemqBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private kubemqBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected kubemqBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new kubemqBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.kubemq.sdk.grpc.Kubemq.Result sendEvent(io.kubemq.sdk.grpc.Kubemq.Event request) {
      return blockingUnaryCall(
          getChannel(), getSendEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<io.kubemq.sdk.grpc.Kubemq.EventReceive> subscribeToEvents(
        io.kubemq.sdk.grpc.Kubemq.Subscribe request) {
      return blockingServerStreamingCall(
          getChannel(), getSubscribeToEventsMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<io.kubemq.sdk.grpc.Kubemq.Request> subscribeToRequests(
        io.kubemq.sdk.grpc.Kubemq.Subscribe request) {
      return blockingServerStreamingCall(
          getChannel(), getSubscribeToRequestsMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.kubemq.sdk.grpc.Kubemq.Response sendRequest(io.kubemq.sdk.grpc.Kubemq.Request request) {
      return blockingUnaryCall(
          getChannel(), getSendRequestMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.kubemq.sdk.grpc.Kubemq.Empty sendResponse(io.kubemq.sdk.grpc.Kubemq.Response request) {
      return blockingUnaryCall(
          getChannel(), getSendResponseMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult sendQueueMessage(io.kubemq.sdk.grpc.Kubemq.QueueMessage request) {
      return blockingUnaryCall(
          getChannel(), getSendQueueMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse sendQueueMessagesBatch(io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest request) {
      return blockingUnaryCall(
          getChannel(), getSendQueueMessagesBatchMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse receiveQueueMessages(io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest request) {
      return blockingUnaryCall(
          getChannel(), getReceiveQueueMessagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse ackAllQueueMessages(io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest request) {
      return blockingUnaryCall(
          getChannel(), getAckAllQueueMessagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.kubemq.sdk.grpc.Kubemq.PingResult ping(io.kubemq.sdk.grpc.Kubemq.Empty request) {
      return blockingUnaryCall(
          getChannel(), getPingMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class kubemqFutureStub extends io.grpc.stub.AbstractStub<kubemqFutureStub> {
    private kubemqFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private kubemqFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected kubemqFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new kubemqFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.kubemq.sdk.grpc.Kubemq.Result> sendEvent(
        io.kubemq.sdk.grpc.Kubemq.Event request) {
      return futureUnaryCall(
          getChannel().newCall(getSendEventMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.kubemq.sdk.grpc.Kubemq.Response> sendRequest(
        io.kubemq.sdk.grpc.Kubemq.Request request) {
      return futureUnaryCall(
          getChannel().newCall(getSendRequestMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.kubemq.sdk.grpc.Kubemq.Empty> sendResponse(
        io.kubemq.sdk.grpc.Kubemq.Response request) {
      return futureUnaryCall(
          getChannel().newCall(getSendResponseMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult> sendQueueMessage(
        io.kubemq.sdk.grpc.Kubemq.QueueMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getSendQueueMessageMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse> sendQueueMessagesBatch(
        io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSendQueueMessagesBatchMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse> receiveQueueMessages(
        io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getReceiveQueueMessagesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse> ackAllQueueMessages(
        io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAckAllQueueMessagesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.kubemq.sdk.grpc.Kubemq.PingResult> ping(
        io.kubemq.sdk.grpc.Kubemq.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getPingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_EVENT = 0;
  private static final int METHODID_SUBSCRIBE_TO_EVENTS = 1;
  private static final int METHODID_SUBSCRIBE_TO_REQUESTS = 2;
  private static final int METHODID_SEND_REQUEST = 3;
  private static final int METHODID_SEND_RESPONSE = 4;
  private static final int METHODID_SEND_QUEUE_MESSAGE = 5;
  private static final int METHODID_SEND_QUEUE_MESSAGES_BATCH = 6;
  private static final int METHODID_RECEIVE_QUEUE_MESSAGES = 7;
  private static final int METHODID_ACK_ALL_QUEUE_MESSAGES = 8;
  private static final int METHODID_PING = 9;
  private static final int METHODID_SEND_EVENTS_STREAM = 10;
  private static final int METHODID_STREAM_QUEUE_MESSAGE = 11;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final kubemqImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(kubemqImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_EVENT:
          serviceImpl.sendEvent((io.kubemq.sdk.grpc.Kubemq.Event) request,
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Result>) responseObserver);
          break;
        case METHODID_SUBSCRIBE_TO_EVENTS:
          serviceImpl.subscribeToEvents((io.kubemq.sdk.grpc.Kubemq.Subscribe) request,
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.EventReceive>) responseObserver);
          break;
        case METHODID_SUBSCRIBE_TO_REQUESTS:
          serviceImpl.subscribeToRequests((io.kubemq.sdk.grpc.Kubemq.Subscribe) request,
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Request>) responseObserver);
          break;
        case METHODID_SEND_REQUEST:
          serviceImpl.sendRequest((io.kubemq.sdk.grpc.Kubemq.Request) request,
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Response>) responseObserver);
          break;
        case METHODID_SEND_RESPONSE:
          serviceImpl.sendResponse((io.kubemq.sdk.grpc.Kubemq.Response) request,
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Empty>) responseObserver);
          break;
        case METHODID_SEND_QUEUE_MESSAGE:
          serviceImpl.sendQueueMessage((io.kubemq.sdk.grpc.Kubemq.QueueMessage) request,
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.SendQueueMessageResult>) responseObserver);
          break;
        case METHODID_SEND_QUEUE_MESSAGES_BATCH:
          serviceImpl.sendQueueMessagesBatch((io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchRequest) request,
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.QueueMessagesBatchResponse>) responseObserver);
          break;
        case METHODID_RECEIVE_QUEUE_MESSAGES:
          serviceImpl.receiveQueueMessages((io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesRequest) request,
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.ReceiveQueueMessagesResponse>) responseObserver);
          break;
        case METHODID_ACK_ALL_QUEUE_MESSAGES:
          serviceImpl.ackAllQueueMessages((io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesRequest) request,
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.AckAllQueueMessagesResponse>) responseObserver);
          break;
        case METHODID_PING:
          serviceImpl.ping((io.kubemq.sdk.grpc.Kubemq.Empty) request,
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.PingResult>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_EVENTS_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.sendEventsStream(
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.Result>) responseObserver);
        case METHODID_STREAM_QUEUE_MESSAGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.streamQueueMessage(
              (io.grpc.stub.StreamObserver<io.kubemq.sdk.grpc.Kubemq.StreamQueueMessagesResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class kubemqBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    kubemqBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.kubemq.sdk.grpc.Kubemq.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("kubemq");
    }
  }

  private static final class kubemqFileDescriptorSupplier
      extends kubemqBaseDescriptorSupplier {
    kubemqFileDescriptorSupplier() {}
  }

  private static final class kubemqMethodDescriptorSupplier
      extends kubemqBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    kubemqMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (kubemqGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new kubemqFileDescriptorSupplier())
              .addMethod(getSendEventMethod())
              .addMethod(getSendEventsStreamMethod())
              .addMethod(getSubscribeToEventsMethod())
              .addMethod(getSubscribeToRequestsMethod())
              .addMethod(getSendRequestMethod())
              .addMethod(getSendResponseMethod())
              .addMethod(getSendQueueMessageMethod())
              .addMethod(getSendQueueMessagesBatchMethod())
              .addMethod(getReceiveQueueMessagesMethod())
              .addMethod(getStreamQueueMessageMethod())
              .addMethod(getAckAllQueueMessagesMethod())
              .addMethod(getPingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
