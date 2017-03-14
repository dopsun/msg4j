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

package com.dopsun.msg4j.o2m;

import java.util.Objects;
import java.util.concurrent.Future;

import javax.annotation.concurrent.ThreadSafe;

import com.dopsun.msg4j.core.delivery.transports.Transport;
import com.dopsun.msg4j.core.delivery.transports.TransportException;
import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.o2m.impl.One2Many;

/**
 * One to many client end point.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
@ThreadSafe
public interface O2mClient extends AutoCloseable {
    /**
     * Creates a new {@link O2mClient}.
     * 
     * @param transport
     * @param config
     * @return
     * 
     * @throws TransportException
     * @throws O2mServiceException
     * 
     * @throws InterruptedException
     *             if connecting was interrupted.
     */
    public static O2mClient create(Transport transport, O2mClientConfiguration config)
            throws TransportException, O2mServiceException, InterruptedException {
        Objects.requireNonNull(transport);
        Objects.requireNonNull(config);

        return One2Many.create(transport, config);
    }

    /**
     * Returns state of this client.
     * 
     * @return state
     */
    O2mClientState getState();

    /**
     * Adds <code>listener</code> to this client. Same <code>listener</code> can be added multiple
     * times.
     * 
     * @param listener
     *            listener to add
     */
    void addEventListener(O2mClientEventListener listener);

    /**
     * Removes first occurrence <code>listener</code> from this client. If listener is
     * {@link #addEventListener(O2mClientEventListener) added} multiple times, then first one will
     * be removed.
     * 
     * @param listener
     *            listener to remove
     * @return <code>true</code> if removed successfully
     */
    boolean removeEventListener(O2mClientEventListener listener);

    /**
     * Requests to server. Request will be sent to server, and result is returned asynchronously.
     * 
     * @param request
     *            request to sent.
     * @return a future from where can receive reply.
     * 
     * @throws O2mServiceException
     * @throws UnsupportedOperationException
     *             if not supported by underlying transport.
     */
    Future<ImmutableMessage> request(ImmutableMessage request)
            throws O2mServiceException, UnsupportedOperationException;

    /**
     * Subscribes data from <code>channelCode</code>. There should be only one active
     * {@link O2mClientSubscriptionMode#REALTIME} or {@link O2mClientSubscriptionMode#UPDATE}. But
     * it can be do multiple snapshot concurrently, as it may do with different <code>filter</code>.
     * 
     * @param channelCode
     *            channel code where data was subscribed from
     * @param mode
     *            subscription mode
     * @param filter
     *            filter of messages if supported
     * @param callback
     *            callback while message received.
     * @return subscription
     * 
     * @throws O2mServiceException
     * @throws UnsupportedOperationException
     *             if not supported, e.g. filter not supported.
     * 
     * @see #subscribe(String, O2mClientSubscriptionMode, O2mClientSubscriptionCallback)
     */
    O2mClientSubscription subscribe(String channelCode, O2mClientSubscriptionMode mode,
            O2mClientSubscriptionFilter filter, O2mClientSubscriptionCallback callback)
            throws O2mServiceException, UnsupportedOperationException;

    /**
     * Subscribes data from <code>channelCode</code>.There should be only one active
     * {@link O2mClientSubscriptionMode#REALTIME} or {@link O2mClientSubscriptionMode#UPDATE}. But
     * it can be do multiple snapshot concurrently, as it may do with different <code>filter</code>.
     * 
     * @param channelCode
     *            channel code where data was subscribed from
     * @param mode
     *            subscription mode
     * @param callback
     *            callback while message received.
     * @return subscription
     * 
     * @throws O2mServiceException
     * @throws UnsupportedOperationException
     *             if not supported.
     * 
     * @see #subscribe(String, O2mClientSubscriptionMode, O2mClientSubscriptionFilter,
     *      O2mClientSubscriptionCallback)
     */
    O2mClientSubscription subscribe(String channelCode, O2mClientSubscriptionMode mode,
            O2mClientSubscriptionCallback callback)
            throws O2mServiceException, UnsupportedOperationException;
}
