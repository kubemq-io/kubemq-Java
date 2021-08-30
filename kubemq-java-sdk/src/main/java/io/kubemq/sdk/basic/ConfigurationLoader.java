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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

class ConfigurationLoader {

    private static Logger logger = LoggerFactory.getLogger(ConfigurationLoader.class);

    /**
     * static cache variable to store the KubeMQ server address
     * once determined from environment variable or Java property
     *
     * @since 1.0
     */
    private static String _path = null;

    /**
     * static cache variable to store the KubeMQ registration key
     *
     * @since 1.0
     */
    private static String _key = null;

    /**
     * static cache variable to store the KubeMQ certificate file
     *
     * @since 1.0
     */
    private static String _cert = null;

    /**
     * static cache variable to store the gRPC KubeMQKeepAliveSeconds
     * this is useful when the kubemq server is behind a load balancer
     * that times out long lived inactive connections
     */
    private static String _keepaliveSeconds = null;

    /**
     * Get KubeMQ Server Address by priority:
     * 1. Environment Variable (KubeMQServerAddress)
     * 2. Java Property (by passing the -DKubeMQServerAddress= option to the JVM)
     *
     * @return KubeMQ server connection string or null if can't be determined
     * @apiNote The result is cached after the function is called once
     * @since 1.0
     */
    static String GetServerAddress() {
        if (StringUtils.isNotBlank(_path)) return _path;

        _path = GetFromEnvironmentVariable("KubeMQServerAddress");
        if (StringUtils.isNotBlank(_path)) {
            logger.info(MessageFormat.format("KubeMQ Server address found in Environment: {0}", _path));
            return _path;
        }

        _path = GetFromProperty("serverAddress");
        if (StringUtils.isNotBlank(_path)) {
            logger.info(MessageFormat.format("KubeMQ Server address set from property: {0}", _path));
            return _path;
        }

        logger.info(MessageFormat.format("KubeMQ Server address not found. using {0}", _path));
        return _path;
    }

    /**
     * Get the KubeMQ license key from environment variable or from Java property
     *
     * @return KubeMQ License Key or empty string if can't be determined
     * @apiNote The result is cached after the function is called once
     * @since 1.0
     */
    static String GetRegistrationKey() {
        if (_key != null) return _key;

        _key = GetFromEnvironmentVariable("KubeMQRegistrationKey");

        if (StringUtils.isNotBlank(_key)) return _key;

        _key = GetFromProperty("registrationKey");

        if (StringUtils.isBlank(_key)) {
            _key = "";
        }

        return _key;
    }

/**
     * Get the KubeMQ keep alive interval from environment variable or from Java property
     *
     * @return KubeMQ gRPC server keep alive time or empty string if can't be determined
     * @apiNote The result is cached after the function is called once
     */
    static String GetKeepAliveSeconds() {
        if (_keepaliveSeconds != null) return _keepaliveSeconds;

        _keepaliveSeconds = GetFromEnvironmentVariable("KubeMQKeepAliveSeconds");

        if (StringUtils.isNotBlank(_keepaliveSeconds)) return _keepaliveSeconds;

        _keepaliveSeconds = GetFromProperty("keepaliveSeconds");

        if (StringUtils.isBlank(_keepaliveSeconds)) {
            _keepaliveSeconds = "";
        }

        return _keepaliveSeconds;
    }

    /**
     * Get the KubeMQ certification file path from environment variable or from Java property
     *
     * @return KubeMQ Certification file path or empty string if can't be determined
     * @apiNote The result is cached after the function is called once
     * @since 1.0
     */
    static String GetCerificateFile() {
        if (_cert != null) return _cert;

        _cert = GetFromEnvironmentVariable("KubeMQCertificateFile");

        if (StringUtils.isNotBlank(_cert)) return _cert;

        _cert = GetFromProperty("certificateFile");

        if (StringUtils.isBlank(_cert)) {
            _cert = "";
        }

        return _cert;
    }

    private static String GetFromEnvironmentVariable(String key) {
        return System.getenv(key);
    }

    private static String GetFromProperty(String key) {
        return System.getProperty(key);
    }

}
