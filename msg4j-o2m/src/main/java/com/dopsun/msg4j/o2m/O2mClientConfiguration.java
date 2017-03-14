/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dopsun.msg4j.o2m;

import java.time.Duration;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

/**
 * Configuration to {@link O2mClient}.
 * 
 * <ul>
 * <li>General:
 * <ul>
 * <li>Version: it is <code>major.minor.update</code> format. Default <code>1.0.0</code>.</li>
 * <li>ClientId: client identifier.</li>
 * <li>ServerId: server identifier. Server may reject incoming connection if server id does not
 * match.</li>
 * <li>SessionIdSupplier: A producer to generate the session id. By default, it's generated with
 * <code>UUID.randomUUID().toString().replaceAll("-", "")</code>, and it's all lower case. This will
 * be used as input parameter for {@link #getSessionSubjectProducer()}.</li>
 * </ul>
 * </li>
 * 
 * <li>Subjects:
 * <ul>
 * <li>SystemSubject: subject for system messages from server to client, e.g. heart-beat from server
 * to client.</li>
 * <li>ReuestSubject: subject for request messages from client to server.</li>
 * <li>PublishSubjectProducer: producer by <code>channelCode</code> for subjects where server
 * publishing messages to client. This is a pattern to be enriched with channel code.</li>
 * <li>SessionSubjectProducer: producer by <code>channelCode</code> for subjects where session
 * messages from server to client, e.g. snapshots.</li>
 * </ul>
 * </li>
 * 
 * <li>Connection:
 * <ul>
 * <li>ConnectMaxRetries: max number of retries before giving up. default 3.</li>
 * <li>ConnectRetryInterval: time duration between two retries. Default 5 seconds.</li>
 * <li>MaxHeartbeatMissingCounts: number of heartbeats missing from server before reconnecting.
 * Default is 3.</li>
 * <li>DisconnectWaitForAcknowledge: wait for server to acknowledge disconnecting from server.
 * Default <code>false</code>.</li>
 * <li>DisconnectAcknowledgeTimeout: Duration for client to wait for server reply. Default is 5
 * seconds.</li>
 * </ul>
 * </li>
 * 
 * <li>Monitoring:
 * <ul>
 * <li>EventListener: event listener which can capture all events, including events during
 * initialization. default <code>null</code> .</li>
 * </ul>
 * </li>
 * 
 * </ul>
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public final class O2mClientConfiguration {
    /**
     * @return a new instance of O2mClientConfiguration
     */
    public static O2mClientConfiguration create() {
        return new O2mClientConfiguration();
    }

    private String version = "1.0.0";
    private String clientId;
    private String serverId;

    private Supplier<String> sessionIdSupplier = () -> {
        return UUID.randomUUID().toString().replaceAll("-", "");
    };

    private String systemSubject;
    private String requestSubject;
    private Function<String, String> publishSubjectProducer;
    private Function<String, String> sessionSubjectProducer;

    private int connectMaxRetries = 3;
    private Duration connectRetryInternal = Duration.ofSeconds(5);
    private int maxHeartbeatMissingCounts = 3;
    private boolean disconnectWaitForAckledge = false;
    private Duration disconnectAckTimeout = Duration.ofSeconds(5);

    private O2mClientEventListener eventListener;

    /**
     * @return client id
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param value
     * @return
     */
    public O2mClientConfiguration setClientId(String value) {
        this.clientId = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param value
     * @return
     */
    public O2mClientConfiguration setVersion(String value) {
        this.version = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * @param value
     * @return
     */
    public O2mClientConfiguration setServerId(String value) {
        this.serverId = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return the sessionIdSupplier
     */
    public Supplier<String> getSessionIdSupplier() {
        return sessionIdSupplier;
    }

    /**
     * @param value
     *            the sessionIdSupplier to set
     * @return
     */
    public O2mClientConfiguration setSessionIdSupplier(Supplier<String> value) {
        this.sessionIdSupplier = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return
     */
    public String getSystemSubject() {
        return systemSubject;
    }

    /**
     * @param value
     * @return
     */
    public O2mClientConfiguration setSystemSubject(String value) {
        this.systemSubject = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return
     */
    public String getRequestSubject() {
        return requestSubject;
    }

    /**
     * @param value
     * @return
     */
    public O2mClientConfiguration setRequestSubject(String value) {
        this.requestSubject = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return
     */
    public Function<String, String> getPublishSubjectProducer() {
        return publishSubjectProducer;
    }

    /**
     * @param value
     * @return
     */
    public O2mClientConfiguration setPublishSubjectProducer(Function<String, String> value) {
        this.publishSubjectProducer = Objects.requireNonNull(value);

        return this;
    }

    /**
     * Producer will be set with:
     * 
     * <pre>
     * String.format(format, channelCode);
     * </pre>
     * 
     * @param format
     *            format of the publish subject. It should include <code>%1$s</code>.
     * @return
     */
    public O2mClientConfiguration setPublishSubjectProducer(String format) {
        Objects.requireNonNull(format);
        if (!format.contains("%1$s")) {
            throw new IllegalArgumentException();
        }

        this.publishSubjectProducer = (channelCode) -> {
            return String.format(format, channelCode);
        };

        return this;
    }

    /**
     * @return
     */
    public Function<String, String> getSessionSubjectProducer() {
        return sessionSubjectProducer;
    }

    /**
     * @param value
     * @return
     */
    public O2mClientConfiguration setSessionSubjectProducer(Function<String, String> value) {
        this.sessionSubjectProducer = Objects.requireNonNull(value);

        return this;
    }

    /**
     * Producer will be set with:
     * 
     * <pre>
     * String.format(format, channelCode);
     * </pre>
     * 
     * @param format
     *            format of the publish subject. It should include <code>%1$s</code>.
     * @return
     */
    public O2mClientConfiguration setSessionSubjectProducer(String format) {
        Objects.requireNonNull(format);
        if (!format.contains("%1$s")) {
            throw new IllegalArgumentException();
        }

        this.sessionSubjectProducer = (channelCode) -> {
            return String.format(format, channelCode);
        };

        return this;
    }

    /**
     * @return the connectMaxRetries
     */
    public int getConnectMaxRetries() {
        return connectMaxRetries;
    }

    /**
     * @param value
     *            the connectMaxRetries to set
     * @return
     */
    public O2mClientConfiguration setConnectMaxRetries(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException();
        }

        this.connectMaxRetries = value;

        return this;
    }

    /**
     * @return the connectRetryInternal
     */
    public Duration getConnectRetryInternal() {
        return connectRetryInternal;
    }

    /**
     * @param value
     *            the connectRetryInternal to set
     * @return
     */
    public O2mClientConfiguration setConnectRetryInternal(Duration value) {
        this.connectRetryInternal = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return the disconnectWaitForAckledge
     */
    public boolean isDisconnectWaitForAckledge() {
        return disconnectWaitForAckledge;
    }

    /**
     * @param value
     *            the disconnectWaitForAckledge to set
     * @return
     */
    public O2mClientConfiguration setDisconnectWaitForAckledge(boolean value) {
        this.disconnectWaitForAckledge = value;

        return this;
    }

    /**
     * @return the disconnectAckTimeout
     */
    public Duration getDisconnectAckTimeout() {
        return disconnectAckTimeout;
    }

    /**
     * @param value
     *            the disconnectAckTimeout to set
     * @return
     */
    public O2mClientConfiguration setDisconnectAckTimeout(Duration value) {
        this.disconnectAckTimeout = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return the maxHeartbeatMissingCounts
     */
    public int getMaxHeartbeatMissingCounts() {
        return maxHeartbeatMissingCounts;
    }

    /**
     * @param value
     *            the maxHeartbeatMissingCounts to set
     * @return
     */
    public O2mClientConfiguration setMaxHeartbeatMissingCounts(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException();
        }

        this.maxHeartbeatMissingCounts = value;

        return this;
    }

    /**
     * @return the eventListener
     */
    @Nullable
    public O2mClientEventListener getEventListener() {
        return eventListener;
    }

    /**
     * @param value
     *            the eventListener to set
     * @return
     */
    public O2mClientConfiguration setEventListener(O2mClientEventListener value) {
        this.eventListener = Objects.requireNonNull(value);

        return this;
    }
}
