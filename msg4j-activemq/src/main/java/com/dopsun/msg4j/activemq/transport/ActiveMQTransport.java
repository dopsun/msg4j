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
 * @author Dop Sun
 * @since 1.0.0
 */
public class ActiveMQTransport implements Transport {
    /** @formatter:off */
    private static final EnumSet<TransportCapability> DEF_CAPABILITIES = EnumSet
            .of(
                    TransportCapability.Guaranteed, 
                    TransportCapability.DurableSubscriber,
                    TransportCapability.Selector
               );
    /** @formatter:on */

    private final ActiveMQSerializer serializer;
    private final boolean isTransacted;

    private final EnumSet<TransportCapability> capabilities;

    private final ConcurrentHashMap<String, ActiveMQTransportQueue> queueBySubject = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ActiveMQTransportTopic> topicBySubject = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<ActiveMQTransportDestination, ActiveMQMessageProducer> producerBySubject = new ConcurrentHashMap<>();

    private final ActiveMQConnection connection;
    private final ActiveMQSession session;

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

        this.isTransacted = config.isTransacted();
        this.serializer = new ActiveMQSerializer();

        this.capabilities = EnumSet.copyOf(DEF_CAPABILITIES);
        if (isTransacted) {
            this.capabilities.add(TransportCapability.Transaction);
        }

        String brokerUrl = config.getBrokerUrl();

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        ActiveMQConnection amqConnection = (ActiveMQConnection) connectionFactory
                .createConnection();

        amqConnection.setExceptionListener(this::onConnectionException);

        amqConnection.start();

        ActiveMQSession amqSession = (ActiveMQSession) amqConnection.createSession(isTransacted,
                Session.AUTO_ACKNOWLEDGE);

        this.connection = amqConnection;
        this.session = amqSession;
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

            this.session.close();
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
    public ActiveMQTransportQueue createQueue(String subject, boolean lastValueQueue)
            throws TransportException, UnsupportedOperationException {
        Objects.requireNonNull(subject);

        if (lastValueQueue) {
            throw new UnsupportedOperationException("Last-value queue is not supported.");
        }

        ActiveMQTransportQueue transportQueue = queueBySubject.get(subject);
        if (transportQueue != null) {
            return transportQueue;
        }

        try {
            ActiveMQQueue queue = (ActiveMQQueue) session.createQueue(subject);
            transportQueue = new ActiveMQTransportQueue(queue);
            queueBySubject.put(subject, transportQueue);
            return transportQueue;
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    @Override
    public TransportTopic createTopic(String subject, boolean durable) throws TransportException {
        Objects.requireNonNull(subject);

        ActiveMQTransportTopic transportTopic = topicBySubject.get(subject);
        if (transportTopic != null) {
            if (transportTopic.isDurable() != durable) {
                throw new IllegalArgumentException(
                        "Subject '" + subject + "' should be durable: " + durable);
            }

            return transportTopic;
        }

        try {
            ActiveMQTopic topic = (ActiveMQTopic) session.createTopic(subject);
            transportTopic = new ActiveMQTransportTopic(topic, durable);
            topicBySubject.put(subject, transportTopic);
            return transportTopic;
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    @Override
    public void publish(TransportDestination destination, Message message)
            throws TransportException {
        Objects.requireNonNull(destination);
        if (!(destination instanceof ActiveMQTransportDestination)) {
            throw new IllegalArgumentException();
        }
        ActiveMQTransportDestination amqDestination = (ActiveMQTransportDestination) destination;

        ActiveMQMessageProducer producer = producerBySubject.get(amqDestination);
        if (producer == null) {
            try {
                producer = (ActiveMQMessageProducer) session
                        .createProducer(amqDestination.getDestination());

                if (amqDestination.isDurable()) {
                    producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                }
            } catch (JMSException e) {
                throw new TransportException(e);
            }
            producerBySubject.put(amqDestination, producer);
        }

        try {
            javax.jms.Message jmsMessage = serializer.toJms(session, message);
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
        if (!(destination instanceof ActiveMQTransportDestination)) {
            throw new IllegalArgumentException();
        }
        Objects.requireNonNull(settings);
        Objects.requireNonNull(consumer);

        if (settings.isBrowsingOnly()) {
            throw new UnsupportedOperationException("Browsing is not supported.");
        }
        if (settings.isDurable() && !(destination instanceof ActiveMQTransportTopic)) {
            throw new UnsupportedOperationException("Durable subscription only for topic.");
        }

        ActiveMQTransportDestination amqDestination = (ActiveMQTransportDestination) destination;

        try {
            MessageConsumer amqConsumer;
            if (settings.isDurable()) {
                ActiveMQTransportTopic amqTopic = (ActiveMQTransportTopic) amqDestination;

                if (settings.getSelector() != null) {
                    amqConsumer = session.createDurableSubscriber(amqTopic.getTopic(),
                            settings.getDurableSubscriberName(), settings.getSelector(), false);
                } else {
                    amqConsumer = session.createDurableSubscriber(amqTopic.getTopic(),
                            settings.getDurableSubscriberName());
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

            return new ActiveMQTransportSubscription(session, amqConsumer,
                    settings.getDurableSubscriberName());
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    @Override
    public void commit() throws TransportException, UnsupportedOperationException {
        try {
            session.commit();
        } catch (JMSException e) {
            throw new TransportException(e);
        }
    }

    @Override
    public void rollback() throws TransportException, UnsupportedOperationException {
        try {
            session.rollback();
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
}
