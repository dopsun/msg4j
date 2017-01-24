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
     * Removes first occurence of <code>listener</code>.
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
     * @param subject
     *            subject for the queue.
     * @param lastValueQueue
     *            <code>true</code> if requesting to create last value queue.
     * @return transport queue object.
     * 
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     * @throws UnsupportedOperationException
     *             if <code>lastValueQueue</code> is <code>false</code>,
     *             {@link TransportCapability#Guaranteed} not supported, or
     *             <code>lastValueQueue</code> is <code>true</code> and
     *             {@link TransportCapability#LastValue} is not supported.
     */
    TransportQueue createQueue(String subject, boolean lastValueQueue)
            throws TransportException, UnsupportedOperationException;

    /**
     * Creates the queue for <code>subject</code>.
     * 
     * @param subject
     *            subject for the queue.
     * @param durable
     *            <code>true</code> if topic is durable, and messages are sent and persisted.
     * @param lastValueQueue
     *            <code>true</code> if requesting to create last value queue.
     * @return transport queue object.
     * 
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     */
    TransportTopic createTopic(String subject, boolean durable) throws TransportException;

    /**
     * Publishes message with direct.
     * 
     * @param destination
     *            destination where message to publish
     * @param message
     *            message to deliver
     * 
     * @throws TransportException
     *             transport exception, e.g. transport has closed.
     */
    void publish(TransportDestination destination, Message message) throws TransportException;

    /**
     * Consumers message from the transport.
     * 
     * @param destination
     *            destination where message to enqueue
     * @param settings
     * @param consumer
     *            consumer to get data.
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
