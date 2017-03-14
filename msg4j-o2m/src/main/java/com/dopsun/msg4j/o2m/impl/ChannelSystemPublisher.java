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

import com.dopsun.msg4j.o2m.O2mServiceException;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.ServerStartedNotificationWriter;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.ServerStoppedNotificationWriter;

/**
 * Publisher for system channel where server sends messages to all live clients.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
final class ChannelSystemPublisher<T> extends ChannelServerPublisher<T> {

    /**
     * @param serverSessionId
     * @param sender
     */
    public ChannelSystemPublisher(String serverSessionId, ChannelMessageSender<T> sender) {
        super(serverSessionId, sender);
    }

    public void sendServerStartedNotification(String serverId, String version)
            throws O2mServiceException {
        Objects.requireNonNull(serverId);
        Objects.requireNonNull(version);

        ServerStartedNotificationWriter writer = O2mMessages.newServerStartedNotification();
        writer.setServerId(serverId);
        writer.setVersion(version);

        this.syncPublish(writer.getMessage());
    }

    public void sendServerStoppeddNotification(String serverId) throws O2mServiceException {
        Objects.requireNonNull(serverId);

        ServerStoppedNotificationWriter writer = O2mMessages.newServerStoppedNotification();
        writer.setServerId(serverId);

        this.syncPublish(writer.getMessage());
    }
}
