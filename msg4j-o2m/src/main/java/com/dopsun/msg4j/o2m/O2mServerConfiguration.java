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
 * <ul>
 * <li>General:
 * <ul>
 * <li>Version: it is <code>major.minor.update</code> format.</li>
 * <li>ServerId: server identifier. Server may rejected incoming connection if server id not
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
 * <li>PublishSubjectPattern: subject pattern for subjects where server publishing messages to
 * client. This is a pattern to be enriched with channel code.</li>
 * <li>SessionSubjectPattern: subject for session messages from server to client, e.g.
 * snapshots.</li>
 * </ul>
 * </li>
 * 
 * <li>Connection:
 * <ul>
 * <li>HeartbeatInternal: heartbeat interval for server to client.</li>
 * </ul>
 * </li>
 * 
 * <li>Features:
 * <ul>
 * <li>RequestHandler: handler to accept requests from client.</li>
 * <li>SapshotProvider: provider to produce snapshots once requested.</li>
 * <li>EventListener: listener to server, which can hook before any event.</li>
 * </ul>
 * </li>
 * </ul>
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public final class O2mServerConfiguration {
    /**
     * @return
     */
    public static O2mServerConfiguration create() {
        return new O2mServerConfiguration();
    }

    private String serverId;
    private Supplier<String> sessionIdSupplier = () -> {
        return UUID.randomUUID().toString().replaceAll("-", "");
    };

    private String systemSubject;
    private String requestSubject;
    private Function<String, String> publishSubjectProducer;
    private Function<String, String> sessionSubjectProducer;

    private Duration heartbeatInterval = Duration.ofSeconds(5);

    private O2mServerRequestHandler requestHandler = (session, request) -> {
        throw new RuntimeException("Not supported request: " + request);
    };

    private O2mServerSnapshotProvider snapshotProvider = O2mServerSnapshotProvider.EMPTY;

    private O2mServerEventListener eventListener;

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
    public O2mServerConfiguration setServerId(String value) {
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
     */
    public void setSessionIdSupplier(Supplier<String> value) {
        this.sessionIdSupplier = Objects.requireNonNull(value);
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
    public O2mServerConfiguration setSystemSubject(String value) {
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
    public O2mServerConfiguration setRequestSubject(String value) {
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
    public O2mServerConfiguration setPublishSubjectProducer(Function<String, String> value) {
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
    public O2mServerConfiguration setPublishSubjectProducer(String format) {
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
    public O2mServerConfiguration setSessionSubjectProducer(Function<String, String> value) {
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
    public O2mServerConfiguration setSessionSubjectProducer(String format) {
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
     * @return the heartbeatInterval
     */
    public Duration getHeartbeatInterval() {
        return heartbeatInterval;
    }

    /**
     * @param value
     *            the heartbeatInterval to set
     * @return
     */
    public O2mServerConfiguration setHeartbeatInterval(Duration value) {
        this.heartbeatInterval = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return the requestHandler
     */
    @Nullable
    public O2mServerRequestHandler getRequestHandler() {
        return requestHandler;
    }

    /**
     * @param value
     *            the requestHandler to set
     * @return
     */
    public O2mServerConfiguration setRequestHandler(O2mServerRequestHandler value) {
        this.requestHandler = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return the snapshotProvider
     */
    @Nullable
    public O2mServerSnapshotProvider getSnapshotProvider() {
        return snapshotProvider;
    }

    /**
     * @param value
     *            the snapshotProvider to set
     * @return
     */
    public O2mServerConfiguration setSnapshotProvider(O2mServerSnapshotProvider value) {
        this.snapshotProvider = Objects.requireNonNull(value);

        return this;
    }

    /**
     * @return the eventListener
     */
    @Nullable
    public O2mServerEventListener getEventListener() {
        return eventListener;
    }

    /**
     * @param value
     *            the eventListener to set
     * @return
     */
    public O2mServerConfiguration setEventListener(O2mServerEventListener value) {
        this.eventListener = Objects.requireNonNull(value);

        return this;
    }
}
