/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.inmem.transports;

import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Consumer;

import com.dopsun.msg4j.core.delivery.transports.ConsumerMode;
import com.dopsun.msg4j.core.delivery.transports.ProducerMode;
import com.dopsun.msg4j.core.delivery.transports.Transport;
import com.dopsun.msg4j.core.delivery.transports.TransportCapability;
import com.dopsun.msg4j.core.delivery.transports.TransportDestination;
import com.dopsun.msg4j.core.delivery.transports.TransportEventListener;
import com.dopsun.msg4j.core.delivery.transports.TransportException;
import com.dopsun.msg4j.core.delivery.transports.TransportQueue;
import com.dopsun.msg4j.core.delivery.transports.TransportSubscriberSettings;
import com.dopsun.msg4j.core.delivery.transports.TransportSubscription;
import com.dopsun.msg4j.core.delivery.transports.TransportTopic;
import com.dopsun.msg4j.core.messages.Message;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public class InMemTransport implements Transport {
    private final InMemBroker broker;

    /**
     * @param broker
     */
    public InMemTransport(InMemBroker broker) {
        Objects.requireNonNull(broker);

        this.broker = Objects.requireNonNull(broker);
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public EnumSet<TransportCapability> capabilities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addEventListener(TransportEventListener listener) throws TransportException {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeEventListener(TransportEventListener listener) throws TransportException {
        // TODO Auto-generated method stub

    }

    @Override
    public TransportQueue createQueue(String subject, ProducerMode producerMode,
            ConsumerMode consumerMode) throws TransportException, UnsupportedOperationException {
        return new InMemTransportQueue(subject, producerMode, consumerMode);
    }

    @Override
    public TransportTopic createTopic(String subject, ProducerMode producerMode,
            ConsumerMode consumerMode) throws TransportException, UnsupportedOperationException {
        return new InMemTransportTopic(subject, producerMode, consumerMode);
    }

    @Override
    public void publish(TransportDestination destination, Message message)
            throws TransportException {
        Objects.requireNonNull(destination);
        Objects.requireNonNull(message);

        InMemTransportDestination inMemDestination = (InMemTransportDestination) destination;
        broker.publish(inMemDestination, message);
    }

    @Override
    public TransportSubscription subscribe(TransportDestination destination,
            TransportSubscriberSettings settings, Consumer<Message> consumer)
            throws TransportException, UnsupportedOperationException {
        Objects.requireNonNull(destination);
        Objects.requireNonNull(settings);
        Objects.requireNonNull(consumer);

        InMemTransportDestination inMemDestination = (InMemTransportDestination) destination;
        return broker.subscribe(inMemDestination, settings, consumer);
    }

    @Override
    public void commit() throws TransportException, UnsupportedOperationException {
        // TODO Auto-generated method stub

    }

    @Override
    public void rollback() throws TransportException, UnsupportedOperationException {
        // TODO Auto-generated method stub

    }

}
