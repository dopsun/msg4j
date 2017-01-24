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

package com.dopsun.msg4j.core.delivery.transports;

import java.util.EnumSet;
import java.util.function.Consumer;

import javax.annotation.concurrent.ThreadSafe;

import com.dopsun.msg4j.core.messages.Message;

/**
 * A transport for message delivery. This is an API abstraction of underlying messaging delivery
 * service.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
@ThreadSafe
public interface Transport extends AutoCloseable {
    /**
     * @return capabilities for this transport.
     */
    EnumSet<TransportCapability> capabilities();

    /**
     * @param listener
     *            lister of transport event
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     */
    void addEventListener(TransportEventListener listener) throws TransportException;

    /**
     * Removes first occurrence of <code>listener</code>.
     * 
     * @param listener
     *            listener to transport event
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     */
    void removeEventListener(TransportEventListener listener) throws TransportException;

    /**
     * Creates the queue for <code>subject</code>.
     * 
     * <p>
     * Calling this function will not provision queue within underlying messaging service provider.
     * </p>
     * 
     * @param subject
     *            subject for the queue.
     * @param producerMode
     *            producer mode
     * @param consumerMode
     *            consumer mode
     * @return transport queue object.
     * 
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     * @throws UnsupportedOperationException
     *             if <code>producerMode</code> or <code>consumerMode</code> is not supported.
     */
    TransportQueue createQueue(String subject, ProducerMode producerMode, ConsumerMode consumerMode)
            throws TransportException, UnsupportedOperationException;

    /**
     * Creates the topic for <code>subject</code>.
     * 
     * <p>
     * Calling this function will not provision topic within underlying messaging service provider.
     * </p>
     * 
     * @param subject
     *            subject for the topic.
     * @param producerMode
     *            producer mode
     * @param consumerMode
     *            consumer mode
     * @return transport queue object.
     * 
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     * @throws UnsupportedOperationException
     *             if <code>producerMode</code> or <code>consumerMode</code> is not supported.
     */
    TransportTopic createTopic(String subject, ProducerMode producerMode, ConsumerMode consumerMode)
            throws TransportException, UnsupportedOperationException;

    /**
     * Publishes message to destination.
     * 
     * @param destination
     *            destination where message to publish, {@link TransportQueue queue} or
     *            {@link TransportTopic topic}.
     * @param message
     *            message to deliver
     * 
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     */
    void publish(TransportDestination destination, Message message) throws TransportException;

    /**
     * Subscribes message from the transport. Messages will be delivered to <code>consumer</code>.
     * 
     * @param destination
     *            destination to subscribe
     * @param settings
     *            settings for subscription.
     * @param consumer
     *            messages delivered to this consumer.
     * @return subscription
     * 
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     * @throws UnsupportedOperationException
     *             if type of subscription is not supported.
     */
    TransportSubscription subscribe(TransportDestination destination,
            TransportSubscriberSettings settings, Consumer<Message> consumer)
            throws TransportException, UnsupportedOperationException;

    /**
     * Commits transaction.
     * 
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     * @throws UnsupportedOperationException
     *             if {@link TransportCapability#Transaction} not supported.
     */
    void commit() throws TransportException, UnsupportedOperationException;

    /**
     * Rollback transaction.
     * 
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     * @throws UnsupportedOperationException
     *             if {@link TransportCapability#Transaction} not supported.
     */
    void rollback() throws TransportException, UnsupportedOperationException;
}
