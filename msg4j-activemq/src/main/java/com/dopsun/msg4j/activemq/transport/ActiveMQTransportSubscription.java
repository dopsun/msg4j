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

import java.util.Objects;

import javax.annotation.Nullable;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;

import org.apache.activemq.ActiveMQSession;

import com.dopsun.msg4j.core.delivery.transports.TransportException;
import com.dopsun.msg4j.core.delivery.transports.TransportSubscription;

/**
 * @author Dop Sun
 * @since 1.0.0
 */
final class ActiveMQTransportSubscription implements TransportSubscription {
    private final ActiveMQSession session;
    private final MessageConsumer consumer;

    /**
     * If this is not <code>null</code>, then this is a durable subscription.
     */
    @Nullable
    private final String durableSubscriberName;

    /**
     * @param session
     * @param consumer
     * @param durableSubscriberName
     *            not <code>null</code> if this is durable subscription.
     */
    ActiveMQTransportSubscription(ActiveMQSession session, MessageConsumer consumer,
            String durableSubscriberName) {
        Objects.requireNonNull(session);
        Objects.requireNonNull(consumer);

        this.session = session;
        this.consumer = consumer;

        this.durableSubscriberName = durableSubscriberName;
    }

    @Override
    public void close() throws Exception {
        try {
            if (durableSubscriberName != null) {
                session.unsubscribe(durableSubscriberName);
            }

            consumer.close();
        } catch (JMSException e) {
            throw new TransportException("Failed to close transport.", e);
        }
    }
}
