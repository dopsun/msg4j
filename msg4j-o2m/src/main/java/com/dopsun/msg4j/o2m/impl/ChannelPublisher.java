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

package com.dopsun.msg4j.o2m.impl;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import com.dopsun.msg4j.core.messages.WritableMessage;
import com.dopsun.msg4j.o2m.O2mServiceException;

/**
 * An channel publisher sending message.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
abstract class ChannelPublisher<T> {
    private final ChannelMessageSender<T> sender;
    private final AtomicLong nextSeqNum = new AtomicLong(1);

    protected ChannelPublisher(ChannelMessageSender<T> sender) {
        Objects.requireNonNull(sender);

        this.sender = sender;
    }

    /**
     * Gets the next sequence number to be used. The number returned has not been used yet.
     * 
     * @return the next sequence number to be used.
     */
    public long getNextSeqNum() {
        return nextSeqNum.get();
    }

    /**
     * Publishes <code>message</code> to this channel.
     * 
     * <p>
     * This is <b>synchronized</b>.
     * </p>
     * 
     * @param message
     *            message to send.
     * 
     * @return value returned from
     *         {@link ChannelMessageSender#send(com.dopsun.msg4j.core.messages.Message)}.
     * 
     * @throws O2mServiceException
     *             throws while publish failed.
     * 
     * @see ChannelMessageSender#send(com.dopsun.msg4j.core.messages.Message)
     */
    public synchronized final T syncPublish(WritableMessage message) throws O2mServiceException {
        Objects.requireNonNull(message);

        long seqNum = nextSeqNum.getAndIncrement();

        O2mMessages.Fields.SeqNum.put(message, seqNum);
        O2mMessages.Fields.Timestamp.put(message, System.currentTimeMillis());

        beforePublish(message);

        return this.sender.send(message);
    }

    protected void beforePublish(WritableMessage message) {

    }
}
