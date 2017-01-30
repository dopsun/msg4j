/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.inmem.transports;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.dopsun.msg4j.core.delivery.transports.ConsumerMode;
import com.dopsun.msg4j.core.delivery.transports.TransportException;
import com.dopsun.msg4j.core.delivery.transports.TransportQueue;
import com.dopsun.msg4j.core.delivery.transports.TransportSubscriberSettings;
import com.dopsun.msg4j.core.delivery.transports.TransportSubscription;
import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.Message;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.Service;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
public final class InMemBroker implements AutoCloseable {
    private final ConcurrentHashMap<String, TransferQueue<ImmutableMessage>> queueMessages = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, TransferQueue<ImmutableMessage>> topicMessages = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, Set<SubscriptionImpl>> queueSubscriptions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Set<SubscriptionImpl>> topicSubscriptions = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<InMemTransportDestination, TransferQueue<ImmutableMessage>> destinationSpool = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<InMemTransportDestination, Service> destinationService = new ConcurrentHashMap<>();

    /**
     * 
     */
    public InMemBroker() {
    }

    @Override
    public void close() throws Exception {
        for (Service service : destinationService.values()) {
            service.stopAsync().awaitTerminated();
        }

        destinationService.clear();
        destinationSpool.clear();
    }

    /**
     * @return
     */
    public InMemTransport createTransport() {
        return new InMemTransport(this);
    }

    /**
     * Publishes message.
     * 
     * @param destination
     * @param message
     */
    public void publish(InMemTransportDestination destination, Message message) {
        Objects.requireNonNull(destination);
        Objects.requireNonNull(message);

        TransferQueue<ImmutableMessage> spool = ensureDestination(destination);

        ImmutableMessage immutableMessage;

        if (message.isImmutable()) {
            immutableMessage = message.asImmutable().get();
        } else {
            immutableMessage = message.asWritable().get().toImmutable();
        }

        if (!spool.tryTransfer(immutableMessage)) {
            spool.add(immutableMessage);
        }
    }

    /**
     * @param destination
     * @param settings
     * @param consumer
     * @return
     * @throws TransportException
     * @throws UnsupportedOperationException
     */
    public TransportSubscription subscribe(InMemTransportDestination destination,
            TransportSubscriberSettings settings, Consumer<Message> consumer)
            throws TransportException, UnsupportedOperationException {
        SubscriptionImpl subscription = new SubscriptionImpl(destination, settings, consumer);

        if (destination instanceof TransportQueue) {
            Set<SubscriptionImpl> set = queueSubscriptions.computeIfAbsent(destination.getSubject(),
                    subject -> Sets.newConcurrentHashSet());
            set.add(subscription);
        } else {
            Set<SubscriptionImpl> set = topicSubscriptions.computeIfAbsent(destination.getSubject(),
                    subject -> Sets.newConcurrentHashSet());
            set.add(subscription);
        }

        return subscription;
    }

    private TransferQueue<ImmutableMessage> ensureDestination(
            InMemTransportDestination destination) {
        ConcurrentHashMap<String, TransferQueue<ImmutableMessage>> destMessages;

        if (destination instanceof TransportQueue) {
            destMessages = queueMessages;
        } else {
            destMessages = topicMessages;
        }

        final TransferQueue<ImmutableMessage> spool = destMessages
                .computeIfAbsent(destination.getSubject(), dest -> new LinkedTransferQueue<>());
        destinationService.computeIfAbsent(destination, dest -> {
            Service service = new AbstractExecutionThreadService() {
                @Override
                protected void run() throws Exception {
                    while (this.isRunning()) {
                        dispatchQueue(dest, spool);
                    }
                }
            };

            service.startAsync().awaitRunning();

            return service;
        });

        return spool;
    }

    private void dispatchQueue(InMemTransportDestination destination,
            TransferQueue<ImmutableMessage> spool) {
        try {
            if (destination instanceof TransportQueue) {
                ImmutableMessage message = spool.peek();

                if (message != null) {
                    Set<SubscriptionImpl> subscriptionSet = queueSubscriptions
                            .get(destination.getSubject());
                    for (SubscriptionImpl subscription : subscriptionSet) {
                        try {
                            subscription.consumer.accept(message);

                            spool.take(); // acknowledged
                        } catch (Throwable ex) {
                            if (subscription.destination.getConsumerMode()
                                    .equals(ConsumerMode.NO_ACK)) {
                                spool.take(); // acknowledged
                            } else {
                                Thread.sleep(1000); // will be delivered again in 1 second.
                            }
                        }

                        break; // Only first consumer to be triggered.
                    }
                }
            } else {
                ImmutableMessage message = spool.poll(100, TimeUnit.MILLISECONDS);
                if (message != null) {
                    Set<SubscriptionImpl> subscriptionSet = topicSubscriptions
                            .get(destination.getSubject());
                    if (subscriptionSet != null) {
                        for (SubscriptionImpl subscription : subscriptionSet) {
                            subscription.consumer.accept(message);
                        }
                    }
                }
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private class SubscriptionImpl implements TransportSubscription {
        private final InMemTransportDestination destination;

        @SuppressWarnings("unused")
        private final SubscriptionSettings settings;

        private final Consumer<Message> consumer;

        public SubscriptionImpl(InMemTransportDestination destination,
                TransportSubscriberSettings settings, Consumer<Message> consumer) {
            Objects.requireNonNull(destination);
            Objects.requireNonNull(settings);
            Objects.requireNonNull(consumer);

            this.destination = destination;
            this.settings = new SubscriptionSettings(settings);
            this.consumer = consumer;
        }

        @Override
        public void close() throws Exception {
            if (destination instanceof TransportQueue) {
                Set<SubscriptionImpl> set = queueSubscriptions.get(destination.getSubject());
                if (set != null) {
                    set.remove(this);
                }
            } else {
                Set<SubscriptionImpl> set = topicSubscriptions.get(destination.getSubject());
                if (set != null) {
                    set.remove(this);
                }
            }
        }
    }

    private class SubscriptionSettings {
        @SuppressWarnings("unused")
        private final boolean browsingOnly;

        @SuppressWarnings("unused")
        private final boolean durable;

        @Nullable
        private final String name;

        @Nullable
        private final String selector;

        public SubscriptionSettings(TransportSubscriberSettings settings) {
            Objects.requireNonNull(settings);
            this.browsingOnly = settings.isBrowsingOnly();
            this.durable = settings.isDurable();
            this.name = settings.getName();
            this.selector = settings.getSelector();
        }

    }
}
