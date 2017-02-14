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
     * <p>
     * <b>Important notes:</b>
     * </p>
     * 
     * <p>
     * Once returned, the subscription have been registered to underlying transport service. But
     * this subscription may not be propagated to the whole network in certain case. When this
     * happens:
     * <ol>
     * <li>Client subscribes to destination.</li>
     * <li>Client sends data subscription to server, via another destination listened by server.
     * </li>
     * <li>Server can immediately publish data to the destination client subscribe to.</li>
     * </ol>
     * </p>
     * 
     * <p>
     * For example, Multi-nodes Routing in Solace, the subscription may take time to propagate to
     * all appliances in the network. So, the 3rd step in above sequences, the first several message
     * may not be reaching the client.
     * </p>
     * 
     * <p>
     * If high level services want the sequence to be guaranteed, the following actions should be
     * enforced:
     * </p>
     * 
     * <ol>
     * <li>Client subscribes to destination.
     * <ol>
     * <li>Client sends subscription confirmation to server. How to send server pending to high
     * level service defined.</li>
     * <li>Server publishes test data to which client subscribes. Depends on implementation, server
     * may publish several messages instead of one, to ensure that client can receive at least
     * one.</li>
     * <li>Client receives the data from subscription, and confirms that subscription reached
     * producer. Client will ignore multiple confirmation messages after confirmed.</li>
     * </ol>
     * </li>
     * 
     * <li>Client sends data subscription to server, via another destination listened by server.
     * </li>
     * <li>Server can publish data to the destination client subscribe to now.</li>
     * </ol>
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
