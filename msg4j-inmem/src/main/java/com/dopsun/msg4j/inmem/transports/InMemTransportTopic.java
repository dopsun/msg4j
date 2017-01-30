/*
 * Copyright (c) 2017 Dop Sun. All rights reserved.
 */

package com.dopsun.msg4j.inmem.transports;

import com.dopsun.msg4j.core.delivery.transports.ConsumerMode;
import com.dopsun.msg4j.core.delivery.transports.ProducerMode;
import com.dopsun.msg4j.core.delivery.transports.TransportTopic;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class InMemTransportTopic extends InMemTransportDestination implements TransportTopic {

    /**
     * @param subject
     * @param producerMode
     * @param consumerMode
     */
    public InMemTransportTopic(String subject, ProducerMode producerMode,
            ConsumerMode consumerMode) {
        super(subject, producerMode, consumerMode);
    }

}
