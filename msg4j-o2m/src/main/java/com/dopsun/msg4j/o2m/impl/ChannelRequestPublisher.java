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
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.dopsun.msg4j.core.messages.ImmutableMessage;
import com.dopsun.msg4j.core.messages.WritableMessage;
import com.dopsun.msg4j.o2m.O2mClientConfiguration;
import com.dopsun.msg4j.o2m.O2mClientSubscriptionFilter;
import com.dopsun.msg4j.o2m.O2mClientSubscriptionMode;
import com.dopsun.msg4j.o2m.O2mServiceException;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.ClientConnectRequestWriter;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.ClientDisconnectRequestWriter;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.SubscribeRequestWriter;
import com.dopsun.msg4j.o2m.impl.O2mMessages.Messages.UserRequestWriter;

/**
 * Publisher for request channel where client sends request to server.
 * 
 * @author Dop Sun
 * @since 1.0.0
 */
final class ChannelRequestPublisher<T> extends ChannelPublisher<T> {
    private final String clientSessionId;
    private final Supplier<String> serverSessionIdSupplier;

    /**
     * @param clientSessionId
     * @param serverSessionIdSupplier
     *            server session id supplier, it's possible that server session is
     *            <code>null</code>.
     * @param sender
     */
    public ChannelRequestPublisher(String clientSessionId, Supplier<String> serverSessionIdSupplier,
            ChannelMessageSender<T> sender) {
        super(sender);

        Objects.requireNonNull(clientSessionId);
        Objects.requireNonNull(serverSessionIdSupplier);

        this.clientSessionId = clientSessionId;
        this.serverSessionIdSupplier = serverSessionIdSupplier;
    }

    /**
     * 
     * @param subscriptionId
     * @param channelCode
     * @param mode
     * @param filter
     * @return
     * @throws O2mServiceException
     */
    public T publishSubscribe(String subscriptionId, String channelCode,
            O2mClientSubscriptionMode mode, @Nullable O2mClientSubscriptionFilter filter)
            throws O2mServiceException {
        Objects.requireNonNull(subscriptionId);
        Objects.requireNonNull(channelCode);
        Objects.requireNonNull(mode);

        SubscribeRequestWriter writer = O2mMessages.newSubscribeRequest();

        writer.setChannelCode(channelCode);
        writer.setSubscribeMode(mode.name());
        writer.setSubscriptionId(subscriptionId);
        if (filter != null) {
            writer.setSelector(filter.getFilterMessage());
        }

        return syncPublish(writer.getMessage());
    }

    public T publishClientDisconnect() throws O2mServiceException {
        ClientDisconnectRequestWriter writer = O2mMessages.newClientDisconnectRequest();
        return syncPublish(writer.getMessage());
    }

    public T publishClientConnect(O2mClientConfiguration config) throws O2mServiceException {
        ClientConnectRequestWriter reqClientConnect = O2mMessages.newClientConnectRequest();

        reqClientConnect.setClientId(config.getClientId());
        reqClientConnect.setServerId(config.getServerId());
        reqClientConnect.setVersion(config.getVersion());

        return syncPublish(reqClientConnect.getMessage());
    }

    public T publishUserRequest(ImmutableMessage payload) throws O2mServiceException {
        Objects.requireNonNull(payload);

        UserRequestWriter writer = O2mMessages.newUserRequest();

        writer.setPayload(payload);

        return syncPublish(writer.getMessage());
    }

    @Override
    protected void beforePublish(WritableMessage message) {
        super.beforePublish(message);

        O2mMessages.Fields.ClientSessionId.put(message, clientSessionId);

        String serverSessionId = serverSessionIdSupplier.get();
        if (serverSessionId != null) {
            O2mMessages.Fields.ServerSessionId.put(message, serverSessionId);
        }
    }
}
