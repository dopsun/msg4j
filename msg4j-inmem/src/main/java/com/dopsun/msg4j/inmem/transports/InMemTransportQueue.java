/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.inmem.transports;

import com.dopsun.msg4j.core.delivery.transports.ConsumerMode;
import com.dopsun.msg4j.core.delivery.transports.ProducerMode;
import com.dopsun.msg4j.core.delivery.transports.TransportQueue;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class InMemTransportQueue extends InMemTransportDestination implements TransportQueue {

    /**
     * @param subject
     * @param producerMode
     * @param consumerMode
     */
    public InMemTransportQueue(String subject, ProducerMode producerMode,
            ConsumerMode consumerMode) {
        super(subject, producerMode, consumerMode);
    }

    @Override
    public boolean isLastValueQueue() {
        return false;
    }

}
