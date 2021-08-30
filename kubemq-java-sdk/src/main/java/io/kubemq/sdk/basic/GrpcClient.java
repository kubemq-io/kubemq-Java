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
package io.kubemq.sdk.basic;

import io.kubemq.sdk.grpc.kubemqGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.MetadataUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLException;
import java.io.File;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class GrpcClient {

    protected String _kubemqAddress;
    protected Long _keepaliveSeconds;
    protected Metadata _metadata = null;
    protected String _authToken = null;
    private ManagedChannel channel = null;
    private kubemqGrpc.kubemqBlockingStub blockingStub = null;
    private kubemqGrpc.kubemqStub stub = null;

    protected GrpcClient() {
        InitRegistration();
    }

    public String getServerAddress() throws ServerAddressNotSuppliedException {
        return getKubeMQAddress();
    }

    public void setServerAddress(String value) {
        this._kubemqAddress = value;
    }

    public Metadata getMetadata() {
        return this._metadata;
    }

    public void addAuthToken(String authToken) {
        if (authToken == null) {
            return;
        }

        if (this._metadata == null) {
            _metadata = new Metadata();
        }
        Metadata.Key<String> key = Metadata.Key.of("authorization", ASCII_STRING_MARSHALLER);
        _metadata.put(key, authToken);
    }

    /**
     *  
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.
     */
    protected kubemqGrpc.kubemqBlockingStub GetKubeMQClient()
            throws ServerAddressNotSuppliedException, SSLException {
        if (blockingStub == null) {
            // Open connection
            if (channel == null) {
                channel = constructChannel();
            }

            blockingStub = constructBlockingClient(channel);

            if (_metadata != null) {
                blockingStub = MetadataUtils.attachHeaders(blockingStub, _metadata);
            }
        }

        return blockingStub;
    }

     /**
     *  
     * @throws ServerAddressNotSuppliedException KubeMQ server address can not be determined.
     * @throws SSLException                      Indicates some kind of error detected by an SSL subsystem.   
     */
    protected kubemqGrpc.kubemqStub GetKubeMQAsyncClient()
            throws ServerAddressNotSuppliedException, SSLException {
        if (stub == null) {
            // Open connection
            if (channel == null) {
                channel = constructChannel();
            }

            stub = constructAsyncClient(channel);
            if (_metadata != null) {
                stub = MetadataUtils.attachHeaders(stub, _metadata);
            }
        }

        return stub;
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.NANOSECONDS);
    }

    public void shutdownNow() {
        channel.shutdownNow();
    }
  
    public boolean isTerminated() {
        return channel.isTerminated();
    }

    /**
     * Construct blockingStub connecting to KubeMQ server at {@code host}
     * (host:port).
     *
     * @return io.grpc.ManagedChannel object used to access the KubeMQ server
     */
    private ManagedChannel constructChannel() throws ServerAddressNotSuppliedException, SSLException {
        String kubemqAddress = getKubeMQAddress();
        String clientCertFile = ConfigurationLoader.GetCerificateFile();
        Long keepAliveTime = getKubeMQKeepAliveInterval();

        Logger logger = getLogger();
        if (logger.isInfoEnabled()) {
            getLogger().info(MessageFormat.format("constructing channel to KubeMQ on {0}", kubemqAddress));
        }


        if (!StringUtils.isBlank(clientCertFile)) {
            NettyChannelBuilder channel = NettyChannelBuilder.forTarget(kubemqAddress)
                    .sslContext(GrpcSslContexts.forClient().trustManager(new File(clientCertFile)).build());
            if (keepAliveTime != null) {
                return channel.keepAliveTime(keepAliveTime, TimeUnit.SECONDS).build();
            } else {
                return channel.build();
            }
        } else {
            ManagedChannelBuilder channel = ManagedChannelBuilder.forTarget(kubemqAddress).usePlaintext();
            if (keepAliveTime != null) {
                return channel.keepAliveTime(keepAliveTime, TimeUnit.SECONDS).build();
            } else {
                return channel.build();
            }
        }
    }

    private org.slf4j.Logger getLogger() {
        return LoggerFactory.getLogger(GrpcClient.class);
    }

    /**
     * Construct blocking blockingStub for accessing KubeMQ server using the
     * existing {@code channel}
     *
     * @param channel Client channel connecting to KubeMQ server
     * @return BlockingStub for accessing KubeMQ server
     */
    private kubemqGrpc.kubemqBlockingStub constructBlockingClient(ManagedChannel channel) {
        return kubemqGrpc.newBlockingStub(channel);
    }

    /**
     * Construct async blockingStub for accessing KubeMQ server using the existing
     * {@code channel}
     *
     * @param channel Client channel connecting to KubeMQ server
     * @return Stub for accessing KubeMQ server
     */
    private kubemqGrpc.kubemqStub constructAsyncClient(ManagedChannel channel) {
        return kubemqGrpc.newStub(channel);  
    }

    private String getKubeMQAddress() throws ServerAddressNotSuppliedException {
        // _kubemqAddress was supplied in the derived constructor
        if (StringUtils.isNotBlank(_kubemqAddress))
            return _kubemqAddress;

        _kubemqAddress = ConfigurationLoader.GetServerAddress();

        if (StringUtils.isBlank(_kubemqAddress)) {
            throw new ServerAddressNotSuppliedException ();
        }

        return _kubemqAddress;
    }

    private Long getKubeMQKeepAliveInterval() {
        // _keepaliveSeconds was supplied in the derived constructor
        if (_keepaliveSeconds != null) {
            return _keepaliveSeconds;
        } else {
            String kaSeconds = ConfigurationLoader.GetKeepAliveSeconds();
            if (StringUtils.isBlank(kaSeconds)) {
                return null;
            }
            return Long.parseLong(kaSeconds);
        }
    }

    private void InitRegistration() {
        String registrationKey = ConfigurationLoader.GetRegistrationKey();

        if (StringUtils.isNotBlank(registrationKey)) {
            _metadata = new Metadata();
            Metadata.Key<String> key = Metadata.Key.of("X-Kubemq-Server-Token", ASCII_STRING_MARSHALLER);
            _metadata.put(key, registrationKey);
        }
    }

}
