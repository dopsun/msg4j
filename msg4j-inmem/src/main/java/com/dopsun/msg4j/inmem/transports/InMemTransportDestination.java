/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.inmem.transports;

import java.util.Objects;

import com.dopsun.msg4j.core.delivery.transports.ConsumerMode;
import com.dopsun.msg4j.core.delivery.transports.ProducerMode;
import com.dopsun.msg4j.core.delivery.transports.TransportDestination;
import com.google.common.base.MoreObjects;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
abstract class InMemTransportDestination implements TransportDestination {
    private final String subject;

    private final ProducerMode producerMode;

    private final ConsumerMode consumerMode;

    /**
     * @param subject
     * @param producerMode
     * @param consumerMode
     */
    protected InMemTransportDestination(String subject, ProducerMode producerMode,
            ConsumerMode consumerMode) {
        Objects.requireNonNull(subject);
        Objects.requireNonNull(producerMode);
        Objects.requireNonNull(consumerMode);

        this.subject = subject;
        this.producerMode = producerMode;
        this.consumerMode = consumerMode;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    @Override
    public ProducerMode getProducerMode() {
        return producerMode;
    }

    @Override
    public ConsumerMode getConsumerMode() {
        return consumerMode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }

        InMemTransportDestination another = (InMemTransportDestination) obj;

        /** @formatter:off */
        return subject.equals(another.subject) 
                && producerMode.equals(another.producerMode)
                && consumerMode.equals(another.consumerMode);
        /** @formatter:on */
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(subject);
        hash = hash * 31 + Objects.hashCode(producerMode);
        hash = hash * 31 + Objects.hashCode(consumerMode);
        return hash;
    }

    @Override
    public String toString() {
        /** @formatter:off */
        return MoreObjects.toStringHelper(this.getClass())
                .add("subject", subject)
                .add("producerMode", producerMode)
                .add("consumerMode", consumerMode)
                .toString();
        /** @formatter:on */
    }
}
