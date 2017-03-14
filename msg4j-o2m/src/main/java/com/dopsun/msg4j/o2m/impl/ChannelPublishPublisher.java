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

import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.o2m.O2mServiceException;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.ChannelUpdateWriter;

/**
 * Publisher for publish channel where server publishes updates to client.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
final class ChannelPublishPublisher<T> extends ChannelServerPublisher<T> {

    /**
     * @param serverSessionId
     * @param sender
     */
    public ChannelPublishPublisher(String serverSessionId, ChannelMessageSender<T> sender) {
        super(serverSessionId, sender);
    }

    /**
     * Publishes channel update message with <code>payload</code>.
     * 
     * @param payload
     * @throws O2mServiceException
     */
    public void publishChannelUpdate(ImmutableMessage payload) throws O2mServiceException {
        Objects.requireNonNull(payload);

        ChannelUpdateWriter writer = O2mMessages.newChannelUpdate();
        writer.setPayload(payload);

        syncPublish(writer.getMessage());
    }

    /**
     * Executes the runnable as synchronized.
     * 
     * <p>
     * This is <b>synchronized</b>.
     * </p>
     * 
     * @param runnable
     *            a runnable to run.
     */
    public synchronized void syncRun(Runnable runnable) {
        runnable.run();
    }
}
