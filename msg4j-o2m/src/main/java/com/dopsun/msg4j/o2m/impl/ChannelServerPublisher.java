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

import com.dopsun.msg4j.core.messages.WritableMessage;

/**
 * Channel server publisher.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
abstract class ChannelServerPublisher<T> extends ChannelPublisher<T> {
    private final String serverSessionId;

    /**
     * @param serverSessionId
     * @param sender
     */
    protected ChannelServerPublisher(String serverSessionId, ChannelMessageSender<T> sender) {
        super(sender);

        Objects.requireNonNull(serverSessionId);

        this.serverSessionId = serverSessionId;
    }

    /**
     * @return the serverSessionId
     */
    public String getServerSessionId() {
        return serverSessionId;
    }

    @Override
    protected void beforePublish(WritableMessage message) {
        super.beforePublish(message);

        O2mMessages.Fields.ServerSessionId.put(message, serverSessionId);
    }
}
