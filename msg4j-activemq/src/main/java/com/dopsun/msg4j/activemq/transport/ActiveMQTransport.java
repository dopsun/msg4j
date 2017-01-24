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

package com.dopsun.msg4j.activemq.transport;

import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import com.dopsun.msg4j.core.delivery.transports.ConsumerMode;
import com.dopsun.msg4j.core.delivery.transports.ProducerMode;
import com.dopsun.msg4j.core.delivery.transports.Transport;
import com.dopsun.msg4j.core.delivery.transports.TransportCapability;
import com.dopsun.msg4j.core.delivery.transports.TransportDestination;
import com.dopsun.msg4j.core.delivery.transports.TransportEventArgs;
import com.dopsun.msg4j.core.delivery.transports.TransportEventListener;
import com.dopsun.msg4j.core.delivery.transports.TransportException;
import com.dopsun.msg4j.core.delivery.transports.TransportSubscriberSettings;
import com.dopsun.msg4j.core.delivery.transports.TransportSubscription;
import com.dopsun.msg4j.core.delivery.transports.TransportTopic;
import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.Message;
import com.google.common.collect.ImmutableList;

/**
 * Transport implementation based on ActiveMQ.
 * 
 * <p>
 * Notes to implementors:
 * <ul>
 * <li>Consumer mode is session specific, so if different destination have different mode, then
 * multiple sessions will be created.</li>
 * <li>Producer will be created on the session with default consumer mode passed in as
 * configuration.</li>
 * <li>Transaction is a passed in parameters. Session for producer will be configured for the
 * transaction.</li>
 * </ul>
 * </p>
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
public class ActiveMQTransport implements Transport {
    /**
     * Default capability of ActiveMQ transport. The exact capability may different from this, e.g.
     * transacted or not.
     */
    /* @formatter:off */
    private static final EnumSet<TransportCapability> DEFAULT_CAPABILITIES = EnumSet
            .of(
                    TransportCapability.Guaranteed, 
                    TransportCapability.DurableSubscriber,
                    TransportCapability.Selector
               );
    /* @formatter:on */

    private final ActiveMQSerializer serializer;
    private final boolean isTransacted;

    private final EnumSet<TransportCapability> capabilities;

    private final ConcurrentHashMap<ActiveMQTransportDestination, ActiveMQMessageProducer> producerBySubject = new ConcurrentHashMap<>();

    private final ActiveMQConnection connection;

    /**
     * Sessions indexed by {@link ConsumerMode}. This should be manipulated by
     * {@link #getOrCreateSession(ConsumerMode)}
     */
    private final ConcurrentHashMap<ConsumerMode, ActiveMQSession> sessionByConsumerMode = new ConcurrentHashMap<>();

    /**
     * This is the session created together with default consumer mode.
     * <p>
     * <b>Node:</b>: this session has been included in <code>sessionByConsumerMode</code>.
     * </p>
     */
    private final ActiveMQSession producerSession;

    /**
     * Listers is volatile and immutable. It can be replaced as a whole. See
     * <code>trigerTransportEvent</code> to see how this field to be used.
     */
    private volatile ImmutableList<TransportEventListener> eventListeners = ImmutableList.of();

    /**
     * @param config
     * @throws JMSException
     */
    public ActiveMQTransport(ActiveMQTransportConfiguration config) throws JMSException {
        Objects.requireNonNull(config);
        Objects.requireNonNull(config.getBrokerUrl());
        Objects.requireNonNull(config.getConsumerMode());

        this.isTransacted = config.isTransacted();
        this.serializer = new ActiveMQSerializer();

        this.capabilities = EnumSet.copyOf(DEFAULT_CAPABILITIES);
        if (isTransacted) {
            this.capabilities.add(TransportCapability.Transaction);
        }

        String brokerUrl = config.getBrokerUrl();

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        ActiveMQConnection amqConnection = (ActiveMQConnection) connectionFactory
                .createConnection();

        amqConnection.setExceptionListener(this::onConnectionException);

        amqConnection.start();

        this.connection = amqConnection;

        this.producerSession = getOrCreateSession(config.getConsumerMode());
    }

    private void onConnectionException(JMSException exception) {
        TransportEventArgs eventArgs = new TransportEventArgs(TransportEventArgs.EXCEPTION_EVENT_ID,
                "Underneath connection reports an exception.", exception);

        trigerTransportEvent(eventArgs);
    }

    @Override
    public void close() throws Exception {
        try {
            for (ActiveMQMessageProducer producer : producerBySubject.values()) {
                producer.close();
            }
            producerBySubject.clear();

            for (ActiveMQSession session : sessionByConsumerMode.values()) {
                session.close();
            }
            sessionByConsumerMode.clear();

            this.connection.stop();

            if (this.connection instanceof ActiveMQConnection) {
                ((ActiveMQConnection) this.connection).getSessionTaskRunner().shutdownNow();
            }
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    @Override
    public EnumSet<TransportCapability> capabilities() {
        return EnumSet.copyOf(capabilities);
    }

    @Override
    public void addEventListener(TransportEventListener listener) throws TransportException {
        Objects.requireNonNull(listener);

        /** @formatter:off */
        eventListeners = ImmutableList.<TransportEventListener> builder()
                .addAll(eventListeners)
                .add(listener)
                .build();
        /** @formatter:on */
    }

    @Override
    public void removeEventListener(TransportEventListener listener) throws TransportException {
        Objects.requireNonNull(listener);

        ImmutableList.Builder<TransportEventListener> builder = ImmutableList.builder();

        boolean found = false;
        for (TransportEventListener item : eventListeners) {
            if (!found && item.equals(listener)) {
                found = true;
            } else {
                builder.add(item);
            }
        }

        eventListeners = builder.build();
    }

    @Override
    public ActiveMQTransportQueue createQueue(String subject, ProducerMode producerMode,
            ConsumerMode consumerMode) throws TransportException, UnsupportedOperationException {
        Objects.requireNonNull(subject);
        Objects.requireNonNull(producerMode);
        Objects.requireNonNull(consumerMode);

        try {
            ActiveMQSession session = getOrCreateSession(consumerMode);
            ActiveMQQueue queue = (ActiveMQQueue) session.createQueue(subject);
            return new ActiveMQTransportQueue(queue, producerMode, consumerMode);
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    @Override
    public TransportTopic createTopic(String subject, ProducerMode producerMode,
            ConsumerMode consumerMode) throws TransportException {
        Objects.requireNonNull(subject);
        Objects.requireNonNull(producerMode);
        Objects.requireNonNull(consumerMode);

        try {
            ActiveMQSession session = getOrCreateSession(consumerMode);
            ActiveMQTopic topic = (ActiveMQTopic) session.createTopic(subject);
            return new ActiveMQTransportTopic(topic, producerMode, consumerMode);
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    @Override
    public void publish(TransportDestination destination, Message message)
            throws TransportException {
        Objects.requireNonNull(destination);
        Objects.requireNonNull(message);

        if (!(destination instanceof ActiveMQTransportDestination)) {
            throw new IllegalArgumentException();
        }

        ActiveMQTransportDestination amqDestination = (ActiveMQTransportDestination) destination;

        ActiveMQMessageProducer producer = producerBySubject.get(amqDestination);
        if (producer == null) {
            try {
                producer = (ActiveMQMessageProducer) producerSession
                        .createProducer(amqDestination.getDestination());

                if (ProducerMode.PERSISTENT == amqDestination.getProducerMode()) {
                    producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                }
            } catch (JMSException e) {
                throw new TransportException(e);
            }
            producerBySubject.put(amqDestination, producer);
        }

        try {
            javax.jms.Message jmsMessage = serializer.toJms(producerSession, message);
            producer.send(jmsMessage);
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    @Override
    public TransportSubscription subscribe(TransportDestination destination,
            TransportSubscriberSettings settings, Consumer<Message> consumer)
            throws TransportException {
        Objects.requireNonNull(destination);
        Objects.requireNonNull(settings);
        Objects.requireNonNull(consumer);

        if (!(destination instanceof ActiveMQTransportDestination)) {
            throw new IllegalArgumentException();
        }

        if (settings.isBrowsingOnly()) {
            throw new UnsupportedOperationException("Browsing is not supported.");
        }
        if (settings.isDurable()) {
            if (settings.getName() == null) {
                throw new IllegalArgumentException("Durable subscription requires name.");
            }

            if (!(destination instanceof ActiveMQTransportTopic)) {
                throw new UnsupportedOperationException("Durable subscription only for topic.");
            }
        }

        ActiveMQTransportDestination amqDestination = (ActiveMQTransportDestination) destination;
        ActiveMQSession session = getOrCreateSession(destination.getConsumerMode());

        try {
            MessageConsumer amqConsumer;
            if (settings.isDurable()) {
                ActiveMQTransportTopic amqTopic = (ActiveMQTransportTopic) amqDestination;

                if (settings.getSelector() != null) {
                    amqConsumer = session.createDurableSubscriber(amqTopic.getTopic(),
                            settings.getName(), settings.getSelector(), false);
                } else {
                    amqConsumer = session.createDurableSubscriber(amqTopic.getTopic(),
                            settings.getName());
                }
            } else {
                if (settings.getSelector() != null) {
                    amqConsumer = session.createConsumer(amqDestination.getDestination(),
                            settings.getSelector(), false);
                } else {
                    amqConsumer = session.createConsumer(amqDestination.getDestination());
                }
            }

            amqConsumer.setMessageListener(jmsMessage -> {
                ImmutableMessage localMessage = null;
                try {
                    localMessage = serializer.fromJms(jmsMessage).toImmutable();
                } catch (JMSException e) {
                    TransportEventArgs eventArgs = new TransportEventArgs(
                            TransportEventArgs.EXCEPTION_EVENT_ID,
                            "Failed to close subscription while onMessage failed.", e);

                    trigerTransportEvent(eventArgs);
                }

                if (localMessage != null) {
                    consumer.accept(localMessage);
                }
            });

            return new ActiveMQTransportSubscription(session, amqConsumer, settings.getName());
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    @Override
    public void commit() throws TransportException, UnsupportedOperationException {
        if (!isTransacted) {
            throw new UnsupportedOperationException("Transaction is not supported.");
        }

        try {
            producerSession.commit();
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    @Override
    public void rollback() throws TransportException, UnsupportedOperationException {
        if (!isTransacted) {
            throw new UnsupportedOperationException("Transaction is not supported.");
        }

        try {
            producerSession.rollback();
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    private void trigerTransportEvent(TransportEventArgs eventArgs) {
        ImmutableList<TransportEventListener> listeners = eventListeners;
        for (TransportEventListener listener : listeners) {
            listener.onEvent(this, eventArgs);
        }
    }

    private ActiveMQSession getOrCreateSession(ConsumerMode consumerMode) {
        return sessionByConsumerMode.computeIfAbsent(consumerMode, mode -> {
            try {
                int ackMode = Session.AUTO_ACKNOWLEDGE;
                switch (mode) {
                case AUTO:
                    ackMode = Session.AUTO_ACKNOWLEDGE;
                    break;
                case CLIENT:
                    ackMode = Session.CLIENT_ACKNOWLEDGE;
                    break;
                case DUPS_OK:
                    ackMode = Session.DUPS_OK_ACKNOWLEDGE;
                    break;
                case NO_ACK:
                    ackMode = Session.DUPS_OK_ACKNOWLEDGE;
                    break;
                default:
                    throw new RuntimeException("Unrecognized acknowledge mode: " + mode);
                }

                return (ActiveMQSession) connection.createSession(isTransacted, ackMode);
            } catch (JMSException e) {
                throw new RuntimeException("Failed to create session.", e);
            }
        });
    }
}
