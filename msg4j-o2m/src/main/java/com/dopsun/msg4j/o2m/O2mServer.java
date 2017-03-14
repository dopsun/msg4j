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

import javax.annotation.concurrent.ThreadSafe;

import com.dopsun.msg4j.core.delivery.transports.Transport;
import com.dopsun.msg4j.core.delivery.transports.TransportException;
import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.o2m.impl.One2Many;

/**
 * One to many server end point.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
@ThreadSafe
public interface O2mServer extends AutoCloseable {
    /**
     * @param transport
     * @param config
     * @return
     * @throws TransportException
     * @throws O2mServiceException
     */
    public static O2mServer create(Transport transport, O2mServerConfiguration config)
            throws TransportException, O2mServiceException {
        Objects.requireNonNull(transport);
        Objects.requireNonNull(config);

        return One2Many.create(transport, config);
    }

    /**
     * Returns state of this server.
     * 
     * @return state
     */
    O2mServerState getState();

    /**
     * Adds <code>listener</code> to this client. Same <code>listener</code> can be added multiple
     * times.
     * 
     * @param listener
     *            listener to add
     */
    void addEventListener(O2mServerEventListener listener);

    /**
     * Removes first occurrence <code>listener</code> from this client. If listener is
     * {@link #addEventListener(O2mServerEventListener) added} multiple times, then first one will
     * be removed.
     * 
     * @param listener
     *            listener to remove
     * 
     * @return <code>true</code> if removed successfully
     */
    boolean removeEventListener(O2mServerEventListener listener);

    /**
     * Publishes message to channel.
     * 
     * @param channelCode
     *            channel where message publishing to
     * @param message
     *            message to be published
     * 
     * @throws O2mServiceException
     */
    void publish(String channelCode, ImmutableMessage message) throws O2mServiceException;
}
