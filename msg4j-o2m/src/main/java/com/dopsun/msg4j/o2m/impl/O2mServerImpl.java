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

package com.dopsun.msg4j.o2m.impl;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Function;

import com.dopsun.msg4j.core.delivery.transports.ConsumerMode;
import com.dopsun.msg4j.core.delivery.transports.ProducerMode;
import com.dopsun.msg4j.core.delivery.transports.Transport;
import com.dopsun.msg4j.core.delivery.transports.TransportException;
import com.dopsun.msg4j.core.delivery.transports.TransportSubscriberSettings;
import com.dopsun.msg4j.core.delivery.transports.TransportSubscription;
import com.dopsun.msg4j.core.delivery.transports.TransportTopic;
import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.Message;
import com.dopsun.msg4j.o2m.O2mServer;
import com.dopsun.msg4j.o2m.O2mServerConfiguration;
import com.dopsun.msg4j.o2m.O2mServerEventListener;
import com.dopsun.msg4j.o2m.O2mServerRequestHandler;
import com.dopsun.msg4j.o2m.O2mServerSnapshotProvider;
import com.dopsun.msg4j.o2m.O2mServerState;
import com.dopsun.msg4j.o2m.O2mServiceException;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.ServerClientHeartbeatWriter;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.common.util.concurrent.Service;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class O2mServerImpl implements O2mServer, ServerRequestHandlerContext {
    /**
     * Transport used by this client.
     */
    private final Transport transport;

    private final String version = "1.0.0";

    private final String serverId;
    /** Auto generated session id. */
    private final String sessionId;

    private final Duration heartbeatInterval;

    private final O2mServerRequestHandler requestHandler;
    private final O2mServerSnapshotProvider snapshotProvider;

    private final Function<String, String> publishSubjectProducer;
    private final Function<String, String> sessionSubjectProducer;

    private final TransportTopic systemTopic;

    private final ChannelSystemPublisher<Void> systemPublisher;
    private final ConcurrentHashMap<String, ChannelPublishPublisher<Void>> publishPublishers = new ConcurrentHashMap<>();

    private final TransportSubscription requestSubscription;

    /**
     * State of this server.
     */
    private final AtomicReference<O2mServerState> state = new AtomicReference<>(
            O2mServerState.INITIALIZING);

    /**
     * A ReadWriteLock for {@link #state}
     */
    private final StampedLock stateLock = new StampedLock();

    /**
     * O2mServer events.
     */
    private final O2mServerEvents o2mServerEvents = new O2mServerEvents(this);

    /**
     * Guava service to publish heartbeat.
     */
    private final Service heartbeatPublishService;

    /**
     * Client session manager.
     */
    private final ClientSessionManager clientSessions = new ClientSessionManager();

    /**
     * System request handlers.
     */
    private final Map<String, ServerRequestHandler> systemRequestHandlers;

    O2mServerImpl(Transport transport, O2mServerConfiguration config)
            throws TransportException, O2mServiceException {
        Objects.requireNonNull(transport);
        Objects.requireNonNull(config);

        Objects.requireNonNull(config.getServerId());
        Objects.requireNonNull(config.getSessionIdSupplier());

        Objects.requireNonNull(config.getHeartbeatInterval());

        Objects.requireNonNull(config.getSystemSubject());
        Objects.requireNonNull(config.getRequestSubject());
        Objects.requireNonNull(config.getPublishSubjectProducer());
        Objects.requireNonNull(config.getSessionSubjectProducer());

        Objects.requireNonNull(config.getRequestHandler());
        Objects.requireNonNull(config.getSnapshotProvider());

        this.transport = transport;
        this.serverId = config.getServerId();
        this.sessionId = config.getSessionIdSupplier().get();

        this.heartbeatInterval = config.getHeartbeatInterval();

        this.requestHandler = config.getRequestHandler();
        this.snapshotProvider = config.getSnapshotProvider();

        this.sessionSubjectProducer = config.getSessionSubjectProducer();
        this.publishSubjectProducer = config.getPublishSubjectProducer();
        this.systemTopic = transport.createTopic(config.getSystemSubject(),
                ProducerMode.NON_PERSISTENT, ConsumerMode.NO_ACK);

        this.systemPublisher = new ChannelSystemPublisher<>(sessionId, (msg) -> {
            try {
                transport.publish(systemTopic, msg);
            } catch (Exception e) {
                o2mServerEvents.onPublishSystemFailed(msg, e);
            }

            return /* Void */ null;
        });

        /** @formatter:off */
        ImmutableMap.Builder<String, ServerRequestHandler> builder = ImmutableMap.builder();

        builder.put(O2mMessages.Messages.CLIENT_CONNECT_REQUEST, new ServerClientConnectRequestHandler(this));
        builder.put(O2mMessages.Messages.CLIENT_DISCONNECT_REQUEST, new ServerClientDisconnectRequestHandler(this));
        builder.put(O2mMessages.Messages.USER_REQUEST, new ServerUserRequestHandler(this));
        builder.put(O2mMessages.Messages.SUBSCRIBE_REQUEST, new ServerSubscribeRequestHandler(this));

        systemRequestHandlers = builder.build();
        /** @formatter:on */

        TransportTopic requestTopic = transport.createTopic(config.getRequestSubject(),
                ProducerMode.NON_PERSISTENT, ConsumerMode.NO_ACK);
        TransportSubscriberSettings requestSettings = TransportSubscriberSettings.create();
        requestSubscription = transport.subscribe(requestTopic, requestSettings,
                this::onRequestMessage);

        comareAndSetState(O2mServerState.INITIALIZING, O2mServerState.STARTING);

        comareAndSetState(O2mServerState.STARTING, O2mServerState.ACTIVE);

        systemPublisher.sendServerStartedNotification(serverId, version);

        heartbeatPublishService = new AbstractScheduledService() {
            @Override
            protected Scheduler scheduler() {
                return Scheduler.newFixedRateSchedule(heartbeatInterval.toMillis(),
                        heartbeatInterval.toMillis(), TimeUnit.MILLISECONDS);
            }

            @Override
            protected void runOneIteration() throws Exception {
                long stamp = stateLock.readLock();
                try {
                    if (state.get().equals(O2mServerState.ACTIVE)) {
                        ServerClientHeartbeatWriter writer = O2mMessages.newServerClientHeartbeat();

                        try {
                            systemPublisher.syncPublish(writer.getMessage());
                        } catch (Exception e) {
                            o2mServerEvents.onPublishHeartbeatFailed(e);
                        }
                    }
                } finally {
                    stateLock.unlockRead(stamp);
                }
            }
        };
        heartbeatPublishService.startAsync();
    }

    @Override
    public void close() throws Exception {
        comareAndSetState(O2mServerState.ACTIVE, O2mServerState.STOPPING);

        heartbeatPublishService.stopAsync().awaitTerminated();

        systemPublisher.sendServerStoppeddNotification(serverId);

        comareAndSetState(O2mServerState.STOPPING, O2mServerState.FINALIZING);
        requestSubscription.close();

        comareAndSetState(O2mServerState.FINALIZING, O2mServerState.CLOSED);
    }

    @Override
    public O2mServerState getState() {
        return state.get();
    }

    @Override
    public O2mServerEvents getEvents() {
        return o2mServerEvents;
    }

    @Override
    public Optional<O2mServerClientSessionImpl> getClientSession(String clientSessionId) {
        Objects.requireNonNull(clientSessionId);

        return clientSessions.ofSessionId(clientSessionId);
    }

    @Override
    public Optional<O2mServerClientSessionImpl> getClientSessionById(String clientId) {
        Objects.requireNonNull(clientId);

        return clientSessions.ofClientId(clientId);
    }

    @Override
    public O2mServerSnapshotProvider getSnapshotProvider() {
        return snapshotProvider;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public Duration getHeartbeatInterval() {
        return heartbeatInterval;
    }

    @Override
    public String getServerSessionId() {
        return sessionId;
    }

    @Override
    public String getServerId() {
        return serverId;
    }

    @Override
    public O2mServerRequestHandler getUserRequestHandler() {
        return requestHandler;
    }

    @Override
    public O2mServerClientSessionImpl createClientSession(String clientId, String clientSessionId) {
        String subject = sessionSubjectProducer.apply(clientSessionId);

        TransportTopic topic;
        try {
            topic = transport.createTopic(subject, ProducerMode.NON_PERSISTENT,
                    ConsumerMode.NO_ACK);
        } catch (UnsupportedOperationException | TransportException e) {
            o2mServerEvents.onFailedToCreateTopic(subject, e);

            throw new RuntimeException("Failed to create topic.", e);
        }

        ChannelSessionPublisher<Void> publisher = new ChannelSessionPublisher<>(sessionId,
                clientSessionId, m -> {
                    try {
                        transport.publish(topic, m);
                    } catch (TransportException e) {
                        o2mServerEvents.onPublishSessionFailed(clientId, clientSessionId, m, e);
                    }

                    return null;
                });

        O2mServerClientSessionImpl clientSession = new O2mServerClientSessionImpl(clientId,
                clientSessionId, publisher);

        Optional<O2mServerClientSessionImpl> optOld = clientSessions.putIfAbsent(clientSession);
        if (optOld.isPresent()) {
            try {
                optOld.get().getPublisher().sendClientSessionReplaced();
            } catch (O2mServiceException e) {
                o2mServerEvents.onPublishClientSessionReplacedFailed(clientId, clientSessionId, e);
            }

            clientSessions.compareAndPut(optOld.get().getSessionId(), clientSession);
        }

        return clientSession;
    }

    @Override
    public void addEventListener(O2mServerEventListener listener) {
        Objects.requireNonNull(listener);

        o2mServerEvents.addEventListener(listener);
    }

    @Override
    public boolean removeEventListener(O2mServerEventListener listener) {
        Objects.requireNonNull(listener);

        return o2mServerEvents.removeEventListener(listener);
    }

    @Override
    public void publish(String channelCode, ImmutableMessage message) throws O2mServiceException {
        Objects.requireNonNull(channelCode);
        Objects.requireNonNull(message);

        long stamp = stateLock.readLock();
        try {
            if (!state.get().equals(O2mServerState.ACTIVE)) {
                throw new O2mServiceException(
                        "Client's state expected: " + O2mServerState.ACTIVE + " actual: " + state);
            }

            ChannelPublishPublisher<Void> publisher = getOrCreatePublishPublisher(channelCode);
            publisher.publishChannelUpdate(message);
        } finally {
            stateLock.unlockRead(stamp);
        }
    }

    @Override
    public ChannelPublishPublisher<Void> getOrCreatePublishPublisher(String channelCode) {
        return publishPublishers.computeIfAbsent(channelCode, code -> {
            String subject = publishSubjectProducer.apply(code);

            TransportTopic topic;
            try {
                topic = transport.createTopic(subject, ProducerMode.NON_PERSISTENT,
                        ConsumerMode.NO_ACK);
            } catch (UnsupportedOperationException | TransportException e) {
                o2mServerEvents.onFailedToCreateTopic(subject, e);

                throw new RuntimeException("Failed to create topic.", e);
            }

            return new ChannelPublishPublisher<>(sessionId, m -> {
                try {
                    transport.publish(topic, m);

                    ImmutableMessage payload = O2mMessages.Fields.Payload.get(m);
                    snapshotProvider.onPublished(channelCode, payload);
                } catch (TransportException e) {
                    o2mServerEvents.onPublishPublishChannelFailed(channelCode, e);
                }

                return null;
            });
        });
    }

    private boolean comareAndSetState(O2mServerState oldState, O2mServerState newState) {
        long stamp = stateLock.writeLock();
        try {
            if (!state.compareAndSet(oldState, newState)) {
                return false;
            }

            o2mServerEvents.onStateChanged(oldState, newState);

            return true;
        } finally {
            stateLock.unlockWrite(stamp);
        }
    }

    private void onRequestMessage(Message message) {
        long stamp = this.stateLock.readLock();
        try {
            String msgType = O2mMessages.Fields.MessageType.get(message);

            ServerRequestHandler handler = systemRequestHandlers.get(msgType);
            if (handler == null) {
                o2mServerEvents.onRequestHandlerNotFound(msgType, message);

                return;
            }

            handler.onRequest(message);
        } finally {
            this.stateLock.unlockRead(stamp);
        }
    }

    private static class ClientSessionManager {
        private final ConcurrentHashMap<String, O2mServerClientSessionImpl> bySessionId = new ConcurrentHashMap<>();
        private final ConcurrentHashMap<String, O2mServerClientSessionImpl> byClientId = new ConcurrentHashMap<>();

        private final StampedLock lock = new StampedLock();

        public Optional<O2mServerClientSessionImpl> ofSessionId(String sessionId) {
            long stamp = lock.readLock();
            try {
                return Optional.ofNullable(bySessionId.get(sessionId));
            } finally {
                lock.unlockRead(stamp);
            }
        }

        public Optional<O2mServerClientSessionImpl> ofClientId(String clientId) {
            long stamp = lock.readLock();
            try {
                return Optional.ofNullable(byClientId.get(clientId));
            } finally {
                lock.unlockRead(stamp);
            }
        }

        public boolean compareAndPut(String oldSessionId, O2mServerClientSessionImpl session) {
            Objects.requireNonNull(oldSessionId);
            Objects.requireNonNull(session);
            if (oldSessionId.equals(session.getSessionId())) {
                throw new IllegalArgumentException();
            }

            long stamp = lock.writeLock();
            try {
                String sessionId = session.getSessionId();
                O2mServerClientSessionImpl oldValue = bySessionId.putIfAbsent(sessionId, session);
                if (oldValue == null) {
                    bySessionId.remove(sessionId);

                    return false;
                }

                if (!oldValue.getSessionId().equals(oldSessionId)) {
                    bySessionId.put(oldValue.getSessionId(), oldValue);
                    return false;
                }

                byClientId.put(session.getClientId(), session);

                return true;
            } finally {
                lock.unlockWrite(stamp);
            }
        }

        public Optional<O2mServerClientSessionImpl> putIfAbsent(
                O2mServerClientSessionImpl session) {
            long stamp = lock.writeLock();
            try {
                String sessionId = session.getSessionId();
                O2mServerClientSessionImpl oldValue = bySessionId.putIfAbsent(sessionId, session);
                if (oldValue != null) {
                    throw new UnsupportedOperationException("Same session id detected.");
                }

                String clientId = session.getClientId();
                oldValue = byClientId.putIfAbsent(clientId, session);
                if (oldValue != null) {
                    bySessionId.remove(sessionId);
                    byClientId.put(clientId, session);

                    return Optional.of(oldValue);
                }

                return Optional.empty();
            } finally {
                lock.unlockWrite(stamp);
            }
        }
    }
}
